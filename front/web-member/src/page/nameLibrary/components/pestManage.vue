<template>
    <div>
        <div style="width: 100% ;height: 100%">
            <Row>
                <Col span="10">
                    结果排序：
                    <ButtonGroup>
                        <Button type="default" @click="pestUp">时间升序</Button>
                        <Button type="default" @click="pestDown">时间降序</Button>
                    </ButtonGroup>
                </Col>
                <Col span="8">
                    <Form :model="pestForm" :label-width="100">
                        <FormItem label="关键字：">
                            <Row>
                                <Col span="22">
                                    <Input v-model="pestForm.keywords" placeholder="请输入内容" />
                                </Col>
                            </Row>
                        </FormItem>
                    </Form>
                </Col>
                <Col span="2">
                    <ButtonGroup>
                        <Button type="default" @click="pestSearch()">查找</Button>
                    </ButtonGroup>
                </Col>
                <Col span="4">
                    <ButtonGroup>
                        <Button type="default" @click="addPest">新增虫害</Button>
                    </ButtonGroup>
                </Col>
            </Row>
            <Row style="margin: 10px;">
                <ul>
                    <li v-for="(pest,index) in pestList" :key="index">
                        <div class="listDemo">
                            <template v-if="pest.fimagesrc.length>0">
                                <img :src="pest.fimagesrc[0]" width="200px" height="200px"/>
                            </template>
                            <template v-else>
                                <img src="../../../img/noPic.png" width="200px" height="200px"/>
                            </template>
                            <div class="species-tool pd10 clear">
                                <p class="p1 clear">
                                    {{pest.fname}}
                                    <span v-if="pest.auditstatus === 1" class="fr t-green">
                                        审核通过
                                    </span>
                                    <span v-else-if="pest.auditstatus === 4" class="fr t-red">
                                        未审核通过
                                    </span>
                                    <span v-else-if="pest.auditstatus === 0" class="fr t-gray">
                                        更新待审核
                                    </span>
                                    <span v-else-if="pest.auditstatus === 2" class="fr t-gray">
                                        新增待审核
                                    </span>
                                    <span v-else-if="pest.auditstatus === 3" class="fr t-gray">
                                        删除待审核
                                    </span>
                                </p>
                                <p class="p2 mt10 mb10">{{pest.fmainfeatures}}</p>
                                <p class="p3" v-if="pest.auditstatus === 1">
                                    <a href="javascript:void(0)" @click="editPest(pest.indexid)"><span>编辑</span></a>
                                    <a href="javascript:void(0)" @click="delPest(pest.indexid)"><span>删除</span></a>
                                    <!-- <a href="#"><span>图库</span></a> -->
                                </p>
                                <p class="p3" v-else>
                                    <a href="javascript:void(0)" @click="show(pest.indexid)"><span>查看</span></a>
                                </p>
                            </div>
                        </div>
                    </li>
                </ul>
                <div style="float:right;margin-top: 250px">
                    <Page :total="pestTotal" :current="pestCurrentPage" :page-size="pestPageSize" @on-change="pestPageChange" show-total></Page>
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
            @on-page-change="handleSpeciPageChange" />

        <!-- 编辑虫害弹框 -->
        <Modal v-model="editPestModal" :title="isShow ? '虫害查看' : '虫害编辑'" width="800" :transfer="false">
            <div align="left" style="margin-top: 5%;margin-left: 10%;margin-right: 10%">
                <Form :model="editPestForm" :label-width="120" label-position="right" ref="editPestForm" :rules="editPestRules">
                    <FormItem label="害虫名称" prop="fname">
                        <Input v-model="editPestForm.fname" placeholder="请输入内容" @on-change="getPinyin" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="汉语拼音">
                        <Input v-model="editPestForm.fpinyin" placeholder="由品种名称自动生成拼音" readonly />
                    </FormItem>
                    <FormItem label="危害物种" prop="speciesid">
                        <Input v-model="editPestForm.specName" placeholder="请输入内容" readonly @on-focus="handleFilterModal('speciFilter')" />
                    </FormItem>
                    <FormItem label="上传图片" prop="fimagesrc">
                        <vui-upload
                            v-if="!isShow"
                            ref="fimagesrc"
                            @on-getPictureList="getFimagesrc"
                            :hint="'图片大小小于2MB，最多上传 1 张'"
                            :total="1"
                            :size="[100,100]">
                        </vui-upload>
                        <span v-else>
                            <img v-for="(item,index) in editPestForm.fimagesrc" :key="index" :src="item" style="width: 100px; height: 100px;">
                        </span>
                    </FormItem>
                    <FormItem label="形态特征">
                        <Input v-model="editPestForm.fmainfeatures" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." :readonly="isShow" />
                    </FormItem>
                    <FormItem label="危害症状">
                        <Input v-model="editPestForm.fhabit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." :readonly="isShow" />
                    </FormItem>
                    <FormItem label="发生规律">
                        <Input v-model="editPestForm.fpetsregular" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." :readonly="isShow" />
                    </FormItem>
                    <FormItem label="防治方法">
                        <Input v-model="editPestForm.fprotectmethod" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." :readonly="isShow" />
                    </FormItem>
                    <FormItem label="备注">
                        <Input v-model="editPestForm.fremarks" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." :readonly="isShow" />
                    </FormItem>
                </Form>
            </div>
            <div slot="footer">
                <Button type="text" @click="editCancel">取消</Button>
                <Button type="primary" @click="updatePest" v-if="!isShow">确定</Button>
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
                //虫害分页
                pestTotal: 0,
                pestCurrentPage: 1,
                pestPageSize: 12,
                //编辑弹窗
                editPestModal: false,
                isShow: false,
                // 编辑虫害表单
                editPestForm: {
                    fid: '',
                    specName:'',
                    indexid: '',
                    speciesid: '',
                    fname: '',
                    fpinyin: '',
                    fimagesrc: [],
                    fmainfeatures: '',
                    fhabit: '',
                    fpetsregular: '',
                    fprotectmethod: '',
                    fremarks: '',
                    fupdatorid:''
                },
                editPestRules: {
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
                //虫害列表
                pestList: [],
                pestForm: {
                    keywords: '',
                    sortType: 2,
                },
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            }
        },
        created () {
            // 取相关物种结果
            this.loadSpeciResult('', '', [], this.pageCur, [])
            this.$api.post('/wiki/api/wiki/listSpeciesPest', {
                keywords: '',
                pageNum: 1,
                pageSize: 12,
                sortType: 2,
                userId: this.loginuserinfo.loginAccount,
                auditstatus: 6
            }).then(response => {
                if (response.code === 200) {
                    this.pestList = response.data
                    this.pestTotal = response.total
                } else {
                    this.$Message.error('查询虫害列表出错！')
                }
            }).catch(error => {
                this.$Message.error('查询虫害列表出错！')
            })
        },
        methods: {
            show (e) {
                this.editPestModal = true
                this.isShow = true
                this.$nextTick(() => {
                    this.getData(e)
                })
            },
            // 修改获取图片
            getFimagesrc (e) {
                var arr = []
                e.forEach(element => {
                    if (element.response) {
                        arr.push(element.response.data.picName)
                    }
                })
                this.editPestForm.fimagesrc = arr
            },
            // 高级搜索弹窗
            handleFilterModal (name) {
                if (!this.isShow) {
                    this.$refs[name].highFilterShow = true
                }
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
                this.editPestForm.speciesid = arrId.join(' ')
                this.editPestForm.specName = arr.join(' ')
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
                            alert("该物种名已被占用!")
                            this.formItem.fname = ''
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            // 点击新增虫害
            addPest () {
                this.$router.push('/pro/addPest')
            },
            pestUp () {
                this.pestForm.sortType = 1
                this.pestSearch()
            },
            pestDown () {
                this.pestForm.sortType =2
                this.pestSearch()
            },
            // 虫害查找
            pestSearch () {
                // 获得当前用户信息
                this.pestList = []
                this.pestTotal = 0
                this.$api.post('/wiki/api/wiki/listSpeciesPest',
                    {
                        keywords: this.pestForm.keywords,
                        sortType: this.pestForm.sortType,
                        userId: this.loginuserinfo.loginAccount,
                        pageNum: this.pestCurrentPage,
                        pageSize: 12,
                        auditstatus: 6
                    }
                ).then(response => {
                    this.pestList = response.data
                    this.pestTotal = response.total
                }).catch(error => {
                    console.log(error)
                })

            },
            //虫害页码改变
            pestPageChange (e) {
                // 获得当前页码
                this.pestCurrentPage = e
                this.pestList = []
                this.pestTotal = 0
                this.pestSearch()
            },
            // 点虫病害删除
            delPest (id) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '<p>您确定删除此虫害？</p>',
                    cancelText: '取消',
                    onOk: () => {
                        this.$api.get(`/wiki/api/wiki/deleteSpeciesPest/${id}/${this.loginuserinfo.loginAccount}`).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('删除虫害成功!')
                                this.pestSearch()
                            } else {
                                this.$Message.error('删除虫害失败!')
                            }
                        }).catch(error => {
                            this.$Message.error('删除虫害失败!')
                        })
                    }
                })
            },
            //  编辑虫害回显
            editPest (e) {
                this.editPestModal = true
                this.isShow = false
                this.getData(e)
            },
            getData (e) {
                console.log(e)
                this.$api.get('/wiki/api/wiki/getSpeciesPest/' + e).then(response => {
                    console.log('response.data===>>>', response.data)
                    this.editPestForm.fid = response.data.fid
                    this.editPestForm.specName = response.data.specName
                    this.editPestForm.indexid = response.data.indexid
                    this.editPestForm.speciesid = response.data.speciesid
                    this.editPestForm.fname = response.data.fname
                    this.editPestForm.fpinyin = response.data.fpinyin
                    this.editPestForm.fmainfeatures = response.data.fmainfeatures
                    this.editPestForm.fhabit = response.data.fhabit
                    this.editPestForm.fpetsregular = response.data.fpetsregular
                    this.editPestForm.fprotectmethod = response.data.fprotectmethod
                    this.editPestForm.fremarks = response.data.fremarks
                    this.editPestForm.fimagesrc = response.data.fimagesrc
                    if (!this.isShow) {
                        this.$refs['fimagesrc'].handleGive(this.editPestForm.fimagesrc)
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            updatePest () {
                if (!this.handleSubmit('editPestForm')) {
                    return
                }
                this.$api.post('/wiki/api/wiki/updateSpeciesPest', {
                    fid: this.editPestForm.fid,
                    indexid : this.editPestForm.indexid,
                    speciesid: this.editPestForm.speciesid,
                    fname: this.editPestForm.fname,
                    fpinyin: this.editPestForm.fpinyin,
                    fimagesrc: this.editPestForm.fimagesrc,
                    fmainfeatures: this.editPestForm.fmainfeatures,
                    fhabit: this.editPestForm.fhabit,
                    fpetsregular: this.editPestForm.fpetsregular,
                    fprotectmethod: this.editPestForm.fprotectmethod,
                    fremarks: this.editPestForm.fremarks,
                    fupdatorid: this.loginuserinfo.loginAccount,
                    fcreatorid: this.loginuserinfo.loginAccount
                }).then(response => {
                    if (200 === response.code) {
                        this.$Message.success('更新虫害成功!')
                        this.pestSearch()
                        this.editCancel()
                    } else {
                        this.$Message.success('更新虫害失败!')
                    }
                }).catch(error => {
                    this.$Message.success('更新虫害失败!')
                })
            },
            // 得到汉字的拼音
            getPinyin () {
                if (this.editPestForm.fname !== '') {
                    this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.editPestForm.fname).then(response => {
                        this.editPestForm.fpinyin = ''
                        this.editPestForm.fpinyin = response.data
                    }).catch(error => {
                        console.log(error)
                    })
                } else {
                    this.editPestForm.fpinyin = ''
                }
            },
            editCancel () {
                this.editPestModal = false
            }
        }
    }
</script>