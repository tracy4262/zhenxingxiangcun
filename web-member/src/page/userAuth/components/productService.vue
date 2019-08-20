<template>
    <div class="product-service pt30 pl10 pr10">
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="产品&服务">
                        <Button type="primary" @click="handleAddProductService"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <product-service-card v-for="(item,index) in data"
                :data="item"
                :index="index"
                :key="index"
                @on-edit="editProductService"
                @on-del="delProductService"
               >
            </product-service-card>
        </Form>
        <Modal
            v-model="productService"
            :title="title"
            :mask-closable="false"
            width="800">
            <div class="pd20">
                <Form ref="productServiceForm" :model="productServiceForm" label-position="left" :label-width="100" :rules="productServiceRules">
                    <Row :gutter="32">
                        <Col span="24">
                        <FormItem label="权限">
                            <i-switch  v-model="productServiceForm.product_status" size="large">
                                <span slot="open">公开</span>
                                <span slot="close">隐藏</span>
                            </i-switch>
                        </FormItem>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="12">
                            <Form-item prop="category" label="类型">
                                <!-- <Input v-model="productServiceForm.category"></Input> -->
                                <Select v-model="productServiceForm.category">
                                    <Option v-for="item in categoryList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                                </Select>
                            </Form-item>
                        </Col>
                        <Col :span="12">
                            <Form-item prop="product" label="三品一标">
                                <Input v-model="productServiceForm.product"></Input>
                            </Form-item>
                        </Col>
                        <Col :span="12">
                            <Form-item prop="name" label="名称">
                                <Input v-model="productServiceForm.name" :maxlength="50"></Input>
                            </Form-item>
                        </Col>
                    <!-- </Row>
                    <Row :gutter="32"> -->
                        <Col :span="12">
                            <Form-item prop="relatedSpecies" label="关联物种">
                                <Input v-model="productServiceForm.relatedSpecies"   class="pinput-clear" icon="ios-close" @on-click="inputClear('relatedSpecies')" readonly @on-focus="handleFilterModal('speciFilter')" ></Input>
                            </Form-item>
                        </Col>
                        <Col :span="12">
                            <Form-item prop="brand" label="品牌">
                                <Input v-model="productServiceForm.brand" :maxlength="20"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                    <!-- <Form-item prop="knowledgeProperty" label="知识产权">
                        <Input v-model="productServiceForm.knowledgeProperty" type="textarea" :autosize="{minRows: 4,maxRows: 5}"></Input>
                    </Form-item> -->
                    <Form-item prop="introduction" label="简介">
                        <Input v-model="productServiceForm.introduction" type="textarea" :autosize="{minRows: 4,maxRows: 5}" :maxlength="500"></Input>
                    </Form-item>
                    <Form-item prop="certificateList" label="资质证书">
                        <vui-upload
                            ref="certificateList"
                            @on-getPictureList="getCertificateList"
                            :pictureLists="productServiceForm.certificateList"
                            :hint="'图片大小小于2MB'"
                            :total="9"
                        ></vui-upload>
                    </Form-item>
                    <Form-item prop="pictureList" label="图片">
                        <vui-upload
                            ref="pictureList"
                            @on-getPictureList="getPictureList"
                            :pictureLists="productServiceForm.pictureList"
                            :hint="'图片大小小于2MB'"
                        ></vui-upload>
                    </Form-item>
                </Form>
            </div>
            <div slot="footer" class="tc">
                <Button type="default" @click="productService = false">取消</Button>
                <Button type="primary" @click.native="productServiceOK">确定</Button>
            </div>
            <!-- 相关物种 -->
            <vui-filter
            ref="speciFilter"
            :cols="2"
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
        </Modal>
    </div>
</template>


<script>
import vuiUpload from '~components/vui-upload'
import vuiFilter from "~components/vuiFilter/filter";
import productServiceCard from './productServiceCard'
export default {
    components:{
        vuiUpload,
        vuiFilter,
        productServiceCard
    },
    created () {
        // 取相关物种结果
        this.loadSpeciResult("", "", [], this.pageCur, []);
    },
    data () {
        return {
            productService: false,
            productServiceForm: {
                product_status: true,
                category:'',
                name:'',
                relatedSpecies:'',
                brand:'',
                product:'',
                introduction:'',
                pictureList:[],
                certificateList:[]
            },
            categoryList:[{
                value:'产品',
                label:'产品'
            },{
                value:'服务',
                label:'服务'
            }],
            productServiceRules:{
                product:[{required:true,trigger:'blur',message:'请填写三品一标'}],
                category:[{required:true,trigger:'blur',message:'请选择类型'}],
                name:[{required:true,message:'请填写名称',trigger:'blur'}],
                relatedSpecies:[{required:true,message:'请选择关联物种',trigger:'change'}],
            },
            total:0,
            pageCur:1,
            speciClassifyDatas: [
                {
                label: "动物",
                value: "0",
                classId: "",
                loading: false,
                checked: false,
                children: []
                },
                {
                label: "植物",
                value: "1",
                classId: "",
                loading: false,
                checked: false,
                children: []
                }
            ],
            speciResultDatas: [],
            data:[],
            isAdd: true,
            editIndex:'',
            title: '添加产品&服务'
        }
    },
    methods: {
        //接收数据
        getData(val){
            this.data = val
        },
        //表单验证
        handleSubmit(){
           this.$emit('on-submit',true)
        },
        //点击添加按钮出现弹框
        handleAddProductService () {
            this.productService = true;
            this.isAdd = true
            this.productServiceForm = {
                product_status: true,
                category:'',
                name:'',
                relatedSpecies:'',
                brand:'',
                knowledgeProperty:'',
                introduction:'',
                pictureList:[],
                certificateList:[]
            }
            this.title = '添加产品&服务'
            //清除物种，清除弹窗图片
            this.inputClear('relatedSpecies')
            this.$refs['pictureList'].handleGive(this.productServiceForm.pictureList)
            this.$refs['certificateList'].handleGive(this.productServiceForm.certificateList)
            this.$nextTick(() => {
                this.$refs['productServiceForm'].resetFields()
            })
        },
        //点击弹窗确定按钮获取表单信息
        productServiceOK () {
            this.$refs.productServiceForm.validate ( (valid) => {
                if(valid) {
                    if(this.isAdd){
                         this.data.unshift(this.productServiceForm);
                    }else{
                        //编辑
                        this.data.splice(this.editIndex,1,this.productServiceForm)
                    }  
                    this.productService = false;          
                    // console.log(this.data)
                }else{
                    this.$Message.error('请核对表单信息')
                }
            })
        },        
        //点击编辑弹窗显示内容
        editProductService (index) {
            this.isAdd = false
            this.title = '编辑产品&服务'
            this.editIndex = index
            this.productService = true;
            this.productServiceForm =  Object.assign({}, this.data[index])
            this.$refs['pictureList'].handleGive(this.productServiceForm.pictureList)
            this.$refs['certificateList'].handleGive(this.productServiceForm.certificateList)
        },
        //删除对应产品服务
        delProductService (index) {
            this.data.splice(index,1)
        },
        //上传图片
        getPictureList (e) {
            var arr = []
            e.forEach( element => {
                if(element.response){
                    arr.push(element.response.data.picName)
                }
            })
            this.productServiceForm.pictureList = arr
        },
        //上传图片
        getCertificateList (e) {
            var arr = []
            e.forEach( element => {
                if(element.response){
                    arr.push(element.response.data.picName)
                }
            })
            this.productServiceForm.certificateList = arr
        },
        //清除关联物种表单
        inputClear (name) {
            if(name === 'relatedSpecies') {
                this.productServiceForm.relatedSpecies = '';
                this.$refs.speciFilter.handleReset()
            }
        },
        //获取焦点时弹出高级搜索框
        handleFilterModal (name) {
            this.$refs[name].highFilterShow = true;
        },
        // 取相关物种
        handleSpeciSearch(letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
        },
        loadSpeciDatas(item, callback) {
            item.loading = true;
            this.$api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
                item.loading = false;
                var d = res.data;
                d.forEach(child => {
                    child.checked = false;
                    child.label = child.className;
                });
                item.children = d;
                callback();
            });
        },
        handleGetSpeciClassify(letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
        },
        handleSpeciPageChange(letter, keyword, classify, num, result) {
            this.pageCur = num;
            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
        },
        //相关物种搜索
        loadSpeciResult(letter, keyword, classify, num, result) {
            if (classify.length) {
                var arr = [];
                var type = '';
                classify.forEach(item => {
                    arr.push(item.classId);
                    if (item.value !== undefined) type = item.value;
                });
            } else {
                arr = null;
            }
            this.$api.post('/member/specicesClass/findSpecies', {
                keywords: keyword,
                fpinyin: letter === '全部' ? '' : letter,
                fclassifiedid: arr,
                type: type,
                pageNum: num,
                pageSize: 32
            }).then(res => {
                let data = res.data;
                if (data === undefined) {
                    this.total = 0;
                } else {
                    this.total = data.total;
                }
                if (result) {
                    if (data === undefined) {
                        this.speciResultDatas = [];
                        return false;
                    } else {
                        result.forEach(item => {
                            data.list.forEach((child, index) => {
                                if (child.label === item.label) {
                                    child.checked = true;
                                }
                            });
                        });
                    }
                } else {
                    data.list.forEach(child => {
                        child.checked = false;
                    });
                }
                this.speciResultDatas = data.list;
            });
        },

        //相关物种查询
        handleGetSpeciResult(classify, result) {
            var arr = [];
            if (result != '') {
                result.forEach(item => arr.push(item.label));
                this.productServiceForm.relatedSpecies = arr.join(' ');
                this.speciesDatas = result.map(function (item) {
                    return item.value;
                });
            } else {
                this.speciesDatas = [];
            }
        },
    }
}
</script>

<style lang="scss">
</style>
