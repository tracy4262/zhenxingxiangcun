<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150" ref="fo">
        <Row :gutter="32">
           <Col span="12">
                <Form-item label="求购信息">
                    <Button type="primary" @click="handleAdd"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
           </Col>
        </Row>
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" :bordered="false">
            <div class="btn-toolbar-card">
                <div class="btn-toolbar">
                    <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                </div>
            </div>
            <!-- <Row>
                <Col span="22"> -->
                <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="82" :rules="ruleInline">
                    <Row :gutter="16">
                        <Col span="24">
                            <Form-item  label="权限">
                                <i-switch  v-model="item.purchase_status" size="large">
                                    <span slot="open">公开</span>
                                    <span slot="close">隐藏</span>
                                </i-switch>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item  prop="name" label="通用商品名">
                                 <!-- <Input  v-model="item.serviceName" class="pinput-clear" icon="ios-close" readonly @on-focus="handleFilterModal('serviceFilter',index)" @on-click="inputClear(index)" />   -->
                                <!-- <Input v-model="item.name"></Input> -->
                                 <Row>
                                    <Col span="17">
                                        <Select
                                            placeholder="支持下拉模糊输入搜索"
                                            v-model="item.name"
                                            filterable
                                            remote
                                            :remote-method="remoteMethod"
                                            :loading="loading" class="mr60" style="width:100%;">
                                            <Option v-for="(option, index) in commonProductNameList" :value="option.label" :key="index">{{option.label}}</Option>
                                            </Select>
                                    </Col>
                                    <Col span="7">
                                        <Button type="primary" v-if="isShowAddBtn" @click="add">添加</Button>
                                        <Button type="primary" v-if="editBtn" @click="modify">补充</Button>  
                                    </Col>
                                </Row>
                                
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="productName" label="产品名称">
                                <Input v-model="item.productName" :maxlength="20"></Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="units" label="产量单位">
                                <Select v-model="item.units"  filterable style="width: 100%" class="units"  placeholder="单位">
                                    <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                                </Select>       
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="total" label="产品数量">
                                <Input :min="1" v-model="item.total"  @on-change="handleTotalAmount('total',index)" style="width:100%;" :maxlength="50"></Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="price" label="产品单价">  
                                <Input v-model="item.price" @on-change="handleTotalAmount('price',index)"  style="width:100%;" :maxlength="50">
                                <span slot="append">元</span>
                                </Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="totalAmount" label="金额">  
                                <Input v-model="item.totalAmount" readonly><span slot="append">元</span></Input>   
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
                <!-- </Col>
                <Col span="2" class="btn-toolbar-card">
                    &nbsp;
                    <div class="btn-toolbar">
                        <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
            </Row> -->
        </Card>
      </Form>
  </div>
</template>
<script>
    import {isNumber, isDecimal2} from '~utils/validate'
    export default {
        data () {
            return {
                data: [],
                ruleInline:{
                    total:[{validator: isNumber, trigger: 'blur'}],
                    price:[{validator: isDecimal2, trigger: 'blur'}]
                },
                index:0,
                submit:true,              
                commonProductNameList: [],
                loading: false,
                isShowAddBtn: false,
                editBtn: false,
                units: []
            }
        },
        created() {
            this.handleGetInit()
        },
        methods: {
              // 取下拉数据
            handleGetInit(){
                this.$api.post('/portal/shopCommdoity/findUnitStandard',{name: ''}).then(response => {
                    if (response.code == 200) {
                        this.units = response.data
                    }
                })
            },
            // 通用商品名 回显时要用到promise函数 先请求到数据才能根据value值回显
           remoteMethod (query) {
                var p = new Promise((resolve, reject) => {
                    if (query !== '') {
                    this.loading = true
                    this.$api.post('/portal/shopCommdoity/findCurrencyCommodity', {
                        name: query
                    }).then(response => {
                        console.log('response', response)
                        if (response.code === 200) {
                            this.loading = false
                            this.commonProductNameList = []
                            response.data.forEach(element => {
                                this.commonProductNameList.push({
                                label: element.commodityName,
                                value: element.id,
                                productCode: element.productCode,
                                productName: element.productName
                                })
                            })
                            resolve('success')
                        } else if (response.code === 300) {
                            this.loading = false
                            this.commonProductNameList = []
                            this.isShowAddBtn = true
                            this.editBtn = false
                            resolve('failed')
                        }
                    }).catch(error => {
                        this.$Message.error('通用商品名查询错误！')
                        resolve('failed')
                    })
                    } else {
                        this.commonProductNameList = []
                        this.isShowAddBtn = true
                        this.editBtn = false
                        resolve('failed')
                    }
                })
                return p
            },
            // 新增通用商品名
            add () {
                this.$router.push('/pro/addCommonProduct')
            },
            modify () {
                this.$router.push({
                    path: '/pro/addCommonProduct',
                    query: {
                    id: this.id
                    }
                })
            },
            getData(val){
                this.data = val
            },
            // 表单验证
            handleSubmit () {
                var data = this.data
                this.submit = true
                for(var i = 0 ;i < data.length ; i++){
                    this.$refs[`corpInfo${i}`][0].validate((valid)=>{
                        if(!valid){
                           this.submit = false
                        }
                    })
                }
                this.$emit('on-submit',this.submit)
            },
            //计算总金额
            handleTotalAmount(type, index){
                var corpInfo = `corpInfo${index}`
                var  total= this.data[index].total
                var  price= this.data[index].price
                this.$refs[corpInfo][0].validate((valid) => {
                    if(valid){
                        let all = total*price
                       this.data[index].totalAmount = all.toFixed(2)
                    }
                })                
            },
            //增加
            handleAdd () {
                this.data.unshift(
                    {
                        purchase_status: true,
                        name: '',
                        productName: '',
                        total: '',
                        price: '',
                        totalAmount: '',
                        units: ''
                    }
                )
                console.log(this.data)
            },
            //删除
            handleDel (index) {
                this.$Modal.confirm({
                    title: '是否确定删除',
                    content: '是否确认删除？',
                    onOk:()=>{
                        this.data.splice(index,1)
                    },
                    okText:'确定',
                    cancelText:'取消'
                });
            },
        }
    }
</script>
