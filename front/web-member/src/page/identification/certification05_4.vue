<template>
	<div>
		<div class="ml80 mb20 mt20">
			<Row>
				<Col span="6"><h2>权限</h2></Col>
				<Col span="3">
					<i-switch v-model="info.switch2" size="large" style="margin-left: 183px;">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</Col>
			</Row>
		</div>
		<Form ref="info" label-position="right" :label-width="200" :model="info" :rules="rule">
			<FormItem label="农事无忧ID">
				<Input v-model="info.id" style="width:200px;margin-left: 40px;" readonly />
			</FormItem>
			<FormItem label="QQ号码">
				<Input v-model="info.qq" :maxlength="12" style="width:200px;margin-left: 40px;" />
			</FormItem>
			<FormItem label="邮箱" prop="email">
				<Input v-model="info.email" style="width:200px;margin-left: 40px;" />
			</FormItem>
			<FormItem label="申请域名">
				<Input v-model="info.domain" :maxlength="20" style="width:200px;margin-left: 40px;" />
			</FormItem>
		</Form>
		<div style="text-align: center;">
			<h2>实时预览</h2>
			<Input v-model="con.content" type="textarea" :rows="4" style="width: 90%;" />
		</div>
		<div class="footer-btn" v-if="base">
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="saveInfo" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>
		<div class="footer-btn" v-if="!base">
			<i-button type="primary" @click="saveInfo" size="large">确定</i-button>
		</div>
	</div>
</template>
<script>
	import api from "~api"
	export default {
		props:{
			base:{
				type:Boolean,
				default:true
			}
		},
		data() {
			return {
				transfer: true,
				info: {
					switch2: true,
					id: "NSWY" + this.moment(new Date()).format('YYYYMMDDHHmmss') + Math.floor(Math.random()*(99-10+1)+10),
					qq: "",
					mobile: "",
					email: "",
					domain:''
				},
				info1: {
					id: "NSWY" + this.moment(new Date()).format('YYYYMMDDHHmmss') + Math.floor(Math.random()*(99-10+1)+10),
					qq: "",
					email: "",
					domain:''
				},
				con: {
					content: "", //保存所有数据
					content1: "" //保存公开可见的数据
				},

				rule: {
					email: [{
						type: 'email',
						message: '邮箱格式不正确！',
						trigger: 'blur'
					}]
				},
			};
		},
		created: function() {
			this.showContact()
		},
		watch: {
			info: {
				handler(curVal, oldVal) {
					this.change();
				},
				deep: true
			},
			info1: {
				handler(curVal, oldVal) {
					this.change1();
				},
				deep: true
			}
		},
		methods: {
			preStep() {
                let type=this.$route.meta.type
                if(1 === type){
                    this.$parent.$parent.$parent.$router.push('/pro/member/progress23/progress25')
                }else{
                    this.$parent.$parent.$parent.$router.push('/pro/member/step23/step25')
                }
			},
			pass() {
				let type = this.$route.meta.type
				if(1 === type) {
					this.$parent.$parent.$parent.gotoPathSec(27)
				} else {
					this.$parent.$parent.$parent.gotoPath(27)
				}
			},
			saveInfo () {
				var _this = this
				this.$refs['info'].validate((valid) => {
                    if (valid) {
						api.post('/member/userFullInfo/insertPhone', {
							qq: this.info.qq,
							id: this.info.id,
							domain: this.info.domain,
							email: this.info.email,
							status2: this.info.switch2,
							step: this.base ? this.$route.path : ''
						}).then(response => {
							const flag = response.code
							console.log("flag", response)
							if(flag === 200) {
								this.saveAll()
								console.log("flag", response)
							} else {
								_this.$Message.error('提交失败！')
							}
						})
                    }
                })
			},
			saveAll() {
				api.post(
					'/member/userFullInfo/insert', {
						content: this.con.content,
						con: this.con.content1,
					}
				).then(response => {
					const flag = response.code
					console.log("basic", response)
					if(flag == 200) {
						this.$Message.success('提交成功！')
						if (this.base) {
							this.pass() // 重启认证
						} else {
							this.$emit('success') // 我的资料
						}
					} else {
						this.$Message.error('提交失败！')
					}
				})
			},

			change() {
				this.con.content = ''
				if(this.info.id) {
					this.con.content += '农事无忧ID：' + this.info.id
					if(this.info.switch2) {
						this.info1.id = '农事无忧ID：' + this.info.id
					} else {
						this.info1.id = ''
					}
				}
				if(this.info.qq) {
					this.con.content += '，QQ号码：' + this.info.qq
					if(this.info.switch2) {
						this.info1.qq = '，QQ号码：' + this.info.qq
					} else {
						this.info1.qq = ''
					}
				}
				if(this.info.email) {
					this.con.content += '，邮箱：' + this.info.email
					if(this.info.switch2) {
						this.info1.email = '，邮箱：' + this.info.email
					} else {
						this.info1.email = ''
					}
				}
				if(this.info.domain) {
					this.con.content += '，域名：' + this.info.domain
					if(this.info.switch2) {
						this.info1.domain = '，域名：' + this.info.domain
					} else {
						this.info1.domain = ''
					}
				}
			},
			change1() {
				this.con.content1 = ''
				if(this.info1.id) {
					this.con.content1 += this.info1.id
				}
				if(this.info1.qq) {
					this.con.content1 += this.info1.qq
				}
				if(this.info1.email) {
					this.con.content1 += this.info1.email
				}
				if(this.info1.addr) {
					this.con.content1 += this.info1.addr
				}
				if(this.info1.domain) {
					this.con.content1 += this.info1.domain
				}
			},
			//联系信息回显
			showContact() {
				api.get('/member/userFullInfo/findContact')
					.then(response => {
						if(response.data.nswyId) {
							console.log("信息展示2", response.data);
							if(response.data.status2 === 0) {
								this.info.switch2 = false
							} else {
								this.info.switch2 = true
							}
							var res = response.data
							this.info.qq = res.qq
							this.info.domain = res.domain
							this.info.id = res.nswyId
							// this.info.zuoji = res.mobile
							this.info.email = res.email
							// this.info.postalcode = res.postalcode
						}

					})
			}
		}
	};
</script>
<style>

</style>