<template>
	<div>
		<div class="tc pd40 pt60">
			<span style="font-size: 14px"> <i class="mr10" style="color: red">*</i>加入时间</span>
			<Date-picker v-model="time" class="ml10" format="yyyy年MM月" :options="options3"  type="month" @on-ok="change" :confirm="confirm" placeholder="选择日期" style="width: 150px;" transfer></Date-picker>
			<Select v-model="political" style="width:200px" class="ml20" transfer filterable @on-change="change">
				<Option v-for="(item,index) in data" :value="item.value" :label="item.value" :key="index">{{item.value}}</Option>
			</Select>
			<i-switch class="ml30" v-model="switch1" @on-change="change1()" size="large">
				<span slot="open">公开</span>
				<span slot="close">隐藏</span>
			</i-switch>
		</div>
		<div>
			<h2 class="tc pb20">实时预览</h2>
			<Input v-model="con.content" type="textarea" :rows="4" placeholder="请输入..."></Input>
		</div>
		<div  class="footer-btn" v-if="base">
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="savePoli" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>
		<div class="footer-btn" v-if="!base">
			<i-button type="primary" @click="savePoli" size="large">确定</i-button>
		</div>
	</div>
</template>
<script>
import $ from 'jquery'
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
                {value:'中国共产党'},
                {value:'中国共青团'},
                {value:'中国民主同盟'},
                {value:'中国民主建国会'},
                {value:'中国民主促进会'},
                {value:'中国致公党'},
                {value:'九三学社'},
                {value:'台湾民主自治同盟'},
                {value:'国民党'},
                {value:'民主党'},
                {value:'无党派'},
                {value:'民进党'}
            ],
			transfer:true,
			confirm:true,
			switch1: true,
			political: '',
			time: '',
			con: {
				content: '',
				content1: '',
				time: '',
				political:'',
				switch1: true
			},
			contents: {
				political: '',
				time: '',
				switch1: true
			},
			contents1: {
				political: '',
				time: '',
				
			},
			options3: {
				disabledDate (date) {
					return date && date.valueOf() > Date.now() - 86400000;
				}
			},
		}
	},
	created(){
		this.$api.get('/member/userFullInfo/findPolitical')
			.then(response => {
				if(response.data){
					var res = response.data
					console.log('pol',res)
					this.political=res.political
					this.time=res.time
					this.switch1=res.status===1?true:false
					// this.con.content=this.time+'加入'+this.political
					// if(this.switch1){
					// 	this.con.content1=this.time+'加入'+this.political
					// }
					// this.con.time=res.time
					// this.con.political=res.political
					// this.con.switch1=this.switch1
					this.saveBasic()
				}
					
			})			
	},
	methods: {
		preStep(){
            let type=this.$route.meta.type
            if(1 === type){
                this.$parent.$parent.$parent.$router.push('/pro/member/progress23/progress29')
            }else{
                this.$parent.$parent.$parent.$router.push('/pro/member/step23/step29')
            }
		},
		pass(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.$parent.gotoPathSec(31)
			}else{
				this.$parent.$parent.$parent.gotoPath(31)
			}
		},
		//保存政治信息单条数据
		savePoli () {
			if (this.con.time) {
                this.$api.post('/member/userFullInfo/savePoli', {
					poli: this.con.political,
					time: this.con.time,
					status: this.switch1,
					step: this.base ? this.$route.path : ''
				}).then(response => {
                    if(response.code === 200) {
                        this.saveAll()
                    } else {
                        this.$Message.error('提交失败！')
                    }
                })
			} else {
                this.$Message.error('时间必须填写！')
			}
		},
		saveAll () {
			this.$api.post('/member/userFullInfo/insert', {
				political: this.con.content,
				political1: this.con.content1,
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
		saveBasic() {
			this.$store.commit('savePolitical', this.con)
		},
		change() {
			this.con.switch1=this.switch1
			if (this.time) {
				this.con.time=this.contents.time =this.moment(this.time).format('YYYY年MM月')
				if (this.switch1 == true) {
					this.contents1.time = this.moment(this.time).format('YYYY年MM月')
				}
			}
			if (this.political) {
				this.con.political=this.contents.political = this.political
				if (this.switch1 == true) {
					this.contents1.political = this.political
				}
			}
			this.con.content = this.contents.time + "加入" + this.contents.political
			this.con.content1 = this.contents1.time + "加入" + this.contents1.political
			this.saveBasic()
		},
		change1() {
			if (this.time != '' & this.switch1 == true) {
				this.contents1.time = this.moment(this.time).format('YYYY年MM月')
			} else {
				this.contents1.time = ''
			}
			if (this.political!= '' & this.switch1 == true) {
				this.contents1.political = this.political
			} else {
				this.contents1.political = ''
			}
			this.con.content1 = this.contents1.time + "加入" + this.contents1.political
			this.saveBasic()
		}
	}
}
</script>