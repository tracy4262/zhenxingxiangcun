<template>
	<!--3设置栏目第八步开始-->
	<div>
		<Button style="margin-left: 20px;" @click="addColumn()" type="primary">添加新分组</Button>
		<div style="margin: 20px auto;display: block" id="columndiv">
			<Row style="text-align: center;font-size: 16px;margin-bottom: 16px;font-weight: 600;background: #fafafa;padding: 10px 0">
				<Col span="5">分组名称</Col>
				<Col span="5" offset="2">权限</Col>
				<Col span="10" offset="1" style="padding-left: 20px">操作</Col>
			</Row>
			<Row style="text-align: center;font-size: 14px;margin-bottom: 16px" v-for="(singlecolum,index) in friends" :key="index">
				<Col span="5" class="columntext">
					<Input v-model="singlecolum.name" @on-change="change" size="large" />
				</Col>
				<Col span="5" offset="2" class="selectComp">
					<Select v-model="singlecolum.authority" style="width:200px" size="large" placeholder="所有人可见" @on-change="change" :transfer='transfer'>
						<Option v-for="(item,index) in author" :value="item.value" :key="index">{{ item.label }}</Option>
					</Select>
				</Col>
				<Col span="9" offset="2" class="buttonOperate">
					<Button-group>
						<Button @click="up()">向上</Button>
						<Button @click="down()">向下</Button>
						<!-- <Button @click="rename()">重命名</Button> -->
						<Button @click="remove(singlecolum)">删除</Button>
					</Button-group>
				</Col>
			</Row>
		</div>
		<div  class="footer-btn" >
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="setFriend" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>
	</div>
	<!--3设置栏目第八步结束-->
</template>
<script>
import $ from 'jquery'
export default {
	data() {
		return {
			transfer:true,
			friends: [
				{ name: '我的好友', authority: '0' }
			],
			author: [
				{
					value: '0',
					label: '所有人可见'
				}, {
					value: '2',
					label: '仅自己可见'
				}, {
					value: '1',
					label: '仅好友可见'
				}
			]
		}
	},
	created: function() {
		this.$parent.baifen = 75;
		this.change()
		this.$api.get('/member/friendGroup/find')
			.then(res=>{
				var arr=[]
				if(res.data.length){
					res.data.forEach(e=>{
						arr.push({name:e.groupName,authority:'0'})
					})
					this.friends=arr
					this.change()
				}
		})
	},
	methods: {
		// 保存好友分组
		setFriend() {
			this.$api.post('/member/friendGroup/insert', {
				friend: this.friends,
				step: this.$route.path
			}).then(response => {
				if (response.code === 200) {
					this.$Message.success('设置成功!')
					this.pass()
				} else {
					this.$Message.error('设置失败！')
				}
			})
		},
		preStep(){
				this.$parent.$parent.$router.go(-1)
			},
			pass(){
				let type=this.$route.meta.type
				if(1 === type){
					this.$parent.$parent.$parent.gotoPathSec(16)
				}else{
					this.$parent.$parent.$parent.gotoPath(16)
				}
			},
		up() {
			var obj = event.target
			if ($(obj).html().indexOf("span") == -1) {
				obj = $(obj).parent()
			}
			var row = $(obj).parent().parent().parent()
			var buttons = $("#columndiv .ivu-btn-group")
			var group = $(obj).parent()
			var index = $(buttons).index(group)
			if (index > 0) {
				$(row).prev().before($(row))
			}
		},
		down() {
			var obj = event.target
			if ($(obj).html().indexOf("span") == -1) {
				obj = $(obj).parent()
			}
			var row = $(obj).parent().parent().parent()
			var buttons = $("#columndiv .ivu-btn-group")
			var group = $(obj).parent()
			var index = $(buttons).index(group)
			if (index + 1 < buttons.length) {
				$(row).next().after($(row))
			}
		},
		addColumn() {
			var newclone = {
				name: '我的好友',
				authority: '0',
			}
			this.friends.push(newclone)
		},
        remove(e) {
            this.$Modal.confirm({
				title: '操作提示',
                content: '<p>您确定删除？</p>',
                cancelText: '取消',
                onOk: () => {
                    this.removes(e)
                },
            });
        },
		removes(e) {
			var index = this.friends.indexOf(e);
			if (index > -1) {
				this.friends.splice(index, 1)
			}
		},
		rename() {
			var obj = event.target
			if ($(obj).html().indexOf("span") == -1) {
				obj = $(obj).parent()
			}
			var row = $(obj).parent().parent().parent()
			$(row).find("div:eq(0)").attr("contenteditable", "true")
		},
		change() {
			this.$store.commit('saveFriends', this.friends)
		}
	}
}
</script>
<style>
	.selectComp .ivu-select-placeholder{
		height: 34px;
		line-height: 34px;
		font-size: 16px !important;
	}
	.buttonOperate .ivu-btn-large {
		padding: 5px 18px 5px;
		font-size: 16px;
		border-radius: 4px;
	}
</style>