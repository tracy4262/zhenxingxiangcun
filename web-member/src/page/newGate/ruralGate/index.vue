<template>
  <div class="new-gate">
    <!-- 上半部咨询政策基地等 -->
    <index-top
      :columnSetting="columnSetting"
      :allTabList="allTabList"
      :recommendationTabList="recommendationTabList"
      :contactUs="contactUs"
    ></index-top>
  </div>
</template>
<script>
import { navStatus, moments, goToPath} from '../mixins/commonMixins'
import indexTop from './components/indexTop'
  export default {
    mixins: [navStatus, moments, goToPath],
    components: {
      indexTop
    },
    data () {
      return {
        activeIndex: 0,
        index: 0,
        // 动态
        dynamics: [],
        dynamicList: [],
        // 知识
        knowledgeFilterList: [],
        loginAccount: '',
        templateId: '',
        websiteInfo: {},
        adcode: '', // 城市编码
        lives: {},
        week: '',
        lunarCalendar: '',
        allTabList: [],
        recommendationTabList: [],
        contactUs: { name: '联系方式', docType: '' },
        knowledgeTabList: [],
        columnSetting: [],
        toService: '',
        toProduct: ''
      }
    },
    created() {
      this.createdInit()
    },
    watch: {
      knowledgeTabList: {
        handler (newValue, oldValue) {
          // 取第一个知识tab的数据
          this.getList('知识', newValue[0].docType)
        },
        deep: true
      }
    },
    methods: {
      createdInit () {
        this.loginAccount = this.$route.query.uid
        this.$api.post('/member-reversion/realStep/findEnableStep', {
                account: this.loginAccount
            }).then(response => {
            if (response.code === 200) {
                if (response.data) {
                    this.templateId = response.data.templateId
                    this.getIntroduction()
                    // this.getList('知识')
                    this.getData()
                }
            }
        })
        // 路由改变 (在门户页面，点击右上角的我的门户)
        if (this.$route.query.tabType) {
            this.$parent.active = `${this.$route.query.id}/${window.location.pathname.split('/')[2]}/${this.$route.query.tabType}`
        } else {
            this.$parent.active = `${this.$route.query.id}/${window.location.pathname.split('/')[2]}`
        }
      },
      knowChange (index, item) {
        this.activeIndex = index
        this.knowledgeFilterList = []
        this.getList('知识', item.docType)
      },
       // 根据栏目设置的 栏目名称 显示 动态 跟知识的title
      getData(){
        // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
        let url = this.templateId === '0' ? '/member-reversion/columnSetting/findColumnSettingInfo' : '/member-reversion/user/columnSetting/findColumnSettingInfo'
        this.$api.post(url, {
            account: this.loginAccount,
            templateId: this.templateId
        }).then(response => {
          if (response.code === 200) {
            let data = response.data.columnSetting
            this.columnSetting = response.data.columnSetting
            console.log('data', data)
            data.forEach((e, index) => {
              if (e.attributionId.split('/')[0] === 'productionBase' || e.attributionId.split('/')[0] === 'product' || e.attributionId.split('/')[0] === 'service') {
                this.recommendationTabList.push({
                  name: e.columnName,
                  attributionId: e.attributionId,
                  docType: e.attributionId.split('/')[1]
                })
              } else if (e.attributionId.split('/')[0] === 'contcat') {
                this.contactUs = {
                  name: e.columnName,
                  docType: ''
                }
              } else {
                this.allTabList.push({
                  name: e.columnName,
                  attributionId: e.attributionId,
                  docType: e.attributionId.split('/')[1]
                })
              }
            })
          }
        })
      },
      detail (item) {
        this.goDetail(item)
      },
      // 查询知识的列表
      getList (dataType, docType) {
        this.$api.get('/member/columnSettings/findColumnList?label=全部&columnId=' + dataType + '&currentPage=1&pageSize=10&account=' + this.loginAccount + '&docType=' + docType)
          .then(response => {
              if (response.data) {
                var list = response.data.dataList
                if (dataType === '知识') {
                  if (list.length > 6) {
                    list = list.splice(0, 6)
                  }
                  this.knowledgeFilterList = list.concat()
                }
              }
            })
      },
      // 获取网站设置数据
      getIntroduction () {
        // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
        let url = this.templateId === '0' ? '/member-reversion/websiteSettings/findWebsiteSettingsInfo' : '/member-reversion/user/websiteSettings/findWebsiteSettingsInfo'
        this.$api.post(url, {
          account: this.loginAccount,
          templateId: this.templateId
        }).then(response => {
          if (response.code === 200) {
            if (response.data.websiteInfo) {
              this.websiteInfo = response.data.websiteInfo
            }
          }
        }).catch(error => {
          this.$Message.error('服务器异常！')
        })
      }
    }
  }
</script>
<style lang="scss" scoped>
.new-gate{
  min-width: 1200px;
  overflow: hidden;
  .new-title-16{
    // font-family: PingFangSC-Regular;
    color: #4A4A4A;
    font-size: 16px;
    &:hover{
      color: #00c587;
    }
  }
  .new-title-14{
    // font-family: PingFangSC-Regular;
    color: #4A4A4A;
    font-size: 14px;
    &:hover{
      color: #00c587;
    }
  }
  .knowledge-list{
    .ivu-card{
      box-shadow: 0 1px 6px rgba(0,0,0,.2)
    }
    .ivu-card:hover{
      box-shadow: 0px 2px 14px 0px rgba(0,0,0,0.16);
    }
    .knowledge-title{
      color: #4A4A4A;
      font-size: 18px;
      cursor: pointer;
      &:hover{
        color: #9B9B9B;
      }
    }
    .knowledge-abstract{
      color: #9B9B9B;;
      font-size: 12px;
      cursor: pointer;
    }
    .user-img{
      border-radius: 50%;
    }
  }
  .about{
    background: -webkit-linear-gradient(left,#5096F7 , #B0E458 ); /* Safari 5.1 - 6.0 */
    background: -o-linear-gradient(right,#5096F7 , #B0E458 ); /* Opera 11.1 - 12.0 */
    background: -moz-linear-gradient(right,#5096F7 , #B0E458 ); /* Firefox 3.6 - 15 */
    background: linear-gradient(to right,#5096F7 , #B0E458 ); /* 标准的语法 */
    min-height: 350px;
    margin-top:200px;
    color:#fff;
    // overflow: hidden;
    img{
      margin-top: -100px;
      box-shadow: 0px 2px 40px 0px rgba(17,36,40,0.15);
    }
    b{
      font-family: PingFangSC-Semibold;
      font-size: 20px
    }
    p{
      font-size: 16px;
      font-family: PingFangSC-Regular;
    }
  }
  .weather{
    .weather-header{
      width: 237px;
      height: 50px;
      color:#fff;
      line-height: 50px;
      background: #F24D61;
      font-size: 16px;
    }
    .weather-body{
      border: 1px solid rgba(232,232,232,1);
      width: 237px;
      height: 200px;
    }
  }
}
</style>

