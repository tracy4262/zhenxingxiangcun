<template>
	<!--3设置栏目第一步开始-->
	<div>
		<div style="width: 302px;margin-left: 304px;margin-top: 60px"><img src="../../img/zhuce-img01.png" alt=""></div>
		<div class="zuce1-text" style="text-align: center;margin-bottom: 30px">
			<h2 style="font-size: 22px;line-height: 76px">个性化</h2>
			<p style="font-size: 14px">个性化将帮助您设置常用的关键词<br/> 这些关键词有助于过滤出最感兴趣的信息，还能协助您建立交易关系
			</p>
		</div>
		<div class="footer-btn">
			<Button type="primary" class="zhuce-btn1" @click="pass">继续</Button>
		</div>
	</div>
	<!--3设置栏目第一步结束-->
</template>
<script>
export default {
	methods: {
		pass(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.gotoPathSec(6)
			}else{
				this.$parent.$parent.gotoPath(6)
			}
		}
	}
}
</script>