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
                <Col span="12">
                    <Form-item label="工作单位" prop="company">
                        <Input v-model="item.company" :maxlength="50" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item label="工作职位" prop="position">
                        <Input v-model="item.position" :maxlength="50" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="工作时间">
                        <DatePicker v-model="item.time" :editable="false" type="daterange" :options="options" 
                            :disabled="!item.isAdd" style="width: 100%;" @on-change="change"></DatePicker>
                    </Form-item>
                </Col>
            </Row>
            <Form-item label="工作详情">
                <Input v-model="item.detail" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200" :disabled="!item.isAdd" @on-change="change" />
            </Form-item>
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
        <Button type="primary"  class="mt40" v-if="isLoading" >保存</Button>
        <Button type="primary" @click="handleSave()" class="mt40" v-else>保存</Button>
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
            modeId: {
                type: String
            },
            yearId: {
                type: String
            },
            appId: {
                type: String
            }
        },
        data () {
            return {
                title: '工作经历',
                formItemInline: {
                    position: [
                        { required: true, message: '请填写工作职位', trigger: 'blur' },
                    ],
                    company: [
                        { required: true, message: '请填写工作单位', trigger: 'blur' },
                    ]
                },
                data: [
                    {
                        company: '',
                        position: '',
                        time: [],
                        detail: '',
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
                templateId: '',
                isLoading: true
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
        created () {
            this.templateId = this.$route.query.templateId
            if (this.modeId !== '' && this.modeId !== undefined) {
                this.init()
                this.initTitle()
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
                this.$api.post('/member-reversion/workExperience/findWorkExperience', {
                    user_id: this.$user.loginAccount,
                    year_id: this.yearId,
                    parent_id: this.modeId,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code === 200) {
                        this.isLoading = false
                        // if (response.data.workExperience_name && response.data.workExperience_name !== '') {
                        //     this.title = response.data.workExperience_name
                        // }
                        if (response.data.textPreview.text_preview && response.data.textPreview.text_preview !== '') {
                            this.preview = response.data.textPreview.text_preview
                            this.id = response.data.textPreview.id
                        }
                        if (response.data.workExperience.length !== 0) {
                            this.data = []
                            response.data.workExperience.forEach((element, index) => {
                                this.data.push({
                                    id: element.id,
                                    company: element.work_unit_model,
                                    position: element.work_position_model,
                                    time: element.work_time_model,
                                    detail: element.work_details_model,
                                    status: element.status,
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
            //增加
            handleAdd () {
                this.data.push({
                    company: '',
                    position: '',
                    time: [],
                    detail: '',
                    isAdd: true,
                    status: true
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
                            this.$api.post('/member-reversion/workExperience/deleteWorkExperience', {
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
            save (item, index) {
                let arr = ['', '']
                if (item.time && item.time.length > 0 && item.time[0] && item.time[1]) {
                    arr = [this.moment(item.time[0]).format('YYYY-MM-DD'), this.moment(item.time[1]).format('YYYY-MM-DD')]
                }
                this.$api.post('/member-reversion/workExperience/saveWorkExperience', {
                    user_id: this.$user.loginAccount,
                    yearId: this.yearId,
                    parent_id: this.appId,
                    workExperience_name: this.title,
                    templateId: this.templateId,
                    workExperience: {
                        id: item.id === '' || item.id === undefined ? 0 : item.id,
                        work_unit_model: item.company,
                        work_position_model: item.position,
                        work_time_model: arr,
                        work_details_model: item.detail,
                        status: item.status
                    }
                }).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init(1)
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            handleSave () {
                this.isLoading = true
                this.$api.post('/member-reversion/workExperience/saveTextPreview', {
                    user_id: this.$user.loginAccount,
                    yearId: this.yearId,
                    sys_dict_id: this.modeId,
                    templateId: this.templateId,
                    textPreview: {
                        id: this.id === '' || this.id === undefined ? 0 : this.id,
                        text_preview: this.preview,
                        is_complete: this.data.length === 0 ? false : true,
                    }
                }).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init()
                        this.$emit('on-save')
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            change () {
                this.preview = ''
                this.preview += `共获得 ${this.data.length} 项工作经历，其中：`
                this.data.forEach(element => {
                    if (element.company && element.company !== '') {
                        this.preview += `${element.company}，`
                    }
                    if (element.position && element.position !== '') {
                        this.preview += `工作职位：${element.position}，`
                    }
                    if (element.time && element.time.length > 0 && element.time[0] && element.time[1]) {
                        console.log(element.time)
                        this.preview += `工作时间：${this.moment(element.time[0]).format('YYYY-MM-DD')} - ${this.moment(element.time[1]).format('YYYY-MM-DD')}，`
                    }
                    if (element.detail && element.detail !== '') {
                        this.preview += `工作详情：${element.detail}，`
                    }
                    this.preview = this.preview.substring(0, this.preview.length - 1) + '；'
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
</style>