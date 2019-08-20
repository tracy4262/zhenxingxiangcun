<!--
    作者：chenqim
    时间：2018-12-24
    描述：专家认证
-->
<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh" />
    <Card v-for="(item,index) in data" :key="index" class="mt40 pd10">
        <Form :ref="`formItem${index}`" :model="item" label-position="left" :label-width="100" :rules="formItemInline">
            <Row>
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="item.status" size="large" :disabled="true">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
                <Col span="12" class="tr">
                    <div class="btn-toolbar" style="margin-top: -10px;">
                        <Button type="text" @click="edit(index)" v-if="!item.isAdd"><Icon type="trash-a" size="16" class="pr5"></Icon> 编辑</Button> 
                        <Button type="text" @click="del(item, index)" v-if="data.length > 1"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <FormItem label="专家姓名" prop="expertName">
                        <Input v-model="item.expertName" :clearable="item.isAdd" @on-change="change" :disabled="true" />
                    </FormItem>
                </Col>
                <Col span="12">
                    <div v-if="item.approveStatus !== undefined" style="margin-top: 8px;">
                        {{ item.approveStatus === 0 ? '专家认证审核中' : item.approveStatus === 1 ? '专家认证审核通过' : '专家认证审核不通过'}}
                    </div>
                </Col>
            </Row>
            <FormItem label="上传个人照片" prop="personalPicture">
                <vui-upload
                    :disabled="true"
                    :ref="`personalPicture${index}`"
                    @on-getPictureList="getPersonalList($event, index)"
                    :pictureLists="item.personalPicture"
                    :hint="'图片大小小于2MB'"
                    :total="1"
                ></vui-upload>
            </FormItem>
            <Row :gutter="32">
                <Col span="12">
                    <FormItem label="职称" prop="title">
                        <Input v-model="item.title" :disabled="true" readonly class="pinput-clear" 
                            @on-focus="handleFilterModal('zhichengFilter', index)" :icon="item.isAdd ? 'ios-close-circle' : ''" 
                            @on-click="inputClear('zhichengFilter', index)" @on-change="change" />
                    </FormItem>
                </Col>
            </Row>
            <FormItem label="上传资质证书" prop="qualificationsPicture">
                <vui-upload
                    :disabled="true"
                    :ref="`qualificationsPicture${index}`"
                    @on-getPictureList="getQualificationsList($event, index)"
                    :pictureLists="item.qualificationsPicture"
                    :hint="'图片大小小于2MB'"
                    :total="9999999"
                ></vui-upload>
            </FormItem>
            <Row :gutter="32">
                <Col span="12">
                    <FormItem label="专家分类" prop="expertType">
                        <Select v-model="item.expertType" @on-change="change" :disabled="true">
                            <Option v-for="(f,index) in expertTypeList" :value="f.value" :key="index">{{ f.label }}</Option>
                        </Select>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="擅长领域" prop="excelField">
                        <Input v-model="item.excelField" :clearable="item.isAdd" @on-change="change" :disabled="true" />
                    </FormItem>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <FormItem label="擅长物种">
                        <Input v-model="item.excelSpecies" :disabled="true" readonly class="pinput-clear" 
                            @on-focus="handleFilterModal('speciFilter2', index)" :icon="item.isAdd ? 'ios-close-circle' : ''" 
                            @on-click="inputClear('speciFilter2', index)" @on-change="change" />
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="相关物种">
                        <Input v-model="item.relatedSpecies" :disabled="true" readonly class="pinput-clear" 
                            @on-focus="handleFilterModal('speciFilter', index)" :icon="item.isAdd ? 'ios-close-circle' : ''" 
                            @on-click="inputClear('speciFilter', index)" @on-change="change" />
                    </FormItem>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <FormItem label="相关行业" prop="relatedIndustries">
                        <Input v-model="item.relatedIndustries" :disabled="true" readonly class="pinput-clear" 
                            @on-focus="handleFilterModal('tradeFilter', index)" :icon="item.isAdd ? 'ios-close-circle' : ''" 
                            @on-click="inputClear('tradeFilter', index)" @on-change="change" />
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="相关商品">
                        <Input v-model="item.relatedGoods" :disabled="true" :clearable="item.isAdd" @on-change="change" />
                    </FormItem>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <FormItem label="相关服务" prop="relatedServices">
                        <Input v-model="item.relatedServices" :disabled="true" readonly class="pinput-clear" 
                            @on-focus="handleFilterModal('serviceFilter', index)" :icon="item.isAdd ? 'ios-close-circle' : ''" 
                            @on-click="inputClear('serviceFilter', index)" @on-change="change" />
                    </FormItem>
                </Col>
            </Row>
        </Form>
        <!-- <div class="tc">
            <Button type="primary" v-if="item.isAdd" @click="save(item, index)">保存</Button>
        </div> -->
    </Card>
    <!-- <Form label-position="left" :label-width="150" class="pb20 mt40">
        <Row :gutter="32">
            <Col span="12">
                <Button type="success" ghost @click="add" icon="md-add" class="btn-light-primary">添加</Button>
            </Col>
        </Row>
    </Form> -->
    <Title title="文字预览" class="mt40"/>
    <div class="pd20 tc pt30 mt20">
        <Input v-model="preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        <Button type="primary" v-if="isLoading" class="mt40">保存</Button>
        <Button type="primary" v-else @click="handleSave()" class="mt40">保存</Button>
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
                title: '专家认证',
                expertName: '',
                formItemInline: {
                    expertName:[{required: true,message:'请填写专家姓名'}],
                    expertType:[{required: true,message:'请选择专家分类'}],
                    title:[{required: true,message:'请选择职称'}],
                    excelField:[{required: true,message:'请填写擅长领域'}],
                    relatedIndustries:[{required: true,message:'请选择相关行业'}],
                    relatedServices:[{required: true,message:'请选择相关服务'}],
                    personalPicture:[{required: true, message: '请上个人照片', type: 'array', min: 1, trigger: 'change'}],
                    qualificationsPicture:[{required: true, message: '请上传资质证书', type: 'array', min: 1, trigger: 'change'}]
                },
                data: [
                    {
                        isAdd: true,
                        status: true,
                        expertName: '',
                        expertType: '',
                        title: '',
                        excelField: '',
                        excelSpecies: '',
                        relatedSpecies: '',
                        relatedIndustries: '',
                        relatedGoods: '',
                        relatedServices: '',
                        personalPicture: [],
                        qualificationsPicture: []
                    }
                ],
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
                    qualificationsPicture:[]
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
                ],
                templateId: '',
                isLoading: true,
                index: 0
            }
        },
        watch: {
            modeId: {
                handler (newValue, oldValue) {
                    this.init()
                    this.initTitle()
                },
                deep: true
            }
        },
        created () {
            if (this.modeId !== '' && this.modeId !== undefined) {
                this.init()
                this.initTitle()
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
        methods: {
            initTitle () {
                this.$api.post('/member-reversion/user/perfect/findTableHead', {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    templateId: this.$template.id
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.propertyName) {
                            this.title = response.data.propertyName
                        }
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 初始化加载数据
            init (type = 0) {
                this.$api.post('/member-reversion/expertAuth/find', {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    templateId: this.$template.id
                }).then(response => {
                    console.log('init res', response)
                    if (response.code === 200) {
                        if (response.data.propertyName && response.data.propertyName !== '') {
                            this.title = response.data.propertyName
                        }
                        if (response.data.preview && response.data.preview !== '') {
                            this.preview = response.data.preview
                            // this.id = response.data.textPreview.id
                        }
                        if (response.data.expertName && response.data.expertName !== '') {
                            this.expertName = response.data.expertName 
                        }
                        if (response.data.list.length !== 0) {
                            this.data = []
                            response.data.list.forEach((element, index) => {
                                this.data.push({
                                    isAdd: false,
                                    approveStatus: element.approveStatus,
                                    status: element.status,
                                    id: element.id,
                                    expertName: element.expertName,
                                    expertType: element.expertType,
                                    title: element.title,
                                    excelField: element.adeptField,
                                    excelSpecies: element.adeptSpecies,
                                    relatedSpecies: element.relatedSpecies,
                                    relatedIndustries: element.relatedIndustry,
                                    relatedGoods: element.relatedProduct,
                                    relatedServices: element.relatedService,
                                    personalPicture: element.personalPhoto,
                                    qualificationsPicture: element.quaCertificate
                                })
                                // console.log('this.$refs', this.$refs)
                                this.$nextTick(() => {
                                    this.$refs['personalPicture' + index][0].handleGive(this.data[index].personalPicture)
                                    this.$refs['qualificationsPicture' + index][0].handleGive(this.data[index].qualificationsPicture)
                                })
                            })
                        } else {
                            this.data[0].expertName = response.data.expertName
                        }
                        if (type === 1) {
                            this.change()
                        }
                        this.isLoading = false
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 增加
            add () {
                this.data.push({
                    isAdd: true,
                    status: true,
                    expertName: this.expertName,
                    expertType:'',
                    title: '',
                    excelField:'',
                    excelSpecies:'',
                    relatedSpecies:'',
                    relatedIndustries:'',
                    relatedGoods:'',
                    relatedServices:'',
                    personalPicture:[],
                    qualificationsPicture:[]
                })
                this.index = this.data.length - 1
            },
            // 编辑
            edit (index) {
                this.data[index].isAdd = true
                this.index = index
            },
            // 删除
            del (item,index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确认删除？',
                    onOk:()=>{
                        if (item.id !== undefined && item.id !== '') {
                            this.$api.post('/member-reversion/expertAuth/delete', {
                                id: item.id
                            }).then(response => {
                                if (response.code === 200) {
                                    this.$Message.success('删除成功！')
                                    this.data.splice(index, 1)
                                    this.change()
                                }
                            }).catch(error => {
                                this.$Message.error('服务器异常！')
                            })
                        } else {
                            this.$Message.success('删除成功！')
                            this.data.splice(index, 1)
                            this.change()
                        }
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            // 单个保存
            save (item, index) {
                this.$refs['formItem' + index][0].validate(flag => {
                    if (flag) {
                        let data = {
                            account: this.$user.loginAccount,
                            yearId: this.yearId,
                            dictId: this.modeId,
                            isComplete: '1',
                            templateId: this.$template.id,
                            id: item.id === '' || item.id === undefined ? '' : item.id,
                            expertName: item.expertName,
                            personalPhoto: item.personalPicture,
                            title: item.title,
                            quaCertificate: item.qualificationsPicture,
                            expertType: item.expertType,
                            adeptField:item.excelField,
                            adeptSpecies: item.excelSpecies,
                            relatedSpecies: item.relatedSpecies,
                            relatedIndustry: item.relatedIndustries,
                            relatedProduct: item.relatedGoods,
                            relatedService: item.relatedServices,
                            status: item.status
                        }
                        console.log('save data', data)
                        this.$api.post('/member-reversion/expertAuth/save', data).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('保存成功！')
                                this.init(1)
                            }
                        }).catch(error => {
                            this.$Message.error('服务器异常！')
                        })
                    }
                })
            },
            // 保存预览信息
            handleSave () {
                this.isLoading = true
                let data = {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    isComplete: '1',
                    textPreview: this.preview,
                    templateId: this.$template.id
                }
                console.log('data', data)
                this.$api.post('/member-reversion/perfect/saveTextPreview', data).then(response => {
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
                this.data.forEach((element, index) => {
                    if (element.expertName && element.expertName !== '') {
                        this.preview += `专家姓名：${element.expertName}，`
                    }
                    if (element.title && element.title !== '') {
                        this.preview += `职称：${element.title}，`
                    }
                    if (element.expertType && element.expertType !== '') {
                        this.preview += `专家分类：${element.expertType}，`
                    }
                    if (element.excelField && element.excelField !== '') {
                        this.preview += `擅长领域：${element.excelField}，`
                    }
                    if (element.excelSpecies && element.excelSpecies !== '') {
                        this.preview += `擅长物种：${element.excelSpecies}，`
                    }
                    if (element.relatedSpecies && element.relatedSpecies !== '') {
                        this.preview += `相关物种：${element.relatedSpecies}，`
                    }
                    if (element.relatedIndustries && element.relatedIndustries !== '') {
                        this.preview += `相关行业：${element.relatedIndustries}，`
                    }
                    if (element.relatedGoods && element.relatedGoods !== '') {
                        this.preview += `相关商品：${element.relatedGoods}，`
                    }
                    if (element.relatedServices && element.relatedServices !== '') {
                        this.preview += `相关服务：${element.relatedServices}，`
                    }
                })
                this.preview = this.preview.substring(0, this.preview.length - 1) + '。'
            },
            // 清除文本框
			inputClear (name, index) {
				if (name === 'speciFilter2') {
					this.data[index].excelSpecies = ''
                    this.$refs.speciFilter2.handleReset()
                    this.handleGetSpeciResult2('', [])
				}
				if (name === 'speciFilter') {
					this.data[index].relatedSpecies = ''
                    this.$refs.speciFilter.handleReset()
                    this.handleGetSpeciResult('', [])
				}
				if (name === 'tradeFilter') {
					this.data[index].relatedIndustries = ''
                    this.$refs.tradeFilter.handleReset()
                    this.handleGetTradeResult('', [])
				}
				if (name === 'serviceFilter') {
					this.data[index].relatedServices = ''
                    this.$refs.serviceFilter.handleReset()
                    this.handleGetServiceResult('', [])
				}
				if (name === 'zhichengFilter') {
					element.title = ''
                    this.$data[index].zhichengFilter.handleReset()
                    this.handleGetZhichengResult('', [])
				}
			},
            // 获取个人照片
            getPersonalList (e, index) {
                var arr = []
                e.forEach(element => {
                     if(element.response){
						arr.push(element.response.data.picName)
					}
                })
                this.data[index].personalPicture = arr
                this.$refs['formItem' + index][0].validate()
                this.change()
            },
            // 获取资质证书
            getQualificationsList (e, index) {                
                var arr = []
                e.forEach(element => {
					if(element.response){
						arr.push(element.response.data.picName)
					}
                })
                this.data[index].qualificationsPicture = arr
                this.$refs['formItem' + index][0].validate()
                this.change()
            },
            leftRefresh () {
                this.$emit('left-refresh')
            },
            // 高级搜索弹窗
            handleFilterModal (name, index) {
                this.$refs[name].highFilterShow = true
                this.index = index
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
                this.data[this.index].title = arr.join(' ')
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
				this.data[this.index].excelSpecies = arr.join(' ')
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
                this.data[this.index].relatedSpecies = arr.join(' ')
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
				this.data[this.index].relatedIndustries = arr.join(' ')
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
				this.data[this.index].relatedServices = arr.join(' ')
                result.forEach(item => this.relatedServices.push(item.value))
                this.change()
			}
        }
    }
</script>
<style lang="scss" scoped>
</style>