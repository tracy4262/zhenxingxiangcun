<template>
  <div class=" pt30 pl10 pr10">
      <Form ref="data" label-position="left"  :rules="ruleInline"  :label-width="150" :model="data">
		  <Row :gutter="32">
              <Col span="24">
                <FormItem label="权限">
					<i-switch  v-model="data.expert_status" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
                </FormItem>
              </Col>
          </Row>
          <Row :gutter="32">
              <Col span="12">
                <FormItem label="专家分类" prop="expertType">
                    <Select v-model="data.expertType">
                        <Option v-for="(f,index) in expertTypeList" :value="f.value" :key="index" clearable>{{ f.label }}</Option>
                    </Select>
                </FormItem>
              </Col>
			<Col span="12">
                <FormItem label="职称" prop="title">
                    <Input v-model="data.title" readonly class="pinput-clear" @on-focus="handleFilterModal('zhichengFilter')" icon="ios-close" @on-click="inputClear('zhichengFilter')"/>
                </FormItem>
              </Col>
          </Row>
          <Row :gutter="32">
               <Col span="12">
                <FormItem label="擅长领域" prop="excelField">
                    <Input v-model="data.excelField" clearable/>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="擅长物种" prop="excelSpecies">
                   <Input v-model="data.excelSpecies" readonly class="pinput-clear" @on-focus="handleFilterModal('speciFilter2')" icon="ios-close" @on-click="inputClear('speciFilter2')"/>
                </FormItem>
              </Col>
          </Row>
          <Row :gutter="32">
               <Col span="12">
                <FormItem label="相关物种" prop="relatedSpecies">
                    <Input v-model="data.relatedSpecies" readonly class="pinput-clear" @on-focus="handleFilterModal('speciFilter')" icon="ios-close" @on-click="inputClear('speciFilter')"/>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="相关行业" prop="relatedIndustries">
                    <Input v-model="data.relatedIndustries" readonly class="pinput-clear" @on-focus="handleFilterModal('tradeFilter')" icon="ios-close" @on-click="inputClear('tradeFilter')" />
                </FormItem>
              </Col>
          </Row>
          <Row :gutter="32">
               <Col span="12">
                <FormItem label="相关商品">
                    <Input v-model="data.relatedGoods" clearable/>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="相关服务" prop="relatedServices">
                    <Input v-model="data.relatedServices" readonly class="pinput-clear" @on-focus="handleFilterModal('serviceFilter')" icon="ios-close" @on-click="inputClear('serviceFilter')" />
                </FormItem>
              </Col>
          </Row>
          <FormItem label="上传个人照片" prop="personalPicture">
                <vui-upload
                    ref="personalPicture"
                    @on-getPictureList="getPersonalList"
                    :pictureLists="data.personalPicture"
                    :hint="'图片大小小于2MB'"
                    :total="9"
                ></vui-upload>
          </FormItem>
           <FormItem label="上传资质证书" prop="qualificationsPicture">
                <vui-upload
                    ref="qualificationsPicture"
                    @on-getPictureList="getQualificationsList"
                    :pictureLists="data.qualificationsPicture"
                    :hint="'图片大小小于2MB'"
                    :total="9"
                ></vui-upload>
          </FormItem>
           <FormItem label="上传荣誉证书" prop="honorPicture">
               <vui-upload
                    ref="honorPicture"
                    @on-getPictureList="getHonorList"
                    :pictureLists="data.honorPicture"
                    :hint="'图片大小小于2MB'"
                    :total="9"
                ></vui-upload>
          </FormItem>
      </Form>
      <!-- 擅长物种 -->
		<vui-filter 
            ref="speciFilter2" 
            :cols="2" 
            :num="4" 
            :pageShow="true" 
            :total="total2" 
            :pageCur="pageCur2" 
            :classifyDatas="speciClassifyDatas" 
            :resultDatas="speciResultDatas2" 
            :load-data="loadSpeciDatas" 
            @on-search="handleSpeciSearch2" 
            @on-get-classify="handleGetSpeciClassify2" 
            @on-get-result="handleGetSpeciResult2" 
            @on-page-change="handleSpeciPageChange2" />
        <!-- 相关物种 -->
		<vui-filter 
            ref="speciFilter" 
            :cols="2" 
            :num="4" 
            :pageShow="true" 
            :total="total" 
            :pageCur="pageCur" 
            :classifyDatas="speciClassifyDatas" 
            :resultDatas="speciResultDatas" 
            :load-data="loadSpeciDatas" 
            @on-search="handleSpeciSearch" 
            @on-get-classify="handleGetSpeciClassify" 
            @on-get-result="handleGetSpeciResult"
            @on-page-change="handleSpeciPageChange" />
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
		<!-- 职称 -->
		<vui-filter
			ref="zhichengFilter"
			:num="1"
			:simple="true"
			:pageShow="true"
			:total="total4"
			:pageCur="pageCur4"
			:resultDatas="zhichengResultDatas"
			@on-search="handleZhichengSearch"
			@on-page-change="handleZhichengPageChange"
			@on-get-result="handleGetZhichengResult"/>
  </div>
</template>
<script>
	import api from '~api'
	import vuiFilter from '~components/vuiFilter/filter'
    import vuiUpload from '~components/vui-upload'
    export default{
        components:{
            vuiFilter,
            vuiUpload
        },
        data(){
            return{  
                relatedServices:[],
                picType:'',
				total: 0,
                pageCur: 1,   
				speciResultDatas: [],          
                total2: 0,
				pageCur2: 1,
				speciClassifyDatas: [{
						label: '动物',
						value: '0',
						classId: '',
						loading: false,
						checked: false,
						children: []
					},
					{
						label: '植物',
						value: '1',
						classId: '',
						loading: false,
						checked: false,
						children: []
					}
				],
				speciResultDatas2: [],
				total3: 0,
                pageCur3: 1,
				tradeResultDatas: [],
				serviceResultDatas: [],
				total4: 0,
                pageCur4: 1,
                zhichengResultDatas:[],
                expertTypeList: [
					{
						value: '农业',
						label: '农业'
					},
					{
						value: '畜牧',
						label: '畜牧'
					},
					{
						value: '渔业',
						label: '渔业'
					},
					{
						value: '林业',
						label: '林业'
					},
					{
						value: '土肥',
						label: '土肥'
					},
					{
						value: '饲料',
						label: '饲料'
					},
					{
						value: '农机',
						label: '农机'
					},
					{
						value: '法律',
						label: '法律'
					},
					{
						value: '兽药',
						label: '兽药'
					},
					{
						value: '加工',
						label: '加工'
					},
					{
						value: '农药',
						label: '农药'
					},
					{
						value: '包装',
						label: '包装'
					},
					{
						value: '质检',
						label: '质检'
					},
					{
						value: '运输',
						label: '运输'
					},
					{
						value: '信息传输',
						label: '信息传输'
					},
					{
						value: '金融',
						label: '金融'
					},
					{
						value: '保险业',
						label: '保险业'
					}
                ],
                data:{
					expert_status: true,
					expertType:'',
					title: '',
                    excelField:'',
                    excelSpecies:'',
                    relatedSpecies:'',
                    relatedIndustries:'',
                    relatedGoods:'',
                    relatedServices:'',
                    personalPicture:[],
                    qualificationsPicture:[],
                    honorPicture:[]
                },
                ruleInline:{
                    expertType:[{required: true,message:'请选择专家分类'}],
                    title:[{required: true,message:'请选择职称'}],
                    excelField:[{required: true,message:'请输入擅长领域'}],
                    excelSpecies:[{required: true,message:'请选择擅长物种'}],
                    relatedSpecies:[{required: true,message:'请选择相关物种'}],
                    relatedIndustries:[{required: true,message:'请选择相关行业'}],
                    relatedServices:[{required: true,message:'请选择相关服务'}],
                    personalPicture:[{required: true, message: '请上个人照片', type: 'array', min: 1, trigger: 'change'}],
                    qualificationsPicture:[{required: true, message: '请上传资质证书', type: 'array', min: 1, trigger: 'change'}],
                    honorPicture:[{required: true, message: '请上传荣誉证书', type: 'array', min: 1, trigger: 'change'}]
                }
            }
        },
        created(){
            // 取行业类型数据
			this.$api.post('/member/system-dict/getSystemDict', {
				typeName:'行业分类',
				pageNum: 1
			}).then(res => {
				console.log('reshhh', res)
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
			 // 职称数据
            this.$api.post('/member/system-dict/getSystemDict', {
                typeName:'职称',
                pageNum: 1,
                pageSize: 32
            }).then(res => {
                var data = res.data.list
                this.total4 = res.data.total
                this.zhichengResultDatas = data
            })
			// 取相关物种结果
			this.loadSpeciResult('', '', [], this.pageCur, [])
			// 取擅长物种结果
			this.loadSpeciResult2('', '', [], this.pageCur2, [])
        },
        mounted(){
            this.$refs['data'].resetFields();
        },
        methods:{
			//清除文本框
			inputClear(item){
				if (item == 'speciFilter2') {
					this.data.excelSpecies = ''
                    this.$refs.speciFilter2.handleReset();
                    this.handleGetSpeciResult2('', []);
				}
				if (item == 'speciFilter') {
					this.data.relatedSpecies = ''
                    this.$refs.speciFilter.handleReset();
                    this.handleGetSpeciResult('', []);
				}
				if (item == 'tradeFilter') {
					this.data.relatedIndustries = ''
                    this.$refs.tradeFilter.handleReset();
                    this.handleGetTradeResult('', []);
				}
				if (item == 'serviceFilter') {
					this.data.relatedServices = ''
                    this.$refs.serviceFilter.handleReset();
                    this.handleGetServiceResult('', []);
				}
				if (item == 'zhichengFilter') {
					this.data.title = ''
                    this.$refs.zhichengFilter.handleReset();
                    this.handleGetZhichengResult('', []);
				}
			},
			//接收数据
			getData(val){
				this.data = Object.assign({title: ''}, val)
				this.$refs['qualificationsPicture'].handleGive(this.data.qualificationsPicture)				
				this.$refs['personalPicture'].handleGive(this.data.personalPicture)
				this.$refs['honorPicture'].handleGive(this.data.honorPicture)
			},
			//下一步表单验证
			handleSubmit(){
				var obj = this.data
				var flag = false
				for (let key in obj) {
					if (obj[key] && obj[key].length) {
						flag = true
					}
				}
				if(flag){
					this.$refs.data.validate ( (valid) => {
						if(valid) {                        
							this.$emit('on-submit',true)
						}else{
							this.$emit('on-submit',false)
						}
					})					
				}else{
					this.$emit('on-submit',true)
				}
			},
            //获取个人照片
            getPersonalList (e) {
                var arr = []
                e.forEach(element => {
                     if(element.response){
						arr.push(element.response.data.picName)
					}
                })
                this.data.personalPicture = arr
            },
            // 获取资质证书
            getQualificationsList (e) {                
                var arr = []
                e.forEach(element => {
					if(element.response){
						arr.push(element.response.data.picName)
					}
                })
                this.data.qualificationsPicture = arr
            },
            // 上传荣誉证书
            getHonorList (e) {
                var arr = []
                e.forEach(element => {
					if(element.response){
						arr.push(element.response.data.picName)
					}
                })
                this.data.honorPicture = arr
            },
            // 高级搜索弹窗
            handleFilterModal (name) {
                this.$refs[name].highFilterShow = true
			},
			// 职称搜索
            handleZhichengSearch (letter, keyword, result) {
                api.post(`/member/system-dict/getSystemDict`, {
                    dictName : keyword,
                    typeName : '职称',
                    character: letter === '全部' ? '' : letter,
                    pageNum: 1,
                    pageSize: 32
                }).then(res => {
                    var d = res.data.list
                    this.total4 = res.data.total
                    this.pageCur4 = 1
                    this.zhichengResultDatas = []
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
                    this.zhichengResultDatas = d
                })
			},
			handleZhichengPageChange (letter, keyword, classify, num, result) {
                this.pageCur4 = num
                api.post('/member/system-dict/getSystemDict', {
                    typeName:'职称',
                    character: letter === '全部' ? '' : letter,
                    dictName: keyword,
                    pageNum: num,
                    pageSize: 32
                }).then(res => {
                    var data = res.data.list
                    this.total4 = res.data.total
                    this.zhichengResultDatas = data
                })
                this.$Message.success('筛选完成！')
			},
			handleGetZhichengResult (classify, result) {
                var arr = []
                result.forEach(item => arr.push(item.label))
                this.data.title = arr.join(' ')
            },
            // 物种
            loadSpeciDatas(item, callback) {
				item.loading = true
				api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
					item.loading = false
					var d = res.data
					d.forEach(child => {
						child.checked = false
						child.label = child.className
					})
					item.children = d
					callback()
				})
            },
            // 擅长物种
			handleGetSpeciResult2(classify, result) {
				var arr = []
				this.adpetSpecies = []
				result.forEach(item => arr.push(item.label))
				this.data.excelSpecies = arr.join(' ')
				result.forEach(item => this.adpetSpecies.push(item.value))				
			},
            handleSpeciSearch2(letter, keyword, classify, result) {
				this.loadSpeciResult2(letter, keyword, classify, this.pageCur2, result)
				this.$Message.success('筛选完成！')
            },
            handleGetSpeciClassify2(letter, keyword, classify, result) {
				this.loadSpeciResult2(letter, keyword, classify, this.pageCur2, result)
				this.$Message.success('筛选完成！')
            },
            handleSpeciPageChange2(letter, keyword, classify, num, result) {
				this.pageCur2 = num
				this.loadSpeciResult2(letter, keyword, classify, this.pageCur2, result)
				this.$Message.success('筛选完成！')
            },
            loadSpeciResult2(letter, keyword, classify, num, result) {
				if(classify.length) {
					var arr = []
					var type = ''
					classify.forEach(item => {
						arr.push(item.classId)
						if(item.value !== undefined) type = item.value
					})
				} else {
					arr = null
				}
				api.post('/member/specicesClass/findSpecies', {
					keywords: keyword,
					fpinyin: letter === '全部' ? '' : letter,
					fclassifiedid: arr,
					type: type,
					pageNum: num,
					pageSize: 32
				}).then(res => {
					var data = res.data
					this.total2 = data.total
					if(result) {
						result.forEach(item => {
							data.list.forEach((child, index) => {
								if(child.label === item.label) {
									child.checked = true
								}
							})
						})
					} else {
						data.list.forEach(child => {
							child.checked = false
						})
					}
					this.speciResultDatas2 = data.list
				})
            },
            // 相关物种
			handleSpeciSearch(letter, keyword, classify, result) {
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
				this.$Message.success('筛选完成！')
			},
			handleGetSpeciClassify(letter, keyword, classify, result) {
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
				this.$Message.success('筛选完成！')
			},
			handleGetSpeciResult(classify, result) {
				var arr = []
				this.speciesResult = []
				result.forEach(item => arr.push(item.label))
				result.forEach(item => this.speciesResult.push(item.value))
				this.data.relatedSpecies = arr.join(' ')
			},
			handleSpeciPageChange(letter, keyword, classify, num, result) {
				this.pageCur = num
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
				this.$Message.success('筛选完成！')
            },
            loadSpeciResult(letter, keyword, classify, num, result) {
				if(classify.length) {
					var arr = []
					var type = ''
					classify.forEach(item => {
						arr.push(item.classId)
						if(item.value !== undefined) type = item.value
					})
				} else {
					arr = null
				}
				api.post('/member/specicesClass/findSpecies', {
					keywords: keyword,
					fpinyin: letter === '全部' ? '' : letter,
					fclassifiedid: arr,
					type: type,
					pageNum: num,
					pageSize: 32
				}).then(res => {
					var data = res.data
					this.total = data.total
					if(result) {
						result.forEach(item => {
							data.list.forEach((child, index) => {
								if(child.label === item.label) {
									child.checked = true
								}
							})
						})
					} else {
						data.list.forEach(child => {
							child.checked = false
						})
					}
					this.speciResultDatas = data.list
				})
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
				this.classfy = []
				result.forEach(item => arr.push(item.label))
				this.data.relatedIndustries = arr.join(' ')
				result.forEach(item => this.classfy.push(item.value))
            },
            handleServiceSearch(letter, keyword, classify) {
				api.post(`/member/service/find`, {
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
				this.relatedServices = []
				result.forEach(item => arr.push(item.label))
				this.data.relatedServices = arr.join(' ')
				result.forEach(item => this.relatedServices.push(item.value))
			},
        }
    }
</script>
