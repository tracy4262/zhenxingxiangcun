<template>
  <div class="new-login">
      <Form ref="registerData" :model="registerData" :rules="ruleInline1">
        <Form-item prop="username" class="mb30">
            <i-input type="text" size="large" v-model.trim="registerData.username" :readonly="registerData.readonly" @on-focus="registerData.readonly = false"  placeholder="请输入用户名" :maxlength='numn'>
                <span slot="prepend" class="pr10 pl10" style="padding-right:36px">
                    <Icon type="ios-person-outline" class="pr10" size="18"></Icon>账号
                </span>
            </i-input>
        </Form-item>
        <Form-item prop="name" class="mb30">
            <i-input type="text" id="pwd" size="large" v-model.trim="registerData.name" placeholder="请输入昵称" :maxlength='nump'>
                <span slot="prepend" class="pr10 pl10" style="padding-right:36px">
                    <Icon type="ios-person-outline" class="pr10" size="18"></Icon>昵称
                </span>
            </i-input>
        </Form-item>
        <Form-item prop="password" class="mb30">
            <i-input :type="typep" id="pwd" size="large" icon="ios-eye-outline" @on-click="showpwd('typep')" v-model.trim="registerData.password" class="password" placeholder="请输入密码" :maxlength='nump'>
                <span slot="prepend" class="pr10 pl10" style="padding-right:36px">
                    <Icon type="ios-lock-outline" class="pr10" size="18"></Icon>密码
                </span>
            </i-input>
        </Form-item>
        <Form-item prop="password1" class="mb30">
            <i-input :type="typep2" size="large" icon="ios-eye-outline" @on-click="showpwd('typep2')" v-model.trim="registerData.password1"class="password" placeholder="请确认密码" :maxlength='nump' @keyup.enter.native="handleSubmit">
                <span slot="prepend" class="pr10 pl10">
                    <Icon type="ios-lock-outline" class="pr10" size="18"></Icon>确认密码
                </span>
            </i-input>
        </Form-item>
        <Form-item prop="city" class="mb30 loaction-vui-cascander">
            <i-input :type="typep2" size="large">
                <span slot="prepend" class="pr10 pl10">
                    <Icon type="ios-pin-outline" class="pr10" size="18"></Icon>所在地区
                </span>
            </i-input>
                <div class="vui-cascander">
                    <vui-cascander :vlue="registerData.city" @handle-get-result="handleGetData" :size="'large'">
                    </vui-cascander>
                </div>
        </Form-item>
        <Form-item prop="isSuccess">
            <div id="TencentCaptcha2" data-appid="2028910108" data-cbfn="callback"  v-if="!registerData.isSuccess">
              <!-- <Button type="default"  long>点我进行图片验证</Button> -->
              <i-input v-model="pictureText" size="large" readonly></i-input>
            </div>
            <div v-if="registerData.isSuccess">
              <Button type="default"  size="large" long icon="ios-checkmark">验证成功</Button>
            </div>
        </Form-item>
        <Form-item class="mb30">
            <Checkbox v-model="isAgree"> 我已阅读并同意相关</Checkbox>
             <span class="t-green" style="text-decoration: underline;cursor: pointer;" @click="readItem">《服务条款》</span>
        </Form-item>
        <Form-item style="box-shadow: 0 5px 18px 0 rgba(86,176,125,0.46);" v-if="isAgree">
            <Button type="primary" size="large" @click="handleSubmit" long>注册</Button>
        </Form-item>
        <Form-item v-if="!isAgree">
            <Button type="primary" size="large" long disabled>注册</Button>
        </Form-item>
      </Form>
  </div>
</template>
<script>
import vuiCascander from '~components/vuiCascader/index'
  export default {
    components: {
      vuiCascander
    },
    props: {
      active: {
        type:String,
        default: '注册'
      }
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
      const checkedPassword = (rule, value, callback) => {
          if(this.registerData.password) {
            if (this.registerData.password !== this.registerData.password1) {
              return callback(new Error('两次输入的密码不一致'))
            }else {
                callback()
            }
          }else {
              callback()
          }
      }
      const checkName = (rule, value, callback) => {
            if(value){
              this.$api.post('/member/login/is_exists',{username:this.registerData.username}).then(response=>{
                  if(response.code == 500){
                      return callback(new Error('用户名已存在'));
                  }else{
                      return callback();
                  }
              })
          }
      }
      return {
        registerData: {
          username: '',
          password: '',
          password1: '',
          name: '',
          city: '',
          isSuccess: ''
        },
        pictureText: '点我进行图片验证',
        isSuccess: false,
        typep:'password',//密码类型,
        typep2:'password',
        nump:16,//密码长度
        numn:16,//名字长度
        isAgree: true, //同意相关条款
        ruleInline1: {
                username: [{
                    required: true,
                    message: '请填写用户名',
                    trigger: 'blur'
                },{
                    min:4,
                    message:'用户名为4-16个字符组成，可使用字母、数字'
                },{
                    validator: userName,
                },{
                    validator: checkName,
                    trigger: 'blur'
                }],
                // name: [{
                //     required: true,
                //     message: '请输入昵称',
                //     trigger: 'blur'
                // }],
                city: [{
                    required: true,
                    message: '请选择所在地区',
                    trigger: 'change'
                }],
                isSuccess: [{
                    required: true,
                    type: 'string',
                    message: '请进行图片验证',
                }],
                password: [{
                    required: true,
                    message: '请输入密码',
                    trigger: 'blur'
                }, {
                    type: 'string',
                    min: 6,
                    message: '登录密码为6-16个字符组成，区分大小写',
                    trigger: 'blur'
                },{
                    validator: password,
                    message:'登录密码为6-16个字符组成，区分大小写'
                }],
                password1: [{
                    required: true,
                    message: '请再次输入密码',
                    trigger: 'blur'
                }, {
                    type: 'string',
                    min: 6,
                    message: '登录密码为6-16个字符组成，区分大小写',
                    trigger: 'blur'
                },{
                    validator: password,
                    message:'登录密码为6-16个字符组成，区分大小写'
                },{
                    validator: checkedPassword,
                    trigger: 'blur'
                }]

            },
      }
    },
    created() {
      let that = this
      window.callback = function(res){
        // that.isSuccess = false
        // res（未通过验证）= {ret: 1, ticket: null}
        // res（验证成功） = {ret: 0, ticket: "String", randstr: "String"}
        if(res.ret === 0){
          that.registerData.isSuccess = 'true'
        }
        if(res.ret === 1){
          that.registerData.isSuccess = ''
        }
      }
    },
    mounted() {
      new TencentCaptcha(document.getElementById('TencentCaptcha2'));
    },
    methods: {
      // 获取结果
      handleResult (e) {
        this.isSuccess = e
      },
      // 注册
      handleSubmit () {
        // if (!this.registerData.isSuccess) {
        //   this.$Message.error('请点击图片验证按钮')
        //   return
        // }
        this.registerData.pwd = this.registerData.password1
        this.$refs['registerData'].validate((valid) => {
          if (valid) {
            this.$api.post('/member/login/insert',this.registerData).then(response => {
              if (response.code === 500) {
                  this.registerData.isSuccess = ''
                  this.$Message.error('用户名已存在！')
              } else if (response.code === 200) {
                  this.$Message.success('注册成功!')
                  this.$emit('on-success', response)
              }
            })
          }
        })
      },
      // 点击阅读条款
      readItem () {
        this.$emit('on-read')
      },
      //, 点击 小眼睛密码可见
      showpwd (e) {
        if (e === 'typep') {
          if (this.typep === 'password') {
            this.typep = 'text'
          } else {
            this.typep = 'password'
          }
        }
        if (e === 'typep2') {
          if (this.typep2 === 'password') {
            this.typep2 = 'text'
          } else {
            this.typep2 = 'password'
          }
        }
      },
      //地区
      handleGetData (value, selectedData) {
          let labelArr = []
          selectedData.forEach(element => {
              labelArr.push(element.label)
          })
          this.registerData.city = labelArr.join('/')
      },
    }
  }
</script>

<style lang="scss">
// 登录部分
.new-login{
    .ivu-input-group-append, .ivu-input-group-prepend{
        background-color: #fff;
    }
    .loaction-vui-cascander {
      position: relative;
      .vui-cascander{
        position: absolute;
        width: 217px;
        top: 1px;
        right: 0px;
        z-index: 9;
        .ivu-input{
          border-radius: 0px 4px 4px 0px;
        }
      }
    }
    // 显示密码
    // .password{
    //     .ivu-icon-ios-eye-outline{
    //         display: none;
    //     }
    //     &:hover{
    //         .ivu-icon-ios-eye-outline{
    //             display: inline-block;
    //         }
    //     }
    // }
}

</style>