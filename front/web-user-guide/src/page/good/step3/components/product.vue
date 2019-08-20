<template>
<div class="pt30 pl10 pr10">
    <Form ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
        <Row :gutter="32">
            <Col span="12">
                <Form-item prop="productName" label="产品商品名">
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
                <Form-item prop="productModel" label="产品型号">
                    <Input v-model="data.productModel" :maxlength="20"></Input>
                </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
                <Form-item prop="productSource" label="产品来源">
                    <Select v-model="data.productSource" style="width: 100%">
                        <Option v-for="item in productSources" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </Form-item>
            </Col>
            <Col span="12" v-if="categoryId == 'CP01'">
                <Form-item prop="provenanceCharacteristics" label="种源特性">
                    <Select v-model="data.provenanceCharacteristics" style="width: 100%">
                        <Option v-for="item in provenanceCharacteristicss" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </Form-item>
            </Col>
        </Row>
        <Row :gutter="32" v-if="categoryId == 'CP01' || categoryId == 'CP05' || categoryId == 'CP06'">
            <Col span="12">
                <Form-item prop="transgenic" label="是否转基因">
                    <Select v-model="data.transgenic" style="width: 100%">
                        <Option v-for="item in isFreshs" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </Form-item>
            </Col>
            <Col span="12">
                <Form-item prop="irradiation" label="是否辐照">
                    <Select v-model="data.irradiation" style="width: 100%">
                        <Option v-for="item in isFreshs" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12" v-if="categoryId == 'CP01' || categoryId == 'CP04' || categoryId == 'CP05'|| categoryId == 'CP06' || categoryId == 'CP07' || categoryId == 'CP08'">
                <Form-item prop="markCertificate" label="标识证书">
                    <!-- 绿色/有机/无公害/地标 -->
                    <CheckboxGroup v-model="data.markCertificate" @on-change="handleGiveImage">
                        <Checkbox label="绿色" ></Checkbox>
                        <Checkbox label="有机" ></Checkbox>
                        <Checkbox label="无公害"></Checkbox>
                        <Checkbox label="地标"></Checkbox>
                    </CheckboxGroup>
                </Form-item>
            </Col>
            <Col span="12" v-if="categoryId == 'CP05' || categoryId == 'CP06'">
                <Form-item prop="isFresh" label="是否鲜活">
                    <Select v-model="data.isFresh" style="width: 100%">
                        <Option v-for="item in isFreshs" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </Form-item>
            </Col>
        </Row>
        <Row :gutter="32" v-if="categoryId == 'CP01' || categoryId == 'CP04' || categoryId == 'CP05'|| categoryId == 'CP06' || categoryId == 'CP07' || categoryId == 'CP08'">
            <Col span="24"v-for="(item, index) in data.markCertificate">
                <Form-item prop="markGreen" label="绿色标识证书" v-if="item == '绿色'">
                    <vuiUpload
                        ref="markGreen"
                        @on-getPictureList="getMarkGreenList"
                        :total="10"
                        ></vuiUpload>
                </Form-item>
                <Form-item prop="markOrganic" label="有机标识证书"  v-if="item == '有机'">
                    <vuiUpload
                        ref="markOrganic"
                        @on-getPictureList="getMarkOrganicList"
                        :total="10"
                        ></vuiUpload>
                </Form-item>
                <Form-item prop="harmless" label="无公害标识证书" v-if="item == '无公害'">
                    <vuiUpload
                        ref="harmless"
                        @on-getPictureList="getHarmlessList"
                        :total="10"
                        ></vuiUpload>
                </Form-item>
                <Form-item prop="landmark" label="地标标识证书" v-if="item == '地标'">
                    <vuiUpload
                        ref="landmark"
                        @on-getPictureList="getLandmarkList"
                        :total="10"
                        ></vuiUpload>
                </Form-item>
            </Col>
            <!-- <Col span="24">
                <Form-item prop="markOrganic" label="有机标识证书">
                    <vuiUpload
                        ref="markOrganic"
                        @on-getPictureList="getMarkOrganicList"
                        :total="10"
                        ></vuiUpload>
                </Form-item>
            </Col>
            <Col span="24">
                <Form-item prop="harmless" label="无公害标识证书">
                    <vuiUpload
                        ref="harmless"
                        @on-getPictureList="getHarmlessList"
                        :total="10"
                        ></vuiUpload>
                </Form-item>
            </Col>
            <Col span="24">
                <Form-item prop="landmark" label="地标标识证书">
                    <vuiUpload
                        ref="landmark"
                        @on-getPictureList="getLandmarkList"
                        :total="10"
                        ></vuiUpload>
                </Form-item>
            </Col> -->
        </Row>
        <Row :gutter="32">
            <Col span="24">
                <Form-item prop="instructions" label="使用说明">
                    <Input v-model="data.instructions" type="textarea"  :autosize="{minRows: 3,maxRows: 4}" :maxlength="300"></Input>
                </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="24">
                <Form-item prop="reminder" label="重要提醒">
                    <Input v-model="data.reminder" type="textarea"  :autosize="{minRows: 3,maxRows: 4}" :maxlength="300"></Input>
                </Form-item>
            </Col>
        </Row>
        <view-panel
            title="添加自定义字段"
            :data="data.customData"
            @on-data="handleGetSafeForm"
            @on-add="handleAddBtn"></view-panel>
    </Form>
        <!-- 添加面板 -->
        <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
</div>
</template>
<script>
import vuiUpload from '~components/vui-upload'
import addPanel from '../../../goods/components/vui-form-control/add-panel'
import viewPanel from '../../../goods/components/vui-form-control/view-panel'
export default {
    components: {
        vuiUpload,
        addPanel,
        viewPanel
    },
    data() {
        return {
            data: {
                productName: '', // 产品商品名
                productBrand: '', // 产品品牌
                ProductNumber: '', // 产品批次号
                productSource: '', // 产品来源
                productModel: '', // 产品型号
                isFresh: '', // 是否鲜活
                transgenic: '', // 是否转基因
                irradiation: '', //是否辐照
                markCertificate: [], // 标识证书
                markGreen: [], // 绿色标识证书
                markOrganic: [], // 有机标识证书
                harmless: [], // 无公害标识证书
                landmark: [], // 地标标识证书
                provenanceCharacteristics: '', // 种源特性
                instructions: '', //使用说明
                reminder: '', //重要提醒
                customData: [], // 自定义
            },
            ruleInline: {
                productName: [{
                    required: true,
                    message: '请输入商品名称',
                    trigger: 'blur'
                }],
                productSource: [{
                    required: true,
                    message: '请选择产品来源',
                    trigger: 'change'
                }],
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
            productType: '',
            categoryId: '',
            templateId: '',
            templateType: '',
        }
    },
    created() {
        this.categoryId = this.$route.query.categoryId
        this.templateId = this.$route.query.templateId
        this.templateType = this.$route.query.templateType
    },
    methods: {
        // 添加组件
        handleControlBtn (data) {
            this.data.customData.push(data)
        },
        handleAddBtn () {
            this.$refs.addPanel.showAddPanel = true
        },
        // 取商品安全数据
        handleGetSafeForm (data) {
        console.log(data)
        },
        // 证书标识 绿色
        getMarkGreenList (e) {
            var arr = []
            e.forEach(element => {
                    if(element.response){
                    arr.push(element.response.data.picName)
                }
            })
            this.data.markGreen = arr
        },
        // 有机标识证书
        getMarkOrganicList (e) {
            var arr = []
            e.forEach(element => {
                    if(element.response){
                    arr.push(element.response.data.picName)
                }
            })
            this.data.markOrganic = arr
        },
        // 无公害标识证书
        getHarmlessList (e) {
            var arr = []
            e.forEach(element => {
                    if(element.response){
                    arr.push(element.response.data.picName)
                }
            })
            this.data.harmless = arr
        },
        // 地标标识证书
        getLandmarkList (e) {
            var arr = []
            e.forEach(element => {
                    if(element.response){
                    arr.push(element.response.data.picName)
                }
            })
            this.data.landmark = arr
        },
        getData(val) {
            console.log('product', val)
            this.data = Object.assign(this.data, val)
            this.handleGiveImage()
        },
        handleGiveImage () {
            this.$nextTick(() => {
                if (this.$refs['markGreen']) {
                    this.$refs['markGreen'][0].handleGive(this.data.markGreen)
                }
                if (this.$refs['markOrganic']) {
                    this.$refs['markOrganic'][0].handleGive(this.data.markOrganic)
                }
                if (this.$refs['harmless']) {
                    this.$refs['harmless'][0].handleGive(this.data.harmless)
                }
                if (this.$refs['landmark']) {
                    this.$refs['landmark'][0].handleGive(this.data.landmark)
                }
            })
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
