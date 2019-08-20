<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" :bordered="false" v-if="item.assets_status">
            <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="150">
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item  prop="name" label="资产名称"> 
                            {{item.name}}
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="assetsType" label="资产类型">
                            {{item.assetsType}}
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="number" label="知识产权编号" v-if="item.assetsType == '知识产权'">
                            {{item.number}}
                        </Form-item>
                    </Col>
                    <Col span="24">
                        <Form-item prop="certificate" label="证书扫描件" v-if="item.assetsType == '知识产权'">
                            <Row  :gutter="8">
                                <Col span="3" v-for="(pic,index) in item.certificate" :key="index" class="pb10">
                                    <div>
                                        <img v-if="pic" :src="pic" width="100%" height="120px">
                                    </div>
                                </Col>
                            </Row>
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="valuation" label="资产估值">
                            {{item.valuation}} <span v-if="item.valuation">万元</span>
                        </Form-item>
                    </Col>
                     <Col span="24">
                        <Form-item prop="assetsExplain" label="资产说明">
                            {{item.assetsExplain}}
                        </Form-item>
                    </Col>
                </Row>
            </Form>
        </Card>

      </Form>
  </div>
</template>
<script>
    export default{
        data () {
            return {
                data: [],
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
