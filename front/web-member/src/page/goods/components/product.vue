<template>
<div class="pt30 pl10 pr10">
    <Form ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
        <Row :gutter="32">
            <Col span="12">
            <Form-item prop="productName" label="商品名">
                <Input v-model="data.productName" :maxlength="50"></Input>
            </Form-item>
            </Col>
            <Col span="12">
            <Form-item prop="productBrand" label="产品品牌">
                <Input v-model="data.productBrand" :maxlength="20"></Input>
            </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
            <Form-item prop="ProductNumber" label="产品批次号">
                <Input v-model="data.ProductNumber" :maxlength="20"></Input>
            </Form-item>
            </Col>
            <Col span="12">
            <Form-item prop="productSource" label="产品来源">
                <Select v-model="data.productSource" style="width: 100%">
                            <Option v-for="item in productSources" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
            </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
            <Form-item prop="provenanceCharacteristics" label="种源特性">
                <Select v-model="data.provenanceCharacteristics" style="width: 100%">
                            <Option v-for="item in provenanceCharacteristicss" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
            </Form-item>
            </Col>
            <Col span="12">
            <Form-item prop="isFresh" label="是否鲜活">
                <Select v-model="data.isFresh" style="width: 100%">
                            <Option v-for="item in isFreshs" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
            </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
                <Form-item prop="productModel" label="产品型号">
                    <Input v-model="data.productModel" :maxlength="20"></Input>
                </Form-item>
            </Col>
        </Row>
        <div v-if="show">
            <Row :gutter="32">
                <Col span="24">
                    <Form-item prop="pestData" label="适用虫害">
                        <Select v-model="data.pestData" multiple 
                            filterable @on-change="pestDataChange" placeholder="最多选择5条">
                            <Option v-for="(option, index) in pestDatas" :value="option.fid" :key="index">{{option.fname}}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="instructions" label="适用病害">
                        <Select v-model="data.diseaseData" multiple 
                            filterable @on-change="diseaseDataChange" placeholder="最多选择5条">
                            <Option v-for="(option, index) in diseaseDatas" :value="option.fid" :key="index">{{option.fname}}</Option>
                        </Select>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="24">
                    <Form-item prop="instructions" label="使用说明">
                        <Input v-model="data.instructions" type="textarea"  :autosize="{minRows: 3,maxRows: 4}" :maxlength="200"></Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="24">
                    <Form-item prop="storageMethod" label="储藏方法">
                        <Input v-model="data.storageMethod" type="textarea"  :autosize="{minRows: 3,maxRows: 4}" :maxlength="50"></Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="24">
                    <Form-item prop="toxicityMark" label="毒性标志">
                        <Input v-model="data.toxicityMark" type="textarea"  :autosize="{minRows: 3,maxRows: 4}" :maxlength="50"></Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="24">
                    <Form-item prop="reminder" label="重要提醒">
                        <Input v-model="data.reminder" type="textarea"  :autosize="{minRows: 3,maxRows: 4}" :maxlength="200"></Input>
                    </Form-item>
                </Col>
            </Row>
        </div>
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
                isFresh: '', // 是否鲜活
                productModel: '', // 产品型号
                pestData: [], // 虫害
                diseaseData: [], // 病害
                instructions: '', //使用说明
                storageMethod: '', // 储藏方法
                toxicityMark: '', //毒性标志
                reminder: '', //重要提醒
            },
            ruleInline: {
                productName: [{
                    required: true,
                    message: '请输入商品名称',
                    trigger: 'blur'
                }]
            },
            productSources: [{
                    label: '自产自销',
                    value: '自产自销'
                },
                {
                    label: '外购',
                    value: '外购'
                }
            ],
            isFreshs: [{
                    label: '是',
                    value: '是'
                },
                {
                    label: '否',
                    value: '否'
                }
            ],
            provenanceCharacteristicss: [{
                    label: '常规品种',
                    value: '常规品种'
                },
                {
                    label: '杂交品种',
                    value: '杂交品种'
                },
                {
                    label: '转基因品种',
                    value: '转基因品种'
                }
            ],
            pestDatas: [],
            diseaseDatas: [],
            show: true,
            speciesid: '',
            productType: ''
        }
    },
    created() {
        // 如果是无形商品 开头是CP09 的则不显示
        this.productType = this.$route.query.productType
        if (this.productType.substring(0,4) == 'CP09') {
            this.show = false
        }
        // 根据物种id 获取病害虫害的数据
        this.speciesid = this.$route.query.speciesid
        // 虫害
        this.$api.post('/wiki/api/wiki/getPestList', {speciesid: this.speciesid}).then(response => {
            if (response.code === 200) {
                this.pestDatas = response.data
            }
        })
        // 病害
        this.$api.post('/wiki/api/wiki/getDiseaseList', {speciesid: this.speciesid}).then(response => {
            if (response.code === 200) {
                this.diseaseDatas = response.data
            }
        })
    },
    methods: {
        // 虫害 最多只能选择5个，再选择则删除前面的只取最后面选择的5个
        pestDataChange () {
            if (this.data.pestData.length > 5) {
                this.data.pestData = this.data.pestData.slice(1)
            }
        },
        // 病害 最多只能选择5个，再选择则删除前面的只取最后面选择的5个
        diseaseDataChange () {
            if (this.data.diseaseData.length > 5) {
                this.data.diseaseData = this.data.diseaseData.slice(1)
            }
        },
        getData(val) {
            this.data = Object.assign(this.data, val)
        },
        handleSubmit() {
            this.$refs['data'].validate((valid) => {
                if (valid) {
                    this.$emit('on-submit', true)
                } else {
                    this.$emit('on-submit', false)
                }
            })
        }
    }
}
</script>
<style>

</style>
