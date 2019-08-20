<template>
    <div class="layout">
        <top :address="false"/>
        <div class="main">
            <div class="container">
                <app-banner
                    src="../../../../static/img/app-banner-species.png"
                    title="名称库管理">
                </app-banner>
                <Row :gutter="20">
                    <Col span="24">
                    <Row>
                        <Col span="24">
                            <Breadcrumb class="pt20 pb20">
                                <BreadcrumbItem to="/pro/nameLibrary">名称库管理</BreadcrumbItem>
                                <BreadcrumbItem>新增物种</BreadcrumbItem>
                            </Breadcrumb>
                            <div class="mt40">
                                <Steps :current="currentStep" class="ml60">
                                    <Step title="物种基本信息"></Step>
                                    <Step title="品种基本信息"></Step>
                                    <Step title="病害基本信息"></Step>
                                    <Step title="虫害基本信息"></Step>
                                    <Step title="提交审核"></Step>
                                </Steps>
                            </div>
                            <div style="margin:30px 25%;">
                                <addSpec v-show="currentStep == 0" :formItem="formItem" :speciesid="speciesid" ref="addSpec" @save="save"/>
                            </div>
                            <addSpec2 v-show="currentStep == 1"  :speciesid="speciesid" ref="addSpec2" @save="save"/>
                            <addSpec3 v-show="currentStep == 2"  :speciesid="speciesid" ref="addSpec2" @save="save"/>
                            <addSpec4 v-show="currentStep == 3"  :speciesid="speciesid" ref="addSpec2" @save="save"/>
                            <div v-if="currentStep == 4">
                                <div class="tc pt50 pb30">
                                    <h2>您已提交新的物种信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
                                </div>
                                <div class="tc pt30 pb50">
                                    <Button type="primary"  @click="complete">完成</Button>
                                </div>
                            </div>
                            <div align="center" class="mb30 pt30" v-if="currentStep < 4">
                                <Button type="default" v-if="currentStep != 0 && currentStep < 4"  class="mr20" @click="preStep()">上一步</Button>
                                <Button type="default" class="ivu-btn-primary mr20" @click="nextStep('formItem')">下一步</Button>
                                <Button type="default" class="mr20" @click="exitAdd">退出</Button>
                                <Button type="default" v-if="currentStep != 0 && currentStep < 4" @click="nextStep">跳过此步骤</Button>
                            </div>
                        </Col>
                    </Row>
                    </Col>
                </Row>
            </div>
        </div>
        <foot></foot>
    </div>
</template>

<script>
    import top from '../../top'
    import foot from '../../foot'
    import $ from 'jquery'
    import api from '~api'

    import photoSelector from '~components/photoSelector'
    import addSpec from './components/addSpec'
    import addSpec2 from './components/addSpec2'
    import addSpec3 from './components/addSpec3'
    import addSpec4 from './components/addSpec4'
    import appBanner from '~components/app-banner'

    export default {
        components: {
            top,
            photoSelector,
            addSpec,
            addSpec2,
            addSpec3,
            addSpec4,
            appBanner,
            foot
        },
        data() {
            return {
                formItem: {
                    selectedSpe: '',
                    fname: '',
                    fpinyin: '',
                    otherSelectedSpe: [],
                    findustriaclassifiedid: '',
                    fimage: [],
                    fshapefeatureid: '',
                    fremarks: '',
                    fisprotection: 0
                },
                currentStep: 0,
                displayName: '',
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                signature: '还没有签名！',
                favorite: 0,
                num: 0,
                value: '',
                speciesid:'',
                indexid:'',
                height:''
            }
        },
        created () {
            this.shouTop()
        },
        methods: {
            // 获取页面高度
            handleGetHeight () {
                let clientHeight = document.documentElement.clientHeight
                let topHeight = this.$refs.top.offsetHeight
                let footHeight = this.$refs.foot.offsetHeight
                this.height = `${clientHeight-topHeight-footHeight}px`
                console.log('gaodu')
            },
            // 上一步
            preStep(){
                if(this.currentStep == 1){
                    this.currentStep = 0
                }else if(this.currentStep == 2){
                    this.currentStep = 1
                }else if(this.currentStep == 3){
                    this.currentStep = 2
                }else if(this.currentStep == 4){
                    this.currentStep = 3
                }
            },
            save(response){
                if ( 200 === response.code) {
                    console.log(response)
                    this.$Message.success('添加物种提交成功');
                    this.currentStep = 1
                    this.speciesid = response.data.speciesid
                    this.indexid = response.data.indexid
                } else {
                    this.$Message.error('添加物种提交失败');
                }
            },
            // 点击下一步
            nextStep(name) {
                if(this.currentStep == 0){
                    if(this.indexid){
                        this.$refs.addSpec.update(name,this.indexid)
                    }else{
                        this.$refs.addSpec.get(name)
                    }
                }else if(this.currentStep == 1){
                    this.currentStep = 2
                }else if(this.currentStep == 2){
                    this.currentStep = 3
                }else if(this.currentStep == 3){
                    this.currentStep = 4
                }
            },
            // 点击完成
            complete(){
                this.$router.push("/pro/nameLibrary")
            },
            // 点击退出
            exitAdd() {
                //跳转到名称库管理
                this.$router.push("/pro/nameLibrary")
            },
            /**
             * 头部数据显示
             */
            shouTop() {
                api.get('/member/memberCenter/index')
                    .then(response => {
                        this.favorite = response.data.favorite
                        this.num = response.data.number
                        this.signature = response.data.signature
                        this.displayName = response.data.displayName
                    })
                    .catch(function (error) {
                        this.$Message.error(error)
                    })
            }
        },
        mounted() {
            var loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            this.handleGetHeight()
        }
    }
</script>
<style>
    .two-step {
        margin: 20px auto 0;
        padding-left: 30%;
    }

    .date-wid24 {
        width: 100%;
    }

    .psup-btn {
        border: 1px solid #eee;
        width: 80px;
        height: 80px;
        border-radius: 5px;
        line-height: 78px;
    }

    .psup-btn:hover {
        border-color: #00c587;
        color: #00c587;
    }
</style>
