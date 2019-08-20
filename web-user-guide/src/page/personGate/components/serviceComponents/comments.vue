<template>
<div style="color:#4b4b4b;" class="service-comments">
  <Card shadow>
    <Row type="flex" align="middle">
      <Col :span="6" class="tc">
        <span class="t-green"><span style="font-size:30px;">{{stars.grade}}</span> %</span>
        <p>总体好评度</p>
      </Col>
      <Col :span="7" :offset="2">
        <Row class="pb10">
          <Col :span="3">好评</Col>
          <Col :span="18"><Progress :percent="stars.grade" hide-info></Progress></Col>
           <Col :span="3" class="tc">{{stars.grade}}%</Col>
        </Row>
        <Row class="pb10">
          <Col :span="3">中评</Col>
          <Col :span="18"><Progress :percent="stars.review" hide-info></Progress></Col>
           <Col :span="3" class="tc">{{stars.review}}%</Col>
        </Row>
        <Row class="pb10">
          <Col :span="3">差评</Col>
          <Col :span="18"><Progress :percent="stars.negative" hide-info></Progress></Col>
           <Col :span="3" class="tc">{{stars.negative}}%</Col>
        </Row>
      </Col>
      <Col :span="7" class="tc":offset="2">
        <div>
          <Button type="primary" @click="handleComments">我要评价</Button>
          <p class="pt20" v-if="!this.loginUser">只有登录过的用户才能评论</p>
        </div>
      </Col>
    </Row>
  </Card>
  <div class="comments" :class="shwoComment ? 'shwo-comments': ''">
    <div style="border: 1px solid #5EB758; background: #F9FEF8;" class="pt30 mt20" >
      <Form :label-width="110">
        <Form-item label="满意度">
          <Rate allow-half v-model="commentRate" show-text ></Rate>
        </Form-item>
        <Form-item label="评价" class="pr50">
          <Input v-model.trim="commentText" type="textarea" :autosize="{minRows: 5,maxRows: 8}" placeholder="请输入..." :maxlength="500"></Input>
          <p class="tr">{{commentText.length}}/500</p>
        </Form-item>
        <Form-item label=" ">
          <Button type="warning" @click="handleComment">发布评论</Button> 
          <span style="color:#a0a0a0;" class="pl50" v-if="!commentText.length">评论内容不能为空</span>
        </Form-item>
      </Form>
    </div>
  </div>
  <section class="pt30">
    <div>
      <!-- 3 好评。 2中评。1差评 -->
      <Button :type="active == '' ? 'primary' : 'text'" @click="handleFilterClick('')"  class="mr10">全部评价({{datas.length}})</Button>
      <Button :type="active == 3 ? 'primary' : 'text'" @click="handleFilterClick(3)"  class="mr10">好评({{stars.gradeNum}})</Button>
      <Button :type="active == 2 ? 'primary' : 'text'" @click="handleFilterClick(2)"  class="mr10">中评({{stars.reviewNum}})</Button>
      <Button :type="active == 1 ? 'primary' : 'text'" @click="handleFilterClick(1)"  class="mr10">差评({{stars.negativeNum}})</Button>
    </div>
    <div v-if="data.length && isShow" class="pt30">
      <ul class="list">
        <li v-for="(item, index) in data" :key="index">
          <Row type="flex" align="middle" style="border-bottom: 1px dashed #e9e9e9;" class="pb10 pt10">
            <Col span="3" class="tc">
              <img :src="item.avatar" width="100" height="100" class="mt10" v-if="item.avatar" style="border-radius: 50%;"/>
              <img src="../../../../img/default_header.png" width="100" height="100" class="mt10" v-else style="border-radius: 50%;"/>
              <p class="mt5 ell" style="color:#666;" :title="item.account">{{item.account}}</p>
            </Col>
            <Col span="21" class="pl20 pr20">
              <p class="mt10 mb10" style="color:#939393;font-size:16px;">{{item.describe_info}}</p>
              <p style="color:#c4c4c4;">发布于{{item.create_time}}</p>
            </Col>
          </Row>
        </li>
      </ul>
      <!-- <div class="mt20 tc pb20">
        <Page :total="total" :page-size="pageSize" @on-change="handleChange"></Page>    
      </div> -->
    </div>
    <div v-if="data.length == 0 && isShow">
      <div  class="tc pt30 pb50">
          <img src="../../../../img/no-content.png"> 
          <p style="margin-top: 10px;">暂无相关评价</p>
      </div>
    </div>
  </section>
</div>
</template>
<script>
  export default {
    data () {
      return {
        pageSize: 10,
        total: 0,
        active: '',
        isShow: false,
        data: [],
        datas: [],
        all: 0,
        good: 0,
        medium: 0,
        bad: 0,
        commentText: '',
        commentRate: 0,
        shwoComment: false,
        showTip: false,
        loginUser: JSON.parse(sessionStorage.getItem('user')),
        id: '',
        type: '',
        stars: {
          grade: 0,
          review: 0,
          negative: 0
        }
      }
    },
    created() {
      this.id = this.$route.query.id
      this.type = this.$route.query.type
      this.handleInitComments()
    },
    filters: {
      
    },
    methods: {
      // 点击tab切换
      handleFilterClick (e) {
        this.active = e
        this.filterComments()
      },
      // 点击tab切换好中差评数据
      filterComments () {
        this.data = this.datas
        if (this.active) {
          // active  1 差评 2 中评 3 好评
          // 好中差分别为5分、3-4分、1-2分
          let arr = this.data.filter((element) => {
              let star = element.star
              if (this.active == 3 && star >= 10) {
                return element
              } else if (this.active == 2 && star >= 6 && star <= 9) {
                return element
              } else if (this.active == 1 && star <= 5) {
                return element
              }
            })
            this.data = arr
        }
      },
      // 点击发布评价
      handleComment () {
        // if (this.commentRate) {
          if (this.commentText) {
            let data = {
              serviceId: this.id,
              account: this.loginUser.loginAccount,
              star: this.commentRate*2,
              describeInfo: this.commentText,
            }
            this.$api.post('/member/fishing//saveComment',data).then(response => {
              if (response.code === 200) {
                this.$Message.success('评论成功')
                this.commentRate = 0
                this.commentText = ''
                this.handleInitComments()
              }
            })
          } else {
            this.$Message.error('请输入评价内容。')
            this.showTip = true
          }
        // } else {
        //   this.$Message.error('请选择满意度。')
        // }
      },
      // 初始化获取评论数据
      handleComments () {
        if (this.loginUser) {
          this.shwoComment = true
        } else {
          this.$emit('on-login')
        }
      },
      // 初始化获取评论数据
      handleInitComments () {
        this.isShow = true
        this.$api.post('/member/fishing/findProductServiceById', {id: this.id, type: this.type}).then(response => {
          if (response.code === 200) {
            this.data = response.data[0].commentList
            this.datas = response.data[0].commentList
            if (response.data[0].commentProbability) {
              this.stars = response.data[0].commentProbability
              this.stars = Object.assign(this.stars, response.data[0].commentProbabilityNum)
            }
            let star = 0
            if (response.data[0].commentProbabilityNum) {
              star = response.data[0].commentProbabilityNum.star
            }
            this.$emit('on-stars', {star: star, stars: this.datas.length})
            this.filterComments()
          }
        })
      },
      // 评论翻页
      handleChange () {
        
      }
    }
  }
</script>
<style lang="scss">
.service-comments{
  .comments{
    height: 0px;
    overflow: hidden;
  }
  .shwo-comments{
    height: auto;
  }
}
</style>
