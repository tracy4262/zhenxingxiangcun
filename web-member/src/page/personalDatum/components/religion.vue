<template>
    <div class="ml10 mt20">
        <Form ref="data" :model="data" label-position="left" :label-width="150"  class="pb20">
            <Row :gutter="32"
             v-if="data.religion.status && data.religion.model">
                <Col span="24">
                    <Form-item label="信仰教会" v-if="data.religion.status">
                        {{data.religion.model}}
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
        }
    },
    methods:{
        //接收数据
        getData(val){
            this.data = val
        },
    }
}
</script>
