<template>
  <div>
     <Modal v-model="show" 
     width="850" 
     heigth="700px"
     :title="title" 
     :mask-closable="false" 
     :styles="{top: '20px'}">
        <div class="pt20" style="min-height: 500px;">
          <!-- <Form 
            ref="data" 
            :rules="dataLine"
            :model="data"
            :label-width="100">
            <FormItem label="联系类型：" prop="tag">
              <Input v-model="data.tag" style="width:200px;" :maxlength="20"></Input>
            </FormItem>
            <FormItem label="联系人：" prop="concat">
              <Input v-model="data.concat" style="width:200px;" :maxlength="10"></Input>
            </FormItem>
            <FormItem label="电话：" prop="phone">
              <Input v-model="data.phone" style="width:200px;"></Input>
            </FormItem>
            <FormItem label="邮编：" prop="postCode">
              <Input v-model="data.postCode" style="width:200px;"></Input>
            </FormItem>
              <Row>
                <Col span="12">
                  <FormItem label="地址：" prop="location">
                        <vui-cascander :values="data.location" @handle-get-result="handleGetData"></vui-cascander>
                  </FormItem>
                </Col>
                <Col span="10" offset="1">
                  <Input v-model="data.address" style="width:200px;" placeholder="详细地址" :maxlength="50"></Input>
                </Col>
              </Row>
              <Row>
                <Col span="12">
                  <FormItem label="坐标：" prop="coordinate">
                    <Input v-model="data.coordinate" readonly></Input>
                  </FormItem>
                </Col>
                <Col span="10" offset="1">
                  <Button type="text" @click="onSelectPoint">获取坐标</Button>
                </Col>
              </Row>
              <div v-if="data.coordinate" class="pl30 pr30 pb30">
                <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${data.latitude},${data.longitude}&title=我的位置&content=${data.latitude},${data.longitude}&output=html`">
                  <img :src="`//api.map.baidu.com/staticimage?height=200&center=${data.longitude},${data.latitude}&zoom=15&scale=2&markers=${data.longitude},${data.latitude}`" alt="" width="100%" height="260px">
                </a>
              </div>
            <view-panel
                title="添加自定义字段"
                :data="data.safeFormData"
                @on-data="handleGetSafeForm"
                @on-add="handleAddBtn"></view-panel>
          </Form>  -->
          <div>
            <Button class="mr20" @click="onChoose">从基础设置中选择</Button>
            <Button  @click="add">添加</Button>
          </div>
          
          <concatCard ref="concatCard" :datas="data" :bgGrey="false"></concatCard>
        </div>
        <!-- 添加面板 -->
        <!-- <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel> -->
        <div slot="footer"class="tr">
          <Button @click="refresh" shape="circle" style="width:100px" class="mr20">取消</Button>
          <Button  type="primary" @click="onSave" shape="circle" style="width:100px"  class="mr20">确定</Button>
        </div>
     <!-- <vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map> -->
    </Modal>
      <Modal v-model="show1" 
        width="900" 
        heigth="700px"
        title="选择联系人信息"
        :mask-closable="false" 
        :styles="{top: '20px'}">
        <div>
              <Table :columns="columns1" :data="tableData" class="mt20"></Table>
        </div>
        <div slot="footer">
          <Button @click="show1 = false">取消</Button>
          <Button type="primary" @click="onOk">确定</Button>
        </div>
      </Modal>
  </div>
</template>
<script>
import vuiCascander from '~components/vuiCascader/index'
import {isPostCode, isPhone2} from '~utils/validate'
import vuiMap from '../../member/components/productionMap'
import concatCard from '../../goFishing/components/concatCard'
import addPanel from './add-panel'
import viewPanel from './view-panel'
  export default {
    components: {
      vuiCascander,
      vuiMap,
      addPanel,
      viewPanel,
      concatCard
    },
    data () {
      return {
        show: false,
        show1: false,
        data: [],
        dataLine:{
          // concat: [
          //   {required: true, message: '请填联系人', trigger: 'blur' }
          // ],
          phone: [
            { required: true, message: '请填写电话', trigger: 'blur' },
             {validator: isPhone2}
          ],
          postCode: [
            { required: true, message: '请填写邮编', trigger: 'blur' },
             {validator: isPostCode}
          ],
          location: [
            { required: true, message: '请选择地址', trigger: 'change' }
          ],
          coordinate: [
            { required: true, message: '请选择坐标', trigger: 'change' }
          ]
        },
        title: '',
        tableData: [],
        selectData: [],
        columns1: [
          {
              width: 80,
              title: ' ',
              render: (h, params) =>{
                return h('Radio', {
                  props: {
                    value: params.row.checked
                  },
                  on: {
                    'on-change': (val) => {
                      params.row.checked = true
                      this.selectData = [params.row]
                      this.tableData.forEach((item, index) => {
                        if (item.checked) {
                          item.checked = false
                          this.tableData.splice(index, 1, item)
                        }
                      })
                      this.tableData.splice(params.index, 1, params.row)
                    }
                  }
                })
              }
          },{
            title: '联系人姓名',
            width: 120,
            key: 'contact_name'
          },{
            title: '身份证号码',
            width: 180,
            key: 'card'
          },{
            title: '座机电话',
            key: 'seat_phone'
          },{
            title: '手机',
            key: 'phone'
          },{
            title: '邮箱',
            width: 200,
            key: 'email'
          }
        ],
      }
    },
    created() {
      this.$api.post('/member-reversion/realStep/findEnableStep', {
                account: this.$user.loginAccount
            }).then(response => {
            if (response.code === 200) {
                if (response.data) {
                    this.templateId = response.data.templateId
                    this.handleInit()
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    methods: {
      // 初始化获取数据
      handleInit () {
        this.$api.post('/member-reversion/user/realCertification/findMemberContact', {
          user_id: this.$user.loginAccount,
          templateId: this.templateId
        }).then(response => {
          if (response.code === 200) {
            this.tableData = response.data
            this.tableData.forEach(e => {
              e.checked = false
                if ('detailAddress' in e) {
                  e.detailAddress = ''
                } else {
                  e.detailAddress = ''
                  if (e.location) {
                    e.detailAddress += '所在位置：' + e.location + '、'
                  }
                  if (e.address ) {
                    e.detailAddress += '详细地址：' + e.address + '、'
                  }
                  if (e.house_number) {
                    e.detailAddress += e.house_number + '号、'
                  }
                  if (e.detailAddress) {
                    e.detailAddress = e.detailAddress.substring(0, e.detailAddress.length - 1) + '。'
                  }
                }
            })
          }
        })
      },
      // 选择 后保存
      onOk () {
        if (this.selectData) {
          this.show1 = false
          this.data = this.selectData
        } else {
          this.$Message.warning('请选择联系人')
        }
      },
      // 点击选择
      onChoose () {
        this.show1 = true
        this.selectData = []
      },
      // 点击添加
      add () {
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
        this.data = [list]
      },
      init (item) {
        if (item) { // 编辑
          item.safeFormData[0].id = item.id
          let list = Object.assign({}, item.safeFormData[0])
          this.data = [list]
          console.log(list)
        } else {
          this.data = []
        }
        this.tableData.forEach(e => {
          e.checked = false
        })
        this.show = true
      },
      // 点击取消
      refresh () {
        this.show = false
      },
      // 点击确定
      onSave () {
        let flag = this.$refs['concatCard'].handleValidate()
        if (flag) {
          let list = {
            account: this.$user.loginAccount,
            safeFormData: this.data,
            id: this.data[0].id ? this.data[0].id : null
          }
          this.$api.post('/member/columnSettings/saveContact', list).then(response => {
              if (response.code === 200) {
                this.$Message.success('保存成功！')
                this.show = false
                this.$emit('on-init')
              }
            })
        } else {
          this.$Message.error('请核对表单信息！')
        }
        // this.$refs['data'].validate(flag => {
        //   if (flag) {
        //     // this.data.account = this.$user.loginAccount
        //     // this.data.id ? '' : this.data.id = null;
        //     // this.$api.post('/member/columnSettings/saveContact', this.data).then(response => {
        //     //   if (response.code === 200) {
        //     //     this.$Message.success('保存成功！')
        //     //     this.show = false
        //     //     this.$emit('on-init')
        //     //   }
        //     // })
        //   } else {
        //     this.$Message.error('请核对表单信息！')
        //   }
        // })
      }
    }
  }
</script>