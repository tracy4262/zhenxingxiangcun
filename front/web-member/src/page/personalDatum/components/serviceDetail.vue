<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" v-if="item.provide_status">
            <!-- <Row>
                <Col span="22"> -->
                <Form  :model="item" label-position="left" :label-width="82" :rules="ruleInline">
                    <Row :gutter="16">
                        <Col span="8">
                            <Form-item  prop="serviceName" label="服务名称">
                                {{item.serviceName}}
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="classification" label="服务分类">
                                {{item.classification}}
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="createTimes" label="创建时间">
                                {{moment(item.createTimes).format('YYYY/MM/DD')}}
                            </Form-item>
                        </Col>
                        <Col span="24">
                            <Form-item prop="pictureList" label="实景图片">  
                                    <div v-if="item.pictureList[0]">
                                        <img :src="item.pictureList[0]" alt="" width="120px" height="120px">        
                                    </div> 
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
        </Card>
      </Form>
  </div>
</template>
<script>
    export default{
        data () {
            return {
                data: [],
                ruleInline:{

                },
                index:0,
                submit:true
            }
        },
        created() {           
        },
        methods: {
            getData(val){
                this.data = val
            }
        }
    }
</script>
