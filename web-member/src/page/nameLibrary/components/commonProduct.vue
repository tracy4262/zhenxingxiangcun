<template>
    <div>
        <div class="mt20">
            <Row>
                <Col span="20">
                    <Form ref="info" :model="info" inline :label-width="100">
                        <Row>
                            <Col span="8">
                                <FormItem label="通用商品名称">
                                    <Input v-model="info.commonProductName" placeholder="请输入内容"/>
                                </FormItem>
                            </Col>
                            <Col span="8">
                                <FormItem label="产品分类">
                                    <Input v-model="info.productTypeName" readonly @on-focus="handleFilterModal('productTypeFilter')" />
                                </FormItem>
                            </Col>
                            <Col span="8">
                                <FormItem label="行业分类">
                                    <Input v-model="info.relatedIndustry" readonly @on-focus="handleFilterModal('tradeFilter')" />
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="8">
                                <FormItem label="关联物种">
                                    <Input v-model="info.relatedSpeciesName" readonly @on-focus="handleFilterModal('speciFilter')" />
                                </FormItem>
                            </Col>
                        </Row>
                    </Form>
                </Col>
                <Col span="2">
                    <ButtonGroup size="large">
                        <Button type="default" @click="query">查找</Button>
                    </ButtonGroup>
                    <br/>
                    <br/>
                    <ButtonGroup size="large">
                        <Button type="default" @click="addCommonProduct()">新增通用商品名</Button>
                    </ButtonGroup>
                </Col>
            </Row>
            <Row style="margin: 10px;">
                <Col span="24">
                    <i-table border :content="self" :columns="columns5" :data="data"></i-table>
                </Col>
            </Row>
            <Row>
                <Col span="24">
                    <Page :total="totalPage" :current="pageNum" :page-size="pageSize" @on-change="handleGetNextPage" class="mt20 mb20 tr"></Page>
                </Col>
            </Row>
        </div>
        
        <Modal
            v-model="modal"
            title="商品详细信息"
            width="700"
            @on-ok="ok"
            @on-cancel="cancel">
            <div class="mb10">名称：{{ data.length === 0 ? '' : data[index].commodityName }}</div>
            <div class="mb10">俗名：{{ data.length === 0 ? '' : data[index].commodityAlias === '' ? '暂无' : this.data[index].commodityAlias}}</div>
            <div class="mb10">关联物种：{{ data.length === 0 ? '' : data[index].relatedspecies}}</div>
            <div class="mb10">产品分类：{{ data.length === 0 ? '' : data[index].classificationName}}</div>
            <div class="mb10">行业分类：{{ data.length === 0 ? '' : data[index].industryName}}</div>
            <div class="mb10">是否主要农产品：{{ data.length === 0 ? '' : data[index].isMainProduct}}</div>
            <div class="mb10">追溯类型：{{ data.length === 0 ? '' : data[index].traceaType}}</div>
            <div class="mb10">审核状态：{{ data.length === 0 ? '' : data[index].aduitStatus}}</div>
            <div class="mb10">备注：{{ data.length === 0 ? '' : data[index].refuseReason === '' ? '暂无' : this.data[index].refuseReason}}</div>
            <div class="mb10">释义：{{ data.length === 0 ? '' : data[index].remark === '' ? '暂无' : this.data[index].remark}}</div>
            <div class="mb10" v-if="data.length !== 0">
                商品图片：
                <span v-if="data[index].image.length === 0">暂无图片</span>
                <img v-else style="width: 50px; height: 50px; margin-right: 10px;" v-for="(item,index) in data[index].image" :src="item" :key="index" />
            </div>
            <div class="mb10" v-if="data.length !== 0">
                产品安全标准：
                <view-panel
                    :data="data[index].safetyCustomContent"
                    class="mt20">
                </view-panel>
            </div>
            <div class="mb10" v-if="data.length !== 0">
                产品质量信息：
                <view-panel
                    :data="data[index].qualityCustomContent"
                    class="mt20">
                </view-panel>
            </div>
        </Modal>

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

    </div>
</template>

<script>
    import vuiFilter from '~components/vuiFilter/filter'
    import viewPanel from '../../goods/detail/components/vui-form-control/view-panel'
    export default {
        components: {
            vuiFilter,
            viewPanel
        },
        data () {
            return {
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
                pageSize: 10,
                pageNum: 1,
                totalPage: 0,
                loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),

                addShow: false,
                addFinsihedShow: false,
                self: this,
                info: {
                    commonProductName: '',
                    productTypeName: '',
                    productType: '', // 传的是商品分类的ID
                    relatedIndustry: '',
                    relatedIndustryId: '',
                    relatedSpeciesName: '',
                    relatedSpeciesId: '',
                },
                columns5: [
                    {
                        title: '名称',
                        key: 'commodityName',
                        align: 'center',
                    },
                    {
                        title: '关联物种',
                        key: 'relatedspecies',
                        align: 'center',
                    },
                    {
                        title: '产品分类',
                        key: 'classificationName',
                        align: 'center',
                    },
                    {
                        title: '审核状态',
                        key: 'aduitStatus',
                        align: 'center',
                    },
                    {
                        title: '备注',
                        key: 'refuseReason',
                        align: 'center',
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 200,
                        align: 'center',
                        render: (h, params) => {
                            if (params.row.aduitStatus === '待审核' || params.row.aduitStatus === '审核未通过') {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        style: {
                                            marginRight: '5px'
                                        },
                                        on: {
                                            click: () => {
                                                this.edit(params.row.id)
                                            }
                                        }
                                    }, '编辑'),
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        style: {
                                            marginRight: '5px'
                                        },
                                        on: {
                                            click: () => {
                                                this.remove(params.row.id)
                                            }
                                        }
                                    }, '删除')
                                ])
                            } else if (params.row.aduitStatus === '审核通过') {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        style: {
                                            marginLeft: '5px'
                                        },
                                        on: {
                                            click: () => {
                                                this.showInfo(params.index)
                                            }
                                        }
                                    }, '查看')
                                ])
                            }
                        }
                    }
                ],
                data: [],
                modal: false,
                index: 0
            }
        },
        created () {
            // 查询列表数据
            this.getData({
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                name: '',  //筛选条件--通用商品名
                productType: '', //筛选条件--产品分类
                industryType: '', //筛选条件--行业分类
                relatedSpecies: '', //筛选条件--关联物种
                account: this.loginUser.loginAccount
            })
        },
        methods: {
            getData (data) {
                this.$api.post('/portal/currencyCommodity/list', data).then(response => {
                    console.log('res', response)
                    if (response.code === 200) {
                        this.data = response.data.list
                        this.totalPage = response.data.total
                    }
                }).catch(error => {
                    this.$Message('查询列表出错！')
                })
            },
            handleGetNextPage (page) {
                this.pageNum = page
                this.index = 0
                this.getData({
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.info.commonProductName,  //筛选条件--通用商品名
                    productType: this.info.productType, //筛选条件--产品分类
                    industryType: this.info.relatedIndustryId, //筛选条件--行业分类
                    relatedSpecies: this.info.relatedSpeciesId, //筛选条件--关联物种
                    account: this.loginUser.loginAccount
                })
            },
            query () {
                this.pageNum = 1
                this.getData({
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.info.commonProductName,  //筛选条件--通用商品名
                    productType: this.info.productType, //筛选条件--产品分类
                    industryType: this.info.relatedIndustryId, //筛选条件--行业分类
                    relatedSpecies: this.info.relatedSpeciesId, //筛选条件--关联物种
                    account: this.loginUser.loginAccount
                })
            },
            edit (id) {
                this.$router.push({
                    path: '/pro/addCommonProduct',
                    query: {
                        id: id
                    }
                })
            },
            // 点击跳转新增通用商品名称
            addCommonProduct () {
                this.$router.push('/pro/addCommonProduct')
            },
            showInfo (index) {
                this.modal = true
                this.index = index
            },
            ok () {},
            cancel () {},
            remove (id) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '确定删除该通用商品信息？',
                    onOk: () => {
                        console.log('id', id)
                        this.$api.post('/portal/currencyCommodity/deleteCommodityById', {
                            id: id
                        }).then(response => {
                            console.log('res', response)
                            if (response.code === 200) {
                                this.$Message.success('删除成功！')
                                this.query()
                            }
                        }).catch(error => {
                            this.$Message('删除失败！')
                        })
                    }
                })
            },
            // 高级搜索弹窗
            handleFilterModal (name) {
                this.$refs[name].highFilterShow = true
                if (name === 'speciFilter') {
                    // 取相关物种结果
                    this.loadSpeciResult('', '', [], this.pageCur, [])
                } else if (name === 'tradeFilter') {
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
                } else if (name === 'productTypeFilter') {
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
                }
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
                this.info.productTypeName = arr.join(' ')
                this.info.productType = arrId.join(' ')
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
                this.info.relatedIndustry = arr.join(' ')
                this.info.relatedIndustryId = arrId.join(' ')
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
                this.info.relatedSpeciesName = arr.join(' ')
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