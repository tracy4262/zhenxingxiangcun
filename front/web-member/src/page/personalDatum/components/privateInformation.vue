<template>
	<div class="pt30 pl10 pr10">
			<Form ref="data" label-position="left"  :label-width="80" :model="data">
                <Row :gutter="32">
                    <Col span="12" v-if="data.userName.status">
                        <FormItem label="姓名">
                            {{data.userName.model}}
						</FormItem>
                    </Col>
                    <Col span="12" v-if="data.sex.status">
                        <FormItem label="性别">
                            {{data.sex.model}}
                        </FormItem>                        
                    </Col>
                    <Col span="12" v-if="data.ethnicGroup.status">
                        <FormItem label="民族">
                            {{data.ethnicGroup.model}}
                        </FormItem>                    
                    </Col>
                    <Col span="12" v-if="data.birthday.status">
                        <FormItem label="生日">
                            {{data.birthday.model}}
                        </FormItem>          
                    </Col>
                    <Col span="12" v-if="data.profession.status">
                        <FormItem label="职业">
                            {{data.profession.model}}
                        </FormItem>            
                    </Col>
                    <Col span="12" v-if="data.professionalTitle.status">
                        <FormItem label="职称">
                            {{data.professionalTitle.model}}
                        </FormItem>
                    </Col>
                    <Col span="12" v-if="data.species.status">
                        <FormItem label="擅长物种">
                            {{data.species.model}}
                        </FormItem>
                    </Col>
                    <Col span="12" v-if="data.phone.status">
                        <FormItem label="手机号码" prop="phone">
                            {{data.phone.model}}
                        </FormItem>
                    </Col>
                     <Col span="12" v-if="data.number.status">
                        <FormItem label="门牌号">
                            {{data.number.model}}
                        </FormItem>
                    </Col>
                    <Col span="12" v-if="data.coordinatePoint.status">
                        <FormItem prop="coordinatePoint" label="坐标位置">
                            {{data.coordinatePoint.model}}
                        </FormItem>
                    </Col>
                    <Col span="12" v-if="data.postalCode.status">
                        <FormItem label="邮政编码" prop="postalCode">
                            {{data.postalCode.model}}
                        </FormItem>
                    </Col>
                    <Col span="12" v-if="data.tel.status">
                        <FormItem label="座机号码" prop="tel">
                            {{data.tel.model}}
                        </FormItem>
                    </Col>
                    <Col span="12" v-if="data.addr.status">
                        <FormItem label="常驻地">
                            {{data.addr.model}}
                        </FormItem>
                    </Col>
                    <Col span="12" v-if="data.addrDetail.status">
                        <FormItem label="详细地址">
                            {{data.addrDetail.model}}
                        </FormItem>
                    </Col>
                </Row>
			</Form>
	</div>
</template>
<script>
	import api from "~api"
	export default {
		props:{
			base:{
				type:Boolean,
				default:true
			}
		},
		data() {			
			return {
                families:[],
                data:{
                    avatar:'',
                    userName:{model:'',name:'姓名',status:true},
                    sex:{model:'',name:'性别',status:true},
                    ethnicGroup:{model:'',name:'民族',status:true},
                    birthday:{model:'',name:'生日',status:true},
                    profession:{model:'',name:'职业',status:true},
                    professionalTitle:{model:'',name:'职称',status:true},
                    species:{model:'',name:'擅长物种',status:true},
                    phone:{model:'',name:'手机号码',status:true},
                    addr:{model:'',name:'常住地',status:true},
                    addrDetail:{model:'',name:'详细地址',status:true},
                    number:{model:'',name:'门牌号',status:true},
                    coordinatePoint:{model:'',name:'坐标位置',status:true},
                    postalCode:{model:'',name:'邮政编码',status:true},
                    tel:{model:'',name:'座机号码',status:true},
                },
			};
		},
		created() {
		},
		methods: {
            //接收数据
			getData(val){
                this.data = val
			},
		},
		mounted() {
		}
	};
</script>