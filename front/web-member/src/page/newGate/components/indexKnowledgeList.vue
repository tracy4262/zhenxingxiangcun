<template>
  <div class="index-gate-knowledge-list">
    <div class="vui-flex pt40 pb20">
      <img src="../../../img/new-gate-icon.png" class="mr10 mt10" height="32px">
      <div class="vui-flex-item tabs">
        <Tabs :value="activeIndex" @on-click="tabClick">
          <TabPane v-for="(item, index) in tabList" :label="item.name" :name="`${index}`" :key="index"></TabPane>
        </Tabs>
      </div>
      <span class="more" @click="handleMore">查看更多</span>
    </div>
    <Row :gutter="16" class="knowledge-list" >
      <Col span="12" v-for="(item, index) in knowledgeFilterList" :key="index" class="pb20">
          <Card @click.native="detail(item)">
            <div>
              <Row>
                <Col span="9" v-if="item.imageAdd || item.coverPhoto">
                  <img :src="item.imageAdd" alt="" height="127px" width="100%" v-if="item.imageAdd">
                  <img :src="item.coverPhoto" alt="" height="127px" width="100%" v-if="item.coverPhoto">
                </Col>
                <Col :span="item.imageAdd || item.coverPhoto ? '15' : '24'" style="height:127px;" class="pl10">
                  <p class="knowledge-title ell" :title="item.title"><b>{{item.title}}</b></p>
                  <div style="height:65px;">
                    <p class="ell-3 pt10 knowledge-abstract">{{item.summary}}</p>
                  </div>
                  <Row class="pt10">
                    <Col span="24" class="tr pr10 t-grey time">{{moment(item.createTime).format('YYYY-MM-DD hh:mm')}}</Col>
                  </Row>
                </Col>
              </Row>
            </div>
          </Card>
      </Col>
    </Row>
    <p v-if="knowledgeFilterList.length === 0" class="tc">暂无相关内容！</p>
  </div>
</template>
<script>
import {goToPath} from '../mixins/commonMixins'
export default {
    mixins: [goToPath],
    props: {
        tabList: {
            type:Array
        },
        small:{
            type:Boolean,
            default:false
        },
        path: {
          type: String,
          default: '/farmHeadPortal'
        }
    },
  data () {
    return {
      knowledgeFilterList: [],
      loginAccount: '',
      templateId: '',
      knowledgeTabList: [],
      activeIndex: '0',
      active: 0
    }
  },
  created() {
    this.loginAccount = this.$route.query.uid
    this.knowledgeTabList = this.tabList
  },
  warch: {
    tabList: {
      handler: function (newDate, oldDate) {
        this.knowledgeTabList = newDate
        this.tabClick(0)
      },
      deep: true
    }
  },
  methods: {
    detail (item) {
      this.goDetail(item)
    },
    handleMore () {
      let item = this.tabList[this.active]
      let url = `${this.path}/${item.type}?uid=${this.loginAccount}&tabType=${item.docType}&id=${item.index}`
      this.$router.push(url)
    },
    tabClick (e) {
      this.activeIndex = `${e}`
      this.active = e
      this.knowledgeFilterList = []
      this.getList(this.tabList[e].dataType, this.tabList[e].docType)
    },
    // 查询知识的列表
    getList (dataType, docType) {
      this.$api.get('/member/columnSettings/findColumnList?label=全部&columnId=' + dataType + '&currentPage=1&pageSize=10&account=' + this.loginAccount + '&docType=' + docType)
        .then(response => {
            if (response.code == 200) {
              var list = response.data.dataList
              if (list.length > 6) {
                list = list.splice(0, 6)
              }
              this.knowledgeFilterList = list
            }
          })
    },
  },
}
</script>
<style lang="scss" scoped>
.index-gate-knowledge-list {
  .knowledge-list{
    .ivu-card{
      box-shadow: 0 2px 14px 0 rgba(0, 0, 0, 0.16);
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
    .time{
      font-size: 14px;
    }
  }
  .more{
    line-height: 50px;
    // border-bottom: 1px solid #dcdee2;
    height: 50px;
    cursor: pointer;
    font-size: 16px;
    color: #4A4A4A;
  }
}
</style>
<style lang="scss">
.index-gate-knowledge-list {
  .tabs{
    .ivu-tabs-bar{
      border:none;
      .ivu-tabs-ink-bar{
        height: 0px;
        display: none;
      }
      .ivu-tabs-nav .ivu-tabs-tab{
        font-size: 22px;
        color: #9B9B9B;
        margin-right: 0px;
      }
      .ivu-tabs-nav .ivu-tabs-tab-active{
        font-size: 22px;
        color: #015198;
      }
      .ivu-tabs-nav-prev{
        line-height:48px;
      }
      .ivu-tabs-nav-next{
        line-height:48px;
      }
    }
  }
}
</style>
