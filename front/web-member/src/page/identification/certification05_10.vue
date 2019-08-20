<template>
	<div>
		<div style="margin: 20px 0 40px 0" class="vui-collapse-wrap font-14">
			<div class="ml40 mb20">
				<Row type="flex" align="middle">
					<Col span="9"><divider solid /></Col>
					<Col span="3"><Button type="default" shape="circle" class="ml10">获得荣誉</Button></Col>
					<Col span="9"><divider solid /></Col>
					<Col span="3">
						<Button class="font-14" v-if="addShow" type="text"  icon="md-add-circle" @click="add">新增</Button>
						<Button class="font-14" v-else type="text" icon="minus-round"  @click="deleteCon">取消</Button>
					</Col>
				</Row>
			</div>
			<div v-show="showEdit" class="ml30 mr30" >
				<div style="background: #f8f8f8;">
				<Form label-position="right" :label-width="150" :model="honor"  class="pt20 pb10" inline>
					<FormItem  label="曾获荣誉">
						<Input v-model="honor.honor" style="width: 150px" @on-change="changeContent"></Input>
					</FormItem>
					<FormItem  label="获得时间">
						<DatePicker :value="honor.time"  :options="options3"  type="month"
					   @on-change="changeTime"
					   placeholder="选择日期"  format="yyyy-MM" style="width: 150px" :transfer='transfer'></DatePicker>
						<i-switch style="margin-left: 30px" v-model="honor.switch1" @on-change="changeContent"  size="large">
							<span slot="open">公开</span>
							<span slot="close">隐藏</span>
						</i-switch>
					</FormItem>			
				</Form>
				<div class="tc pb20">
					<Button type="primary" shape="circle" @click="saveData">保  存</Button>
				</div>
				</div>
				<h2  class="tc pb20 pt10">实时预览</h2>
				<Input v-model="honor.content" type="textarea" :rows="4"></Input>
			</div>
			<div v-for="(item,index) in con" class="mb10 mt20">
				<div class="vui-flex vui-flex-center vui-flex-middle hd" v-if="item.sch0 ">
					<Row class="vui-flex-item tl">
						<Col span="4">
						<div class="ell">
							{{item.time}}
						</div>
						</Col>
						<Col span="10" offset="2">
						<div class="ell">
							{{item.honor}}
						</div>
						</Col>
						<Col span="6">
							<div class="edit-cour">
								<Button class="font-14" type="text"  icon="document-text" size="small"  @click="editData(index)">编辑</Button>
						        <Button class="font-14" type="text" icon="trash-a" size="small"  @click="deleteData(index)">删除</Button>
									<!-- <span @click="editData(index)">
										<Icon type="document-text" size="20"  class="ml5"/> 编辑
									</span>
								<span @click="deleteData(index)">
										<Icon type="trash-a" size="20"  class="ml5"/> 删除
									</span> -->

							</div>
						</Col>
					</Row>
				</div>
			</div>
		</div>
		<div class="footer-btn" v-if="base">
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="saveHonor" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>
		<div class="footer-btn" v-if="!base">
			<i-button type="primary" @click="saveHonor" size="large">确定</i-button>
		</div>
	</div>
</template>
<script>
import $ from "jquery"
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
			transfer: true,
			confirm: true,
			addShow:false,
			showEdit:false,
			honor:{
				honor: '',
				time: '',
				content: '',
				content1: '',
				sch0: true,
				sch1:false,
				switch1: true
				},
			con: [],
			options3: {
				disabledDate(date) {
				return date && date.valueOf() > Date.now() - 86400000;
				}
			},
			ruleInline: {
				honor: [
					{
						required: true,
						message: "请填写曾获荣誉",
						trigger: "blur"
					},
					{
						pattern: /^[\u4e00-\u9fa5]/,
						message: "请填写中文字符",
						trigger: "blur"
					}
				],
			},			
			editIndex:-1
		}
    },
  created: function() {
  	 this.getInit()
  },
  methods: {
	getInit(){
		this.$api.get("/member/userFullInfo/findhonner").then(res => {
			if (res.data.length) {
				console.log('honner',res)
				this.con=[]
				res.data.forEach(e=>{
					this.con.push({
						honor:e.honor,
						time:e.time,
						sch0:true,
						sch1:false,
						switch1:e.status===1 ? true : false
					})
				})
				this.addShow = true
			}else{
				this.showEdit = true
			}
		})
	  },
  	changeTime(time){
  		this.honor.time = time
  		this.changeContent()
  	},
  	changeContent(){
  		if(this.honor.time){
  			this.honor.content = this.honor.time + "获得" + this.honor.honor
  		}
    	if(this.honor.switch1){
    		this.honor.content1 = this.honor.content
    	}else{
    		this.honor.content1 = ''
    	}
       console.log("curValdeep1111====",this.con)
  	},
  	preStep(){
        let type=this.$route.meta.type
        if(1 === type){
            this.$parent.$parent.$parent.$router.push('/pro/member/progress23/progress31')
        }else{
            this.$parent.$parent.$parent.$router.push('/pro/member/step23/step31')
        }
	},
	pass(){
		let type=this.$route.meta.type
		if(1 === type){
			this.$parent.$parent.$parent.gotoPathSec(33)
		}else{
			this.$parent.$parent.$parent.gotoPath(33)
		}
	},
	// 保存荣誉信息单条数据
	saveHonor () {
		this.$api.post('/member/userFullInfo/saveHonor', {
			honor: this.con,
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
		this.$api.post('/member/userFullInfo/insert', {
			honor: this.con.length === 0 ? '' : this.con,
			perfect_info_step: 'honor'
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
		var len = this.con.length;
		this.con.forEach((item,index)=>{
		if(index != len){
			item.sch0 = true
			item.sch1 = false
		}
		})
		var newcon = {
		honor: '',
		time: '',
		content: '',
		content1: '',
		sch0: true,
		sch1: false,
		switch1: true,
		};
		this.addShow = false
		this.honor = newcon
		this.showEdit = true
    },
    editData(e) {
		this.editIndex = e
		this.con[e].sch1 = true
		this.honor = this.con[e]
		this.showEdit = true
		this.addShow = false
		this.changeContent()
	},
	deleteCon(){
		this.editIndex = -1
		this.addShow = true
		this.showEdit = false
		this.honor = {}
	},
    deleteData(index) {
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
			this.con.push(this.honor)
		}else{ //说明是新增时候的保存
			this.con.push(this.honor)
		}
		this.save(1)
	},
	save(type,index){
		this.$api.post('/member/userFullInfo/saveHonor', {
			honor: this.con,
			step: this.base ? this.$route.path : ''
		}).then(response => {
			if(response.code === 200) {
				this.$api.post('/member/userFullInfo/insert', {
					honor: this.con.length === 0 ? '' : this.con,
					perfect_info_step: 'honor'
				}).then(response => {
					if (response.code === 200) {
						this.$Message.success('操作成功')
						if (type == 1) {
							this.addShow = true
							this.honor.sch0 = true
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
};
</script>
<style scoped>
	.con-text{
		width: 80% !important;
	}
</style>
