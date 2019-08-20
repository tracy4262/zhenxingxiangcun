<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh" />
    <div class="mt40">
        <Form ref="formItem" :model="form" label-position="left" :label-width="100" :rules="formItemInline">
            <Row>
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="form.status" size="large">
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
                    @on-getPictureList="getList"
                    :hint="'图片大小小于2MB，支持后缀名png jpg'"
                    :total="9999999"
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
                title: '地表水质量信息',
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
                        title: '水质类别',
                        key: 'type',
                        align: 'center',
                        width: 120
                    },
                    {
                        title: '判断标准',
                        key: 'standard',
                        align: 'center',
                        width: 200
                    },
                    {
                        title: '水质状况',
                        key: 'situation',
                        align: 'center',
                        width: 100
                    },
                    {
                        title: '表征颜色',
                        key: 'color',
                        align: 'center',
                        width: 100
                    },
                    {
                        title: '水质功能类别',
                        key: 'functionType',
                        align: 'center'
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
            // 初始化页面时加载数据
            init () {
                this.$api.post('/member-reversion/envCondition/findSurfaceWaterQua', {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code === 200) {
                        this.isLoading = false
                        this.data = response.data.waterQuality
                        if (response.data.waterSelected) {
                            this.selectedData = response.data.waterSelected
                        }
                        if (response.data.detectReport) {
                            this.form.pictureList = response.data.detectReport
                            this.$refs['air'].handleGive(this.form.pictureList)
                        }
                        if (response.data.status !== undefined) {
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
            // 保存
            handleSave () {
                this.isLoading = true
                let data = {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    propertyName: this.title,
                    isComplete: '1',
                    waterQuality: this.selectedData,
                    detectReport: this.form.pictureList,
                    status: this.form.status,
                    textPreview: this.preview,
                    templateId: this.templateId
                }
                this.$api.post('/member-reversion/envCondition/modifySurfaceWaterQua', data).then(response => {
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
                let typeStr = ''
                let situationStr = ''
                this.selectedData.forEach((element, index) => {
                    this.data.forEach(item => {
                        if (parseInt(element) === item.id) {
                            if (index === this.selectedData.length - 1) {
                                typeStr += item.type
                                situationStr += item.situation
                            } else {
                                typeStr += item.type + '、'
                                situationStr += item.situation + '、'
                            }
                        }
                    })
                })
                this.preview = `所在地水质类别为${typeStr}，地表水质量状况为${situationStr}。
附检测报告：${this.form.pictureList}`
            },
            tableSelectedChange (data) {
                let arr = []
                let typeStr = ''
                let situationStr = ''
                data.forEach((element, index) => {
                    arr.push(element.id)
                    if (index === data.length - 1) {
                        typeStr += element.type
                        situationStr += element.situation
                    } else {
                        typeStr += element.type + '、'
                        situationStr += element.situation + '、'
                    }
                })
                this.selectedData = arr
                // 当更改表格数据时重新生成预览信息
                this.preview = `所在地水质类别为${typeStr}，地表水质量状况为${situationStr}。
附检测报告：${this.form.pictureList}`
            },
            leftRefresh () {
                this.$emit('left-refresh')
            }
        }
    }
</script>
<style lang="scss" scoped>
</style>