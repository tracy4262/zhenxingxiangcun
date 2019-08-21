<template>
<div>
    <div class="add-service">
        <Form ref="data1" :model="data" :label-width="120"   label-position="left" :rules="ruleInline" >
            <FormItem label="服务名称" prop="service_name">
                <Input v-model="data.service_name" :maxlength="20"/>
            </FormItem>
            <FormItem label="简单描述">
                <Input type="textarea" v-model="data.simple_describe" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200"
                placeholder="请输入"></Input>
            </FormItem>
            <FormItem label="查看实况">
                <Input type="textarea" v-model="data.online_preview" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200"
                placeholder="请输入真实链接"></Input>
            </FormItem>
            <FormItem label="上传图片">
                 <vui-upload
                    ref="picture"
                    @on-getPictureList="getPictureList"
                    :hint="'图片大小小于2MB'"
                    :total="9"
                ></vui-upload>
            </FormItem>
            <FormItem label="服务介绍">
                 <quil-editor @quilCon="getServiceIntroduce" :content="data.service_describe"></quil-editor>
            </FormItem>
             <FormItem label="网点服务">
                <Button @click="selectBusinessOutlets" class="mr10">从基础设置中选择</Button> 
                <Button @click="handleAddBusinessOutlets">添加</Button>
            </FormItem>
        </Form>
    </div>
    <div style="width: 960px; margin: 0 auto;" class="pl100">
        <selectBusinessOutletCard ref="selectBusinessOutletCard" :datas="data.serviceOutlet" class="pl30"></selectBusinessOutletCard>
    </div>
    <div class="add-service">
        <Form ref="data2" :model="data" :label-width="120"   label-position="left" :rules="ruleInline" >
            <!-- <FormItem label="联系人" prop="contact_name">
                <Input v-model="data.contact_name" :maxlength="20"/>
            </FormItem>
            <FormItem label="QQ"  prop="qq_number">
                <Input v-model="data.qq_number" :maxlength="20"/>
            </FormItem>
            <FormItem label="联系电话" prop="phone">
                <Input v-model="data.phone" :maxlength="11"/>
            </FormItem>
            <FormItem label="邮箱" prop="email">
                <Input v-model="data.email" :maxlength="50"/>
            </FormItem>
            <FormItem label="联系地址">
                <vui-cascander :values="data.address" @handle-get-result="handleGetData"></vui-cascander>
                <Input class="mt20" v-model="data.addrs" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入详细地址" :maxlength="200" @on-change="changeAddr" ></Input>
                <Input type="textarea" class="mt20" v-model="data.addrView" :autosize="{minRows: 2,maxRows: 5}" readonly></Input>
            </FormItem>
            <FormItem label="经纬度">
                <Input v-model="data.coordinate" readonly @on-focus="onSelectPoint" />
            </FormItem>
            <FormItem label="选择交通引导">
                <Input type="textarea" :autosize="{minRows: 3,maxRows: 5}" v-model="data.traffic_lead" :maxlength="200"/>
            </FormItem> -->
            <FormItem label="开放时间段">
               <Input v-model="data.coordinate"  placeholder="例如：周一 9:00 — 12:00"/>
            </FormItem>
             <FormItem label="联系人信息" prop="contact">
                <Button @click="selectConcat" class="mr10">从基础设置中选择</Button> 
                <Button @click="handleAddConcat">添加</Button>
            </FormItem>
        </Form>
    </div>
    <div style="width: 960px; margin: 0 auto;" class="pl100">
        <concatCard ref="concatCard" :datas="data.contact" class="pl30"></concatCard>
    </div>
    <div class="add-service">
        <Form ref="data3" :model="data" :label-width="120"  label-position="left" :rules="ruleInline" >
            <FormItem label="公交路线指引">
                <Row v-for="(item, index) in data.busLead" :key="index" class="pb15">
                    <Col span="18"><Input v-model="item.name"  :maxlength="200"/></Col>
                    <Col span="6" class="pl15">
                        <Button @click="delBusLead(index)" v-if="data.busLead.length > 1" class="mr5">删除</Button>
                        <Button @click="addBusLead" v-if="index === 0">添加</Button>
                    </Col>
                </Row>
            </FormItem>
             <FormItem label="自驾路线指引">
                 <Row v-for="(item, index) in data.traffic_lead" :key="index" class="pb15">
                    <Col span="18"><Input v-model="item.name" :maxlength="200"/></Col>
                    <Col span="6" class="pl15">
                        <Button @click="delTrafficLead(index)" v-if="data.traffic_lead.length > 1" class="mr5">删除</Button>
                        <Button @click="addTrafficLead" v-if="index === 0" >添加</Button>
                    </Col>
                </Row>
            </FormItem>
        </Form>
        <div class="tc pt20">
            <Button type="primary" @click="handleBack">上一步</Button>
            <Button type="primary" @click="handleSave">下一步</Button>
            <Button type="text" @click="handleNext">以后再完善</Button>
        </div>
        
		<vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
        <selectBusinessOutlet ref="selectBusinessOutlet" @on-save="saveBusinessOutlet"></selectBusinessOutlet>
        <contact ref="contact" @on-save="saveConcat"></contact>
    </div>
</div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
	import vuiMap from '../../member/components/productionMap'
    import vuiCascander from '~components/vuiCascader/index'
    import quilEditor from '~components/vuequilEditor';    
    import {isEmail2,isQQ,isPhone} from '~utils/validate'
    import selectBusinessOutlet from '../../goFishing/components/selectBusinessOutlet'
    import selectBusinessOutletCard from '../../goFishing/components/selectBusinessOutletCard'
    import concatCard from '../../goFishing/components/concatCard'
    import contact from '../../goFishing/components/contact'
    import vuiSpecies from '~components/vui-species'
    export default {        
        components:{
            vuiUpload,
            vuiMap,
            vuiCascander,
            quilEditor,
            selectBusinessOutlet,
            selectBusinessOutletCard,
            concatCard,
            contact,
            vuiSpecies
        },
        data() {
            return{
                data:{
                    service_name:'', //服务名称
                    simple_describe:'', //描述
                    online_preview:'',//在线预览 => 查看实况
                    image_url:[], //上传图片
                    service_describe:'', //服务介绍
                    contact_name: '',// 联系人
                    qq_number:'', // qq_number
                    phone:'',//联系电话
                    email:'', //邮箱
                    address:'', //地址
                    addrs:'', //详细地址
                    coordinate:'', //经纬度
                    variety: [], // 品种
                    // season: '', // 季节
                    traffic_lead:[{name:''}], //交通引导 自驾路线指引
                    busLead: [{name:''}], // 公交 路线指引
                    serviceOutlet: [], // 服务网点
                    contact: [],  // 联系人信息
                    openingHours: '', // 开放时间段
                    id:''
                },
                ruleInline:{
                    service_name:[{required: true, message:'请输入服务名称', trigger: 'blur'}],
                    service_describe: [{required: true, message:'请填写服务介绍', trigger: 'blur'}],
                    contact: [
                        { required: true, type: 'array', min: 1, message: '填写联系人信息', trigger: 'change'}
                    ]
                },
                templateId: 0,
            }
        },
        created () {
            this.data.id = this.$route.query.id
            if(this.data.id){
                this.handleInit()
            }
            // 查询模板id
            this.$api.post('/member-reversion/user/userTemplateManage/find', {
                    account: this.$user.loginAccount
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.userTemplate) {
                            this.templateId = response.data.userTemplate.templateId
                        }
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
        },
        methods:{// 自驾路线指引
            addTrafficLead () {
                this.data.traffic_lead.push({name: ''})
            },
            delTrafficLead (index) {
                this.$Modal.confirm({
                    title: '是否确定删除',
                    content: '是否确认删除？',
                    onOk:()=>{
                        this.data.traffic_lead.splice(index,1)
                        this.$Message.success('删除成功！')
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            // 公交路线指引
            addBusLead () {
                this.data.busLead.push({name: ''})
            },
            delBusLead (index) {
                this.$Modal.confirm({
                    title: '是否确定删除',
                    content: '是否确认删除？',
                    onOk:()=>{
                        this.data.busLead.splice(index,1)
                        this.$Message.success('删除成功！')
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            // 点击添加网点
            handleAddBusinessOutlets () {
                this.data.serviceOutlet.push(
                    { 
                        edit: true,
                        status: true,
                        networkName: '', // 网点名字
                        networkType: [], // 网点类型
                        location: '', // 网点所在地
                        address: '', // 详细地址
                        houseNumber: '', // 门牌号
                        perfectAddress: '', //网点完整地址
                        longitude: '', // 东经
                        latitude: '', // 北纬
                        contact: '', // 联系人
                        officePhone: '', // 办公电话
                        phone: '' // 手机号码
                    }
                )
            },
            // 点击选择网点
            selectBusinessOutlets () {
                this.$refs['selectBusinessOutlet'].show = true
            },
            // 选择网点后点击确认
            saveBusinessOutlet (e) {
                this.data.serviceOutlet = e
            },
             // 点击添加 联系人
            handleAddConcat () {
                let list = {
                    user_id: this.account,
                    status: true,
                    member_name: '',
                    member_abbreviation: '',
                    contact_name: '',
                    card: '',
                    seat_phone: '',
                    phone: '',
                    qq_number: '',
                    wechat_number: '',
                    email: '',
                    website_url: '',
                    postal_code: '',
                    image: [],
                    image_status: true,
                    location: '',
                    location_status: true,
                    address: '',
                    addressPreview: '',
                    house_number: '',
                    address_status: true,
                    longitude: '',
                    latitude: '',
                    qr_code_contact_http: '',
                    qr_code_user_http: '',
                    isEdit: true,
                    member_name_id: '',
                    positioning: ''
                }
                this.data.contact.push(list)
            },
            // 点击选择 联系人
            selectConcat () {
                this.$refs['contact'].show = true
            },
            // 选择联系人后点击确认
            saveConcat (e) {
                this.data.contact = e
            },
             //初始化获取数据
            handleInit(){
                this.$api.post('/member/fishing/findFishingService',{id: this.data.id,pageNum:1}).then(response => {
                    if(response.code == 200){
                        if(response.data.list[0]){
                            this.data =  Object.assign(this.data, response.data.list[0]) 
                            this.$refs['picture'].handleGive(this.data.image_url)
                            // this.changeAddr()
                            if (!this.data.traffic_lead.length) {
                                this.data.traffic_lead = [{name:''}]
                            }
                            if (!this.data.busLead.length) {
                                this.data.busLead = [{name:''}]
                            }
                        }
                    }
                })
            },
            // 保存并继续
            handleSave () {
                let flag = true
                this.$refs['data1'].validate((valid) => {
                    if (!valid) {
                        flag = false
                    }
                })
                this.$refs['data2'].validate((valid) => {
                    if (!valid) {
                        flag = false
                    }
                })
                this.$refs['data3'].validate((valid) => {
                    if (!valid) {
                        flag = false
                    }
                })
                if (!this.$refs['concatCard'].handleValidate()) {
                    flag = this.$refs['concatCard'].handleValidate()
                }
                if (!this.$refs['selectBusinessOutletCard'].handleValidate()) {
                    flag = this.$refs['selectBusinessOutletCard'].handleValidate()
                }
                if (flag) {
                    this.$api.post('/member/fishing/updateFishingService',this.data).then(response=>{
                        if(response.code == 200){
                            this.$Message.success('保存成功')
                            this.$router.push(`/stayAddService/step3?id=${this.data.id}`)
                        }
                    })   
                } else {
                    this.$Message.error('请核对输入信息!');
                }
            },
            // 以后在完善            
            handleNext () {
                this.$router.push('/stay/service')
            },
            // 上一步
            handleBack () {
                this.$router.push('/stayAddService/step1?id=' + this.data.id)
            },
            //获取上传图片
            getPictureList(e){
                var arr = []
                e.forEach(element => {
                     if(element.response){
						arr.push(element.response.data.picName)
					}
                })
                this.data.image_url = arr
            },
            //获取服务介绍
            getServiceIntroduce(e){
                this.data.service_describe = e
            },
             //地区
            handleGetData (value, selectedData) {
				let labelArr = []
				selectedData.forEach(element => {
					labelArr.push(element.label)
				})
				this.data.address = labelArr.join('/')
                this.changeAddr()
            },
            changeAddr () {
                if (this.data.addrs && this.data.address) {
                    this.data.addrView = `${this.data.address} / ${this.data.addrs}`
                } else if (!this.data.addrs) {
                    this.data.addrView = `${this.data.address}`
                }  else if (!this.data.address) {
                    this.data.addrView = `${this.data.addrs}`
                }
            },
            //坐标
            onSelectPoint() {
				this.$refs.experMap.showMap = true
            },
            // 取坐标
			onGetPoint(point) {
				if(point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
					this.data.coordinate = `${point.lng},${point.lat}`
				} else {
					this.data.coordinate = ''
				}
			},
        }
    }
</script>
