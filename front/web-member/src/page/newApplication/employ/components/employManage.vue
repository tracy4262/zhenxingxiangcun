<!--
    作者：chenqim
    时间：2018-12-20
    描述：聘请管理
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <div class="mt20">
            <Form ref="queryInfo" :model="queryInfo" label-position="right" :label-width="90" :rules="ruleInline">
                <Row :gutter="32">
                    <Col span="8">
                        <Form-item prop="trade" class="input" label="相关行业：">
                            <Input v-model="queryInfo.trade" class="pinput-clear" icon="ios-close-circle" readonly @on-focus="handleFilterModal('tradeFilter')" @on-click="inputClear('trade')" />
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item prop="speci" class="input" label="相关物种：">
                            <Input v-model="queryInfo.speci" class="pinput-clear" icon="ios-close-circle" readonly @on-focus="handleFilterModal('speciFilter')" @on-click="inputClear('speci')" />
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item prop="name" class="input" label="专家姓名：">
                            <Input v-model="queryInfo.name" clearable />
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="16">
                        <Form-item prop="trade" class="input" label="所处位置：">
                            <Cascader :data="locationList" v-model="queryInfo.locationList" :load-data="loadPositionDatas" :render-format="format" change-on-select>
                            </Cascader>
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Button type="primary" @click="query">查询</Button>
                        <Button type="text" @click="clear">重置</Button>
                    </Col>
                </Row>
            </Form>
        </div>
        <div class="mt20">
            <Table border :columns="columns" :data="data"></Table>
        </div>
        <div class="mt20 tr" v-if="data.length !== 0">
            <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange" />
        </div>
        <!-- 相关行业 -->
        <vui-filter
        ref="tradeFilter"
        :simple="true"
        :cols="2"
        :pageShow="true"
        :total="total6"
        :pageCur="pageCur6"
        :resultDatas="tradeResultDatas"
        @on-search="handleTradeSearch"
        @on-page-change="handleTradePageChange"
        @on-get-result="handleGetTradeResult" />

        <!-- 相关物种 -->
        <vui-filter
        ref="speciFilter"
        :cols="2"
        :pageShow="true"
        :total="total5"
        :pageCur="pageCur5"
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
export default {
    name: 'employManage',
    components: {
        vuiFilter
    },
    data () {
        return {
            columns: [
                {
                    title: '专家姓名',
                    key: 'expertName',
                    width: 100
                },
                {
                    title: '擅长领域',
                    key: 'adeptField'
                },
                {
                    title: '擅长物种',
                    key: 'adeptSpecies'
                },
                {
                    title: '相关行业',
                    key: 'relatedIndustry'
                },
                {
                    title: '相关物种',
                    key: 'relatedSpecies'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: 200,
                    render: (h, params) => {
                        return h('div', [
                            h('a', {
                                style: {
                                    marginRight: '10px',
                                    color: '#2c92ff'
                                },
                                on: {
                                    click: () => {
                                        this.detail(params.row)
                                    }
                                }
                            }, '查看详情'),
                            h('a', {
                                style: {
                                    color: '#ff5c76'
                                },
                                on: {
                                    click: () => {
                                        this.relive(params.row)
                                    }
                                }
                            }, '解除关系')
                        ])
                    }
                }
            ],
            data: [],
            total: 0,
            pageSize: 10,
            pageNum: 1,
            queryInfo: {
                trade: '',
                tradeId: '',
                speci: '',
                speciId: '',
                name: '',
                location: '',
                locationList: []
            },
            locationList: [],
            ruleInline: {},
            tradeResultDatas: [],
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
            speciResultDatas: [],
            total5: 0,
            pageCur5: 1,
            total6: 0,
            pageCur6: 1
        }
    },
    created () {
        this.init()
        // 取相关行业数据
        this.$api.post('/member/system-dict/getSystemDict', {
            typeName:'行业分类',
			pageNum: 1
        }).then(res => {
            console.log('初始化相关行业', res)
            var data = res.data.list
            this.total6 = res.data.total
            this.tradeResultDatas = data
        })
        // 取相关物种结果
        this.loadSpeciResult('', '', [], this.pageCur5, [])
        // 取地址
        this.$api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
            this.locationList = res.data
        })
    },
    methods: {
        init () {
            this.$api.post('/member-reversion/employ/manage', {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                account: this.$user.loginAccount,
                type: 1, // type:1 查聘请管理
                relatedIndustry: this.queryInfo.trade, //相关行业
                relatedSpecies: this.queryInfo.speci, //相关物种
                expertName: this.queryInfo.name, //专家名称
                location: this.queryInfo.location //所在位置
            }).then(response => {
                console.log('res', response)
                if (response.code === 200) {
                    this.data = response.data.list
                    this.total = response.data.total
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        query () {
            this.pageNum = 1
            this.init()
        },
        clear () {
            this.inputClear('trade')
            this.inputClear('speci')
            this.queryInfo.name = ''
            this.queryInfo.location = ''
            this.queryInfo.locationList = []
        },
        detail (item) {
            window.open('/portals/index?uid='+item.account)
        },
        relive (item) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认解除关系？',
                okText:'确定',
                cancelText:'取消',
                onOk: () => {
                    this.$api.post('/member-reversion/employ/relieve', {
                        type: 0,  //0:聘请管理里的解除关系，1:受聘管理里的解除关系
                        activeAccount: this.$user.loginAccount,  //主动解除方
                        passiveAccount: item.account  //被动解除方
                    }).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('解除关系成功！')
                            this.init()
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        },
        pageChange (page) {
            this.pageNum = page
            this.init()
        },
        // 清空文本框
        inputClear(name) {
            if (name === 'speci') {
                this.queryInfo.speci = ''
                this.queryInfo.speciId = ''
                this.$refs.speciFilter.handleReset()
            } else if (name === 'trade') {
                this.queryInfo.trade = ''
                this.queryInfo.tradeId =''
                this.$refs.tradeFilter.handleReset()
            }
        },
        // 地区
        loadPositionDatas (item, callback) {
            item.loading = true
            this.$api.post(`/member/town/next/${item.value}`).then(res => {
                item.loading = false
                item.children = res.data
                callback()
            })
        },
        format (labels, selectedData) {
            this.queryInfo.location = labels.join('/')
            return labels.join('/')
        },
        // 高级搜索弹窗
        handleFilterModal (name) {
            this.$refs[name].highFilterShow = true
        },
        // 相关行业搜索
        handleTradeSearch (letter, keyword, result) {
            this.$api.post(`/member/system-dict/getSystemDict`, {
                dictName : keyword,
                typeName : '行业分类',
                character: letter === '全部' ? '' : letter,
                pageNum: 1
            }).then(res => {
                console.log('行业分类按条件查询', res)
                var d = res.data.list
                this.total6 = res.data.total
                this.pageCur6 = 1
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
        handleGetTradeResult (classify, result) {
            var arr = []
            var arr2 = []
            result.forEach(item => {
                arr.push(item.label)
                arr2.push(item.value)
            })
            this.queryInfo.trade = arr.join(' ')
            this.queryInfo.tradeId = arr2.join(' ')
        },
        handleTradePageChange (letter, keyword, classify, num, result) {
            this.pageCur6 = num
            this.$api.post('/member/system-dict/getSystemDict', {
                typeName:'行业分类',
                character: letter === '全部' ? '' : letter,
                dictName: keyword,
                pageNum: num
            }).then(res => {
                console.log('行业分类分页', res)
                var data = res.data.list
                this.total6 = res.data.total
                this.tradeResultDatas = data
            })
            this.$Message.success('筛选完成！')
        },
        loadSpeciDatas (item, callback) {
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
        // 相关物种
        handleSpeciSearch (letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur5, result)
            this.$Message.success('筛选完成！')
        },
        handleGetSpeciClassify (letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur5, result)
            this.$Message.success('筛选完成！')
        },
        handleGetSpeciResult (classify, result) {
            var arr = []
            var arr2 = []
            result.forEach(item => {
                arr.push(item.label)
                arr2.push(item.value)
            })
            this.queryInfo.speci = arr.join(' ')
            this.queryInfo.speciId = arr2.join(' ')
        },
        handleSpeciPageChange (letter, keyword, classify, num, result) {
            this.pageCur5 = num
            this.loadSpeciResult(letter, keyword, classify, this.pageCur5, result)
            this.$Message.success('筛选完成！')
        },
        loadSpeciResult (letter, keyword, classify, num, result) {
            if(classify.length) {
                var arr = []
                classify.forEach(item => {
                    arr.push(item.classId)
                })
            } else {
                arr = null
            }
            let a = {
                keywords: keyword,
                fpinyin: letter === '全部' ? '' : letter, 
                fclassifiedid: arr,
                pageNum: num,
                pageSize: 32
            }
            this.$api.post('/member/specicesClass/findSpecies', a).then(res => {
                var data = res.data
                this.total5 = data.total
                if (result) {
                    result.forEach(item => {
                        data.list.forEach((child,index)=>{
                            if (child.label === item.label) {
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
        }
    }
}
</script>
<style lang="scss" scoped>

</style>
