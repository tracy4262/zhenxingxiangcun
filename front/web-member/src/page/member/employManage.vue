<template>
    <div class="layout">
        <top :address="false" />
        <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="24">
                        <app-banner
                            src="../../../../static/img/app-banner-employ.png"
                            title="聘请管理">
                        </app-banner>
                        <Tabs value="expert" class="company-code-tabs">
                            <TabPane label="聘请的专家" name="expert" class="pd20">
                                <Row>
                                    <Form ref="queryInfo" :model="queryInfo" label-position="right" :label-width="90" :rules="ruleInline">
                                        <Col span="7">
                                            <Form-item prop="trade" class="input" label="相关行业：">
                                                <Input v-model="queryInfo.trade" class="pinput-clear" icon="ios-close" readonly @on-focus="handleFilterModal('tradeFilter')" @on-click="inputClear('trade')" />
                                            </Form-item>
                                        </Col>
                                        <Col span="7">
                                            <Form-item prop="speci" class="input" label="相关物种：">
                                                <Input v-model="queryInfo.speci" class="pinput-clear" icon="ios-close" readonly @on-focus="handleFilterModal('speciFilter')" @on-click="inputClear('speci')" />
                                            </Form-item>
                                        </Col>
                                        <Col span="7">
                                            <Form-item prop="name" class="input" label="专家姓名：">
                                                <Input v-model="queryInfo.name" />
                                            </Form-item>
                                        </Col>
                                        <Col span="3" class="btn">
                                            <Button type="primary" @click="selectAll">查询</Button>
                                        </Col>
                                    </Form>
                                </Row>
                                <Row>
                                    <Col>
                                        <Button type="default" class="fr" @click.native="addExpertManage">添加专家</Button>
                                    </Col>
                                </Row>
                                <Table style="margin-top: 30px;" border ref="expertTable" size="small" :columns="expertTable.columns" :data="expertTable.data" />
                                <div class="page">
                                    <Page :total="total" :current.sync="currentPage" :page-size="pageSize" @on-change="selectAllNextPage"></Page>
                                </div>
                            </TabPane> 

                            <TabPane v-if="loginUser.userType === 4" label="受聘企业" name="company" class="pd20">
                                <Table  border ref="companyTable" size="small" :columns="companyTable.columns" :data="companyTable.data" />
                                <div style="float: right; margin-top:30px;">
                                    <Page :total="total2" :current.sync="currentPage2" :page-size="pageSize2" @on-change="selectAllCompanyNextPage"></Page>
                                </div>
                            </TabPane>

                            <TabPane label="邀请通知" name="inform" class="pd20">
                                <Table  border ref="inviteTable" size="small" :columns="inviteTable.columns" :data="inviteTable.data" />
                                <div style="float: right; margin-top: 30px;">
                                    <Page :total="total4" :current.sync="currentPage3" :page-size="pageSize4" @on-change="selectAllInformNextPage"></Page>
                                </div>
                            </TabPane>
                        </Tabs>

                    </Col>
                </Row>
            </div>
        </div>
        <foot></foot>        
        <!-- 邀请专家弹出框 -->
        <inviteExpert ref="expert" v-model="expertMangeModalShow"></inviteExpert>

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
import api from '~api'
import top from '../../top'
import foot from '../../foot'
import vuiFilter from '~components/vuiFilter/filter'
import inviteExpert from './components/inviteExpert'
import appBanner from '~components/app-banner'

export default {
    components:{
        top,
        foot,
        appBanner,
        vuiFilter,
        inviteExpert
    },
    data() {
        return {
            queryInfo: {
                trade: '',
                tradeId: '',
                speci: '',
                speciId: '',
                name: ''
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

            ruleInline: {},
            total: 0,
            pageSize: 10,
            currentPage: 1,

            total2: 0,
            pageSize2: 10,
            currentPage2: 1,

            total3: 0, 
            pageSize3: 8,
            currentPage3: 1,

            total4: 0, 
            pageSize4: 10,
            currentPage4: 1,
            expertAllTable:[],
            expertManageSearch:{
                field: '',
                expert_name: '',
            },
            expertMangeModalShow:false,
            expertMangeForm:{
                field: '',
                expert_name: '',
                city: [],
            },
            expertTable:{
                columns:[
                    {
                        title: '专家姓名',
                        key: 'expert_name',
                        width: 100
                    },
                    {
                        title: '擅长领域',
                        key: 'field'
                    },
                    {
                        title: '擅长物种',
                        key: 'speci'
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
                        width: 120
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        align: 'center',
                        render: (h, params) => {
                            if (params.row.status === '接受') {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        on: {
                                            click: () => {
                                                this.$Modal.confirm({
                                                    title: '操作提示',
                                                    content: '<p>您确定要解除与该专家的关系吗？',
                                                    onOk: () => {
                                                        this.removeEmploy(params.row.id)
                                                    }
                                                })
                                            }
                                        }
                                    },'解除关系')
                                ])
                            } else {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small',
                                            disabled: true
                                        },
                                        on: {
                                            click: () => {
                                                this.$Modal.confirm({
                                                    title: '操作提示',
                                                    content: '<p>您确定要解除与该专家的关系吗？',
                                                    onOk: () => {
                                                        this.removeEmploy(params.row.id)
                                                    }
                                                })
                                            }
                                        }
                                    },'解除关系')
                                ])
                            }
                        }
                    }
                ],
                data:[]
            },
            //邀请通知
            inviteTable:{
                columns:[
                    {
                        title: '申请方',
                        key: 'expert_name'
                    },
                    {
                        title: '发送时间',
                        key: 'create_time'
                    },
                    {
                        title: '状态',
                        key: 'status'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 160,
                        align: 'center',
                        render: (h, params) => {
                            if (params.row.status === '申请辞职中') {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        on: {
                                            click: () => {
                                                this.$Modal.confirm({
                                                    title: '操作提示',
                                                    content: '<p>您确定同意解除关系吗？</p>',
                                                    onOk: () => {
                                                        this.agreeResign(params.row.id)
                                                    }
                                                })
                                            }
                                        }
                                    }, '同意'),
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        on: {
                                            click: () => {
                                                this.$Modal.confirm({
                                                    title: '操作提示',
                                                    content: '<p>您确定拒绝解除关系吗？</p>',
                                                    onOk: () => {
                                                        this.refuseResign(params.row.id)
                                                    }
                                                })
                                            }
                                        }
                                    }, '拒绝')
                                ])
                            } else if (params.row.status === '待处理') {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        on: {
                                            click: () => {
                                                this.$Modal.confirm({
                                                    title: '操作提示',
                                                    content: '<p>您确定要接受邀请吗？</p>',
                                                    onOk: () => {
                                                        this.acceptInvitation(params.row.id)
                                                    }
                                                })
                                            }
                                        }
                                    }, '接受'),
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        on: {
                                            click: () => {
                                                this.$Modal.confirm({
                                                    title: '操作提示',
                                                    content: '<p>您确定要拒绝邀请吗？</p>',
                                                    onOk: () => {
                                                        this.refuseInvitation(params.row.id)
                                                    }
                                                })
                                            }
                                        }
                                    }, '拒绝')
                                ])
                            }
                        }
                    }
                ],
                data:[]
            },
            //关联企业
            companyTable:{
                columns:[
                    {
                        title: '聘请方',
                        key: 'expert_name'
                    },
                    {
                        title: '关系成立时间',
                        key: 'deal_time'
                    },
                    {
                        title: '状态',
                        key: 'status'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 160,
                        align: 'center',
                        render: (h, params) => {
                            if (params.row.status === '申请辞职中') {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small',
                                            disabled: true
                                        },
                                        on: {
                                            click: () => {
                                                this.$Modal.confirm({
                                                    title: '操作提示',
                                                    content: '<p>您确定要解除关系吗？</p>',
                                                    onOk: () => {
                                                        this.resign(params.row.id)
                                                    }
                                                })
                                            }
                                        }
                                    }, '申请解除关系')
                                ])
                            } else {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        on: {
                                            click: () => {
                                                this.$Modal.confirm({
                                                    title: '操作提示',
                                                    content: '<p>您确定要解除关系吗？</p>',
                                                    onOk: () => {
                                                        this.resign(params.row.id)
                                                    }
                                                })
                                            }
                                        }
                                    }, '申请解除关系')
                                ])
                            }
                        }
                    }
                ],
                data:[]
            },
            loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            total6: 0,
            pageCur6: 1
        }
    },

    created () {
        console.log('123', this.loginUser.userType)
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

        this.initTab1({
            keyWord: this.queryInfo.name,
            relatedSpecies: this.queryInfo.speci,
            relatedIndustry: this.queryInfo.trade,
            pageNum: 1,
            pageSize: this.pageSize,
            loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
        })
        this.initTab3({
            pageNum: 1,
            pageSize: this.pageSize4,
            userType: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).userType,
            loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
        })
        if (this.loginUser.userType === 4) {
            this.initTab2({
                pageNum: 1,
                pageSize: this.pageSize2,
                loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
            })
        }
    },
    watch: {
        expertMangeModalShow (oldValue, newValue) {
            console.log('oldValue', oldValue)
            console.log('newValue', newValue)
            if (newValue) {
                this.expertTable.data = []
                this.currentPage = 1
                this.initTab1({
                    keyWord: this.queryInfo.name,
                    relatedSpecies: this.queryInfo.speci,
                    relatedIndustry: this.queryInfo.trade,
                    pageNum: 1,
                    pageSize: this.pageSize,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
                // 调用子组件的方法
                this.$refs.expert.expertAllTable = []
                this.$refs.expert.currentPage = 1
                this.$refs.expert.init({
                    keyWord: '',
                    relatedSpecies: '',
                    relatedIndustry: '',
                    location: '',
                    pageNum: 1,
                    pageSize: 8,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
                this.$refs.expert.queryInfo = {
                    trade: '',
                    tradeId: '',
                    speci: '',
                    speciId: '',
                    name: '',
                    location: '',
                    locationList: []
                }
            }
        }
    },
    methods:{
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
            api.post('/member/specicesClass/findSpecies', a).then(res => {
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
        // 初始化聘请的专家
        initTab1 (params) {
            api.post('/member/Employ/selectAll', params).then(response => {
                console.log('tab1', response)
                if (response.code === 200) {
                    response.data.list.forEach(item => {
                        this.expertTable.data.push({
                            id: item.id,
                            expert_name: item.expertName,
                            field: item.adeptField,
                            speci: item.adeptSpecies,
                            relatedIndustry: item.relatedIndustry,
                            relatedSpecies: item.relatedSpecies,
                            status: item.employStatus
                        })
                    })
                    this.total = response.data.total
                }
            }).catch(error => {
                console.error(error)
            })
        },
        // 初始化受聘企业
        initTab2 (params) {
            api.post('/member/Employ/selectAllCompany', params).then(response => {
                console.log('tab2', response)
                if (response.code === 200) {
                    response.data.list.forEach(item => {
                        this.companyTable.data.push({
                            id: item.id,
                            expert_name: item.corpName,
                            deal_time: item.createTime,
                            status: item.employStatus
                        })
                    })
                    this.total2 = response.data.total
                }
            }).catch(error => {
                console.error(error)
            })
        },
        // 初始化邀请通知
        initTab3 (params) {
            api.post('/member/Employ/selectAllInform', params).then(response => {
                console.log('tab3', response)
                if (response.code === 200) {
                    response.data.list.forEach(item => {
                        this.inviteTable.data.push({
                            id: item.id,
                            expert_name: item.applicantName,
                            create_time: item.createTime,
                            status: item.employStatus
                        })
                    })
                    this.total4 = response.data.total
                }
            }).catch(error => {
                console.error(error)
            })
        },
        // 邀请专家下一页
        selectAllNextPage(page){
            // this.currentPage = page
            this.expertTable.data = []
            this.initTab1({
                keyWord: this.queryInfo.name,
                relatedSpecies: this.queryInfo.speci,
                relatedIndustry: this.queryInfo.trade,
                pageNum: page,
                pageSize: this.pageSize,
                loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
            })
        },
        // 受聘企业下一页
        selectAllCompanyNextPage(page){
            this.currentPage2 = page
            this.companyTable.data = []
            this.initTab2({
                pageNum: page,
                pageSize: this.pageSize2,
                loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
            })
        },
        // 邀请通知下一页
        selectAllInformNextPage (page) {
            this.currentPage4 = page
            this.inviteTable.data = []
            this.initTab3({
                pageNum: page,
                pageSize: this.pageSize4,
                userType: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).userType,
                loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
            })
        },
        //邀请专家
        addExpert(id){
            this.$Modal.confirm({
                title: '是否确认邀请该专家？',
                onOk: () => {
                    api.post('/member/Employ/addExpert', {
                        id: id
                    }).then(response => {
                        console.log(response)
                        this.$Message.info('已经向该专家发出邀请')
                        this.addExpertManage()
                    }).catch(function(error) {
                        console.log(error)
                    })
                },
                onCancel: () => {
                    this.$Message.info('取消邀请')
                }
            })
        },
        // 解聘
        removeEmploy(id){
            api.post('/member/Employ/removeEmploy',{
                id: id
			}).then(response => {
                if(response.data === 1) {
                    this.$Message.success('解除成功!')
                } else {
                    this.$Message.error('解除失败')
                }
                this.expertTable.data = []
                this.initTab1({
                    keyWord: this.queryInfo.name,
                    relatedSpecies: this.queryInfo.speci,
                    relatedIndustry: this.queryInfo.trade,
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
                // 调用子组件的方法
                this.$refs.expert.expertAllTable = []
                this.$refs.expert.init({
                    keyWord: '',
                    relatedSpecies: '',
                    relatedIndustry: '',
                    location: '',
                    pageNum: 1,
                    pageSize: 8,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
			}).catch(function(error) {
				console.log(error)
            })
        },
        // 申请辞职
        resign (id) {
            api.post('/member/Employ/resignation',{
                id: id
			}).then(response => {
                if(response.data === 1) {
                    this.$Message.success('申请成功！')
                } else {
                    this.$Message.error('申请失败！')
                }
                this.companyTable.data = []
                this.initTab2({
                    pageNum: this.currentPage2,
                    pageSize: this.pageSize2,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
			}).catch(function(error) {
				console.log(error)
            })
        },
        // 同意辞职
        agreeResign (id) {
            api.post('/member/Employ/removeEmploy', {
                id: id,	
            }).then(response => {
                console.log(response)
                if(response.data === 1) {
                    this.$Message.success('解除成功！')
                } else {
                    this.$Message.error('解除失败！')
                }
                this.inviteTable.data = []
                this.initTab3({
                    pageNum: this.currentPage4,
                    pageSize: this.pageSize4,
                    userType: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).userType,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
                this.expertTable.data = []
                this.initTab1({
                    keyWord: this.queryInfo.name,
                    relatedSpecies: this.queryInfo.speci,
                    relatedIndustry: this.queryInfo.trade,
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
            }).catch(function(error) {
                console.log(error)
            })
        },
        // 拒绝辞职
        refuseResign (id) {
            api.post('/member/Employ/refuseResignation', {
                id: id,	
            }).then(response => {
                console.log(response)
                if(response.data === 1) {
                    this.$Message.success('拒绝成功！')
                } else {
                    this.$Message.error('拒绝失败！')
                }
                this.inviteTable.data = []
                this.initTab3({
                    pageNum: this.currentPage4,
                    pageSize: this.pageSize4,
                    userType: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).userType,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
                this.expertTable.data = []
                this.initTab1({
                    keyWord: this.queryInfo.name,
                    relatedSpecies: this.queryInfo.speci,
                    relatedIndustry: this.queryInfo.trade,
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
            }).catch(function(error) {
                console.log(error)
            })
        },
        // 接受邀请
        acceptInvitation (id) {
            api.post('/member/Employ/acceptInvitation', {
                id: id,	
            }).then(response => {
                console.log(response)
                if(response.data === 1) {
                    this.$Message.success('接受成功！')
                } else {
                    this.$Message.error('接受失败！')
                }
                this.inviteTable.data = []
                this.initTab3({
                    pageNum: this.currentPage4,
                    pageSize: this.pageSize4,
                    userType: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).userType,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
                this.companyTable.data = []
                this.initTab2({
                    pageNum: this.currentPage2,
                    pageSize: this.pageSize2,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
            }).catch(function(error) {
                console.log(error)
            })
        },
        // 拒绝邀请
        refuseInvitation (id) {
           api.post('/member/Employ/refuseInvitation', {
                id: id,	
            }).then(response => {
                console.log(response)
                if(response.data === 1) {
                    this.$Message.success('拒绝成功！')
                } else {
                    this.$Message.error('拒绝失败！')
                }
                this.inviteTable.data = []
                this.initTab3({
                    pageNum: this.currentPage4,
                    pageSize: this.pageSize4,
                    userType: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).userType,
                    loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                })
            }).catch(function(error) {
                console.log(error)
            })
        },
        //查询所有聘请专家
        selectAll () {
            this.currentPage = 1
            this.expertTable.data = []
            this.initTab1({
                keyWord: this.queryInfo.name,
                relatedSpecies: this.queryInfo.speci,
                relatedIndustry: this.queryInfo.trade,
                pageNum: 1,
                pageSize: this.pageSize,
                loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
            })
        },
        //查询所有被聘请的公司
        selectAllCompany(){
            api.post(
                '/member/Employ/selectAllCompany/'+this.currentPage2 + "?pageSize=" + this.pageSize2,
                ).then(response => {
				console.log(response)
                this.companyTable.data = response.data.list
                this.total2 = response.data.total
			}).catch(function(error) {
				console.log(error)
			})
        }, 
        // //查询所有邀请通知
        selectAllInform(){
            api.post(
                '/member/Employ/selectAllInform/'+this.currentPage4 + "?pageSize=" + this.pageSize4,
                ).then(response => {
				console.log(response)
                this.inviteTable.data = response.data.list
                this.total4 = response.data.total
			}).catch(function(error) {
				console.log(error)
            })
        },                
        //添加按钮
        addExpertManage(){
            this.expertMangeForm.expert_name = ''
            this.expertTable.flag = true
            this.expertMangeModalShow = true
        }        
    }
}
</script>
<style scoped>
.page {
    float: right;
    margin-top: 30px;
}
</style>

<style lang="scss">
.btn {
    text-align: center;
}
.table {
    margin-top: 30px;
}
</style>