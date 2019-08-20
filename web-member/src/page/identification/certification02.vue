<template>
	<!--2设置栏目第一步开始-->
	<div>
		<div style="width: 302px;margin-left: 304px;margin-top: 60px"><img src="../../img/zhuce-img01.png" alt=""></div>
		<div class="zuce1-text" style="text-align: center;margin-bottom: 30px">
			<h2 style="font-size: 22px;line-height: 76px">设置栏目</h2>
			<p style="font-size: 14px">设置栏目将帮助您建立栏目选单<br/> 自由设置和发布栏目专属信息，权限过滤将确保这些信息能被经过您允许的人员查看
			</p>
		</div>
		<div class="footer-btn">
			<Button type="primary" class="zhuce-btn1" @click="next">继续</Button>
		</div>
	</div>
	<!--2设置栏目第一步结束-->
</template>

<script>
export default {
	methods: {
		next(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.gotoPathSec(4)
			}else{
				this.$parent.$parent.gotoPath(4)
			}
		}
	}
}
</script>
