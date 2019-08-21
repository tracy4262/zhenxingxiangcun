<template>
<div >
    <div class="live-video-index">
    <div class="banner">
        <Carousel autoplay>
            <Carousel-item>
                <div class="demo-carousel">
                <img src="../../static/img/banner.jpg" alt="" width="100%;" height="340px;">
                </div>
            </Carousel-item>
            <Carousel-item>
                <div class="demo-carousel">
                <img src="../../static/img/banner.jpg" alt="" width="100%;" height="340px;">
                </div>
            </Carousel-item>
            <Carousel-item>              
                <div class="demo-carousel">
                <img src="../../static/img/banner.jpg" alt="" width="100%;" height="340px;">
                </div>
            </Carousel-item>
            <Carousel-item>
                <div class="demo-carousel">
                <img src="../../static/img/banner.jpg" alt="" width="100%;" height="340px;">
                </div>
            </Carousel-item>
        </Carousel>
    </div>
    <div class="news ww" >
        <Row type="flex" justify="end"> 
            <i-col span="2">
                <i-button type="text" icon="arrow-up-c" v-if="!isTime" @click="reloadTrue()">顺序</i-button>
                <i-button type="text" icon="arrow-down-c" v-if="isTime" @click="reloadFalse()">倒序</i-button>
            </i-col>
        </Row>
         <Row :gutter="16" v-if="describeList.length > 0"> 
            <i-col span="6" v-for="item in describeList">
                <div class="ivu-tv-live">
                    <div v-if="!item.liveStatusInfo.val" class="unliveing">休息中</div>
                    <div v-else class="liveing">直播中</div>
                    <!-- <router-link :to="{ path: 'chatRoom' }" ></router-link> -->
                            <a @click="toRoom(item.account,item.liveId)"><img :src="item.liveImage" alt="" height="160px" width="290px"></a>
                    
                    <div class="describe">
                            <a @click="toRoom(item.account,item.liveId)" class="text">{{item.liveName}}</a>
                        <!-- <router-link :to="{ path: 'chatRoom',query:{id:item.liveId} }" class="text">{{item.liveName}}</router-link> -->
                        <i-button type="text" class="name">{{item.userName}}</i-button> 
                        <img src="../../static/img/p.png" alt="" height="18px" width="18px" class="img">
                        <img src="../../static/img/v.png" alt="" height="18px" width="18px">
                    </div>
                </div>
            </i-col>
        </Row>
        <div class="pages" v-if="describeList.length > 0">
             <Page :total="totals" :page-size="pageSize" show-total :current="currentPage" @on-change="nextPage" />
        </div>
        <div v-if="describeList.length == 0" class="tc">
            <img src="../img/ma-img-002.png"> 
            <p style="margin-top: 10px;padding-bottom:20px;">暂无近期直播</p>
        </div>
    </div>
  </div>
</div>
 
</template>
<script>
// import top from "../top";
import api from "../api";


    export default {   
        components:{
            // top
        }, 
        data () {
            return {
                value: 0,
                isTime:true,
                totals:0,
                pageSize:12,
                currentPage:1,
                describeList:[],
                // loginuserinfo:{loginAccount:'luona'},  
                loginuserinfo:JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                type:2,
                account:''
            }
        },
        created () {
            this.getInit(this.currentPage)
        },
        methods:{
            getInit (pageNum) { //sortType 排序1是正序，2是倒序
                api.post('/relationship/live/listlive',{pageNum :pageNum,pageSize :this.pageSize,sortType :this.type }).then(res=>{
                    console.log(res)
                    if(res.code == 200){
                        if(this.type ===1){
                            this.isTime = false
                        }else if(this.type === 2)  {
                            this.isTime = true
                        }
                        this.describeList = res.data
                        this.totals = res.total
                    }
                })
            },
            toRoom(account,id){
                this.account = this.loginuserinfo.loginAccount
                if(this.account){                    
                    if(account == this.account){
                        this.$router.push({ path: "/chatRoom", query: { id:id,account:this.account}});
                    }else{                
                        this.$router.push({ path: "/liveRoom", query: { id:id,account:this.account}});                    
                    }
                }else{
                    this.$Message.warning('请先登录')
                }
            },
            nextPage (page) {
                console.log(page)
                this.currentPage = page
                this.getInit(this.currentPage)
            },
            reloadTrue(){//倒叙2
                this.type = 2
                this.getInit(1)
                this.currentPage = 1
            },
            reloadFalse(){//倒叙1
                this.type = 1
                this.getInit(1)
                this.currentPage = 1
            }
        }
    }
</script>
<style>
.tc{
    text-align: center;
}
.live-video-index{
    background: #f3f3f3;
}
.live-video-index .news {
    padding: 30px 0;
}
.live-video-index .news .pages{
   text-align: center;
   padding-top: 10px;
   padding-bottom: 20px;
}
.ivu-tv-live .describe{
    padding: 10px 15px 15px 15px;
    text-align:left;
}
.ivu-tv-live .describe .text{
    font-size: 18px;
    color:#333;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    margin-bottom: 10px;
    height: 53px;
}
.ivu-tv-live .describe .name{
    font-size: 14px;
    color:#999;
    padding:0px;
    vertical-align: top;
    padding-right:5px;
}
.ivu-tv-live .describe .img{
    margin:0px 5px;
}
.live-video-index .ivu-tv-live{
    position: relative;
    background: #fff;
    overflow: hidden;
    margin-bottom: 16px;
}
.liveing{
    position: absolute;    
    background: #4FAC77;
    height: 20px;
    color: #fff;
    width: 60px;
    line-height: 20px;
    border-radius: 0 0 10px;
    text-align:center;
}
.unliveing{
    position: absolute;    
    background: #AAADAA;
    height: 20px;
    color: #fff;
    width: 60px;
    line-height: 20px;
    border-radius: 0 0 10px;
    text-align:center;
}
.ww{
    width:1200px;
    max-width:1960px;
    margin:0 auto;
}

</style>

