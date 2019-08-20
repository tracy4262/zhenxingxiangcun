<template>
  <div class="pd20">
    <Title :title="title"  :id="id" :yearId="yearId" edit/>
    <Card v-for="(item,index) in data" :key="index" class="mt40">
        <Form :ref="`formItem${index}`" :model="item" label-position="left" :label-width="90" :rules="formItemInline">
            <Row>
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="item.status" size="large" :disabled="!item.isAdd">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
                <!-- <Col span="12" class="tr">
                    <div class="btn-toolbar" style="margin-top: -10px;">
                        <Button type="text" @click="edit(item, index)" v-if="!item.isAdd"><Icon type="trash-a" size="16" class="pr5"></Icon> 编辑</Button> 
                        <Button type="text" @click="del(item, index)" v-if="item.isAdd"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col> -->
            </Row>
            <Row :gutter="16">
                <Col span="8">
                    <Form-item label="学校名称">
                        <Input v-model="item.school_model" :maxlength="50" :disabled="!item.isAdd"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="学历">
                        <Select v-model="item.education_model" :disabled="!item.isAdd">
                            <Option v-for="item in degrees" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="专业名称">
                        <Input v-model="item.major_model" :maxlength="50" :disabled="!item.isAdd" />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="8">
                    <Form-item label="是否统招">
                        <Select v-model="item.is_general_model" :disabled="!item.isAdd" >
                            <Option v-for="item in recruitmentList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="入学/毕业时间">
                        <DatePicker v-model="item.entrance_graduation_time_model" :editable="false"  type="daterange" :options="options" 
                            :disabled="!item.isAdd" style="width: 100%;"></DatePicker>
                    </Form-item>
                </Col>
            </Row>
        </Form>
        <!-- <div class="tc">
            <Button type="primary" v-if="item.isAdd" @click="save(item, index)">保存</Button>
        </div> -->
    </Card>
    <!-- <Form label-position="left" :label-width="150" class="pb20 mt40">
        <Row :gutter="32">
            <Col span="12">
                <Button type="success" ghost @click="handleAdd" icon="md-add" class="btn-light-primary">添加</Button>
            </Col>
        </Row>
    </Form> -->
    <Title title="文字预览" class="mt50"/>
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
                title: '',
                textPreview: {},
                sys_dict_id: '',
                formItemInline: {},
                data: [
                    {
                        school_model: '',
                        education_model: '',
                        major_model: '',
                        is_general_model: '',
                        entrance_graduation_time_model: ['', ''],
                        isAdd: false,
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
                isLoading: true
            }
        },
        methods: {
            handleInit (type) {
                this.$api.post('/member-reversion/educationLive/findEducationLive', {templateId: this.$template.id, user_id: this.$user.loginAccount, year_id: this.yearId, parent_id: this.id}).then(response => {
                    if (response.code == 200) {
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
                if (!val.textPreview.text_preview) {
                    val.textPreview.text_preview = '学校名称（），学历（），专业名称（），是否统招（），入学/毕业时间（）。'
                }
                this.textPreview = val.textPreview
                this.sys_dict_id = this.id
                this.title = val.educationLive_name
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
                                    this.$Message.success('保存成功')
                                    this.data.splice(index, 1)
                                }
                            })
                        } else {
                            this.$Message.success('保存成功')
                            this.data.splice(index, 1)
                        }
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            // 单个保存
            save (item, index) {
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
                    user_id: this.$user.loginAccount
                }
                this.$api.post('/member-reversion/educationLive/saveEducationLive', list).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功')
                        this.data[index].isAdd = false
                        this.handleInit('1')
                    }
                })
            },
            // 最下方的保存 保存文字预览
            handleSave () {
                this.data.length ? this.textPreview.is_complete = true : this.textPreview.is_complete = false
                let list = {
                    templateId: this.$template.id,
                    textPreview: this.textPreview,
                    sys_dict_id: this.sys_dict_id,
                    educationLive_name: this.title,
                    yearId: this.yearId,
                    user_id: this.$user.loginAccount
                }
                this.isLoading = true
                this.$api.post('/member-reversion/educationLive/saveTextPreview',list).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功')
                        this.$emit('on-save')
                        this.handleInit()
                    }
                })
            }
        },
        mounted() {
            this.textPreview.text_preview = '学校名称（），学历（），专业名称（），是否统招（），入学/毕业时间（）。'
        },
    }
</script>
<style lang="scss" scoped>
</style>