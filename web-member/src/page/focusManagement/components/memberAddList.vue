<template>
  <div style="position: relative;">
    <Form :label-width="70" class="pt30 pr20 pl20" style="background: rgba(226,246,242,0.21);">
      <Row :gutter="32">
        <Col span="8">
          <FormItem label="所在行业">
                <national-economy-control v-if="type != '5'"
                  :value="form.trade" 
                  @on-get-data="onGetEconomyData"
                  ></national-economy-control>
            <vui-trade v-if="type === '5'" :values="form.trade" :num="1" @on-save="onGetEconomyData"></vui-trade>
          </FormItem>
        </Col>
        <Col span="8">
          <FormItem label="关联物种">
            <vui-species :values="form.species" :num="1" @on-save="getSpecies"></vui-species>
          </FormItem>
        </Col>
        <Col span="8">
          <FormItem label="关联产品">
             <!-- 从通用商品名中选择 -->
            <vui-product-name 
              :values="form.product"
              clearable
              @handle-get-result="handleResult"></vui-product-name>
            <!-- <Input v-model="form.product"/> -->
          </FormItem>
        </Col>
      </Row>
      <Row :gutter="32">
        <Col span="16">
          <FormItem label="所在地区">
             <Cascader 
              :data="locationList" 
              change-on-select 
              :render-format="formats" 
              :load-data="loadPositionDatas"></Cascader>
            <!-- <vui-cascander :values="form.area" @handle-get-result="handleGetData"></vui-cascander> -->
          </FormItem>
        </Col>
        <Col span="8">
          <FormItem label="关联服务">
            <vui-service :values="form.service" :num="1" @on-save="getService"></vui-service>
          </FormItem>
        </Col>
      </Row>
      <Row :gutter="32">
        
        <Col span="8"  v-if="type == '3'">
          <FormItem label="企业类型">
            <Select v-model="form.companyType" clearable>
                <Option v-for="item in companyTypes" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
              <!-- <Input v-model="form.companyType" placeholder="请输入..."  /> -->
          </FormItem>
        </Col>
        <Col span="8"  v-if="type == '4'">
          <FormItem label="机关类型">
            <Select v-model="form.organType" clearable>
                <Option v-for="item in organTypes" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
              <!-- <Input v-model="form.organType" placeholder="请输入..."  /> -->
          </FormItem>
        </Col>
        <Col span="8"  v-if="type == '5'">
          <FormItem label="擅长领域">
            <vui-species :values="form.expertise" :num="1" @on-save="getExpertise"></vui-species>
              <!-- <Input v-model="form.expertise" placeholder="请输入..."  /> -->
          </FormItem>
        </Col>
        <Col span="8"  v-if="type == '5'">
          <FormItem label="专家类型">
            <Select v-model="form.expertType" clearable>
                <Option v-for="(f,index) in expertTypeList" :value="f.value" :key="index">{{ f.label }}</Option>
            </Select>
              <!-- <Input v-model="form.expertType" placeholder="请输入..."  /> -->
          </FormItem>
        </Col>
        <Col span="8">
          <FormItem label="关键字">
              <Input v-model="form.keyword" placeholder="请输入..."  />
          </FormItem>
        </Col>
        <Col span="16" class="tr" v-if="type == '1' || type == '2'">
            <Button type="primary" class="fr" @click="search" :loading="searching">查询</Button>
        </Col>
        <Col span="8" class="tr" v-if="type == '3' || type == '4'">
            <Button type="primary" class="fr" @click="search" :loading="searching">查询</Button>
        </Col>
        <Col span="24" class="tr pb20" v-if="type == '5'">
            <Button type="primary" class="fr" @click="search" :loading="searching">查询</Button>
        </Col>
      </Row>
    </Form>
    <div class="pl20 pr20 pt20 pb20">
      <memberList focusType="2" :data="data" :pages="pages"  @on-init="nextPage" @on-cancel="handleCancel" ></memberList>
      <div class="tr" style="right: 20px; bottom: 23px; position: absolute;" v-if="data.length">
        <Button type="primary" size="small" @click.native="focusAll">一键关注</Button>
      </div>
    </div>
  </div>
</template>
<script>

import vuiFilter from "~components/vuiFilter/filter";
import vuiSpecies from "~components/vui-species";
import vuiTrade from '~components/vui-trade'
import vuiService from '~components/vui-service'
import vuiCascander from '~components/vuiCascader/index'
import memberList from './memberList'
import nationalEconomyControl from '~components/national-economy-control'
import vuiProductName from '~components/vui-productName'
export default {
  components: {
    vuiFilter,
    vuiSpecies,
    vuiCascander,
    vuiTrade,
    vuiService,
    memberList,
    nationalEconomyControl,
    vuiProductName
  },
  props: {
    type: {
      type: String,
      default: '0'
    }
  },
  data () {
    return {
      data: [],
      form: {
        species: '', // 物种
        trade: '', // 行业
        service: '', // 服务
        product: '', // 产品
        city: '', // 所在地区
        keyword: '', // 关键字
        companyType: '', // 企业类型
        organType: '', // 机关类型
        expertise: '', // 擅长领域
        expertType: '', // 专家类型
      },
      pages: {
        pageSize: 24,
        pageNum: 1,
        total: 0
      },
      searching: false,
      locationList: [],
      companyTypes: [ // 企业类型
        {label: '农业龙头企业', value: '法人/企业法人/农业龙头企业'},
        {label: '农民合作社', value: '法人/企业法人/农民合作社'},
        {label: '家庭农场', value: '法人/企业法人/家庭农场'},
      ],
      organTypes: [ // 机关类型
        {label: '机关法人', value: '法人/机关法人'}
      ],
      expertTypeList: [
					{
						value: '农业',
						label: '农业'
					},
					{
						value: '畜牧',
						label: '畜牧'
					},
					{
						value: '渔业',
						label: '渔业'
					},
					{
						value: '林业',
						label: '林业'
					},
					{
						value: '土肥',
						label: '土肥'
					},
					{
						value: '饲料',
						label: '饲料'
					},
					{
						value: '农机',
						label: '农机'
					},
					{
						value: '法律',
						label: '法律'
					},
					{
						value: '兽药',
						label: '兽药'
					},
					{
						value: '加工',
						label: '加工'
					},
					{
						value: '农药',
						label: '农药'
					},
					{
						value: '包装',
						label: '包装'
					},
					{
						value: '质检',
						label: '质检'
					},
					{
						value: '运输',
						label: '运输'
					},
					{
						value: '信息传输',
						label: '信息传输'
					},
					{
						value: '金融',
						label: '金融'
					},
					{
						value: '保险业',
						label: '保险业'
					}
      ],
    }
  },
  created() {
    // 取地址
    this.$api.post("/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816").then(res => {
      this.locationList = res.data
    });
  },
  methods: {
    handleCancel (item, index) {
      if (item.followType === '0') { // 未关注 调用关注的接口
        this.handleFocus([item])
      } else { // 已关注 调用取消关注的接口
      console.log(item)
        this.$api.post('/member/followManage/deleteFollowMemberInfo', {dataList: [item]}).then(response => {
          if (response.code === 200) {
            this.$Message.success('取消关注成功')
            this.getInit()
            this.$emit('on-init')
          } else {
            this.$Message.error('取消关注失败')
          }
        })
      }
    },
    getInit () {
      let type = ''
      if (this.type === '1') { // 全部
        type = ''
      } else if (this.type === '2') { // 个人
        type = '个人'
      } else if (this.type === '3') { // 企业
        type = '法人/企业法人'
      } else if (this.type === '4') { // 机关
        type = '法人/机关法人'
      } else if (this.type === '5') { // 专家
        type = '专家'
      }
      this.form.pageSize = this.pages.pageSize
      this.form.pageNum = this.pages.pageNum
      this.form.account = this.$user.loginAccount
      this.form.memberClass = type
      this.$api.post('/member/followManage/findLoginByMember', this.form).then(response => {
        if (response.code === 200) {
          this.data = response.data.list
          this.pages.total = response.data.total
        }
      })
    },
    // 一键关注
    focusAll () {
      let arr = []
      this.data.forEach((element, index) => {
        if (element.followType === '0') {
          arr.push(element)
        }
      })
      console.log(arr)
      if (arr.length) {
        this.handleFocus(arr)
      } else {
        this.$Message.warning('已全部关注')
      }
    },
    handleFocus (arr) {
      this.$api.post('/member/followManage/insertFollowMemberInfo', {account: this.$user.loginAccount, dataList: arr}).then(response => {
          if (response.code === 200) {
            this.$Message.success('关注成功')
            this.getInit()
            this.$emit('on-init')
          } else {
            this.$Message.error('关注失败')
          }
        })
    },
    // 查询
    search () {
      this.nextPage(1)
    },
    // 翻页
    nextPage (e) {
      this.pages.pageNum = e
      this.getInit()
    },
    // 获取行业分类 从经济社会发展——产业信息——产业名称中来
    onGetEconomyData (data) {
      this.form.trade = data
    },
    // 获取 物种
    getSpecies (e) {
      this.form.species = e
    },
    // 获取 擅长物种
    getExpertise (e) {
      this.form.expertise = e
    },
    // 行业
    getTrade (e) {
      this.form.trade = e
    },
    // 产品
    handleResult (e) {
      this.form.product = e
    },
    // 服务
    getService (e) {
      this.form.service = e
    },
    loadPositionDatas(item, callback) {
      item.loading = true;
      this.$api.post(`/member/town/next/${item.value}`).then(res => {
        item.loading = false;
        item.children = res.data;
        callback();
      });
    },
    formats (labels, selectedData) {
      var label =''          
      for(var i = 0;i<labels.length;i++){
        if(i==labels.length-1){
          label+=labels[i]
        }else{
          label+=labels[i]+'/'
        } 
      }
      console.log(label)
      // this.addr = label
      this.form.city = label
      return label
    },
  },
}
</script>
