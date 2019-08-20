<template>
    <div class="form-wrap">
        <div style="width: 900px;">
            <Button type="primary" @click="addCamera" class="add-button">新增</Button>
            <Modal v-model="showEditCamera" width="560" :mask-closable="false">
                <p slot="header">
                    <span>新增摄像头信息</span>
                </p>
                <div style="text-align:center">
                    <Form ref="cameraInfo" :model="cameraInfo" label-position="right" :label-width="150" :rules="ruleInline">
                        <Form-item prop="cameraName" class="input" label="设备名称：">
                            <Input type="text" v-model="cameraInfo.cameraName"/>
                        </Form-item>
                        <Form-item prop="cameraAddress" class="input" label="设备地址：">
                            <Input type="text" v-model="cameraInfo.cameraAddress"/>
                        </Form-item>
                    </Form>
                </div>
                <div slot="footer">
                    <Button type="primary" @click="save">保存</Button>
                </div>
            </Modal>
            <Table border :columns="cameraColumns" :data="cameraData" class="data-table"></Table>
            <Row>
                <Page :total="total" show-total @on-change="getNextPage" style="float: right; margin-bottom: 20px;"></Page>
            </Row>
            <Button type="default" @click="last">上一步</Button>&nbsp;&nbsp;&nbsp;&nbsp;
            <Button type="primary" @click="next">下一步</Button>&nbsp;&nbsp;&nbsp;&nbsp;
            <Button type="default" @click="exit">退出</Button>
        </div>
    </div>
</template>

<script>
    export default {
        components:{
        },
        data() {
            return {
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                cameraColumns: [
                    {
                        title: '序号',
                        type: 'index',
                    },
                    {
                        title: '设备名称',
                        key: 'equipmentName'
                    },
                    {
                        title: '设备地址',
                        key: 'equipmentAddress'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.removeCamera(params.row.cameraId)
                                        }
                                    }
                                }, '删除')
                            ])
                        }
                    }
                ],
                cameraData: [],
                showEditCamera: false,
                cameraInfo: {
                    cameraName: '',
                    cameraAddress: ''
                },
                ruleInline: {
                    cameraName: [
                        { required: true, message: '设备名称不能为空', trigger: 'blur' }
                    ],
                    cameraAddress: [
                        { required: true, message: '设备地址不能为空', trigger: 'blur' }
                    ],
                },
                total: 0,
                currentPage: 1,
                pageSize: 10
            }
        },
        created () {
            if (this.$route.query.id !== undefined && this.$route.query.id !== '') {
                let data = {
                    productId: this.$route.query.id,
                    pageNum: 1,
                    pageSize: this.pageSize
                }
                this.init(data)
            }
        },
        methods: {
            init (data) {
                this.$api.post('/member/product-base/camera-query', data).then(response => {
                    console.log('response', response)
                    if (response.code === 200) {
                        this.cameraData = response.data.list
                        this.total = response.data.total
                    }
                })
            },
            handleSubmit(name) {
                let flag = false
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        flag = true
                    } else {
                        flag = false
                    }
                })
                return flag
            },
            getNextPage (page) {
                this.currentPage = page
                let data = {
                    productId: this.$route.query.id,
                    pageNum: page,
                    pageSize: this.pageSize
                }
                this.init(data)
            },
            last () {
                let lastStep = 0
                this.$emit('last', lastStep)
                this.$parent.$router.push({
                    path: '/member/addProductionBase/addProductionBaseStep1',
                    query: {
                        id: this.$route.query.id
                    }
                })
            },
            next () {
                let nextStep = 2
                this.$emit('next', nextStep)
                this.$parent.$router.push({
                    path: '/member/addProductionBase/addProductionBaseStep3',
                    query: {
                        id: this.$route.query.id
                    }
                })
            },
            exit () {
                this.$router.push('/member/productionBaseList')
            },
            addCamera () {
                this.showEditCamera = true
            },
            removeCamera (id) {
                let _that = this
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '<p>是否确认删除摄像头？</p>',
                    onOk: () => {
                        this.$api.post('/member/product-base/camera-delete', {cameraId: id}).then(response => {
                            console.log('response', response)
                            if (response.code === 200) {
                                let data = {
                                    productId: this.$route.query.id,
                                    pageNum: _that.currentPage,
                                    pageSize: _that.pageSize
                                }
                                _that.init(data)
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    },
                    onCancel: () => {
                    }
                })
            },
            save () {
                // 调用保存接口
                // 调用刷新接口
                let flag = this.handleSubmit('cameraInfo')
                console.log('flag', flag)
                if (!flag) {
                    return
                }
                let data = {
                    productId: this.$route.query.id,
                    equipmentName: this.cameraInfo.cameraName,
                    equipmentAddress: this.cameraInfo.cameraAddress
                }
                let _that = this
                this.$api.post('/member/product-base/camera-save', data).then(response => {
                    console.log('response', response)
                    if (response.code === 200) {
                        let data2 = {
                            productId: _that.$route.query.id,
                            pageNum: _that.currentPage,
                            pageSize: _that.pageSize
                        }
                        _that.init(data2)
                        _that.cameraInfo.cameraName = ''
                        _that.cameraInfo.cameraAddress = ''
                        _that.showEditCamera = false
                    } 
                }).catch(error => {
                    console.log(error)
                })
            }
        }
    }
</script>
<style scoped>
    .form-wrap {
        text-align: center;
        display: flex;
        justify-content: center;
        margin-top: 30px;
    }
    .data-table {
        width: 900px;
        margin-top: 45px;
        margin-bottom: 20px;
    }
    .add-button {
        position: absolute;
        right:55px;
    }
</style>