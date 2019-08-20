<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" />
    <Form ref="formItem" :model="form" label-position="left" :label-width="100" :rules="formItemInline" class="mt40">
        <Row>
            <Col span="12">
                <Form-item label="权限">
                    <i-switch  v-model="form.status" size="large" :disabled="isAdd">
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="8">
                <FormItem label="专家分类">
                    <Select v-model="form.expertType" @on-change="change" :disabled="isAdd">
                        <Option v-for="(f,index) in expertTypeList" :value="f.value" :key="index" clearable>{{ f.label }}</Option>
                    </Select>
                </FormItem>
                </Col>
            <Col span="8">
                <FormItem label="职称">
                    <Input v-model="form.title" readonly :disabled="isAdd" class="pinput-clear" @on-focus="handleFilterModal('zhichengFilter')" @on-click="inputClear('zhichengFilter')" @on-change="change" />
                </FormItem>
            </Col>
            <Col span="8">
                <FormItem label="擅长领域">
                    <Input v-model="form.excelField" :disabled="isAdd" clearable @on-change="change" />
                </FormItem>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="8">
                <FormItem label="擅长物种">
                    <Input v-model="form.excelSpecies" :disabled="isAdd" readonly class="pinput-clear" @on-focus="handleFilterModal('speciFilter2')" @on-click="inputClear('speciFilter2')" @on-change="change" />
                </FormItem>
            </Col>
            <Col span="8">
                <FormItem label="相关物种">
                    <Input v-model="form.relatedSpecies" :disabled="isAdd" readonly class="pinput-clear" @on-focus="handleFilterModal('speciFilter')" @on-click="inputClear('speciFilter')" @on-change="change" />
                </FormItem>
            </Col>
            <Col span="8">
                <FormItem label="相关行业">
                    <Input v-model="form.relatedIndustries" :disabled="isAdd" readonly class="pinput-clear" @on-focus="handleFilterModal('tradeFilter')" @on-click="inputClear('tradeFilter')" @on-change="change" />
                </FormItem>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="8">
                <FormItem label="相关商品">
                    <Input v-model="form.relatedGoods" :disabled="isAdd" clearable @on-change="change" />
                </FormItem>
            </Col>
            <Col span="8">
                <FormItem label="相关服务">
                    <Input v-model="form.relatedServices" :disabled="isAdd" readonly class="pinput-clear" @on-focus="handleFilterModal('serviceFilter')" @on-click="inputClear('serviceFilter')" @on-change="change" />
                </FormItem>
            </Col>
        </Row>
        <FormItem label="上传个人照片">
            <vui-upload
            :disabled="isAdd"
                ref="personalPicture"
                @on-getPictureList="getPersonalList"
                :pictureLists="form.personalPicture"
                :hint="'图片大小小于2MB'"
                :total="9"
            ></vui-upload>
        </FormItem>
        <FormItem label="上传资质证书">
            <vui-upload
            :disabled="isAdd"
                ref="qualificationsPicture"
                @on-getPictureList="getQualificationsList"
                :pictureLists="form.qualificationsPicture"
                :hint="'图片大小小于2MB'"
                :total="9"
            ></vui-upload>
        </FormItem>
        <FormItem label="上传荣誉证书">
            <vui-upload
            :disabled="isAdd"
                ref="honorPicture"
                @on-getPictureList="getHonorList"
                :pictureLists="form.honorPicture"
                :hint="'图片大小小于2MB'"
                :total="9"
            ></vui-upload>
        </FormItem>
    </Form>
    <Title title="文字预览"/>
    <div class="pd20 tc pt30 mt20">
        <Input v-model="preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        <Button type="primary" @click="handleSave()" class="mt40">保存</Button>
    </div>
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
    import vuiUpload from '~components/vui-upload'
    import vuiFilter from '~components/vuiFilter/filter'
    import Title from '../../components/title'
    export default {
        components: {
            vuiUpload,
            Title,
            vuiFilter
        },
        props: {
            modeId: {
                type: String
            },
            yearId: {
                type: String
            },
            appId: {
                type: String
            }
        },
        data () {
            return {
                title: '专业信息',
                formItemInline: {},
                isAdd: true,
                form: {
                    status: true,
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
                options: {
                    disabledDate (date) {
                        return date && date.valueOf() > Date.now()
                    }
                },
                preview: '',
                relatedServices:[],
				total: 0,
                pageCur: 1,   
				speciResultDatas: [],          
                total2: 0,
				pageCur2: 1,
				speciClassifyDatas: [
                    {
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
                ]
            }
        },
        watch: {
            modeId: {
                handler (newValue, oldValue) {
                    this.init()
                },
                deep: true
            }
        },
        created () {
            if (this.modeId !== '' && this.modeId !== undefined) {
                this.init()
            }
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
        mounted () {
            this.$refs['formItem'].resetFields()
        },
        methods: {
            // 初始化加载数据
            init () {
                this.$api.post('/member-reversion/expertInfo/findExpertInfo', {
                    user_id: this.$user.loginAccount,
                    year_id: this.yearId,
                    templateId: this.$template.id,
                    parent_id: this.modeId
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.expertInfo_name && response.data.expertInfo_name !== '') {
                            this.title = response.data.expertInfo_name
                        }
                        if (response.data.textPreview.text_preview && response.data.textPreview.text_preview !== '') {
                            this.preview = response.data.textPreview.text_preview
                            this.id = response.data.textPreview.id
                        }
                        // 判断对象是否为空
                        if (JSON.stringify(response.data.expertInfo) !== '{}') {
                            this.form = {
                                id: response.data.expertInfo.id,
                                status: response.data.expertInfo.status === '1' ? true : false,
                                expertType: response.data.expertInfo.expertType,
                                title: response.data.expertInfo.title,
                                excelField: response.data.expertInfo.excelField,
                                excelSpecies: response.data.expertInfo.excelSpecies,
                                relatedSpecies: response.data.expertInfo.relatedSpecies,
                                relatedIndustries: response.data.expertInfo.relatedIndustries,
                                relatedGoods: response.data.expertInfo.relatedGoods,
                                relatedServices: response.data.expertInfo.relatedServices,
                                personalPicture: response.data.expertInfo.personal_photo,
                                qualificationsPicture: response.data.expertInfo.qualification_certificate,
                                honorPicture: response.data.expertInfo.honor_certificate
                            }
                            this.$refs.personalPicture.handleGive(this.form.personalPicture)
                            this.$refs.qualificationsPicture.handleGive(this.form.qualificationsPicture)
                            this.$refs.honorPicture.handleGive(this.form.honorPicture)
                        }
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 保存
            handleSave () {
                this.$api.post('/member-reversion/expertInfo/insertExpertInfo', {
                    user_id: this.$user.loginAccount,
                    templateId: this.$template.id,
                    yearId: this.yearId,
                    parent_id: this.appId,
                    expertInfo_name: this.title,
                    status: this.form.status,
                    sys_dict_id: this.modeId,
                    textPreview: {
                        id: this.id === '' || this.id === undefined ? 0 : this.id,
                        text_preview: this.preview,
                        is_complete: true
                    },
                    expertInfo: {
                        id: this.form.id === '' || this.form.id  === undefined ? 0 : this.form.id,
                        expert_type: this.form.expertType,
                        title: this.form.title,
                        adept_field: this.form.excelField,
                        adept_species: this.form.excelSpecies,
                        related_species: this.form.relatedSpecies,
                        related_industry: this.form.relatedIndustries,
                        related_product: this.form.relatedGoods,
                        related_service: this.form.relatedServices,
                        personal_photo: this.form.personalPicture,
                        qualification_certificate: this.form.qualificationsPicture,
                        honor_certificate: this.form.honorPicture
                    }
                }).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init()
                        this.$emit('on-save')
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 拼接文字预览
            change () {
                this.preview = ''
                if (this.form.expertType && this.form.expertType !== '') {
                    this.preview += `专家分类：${this.form.expertType}，`
                }
                if (this.form.title && this.form.title !== '') {
                    this.preview += `职称：${this.form.title}，`
                }
                if (this.form.excelField && this.form.excelField !== '') {
                    this.preview += `擅长领域：${this.form.excelField}，`
                }
                if (this.form.excelSpecies && this.form.excelSpecies !== '') {
                    this.preview += `擅长物种：${this.form.excelSpecies}，`
                }
                if (this.form.relatedSpecies && this.form.relatedSpecies !== '') {
                    this.preview += `相关物种：${this.form.relatedSpecies}，`
                }
                if (this.form.relatedIndustries && this.form.relatedIndustries !== '') {
                    this.preview += `相关行业：${this.form.relatedIndustries}，`
                }
                if (this.form.relatedGoods && this.form.relatedGoods !== '') {
                    this.preview += `相关商品：${this.form.relatedGoods}，`
                }
                if (this.form.relatedServices && this.form.relatedServices !== '') {
                    this.preview += `相关服务：${this.form.relatedServices}，`
                }
                this.preview = this.preview.substring(0, this.preview.length - 1) + '。'
            },
            //清除文本框
			inputClear(item){
				if (item == 'speciFilter2') {
					this.form.excelSpecies = ''
                    this.$refs.speciFilter2.handleReset();
                    this.handleGetSpeciResult2('', []);
				}
				if (item == 'speciFilter') {
					this.form.relatedSpecies = ''
                    this.$refs.speciFilter.handleReset();
                    this.handleGetSpeciResult('', []);
				}
				if (item == 'tradeFilter') {
					this.form.relatedIndustries = ''
                    this.$refs.tradeFilter.handleReset();
                    this.handleGetTradeResult('', []);
				}
				if (item == 'serviceFilter') {
					this.form.relatedServices = ''
                    this.$refs.serviceFilter.handleReset();
                    this.handleGetServiceResult('', []);
				}
				if (item == 'zhichengFilter') {
					this.form.title = ''
                    this.$refs.zhichengFilter.handleReset();
                    this.handleGetZhichengResult('', []);
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
                this.form.personalPicture = arr
            },
            // 获取资质证书
            getQualificationsList (e) {                
                var arr = []
                e.forEach(element => {
					if(element.response){
						arr.push(element.response.data.picName)
					}
                })
                this.form.qualificationsPicture = arr
            },
            // 上传荣誉证书
            getHonorList (e) {
                var arr = []
                e.forEach(element => {
					if(element.response){
						arr.push(element.response.data.picName)
					}
                })
                this.form.honorPicture = arr
            },
            // 高级搜索弹窗
            handleFilterModal (name) {
                this.$refs[name].highFilterShow = true
			},
			// 职称搜索
            handleZhichengSearch (letter, keyword, result) {
                this.$api.post(`/member/system-dict/getSystemDict`, {
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
                this.$api.post('/member/system-dict/getSystemDict', {
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
                this.form.title = arr.join(' ')
                this.change()
            },
            // 物种
            loadSpeciDatas(item, callback) {
				item.loading = true
				this.$api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
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
				this.form.excelSpecies = arr.join(' ')
                result.forEach(item => this.adpetSpecies.push(item.value))
                this.change()			
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
				this.$api.post('/member/specicesClass/findSpecies', {
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
                this.form.relatedSpecies = arr.join(' ')
                this.change()
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
				this.$api.post('/member/specicesClass/findSpecies', {
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
				this.form.relatedIndustries = arr.join(' ')
                result.forEach(item => this.classfy.push(item.value))
                this.change()
            },
            // 相关服务
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
				this.relatedServices = []
				result.forEach(item => arr.push(item.label))
				this.form.relatedServices = arr.join(' ')
                result.forEach(item => this.relatedServices.push(item.value))
                this.change()
			}
        },
        mounted() {
            this.preview = `专家分类（），职称（），擅长领域（），擅长物种（），相关物种（），相关行业（），相关商品（），相关服务（）。`
        },
    }
</script>
<style lang="scss" scoped>
</style>