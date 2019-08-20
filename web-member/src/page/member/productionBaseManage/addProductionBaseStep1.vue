<template>
    <div class="form-wrap">
        <vui-map 
        ref="vuiMap"
        @on-get-point="onGetPoint"
        ></vui-map>
        <div style="width: 500px;">
            <Form ref="baseInfo" :model="baseInfo" label-position="right" :label-width="150" :rules="ruleInline">
                <Form-item prop="baseName" class="input" label="基地名称：">
                    <Input v-model="baseInfo.baseName" :maxlength="30" placeholder="请输入内容" />
                </Form-item>
                <Form-item prop="baseLocation" class="input" label="地理位置：">
                    <!-- <Cascader :render-format="format" v-model="baseInfo.baseLocation" :data="locationList" :load-data="loadPositionDatas" change-on-select>
                    </Cascader> -->                    
                    <vui-cascander :values="baseInfo.baseLocation" @handle-get-result="handleGetData"></vui-cascander>
                </Form-item>
                <Form-item prop="baseCenterCoordinatePoint" class="input" label="基地中心坐标点：">
                    <Input v-model="baseInfo.baseCenterCoordinatePoint" placeholder="请输入内容" readonly @on-focus="onSelectPoint" />
                </Form-item>
                <Form-item prop="contactAccount" class="input" label="联系人帐号：">
                    <Input v-model="baseInfo.contactAccount" placeholder="请输入内容" />
                </Form-item>
                <Form-item class="input" label="联系人姓名：">
                    <Input v-model="baseInfo.contactName" placeholder="请输入内容" />
                </Form-item>
                <Form-item prop="contactPhone" class="input" label="联系电话：">
                    <Input v-model="baseInfo.contactPhone" placeholder="请输入内容" />
                </Form-item>
                <Form-item class="input" label="基地介绍：">
                    <Input type="textarea" v-model="baseInfo.baseIntroduction" :maxlength="500" placeholder="请输入内容" />
                </Form-item>
            </Form>
            <Button type="primary" @click="next">下一步</Button>&nbsp;&nbsp;&nbsp;&nbsp;
            <Button type="default" @click="exit">退出</Button>
        </div>
    </div>
</template>

<script>
    import vuiMap from '../components/productionMap'
    import vuiCascander from '~components/vuiCascader/index'
    export default {
        components:{
            vuiMap,
            vuiCascander
        },
        data() {
            let checkPhones = (rules, value, cb) => {
                this.$store.dispatch('checkPhones', { rules, value, cb })
            }
            return {
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                baseInfo: {
                    baseName: '',
                    baseLocation: '',
                    baseCenterCoordinatePoint: '',
                    contactAccount: '',
                    contactName: '',
                    contactPhone: '',
                    baseIntroduction: '',
                    locationId: []
                },
                ruleInline: {
                    baseName: [
                        { required: true, message: '基地名称不能为空', trigger: 'blur' }
                    ],
                    contactAccount: [
                        { required: true, message: '联系人帐号不能为空', trigger: 'blur' }
                    ],
                    contactPhone: [
                        { validator: checkPhones, trigger: 'blur' }
                    ],
                    baseLocation: [
                        { required: true, message: '请选择地理位置', trigger: 'change' }
                    ],
                    baseCenterCoordinatePoint: [
                        { required: true, message: '基地中心坐标点不能为空', trigger: 'change' }
                    ]
                },
                locationList: [],
                point:''
            }
        },
        created () {
            // 取地址
            this.$api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
                this.locationList = res.data
            })
            if (this.$route.query.id !== undefined && this.$route.query.id !== '') {
                this.init({productId: this.$route.query.id})
            }
        },
        methods: {
            handleGetData (value, selectedData) {
                let labelArr = []
                selectedData.forEach(element => {
                    labelArr.push(element.label)
                })
                this.baseInfo.baseLocation = labelArr.join('')
                this.baseInfo.locationId = value
            },
            init (data) {
                let _that = this
                this.$api.post('/member/product-base/query-product-id', data).then(response => {
                    console.log('response', response)
                    if (response.code === 200) {
                        _that.baseInfo.baseName = response.data.baseName
                        _that.baseInfo.baseLocation = response.data.geographicalPosition
                        _that.baseInfo.baseCenterCoordinatePoint = response.data.coordinate
                        _that.baseInfo.contactAccount = response.data.contactAccount
                        _that.baseInfo.contactName = response.data.contactName
                        _that.baseInfo.contactPhone = response.data.contactTel
                        _that.baseInfo.baseIntroduction = response.data.baseSynopsis
                        _that.baseInfo.locationId = response.data.locationId
                        _that.point = _that.baseInfo.baseCenterCoordinatePoint

                    }
                }
                ).catch(error => {
                    console.log(error)
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
            next () {
                let flag = this.handleSubmit('baseInfo')
                console.log('flag', flag)
                if (!flag) {
                    return
                }
                let data = {
                    baseName: this.baseInfo.baseName,
                    geographicalPosition: this.baseInfo.baseLocation,
                    coordinate: this.baseInfo.baseCenterCoordinatePoint,
                    contactAccount: this.baseInfo.contactAccount,
                    contactName: this.baseInfo.contactName,
                    contactTel: this.baseInfo.contactPhone,
                    baseSynopsis: this.baseInfo.baseIntroduction,
                    loginAccount: this.loginuserinfo.loginAccount,
                    locationId: this.baseInfo.locationId,
                    productId: ''
                }
                if (this.$route.query.id !== undefined && this.$route.query.id !== '') {
                    data.productId = this.$route.query.id
                }
                let _that = this
                this.$api.post('/member/product-base/save', data).then(response => {
                    console.log('response', response)
                    if (response.code === 200) {
                        let nextStep = 1
                        _that.$emit('next', nextStep)
                        this.$parent.$router.push({
                            path: '/member/addProductionBase/addProductionBaseStep2',
                            query: {
                                id: response.data === undefined ? _that.$route.query.id : response.data.productId
                            }
                        })
                    } else if (response.code === 500) {
                        _that.$Message.error('服务器异常')
                    } else if (response.code === 300) {
                        _that.$Message.error(response.msg)
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            exit () {
                this.$router.push({
                    path: '/member/productionBaseList',
                    query: {
                        uid: this.loginuserinfo.loginAccount
                    }
                })
            },
            loadPositionDatas (item, callback) {
                item.loading = true
                this.$api.post(`/member/town/next/${item.value}`).then(res => {
                    item.loading = false
                    item.children = res.data
                    callback()
                })
            },
            format (labels, selectedData) {
                let locationStr = ''
                labels.forEach(item => {
                    locationStr += item
                })
                this.baseInfo.baseLocation = locationStr
                return locationStr
            },
            // 取坐标
            onGetPoint (point) {
                // this.point = `${point.lng},${point.lat}`
                if (point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
                    this.baseInfo.baseCenterCoordinatePoint = `${point.lng},${point.lat}`
                } else {
                    this.baseInfo.baseCenterCoordinatePoint = ''
                }
            },
            onSelectPoint () {
                this.$refs.vuiMap.showMap = true
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
</style>