<template>
    <div class="food-quality  pt30 pl10 pr10">
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="三品一标">
                        <Button type="primary" @click="handleAddFood"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <!-- <div class="foodCard"> -->
                <!-- <food-card v-for="(item,index) in foodFormItems"
                    :index="index"
                    :key="index"
                    :data="item"
                    @on-edit="editFood"
                    @on-del="delFood">
                ></food-card> -->
            <!-- </div> -->
            <Row :gutter="16">
                <Col span="12" v-for="(item,index) in this.foodFormItems" :key="index"> 
                    <Card class="mb20"  style="height:142px">
                        <Row  type="flex" align="top">
                            <Col :span="6" v-if="item.avatar">
                                <img :src="item.avatar" width="86px" height="110px">
                            </Col>
                            <Col :span="item.avatar ? '18':'24'">
                                <Row type="flex" align="middle">
                                    <Col span="12" class="colDetail ell">  
                                        {{item.name}}
                                    </Col>
                                    <Col span="12" class="tr">
                                        <div class="btn-toolbar">
                                            <Button type="text"  @click="handleEdit(index)"  size="small"><Icon type="edit" size="16" class="pr5"></Icon> 编辑</Button>
                                            <Button type="text"  @click="handleDel(index)"  size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                                        </div>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="24" class="pt10">
                                        <span  style="color:#00c587">{{item.category}}</span>
                                    </Col>
                                </Row>
                                <Row class="pt5 t-grey ft12">
                                    <Col span="24">
                                        <div class="content ell-3">
                                            {{item.introduction}}
                                        </div>
                                    </Col>
                                </Row>
                            </Col>
                        </Row>
                    </Card>
                </Col>
            </Row>
            
        </Form>
        <Modal
            v-model="foodShow"
            title="添加三品一标"
            :mask-closable="false"
            width="800px">
            <div class="pd20">
                <Form ref="foodForm" :model="foodForm" label-position="left" :label-width="100" :rules="foodRules">
                    <Row :gutter="32">
                        <Col :span="12">
                            <Form-item prop="category" label="类型">
                                <Select v-model="foodForm.category">
                                    <Option v-for="item in categoryList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                                </Select>
                            </Form-item>
                        </Col>
                        <Col :span="12">
                            <Form-item prop="name" label="名称">
                                <Input v-model="foodForm.name" :maxlength="20"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="24">
                            <Form-item prop="avatar" label="资质证书">
                                <vui-upload
                                    ref="upload"
                                    @on-getPictureList="getPictureList"
                                    :pictureLists="foodForm.avatar"
                                    :total="1"
                                    :multiple="false"
                                    :hint="'图片大小小于2M'">
                                </vui-upload>
                            </Form-item>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="24">
                            <Form-item prop="introduction" label="简介">
                                <Input v-model="foodForm.introduction" type="textarea" :rows="4" :maxlength="500"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
            </div>
            <div slot="footer" class="tc">
                <Button type="default" @click="foodShow = false">取消</Button>
                <Button type="primary" @click.native="handleSubmit">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import vuiUpload from '~components/vui-upload'
import foodCard from './foodCard'
export default {
    components:{
        vuiUpload,
        foodCard
    },
    data () {
        return {
            more:false,
            foodShow:false,
            foodForm:{
                category:'',
                name:'',
                avatar:'',
                introduction:''
            },
            categoryList:[
                {
                    label:'无公害',
                    value:'无公害'
                },
                {
                    label:'绿色食品',
                    value:'绿色食品'
                },
                {
                    label:'有机食品',
                    value:'有机食品'
                },
            ],
            foodRules:{
                category:[
                    {required:true,message:'请选择类型',trigger:'blur'}
                ],
                name:[
                    {required:true,message:'请填写名称',trigger:'blur'}
                ]
            },
            foodFormItems:[],
            editIndex:0,
            isAdd:true
        }
    },
    methods:{
        // 添加
        handleAddFood () {
            this.foodShow = true;
            this.isAdd = true
            this.foodForm = {
                category:'',
                name:'',
                avatar:'',
                introduction:''
            }
            this.$refs['upload'].handleGive(this.foodForm.avatar)
        },
        // 获取图片地址
        getPictureList (e) {
            this.foodForm.avatar = e[0].response.data.picName
        },
        // 确定
        handleSubmit () {
            this.$refs['foodForm'].validate((valid) => {
                if(valid){
                    if (this.isAdd) { //添加
                        this.foodFormItems.push(this.foodForm)
                    }else{ //编辑
                        this.foodFormItems.splice(this.editIndex,1,this.foodForm)
                    }
                    this.foodShow = false                    
                }
            })
            
        },
        // editFood (index) {
        //     this.foodShow = true;
        //     this.foodForm = this.foodFormItems[index]
        // },
        // delFood (index) {
        //     this.foodFormItems.splice(index,1)
        // },
        // 编辑
        handleEdit (index) {
            this.editIndex = index
            this.isAdd = false
            this.foodShow = true;
            this.foodForm = this.foodFormItems[index]
            this.$refs['upload'].handleGive(this.foodForm.avatar)
        },
        // 删除
        handleDel (index) {
             this.$Modal.confirm({
                title: '是否确定删除',
                content: '是否确认删除？',
                onOk:()=>{
                    this.foodFormItems.splice(index,1)
                },
                okText:'确定',
                cancelText:'取消'
            });            
        }
    }
}
</script>

<style lang="scss">
</style>


