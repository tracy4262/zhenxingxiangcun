<template>
  <div style="background: #F4F4F4;" class="pb40 pt10">
     <!-- <img src="../../img/new-gate-banner2.png" alt="" height="200px" width="100%"> -->
     <div class="service-layouts bg-white pd30">
       <serviceList :dataList="columnList"></serviceList>
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
import serviceList from './components/serviceList'
import { navStatus, goToPath} from './mixins/commonMixins'
  export default {
    mixins: [navStatus, goToPath],
    components: {
      serviceList
    },
    data () {
      return {
        loginAccount: '',
        columnList: [
          // {serviceName: '农家乐', address: '先锋村', image: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551071920289&di=abdce01de49f8f90ff4a502c5d2fefc4&imgtype=0&src=http%3A%2F%2Fimg.2016.yidaba.com%2Fattaments%2F201610%2F20161013%2Fbig%2F6063ac441c13a6eb87ba035c9f2a2477.jpg'},
          // {serviceName: '景区', address: '先锋村', image: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551072031029&di=aa30e196459d41c0a5d70756211a72f9&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fe61190ef76c6a7ef055f2af2f7faaf51f2de66ed.jpg'},
          // {serviceName: '垂钓', address: '先锋村', image: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551068317783&di=7c271e592b2db02ffdf722a3c4833c13&imgtype=0&src=http%3A%2F%2Fwww.qz123.com%2FUploads%2FImages%2F20180411%2F6365903665542643885432903.jpg'},
          // {serviceName: '采摘', address: '先锋村', image: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551071975464&di=651bd757c757d3d2f1c6a8963d93e7a9&imgtype=0&src=http%3A%2F%2Fwww.ayzmd.com%2Fupload%2Fimage%2F20180327%2F1522132120.jpg'},
          // {serviceName: '咨询', address: '先锋村', image: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551072106792&di=1483fa9b32bea7b8fe8bc2de205ed2bd&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fmw690%2F001tdr8xgy71bgvIEmkd8'},
          // {serviceName: '民宿', address: '先锋村', image: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551072072796&di=f63cd13ddc2f4713577120fb8e85cae1&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F00e93901213fb80e61440ded3dd12f2eb9389476.jpg'},
        ],
        dataType: '政策',
        currentPage: 1,
        pageSize: 20,
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
        this.$api.post('/member-reversion/myRecommend/serviceList', {
            account: this.loginAccount,
            flag: '1', // 0:查询所有服务, 1:查询已推荐服务
            address: '',
            serviceName: '',
            memberName: '',
            pageNum: this.currentPage,
            pageSize: this.pageSize
        }).then(response => {
            if (response.code === 200) {
              this.loading = false
              console.log('res', response)
              let list = response.data.list
              this.total = response.data.total
              this.columnList = this.columnList.concat(list)
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
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
