<template>
  <div class="pb40 pt10">
     <div class="expert-service-layouts bg-white pd30">
       <productList :dataList="columnList" :column="4"></productList>
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
import productList from '../../components/productList'
import { navStatus, goToPath} from '../../mixins/commonMixins'
  export default {
    mixins: [navStatus, goToPath],
    components: {
      productList
    },
    data () {
      return {
        loginAccount: '',
        columnList: [], 
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
                account: this.account,
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
.expert-service-layouts{
  width:1000px;
  margin:0 auto;
  margin-top:30px;
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
