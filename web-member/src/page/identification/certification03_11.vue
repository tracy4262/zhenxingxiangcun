<template>
	<!--3实名认证第十一步开始-->
	<div>
		<p style="text-align: center;margin-bottom: 20px;font-size: 18px;">填 加 银 行 卡</p>
		<Row>
			<Col span="8" offset="8" class="mb10">
				<Form ref="account" :model="account" :rules="ruleInline" class="tc" :label-width="10">
					<FormItem prop="bank" label=" ">
						<Input v-model="account.bank" style="width:255px;" placeholder="银行卡号(只支持储蓄卡)"  @on-change="change" :maxlength="bankLength"></Input>
					</FormItem>
					<FormItem prop="phone" label=" ">
						<Input v-model="account.phone" style="width:255px;" placeholder="银行预留手机号"   @on-change="change" :maxlength="phoneLength"></Input>
					</FormItem>
					<FormItem prop="code" label=" ">
						<Input v-model="account.code" style="width:255px;">
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
					</FormItem>
				</Form>		
			</Col>
		</Row>
		<div  class="footer-btn" >
				<i-button type="primary" @click="preStep" size="large">上一步</i-button>
				<i-button type="primary" @click="setAccount" size="large">下一步</i-button>
				<span class="tiaoguo" @click="pass">跳过</span>
		</div>
	</div>
	<!--3实名认证第十一步结束-->
</template>
<script>
import api from '~api'
import $ from 'jquery'
import vuiCountdown from '~components/countdown'

export default {
	components:{
		vuiCountdown
	},
	data() {
		let checkModle = (rules, value, cb) => {
			this.$store.dispatch("checkModle", { rules, value, cb }); // 验证手机号
		};
		let checkBankID = (rules, value, cb) => {
			this.$store.dispatch("checkBankID", { rules, value, cb }); // 验证银行卡号
		};
		return {
			bankLength:19,
			phoneLength:11,
			codeLength:6,
			account: {
	            phone: '',
	            bank: '',
	            code:''
			},
			ruleInline:{
				phone: [{ required: true, validator: checkModle, trigger: 'blur' }],
	            bank: [{ required: true, validator: checkBankID, trigger: 'blur' }],
	            code:[{ required: true, message: '请输入验证码', trigger: 'blur' }]
			},
			sendShow: false,
            sendTime: 60,
            sendStrat: false,
            sendLabel: '发送验证码',
            sendDisabled: false
		}
	},
	created: function() {
		this.$parent.baifen = 100
		this.$api.get('/member/bank/find')
			.then(res=>{
				console.log('账号',res)
				if(res.data){
					this.account.bank=res.data.bankaccount
					this.account.phone=res.data.phone
					this.change()
				}
		})
	},
	methods: {
		//下一步
		setAccount() {
			this.$refs['account'].validate((valid) => {
				if (valid) {
					this.$api.post(
						'/member/bank/insert', {
							phone: this.account.phone,
							bank: this.account.bank,
							code: this.account.code,
							step: this.$route.path
						}
					).then(response => {
						console.log(response.data);
						if(0 == response.data) {
							this.$Message.error('提交失败');
						} else if(9 == response.data) {
							this.$Message.error('验证码不正确');
						} else {
							this.$Message.success('提交成功!');
							this.pass()
						}
					})
				}
			})
			// let modle = /^1[3|4|5|7|8][0-9]{9}$/ //验证规则
			// if(!this.account.bank) {
			// 	this.$Message.error('请填写卡号')
			// } else
			// if(!this.account.phone) {
			// 	this.$Message.error('请输入手机号码')
			// } else if(!modle.test(this.account.phone)){
			// 	this.$Message.error('请填写正确的手机号码！')
			// }else{
			// 	this.$api.post(
			// 		'/member/bank/insert', {
			// 			phone: this.account.phone,
			// 			bank: this.account.bank,
			// 			code: this.account.code,
			// 			step: this.$route.path
			// 		}
			// 	).then(response => {
			// 		console.log(response.data);
			// 		if(0 == response.data) {
			// 			this.$Message.error('提交失败');
			// 		} else if(9 == response.data) {
			// 			this.$Message.error('验证码不正确');
			// 		} else {
			// 			this.$Message.success('提交成功!');
			// 			this.pass()
			// 		}
			// 	})
			// }
			
		},
		preStep(){
				this.$parent.$parent.$router.go(-1)
			},
		pass(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.$parent.gotoPathSec(18)
			}else{
				this.$parent.$parent.$parent.gotoPath(18)
			}
		},
		 handleSendFinish () {
            this.sendStrat = false
            this.sendShow = false
            this.sendLabel = '重新发送'
            this.sendDisabled = false
            this.sendTime = 60
        },
		change() {
			this.$store.commit('saveAccount', this.account)
		},
		//倒计时
		refresh(){
			if(this.last_time>1){
				this.last_time--
				$("#authcode").html("<span>"+this.last_time+"</span>")
			}else{
				this.isDisable=false
				$("#authcode").html("<span>发送</span>")
			}
		},
		//验证码发送
		code() {
			var modle = /^1[3|4|5|7|8][0-9]{9}$/ //验证规则
			if (!this.account.bank) {
				this.$Message.error('请填写卡号')
			}else if (!this.account.phone) {
				this.$Message.error('请输入手机号码')
			}else if (!this.account.phone) {
				this.$Message.error('请输入您的手机号')
			}else if (!modle.test(this.account.phone)) {
				this.$Message.error('请输入正确的手机号码')
			}else{
				this.sendStrat = true
	            this.sendShow = true
	            this.sendLabel = ''
	            this.sendDisabled = true
				api.post(
					'/member/Certification/code',
					{
						phone: this.account.phone,
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
				}).catch(function(error) {
				})
			}
		}
	}
}
</script>