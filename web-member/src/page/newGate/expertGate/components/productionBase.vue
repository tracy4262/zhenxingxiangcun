<template>
  <div class="pb40 pt10">
     <div class="expert-service-layouts bg-white pd40" style="min-height: 500px;">
       <baseList :dataList="columnList" class="pt20"></baseList>
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
import baseList from '../../components/baseList'
import { navStatus , goToPath} from '../../mixins/commonMixins'
  export default {
    mixins: [navStatus, goToPath],
    components: {
      baseList
    },
    data () {
      return {
        loginAccount: '',
        columnList: [
          // {productionBaseName: '橘子基地', description: '简介', seller: '张宏', name: '桔子', address: '先锋村', image: 'http://s11.sinaimg.cn/orignal/400a847d2620864c913ba'},
          // {productionBaseName: '玉米基地', description: '简介', seller: '王尚', name: '玉米', address: '先锋村', image: 'http://www.gov.cn/jrzg/images/images/1c6f6506c7f80ea245ea04.jpg'},
          // {productionBaseName: '莲藕基地', description: '简介', seller: '王丽', name: '莲藕', address: '先锋村', image: 'http://img5.imgtn.bdimg.com/it/u=3240119989,4067199632&fm=26&gp=0.jpg'},
          // {productionBaseName: '桃子基地', description: '简介', seller: '韩鑫', name: '桃子', address: '先锋村', image: 'http://img0.imgtn.bdimg.com/it/u=2013740337,3355280935&fm=26&gp=0.jpg'},
          // {productionBaseName: '棉花基地', description: '简介', seller: '李硕', name: '棉花', address: '先锋村', image: 'http://img0.ph.126.net/dOxc7jHw5bPSh9YNQ6sGzA==/2708915175880434940.jpg'},
          // {productionBaseName: '渔业养殖', description: '简介', seller: '孙素', name: '鱼', address: '先锋村', image: 'http://imgsrc.baidu.com/imgad/pic/item/0b55b319ebc4b745b433792fc4fc1e178a821560.jpg'},
        ],
        currentPage: 1,
        pageSize: 20,
        total: 0,
        loading: false
      }
    },
    created() {
      this.loginAccount = this.$route.query.uid
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
        this.$api.post('/member-reversion/myRecommend/baseList', {
            account: this.loginAccount,
            flag: '1', // 0:查询所有基地, 1:查询已推荐基地
            address: '',
            baseName: '',
            memberName: '',
            pageNum: this.currentPage,
            pageSize: this.pageSize
        }).then(response => {
            if (response.code === 200) {
                this.columnList = this.columnList.concat(response.data.list)
                this.loading = false
                this.total = response.data.total
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
      }
    }
  }
</script>
<style>
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
