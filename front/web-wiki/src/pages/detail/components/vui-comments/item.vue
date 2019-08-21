<template>
<div>
  <ul class="vui-comment-list">
    <li class="vui-comment-item" v-for="(item, index) in data.list" :key="index">
      <Row>
        <Col span="21">
          <Avatar :src="item.author.avatar"></Avatar>
          <template v-if="item.replyAuthor">
            <span class="t-blue">{{item.author.name}}</span> 回复 <span class="t-blue">{{item.replyAuthor.name}}</span>
          </template>
          <template v-else>{{item.author.name}}</template>
        </Col>
        <Col span="3">{{moment(data.create_time).format('YYYY-MM-DD')}}</Col>
      </Row>
      <div class="mt10 mb10">{{item.content}}</div>
      <div>
        <Button type="text" size="small" @click="handleLike(item)"><Icon :size="14" type="ios-thumbs-up-outline"></Icon> {{item.thumb_up_num}}</Button>
        <!-- <Button type="text" size="small" v-if="item.reply" @click="item.replyListShow = !item.replyListShow"><Icon :size="15" type="chatbubble-working"></Icon> {{item.replyListShow ? '收起评论' : '查看评论'}}</Button> -->
        <Button type="text" size="small" v-if="account != item.user_account" @click="handleReplyBtn(item, index)"><Icon :size="15" type="ios-undo"></Icon> <span v-if="!item.replyBoxShow">回复</span><span v-if="item.replyBoxShow">收起回复</span> </Button>
        <Poptip
          v-else
          transfer
          confirm
          title="确定要删除此评论？"
          @on-ok="handleReplyDel(item, index)">
          <Button type="text" size="small"><Icon :size="15" type="md-trash"></Icon> 删除</Button>
        </Poptip>
      </div>
      <template v-if="item.replyBoxShow">
        <vui-reply
        :ref="`input${index}`"
        :placeholder="placeholder"
        @on-reply="handleSendReply($event, item, index)"></vui-reply>
      </template>
      <!-- <transition name="fadeIn">
        <template v-if="item.replyListShow">
          <vui-comment-item
            v-if="item.reply"
            :data="item.reply"
            :replyBtnShow="false"
            class="vui-comment-item-child mt20">
          </vui-comment-item>
        </template>
      </transition> -->
    </li>
  </ul>
  <div class="tc pb20" v-if="data.list.length">
    <Page :total="data.total" size="small" :page-size="data.pageSize" :current="data.pageNum" @on-change="handleNextPage" />  
  </div>
</div>
</template>
<script>
/* eslint-disable */
import vuiReply from './reply'
export default {
  name: 'vui-comment-item',
  props: {
    data: {
      type: Object,
      default () {
        return {}
      }
    },
    replyBtnShow: {
      type: Boolean,
      default: true
    },
    dataType:{
      type:String,
      default:'动态'
    },
  },
  components: {
    vuiReply
  },
  data: () => ({
    placeholder: '',
    refs: 'input',
    loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
    account:''
  }),
  created(){
    this.account = this.loginuserinfo.loginAccount
  },
  methods: {
    //下一页
    handleNextPage(e){
      this.$emit('on-NextPage',e)
    },
    // 点赞
    handleLike (item) {
      var list = {account:this.loginuserinfo.loginAccount,commentId:item.id}
      var url = '/member/thumb/detailThumbCommentAdd'
      if(this.dataType == '动态'|| this.dataType == 'information'){     
        list.commentType = 'information'
      }else if(this.dataType == '政策'|| this.dataType == 'policy'){
        list.commentType = 'policy'
      }else if(this.dataType == '知识' || this.dataType == 'knowledge'){
        list.commentType = 'knowledge'
      }else if(this.dataType == '标准'){
        this.$emit('on-like',item)
        return
      }
      this.handleLikes(url,list,item)
    },
    //政策知识资讯点赞调用的接口
    handleLikes (url, list,item){
      if(list.account){
        this.$api.post(url,list).then(response => {
          if (response.code === 200) {
            if(response.data === 'exist'){
                this.$Message.error('您已点赞')
            } else{
                this.$Message.success('点赞成功')
                this.$emit('on-NextPage',this.data.pageNum)
            }
          }
        })
      }
    },
    // 回复按钮
    handleReplyBtn (item, index) {
      item.replyBoxShow = !item.replyBoxShow
      this.placeholder = `回复${item.author.name}`
      this.autofocus = true
      // 获取焦点
      this.$nextTick(() => {
        // console.log(this.$refs['input' + index])
        // let input = this.$refs['input' + index].$refs['input'].focus()
        // if (input) {
        //   console.log('input')
        //   input.$refs['input'].focus()
        // }
      })
    },
    // 删除评论 需要发请求后台删除，在删除前台
    handleReplyDel (item, index) {
      var type = ''
      if(this.dataType == '政策'|| this.dataType == 'policy') {
        type = 'policy'
      } else if(this.dataType == '知识' || this.dataType == 'knowledge'){
        type = 'knowledge'
      }else if(this.dataType == '动态'|| this.dataType == 'information'){
        type = 'information'
      }else if(this.dataType == '标准'){
        this.$api.post('/member/standard/deleteMyComment',{comment_id:item.id}).then(response=>{
          if(response.code == 200){
            this.$Message.success('删除成功')
            this.$emit('on-NextPage',this.data.pageNum, 'del')
          } else {
            this.$Message.error('删除失败')
          }
        })
        return
      }
      // 政策知识资讯的删除评论
      this.$api.post('/member/columnSettings/deleteMyComment',{id:item.id,type:type}).then(response=>{
        if(response.code == 200){
          this.$Message.success('删除成功')
          console.log(this.data.pageNum)
          this.$emit('on-NextPage',this.data.pageNum, 'del')
        } else {
          this.$Message.error('删除失败')
        }
      })
      // this.data.splice(index, 1)
    },
    // 发表评论（对已经评论的回复）
    handleSendReply (data, item, index) {
      // 图书传的是knowledge/policy/information
      var _url = ''
				if(this.dataType == '政策' || this.dataType == 'policy') {
					_url = '/member/policy/saveHuiFu'
				} else if(this.dataType == '知识' || this.dataType == 'knowledge'){
					_url = '/member/knowLege/saveHuiFu'
				}else if(this.dataType == '动态' || this.dataType == 'information'){
					_url = '/member/inforMation/saveHuiFu'
				}else if(this.dataType == '标准'){
          // 标准的回复
          this.$emit('on-replyComment',{content:data.content,replyAccount:item.user_account,id:item.id})
          return
        }
				this.$api.post(_url, {
					 	postId:item.id,
						comment: data.content,
            dynamicId:'',
            authorName: item.author.name ? item.author.name : '',
            account: this.$user.loginAccount
					}
				).then(response => {
					var flag = response.data
					if(flag == '1') {
						item.replyBoxShow = false
            this.$Message.success('评论成功')
            this.$emit('on-NextPage',this.data.pageNum)
					}else{
						this.$Message.error('评论失败!')
					}
				})
      // this.data.splice(index + 1, 0, Object.assign({}, data, {
      //   replyAuthor: {
      //     name: item.author.name
      //   }
      // }))
      // item.replyBoxShow = false
      // // 发请求判断成功状态
      // this.$Message.success('评论成功')
    }
  }
}
</script>
<style lang="scss">
.vui-comment{
  &-list{
    li{
      list-style: none;
    }
  }
  &-item{
    margin-bottom: 15px;
    border-bottom: 1px solid #eee;
    padding-bottom: 15px;
    &-child {
      border: 1px solid #eee;
      .vui-comment-item{
        margin-bottom: 0;
        padding-bottom: 0;
        border-top: 1px dotted #eee;
        padding: 10px;
        &:last-child{
          border-bottom: none;
        }
      }
    }
  }
}
.fadeIn {
	animation-name: fadeIn
}
@keyframes fadeIn {
	0% {
		opacity: 0;
		transform: translateY(-20px)
	}
	100% {
		opacity: 1;
		transform: translateY(0)
	}
}
</style>
