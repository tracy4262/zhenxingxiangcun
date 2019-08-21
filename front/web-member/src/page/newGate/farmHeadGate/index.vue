<template>
  <div class="new-farm-head-gate new-gate">
    <Row>
      <Col span="18" class="pt40">
        <Row>
            <Col span="7">
                <Carousel v-model="value" loop dots="outside">
                    <CarouselItem v-for="(item, index) in rotationPictureList" :key="index" class="carouse-height bc">
                        <div class="tc" @click="detail(item)" style="cursor: pointer;">
                            <img :src="item.image" style="width: 100%; height: 240px;" />
                            <div class="mt10 mb10 ell" :title="item.title">{{ item.title }}</div>
                        </div>
                    </CarouselItem>
                </Carousel>
            </Col>
            <!-- 走进企业 -->
            <Col span="16" class="pl20">
                <div style="background-color: #fafafa; padding-top: 1px; padding-bottom: 1px;">
                    <Title :title="introducation.name" class="ml10"></Title>
                </div>
                <div class="mt10">
                    <p style="color: #4A4A4A; font-size: 14px; line-height: 24px;">{{ introduction }}<a @click="introducationDetail">查看详情 >></a></p>
                </div>
            </Col>
        </Row>
        <indexProductList ref="product" :tabList="productTabList"></indexProductList>
        <index-production-base :dataList="productionBaseList" :productionBaseTitle="productionBaseTitle"></index-production-base>
        <indexKnowledgeList ref="knowledge" :tabList="knowledgeTabList"></indexKnowledgeList>
      </Col>
      <Col span="6" class="pl20 pt40">
        <div>
           <!-- 详情 -->
           <div class="title-green">
              <Row type="flex" align="middle">
                  <Col span="16">
                  <span class="left"></span> 
                  联系我们
                  </Col>
                  <Col span="8" class="tr title-more pr10" @click.native="handleMore(concatUrl)">
                      查看更多
                  </Col>
              </Row>
          </div>
          <div class="pd10">
            <Row v-if="contactUsDetail.longitudeStatus">
                <img :src="`//api.map.baidu.com/staticimage?width=286&height=180&center=${contactUsDetail.longitude},${contactUsDetail.latitude}&zoom=14&markers=${contactUsDetail.longitude},${contactUsDetail.latitude}`" style="width: 100%; height: 150px;">
            </Row>
            <Row class="mt20 concat pb15">
                <Col span="24" class="p" v-if="contactUsDetail.memberMameStatus">姓名：{{ contactUsDetail.member_name }}</Col>
                <Col span="24" class="p" v-if="contactUsDetail.seatPhoneStatus">座机电话：{{ contactUsDetail.seat_phone }}</Col>
                <Col span="24" class="p" v-if="contactUsDetail.phoneStatus">手机号：{{ contactUsDetail.phone }}</Col>
                <Col span="24" class="p" v-if="contactUsDetail.qqNumberStatus">QQ号：{{ contactUsDetail.qq_number }}</Col>
                <Col span="24" class="p" v-if="contactUsDetail.wechatNumberStatus">微信号：{{ contactUsDetail.wechat_number }}</Col>
                <Col span="24" class="p" v-if="contactUsDetail.emailStatus">邮箱：{{ contactUsDetail.email }}</Col>
                    <Col span="24" v-if="contactUsDetail.detailAddressStatus" class="ell" :title="contactUsDetail.detailAddress">详细地址：{{ contactUsDetail.detailAddress }}</Col>
            </Row>
           </div>
           <div class="title-green">
              <Row type="flex" align="middle">
                  <Col span="16">
                  <span class="left"></span> 
                  荣誉奖项
                  </Col>
                  <!-- <Col span="8" class="tr title-more pr10">
                      查看更多
                  </Col> -->
              </Row>
          </div>
            <div class="pd10 pb20">
              <honor-list :data="honorData"></honor-list>
            </div>
          </div>
          <div class="title-green">
              <Row type="flex" align="middle">
                  <Col span="16">
                  <span class="left"></span> 
                  专家团队
                  </Col>
                  <!-- <Col span="8" class="tr title-more pr10" @click="handleMore">
                      查看更多
                  </Col> -->
              </Row>
          </div>
          <Row :gutter="8" class="pd10 pb15" style="height: 280px; overflow-y:auto;">
              <Col span="8" v-for="(item, index) in expertTeam" :key="index" class="tc pb10" @click.native="expertDetail(item)" style="cursor: pointer;">
                  <img :src="item.personalPhoto" style="width: 100%; height: 70px;">
                  <p style="font-size: 14px; color: #4A4A4A; line-height: 20px;" class="mt5 ell" :title="item.expertName">{{ item.expertName }}</p>
                  <p style="color: #9B9B9B; line-height: 17px;" class="ell mt5" :title="item.title">{{ item.title }}</p>
              </Col>
          </Row>
          <div class="title-green">
              <Row type="flex" align="middle">
                  <Col span="16">
                  <span class="left"></span> 
                  管理团队
                  </Col>
              </Row>
          </div>
          <div class="management-team pb20 pd10">
            <p v-for="(item, index) in teamData" :key="index">
              {{item.department}}
              {{item.job}}<span v-if="item.job">：</span>
              {{item.name}}
            </p>
          </div>
      </Col>
    </Row>
    <div style="width:1000px">
    </div>
  </div>
</template>
<script>
import Title from '../components/title'
import honorList from '../components/honorList'
import indexKnowledgeList from '../components/indexKnowledgeList'
import indexProductList from '../components/indexProductList'
import $ from 'jquery'
import { navStatus, moments, goToPath} from '../mixins/commonMixins'
import indexProductionBase from '../components/indexProductionBase'
  export default {
    mixins: [navStatus, moments, goToPath],
    components: {
      Title,
      honorList,
      indexProductList,
      indexKnowledgeList,
      indexProductionBase
    },
    data () {
      return {
        value: 0,
        introducation: {},
        introduction: '',
        rotationPictureList: [],
        expertTeam: [],
        honorData: [],
        contactUsDetail: {},
        templateId: '',
        concatUrl: '',
        productionBaseList:[],
        productionBaseTitle: '生产基地',
        teamData: [],
        knowledgeTabList: [], // 包含知识，资讯，政策
        productTabList: [] // 包含 产品 服务
      }
    },
    created() {
      this.createdInit()
    },
    methods: {
      createdInit () {
        this.loginAccount = this.$route.query.uid
        this.getHonor()
        this.getExpertTeam()
        this.getContactUs()
        this.getProductionBase()
        this.getRotationPicture()
        this.getIntroduction()
        this.getTeam()
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
      introducationDetail () {
        let item = this.introducation
        let url = `/farmHeadPortal/${item.type}?uid=${this.loginAccount}&id=${item.index}`
        this.$router.push(url)
      },
      // 查询会员介绍
      getIntroduction () {
          this.$api.post('/member/memberIntroduce/findMemberIntroduceInfo', {
              account: this.loginAccount
          }).then(response => {
              if (response.code === 200 && response.data) {
                  this.introduction = response.data.introduceDetail.abstracts
              }
          }).catch(error => {
              this.$Message.error('服务器异常！')
          })
      },
      // 获取轮播图
      getRotationPicture () {
          this.$api.post('/member/inforMation/dynamicCoverList', {
              account: this.loginAccount
          }).then(response => {
              console.log('res', response)
              if (response.code === 200) {
                  this.rotationPictureList = response.data
                  this.rotationPictureList.forEach(element => {
                      element.dataType = '动态'
                  })
              }
          }).catch(error => {
              this.$Message.error('服务器异常！')
          })
      },
      // 获取管理团队数据
      getTeam () {
        this.$api.post('/member-reversion/administrationDivision/findManagerialStaffByAccount', {
            account: this.loginAccount,
        }).then(response => {
            if (response.code === 200) {
                console.log('res', response)
                this.teamData = response.data
                // this.productionBaseList = response.data.list
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
      },
      getProductionBase () {
        this.$api.post('/member-reversion/myRecommend/baseList', {
            account: this.loginAccount,
            flag: '1', // 0:查询所有基地, 1:查询已推荐基地
            address: '',
            baseName: '',
            memberName: '',
            pageNum: 1,
            pageSize: 3
        }).then(response => {
            if (response.code === 200) {
                console.log('res', response)
                this.productionBaseList = response.data.list
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
      },
      handleMore (url) {
        this.$router.push(url)
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
            let knowledgeTabList = []
            let productTabList = new Array
            data.forEach((e, index) => {
              // e.attributionId = `${index}/${e.attributionId}`
              if (e.attributionId.split('/')[0] === 'dynamic') {
                knowledgeTabList.push({
                  name: e.columnName,
                  dataType: '动态',
                  index: index + 1,
                  type: e.attributionId.split('/')[0],
                  docType: e.attributionId.split('/')[1]
                })
              }
              if (e.attributionId.split('/')[0] === 'policy') {
                knowledgeTabList.push({
                  dataType: '政策',
                  name: e.columnName,
                  index: index + 1,
                  type: e.attributionId.split('/')[0],
                  docType: e.attributionId.split('/')[1]
                })
              }
              if (e.attributionId.split('/')[0] === 'knowledge') {
                knowledgeTabList.push({
                  name: e.columnName,
                  dataType: '知识',
                  index: index + 1,
                  type: e.attributionId.split('/')[0],
                  docType: e.attributionId.split('/')[1],
                  attribution: e.attribution.split('/')[1]
                })
              }
              if (e.attributionId.split('/')[0] === 'contcat') {
                  this.concatUrl = `/farmHeadPortal/contcat?uid=${this.loginAccount}&id=${index+1}`
              }
              if (e.attributionId.split('/')[0] === 'introduction') {
                this.introducation = {
                  name: e.columnName,
                  index: index + 1,
                  type: e.attributionId.split('/')[0],
                }
              }
              if (e.attributionId.split('/')[0] === 'product') {
                productTabList.push({
                  name: e.columnName,
                  dataType: '产品',
                  index: index + 1,
                  type: e.attributionId.split('/')[0],
                })
              }
              if (e.attributionId.split('/')[0] === 'service') {
                productTabList.push({
                  name: e.columnName,
                  dataType: '服务',
                  index: index + 1,
                  type: e.attributionId.split('/')[0],
                })
              }
            })
            this.knowledgeTabList = knowledgeTabList
            this.productTabList = productTabList
            this.$nextTick(() => {
              this.$refs['product'].tabClick(0)
              this.$refs['knowledge'].tabClick(0)
            })
          }
        })
      },
      getContactUs () {
          this.$api.post('/member/columnSettings/findContact', {
              account: this.loginAccount
          }).then(response => {
              if (response.code === 200) {
                if (response.data.length) {
                    this.contactUsDetail = response.data[0].safeFormData[0]
                }
              }
          }).catch(error => {
              this.$Message.error('服务器异常！')
          })
      },
      getExpertTeam () {
          this.$api.post('/member-reversion/employ/manage', {
              account: this.loginAccount,
              expertName: '',
              location: '',
              pageNum: 1,
              pageSize: 100,
              relatedIndustry: '',
              relatedSpecies: '',
          }).then(response => {
              if (response.code === 200) {
                  console.log('expert team', response)
                  this.expertTeam = response.data.list
              }
          }).catch(error => {
              this.$Message.error('服务器异常！')
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
      expertDetail (item) {
        this.$toPortals(item.account)
      }
    }
  }
</script>
<style lang="scss" scoped>
.new-farm-head-gate{
  width: 1200px;
  margin: 0 auto;
  .title-green{
    background: #FAFAFA;
    font-size: 14px;
    color: #4A4A4A;
    padding: 10px 0px;
    font-weight: 600;
    .left{
      display: inline-block;
      width: 7px;
      height: 19px;
      background: #00C587;
      margin-left:10px;
      vertical-align: bottom;
    }
    .title-more{
      color: #9B9B9B;
      font-size: 12px;
      font-weight: 400;
      cursor: pointer;
    }
  }
  .management-team{
    p{
      font-size: 14px;
      font-weight: 600;
      color: #4A4A4A;
      padding: 8px 0px;
    }
  }
  .concat{
    .p{
      font-size: 14px;
      line-height: 23px;
      color: #4A4A4A;
    }
  }
}
.bc {
    background-color: #f9f9f9;
}
.carouse-height {
    height: 440px;
}
</style>

