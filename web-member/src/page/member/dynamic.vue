<template>
	<div id="dynamic">
		<Row>
			<Col span="24">
			<img src="../../../img/timg.jpg" width="50" height="100" />
			<p>李云霞</p>
			<p>发表于：<i>6天前</i></p>
			</Col>
			<Col :span="6">后备母猪配种时机</Col>
			<Col :span="6" :offset="12">浏览：997 评论：18</Col>
			<Col :span="6">技术文献库</Col>
		</Row>
		<Row>
			<Col span="24">
			<img src="../../../img/timg.jpg" width="50" height="100" />
			<p>李云霞</p>
			<p>发表于：<i>6天前</i></p>
			</Col>
			<Col :span="6">后备母猪配种时机</Col>
			<Col :span="6" :offset="12">浏览：997 评论：18</Col>
			<Col :span="6">技术文献库</Col>
		</Row>
		<Row>
			<Col span="24">
			<img src="../../../img/timg.jpg" width="50" height="100" />
			<p>李云霞</p>
			<p>发表于：<i>6天前</i></p>
			</Col>
			<Col :span="6">后备母猪配种时机</Col>
			<Col :span="6" :offset="12">浏览：997 评论：18</Col>
			<Col :span="6">技术文献库</Col>
		</Row>
		<Row>
			<Col span="24">
			<img src="../../../img/timg.jpg" width="50" height="100" />
			<p>李云霞</p>
			<p>发表于：<i>6天前</i></p>
			</Col>
			<Col :span="6">后备母猪配种时机</Col>
			<Col :span="6" :offset="12">浏览：997 评论：18</Col>
			<Col :span="6">技术文献库</Col>
		</Row>

	</div>
</template>

<script>
	export default {
		data() {
			return {}
		}

	}
</script>

<style>
	#dynamic img {
		float: left;
		width: 50px;
		height: 100px
	}

	#dynamic .ivu-row {
		background-color: white;
		margin-bottom: 20px;
	}
</style>