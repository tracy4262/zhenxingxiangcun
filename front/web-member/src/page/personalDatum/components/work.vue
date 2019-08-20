<template>
    <div class="education pt30 pl10 pr10">
        <Form  label-position="left" :label-width="150" class="mt20 pb20">
            <Card class="mb20" v-for="(item,index) in data" :key="index"
               v-if="(item.WorkUnit.status && item.WorkUnit.model) || 
                    (item.job.status && item.job.model) || 
                    (item.detail.status && item.detail.model) || 
                    (item.workTime.status && item.workTime.model[0])">
                <div >
                    <Row class="pb10" v-if="item.WorkUnit.status">
                        <Col span="18">
                        {{item.WorkUnit.model}}
                        </Col>
                    </Row>
                    <Row  class="t-grey" style="font-size:12px">
                        <Col span="18" v-if="item.job.status">
                         {{item.job.model}}
                        </Col>
                        <Col span="6" class="tr" :offset="item.job.model&&item.job.status ? 0 : 18" v-if="item.workTime.model[0] && item.workTime.status">
                        {{moment(item.workTime.model[0]).format('YYYY/MM/DD')}} - {{moment(item.workTime.model[1]).format('YYYY/MM/DD')}}
                        </Col>
                    </Row>
                    <Row  class="t-grey pt10" style="font-size:12px" v-if="item.detail.status"> 
                        <Col span="24" >
                         {{item.detail.model}}
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
            formItem:{
                WorkUnit:{model:'',name:'工作单位',status:true},
                job:{model:'',name:'工作职位',status:true},
                workTime:{model:['',''],name:'工作时间',status:true},
                detail: {model:'',name:'工作详情',status:true}
            },
            data:[],
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

<style lang="scss">
</style>


