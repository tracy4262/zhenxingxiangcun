<template>
<div class="add-service">
    <Form ref="data" :model="data" :label-width="120" label-position="left" :rules="ruleInline" >
        <FormItem label="通用服务名称" prop="currency_service_name">
             <!-- <Input v-model="data.currency_service_name" :maxlength="200"/> -->
             <Input v-model="data.currency_service_name" readonly></Input>
        </FormItem>
        <FormItem label="行业分类">
             <Input v-model="data.trade_class_id" readonly></Input>
            <!-- <Input v-model="data.trade_class_id" readonly class="pinput-clear" @on-focus="handleFilterModal('tradeFilter')" icon="ios-close" @on-click="inputClear('tradeFilter')" /> -->
        </FormItem>
        <FormItem label="服务分类" prop="service_class_id">
             <Input v-model="data.service_class_id" readonly></Input>
            <!-- <Input v-model="data.service_class_id" readonly class="pinput-clear" @on-focus="handleFilterModal('serviceFilter')" icon="ios-close" @on-click="inputClear('serviceFilter')" /> -->
        </FormItem>
    </Form>
      <div class="tc pt20">
        <Button type="primary" @click="handleSave">下一步</Button>
        <Button type="text" @click="handleNext">以后再完善</Button>
    </div>
    <!-- 相关行业 -->
    <vui-filter
        ref="tradeFilter"
        :num="4"
        :simple="true"
        :pageShow="true"
        :cols="2"
        :total="total3"
        :pageCur="pageCur3"
        :resultDatas="tradeResultDatas"
        @on-search="handleTradeSearch"
        @on-page-change="handleTradePageChange"
        @on-get-result="handleGetTradeResult" />
    <!-- 相关服务 -->
    <vui-filter 
        ref="serviceFilter"  
        :num="4" 
        :simple="true" 
        :cols="2" 
        :resultDatas="serviceResultDatas" 
        @on-search="handleServiceSearch" 
        @on-get-result="handleGetServiceResult" />
</div>
</template>
<script>
	import vuiFilter from '~components/vuiFilter/filter'
    export default {
        components:{
            vuiFilter
        },
        data() {
            return{
                total3: 0,
                pageCur3: 1,
				tradeResultDatas: [],
				serviceResultDatas: [],
                data:{
                    currency_service_name:'',
                    trade_class_id:'',
                    service_class_id:'',
                    account:''
                },
                ruleInline:{
                    currency_service_name:[{required: true,message:'请输入通用服务名称', trigger: 'blur'}],
                    // service_class_id:[{required: true,message:'请选择服务分类', trigger: 'change'}]
                },
                id:'',
                pageNum:'',
                loginUser:JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            }
        },
        created(){
             // 取行业类型数据
			this.$api.post('/member/system-dict/getSystemDict', {
				typeName:'行业分类',
				pageNum: 1
			}).then(res => {
				var data = res.data.list
				this.total3 = res.data.total
				this.tradeResultDatas = data
			})
			// 取相关服务数据
			this.$api.post('/member/service/find', {}).then(res => {
				var data = res.data
				data.forEach((item, index) => {
					item.checked = false
				})
				this.serviceResultDatas = data
            })
            this.id = this.$route.query.id
            this.data.account = this.loginUser.loginAccount
            if(this.id){
                this.handleInit()
            }
            // 服务应用 查询
            this.init('3', '', '')
        },
        methods:{
            init (level, recommend, flag) {
                this.$api.post('/member/applicationCentrality/findList', 
                    {
                    level: level, // level 0 基础 1 通用 2 高级
                    recommend: recommend, // recommend 1 推荐 0 不推荐
                    account: this.$user.loginAccount,
                    templateId: this.templateId,
                    appName: this.keyword,
                    flag: flag // 1 最新，0 最热
                    }
                ).then(response => {
                    if (response.code === 200) {
                    // level 0 基础 1 通用 2 高级 3 服务
                        if (level === '3') {
                            response.data.forEach(e => {
                                if (e.url == '/stay/roomType') {
                                    this.data.currency_service_name = e.appName
                                    this.data.service_class_id = e.serviceTypeName
                                    this.data.trade_class_id = e.industryName
                                }
                            })
                        }
                    }
                })
            },
            //初始化获取数据
            handleInit(){
                this.$api.post('/member/fishing/findFishingService',{id: this.id,pageNum:1}).then(response => {
                    if(response.code == 200){
                        if(response.data.list[0]){
                            this.data = response.data.list[0]
                        }
                    }
                })
            },
            // 保存并继续
            handleSave () {
                this.$refs['data'].validate((valid) => {
                    if (valid) {
                        // 0 垂钓 1采摘 2景区 3餐饮 4住宿
                        this.data.type = 4
                        //说明是编辑
                        if(this.id){
                            this.$api.post('/member/fishing/updateFishingService',this.data).then(response=>{
                                if(response.code == 200){
                                    this.$Message.success('保存成功')
                                    this.$router.push(`/stayAddService/step2?id=${this.data.id}`)
                                }
                            })
                        } else {
                            this.$api.post('/member/fishing/insertFishingService',this.data).then(response=>{
                                if(response.code == 200){
                                    this.$Message.success('保存成功')
                                    this.$router.push(`/stayAddService/step2?id=${response.data}`)
                                }
                            })
                        }
                    } else {
                        this.$Message.error('请核对输入信息!');
                    }
                })                
            },
            // 以后在完善            
            handleNext () {
                this.$router.push('/stay/service')
            },
            //清除文本框
			inputClear(item){
				if (item == 'tradeFilter') {
					this.data.trade_class_id = ''
                    this.$refs.tradeFilter.handleReset();
                    this.handleGetTradeResult('', []);
                }
                if (item == 'serviceFilter') {
					this.data.service_class_id = ''
                    this.$refs.serviceFilter.handleReset();
                    this.handleGetServiceResult('', []);
				}
            },
            // 高级搜索弹窗
            handleFilterModal (name) {
                this.$refs[name].highFilterShow = true
            },
            // 行业类型
            handleTradeSearch (letter, keyword, result) {
                this.$api.post(`/member/system-dict/getSystemDict`, {
					dictName : keyword,
					typeName : '行业分类',
					character: letter === '全部' ? '' : letter,
					pageNum: 1
				}).then(res => {
					var d = res.data.list
					this.total3 = res.data.total
					this.pageCur3 = 1
					this.tradeResultDatas = []
					if (!d.length) return 
					if (result.length) {
						result.forEach(item => {
							d.forEach(child => {
								if (child.label === item.label) {
									child.checked = true
									child.label = child.label
									child.value = child.value
								}
							})
						})
					} else {
						d.forEach(child => {
							child.checked = false
							child.label = child.label
							child.value = child.value
						})
					}
					this.tradeResultDatas = d
				})
            },
            handleTradePageChange (letter, keyword, classify, num, result) {
                this.pageCur3 = num
                this.$api.post('/member/system-dict/getSystemDict', {
					typeName:'行业分类',
					character: letter === '全部' ? '' : letter,
					dictName: keyword,
					pageNum: num
				}).then(res => {
					var data = res.data.list
					this.total3 = res.data.total
					this.tradeResultDatas = data
				})
                this.$Message.success('筛选完成！')
            },
            handleGetTradeResult (classify, result) {
                var arr = []
				result.forEach(item => arr.push(item.label))
				this.data.trade_class_id = arr.join(' ')
            },
            handleServiceSearch(letter, keyword, classify) {
				this.$api.post(`/member/service/find`, {
					character: letter === '全部' ? '' : letter,
					keyword: keyword
				}).then(res => {
					var d = res.data
					this.serviceResultDatas = []
					if(d.length) {
						if(classify.length) {
							classify.forEach(item => {
								d.forEach(child => {
									if(child.label === item.label) {
										child.checked = true
									}
								})
							})
						} else {
							d.forEach(child => {
								child.checked = false
							})
						}
						this.serviceResultDatas = d
						this.$Message.success('筛选完成！')
					}
				})
			},
			handleGetServiceResult(classify, result) {
				var arr = []
				result.forEach(item => arr.push(item.label))
				this.data.service_class_id = arr.join(' ')
			}
        }
    }
</script>
<style>
   .add-service{
       width:700px;
       margin:0 auto;
   } 
</style>