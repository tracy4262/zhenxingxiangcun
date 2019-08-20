<template>
  <div class="pt30 pl10 pr10"> 
    <Form  label-position="left" :label-width="150" class="pb20">
        <Card v-for="(item,index) in data" :key="index" class="mt20" v-if="item.professional_status">
            <Row type="flex" align="middle">
                <Col span="16" class="ell">
                    {{item.name}}
                </Col>
            </Row>
            <Row>
                <Col span="16" class="t-grey pt5">
                {{item.content}}
                </Col>
            </Row>
            <Row class="mt10" :gutter="8">
                <Col span="3" v-for="(picName,index) in item.qualificationPictureList" class="pb10" :key="index">
                    <img :src="picName" width="100%" height="120px">
                </Col>
            </Row>
        </Card>
    </Form>
  </div>
</template>
<script>
    export default {
        data () {
            return {
                qualification: false,
                qualificationFormItem: {
                    name: '',
                    content: '',
                    qualificationPictureList: []
                },
                data: [],
            }
        },
        methods: {
            //接收数据
			getData(val){
                this.data = val
			}
        }
    }
</script>
