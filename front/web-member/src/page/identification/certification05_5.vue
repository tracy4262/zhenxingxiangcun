<template>
	<div>
		<div style="margin: 20px 0 40px 0" class="vui-collapse-wrap font-14">
			<div class="ml40 mb20">
				<Row  type="flex" align="middle">
					<Col span="9"><divider solid /></Col>
					<Col span="3"><Button type="default" shape="circle" class="ml10" >教育经历</Button></Col>
					<Col span="9"><divider solid /></Col>
					<Col span="3">
						<Button class="font-14" v-if="addShow" type="text"  icon="md-add-circle" @click="add">新增</Button>
						<Button class="font-14" v-else type="text" icon="minus-round"  @click="deleteCon">取消</Button>
					</Col>
				</Row>
			</div>
			<div v-show="showEdit" class="ml30 mr30" >
				<div style="background: #f8f8f8;">
					<Form label-position="right" :label-width="200" :model="education"  class="pt20 pb10">
						<FormItem v-for="(child,index) in education.children" :label="child.label" v-show="child.show" :key="index">
							<Date-picker :value="child.value" v-if="'入学/毕业时间' === child.label"  :options="options" style="width: 300px" @on-change="handleTime" type="daterange" format="yyyy-MM-dd" :transfer='transfer'>
							</Date-picker>
							<Select v-model="child.value" style="width:300px" @on-change="changeContent" :transfer='transfer' v-else-if="'学历学位' === child.label">
								<Option v-for="(item,index) in xueli" :value="item.value" :key="index">{{ item.label }}</Option>
							</Select>
							<!-- <Select v-model="child.value" style="width:300px" @on-change="changeContent" :transfer='transfer' v-else-if="'专业名称' === child.label">
								<Option v-for="item in zhuanye" :value="item.value">{{ item.label }}</Option>
							</Select> -->
							<Input v-model="child.value" style="width:300px" @on-change="changeContent" :transfer='transfer' v-else-if="'专业名称' === child.label" />
							<Select v-model="child.value" style="width:300px" @on-change="changeContent" :transfer='transfer' v-else-if="'是否统招' === child.label">
								<Option v-for="(item,index) in tongzhao" :value="item.value" :key="index">{{ item.label }}</Option>
							</Select>
							<Input v-model="child.value" style="width: 300px" @on-change="changeContent" v-else/>
							<i-switch v-model="child.status" @on-change="changeContent" size="large" class="ml30">
								<span slot="open">公开</span>
								<span slot="close">隐藏</span>
							</i-switch>
						</FormItem>
					</Form>
					<div class="tc pb20">
						<Button type="primary" shape="circle"  @click="saveData">保  存</Button>
					</div>
				</div>
				<h2 class="tc pd20" >实时预览</h2>
				<Input v-model="education.content" type="textarea" :rows="4"></Input>
			</div>
			<div v-for="(item,index) in con" class="mb10 mt20" :key="index">
				<div class="vui-flex vui-flex-center vui-flex-middle hd" v-if="item.sch0 ">
					<Row class="vui-flex-item tl" type="flex" align="middle">
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
						<Col span="2">
						<div class="ell" v-if="item.children[2].value != ''">
							{{item.children[2].value}}
						</div>
						<div v-else>
							暂无
						</div>
						</Col>
						<Col span="2">
						<div class="ell">
							{{item.children[3].value}}
						</div>
						</Col>
						<Col span="6">
						<div class="ell">
							{{item.children[4].value.join('至')}}
						</div>
						</Col>
						<Col span="6">
							<div class="edit-cour">
								<Button class="font-14" type="text"  icon="document-text" size="small"  @click="editData(index)">编辑</Button>
								<Button class="font-14" type="text" icon="trash-a" size="small" @click="deleteData(index)">删除</Button>
							</div>
						</Col>
					</Row>
				</div>
			</div>


		</div>
		<div class="footer-btn" v-if="base">
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="saveEdu" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>
		<div class="footer-btn" v-if="!base">
			<i-button type="primary" @click="saveEdu" size="large">确定</i-button>
		</div>
		<!-- 专业名称 -->
		<vui-filter ref="academicFilter" :simple="true" :resultDatas="academicResultDatas" @on-search="handleAcademicSearch" @on-get-result="handleGetAcademicResult" />

	</div>
</template>
<script>
	import divider from '~components/divider'
	import $ from 'jquery'
	import vuiFilter from '~components/vuiFilter/filter'
	import api from '~api'
	export default {
		components: {
			vuiFilter,
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
                        return date && date.valueOf() > Date.now();
                    }
				},
				showEdit:false,
				education:{},
				transfer: true,
				confirm: true,
				addShow : false,
				zhuanye: [{
						value: '计算机信息与技术',
						label: '计算机信息与技术'
					},
					{
						value: '磨具设计与制造',
						label: '磨具设计与制造'
					},
					{
						value: '会计',
						label: '会计'
					}
				],
				tongzhao: [{
						value: '是',
						label: '是'
					},
					{
						value: '否',
						label: '否'
					}

				],
				xueli: [{
						value: '小学',
						label: '小学'
					},
					{
						value: '初中',
						label: '初中'
					},
					{
						value: '高中',
						label: '高中'
					},
					{
						value: '高职高专',
						label: '高职高专'
					},
					{
						value: '大专',
						label: '大专'
					},
					{
						value: '本科',
						label: '本科'
					},
					{
						value: '研究生',
						label: '研究生'
					},
					{
						value: '博士',
						label: '博士'
					},
				],
				con: [],
				academicResultDatas: [],
				editIndex:-1
			}
		},
		created() {
			//初始化获取数据
			this.getInit()
			/*// 取相关行业数据
			api.post('/member/system-dict/getSystemDict', {
			typeName:'大学专业'
			}).then(res => {
				var data = res.data.list
				data.forEach((item,index)=>{
					item.checked = false
					item.label = item.dictName
					item.value = item.dictId
				})
				this.academicResultDatas = data
			})*/
		},
		methods: {
			getInit(){
				this.$api.post(
					'/member/userFullInfo/findEdu'
				).then(res => {
					if(res.code === 200) {
						console.log("res5",res)
						if(res.data) {
							this.con = JSON.parse(res.data)
							this.con.forEach(item => {
								if(item) {
									item.content = []
									item.children.forEach(child => {
										if(child.status && child.value.toString() !== '') {
											item.content += `${child.label}: ${child.value}，`
										}
									})
								}
							})
							this.addShow = true
						}else{
							this.education = {
								children: [{
										label: '学校名称',
										value: '',
										status: true,
										show: true
									},
									{
										label: '学历学位',
										value: '',
										status: true,
										show: true
									},
									{
										label: '专业名称',
										value: '',
										status: true,
										show: true
									},
									{
										label: '是否统招',
										value: '',
										status: true,
										show: true
									},
									{
										label: '入学/毕业时间',
										value: [],
										status: true,
										show: true
									}
								],
								content: '',
								sch0: true,
								sch1: false
							}
							this.showEdit = true
						}
					}
				})
			},
			// 高级搜索弹窗
			handleFilterModal(name) {
				this.$refs[name].highFilterShow = true
			},
			// 专业搜索
			handleAcademicSearch(letter, keyword, result) {
				console.log(letter)
				api.post(`/member/system-dict/getSystemDict`, {
					dictName: keyword,
					typeName: '大学专业',
					character: letter === '全部' ? '' : letter,
				}).then(res => {
					var d = res.data.list
					this.academicResultDatas = []
					if(!d.length) return
					if(result.length) {
						result.forEach(item => {
							d.forEach(child => {
								if(child.dictName === item.dictName) {
									child.checked = true
									child.label = child.dictName
									child.value = child.dictId
								}
							})
						})
					} else {
						d.forEach(child => {
							child.checked = false
							child.label = child.dictName
							child.value = child.dictId
						})
					}
					this.academicResultDatas = d
				})
			},
			handleGetAcademicResult(classify, result) {
				var arr = []
				result.forEach(item => arr.push(item.label))
				this.child.value = arr.join(' ')

				this.industryDatas = result.map(function(item) {
					return item.value
				})

				let obj = {
					regionDatas: this.regionDatas,
					speciesDatas: this.speciesDatas,
					industryDatas: this.industryDatas,
					productDatas: this.productDatas,
					serviceDatas: this.serviceDatas,
					informationDatas: this.informationDatas,
					fileType: this.fileType,
					keywrod: this.keywrod,

					position: this.position,
					expertType: this.expertType,
					adeptField: this.adeptField,

					corpType: this.corpType
				}
				this.$emit('searchInMa', obj)
			},
			changeContent(){
				let item = this.education
				if(item) {
					if(item.children.length) {
						if(item.children[1].value === '小学' ||
							item.children[1].value === '初中' ||
							item.children[1].value === '高中') {
							item.children[2].show = false
						} else {
							item.children[2].show = true
						}
					}
				item.content = []
				item.children.forEach(child => {
					if(child.status && child.value.toString() !== '') {
						if(child.label === '入学/毕业时间') {
							item.content += `${child.label}: ${child.value.join('至')}`
						} else {
							item.content += `${child.label}: ${child.value}，`
						}
					}
				})
			}
			},
			handleTime(time){
				this.education.children[4].value = time
				this.changeContent()
			},
			preStep() {
                let type=this.$route.meta.type
                if(1 === type){
                    this.$parent.$parent.$parent.$router.push('/pro/member/progress23/progress26')
                }else{
                    this.$parent.$parent.$parent.$router.push('/pro/member/step23/step26')
                }
			},
			pass() {
				let type = this.$route.meta.type
				if(1 === type) {
					this.$parent.$parent.$parent.gotoPathSec(28)
				} else {
					this.$parent.$parent.$parent.gotoPath(28)
				}
			},
			saveEdu () {
				this.$api.post('/member/userFullInfo/insertEdu', {
					edu: this.con,
					step: this.base ? this.$route.path : ''
				}).then(response => {
					if(response.code === 200) {
						this.saveAll()
					} else {
						this.$Message.error('提交失败！')
					}
				})
			},
			saveAll () {
				this.$api.post(
					'/member/userFullInfo/insert', {
						education: this.con.length === 0 ? '' : this.con,
						perfect_info_step: 'education'
					}
				).then(response => {
					if (response.code === 200 ) {
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
				this.showEdit = true
				var len = this.con.length
				this.con.forEach((item, index) => {
					if(index != len) {
						item.sch0 = true
						item.sch1 = false
					}
				})
				var newclone = {
					children: [{
							label: '学校名称',
							value: '',
							status: true,
							show: true
						},
						{
							label: '学历学位',
							value: '',
							status: true,
							show: true
						},
						{
							label: '专业名称',
							value: '',
							status: true,
							show: true
						},
						{
							label: '是否统招',
							value: '',
							status: true,
							show: true
						},
						{
							label: '入学/毕业时间',
							value: [],
							status: true,
							show: true
						}
					],
					content: '',
					sch0: true,
					sch1: false
				}
				this.addShow = false
				this.education = newclone
				console.log(this.education)
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
				this.addShow = true
				this.showEdit = false
				this.education = {}
			},
            deleteData(index){
                this.$Modal.confirm({
                    content: '<p>您确定删除？</p>',
                    cancelText: '取消',
                    onOk: () => {
						this.con.splice(index, 1)
						this.save(2,index)
                    },
                });
			},
			saveData() {
				if(this.editIndex >= 0){ //说明是编辑时候的保存
					this.con.splice(this.editIndex,1)
					this.con.push(this.education)
				}else{ //说明是新增时候的保存
					this.con.push(this.education)
				}
				this.save(1)
			},
			save(type,index){
				this.$api.post('/member/userFullInfo/insertEdu', {
					edu: this.con,
					step: this.base ? this.$route.path : ''
				}).then(response => {
					if(response.code === 200) {
						this.$api.post(
							'/member/userFullInfo/insert', {
								education: this.con.length === 0 ? '' : this.con,
								perfect_info_step: 'education'
							}
						).then(response => {
							if (response.code === 200 ) {
								this.$Message.success('操作成功')
								this.getInit()
								if(type = 1){ //保存
									this.addShow = true
									this.education.sch0 = true
									this.showEdit = false
								}
							} else {
								this.$Message.error('操作失败！')
							}
						})
					} else {
						this.con.pop()
						this.$Message.error('操作失败！')
					}
				})
			}
		}
	}
</script>
<style lang="scss">
.font-14{
	font-size: 14px;
}
.vui-collapse-wrap .font-14{
    font-size:14px;
}
</style>
