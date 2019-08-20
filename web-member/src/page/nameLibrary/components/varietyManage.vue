<template>
    <div>
        <div style="width: 100% ;height: 100%">
            <Row>
                <Col span="9">
                    结果排序：
                    <ButtonGroup>
                        <Button type="default" @click="varieteyUp">时间升序</Button>
                        <Button type="default" @click="varieteyDown">时间降序</Button>
                    </ButtonGroup>
                </Col>
                <Col span="9">
                    <Form :model="varieteyForm" :label-width="100">
                        <FormItem label="关键字：">
                            <Row>
                                <Col span="22">
                                <Input v-model="varieteyForm.keywords" placeholder="请输入内容"/>
                                </Col>
                            </Row>
                        </FormItem>
                    </Form>
                </Col>
                <Col span="2">
                    <ButtonGroup>
                        <Button type="default" @click="varieteySearch">查找</Button>
                    </ButtonGroup>
                </Col>
                <Col span="4">
                    <ButtonGroup>
                        <Button type="default" @click="addVariety">新增品种</Button>
                    </ButtonGroup>
                </Col>
            </Row>
            <Row style="margin: 10px;">
                <ul>
                    <li v-for="(varietey,index) in varieteyList" :key="index">
                        <div class="listDemo">
                            <template v-if="varietey.ficon.length >0 ">
                                <img :src="varietey.ficon[0]" width="200px" height="200px"/>
                            </template>
                            <template v-else>
                                <img src="../../../img/noPic.png" width="200px" height="200px"/>
                            </template>
                            <div class="species-tool pd10 clear">
                                <p class="p1 clear">
                                    {{varietey.fname}}
                                    <span v-if="varietey.auditstatus === 1" class="fr t-green">
                                        审核通过
                                    </span>
                                    <span v-else-if="varietey.auditstatus === 4" class="fr t-red">
                                        未审核通过
                                    </span>
                                    <span v-else-if="varietey.auditstatus === 0" class="fr t-gray">
                                        更新待审核
                                    </span>
                                    <span v-else-if="varietey.auditstatus === 2" class="fr t-gray">
                                        新增待审核
                                    </span>
                                    <span v-else-if="varietey.auditstatus === 3" class="fr t-gray">
                                        删除待审核
                                    </span>
                                </p>
                                <p class="p2 mt10 mb10">{{varietey.fvarietyorigin}}</p>
                                <p class="p3" v-if="varietey.auditstatus === 1">
                                    <a href="javascript:void(0)" @click="editVarietey(varietey.indexid)"><span>编辑</span></a>
                                    <a href="javascript:void(0)" @click="delVarietey(varietey.indexid)"><span>删除</span></a>
                                    <!-- <a href="#"><span>图库</span></a> -->
                                </p>
                                <p class="p3" v-else>
                                    <a href="javascript:void(0)" @click="show(varietey.indexid)"><span>查看</span></a>
                                </p>
                            </div>
                        </div>
                    </li>
                </ul>
                <div style="float:right;margin-top: 250px">
                    <Page :total="varieteyTotal" :current="varieteyCurrentPage" :page-size="varieteyPageSize" @on-change="varieteyPageChange" show-total></Page>
                </div>
            </Row>
        </div>
        <!-- 相关物种 -->
        <vui-filter
            ref="speciFilter"
            :cols="2"
            :transfer="true"
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

        <!-- 品种编辑弹框-->
        <Modal v-model="editVarieteyModal" :title="isShow ? '品种查看' : '品种编辑'" width="800" :mask-closable="false" :transfer="false">
            <div align="left" style="margin-top: 5%;margin-left: 10%;margin-right: 10%">
                <Form :model="editVarieteyForm" :label-width="180" label-position="right" ref="editVarieteyForm" :rules="editVarieteyRules">
                    <FormItem label="物种名称" prop="speciesid">
                        <Input v-model="editVarieteyForm.specName" placeholder="请输入内容" readonly @on-focus="handleFilterModal('speciFilter')" />
                    </FormItem>
                    <FormItem label="品种名称" prop="fname">
                        <Input v-model="editVarieteyForm.fname" placeholder="请输入内容" @on-change="getEditPinyin" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="汉语拼音" prop="fpinyin">
                        <Input v-model="editVarieteyForm.fpinyin" readonly placeholder="由品种名称自动生成拼音" />
                    </FormItem>
                    <FormItem label="品种类型" prop="fvarietykind">
                        <Input v-model="editVarieteyForm.fvarietykind" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="品种来源" prop="fvarietyorigin">
                        <Input v-model="editVarieteyForm.fvarietyorigin" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." :readonly="isShow" />
                    </FormItem>
                    <FormItem label="选育单位" prop="fbreedingunit">
                        <Input v-model="editVarieteyForm.fbreedingunit" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="是否转基因" prop="fistransgene">
                        <RadioGroup v-model="editVarieteyForm.fistransgene" :disabled="isShow">
                            <Radio label=1>是</Radio>
                            <Radio label=0>否</Radio>
                        </RadioGroup>
                    </FormItem>
                    <FormItem label="申请日" prop="fapplydate">
                        <DatePicker type="date" placeholder="选择日期" v-model="editVarieteyForm.fapplydate" :disabled="isShow"></DatePicker>
                    </FormItem>
                    <FormItem label="申请号" prop="fapplynumber">
                        <Input v-model="editVarieteyForm.fapplynumber" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="申请公告日" prop="fapplyannouncedate">
                        <DatePicker type="date" placeholder="选择日期" v-model="editVarieteyForm.fapplyannouncedate" :disabled="isShow"></DatePicker>
                    </FormItem>
                    <FormItem label="申请公告号" prop="fapplyannouncenumber">
                        <Input v-model="editVarieteyForm.fapplyannouncenumber" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="授权日" prop="fauthdate">
                        <DatePicker type="date" placeholder="选择日期" v-model="editVarieteyForm.fauthdate" :disabled="isShow"></DatePicker>
                    </FormItem>
                    <FormItem label="授权号" prop="fauthnumber">
                        <Input v-model="editVarieteyForm.fauthnumber" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="授权公告日" prop="fauthannouncedate">
                        <DatePicker type="date" placeholder="选择日期" v-model="editVarieteyForm.fauthannouncedate" :disabled="isShow"></DatePicker>
                    </FormItem>
                    <FormItem label="授权公告号" prop="fauthannouncenumber">
                        <Input v-model="editVarieteyForm.fauthannouncenumber" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="培育人" prop="fgrowpeople">
                        <Input v-model="editVarieteyForm.fgrowpeople" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="品种权(申请)人" prop="fvarietyowner">
                        <Input v-model="editVarieteyForm.fvarietyowner" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="品种权审定编号" prop="fvarietyapprnum">
                        <Input v-model="editVarieteyForm.fvarietyapprnum" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="审定年份" prop="fvarietyapprdate">
                        <DatePicker type="year" v-model="editVarieteyForm.fvarietyapprdate" :disabled="isShow"></DatePicker>
                    </FormItem>
                    <FormItem label="审定单位" prop="fvarietyapprunit">
                        <Input v-model="editVarieteyForm.fvarietyapprunit" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="特征特性" prop="ffeature">
                        <Input v-model="editVarieteyForm.ffeature" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="产量" prop="foutput">
                        <Input v-model="editVarieteyForm.foutput" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="栽培技术" prop="fgrowteachology">
                        <Input v-model="editVarieteyForm.fgrowteachology" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="适宜种植地区" prop="fsuiteplatearea">
                        <Input v-model="editVarieteyForm.fsuiteplatearea" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="推广现状" prop="fvarietyrecommand">
                        <Input v-model="editVarieteyForm.fvarietyrecommand" placeholder="请输入内容" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="上传图标" prop="ficon">
                        <vui-upload
                            v-if="!isShow"
                            ref="uploadFicon"
                            @on-getPictureList="getFicon"
                            :hint="'图片大小小于2MB，最多上传 1 张'"
                            :total="1"
                            :size="[100,100]"
                            ></vui-upload>
                            <span v-else>
                                <img v-for="(item,index) in editVarieteyForm.ficon" :key="index" :src="item" style="width: 100px; height: 100px;">
                            </span>
                    </FormItem>
                    <!-- <FormItem label="详细图片">
                        <vui-upload
                            ref="uploadImage"
                            @on-getPictureList="getImage"
                            :hint="'图片大小小于2MB，最多上传 4 张'"
                            :total="4"
                            :size="[100,100]"
                            ></vui-upload>
                    </FormItem> -->
                </Form>
            </div>
            <div slot="footer">
                <Button type="text" @click="editCancel">取消</Button>
                <Button type="primary" @click="updateVarietey" v-if="!isShow">确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
    import vuiFilter from '~components/vuiFilter/filter'
    import vuiUpload from '~components/vui-upload'

    export default {
        components: {
            vuiFilter,
            vuiUpload
        },
        data() {
            return {
                isShow: false,
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
                // 品种分页
                varieteyTotal: 0,
                varieteyCurrentPage: 1,
                varieteyPageSize: 12,
                editVarieteyModal: false,
                total: 0,
                pageCur: 1,
                // 编辑品种表单
                editVarieteyForm: {
                    fid: '',
                    specName: '',
                    indexid: '',
                    speciesid: '',
                    fname: '',
                    fpinyin: '',
                    fvarietykind: '',
                    fvarietyorigin: '',
                    fbreedingunit: '',
                    fistransgene: '',
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
                editVarieteyRules: {
                    fname: [
                        {required: true, message: '请填写品种名称', trigger: 'blur'}
                    ],
                    speciesid: [
                        {required: true, message: '请选择物种名称', trigger: 'change'}
                    ],
                    ficon: [
                        { required: true, type: 'array', min: '1', message: '请上传图标', trigger: 'change' }
                    ]
                },
                size: 0,
                //品种列表
                varieteyList: [],
                //品种查询表单
                varieteyForm: {
                    keywords: '',
                    sortType: 0,
                },
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            }
        },
        created () {
            // 取相关物种结果
            this.loadSpeciResult('', '', [], this.pageCur, [])
            // 品种列表查询
            this.varieteyList = []
            this.$api.post('/wiki/api/wiki/listSpeciesVarietey', {
                sortType: 0,
                userId: this.loginuserinfo.loginAccount,
                pageNum: 1,
                pageSize: 12,
                auditstatus: 6
            }).then(response => {
                if (response.code === 200) {
                    this.varieteyList = response.data
                    this.varieteyTotal = response.total
                } else {
                    this.$Message.error('查询品种列表失败！')
                }
            }).catch(error => {
                this.$Message.error('查询品种列表失败！')
            })
        },
        methods: {
            show (e) {
                this.$refs['editVarieteyForm'].resetFields()
                this.editVarieteyModal = true
                this.isShow = true
                this.$nextTick(() => {
                    this.getData(e)
                })
            },
            // 修改获取图片
            getFicon (e) {
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.editVarieteyForm.ficon = arr
                this.handleSubmit('editVarieteyForm')
            },
            /* getImage (e) {
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.editVarieteyForm.image = arr
            }, */
            // 高级搜索弹窗
            handleFilterModal (name) {
                if (!this.isShow) {
                    this.$refs[name].highFilterShow = true
                }
            },
            // 取相关物种
            handleSpeciSearch(letter, keyword, classify, result) {
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
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
            },
            handleGetSpeciClassify(letter, keyword, classify, result) {
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
            },
            handleSpeciPageChange(letter, keyword, classify, num, result) {
                this.pageCur = num
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
            },
            loadSpeciResult(letter, keyword, classify, num, result) {
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
            handleGetSpeciResult(classify, result) {
                var arr = []
                var arrId = []
                result.forEach(item => arr.push(item.label))
                result.forEach(item => arrId.push(item.value))
                this.editVarieteyForm.speciesid = arrId.join(' ')
                this.editVarieteyForm.specName = arr.join(' ')
            },
            editCancel () {
                this.editVarieteyModal = false
            },
            handleSubmit (name) {
                let flag = false;
                this.$refs[name].validate(valid => {
                    if (valid) {
                        flag = true
                    } else {
                        this.$Message.error('表单验证失败!')
                    }
                });
                return flag
            },
            // 点击跳转添加品种
            addVariety () {
                this.$router.push('/pro/addVariety')
            },
            // 品种升序查询
            varieteyUp () {
                this.varieteyForm.sortType = 1
                this.varieteySearch()
            },
            // 品种降序查询
            varieteyDown () {
                this.varieteyForm.sortType = 0
                this.varieteySearch()
            },
            //品种查询按钮
            varieteySearch () {
                this.varieteyList = []
                this.varieteyTotal = 0
                this.$api.post('/wiki/api/wiki/listSpeciesVarietey', {
                    keywords: this.varieteyForm.keywords,
                    sortType: this.varieteyForm.sortType,
                    userId: this.loginuserinfo.loginAccount,
                    pageNum: this.varieteyCurrentPage,
                    pageSize: 25,
                    auditstatus: 6
                }).then(response => {
                    if (response.code === 200){
                        this.varieteyList = response.data
                        this.varieteyTotal = response.total
                    } else {
                        this.$Message.error('查询品种列表失败！')
                    }
                }).catch(error => {
                    this.$Message.error('查询品种列表失败！')
                })
            },
            //品种页码改变
            varieteyPageChange (e) {
                // 获得当前页码
                this.varieteyCurrentPage = e
                var loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
                this.varieteyList = []
                this.varieteyTotal = 0
                this.varieteySearch()
            },
            // 编辑品种回显
            editVarietey (e) {
                this.$refs['editVarieteyForm'].resetFields()
                this.editVarieteyModal = true
                this.isShow = false
                this.getData(e)
            },
            getData (e) {
                this.$api.get('/wiki/api/wiki/getSpeciesVarietey/' + e).then(response => {
                    console.log('edit res', response)
                    this.editVarieteyForm.fid = response.data.fid
                    this.editVarieteyForm.indexid = response.data.indexid
                    this.editVarieteyForm.speciesid = response.data.speciesid
                    this.editVarieteyForm.specName = response.data.specName
                    this.editVarieteyForm.fname = response.data.fname
                    this.editVarieteyForm.fpinyin = response.data.fpinyin
                    this.editVarieteyForm.fvarietykind = response.data.fvarietykind
                    this.editVarieteyForm.fvarietyorigin = response.data.fvarietyorigin
                    this.editVarieteyForm.fbreedingunit = response.data.fbreedingunit
                    if( response.data.fistransgene==="否"){
                        this.editVarieteyForm.fistransgene='0'
                    }else{
                        this.editVarieteyForm.fistransgene='1'
                    }
                    this.editVarieteyForm.fapplydate = response.data.fapplydate
                    this.editVarieteyForm.fapplynumber = response.data.fapplynumber
                    this.editVarieteyForm.fapplyannouncedate = response.data.fapplyannouncedate
                    this.editVarieteyForm.fapplyannouncenumber = response.data.fapplyannouncenumber
                    this.editVarieteyForm.fauthdate = response.data.fauthdate
                    this.editVarieteyForm.fauthnumber = response.data.fauthnumber
                    this.editVarieteyForm.fauthannouncedate = response.data.fauthannouncedate
                    this.editVarieteyForm.fauthannouncenumber = response.data.fauthannouncenumber
                    this.editVarieteyForm.fgrowpeople = response.data.fgrowpeople
                    this.editVarieteyForm.fvarietyowner = response.data.fvarietyowner
                    this.editVarieteyForm.fvarietyapprnum = response.data.fvarietyapprnum
                    this.editVarieteyForm.fvarietyapprdate = response.data.fvarietyapprdate
                    this.editVarieteyForm.fvarietyapprunit = response.data.fvarietyapprunit
                    this.editVarieteyForm.ffeature = response.data.ffeature
                    this.editVarieteyForm.foutput = response.data.foutput
                    this.editVarieteyForm.fgrowteachology = response.data.fgrowteachology
                    this.editVarieteyForm.fsuiteplatearea = response.data.fsuiteplatearea
                    this.editVarieteyForm.fvarietyrecommand = response.data.fmarketsituation
                    this.editVarieteyForm.ficon = response.data.ficon
                    this.editVarieteyForm.image = response.data.image                        
                    // this.$refs['uploadImage'].handleGive(this.editVarieteyForm.image)
                    if (!this.isShow) {
                        this.$refs['uploadFicon'].handleGive(this.editVarieteyForm.ficon)
                    }                    
                }).catch(error => {
                    console.log(error)
                })
            },
            // 删除品种
            delVarietey (e) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '<p>您确定删除此品种？</p>',
                    cancelText: '取消',
                    onOk: () => {
                        this.$api.get(`/wiki/api/wiki/deleteSpeciesVarietey/${e}/${this.loginuserinfo.loginAccount}`).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('删除品种成功!')
                                this.varieteySearch()
                            } else {
                                this.$Message.error('删除品种失败！')
                            }
                        }).catch(error => {
                            this.$Message.error('删除品种失败！')
                        })
                    }
                })
            },
            // 确认更新品种
            updateVarietey () {
                if (!this.handleSubmit('editVarieteyForm')) {
                    return
                }
                let fistransgene = ''
                if(this.editVarieteyForm.fistransgene === '是'){
                    fistransgene = '1'
                }else{
                    fistransgene = '0'
                }
                this.editVarieteyForm.fvarietyapprdate = this.editVarieteyForm.fvarietyapprdate ? this.moment(this.editVarieteyForm.fvarietyapprdate).format('YYYY') : ''
                var loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
                this.$api.post('/wiki/api/wiki/updateSpeciesVarietey',
                    {
                        fid: this.editVarieteyForm.fid,
                        indexid: this.editVarieteyForm.indexid,
                        speciesid: this.editVarieteyForm.speciesid,
                        fname: this.editVarieteyForm.fname,
                        fpinyin: this.editVarieteyForm.fpinyin,
                        fvarietykind: this.editVarieteyForm.fvarietykind,
                        fvarietyorigin: this.editVarieteyForm.fvarietyorigin,
                        fbreedingunit: this.editVarieteyForm.fbreedingunit,
                        fistransgene: fistransgene,
                        fapplydate: this.editVarieteyForm.fapplydate,
                        fapplynumber: this.editVarieteyForm.fapplynumber,
                        fapplyannouncedate: this.editVarieteyForm.fapplyannouncedate,
                        fapplyannouncenumber: this.editVarieteyForm.fapplyannouncenumber,
                        fauthdate: this.editVarieteyForm.fauthdate,
                        fauthnumber: this.editVarieteyForm.fauthnumber,
                        fauthannouncedate: this.editVarieteyForm.fauthannouncedate,
                        fauthannouncenumber: this.editVarieteyForm.fauthannouncenumber,
                        fgrowpeople: this.editVarieteyForm.fgrowpeople,
                        fvarietyowner: this.editVarieteyForm.fvarietyowner,
                        fvarietyapprnum: this.editVarieteyForm.fvarietyapprnum,
                        fvarietyapprdate: this.editVarieteyForm.fvarietyapprdate,
                        fvarietyapprunit: this.editVarieteyForm.fvarietyapprunit,
                        ffeature: this.editVarieteyForm.ffeature,
                        foutput: this.editVarieteyForm.foutput,
                        fgrowteachology: this.editVarieteyForm.fgrowteachology,
                        fsuiteplatearea: this.editVarieteyForm.fsuiteplatearea,
                        fmarketsituation: this.editVarieteyForm.fvarietyrecommand,
                        ficon: this.editVarieteyForm.ficon,
                        // image: this.editVarieteyForm.image,
                        fupdatorid: loginuserinfo.loginAccount,
                        fcreatorid: loginuserinfo.loginAccount
                    }
                ).then(response => {
                    console.log("提交的参数==>>", response)
                    if (200 === response.code) {
                        console.log('更新返回值', response.data)
                        this.$Message.success('更新品种成功!')
                        this.editCancel()
                        this.varieteySearch()
                    } else {
                        this.$Message.success('更新品种失败!')
                    }
                }).catch(error => {
                    console.log(error)
                })
                this.varieteySearch()
            },
            // 编辑时获得拼音
            getEditPinyin () {
                if ('' != this.editVarieteyForm.fname) {
                    this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.editVarieteyForm.fname).then(response => {
                        this.editVarieteyForm.fpinyin = ''
                        this.editVarieteyForm.fpinyin = response.data
                    }).catch(error => {
                        console.log(error)
                    })
                } else {
                    this.editVarieteyForm.fpinyin = ''
                }

            }
        }
    }
</script>

