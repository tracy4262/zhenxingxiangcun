<template>
  <div class="layouts">
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
        <img v-else src="../../../../static/img/goods-list-no-picture1.png" width="100%" height="216">
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
            <Col span="14" class="ell">{{item.address}}</Col>
            <Col span="10" class="tr">
              好评率 
              <b class="t-green"  v-if="item.grade > -1">{{item.grade}} %</b>
              <b class="t-green"  v-else>0 %</b>
            </Col>
          </Row>
          <p class="t-grey" style="font-size:12px;"><span style="text-decoration: underline;cursor: pointer;">{{item.seller}} </span>
            <Button icon="chatbubble-working" type="text" @click.stop="webimchat(item.userId, item.account, item.avatar)"></Button> 
          </p>
            
        </div>
      </div>
    </li>
  </ul>
  </div>
</template>
<script>
import vuiClocker from '~components/clocker/clocker'
  export default {
    components:{
      vuiClocker
    },
    data () {
      return {
        loginUser: JSON.parse(sessionStorage.getItem('user')),
        account: '',
        list: {default: '1'},
        pageSize: 5,
        total: 0,
        pageNum: 1,
        gateAccount: '',
        listData: []
      }
    },
    created() {
      this.gateAccount = this.$route.query.uid
      if (this.loginUser) {
        this.account = this.loginUser.loginAccount
      }
      this.handleGetList(this.list)
    },
    methods:{
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
       handleGetList (e) {
        console.log(this)
        // default :1 默认排序。timePriceFlag: 0 价格正序 1价格倒序 ,name 关键字
        let list = Object.assign({
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          isShopDisplay: 1,
          account: this.gateAccount
        }, e)
        this.$api.post('/portal/shopCommdoity/findShopCommodityList', list).then(response => {
          if (response.code == 200) {
            this.total = response.data.total
            this.listData = response.data.list
            this.isShow = true
          }
        })
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
