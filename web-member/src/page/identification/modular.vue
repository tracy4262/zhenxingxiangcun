<template>
	<div class='tc modular_wid'>
		<!--
        	作者：786905664@qq.com
        	时间：2017-12-07
        	描述：企业
        -->
		<Row class='tc mt50' v-if="type===1">
			<Col span='4' v-for='(item, index) in modularEnterprise' :key='index' class="mt20" >
				<div class="mod" :class="{'intro': item.checked}" :style="index === 0 ? 'cursor: not-allowed;' : 'cursor:pointer;'" @click="change(item)">
					{{item.name}}
				</div>
			</Col>
			<!--<Col span='4' class="mt20">
			<div class="mod" @click="selfSet">
				<span>自定义</span>
			</div>
			</Col>-->
		</Row>
		<!--
        	作者：786905664@qq.com
        	时间：2017-12-07
        	描述：个人
        -->
		<Row class='tc mt50' v-if="type===0">
			<Col span='4' v-for='(item, index) in modular' :key='index' class="mt20" >
				<div class="mod" :class="{'intro': item.checked}" :style="index === 0 ? 'cursor: not-allowed;' : 'cursor:pointer;'" @click="change(item)">
					{{item.name}}
				</div>
			</Col>
			<!-- <Col span='4' class="mt20">
			<div class="mod" @click="selfSet">
				<span>自定义</span>
			</div>
			</Col> -->
		</Row>
		<!--
        	作者：786905664@qq.com
        	时间：2017-12-07
        	描述：机关
        -->
		<Row class='tc mt50' v-if="type === 3">
			<Col span='4' v-for='(item, index) in modularOrgans' :key='index' class="mt20" >
				<div class="mod" :class="{'intro': item.checked}" :style="index === 0 ? 'cursor: not-allowed;' : 'cursor:pointer;'" @click="change(item)">
					{{item.name}}
				</div>
			</Col>
			<!-- <Col span='4' class="mt20">
			<div class="mod" @click="selfSet">
				<span>自定义</span>
			</div>
			</Col> -->
		</Row>
		<!--
        	作者：786905664@qq.com
        	时间：2017-12-07
        	描述：乡村
        -->
		<Row class='tc mt50' v-if="type === 5">
			<Col span='4' v-for='(item, index) in modularCountry' :key='index' class="mt20" >
				<div class="mod" :class="{'intro': item.checked}" :style="index === 0 ? 'cursor: not-allowed;' : 'cursor:pointer;'" @click="change(item)">
					{{item.name}}
				</div>
			</Col>
			<!-- <Col span='4' class="mt20">
			<div class="mod" @click="selfSet">
				<span>自定义</span>
			</div>
			</Col> -->
		</Row>
		<!--
        	作者：786905664@qq.com
        	时间：2017-12-07
        	描述：专家
        -->
		<Row class='tc mt50' v-if="type===4">
			<Col span='4' v-for='(item, index) in modularExpert' :key='index' class="mt20" >
				<div class="mod" :class="{'intro': item.checked}" :style="index === 0 ? 'cursor: not-allowed;' : 'cursor:pointer;'" @click="change(item)">
					{{item.name}}
				</div>
			</Col>
			<!-- <Col span='4' class="mt20">
			<div class="mod" @click="selfSet">
				<span>自定义</span>
			</div>
			</Col> -->
		</Row>
		<!-- 自定义导航 -->
		<Modal :mask-closable="false" v-model="modal" title="自定义导航" @on-ok="saveCustomNav" @on-cancel="cancel">
			<Form :label-width="100">
				<FormItem label="导航项目">
					<CheckboxGroup v-model="defaultNav">
						<Row v-if="type===0">
							<Col span="6" v-for="(m,index) in modular" :key="index">
								<Checkbox :label="m.name" :disabled="m.name === '首页'"></Checkbox>
							</Col>
						</Row>
						<Row v-if="type===1">
							<Col span="6" v-for="(m,index) in modularEnterprise" :key="index" >
								<Checkbox :label="m.name" :disabled="m.name === '首页'"></Checkbox>
							</Col>
						</Row>
						<Row v-if="type===4">
							<Col span="6" v-for="(m,index) in modularExpert" :key="index">
								<Checkbox :label="m.name" :disabled="m.name === '首页'"></Checkbox>
							</Col>
						</Row>
						<Row v-if="type===3">
							<Col span="6" v-for="(m,index) in modularOrgans" :key="index">
								<Checkbox :label="m.name" :disabled="m.name === '首页'"></Checkbox>
							</Col>
						</Row>
						<Row v-if="type===5">
							<Col span="6" v-for="(m,index) in modularCountry" :key="index">
								<Checkbox :label="m.name" :disabled="m.name === '首页'"></Checkbox>
							</Col>
						</Row>
					</CheckboxGroup>
				</FormItem>
				<!--<FormItem label="自定义"  class="mb5" v-show="addColumn">
					<Row>
						<Col span="16">
							<Input v-model="columnName" placeholder="请输入栏目名称" class="pl10 pr10 " :disabled="lumnsize>11" style="width: 160px;"/>
						</Col>
						<Col span="5" offset="1">
							<Button type="primary" @click.native="save">保存</Button>
						</Col>
					</Row>
				</FormItem>
				<FormItem label="">
					<Button type="default" @click.native="addColumn=!addColumn">添加自定义</Button>
				</FormItem>-->
			</Form>
		</Modal>
		<div  class="footer-btn" >
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="saveWebsite" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>	
	</div>
</template>
<script>
	import $ from'jquery'
	import api from '~src/api'
	export default {
		data() {
			return {
				type:0,
				current: 0,
				modal: false,
				defaultNav: [],
				addColumn:false,
				disabled:false,
				columnName:'',
				lumnsize:0,
				website:{ 
					name:'',
					status:'',
					position:'',
					logo:'',
					banner:'',
					summary:'',//简介
					introduce:'',//介绍
					template:'',
					modular:['首页'],
					type:0
				},
				//个人
				modular: [
					{name:'首页',checked:true},
					{name:'个人介绍',checked:false},
					{name:'动态',checked:false},
					{name:'政策',checked:false},
					{name:'知识',checked:false},
					{name:'标准',checked:false},
                    {name:'专家团队',checked:false}
					//{name:'产品',checked:false}
					//{name:'服务',checked:false}
				]	,
				//企业
				modularEnterprise: [
					{name:'首页',checked:true},
					{name:'企业介绍',checked:false},
					{name:'企业动态',checked:false},
					{name:'政策法规',checked:false},
					{name:'企业知识库',checked:false},
					{name:'标准',checked:false},
					{name:'专家团队',checked:false},
					/*{name:'企业产品',checked:false},*/
					/*{name:'企业服务',checked:false},*/
					{name:'联系我们',checked:false}
				],
				//机关
				modularOrgans: [
					{name:'首页',checked:true},
					{name:'基本信息',checked:false},
					{name:'政务动态',checked:false},
					{name:'政策法规',checked:false},
					{name:'共享知识',checked:false},
					{name:'标准',checked:false},
					/*{name:'机关服务',checked:false},*/
					{name:'专家团队',checked:false},
					/*{name:'种养户',checked:false},
					{name:'基地',checked:false},*/
					{name:'联系方式',checked:false}
				],
				//乡村
				modularCountry: [
					{name:'首页',checked:true},
					/*{name:'土特产',checked:false},*/
					{name:'乡村介绍',checked:false},
					/*{name:'供需平台',checked:false},*/
					/*{name:'村务公开',checked:false},*/
					{name:'乡村动态',checked:false},
					{name:'政策法规',checked:false},
					{name:'专业知识库',checked:false},
					{name:'标准',checked:false},
					{name:'专家咨询',checked:false},
					/*{name:'种养户',checked:false},
					{name:'基地',checked:false},*/
					{name:'联系我们',checked:false}
				],
				//专家
				modularExpert: [
					{name:'首页',checked:true},
					{name:'工作室简介',checked:false},
					{name:'动态',checked:false},
					{name:'政策',checked:false},
					{name:'知识',checked:false},
					{name:'标准',checked:false},
                    {name:'专家团队',checked:false}
					/*{name:'产品',checked:false},
					{name:'服务',checked:false}*/
				]	
			}
		},
		watch: {
			defaultNav: {
				handler(curr, old) {
					console.log('curr',curr)
					this.lumnsize=curr.length
					if(this.lumnsize>11){
						this.$Message.error('最多只能添加12个栏目！');
					}
				},
				deep: true
			}
		},
		created() {
			this.$parent.current = 2
			var account=JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
			api.post('/member/login/findbyname/' + account).then(response => {
				this.type = response.data.userType
			})
			//this.init()
			api.get('/member/website/find/'+account).then(res=>{
				console.log('modular',res)
				var arr=[]
				// 如果是第一次进入本页面 默认选中前5个模块
				if (res.data === undefined || res.data.modular === '') {
					if(this.type===0){
						this.modular.forEach((element, index) => {
							if (index >= 0 && index <= 4) {
								element.checked = true
								this.website.modular.push(element.name)
							}
						})
					}else if(this.type===1){
						this.modularEnterprise.forEach((element, index) => {
							if (index >= 0 && index <= 4) {
								element.checked = true
								this.website.modular.push(element.name)
							}
						})
					}else if(this.type===4){
						this.modularExpert.forEach((element, index) => {
							if (index >= 0 && index <= 4) {
								element.checked = true
								this.website.modular.push(element.name)
							}
						})
					}else if(this.type===3){
						this.modularOrgans.forEach((element, index) => {
							if (index >= 0 && index <= 4) {
								element.checked = true
								this.website.modular.push(element.name)
							}
						})
					}else if(this.type===5){
						this.modularCountry.forEach((element, index) => {
							if (index >= 0 && index <= 4) {
								element.checked = true
								this.website.modular.push(element.name)
							}
						})
					}
				}
				// 不是第一次进入本页面 回显上一次选中的模块
				if(res.data && res.data.modular){
					arr=res.data.modular.split(',')
					arr.forEach(e=>{
						if(this.type===0){
							this.modular.forEach(m=>{
								if(e===m.name){
									m.checked=true
									this.website.modular.push(e)
								}
							})
						}else if(this.type===1){
							this.modularEnterprise.forEach(m=>{
								if(e===m.name){
									m.checked=true
									this.website.modular.push(e)
								}
							})
						}else if(this.type===4){
							this.modularExpert.forEach(m=>{
								if(e===m.name){
									m.checked=true
									this.website.modular.push(e)
								}
							})
						}else if(this.type===3){
							this.modularOrgans.forEach(m=>{
								if(e===m.name){
									m.checked=true
									this.website.modular.push(e)
								}
							})
						}else if(this.type===5){
							this.modularCountry.forEach(m=>{
								if(e===m.name){
									m.checked=true
									this.website.modular.push(e)
								}
							})
						}
					})
				}			
			})
		},
		methods: {
			preStep(){
				//this.$parent.$parent.$parent.$router.go(-1)
                let type=this.$route.meta.type
                if(1 === type){
                    this.$parent.$parent.$parent.$router.push('/pro/member/progress35/progress37')
                }else{
                    this.$parent.$parent.$parent.$router.push('/pro/member/step35/step37')
                }
			},
			pass(){
				this.$parent.$parent.$parent.certificationScuuess()
			},
			//网站设置
			saveWebsite() {
				let modular_ = ''
				if (this.website.modular) {
					modular_ = this.website.modular.join(',')
				}
				this.$api.post('/member/website/insert', {
					name: this.website.name,
					position: this.website.position,
					logo: this.website.logo,
					status: this.website.status,
					banner: this.website.banner,
					summary: this.website.summary,
					introduce: this.website.introduce,
					template: this.website.template,
					modular: modular_,
					step: this.$route.path,
					type:this.website.type
				}).then(response => {
					if (response.code === 200) {
						this.pass()
					} else {						
						this.$Message.error('提交失败！')
					}
				})
			},
			change(item) {
				if('首页' === item.name ){
					this.$Message.info('首页不能隐藏！');
				}else{
					var _this=this
					item.checked = !item.checked
					if (item.checked) {//选中
						var len=_this.website.modular.indexOf(item.name)
						if(len===-1){
							_this.website.modular.push(item.name)
						}
					}else{
						var len=_this.website.modular.indexOf(item.name)
						if(len!=-1){
							_this.website.modular.splice(len,1)
						}
					}
				}
			},
			//添加自定义的模块名
			save() {
				if(this.columnName){
					this.defaultNav.push(this.columnName)
					if(this.type===0){
						this.modular.push({name:this.columnName,checked:true})
					}else if(this.type===1){
						this.modularEnterprise.push({name:this.columnName,checked:true})
					}else if(this.type===4){
						this.modularExpert.push({name:this.columnName,checked:true})
					}
					this.columnName=''
				}else{
					this.$Message.error('栏目名不能为空！');
				}
				
			},
			//点击自定义
			selfSet(){
				var _this=this
				 if(this.type===1){
					this.modularEnterprise.forEach(e=>{
						if(e.checked){
							_this.defaultNav.push(e.name)
							if(e.name!='首页'){
								e.cheecked = false
							}
						}
					})
				}else if(this.type===0){
					this.modular.forEach(e=>{
						if(e.checked){
							_this.defaultNav.push(e.name)
							if(e.name!='首页'){
								e.checked = false
							}
						}
					})
				}else if(this.type===4){
					this.modularExpert.forEach(e=>{
						if(e.checked){
							_this.defaultNav.push(e.name)
							if(e.name!='首页'){
								e.checked = false
							}
						}
					})
				}else if(this.type===3){
					this.modularOrgans.forEach(e=>{
						if(e.checked){
							_this.defaultNav.push(e.name)
							if(e.name!='首页'){
								e.checked = false
							}
						}
					})
				}else if(this.type===5){
					this.modularCountry.forEach(e=>{
						if(e.checked){
							_this.defaultNav.push(e.name)
							if(e.name!='首页'){
								e.checked = false
							}
						}
					})
				}
				this.modal=true
			},
			cancel(){
				this.modal=false
				this.defaultNav=[]
			},
			saveCustomNav() {
				var _this=this
				_this.website.modular=[]
				this.defaultNav.forEach(item => {
					if(this.type===1){
						_this.modularEnterprise.forEach(val=>{
							if(val.name === item){
								val.checked=true
							}
						})
					}else if(this.type===0){
						_this.modular.forEach(val=>{
							if(val.name === item){
								val.checked=true
							}
						})
						
					}else if(this.type===4){
						_this.modularExpert.forEach(val=>{
							if(val.name === item){
								val.checked=true
							}
						})
					}else if(this.type===3){
						_this.modularOrgans.forEach(val=>{
							if(val.name === item){
								val.checked=true
							}
						})
					}else if(this.type===5){
						_this.modularCountry.forEach(val=>{
							if(val.name === item){
								val.checked=true
							}
						})
					}
					_this.website.modular.push(item)
				})
				this.defaultNav = []
				this.addColumn=false
			},
			
		}
	}
</script>
<style>
	.mod{
		width: 80px;
		height: 80px;
		text-align: center;
		vertical-align: middle;
		margin:0 auto;
		line-height:80px;
		border:1px solid #dddee1;
		background:#f7f7f7;
		border-radius:3px;
		position:relative;
		cursor:pointer;
	}

	.modular_wid{
		width:600px;margin-left:auto;margin-right:auto;
	}
	.intro{
		background-color:#00c587;
		color:#fff;
		border-color:#00c587;
	}
</style>