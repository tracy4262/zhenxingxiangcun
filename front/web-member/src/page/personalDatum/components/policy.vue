<template>
    <div class="ml10 mt20">
        <Form ref="data" :model="data" label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32" 
            v-if="(data.policy.status && data.policy.model) || (data.joinTime.status && data.joinTime.model) ">
                <Col span="12">
                    <Form-item label="政治面貌" v-if="data.policy.status">
                        {{data.policy.model}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.joinTime.status">
                    <Form-item label="加入时间">
                         {{data.joinTime.model}}
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
            }
        }
    },
    methods:{
        //接收数据
        getData(val){
            this.data = val
        }
    }
}
</script>
