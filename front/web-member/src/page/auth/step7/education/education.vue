<template>
  <div class="pd20">
    <Title :title="title"  :id="id" :yearId="yearId" edit :templateId="templateId" @left-refresh="leftRefresh" />
    <Card v-for="(item,index) in data" :key="index" class="mt40">
        <Form :ref="`formItem${index}`" :model="item" label-position="left" :label-width="90" :rules="formItemInline">
            <Row>
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="item.status" size="large" :disabled="!item.isAdd" @on-change="changePreview">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
                <Col span="12" class="tr">
                    <div class="btn-toolbar" style="margin-top: -10px;">
                        <Button type="text" @click="edit(item, index)" v-if="!item.isAdd"><Icon type="trash-a" size="16" class="pr5"></Icon> 编辑</Button> 
                        <Button type="text" @click="del(item, index)" v-if="item.isAdd && data.length > 1"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="8">
                    <Form-item label="学校名称" prop="school_model">
                        <Input v-model="item.school_model" :maxlength="50" :disabled="!item.isAdd" @on-change="changePreview"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="学历" prop="education_model">
                        <Select v-model="item.education_model" :disabled="!item.isAdd" @on-change="changePreview">
                            <Option v-for="item in degrees" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="专业名称">
                        <Input v-model="item.major_model" :maxlength="50" :disabled="!item.isAdd"  @on-change="changePreview"/>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="8">
                    <Form-item label="是否统招">
                        <Select v-model="item.is_general_model" :disabled="!item.isAdd"  @on-change="changePreview">
                            <Option v-for="item in recruitmentList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="入学/毕业时间">
                        <DatePicker v-model="item.entrance_graduation_time_model" :editable="false"  @on-change="changePreview" type="daterange" :options="options" 
                            :disabled="!item.isAdd" style="width: 100%;"></DatePicker>
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
        <Input v-model="textPreview.text_preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        <Button type="primary" v-if="isLoading" class="mt40">保存</Button>
        <Button type="primary" v-else @click="handleSave()" class="mt40">保存</Button>
    </div>
  </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    import Title from '../../components/title'
    export default {
        components: {
            vuiUpload,
            Title
        },
        props: {
            id: String,
            yearId: {
            type: String
            },
            appId: {
            type: String
            }
        },
        data () {
            return {
                title: '教育经历',
                textPreview: {},
                sys_dict_id: '',
                formItemInline: {
                    school_model: [
                        { required: true, message: '请填写学校名称', trigger: 'blur' },
                    ],
                    education_model: [
                        { required: true, message: '请选择学历', trigger: 'blur' },
                    ]
                },
                data: [
                    {
                        school_model: '',
                        education_model: '',
                        major_model: '',
                        is_general_model: '',
                        entrance_graduation_time_model: ['', ''],
                        isAdd: true,
                        status: true
                    }
                ],
                options: {
                    disabledDate (date) {
                        return date && date.valueOf() > Date.now()
                    }
                },
                preview: '',
                index: 0,
                degrees:[
                    {
                        value: '小学',
                        label: '小学'
                    },
                    {
                        value: '初中',
                        label: '初中'
                    },
                    {
                        value: '高中',
                        label: '高中'
                    },
                    {
                        value: '高职高专',
                        label: '高职高专'
                    },
                    {
                        value: '大专',
                        label: '大专'
                    },
                    {
                        value: '本科',
                        label: '本科'
                    },
                    {
                        value: '研究生',
                        label: '研究生'
                    },
                    {
                        value: '博士',
                        label: '博士'
                    }
                ],
                recruitmentList:[
                    {
                        label: '是',
                        value: '是'
                    },
                    {
                        label: '否',
                        value: '否'
                    }
                ],
                templateId: '',
                isLoading: true
            }
        },
        created() {
            this.templateId = this.$route.query.templateId
        },
        methods: {
            initTitle () {
                this.$api.post('/member-reversion/user/perfect/findTableHead', {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.id,
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
            handleInit (type) {
                console.log('init')
                let data = {
                    user_id: this.$user.loginAccount,
                    year_id: this.yearId,
                    parent_id: this.id,
                    templateId: this.templateId
                }
                console.log('data', data)
                this.$api.post('/member-reversion/educationLive/findEducationLive', data).then(response => {
                    if (response.code === 200) {
                        this.getData(response.data, type)
                    }
                })
            },
            //接收数据
			getData (val, type) {
                this.isLoading = false
                if (val.educationLive.length) {
                    this.data = val.educationLive
                    this.data.forEach(e => {
                        e.isAdd = false
                    })
                }
                if (!type) {
                    this.textPreview = val.textPreview
                }
                this.sys_dict_id = this.id
                // this.title = val.educationLive_name
            },
            // 拼接文字预览
            changePreview () {
                let strs = ''
                this.data.forEach(e => {
                    let str = ''
                    if (e.status) {
                        if (e.school_model) {
                            str += `学校名称：${e.school_model}、`
                        }
                        if (e.education_model) {
                            str += `学历：${e.education_model}、`
                        }
                        if (e.major_model) {
                            str += `专业：${e.major_model}、`
                        }
                        if (e.is_general_model == '是') {
                            str += `统招、`
                        } else {
                            str += `非统招、`
                        }
                        if (e.entrance_graduation_time_model[0]) {
                            str += `入学时间：${this.moment(e.entrance_graduation_time_model[0]).format('YYYY-MM-DD')}、`
                        }
                        if (e.entrance_graduation_time_model[1]) {
                            str += `毕业时间：${this.moment(e.entrance_graduation_time_model[1]).format('YYYY-MM-DD')}、`
                        }
                    }
                    if (str) {
                        str = str.substring(0, str.length - 1) + '。'
                        strs += str
                    }
                })
                this.textPreview.text_preview = strs
            },
            //增加
            handleAdd () {
                this.data.push({
                    school_model: '',
                    education_model: '',
                    major_model: '',
                    is_general_model: '',
                    entrance_graduation_time_model: ['', ''],
                    isAdd: true,
                    status: true
                })
                this.index = this.data.length - 1
            },
            edit (item, index) {
                item.isAdd = true
                this.data.splice(index, 1, item)
                this.index = index
            },
            del (item, index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确认删除？',
                    onOk:()=>{
                        if (item.id) {
                            this.$api.post('/member-reversion/educationLive/deleteEducationalExperience', {id: item.id}).then(response => {
                                if (response.code === 200) {
                                    this.$Message.success('删除成功!')
                                    this.data.splice(index, 1)
                                    this.changePreview()
                                }
                            })
                        } else {
                            this.$Message.success('删除成功!')
                            this.data.splice(index, 1)
                            this.changePreview()
                        }
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            // 单个保存
            save (item, index) {
                this.$refs[`formItem${index}`][0].validate(v=>{
                    if (v) {
                        var starTime = item.entrance_graduation_time_model[0]
                        var endTime = item.entrance_graduation_time_model[1]
                        if(starTime && endTime){
                            item.entrance_graduation_time_model[0] = this.moment(starTime).format('YYYY/MM/DD')
                            item.entrance_graduation_time_model[1] = this.moment(endTime).format('YYYY/MM/DD')
                        }
                        let list = {
                            educationLive: item,
                            sys_dict_id: this.sys_dict_id,
                            educationLive_name: this.title,
                            yearId: this.yearId,
                            user_id: this.$user.loginAccount,
                            templateId: this.templateId
                        }
                        this.$api.post('/member-reversion/educationLive/saveEducationLive', list).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('保存成功')
                                this.data[index].isAdd = false
                                this.handleInit('1')
                            }
                        })
                    } else {
                        this.$Meaasge.error('请核对表单信息')
                    }
                })
            },
            // 最下方的保存 保存文字预览
            handleSave () {
                this.isLoading = true
                this.data.length ? this.textPreview.is_complete = true : this.textPreview.is_complete = false
                let list = {
                    textPreview: this.textPreview,
                    sys_dict_id: this.sys_dict_id,
                    educationLive_name: this.title,
                    yearId: this.yearId,
                    user_id: this.$user.loginAccount,
                    templateId: this.templateId
                }
                this.$api.post('/member-reversion/educationLive/saveTextPreview',list).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功')
                        this.$emit('on-save')
                        this.handleInit()
                    }
                })
            },
            leftRefresh () {
                this.$emit('left-refresh')
            }
        }
    }
</script>
<style lang="scss" scoped>
</style>