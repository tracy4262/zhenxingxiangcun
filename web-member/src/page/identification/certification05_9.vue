<template>
	<div>
		<div class="tc pd40">
			<div class="tc mt20">
				<Select v-model="religion" style="width:300px" transfer filterable  @on-change="change">
					<Option v-for="(item,index) in data" :value="item.value" :label="item.value" :key="index">{{ item.value }}</Option>
				</Select>
				<i-switch v-model="switch1" @on-change="change1" class="ml30" size="large">
					<span slot="open">公开</span>
					<span slot="close">隐藏</span>
				</i-switch>
			</div>
		</div>
		<div>			
			<h2 class="pb20 tc">实时预览</h2>
			<Input v-model="con.content" type="textarea" :rows="4" placeholder="请输入..."></Input>
		</div>
		<div class="footer-btn" v-if="base">
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="saveReli" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>
		<div class="footer-btn" v-if="!base">
			<i-button type="primary" @click="saveReli" size="large">确定</i-button>
		</div>
	</div>
</template>
<script>
import $ from 'jquery'
var works = '';
export default {
	props:{
		base:{
			type:Boolean,
			default:true
		}
	},
	data() {
		return {
			data:[
				{value:'佛教'},
				{value:'道教'},
				{value:'基督教'},
				{value:'犹太教'},
				{value:'伊斯兰教'},
				{value:'印度教'},
				{value:'神道教'},
				{value:'儒教'}
			],
			religion: '',
			switch1: true,
			con: {
				content: '',
				content1: '',
				switch1:true,
			}
		}
	},
	created(){
		var _this=this
		this.$api.get('/member/userFullInfo/findReligion')
			.then(response => {
				if(response.data){
					var res = response.data
					this.switch1 = response.data.status === 0 ? false : true
					this.religion=res.religion.substring(2)
					this.change()
					// this.saveBasic()
				}
					
			})			
	},
	methods: {
		preStep(){
            let type=this.$route.meta.type
            if(1 === type){
                this.$parent.$parent.$parent.$router.push('/pro/member/progress23/progress30')
            }else{
                this.$parent.$parent.$parent.$router.push('/pro/member/step23/step30')
            }
		},
		pass(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.$parent.gotoPathSec(32)
			}else{
				this.$parent.$parent.$parent.gotoPath(32)
			}
		},
		// 保存宗教信息单条数据
		saveReli () {
			this.$api.post('/member/userFullInfo/saveReli', {
				religion: this.con.content,
				status: this.con.switch1,
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
				religion: this.con.content,
				religion1: this.con.content1,
				perfect_info_step: 'religion'
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
		/* saveBasic() {
			this.$store.commit('saveReligion', this.con)
		}, */
		change () {
			if (this.religion) {
				this.con.content = "信仰"+this.religion
				this.con.switch1 = this.switch1
				if (this.switch1) {
					this.con.content1 = this.religion
				}
			}
			// this.saveBasic()
		},
		change1 () {
			this.con.switch1 = this.switch1
			if (this.religion && this.switch1) {
				this.con.content1 = this.religion
			} else {
				this.con.content1 = ''
			}
			// this.saveBasic()
		}
	}
}
</script>