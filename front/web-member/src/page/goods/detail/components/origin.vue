<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="productOrigin" label="产品产地">
                        <vui-cascander :values="data.productOrigin" @handle-get-result="handleGetData"></vui-cascander>
                        <!-- <Input v-model="data.productOrigin" :maxlength="50"></Input> -->
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="productOriginAddress" label="产品产地地址">
                        <Input v-model="data.productOriginAddress" type="textarea"  :autosize="{minRows: 3,maxRows: 4}" :maxlength="50"></Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="location" label="产品产地地理位置">
                        <Input v-model="data.location" readonly @on-focus="onSelectPoint" />
                    </Form-item>
                </Col>
            </Row>        
        </Form>        
		<vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
    </div>
</template>
<script>
	import vuiMap from '../../../member/components/productionMap'
    import vuiCascander from '~components/vuiCascader/index'
    export default{
        components: {
            vuiMap,
            vuiCascander
        },
        data () {
            return {
                data: {
                    productOrigin: '', // 产品产地
                    productOriginAddress: '', // 产品产地地址
                    location: '' // 产品地理位置
                },
                ruleInline: {
                    productOrigin: [
                        {required: true, message: '请选择产品产地', trigger: 'change'}
                    ]
                }    
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
            },
            //地区
            handleGetData (value, selectedData) {
				let labelArr = []
				selectedData.forEach(element => {
					labelArr.push(element.label)
				})
				this.data.productOrigin = labelArr.join('/')
            },
            //坐标
            onSelectPoint() {
				this.$refs.experMap.showMap = true
			},
			// 取坐标
			onGetPoint(point) {
				if(point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
					this.data.location = `${point.lng},${point.lat}`
				} else {
					this.data.location = ''
				}
			},
        }
    }
</script>
<style>

</style>
