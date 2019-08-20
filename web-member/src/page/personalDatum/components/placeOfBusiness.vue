<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" :bordered="false" v-if="item.assets_status">
            <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="150" >
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item  prop="place" label="处所">                                
                           {{item.place}}
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="rightType" label="权利类型">
                           {{item.rightType}}
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="area" label="场所面积">
                            {{item.area}} <span v-if="item.area">平方米</span>
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="purpose" label="用途" >
                            {{item.purpose}}
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="certificateNumber" label="不动产证号" v-if="item.rightType == '自购'">
                            {{item.certificateNumber}}
                        </Form-item>
                    </Col>
                    <Col span="24">
                        <Form-item prop="certificate" label="不动产证扫描件" v-if="item.rightType == '自购'">
                            <Row>
                                <Col span="8" v-for="(pic,index) in item.certificate" :key="index">
                                    <div>
                                        <img v-if="pic" :src="pic" width="100%" height="120px">
                                    </div>
                                </Col>
                            </Row>
                        </Form-item>
                    </Col>
                     <Col span="24">
                        <Form-item prop="contract" label="租赁合同扫描件">
                            <Row :gutter="8">
                                <Col span="3" v-for="(pic,index) in item.contract" :key="index" class="pb10">
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
    import vuiFilter from '~components/vuiFilter/filter'
    import vuiUpload from '~components/vui-upload'
    import {isDecimal2} from '~utils/validate'
    export default{
        components: {
            vuiFilter,
            vuiUpload
        },
        props:{
            isAdd:{
                type: Boolean,
                default:false
            }
        },
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
