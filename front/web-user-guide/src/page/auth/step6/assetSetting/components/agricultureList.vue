<template>

  <div style="background:#f9f9f9;" class="mb40">
   <!-- 第一产业 -->
        <div class="pd20" >
           <Row  :gutter="38" class="pb20">
            <Col span="8">
              <b  style="font-size:14px;">{{title}}</b>
            </Col>
          </Row>
          <Form :label-width="90" label-position="left" :rules="ruleInline" v-for="(item, index) in data" :ref="`data${index}`" :model="item">
            <Row :gutter="16">
              <Col span="8">
                  <Form-item  prop="rightHolderName" label="权利人姓名">
                    <!-- 单选 默认为会员名称，可以从人口信息花名册中选择-->
                    <Select v-model="item.rightHolderName" style="width: 100%" :disabled="!edit">
                        <Option v-for="(item, index) in rightHolderNames" :value="item.name" :key="index">{{item.name}}</Option>
                    </Select>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="facilityName" label="设施名称">
                    <Select v-model="item.facilityCategory" style="width: 100%" :disabled="!edit">
                      <Option v-for="(item, index) in facilityCategorys" :value="item.label" :key="index">{{item.label}}</Option>
                    </Select>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="area" label="面积">
                      <Input v-model="item.area" :maxlength="20" :disabled="!edit"  placeholder="请输入面积">
                      <span slot="append">平方米</span>
                      </Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="plotNumber" label="地块编号">
                      <Input v-model="item.plotNumber" :maxlength="20" :disabled="!edit"></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="plotName" label="地块名称">
                      <Input v-model="item.plotName" :maxlength="20" :disabled="!edit">
                      </Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="investmentAmount" label="投资额">
                      <Input v-model="item.investmentAmount" :maxlength="20" :disabled="!edit"  placeholder="请输入投资额">
                      <span slot="append">元</span>
                      </Input>
                  </Form-item>
              </Col>
          </Row>
          </Form>
        </div>
        <Divider></Divider>
        <div class="pd20" style="background:#f9f9f9;">
          <p class="tr t-orange subtotal">小计:面积 {{areaTotal}} 平方米，投资额 {{total}} 元</p>
        </div>
  </div>
</template>
<script>
import Divider from '~components/divider'
import {numAdd} from '~utils/utils'
import {isMoney3} from '~utils/validate'
  export default {
    components: {
      Divider
    },
    props: {
      title: '',
      type: '',
      yearId: {
        type: String
      },
      id: {
        type: String
      },
      appId: {
        type: String
      }
    },
    data () {
      return {
        edit :false,
        total: 0,
        areaTotal: 0,
        rightHolderNames: [],
        facilityCategorys: [],
        gardenings: [
          {label: '连栋温室', value: '连栋温室'},
          {label: '日光温室', value: '日光温室'},
          {label: '塑料大棚', value: '塑料大棚'},
          {label: '小拱棚(遮阳棚)', value: '小拱棚(遮阳棚)'},
        ],
        waters: [
          {label: '围网养殖', value: '围网养殖'},
          {label: '网箱养殖', value: '网箱养殖'},
          {label: '水产大棚温室', value: '水产大棚温室'},
        ],
        animals: [
          {label: '开放(敞)式', value: '开放(敞)式'},
          {label: '有窗式', value: '有窗式'}
        ],
        bacterias: [
          {label: '菇房', value: '菇房'}
        ],
        data: [
          {
            status: true,
            rightHolderName: this.$user.displayName, // 权利人姓名
            // 类别名称
            categoryAndName: [],
            facilityCategory: '', // 设施类别
            facilityName: '', // 设施名称
            moplotNumberdel: '', // 地块编号
            plotName: '', // 地块名称
            area: '', // 面积
            investmentAmount: '', // 投资额
            type: this.type
          }
        ],
        ruleInline: {
          area: [
            {validator: isMoney3, trigger: 'blur'},
          ],
          investmentAmount: [
            {validator: isMoney3, trigger: 'blur'},
          ]
        },
        activeIndex: 0
      }
    },
    created() {
      if (this.type === '0') {
        this.facilityCategorys = this.gardenings
      }
      if (this.type === '1') {
        this.facilityCategorys = this.waters
      }
      if (this.type === '2') {
        this.facilityCategorys = this.animals
      }
      if (this.type === '3') {
        this.facilityCategorys = this.bacterias
      }
      this.handleSelect()
    },
    methods: {
      // 取下拉数据
      handleSelect () {
        let list = {
          user_id: this.$user.loginAccount,
          year_id: this.yearId
        }
        this.$api.post('/member-reversion/administrationDivision/findRoster', list).then(response => {
          if (response.code === 200) {
            this.rightHolderNames = response.data
            if (this.$user.displayName) {
              this.rightHolderNames.unshift({name: this.$user.displayName})
            }
          }
        })
      },
      // 获取数据
      getData (data) {
        this.data = data
        this.edit = false
      }
    }
  }
</script>
