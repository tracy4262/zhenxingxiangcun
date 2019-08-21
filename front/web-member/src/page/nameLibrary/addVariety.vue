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
                    <BreadcrumbItem>新增品种</BreadcrumbItem>
                </Breadcrumb>
                <Steps :current="currentStep" class="mt40 mb40" style="margin-left: 250px; width: 80%;">
                    <Step title="品种基本信息"></Step>
                    <Step title="提交审核"></Step>
                </Steps>
                <div style="width: 100%;">
                    <div v-if="step" style="width: 1000px; margin: 0 auto;">
                        <Form :model="formItem" :label-width="140" label-position="right" ref="formItem" :rules="formItemRules">
                        <Row>
                            <Col span="12">
                                <FormItem label="物种名称" prop="speciesid">
                                    <Input v-model="formItem.specName" readonly @on-focus="handleFilterModal('speciFilter')" />
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem label="品种名称" prop="fname">
                                    <Input v-model="formItem.fname" :maxlength="20" placeholder="请输入内容" @on-change="getAddPinyin" @on-blur="checkAddFname" />
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                            <FormItem label="汉语拼音">
                                <Input v-model="formItem.fpinyin" readonly placeholder="由品种名称自动生成拼音" />
                            </FormItem>
                            </Col>
                            <Col span="12">
                            <FormItem label="品种类型" prop="fvarietykind">
                                <Input v-model="formItem.fvarietykind" :maxlength="20" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                            <FormItem label="选育单位" prop="fbreedingunit">
                                <Input v-model="formItem.fbreedingunit" :maxlength="50" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                            <Col span="12">
                            <FormItem label="是否转基因" prop="fistransgene">
                                <RadioGroup v-model="formItem.fistransgene">
                                    <Radio label=1>是</Radio>
                                    <Radio label=0>否</Radio>
                                </RadioGroup>
                            </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                            <FormItem label="申请日" prop="fapplydate">
                                <DatePicker type="date" placeholder="选择日期" v-model="formItem.fapplydate" class="date-wid24"></DatePicker>
                            </FormItem>
                            </Col>
                            <Col span="12">
                            <FormItem label="申请号" prop="fapplynumber">
                                <Input v-model="formItem.fapplynumber" :maxlength="20" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                            <FormItem label="申请公告日" prop="fapplyannouncedate">
                                <DatePicker class="date-wid24" type="date" placeholder="选择日期" v-model="formItem.fapplyannouncedate"></DatePicker>
                            </FormItem>
                            </Col>
                            <Col span="12">
                            <FormItem label="申请公告号" prop="fapplyannouncenumber">
                                <Input v-model="formItem.fapplyannouncenumber" :maxlength="20" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                        </Row>

                        <Row>
                            <Col span="12">
                            <FormItem label="授权日" prop="fauthdate">
                                <DatePicker class="date-wid24" type="date" placeholder="选择日期" v-model="formItem.fauthdate"></DatePicker>
                            </FormItem>
                            </Col>
                            <Col span="12">
                            <FormItem label="授权号" prop="fauthnumber">
                                <Input v-model="formItem.fauthnumber" :maxlength="20" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                        </Row>

                        <Row>
                            <Col span="12">
                            <FormItem label="授权公告日" prop="fauthannouncedate">
                                <DatePicker class="date-wid24" type="date" placeholder="选择日期" v-model="formItem.fauthannouncedate"></DatePicker>
                            </FormItem>
                            </Col>
                            <Col span="12">
                            <FormItem label="授权公告号" prop="fauthannouncenumber">
                                <Input v-model="formItem.fauthannouncenumber" :maxlength="20" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                        </Row>

                        <Row>
                            <Col span="12">
                            <FormItem label="培育人" prop="fgrowpeople">
                                <Input v-model="formItem.fgrowpeople" :maxlength="20" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                            <Col span="12">
                            <FormItem label="品种权(申请)人" prop="fvarietyowner">
                                <Input v-model="formItem.fvarietyowner" :maxlength="20" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                        </Row>

                        <Row>
                            <Col span="12">
                            <FormItem label="品种权审定编号" prop="fvarietyapprnum">
                                <Input v-model="formItem.fvarietyapprnum" :maxlength="20" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                            <Col span="12">
                            <FormItem label="审定年份" prop="fvarietyapprdate">
                                <DatePicker class="date-wid24" type="year" v-model="formItem.fvarietyapprdate"></DatePicker>
                            </FormItem>
                            </Col>
                        </Row>

                        <Row>
                            <Col span="12">
                            <FormItem label="审定单位" prop="fvarietyapprunit">
                                <Input v-model="formItem.fvarietyapprunit" :maxlength="50" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                            <Col span="12">
                            <FormItem label="特征特性" prop="ffeature">
                                <Input v-model="formItem.ffeature" :maxlength="200" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                        </Row>

                        <Row>
                            <Col span="12">
                            <FormItem label="产量" prop="foutput">
                                <Input v-model="formItem.foutput" :maxlength="20" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                            <Col span="12">
                            <FormItem label="栽培技术" prop="fgrowteachology">
                                <Input v-model="formItem.fgrowteachology" :maxlength="200" placeholder="请输入内容" />
                            </FormItem>
                            </Col>

                            <Col span="12">
                            <FormItem label="适宜种植地区" prop="fsuiteplatearea">
                                <Input v-model="formItem.fsuiteplatearea" :maxlength="200" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                            <Col span="12">
                            <FormItem label="推广现状" prop="fvarietyrecommand">
                                <Input v-model="formItem.fvarietyrecommand" :maxlength="200" placeholder="请输入内容" />
                            </FormItem>
                            </Col>
                        </Row>

                        <Row>
                            <Col span="24">
                                <FormItem label="品种来源" prop="fvarietyorigin">
                                    <Input v-model="formItem.fvarietyorigin" type="textarea" :maxlength="500" :autosize="{minRows: 2,maxRows: 5}" />
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="24">
                                <FormItem label="上传图标" prop="ficon">
                                    <vui-upload
                                        ref="formItemFicon"
                                        @on-getPictureList="getFormItemFicon"
                                        :hint="'图片大小小于2MB，最多上传 1 张'"
                                        :total="1"
                                        :size="[100,100]"
                                        ></vui-upload>
                                </FormItem>
                            </Col>
                            <!-- <Col span="24">
                            <FormItem label="详细图片：">
                                <vui-upload
                                    ref="formItemimage"
                                    @on-getPictureList="getFormItemImage"
                                    :hint="'图片大小小于2MB，最多上传 4 张'"
                                    :total="4"
                                    :size="[100,100]"
                                    ></vui-upload>
                            </FormItem>
                            </Col> -->
                        </Row>
                    </Form>
                    <div align="center" class="mb30">
                        <Button class="ivu-btn-primary" type="default" @click="next()">完成</Button>
                        <Button type="default" @click="complete">取消</Button>
                    </div>
                    </div>
                    <div v-else>
                        <div class="tc pt50 pb30">
                            <h2>您已提交新的品种信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
                        </div>
                        <div class="tc pt30 pb50">
                            <Button type="primary" @click="complete">完成</Button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <foot></foot>
        <!-- 相关物种 -->
        <vui-filter
            ref="speciFilter"
            :cols="2"
            :pageShow="true"
            :total="total"
            :pageCur="pageCur"
            :classifyDatas="speciClassifyDatas"
            :resultDatas="speciResultDatas"
            :load-data="loadSpeciDatas"
            @on-search="handleSpeciSearch"
            @on-get-classify="handleGetSpeciClassify"
            @on-get-result="handleGetSpeciResult"
            @on-page-change="handleSpeciPageChange"/>
    </div>
</template>

<script>
    import top from '../../top'
    import foot from '../../foot'
    import appBanner from '~components/app-banner'
    import vuiUpload from '~components/vui-upload'
    import vuiFilter from '~components/vuiFilter/filter'
    export default {
        components: {
            top,
            appBanner,
            foot,
            vuiUpload,
            vuiFilter
        },
        data () {
            return {
                step: true,
                currentStep: 0,
                total: 0,
                pageCur: 1,
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
                formItem: {
                    specName: '',
                    speciesid: '',
                    fname: '',
                    fpinyin: '',
                    fvarietykind: '',
                    fvarietyorigin: '',
                    fbreedingunit: '',
                    fistransgene: '1',
                    fapplydate: '',
                    fapplynumber: '',
                    fapplyannouncedate: '',
                    fapplyannouncenumber: '',
                    fauthdate: '',
                    fauthnumber: '',
                    fauthannouncedate: '',
                    fauthannouncenumber: '',
                    fgrowpeople: '',
                    fvarietyowner: '',
                    fvarietyapprnum: '',
                    fvarietyapprdate: '',
                    fvarietyapprunit: '',
                    ffeature: '',
                    foutput: '',
                    fgrowteachology: '',
                    fsuiteplatearea: '',
                    fvarietyrecommand: '',
                    ficon: [],
                    image: []
                },
                //新增表单验证
                formItemRules: {
                    speciesid: [
                        {required: true, message: '请选择物种名称', trigger: 'change'}
                    ],
                    fname: [
                        {required: true, message: '请填写品种名称', trigger: 'blur'}
                    ],
                    ficon: [
                        { required: true, type: 'array', min: '1', message: '请上传图标', trigger: 'change' }
                    ]
                }
            }
        },
        created () {
            // 取相关物种结果
            this.loadSpeciResult('', '', [], this.pageCur, [])
        },
        methods: {
            getFormItemFicon (e) {
                var arr = []
                e.forEach(element => {
                    if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.formItem.ficon = arr
                this.handleSubmit('formItem')
            },
            getFormItemImage (e) {
               var arr = []
                e.forEach(element => {
                    if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.formItem.image = arr 
            },
            // 添加时获得拼音
            getAddPinyin () {
                if ('' != this.formItem.fname) {
                    this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.formItem.fname).then(response => {
                        this.formItem.fpinyin = ''
                        this.formItem.fpinyin = response.data
                    }).catch(function (error) {
                        console.log(error)
                    })
                } else {
                    this.formItem.fpinyin = ''
                }
            },
            next () {
                if (this.handleSubmit('formItem')) {
                    this.formItem.fvarietyapprdate = this.formItem.fvarietyapprdate ? this.moment(this.formItem.fvarietyapprdate).format('YYYY') : ''
                    let data = {
                        speciesid: this.formItem.speciesid,
                        fname: this.formItem.fname,
                        fpinyin: this.formItem.fpinyin,
                        fvarietykind: this.formItem.fvarietykind,
                        fvarietyorigin: this.formItem.fvarietyorigin,
                        fbreedingunit: this.formItem.fbreedingunit,
                        fistransgene: this.formItem.fistransgene,
                        fapplydate: this.formItem.fapplydate,
                        fapplynumber: this.formItem.fapplynumber,
                        fapplyannouncedate: this.formItem.fapplyannouncedate,
                        fapplyannouncenumber: this.formItem.fapplyannouncenumber,
                        fauthdate: this.formItem.fauthdate,
                        fauthnumber: this.formItem.fauthnumber,
                        fauthannouncedate: this.formItem.fauthannouncedate,
                        fauthannouncenumber: this.formItem.fauthannouncenumber,
                        fgrowpeople: this.formItem.fgrowpeople,
                        fvarietyowner: this.formItem.fvarietyowner,
                        fvarietyapprnum: this.formItem.fvarietyapprnum,
                        fvarietyapprdate: this.formItem.fvarietyapprdate,
                        fvarietyapprunit: this.formItem.fvarietyapprunit,
                        ffeature: this.formItem.ffeature,
                        foutput: this.formItem.foutput,
                        fgrowteachology: this.formItem.fgrowteachology,
                        fsuiteplatearea: this.formItem.fsuiteplatearea,
                        fmarketsituation: this.formItem.fvarietyrecommand,
                        ficon: this.formItem.ficon,
                        /* image: this.formItem.image, */
                        auditstatus: 2,
                        fcreatorid: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                    }
                    console.log('data', data)
                    this.$api.post('/wiki/api/wiki/saveSpeciesVarietey', data).then(response => {
                        console.log("添加返回的参数==>>", response)
                        if (response.code === 200) {
                            console.log('添加品种id', response.data)
                            this.$Message.success('添加品种成功!')
                            this.step = false
                        } else {
                            this.$Message.success('添加品种失败!')
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            complete () {
                this.$router.push({
                    path: '/pro/nameLibrary',
                    query: {
                        tabValue: 'tab2'
                    }
                })
            },
            // 高级搜索弹窗
            handleFilterModal (name) {
                this.$refs[name].highFilterShow = true
            },
            // 取相关物种
            handleSpeciSearch (letter, keyword, classify, result) {
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
            },
            loadSpeciDatas (item, callback) {
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
            handleGetSpeciClassify (letter, keyword, classify, result) {
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
            },
            handleSpeciPageChange (letter, keyword, classify, num, result) {
                this.pageCur = num
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
            },
            loadSpeciResult (letter, keyword, classify, num, result) {
                if (classify.length) {
                    var arr = []
                    var type = ''
                    classify.forEach(item => {
                        arr.push(item.classId)
                        if(item.value !== undefined) type =item.value
                    })
                } else {
                    arr = null
                }
                this.$api.post('/member/specicesClass/findSpecies', {
                    keywords: keyword,
                    fpinyin: letter === '全部' ? '' : letter,
                    fclassifiedid: arr,
                    type:type,
                    pageNum: num,
                    pageSize: 32
                }).then(res => {
                    var data = res.data
                    this.total = data.total
                    if (result) {
                        result.forEach(item => {
                            data.list.forEach((child, index) => {
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
            handleGetSpeciResult (classify, result) {
                let arr = []
                let arrId = []
                result.forEach(item => arr.push(item.label))
                result.forEach(item => arrId.push(item.value))
                this.formItem.specName = arr.join(' ')
                this.formItem.speciesid = arrId.join(' ')
            },
            checkAddFname () {
                if ('' != this.formItem.fname) {
                    this.$api.get('/wiki/api/wiki/existName/' + 2 + '/' + this.formItem.fname).then(response => {
                        console.log("检测物种名是否被占用:==>>", response.data, "<<==")
                        if (1 === response.data) {
                            this.$Message.success("该物种名已被占用!")
                            this.formItem.fname = ''
                        }
                    }).catch(function (error) {
                        console.log(error)
                    })
                }
            },
            handleSubmit (name) {
                let flag = false;
                this.$refs[name].validate(valid => {
                    if (valid) {
                        flag = true;
                    } else {
                        this.$Message.error('表单验证失败!')
                    }
                });
                return flag
            }
        }
    }
</script>
