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
                    <BreadcrumbItem>新增物种</BreadcrumbItem>
                </Breadcrumb>
                <Steps :current="currentStep" class="mt40 mb40" style="margin-left: 250px; width: 80%;">
                    <Step title="物种基本信息"></Step>
                    <Step title="提交审核"></Step>
                </Steps>
                <div style="width: 100%;">
                    <div v-if="step" style="width: 800px; margin: 0 auto;">
                        <Form :model="formItem" ref="formItem" :label-width="120" label-position="right" :rules="ruleFormItem">
                            <FormItem label="物种分类" prop="selectedSpe">
                                <Cascader :data="specData" @on-change="getSelectedSpe"></Cascader>
                            </FormItem>
                            <FormItem label="物种名称" prop="fname">
                                <Input v-model="formItem.fname" placeholder="请输入内容" @on-change="getPinyin" @on-blur="checkFname" :maxlength="50" />
                            </FormItem>
                            <FormItem label="汉语拼音">
                                <Input v-model="formItem.fpinyin" readonly placeholder="由物种名称自动生成拼音" />
                            </FormItem>
                            <FormItem label="物种俗名">
                                <Input v-model="formItem.sucheng" :maxlength="20" />
                            </FormItem>
                            <FormItem label="其他分类">
                                <Cascader :data="specData" @on-change="getOtherSelectedSpe"></Cascader>
                            </FormItem>
                            <FormItem label="产业分类" prop="findustriaclassifiedid">
                                <Select v-model="formItem.findustriaclassifiedid" placeholder="请选择" clearable>
                                    <Option value="A01">农业</Option>
                                    <Option value="A02">林业</Option>
                                    <Option value="A03">畜牧业</Option>
                                    <Option value="A04">水产业</Option>
                                </Select>
                            </FormItem>
                            <FormItem label="是否保护">
                                <Select v-model="formItem.fisprotection" clearable>
                                    <Option value="0">否</Option>
                                    <Option value="1">一级保护</Option>
                                    <Option value="2">二级保护</Option>
                                    <Option value="3">地方重点保护</Option>
                                </Select>
                            </FormItem>
                            <FormItem label="物种图片">
                                <vui-upload
                                    @on-getPictureList="getPictureList"
                                    :hint="'图片大小小于2MB，最多上传 4 张'"
                                    :total="4"
                                    :size="[100,100]"
                                ></vui-upload>
                            </FormItem>
                            <FormItem label="主要产品">
                                <Input v-model="formItem.majorProduct" :maxlength="100" />
                            </FormItem>
                            <FormItem label="性状特征">
                                <Input v-model="formItem.fshapefeatureid" type="textarea"
                                    :autosize="{minRows: 2,maxRows: 5}" :maxlength="500" />
                            </FormItem>
                            <FormItem label="备注">
                                <Input v-model="formItem.fremarks" type="textarea"
                                    :autosize="{minRows: 2,maxRows: 5}" :maxlength="500" />
                            </FormItem>
                        </Form>
                        <div class="tc mt20 mb40">
                            <Button type="primary" @click="next">提交</Button>
                            <Button type="default" class="ml20" @click="complete">退出</Button>
                        </div>
                    </div>
                    <div v-else>
                        <div class="tc pt50 pb30">
                            <h2>您已提交新的物种信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
                        </div>
                        <div class="tc pt30 pb50">
                            <Button type="primary" @click="complete">完成</Button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <foot></foot>
    </div>
</template>

<script>
    import top from '../../top'
    import foot from '../../foot'
    import appBanner from '~components/app-banner'
    import vuiUpload from '~components/vui-upload'
    export default {
        components: {
            top,
            appBanner,
            foot,
            vuiUpload
        },
        data () {
            return {
                step: true,
                currentStep: 0,
                formItem: {
                    selectedSpe: '',
                    fname: '',
                    fpinyin: '',
                    sucheng: '',
                    otherSelectedSpe: '',
                    findustriaclassifiedid: '',
                    fimage: [],
                    majorProduct: '',
                    fshapefeatureid: '',
                    fremarks: '',
                    fisprotection: ''
                },
                ruleFormItem: {
                    selectedSpe: [
                        {required: true, min: 2, message: '请选择物种类型', trigger: 'change'}
                    ],
                    findustriaclassifiedid: [
                        {required: true, message: '请选择产业分类', trigger: 'change'}
                    ],
                    fname: [
                        {required: true, message: '请填写物种名称', trigger: 'blur'}
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
                ]
            }
        },
        created () {
        },
        methods: {
            // 获取照片
            getPictureList (e) {
                var arr = []
                e.forEach(element => {
                    if (element.response) {
                        arr.push(element.response.data.picName)
                    }
                })
                this.formItem.fimage = arr
            },
            getSelectedSpe (value) {
                this.formItem.selectedSpe = value[1]
            },
            getOtherSelectedSpe (value) {
                this.formItem.otherSelectedSpe = value[1]
            },
            // 得到汉字的拼音
            getPinyin () {
                if (this.formItem.fname !== '') {
                    this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.formItem.fname).then(response => {
                        this.formItem.fpinyin = ''
                        this.formItem.fpinyin = response.data
                    }).catch(error => {
                        this.$Message.error('获取拼音名称出错！')
                    })
                } else {
                    this.formItem.fpinyin = ''
                }

            },
            // 失去焦点时检测物种名是否被使用
            checkFname () {
                if (this.formItem.fname !== '') {
                    this.$api.get('/wiki/api/species/getIsNameExist/' + this.formItem.fname).then(response => {
                        if (response.data === 1) {
                            this.formItem.fname = ''
                            this.formItem.fpinyin = ''
                            this.$Message.error('此物种已存在！')
                        }
                    }).catch(error => {
                        this.$Message.error('查询物种名称出错！')
                    })
                }
            },
            next () {
                this.$refs['formItem'].validate((valid) => {
                    if (valid) {
                        let data = {
                            fclassifiedid: this.formItem.selectedSpe,
                            fname: this.formItem.fname,
                            fpinyin: this.formItem.fpinyin,
                            speciesVulgo: this.formItem.sucheng,
                            otherClassify: this.formItem.otherSelectedSpe,
                            findustriaclassifiedid: this.formItem.findustriaclassifiedid,
                            fisprotection: this.formItem.fisprotection,
                            ficon: this.formItem.fimage,
                            majorProduct: this.formItem.majorProduct,
                            fshapefeatureid: this.formItem.fshapefeatureid,
                            fremarks: this.formItem.fremarks,
                            auditstatus: '2',
                            fcreatorid: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                        }
                        console.log('data', data)
                        this.$api.post('/wiki/api/species/saveSpecies/', data).then(response => {
                            console.log('response', response)
                            if (response.code === 200) {
                                this.step = false
                                this.currentStep = 1
                            } else {
                                this.$Message.error('新增物种出错！')
                            }
                        }).catch(error => {
                            this.$Message.error('新增物种出错！')
                        })
                    }
                })
            },
            complete () {
                this.$router.push('/pro/nameLibrary')
            }
        }
    }
</script>
