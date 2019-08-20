<template>

  <div style="background:#f9f9f9;" class="mb40">
   <!-- 第一产业 -->
        <div class="pd20" >
           <Row  :gutter="38" class="pb20">
            <Col span="8">
              <b  style="font-size:14px;">{{title}}</b>
            </Col>
            <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" @click="handleEdit" v-if="!edit">编辑</span>
            </Col>
          </Row>
          <Form :label-width="90" label-position="left" :rules="ruleInline" v-for="(item, index) in data" :key="index" :ref="`data${index}`" :model="item">
            <Row :gutter="16">
              <Col span="8">
                  <Form-item  prop="rightHolderName" label="权利人姓名">
                    <!-- 单选 地块权属信息-->
                    <Select v-model="item.rightHolderName" style="width: 100%" :disabled="!edit">
                        <Option v-for="(item, i) in rightHolderNames" :value="item.id" :key="i">{{item.landUser}}</Option>
                    </Select>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="facilityName" label="设施名称">
                    <Select v-model="item.facilityCategory" style="width: 100%" :disabled="!edit">
                      <Option v-for="(item, i) in facilityCategorys" :value="item.label" :key="i">{{item.label}}</Option>
                    </Select>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="area" label="面积">
                      <Input v-model="item.area" :maxlength="20"  :disabled="!edit" @on-change="handleNumAdd" placeholder="请输入面积">
                      <span slot="append">平方米</span>
                      </Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="plotNumber" label="地块编号">
                      <Input v-model="item.moplotNumberdel" :maxlength="20" :disabled="!edit"></Input>
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
                      <Input v-model="item.investmentAmount"  :maxlength="20" :disabled="!edit" @on-change="handleNumAdd" placeholder="请输入投资额">
                      <span slot="append">元</span>
                      </Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8" offset="16" class="tr" v-if="edit && data.length != 1">
                <FormItem label="">
                  <Button  @click="handleDel(item, index)">删除</Button>
                </FormItem>
              </Col>
          </Row>
          </Form>
          <div class="pl80" v-if="edit">
           <Button type="primary" class="btn-light-primary" icon="md-add" ghost @click="handleAdd">添加</Button>
          </div>
          <div class="pd20 tc"  v-if="edit">
            <Button type="primary" @click="handleSave" >保存</Button>
          </div>
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
        edit :true,
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
            rightHolderName: this.$parent.$parent.$parent.$parent.$parent.displayName, // 权利人姓名
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
        activeIndex: 0,
        templateId: '',
        saveisloading: false
      }
    },
    created() {
      this.templateId = this.$route.query.templateId
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
         this.$api.post('/member-reversion/landInfo/findLandInfo',{
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.id,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code == 200) {
                        this.rightHolderNames = response.data.list
                    }
                })
        // let list = {
        //   user_id: this.$user.loginAccount,
        //   year_id: this.yearId
        // }
        // this.$api.post('/member-reversion/administrationDivision/findRoster', list).then(response => {
        //   if (response.code === 200) {
        //     this.rightHolderNames = response.data
        //     this.rightHolderNames.unshift({name: this.$parent.$parent.$parent.$parent.$parent.displayName})
        //   }
        // })
      },
      // 获取数据
      getData (data) {
        this.data = data
        this.edit = false
        this.handleNumAdd()
      },
      // 计算小计
      handleNumAdd () {
        this.total = 0
        this.areaTotal = 0
        this.data.forEach(e => {
          this.total = numAdd(parseFloat(this.total ? this.total : 0).toFixed(2), parseFloat(e.investmentAmount ? e.investmentAmount : 0).toFixed(2))
          this.areaTotal = numAdd(parseFloat(this.areaTotal ? this.areaTotal : 0).toFixed(2), parseFloat(e.area ? e.area : 0).toFixed(2))
        })
        this.$emit('on-numAdd')
      },
      categoryAndNameChange ($event, item, index) {
        item.facilityCategory = $event[0]
        item.facilityName = $event[1]
        this.data.splice(index, 1, item)
      },
      // 删除
      handleDel (item, index) {
        this.$Modal.confirm({
            title: '是否确定删除',
            onOk:()=>{
              if (item.id) {
                  this.$api.post('/member-reversion/assetSeting/deleteFacilityAgricultureInfo', {id: item.id}).then(response => {
                      if (response.code === 200) {
                          this.data.splice(index, 1)
                          this.$Message.success('删除成功！')
                          this.handleNumAdd()
                      }
                  })
              } else {
                  this.data.splice(index, 1)
                  this.$Message.success('删除成功！')
                  this.handleNumAdd()
              }
            },
            okText:'确定',
            cancelText:'取消'
        })
      },
      // 添加
      handleAdd () {
         this.data.push(
          {
            status: true,
            rightHolderName: this.$parent.$parent.$parent.$parent.$parent.displayName, // 权利人姓名
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
        )
      },
      // 保存
      handleSave () {
        if (!this.saveisloading) {
          this.saveisloading = true
          let flag = true
          for (let i = 0; i < this.data.length; i++) {
            this.$refs[`data${i}`][0].validate(v => {
              if (!v) {

                this.$Message.error('请核对表单信息')
                flag = v
              }
            })
          } 
          if (flag) {
            let list = {
              account: this.$user.loginAccount,
              yearId: this.yearId,
              parentId: this.id,
              facilityAgricultureInfoName: this.title,
              facilityAgricultureInfo: this.data,
              templateId: this.templateId
            }
            this.$api.post('/member-reversion/assetSeting/saveFacilityAgricultureInfo', list).then(response => {
              this.saveisloading = false
                if (response.code === 200) {
                    this.$Message.success('保存成功')
                    this.edit = false
                    this.$emit('on-init', this.type)
                }
            })
          } else {
            this.saveisloading = true
          }
        }
      },
      handleEdit () {
        this.edit = true
      }
    }
  }
</script>
