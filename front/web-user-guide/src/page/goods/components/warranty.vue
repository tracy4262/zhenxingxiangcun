<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="date" label="日期">
                        <Select v-model="data.date" style="width: 100%" @on-change="handleChangeDate">
                            <Option v-for="item in credentialss" :value="item.value" :key="item.value">{{ item.value }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.date === '采收日期'">
                    <Form-item prop="harvestDate" label="时间段">
                        <DatePicker placeholder="采收日期" type="datetime" style="width:100%;" format="yyyy-MM-dd HH:mm" @on-change="hanleChange" :editable="false" v-model="data.harvestDate" ></DatePicker>
                    </Form-item>
                </Col>
                <Col span="12" v-else>
                    <Form-item prop="productionDate" label="时间">
                        <DatePicker placeholder="生产日期" style="width:100%;" type="date" @on-change="hanleChange" :options="options3" :editable="false" v-model="data.productionDate" ></DatePicker>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="shelfLife" label="保质期">
                        <Input v-model="data.shelfLife" @on-change="shelfLifeChange">
                        <span slot="append"> 月</span>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="shelfLifeTo" label="保质期至">
                        <DatePicker style="width:100%;" type="date" readonly :options="options4" :editable="false" v-model="data.shelfLifeTo" ></DatePicker>
                    </Form-item>
                </Col>
            </Row>        
        </Form>
    </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    import {isNumber} from '~utils/validate'
    export default {
        components: {
            vuiUpload
        },
        data () {
            return {
                data: {
                    date: '采收日期', // 日期
                    harvestDate: '', // 时间段 采收日期
                    productionDate: '', // 时间 生产日期
                    shelfLife: '', // 保质期
                    shelfLifeTo: '', // 保质期至
                },
                ruleInline: {
                    shelfLife:[{validator:isNumber,trigger:'blur'}]
                },
                options3: {
					disabledDate(date) {
						return date && date.valueOf() > Date.now();
					}
                },  
                options4: {
					disabledDate(date) {
						return date && date.valueOf() < Date.now();
					}
                },
                credentialss: [
                    {value: '采收日期'},
                    {value: '生产日期'},
                ]
            }
        },
        methods:{
            // 日期改变
            handleChangeDate () {
                if (this.data === '采收日期') {
                    this.data.harvestDate = ''
                } else {
                    this.data.productionDate = ''
                }
            },
            hanleChange () {
                if (this.data.harvestDate) {
                    this.data.harvestDate = `${this.moment(this.data.harvestDate).format('YYYY/MM/DD HH:mm')}`
                    this.getShelfLifeTo(this.data.harvestDate)
                } else {
                    this.data.productionDate = `${this.moment(this.data.productionDate).format('YYYY/MM/DD')}`
                    this.getShelfLifeTo(this.data.productionDate)
                }
            },
            // 保质期改变 月
            shelfLifeChange () {
                if (this.data.harvestDate) {
                    this.getShelfLifeTo(this.data.harvestDate)
                } else if (this.data.productionDate) {
                    this.getShelfLifeTo(this.data.productionDate)
                }
            },
            // 动态改变保质时间
            getShelfLifeTo (d) {
                if (this.data.shelfLife) {
                    let date = new Date(d)
                    this.data.shelfLifeTo = this.moment(date).add(this.data.shelfLife, 'months')._d
                }
            },
            getData (val) {
                this.data = val
            },
            handleSubmit () {
                 if(this.data.harvestDate){
                    this.data.harvestDate = this.moment(this.data.harvestDate).format('YYYY/MM/DD HH:mm')
                }  
                if(this.data.productionDate){
                    this.data.productionDate = this.moment(this.data.productionDate).format('YYYY/MM/DD')
                }  
                if(this.data.shelfLifeTo){
                    this.data.shelfLifeTo = this.moment(this.data.shelfLifeTo).format('YYYY/MM/DD')
                }       
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
