<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId"/>
    <div class="mt40">
        <Form ref="formItem" :model="form" label-position="left" :label-width="100" :rules="formItemInline">
            <Row>
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="form.status" size="large" :disabled="true">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
            </Row>
            <Table border ref="selection" :columns="columns" :data="data" @on-selection-change="tableSelectedChange"></Table>
            <Form-item label="检测报告" class="mt20">
                <vui-upload
                    ref="air"
                    :disabled="true"
                    @on-getPictureList="getList"
                    :hint="'图片大小小于2MB，支持后缀名png jpg'"
                    :total="10"
                    :size="[80,80]"
                ></vui-upload>
            </Form-item>
        </Form>
    </div>
    <Title title="文字预览"/>
    <div class="pd20 tc pt30">
        <Input v-model="preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
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
            modeId: {
                type: String
            },
            yearId: {
                type: String
            }
        },
        data () {
            return {
                title: '生态环境质量信息',
                formItemInline: {
                },
                form: {
                    status: true,
                    pictureList: []
                },
                selectedData: [],
                preview: '',
                disabled: false,
                columns: [
                    {
                        title: '生态环境指数（EI）',
                        key: 'ei',
                        align: 'center',
                        width: 120
                    },
                    {
                        title: '级别',
                        key: 'level',
                        align: 'center',
                        width: 100
                    },
                    {
                        title: '描述',
                        key: 'description',
                        align: 'center',
                    },
                    {
                        type: 'selection',
                        align: 'center',
                        width: 60
                    }
                ],
                data: [],
                isLoading: true
            }
        },
        created () {
            if (this.modeId !== '' && this.modeId !== undefined) {
                this.init()
            }
        },
        watch: {
            modeId: {
                handler (newValue, oldValue) {
                    this.init()
                },
                deep: true
            }
        },
        methods: {
            // 初始化页面时加载数据
            init () {
                this.$api.post('/member-reversion/envCondition/findEcologyEnvQua', {
                    account: this.$user.loginAccount,
                    templateId: this.$template.id,
                    yearId: this.yearId,
                    dictId: this.modeId
                }).then(response => {
                    if (response.code === 200) {
                        this.isLoading = false
                        this.data = response.data.ecologyEnv
                        this.data.forEach(e => {
                            e._disabled = true
                        })
                        if (response.data.envSelected) {
                            this.selectedData = response.data.envSelected
                        }
                        if (response.data.detectReport) {
                            this.form.pictureList = response.data.detectReport
                            this.$refs['air'].handleGive(this.form.pictureList)
                        }
                        if (response.data.status) {
                            this.form.status = response.data.status === 1 ? true : false
                        }
                        if (response.data.propertyName) {
                            this.title = response.data.propertyName
                        }
                        if (response.data.textPreview) {
                            this.preview = response.data.textPreview
                        }
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 保存所有
            handleSave () {
                let data = {
                    templateId: this.$template.id,
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    propertyName: this.title,
                    isComplete: '1',
                    ecologyEnv: this.selectedData,
                    detectReport: this.form.pictureList,
                    status: this.form.status,
                    textPreview: this.preview
                }
                this.isLoading = true
                this.$api.post('/member-reversion/envCondition/modifyEcologyEnvQua', data).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.$emit('on-save')
                        this.init()
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            getList (e) {
                var arr = []
                e.forEach(element => {
                     if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.form.pictureList = arr
                // 当更改图片时重新生成预览信息
                let eiStr = ''
                let levelStr = ''
                this.selectedData.forEach((element, index) => {
                    this.data.forEach(item => {
                        if (parseInt(element) === item.id) {
                            if (index === this.selectedData.length - 1) {
                                eiStr += item.ei
                                levelStr += item.level
                            } else {
                                eiStr += item.ei + '、'
                                levelStr += item.level + '、'
                            }
                        }
                    })
                })
            },
            tableSelectedChange (data) {
                let arr = []
                let eiStr = ''
                let levelStr = ''
                data.forEach((element, index) => {
                    arr.push(element.id)
                    if (index === data.length - 1) {
                        eiStr += element.ei
                        levelStr += element.level
                    } else {
                        eiStr += element.ei + '、'
                        levelStr += element.level + '、'
                    }
                })
                this.selectedData = arr
            }
        },
        mounted () {
            this.preview = `所在地生态环境状况指数为（），全村生态环境状况（）。`
        }
    }
</script>
<style lang="scss" scoped>
</style>