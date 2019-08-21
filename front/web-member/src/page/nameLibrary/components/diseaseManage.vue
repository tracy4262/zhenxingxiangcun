<template>
	<div>
		<div style="width: 100% ;height: 100%">
	        <Row>
	            <Col span="9">
                    结果排序：
                    <ButtonGroup>
                        <Button type="default" @click="diseaseUp">时间升序</Button>
                        <Button type="default" @click="diseaseDown">时间降序</Button>
                    </ButtonGroup>
	            </Col>
	            <Col span="9">
                    <Form :model="diseaseForm" :label-width="100">
                        <FormItem label="关键字：">
                            <Row>
                                <Col span="22">
                                    <Input v-model="diseaseForm.keywords" placeholder="请输入内容" />
                                </Col>
                            </Row>
                        </FormItem>
                    </Form>
	            </Col>
	            <Col span="2">
                    <ButtonGroup>
                        <Button type="default" @click="diseaseSearch">查找</Button>
                    </ButtonGroup>
	            </Col>
	             <Col span="4">
                    <ButtonGroup>
                        <Button type="default" @click="addSpec3">新增病害</Button>
                    </ButtonGroup>
	            </Col>
	        </Row>

	        <Row style="margin: 10px;">
	            <ul>
	                <li v-for="(disease,index) in diseaseList" :key="index">
	                    <div class="listDemo">
	                        <template v-if="disease.fimagesrc.length>0 ">
                                <img :src="disease.fimagesrc[0]" width="200px" height="200px"/>
	                        </template>
	                        <template v-else>
                                <img src="../../../img/noPic.png" width="200px" height="200px"/>
                            </template>
	                        <div class="species-tool pd10 clear">
	                            <p class="p1 clear">
	                                {{disease.fname}}
	                                <span v-if="disease.auditstatus === 1" class="fr t-green">
                                        审核通过
                                    </span>
                                    <span v-else-if="disease.auditstatus === 4" class="fr t-red">
                                        未审核通过
                                    </span>
                                    <span v-else-if="disease.auditstatus === 0" class="fr t-gray">
                                        更新待审核
                                    </span>
                                    <span v-else-if="disease.auditstatus === 2" class="fr t-gray">
                                        新增待审核
                                    </span>
                                    <span v-else-if="disease.auditstatus === 3" class="fr t-gray">
                                        删除待审核
                                    </span>
	                            </p>
	                            <p class="p2 mt10 mb10">{{disease.etiology}}</p>
	                            <p class="p3" v-if="disease.auditstatus === 1">
                                    <a href="javascript:void(0)" @click="editDisease(disease.indexid)"><span>编辑</span></a>
                                    <a href="javascript:void(0)"  @click="delDisease(disease.indexid)"><span>删除</span></a>
                                    <!-- <a href="#"><span>图库</span></a> -->
                                </p>
                                <p class="p3" v-else>
                                    <a href="javascript:void(0)" @click="show(disease.indexid)"><span>查看</span></a>
                                </p>
	                        </div>
	                    </div>
	                </li>
	            </ul>
	            <div style="float:right;margin-top: 250px">
	                <Page :total="diseaseTotal" :current="diseaseCurrentPage" :page-size="diseasePageSize" @on-change="diseasePageChange" show-total></Page>
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

        <!-- 品种弹框-->
        <Modal v-model="editDiseaseModal" :title="isShow ? '病害查看' : '病害编辑'" width="800" :mask-closable="false" :transfer="false">
            <div align="left" style="margin-top: 5%;margin-left: 10%;margin-right: 10%">
                <Form :model="editDiseaseForm" :label-width="120" label-position="right" ref="editDiseaseForm" :rules="editDiseaseRules">
                    <FormItem label="病害名称" prop="fname">
                        <Input v-model="editDiseaseForm.fname" placeholder="请输入内容" @on-change="getPinyin" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="汉语拼音">
                        <Input v-model="editDiseaseForm.fpinyin" readonly placeholder="由品种名称自动生成拼音" />
                    </FormItem>
                    <FormItem label="危害物种" prop="speciesid">
                        <Input v-model="editDiseaseForm.specName" placeholder="请输入内容" readonly @on-focus="handleFilterModal('speciFilter')" />
                    </FormItem>
                    <FormItem label="上传图标" prop="fimagesrc">
                        <vui-upload
                            v-if="!isShow"
                            ref="fimagesrc"
                            @on-getPictureList="getFimagesrc"
                            :hint="'图片大小小于2MB，最多上传 1 张'"
                            :total="1"
                            :size="[100,100]"
                        ></vui-upload>
                        <span v-else>
                            <img v-for="(item,index) in editDiseaseForm.fimagesrc" :key="index" :src="item" style="width: 100px; height: 100px;">
                        </span>
                    </FormItem>
                    <FormItem label="病原学" v-if="animal">
                        <Input v-model="editDiseaseForm.etiology" type="textarea" :autosize="{minRows: 2,maxRows: 5}" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="流行特点" v-if="animal">
                        <Input v-model="editDiseaseForm.epidemiologicalfeatures" type="textarea" :autosize="{minRows: 2,maxRows: 5}" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="病理剖检" v-if="animal">
                        <Input v-model="editDiseaseForm.fpathologycheck" type="textarea" :autosize="{minRows: 2,maxRows: 5}" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="诊断" v-if="animal">
                        <Input v-model="editDiseaseForm.fdiagnose" type="textarea" :autosize="{minRows: 2,maxRows: 5}" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="防治" v-if="animal">
                        <Input v-model="editDiseaseForm.fprevention" type="textarea" :autosize="{minRows: 2,maxRows: 5}" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="危害症状" v-if="plant">
                        <Input v-model="editDiseaseForm.ffeature" type="textarea" :autosize="{minRows: 2,maxRows: 5}" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="发生规律" v-if="plant">
                        <Input v-model="editDiseaseForm.fdiseaseregular" type="textarea" :autosize="{minRows: 2,maxRows: 5}" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="防治办法" v-if="plant">
                        <Input v-model="editDiseaseForm.fprotectmethod" type="textarea" :autosize="{minRows: 2,maxRows: 5}" :readonly="isShow" />
                    </FormItem>
                </Form>
            </div>
            <div slot="footer">
                <Button type="text" @click="editCancel">取消</Button>
                <Button type="primary" @click="updateDisease" v-if="!isShow">确定</Button>
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
        data () {
            return {
                animal: false,
                plant: false,
                isShow: false,
                total:0,
                pageCur:1,
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
                //病害分页
                diseaseTotal: 0,
                diseaseCurrentPage: 1,
                diseasePageSize: 12,
                editDiseaseModal: false,
                // 编辑物种表单
                editDiseaseForm: {
                    specName:'',
                    speciesid: '',
                    fname: '',
                    disease_name: '',
                    fpinyin: '',
                    fimagesrc:[],
                    etiology: '',
                    epidemiologicalfeatures: '',
                    fpathologycheck: '',
                    fdiagnose: '',
                    fprevention: '',
                    // 植物
                    ffeature: '',
                    fdiseaseregular: '',
                    fprotectmethod: ''
                },
                editDiseaseRules: {
                    speciesid: [
                        { required: true, message: '请选择危害物种', trigger: 'change' }
                    ],
                    fname: [
                        { required: true, message: '请填写病害名称', trigger: 'blur' }
                    ],
                    fimagesrc: [
                        { required: true, type: 'array', min: '1', message: '请上传图标', trigger: 'change' }
                    ]
                },
                //病害列表
                diseaseList: [],
                diseaseForm: {
                    keywords: '',
                    sortType: 2,
                },
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            }
        },
        created () {
            // 取相关物种结果
            this.loadSpeciResult('', '', [], this.pageCur, [])
            // 病害列表查询
            this.$api.post('/wiki/api/wiki/listSpeciesDisease', {
                keywords: '',
                pageNum: 1,
                pageSize: 12,
                sortType: 2,
                userId: this.loginuserinfo.loginAccount,
                auditstatus: 6
            }).then(response => {
                if (response.code === 200) {
                    this.diseaseList = response.data
                    this.diseaseTotal = response.total
                } else {
                    this.$Message.error('查询病害列表出错！')
                }
            }).catch(error => {
                this.$Message.error('查询病害列表出错！')
            })
        },

        methods: {
            show (e) {
                this.editDiseaseModal = true
                this.isShow = true
                this.$nextTick(() => {
                    this.getData(e)
                })
            },
            // 修改获取图片
            getFimagesrc (e) {
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.editDiseaseForm.fimagesrc = arr
            },
            // 高级搜索弹窗
            handleFilterModal(name) {
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
					var type =''
                    classify.forEach(item => {
                        arr.push(item.classId)
						if (item.valueOf !== undefined) type = item.value
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
            handleGetSpeciResult(classify, result) {
                var arr = []
                result.forEach(item => arr.push(item.label))
                if (arr.length > 0) {
                    this.formItem.specName = arr[0]
                    this.$api.post('/wiki/api/species/searchSpecies', {
                        name: this.formItem.specName
                    }).then(response => {
                        console.log('response--->>', response)
                        if (200 === response.code) {
                            if ('' != response.data) {
                                this.formItem.speciesid = response.data.species.speciesid
                                console.log('this.formItem.speciesid--->>', this.formItem.speciesid)
                            }
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            //---检测病害名是否占用-------
            checkAddFname() {
                if ('' != this.formItem.fname) {
                    this.$api.get('/wiki/api/wiki/existName/' + 3 + '/' + this.formItem.fname).then(response => {
                        console.log("检测物种名是否被占用:==>>", response.data, "<<==")
                        if (1 === response.data) {
                            this.$Message.error("该物种名已被占用!")
                            this.formItem.fname = ''
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            handleSubmit(name) {
                let flag = false;
                this.$refs[name].validate(valid => {
                    if (valid) {
                    flag = true;
                    // this.$Message.success('提交成功!');
                    } else {
                        this.$Message.error('表单验证失败!');
                    }
                });
                return flag;
            },
            // 点击添加新病害
            addSpec3() {
                this.$router.push('/pro/addDisease')
            },
            // 点击升序
            diseaseUp() {
                this.diseaseForm.sortType = 1
                this.diseaseSearch()
            },
            diseaseDown() {
                this.diseaseForm.sortType = 2
                this.diseaseSearch()
            },
            // 病害查找
            diseaseSearch() {
                this.diseaseList = []
                this.diseaseTotal = 0
                this.$api.post('/wiki/api/wiki/listSpeciesDisease',
                    {
                        keywords: this.diseaseForm.keywords,
                        sortType: this.diseaseForm.sortType,
                        userId: this.loginuserinfo.loginAccount,
                        pageNum: this.diseaseCurrentPage,
                        pageSize: 12,
                        auditstatus: 6
                    }
                ).then(response => {
                    console.log("查询的结果:", response)
                    this.diseaseList = response.data
                    this.diseaseTotal = response.total
                }).catch(error => {
                    console.log(error)
                })

            },
            //病害页码改变
            diseasePageChange(e) {
                // 获得当前页码
                this.diseaseCurrentPage = e
                
                this.diseaseList = []
                this.diseaseTotal = 0
                this.diseaseSearch()
            },
            // 点击病害删除
            delDisease(id) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '<p>您确定删除此病害？</p>',
                    cancelText: '取消',
                    onOk: () => {
                        this.$api.get(`/wiki/api/wiki/deleteSpeciesDisease/${id}/${this.loginuserinfo.loginAccount}`).then(response => {
                            if (response.code === 200) {
                                this.$Message.success("删除病害成功!")
                                this.diseaseSearch()
                            } else {
                                this.$Message.error('删除病害失败！')
                            }
                        }).catch(error => {
                            this.$Message.error('删除病害失败！')
                        })
                    }
                })
            },
            //  编辑病害回显
            editDisease(e) {
                this.editDiseaseModal = true
                this.isShow = false
                this.getData(e)
            },
            getData (e) {
                this.$api.get('/wiki/api/wiki/getSpeciesDisease/' + e).then(response => {
                    console.log(response)
                    if (response.code === 200 && response.data !== undefined) {
                        this.editDiseaseForm.fid = response.data.fid
                        this.editDiseaseForm.speciesid = response.data.speciesid
                        this.editDiseaseForm.specName = response.data.specName
                        this.editDiseaseForm.indexid = response.data.indexid
                        this.editDiseaseForm.fname = response.data.fname
                        this.editDiseaseForm.fpinyin = response.data.fpinyin
                        // 动物
                        this.editDiseaseForm.etiology = response.data.fcausediseasesubject
                        this.editDiseaseForm.epidemiologicalfeatures = response.data.fcommonfeature
                        this.editDiseaseForm.fpathologycheck = response.data.fpathologycheck
                        this.editDiseaseForm.fdiagnose = response.data.fdiagnose
                        this.editDiseaseForm.fprevention = response.data.fprevention
                        this.editDiseaseForm.fimagesrc = response.data.fimagesrc
                        // 植物
                        this.editDiseaseForm.ffeature = response.data.ffeature
                        this.editDiseaseForm.fdiseaseregular = response.data.fdiseaseregular
                        this.editDiseaseForm.fprotectmethod = response.data.fprotectmethod
                        if (!this.isShow) {
                            this.$refs['fimagesrc'].handleGive(this.editDiseaseForm.fimagesrc)
                        }
                        // 判断选择的该物种是动物还是植物
                        this.$api.post('/wiki/api/species/getSpeciesClassify', {
                            speciesid: this.editDiseaseForm.speciesid
                        }).then(response => {
                            console.log('res', response)
                            if (response.code === 200) {
                                if (response.data.type === '动物') {
                                    // 显示动物病害独有的字段
                                    this.animal = true
                                    this.plant = false
                                } else if (response.data.type === '植物') {
                                    // 显示植物独有的字段
                                    this.plant = true
                                    this.animal = false
                                }
                            }
                        }).catch(error => {
                            this.$Message.error('获取物种类型出错！')
                        })
                    } 
                }).catch(error => {
                    this.$Message.error('获取病害数据出错！')
                })
            },
            //  编辑保存
            updateDisease(e) {
                if (!this.handleSubmit('editDiseaseForm')) {
                    return
                }
                this.$api.post('/wiki/api/wiki/updateSpeciesDisease', {
                    fid: this.editDiseaseForm.fid,
                    indexid: this.editDiseaseForm.indexid,
                    speciesid: this.editDiseaseForm.speciesid,
                    fname: this.editDiseaseForm.fname,
                    fpinyin: this.editDiseaseForm.fpinyin,
                    fimagesrc: this.editDiseaseForm.fimagesrc,
                    // 动物
                    fcausediseasesubject: this.editDiseaseForm.etiology,
                    fcommonfeature: this.editDiseaseForm.epidemiologicalfeatures,
                    fpathologycheck: this.editDiseaseForm.fpathologycheck,
                    fdiagnose: this.editDiseaseForm.fdiagnose,
                    fprevention: this.editDiseaseForm.fprevention,
                    // 植物
                    ffeature: this.editDiseaseForm.ffeature,
                    fdiseaseregular: this.editDiseaseForm.fdiseaseregular,
                    fprotectmethod: this.editDiseaseForm.fprotectmethod,
                    // 账户
                    fupdatorid: this.loginuserinfo.loginAccount,
                    fcreatorid: this.loginuserinfo.loginAccount
                }).then(response => {
                    if (200 === response.code) {
                        this.diseaseSearch()
                        this.editCancel()
                        this.$Message.success('更新病害成功!')
                    } else {
                        this.$Message.success('更新病害失败!')
                    }
                }).catch(error => {
                    this.$Message.success('更新病害失败!')
                })
            },
            // 得到汉字的拼音
            getPinyin () {
                if ('' != this.editDiseaseForm.fname) {
                    this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.editDiseaseForm.fname).then(response => {
                        console.log("response.data:", response.data)
                        this.editDiseaseForm.fpinyin = ''
                        this.editDiseaseForm.fpinyin = response.data
                    }).catch(error => {
                        console.log(error)
                    })
                } else {
                    this.editDiseaseForm.fpinyin = ''
                }

            },
            editCancel () {
                this.editDiseaseModal = false
            }
        }
    }
</script>