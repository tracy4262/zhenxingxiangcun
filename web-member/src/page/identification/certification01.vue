<template>
	<div>
		<p style="text-align: center;margin-top: 30px;font-size: 14px;line-height: 1.8">
			您的身份信息仅用于实名认证，我们不会将此信息用于其它任何地方<br/>
				实名认证后才能使用会员中心各项功能，请慎重填写</p>
		<div class="ivu-certification mt20">
			<Row>
				<Col span="10" offset="7">
				<Form ref="certification" :model="certification" :rules="ruleInline" :label-width="12">
					<Form-item prop="name" label=" ">
						<Input v-model="certification.name" placeholder="姓名" :maxlength="nameLength"  size="large" @on-change="userNameChange"></Input>
					</Form-item>
					<Form-item prop="idcard" label=" ">
						<Input v-model="certification.idcard" placeholder="身份证" :maxlength="idcardLength"  size="large" @on-change="userNameChange"></Input>
					</Form-item>
					<Form-item prop="phone" label=" ">
						<Input v-model="certification.phone" placeholder="手机号" :maxlength="phoneLength" size="large" @on-change="userNameChange"></Input>
					</Form-item>
					<Form-item prop="code" label=" ">
						<Input v-model="certification.code" :maxlength="codeLength">
							<Button type="primary" :disabled="sendDisabled"  slot="append" @click.native="code">
								<vui-countdown
								title="秒重新发送"
								v-model="sendTime"
								:start="sendStrat"
								v-show="sendShow"
								@finish="handleSendFinish"
								/>
							<template v-show="!sendShow">{{sendLabel}}</template>
							</Button>
						</Input>
					</Form-item>
					<Form-item prop="city" label=" " class="city">
						<vui-cascander :values="certification.cityName" @handle-get-result="handleGetData"></vui-cascander>
					</Form-item>
				</Form>
				</Col>
			</Row>
			<div class="mb20 tc">
				<i-button type="primary" @click="regest" class="zhuce-btn1" >确认提交</i-button>
				<i-button @click="pass" class="zhuce-btn1">以后再说</i-button>
			</div>
		</div>

	</div>
</template>
<script>
import api from '~api'
import $ from 'jquery'
import vuiCountdown from '~components/countdown'
import vuiCascander from '~components/vuiCascader/index'

export default {
	components:{
		vuiCountdown,
		vuiCascander
	},
	data() {
		// 自定义用户名验证checkCity
		const checkCode = (rule, value, callback) => {
			var uPattern = /^\d{6}$/
			if (!uPattern.test(value)) {
				return callback(new Error('请填写验证码'));
			}else {
				callback()
			}
		}
		const checkCity = (rule, value, callback) => {
			if (value.length) {				
				callback()
			}else {
				return callback(new Error('请填写选择地区'));
			}
		}
		return {
			nameLength:6,
			idcardLength:18,
			phoneLength:11,
			codeLength:6,
			cityList:[],
			certification: {
				name: '',
				idcard: '',
				code: '',
				phone: '',
				city: [],
				cityName: ''
			},
			sendShow: false,
            sendTime: 60,
            sendStrat: false,
            sendLabel: '发送验证码',
            sendDisabled: false,
            addres:'',
            ruleInline: {
                name: [
                    {
                        required: true,
                        message: "请填写姓名",
                        trigger: "blur"
                    },
                    {
                        pattern: /^[\u4e00-\u9fa5]{2,6}/,
                        min: 2,
                        max: 6,
                        message: "2-6个中文字符，不能含有特殊字符、字母数字。",
                        trigger: "blur"
                    }
                ],
                idcard: [
                    {
                        required: true,
                        message: "请填写身份证号码",
                        trigger: "blur"
                    },
                    {
                        pattern: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/ || /^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$/,
                        message: "请填写正确的身份证号码",
                        trigger: "blur"
                    }
                ],
                phone: [
                    {
                        required: true,
                        message: "请填写手机号码",
                        trigger: "blur"
                    },
                    {
                        pattern: /^1(3|4|5|6|7|8|9)\d{9}$/,
                        message: "请填写正确的手机号码",
                        trigger: "blur"
                    }
                ],
				code:[
					{required: true, validator: checkCode,message:'请填写验证码'}
				]				
            }
		}
	},
	watch: {
    certification: {
      handler(curVal, oldVal) {
        var user = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')));
        user.displayName = curVal.name;
        sessionStorage.setItem("user", JSON.stringify(user));
      },
      deep: true
    }
  },
	created: function() {
	    //回显
	    api.get('/member/Certification/find').then(response => {
			const flag = response.code
			console.log('cer',response)
			if(flag == 200) {
				if(response.data){
					this.certification.idcard = response.data.idCard
					this.certification.phone=response.data.mobile
					if(response.data.realname){
						this.certification.name = response.data.realname
					}
					if (response.data.city) {
						this.certification.cityName = response.data.city
					}
					this.$store.commit('showUserName', this.certification)
					console.log('this.certification.cityName', this.certification.cityName)
				}
			}
		})
	},
	methods: {
		pass(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.gotoPathSec(3)
			}else{
				this.$parent.$parent.gotoPath(3)
			}
		},
		regest() {
			this.$refs['certification'].validate((valid) => {
				if (valid) {
					let data = {
						name: this.certification.name,
						idcard: this.certification.idcard,
						code: this.certification.code,
						phone: this.certification.phone,
						city: this.certification.cityName,
						locationId: '',
						step: this.$route.path
					}
					console.log('data', data)
					api.post('/member/Certification/insert', data).then(response => {
						console.log(response.data)
						if(0 == response.data) {
							this.$Message.error('提交失败')
						}else{
							if(9 == response.data) {
								this.$Message.error('验证码错误')
							} else {
								this.$Message.success('提交成功!')
								this.pass()

							}
						}
					})
				}
			})
		},
		userNameChange() {
			this.$store.commit('showUserName', this.certification)
		},
		//验证码发送
		code() {
			var reg1 = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
			var reg2 = /^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$/
			var modle = /^1[3|4|5|7|8][0-9]{9}$/ //验证规则
			if (!this.certification.name) {
				this.$Message.error('请填写姓名')
			}else if(!this.certification.idcard) {
				this.$Message.error('请输入身份证号码')
			}else if (!(reg1.test(this.certification.idcard) || reg2.test(this.certification.idcard))) {
				this.$Message.error('请输入正确的身份证号码')
			}else if (!this.certification.phone) {
				this.$Message.error('请输入您的手机号')
			}else if (!modle.test(this.certification.phone)) {
				this.$Message.error('手机号填写错误')
			}else{
				this.sendStrat = true
	            this.sendShow = true
	            this.sendLabel = ''
	            this.sendDisabled = true
				api.post(
					'/member/Certification/code',
					{
						phone: this.certification.phone,
					}
				).then(response => {
					console.log(response.data)
					if (0 == response.data) {
						this.$Message.error('手机号填写错误')
					} else {
						this.last_time=60
						this.isDisable=true
						clearInterval(this.clock)
						this.clock = setInterval(this.refresh,1000)
						this.$Message.success('短信发送成功!')

					}
				})
			}
		},
        handleSendFinish () {
            this.sendStrat = false
            this.sendShow = false
            this.sendLabel = '重新发送'
            this.sendDisabled = false
            this.sendTime = 60
		},
		handleGetData (value, selectedData) {
			console.log('value', value, 'selectedData', selectedData)
			let labelArr = []
			selectedData.forEach(element => {
				labelArr.push(element.label)
			})
			this.certification.cityName = labelArr.join('/')
		}
	}
}
</script>
<style>
	.ivu-certification .ivu-form-item-error-tip{
		position: relative;
	}
</style>