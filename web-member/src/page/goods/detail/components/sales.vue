<template>
    <div class="pd20 sales">
        <Form  ref="data" :model="data" label-position="left" :label-width="150">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="productStatus" label="产品状态：">
                        {{data.productStatus}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="productPackaging" label="产品包装：">
                        {{data.productPackaging}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.productPackaging != '否'">
                    <Form-item prop="Packing" label="包装方式：">
                        {{data.Packing}}
                    </Form-item>
                </Col>
            </Row>      
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="netWeight" label="以销售单元为计量单位每单元产品净含量：">
                        <span>{{data.netWeight}}</span>
                        <span v-if="data.netWeight">{{data.netWeightUnits}}</span>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="packageWeight" label="以销售单元为计量单位所用包装重量：">
                        <span>{{data.packageWeight}}</span>
                        <span v-if="data.packageWeight">{{data.packageWeightUnits}}</span>
                    </Form-item>
                </Col>
            </Row>      
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="output" label="产品产量：">
                        <span>{{data.output}}</span>
                        <span v-if="data.output">{{data.outputUnits}}</span>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="productAvailability" label="产品可售量：">
                        <span>{{data.productAvailability}}</span>
                        <span v-if="data.productAvailability">{{data.productAvailabilityUnits}}</span>
                    </Form-item>
                </Col>
            </Row>      
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="productSalesVolume" label="产品起售量：">
                        <span>{{data.productSalesVolume}}</span>
                        <span v-if="data.productSalesVolume">{{data.productSalesVolumeUnits}}</span>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="maximumSingleShipment" label="单次最大供货量：">
                        <span>{{data.maximumSingleShipment}}</span>
                        <span v-if="data.maximumSingleShipment">{{data.maximumUnits}}</span>
                    </Form-item>
                </Col>
            </Row>      
            <Row :gutter="32">
                <Col span="24">
                    <Form-item prop="productLocation" label="产品所在地：">
                        {{data.productLocation}}
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="productOriginAddress" label="产品所在地地址：">
                        {{data.productOriginAddress}}
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="location" label="产品所在地地理位置：">
                        <div>
                            <a target="_blank"  v-if="data.center.lat" :href="`http://api.map.baidu.com/marker?location=${data.center.lat},${data.center.lng}&title=我的位置&content=${location}&output=html`">
                                <img :src="`//api.map.baidu.com/staticimage?height=200&center=${data.center.lng},${data.center.lat}&zoom=15&scale=2&markers=${data.center.lng},${data.center.lat}`" alt="" width="100%">
                            </a>
                        </div>
                    </Form-item>
                </Col>
            </Row>        
        </Form>
    </div>
</template>
<script>
    import $ from 'jquery'
    export default {
        data () {
            return {
                data: {
                    productStatus: '', // 产品状态
                    productPackaging: '', // 产品包装
                    Packing: '', // 包装方式
                    netWeight: '', // 以销售单元为计量单位每单元产品净含量
                    netWeightUnits: '公斤', // 以销售单元为计量单位每单元产品净含量 单位
                    packageWeight: '', // 以销售单元为计量单位所用包装重量
                    packageWeightUnits: '公斤', // 以销售单元为计量单位所用包装重量 单位
                    output: '', // 产品产量
                    outputUnits: '公斤', // 产品产量单位
                    productAvailability: '', // 产品可售量
                    productAvailabilityUnits: '公斤', // 产品可售量单位
                    productSalesVolume: '', // 产品起售量
                    productSalesVolumeUnits: '公斤', // 产品起售量单位
                    maximumSingleShipment: '', // 单次最大供货量
                    maximumUnits: '公斤', // 单次最大供货量单位
                    productLocation: '', // 产品所在地
                    location: '', // 产品产地地理位置
                    productOriginAddress: '', // 产品产地地址
                    center: {}
                },
                location: ''
            }
        },
        methods: {
            getData (val) {
                this.data = val
                this.handleGetlocation(val)
            },
            handleGetlocation (e) {
                this.data = e
                var center = {}                
                this.data.center = center
                center.lng = this.data.location.split(',')[0]
                center.lat = this.data.location.split(',')[1]
                var v = this
                if(this.data.location){
                center.lng = this.data.location.split(',')[0]
                center.lat = this.data.location.split(',')[1] 
                this.data.center = center
                    $.ajax({
                        async : 'false',
                        url : 'http://api.map.baidu.com/geocoder/v2/',
                        type : 'GET',
                        dataType : 'jsonp',
                        jsonpCallback : 'showLocation',
                        timeout : '5000',
                        contentType : 'application/json;utf-8',
                        data : {
                            location: e.location.split(',')[1] + ',' + e.location.split(',')[0],
                            output:'json',
                            ak:'7syPirZ2AWxacMfHeAfuujdDgFmxCB5g',
                            callback:'showLocation'
                        },
                        success : function(data) {
                            v.location = data.result.formatted_address
                        }
                    });
                }
            }
        }
    }
</script>
<style lang="scss">
    /* .sales .ivu-form .ivu-form-item-label{
        line-height: 22px;
    }
    .units .ivu-select-item{
        padding: 7px 8px;
    }
    .ivu-select-group-title{
        text-align: left;
    } */
    // .sales .ivu-input-group-append, .sales .ivu-input-group-prepend{
    //     padding:0px;
    // }
</style>
