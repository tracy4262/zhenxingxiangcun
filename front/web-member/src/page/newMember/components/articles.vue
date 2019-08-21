<template>
  <div class="articles-ivu pd20">
      <Row type="flex" justify="center">
          <Col span="16">
            <Avatar v-if="data.avatar" :src="data.avatar" width="32" height="31" class="list-avatar"/>
            <Avatar v-else src="../../../../static/img/user-icon-big.png" width="32" height="31" class="list-avatar"/>
            <span class="list-name pl5">{{data.account}}</span>
          </Col>
          <Col span="8" class="tr">
            {{moment(data.createTime).format('YYYY-MM-DD')}}
          </Col>
      </Row>
      <h2 v-if="data.columnType != '图书'" class="list-title pt10 ell pb10" @click="deitel"> {{data.title}}</h2>
      <!-- 文章短信列表 -->
      <div v-if="data.columnType == '文章'">
          <!-- 显示部分 -->
        <Row v-if="!isShow">
            <Col span="8" class="pr10" v-if="showImg">
                    <img src="../../../img/com-banner8.jpg" width="100%" height="150">
            </Col>
            <Col :span="showImg?'16':'24'" class="pd5">
                <div>
                    <div class="list-content" v-html="data.content"></div>
                    <div class="tc"><Button type="text" class="list-more" @click="readMore">阅读全文<Icon type="ios-arrow-down"></Icon></Button></div>
                </div>
            </Col>
        </Row>
        <!-- 点击阅读详情后 -->
        <div v-if="isShow" v-html="data.content"></div>
      </div>
      <!-- 图片列表 -->
      <div class="pics" v-if="data.columnType == '图册'">
          <Row :gutter="8" class="pb10">
              <Col span="6" v-for="(item, index) in data.imgContent" :key="index">
                    <img :src="item" width="100%" height="120px" v-if="item">
              </Col>
          </Row>
      </div>
      <!-- 视频列表 -->
      <div v-if="data.columnType == '视频'">
          <Row :gutter="8">
              <Col span="6" v-for="(item,index) in data.videoImgs" :key="index">
                <div class="video">
                    <!-- 用原生标签，隐藏下面的功能按钮，名称定位上去 -->
                    <video :src="item.addr" width="100%" height="120px"/>
                    <div class="video-icon" @click="deitel">
                        <Icon type="ios-circle-outline" class="video-outline" size="34"></Icon>
                        <Icon type="ios-play" class="video-play" size="22"></Icon>
                        <p class="pt90 tc ell pl5 pr5" v-if="item.describe">{{item.describe}}</p>
                        <p class="pt90 tc ell pl5 pr5" v-else>{{item.mediaName}}</p>
                    </div>
                </div>
              </Col>
          </Row>
      </div>
      <!-- 音频列表 -->
      <div v-if="data.columnType == '音频'">
            <Row class="audio mb10" v-for="(item,index) in data.videoImgs" :key="index" >
                <Col span="24" class="pr20"> 
                    <div class="video-icon pl5 fl" @click="deitel">
                        <Icon type="ios-radio-button-off" class="video-outline" size="26"></Icon>
                        <Icon type="ios-play" class="video-play" size="16"></Icon>
                    </div>
                    <div class="pl40">
                        <p class="ell">{{item.mediaName}}</p>
                    </div>
                </Col>
            </Row>
      </div>
      <!-- 图书列表 -->
      <div v-if="data.columnType == '图书'">
          <Row class="pt10 pb10">
            <Col span="4">
                <img :src="data.coverPhoto" width="100%" height="110">
            </Col>
            <Col span="20" class="pd5">
                <h2 class="list-title"  @click="deitel">{{data.title}} <span>  {{data.author}}  著</span></h2>
                <div class="list-content" v-if="!isShowBooks">{{data.summary}}</div>
                <div v-if="isShowBooks">{{data.summary}}</div>
                <div class="tc" v-if="!isShowBooks && data.summary.length > 125"><Button type="text" class="list-more" @click="readMore">阅读全部<Icon type="chevron-down"></Icon></Button></div>
            </Col>
          </Row>
      </div>
      <div>
          
      </div>
      <div class="articles-ivu-foot">
          <Row>
              <Col span="16">
                <Tag type="border" color="primary">{{data.docType}}</Tag>
                <Button type="text" @click="fabulous(data.id)">
                    <i class="icon-laud" style="font-size:16px"></i>
                    <span>{{data.thumbUpNum}} 赞</span>
                </Button>
                <Button type="text" @click="handleComment">
                    <Icon type="ios-text-outline" size="16"/>
                    <span v-if="!showComment"> {{data.postNum}}条评论</span>
                    <span v-else>收起评论</span>
                </Button>
              </Col>
              <Col span="8" class="tr">
                <Button type="text" @click="readMore" v-if="isShow">
                    <span>收起</span>
                    <Icon type="ios-arrow-up" size="14"></Icon>
                </Button>
              </Col>
          </Row>
        <Card class="mt10" v-if="showComment">
            <p slot="title">
                {{data.postNum}}条评论
            </p>
            <comment :data="commentData" v-if="commentData.list" @on-NextPage="handleNextPage" :dataType="dataType"/> 
            <Row>
                <Col span="22">
                    <Input v-model.trim="replys" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></Input>
                </Col> 
                <Col span="2" class="pl5">
                    <Button type="primary" @click="handleSendAuthorReply">评论</Button>
                </Col> 
            </Row>
        </Card>
      </div>
  </div>
</template>
<script>
    import comment from '../../../components/vui-comments/item.vue'
    
    export default {
        components:{            
			comment
        },
        props:{
            dataType:{
                type:String,
                default:'动态'
            },
            data:{
                default:()=>{
                    return {
                    }
                }
            }
        },
        data(){
            return{
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                showComment:false,
               isShow:false,
               isShowBooks:false,
               showImg:false,
               commentData:[],
               replys:'',
               currentPage: 1
            }
        },
        created(){
            if(this.data.columnType == '图册'){
                var img = this.data.content.substring(3, this.data.content.length-3)
                img = img.split('>')
                 this.data.imgContent = []
                    if(img){
                        for(let i = 0 ; i < img.length-1 ; i ++){
                        if(i < 4){
                            let src = img[i].split('=')[1]
                            src = src.substring(1, src.length-1)
                            this.data.imgContent.push(src)
                        }
                    }
                 }
            }
            // this.getComment()
        },
        watch: {
            data: {
                handler (newVal, oldVal) {
                    this.data = newVal
                    if(this.data.columnType == '图册'){
                        var img = this.data.content.substring(3, this.data.content.length-3)
                        img = img.split('>')
                        this.data.imgContent = []
                        if(img){
                            for(let i = 0 ; i < img.length-1 ; i ++){
                                if(i < 4){
                                    let src = img[i].split('=')[1]
                                    src = src.substring(1, src.length-1)
                                    this.data.imgContent.push(src)
                                }
                            }
                        }
                    }
                },
                deep: true
            }
        },
        methods:{
            //下一页
            handleNextPage(e, del){
                if (del) { // 删除的回调 评论数量减一
                    this.data.postNum -- 
                }
                this.currentPage = e
                this.getComment()
            },
            //对列表评论
            handleSendAuthorReply(){
                if (this.replys) {
                    var _url = ''
                    let commentType = ''
                    if(this.dataType == '政策') {
                        commentType = 'policy'
                        _url = '/member/policy/saveHuiFu'
                    } else if(this.dataType == '知识'){
                        commentType = 'knowledge'
                        _url = '/member/knowLege/saveHuiFu'
                    }else if(this.dataType == '动态'){
                        commentType = 'information'
                        _url = '/member/inforMation/saveHuiFu'
                    }
                    this.$api.post(_url, {
                            commentType: commentType,
                            dynamicId: this.data.id,
                            comment: this.replys,
                            account: this.$user.loginAccount,
                            displayName: this.$user.displayName ? this.$user.displayName : '',
                        }
                    ).then(response => {
                        var flag = response.data
                        if(flag === 1) {
                            this.getComment()
                            this.replys = ""
                            this.data.postNum ++
                        } else {
                            this.$Message.error('回复失败!')
                        }
                    })
                } else {
                    this.$Message.info('请输入评论内容')
                }
            },
            //获取评论
            getComment(){
                var type = ''
				if(this.dataType == '政策') {
					type = 'policy'
				} else if(this.dataType == '知识'){
					type = 'knowledge'
				}else if(this.dataType == '动态'){
					type = 'information'
				}
                this.$api.post('/member/columnSettings/findComments',
                {
                    information_detail_id:this.data.id,
                    account: this.$user.loginAccount,
                    pageNum:this.currentPage,
                    type: type
                }).then(response=>{
                    if(response.code == 200){
                        this.commentData = response.data
                    }
                })
            },
            // 详情页跳转
            deitel(){
                var url = ''
                var id = this.data.id
                var informationId = this.data.informationId
                var columnType = this.data.columnType
                if(this.dataType == '动态'){
                    if(columnType == '图书'){
                        url = `/InforMation/bookBlurb?id=${informationId}&informationDetailId=${id}&book_type=information`
                    }else{
                        url =  `/InforMation/findInforMationDetail?id=${id}`
                    }
                }else if(this.dataType == '政策'){
                    if(columnType == '图书'){
                        url = `/InforMation/bookBlurb?id=${informationId}&informationDetailId=${id}&book_type=policy`
                    }else{
                        url = `/InforMation/policyDetail?id=${id}`
                    }
                }else if(this.dataType == '知识'){
                    if(columnType == '图书'){
                        url = `/InforMation/bookBlurb?id=${informationId}&informationDetailId=${id}&book_type=knowledge`
                    }else{
                        url = `/InforMation/knowledgeDetail?id=${id}`
                    }
                }
                window.open(url, "_blank");
            },
            // 阅读更多以及收起的点击事件
            readMore(){
                this.isShow = !this.isShow
                this.isShowBooks = !this.isShowBooks
            },
            //点赞
            fabulous(id){
                var list = {account:this.$user.loginAccount,detailId:id}
                var url = '/member/thumb/detailThumbAdd'
                if(this.dataType == '动态'){     
                    list.detailType = 'information'
                }else if(this.dataType == '政策'){
                    list.detailType = 'policy'
                }else if(this.dataType == '知识'){
                    list.detailType = 'knowledge'
                }
                this.fabulou(url,list)
            },
            //评论
            handleComment(){
                if(!this.showComment){
                    this.getComment()
                }
                this.showComment = !this.showComment
            },
            // 点赞调的方法
            fabulou(url,data){
                this.$api.post(url,data).then(response => {
                    if (response.code === 200) {
                        if(response.data === 'exist'){
                            this.$Message.error('您已点赞')
                        } else{
                            this.$Message.success('点赞成功')
                            this.data.thumbUpNum++
                        }
                    }
                })
            }
        }
    }

</script>
<style lang="scss" scoped>
.articles-ivu{
    border:1px solid #f6f6f6;
   .list-title{
       font-weight: 700;
       font-size: 16px;
       color:#4a4a4a;
       cursor: pointer;
       &:hover{
           color:#777;
       }
       span{
            font-size: 12px;
            font-weight: 400;
       }
   } 
   .list-content{
       font-size: 14px;
       color:#4a4a4a;
       line-height: 24px;
       max-height: 95px;
       overflow: hidden;
   }
   .list-more{
       color:#00c587;
   }
   .list-avatar{
       vertical-align: middle;
   }
   .list-name{
       font-size: 15px;
       color:#4a4a4a;
   }
   .audio{
        width: 70%;
        border: 1px solid #d8d8d8;
        border-radius: 4px;
        padding: 5px;
        cursor: pointer;
       .video-icon{
           width:22px;
           position: absolute;
           .video-play{
                position: absolute;
                left: 12px;
                top: 5px;
            }
       }
       p{
           padding-top:2px;
       }
   }
    .video{
        position: relative;
        .video-icon{
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0px;
            left: 0px;
            color: #fff;
            cursor: pointer;
            p{
                font-size: 12px;
            }
            .video-outline{
                position: absolute;
                top: 35%;
                left: 41%;
            }
            .video-play{
                position: absolute;
                top: 39%;
                left: 46%;
            }
        }
    }
    .list-avatar.ivu-avatar {
        width: 32px;
        height: 32px;
        line-height: 31px;
        border-radius: 16px;
    }
}

</style>

