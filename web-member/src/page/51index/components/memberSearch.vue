<template>
  <div style="position: relative;">
    <Form :label-width="80">
        <Row type="flex" align="middle" class="pt30 pb20">
            <Col span="16" offset="4">
                <Input v-model="form.keyword" search enter-button placeholder="请输入" @on-search="search"/>
            </Col>
        </Row>
        <Row type="flex" align="middle" class="pb20">
            <Col span="8" offset="4">
                <span>为您找到相关结果约{{ count }}个</span>
            </Col>
            <Col span="8" class="tr">
                <Button @click="clickShow = !clickShow" class="t-green" type="text">
                    <Icon type="ios-funnel-outline" size="18" /> 高级搜索
                </Button>
            </Col>
        </Row>
        <div v-if="clickShow" style="background: #f6f6f6;" class="pd20">
            <Row :gutter="32">
                <Col :span="form.member_class === 'f413d522b310458aa0bf763012913a0e' ? 4 : 8">
                    <FormItem label="会员类型">
                        <Cascader
                            clearable
                            v-model="form.memberClass"
                            :data="memberClass"
                            change-on-select
                            @on-change="onChange">
                        </Cascader>
                    </FormItem>
                </Col>
                <Col v-if="form.member_class === 'f413d522b310458aa0bf763012913a0e'" span="4">
                    <FormItem label="是否是专家">
                        <RadioGroup v-model="form.isExpert" @on-change="expertChange">
                            <Radio label="是"></Radio>
                            <Radio label="否"></Radio>
                        </RadioGroup>
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem label="行政区划">
                        <Cascader
                        :data="locationList"
                        change-on-select
                        :render-format="formats"
                        :load-data="loadPositionDatas"></Cascader>
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
                    <FormItem label="关联服务">
                        <vui-service :values="form.service" :num="1" @on-save="getService"></vui-service>
                    </FormItem>
                </Col>
            </Row>
            <Row :gutter="32">
              <!-- 专家的字段  -->
                <Col span="8"  v-if="form.isExpert === '是'">
                  <FormItem label="擅长领域">
                    <vui-species :values="form.expertise" :num="1" @on-save="getExpertise"></vui-species>
                  </FormItem>
                </Col>
                <Col span="8"  v-if="form.isExpert === '是'">
                  <FormItem label="专家类型">
                    <Select v-model="form.expertType" clearable @on-change="expertTypeChange">
                        <Option v-for="(f,index) in expertTypeList" :value="f.value" :key="index">{{ f.label }}</Option>
                    </Select>
                  </FormItem>
                </Col>
            </Row>
        </div>
    </Form>
  </div>
</template>
<script>

import vuiSpecies from "~components/vui-species";
import vuiTrade from '~components/vui-trade'
import vuiService from '~components/vui-service'
import vuiCascander from '~components/vuiCascader/index'
import nationalEconomyControl from '~components/national-economy-control'
import vuiProductName from '~components/vui-productName'
export default {
  components: {
    vuiSpecies,
    vuiCascander,
    vuiTrade,
    vuiService,
    nationalEconomyControl,
    vuiProductName
  },
  props: {
    count: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
        type: '1',
        clickShow: false,
        form: {
            isExpert: '否',
            memberClass: [], // 会员类别 绑定的字段
            member_class: '', // 会员类别 查询时候的字段
            species: '', // 物种
            trade: '', // 行业
            service: '', // 服务
            product: '', // 产品
            district: '', // 所在地区
            keyword: '', // 关键字
            expertise: '', // 擅长领域
            expertType: '', // 专家类型
        },
        searching: false,
        locationList: [],
        memberClass: [],
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
        ]
    }
  },
  created() {
    // 取地址
    this.$api.post("/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816").then(res => {
      this.locationList = res.data
    })
    this.getMemberClass()
    // 1、全部 2 个人 3 企业 4 机关 5 专家
    let type = this.$route.query.type
    if (type === '3') {
      this.type = '3'
      this.form.isExpert = '否'
      this.form.memberClass = ['1cb4efe86cf1499d83ae1138daa999c1', '58e0d611a9804efa9817e1231326dcc6']
      this.form.member_class = '1cb4efe86cf1499d83ae1138daa999c1/58e0d611a9804efa9817e1231326dcc6'
      this.clickShow = true
    } else if (type == '5') {
      this.type = '5'
      this.form.memberClass = ['f413d522b310458aa0bf763012913a0e']
      this.form.member_class = 'f413d522b310458aa0bf763012913a0e'
      this.form.isExpert = '是'
      this.clickShow = true
    } else {
      this.type = '1'
      this.form.isExpert = '否'
    }
    this.search()
  },
  methods: {
    getMemberClass () {
        this.$api.post('/member/member/getMemberType', { pid: '0' }).then(res => {
            console.log('member class', res)
            if (res.code === 200) {
                this.memberClass = res.data
            }
        }).catch(error => {
            this.$Message('服务器异常！')
        })
    },
    // 查询
    search () {
        this.$emit('on-search', this.form)
    },
    onChange (value, selectedData) {
        let arr = []
        selectedData.forEach( e => {
            arr.push(e.value)
        })
        console.log('form.memberClass', this.form.memberClass)
        this.form.member_class = arr.join('/')
        console.log('this.form.member_class', this.form.member_class)
        this.form.isExpert = '否'
        this.search()
    },
    expertChange () {
        this.form.expertise = ''
        this.form.expertType = ''
        this.search()
    },
    // 获取行业分类 从经济社会发展——产业信息——产业名称中来
    onGetEconomyData (data) {
      this.form.trade = data
      this.search()
    },
    // 获取 物种
    getSpecies (e) {
      this.form.species = e
      this.search()
    },
    // 行业
    getTrade (e) {
      this.form.trade = e
      this.search()
    },
    // 产品
    handleResult (e) {
      this.form.product = e
      this.search()
    },
    // 服务
    getService (e) {
      this.form.service = e
      this.search()
    },
    // 获取 擅长物种
    getExpertise (e) {
      this.form.expertise = e
      this.search()
    },
    // 专家类型
    expertTypeChange (e) {
      this.form.expertType = e
      this.search()
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
      this.form.district = label
      return label
    },
  },
}
</script>
