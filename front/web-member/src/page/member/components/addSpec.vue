<template>
  <div>
        <Form :model="formItem" ref="formItem" :label-width="120" label-position="right"
            :rules="ruleFormItem">
        <FormItem label="物种分类：" prop="selectedSpe">
            <Cascader :data="specData" @on-change="getSelectedSpe"></Cascader>
            <Input v-model="formItem.selectedSpe" v-show="false"></Input>
        </FormItem>
        <FormItem label="物种名称：" prop="fname">
            <Input v-model="formItem.fname" placeholder="请输入内容" @on-change="getPinyin"
                    @on-blur="checkFname"></Input>
        </FormItem>
        <FormItem label="汉语拼音：" prop="fpinyin">
            <Input v-model="formItem.fpinyin" placeholder="由物种名称自动生成拼音"></Input>
        </FormItem>
        <FormItem label="其他物种分类：">
            <Cascader :data="specData" v-model="formItem.otherSelectedSpe"></Cascader>
        </FormItem>
        <FormItem label="产业分类：" prop="findustriaclassifiedid">
            <Select v-model="formItem.findustriaclassifiedid" placeholder="请选择">
                <Option value="A01">林业</Option>
                <Option value="A02">农业</Option>
                <Option value="A03">畜牧业</Option>
                <Option value="A04">水产业</Option>
            </Select>
        </FormItem>
        <FormItem label="物种图片：">
            <vui-upload
                @on-getPictureList="getPictureList"
                :hint="'图片大小小于2MB，最多上传 4 张'"
                :total="4"
                :size="[100,100]"
            ></vui-upload>
        </FormItem>
        <FormItem label="性状特征：">
            <Input v-model="formItem.fshapefeatureid" type="textarea"
                    :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></Input>
        </FormItem>
        <FormItem label="备注：">
            <Input v-model="formItem.fremarks" type="textarea"
                    :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></Input>
        </FormItem>
        <FormItem label="是否保护：">
            <RadioGroup v-model="formItem.fisprotection">
                <Radio label="0">否</Radio>
                <Radio label="1">一级保护</Radio>
                <Radio label="2">二级保护</Radio>
                <Radio label="3">地方重点保护</Radio>
            </RadioGroup>
        </FormItem>
    </Form>
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
            formItem:{
                type: Object,
                default: ()=>{
                    return {
                        selectedSpe: '',
                        fname: '',
                        fpinyin: '',
                        otherSelectedSpe: [],
                        findustriaclassifiedid: '',
                        fimage: [],
                        fshapefeatureid: '',
                        fremarks: '',
                        fisprotection: 0
                    }
                }
            },
            speciesid:{
                type:String,
                default:()=>{
                    return ''
                }
            }
        },
        data(){
            return{
                formInline: {
                    selectedSpe: []
                },
                ruleFormItem: {
                    selectedSpe: [
                        {required: true, min: 2, message: '请选择物种类型', trigger: 'blur'}
                    ],
                    fname: [
                        {required: true, message: '请填写物种名称', trigger: 'blur'}
                    ],
                    fpinyin: [
                        {required: true, message: '请填写汉语拼音', trigger: 'blur'}
                    ]
                },
                size: 0,
                uploadList: [],
                specData: [{
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
                currentStep: 0,
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
                modal1: false,
                daili: {
                    name: '',
                    type: '',
                    account: ''
                },
                corpInfo3: {
                    identification_card_picture_list: []
                },
                fname: true
            }
        },
        created(){
        },
        methods:{
            //表单验证，点击下一步触发
            update(name,indexid,speciesid){
                this.$refs[name].validate((valid) => {
                    console.log(valid)
                    if (valid) {
                        //得到用户信息
                        var loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
                        //1.提交第一步数据
                        api.post('/wiki/api/species/updateSpecies',
                            {
                                indexid: indexid,
                                fclassifiedid: this.formItem.selectedSpe,
                                fname: this.formItem.fname,
                                fpinyin: this.formItem.fpinyin,
                                fouthclassifiedid: this.formItem.otherSelectedSpe[1],
                                findustriaclassifiedid: this.formItem.findustriaclassifiedid,
                                fimage: this.corpInfo3.identification_card_picture_list,
                                fshapefeatureid: this.formItem.fshapefeatureid,
                                fremarks: this.formItem.fremarks,
                                fisprotection: this.formItem.fisprotection,
                                fcreatorid: loginuserinfo.loginAccount,
                                speciesid: this.speciesid
                            }
                        ).then(response => {                            
                            if (200 == response.code) {
                                this.$Message.success('更新物种成功')
                            } else {
                                this.$Message.success('更新物种失败!')
                            }
                        })
                    } else {
                        this.$Message.error('表单验证失败!');
                    }
                })
            },
            //表单验证，点击下一步触发
            get(name){
                this.$refs[name].validate((valid) => {
                    console.log(valid)
                    if (valid) {
                        //得到用户信息
                        var loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
                        this.checkFname()                
                        if(this.fname){
                            //1.提交第一步数据
                            api.post('/wiki/api/species/saveSpecies',
                                {
                                    fclassifiedid: this.formItem.selectedSpe,
                                    fname: this.formItem.fname,
                                    fpinyin: this.formItem.fpinyin,
                                    fouthclassifiedid: this.formItem.otherSelectedSpe[1],
                                    findustriaclassifiedid: this.formItem.findustriaclassifiedid,
                                    fimage: this.corpInfo3.identification_card_picture_list,
                                    fshapefeatureid: this.formItem.fshapefeatureid,
                                    fremarks: this.formItem.fremarks,
                                    fisprotection: this.formItem.fisprotection,
                                    fcreatorid: loginuserinfo.loginAccount
                                }
                            ).then(response => {                            
                                this.$emit('save',response)
                            })                            
                        }
                    } else {
                        this.$Message.error('表单验证失败!');
                    }
                })
            },
            // 获取照片
            getPictureList (e) {
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.corpInfo3.identification_card_picture_list = arr
            },
            getSelectedSpe(value) {
                this.formItem.selectedSpe = value[1]
            },
            // 得到汉字的拼音
            getPinyin() {
                if ('' != this.formItem.fname) {
                    api.get('/wiki/api/species/getSpeciesPinYin/' + this.formItem.fname).then(response => {
                        this.formItem.fpinyin = ''
                        this.formItem.fpinyin = response.data
                    }).catch(function (error) {
                        this.$Message.error(error)
                    })
                } else {
                    this.formItem.fpinyin = ''
                }

            },
            // 失去焦点时检测物种名是否被使用
            checkFname() {
                this.fname = true
                if ('' != this.formItem.fname) {
                    api.get('/wiki/api/species/getIsNameExist/' + this.formItem.fname).then(response => {
                        if (1 == response.data) {
                            this.formItem.fname = ''
                            this.$Message.error('此物种已存在');
                            this.fname = false
                        }
                    }).catch(function (error) {
                        this.$Message.error(error)
                    })
                }
            },
        }
    }

</script>
