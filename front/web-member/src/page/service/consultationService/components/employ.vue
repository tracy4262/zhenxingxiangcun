<!--
    作者：chenqim
    时间：2018-12-21
    描述：我要聘请
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <!-- 查找专家 -->
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
            <Row>
                <div v-for="(item, index) in list" :key="index" class="proxy-card">
                    <expert-card :item="item" @refresh="refresh"></expert-card>
                </div>
            </Row>
        </div>
        <div class="mt20 tr" v-if="list.length !== 0">
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
import expertCard from './expertCard'
import vuiFilter from '~components/vuiFilter/filter'
export default {
    name: '',
    components: {
        expertCard,
        vuiFilter
    },
    data () {
        return {
            modal: false,
            total: 0,
            pageSize: 12,
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
            pageCur6: 1,
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
                    title: '状态',
                    key: 'status',
                    width: 100
                },
                {
                    title: '操作',
                    key: 'action',
                    width: 180,
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
                                    color: params.row.status === '拒绝' ? '#d8d8d8' : '#ff5c76',
                                    cursor: params.row.status === '拒绝' ? 'not-allowed' : 'pointer'
                                },
                                on: {
                                    click: () => {
                                        params.row.status === '待处理' ? this.relive(params.row) : ''
                                    }
                                }
                            }, '取消邀请')
                        ])
                    }
                }
            ],
            list: []
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
        // 初始化查询
        init () {
            this.$api.post('/member-reversion/employ/findExpert', {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                account: this.$user.loginAccount,
                relatedIndustry: this.queryInfo.trade, //相关行业
                relatedSpecies: this.queryInfo.speci, //相关物种
                expertName: this.queryInfo.name, //专家名称
                location: this.queryInfo.location //所在位置
            }).then(response => {
                console.log('init res', response)
                if (response.code === 200) {
                    if (response.data.list.length > 0) {
                        this.list = response.data.list
                        this.total = response.data.total
                    } else {
                        this.list = []
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        init2 () {
            this.$api.post('/member-reversion/employ/manage', {
                number: this.pageNum,
                size: this.pageSize,
                account: this.$user.loginAccount,
                type: 0 // type:0 查聘请处理中
            }).then(response => {
                console.log('init2 res', response)
                if (response.code === 200) {
                    this.list = response.data.list
                    this.total = response.data.total
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        // 去门户
        detail (item) {
            window.open('/portals/index?uid='+item.account)
        },
        relive (item) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认取消邀请？',
                okText:'确定',
                cancelText:'取消',
                onOk: () => {
                    this.$api.post('/member-reversion/employ/cacelInvite', {
                        sendAccount: this.$user.loginAccount,
                        beInvitedAccount: item.account
                    }).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('取消邀请成功！')
                            this.init2()
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        },
        // 查询
        query () {
            this.pageNum = 1
            this.init()
        },
        // 重置
        clear () {
            this.inputClear('trade')
            this.inputClear('speci')
            this.queryInfo.name = ''
            this.queryInfo.location = ''
            this.queryInfo.locationList = []
        },
        // 分页
        pageChange (page) {
            this.pageNum = page
            this.init()
        },
        refresh () {
            this.pageNum = 1
            this.init()
        },
        // 清空文本框
        inputClear (name) {
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
    .proxy-card {
        width: calc(100% / 4);
        display: inline-block;
    }
    .vertical-center-modal {
        display: flex;
        align-items: center;
        justify-content: center;

        .ivu-modal {
            top: 0;
        }
    }
</style>
