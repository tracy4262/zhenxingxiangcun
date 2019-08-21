<template>
	<div id="custom">
		<div class="mian-bot1" style="margin-bottom: 160px">
			<div class="mian-bot1-title">
				<span>菜单设置</span> &nbsp;
				<div style="border-top:1px solid #ededed;margin-top: 12px;">

					<Checkbox-group v-model="checkAllGroup">
						<ul style="display:inline-block">
							<li>
								<Checkbox label="personalInfo"><span>个人信息</span></Checkbox>
							</li>

							<li>
								<Checkbox label="circle">
									<span>我的圈子</span>
								</Checkbox>
							</li>
							<li>
								<Checkbox label="service">
									<span>我的服务</span>
								</Checkbox>
							</li>
							<li>
								<Checkbox label="studio">
									<span>工作室</span>
								</Checkbox>
							</li>
						</ul>

						<ul style="text-align:center;display: inline-block;position: relative;margin: auto 200px;">
							<li>
								<Checkbox label="approve">
									<span>我的认证</span>
								</Checkbox>
							</li>
							<li>
								<Checkbox label="contactInfo">
									<span>联系方式</span>
								</Checkbox>
							</li>
							<li style="margin-left: 12px">
								<Checkbox label="repository">
									<span>我的知识库</span>
								</Checkbox>
							</li>
							<li>
								<Checkbox label="verification">
									<span>资质审核</span>
								</Checkbox>
							</li>

						</ul>
						<ul style="text-align: right;display: inline-block;">
							<li>
								<Checkbox label="photo">
									<span>我的相册</span>
								</Checkbox>
							</li>
							<li>
								<Checkbox label="product">
									<span>我的商品</span>
								</Checkbox>
							</li>
							<li>
								<Checkbox label="policy">
									<span>我的政策</span>
								</Checkbox>
							</li>
							<li>
								<Checkbox label="certificate">
									<span>荣誉证书</span>
								</Checkbox>
							</li>
						</ul>
					</Checkbox-group>

				</div>

			</div>
		</div>
		<br/>
		<div class="mian-bot1" style="margin-bottom: 160px">
			<div class="mian-bot1-title">
				<span>隐私设置</span> &nbsp;
				<div style="border-top:1px solid #ededed;margin-top: 12px;">
					<div style="height: 20px"></div>
					<Checkbox-group v-model="privacry" @on-change="privacryChange">
						<Row class="check-box" :gutter="86">
							<Col span="24">
							<ul>
								<li style="margin-bottom: 24px">个人信息
									<div style="float: right">
										<Button-group size="small">
											<Button>所有人</Button>
											<Button type="primary">仅好友</Button>
											<Button>仅自己</Button>
										</Button-group>
									</div>
								</li>
								<li style="margin-bottom: 24px">我的认证
									<div style="float: right">
										<Button-group size="small">
											<Button>所有人</Button>
											<Button type="primary">仅好友</Button>
											<Button>仅自己</Button>
										</Button-group>
									</div>
								</li>

								<li style="margin-bottom: 24px">我的圈子
									<div style="float: right">
										<Button-group size="small">
											<Button>所有人</Button>
											<Button type="primary">仅好友</Button>
											<Button>仅自己</Button>
										</Button-group>
									</div>
								</li>
								<li style="margin-bottom: 24px">联系方式
									<div style="float: right">
										<Select v-model="model10" multiple style="width:108px">
											<Option v-for="item in cityList" :value="item.value" :key="item">{{ item.label }}</Option>
										</Select>
									</div>
								</li>

								<li style="margin-bottom: 24px">我的商品
									<div style="float: right">
										<Select v-model="model10" multiple style="width:108px">
											<Option v-for="item in cityList" :value="item.value" :key="item">{{ item.label }}</Option>
										</Select>
									</div>
								</li>
							</ul>
							</Col>

						</Row>

					</Checkbox-group>

				</div>
				<div style="margin-top: 12px;">
					<div style="height: 20px"></div>
					<Row class="check-box" :gutter="86">
						<Col span="12">
						<ul>
							<li style="margin-bottom: 24px">个人信息
								<div style="float: right">
									<i-switch v-model="switch1"></i-switch>
								</div>
							</li>
							<li style="margin-bottom: 24px">我的认证
								<div style="float: right">
									<i-switch v-model="switch2"></i-switch>
								</div>
							</li>

							<li style="margin-bottom: 24px">我的相册
								<div style="float: right">
									<i-switch v-model="switch3"></i-switch>
								</div>
							</li>
							<li style="margin-bottom: 24px">我的圈子
								<div style="float: right">
									<i-switch v-model="switch4"></i-switch>
								</div>
							</li>
							<li style="margin-bottom: 24px">联系方式
								<div style="float: right">
									<i-switch v-model="switch5"></i-switch>
								</div>
							</li>

							<li style="margin-bottom: 24px">我的商品
								<div style="float: right">
									<i-switch v-model="switch6"></i-switch>
								</div>
							</li>

						</ul>
						</Col>
						<Col span="12">
						<ul>
							<li style="margin-bottom: 24px">我的服务
								<div style="float: right">
									<i-switch v-model="switch7"></i-switch>
								</div>
							</li>
							<li style="margin-bottom: 24px">我的政策
								<div style="float: right">
									<i-switch v-model="switch8"></i-switch>
								</div>
							</li>

							<li style="margin-bottom: 24px">我的知识库
								<div style="float: right">
									<i-switch v-model="switch9"></i-switch>
								</div>
							</li>
							<li style="margin-bottom: 24px">我的工作室
								<div style="float: right">
									<i-switch v-model="switch10"></i-switch>
								</div>
							</li>
							<li style="margin-bottom: 24px">资质审核
								<div style="float: right">
									<i-switch v-model="switch11"></i-switch>
								</div>
							</li>

							<li style="margin-bottom: 24px">荣誉证书
								<div style="float: right">
									<i-switch v-model="switch12"></i-switch>
								</div>
							</li>

						</ul>
						</Col>
					</Row>

				</div>
				<div style="width: 150px;margin: 36px auto">
					<Button type="primary" shape="circle" size="large" style="width: 150px;height: 50px;" @click="saveAllChange">保存</Button>
				</div>

			</div>
		</div>

	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				checkAllGroup: [],
				privacry: [],
				switch1: '',
				switch2: '',
				switch11: '',
				switch3: '',
				switch4: '',
				switch5: '',
				switch6: '',
				switch7: '',
				switch8: '',
				switch9: '',
				switch10: '',
				switch12: ''
			}
		},
		methods: {
			checkAllGroupChange() {
				axios({
					method: 'post',
					url: this.$url.api + '/login/selfConf',
					data: {
						self: JSON.stringify(this.checkAllGroup).toString()
					}
				})
			},
			privacryChange() {

			},
			change() {
				axios({
					method: 'post',
					url: this.$url.api + '/login/selfConf',
					data: {
						/*personalInfo:this.switch1,
						approve:,
						circle:,
						photo:,
						contactInfo:,
						product:,
						repository:,
						service:,
						policy:,
						studio:,
						verification:this.switch11,
						certificate:this.switch12,*/

					}
				})
			},
			saveAllChange() {
				console.log("sw1=" + this.switch1 + "sw2=" + this.switch2)

			}

		}
	}
</script>

<style>
	.check-box {
		margin: 50px auto;
	}
</style>