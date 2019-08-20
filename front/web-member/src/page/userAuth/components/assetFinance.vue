<template>
  <div  class="pt30 pl10 pr10 asset-finance"> 
    <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
        <Row :gutter="32">
            <Col span="24">
            <FormItem label="权限">
                <i-switch  v-model="data.finance_status" size="large">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                </i-switch>
            </FormItem>
            </Col>
        </Row>
        <Row :gutter="32">
           <Col span="12">
                <Form-item prop="interests" label="所有者权益">
                    <Input v-model="data.interests">
                        <span slot="append" @on-change="changeInterests">万元</span>
                    </Input>
                </Form-item>
           </Col>
           <Col span="12">
                <Form-item prop="assets" label="固定资产规模">
                    <Input v-model="data.assets" @on-change="changeAssets">
                        <span slot="append">万元</span>
                    </Input>
                </Form-item>
           </Col>
        </Row>        
    </Form>
    <Form  label-position="left" :label-width="150" class="pb20" v-if="more">
        <Row :gutter="32">
            <Col span="12">
                <Form-item label="银行授信">
                    <Button type="primary" @click="handleAddBankCredit"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
            </Col>
        </Row>
        <display-card v-for="(item,index) in bankCreditFormItems" 
            :index="index" 
            :key="index" 
            :data="item" 
            :labelList="{name:'银行名称',leftLabel:'授信额度',rightLabel:'有效期'}"
            @on-edit="editBankCredit"
            @on-del="delBankCredit">
        </display-card>
    </Form>
    <Form  label-position="left" :label-width="150" class="pb20" v-if="more">
        <Row :gutter="32">
            <Col span="12">
                <Form-item label="无形资产">
                    <Button type="primary"  @click="handleAddAassets"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
            </Col>
        </Row>
        <display-card v-for="(item,index) in assetsFormItems" 
            :index="index" 
            :key="index" 
            :data="item" 
            :labelList="{name:'银行名称',leftLabel:'估值',rightLabel:''}"
            @on-edit="editAassets"
            @on-del="delAassets">
        </display-card>
    </Form>
     <Form  label-position="left" :label-width="150" class="pb20" v-if="more">
        <Row :gutter="32">
            <Col span="12">
                <Form-item label="银行账户">
                    <Button type="primary"  @click="handleAddBankAccount"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
            </Col>
        </Row>
        <display-card v-for="(item,index) in bankAccountFormItems" 
            :index="index" 
            :key="index" 
            :data="item" 
            :labelList="{name:'开户银行',leftLabel:'银行行号',rightLabel:'开户行账号'}"
            @on-edit="editBankAccount"
            @on-del="delBankAccount">
        </display-card>
    </Form>
    <Modal
        v-model="bankCredit"
        title="新增银行授信"
        :mask-closable="false">
        <div class="pd20">
            <Form  ref="bankCreditFormItem" :model="bankCreditFormItem" label-position="left" :label-width="100" :rules="bankCreditFormItemInline">
                <Form-item prop="name" label="银行名称" >
                    <Input v-model="bankCreditFormItem.name" :maxlength="20">
                    </Input>
                </Form-item>
                <Form-item prop="leftValue" label="授信额度">
                    <Input v-model="bankCreditFormItem.leftValue" :maxlength="20">
                        <span slot="append">万元</span>
                    </Input>
                </Form-item>
                <Form-item prop="rightValue" label="期限">
                     <DatePicker v-model="bankCreditFormItem.rightValue" clearable :editable="false" format="yyyy/MM/dd" type="daterange" placement="bottom-end"  style="width: 100%;"></DatePicker>
                </Form-item>
            </Form>
        </div>
        <div slot="footer" class="tc">
            <Button type="default" @click="bankCredit = false">取消</Button>
            <Button type="primary" @click.native="bankCreditOk">确定</Button>
        </div>
    </Modal>
     <Modal
        v-model="assets"
        title="新增无形资产"
        :mask-closable="false">
        <div class="pd20">
            <Form  ref="assetsFormItem" :model="assetsFormItem" label-position="left" :label-width="100" :rules="assetsFormItemInline">
                <Form-item prop="name" label="名称">
                    <Input v-model="assetsFormItem.name" :maxlength="20">
                    </Input>
                </Form-item>
                <Form-item prop="leftValue" label="估值">
                    <Input v-model="assetsFormItem.leftValue" :maxlength="20">
                        <span slot="append">万元</span>
                    </Input>
                </Form-item>
            </Form>
        </div>
        <div slot="footer" class="tc">
            <Button type="default" @click="assets = false">取消</Button>
            <Button type="primary" @click.native="assetsOk">确定</Button>
        </div>
    </Modal>
     <Modal
        v-model="bankAccount"
        title="新增银行账户"
        :mask-closable="false">
        <div class="pd20">
            <Form  ref="bankAccountFormItem" :model="bankAccountFormItem" label-position="left" :label-width="100" :rules="bankAccountFormItemInline">
                <Form-item prop="name" label="开户银行">
                    <Input v-model="bankAccountFormItem.name" :maxlength="30"></Input>
                </Form-item>
                <Form-item prop="leftValue" label="银行行号">
                    <Input v-model="bankAccountFormItem.leftValue" :maxlength="19"></Input>
                </Form-item>
                <Form-item prop="rightValue" label="开户账号">
                    <Input v-model="bankAccountFormItem.rightValue" :maxlength="19"></Input>
                </Form-item>
            </Form>
        </div>
        <div slot="footer" class="tc">
            <Button type="default" @click="bankAccount = false">取消</Button>
            <Button type="primary" @click.native="bankAccountOk">确定</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
import displayCard from './displayCard'
import {isDecimal2,isBankCard,isNumber} from '~utils/validate'
    export default{
        components:{
            displayCard
        },
        props:{
            more:{
                type: Boolean,
                default:false
            }
        },
        data(){
            return{
                data:{
                    finance_status: true,
                    interests:'',
                    assets:''
                },
                ruleInline:{
                    interests:[
                        { trigger:'blur',validator:isDecimal2 }
                    ],
                    assets:[
                        { trigger:'blur',validator:isDecimal2}
                    ]
                },
                bankCredit:false,
                bankCreditFormItem:{
                    rightValue:[],
                    name:'',
                    leftValue:''
                },
                bankCreditFormItemInline:{
                    rightValue:[{required: true, type: 'array',message: '请选择期限',trigger: 'blur' }],
                    name:[{required: true,message: '请填写银行名称', trigger: 'blur' }],
                    leftValue:[{required: true, message: '请填写授信额度',trigger: 'blur' },{trigger:'blur',validator:isNumber}]
                },
                bankCreditFormItems:[],
                assets:false,
                assetsFormItem:{
                    name:'',
                    leftValue:''
                },
                assetsFormItemInline:{
                    name:[{required: true, message: '请填写名称',trigger: 'blur' }],
                    leftValue:[{required: true, message: '请填写估值',trigger: 'blur' },{trigger:'blur',validator:isNumber}]
                },
                assetsFormItems:[],
                bankAccount:false,
                bankAccountFormItem:{
                    rightValue:'',
                    name:'',
                    leftValue:''
                },
                bankAccountFormItemInline:{
                    rightValue:[{required: true, message: '请填写开户银行',trigger: 'blur' }],
                    name:[{required: true, message: '请填写银行行号',trigger: 'blur' },{trigger:'blur',validator:isBankCard}],
                    leftValue:[{required: true, message: '请填写开户账号',trigger: 'blur' },{trigger:'blur',validator:isBankCard}]
                },
                bankAccountFormItems:[]
            }
        },
        methods:{
            //转string
            changeInterests(){
                this.data.interests = this.data.interests + ''
            }, 
            //转string
            changeAssets(){
                this.data.assets = this.data.assets + ''
            },          
            //接收数据
			getData(val){
                this.data = val
                console.log('interests',this.data)
			},
            // 银行账户部分
            handleAddBankAccount(){
                this.bankAccountFormItem ={
                    rightValue:'',
                    bankName:'',
                    leftValue:''
                }
                this.bankAccount = true
            },
            // 增加银行账户确认
            bankAccountOk(){
                this.$refs['bankAccountFormItem'].validate((valid) => {
                    if (valid) {
                        this.bankAccountFormItems.push(this.bankAccountFormItem)
                        this.bankAccount = false
                    }
                })
            },
            //编辑银行账户
            editBankAccount(index){
                this.handleAddBankAccount()
                this.bankAccountFormItem = this.bankAccountFormItems[index]
            },
            //删除银行账户
            delBankAccount(index){
                this.bankAccountFormItems.splice(index,1)
            },
            // 无形资产部分
            assetsOk(){
                this.$refs['assetsFormItem'].validate((valid) => {
                    if (valid) {
                        this.assetsFormItems.push(this.assetsFormItem)
                        this.assets = false
                    }
                })
            },
            //增加无形资产
            handleAddAassets(){
                this.assetsFormItem = {
                    name:'',
                    leftValue:''
                }
                this.assets = true
            },
            //编辑无形资产的回调
            editAassets(index){
                this.handleAddAassets()
                this.assetsFormItem = this.assetsFormItems[index]
            },
            //删除无形资产的回调
            delAassets(index){
                this.assetsFormItems.splice(index,1)
            },

            //编辑银行授信的回调
            editBankCredit(index){
                this.handleAddBankCredit()
                this.bankCreditFormItem = this.bankCreditFormItems[index]
            },
            //删除银行授信的回调
            delBankCredit(index){
                this.bankCreditFormItems.splice(index,1)
            },
            // 增加银行授信
            handleAddBankCredit(){
                this.bankCreditFormItem = {
                    rightValue:[],
                    bankName:'',
                    leftValue:''
                },
                this.bankCredit = true
            },
            // 新增银行授信确认
            bankCreditOk(){
                this.$refs['bankCreditFormItem'].validate((valid) => {
                    if (valid) {
                        this.bankCreditFormItems.push(this.bankCreditFormItem)
                        this.bankCredit = false
                    }
                })
            },
            //点击下一步的时候表单验证
            handleSubmit(){
                this.$refs['data'].validate((valid) => {
                    if(valid) {                        
                        this.$emit('on-submit',true)
                    }else{
                        this.$emit('on-submit',false)
                    }
                })
            },
        }
    }

</script>
<style lang="scss">
</style>
