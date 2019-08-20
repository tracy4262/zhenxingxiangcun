<template>
  <div>
    <Form :label-width="90" 
          v-for="(item, index) in data" :key="index"
          label-position="left" 
          style="background:#f9f9f9;" 
          class="pd20 mb30" 
          :model="item"  
          :rules="ruleInline" 
          :ref="`form${index}`"
          >
          <Row :gutter="16">
              <!-- <Col span="8">
                  <Form-item label="权限">
                      <i-switch size="large"  v-model="item.status" >
                          <span slot="open">公开</span>
                          <span slot="close">隐藏</span>
                      </i-switch>
                  </Form-item>
              </Col> -->
              <Col span="8" offset="16" class="tr">
                <!-- <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.edit">编辑</span> -->
                <!-- <span class="auth-btn-toolbar" @click="handleDel(item, index)" v-if="data.length != 1 && item.edit">删除</span> -->
                <span class="auth-btn-toolbar" @click="handleDel(item, index)" style="cursor: pointer;">删除</span>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <FormItem prop="networkName" label="网点名称">
                      <Input v-model="item.networkName" :maxlength="20" ></Input>       
                  </FormItem>
              </Col>
              <Col span="8">
                  <Form-item prop="networkType" label="网点类型">
                    <CheckboxGroup v-model="item.networkType" >
                        <Checkbox label="销售门店" ></Checkbox>
                        <Checkbox label="售后网点" ></Checkbox>
                    </CheckboxGroup>
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
              <Col span="16">
                  <Form-item prop="location" label="网点所在地" @click.native="activeMode = index">
                    <vui-cascander :values="item.location" @handle-get-result="handleGetData" ></vui-cascander>
                  </Form-item>
              </Col>
              <Col span="24">
                  <Form-item  label="详细地址">
                      <Row>
                        <Col span="10" class="pr10">
                          <Input v-model="item.address" :maxlength="50"  placeholder="请输入所在街道/湾组/楼栋" @on-change="changePerfectAddress(item, index)"></Input>
                        </Col>
                        <Col span="5" class="pl10">
                          <Input v-model="item.houseNumber" :maxlength="20"  placeholder="请输入详细门牌号"  @on-change="changePerfectAddress(item, index)"></Input>
                        </Col>
                      </Row>
                  </Form-item>
              </Col>
               <Col span="16">
                  <Form-item  label="网点完整地址是">
                      <Input type="textarea" 
                      
                        :value="item.perfectAddress" 
                        :autosize="{minRows: 3,maxRows: 5}" readonly></Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="contact" label="联系人">
                    <Input v-model="item.contact" :maxlength="20" ></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="officePhone" label="办公电话">
                    <Input v-model="item.officePhone" :maxlength="20" ></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="phone" label="手机号码">
                    <Input v-model="item.phone" :maxlength="20" ></Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
            <Col span="8">
              <FormItem label="东经">
                <Input v-model="item.longitude" readonly ></Input>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="北纬">
                <Input v-model="item.latitude" readonly ></Input>
              </FormItem>
            </Col>
            <Col span="8" style="text-decoration: underline; color: #6C6C6C; cursor: pointer;" class="pt5">
              <span @click="onSelectPoint(item, index)">定位获取</span>
            </Col>
          </Row>
          <div class="pb30 pt10" v-if="item.latitude">
            <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.longitude,item.latitude}&output=html`">
              <img :src="`//api.map.baidu.com/staticimage/v2?ak=7syPirZ2AWxacMfHeAfuujdDgFmxCB5g&center=${item.longitude},${item.latitude}&zoom=15&markers=${item.longitude},${item.latitude}&width=800`" width="100%" />
            </a>
          </div>
          <!-- <div class="tc pd20" v-if="item.edit">
            <Button type="primary" @click="handleSave(item, index)">保存</Button>
          </div> -->
    </Form>
     <vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
  </div>
</template>
<script>
import vuiCascander from '~components/vuiCascader/index'
import vuiMap from '../../member/components/productionMap'
import {isPhone2, isTelephoneAndphone} from '~utils/validate'
export default {
  components: {
      vuiCascander,
      vuiMap
  },
  props: {
    datas: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data () {
    return {
      data: [],
      ruleInline:{
        phone: [{validator: isPhone2, trigger: 'blur'}],
        officePhone: [{validator: isTelephoneAndphone, trigger: 'blur'}],
      },
    }
  },
  watch: {
    datas: {
      handler (newValue, oldValue) {
        console.log('watch title', oldValue, newValue)
        this.data = newValue
      },
      deep: true
    }
  },
  methods: {
    handleValidate () {
      let flag = true
      this.data.forEach((e, index) => {
        this.$refs[`form${index}`][0].validate(v => {
            if (!v) {
              flag = false
            }
          })
      })
      return flag
    },
    handleDel (index) {
      this.$Modal.confirm({
          title: '是否确定删除',
          content: '是否确认删除？',
          onOk:()=>{
              this.$Message.success('删除成功！')
              this.data.splice(index,1)
          },
          okText:'确定',
          cancelText:'取消'
      })
    },
    // 获取地址
    handleGetData (value, selectedData) {
      let labelArr = []
      selectedData.forEach(element => {
          labelArr.push(element.label)
      })
      this.data[this.activeMode].location = labelArr.join('/')
      this.changePerfectAddress(this.data[this.activeMode], this.activeMode)
    },
    changePerfectAddress (item, index) {
      let str = ''
      if (item.location) {
        str += `${item.location}，`
      }
      if (item.address) {
        str += `${item.address}，`
      }
      if (item.houseNumber) {
        str += `${item.houseNumber}，`
      }
      if (str) {
        str = `${str.substring(0,str.length-1)}。`
      }
      item.perfectAddress = str
    },
    // 经纬度
    onSelectPoint (item, index) {
      this.activeMode = index
      this.$refs.experMap.showMap = true
    },
    // 取坐标
    onGetPoint (point) {
        let item = this.data[this.activeMode]
        if(point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
          item.longitude = point.lng
          item.latitude = point.lat
        } else {
          item.longitude = ''
          item.latitude = ''
        }
        this.data.splice(this.activeMode, 1, item)
    },
  },
}
</script>
