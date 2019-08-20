<template>  
  <div  class="pd20">
     <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" ></Title>

    <Form :label-width="82" 
          v-for="(item, index) in data" :key="index"
          label-position="left" 
          style="background:#f9f9f9;" 
          class="pd20 mt40" 
          :model="item"  
          :rules="ruleInline" 
          :ref="`form${index}`"
          >
          <Row :gutter="16">
              <Col span="8">
                  <Form-item label="权限">
                      <i-switch size="large"  v-model="item.status" :disabled="true">
                          <span slot="open">公开</span>
                          <span slot="close">隐藏</span>
                      </i-switch>
                  </Form-item>
              </Col>
              <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.edit">编辑</span>
                <span class="auth-btn-toolbar" @click="handleDel(item, index)" v-if="data.length != 1 && item.edit">删除</span>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="networkName" label="网点名称">
                      <Input v-model="item.networkName" :maxlength="20" :disabled="true" @on-change="changePreview"></Input>       
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="networkType" label="网点类型">
                    <CheckboxGroup v-model="item.networkType"  @on-change="changePreview">
                        <Checkbox label="销售门店" :disabled="true"></Checkbox>
                        <Checkbox label="售后网点" :disabled="true"></Checkbox>
                    </CheckboxGroup>
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
              <Col span="16">
                  <Form-item prop="location" label="网点所在地" @click.native="activeMode = index">
                    <vui-cascander :values="item.location" @handle-get-result="handleGetData" :disabled="true"></vui-cascander>
                  </Form-item>
              </Col>
              <Col span="24">
                  <Form-item  label="详细地址">
                      <Row>
                        <Col span="10" class="pr10">
                          <Input v-model="item.address" :maxlength="50" :disabled="true" placeholder="请输入所在街道/湾组/楼栋" @on-change="changePerfectAddress(item, index)"></Input>
                        </Col>
                        <Col span="5" class="pl10">
                          <Input v-model="item.houseNumber" :maxlength="20" :disabled="true" placeholder="请输入详细门牌号"  @on-change="changePerfectAddress(item, index)"></Input>
                        </Col>
                      </Row>
                  </Form-item>
              </Col>
               <Col span="16">
                  <Form-item  label="网点完整地址是">
                      <Input type="textarea" 
                      :disabled="true"
                        :value="item.perfectAddress" 
                        :autosize="{minRows: 3,maxRows: 5}" readonly></Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="contact" label="联系人">
                    <Input v-model="item.contact" :maxlength="20" @on-change="changePreview" :disabled="true"></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="officePhone" label="办公电话">
                    <Input v-model="item.officePhone" :maxlength="20" @on-change="changePreview" :disabled="true"></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="phone" label="手机号码">
                    <Input v-model="item.phone" :maxlength="20" @on-change="changePreview" :disabled="true"></Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
            <Col span="8">
              <FormItem label="东经">
                <Input v-model="item.longitude" readonly  @on-change="changePreview" :disabled="true"></Input>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="北纬">
                <Input v-model="item.latitude" readonly  @on-change="changePreview" :disabled="true"></Input>
              </FormItem>
            </Col>
            <Col span="8" style="text-decoration: underline; color: #6C6C6C; cursor: pointer;" class="pt5">
              <!-- <span @click="onSelectPoint(item, index)"v-if="item.edit">定位获取</span> -->
              <span>定位获取</span>
            </Col>
          </Row>
          <div class="pb30 pt10" v-if="item.latitude">
            <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.longitude,item.latitude}&output=html`">
              <img :src="`//api.map.baidu.com/staticimage/v2?ak=7syPirZ2AWxacMfHeAfuujdDgFmxCB5g&center=${item.longitude},${item.latitude}&zoom=15&markers=${item.longitude},${item.latitude}&width=800`" width="100%" />
            </a>
          </div>
          <div class="tc pd20" v-if="item.edit">
            <!-- <Button type="primary" @click="handleSave(item, index)">保存</Button> -->
          </div>
    </Form>
    <!-- <Button type="primary" ghost @click="handleAdd" class="mt20 btn-light-primary" icon="md-add">增加</Button> -->
    <Title title="文字预览" class="mt50"></Title>
    <div class="pd20 pt30">
      <Input type="textarea" v-model="preview" :autosize="{minRows: 3,maxRows: 5}"></Input>
    </div>
    <div class="tc pd20">
    <Button type="primary" @click="onSave" :loading="isLoading">保存</Button>
    </div>
     <vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
  </div>
</template>
<script>
import vuiCascander from '~components/vuiCascader/index'
import Title from '../../components/title'
import vuiMap from '../../../member/components/productionMap'
import {isPhone2, isTelephoneAndphone} from '~utils/validate'
export default {
  components: {
      Title,
      vuiCascander,
      vuiMap
  },
  props: {
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
      data: [{ 
            edit: true,
            status: true,
            networkName: '', // 网点名字
            networkType: [], // 网点类型
            location: '', // 网点所在地
            address: '', // 详细地址
            houseNumber: '', // 门牌号
            perfectAddress: '', //网点完整地址
            longitude: '', // 东经
            latitude: '', // 北纬
            contact: '', // 联系人
            officePhone: '', // 办公电话
            phone: '' // 手机号码
          }],
      ruleInline:{
        phone: [{validator: isPhone2, trigger: 'blur'}],
        officePhone: [{validator: isTelephoneAndphone, trigger: 'blur'}],
      },
      index:0,
      submit:true,
      commonProductNameList: [],
      loading: false,
      isShowAddBtn: false,
      editBtn: false,
      units: [],
      preview: '',
      title: '营业网点',
      templateId: '',
      isLoading: true,
      activeMode: 0
    }
  },
  created() {
  },
  methods: {
    initTitle () {
        this.$api.post('/member-reversion/user/perfect/findTableHead', {
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            templateId: this.$template.id,
        }).then(response => {
            if (response.code === 200) {
                if (response.data.propertyName) {
                    this.title = response.data.propertyName
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    //  初始化数据
    handleInit (type) {
        this.$api.post('/member-reversion/businessOutlets/findBusinessOutletsInfo',{
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            templateId: this.$template.id,
        }).then(response => {
            if (response.code == 200) {
                this.isLoading = false
                if (type) { // 说明是更新数据
                } else {
                    this.preview = response.data.preview
                }
                if (response.data.BusinessOutlets.length) {
                    this.data = response.data.BusinessOutlets
                    this.data.forEach(e => {
                        e.edit = false
                    })
                }
            }
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
      this.changePreview()
    },
    // 文字预览
    changePreview () {
        let str = ''
        if (this.data.length) {
            str =  `所在地有服务网点${this.data.length}个，`
        }
        this.data.forEach(e => {
            if (e.productName) {
                str += `网点名称：${e.networkName}，`
            }
            if (e.networkType.length) {
                str += `网点类型：${e.networkType.join('、')}，`
            }
            if (e.perfectAddress) {
                str += `网点完整地址：${e.perfectAddress.substring(0,e.perfectAddress.length-1)}、`
            }
            if (e.longitude) {
                str += `东经：${e.longitude}，`
            }
            if (e.latitude) {
                str += `北纬：${e.latitude}，`
            }
            if (e.contact) {
                str += `联系人：${e.contact}，`
            }
            if (e.officePhone) {
                str += `办公电话：${e.officePhone}、`
            }
            if (e.phone) {
                str += `手机号码：${e.phone}、`
            }
        })
        if (str) {
            str = `${str.substring(0,str.length-1)}。`
        }
        this.preview = str
    },
    // 编辑
    handleEdit (item, index) {
      item.edit = true
      this.data.splice(index,1, item)
    },
    // 单条 保存
    handleSave (item, index) {
        this.$refs[`form${index}`][0].validate(v => {
          if (v) {
            item.id ? '' : item.id = 0
            item.account = this.$user.loginAccount
            item.yearId = this.yearId
            item.dictId = this.id
            item.templateId = this.$template.id,
            this.$api.post('/member-reversion/businessOutlets/saveOrUpdateBusinessOutlets', item).then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功')
                    item.edit = false
                    this.handleInit(1)
                }
            })
          } else {
            this.$Message.error('请核对表单信息')
          }
        })
    },
    // 保存
    onSave () {
        let list = {
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            textPreview: this.preview,
            isComplete: true,
            templateId: this.$template.id,
        }
        this.isLoading = true
        this.$api.post('/member-reversion/perfect/saveTextPreview', list).then(response => {
            if (response.code === 200) {
                this.$Message.success('保存成功')
                this.handleInit()
                this.$emit('on-save')
            }
        })
    },
    // 添加
    handleAdd () {
        this.data.push(
          { 
            edit: true,
            status: true,
            networkName: '', // 网点名字
            networkType: [], // 网点类型
            location: '', // 网点所在地
            address: '', // 详细地址
            houseNumber: '', // 门牌号
            perfectAddress: '', //网点完整地址
            longitude: '', // 东经
            latitude: '', // 北纬
            contact: '', // 联系人
            officePhone: '', // 办公电话
            phone: '' // 手机号码
          }
        )
    },
    handleDel (item, index) {
        this.$Modal.confirm({
            title: '是否确定删除',
            content: '是否确认删除？',
            onOk:()=>{
                if (item.id) {
                    this.$api.post('/member-reversion/businessOutlets/deleteBusinessOutlets', {id: item.id}).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('删除成功！')
                            this.data.splice(index,1)
                            this.changePreview()
                        }
                    })
                } else {
                    this.$Message.success('删除成功！')
                    this.data.splice(index,1)
                    this.changePreview()
                }
            },
            okText:'确定',
            cancelText:'取消'
        });
    },
  }
}
</script>
