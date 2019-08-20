<template>
	<div style="margin: 30px auto;">
		<div style="margin: 20px auto;">
			<Row   type="flex" justify="center" align="middle" style="font-size: 16px;margin-bottom: 16px;font-weight: 600;background: #fafafa;padding: 10px 0">
				<Col span="6">栏目名称</Col>
				<Col span="6">是否启用/隐藏</Col>
				<Col span="6">访问权限</Col>
			</Row>
			<Row type="flex" justify="center" align="middle" v-for="(singlecolum,index) in column" :key="index" style="font-size: 16px;margin-bottom: 10px;">
				<Col span="6">
					{{singlecolum.name}}
				</Col>
				<Col span="6" v-if="singlecolum.name === '动态'">
					<i-switch  v-model="singlecolum.status" size="large" @on-change="change" :disabled="disabled">
						<span slot="open">启用</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</Col>
				<Col span="6" v-else>
					<i-switch  v-model="singlecolum.status" size="large" @on-change="change">
						<span slot="open">启用</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</Col>
				<Col span="6">
					<Select v-model="singlecolum.authority" style="width:110px"  @on-change="change" :transfer='transfer'>
						<Option v-for="(item,index) in author" :key="index" :value="item.value" >{{ item.label }}</Option>
					</Select>
				</Col>
			</Row>
		</div>
		<div  class="footer-btn" >
					<i-button type="primary" @click="preStep" size="large">上一步</i-button>
					<i-button type="primary" @click="columnSet1" size="large">下一步</i-button>
					<span class="tiaoguo" @click="pass">跳过</span>
		</div>
	</div>
</template>
<script>
export default {
	data() {
		return {
			disabled:true,
			transfer:true,
			column: [
			{
				name: '动态',
				status: true,
				authority: 0
			}, {
				name: '政策',
				status: true,
				authority: 0
			}, {
				name: '知识',
				status: true,
				authority: 0
			}, {
				name: '产品',
				status: true,
				authority: 0
			}, {
				name: '服务',
				status: true,
				authority: 0
			}, {
				name: '标准',
				status: true,
				authority: 0
			}/* ,{
                name: '图书',
                status: true,
               authority: 0
              } */],
			author: [
				{
					value: 0,
					label: '所有人可见'
				}, {
					value: 2,
					label: '仅自己可见'
				}, {
					value: 1,
					label: '仅好友可见'
				}
			]
		}
	},
	methods: {
		change() {
		},
		pass(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.gotoPathSec(5)
			}else{
				this.$parent.$parent.gotoPath(5)
			}
		},
		preStep() {
				if(this.$route.path === '/pro/member/progress6/progress7/progress8') {
					this.$router.push('/pro/member/progress5')
				} else if(this.$route.path == '/pro/member/step6/step7/step8') {
					this.$router.push('/pro/member/step5')
				} else {
					this.$router.go(-1)

				}

			},
		columnSet1() {
			this.$api.post(
				'/member/columnSettings/insert', {
					base: this.column,
					step: this.$route.path
				}
			).then(response => {
				console.log(response);
				if(500 === response.code) {
					this.$Message.error('设置失败！')
				} else {
					this.$Message.success('设置成功!');
					this.pass()
				}
			})
		},
	},
	created: function() {
		this.$parent.count1=3
		this.$store.commit('saveColumn', this.column)
		//回显
      /*  this.$api.get('/member/columnSettings/findColumnSetting')
            .then(response => {
                console.log("栏目", response.data)
                this.columnSetting = response.data
                var _this = this
                if(this.columnSetting.length) {
                    var arr=[]
                    this.columnSetting.forEach(e => {
                        var cu = {
                            name:e.columnName,
                            status:e.status === 0 ? true : false,
                            authority:e.security
                        }
                        arr.push(cu)
                    })
                    this.column=arr
                }
            })*/
	}
}
</script>