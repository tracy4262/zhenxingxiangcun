<template>
  <div >
    <div :class="bgGrey ? 'certification' : ''" class="pd20 mb20 pt30" v-for="(item, index) in data">
      <Form :label-width="80" label-position="left" :ref="`data${index}`" :model="item" :rules="dataLine">
        <Row :gutter="32" class="pb20">
          <Col span="8" offset="16" class="tr">
            <span class="auth-btn-toolbar"  @click="handleDel(item, index)"  style="cursor: pointer;">删除</span>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="会员全称" prop="member_name">
              <Select v-model="item.member_name_id" style="width:100%"  @on-change="selectChange(index)">
                <Option v-for="item in selectData" :value="item.member_aptitude_real_info_id" :key="item.member_aptitude_real_info_id">{{ item.member_name }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.memberMameStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
          </Col>
          <Col span="8">
            <FormItem label="名称简写" prop="member_abbreviation"> 
              <Select v-model="item.member_name_id" style="width:100%"  @on-change="selectChange(index)">
                <Option v-for="item in selectData" :value="item.member_aptitude_real_info_id" :key="item.member_aptitude_real_info_id">{{ item.member_abbreviation }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.memberAbbreviationStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="联系人姓名" prop="contact_name">
              <Input v-model="item.contact_name"  :maxlength="20"></Input>
            </FormItem>
          </Col>
          <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.contactNameStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
          </Col>
          <Col span="8">
            <FormItem label="身份证号" prop="card">
              <Input v-model="item.card" :maxlength="18" ></Input>
            </FormItem>
          </Col>
          <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.cardStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="座机电话" prop="seat_phone">
              <Input v-model="item.seat_phone" :maxlength="13" ></Input>
            </FormItem>
          </Col>
          <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.seatPhoneStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
          </Col>
          <Col span="8">
            <FormItem label="手机号码" prop="phone">
              <Input v-model="item.phone" :maxlength="11" ></Input>
            </FormItem>
          </Col>
          <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.phoneStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="QQ号码" prop="qq_number">
              <Input v-model="item.qq_number" :maxlength="11" ></Input>
            </FormItem>
          </Col>
          <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.qqNumberStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
          </Col>
          <Col span="8">
            <FormItem label="微信账号" prop="wechat_number">
              <Input v-model="item.wechat_number"  :maxlength="20"></Input>
            </FormItem>
          </Col>
          <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.wechatNumberStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="邮箱" prop="email">
              <Input v-model="item.email" ></Input>
            </FormItem>
          </Col>
          <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.emailStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
          </Col>
          <Col span="8">
            <FormItem label="网站地址" prop="website_url">
              <Input v-model="item.website_url"  :maxlength="200"></Input>
            </FormItem>
          </Col>
          <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.websiteUrlStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="邮政编码" prop="postal_code">
              <Input v-model="item.postal_code" :maxlength="6" ></Input>
            </FormItem>
          </Col>
          <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.postalCodeStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="20">
            <FormItem label="个人照片" prop="image">
              <vui-upload
                    :ref="`upload${index}`"
                    @on-getPictureList="getPictureList($event, index)"
                    :total="1"
                    :multiple="false"
                    :hint="'支持拓展名称：png jpg'"
                    :size="[80,80]"
                    ></vui-upload>
            </FormItem>
            </Col>
            <Col span="4">
              <Switch class="ml20"  size="large" v-model="item.image_status" >
                  <span slot="open">公开</span>
                  <span slot="close">隐藏</span>
              </Switch>
            </Col>
        </Row>
        <!-- <FormItem label="所在位置" prop="location">
          <Row>
            <Col span="20"  @click.native="locationClick(index)">
              <vui-cascander :values="item.location" @handle-get-result="handleGetData" ></vui-cascander>
            </Col>
            <Col span="4">
             <Switch class="ml20"  size="large" v-model="item.location_status"  @on-change="addressChange(item, index)">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
            </Switch>
            </Col>
          </Row>
        </FormItem> -->
        <Row :gutter="32">
          <Col span="20">
            <FormItem label="会员详细地址" prop="detailAddress">
                <Input type="textarea" v-model="item.detailAddress" :autosize="{minRows: 3,maxRows: 5}"></Input>
            </FormItem>
          </Col>
          <Col span="4">
            <Switch class="ml20"  size="large" v-model="item.detailAddressStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
            </Switch>
          </Col>
        </Row>
        <!-- <Row :gutter="38">
          <Col span="13" >
            <FormItem label="详细地址" prop="detailAddress">
              <Input v-model="item.address"  placeholder="请输入所在街道/湾组/楼栋" @on-change="addressChange(item, index)"></Input>
            </FormItem>
          </Col>
          <Col span="11" style="overflow: hidden;">
            <FormItem  prop="house_number" style="margin-left: -70px;">
              <Row :gutter="0">
                <Col span="16" class="pl10" style="margin-left:-10px;">
                  <Input v-model="item.house_number"  placeholder="请输入详细门牌号"  @on-change="addressChange(item, index)"></Input>
                </Col>
                <Col span="5">
                  <Switch class="ml20" size="large" v-model="item.address_status"  @on-change="addressChange(item, index)">
                      <span slot="open">公开</span>
                      <span slot="close">隐藏</span>
                  </Switch>
                </Col>
              </Row>
            </FormItem>
          </Col>
        </Row> -->
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="东经" prop="longitude">
              <Input v-model="item.longitude" readonly></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="北纬" prop="latitude">
              <Input v-model="item.latitude" readonly></Input>
            </FormItem>
          </Col>
          <Col span="4" style="text-decoration: underline; color: #6C6C6C; cursor: pointer;" class="pt5">
            <span v-if="!item.isEdit">定位获取</span>
            <span v-else @click="onSelectPoint(index)">定位获取</span>
          </Col>
          <Col span="4">
             <Switch class="ml20"  size="large" v-model="item.longitudeStatus">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
            </Switch>
          </Col>
        </Row>
        <div class="pb30 pt10" v-if="item.latitude">
          <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.latitude},${item.longitude}&output=html`">
            <img :src="`//api.map.baidu.com/staticimage?height=200&center=${item.longitude},${item.latitude}&zoom=15&scale=2&markers=${item.longitude},${item.latitude}`" alt="" width="100%" height="500px">
          </a>
        </div>
      </Form>
    </div>
     <vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
  </div>
</template>
<script>
import $ from 'jquery'
import vuiUpload from '~components/vui-upload'
import vuiMap from '../../member/components/productionMap'
import VueQrCode from '@xkeshi/vue-qrcode'
import vuiCascander from '~components/vuiCascader/index'
import {isTelephone, isPhone2, isQQ, isWeChat2, isEmail2, isUrl, isPostCode, isIdCard2} from '~utils/validate'
  export default {
    components: {
      vuiUpload,
      vuiMap,
      VueQrCode,
      vuiCascander
    },
    props: {
      datas: {
        type: Array,
        default: () => {
          return []
        }
      },
      bgGrey: {
        type: Boolean,
        default: true
      }
    },
    data () {
      return {
        data: [],
        activeIndex: 0,
        location: '',
        dataLine: {
          // member_name: [
          //   { required: true, message: '请填选择会员全称', trigger: 'change' }
          // ],
          // member_abbreviation: [
          //   { required: true, message: '请填选择名称简写', trigger: 'change' }
          // ],
          // contact_name: [
          //   { required: true, message: '请填写联系人姓名', trigger: 'blur' }
          // ],
          card: [
            // {required: true, message: '请填写身份证号', trigger: 'blur'},
            {validator: isIdCard2}
          ],
          seat_phone: [
            // {required: true, message: '请填写座机电话', trigger: 'blur'},
            {validator: isTelephone},
          ],
          phone: [
            // {required: true, message: '请填写手机号码', trigger: 'blur'},
            {validator: isPhone2},
          ],
          qq_number: [
            // {required: true, message: '请填写QQ号码', trigger: 'blur'},
            {validator: isQQ},
          ],
          wechat_number: [
            // {required: true, message: '请填写微信账号', trigger: 'blur'},
            {validator: isWeChat2},
          ],
          email:[
            // {required: true, message: '请填写邮箱', trigger: 'blur'},
            {validator: isEmail2},
          ],
          website_url:[
            /* {required: true, message: '请填写网站地址', trigger: 'blur'}, */
            {validator: isUrl},
          ],
          postal_code:[
            // {required: true, message: '请填写邮政编码', trigger: 'blur'},
            {validator: isPostCode},
          ],
          // location:[
          //   {required: true, message: '请选择所在位置', trigger: 'change'}
          // ],
          // address:[
          //   {required: true, message: '请填写详细地址', trigger: 'blur'}
          // ],
          // house_number:[
          //   {required: true, message: '请填写详细门牌号', trigger: 'blur'}
          // ],
          // image: [
          //   { required: true, type: 'array', min: 1, message: '请上传个人照片', trigger: 'change' }
          // ],
          // longitude:[
          //   {required: true, message: '请选获取定位'}
          // ],
          // latitude:[
          //   {required: true, message: '请选获取定位'}
          // ],
        },
        selectData: [],
        nameData: [],
        abbreviationData: [],
        templateId: '',
        stepId: ''
      }
    },
    created() {
      this.$api.post('/member-reversion/realStep/findEnableStep', {
                account: this.$user.loginAccount
            }).then(response => {
            if (response.code === 200) {
                if (response.data) {
                    this.templateId = response.data.templateId
                    this.handleSelectData()
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
      this.account = this.$user.loginAccount
    },
    watch: {
      datas: {
        handler (newValue, oldValue) {
          console.log('watch title', oldValue, newValue)
          this.data = newValue
          this.$nextTick(e => {
            for(var i = 0 ;i < this.data.length ; i++){
                var list = this.data[i].image
                this.$refs[`upload${i}`][0].handleGive(list)
            }
          })
        },
        deep: true
      }
    },
    methods: {
      handleValidate () {
        let flag = true
        this.data.forEach((e, index) => {
          this.$refs[`data${index}`][0].validate(v => {
              if (!v) {
                flag = false
              }
            })
        })
        return flag
      },
      // 数据改变
      selectChange (index) {
        this.selectData.forEach(e => {
          if (e.member_aptitude_real_info_id === this.data[index].member_name_id) {
            this.data[index].member_abbreviation = e.member_abbreviation
            this.data[index].member_name = e.member_name
          }
        })
      },
      // 查询下拉列表数据0c40c317d011483fb8ad4d5b2f61a424
      handleSelectData () {
        this.$api.post('/member-reversion/user/realCertification/findMemberContactOfByAptitude', {
          user_id: this.account,
          templateId: this.templateId
        }).then(response => {
          if (response.code === 200) {
            this.selectData = response.data
          }
        })
      },
      // 初始化获取数据
      handleInit () {
        this.$api.post('/member-reversion/user/realCertification/findMemberContact', {
          user_id: this.account,
          templateId: this.templateId
        }).then(response => {
          if (response.code === 200) {
            let data = []
            data = response.data
            this.data = data
            this.$nextTick(e => {
              for(var i = 0 ;i < data.length ; i++){
                  var list = this.data[i].image
                  this.$refs[`upload${i}`][0].handleGive(list)
              }
            })
          }
        })
      },
      locationClick (index) {
        this.activeIndex = index
      },
      // 所在位置
      handleGetData ($event, selectedData) {
        let labelArr = []
        let idArr = []
        selectedData.forEach(element => {
            labelArr.push(element.label)
            idArr.push(element.value)
        })
        this.data[this.activeIndex].location = labelArr.join('/')
        this.data[this.activeIndex].locationId = idArr.join('/')
        this.addressChange(this.data[this.activeIndex], this.activeIndex)
      },
      // 地址发生改变 ， 地址预览变化
      addressChange (item, index) {
        item.addressPreview = ''
        if (item.location) {
          item.addressPreview += '所在位置：' + item.location + '、'
        }
        if (item.address ) {
           item.addressPreview += '详细地址：' + item.address + '、'
        }
        if (item.house_number) {
          item.addressPreview += item.house_number + '号、'
        }
        if (item.longitude) {
          item.addressPreview += '东经：' + item.longitude + '、'
        }
        if (item.latitude) {
          item.addressPreview += '北纬：' + item.latitude + '、'
        }
        if (item.addressPreview) {
          item.addressPreview = item.addressPreview.substring(0, item.addressPreview.length - 1) + '。'
        }
      },
      // 点击编辑
      handleEdit (item, index) {
        item.isEdit = true
      },
      // 点击删除
      handleDel (item, index) {
        this.$Modal.confirm({
            title: '是否确定删除',
            content: '是否确认删除？',
            onOk:()=>{
                this.data.splice(index, 1)
                this.$Message.success('删除成功')
            },
            okText:'确定',
            cancelText:'取消'
        });
      },
      // 上传资质照片
      getPictureList ($event, e) {
        var arr = []
        $event.forEach(element => {
              if(element.response){
                arr.push(element.response.data.picName)
            }
        })
        this.data[e].image = arr
        this.$refs[`data${e}`][0].validate()
      },
      // 点击保存
      handleSave (item, index) {
        let url = ''
        if (item.member_contact_info_id) { // 更新
          url = '/member-reversion/user/realCertification/updateMemberContact'
        } else { // 保存
          url = '/member-reversion/user/realCertification/saveMemberContact'
        }
        item.user_id = this.account
        item.templateId = this.templateId
        item.loginStep ={
          id: this.stepId ? this.stepId : 0,
          account: this.$user.loginAccount,
          templateId: this.templateId,
          step: 6
        }
        this.$refs[`data${index}`][0].validate(v => {
          if (v) {
            this.$api.post(url, {data: item}).then(response => {
              if (response.code === 200) {
                item.isEdit = false
                this.$Message.success('保存成功')
                this.handleInit()
              }
            })
          } else {
            this.$Message.error('请核对表单信息')
          }
        })
      },
      // 点击定位获取
      onSelectPoint (index) {
        this.activeIndex = index
				this.$refs.experMap.showMap = true
			},
      // 取坐标
			onGetPoint(point) {
				if(point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
					this.data[this.activeIndex].longitude = point.lng
          this.data[this.activeIndex].latitude = point.lat
				} else {
					this.data[this.activeIndex].longitude = ''
					this.data[this.activeIndex].latitude = ''
				}
			},
      // 点击添加
      handleAdd () {
        let list = {
          user_id: this.account,
          status: true,
          member_name: '',
          member_abbreviation: '',
          contact_name: '',
          card: '',
          seat_phone: '',
          phone: '',
          qq_number: '',
          wechat_number: '',
          email: '',
          website_url: '',
          postal_code: '',
          image: [],
          image_status: true,
          location: '',
          location_status: true,
          address: '',
          addressPreview: '',
          house_number: '',
          address_status: true,
          longitude: '',
          latitude: '',
          qr_code_contact_http: '',
          qr_code_user_http: '',
          isEdit: true,
          member_name_id: '',
          positioning: ''
        }
        this.data.push(list)
      }
    }
  }
</script>
<style>
.certification{
  background-color: #F9F9F9;
}
</style>

