<template>
  <div class="pt30 pl10 pr10"> 
    <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
        <Row :gutter="32">
            <Col span="24">
            <FormItem label="权限">
                <i-switch  v-model="data.manage_status" size="large">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                </i-switch>
            </FormItem>
            </Col>
        </Row>
        <Row :gutter="32">
           <Col span="12">
                <Form-item prop="scale" label="企业规模">
                    <!-- <Input v-model="data.scale" /> -->
                    <Select v-model="data.scale">
                        <Option v-for="item in scales" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </Form-item>
           </Col>
           <Col span="12">
                <Form-item prop="industry" label="所属行业">
                    <Input v-model="data.industry" readonly @on-focus="handleFilterModal('tradeFilter')" />
                </Form-item>
           </Col>
        </Row>
        <Row :gutter="32">
           <Col span="12">
                <Form-item prop="turnover" label="上年度营业收入">
                    <Input v-model="data.turnover" :maxlength="50" @on-change="getToString">
                        <span slot="append">万元</span>
                    </Input>
                </Form-item>
           </Col>
           <Col span="12">
                <Form-item prop="JointStockCode" label="股份代码">
                    <Input v-model="data.JointStockCode" :maxlength="20"/>
                </Form-item>
           </Col>
        </Row>
    </Form>
    <!-- 相关行业 -->
		<vui-filter
		ref="tradeFilter"
		:num="4"
		:simple="true"
		:pageShow="true"
		:cols="2"
		:total="total"
        :pageCur="pageCur"
		:resultDatas="tradeResultDatas"
		@on-search="handleTradeSearch"
		@on-page-change="handleTradePageChange"
		@on-get-result="handleGetTradeResult" />
  </div>
</template>
<script>
	import vuiFilter from '~components/vuiFilter/filter'
    import {isDecimal2} from '~utils/validate'
    export default{
        components:{
            vuiFilter
        },
        data(){
            return{
				total: 0,
                pageCur: 1,
				tradeResultDatas: [],
                scales:[
                    {value:'50人',label:'50人'},
                    {value:'50-100人',label:'50-100人'},
                    {value:'150-300人',label:'150-300人'},
                    {value:'300-500人',label:'300-500人'}
                ],
                data:{
                    manage_status: true,
                    scale:'',
                    industry:'',
                    turnover:'',
                    JointStockCode:''
                },
                ruleInline:{
                    // scale:[
                    //     { required: true, message: '请填写企业规模', trigger: 'blur' }
                    // ],
                    // industry:[
                    //     { required: true, message: '请选择所属行业', trigger: 'blur' }
                    // ],
                    JointStockCode:[
                        {max:20,trigger: 'blur',message: '请填写正确的股份代码'}
                    ],
                    turnover:[
                        {validator:isDecimal2, trigger: 'blur'}
                    ]
                }
            }
        },
        created(){
            // 取相关行业数据
            this.$api.post('/member/system-dict/getSystemDict', {
                typeName:'行业分类',
                pageNum: 1
            }).then(res => {
                var data = res.data.list
                this.total2 = res.data.total
                this.tradeResultDatas = data
            })
        },
        methods:{
            //接收数据
			getData(val){
                this.data = val
                console.log
            },
            getToString(){
                this.data.turnover = this.data.turnover + ''
            },
            //点击下一步的时候表单验证
            handleSubmit(){
                this.$refs['data'].validate((valid) => {
                    if (valid) {                        
                       this.$emit('on-submit',true)
                    } else {                        
                       this.$emit('on-submit',false)
                    }
                })
            },            
			// 高级搜索弹窗
            handleFilterModal (name) {
                this.$refs[name].highFilterShow = true
            },
            // 行业类型
            handleTradeSearch (letter, keyword, result) {
                this.$api.post(`/member/system-dict/getSystemDict`, {
					dictName : keyword,
					typeName : '行业分类',
					character: letter === '全部' ? '' : letter,
					pageNum: 1
				}).then(res => {
					var d = res.data.list
					this.total3 = res.data.total
					this.pageCur3 = 1
					this.tradeResultDatas = []
					if (!d.length) return 
					if (result.length) {
						result.forEach(item => {
							d.forEach(child => {
								if (child.label === item.label) {
									child.checked = true
									child.label = child.label
									child.value = child.value
								}
							})
						})
					} else {
						d.forEach(child => {
							child.checked = false
							child.label = child.label
							child.value = child.value
						})
					}
					this.tradeResultDatas = d
				})
            },
            handleTradePageChange (letter, keyword, classify, num, result) {
                this.pageCur3 = num
                this.$api.post('/member/system-dict/getSystemDict', {
					typeName:'行业分类',
					character: letter === '全部' ? '' : letter,
					dictName: keyword,
					pageNum: num
				}).then(res => {
					var data = res.data.list
					this.total3 = res.data.total
					this.tradeResultDatas = data
				})
                this.$Message.success('筛选完成！')
            },
            handleGetTradeResult (classify, result) {
                var arr = []
				result.forEach(item => arr.push(item.label))
				this.data.industry = arr.join(' ')
            },
        }
    }

</script>
<style>

</style>
