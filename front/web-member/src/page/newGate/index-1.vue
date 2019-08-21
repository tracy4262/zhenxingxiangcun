<template>
  <div class="new-gate">
    <!-- 上半部咨询政策基地等 -->
    <index-top
      :columnSetting="columnSetting"
      :dynamicTabList="dynamicTabList"
      :policyTabList="policyTabList"
      :investment="investment"
      :productBase="productBase"
    ></index-top>
    <!-- 推荐商品 -->
    <div class="layouts">
      <Row type="flex" align="middle">
        <Col span="12" style="font-size: 22px; line-height: 30px; font-weight: bold;"><img src="../../img/product.png" alt="" class="mr10">{{ productTitle }}</Col>
        <Col span="12" style="font-size: 16px; line-height: 22px;" class="tr">
          <router-link :to="toProduct" class="new-title-16 mr10">更多</router-link>
        </Col>
      </Row>
      <productList :dataList="productList"></productList>
    </div>
    <!-- 推荐服务 -->
    <div class="layouts">
      <Row type="flex" align="middle" class="mt40">
        <Col span="12" style="font-size: 22px; line-height: 30px; font-weight: bold;"><img src="../../img/service.png" alt="" class="mr10">{{ serviceTitle }}</Col>
        <Col span="12" style="font-size: 16px; line-height: 22px;" class="tr">
          <router-link :to="toService" class="new-title-16 mr10">更多</router-link>
        </Col>
      </Row>
      <serviceList :dataList="serviceList"></serviceList>
    </div>
    <!-- 乡村知识 -->
    <div class="layouts">
      <div class="vui-flex pt40 pb20">
        <img src="../../img/new-gate-icon.png" alt="" class="mr10">
        <!-- <b style="color: #015198; font-size: 22px;font-family: PingFangSC-Semibold;">{{ knowledgeTitle }}</b> -->
        <div class="vui-flex-item tl" style="line-height: 32px;">
          <Row type="flex" justify="start">
            <Col span="3" v-for="(item, index) in knowledgeTabList" :key="index">
              <Button :type="activeIndex === index ? 'primary' : 'text'" @click="knowChange(index, item)">{{ item.name }}</Button>
            </Col>
          </Row>
        </div>
      </div>
      <Row :gutter="16" class="knowledge-list" >
        <Col span="12" v-for="(item, index) in knowledgeFilterList" :key="index" class="pb20">
            <Card @click.native="detail(item)">
              <div>
                <Row>
                  <Col span="9" v-if="item.imageAdd || item.coverPhoto">
                    <img :src="item.imageAdd" alt="" height="127px" width="192px" v-if="item.imageAdd">
                    <img :src="item.coverPhoto" alt="" height="127px" width="192px" v-if="item.coverPhoto">
                  </Col>
                  <Col :span="item.imageAdd || item.coverPhoto ? '15' : '24'" style="height:127px;">
                    <p class="knowledge-title ell" :title="item.title"><b>{{item.title}}</b></p>
                    <div style="height:65px;">
                      <p class="ell-3 pt10 knowledge-abstract">{{item.summary}}</p>
                    </div>
                    <Row class="pt10">
                      <Col span="24" class="tr pr10 t-grey">{{moment(item.createTime).format('YYYY-MM-DD hh:mm')}}</Col>
                    </Row>
                  </Col>
                </Row>
              </div>
            </Card>
        </Col>
      </Row>
      <h5 v-if="knowledgeFilterList.length === 0">暂无相关内容！</h5>
    </div>
    <!-- about -->
    <div class="about">
      <div class="layouts">
        <Row>
          <Col span="12" class="pr20">
            <img src="../../img/new-gate-banner3.png" alt="" width="100%" height="325px">
          </Col>
            <p class="pt30"><b>关于</b></p>
            <p class="pt10">{{websiteInfo.websiteProfile}}</p>
        </Row>
      </div>
    </div>
  </div>
</template>
<script>
import Title from './components/title'
import $ from 'jquery'
import { navStatus, moments, goToPath} from './mixins/commonMixins'
import indexTop from './ruralGate/components/indexTop'
import productList from './components/productList'
import serviceList from './components/serviceList'
  export default {
    mixins: [navStatus, moments, goToPath],
    components: {
      Title,
      indexTop,
      productList,
      serviceList
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
        key: '95adbfe8184e519713d2b5c9bcd97019',
        productTitle: '推荐商品',
        serviceTitle: '推荐服务',
        knowledgeTitle: '农技知识',
        productList: [
          {discount: '9.9', unit: '斤', seller: '张宏', name: '绿鲜知 云南甜玉米 水果玉米', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1547676993,2174015573&fm=15&gp=0.jpg'},
          {discount: '22', unit: '斤', seller: '王尚', name: '鲢鱼', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2234846370,1385206490&fm=26&gp=0.jpg'},
          {discount: '23', unit: '斤', seller: '王丽', name: '草鱼', address: '先锋村', image: 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4114146544,4064470043&fm=200&gp=0.jpg'},
          {discount: '20.5', unit: '斤', seller: '韩鑫', name: '鲈鱼', address: '先锋村', image: 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3326750850,4117637607&fm=26&gp=0.jpg'},
          {discount: '15.5', unit: '斤', seller: '李硕', name: '莲藕', address: '先锋村', image: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1700645086,3627266877&fm=200&gp=0.jpg'},
          {discount: '18.5', unit: '斤', seller: '孙素', name: '水果藕', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1010754269,1960785645&fm=26&gp=0.jpg'},
          {discount: '50', unit: '斤', seller: '孙素', name: '花旗莲', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1014809633,34860117&fm=26&gp=0.jpg'},
          {discount: '5000-8000', unit: '棵', seller: '孙素', name: '桂花树', address: '先锋村', image: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2329696859,4081472697&fm=200&gp=0.jpg'},
          {discount: '1800-2000', unit: '棵', seller: '孙素', name: '广玉兰树', address: '先锋村', image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2208217277,3429394855&fm=26&gp=0.jpg'},
          {discount: '2500-3000', unit: '棵', seller: '孙素', name: '紫薇树', address: '先锋村', image: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4168228648,1904385376&fm=26&gp=0.jpg'},
        ],
        serviceList: [
          {serviceName: '农家乐', address: '先锋村', image: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551071920289&di=abdce01de49f8f90ff4a502c5d2fefc4&imgtype=0&src=http%3A%2F%2Fimg.2016.yidaba.com%2Fattaments%2F201610%2F20161013%2Fbig%2F6063ac441c13a6eb87ba035c9f2a2477.jpg'},
          {serviceName: '景区', address: '先锋村', image: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551072031029&di=aa30e196459d41c0a5d70756211a72f9&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fe61190ef76c6a7ef055f2af2f7faaf51f2de66ed.jpg'},
          {serviceName: '垂钓', address: '先锋村', image: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551068317783&di=7c271e592b2db02ffdf722a3c4833c13&imgtype=0&src=http%3A%2F%2Fwww.qz123.com%2FUploads%2FImages%2F20180411%2F6365903665542643885432903.jpg'},
          {serviceName: '采摘', address: '先锋村', image: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551071975464&di=651bd757c757d3d2f1c6a8963d93e7a9&imgtype=0&src=http%3A%2F%2Fwww.ayzmd.com%2Fupload%2Fimage%2F20180327%2F1522132120.jpg'},
          {serviceName: '咨询', address: '先锋村', image: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551072106792&di=1483fa9b32bea7b8fe8bc2de205ed2bd&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fmw690%2F001tdr8xgy71bgvIEmkd8'},
        ],
        dynamicTabList: [],
        policyTabList: [],
        investment: { name: '招商引资', docType: '' },
        productBase: { name: '推荐基地', docType: '' },
        knowledgeTabList: [],
        columnSetting: [],
        toService: '',
        toProduct: ''
      }
    },
    created() {
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
      this.getTime()
      // 路由改变 (在门户页面，点击右上角的我的门户)
      if (this.$route.query.tabType) {
          this.$parent.active = `${this.$route.query.id}/${window.location.pathname.split('/')[2]}/${this.$route.query.tabType}`
      } else {
          this.$parent.active = `${this.$route.query.id}/${window.location.pathname.split('/')[2]}`
      }
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
            let productIndex = 0
            let serviceIndex = 0
            data.forEach((e, index) => {
              if (e.attributionId.split('/')[0] === 'dynamic') {
                this.dynamicTabList.push({
                  name: e.columnName,
                  docType: e.attributionId.split('/')[1]
                })
              }
              if (e.attributionId.split('/')[0] === 'policy') {
                // 招商引资另放
                if (e.attributionId.split('/')[1] === '030211') {
                  this.investment = {
                    name: e.columnName,
                    docType: e.attributionId.split('/')[1]
                  }
                } else {
                  this.policyTabList.push({
                    name: e.columnName,
                    docType: e.attributionId.split('/')[1]
                  })
                }
              }
              if (e.attributionId.split('/')[0] === 'productionBase') {
                this.productBase = {
                  name: e.columnName
                }
              }
              if (e.attributionId.split('/')[0] === 'product') {
                this.productTitle = e.columnName
                productIndex = index + 1
              }
              if (e.attributionId.split('/')[0] === 'service') {
                this.serviceTitle = e.columnName
                serviceIndex = index + 1
              }
              if (e.attributionId.split('/')[0] === 'knowledge') {
                this.knowledgeTabList.push({
                  name: e.columnName,
                  docType: e.attributionId.split('/')[1],
                  attribution: e.attribution.split('/')[1]
                })
              }
              // 推荐服务是一级栏目，跳转时需要带两个参数uid，id
              this.toService = `/portals/service?uid=${this.loginAccount}&id=${serviceIndex}`
              // 推荐服务是一级栏目，跳转时需要带两个参数uid，id
              this.toProduct = `/portals/product?uid=${this.loginAccount}&id=${productIndex}`
            })
          }
        })
      },
      getTime () {
        // let lunarCalendar = this.getLunarDate(this.moment(new Date).format('YYYY-MM-DD'))
        // this.lunarCalendar = `农历${lunarCalendar.lunarMonth}月${lunarCalendar.lunarDay}`
        let week = this.moment(new Date).format('d')
        let arr = ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期天']
        this.week = arr[Number(week) - 1]
      },
      detail (item) {
        this.goDetail(item)
      },
      // 获取定位
      getLoaction () {
        $.ajax({
            async : 'false',
            url : `https://restapi.amap.com/v3/ip?key=${this.key}`,
            type : 'GET',
            dataType : 'jsonp',
            jsonpCallback : 'showLocation',
            timeout : '5000',
            contentType : 'application/json;utf-8',
            success : data => {
              if (data.status == 1){
                  this.adcode = data.adcode
                  this.getWeather()
              }
            }
        });
      },
      // 获取天气
      getWeather () {
        $.ajax({
            async : 'false',
            url : `https://restapi.amap.com/v3/weather/weatherInfo?key=${this.key}&city=${this.adcode}`,
            type : 'GET',
            dataType : 'jsonp',
            jsonpCallback : 'showLocation',
            timeout : '5000',
            contentType : 'application/json;utf-8',
            success : data => {
              if (data.status == 1){
                  this.lives = data.lives[0]
                  let week = this.moment(this.lives.reporttime).format('d')
                  let lunarCalendar = this.getLunarDate(this.moment(this.lives.reporttime).format('YYYY-MM-DD'))
                  this.lunarCalendar = `农历${lunarCalendar.lunarMonth}月${lunarCalendar.lunarDay}`
                  switch (week) {
                    case '1':
                      this.week = '星期一'
                      break;
                    case '2':
                      this.week = '星期二'
                      break;
                    case '3':
                      this.week = '星期三'
                      break;
                    case '4':
                      this.week = '星期四'
                      break;
                    case '5':
                      this.week = '星期五'
                      break;
                    case '6':
                      this.week = '星期六'
                      break;
                    case '7':
                      this.week = '星期天'
                      break;
                    default:
                      break;
                  }
              }
            }
        });
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

