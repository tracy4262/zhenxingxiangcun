<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Card v-for="(item , index) in data" :key="index" class="mb20" v-if="item.status">          
            <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="155">
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item  prop="name" label="房主姓名">
                           {{item.name}}
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="addr" label="房屋地址">
                            {{item.addr}}
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="addr" label="详细地址">
                            {{item.addrDetail}}
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="buildingArea" label="建筑面积">
                            {{item.buildingArea}} <span v-if="item.buildingArea">平方米</span>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="useArea" label="土地使用面积">  
                            {{item.useArea}} <span v-if="item.useArea">平方米</span>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="certificate" label="是否办证">  
                           {{item.certificate}}
                        </Form-item>
                    </Col>
                    <Col span="12" v-if="item.certificate == '否'">
                        <Form-item prop="reason" label="未办证原因">  
                           {{item.reason}}
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32"  v-if="item.certificate == '是'">
                    <Col span="12">
                        <Form-item prop="houseNumber" label="房屋所有权证编号">  
                            {{item.houseNumber}}
                        </Form-item>
                    </Col>                    
                    <Col span="12">
                        <Form-item prop="landNumber" label="土地使用证编号">  
                            {{item.landNumber}}
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="estate" label="不动产权证编号">  
                            {{item.estate}}
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="structure" label="房屋结构">  
                            {{item.structure}}
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="distance" label="房屋与乡村公路的距离">  
                            {{item.distance}}   <span v-if="item.distance">米</span>
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="land" label="所在地理位置">  
                            {{item.land}}
                        </Form-item>
                    </Col>
                     <Col span="12">
                        <Form-item prop="purpose" label="房屋用途">  
                            {{item.purpose}}
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="24">
                        <Form-item prop="development" label="房屋建设情况描述">  
                            {{item.development}}
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <Form-item prop="waterSource" label="饮水来源">  
                           {{item.waterSource}}
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="waterHard" label="饮水是否困难">  
                           {{item.waterHard}}
                        </Form-item>
                    </Col>
                        <Col span="12">
                        <Form-item prop="biogasPool" label="沼气池">  
                            {{item.biogasPool}}
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="pool" label="一池三改">  
                            {{item.pool}}
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="gas" label="天然气">  
                            {{item.gas}}
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="communicationQuality" label="通电质量">  
                            {{item.communicationQuality}}
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="broadband" label="宽带网">  
                             {{item.broadband}}
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="tcSignal" label="电视信号">  
                            {{item.tcSignal}}
                        </Form-item>
                    </Col>
                    <Col span="12">
                        <Form-item prop="network" label="电信网络">  
                           {{item.network}}
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
            }
        },
        created() {
        },
        methods: {
            getData(val){
                this.data = val
            },
        }
    }
</script>
<style>
.family-deatil .ivu-card{
    overflow: visible;
}
</style>
