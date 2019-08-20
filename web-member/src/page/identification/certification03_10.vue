<template>
	<!--3实名认证第十步开始-->
	<div>
		 <p style="text-align: center;margin-top: 10px;font-size: 18px;">填 写 信 息</p> 
		<Row class="mt30">
			<Col span="8" offset="8">
				<Form label-position="right" :label-width="100">
					<FormItem label="真实姓名">
						<Input type="text" v-model="account.name" placeholder="真实姓名"   >
						</Input>
					</FormItem>
					<FormItem label="身份证号码">
						<Input type="text" v-model="account.idcard" placeholder="身份证号码"   >
						</Input>
					</FormItem>
				</Form>
			</Col>
		</Row>
		<div  class="footer-btn" >
					<i-button type="primary" @click="preStep" size="large">上一步</i-button>
					<i-button type="primary" @click="setAccountInfo" size="large">下一步</i-button>
					<span class="tiaoguo" @click="pass">跳过</span>
		</div>
	</div>
	<!--3实名认证第十步结束-->
</template>
<script>
import api from "~api"
export default {
  data() {
    return {
      disabled: false,
      readonly: true,
      nameLength: 4,
      idcardLength: 18,
      reg1: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
	  reg2: /^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$/,
      account: {
        name: "",
        idcard: ""
      }
    };
  },
  created: function() {
    this.$parent.baifen = 100
    this.find()
  },
  methods: {
  	preStep(){
				this.$parent.$parent.$router.go(-1)
			},
	pass(){
		let type=this.$route.meta.type
		if(1 === type){
			this.$parent.$parent.$parent.gotoPathSec(17)
		}else{
			this.$parent.$parent.$parent.gotoPath(17)
		}
	},
	setAccountInfo() {
		//验证
		if(!this.account.name) {
				this.$Message.error('请输入姓名')
			} else
			if(!this.account.idcard) {
				this.$Message.error('请输入身份证号码')
			} else if(!(this.reg1.test(this.account.idcard) || this.reg2.test(this.account.idcard))) {
				this.$Message.error('请输入正确的身份证号码')
			} else {
				this.$api.post(
					'/member/bank/insertInfo', {
					name: this.account.name,
					idcard: this.account.idcard,
					step: this.$route.path
				}).then(response => {
					if(0 == response.data) {
						this.$Message.error('设置失败');
					} else {
						this.$Message.success('设置成功!');
						this.pass()
					}
				})
			}
			
		},

    find() {
      api.get("/member/Certification/find").then(response => {
        const flag = response.code
        console.log("flag", response)
        if (flag == 200) {
          if (response.data && response.data.realname) {
            this.disabled = true
            this.account.name = response.data.realname
            this.account.idcard = response.data.idCard
            this.$store.commit("saveAccount", this.account)
          }
        } else {
          this.$Message.error('信息查询失败！')
        }
      });
    }
  }
};
</script>