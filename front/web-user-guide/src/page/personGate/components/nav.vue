<template>
    <div class="wrapper">
        <top :address="false" ref="top"/>
        <div class="rural-gate-head">
            <div class="layouts pd10">
                <Row  type="flex" align="middle">
                    <Col span="20">
                         <img :src="headData.websiteLOGO" alt="" height="50">
                        <span v-if="headData.isShowWebsiteName === '是'">{{headData.websiteName}}</span>
                    </Col>
                    <Col span="4">
                        <!-- <p class="t-orange h5">
                            <Icon type="android-call"></Icon>
                            <span v-if="headData.mobile !== ''">{{headData.mobile}}</span>
                            <span v-if="headData.mobile === ''">{{headData.phone}}</span>
                        </p> -->
                    </Col>
                </Row>
            </div>
        </div>
        <nav class="rural-gate-nav">
            <div class="layouts">
                <Row>
                    <Col span="22">
                        <ul class="layouts clear">
                            <template v-for="(item,index) in data">
                                <li>
                                    <Dropdown v-if="item.isShow">
                                        <div  v-if="item.title == '直播间'">                        
                                            <a  :href="item.url" target="_self" v-if="loginAccount == account" class="item" :class="{'on': item.checked}">
                                                {{item.title}}
                                                <Icon type="ios-arrow-down" v-if="item.child"></Icon>
                                            </a>
                                            <a  :href="item.urls" target="_self" v-else class="item" :class="{'on': item.checked}">
                                                {{item.title}}
                                                <Icon type="ios-arrow-down" v-if="item.child"></Icon>
                                            </a>
                                        </div>
                                        <router-link :to="`${item.url}?uid=${$route.query.uid}`" class="item" :class="{'on': item.checked}" v-else>
                                            {{item.title}}
                                            <Icon type="ios-arrow-down" v-if="item.child"></Icon>
                                        </router-link>
                                        <DropdownMenu slot="list"  v-for="(sub,index) in item.child" :key="index">
                                            <DropdownItem><router-link :to="`${sub.url}?uid=${$route.query.uid}`">{{sub.name}}</router-link></DropdownItem>
                                        </DropdownMenu>
                                    </Dropdown>
                                </li>
                            </template>
                        </ul>
                    </Col>
                </Row>
            </div>
        </nav>
        <router-view></router-view>
        <foot :data="headData"/>
    </div>
</template>
<script>
import top from '../../../top'
import foot from '~page/companyGate/components/foot'
export default {
    props:{
        active:{
            type:Number,
            default:0
        }
    },
    components:{
        top,
        foot
    },
    data () {
        return {
            data:[{
                title: '首页',
                url: '/personGate/index',
                checked: true,
                isShow: true
            },{
                title: '个人介绍',
                url: '/personGate/brief/index',
                checked: false,
                isShow: true
            }, {
                title: '动态',
                url: '/personGate/dynamic',
                checked: false,
                isShow: true
            }, {
                title: '政策',
                url: '/personGate/policies',
                checked: false,
                isShow: true
            }, {
                title: '知识',
                url: '/personGate/knowledge',
                checked: false,
                isShow: true
            },{
                title: '标准',
                url: '/personGate/standard',
                checked: false,
                isShow: true
            },{
                title: '专家团队',
                url: '/personGate/expert',
                checked: false,
                isShow: true
            },{
                title: '产品',
                url: '/personGate/product',
                checked: false,
                isShow: true
            },
            {
                title: '服务',
                url: '/personGate/service/all',
                checked: false,
                isShow: true
            },
            {
                title: '直播间',
                url: this.$url.liveVideo+'/liveVideoSetting?account='+this.$route.query.uid,
                urls: this.$url.liveVideo+'/?account='+this.$route.query.uid,
                checked: false,
                isShow: true
            },{
                title: '联系我们',
                url: '/personGate/contact',
                checked: false,
                isShow: true
            }


            // {
            //     title: '商品',
            //     url: '/personGate/commodity',
            //     checked: false,
             //   isShow: true,
            // }, {
            //     title: '服务',
            //     url: '/personGate/service',
            //     checked: false,
             //   isShow: true,
            // },  {
            //     title: '基地',
            //     url: '/personGate/base',
            //     checked: false,
                // isShow: true,
            // }, {
            //     title: '种养户',
            //     url: '/personGate/farmers',
            //     checked: false,
             //   isShow: true,
            // }
            ],
            loginAccount: '',
            headData: {},
            loginUser: this.$user,
            account: ''
        }
    },
    created(){
        this.loginAccount = this.$route.query.uid
        if (this.loginUser) {
            this.account = this.loginUser.loginUser
        }
        // this.show()
        this.getHeadData()
    },
    methods:{
        //是否隐藏
        show(){
            let modular = []
            this.$api.post('/portal/myGate/getModular', {
                loginAccount: this.loginAccount
            })
                .then(response => {
                    if (response.code === 200) {
                        modular = response.data.modular.split(",")
                        this.data.map(item=>{
                            item.isShow = true
                            if(modular.indexOf(item.title) < 0){
                               item.isShow = false
                            }
                            if(item.title == '直播间'){
                                if(this.loginAccount === this.$user.loginAccount){
                                    item.isShow = true
                                }else{
                                    item.isShow = false
                                }
                            }
                        })
                    }
                })
        },

         getHeadData(){
             // type 1 企业，3 个人、专家 ，4 乡村，5 机关
            this.$api.post('/member/websiteSettings/findWebsiteSettingsInfo', {
                account: this.loginAccount,
                userType: 3
            })
            .then(response => {
                if (response.code === 200) {
                    if( response.data.websiteInfo){
                        this.headData = response.data.websiteInfo
                    }  
                    var moduleData = response.data.moduleData
                    moduleData.forEach(element => {
                        this.data.forEach(item=>{
                            if(item.title == element.name){
                                if(!element.checked && item.title !='首页'){
                                    item.isShow = false
                                }
                            }
                        })
                    });
                }

            })
        }
    }
}
</script>
<style lang="scss" scoped>
.rural-gate-head{
    background: url(../../../img/person-banner.jpg) center no-repeat;
}
.rural-gate-nav{
    background-color: #F5A623;
    li{
        float: left;
        &:hover{
            .sub{
                display: block;
            }
        }
    }
    .item{
        color: #fff;
        font-size: 16px;
        padding: 10px;
        display: block;
        margin-right:10px;
        &:hover,
        &.on{
            background-color: #d89400;
        }
    }
}
</style>
