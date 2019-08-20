<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh" />
    <Card v-for="(item,index) in data" :key="index" class="mt40">
        <Form :ref="`formItem${index}`" :model="item" label-position="left" :label-width="100" :rules="formItemInline">
            <Row>
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="item.status" size="large" :disabled="!item.isAdd">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
                <Col span="12" class="tr">
                    <div class="btn-toolbar" style="margin-top: -10px;">
                        <Button type="text" @click="edit(index)" v-if="!item.isAdd"><Icon type="trash-a" size="16" class="pr5"></Icon> 编辑</Button> 
                        <Button type="text" @click="del(item, index)" v-if="item.isAdd && data.length > 1"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="成员名称">
                        <Input v-model="item.name" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="与户主关系">
                        <Input v-model="item.relationship" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="性别">
                        <Select v-model="item.gender" :disabled="!item.isAdd" @on-change="change">
                            <Option value="男">男</Option>
                            <Option value="女">女</Option>
                        </Select>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="出生日期">
                        <DatePicker v-model="item.birthday" type="date" @on-change="change" :editable="false" placeholder="请选择日期" :options="options3" placement="bottom-end" :disabled="!item.isAdd"></DatePicker>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="手机号码" prop="phone">
                        <Input v-model="item.phone" :maxlength="11" @on-change="change" :disabled="!item.isAdd" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="劳动技能">
                        <Input v-model="item.skill" :maxlength="50" @on-change="change" :disabled="!item.isAdd" />
                    </Form-item>
                </Col>
            </Row>
        </Form>
        <div class="tc">
            <Button type="primary" v-if="item.isAdd" @click="save(item, index)">保存</Button>
        </div>
    </Card>
    <Form label-position="left" :label-width="150" class="pb20 mt40">
        <Row :gutter="32">
            <Col span="12">
                <Button type="success" ghost @click="handleAdd" icon="md-add" class="btn-light-primary">添加</Button>
            </Col>
        </Row>
    </Form>
    <Title title="文字预览"/>
    <div class="pd20 tc pt30">
        <Input v-model="preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        <Button type="primary" v-if="isLoading" class="mt40">保存</Button>
        <Button type="primary" v-else @click="handleSave()" class="mt40">保存</Button>
    </div>
  </div>
</template>
<script>
    import Title from '../../components/title'
    import vuiCascander from '~components/vuiCascader/index'
    import {isPhone2} from '~utils/validate'
    export default {
        components: {
            Title,
            vuiCascander
        },
        props: {
            modeId: {
                type: String
            },
            yearId: {
                type: String
            }
        },
        data () {
            return {
                title: '家庭成员信息',
                formItemInline: {
                    phone: [{validator: isPhone2, trigger: 'blur'}],
                },
                data: [{
                    name: '',
                    relationship: '',
                    gender: '',
                    birthday: '',
                    phone: '',
                    skill: '',
                    status: true,
                    isAdd: true
                }],
                options3:{
                    disabledDate (date) {
                        return date && date.valueOf() > Date.now();
                    }
                },
                preview: '',
                index: 0,
                units: [],
                templateId: '',
                isLoading: true
            }
        },
        created () {
            this.templateId = this.$route.query.templateId
            if (this.modeId !== '' && this.modeId !== undefined) {
                this.init()
                this.initTitle()
            }
        },
        watch: {
            modeId: {
                handler (newValue, oldValue) {
                    this.init()
                    this.initTitle()
                },
                deep: true
            }
        },
        methods: {
            initTitle () {
                this.$api.post('/member-reversion/user/perfect/findTableHead', {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.propertyName) {
                            this.title = response.data.propertyName
                        }
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 初始化加载数据
            init (type = 0) {
                this.$api.post('/member-reversion/familyMember/find', {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code === 200) {
                        this.isLoading = false
                        /* if (response.data.propertyName && response.data.propertyName !== '') {
                            this.title = response.data.propertyName
                        } */
                        if (response.data.preview && response.data.preview !== '') {
                            this.preview = response.data.preview
                        }
                        if (response.data.list.length !== 0) {
                            this.data = []
                            response.data.list.forEach((element, index) => {
                                this.data.push({
                                    id: element.id,
                                    name: element.name,
                                    relationship: element.relationship,
                                    gender: element.gender,
                                    birthday: element.birthday,
                                    phone: element.phone,
                                    skill: element.skill,
                                    status: element.status === 1 ? true : false,
                                    isAdd: false
                                })
                            })
                        }
                        if (type === 1) {
                            this.change()
                        }
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 增加
            handleAdd () {
                this.data.push({
                    name: '',
                    relationship: '',
                    gender: '',
                    birthday: '',
                    phone: '',
                    skill: '',
                    status: true,
                    isAdd: true
                })
                this.index = this.data.length - 1
            },
            edit (index) {
                this.data[index].isAdd = true
                this.index = index
            },
            del (item, index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确认删除？',
                    onOk:()=>{
                        if (item.id !== undefined && item.id !== '') {
                            this.$api.post('/member-reversion/familyMember/delete', {
                                id: item.id
                            }).then(response => {
                                if (response.code === 200) {
                                    this.$Message.success('删除成功！')
                                    this.data.splice(index, 1)
                                    this.change()
                                }
                            }).catch(error => {
                                this.$Message.error('服务器异常！')
                            })
                        } else {
                            this.$Message.success('删除成功！')
                            this.data.splice(index, 1)
                            this.change()
                        }
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            // 保存单个家庭成员信息
            save (item, index) {
                this.$refs[`formItem${index}`][0].validate(v => {
                    if (v) {
                        let data = {
                            account: this.$user.loginAccount,
                            yearId: this.yearId,
                            dictId: this.modeId,
                            isComplete: '1',
                            name: item.name,
                            relationship: item.relationship,
                            gender: item.gender,
                            birthday: item.birthday ? this.moment(item.birthday).format('YYYY-MM-DD'): '',
                            phone: item.phone,
                            skill: item.skill,
                            status: item.status,
                            id: item.id === '' || item.id === undefined ? '' : item.id,
                            templateId: this.templateId
                        }
                        this.$api.post('/member-reversion/familyMember/save', data).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('保存成功！')
                                this.init(1)
                            }
                        }).catch(error => {
                            this.$Message.error('服务器异常！')
                        })
                    } else {
                        this.$Message.erroe('请核对表单信息')
                    }
                })
            },
            // 保存预览信息
            handleSave () {
                this.isLoading = true
                let data = {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    isComplete: '1',
                    textPreview: this.preview,
                    templateId: this.templateId
                }
                this.$api.post('/member-reversion/perfect/saveTextPreview', data).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init()
                        this.$emit('on-save')
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 拼接文字预览
            change () {
                this.preview = ''
                this.preview += `共有 ${this.data.length} 名家庭成员，其中：`
                this.data.forEach(element => {
                    if (element.name && element.name !== '') {
                        this.preview += `${element.name}，`
                    }
                    if (element.relationship && element.relationship !== '') {
                        this.preview += `与户主关系：${element.relationship}，`
                    }
                    if (element.gender && element.gender !== '') {
                        this.preview += `性别：${element.gender}，`
                    }
                    if (element.birthday && element.birthday !== '') {
                        this.preview += `出生日期：${this.moment(element.birthday).format('YYYY-MM-DD')}，`
                    }
                    if (element.phone && element.phone !== '') {
                        this.preview += `手机号码：${element.phone}，`
                    }
                    if (element.skill && element.skill !== '') {
                        this.preview += `劳动技能：${element.skill}，`
                    }
                })
                this.preview = this.preview.substring(0, this.preview.length - 1) + '。'
            },
            leftRefresh () {
                this.$emit('left-refresh')
            }
        }
    }
</script>
<style lang="scss" scoped>
    .honor-picture-list {
        width: 10%;
        height: 100px;
        float:left;
        margin-right: 10px;
        &:last-child{
            margin-right: 0;
        }
    }
    .units .ivu-select-item{
        padding: 7px 8px;
    }
</style>