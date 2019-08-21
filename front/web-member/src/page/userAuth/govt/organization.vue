<template>
    <div class="organization  pt30 pl10 pr10">
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="组织机构">
                        <Button type="primary" @click="handleAddOrganization"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <organization-card v-for="(item,index) in organizationFormItems"
                :index="index"
                :key="index"
                :data="item"
                @on-edit="editOrganization"
                @on-del="delOrganization">
            ></organization-card>
        </Form>
        <Modal
            v-model="organizationShow"
            title="添加组织机构"
            :mask-closable="false"
            width="50%">
            <div class="pd20">
                <Form ref="organizationForm" :model="organizationForm" label-position="left" :label-width="100"  :rules="organizationRules">
                    <Row :gutter="32">
                        <Col :span="12">
                            <Form-item prop="departmentName" label="部门名称">
                                <Input v-model="organizationForm.departmentName"></Input>
                            </Form-item>
                        </Col>
                        <Col :span="12">
                            <Form-item prop="head" label="负责人">
                                <Input v-model="organizationForm.head"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="12">
                            <Form-item prop="phone" label="联系电话">
                                <Input v-model="organizationForm.phone" :maxlength="11"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="24">
                            <Form-item prop="introduction" label="机关简介">
                                <Input v-model="organizationForm.introduction" type="textarea" :rows="4"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
            </div>
            <div slot="footer" class="tc">
                <Button type="default" @click="handleCancel">取消</Button>
                <Button type="primary" @click.native="handleSubmit">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import {isPhone2} from '~utils/validate'
import organizationCard from './organizationCard'
export default {
    components:{
        organizationCard
    },
    data () {
        return {
            organizationShow:false,
            organizationForm:{
                departmentName:'',
                head:'',
                phone:'',
                introduction:''
            },
            organizationRules:{
                departmentName:[
                    {required:true,message:'请填写部门名称',trigger:'blur'}
                ],
                head:[
                    {required:true,message:'请填写负责人',trigger:'blur'}
                ],
                phone:[
                    {validator:isPhone2,trigger:'blur'}
                ]
            },
            organizationFormItems:[]
        }
    },
    methods:{
        handleAddOrganization () {
            this.organizationShow = true;
            this.organizationForm = {
                departmentName:'',
                head:'',
                phone:'',
                introduction:''
            }
        },
        handleCancel () {
            this.organizationShow = false;
            // this.$refs.organizationForm.resetFields();
        },
        handleSubmit () {
            this.$refs.organizationForm.validate ( (valid) => {
                if(valid) {
                    this.organizationShow = false;
                    console.log(this.organizationForm)
                    this.organizationFormItems.push(this.organizationForm)
                    console.log(this.organizationFormItems)
                }
            })
            
        },
        editOrganization (index) {
            this.organizationShow = true;
            this.organizationForm = this.organizationFormItems[index]
        },
        delOrganization (index) {
            this.organizationFormItems.splice(index,1);
        }

    }
  
}
</script>

<style>

</style>


