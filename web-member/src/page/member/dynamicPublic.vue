<template>
	<div>
		<Button type="default" @click="publish" size="small" slot="extra">发布</Button>
										<Modal v-model="publishShow" width="600" :closable="false">
											<div style="text-align:center">
												<Form ref="mydynamic" :model="mydynamic" :rules="ruleInline" label-position="right" :label-width="100">
													<Form-item label="标题" prop="title">
														<Input type="text" v-model="mydynamic.title" placeholder="标题">
														</Input>
													</Form-item>
													<Form-item label="分类" prop="type">
														<Input type="text" v-model="mydynamic.type" placeholder="分类" label-position="right" :label-width="100">
														</Input>
													</Form-item>
													<Form-item label="信息来源" prop="source" label-position="right" :label-width="100" style="text-align: left;">
														<Radio-group v-model="mydynamic.source">
															<Radio label="原创">
															</Radio>
															<Radio label="转载">
															</Radio>
														</Radio-group>
													</Form-item>
													<Form-item label="作者" prop="author" label-position="right" :label-width="100">
														<Input type="text" v-model="mydynamic.author" placeholder="作者">
														</Input>
													</Form-item>
													<Form-item label="关联物种" prop="species" label-position="right" :label-width="100">
														<Input type="text" v-model="mydynamic.species" placeholder="关联物种">
														</Input>
													</Form-item>
													<Form-item label="通用商品名" prop="goodsname" label-position="right" :label-width="100">
														<Input type="text" v-model="mydynamic.goodsname" placeholder="通用商品名">
														</Input>
													</Form-item>
													<Form-item label="通用服务名" prop="servicename" label-position="right" :label-width="100">
														<Input type="text" v-model="mydynamic.servicename" placeholder="通用服务名">
														</Input>
													</Form-item>
													<Form-item label="行业分类" prop="industry" label-position="right" :label-width="100">
														<Input type="text" v-model="mydynamic.industry" placeholder="行业分类">
														</Input>
													</Form-item>
													<Form-item label="地区" prop="district" label-position="right" :label-width="100">
														<Cascader :data="cityList" change-on-select v-model="mydynamic.district"></Cascader>
													</Form-item>
													<Form-item label="栏目类别" prop="category" label-position="right" :label-width="100">
														<Input type="text" v-model="mydynamic.category" placeholder="栏目类别">
														</Input>
													</Form-item>
													<Form-item label="摘要" prop="summary" label-position="right" :label-width="100">
														<Input type="text" v-model="mydynamic.summary" placeholder="摘要">
														</Input>
													</Form-item>
													<Form-item label="详细内容" prop="content">
														<ueditor></ueditor>
													</Form-item>
													<Form-item label="好友提醒" prop="tixing" label-position="right" :label-width="100">
														<Input type="textarea" v-model="mydynamic.tixing" placeholder="好友提醒">
														</Input>
													</Form-item>
													<Form-item label="提醒内容" prop="con" label-position="right" :label-width="100">
														<Input type="textarea" v-model="mydynamic.con" placeholder="提醒内容">
														</Input>
													</Form-item>
													<Form-item label="关键字" prop="label" label-position="right" :label-width="100">
														<Input type="text" v-model="mydynamic.label" placeholder="关键字">
														</Input>
													</Form-item>
													<Form-item label="文稿类型" prop="leixin" label-position="right" :label-width="100" style="text-align: left;">
														<Radio-group v-model="mydynamic.leixin">
															<Radio label="文章">
															</Radio>
															<Radio label="图册">
															</Radio>
															<Radio label="音频">
															</Radio>
															<Radio label="视频">
															</Radio>
															<Radio label="短信">
															</Radio>
															<Radio label="微博">
															</Radio>
														</Radio-group>
													</Form-item>
													<Form-item label="发送短信" prop="sms" label-position="right" :label-width="100" style="text-align: left;">
														<Radio-group v-model="mydynamic.sms">
															<Radio label="是">
															</Radio>
															<Radio label="否">
															</Radio>
														</Radio-group>
													</Form-item>
													<Form-item label="提交首页显示" prop="xianshi" label-position="right" :label-width="100" style="text-align: left;">
														<Radio-group v-model="mydynamic.xianshi">
															<Radio label="是">
															</Radio>
															<Radio label="否">
															</Radio>
														</Radio-group>
													</Form-item>
													<Form-item label="公开范围" prop="security" label-position="right" :label-width="100" style="text-align: left;">
														<Radio-group v-model="mydynamic.security">
															<Radio label="所有人可见">
															</Radio>
															<Radio label="仅好友可见">
															</Radio>
															<Radio label="仅自己可见">
															</Radio>
															<Radio label="仅指定组可见">
															</Radio>
														</Radio-group>
													</Form-item>
												</Form>
											</div>
											<div slot="footer" style="text-algin:center">
												<div style="text-algin:center">
													<Button type="primary" shape="circle" @click="publishSubmit()" style="width:100px">确定</Button>
												</div>

											</div>
										</Modal>
	</div>
</template>

<script>
</script>

<style>
</style>