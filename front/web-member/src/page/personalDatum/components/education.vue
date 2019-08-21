<template>
    <div class="education pt30 pl10 pr10">
        <Form  label-position="left" :label-width="150" class="pb20">
            <Card class="mb20" v-for="(item,index) in data" :key="index" 
            v-if="(item.school.status && item.school.model) || 
            (item.degree.status && item.degree.model) || 
            (item.recruitment.status && item.recruitment.model) || 
            (item.major.status && item.major.model) || 
            (item.graduationTime.status && item.graduationTime.model)">
                <div >
                    <Row class="pb10" v-if="item.school.status">
                        <Col span="18">
                        {{item.school.model}}
                        </Col>
                    </Row>
                    <Row  class="t-grey" style="font-size:12px">
                        <Col span="18" > &nbsp;
                            <span class="pr20" v-if="item.degree.status">{{item.degree.model}} </span> 
                            <span class="pr20" v-if="item.recruitment.model == '是' && item.recruitment.status"> 统招  </span> 
                            <span class="pr20" v-if="item.recruitment.model == '否' && item.recruitment.status"> 非统招 </span> 
                            <span class="pr20" v-if="item.major.status">{{item.major.model}}</span>
                        </Col>
                        <Col span="6" class="tr"  v-if="item.graduationTime.model[0] && item.graduationTime.status">
                        {{moment(item.graduationTime.model[0]).format('YYYY/MM/DD')}} - {{moment(item.graduationTime.model[1]).format('YYYY/MM/DD')}}
                        </Col>
                    </Row>
                </div>
            </Card>
        </Form>
    </div>
</template>

<script>
import {isEmpty} from '~utils/validate'
export default {
    data () {
        return {
            educationForm:{
                school:{model:'',name:'学校名称',status:true},
                degree:{model:'',name:'学历',status:true},
                major:{model:'',name:'专业名称',status:true},
                recruitment: {model:'',name:'是否统招',status:true},
                graduationTime:{model:['',''],name:'入学/毕业时间',status:true},
            },
            data:[],
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

<style lang="scss">
.switch{
    height:35px;
    line-height: 35px;
}
.education {

}
</style>


