<template>
    <div>
        <Button type="primary" long @click="openUserGuide">请打开用户向导</Button>
        <div v-if="step >= 7">
            <Card :bordered="false">
                <Row type="flex" align="middle">
                    <Col span="24">
                        <span v-for="(item, index) in column" :key="index">
                            <!-- <Button :type="active === index ? 'primary' : 'text'" @click="onSelect(index)">{{ item.columnName }}</Button> -->
                            <span :class="active === index ? 'column-active' : 'column-not-active'" @click="onSelect(item, index)">{{ item.columnName }}</span>
                        </span>
                    </Col>
                    <!-- <Col span="3" class="tr">
                        <Button @click="setColumn">栏目设置</Button>
                    </Col> -->
                </Row>
                <hr style="height: 1px; margin: 18px 0;">
                <Row type="flex" align="middle">
                    <Col span="1">
                        <img src="../../../img/publish_icon.png"/>
                    </Col>
                    <Col span="17">
                        <div class="ml20" style="margin-top: 3px;">您可以发布说点什么！</div>
                    </Col>
                    <Col span="6" class="tr">
                        <Button type="primary" @click="handlePublish" v-show="!(active == 0)">发布</Button>
                    </Col>
                </Row>
            </Card>
            <Card class="mt20 mb30" :bordered="false">
                <standard-list ref="standard" v-if="dataType === '会员标准'"></standard-list>
                <!-- <div v-if="dataType === '会员介绍'">
                    <div v-for="(item, index) in list" :key="index">
                        <preview :ref="`preview${item.mode}`" :item="item" :yearId="yearId" :templateId="templateId" @refresh="refresh" />
                    </div>
                </div> -->
                <memberIntroduction v-if="dataType === '会员介绍'" ref="introduction"></memberIntroduction>
                <!-- // knowledge 知识 policy 政策 information dynamic 动态 -->
                <articles-list :docType="docType" v-show="dataType === '全部'" dataType="全部" ref="all"></articles-list>
                <articles-list :docType="docType" v-show="dataType === '会员动态'" dataType="动态" ref="dynamic"></articles-list>
                <articles-list :docType="docType" v-show="dataType === '会员政策'" dataType="政策" ref="policy"></articles-list>
                <articles-list :docType="docType" v-show="dataType === '会员知识'" dataType="知识" ref="knowledge"></articles-list>
                <concat-list  v-if="dataType === '联系方式'" @on-edit="editConcat" ref="contcat"></concat-list>
            </Card>
        </div>
        <Card v-if="step != -1 && step < 7" :bordered="false" class="mt20 tc pt40 pb40 mb30">
            <img src="../../../img/member-icon.png" alt="" height="117px" width="129px">
            <p class="mt40">您还未填写任何介绍信息，点击 <span class="t-green" @click="openUserGuide" style="cursor: pointer; text-decoration: underline;">用户向导</span> 完善信息试试吧～</p>
        </Card>
        
        <!-- 动态政策知识发布 -->
        <publish :options="options" @showDany="closePublish" ref="publish" @on-init="onInit"/>
        <!-- 标准发布 -->
        <standardPublish :options="standardOptions" @showDany="closePublish" ref="standardPublish" @on-init="onInitStandard"/>
        <!-- 联系方式 -->
        <concat ref="concat" @on-init="initConcatList"></concat>
        <!-- 发布服务 -->
        <publishService ref="publishService"></publishService>
        <!-- 会员介绍发布 -->
        <publishMember ref="publishMember" @on-init="introductionInit"></publishMember>
    </div>
</template>
<script>
import articlesList from './articlesList.vue'
import preview from '../../auth/step7/components/preview'
import standardPublish from './standardPublish'
import standardList from './standardList'
import publish from './publish.vue'
import concat from './concat.vue'
import concatList from './concatList.vue'
import publishService from './publishService'
import publishMember from './publishMember'
import memberIntroduction from './memberIntroduction'
export default {
    name: 'memberCenter',
    components: {
        articlesList,
        preview,
        standardPublish,
        standardList,
        publish,
        concat,
        concatList,
        publishService,
        publishMember,
        memberIntroduction
    },
    data () {
        return {
            list: [],
            column: [],
            dataType: '全部',
            templateId: '',
            active: 0,
            yearId: '',
            //发布组件
            options: {
                realUrl: '',
                uploadId: 'DanyId',
                knowledgeShow: false,
                policyShow: false,
                infoShow: false,
                showAll: true,
                showPublish: false,
                categorys: [],
                columnSetting:[],
                title: '动态'
            },
            standardOptions: {
                realUrl: '',
                uploadId: 'DanyId',
                knowledgeShow: false,
                policyShow: false,
                infoShow: false,
                showAll: true,
                showPublish: false,
                columnSetting:[],
                title: '标准'
            },
            step: -1,
            docType: ''
        }
    },
    created () {
        this.$api.post('/member-reversion/realStep/findEnableStep', {
                account: this.$user.loginAccount
            }).then(response => {
            if (response.code === 200) {
                if (response.data) {
                    this.templateId = response.data.templateId
                    this.step = Number(response.data.step)
                    console.log(this.step)
                    if (this.step >= 7) {
                        this.handleInit()
                        // this.getYearId()
                    }
                } else { // 说明没走过认证
                    this.step = -2
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    methods: {
        introductionInit () {
            console.log('发布成功')
            this.$refs['introduction'].getList()
        },
        initConcatList () {
            this.$refs['contcat'].getList()
        },
        // 点击用户向导到哪一步
        clcikAuth () {
            if (this.step == -1) { // 说明还未进行过实名认证
                this.$router.push({
                    path: `/auth/step1`
                })
            } else if (this.step == 6) {
                this.$router.push({
                    path: `/auth/step6`,
                    query: {
                        templateId: this.templateId
                    }
                })
            } else if (this.step == 6.4) {
                this.$router.push({
                    path: `/auth/step7`,
                    query: {
                        templateId: this.templateId
                    }
                })
            } else{
                let step = parseInt(this.step) + 1
                this.$router.push({
                    path: `/auth/step${step}`,
                    query: {
                        templateId: this.templateId
                    }
                })
            }
        },
        closePublish() {
            this.options.showPublish = false
        },
        // 取最上面栏目名称数据
        handleInit () {
            // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
            let url = this.templateId === '0' ? '/member-reversion/columnSetting/findColumnSettingInfo' : '/member-reversion/user/columnSetting/findColumnSettingInfo'
            this.$api.post(url, {
                account: this.$user.loginAccount,
                templateId: this.templateId
            }).then(response => {
                console.log('init res', response)
                if (response.code === 200) {
                    let arr = [{columnName: '首页', attribution: '全部'}]
                    // 会员服务，会员产品，生产基地栏目在会员中心不展示
                    this.column = arr.concat(response.data.columnSetting.filter(element => {
                        return element.attribution !== '会员产品' && element.attribution !== '会员服务' && element.attribution !== '生产基地'
                    }))
                }
            })
        },
        getYearId () {
            // 先查询年度id
            this.$api.post('/member-reversion/perfect/findYearInfo', {
                account: this.$user.loginAccount
            }).then(response => {
                console.log('response', response)
                if (response.code === 200) {
                    let arr = []
                    response.data.forEach(element => {
                        arr.push({
                        name: element.fileName,
                        id: element.id,
                        checked: element.fileName.substring(0, 4) === new Date().getFullYear().toString() ? true : false
                        })
                    })
                    // 默认年份id
                    arr.forEach(element => {
                        if (element.checked) {
                        this.yearId = element.id
                        }
                    })
                    this.getAll()
                }
            })
        },
        refresh () {
            this.getAll()
        },
        // 取完善信息全部
        getAll () {
            this.$api.post('/member-reversion/user/perfect/findAllTextPreviewList', {
                account: this.$user.loginAccount,
                yearId: this.yearId,
                level: '0',
                templateId: this.templateId
            }).then(response => {
                if (response.code === 200) {
                    let arr = []
                    response.data.forEach(element => {
                        let content = ''
                        if (element.textPreview.length !== 0) {
                            element.textPreview.forEach(item => {
                                content += item.textPreview
                            })
                        }
                        arr.push({
                            title: element.appName,
                            content: content,
                            mode: element.url,
                            appId: element.parentId,
                            id: element.textPreview.length !== 0 && element.textPreview[0].textPreviewId !== undefined ? element.textPreview[0].textPreviewId : 0
                        })
                    })
                    this.list = arr
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        openUserGuide () {
            if (this.$user.userType === 10 || this.$user.userType === '10') {
                // 管理员
                this.$router.push(`user-auth-admin/auth/step1`)
            } else {
                // 其余用户
                this.$router.push(`/auth/step1`)
            }
        },
        onSelect (item, index) {
            // 栏目设置中选的都是最末级，此处指需要传入一级栏目即可
            this.dataType = item.attribution.split('/')[0]
            if (item.attributionId) {
                this.docType = item.attributionId.split('/')[1] ? item.attributionId.split('/')[1] : ''
                let type = item.attributionId.split('/')[0]
                console.log(type)
                console.log(this.docType)
                this.$nextTick(() => {
                    this.$refs[`${type}`].getList()
                })
            }
            this.active = index
            if (this.active == 0) {
                this.docType = ''
                this.$nextTick(() => {
                    this.$refs['all'].getNextPage(1)
                })
            }
        },
        // 发布成功的回调
        onInit (type) {
            // knowledge 知识 policy 政策 dynamic 动态
            console.log(type)
            this.$refs[`${type}`].currentPage = 1
            this.$refs[`${type}`].getList()
            // 首页数据 更新
            this.$refs['all'].getList()
        },
        // 标准发布成功的回调
        onInitStandard () {
            this.$refs['standard'].pageNum = 1
            this.$refs['standard'].current = 1
            this.$refs['standard'].init()
        },
        handlePublish () {
            console.log(this.column[this.active])
            let attributionId = this.column[this.active].attributionId.split('/')
            let attribution = this.column[this.active].attribution.split('/')
            let category = ''
            let categoryLabel = ''
            if (attributionId.length > 1) {
                category = attributionId[1]
                categoryLabel = attribution[1]
            }
            if (this.dataType === '会员介绍') {
                this.$refs['publishMember'].init()
            } else if (this.dataType === '联系方式') {
                this.$refs['concat'].init()
            } else if (this.dataType === '会员动态') {
                this.options.title = '动态'
                this.$refs.publish.showMydynamic(0, category, categoryLabel)
                this.options.showPublish = true
            } else if (this.dataType === '会员知识') {
                this.options.title = '知识'
                this.$refs.publish.showMydynamic(2, category, categoryLabel)
                this.options.showPublish = true
            } else if (this.dataType === '会员政策') {
                this.options.title = '政策'
                this.$refs.publish.showMydynamic(1, category, categoryLabel)
                this.options.showPublish = true
            } else if (this.dataType === '会员标准') {
                this.standardOptions.title = '标准'
                this.standardOptions.title = '标准'
                this.standardOptions.showPublish = true
                this.standardOptions.infoShow = true
                this.standardOptions.policyShow = false
                this.standardOptions.knowledgeShow = false
                this.standardOptions.realUrl = '/inforMation/publish'
                this.$refs['standardPublish'].getName()
            }
        },
        // 编辑联系方式
        editConcat (item) {
            this.$refs['concat'].init(item)
        },
        setColumn () {}
    }
}
</script>
<style lang="scss" scoped>
    .column-active {
        color: #00c587;
        // margin-right: 20px;
        display: inline-block;
        padding:10px;
        cursor: pointer;
    }
    .column-not-active {
        // margin-right: 20px;
        display: inline-block;
        padding:10px;
        cursor: pointer;
    }
</style>

