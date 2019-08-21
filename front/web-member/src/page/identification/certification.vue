<template>
	<!--1实名认证第一步开始-->
	<div style="display: block">
		<div style="width: 302px;margin-left: 304px;margin-top: 60px"><img src="../../img/zhuce-img01.png" alt=""></div>
		<div class="zuce1-text" style="text-align: center;margin-bottom: 30px">
			<h2 style="font-size: 22px;line-height: 76px">实名认证</h2>
			<p style="font-size: 14px">您的帐号未进行实名注册，为营造健康的信息服务环境，提高服务精准度，邀请<br/>您 使用有效身份证进行实名注册
			</p>
		</div>
		<div class="footer-btn">
			<Button type="primary"  class="zhuce-btn1" @click="pass">继续</Button>
		</div>
	</div>
	<!--1实名认证第一步结束-->
</template>
<script>
export default {
	methods: {
		pass(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.gotoPathSec(2)
			}else{
				this.$parent.$parent.gotoPath(2)
			}
		}
	}
}
</script>
