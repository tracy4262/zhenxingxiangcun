<template>
	<div style="text-align: center;">
			<Row class="mt50">
			<Col span="8"  :key='index' class="mt20" v-for='(t,index) in templatePerson' v-if="t.type===type && govTemp != '村级'">
				<img :src="t.src" :class="{'intro': t.checked}" width="100px" height="100px" @click="getName(t.name,t.type)"/>
				<h3 class="template-name"><span>{{t.name}}</span></h3>
			</Col>
			<Col span='8'  :key='index' class="mt20" v-for='(t,index) in templateOrgans' v-if="type===3 && govTemp === '村级' || type===5">
				<img :src="t.src" :class="{'intro': t.checked}" width="100px" height="100px" @click="getName(t.name,t.type)"/>
				<h3 class="template-name"><span>{{t.name}}</span></h3>
			</Col>
			</Row>
			<div class="footer-btn" >
				<i-button type="primary" @click="preStep" size="large">上一步</i-button>
				<i-button type="primary" @click="saveWebsite" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>
	</div>
</template>
<script>
	import $ from 'jquery'
	import api from '~src/api'
	
	export default {
		data() {
			return {
				type:'',
				current:0,
				temp:'个人模板',
				checked: false,
				status:false,
				result:'个人模板',
				govTemp:'',
				templatePerson:[
					{
						src:'http://www.ns51.cn:8999/00/3E/wKgHPVpnJmWAVJnzAABF3huDu6A351.png',
						name:'个人模板',
						checked: false,
						type:0
					},
					{
						src:'http://www.ns51.cn:8999/00/3E/wKgHPVpnJl-AKnZNAABMck87kHE045.png',
						name:'企业模板',
						checked: false,
						type:1
					},
					{
						src:'http://www.ns51.cn:8999/00/40/wKgHPVpoQgyAYzi3AABN3nFWl1E198.png',
						name:'专家模板',
						checked: false,
						type:4
					},
					{
						src:'http://www.ns51.cn:8999/00/3E/wKgHPVpnJmKAXmLVAAA-ysLVAJM761.png',
						name:'机关模板',
						checked: false,
						type:3
					},
				],
				templateOrgans:[
					{
						src:'http://www.ns51.cn:8999/00/3E/wKgHPVpnJmKAXmLVAAA-ysLVAJM761.png',
						name:'机关模板',
						checked: false,
						type:3
					},
					{
						src:'http://www.ns51.cn:8999/00/3E/wKgHPVpnJluAXKyZAABEyzWmeHw811.png',
						name:'乡村模板',
						checked: false,
						type:5
					},
				],
				website:{
					name:'',
					status:'',
					position:'',
					logo:'',
					banner:'',
					summary:'',//简介
					introduce:'',//介绍
					template:'',
					modular:'',
					type:0
				},
			}
		},
		watch: {
			website: {
				handler(curVal, oldVal) {
					console.log("website",this.website)
				},
				deep: true
			}

		},
		created() {
			this.$parent.current = 1
			var account=JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
			api.post('/member/login/findbyname/' + account).then(response => {
				console.log("account",response.data);
				this.type = response.data.userType
			})
			api.get('/member/govInfo/findByAccount/'+ account).then(res=>{
				console.log("user",res)
				if(res.data){
					this.govTemp = res.data.govLevel
				}
			})
			// 回显选中模版
			api.get('/member/website/find/' + account).then(response=>{
				if (response.code === 200) {
					if (response.data && response.data.modular) {
						this.website.modular = response.data.modular
					}
					if (this.type === 3 || this.type === 5) {
						this.templateOrgans.forEach(element => {
							if (response.data && element.name === response.data.template) {
								element.checked = true
								this.website.type=element.type
							}
						})
					} else {
						this.templatePerson.forEach(element => {
							if (response.data && element.name === response.data.template) {
								element.checked = true
							}
						})
					}	
				}
			})
		},
		methods: {
			preStep(){
				//this.$parent.$parent.$parent.$router.go(-1)
                let type=this.$route.meta.type
                if(1 === type){
                    this.$parent.$parent.$parent.$router.push('/pro/member/progress35/progress36')
                }else{
                    this.$parent.$parent.$parent.$router.push('/pro/member/step35/step36')
                }
			},
			pass(){
				let type=this.$route.meta.type
				if(1 === type){
					this.$parent.$parent.$parent.gotoPathSec(38)
				}else{
					this.$parent.$parent.$parent.gotoPath(38)
				}
			},
			//网站设置
			saveWebsite () {
				this.$api.post('/member/website/insert', {
					name: this.website.name,
					position: this.website.position,
					logo: this.website.logo,
					status: this.website.status,
					banner: this.website.banner,
					summary: this.website.summary,
					introduce: this.website.introduce,
					template: this.website.template,
					step: this.$route.path,
					type:this.website.type,
					modular: this.website.modular
				}).then(response => {
					if (response.code === 200) {
						this.$Message.success('设置成功!')
						this.pass()
					} else {						
						this.$Message.error('设置失败！')
					}
				})
			},
			getName(e,type){
				var obj = event.target
				$(obj).toggleClass("intro");
				var cla = $(obj).attr("class")
				if (cla === 'intro') {
					this.website.template = e
					this.website.type = type
				} else {
					this.website.template = this.temp
				}
			}
		}
	}
</script>
<style scoped>
	.intro{
		border:2px solid #00c587;
	}
	.template-name {
		line-height: 40px;
		font-size: 12px;
	}
</style>