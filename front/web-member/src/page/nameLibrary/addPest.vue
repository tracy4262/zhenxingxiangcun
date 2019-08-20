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
                    <BreadcrumbItem>新增虫害</BreadcrumbItem>
                </Breadcrumb>
                <Steps :current="currentStep" class="mt40 mb40" style="margin-left: 250px; width: 80%;">
                    <Step title="虫害基本信息"></Step>
                    <Step title="提交审核"></Step>
                </Steps>
                <div style="width: 100%;">
                    <div v-if="step" style="width: 800px; margin: 0 auto;">
                        <Form :model="formItem" ref="formItem" :label-width="120" label-position="right" :rules="formItemRule">
                            <FormItem label="虫害名称" prop="fname">
                                <Input v-model="formItem.fname" placeholder="请输入内容" @on-change="getAddPinyin"  @on-blur="checkAddFname" />
                            </FormItem>
                            <FormItem label="汉语拼音" prop="fpinyin">
                                <Input v-model="formItem.fpinyin" placeholder="由品种名称自动生成拼音" />
                            </FormItem>
                            <FormItem label="危害物种" prop="speciesid">
                                <Input v-model="formItem.specName" placeholder="请输入内容" readonly @on-focus="handleFilterModal('speciFilter')" />
                            </FormItem>
                            <FormItem label="上传图标" prop="fimagesrc">
                                <vui-upload
                                    ref="formItemFimagesrc"
                                    @on-getPictureList="getFormItemFimagesrc"
                                    :hint="'图片大小小于2MB，最多上传 1 张'"
                                    :total="1"
                                    :size="[100,100]"
                                ></vui-upload>
                            </FormItem>
                            <FormItem label="形态特征" prop="fmainfeatures">
                                <Input v-model="formItem.fmainfeatures" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
                            </FormItem>
                            <FormItem label="危害症状" prop="fhabit">
                                <Input v-model="formItem.fhabit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
                            </FormItem>
                            <FormItem label="发生规律" prop="fpetsregular">
                                <Input v-model="formItem.fpetsregular" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
                            </FormItem>
                            <FormItem label="防治方法" prop="fprotectmethod">
                                <Input v-model="formItem.fprotectmethod" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
                            </FormItem>
                            <FormItem label="备注" prop="fremarks">
                                <Input v-model="formItem.fremarks" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
                            </FormItem>
                        </Form>
                    <div align="center" class="mb30">
                        <Button class="ivu-btn-primary" type="default" @click="next">完成</Button>
                        <Button type="default" @click="complete">取消</Button>
                    </div>
                    </div>
                    <div v-else>
                        <div class="tc pt50 pb30">
                            <h2>您已提交新的虫害信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
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
                currentStep: 0,
                formItem: {
                    specName: '',
                    speciesid: '',
                    fname: '',
                    fpinyin: '',
                    fimagesrc: [],
                    fmainfeatures: '',
                    fhabit: '',
                    fpetsregular: '',
                    fprotectmethod: '',
                    fremarks: '',
                    fcreatorid: ''
                },
                formItemRule: {
                    fname: [
                        {required: true, message: '请填写虫害名称', trigger: 'blur'}
                    ],
                    speciesid: [
                        {required: true, message: '请选择危害物种', trigger: 'change'}
                    ],
                    fimagesrc: [
                        { required: true, type: 'array', min: '1', message: '请上传图标', trigger: 'change' }
                    ]
                },
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            }
        },
        created () {
            // 取相关物种结果
            this.loadSpeciResult('', '', [], this.pageCur, [])
        },
        methods: {
            getFormItemFimagesrc (e) {
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.formItem.fimagesrc = arr
                this.handleSubmit('formItem')
            },
            handleSubmit (name) {
                let flag = false
                this.$refs[name].validate(valid => {
                    if (valid) {
                    flag = true
                    } else {
                        this.$Message.error('表单验证失败!')
                    }
                })
                return flag
            },
            //---检测虫害名是否占用-------
            checkAddFname () {
                if ('' != this.formItem.fname) {
                    this.$api.get('/wiki/api/wiki/existName/' + 4 + '/' + this.formItem.fname).then(response => {
                        console.log("检测物种名是否被占用:==>>", response.data, "<<==")
                        if (1 === response.data) {
                            this.$Message.error('该虫害名称已被占用！')
                            this.formItem.fname = ''
                            this.formItem.fpinyin = ''
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            // 完成
            next () {
                if (this.handleSubmit('formItem')) {
                    let data = {
                        fcreatorid: this.loginuserinfo.loginAccount,
                        speciesid: this.formItem.speciesid,
                        fname: this.formItem.fname,
                        fpinyin: this.formItem.fpinyin,
                        fimagesrc: this.formItem.fimagesrc,
                        fmainfeatures: this.formItem.fmainfeatures,
                        fhabit: this.formItem.fhabit,
                        fpetsregular: this.formItem.fpetsregular,
                        fprotectmethod: this.formItem.fprotectmethod,
                        fremarks: this.formItem.fremarks,
                        auditstatus: 2
                    }
                    console.log('data', data)
                    this.$api.post('/wiki/api/wiki/saveSpeciesPest', data).then(response => {
                        console.log("response", response)
                        if (response.code === 200) {
                            this.$Message.success('添加虫害成功!')
                            this.step = false
                            this.currentStep = 1
                        } else {
                            this.$Message.success('添加虫害失败!')
                        }
                    }).catch(error => {
                        this.$Message.success('添加虫害失败!')
                    })                   
                }
            },
            // 退出
            complete () {
                this.$router.push({
                    path: '/pro/nameLibrary',
                    query: {
                        tabValue: 'tab4'
                    }
                })
            },
            // 得到汉字的拼音
            getAddPinyin () {
                if ('' != this.formItem.fname) {
                    this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.formItem.fname).then(response => {
                        this.formItem.fpinyin = ''
                        this.formItem.fpinyin = response.data
                    }).catch(error => {
                        console.log(error)
                    })
                } else {
                    this.formItem.fpinyin = ''
                }

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
                    var type =''
                    classify.forEach(item => {
                        arr.push(item.classId)
                        if (item.valueOf !== undefined)
                            type = item.value
                    })
                } else {
                    arr = null
                }
                this.$api.post('/member/specicesClass/findSpecies', {
                    keywords: keyword,
                    fpinyin: letter === '全部' ? '' : letter,
                    fclassifiedid: arr,
                    pageNum: num,
                    type:type,
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
                var arr = []
                var arrId = []
                result.forEach(item => arr.push(item.label))
                result.forEach(item => arrId.push(item.value))
                this.formItem.speciesid = arrId.join(' ')
                this.formItem.specName = arr.join(' ')
            }
        }
    }
</script>
