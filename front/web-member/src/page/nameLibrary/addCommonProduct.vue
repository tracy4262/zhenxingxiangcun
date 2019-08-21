<template>
    <div class="layout">
        <top :address="false"/>
        <div class="main">
            <div class="container">
                <app-banner
                    src="../../../../static/img/app-banner-species.png"
                    title="名称库管理">
                </app-banner>
                <Breadcrumb class="pt20 pb20">
                    <BreadcrumbItem to="/pro/nameLibrary">名称库管理</BreadcrumbItem>
                    <BreadcrumbItem>新增通用商品</BreadcrumbItem>
                </Breadcrumb>
                <div style="display: flex; justify-content: center; align-items: center;" class="mt20 mb30">
                    <div style="width: 60%; margin-left: 300px;">
                        <Steps :current="currentStep">
                            <Step title="通用商品基本信息"></Step>
                            <Step title="提交审核"></Step>
                        </Steps>
                    </div>
                </div>
                <div v-if="step1" style="width: 800px; margin: 0 auto;">
                    <Form ref="info" :model="info" :label-width="200" label-position="right" :rules="ruleInline">
                        <FormItem prop="commodityName" label="通用商品名称">
                            <Input v-model="info.commodityName" placeholder="请输入内容" @on-change="getPinyin" :maxlength="20" />
                        </FormItem>
                        <FormItem label="通用商品名拼音">
                            <Input v-model="info.commodityPinyin" placeholder="由通用服务名称自动生成拼音" readonly />
                        </FormItem>
                        <FormItem label="俗名别名">
                            <Input v-model="info.commodityAlias" placeholder="请输入内容" />
                        </FormItem>
                        <FormItem label="行业分类">
                            <Input v-model="info.industryType" readonly @on-focus="handleFilterModal('tradeFilter')" />
                        </FormItem>
                        <FormItem prop="productType" label="产品分类">
                            <Input v-model="info.productType" readonly @on-focus="handleFilterModal('productTypeFilter')" />
                        </FormItem>
                        <FormItem label="关联物种">
                            <Input v-model="info.relatedSpecies" readonly @on-focus="handleFilterModal('speciFilter')" />
                        </FormItem>
                        <FormItem label="是否主要农产品">
                            <RadioGroup v-model="info.isMainProduct">
                                <Radio label="是"></Radio>
                                <Radio label="否"></Radio>
                            </RadioGroup>
                        </FormItem>
                        <FormItem label="追溯类型">
                            <Select v-model="info.traceaType" clearable>
                            <Option v-for="item in traceaTypeList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="释义">
                            <Input v-model="info.remark" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
                        </FormItem>
                        <FormItem label="上传商品预览图片">
                            <vui-upload
                                ref="pic"
                                @on-getPictureList="getList"
                                :pictureLists="info.image"
                                :hint="'支持jpg/png格式，最多可上传10张'"
                                :total="10"
                            ></vui-upload>
                        </FormItem>
                    </Form>
                    <view-panel
                        title="质量信息表单"
                        :data="qualityFormData"
                        @on-data="handleGetQualityForm"
                        @on-add="handleAddBtnAddPanel('quality')" class="mt20" style="margin-left: 100px;"></view-panel>
                    <view-panel
                        title="安全标准表单"
                        :data="safeFormData"
                        @on-data="handleGetSafeForm"
                        @on-add="handleAddBtnSecurity('safe')" class="mt20" style="margin-left: 100px;"></view-panel>
                    <div class="tc mt20 mb40">
                        <Button type="primary" @click="next">提交</Button>
                        <Button type="default" class="ml20" @click="complete">退出</Button>
                    </div>
                </div>
                <div v-else class="tc mb40">
                    <h2>您已提交新的通用商品信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
                    <Button type="primary" class="mt20 mb40" @click="complete">完成</Button>
                </div>
            </div>
        </div>

        <foot></foot>

        <!-- 商品分类控件 -->
        <vui-filter
            ref="productTypeFilter"
            :num="1"
            :simple="true"
            :pageShow="true"
            :cols="2"
            :total="total2"
            :pageCur="pageCur2"
            :resultDatas="productTypeResultDatas"
            @on-search="handleProductTypeSearch"
            @on-page-change="handleProductTypePageChange"
            @on-get-result="handleGetProductTypeResult" />

        <!-- 相关行业 -->
        <vui-filter
            ref="tradeFilter"
            :num="1"
            :simple="true"
            :pageShow="true"
            :cols="2"
            :total="total3"
            :pageCur="pageCur3"
            :resultDatas="tradeResultDatas"
            @on-search="handleTradeSearch"
            @on-page-change="handleTradePageChange"
            @on-get-result="handleGetTradeResult" />

        <!-- 相关物种 -->
        <vui-filter 
            ref="speciFilter" 
            :cols="2" 
            :num="1" 
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

        <!-- 添加面板 -->
        <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
        <add-panel-security ref="security" @on-save="handleControlBtn"></add-panel-security>

    </div>
</template>

<script>
import top from '../../top'
import foot from '../../foot'
import appBanner from '~components/app-banner'
import vuiFilter from '~components/vuiFilter/filter'
import vuiUpload from '~components/vui-upload'
import addPanel from '../goods/components/vui-form-control/add-panel'
import addPanelSecurity from '../goods/components/vui-form-control/add-panel-security'
import viewPanel from '../goods/components/vui-form-control/view-panel'
export default {
    components: {
        vuiFilter,
        vuiUpload,
        addPanel,
        viewPanel,
        addPanelSecurity,
        top,
        foot,
        appBanner
    },
    data () {
        return {
            currentStep: 0,
            step1: true,
            info: {
                // id : '',  //如果是更新操作，则需要传id
                account : JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,  //用户名
                commodityName : '',  //通用商品名称
                commodityPinyin : '', //通用商品名拼音
                commodityAlias : '', //俗名别名
                industryType: '',
                industryTypeId : '', //行业分类id
                productType: '',
                productTypeId : '', //产品分类id
                relatedSpecies: '',
                relatedSpeciesId : '', //关联物种id
                remark : '',  //释义
                image: [],  //图册
                traceaType: '',  //追溯类型
                isMainProduct: '否' //是否主要农产品
            },
            ruleInline: {
                commodityName:[
                    { required: true, message: '请填写通用商品名称', trigger:'blur' }
                ],
                productType:[
                    { required: true, message: '请选择产品分类', trigger:'change' }
                ]
            },
            // 商品分类
            total2: 0,
            pageCur2: 1,
            productTypeResultDatas: [],
            // 行业分类
            total3: 0,
            pageCur3: 1,
            tradeResultDatas: [],
            // 相关物种
            total: 0,
            pageCur: 1,   
            speciResultDatas: [],
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
            traceaTypeList: [
                {
                label: '应溯',
                value: '应溯'
                },
                {
                label: '被溯',
                value: '被溯'
                }
            ],
            safeFormData: [],
            qualityFormData: []
        }
    },
    created () {
        // 商品分类数据初始化
        this.$api.post('/portal/shopCommdoity/findProductClassification', {
            pinyin: '',
            name: '',
            num: 1,
            size: 32
        }).then(res => {
            this.total2 = res.data.total
            res.data.list.forEach(element => {
                this.productTypeResultDatas.push({
                    label: element.className,
                    value: element.productCode
                })
            })
        })
        // 取行业类型数据
        this.$api.post('/member/system-dict/getSystemDict', {
            typeName:'行业分类',
            pageNum: 1
        }).then(res => {
            console.log('222', res)
            var data = res.data.list
            this.total3 = res.data.total
            this.tradeResultDatas = data
        })
        // 取相关物种结果
        this.loadSpeciResult('', '', [], this.pageCur, [])
        // 编辑
        if (this.$route.query.id && this.$route.query.id !== '') {
            this.$api.post('/portal/currencyCommodity/findCommodityById', {
                id: this.$route.query.id
            }).then(response => {
                console.log('res', response)
                if (response.code === 200) {
                    this.listShow = false
                    this.$nextTick(() => {
                        this.info = response.data
                        this.$refs.pic.handleGive(response.data.image)
                        this.safeFormData = response.data.safetyCustomContent
                        this.qualityFormData = response.data.qualityCustomContent
                    })
                }
            }).catch(error => {
                this.$Message('查询通用商品出错！')
            })
        }
    },
    methods: {
        next () {
            this.$refs['info'].validate((valid) => {
                if (valid) {
                    if (this.qualityFormData.length === 0) {
                        this.$Message.error('请添加质量信息表单！')
                        return
                    }
                    if (this.safeFormData.length === 0) {
                        this.$Message.error('请添加安全标准表单！')
                        return
                    }
                    this.info.safetyCustomContent = this.safeFormData
                    this.info.qualityCustomContent = this.qualityFormData
                    console.log('data', this.info)
                    this.$api.post('/portal/currencyCommodity/saveOrUpdate', this.info).then(response => {
                        console.log('res', response)
                        if (response.code === 200) {
                            this.$Message.success('保存成功！')
                            this.step1 = false
                        }
                    }).catch(error => {
                        this.$Message.error('新增通用商品出错！')
                    })
                }
            })
        },
        complete () {
            this.$router.push({
                path: '/pro/nameLibrary',
                query: {
                    tabValue: 'tab5'
                }
            })
        },
        // 得到汉字的拼音
        getPinyin () {
            if (this.info.commodityName !== '') {
                this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.info.commodityName).then(response => {
                    this.info.commodityPinyin = ''
                    this.info.commodityPinyin = response.data
                }).catch(error => {
                    this.$Message.error('拼音转换出错！')
                })
            } else {
                this.info.commodityPinyin = ''
            }
        },
        /* checkName () {
            if (this.info.commodityName !== '') {
                this.$api.post('/portal/currencyCommodity/list', {
                    fullName: this.info.commodityName,
                    aduitStatus: '1'
                }).then(response => {
                    console.log('res', response)
                    if (response.code === 200) {
                        if (response.data.total > 0) {
                            this.$Message.error('该通用商品名已存在，请重新输入通用商品名！')
                            this.info.commodityName = ''
                            this.info.commodityPinyin = ''
                        }
                    }
                }).catch(error => {
                    this.$Message('查询通用商品名出错！')
                })
            }
        }, */
        getList (e) {
            var arr = []
            e.forEach(element => {
                    if(element.response){
                    arr.push(element.response.data.picName)
                }
            })
            this.info.image = arr
        },
        // 取商品质量数据
        handleGetQualityForm (data) {
            console.log(data)
        },
        // 取商品安全数据
        handleGetSafeForm (data) {
            console.log(data)
        },
        // 质量信息添加
        handleAddBtnAddPanel (name) {
            this.$refs.addPanel.showAddPanel = true
            this.name = name
        },
        // 添加组件
        handleControlBtn (data) {
            if (this.name === 'safe') {
                this.safeFormData.push(data)
            } else {
                this.qualityFormData.push(data)
            }
        },
        // 安全标准添加
        handleAddBtnSecurity (name) {
            this.$refs.security.showAddPanel = true
            this.name = name
        },
        // 高级搜索弹窗
        handleFilterModal (name) {
            this.$refs[name].highFilterShow = true
        },
        // 商品分类
        handleProductTypeSearch (letter, keyword, result) {
            this.$api.post(`/portal/shopCommdoity/findProductClassification`, {
                name : keyword,
                pinyin: letter === '全部' ? '' : letter,
                num: 1,
                size: 32
            }).then(res => {
                console.log('123', res)
                var data = []
                if (res.data.list) {
                    res.data.list.forEach(element => {
                        data.push({
                            label: element.className,
                            value: element.productCode
                        })
                    })
                }
                this.total2 = res.data.total
                this.productTypeResultDatas = []
                // 选中回显
                if(result) {
                    result.forEach(item => {
                        data.forEach((child, index) => {
                            if(child.label === item.label) {
                                child.checked = true
                            }
                        })
                    })
                } else {
                    data.forEach(child => {
                        child.checked = false
                    })
                }
                this.productTypeResultDatas = data
            })
        },
        handleProductTypePageChange (letter, keyword, classify, num, result) {
            this.pageCur2 = num
            this.$api.post('/portal/shopCommdoity/findProductClassification', {
                name : keyword,
                pinyin: letter === '全部' ? '' : letter,
                num: num,
                size: 32
            }).then(res => {
                var data = []
                res.data.list.forEach(element => {
                    data.push({
                        label: element.className,
                        value: element.productCode
                    })
                })
                this.total2 = res.data.total
                this.productTypeResultDatas = []
                // 选中回显
                if(result) {
                    result.forEach(item => {
                        data.forEach((child, index) => {
                            if(child.label === item.label) {
                                child.checked = true
                            }
                        })
                    })
                } else {
                    data.forEach(child => {
                        child.checked = false
                    })
                }
                this.productTypeResultDatas = data
            })
            this.$Message.success('筛选完成！')
        },
        handleGetProductTypeResult (classify, result) {
            let arr = []
            let arrId = []
            result.forEach(item => arr.push(item.label))
            result.forEach(item => arrId.push(item.value))
            this.info.productType = arr.join(' ')
            this.info.productTypeId = arrId.join(' ')
        },
        // 行业类型相关
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
            let arr = []
            let arrId = []
            result.forEach(item => arr.push(item.label))
            result.forEach(item => arrId.push(item.value))
            this.info.industryType = arr.join(' ')
            this.info.industryTypeId = arrId.join(' ')
        },
        // 相关物种相关
        handleSpeciSearch(letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
            this.$Message.success('筛选完成！')
        },
        handleGetSpeciClassify(letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
            this.$Message.success('筛选完成！')
        },
        handleGetSpeciResult(classify, result) {
            let arr = []
            let arrId = []
            result.forEach(item => arr.push(item.label))
            result.forEach(item => arrId.push(item.value))
            this.info.relatedSpecies = arr.join(' ')
            this.info.relatedSpeciesId = arrId.join(' ')
        },
        handleSpeciPageChange(letter, keyword, classify, num, result) {
            this.pageCur = num
            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
            this.$Message.success('筛选完成！')
        },
        loadSpeciResult(letter, keyword, classify, num, result) {
            if (classify.length) {
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
                if (result) {
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
        }
    }
}
</script>
