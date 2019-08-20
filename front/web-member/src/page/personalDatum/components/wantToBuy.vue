<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150" ref="fo">
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" v-if="item.purchase_status">
            <!-- <Row>
                <Col span="22"> -->
                <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="82" :rules="ruleInline">
                    <Row :gutter="16">
                        <Col span="8">
                            <Form-item  prop="name" label="通用商品名">
                                {{item.name}}
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="productName" label="产品名称">
                                {{item.productName}}
                            </Form-item>
                        </Col>
                         <Col span="8">
                            <Form-item prop="units" label="产量单位">
                                {{item.units}}
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="total" label="产品数量">
                                {{item.total}}
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="price" label="产品单价">  
                                {{item.price}}
                                <span v-if="item.price">元</span>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="totalAmount" label="金额">  
                                {{item.totalAmount}}
                                <span v-if="item.totalAmount">元</span>
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
        </Card>
      </Form>
  </div>
</template>
<script>
    // import vuiFilter from '~components/vuiFilter/filter'
    // import vuiUpload from '~components/vui-upload'
    import {isNumber, isDecimal2} from '~utils/validate'
    export default {
        components: {
            // vuiFilter,
            // vuiUpload
        },
        // props: {
        //     datas: {
        //         type: Array,
        //         default: ()=>{
        //             return [
        //                 {
        //                     name: '',
        //                     productName: '',
        //                     total: '',
        //                     price: '',
        //                     totalAmount: '',
        //                 }
        //             ]
        //         }
        //     }
        // },
        data () {
            return {
                data: [
                    {
                        purchase_status: true,
                        name: '',
                        productName: '',
                        total: '',
                        price: '',
                        totalAmount: '',
                    }
                ],
                ruleInline:{
                    total:[{validator: isNumber, trigger: 'blur'}],
                    price:[{validator: isDecimal2, trigger: 'blur'}]
                },
                index:0,
                submit:true
            }
        },
        created() {
        },
        methods: {
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
                var  total= this.data[this.index].total
                var  price= this.data[this.index].price
                this.$refs[corpInfo][0].validate((valid) => {
                    if(valid){
                       this.data[this.index].totalAmount = total*price
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
