<template>
  <div  class="pt30 pl10 pr10 asset-finance"> 
    <Form  ref="data" :model="data" label-position="left" :label-width="150" v-if="data.finance_status">
        <Row :gutter="32">
           <Col span="12">
                <Form-item prop="interests" label="所有者权益">
                   {{data.interests}} <span v-if="data.interests">万元</span>
                </Form-item>
           </Col>
           <Col span="12">
                <Form-item prop="assets" label="固定资产规模">
                   {{data.assets}} <span v-if="data.assets">万元</span>
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
                    interests:'',
                    assets:''
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
<style lang="scss">
</style>
