<template>
    <div>
        <Modal :styles="{top: '50px'}" v-model="expertMangeModalShow" title="添加专家" width="1000" :mask-closable="false">
            <Row>
                <Form ref="queryInfo" :model="queryInfo" label-position="right" :label-width="90" :rules="ruleInline">
                    <Row>
                        <Col span="7">
                            <Form-item prop="trade" class="input" label="相关行业：">
                                <Input v-model="queryInfo.trade" readonly @on-focus="handleFilterModal('tradeFilter')" @on-click="inputClear('trade')" class="pinput-clear" icon="ios-close"/>
                            </Form-item>
                        </Col>
                        <Col span="7">
                            <Form-item prop="speci" class="input" label="相关物种：">
                                <Input v-model="queryInfo.speci" readonly @on-focus="handleFilterModal('speciFilter')" @on-click="inputClear('speci')" class="pinput-clear" icon="ios-close"/>
                            </Form-item>
                        </Col>
                        <Col span="7">
                            <Form-item prop="name" class="input" label="关键字：">
                                <Input v-model="queryInfo.name" />
                            </Form-item>
                        </Col>
                        <Col span="3" class="btn">
                            <Button type="primary" @click="queryExpert">查询</Button>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="12">
                            <Form-item prop="location" class="input" label="所处位置：">
                                <Cascader :data="locationList" v-model="queryInfo.locationList" :load-data="loadPositionDatas" :render-format="format" change-on-select>
                                </Cascader>
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
            </Row>
            <Row class="expert-list" style="height: 222px;">
                <Col span="6" v-for="(item,index) in expertAllTable" :key="index">
                    <div class="vui-flex vui-flex-middle wrap" >
                        <img v-if="item.head !== '' && item.head !== null" :src="item.head" alt="" width="90" height="90" style="margin:5px;" />
                        <img v-else src="../../../../static/img/user-icon-big.png" alt="" width="90" height="90" style="margin:5px;" />
                        <div class="vui-flex-item">
                            <p><span class="h6 t-green">{{item.expert_name}}</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>{{item.sex}}</span></p>
                            <p>{{item.account}}</p>
                            <p class="ell" style="width: 115px;" :title="item.addr">{{item.addr}}</p>
                            <template>
                                <Button type="primary" size="small" icon="android-person-add" class="mt5" @click.native.stop="addExpert(item.id)"> 邀请</Button>
                            </template>
                        </div>
                    </div>
                </Col>
            </Row>
            <Row>
                <div style="float: right; margin-top: 10px;">
                    <Page :total="total3" :current.sync="currentPage" :page-size="pageSize3" @on-change="expertAllTableNextPage"></Page>
                </div>
            </Row>
        </Modal>
        <!-- 相关行业 -->
        <vui-filter
        ref="tradeFilter"
        :simple="true"
        :transfer="true"
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
        :transfer="true"
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
    import api from '~api'

    export default {
        name: 'inviteModal',
        components: {
            vuiFilter
        },
        data () {
            return {
                queryInfo: {
                    trade: '',
                    tradeId: '',
                    speci: '',
                    speciId: '',
                    name: '',
                    location: '',
                    locationList: []
                },
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
                locationList: [],
                ruleInline: {},
                expertAllTable: [],
                total3: 0,
                pageSize3: 8,
                currentPage: 1,
                total6: 0,
                pageCur6: 1
            }
        },
        props: {
            value: {
                type: Boolean,
                default: false
            }
        },
        created () {
            // 取地址
            api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
                this.locationList = res.data
            })
            // 取相关行业数据
            api.post('/member/system-dict/getSystemDict', {
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
            this.init({
                keyWord: this.queryInfo.name,
                relatedSpecies: this.queryInfo.speci,
                relatedIndustry: this.queryInfo.trade,
                location: this.queryInfo.location,
                pageNum: 1,
                pageSize: this.pageSize3,
                loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
            })
        },
        computed: {
            expertMangeModalShow: {
                get () {
                    return this.value
                },
                set (value) {
                    this.$emit('input', value)
                }
            }
        },
        methods: {
            //清空文本框
            inputClear(name){
                if(name === 'speci'){
                    this.queryInfo.speci = ''
                    this.queryInfo.speciId = ''
                    this.$refs.speciFilter.handleReset()
                }else if(name === 'trade'){
                    this.queryInfo.trade = ''
                    this.queryInfo.tradeId =''
                    this.$refs.tradeFilter.handleReset()
                }
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
            },
            init (params) {
                api.post('/member/Employ/expertAllTable', params).then(response => {
                    console.log('res', response)
                    if (response.code === 200) {
                        for (let i = 0; i < response.data.list.length; i++) {
                            this.expertAllTable.push({
                                id: response.data.list[i].id,
                                head: response.data.list[i].avatar,
                                expert_name: response.data.list[i].expertName,
                                account: response.data.list[i].loginAccount,
                                sex: response.data.list[i].sex,
                                addr: response.data.list[i].addr,
                            })
                        }
                        this.total3 = response.data.total
                    }
                }).catch(error => {
                    console.error(error)
                })
            },
            //邀请专家
            addExpert(id){
                this.$Modal.confirm({
                    title: '邀请确认提示框',
                    content: '<p>确认是否邀请该专家？</p>',
                    onOk: () => {
                        api.post(
                            '/member/Employ/addExpert', {
                                id: id,
                                loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                            }
                        ).then(response => {
                            console.log(response)
                            if (response.code === 200) {
                                this.$Message.info('已经向该专家发出邀请')
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    },
                    onCancel: () => {
                        this.$Message.info('取消邀请')
                    }
                })
            },
            expertAllTableNextPage (page) {
                console.log('page', page)
                this.expertAllTable = []
                this.init({
                    keyWord: this.queryInfo.name,
                    relatedSpecies: this.queryInfo.speci,
                    relatedIndustry: this.queryInfo.trade,
                    location: this.queryInfo.location,
                    pageNum: page,
                    pageSize: this.pageSize3,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
            },
            loadPositionDatas (item, callback) {
                item.loading = true
                api.post(`/member/town/next/${item.value}`).then(res => {
                    item.loading = false
                    item.children = res.data
                    callback()
                })
            },
            format (labels, selectedData) {
                this.queryInfo.location = labels.join('/')
                return labels.join('/')
            },
            queryExpert () {
                this.expertAllTable = []
                this.currentPage = 1
                this.init({
                    keyWord: this.queryInfo.name,
                    relatedSpecies: this.queryInfo.speci,
                    relatedIndustry: this.queryInfo.trade,
                    location: this.queryInfo.location,
                    pageNum: 1,
                    pageSize: this.pageSize3,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
                
            }
        }
    }

</script>

<style scoped>

</style>


