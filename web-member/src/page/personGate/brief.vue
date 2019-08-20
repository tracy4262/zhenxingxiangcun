<template>
    <div class="wrapper person-brief">
        <div class="person-brief-head"></div>
        <div class="layouts ma-brieftabs">
            <div class="tc mb20">
                <Avatar v-if="data.avatar && data.avatar !== ''" class="person-user" :src="data.avatar" />
                <Avatar v-else class="person-user" src="../../../static/img/user-icon-big.png" />
                <h5 class="b mb5 mt10">{{data.userName.model}}</h5>
                <p class="t-grey">职业 | {{data.profession.model}}</p>
            </div>
            <Tabs :value="tabActive" @on-click="handleTabClick">
                <TabPane label="简介" name="index"></TabPane>
                <TabPane label="资料" name="archives"></TabPane>
                <!-- <TabPane label="联系方式" name="contact"></TabPane> -->
                <TabPane label="荣誉风采" name="honor"></TabPane>
                <!-- <TabPane label="资质证书" name="aptitude"></TabPane> -->
                <!-- <TabPane label="个人风采" name="photo"></TabPane> -->
            </Tabs>
            <router-view></router-view>
        </div>
    </div>
</template>
<script>
import { navStatus } from '~page/companyGate/mixins/commonMixins'
export default {
    mixins: [navStatus],
    data () {
        return {
            index: 1,
            tabActive: 'index',
            data:{
                avatar:'',
                userName:{model:'',name:'姓名',status:false},
                sex:{model:'',name:'性别',status:false},
                ethnicGroup:{model:'',name:'民族',status:false},
                birthday:{model:'',name:'生日',status:false},
                profession:{model:'',name:'职业',status:false},
                professionalTitle:{model:'',name:'职称',status:false},
                species:{model:'',name:'擅长物种',status:false},
                phone:{model:'',name:'手机号码',status:false},
                addr:{model:'',name:'常住地',status:false},
                coordinatePoint:{model:'',name:'坐标位置',status:false},
                postalCode:{model:'',name:'邮政编码',status:false},
                tel:{model:'',name:'座机号码',status:false},
            },
            loginAccount: ''
        }
    },
    created () {
        this.loginAccount = this.$route.query.uid
        this.tabActive = this.$router.currentRoute.name
        this.getData()
    },
    methods: {
        handleTabClick (e) {
            this.$router.push(`/personGate/brief/${e}?uid=${this.$route.query.uid}`)
        },
         getData(){ 
            this.$api.post('/member/perfectInfo/findPerfectInfo', { account: this.loginAccount }).then(response=>{
                if(response.code == 200){
                    var data = response.data
                    //隐私信息
                    if (
                        data.privateInformation &&
                        Object.keys(data.privateInformation).length
                    ) {
                        // 如果有数据说明之前已经保存过，不管是保存的是否是空数据，都已经有数据格式了
                        var datas = data.privateInformation
                        this.data = datas 
                    }
                }
            })
        }
    },
    watch:{
        '$route' (to, from){
            this.tabActive = to.name
        }
    }
}
</script>
<style lang="scss">
.person-brief{
    &-head{
        background: url(../../img/com-banner1.jpg) top center no-repeat;
        height: 200px;
    }
    .person-user.ivu-avatar{
        height:120px;
        width:120px;
        overflow: initial;
        margin: -100px auto 0;
        border: 4px solid #fff;
        position: relative;
        img{
            position: relative;
            z-index:2;
            border-radius:100px;
        }
        &,&:after{border-radius:10rem;}
        &:after{
            position:absolute;
            content:'';
            left:0;
            top:0;
            width:100%;
            height:100%;
            background-color:transparent;
            animation: userBor 5s infinite cubic-bezier(0.600, -0.280, 0.735, 0.045)
        }
        @keyframes userBor {
            25%,75%{
                transform: scale(1);
                background:rgba(255,255,255,.5);
            }
            0%,50%,100%{
                transform: scale(1.4);
                background:rgba(255,255,255,.1);
            }
        
        }
    }
    .ivu-tabs-bar{
        border:none;
        margin: 0;
    }
    .ma-brieftabs .ivu-tabs{

        width: 256px;
        margin: 0 auto;
    }

    .person-brief .ivu-tabs-nav-scroll .nav-text .ivu-tabs-nav .ivu-tabs-tab-active{
        color: red;
    }
    
}
.ma-brieftabs .ivu-tabs-nav .ivu-tabs-tab:hover{color: #f5a623;}
.ma-brieftabs .ivu-tabs-nav .ivu-tabs-tab-active{color: #f5a623;}
.ma-brieftabs .ivu-tabs-ink-bar-animated{background-color: #f5a623;}
</style>
