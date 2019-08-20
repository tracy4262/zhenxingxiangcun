<template>
	<!--3设置栏目第一步开始-->
	<div>
		<div style="width: 302px;margin-left: 304px;margin-top: 60px"><img src="../../img/zhuce-img01.png" alt=""></div>
		<div class="zuce1-text" style="text-align: center;margin-bottom: 30px">
			<h2 style="font-size: 22px;line-height: 76px">应用设置</h2>
			<p style="font-size: 14px">你还没有添加应用<br/> 应用中心作为平台的一种功能扩展将为您提供众多管理工具和模块</br>
				您可以根据自己的需求，选择添加相应的模块
			</p>
		</div>
		<div class="footer-btn">
			<Button type="primary"  class="zhuce-btn1" @click="next">继续</Button>
		</div>
	</div>
	<!--4设置栏目第一步结束-->
</template>
<script>
export default {
	methods: {
		next(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.gotoPathSec(20)
			}else{
				this.$parent.$parent.gotoPath(20)
			}
		}
	}
}
</script>