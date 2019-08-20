<template>
	<div>
		<!--5完善信息第一步开始-->
		<div>
			<div style="width: 302px;margin-left: 304px;margin-top: 60px"><img src="../../img/zhuce-img01.png" alt=""></div>
			<div class="zuce1-text" style="text-align: center;margin-bottom: 30px">
				<h2 style="font-size: 22px;line-height: 76px">完善信息</h2>
				<p style="font-size: 14px">检测到您还没有完善用户信息，为更好的使用系统，请您完善个人信息<br/> 您可以通过下面的链接，前往个人资料页面
				</p>
			</div>
		</div>
		<div class="footer-btn">
			<Button type="primary"  class="zhuce-btn1" @click="next">继续</Button>
		</div>
	</div>
</template>
<script>
export default {
	methods: {
		next(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.gotoPathSec(23)
			}else{
				this.$parent.$parent.gotoPath(23)
			}
		}
	}
}
</script>