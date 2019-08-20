<template>
<div>
  <filter-btn ref="btn" @on-search ="handleOnchange"></filter-btn>
  <ul class="goods-list">
    <li v-for="(item, index) in listData" :key="index">
      <div @click="handleDetail(item)">
        <div class="clocker" v-if="item.finish">
          <template >
            距离结束还剩：
            <vui-clocker :time="item.time" format="%D天 %H小时 %M分 %S秒"/>
          </template>
          <!-- <template v-else>
            活动已结束
          </template> -->
        </div>
        <!-- goods-list-no-picture.png -->
        <img v-if="item.src[0]" :src="item.src[0]" width="100%" height="216">
        <img v-else src="../../../../../static/img/goods-list-no-picture1.png" width="100%" height="216">
        <div class="pd5">
          <div class="mt10 mb10" v-if="item.price && item.finish">
            <span class="t-orange"><b style="font-size: 12px">￥</b><b style="font-size: 20px">{{item.price}}</b></span>
            <span class="t-grey ml10" style="text-decoration: line-through;"><span style="font-size: 12px">￥</span>{{item.discount}}</span>
          </div>
          <div class="mt10 mb10" v-else>
            <span class="t-orange"><b style="font-size: 12px">￥</b><b style="font-size: 20px">{{item.discount}}</b></span>
          </div>
          <p class="name ell" :title="item.name" style="margin-bottom:6px;">{{item.name}}</p>
          <Row class="t-grey" style="font-size:12px;">
            <Col span="14" class="ell" :title="item.address" style="cursor: default;">{{item.address}}</Col>
            <!-- -1表示没有人评论 0代表没有好评 没有好评时不显示，-->
            <!-- 好评计算公式：好评率=好评数/总评价数 比如：没人给好评（10人评价，0人好评为0/10=0%），有人评价（10人评价，2人好评为2/10=20%） -->
            <Col span="10" class="tr" v-if="item.grade !== -1">
              好评率 
              <b class="t-green"  >{{item.grade}} %</b>
              <!-- <b class="t-green"  v-else>0 %</b> -->
            </Col>
          </Row>
          <Row class="t-grey" style="font-size:12px;" type="flex" align="middle">
            <Col span="18" class="ell" :title="item.seller" style="cursor: pointer;text-decoration: underline;">
              <span v-if="item.seller">{{item.seller}}</span>
              <span v-else>&nbsp;</span>
            </Col>
            <Col span="6" class="tr">

            <Button icon="ios-text-outline" type="text" @click.stop="webimchat(item.userId, item.account, item.avatar)"></Button> 
            </Col>
          </Row>
          <!-- <p class="t-grey" style="font-size:12px;">
            <span style="display: inline-block;width:165px;vertical-align: middle;">
              <span class="ell" style="text-decoration: underline;cursor: pointer;display:inlineblock;">{{item.seller}} </span>
            </span>
            <Button icon="chatbubble-working" type="text" @click.stop="webimchat(item.userId, item.account, item.avatar)"></Button> 
          </p> -->
            
        </div>
      </div>
    </li>
  </ul>
  <div v-if="listData.length == 0 && isShow">
    <div  class="tc pt30 pb50">
        <img src="../../../../img/no-content.png"> 
        <p style="margin-top: 10px;">暂无相关产品</p>
    </div>
  </div>
  <div class="mt30 mb50 tc" v-if="listData.length && isShow">
    <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="handleChange" :class="pageClass"></Page>
  </div>
</div>
</template>

<script>
import vuiClocker from '~components/clocker/clocker'
import filterBtn from './btn-bar'
export default {
  components: {
    vuiClocker,
    filterBtn
  },
  props: {
    pageClass: {
      type: String,
      default: () => {
        return ''
      }
    }
  },
  data () {
    return {
      isShow: false,
      listData: [],
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      account: '',
      list: {default: '1'},
      pageSize: 20,
      total: 0,
      pageNum: 1,
      gateAccount: ''
    }
  },
  created() {
    this.gateAccount = this.$route.query.uid
    if (this.loginUser) {
      this.account = this.loginUser.loginAccount
    }
    if (this.keyword) {
      this.handleSearch(1)
    } else {      
      this.handleGetList(this.list)
    }
  },
  methods: {
    // 到详情页
    handleDetail (item) {
      this.$router.push(`/goods/detail?id=${item.id}&account=${item.account}`)
    },
    // 聊天
    webimchat (userId, name, avatar) {
      if (!this.account) {
        this.$Message.error('请登录后再发起聊天')
        this.$emit('on-login')
        return
      } 
      layui.layim.chat({
        id: userId,
        name: name,
        avatar: avatar,
        type: 'friend'
      })
    },
    // 翻页
    handleChange (e) {
      this.pageNum = e
      this.handleGetList(this.list)
    },
    handleGetList (e) {
      console.log(this)
      // default :1 默认排序。timePriceFlag: 0 价格正序 1价格倒序 ,name 关键字
      let list = Object.assign({
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        isShopDisplay: 1,
        productCode: this.$route.query.productCode,
        account: this.gateAccount
      }, e)
      this.$api.post('/portal/shopCommdoity/findShopCommodityList', list).then(response => {
        if (response.code == 200) {
          this.total = response.data.total
          this.listData = response.data.list
          this.isShow = true
        }
      })
    },
    // 排序
    handleOnchange (e, dataName) {
      this.pageNum = 1
      if (e.name == '价格') {
        this.list = {timePriceFlag: `${e.asc}`}
      } else if (e.name == '好评率') {
        this.list = {gradeFlag: `${e.asc}`}
      } else if (e.name == '默认') {
        this.list = {default: '1'}
      }
      this.handleGetList(Object.assign({}, this.list, {name: this.$parent.keyword}))
    },
    handleReset () {
      this.$refs.btn.handleReset()
    }
  }
}
</script>

<style lang="scss" scoped>
.goods-list{
  .clocker{
    background: rgba(254,121,34,1);
    color: #fff;
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    padding: 6px 2px;
    text-align: center;
  }
  li{
    height: 360px;
    position: relative;
    background: #fff;
    margin: 15px 15px 0 0;
    display: inline-block;
    width: calc(100% / 5 - 12px);
    list-style: none;
    padding: 2px;
    border: 1px solid rgba(237,237,237,0.62);
    transition: box-shadow .2s cubic-bezier(.47,0,.745,.715);
    &:nth-child(5n){
      margin-right: 0;
    }
    .name{color:#4a4a4a;}
    &:hover{
      box-shadow: 0 0 0 2px #00c587;
    }
    a {
      display: block;
    }
  }
}
</style>
