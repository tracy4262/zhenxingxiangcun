<template>
    <div class="pt30 pl10 pr10">
        <Form ref="data" :model="data" label-position="left" :label-width="80" :rules="ruleValidate" class="pb20">
            <Row :gutter="32">
                <Col span="24">
                    <Form-item label="信仰教会">
                        <Select v-model="data.religion.model" clearable style="width: 250px;">
                            <Option v-for="(item, index) in religionList" :key="index" :value="item.value">{{ item.label }}</Option>
                        </Select>
                        <i-switch v-model="data.religion.status" size="large" class="ml20">
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
                religion: {
                    model: '',
                    name: '信仰教会',
                    status: true
                }
            },
            content: '',
            ruleValidate: {},
            religionList: [
                {
                    label: '佛教',
                    value: '佛教'
                },
                {
                    label: '道教',
                    value: '道教'
                },
                {
                    label: '基督教',
                    value: '基督教'
                },
                {
                    label: '犹太教',
                    value: '犹太教'
                },
                {
                    label: '伊斯兰教',
                    value: '伊斯兰教'
                },
                {
                    label: '印度教',
                    value: '印度教'
                },
                {
                    label: '神道教',
                    value: '神道教'
                },
                {
                    label: '儒教',
                    value: '儒教'
                }
            ]
        }
    },
    watch: {
        data: {
            handler(newValue, oldValue) {
                this.content = ''
                if (newValue.religion.model && newValue.religion.status) {
                    this.content += '信仰'
                }
                if (newValue.religion.status) {
                    this.content += newValue.religion.model
                }
            },
            deep: true
        }
    },
    methods:{
        //接收数据
        getData(val){
            this.data = val
        },
        //下一步表单验证
        handleSubmit(){
            this.$emit('on-submit',true)
        }
    }
}
</script>
