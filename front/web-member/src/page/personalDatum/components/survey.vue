<template>
  <div class="pt30 pl10 pr10"> 
    <Form  ref="data" :model="data" label-position="left" :label-width="150" v-if="data.manage_status">
        <Row :gutter="32">
           <Col span="12">
                <Form-item prop="scale" label="企业规模">
                    {{data.scale}}
                </Form-item>
           </Col>
           <Col span="12">
                <Form-item prop="industry" label="所属行业">
                    {{data.industry}}
                </Form-item>
           </Col>
        </Row>
        <Row :gutter="32">
           <Col span="12">
                <Form-item prop="turnover" label="上年度营业收入">
                    {{data.turnover}} <span v-if="data.turnover">万元</span>
                </Form-item>
           </Col>
           <Col span="12">
                <Form-item prop="JointStockCode" label="股份代码">
                    {{data.JointStockCode}}
                </Form-item>
           </Col>
        </Row>
    </Form>
  </div>
</template>
<script>
    export default{
        data(){
            return{
                data:{
                    scale:'',
                    industry:'',
                    turnover:'',
                    JointStockCode:''
                },
            }
        },
        methods:{
            //接收数据
			getData(val){
                this.data = val
            }
        }
    }

</script>
<style>

</style>
