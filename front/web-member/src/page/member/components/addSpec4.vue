<template>
  <div>
    <div class="tc mt20">
        <Button type="primary" @click="addMorePest">
            继续添加
        </Button>
    </div>
    <div align="left" style="margin-top: 5%">
        <Collapse @on-change="changeCurrentIndex" :accordion="true">
            <div v-for="(formItem ,index) in formItems">
                <Panel :name="index.toString()" class="clear add-spfrom-list">
                    {{formItem.itemName}}
                    <span class="fr">
                        <template v-if="currentEditIndex==index ">
                            <a href="javaScript:;" class="mr10" @click="save(index,$event)">保存</a>
                        </template>
                    <template v-if="currentEditIndex!=index ">
                            <a href="javaScript:;" class="mr10" >编辑</a>
                    </template>
                            <a href="javaScript:;" class="mr10" @click="del(index,$event)">删除</a>
                </span>
                    <p slot="content" class="contentClass">
                    <Form :model="formItem" ref="formItem" :label-width="120"
                            label-position="right" :rules="formItemRules">
                        <FormItem label="害虫名称：" prop="fname">
                            <Input v-model="formItem.fname" placeholder="请输入内容"
                                    @on-change="getPinyin(index)" @on-blur="checkFname"></Input>
                        </FormItem>
                        <FormItem label="汉语拼音：" prop="fpinyin">
                            <Input v-model="formItem.fpinyin" placeholder="由品种名称自动生成拼音"></Input>
                        </FormItem>
                        <FormItem label="上传图片：">
                            <vui-upload
                                    @on-getPictureList="getFimagesrc"
                                    :hint="'图片大小小于2MB，最多上传 4 张'"
                                    :total="4"
                                    :size="[100,100]"
                                ></vui-upload>
                        </FormItem>
                        <FormItem label="形态特征：" prop="fmainfeatures">
                            <Input v-model="formItem.fmainfeatures" type="textarea"
                                    :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></Input>
                        </FormItem>
                        <FormItem label="危害症状：" prop="fhabit">
                            <Input v-model="formItem.fhabit" type="textarea"
                                    :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></Input>
                        </FormItem>
                        <FormItem label="发生规律：" prop="fpetsregular">
                            <Input v-model="formItem.fpetsregular" type="textarea"
                                    :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></Input>
                        </FormItem>
                        <FormItem label="防治方法：" prop="fprotectmethod">
                            <Input v-model="formItem.fprotectmethod" type="textarea"
                                    :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></Input>
                        </FormItem>
                        <FormItem label="备注：" prop="fremarks">
                            <Input v-model="formItem.fremarks" type="textarea"
                                    :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></Input>
                        </FormItem>

                    </Form>
                    <span class="content-class-save-pest" >
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
             return {
                components: {},
                indexid: '',
                currentEditIndex: '0',
                formItems: [{
                    itemName: '请输入虫害基本信息',
                    indexid: '',
                    speciesid: '',
                    fname: '',
                    fpinyin: '',
                    fimagesrc: [],
                    fmainfeatures: '',
                    fhabit: '',
                    fpetsregular: '',
                    fprotectmethod: '',
                    fremarks: ''
                }],
                currentStep: 3,
                displayName: '',
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                signature: '还没有签名！',
                favorite: 0,
                num: 0,
                value: '',
                modal1: false,
                formItemRules: {
                    fname: [
                        {required: true, message: '请填写病害名称', trigger: 'blur'}
                    ],
                    fpinyin: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ]
                }
            }
        },
         methods: {
            // 获取image
            getFimagesrc (e) {
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.formItems[this.currentEditIndex].fimagesrc = arr
            },
            // 点击上一步
            upStep() {
                this.$router.push({path: '/pro/addSpec3', query: {speciesid: this.speciesid}})
            },
            // 点击下一步
            nextStep() {
                this.$router.push({path: '/pro/addSpec5', query: {speciesid: this.speciesid}})
            },
            // 点击退出
            exitAdd() {
                //跳转到名称库管理
                this.$router.push("/pro/nameLibrary")
            },
            // 添加更多病害
            addMorePest() {
                var newPest = {
                    itemName: '虫害基本信息',
                    indexid: '',
                    speciesid: '',
                    fname: '',
                    fpinyin: '',
                    fimagesrc: [],
                    fmainfeatures: '',
                    fhabit: '',
                    fpetsregular: '',
                    fprotectmethod: '',
                    fremarks: ''
                }
                this.formItems.push(newPest)
            },
            // 当前展开的索引,目前只能展开一个所以
            changeCurrentIndex(e) {
                if (e.length > 0)
                    this.currentEditIndex = e[0]
            },
            checkFname() {
                if ('' != this.formItems[this.currentEditIndex].fname) {
                    api.get('/wiki/api/wiki/existName/' + 4+ '/' + this.formItems[this.currentEditIndex].fname).then(response => {
                        if (1 === response.data) {
                            this.$Message.error("该虫害名已被占用!")
                            this.formItems[this.currentEditIndex].fname = ''
                        }
                    }).catch(function (error) {
                        this.$Message.error(error)
                    })
                }
            },
            save(index,e) {
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
                    api.post('/wiki/api/wiki/updateSpeciesPest',
                        {
                            indexid: this.formItems[index].indexid,
                            speciesid: this.speciesid,
                            fname: this.formItems[index].fname,
                            fpinyin: this.formItems[index].fpinyin,
                            fimagesrc: this.formItems[index].fimagesrc,
                            fmainfeatures: this.formItems[index].fmainfeatures,
                            fhabit: this.formItems[index].fhabit,
                            fpetsregular: this.formItems[index].fpetsregular,
                            fprotectmethod: this.formItems[index].fprotectmethod,
                            fremarks: this.formItems[index].fremarks,
                            fupdatorid: loginuserinfo.loginAccount
                        }
                    ).then(response => {
                        if (200 === response.code) {
                            this.$Message.success('更新虫害成功!')
                        } else {
                            this.$Message.warning('更新虫害失败!')
                        }
                    }).catch(function (error) {
                        this.$Message.error(error)
                    })
                }
                else {
                    api.post('/wiki/api/wiki/saveSpeciesPest',
                        {
                            speciesid: this.speciesid,
                            fname: this.formItems[index].fname,
                            fpinyin: this.formItems[index].fpinyin,
                            fimagesrc: this.formItems[index].fimagesrc,
                            fmainfeatures: this.formItems[index].fmainfeatures,
                            fhabit: this.formItems[index].fhabit,
                            fpetsregular: this.formItems[index].fpetsregular,
                            fprotectmethod: this.formItems[index].fprotectmethod,
                            fremarks: this.formItems[index].fremarks,
                            fcreatorid: loginuserinfo.loginAccount
                        }
                    ).then(response => {
                        if (200 === response.code) {
                            this.formItems[index].indexid = response.data
                            this.$Message.success('添加虫害成功!')
                        } else {
                            this.$Message.warning('添加虫害失败!')
                        }
                    }).catch(function (error) {
                        this.$Message.error(error)
                    })
                }
            },
            del(index,e) {
                var indexid = this.formItems[index].indexid
                if(e.target.className === 'mr10'){
                    e.cancelBubble = true
                    e.stopPropagation()
                }
                //  删除后台的数据
                api.get('/wiki/api/wiki/deleteSpeciesPest/' + indexid)
                    .then(response => {
                        this.$Message.success('删除虫害成功!')
                    }).catch(function (error) {
                    this.$Message.error(error)
                })
                this.formItems.splice(index,1)
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
         }
     }

</script>
<style lang="scss">
    .content-class-save-pest{
        text-align: center;
        display: block;
    }
</style>
