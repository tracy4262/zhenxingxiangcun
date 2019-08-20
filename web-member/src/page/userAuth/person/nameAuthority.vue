<template>
    <div class="name-authority pt30 pl10 pr10">
        <Form ref="certification" :model="certification" label-position="left" :label-width="150" :rules="nameAuthorityRules">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item class="input" prop="name" label="姓名">
                        <Input v-model="certification.name" :maxlength="nameLength"  @on-change="userNameChange" />
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item class="input" prop="idcard" label="身份证">
                        <Input v-model="certification.idcard" :maxlength="idcardLength"   @on-change="userNameChange"/>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item class="input" prop="phone" label="电话">
                        <Input v-model="certification.phone" :maxlength="phoneLength"  @on-change="userNameChange" />
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item class="input" prop="code" label="验证码">
                        <Input v-model="certification.code"  :maxlength="codeLength" >
                            <Button type="primary" :disabled="sendDisabled"  slot="append" @click.native="code">
                                <vui-countdown
								title="秒重新发送"
								v-model="sendTime"
								:start="sendStrat"
								v-show="sendShow"
								@finish="handleSendFinish"
								/>
                                <template v-show="!sendShow">{{sendLabel}}</template>
                            </Button>
                        </Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item class="input" prop="city" label="所属地区">
                        <vui-cascander :values="certification.cityName" @handle-get-result="handleGetData"></vui-cascander>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="addrDetail" label="" style="margin-left:-150px;">
                        <Input v-model="certification.addrDetail" @on-change="changeAddr" :maxlength="50" placeholder="详细地址..."/>
                    </Form-item>
                </Col>
                    <Col span="24">
                    <Form-item prop="" label="">
                        <Input v-model="certification.addrView" type="textarea" :autosize="{minRows: 2,maxRows: 5}" readonly/>
                    </Form-item>
                </Col>
            </Row>
         </Form>
        <div class="tc pd20">
            <Button type="primary" @click="handleClickNext">下一步</Button>
            <!-- <Button type="text" @click="handleClickOver">跳过</Button> -->
            </div>
        </div>
</template>

<script>
import vuiCascander from '~components/vuiCascader/index'
import vuiCountdown from '~components/countdown'
import {isIdCard,isPhone2} from '~utils/validate'
export default {
    components:{
        vuiCascander,
        vuiCountdown
    },
    data () {
        //自定义验证码的验证
        const checkCode = (rule, value, callback) => {
			var uPattern = /^\d{6}$/
			if (!uPattern.test(value)) {
				return callback(new Error('请填写验证码'));
			}else {
				callback()
			}
        }
        const checkCity = (rule, value, callback) => {
			if (value.length) {
				callback()
			}else {
				return callback(new Error('请填写选择地区'));
			}
		}
        return {
            nameLength:6,
			idcardLength:18,
			phoneLength:11,
            codeLength:6,
            sendDisabled:false,
            sendShow:false,
            sendTime:60,
            sendStrat:false,
            sendShow:false,
            sendLabel:'发送验证码',
            loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            certification:{
                name:'',
                idcard:'',
                phone:'',
                code:'',
                cityName:'',
                addrView: ''
            },
            nameAuthorityRules:{
                name:[
                    {
                        required:true,message:'请填写姓名',trigger:'blur'
                    },
                    {
                        pattern: /^[\u4e00-\u9fa5]{2,6}/,
                        min: 2,
                        max: 6,
                        message: "2-6个中文字符，不能含有特殊字符、字母数字。",
                        trigger: "blur"
                    }
                ],
                idcard:[
                    {
                        required:true,
                        validator:isIdCard,
                        message: "请填写正确的身份证号码",
                        trigger: "blur"
                    }
                ],
                phone:[
                    {
                        required:true,message:'请填写手机号',trigger:'blur'
                    },
                    {
                        validator: isPhone2,
                        message: "请填写正确的手机号码",
                        trigger: "blur"
                    }
                ],
                code:[
                    {required:true,message:'请填写验证码',validator: checkCode}
                ],
                cityName:[{required:true,message:'请选择所属区域',validator: checkCity}],
            }
        }
    },
    created () {
        // 回显
	    this.$api.get('/member/Certification/find').then(response => {
			const flag = response.code
			if(flag == 200) {
				if(response.data){
					this.certification.idcard = response.data.idCard
					this.certification.phone = response.data.mobile
					this.certification.addrDetail = response.data.addrDetail
					if(response.data.realname){
						this.certification.name = response.data.realname
					}
					if (response.data.city) {
						this.certification.cityName = response.data.city
					}
					this.$store.commit('showUserName', this.certification)
                    this.changeAddr()
				}
			}
		})
    },
    methods:{
        handleClickNext () {
            this.$refs.certification.validate( (valid) => {
                if(valid){
                    this.$refs['certification'].validate((valid) => {
                        if (valid) {
                            let data = {
                                name: this.certification.name,
                                idcard: this.certification.idcard,
                                code: this.certification.code,
                                phone: this.certification.phone,
                                city: this.certification.cityName,
                                locationId: '',
                                account: this.loginUser.loginAccount,
                                addrDetail: this.certification.addrDetail,
                            }
                            this.$api.post('/member/Certification/insert', data).then(response => {
                                if(0 === response.data) {
                                    this.$Message.error('提交失败')
                                }else{
                                    if(9 === response.data) {
                                        this.$Message.error('验证码错误')
                                    } else {
                                        this.$Message.success('提交成功!')
                                        this.handleClickOver()
                                    }
                                }
                            })
                        }
			        })
                }
            })
        },
        userNameChange() {
			this.$store.commit('showUserName', this.certification)
		},
        handleClickOver () {
            this.$router.push('/auth/personAuth/step2')
        },
        //验证码发送
		code() {
			var reg1 = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
			var reg2 = /^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$/
			var modle = /^1[3|4|5|7|8][0-9]{9}$/ //验证规则
			if (!this.certification.name) {
				this.$Message.error('请填写姓名')
			}else if(!this.certification.idcard) {
				this.$Message.error('请输入身份证号码')
			}else if (!(reg1.test(this.certification.idcard) || reg2.test(this.certification.idcard))) {
				this.$Message.error('请输入正确的身份证号码')
			}else if (!this.certification.phone) {
				this.$Message.error('请输入您的手机号')
			}else if (!modle.test(this.certification.phone)) {
				this.$Message.error('手机号填写错误')
			}else{
				this.sendStrat = true
	            this.sendShow = true
	            this.sendLabel = ''
	            this.sendDisabled = true
				this.$api.post(
					'/member/Certification/code',
					{
						phone: this.certification.phone,
					}
				).then(response => {
					if (0 == response.data) {
						this.$Message.error('手机号填写错误')
					} else {
						this.last_time=60
						this.isDisable=true
						clearInterval(this.clock)
						this.clock = setInterval(this.refresh,1000)
						this.$Message.success('短信发送成功!')
					}
				})
			}
		},
        handleSendFinish () {
            this.sendStrat = false
            this.sendShow = false
            this.sendLabel = '重新发送'
            this.sendDisabled = false
            this.sendTime = 60
		},
        handleGetData (value, selectedData) {
			let labelArr = []
			selectedData.forEach(element => {
				labelArr.push(element.label)
			})
            this.certification.cityName = labelArr.join('/')
            this.changeAddr()
        },
        changeAddr () {
            if (this.certification.addrDetail && this.certification.cityName) {
                this.certification.addrView = `${this.certification.cityName} / ${this.certification.addrDetail}`
            } else if (!this.certification.addrDetail) {
                this.certification.addrView = `${this.certification.cityName}`
            }  else if (!this.certification.cityName) {
                this.certification.addrView = `${this.certification.addrDetail}`
            }
        }
    }

}
</script>
