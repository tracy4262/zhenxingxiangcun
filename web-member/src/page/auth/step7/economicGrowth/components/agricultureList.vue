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
          <Form :label-width="80" label-position="left"  :ref="`data${index}`" v-for="(item, index) in data" :key="index" :model="item":rules="dataLine">
            <Row  :gutter="32">
              <Col span="8">
                <FormItem label="农产品名称">
                  <!-- 从通用商品名中选择 -->
                  <vui-product-name :disabled="!edit" :values="item.productTypeName" @handle-get-result="handleResult($event, item, index)"></vui-product-name>
                  <!-- <Input v-model="item.productTypeName" :disabled="!edit"></Input> -->
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="产量" prop="Yield">
                  <Row>
                    <Col span="14">
                      <Input v-model="item.Yield" :disabled="!edit" :maxlength="20" >
                      </Input>
                    </Col>
                    <Col span="10" class="pl10">
                      <vuiUnit :value="item.YieldUnit" @on-get-data="onGetYieldUnit($event, index)" :disabled="!edit"></vuiUnit>
                    </Col>
                  </Row>
                </FormItem>
              </Col>
              <Col span="8">
               <FormItem label="是否可折算为重量">
                  <Select v-model="item.isConversion" :disabled="!edit">
                    <Option  v-for="(list, i) in isConversions" :value="list.value" :key="i">{{list.label}}</Option >
                  </Select>
                </FormItem>
              </Col>
            </Row>
            <Row  :gutter="16" v-if="item.isConversion == '是'">
              <Col span="8">
                <FormItem label="折算时单位重量" prop="whenWeight">
                  <Input v-model="item.whenWeight" :disabled="!edit" :maxlength="20" > <span slot="append">千克</span></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="折算后产量" prop="afterWeight">
                  <Row>
                    <Col span="14">
                      <Input v-model="item.afterWeight" :disabled="!edit" :maxlength="20" >
                      </Input>
                    </Col>
                    <Col span="10" class="pl10">
                      <vuiUnit :value="item.afterWeightUnit" @on-get-data="onGetAfterWeightUnit($event, index)" :disabled="!edit"></vuiUnit>
                    </Col>
                  </Row>
                </FormItem>
              </Col>
            </Row>
            <Row  :gutter="16">
              <Col span="8">
                <FormItem label="单价" prop="price">
                  <Input v-model="item.price" :disabled="!edit" :maxlength="20" ><span slot="append">元</span></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="产值" prop="output">
                  <Input v-model="item.output" :disabled="!edit" :maxlength="20" @on-change="handleNumAdd"><span slot="append">万元</span></Input>
                </FormItem>
              </Col>
               <Col span="8" class="tr">
                <Button  @click="handleDel(item, index)" v-if="data.length != 1 && edit">删除</Button>
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
          <p class="tr t-orange subtotal">产值小计:{{total}}万元</p>
        </div>
  </div>
</template>
<script>
import Divider from '~components/divider'
import {numAdd} from '~utils/utils'
import {isNumber, isMoney3} from '~utils/validate'
import vuiProductName from '~components/vui-productName'
import vuiUnit from '~components/vui-unit'
  export default {
    components: {
      Divider,
      vuiProductName,
      vuiUnit
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
        activeIndex: 0,
        edit :true,
        total: '',
        data: [
          {price: '', // 单价
          output: '', // 产值
          Yield: '', // 产量
          YieldUnit: '', //产量单位
          productTypeName: '',  //农产品名称
          afterWeight:'',// 折算后单位重量
          afterWeightUnit: 'kg', //折算后单位
          whenWeight:'',// 折算时单位重量
          isConversion: ''}
         ],
        dataLine: {
          Yield:[{validator:isMoney3,trigger: 'blur'}],
          whenWeight:[{validator:isMoney3,trigger: 'blur'}],
          afterWeight:[{validator:isMoney3,trigger: 'blur'}],
          price:[{validator:isMoney3,trigger: 'blur'}],
          output:[{validator:isMoney3,trigger: 'blur'}],
        },
        isConversions: [
          {value: '是', label: '是'},
          {value: '否', label: '否'}
        ],
        templateId: '',
        saveisloading: false
      }
    },
    created() {
      this.templateId = this.$route.query.templateId
    },
    methods: {
      onGetYieldUnit($event, index){
        this.data[index].YieldUnit = $event
      },
      onGetAfterWeightUnit($event, index){
        this.data[index].afterWeightUnit = $event
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
        this.data.forEach(e => {
          this.total = numAdd(parseFloat(this.total ? this.total : 0).toFixed(2), parseFloat(e.output ? e.output : 0).toFixed(2))
        })
        this.$emit('on-numAdd')
      },
      // 删除
      handleDel (item, index) {
        this.$Modal.confirm({
            title: '是否确定删除',
            onOk:()=>{
              if (item.id) {
                  this.$api.post('/member-reversion/ecoSocial/deleteFarmProduct', {id: item.id}).then(response => {
                      if (response.code === 200) {
                          this.data.splice(index, 1)
                          this.$Message.success('删除成功!')
                          this.handleNumAdd()
                      }
                  })
              } else {
                  this.data.splice(index, 1)
                  this.$Message.success('删除成功!')
                  this.handleNumAdd()
              }
            },
            okText:'确定',
            cancelText:'取消'
        })
      },

      handleResult ($event, item, index) {
        this.data[index].productTypeName = $event
      },
      // 添加
      handleAdd () {
        let list =  {price: '', // 单价
          output: '', // 产值
          Yield: '', // 产量
          YieldUnit: '', //产量单位
          productTypeName: '',  //农产品名称
          afterWeight:'',// 折算后单位重量
          afterWeightUnit: 'kg', //折算后单位
          whenWeight:'',// 折算时单位重量
          isConversion: ''}
        this.data.push(list)
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
              dictId: this.id,
              type: this.type,
              list: this.data,
              templateId: this.templateId
            }
            this.$api.post('/member-reversion/ecoSocial/saveFarmProduct', list).then(response => {
              this.saveisloading = false
                if (response.code === 200) {
                    this.$Message.success('保存成功')
                    this.edit = false
                    this.$emit('on-init',this.type)
                }
            })
          } else {
            this.saveisloading = false
          }
        }
      },
      handleEdit () {
        this.edit = true
      }
    }
  }
</script>
