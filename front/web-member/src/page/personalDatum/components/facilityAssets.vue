<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Card v-for="(item , index) in data" :key="index" class="mb20  card" :bordered="false" v-if="item.assets_status">
            <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="150" >
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
                        <Form-item prop="model" label="品牌型号">
                            {{item.model}}
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="purchaseTime" label="采购时间" >
                            <span v-if="item.purchaseTime">{{moment(item.purchaseTime).format('YYYY/MM/DD')}}</span>
                        </Form-item>
                    </Col>
                    <Col span="24">
                        <Form-item prop="assetPicture" label="资产图片">
                            <Row :gutter="8">
                                <Col span="3" v-for="(pic,index) in item.assetPicture" :key="index"  class="pb10">
                                    <div>
                                        <img v-if="pic" :src="pic" width="100%" height="120px">
                                    </div>
                                </Col>
                            </Row>
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="originalValue" label="资产原值">
                            {{item.originalValue}} <span v-if="item.originalValue">万元</span>
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="depreciation" label="年折旧率">
                            {{item.depreciation}} <span v-if="item.depreciation">%</span>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="netAssetValue" label="资产净值">
                            {{item.netAssetValue}} <span v-if="item.netAssetValue">万元</span>
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
            },
        }
    }
</script>
