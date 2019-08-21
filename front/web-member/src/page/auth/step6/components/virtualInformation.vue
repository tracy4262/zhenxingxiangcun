<template>
  <div class="pd30 mt40" style="background: #F9F9F9;">
    <Form :label-width="80" label-position="left" ref="data" :model="data" :rules="dataLine">
      <Row :gutter="16">
        <Col span="13">
            <FormItem label="会员账号">
              <Input style="width:250px;" readonly v-model="data.user_nswy_id"></Input>
              <!-- <Switch class="ml20"  size="large" v-model="data.user_nswy_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch> -->
            </FormItem>
            <FormItem label="用户名" prop="user_id" >
              <Input style="width:250px;" v-model="data.user_id" readonly></Input>
              <Switch class="ml20"  size="large" v-model="data.user_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch>
            </FormItem>
            <!-- 简称不是昵称，用户自己输入 -->
            <FormItem label="简称"  prop="user_abbreviation">
              <Input style="width:250px;" v-model="data.user_abbreviation"></Input>
              <Switch class="ml20"  size="large" v-model="data.user_abbreviation_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch>
            </FormItem>
            <FormItem label="备注名称" prop="user_name_remark">
              <Input style="width:250px;" v-model="data.user_name_remark"></Input>
              <Switch class="ml20"  size="large" v-model="data.user_name_remark_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch>
            </FormItem>
            <FormItem label="密码" prop="pass_word">
              <Input style="width:250px;" type="password" readonly v-model="data.pass_word"></Input>
              <!-- <Switch class="ml20"  size="large" v-model="data.pass_word_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch> -->
            </FormItem>
            <FormItem label="座机电话" prop="seat_phone">
              <Input style="width:250px;" v-model="data.seat_phone"></Input>
              <Switch class="ml20"  size="large" v-model="data.seat_phone_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch>
            </FormItem>
            <FormItem label="手机" prop="phone">
              <Input style="width:250px;" v-model="data.phone"></Input>
              <Switch class="ml20"  size="large" v-model="data.phone_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch>
            </FormItem>
            <FormItem label="QQ号码" prop="qq_number">
              <Input style="width:250px;" v-model="data.qq_number"></Input>
              <Switch class="ml20"  size="large" v-model="data.qq_number_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch>
            </FormItem>
            <FormItem label="微信" prop="wechat_number">
              <Input style="width:250px;" v-model="data.wechat_number"></Input>
              <Switch class="ml20"  size="large" v-model="data.wechat_number_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch>
            </FormItem>
            <FormItem label="邮箱" prop="email">
              <Input style="width:250px;" v-model="data.email"></Input>
              <Switch class="ml20"  size="large" v-model="data.email_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch>
            </FormItem>
            <FormItem label="网站地址" prop="website_url">
              <Input style="width:250px;" v-model="data.website_url"></Input>
              <Switch class="ml20"  size="large" v-model="data.website_url_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch>
            </FormItem>
            <FormItem label="所在地区">
              <div  style="width: 250px; display: inline-block;">
                <vui-cascander :values="data.location" @handle-get-result="handleGetData"></vui-cascander>
              </div>
              <Switch class="ml20"  size="large" v-model="data.location_status">
                  <span slot="open">显示</span>
                  <span slot="close">隐藏</span>
                </Switch>
            </FormItem>
        </Col>
        <Col span="11">
            <FormItem label="图像">
                <vui-upload
                    ref="upload"
                    @on-getPictureList="getPictureList"
                    :total="1"
                    :multiple="false"
                    :hint="'支持拓展名称：png jpg'"
                    :size="[80,80]"
                    :cover="true"
                    ></vui-upload>
            </FormItem>
        </Col>
      </Row>
      <div class="pt30">
        <Button type="primary" ghost @click="onQrCodeChange">更新虚拟信息二维码</Button>
        <div class="pt30">
            <vue-qr-code :value="qrCodeUrl" :options="{ size: 120}"></vue-qr-code>
        </div>
        <div class="tc pd20">
          <Button type="primary" @click="handleSave">保存</Button>
        </div>
      </div>
    </Form>
  </div>
</template>
<script>
import {isTelephone, isPhone2, isQQ, isWeChat2, isEmail2, isUrl} from '~utils/validate'
import vuiCascander from '~components/vuiCascader/index'
import vuiUpload from '~components/vui-upload'
import VueQrCode from '@xkeshi/vue-qrcode'
  export default {
    components: {
      vuiUpload,
      vuiCascander,
      VueQrCode
    },
    data () {
      // 自定义用户名验证
      const userName = (rule, value, callback) => {
          var uPattern = /^[a-zA-Z0-9]{4,16}$/
          if (!uPattern.test(value)) {
              return callback(new Error('用户名为4-16个字符组成，可使用字母、数字'));
          }else {
              callback()
          }
      }
      const password = (rule, value, callback) => {
          var uPattern = /^[a-zA-Z0-9]{6,16}$/
          if (!uPattern.test(value)) {
              return callback(new Error('登录密码为6-16个字符组成，可使用字母、数字'));
          }else {
              callback()
          }
      }
      const checkName = (rule, value, callback) => {
            if(value){
              this.$api.post('/member/login/is_exists',{username:this.data.user_id}).then(response=>{
                  if(response.code == 500){
                      return callback(new Error('用户名已存在'));
                  }else{
                      return callback();
                  }
              })
          }
      }
      return {
        data:{
            user_nswy_id:'',
            user_id:'',
            user_status: true,
            user_abbreviation:'',
            user_abbreviation_status: true,
            user_name_remark:'',
            user_name_remark_status: true,
            pass_word:'',
            // website_url_status: true,
            seat_phone:'',
            seat_phone_status: true,
            phone:'',
            phone_status: true,
            qq_number:'',
            qq_number_status: true,
            wechat_number:'',
            wechat_number_status: true,
            email:'',
            email_status: true,
            website_url:'',
            website_url_status: true,
            location:'',
            location_status: true,
            image:''
        },
        dataLine: {
          user_id: [{
              // required: true,
              message: '请填写用户名',
              trigger: 'blur'
          },{
              min:4,
              message:'用户名为4-16个字符组成，可使用字母、数字'
          },{
              validator: userName,
          },{
            //   validator: checkName,
            //   trigger: 'blur'
          }],
        //   pass_word: [{
        //       // required: true,
        //       message: '请输入密码',
        //       trigger: 'blur'
        //   }, {
        //       type: 'string',
        //       min: 6,
        //       message: '登录密码为6-16个字符组成，区分大小写',
        //       trigger: 'blur'
        //   },{
        //       validator: password,
        //       message:'登录密码为6-16个字符组成，区分大小写'
        //   }],
          seat_phone: [{
              validator: isTelephone,
          }],
          phone: [{
              validator: isPhone2,
          }],
          qq_number: [{
              validator: isQQ,
          }],
          wechat_number: [{
              validator: isWeChat2,
          }],
          email:[{
              validator: isEmail2,
          }],
          website_url:[{
              validator: isUrl,
          }]
        },
        qrCodeUrl: '',
        account:''
      }
    },
    created() {
        this.account = this.$user.loginAccount
        this.onQrCodeChange()
        this.handleInit()
        console.log('useruseruseruseruser', this.$user)
    },
    methods: {
        // 初始化
        handleInit () {
            this.$api.post('/member-reversion/realCertification/findList',{user_id: this.account}).then(response => {
                console.log(response)
                if (response.code === 200) {
                    this.data = Object.assign(this.data, response.data)
                    if (this.data.image) {
                        this.$refs['upload'].handleGive(this.data.image)
                    }
                    console.log(this.data)
                }
            })
        },
        // 保存
        handleSave () {
            console.log(this.$refs['data'])
            let v = this
            this.$refs['data'].validate(e => {
                if (e) {
                    this.$api.post('/member-reversion/realCertification/saveReal', {data: this.data}).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('保存成功')
                        } 
                    })
                }
            })
        },
        // 更换二维码
        onQrCodeChange () {
            this.qrCodeUrl = `http://192.168.7.42:8010/nswy-member-info?user_id=${this.account}&t=${Date.parse( new Date())}`
        },
      // 获取图片
      getPictureList (e) {
        if(e[0]){
            this.data.image =  e[0].response.data.picName
        }
      },
      // 获取地址
      handleGetData (value, selectedData) {
         let labelArr = []
        selectedData.forEach(element => {
            labelArr.push(element.label)
        })
        this.data.address = labelArr.join('/')
      }
    }
  }
</script>
<style>

</style>
