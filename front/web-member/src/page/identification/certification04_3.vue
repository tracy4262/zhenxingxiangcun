
<template>
	<!--4应用设置第三步开始-->
	<div>
		<p style="font-size: 22px ;text-align: center;line-height: 80px;padding-top: 20px">高级应用</p>
		<div class="tr mr100 pb20">
			<ButtonGroup>
				<Button type="default"  @click="checkAll">全选</Button>
				<Button type="default"  @click="checkChange">反选</Button>
			</ButtonGroup>
			<!-- <p style="font-size: 16px;color:#00c587;margin-right:100px" class="fr  mt20 mb20" @click="checkAll()">全部选择</p> -->
		</div>
		<Row  style="height: 200px;">
			<Checkbox-group v-model="app.agent">
				<Col span="6" offset="10" v-for="(item,index) in allappinfo" :key="item.id">
					<Checkbox :label="item.id" class="check_box2">
						<img src="../../img/gjyx-icon.png" alt="" style="vertical-align: middle;margin-left: 6px">
						<span class="species">{{item.appName}}</span>
					</Checkbox>
				</Col>
			</Checkbox-group>
		</Row>
		<div  class="footer-btn" >
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="setApp" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>
	</div>
	<!--4应用设置第三步结束-->
</template>
<script>
export default {
	data() {
		return {
			app: {
				agent: [],
				level: 1
			},
			apps:[],
            allappinfo : [],
			loginuserinfo:JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
		}
	},
	watch:{
		app:{
			handler(curVal, oldVal) {
				this.$store.commit('saveApp', curVal)
				},
				deep: true
		}
	},
	methods: {
		preStep(){
            let type=this.$route.meta.type
            if(1 === type){
                this.$parent.$parent.$router.push('/pro/member/progress20')
            }else{
                this.$parent.$parent.$router.push('/pro/member/step20')
            }
		},
		pass(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.gotoPathSec(22)
			}else{
				this.$parent.$parent.gotoPath(22)
			}
		},
		setApp() {
			this.$api.post(
				'/member/bank/setapp', {
					appname: this.app.agent,
					level: this.app.level,
					step: this.$route.path
				}
			).then(response => {
				if(200 != response.code) {
					this.$Message.error('设置失败!')
				} else {
					this.$Message.success('设置成功!');
					this.pass()
				}
			})
		},
		checkAll(){
			this.app.agent=this.apps
		},
		checkChange(){
			var arr = []
			for(var i = 0 ; i < this.apps.length ;i ++){
				if(this.app.agent.indexOf(this.apps[i])<0){
					arr.push(this.apps[i])

				}
			}
			this.app.agent = arr
		}
	},
	created:function(){
		this.$api.post('/member/bank/findAllappInfo', {
		    	level : 1
			}
		).then(res=>{
				if(res.data.length){
					console.log('应用设置',res)
					var arr=[]
					res.data.forEach(e=>{
                        arr.push(e.id)
                        this.allappinfo.push({appName:e.appName, id:e.id})
					})
					if(arr.length){
                        this.apps=arr
                        this.app.level=1
					}
					//level  基础应用0  高级应用1
					this.$api.post('/member/login/find-my-app',{account:this.loginuserinfo.loginAccount,level:1}).then(response => {
						console.log(response)
						if(response.code == 200){
							if(response.data.appStatus == 0){ // 第一次进去
								this.app.agent = this.apps
							}else{
								var arr = []
								var appList = response.data.appList
								for(var i = 0 ;i < response.data.appList.length ; i++){
									this.app.agent.push(parseInt(appList[i].appId))
								}
							}
						}
					})
				}
				this.$store.commit('saveApp', this.app)
			}).catch(error => {
			    console.error(error)
		})		
	}
}
</script>
<style scoped>
label.check_box2 {
	margin: 10px 27px;
	font-size: 14px;
	width: 145px;
}

.ivu-checkbox-checked .ivu-checkbox-inner {
	border-color: #00c587 ! important;
	background-color: #00c587! important;
}
.species{
	font-size: 16px ;
	line-height: 44px;
	margin-left: 10px;
}
</style>