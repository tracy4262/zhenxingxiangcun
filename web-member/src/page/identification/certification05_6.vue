<template>
	<div>
		<div style="margin: 20px 0 40px 0" class="vui-collapse-wrap font-14">
			<div class="ml40 mb20">
				<Row type="flex" align="middle">
					<Col span="9"><divider solid /></Col>
					<Col span="3"><Button type="default" shape="circle" class="ml10">工作经历</Button></Col>
					<Col span="9"><divider solid /></Col>
					<Col span="3">
						<Button class="font-14" v-if="addShow" type="text"  icon="md-add-circle" @click="add">新增</Button>
						<Button class="font-14" v-else type="text" icon="minus-round"  @click="deleteCon">取消</Button>
					</Col>
			</Row>
			</div>
			<div v-show="showEdit" class="ml30 mr30">
				<div style="background: #f8f8f8;">
					<Form label-position="right" :label-width="200" :model="education"  class="pt20 pb10">
				        <FormItem v-for="(child,index) in education.children" :key="index" :label="child.label">
				        	<Date-picker :value="child.value" :options="options" v-if="'工作时间' === child.label" style="width: 300px" @on-change="handleTime"  type="daterange" format="yyyy-MM-dd"  :transfer='transfer'>
							</Date-picker>
							<Input v-model="child.value" type="textarea" :rows="4" style="width: 300px" @on-change="handleChange" v-else-if="'工作详情' === child.label"/>
							<Input v-model="child.value" style="width: 300px" @on-change="handleChange" v-else/>
							<i-switch v-model="child.status" @on-change="handleChange" size="large" class="ml30">
								<span slot="open">公开</span>
								<span slot="close">隐藏</span>
							</i-switch>
				        </FormItem>
	      			</Form>
					<div class="tc pb20">
						<Button type="primary" shape="circle" @click="saveData">保  存</Button>
					</div>
				</div>
				<h2 class="tc pd20">实时预览</h2>
				<Input v-model="education.content" type="textarea" :rows="4"></Input>
			</div>
			<div v-for="(item,index) in con" :key="index" class="mb10 mt20">
				<div class="vui-flex vui-flex-center vui-flex-middle hd" v-if="item.sch0">
					<Row class="vui-flex-item tl" type="flex" align="middle">
						<Col span="6">
						<div class="ell">
							{{item.children[2].value.join('至')}}
						</div>
						</Col>
						<Col span="4">
						<div class="ell">
							{{item.children[0].value}}
						</div>
						</Col>
						<Col span="4">
						<div class="ell">
							{{item.children[1].value}}
						</div>
						</Col>
						<Col span="5">
						<div class="ell">
							{{item.children[3].value}}
						</div>
						</Col>
						<Col span="5">
							<div class="edit-cour">
								<Button class="font-14" type="text"  icon="document-text" size="small" @click="editData(index)">编辑</Button>
						        <Button class="font-14" type="text" icon="trash-a" size="small" @click="deleteData(index)">删除</Button>
							</div>
						</Col>
					</Row>
				</div>
			</div>			
		</div>
		<div  class="footer-btn" v-if="base">
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="saveWork" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>
		<div class="footer-btn" v-if="!base">
			<i-button type="primary" @click="saveWork" size="large">确定</i-button>
		</div>
	</div>
</template>
<script>
import $ from 'jquery'
import divider from '~components/divider'
export default {
    components: {
        divider
	},
	props:{
		base:{
			type:Boolean,
			default:true
		}
	},
	data() {
		return {
			options:{
				disabledDate (date) {
					console.log('date'+date)
					return date && date.valueOf() > Date.now();
				}
			},
			showEdit:false,
			education:{},
			transfer:true,
			confirm:true,
			addShow: false,
			con:[],
			editIndex:-1
		}
	},
	created(){
		this.getInit()
	},
	methods: {
		getInit(){
			this.$api.post(
				'/member/userFullInfo/findWork'
			).then(res => {
				console.log("res",res)
				if(res.code === 200) {
					if(res.data){
						this.con=JSON.parse(res.data)
						this.con.forEach(item => {
					        if (item) {
							  item.content = []
					          item.children.forEach(child => {
					            if (child.status && child.value.toString() !== '') {
					            	item.content += `${child.label}: ${child.value}，`
								}
							  })
							}
						  })
						this.addShow = true
					}else{
						this.education = {
							children:[
								{
									label:'工作单位',
									value:'',
									status:true
								},
								{	
									label:'工作职位',
									value:'',
									status:true
								},
								{	
									label:'工作时间',
									value:[],
									status:true
								},
								{	
									label:'工作详情',
									value:'',
									status:true
								}
							],
							content:'',
							sch0:true,
							sch1:false
						}
						this.showEdit = true
					}
					
				}
			})
		},
		handleChange () {
			let item = this.education
	        if (item) {
	          item.content = []
	          item.children.forEach(child => {
	            if (child.status && child.value.toString() !== '') {
	            	if(child.label === '工作时间') {
	            		item.content += `${child.label}: ${child.value.join('至')}`
	            	}else if(child.label === '工作详情') {
                        item.content += `${child.label}: ${child.value}`
                    } else{
            			item.content += `${child.label}: ${child.value},`
	            	}
				}
			  })
			}
		},
		handleTime(time){
			this.education.children[2].value = time
			this.handleChange ()
		},
		preStep(){
            let type=this.$route.meta.type
            if(1 === type){
                this.$parent.$parent.$parent.$router.push('/pro/member/progress23/progress27')
            }else{
                this.$parent.$parent.$parent.$router.push('/pro/member/step23/step27')
            }
		},
		pass(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.$parent.gotoPathSec(29)
			}else{
				this.$parent.$parent.$parent.gotoPath(29)
			}
		},
		//保存工作信息单条数据
		saveWork () {
			this.$api.post('/member/userFullInfo/insertWork', {
				work: this.con,
				step: this.base ? this.$route.path : ''
			}).then(response => {
				if (response.code === 200) {
					this.saveAll()
				} else {
					this.$Message.error('提交失败！')
				}
			})
		},
		saveAll () {
			this.$api.post('/member/userFullInfo/insert', {
				work: this.con.length === 0 ? '' : this.con,
				perfect_info_step: 'work'
			}).then(response => {
				if (response.code === 200) {
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
		add() {
			console.log(this.con)
			this.showEdit = true
			var len = this.con.length
			this.con.forEach((item,index)=>{
				if(index != len){
					item.sch0 = true
					item.sch1 = false
				}
				})
			var newcon = {
				children:[
					{
						label:'工作单位',
						value:'',
						status:true
					},
					{	
						label:'工作职位',
						value:'',
						status:true
					},
					{	
						label:'工作时间',
						value:[],
						status:true
					},
					{	
						label:'工作详情',
						value:'',
						status:true
					}
				],
				content:'',
				sch0:true,
				sch1:false
			}
			this.education = newcon
			this.addShow = false
		},
		editData(e) {
			this.editIndex = e
			this.con[e].sch1 = true
			this.education = this.con[e]
			this.showEdit = true
			this.addShow = false
		},
		deleteCon(){		
			this.editIndex = -1
			this.education = {}
			this.addShow = true
			this.showEdit = false
		},
        deleteData(index) {
            this.$Modal.confirm({
                content: '<p>您确定删除？</p>',
                cancelText: '取消',
                onOk: () => {
                    this.con.splice(index, 1)
					this.save(1,index)
                },
            });
        },
		//保存
		saveData(index) {
			if(this.editIndex >= 0){ //说明是编辑时候的保存
				this.con.splice(this.editIndex,1)
				this.con.push(this.education)
			}else{ //说明是新增时候的保存
				this.con.push(this.education)
			}
			this.save(1)
		},
		//保存或者删除后执行的方法
		save(type,index){
			console.log(this.con)
			this.$api.post('/member/userFullInfo/insertWork', {
				work: this.con,
				step: this.base ? this.$route.path : ''
			}).then(response => {
				if (response.code === 200) {
					this.$api.post('/member/userFullInfo/insert', {
						work: this.con.length === 0 ? '' : this.con,
						perfect_info_step: 'work'
					}).then(response => {
						if (response.code === 200) {
							this.$Message.success('操作成功！')
							if (type==1) {
								this.addShow = true
								this.education.sch0 = true
								this.showEdit = false
							}
							this.getInit()
						} else {
							this.$Message.error('操作失败！')
						}
					})
				} else {
					this.$Message.error('操作失败！')
				}
			})
		}
	}
}
</script>
<style>
.font-14{
	font-size: 14px;
}
.vui-collapse-wrap .font-14{
    font-size:14px;
}
</style>
