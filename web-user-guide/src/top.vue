<!--
    作者：chenqim
    时间：2018-09-27
    描述：页面顶部
-->
<template>
    <div>
        <Affix class="new-top-affix">
            <div class="top-back">
                <div class="top-title">感谢湖北省人民政府丨亚洲开发银行 支持惠民项目</div>
            </div>
            <div class="ns-header">
                <div style="width: 1200px; margin: auto;">
                    <Row type="flex" align="middle" class="top-nav">
                            <Col span="3">
                                <a class="logo" href="/">
                                    <img src="../src/img/huiyuan-logo.png" width="98px" height="40px">
                                </a>
                            </Col>
                            <Col span="10" offset="7" class="ns-nav pl10">
                                <Col span="4">
                                    <a :href="`${location}/index`" :class="{'ns-active': active === '0'}">
                                        无忧导航
                                    </a>
                                </Col>
                                <Col span="4" offset="1">
                                    <a :href="`${location}/51index`" :class="{'ns-active': active === '1'}">
                                        无忧首页
                                    </a>
                                </Col>
                                <Col span="4" offset="1">
                                    <a :href="`${location}/mapNav`" :class="{'ns-active': active === '2'}">
                                        地图导航
                                    </a>
                                </Col>
                                <Col span="4" offset="1">
                                    <a href="javascript:void(0);" :class="{'ns-active': active === '3'}">
                                        掌上无忧
                                    </a>
                                </Col>
                                <Col span="4" offset="1">
                                    <a href="javascript:void(0);" :class="{'ns-active': active === '4'}">
                                        农业大数据
                                    </a>
                                </Col>
                            </Col>
                            <Col span="3" offset="1" v-if="!loginuserinfo" class="tr">
                                <Button type="text" size="large" @click="loginuser()">登录</Button>
                                <Button type="primary" size="large" class="ml10 mr10" @click="regist()">注册</Button>
                            </Col>
                            <Col span="3" offset="1" v-if="loginuserinfo" class="tr">
                                <a>
                                    <Avatar :src="loginuserinfo.avatar" v-if="loginuserinfo.avatar" />
                                    <Avatar src="../../static/img/user-icon-big.png" v-else />
                                </a>
                                <Dropdown style="vertical-align: middle; margin-left: 10px;">
                                    <a href="javascript:void(0)">
                                        <span v-if="loginuserinfo.displayName">
                                            <span v-if="loginuserinfo.displayName.length <= 6">{{loginuserinfo.displayName}}</span>
                                            <span v-else :title="loginuserinfo.displayName">{{loginuserinfo.displayName.substring(0,6)}}...</span>
                                        </span>
                                        <span v-else>
                                            <span v-if="loginuserinfo.loginAccount.length <= 10">{{loginuserinfo.loginAccount}}</span>
                                            <span v-else :title="loginuserinfo.loginAccount">{{loginuserinfo.loginAccount.substring(0,6)}}...</span>
                                        </span>
                                        <Icon type="ios-arrow-down" />
                                    </a>
                                    <DropdownMenu slot="list" style="margin-top: 10px;">
                                        <DropdownItem>
                                            <a @click="isLogin('/pro/member')">会员中心</a>
                                        </DropdownItem>
                                        <DropdownItem>
                                            <a @click="isLogin('/center')">应用中心</a>
                                        </DropdownItem>
                                        <DropdownItem>
                                            <a  @click="isLogin('iswho')">我的门户</a>
                                        </DropdownItem>
                                        <DropdownItem>
                                            <a @click="isLogin('/personalIndex/detail')">我的资料</a>
                                        </DropdownItem>
                                        <DropdownItem>
                                            <a href="javascript:void(0)" target="_blank">账户安全</a>
                                        </DropdownItem>
                                        <DropdownItem>
                                            <a href="javascript:void(0)" target="_blank">收货地址</a>
                                        </DropdownItem>
                                        <DropdownItem>
                                            <a href="javascript:void(0)" target="_blank">消费记录</a>
                                        </DropdownItem>
                                        <DropdownItem divided style="text-align: center;">
                                            <a @click="logout">
                                                退出
                                            </a>
                                        </DropdownItem>
                                    </DropdownMenu>
                                </Dropdown>
                            </Col>
                    </Row>
                </div>
                <Modal v-model="modal1" width="850" :mask-closable="false">
                    <h1 style="font-size: 24px;font-weight: normal;line-height: 70px;text-align: center">推荐好友</h1>
                    <Row style="    padding-left: 14px;">
                        <Col span="6" style="width: 138px;height: 174px;border: 1px solid #cccccc;border-radius: 7px;margin: 10px  10px" v-for="(f,index) in friend" :key="index">
                        <div>
                            <div style="position: absolute;left: 0;top: 0"><img src="../src/img/huiyuan-gou.png" alt=""></div>
                            <div style="position: absolute;right:10px;top: 10px">
                                <img src="../src/img/huiyuan-hongxin.png" alt="">
                            </div>
                            <span style="font-size: 14px;color: #00c587;position: absolute;right: 30px;top: 6px">222</span>
                            <div class="tuijian-user"></div>
                            <div style="text-align: center;font-size: 16px;margin-top: 8px">
                                <span><img src="../src/img/tuijian-vip.png" alt=""></span>
                                <span>{{f.displayName}}</span>
                            </div>
                            <div style="text-align: center;margin-top: 4px">
                                <Button type="default" size="small" style="margin: auto">农业</Button>
                            </div>
                        </div>
                        </Col>
                    </Row>
                    <div style="text-align: center;" slot="footer">
                        <Button type="primary" shape="circle" size="large" style="width: 200px;height: 50px;margin: 20px 0;font-size: 16px" @click="pass()">一键关注</Button>
                        <span style="font-size: 16px;padding-left: 16px;vertical-align: middle;" @click="pass()">跳过</span>
                    </div>
                </Modal>
            </div>
        </Affix>
        
       <login-register ref="loginRegister" @on-success="handleSuccess"></login-register>
    </div>
</template>
<script>
import loginRegister from '~components/loginRegister/index'

export default {
    components: {
        loginRegister
    },
    props:{
        active: {
            type: String,
            default: null
        },
        address:{
            type:Boolean,
            default:true
        },
        searchBtn:{
            type:Boolean,
            default:false
        },
        goShop:{
            type:Boolean,
            default:false
        }
    },
    data() {
        return {
        	friend:[],
        	total:0,
            value1: '',
            currentPage: 1,
            pageSize: 10,
            currentPage: 0,
            modal1: false,//推荐好友显示
            modal3:false,
            stepsCur:0,
            step1:true,
            step2:false,
            step3:false,
            loginuserinfo:{},
            cityList: [],
            corpList: [],
            account:'',
            datum:'',
            location: window.location.origin
        }
    },
    computed: {
        getWebsite() { //获得网站信息
            return this.$store.getters.getWebsite
        }

    },
    created () {
        this.loginuserinfo = this.$user
        console.log(this.$user)
        this.search = this.$route.query.title
        console.log('loginuserinfo', this.loginuserinfo)
        // 取地址
        if (this.address) {
            // this.$api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
            //     if( res.data === ''){
            //         this.cityList = []
            //     }else{
            //         this.cityList = res.data
            //     }
            //     //this.cityList = res.data
            // })
            this.cityList = [{
                value: '01',
                label: '中国',
                children: [
                  {
                    value: '011',
                    label: '湖北省',
                    children: [{
                    	value: '021',
                      label: '襄阳市',
                      children: [{
                        value: '021',
                        label: '襄州区'
                      }]
                    }]
                  }
              ]
          }]
        }
        if(this.loginuserinfo){
            this.account = this.loginuserinfo.loginAccount
            this.init()
        }
    },
    methods: {
        init () {
            this.$api.post('/member/login/findCurrentUser', {
                account: this.$user.loginAccount
            }).then(response => {
                console.log('res123', response)
                if (response.data.displayName) {
                    this.displayName = response.data.displayName
                }
                if (response.data.avatar) {
                    this.avatar = response.data.avatar
                }
            })
        },
        // 登录成功的回调
        handleSuccess (response) {
            sessionStorage.setItem('key', response.data.key)
            response.data.proxy.forEach(element => {
                sessionStorage.setItem(element.account, JSON.stringify(element.session))
            })
            console.log(sessionStorage)
			console.log('session', sessionStorage.getItem(sessionStorage.getItem('key')))
            this.friends()
            this.loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            window.location.reload()
        },
        // 是否登录
        isLogin(e){
            const user=sessionStorage.getItem('user')
            if(user === null){
                this.loginuser()
                this.$Message.error('请先登录！')
            }else{
                let loginAccount = this.$user.loginAccount
                if (e === 'iswho') {
                    this.$api.post('/member/login/findCurrentUser', {
                        account: loginAccount
                    })
                    .then(response => {
                        if (response.code === 200) {
                            if (response.data.userType !== undefined) {
                                let userType = response.data.userType
                                // 0个人用户 1企业 2种养户 3机关 4专家 5乡村
                                if (userType === 1) {
                                    window.location.href = `${window.location.origin}/companyGate/index?uid=${loginAccount}`
                                } else if (userType == 2) {
                                    window.location.href = `${window.location.origin}/personGate/index?uid=${loginAccount}`
                                } else if (userType === 3) {
                                    window.location.href = `${window.location.origin}/govGate/index?uid=${loginAccount}`
                                } else if (userType === 4) {
                                    window.location.href = `${window.location.origin}/expertGate/index?uid=${loginAccount}`
                                } else if (userType === 5) {
                                    window.location.href = `${window.location.origin}/ruralGate/index?uid=${loginAccount}`
                                } else {
                                    window.location.href = `${window.location.origin}/personGate/index?uid=${loginAccount}`
                                }
                            }
                        }
                    })
                } else {
                    window.location.href = `${window.location.origin}${e}?uid=${loginAccount}`
                }
            }
        },
        // 高级搜索
        find() {
            this.list = []
            var path = this.$route.path
            this.url = ''
            if (path == '/search/InforMation') {
                if (this.search == '') {
                    /*    this.search=null
                        this.$router.push({path: '/search/InforMation', query: {title: this.search}})*/
                    this.url = '/member/inforMation/findInforMation/' + this.currentPage + "?pageSize=" + this.pageSize
                } else {
                    this.$router.push({ path: '/search/InforMation', query: { title: this.search } })
                    this.url = '/member/inforMation/findInforMationTitle/' + this.currentPage + "?title=" + this.search + "&pageSize=" + this.pageSize
                }
            } else if (path == '/search/policy') {
                if (this.search == '') {
                    this.url = '/member/policy/findPolicy/' + this.currentPage + "?pageSize=" + this.pageSize
                } else {
                    this.$router.push({ path: '/search/policy', query: { title: this.search } })
                    this.url = '/member/policy/findPolicyTitle/' + this.currentPage + "?title=" + this.search + "&pageSize=" + this.pageSize
                }
            } else if (path == '/search/knowledge') {
                if (this.search == '') {
                    this.url = '/member/knowLege/findKnowLedge/' + this.currentPage + "?pageSize=" + this.pageSize
                } else {
                    this.$router.push({ path: '/search/knowledge', query: { title: this.search } })
                    this.url = '/member/knowLege/findKnowLedgeTitle/' + this.currentPage + "?title=" + this.search + "&pageSize=" + this.pageSize
                }
            } else if (path == '/search/expert') {
                if (this.search == '') {
                    this.url = '/member/expert/find/' + this.currentPage + "?pageSize=" + this.pageSize
                } else {
                    this.$router.push({ path: '/search/expert', query: { title: this.search } })
                    this.url = '/member/expert/findExpertTitle/' + this.currentPage + "?title=" + this.search + "&pageSize=" + this.pageSize
                }
            } else if (path == '/search/memberSearch') {
                if (this.search == '') {
                    /*    this.search=null
                     this.$router.push({path: '/search/InforMation', query: {title: this.search}})*/
                    this.url = '/member/inforMation/findInforMation/' + this.currentPage + "?pageSize=" + this.pageSize
                } else {
                    this.$router.push({ path: '/search/memberSearch', query: { title: this.search } })
                    this.url = '/member/inforMation/findInforMationTitle/' + this.currentPage + "?title=" + this.search + "&pageSize=" + this.pageSize
                }
            }

            this.$api.get(this.url)
                .then(response => {
                    this.list = response.data.list
                })
        },
        //选择地区回调
        CascaderChange(value, selectedData){
            this.value1 = selectedData[selectedData.length - 1].label
            console.log(selectedData)
            // this.$api.post('/member/corpInfo/query-corp-by-location', {location: this.value1}).then(response => {
            //     if (response.code === 200) {
            //         this.corpList = []
            //         response.data.forEach(element => {
            //             this.corpList.push({value: element.id, label: element.corpName})
            //         })
            //     }
            // }).catch(error => {
            //     console.error(error)
            // })
        },
        // loadData (item, callback) {
        //     item.loading = true
        //     this.$api.post(`/member/town/next/${item.value}`).then(res => {
        //         item.loading = false
        //         item.children = res.data
        //         callback()
        //     })
        // },
        routeTo(e) {
            if(e == '/portal'){
                this.portal = this.$url.shop + '/center/gateway.htm?uid='+this.loginuserinfo.uniqueId
                window.open(this.portal)
            }else{
                this.$router.push(e)
            }
        },
        //点击登录显示登录界面
        loginuser() {
            this.$refs['loginRegister'].loginuser()
        },
        //点击注册显示注册界面
        regist() {
            this.$refs['loginRegister'].regist()
        },
        //推荐好友
		friends() {
			this.$api.get('/member/login/friends/' + this.currentPage)
				.then(response => {
					this.friend = response.data.list
					this.total = response.data.total
				})
		},
		nextPage(page) {
			this.currentPage = page;
			this.$api.get('/member/login/friends/' + this.currentPage)
				.then(response => {
					this.friend = response.data.list
					this.total = response.data.total
				})
		},
		pass() {
			this.modal1 = !this.modal1
			window.location.reload()
		},
        goto(e){
            this.portal = this.$url.shop+'/center/' +e+'.htm?uid='+this.loginuserinfo.uniqueId
            window.open(this.portal)
        },
        logout(){
         	var path=this.$route.path
            this.$api.get('/member/login/logout')
            sessionStorage.removeItem("user")
            window.location.href = `${window.location.origin}/index`
            // if('/index'==path){
            //     window.location.reload()
            // }else{
            //     this.$router.push('/index')
            //     window.location.reload()
            // }
        }
    }
}
</script>

<style>
    .address-cascader .ivu-cascader-menu .ivu-cascader-menu-item{white-space: nowrap;}
</style>
<style lang="scss">
.new-top-affix {
    .ivu-affix{
        z-index:999 !important;
    }
}
.ns-header {
    border-bottom: 1px solid #ededed;
    min-width:1200px;
    background-color: #fff;
}
.top-nav{
    height: 74px;
    .logo{
        display: block;
        /* padding: 10px; */
        /* margin-left: 110px; */
    }
    .ns-nav a {
        display: block;
        height: 62px;
        line-height: 58px;
        font-size: 15px;
        text-align: center;
        color: #666;
        border-top: 4px solid #fff;
        &:hover {
            color: #00c587 !important;
        }
        &.ns-active {
            border-top-color:#00c587;
            color: #00c587 !important;
        }
    }
    .ivu-dropdown-rel,
    .ivu-dropdown-menu{
        a{
            font-size: 15px;
            color:#666;
        }
    }
    .ivu-dropdown-menu{
        min-width: auto
    }
}
.login-modle .ivu-modal-close {
    top: 0;
    z-index: 99;
}
.top-title {
    /* margin-left: 120px; */
    color: #7C7C7C;
    font-size: 12px;
    width: 1200px;
    margin: auto;
}
.top-back {
    background-color: #F5F5F5;
    padding:3px;
}
</style>
