<template>
  <div style="background: #F4F4F4;" class="pb40 pt10">
     <div class="service-layouts bg-white pd30">
       <productList :dataList="columnList"></productList>
       <div class="demo-spin-col mt40 mb40" v-if="loading">
          <Spin fix>
                <Icon type="ios-loading" size=18 class="demo-spin-icon-load"></Icon>
                <div>加载中...</div>
            </Spin>
        </div>
       <div class="tc pt80 pb30" v-if="total > columnList.length">
          <Button @click="more" style="width:200px;">更多</Button>
       </div>
     </div>
  </div>
</template>
<script>
import productList from './components/productList'
import { navStatus, goToPath} from './mixins/commonMixins'
  export default {
    mixins: [navStatus, goToPath],
    components: {
      productList
    },
    data () {
      return {
        loginAccount: '',
        columnList: [
          // {discount: '7', unit: '斤', seller: '张宏', name: '橘子', address: '先锋村', image: 'http://imgsrc.baidu.com/imgad/pic/item/d043ad4bd11373f0eccd2fa6af0f4bfbfbed041c.jpg'},
          // {discount: '3', unit: '个', seller: '王尚', name: '玉米', address: '先锋村', image: 'http://image.tech-food.com/images/all/bpic/201808/20180821102646_9245.jpg'},
          // {discount: '6', unit: '斤', seller: '王丽', name: '莲藕', address: '先锋村', image: 'http://www.pig66.com/uploadfile/2018/0702/20180702074441793.jpg'},
          // {discount: '12', unit: '斤', seller: '韩鑫', name: '桃子', address: '先锋村', image: 'http://m.360buyimg.com/n12/jfs/t3754/177/1788978559/346511/9daf00b7/58356ad2N21edf337.jpg!q70.jpg'},
          // {discount: '6', unit: '斤', seller: '李硕', name: '棉花', address: '先锋村', image: 'http://www.chinairn.com/userfiles/image/20181022/20181022143259_2009.jpg'},
          // {discount: '4', unit: '斤', seller: '孙素', name: '鱼', address: '先锋村', image: 'http://s15.sinaimg.cn/bmiddle/4d5001baebf4c10f497ee'},
        ],
        seller: '',
        dataType: '政策',
        currentPage: 1,
        pageSize: 10,
        total: 0,
        loading: true
      }
    },
    created() {
      this.loginAccount = this.$route.query.uid
      this.getList()
      this.loading = false
    },
    methods: {
      createdInit() {
        this.columnList = []
        this.total = 0
        this.currentPage = 1
      },
      // 更多
      more () {
        this.currentPage ++
        if (!this.loading) {
          this.getList()
        }
      },
      getList () {
        this.loading = true
        this.$api.post('/member-reversion/myRecommend/productList', {
            account: this.loginAccount,
            flag: '1', //0:查询所有服务, 1:查询已推荐服务
            productLocation: '',
            keyword: '',
            memberName: '',
            pageNum: this.currentPage,
            pageSize: this.pageSize
        }).then(response => {
            if (response.code === 200) {
                console.log('res', response)
                this.total = response.data.total
                this.columnList = this.columnList.concat(response.data.list)
                this.loading = false
            }
        })
      }
    }
  }
</script>
<style>
.service-layouts{
  width:1200px;
  margin:0 auto;
  margin-top:40px;
  color:#4a4a4a;
  box-shadow: 0px 2px 14px 0px rgba(0,0,0,0.10);
}
.demo-spin-icon-load{
  animation: ani-demo-spin 1s linear infinite;
}
@keyframes ani-demo-spin {
  from { transform: rotate(0deg);}
  50%  { transform: rotate(180deg);}
  to   { transform: rotate(360deg);}
}
.demo-spin-col{
  height: 40px;
  position: relative;
}
</style>
