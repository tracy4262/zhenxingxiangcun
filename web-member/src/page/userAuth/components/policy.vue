<template>
    <div class="pt30 pl10 pr10">
        <Form ref="data" :model="data" label-position="left" :label-width="80" :rules="ruleValidate" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="政治面貌">
                        <Select v-model="data.policy.model" clearable style="width: 250px;" @on-change="onchange">
                            <Option v-for="(item, index) in policyList" :key="index" :value="item.value">{{ item.label }}</Option>
                        </Select>
                        <i-switch v-model="data.policy.status" size="large" class="ml20">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item label="加入时间">
                        <Date-picker v-model="data.joinTime.model" format="yyyy年MM月" :options="options" :editable="false"
                            type="month" placeholder="选择日期" transfer style="width: 250px;" @on-change="handleChange">
                        </Date-picker>
                        <i-switch v-model="data.joinTime.status" size="large" class="ml20">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="24">
                    <Form-item label="实时预览">
                        <Input v-model="content" type="textarea" readonly />
                    </Form-item>
                </Col>
            </Row>
        </Form>
    </div>
</template>
<script>
export default {
    components: {
    },
    data () {
        return {
            data: {
                policy: {
                    model: '',
                    name: '政治面貌',
                    status: true
                },
                joinTime: {
                    model: '',
                    name: '加入时间',
                    status: true
                }
            },
            content: '',
            ruleValidate: {},
            policyList: [
                {
                    label: '中国共产党',
                    value: '中国共产党'
                },
                {
                    label: '中国共青团',
                    value: '中国共青团'
                },
                {
                    label: '中国民主同盟',
                    value: '中国民主同盟'
                },
                {
                    label: '中国民主建国会',
                    value: '中国民主建国会'
                },
                {
                    label: '中国民主促进会',
                    value: '中国民主促进会'
                },
                {
                    label: '中国致公党',
                    value: '中国致公党'
                },
                {
                    label: '九三学社',
                    value: '九三学社'
                },
                {
                    label: '台湾民主自治同盟',
                    value: '台湾民主自治同盟'
                },
                {
                    label: '国民党',
                    value: '国民党'
                },
                {
                    label: '民主党',
                    value: '民主党'
                },
                {
                    label: '无党派',
                    value: '无党派'
                },
                {
                    label: '民进党',
                    value: '民进党'
                }
            ],
            options: {
                disabledDate (date) {
                    return date && date.valueOf() > Date.now() - 86400000
                }
            }
        }
    },
    methods:{
        //接收数据
        getData(val){
            this.data = val
            this.onchange()
        },
        //下一步表单验证
        handleSubmit(){
            let type = typeof this.data.joinTime.model
            if (type === 'object'){
                this.data.joinTime.model =  this.moment(this.data.joinTime.model).format('YYYY年MM月')
            }
            this.$emit('on-submit',true)
        },
        handleChange () {
            if(this.data.joinTime.model){
                this.data.joinTime.model =  this.moment(this.data.joinTime.model).format('YYYY年MM月')
            } else {
                this.data.joinTime.model = ''
            }
            this.onchange()
        },
        onchange(){
            var newValue = this.data
            this.content = ''
            if (newValue.joinTime.status && newValue.joinTime.model) {
                this.content += newValue.joinTime.model
            }
            if (newValue.policy.model && newValue.joinTime.model && newValue.joinTime.status && newValue.policy.status) {
                this.content += '加入'
            }
            if (newValue.policy.status) {
                this.content += newValue.policy.model
            }
        }
    }
}
</script>
