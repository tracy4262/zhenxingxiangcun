<template>
	<!--3实名认证第十二步开始-->
	<div>
		<p style="text-align: center;margin-bottom: 20px;font-size: 18px;">设 置 支 付 密 码</p>
		<div style="margin: 10px auto">
			<div style="text-align: center">
				<Input v-model="account.pwd" placeholder="请输入支付密码" style="width: 260px;height: 45px;margin-top:6px;" size="large"  type="password" :maxlength="codeLength"></Input>
				<span class="zhushi">*</span>
			</div>
			<div style="text-align: center">
				<Input v-model="account.secpwd" placeholder="请再次输入支付密码" style="width: 260px;height: 45px;margin-top: 6px" size="large"  type="password" :maxlength="codeLength"></Input>
				<span class="zhushi">*</span>
			</div>
		</div>
		<div  class="footer-btn" >
				<i-button type="primary" @click="preStep" size="large">上一步</i-button>
				<i-button type="primary" @click="setPwd" size="large">下一步</i-button>
				<span class="tiaoguo" @click="pass">跳过</span>
		</div>
	</div>
	<!--3实名认证第十二步结束-->
</template>
<script>
export default {
	data() {
		return {
			codeLength:8,
			account: {
	            pwd: '',
	            secpwd: '',
			}
		}
	},
	created: function() {
		this.$parent.baifen = 100
	},
	methods: {
		preStep(){
				this.$parent.$parent.$router.go(-1)
			},
		pass(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.$parent.gotoPathSec(19)
			}else{
				this.$parent.$parent.$parent.gotoPath(19)
			}
		},
		setPwd() {
			if(this.account.pwd ==='' || this.account.secpwd ==='') {
				this.$Message.error('密码不能为空！')
			} else if(this.account.pwd != this.account.secpwd) {
				this.$Message.error('两次输入密码不一样，请重新输入！')
			} else {
				this.$api.post(
					'/member/bank/password', {
						pwd: this.account.pwd,
						secpwd: this.account.secpwd,
						step: this.$route.path
					}
				).then(response => {
					console.log(response.data);
					if(3 == response.data) {
						this.$Message.error('两次输入的密码不一样，请重新输入！');
					} else if(0 == response.data) {
						this.$Message.error('设置失败！');
					} else {
						this.$Message.success('设置成功!');
						this.pass()
	
					}
				})
			}
		},
	}
}
</script>