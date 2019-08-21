<template>
  <div class="new-expert-gate pt20 pb50">
    <Row>
      <Col :span="liveData.liveName || honorData.length ? 17 : 24">
        <div class="bg-white bd-4" >
          <div class="pd20">
            <Tabs :value="active" @on-click="handleClick">
                <TabPane :label="item.name" :name="`${index}`" v-for="(item,index) in tabList" :key="index"></TabPane>
            </Tabs>
          <!-- </div>
          <div class="pl20 "> -->
            <new-detail :dataList="list"></new-detail>
          </div>
        </div>
      </Col>
      <Col span="7">
        <div class="bg-white bd-4 ml20 mb20 pd20 right-top" v-if="liveData.liveName && liveData.show">
          <p class="h pt5 pb10"> <img src="../../../img/tv-icon.png" alt="" class="mr10" width="18px" height="18px">专家直播间</p>
          <div class="content-img">
            <img :src="liveData.liveImage" alt="" class="img">
            <p>{{liveData.liveName}}</p>
          </div>
          <p class="time pt10">直播时间：{{moment(liveData.createTime).format('YYYY-MM-DD HH:MM')}}</p>
          <p class="about pt5 t-grey">相关物种：{{liveData.SpeciesName}}</p>
          <p class="about pt5 t-grey">相关行业：{{liveData.industryName}}</p>
        </div>
        <div class="bg-white bd-4 ml20 pd20 right-bottom" v-if="honorData.length">
          <div>
            <p class="h pt5 pb15"> <img src="../../../img/honor-icon.jpg" alt="" class="mr10" width="18px" height="18px">荣誉称号</p>
            <honor-list :data="honorData"></honor-list>
            <!-- <div  v-for="(item, index) in honorData">
              <Tooltip class="vui-tooltip" placement="top" :content="`${moment(item.history_time).format('YYYY年MM月DD日')}${item.honorary_name}`" :delay="1000">
                <p class="list ell">{{moment(item.history_time).format('YYYY年MM月DD日')}}  {{item.honorary_name}}</p>
              </Tooltip>
            </div> -->
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
import Title from '../components/title'
import newDetail from './components/detail.vue'
import $ from 'jquery'
import { navStatus, moments, goToPath} from '../mixins/commonMixins'
import honorList from '../components/honorList'
  export default {
    mixins: [navStatus, moments, goToPath],
    components: {
      Title,
      newDetail,
      honorList,
    },
    data () {
      return {
        active: '0',
        tabList: [
          {name: '专家动态'},
          {name: '专家文章'},
          {name: '专家课堂'}
        ],
        dataType: '动态',
        currentPage: 1,
        pageSize: 10,
        tabType: '',
        loginAccount: '',
        list: [],
        loading: false,
        templateId: '',
        honorData: [],
        liveData: {}
      }
    },
    created() {
      this.createdInit()
      this.getList()
    },
    methods: {
      getList () {

      },
      createdInit () {
        this.loginAccount = this.$route.query.uid
        this.getLve()
        this.getHonor()
        // 去模板数据
        this.$api.post('/member-reversion/realStep/findEnableStep', {
                account: this.loginAccount
            }).then(response => {
            if (response.code === 200) {
                if (response.data) {
                    this.templateId = response.data.templateId
                    this.getData()
                }
            }
        })
      },
      getHonor () {
        this.$api.post('/member-reversion/honoraryTitle/findHonoraryTitleByAccount',{
            account: this.loginAccount
        }).then(res => {
          if (res.code === 200) {
            this.honorData = res.data
          }
        })
      },
      // 获取直播信息
      getLve () {
        this.$api.post('/relationship/live/findLiveListByAccount',{
            account: this.loginAccount
        }).then(res => {
          if (res.code === 200) {
            let list = {}
            list = Object.assign(list, res.data.liveList,)
            list = Object.assign(list, res.data.liveIndustry,)
            list = Object.assign(list, res.data.liveArea,)
            list = Object.assign(list, res.data.liveSpecies,)
            // 当前时间减去一天，是不是比直播时间小，如果小就 true 否则 是 false 判断是否显示直播间
            list.show = this.moment(this.moment(this.liveData.createTime).format('YYYY-MM-DD')).isBefore(this.moment(new Date()).format('YYYY-MM-DD'))
            this.liveData = list
          }
        })
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
            let tabList = []
            data.forEach((e, index) => {
              if (e.attributionId.split('/')[0] === 'dynamic') {
                tabList.push({
                  dataType: '动态',
                  name: e.columnName,
                  docType: e.attributionId.split('/')[1]
                })
              }
              if (e.attributionId.split('/')[0] === 'policy') {
                tabList.push({
                  dataType: '政策',
                  name: e.columnName,
                  docType: e.attributionId.split('/')[1]
                })
              }
              if (e.attributionId.split('/')[0] === 'knowledge') {
                tabList.push({
                  dataType: '知识',
                  name: e.columnName,
                  docType: e.attributionId.split('/')[1]
                })
              }
            })
            this.tabList = tabList
            this.handleClick(0)
          }
        })
      },
      // 点击tab切换
      handleClick (index) {
        let item = this.tabList[index]
        this.tabType = item.docType
        this.dataType = item.dataType
        this.init()
      },
      // 根据tab 查询数据
      init () {
        this.$api.get('/member/columnSettings/findColumnList?label=全部&columnId=' + this.dataType + '&currentPage=' + this.currentPage + "&pageSize=" + this.pageSize + '&account=' + this.loginAccount + '&docType=' + this.tabType)
          .then(response => {
              if (response.data) {
                var list = response.data.dataList
                this.total = response.data.total
                list.map(function(item){
                      item.createTime = item.createTime.split(" ")[0];
                      if(item.commentNum === undefined){
                          item.commentNum = 0
                      }
                  })
                this.list = list
                this.loading = false
              }
            })
      }
    }
  }
</script>
<style lang="scss" scoped>
.bd-4{
  border-radius: 4px;
}
.new-expert-gate{
  width:1000px;
  margin: 0 auto;
  .right-top{
    .h{
      color: #4A4A4A;
      font-size: 14px;
      font-weight: 600;
      line-height: 20px;
    }
    .content-img{
      position: relative;
      .img{
        height: 139px;
        width: 100%;
      }
      p{
        position: absolute;
        bottom: 0px;
        color: #ffffff;
        padding: 5px;
        font-size: 14px;
      }
    }
    .time{
      font-size: 14px;
    }
    .about{
      font-size: 12px;
    }
  }
  .right-bottom{
    font-size: 14px;
    color: #4A4A4A;
    .h{
      font-weight: 600;
    }
    .list{
      // line-height: 38px;
      padding: 8px 0px;
      cursor: default;
    }
    .vui-tooltip{
      width: 100%;
    }
  }
}
</style>

