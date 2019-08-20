<template>
  <div style="background: #F4F4F4;" class="pb40 pt10">
     <div class="introduction-layouts bg-white pd30" style="min-height: 500px;">
       <standard-list :data="columnList"></standard-list>
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
import standardList from './components/standardList'
import { navStatus, goToPath} from './mixins/commonMixins'
  export default {
    mixins: [navStatus, goToPath],
    components: {
      standardList
    },
    data () {
      return {
        loginAccount: '',
        columnList: [],
        currentPage: 1,
        pageSize: 10,
        total: 0,
        loading: true
      }
    },
    created() {
      this.loginAccount = this.$route.query.uid
      this.getList()
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
        this.$api.post('/portal/standard/standard-list', {
           account: this.loginAccount,
            label: '全部',
            pageSize: this.pageSize,
            pageNum: this.currentPage
        }).then(response => {
                if (response.code === 200) {
                    if(response.data !== undefined){
                        let list = response.data.list
                        this.total = response.data.total
                        this.columnList = this.columnList.concat(list)
                        this.loading = false
                    }  
                }
            }).catch(error => {
                this.$Message.error('操作异常！')
            })
      }
    }
  }
</script>
<style>
.introduction-layouts{
  width:1000px;
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
