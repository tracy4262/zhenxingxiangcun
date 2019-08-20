<template>
    <div>
        <div style="width: 100% ;height: 100%">
            <Row>
                <Col span="10">
                    <div class="mb25">
                        查看分类：
                        <ButtonGroup>
                            <Button type="default" @click="myEditSpec">我编辑的物种</Button>
                            <Button type="default" @click="myFollowSpec">我关注的物种</Button>
                        </ButtonGroup>
                    </div>
                    <div>
                        结果排序：
                        <ButtonGroup>
                            <Button type="default" @click="upTime">时间升序</Button>
                            <Button type="default" @click="downTime">时间降序</Button>
                        </ButtonGroup>
                    </div>
                </Col>
                <Col span="10">
                    <Form :model="specForm" :label-width="100">
                        <FormItem label="关键字：">
                            <Row>
                                <Col span="20">
                                <Input v-model="specForm.keywords" placeholder="请输入内容"/>
                                </Col>
                            </Row>
                        </FormItem>
                        <FormItem label="物种分类：">
                            <Row>
                                <Col span="20">
                                    <Cascader :data="specData" v-model="specForm.selectedSpe"></Cascader>
                                </Col>
                            </Row>
                        </FormItem>
                    </Form>
                </Col>
                <Col span="4">
                    <ButtonGroup>
                        <Button type="default" @click="specSearch()">查找</Button>
                    </ButtonGroup>
                    <br/>
                    <br/>
                    <ButtonGroup>
                        <Button type="default" @click="addSpec()">新增物种</Button>
                    </ButtonGroup>
                </Col>
            </Row>
            <Row style="margin: 10px;">
                <ul>
                    <li v-for="(spec,index) in specList" :key="index">
                        <div class="listDemo">
                            <template v-if="spec.ficon.length>0">
                                <img :src="spec.ficon[0]" idth="200px" height="200px"/>
                            </template>
                            <template v-else>
                                <img src="../../../../static/img/goods-list-no-picture1.png" width="200px" height="200px"/>
                            </template>
                            <div class="species-tool pd10 clear">
                                <p class="p1 clear">
                                    {{spec.fname}}
                                    <span v-if="spec.auditstatus === 1" class="fr t-green">
                                        审核通过
                                    </span>
                                    <span v-else-if="spec.auditstatus === 4" class="fr t-red">
                                        未审核通过
                                    </span>
                                    <span v-else-if="spec.auditstatus === 0" class="fr t-gray">
                                        更新待审核
                                    </span>
                                    <span v-else-if="spec.auditstatus === 2" class="fr t-gray">
                                        新增待审核
                                    </span>
                                    <span v-else-if="spec.auditstatus === 3" class="fr t-gray">
                                        删除待审核
                                    </span>
                                </p>
                                <p class="p2 mt10 mb10">{{spec.fshapefeatureid}}</p>
                                <p class="p3" v-if="spec.auditstatus === 1">
                                    <a href="javascript:void(0)" @click="editSpec(spec.indexid)"><span>编辑</span></a>
                                    <a href="javascript:void(0)" @click="delSpec(spec.indexid)"><span>删除</span></a>
                                    <!-- <a href="#"><span>图库</span></a> -->
                                </p>
                                <p class="p3" v-else>
                                    <a href="javascript:void(0)" @click="show(spec.indexid)"><span>查看</span></a>
                                </p>
                            </div>
                        </div>
                    </li>
                </ul>
                <div style="float:right;margin-top: 250px">
                    <Page :total="specTotal" :current="specCurrentPage"
                        :page-size="specPageSize" @on-change="specPageChange"
                        show-total></Page>
                </div>
            </Row>
        </div>

        <!-- 物种编辑弹框-->
        <Modal v-model="editSpecModal" :title="isShow ? '物种查看' : '物种编辑'" width="800" :mask-closable="false">
            <div align="left" style="margin-top: 5%;margin-left: 10%;margin-right: 10%">
                <Form :model="editSpecForm" :label-width="120" label-position="right" ref="editSpecForm" :rules="ruleEditSpecForm">
                    <FormItem label="物种分类" prop="fclassifiedid">
                        <Cascader :data="specData" v-model="editSpecForm.fclassifiedid" :disabled="isShow"></Cascader>
                    </FormItem>
                    <FormItem label="物种名称" prop="fname">
                        <Input v-model="editSpecForm.fname" placeholder="请输入内容" @on-change="getPinyin" :readonly="isShow" :maxlength="50" />
                    </FormItem>
                    <FormItem label="汉语拼音">
                        <Input v-model="editSpecForm.fpinyin" placeholder="由物种名称自动生成拼音" readonly />
                    </FormItem>
                    <FormItem label="物种俗名">
                        <Input v-model="editSpecForm.speciesVulgo" :maxlength="20" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="其他分类">
                        <Cascader :data="specData" v-model="editSpecForm.fouthclassifiedid" :disabled="isShow"></Cascader>
                    </FormItem>
                    <FormItem label="产业分类" prop="findustriaclassifiedid">
                        <Select v-model="editSpecForm.findustriaclassifiedid" placeholder="请选择" clearable :disabled="isShow">
                            <Option value="A01">农业</Option>
                            <Option value="A02">林业</Option>
                            <Option value="A03">畜牧业</Option>
                            <Option value="A04">水产业</Option>
                        </Select>
                    </FormItem>
                    <FormItem label="是否保护">
                        <Select v-model="editSpecForm.fisprotection" clearable :disabled="isShow">
                            <Option value="0">否</Option>
                            <Option value="1">一级保护</Option>
                            <Option value="2">二级保护</Option>
                            <Option value="3">地方重点保护</Option>
                        </Select>
                    </FormItem>
                    <FormItem label="物种图片">
                        <vui-upload
                            v-if="!isShow"
                            ref="upload"
                            @on-getPictureList="getPictureList"
                            :hint="'图片大小小于2MB，最多上传 4 张'"
                            :total="4"
                            :size="[100,100]"
                        ></vui-upload>
                        <span v-else>
                            <img v-for="(item,index) in editSpecForm.fimage" :key="index" :src="item" style="width: 100px; height: 100px;">
                        </span>
                    </FormItem>
                    <FormItem label="主要产品">
                        <Input v-model="editSpecForm.majorProduct" :maxlength="100" :readonly="isShow" />
                    </FormItem>
                    <FormItem label="性状特征">
                        <Input v-model="editSpecForm.fshapefeatureid" type="textarea" :readonly="isShow"
                            :autosize="{minRows: 2,maxRows: 5}" :maxlength="500" />
                    </FormItem>
                    <FormItem label="备注">
                        <Input v-model="editSpecForm.fremarks" type="textarea" :readonly="isShow"
                            :autosize="{minRows: 2,maxRows: 5}" :maxlength="500" />
                    </FormItem>
                </Form>
            </div>
            <div slot="footer">
                <Button type="text" @click="editCancel">取消</Button>
                <Button type="primary" @click="updateSpec"  v-if="!isShow">确定</Button>
            </div>
        </Modal>
    </div>  
</template>
<script>
import vuiUpload from '~components/vui-upload'
export default {
    components: {
        vuiUpload
    },
    data () {
        return {
            isShow: false,
            selectedSpe: [],
            //物种分页
            specTotal: 0,
            specCurrentPage: 1,
            specPageSize: 12,
            //编辑弹窗
            editSpecModal: false,
            // 编辑物种表单
            editSpecForm: {
                indexid: '',
                fclassifiedid: [],
                fname: '',
                fpinyin: '',
                speciesVulgo: '',
                fouthclassifiedid: [],
                findustriaclassifiedid: '',
                fimage: [],
                majorProduct: '',
                fshapefeatureid: '',
                fremarks: '',
                fisprotection: '',
                fupdatorid: '',
                speciesid: ''
            },
            ruleEditSpecForm: {
                fname: [
                    {required: true, message: '请填写物种名称', trigger: 'blur'}
                ],
                fclassifiedid: [
                    {required: true, type: 'array', min: '2', message: '请选择物种类型', trigger: 'change'}
                ],
                findustriaclassifiedid: [
                    {required: true, message: '请选择产业类型', trigger: 'blur'}
                ]
            },
            specData: [
                {
                    value: '0',
                    label: '动物',
                    children: [
                        {
                            value: 'WZ0201',
                            label: '肉用类'
                        },
                        {
                            value: 'WZ0202',
                            label: '肉蛋兼用类'
                        },
                        {
                            value: 'WZ0203',
                            label: '肉奶兼用类'
                        },
                        {
                            value: 'WZ0204',
                            label: '肉毛(绒)兼用类'
                        },
                        {
                            value: 'WZ0206',
                            label: '肉皮兼用类'
                        },
                        {
                            value: 'WZ0207',
                            label: '肉药兼用类'
                        },
                        {
                            value: 'WZ0208',
                            label: '役力类'
                        },
                        {
                            value: 'WZ0209',
                            label: '丝蜜类'
                        },
                        {
                            value: 'WZ0210',
                            label: '宠娱观赏类'
                        }
                        ,
                        {
                            value: 'WZ0211',
                            label: '实验类'
                        },
                        {
                            value: 'WZ0213',
                            label: '饲饵类'
                        }
                    ]
                },
                {
                    value: '1',
                    label: '植物',
                    children: [
                        {
                            value: 'WZ0101',
                            label: '粮食类'
                        },
                        {
                            value: 'WZ0102',
                            label: '纤维类'
                        },
                        {
                            value: 'WZ0103',
                            label: '油料类'
                        },
                        {
                            value: 'WZ0104',
                            label: '糖料类'
                        },
                        {
                            value: 'WZ0111',
                            label: '嗜好类'
                        },
                        {
                            value: 'WZ0114',
                            label: '瓜果蔬类'
                        },
                        {
                            value: 'WZ0116',
                            label: '食用菌藻类'
                        },
                        {
                            value: 'WZ0105',
                            label: '饮料类'
                        },
                        {
                            value: 'WZ0106',
                            label: '调料类'
                        }
                        ,
                        {
                            value: 'WZ0113',
                            label: '香料类'
                        },
                        {
                            value: 'WZ0112',
                            label: '药用类'
                        },
                        {
                            value: 'WZ0117',
                            label: '花卉类'
                        },
                        {
                            value: 'WZ0121',
                            label: '绿化观赏林木'
                        },
                        {
                            value: 'WZ0107',
                            label: '染料类'
                        },
                        {
                            value: 'WZ0108',
                            label: '漆料类'
                        },
                        {
                            value: 'WZ0109',
                            label: '胶液料类'
                        },
                        {
                            value: 'WZ0110',
                            label: '鞣料类'
                        },
                        {
                            value: 'WZ0118',
                            label: '饲料类'
                        },
                        {
                            value: 'WZ0119',
                            label: '肥料类'
                        },
                        {
                            value: 'WZ0120',
                            label: '防护用材林木'
                        },
                        {
                            value: 'WZ0115',
                            label: '能源类'
                        }
                    ]
                }
            ],
            //物种列表
            specList: [],
            // 物种查询表单
            specForm: {
                keywords: '',
                mySpec: 1,
                selectTime: 2,
                selectedSpe: []
            },
            loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
        }
        
    },
    created () {
        this.$api.post('/wiki/api/species/listSpecies', {
            userId: this.loginuserinfo.loginAccount,
            pageNum: 1,
            pageSize: 12,
            auditstatus: '6',
            seeType: 1,
            sortType: 2
        }).then(response => {
            this.specList = response.data
            this.specTotal = response.total
            console.log('this.specList', this.specList)
        }).catch(error => {
            this.$Message.error(error)
        })
    },
    methods: {
        show (e) {
            this.editSpecModal = true
            this.isShow = true
            this.$nextTick(() => {
                this.getData(e)
            })
        },
        // 获取图片
        getPictureList(e){
            var arr = []
            e.forEach(element => {
                    if(element.response){
                    arr.push(element.response.data.picName)
                }
            })
            this.editSpecForm.fimage = arr
        },
        // 点击新增物种跳转页面
        addSpec() {
            this.$router.push('/pro/addSpecies')
        },
        //点击选择我编辑的物种
        myEditSpec() {
            this.specForm.mySpec = 1
            this.specSearch()
        },
        // 点击选择我关注的物种
        myFollowSpec() {
            this.specForm.mySpec = 0
            this.specSearch()
        },
        // 点击升序
        upTime() {
            this.specForm.selectTime = 1
            this.specSearch()
        },
        // 点击降序
        downTime() {
            this.specForm.selectTime = 2
            this.specSearch()
        },
        //物种搜索按钮
        specSearch () {
            // 获得当前用户信息
            var loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            var _this = this
            this.specList = []
            this.specTotal = 0
            let data = {
                fclassifiedid: this.specForm.selectedSpe[1],
                seeType: this.specForm.mySpec,
                sortType: this.specForm.selectTime,
                userId: loginuserinfo.loginAccount,
                keywords: this.specForm.keywords,
                pageNum: this.specCurrentPage,
                pageSize: this.specPageSize,
                auditstatus: '6'
            }
            console.log('data', data)
            this.$api.post('/wiki/api/species/listSpecies', data).then(response => {
                this.specList = response.data
                this.specTotal = response.total
            }).catch(error => {
                this.$Message.error(error)
            })
        },
        // 物种页码改变
        specPageChange (e) {
            // 获得当前页码
            this.specCurrentPage = e
            this.specList = []
            this.specTotal = 0
            this.specSearch()
        },
        // 点击物种删除
        delSpec (id) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '<p>您确定删除此物种？</p>',
                cancelText: '取消',
                onOk: () => {
                    this.delSpecs(id)
                }
            })
        },
        delSpecs (id) {
            this.$api.get(`/wiki/api/species/deleteSpecies/${id}/${this.loginuserinfo.loginAccount}`).then(response => {
                this.$Message.success('删除品种成功!')
                this.specSearch()
            }).catch(error => {
                this.$Message.error('删除品种失败!')
            })
        },
        // 物种编辑弹窗
        editSpec (e) {
            console.log(e)
            this.editSpecModal = true
            this.isShow = false
            this.editSpecForm.indexid = e
            this.getData(e)
        },
        getData (e) {
            this.$api.get('/wiki/api/species/getSpecies/' + e).then(response => {
                console.log('edit res', response)
                var fClass = response.data.fclassifiedid
                if ("" != fClass) {
                    if (fClass == 'WZ0201' || fClass == 'WZ0202' || fClass == 'WZ0203' || fClass == 'WZ0204' ||
                        fClass == 'WZ0205' || fClass == 'WZ0206' || fClass == 'WZ0207' || fClass == 'WZ0208' || fClass == 'WZ0209' ||
                        fClass == 'WZ0210' || fClass == 'WZ0211') {
                        this.editSpecForm.fclassifiedid = ["0", fClass]
                    } else {
                        this.editSpecForm.fclassifiedid = ["1", fClass]
                    }
                }
                this.editSpecForm.fname = response.data.fname
                this.editSpecForm.fpinyin = response.data.fpinyin
                this.editSpecForm.speciesVulgo = response.data.speciesVulgo
                var fOutClass = response.data.otherClassify
                if ("" != fOutClass) {
                    if (fOutClass == 'WZ0201' || fOutClass == 'WZ0202' || fOutClass == 'WZ0203' || fOutClass == 'WZ0204' ||
                        fOutClass == 'WZ0205' || fOutClass == 'WZ0206' || fOutClass == 'WZ0207' || fOutClass == 'WZ0208' || fOutClass == 'WZ0209' ||
                        fOutClass == 'WZ0210' || fOutClass == 'WZ0211') {
                        this.editSpecForm.fouthclassifiedid = ["0", fOutClass]
                    } else {
                        this.editSpecForm.fouthclassifiedid = ["1", fOutClass]
                    }
                }
                this.editSpecForm.findustriaclassifiedid = response.data.findustriaclassifiedid
                this.editSpecForm.majorProduct = response.data.majorProduct
                this.editSpecForm.fimage = response.data.ficon
                this.editSpecForm.fshapefeatureid = response.data.fshapefeatureid
                this.editSpecForm.fremarks = response.data.fremarks
                this.editSpecForm.fisprotection = response.data.fisprotection
                this.editSpecForm.speciesid = response.data.speciesid
                if (!this.isShow) {
                    this.$refs['upload'].handleGive(this.editSpecForm.fimage)
                }
            }).catch(error=> {
                this.$Message.error(error)
            })
        },
        // 更新物种点击确认
        updateSpec () {
            console.log('111')
            this.$refs['editSpecForm'].validate((valid) => {
                console.log('valid', valid)
                if (valid) {
                    // 提交物种更新的数据
                    //1.提交第一步数据
                    let data = {
                        fclassifiedid: this.editSpecForm.fclassifiedid[1],
                        fname: this.editSpecForm.fname,
                        fpinyin: this.editSpecForm.fpinyin,
                        speciesVulgo: this.editSpecForm.speciesVulgo,
                        otherClassify: this.editSpecForm.fouthclassifiedid[1],
                        findustriaclassifiedid: this.editSpecForm.findustriaclassifiedid,
                        ficon: this.editSpecForm.fimage,
                        majorProduct: this.editSpecForm.majorProduct,
                        fshapefeatureid: this.editSpecForm.fshapefeatureid,
                        fremarks: this.editSpecForm.fremarks,
                        fisprotection: this.editSpecForm.fisprotection,
                        fupdatorid: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
                        fcreatorid: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
                        speciesid: this.editSpecForm.speciesid
                    }
                    console.log('data', data)
                    this.$api.post('/wiki/api/species/updateSpecies', data).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('更新物种成功')
                            this.editCancel()
                            // 图片清空
                            this.specSearch()
                            // location.reload()
                        } else {
                            this.$Message.success('更新物种失败!')
                        }
                    }).catch(error => {
                        this.$Message.error(error)
                    })
                }
            })
        },
        // 得到汉字的拼音
        getPinyin() {
            if ('' != this.editSpecForm.fname) {
                this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.editSpecForm.fname).then(response => {
                    this.editSpecForm.fpinyin = ''
                    this.editSpecForm.fpinyin = response.data
                }).catch(function (error) {
                    this.$Message.error(error)
                })
            } else {
                this.editSpecForm.fpinyin = ''
            }

        },
        //取消
        editCancel() {
            this.editSpecModal = false
        }
    }
}
</script>