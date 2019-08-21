<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" v-if="item.product_status">
            <!-- <Row>
                <Col span="22"> -->
                <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="82" :rules="ruleInline">
                    <Row :gutter="16">
                        <Col span="8">
                            <Form-item  prop="species" label="物种">
                                {{item.species}}
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="scale" label="生产规模">
                                {{item.scale}}
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="commodityName" label="通用商品名">
                                {{item.commodityName}}
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
                            <Form-item prop="product" label="产品产量">
                                {{item.product}}                    
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="conversion" label="折算产量">
                                {{item.conversion}}
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
        </Card>
      </Form>
  </div>
</template>
<script>
	import vuiFilter from '~components/vuiFilter/filter'
    export default{
        components: {
            vuiFilter
        },
        data () {
            return {
                data: [],
                ruleInline:{

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
            }
        }
    }
</script>
