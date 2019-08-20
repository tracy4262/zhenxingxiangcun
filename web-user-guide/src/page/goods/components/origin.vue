<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="productOrigin" label="产品产地">
                        <vui-cascander :values="data.productOrigin" @handle-get-result="handleGetData"></vui-cascander>
                    </Form-item>
                </Col>
                <!-- <Col span="12">
                    <Form-item prop="addrDetail" label="" style="margin-left:-150px;">
                        <Input v-model="data.addrDetail" @on-change="changeAddr" :maxlength="50" placeholder="详细地址..."/>
                    </Form-item>
                </Col>
                    <Col span="24">
                    <Form-item prop="" label="" style="margin-top:-10px;">
                        <Input v-model="data.addrView" type="textarea" :autosize="{minRows: 2,maxRows: 5}" readonly/>
                    </Form-item>
                </Col> -->
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
	import vuiMap from '../../member/components/productionMap'
    import vuiCascander from '~components/vuiCascader/index'
    export default {
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
                    ],
                    addrDetail: [
                        {required: true, message: '请输入产品产地详细地址', trigger: 'blur'}
                    ]
                }    
            }
        },
        methods:{
            getData (val) {
                this.data = val
                // this.changeAddr()
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
                // this.changeAddr()
            },
            changeAddr () {
                if (this.data.addrDetail && this.data.productOrigin) {
                    this.data.addrView = `${this.data.productOrigin} / ${this.data.addrDetail}`
                } else if (!this.data.addrDetail) {
                    this.data.addrView = `${this.data.productOrigin}`
                }  else if (!this.data.productOrigin) {
                    this.data.addrView = `${this.data.addrDetail}`
                }
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
