<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" />
    <div class="mt40">
        <Form ref="formItem" :model="form" label-position="left" :label-width="100" :rules="formItemInline">
            <Table border ref="selection" :columns="columns" :data="data" @on-selection-change="tableSelectedChange"></Table>
            <Form-item label="检测报告" class="mt20">
                <vui-upload
                    ref="air"
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
        <Button type="primary" :loading="loading" @click="handleSave()" class="mt40">保存</Button>
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
                    pictureList: []
                },
                selectedData: [],
                preview: '',
                columns: [
                    {
                        title: '水质类别',
                        key: 'type',
                        align: 'center',
                        width: 100
                    },
                    {
                        title: '判断标准',
                        key: 'standard',
                        align: 'center',
                        width: 180
                    },
                    {
                        title: '水质状况',
                        key: 'situation',
                        align: 'center',
                        width: 90
                    },
                    {
                        title: '表征颜色',
                        key: 'color',
                        align: 'center',
                        width: 90
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
                loading: true,
                baseId: ''
            }
        },
        created () {
            this.baseId = this.$route.query.id
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
                this.$api.post('/member-reversion/productionBase/findTableHead', {
                    account: this.$user.loginAccount,
                    dictId: this.modeId
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
                this.$api.post('/member-reversion/productionBase/envCondition/findSurfaceWaterQua', {
                    account: this.$user.loginAccount,
                    dictId: this.modeId,
                    baseId: this.baseId
                }).then(response => {
                    if (response.code === 200) {
                        this.data = response.data.waterQuality
                        if (response.data.waterSelected) {
                            this.selectedData = response.data.waterSelected
                        }
                        if (response.data.detectReport) {
                            this.form.pictureList = response.data.detectReport
                            this.$refs['air'].handleGive(this.form.pictureList)
                        }
                        if (response.data.textPreview) {
                            this.preview = response.data.textPreview
                        }
                        this.loading = false
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 保存
            handleSave () {
                this.loading = true
                let data = {
                    account: this.$user.loginAccount,
                    dictId: this.modeId,
                    propertyName: this.title,
                    isComplete: '1',
                    waterQuality: this.selectedData,
                    detectReport: this.form.pictureList,
                    textPreview: this.preview,
                    baseId: this.baseId
                }
                this.$api.post('/member-reversion/productionBase/envCondition/modifySurfaceWaterQua', data).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
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
            }
        }
    }
</script>
<style lang="scss" scoped>
</style>