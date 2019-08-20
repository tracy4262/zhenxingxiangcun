<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Card v-for="(item , index) in data" :key="index" class="mb20" v-if="item.family_modern_status">           
                <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="130" >
                    <Row :gutter="16">
                        <Col span="8">
                            <Form-item  prop="tv" label="电视机数量">                                
                                {{item.tv}} <span v-if="item.tv">台</span>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="computer" label="电脑数量">
                                {{item.computer}} <span v-if="item.computer">台</span>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="icebox" label="冰箱数量">
                                {{item.icebox}} <span v-if="item.computer">台</span>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="ari" label="空调数量">
                                {{item.ari}} <span v-if="item.ari">台</span>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="car" label="汽车数量">
                                {{item.car}} <span v-if="item.car">辆</span>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="motorcycle" label="摩托车数量">
                                {{item.motorcycle}} <span v-if="item.motorcycle">辆</span>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="heater" label="太阳能热水器数量">
                                {{item.heater}}  <span v-if="item.heater">台</span>
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
                data: [
                    {
                        tv: '',
                        computer: '',
                        icebox: '',
                        ari: '',
                        car: '',
                        motorcycle: '',
                        heater: ''
                    }
                ],
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
