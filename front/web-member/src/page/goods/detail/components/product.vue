<template>
<div class="pd20">
    <Form ref="data" :model="data" label-position="left" :label-width="150">
        <Row :gutter="32">
            <Col span="12">
            <Form-item label="商品名："> {{data.productName}} </Form-item>
            </Col>
            <Col span="12">
            <Form-item label="产品品牌："> {{data.productBrand}} </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
                <Form-item label="产品批次号："> {{data.ProductNumber}} </Form-item>
            </Col>
            <Col span="12">
            <Form-item label="产品来源："> {{data.productSource}} </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
            <Form-item label="种源特性："> {{data.provenanceCharacteristics}} </Form-item>
            </Col>
            <Col span="12">
            <Form-item label="是否鲜活："> {{data.isFresh}} </Form-item>
            </Col>
        </Row>
         <Row :gutter="32">
            <Col span="12">
            <Form-item label="产品型号："> {{data.productModel}} </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="24">
            <Form-item label="适用虫害：">
                <span v-for="(item, index) in data.pestTextData ">
                    {{item }}{{index+1 === data.pestTextData .length ? '。' : '、'}}
                </span>
            </Form-item>
            </Col>
        </Row>
         <Row :gutter="32">
            <Col span="24">
            <Form-item label="适用病害：">
                 <span v-for="(item, index) in data.diseaseTextData">
                    {{item }}{{index+1 === data.diseaseTextData.length ? '。' : '、'}}
                </span>
            </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="24">
                <Form-item label="使用说明：">{{data.instructions}}</Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="24">
                <Form-item label="储藏方法：">{{data.storageMethod}}</Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="24">
                <Form-item label="毒性标志：">{{data.toxicityMark}}</Form-item>
            </Col>
        </Row>
         <Row :gutter="32">
            <Col span="24">
                <Form-item label="重要提醒：">{{data.reminder}}</Form-item>
            </Col>
        </Row>
    </Form>
</div>
</template>
<script>
export default {
    data() {
        return {
            data: {
                productName: '', // 商品名
                productBrand: '', // 产品品牌
                ProductNumber: '', // 产品批次号
                productSource: '', // 产品来源
                provenanceCharacteristics: '', // 种源特性
                isFresh: '' // 是否鲜活
            }
        }
    },
    methods: {
        getData(val) {
            this.data = val
        }
    }
}
</script>
<style>

</style>
