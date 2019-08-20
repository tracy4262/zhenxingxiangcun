<template>
	<!--1实名认证第一步开始-->
	<div style="display: block">
		<div style="width: 302px;margin-left: 304px;margin-top: 60px"><img src="../../img/zhuce-img01.png" alt=""></div>
		<div class="zuce1-text" style="text-align: center;margin-bottom: 30px">
			<h2 style="font-size: 22px;line-height: 76px">网站设置</h2>
			<p style="font-size: 14px">申请店铺完全免费，一个身份只能开一家店，开店后店铺无法注销<br/> 申请店铺到正式开通需要1-3个工作日
			</p>
		</div>
		<div class="footer-btn">
			<Button type="primary"  class="zhuce-btn1" @click="next">继续</Button>
		</div>
	</div>
	<!--1实名认证第一步结束-->
</template>
<script>
	export default {
		methods:{
			next(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.gotoPathSec(35)
			}else{
				this.$parent.$parent.gotoPath(35)
			}
		},
		}
	}
</script>