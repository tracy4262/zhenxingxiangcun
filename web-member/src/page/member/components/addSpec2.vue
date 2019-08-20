<template>
  <div>
      <div class="tc mt20">
        <Button type="primary" @click="addMoreSpecVarietey">
            继续添加
        </Button>
    </div>

    <div align="left" class="mt30">
        <Collapse @on-change="changeCurrentIndex" :accordion="true">
            <!-- 遍历表单数组-->
            <div v-for="(formItem,index) in formItems">
                <Panel :name="index.toString()" class="clear add-spfrom-list">
                    {{formItem.itemName}}
                    <span class="fr">
                        <template v-if="currentEditIndex==index ">
                            <a href="javaScript:;" class="mr10" @click="save(index,$event)">保存</a>
                        </template>
                        <template v-if="currentEditIndex!=index ">
                            <a href="javaScript:;" class="mr10">编辑</a>
                        </template>
                    <a href="javaScript:;" class="mr10" @click="del(index,$event)">删除</a>
                </span>
                    <p slot="content" class="contentClass">
                    <Form :model="formItem" :label-width="140" label-position="right" :ref="'formItem'+index"
                            :rules="formItemRules">
                        <FormItem label="品种名称：" prop="fname">
                            <Input v-model="formItem.fname" placeholder="请输入内容"
                                    @on-change="getPinyin(index)" @on-blur="checkFname"></Input>
                        </FormItem>
                        <FormItem label="汉语拼音：" prop="fpinyin">
                            <Input v-model="formItem.fpinyin"
                                    placeholder="由品种名称自动生成拼音"></Input>
                        </FormItem>
                        <FormItem label="品种类型：" prop="fvarietykind">
                            <Input v-model="formItem.fvarietykind" placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="品种来源：" prop="fvarietyorigin">
                            <Input v-model="formItem.fvarietyorigin" type="textarea"
                                    :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></Input>
                        </FormItem>
                        <FormItem label="选育单位：" prop="fbreedingunit">
                            <Input v-model="formItem.fbreedingunit" placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="是否转基因：" prop="fistransgene">
                            <RadioGroup v-model="formItem.fistransgene">
                                <Radio label=1>是</Radio>
                                <Radio label=0>否</Radio>
                            </RadioGroup>
                        </FormItem>
                        <FormItem label="申请日：" prop="fapplydate">
                            <DatePicker class="date-wid24" type="date" placeholder="选择日期"
                                        v-model="formItem.fapplydate"></DatePicker>
                        </FormItem>
                        <FormItem label="申请号：" prop="fapplynumber">
                            <Input v-model="formItem.fapplynumber" placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem class="date-wid24" label="申请公告日：" prop="fapplyannouncedate">
                            <DatePicker class="date-wid24" type="date" placeholder="选择日期"
                                        v-model="formItem.fapplyannouncedate"></DatePicker>
                        </FormItem>
                        <FormItem label="申请公告号：" prop="fapplyannouncenumber">
                            <Input v-model="formItem.fapplyannouncenumber" placeholder="请输入内容"
                                    v></Input>
                        </FormItem>
                        <FormItem class="date-wid24" label="授权日：" prop="fauthdate">
                            <DatePicker class="date-wid24" type="date" placeholder="选择日期"
                                        v-model="formItem.fauthdate"></DatePicker>
                        </FormItem>
                        <FormItem label="授权号：" prop="fauthnumber">
                            <Input v-model="formItem.fauthnumber" placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="授权公告日：" prop="fauthannouncedate">
                            <DatePicker class="date-wid24" type="date" placeholder="选择日期"
                                        v-model="formItem.fauthannouncedate"></DatePicker>
                        </FormItem>
                        <FormItem label="授权公告号：" prop="fauthannouncenumber">
                            <Input v-model="formItem.fauthannouncenumber"
                                    placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="培育人：" prop="fgrowpeople">
                            <Input v-model="formItem.fgrowpeople"
                                    placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="品种权(申请)人：" prop="fvarietyowner">
                            <Input v-model="formItem.fvarietyowner" placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="品种权审定编号：" prop="fvarietyapprnum">
                            <Input v-model="formItem.fvarietyapprnum" placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="审定年份：" prop="fvarietyapprdate">
                            <DatePicker class="date-wid24" type="year"
                                        v-model="formItem.fvarietyapprdate"></DatePicker>
                        </FormItem>
                        <FormItem label="审定单位：" prop="fvarietyapprunit">
                            <Input v-model="formItem.fvarietyapprunit" placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="特征特性：" prop="ffeature">
                            <Input v-model="formItem.ffeature" placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="产量：" prop="foutput">
                            <Input v-model="formItem.foutput" placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="栽培技术：" prop="fgrowteachology">
                            <Input v-model="formItem.fgrowteachology" placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="适宜种植地区：" prop="fsuiteplatearea">
                            <Input v-model="formItem.fsuiteplatearea" placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="推广现状     ：" prop="fvarietyrecommand">
                            <Input v-model="formItem.fvarietyrecommand"
                                    placeholder="请输入内容"></Input>
                        </FormItem>
                        <FormItem label="上传图标：">
                            <!-- todo icon -->
                             <vui-upload
                                @on-getPictureList="getFicon"
                                :hint="'图片大小小于2MB，最多上传 4 张'"
                                :total="4"
                                :size="[100,100]"
                            ></vui-upload>
                        </FormItem>
                        <FormItem label="详细图片：">
                             <vui-upload
                                @on-getPictureList="getImage"
                                :hint="'图片大小小于2MB，最多上传 4 张'"
                                :total="4"
                                :size="[100,100]"
                            ></vui-upload>
                        </FormItem>
                    </Form>
                    <span class="content-class-save" >
                        <Button type="default" @click="save(index,$event)">保存</Button>
                    </span>
                    </p>
                </Panel>
            </div>
        </Collapse>
    </div>
  </div>
</template>
<script>
    import $ from 'jquery'
    import api from '~api'
    import vuiUpload from '~components/vui-upload'
    export default{
        components:{
            vuiUpload
        },
        props:{
            speciesid:{
                type:String,
                default:()=>{
                    return ''
                }
            }
        },
        data(){
            return{
                isEdit: false,
                isSave: true,
                formInline: {
                    selectedSpe: []
                },
                currentEditIndex: 0,
                indexid: '',
                formItems: [
                    {
                        indexid: '',//索引id
                        itemName: '请输入新品种名称:',//每条显示的名称
                        speciesid: this.speciesid,
                        fname: '',
                        fpinyin: '',
                        fvarietykind: '',
                        fvarietyorigin: '',
                        fbreedingunit: '',
                        fistransgene: 1,
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
                    }
                ],
                formItemRules: {
                    fname: [
                        {required: true, message: '请填写品种名称', trigger: 'blur'}
                    ],
                    fpinyin: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    fname: [
                        {required: true, message: '请填写品种名称', trigger: 'blur'}
                    ],
                    fvarietykind: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    fvarietyorigin: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    fbreedingunit: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    fistransgene: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ]
                },
                action: `${this.$url.upload}/upload/up`,
                currentStep: 1,
                highAppData: [],
                baseAppData: [],
                useAppData: [],
                displayName: '',
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                signature: '还没有签名！',
                favorite: 0,
                num: 0,
                value: '',
                customVal: '',
                subClassModalShow: false,
                subClassForm: {
                    subClass: '',
                    name: ''
                },
                size: 0,
                size2: 0,
                modal1: false,
                picType:''
            }
        },
        created(){
           
        },
        methods:{
            // 获取image
            getImage (e) {
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.formItems[this.currentEditIndex].image = arr
            },
             // 获取ficon
            getFicon (e) {
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.formItems[this.currentEditIndex].ficon = arr
            },
            // 得到汉字的拼音
            getPinyin(index) {
                if ('' != this.formItems[index].fname) {
                    api.get('/wiki/api/species/getSpeciesPinYin/' + this.formItems[index].fname).then(response => {
                        this.formItems[index].fpinyin = ''
                        this.formItems[index].fpinyin = response.data
                    }).catch(function (error) {
                        this.$Message.error(error)
                    })
                } else {
                    this.formItems[index].fpinyin = ''
                }

            },
            // 保存
            save(index,e) {
                var _this = this
                var name = 'formItem'

                var loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))

                if(e.target.className === 'mr10'){
                    e.cancelBubble = true
                    e.stopPropagation()
                }

                // 标题显示名
                if ('' != this.formItems[index].fname) {
                    this.formItems[index].itemName = this.formItems[index].fname
                }

                // 判断是插入品种还是编辑品种
                if ('' != this.formItems[index].indexid) {
                    api.post('/wiki/api/wiki/updateSpeciesVarietey',
                        {
                            indexid: this.formItems[index].indexid,
                            speciesid: this.speciesid,
                            fname: this.formItems[index].fname,
                            fpinyin: this.formItems[index].fpinyin,
                            fvarietykind: this.formItems[index].fvarietykind,
                            fvarietyorigin: this.formItems[index].fvarietyorigin,
                            fbreedingunit: this.formItems[index].fbreedingunit,
                            fistransgene: this.formItems[index].fistransgene,
                            fapplydate: this.formItems[index].fapplydate,
                            fapplynumber: this.formItems[index].fapplynumber,
                            fapplyannouncedate: this.formItems[index].fapplyannouncedate,
                            fapplyannouncenumber: this.formItems[index].fapplyannouncenumber,
                            fauthdate: this.formItems[index].fauthdate,
                            fauthnumber: this.formItems[index].fauthnumber,
                            fauthannouncedate: this.formItems[index].fauthannouncedate,
                            fauthannouncenumber: this.formItems[index].fauthannouncenumber,
                            fgrowpeople: this.formItems[index].fgrowpeople,
                            fvarietyowner: this.formItems[index].fvarietyowner,
                            fvarietyapprnum: this.formItems[index].fvarietyapprnum,
                            fvarietyapprdate: this.formItems[index].fvarietyapprdate,
                            fvarietyapprunit: this.formItems[index].fvarietyapprunit,
                            ffeature: this.formItems[index].ffeature,
                            foutput: this.formItems[index].foutput,
                            fgrowteachology: this.formItems[index].fgrowteachology,
                            fsuiteplatearea: this.formItems[index].fsuiteplatearea,
                            fvarietyrecommand: this.formItems[index].fvarietyrecommand,
                            ficon: this.formItems[index].ficon,
                            image: this.formItems[index].image,
                            fupdatorid: loginuserinfo.loginAccount
                        }
                    ).then(response => {
                        if (200 === response.code) {
                            this.$Message.success('更新品种成功!')
                        } else {
                            this.$Message.warning('更新品种失败!')
                        }
                    }).catch(function (error) {
                        this.$Message.error(error)
                    })
                }
                else {
                    api.post('/wiki/api/wiki/saveSpeciesVarietey',
                        {
                            speciesid: this.speciesid,
                            fname: this.formItems[index].fname,
                            fpinyin: this.formItems[index].fpinyin,
                            fvarietykind: this.formItems[index].fvarietykind,
                            fvarietyorigin: this.formItems[index].fvarietyorigin,
                            fbreedingunit: this.formItems[index].fbreedingunit,
                            fistransgene: this.formItems[index].fistransgene,
                            fapplydate: this.formItems[index].fapplydate,
                            fapplynumber: this.formItems[index].fapplynumber,
                            fapplyannouncedate: this.formItems[index].fapplyannouncedate,
                            fapplyannouncenumber: this.formItems[index].fapplyannouncenumber,
                            fauthdate: this.formItems[index].fauthdate,
                            fauthnumber: this.formItems[index].fauthnumber,
                            fauthannouncedate: this.formItems[index].fauthannouncedate,
                            fauthannouncenumber: this.formItems[index].fauthannouncenumber,
                            fgrowpeople: this.formItems[index].fgrowpeople,
                            fvarietyowner: this.formItems[index].fvarietyowner,
                            fvarietyapprnum: this.formItems[index].fvarietyapprnum,
                            fvarietyapprdate: this.formItems[index].fvarietyapprdate,
                            fvarietyapprunit: this.formItems[index].fvarietyapprunit,
                            ffeature: this.formItems[index].ffeature,
                            foutput: this.formItems[index].foutput,
                            fgrowteachology: this.formItems[index].fgrowteachology,
                            fsuiteplatearea: this.formItems[index].fsuiteplatearea,
                            fvarietyrecommand: this.formItems[index].fvarietyrecommand,
                            ficon: this.formItems[index].ficon,
                            image: this.formItems[index].image,
                            fcreatorid: loginuserinfo.loginAccount
                        }
                    ).then(response => {
                        if (200 === response.code) {
                            this.formItems[index].indexid = response.data.id
                            this.$Message.success('添加品种成功!')
                        } else {
                            this.$Message.warning('添加品种失败!')
                        }
                    }).catch(function (error) {
                        this.$Message.error(error)
                    })
                }
            },
            //  删除
            del(index,e) {
                if(e.target.className === 'mr10'){
                    e.cancelBubble = true
                    e.stopPropagation()
                }
                if(this.formItems[index].indexid !== undefined || this.formItems[index].indexid !== ''){
                    let indexid = this.formItems[index].indexid
                    api.get('/wiki/api/wiki/deleteSpeciesVarietey/' + indexid)
                        .then(response => {
                            this.$Message.success('删除品种成功!')
                        }).catch(function (error) {
                        this.$Message.error(error)
                    })
                }
                
                this.formItems.splice(index, 1)
            },
            // 继续添加
            addMoreSpecVarietey() {
                var loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
                var newFormitem = {
                    indexid: '',//索引id
                    itemName: '请输入新品种名称:',//每条显示的名称
                    speciesid: this.speciesid,
                    fname: '',
                    fpinyin: '',
                    fvarietykind: '',
                    fvarietyorigin: '',
                    fbreedingunit: '',
                    fistransgene: 1,
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
                    image: [],
                    fcreatorid: loginuserinfo.loginAccount
                }
                // 增加一栏
                this.formItems.push(newFormitem)
            },
            checkFname() {
                if ('' != this.formItems[this.currentEditIndex].fname) {
                    api.get('/wiki/api/wiki/existName/' + 2 + '/' + this.formItems[this.currentEditIndex].fname).then(response => {
                        if (1 === response.data) {
                            this.$Message.error("该物种名已被占用!")
                            this.formItems[this.currentEditIndex].fname = ''
                        }
                    }).catch(function (error) {
                        this.$Message.error(error)
                    })
                }
            },
            // 当前展开的索引,目前只能展开一个索引
            changeCurrentIndex(e) {
                if (e.length > 0){
                    this.currentEditIndex = e[0]
                }  
            },
        }
    }
</script>
<style>
    .content-class-save{
        text-align: center;
        display: block;
    }
    .contentClass {
        margin-left: 20%;
        margin-right: 20%;
    }
</style>
