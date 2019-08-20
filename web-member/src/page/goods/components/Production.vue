<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="whether" label="是否投入品或原料">
                        <Select v-model="data.whether" style="width: 100%">
                            <Option v-for="item in whethers" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.whether == '是'">
                    <Form-item prop="rawMaterial" label="投入品或原料">
                        <Input v-model="data.rawMaterial" :maxlength="50"></Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="productionMethods" label="生产方式">
                        <Select v-model="data.productionMethods" style="width: 100%">
                            <Option v-for="item in productionMethodss" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="roughProcessing" label="粗加工方式">
                        <Select v-model="data.roughProcessing" style="width: 100%">
                            <Option v-for="item in roughProcessings" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="transgenic" label="是否转基因">
                        <Select v-model="data.transgenic" style="width: 100%">
                            <Option v-for="item in whethers" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="irradiation" label="是否辐照">
                        <Select v-model="data.irradiation" style="width: 100%">
                            <Option v-for="item in whethers" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
            </Row>        
        </Form>
    </div>
</template>
<script>
    export default {
        data () {
            return {
                data: {
                    whether: '', // 是否投入品或原料
                    rawMaterial: '', // 投入品或原料
                    productionMethods: '', // 生产方式
                    roughProcessing: '', // 粗加工方式
                    transgenic: '', // 是否转基因
                    irradiation: '' //是否辐照
                },
                ruleInline: {
                },
                productionMethodss:[
                    {label: '人工种植', value: '人工种植'}
                ],
                whethers:[
                    {label: '是', value: '是'},
                    {label: '否', value: '否'}
                ],
                roughProcessings: [
                    {label: '无加工', value: '无加工'},
                    {label: '打蜡', value: '打蜡'},
                    {label: '清洗', value: '清洗'}
                ]                
            }
        },
        methods:{
            getData (val) {
                this.data = val 
            },
            handleSubmit () {
                this.$refs['data'].validate((valid) => {
                    if(valid) {                        
                        this.$emit('on-submit',true)
                    }else{
                        this.$emit('on-submit',false)
                    }
                })
            }
        }
    }
</script>
<style>

</style>
