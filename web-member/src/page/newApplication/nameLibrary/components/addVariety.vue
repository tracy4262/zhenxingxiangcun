<template>
    <Card class="pt30">
        <div v-if="step" style="width: 1000px; margin: 0 auto;">
            <Form :model="formItem" :label-width="140" label-position="right" ref="formItem" :rules="formItemRules">
            <Row>
                <Col span="12">
                    <FormItem label="物种名称" prop="speciesid">
                    <vuiSpecies ref="vuiSpecies" :disabled="edit" :values="formItem.specName" @on-save="onSaveName" @on-save-id="onSaveId" :num="1"></vuiSpecies>
                        <!-- <Input v-model="formItem.specName" readonly @on-focus="handleFilterModal('speciFilter')" /> -->
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="品种名称" prop="fname">
                        <Input v-model="formItem.fname"  :readonly="varietyId ? true : false" :disabled="edit" :maxlength="20" placeholder="请输入内容" @on-change="getAddPinyin" @on-blur="checkAddFname" />
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="12">
                <FormItem label="汉语拼音">
                    <Input v-model="formItem.fpinyin" :disabled="edit" readonly placeholder="由品种名称自动生成拼音" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="品种类型" prop="fvarietykind">
                    <Input v-model="formItem.fvarietykind" :disabled="edit" :maxlength="20" placeholder="请输入内容" />
                </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="12">
                <FormItem label="选育单位" prop="fbreedingunit">
                    <Input v-model="formItem.fbreedingunit" :disabled="edit" :maxlength="50" placeholder="请输入内容" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="是否转基因" prop="fistransgene">
                    <RadioGroup v-model="formItem.fistransgene" :disabled="edit">
                        <Radio label=1>是</Radio>
                        <Radio label=0>否</Radio>
                    </RadioGroup>
                </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="12">
                <FormItem label="申请日" prop="fapplydate">
                    <DatePicker type="date" placeholder="选择日期" :disabled="edit" v-model="formItem.fapplydate" class="date-wid24"></DatePicker>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="申请号" prop="fapplynumber">
                    <Input v-model="formItem.fapplynumber" :disabled="edit" :maxlength="20" placeholder="请输入内容" />
                </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="12">
                <FormItem label="申请公告日" prop="fapplyannouncedate">
                    <DatePicker class="date-wid24":disabled="edit" type="date" placeholder="选择日期" v-model="formItem.fapplyannouncedate"></DatePicker>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="申请公告号" prop="fapplyannouncenumber">
                    <Input v-model="formItem.fapplyannouncenumber" :disabled="edit" :maxlength="20" placeholder="请输入内容" />
                </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                <FormItem label="授权日" prop="fauthdate">
                    <DatePicker class="date-wid24" type="date" :disabled="edit" placeholder="选择日期" v-model="formItem.fauthdate"></DatePicker>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="授权号" prop="fauthnumber">
                    <Input v-model="formItem.fauthnumber" :disabled="edit" :maxlength="20" placeholder="请输入内容" />
                </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                <FormItem label="授权公告日" prop="fauthannouncedate">
                    <DatePicker class="date-wid24" type="date" :disabled="edit" placeholder="选择日期" v-model="formItem.fauthannouncedate"></DatePicker>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="授权公告号" prop="fauthannouncenumber">
                    <Input v-model="formItem.fauthannouncenumber" :disabled="edit" :maxlength="20" placeholder="请输入内容" />
                </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                <FormItem label="培育人" prop="fgrowpeople">
                    <Input v-model="formItem.fgrowpeople" :disabled="edit" :maxlength="20" placeholder="请输入内容" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="品种权(申请)人" prop="fvarietyowner">
                    <Input v-model="formItem.fvarietyowner" :disabled="edit" :maxlength="20" placeholder="请输入内容" />
                </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                <FormItem label="品种权审定编号" prop="fvarietyapprnum">
                    <Input v-model="formItem.fvarietyapprnum" :disabled="edit" :maxlength="20" placeholder="请输入内容" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="审定年份" prop="fvarietyapprdate">
                    <DatePicker class="date-wid24" type="year" :disabled="edit" v-model="formItem.fvarietyapprdate"></DatePicker>
                </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                <FormItem label="审定单位" prop="fvarietyapprunit">
                    <Input v-model="formItem.fvarietyapprunit" :disabled="edit" :maxlength="50" placeholder="请输入内容" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="特征特性" prop="ffeature">
                    <Input v-model="formItem.ffeature" :disabled="edit" :maxlength="200" placeholder="请输入内容" />
                </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                <FormItem label="产量" prop="foutput">
                    <Input v-model="formItem.foutput" :maxlength="20" :disabled="edit" placeholder="请输入内容" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="栽培技术" prop="fgrowteachology">
                    <Input v-model="formItem.fgrowteachology" :maxlength="200" :disabled="edit" placeholder="请输入内容" />
                </FormItem>
                </Col>

                <Col span="12">
                <FormItem label="适宜种植地区" prop="fsuiteplatearea">
                    <Input v-model="formItem.fsuiteplatearea" :maxlength="200" :disabled="edit" placeholder="请输入内容" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="推广现状" prop="fvarietyrecommand">
                    <Input v-model="formItem.fvarietyrecommand" :maxlength="200" :disabled="edit" placeholder="请输入内容" />
                </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <FormItem label="品种来源" prop="fvarietyorigin">
                        <Input v-model="formItem.fvarietyorigin" type="textarea" :disabled="edit" :maxlength="500" :autosize="{minRows: 2,maxRows: 5}" />
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="24">
                    <FormItem label="上传图标" prop="ficon">
                        <vui-upload
                            ref="formItemFicon"
                            :disabled="edit"
                            @on-getPictureList="getFormItemFicon"
                            :hint="'图片大小小于2MB，最多上传 1 张'"
                            :total="1"
                            :size="[100,100]"
                            ></vui-upload>
                    </FormItem>
                </Col>
            </Row>
        </Form>
        <div align="center" class="mb30">
            <Button type="primary" class="mr20" @click="next()" v-if="!edit">完成</Button>
            <Button type="default" @click="complete">退出</Button>
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
    </Card>
</template>

<script>
    import vuiUpload from '~components/vui-upload'
    import vuiFilter from '~components/vuiFilter/filter'
    import vuiSpecies from '~components/vui-species'
    export default {
        components: {
            vuiUpload,
            vuiFilter,
            vuiSpecies
        },
        data () {
            return {
                step: true,
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
                },
                varietyId: '',
                edit: true
            }
        },
        created () {
            if (this.$route.query.speciesId) {
                console.log('9999')
                this.varietyId = this.$route.query.speciesId
                this.getData()
            }
            if (this.$route.query.edit) {
                this.edit = true
            } else{
                this.edit = false
            }
        },
        methods: {
            getData () {
              this.$api.get('/wiki/api/wiki/getSpeciesVarietey/' + this.varietyId).then(response => {
                    response.data.fistransgene = response.data.fistransgene+ ''
                    this.formItem = response.data
                    this.$refs['formItemFicon'].handleGive(this.formItem.ficon)
                }).catch(error => {
                    console.log(error)
                })
            },
            // 获取物种
            onSaveName (e) {
              this.formItem.specName = e
            },
            onSaveId (e) {
              this.formItem.speciesid = e
              console.log(this.formItem)
            },
            getFormItemFicon (e) {
                var arr = []
                e.forEach(element => {
                    if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.formItem.ficon = arr
                if (arr.length) {
                    this.handleSubmit('formItem')
                } else {
                    this.handleSubmit('formItem', '1')
                }
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
                    if (this.varietyId) { // 更新
                        this.formItem.fvarietyapprdate = this.formItem.fvarietyapprdate ? this.moment(this.formItem.fvarietyapprdate).format('YYYY') : ''
                        this.formItem.fupdatorid = this.$user.loginAccount
                        this.formItem.fcreatorid = this.$user.loginAccount
                        this.$api.post('/wiki/api/wiki/updateSpeciesVarietey',this.formItem).then(response => {
                            console.log("提交的参数==>>", response)
                            if (200 === response.code) {
                                console.log('更新返回值', response.data)
                                this.$Message.success('更新品种成功!')
                                this.step = false
                            } else {
                                this.$Message.success('更新品种失败!')
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    } else { // 新增
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
                }
            },
            complete () {
                this.$router.push('/nameLibrary/variety')
            },
            checkAddFname () {
                if (this.formItem.fname && !this.varietyId) {
                    this.$api.get('/wiki/api/wiki/existName/' + 2 + '/' + this.formItem.fname).then(response => {
                        console.log("检测物种名是否被占用:==>>", response.data, "<<==")
                        if (1 === response.data) {
                            this.$Message.error("该品种名已被占用!")
                            this.formItem.fname = ''
                        }
                    }).catch(function (error) {
                        console.log(error)
                    })
                }
            },
            handleSubmit (name, pic) {
                let flag = false;
                this.$refs[name].validate(valid => {
                    if (valid) {
                        flag = true;
                    } else {
                        if (!pic) {
                            this.$Message.error('请核对表单信息')
                        }
                    }
                });
                return flag
            }
        }
    }
</script>
