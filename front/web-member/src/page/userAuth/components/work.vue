<template>
    <div class="education pt30 pl10 pr10">
        <Form  label-position="left" :label-width="150" class="mt20 pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="工作经历">
                        <Button type="primary" @click="handleAdd"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <Card class="mb20" v-for="(item,index) in data" :key="index">
                <div >
                    <Row class="pb10">
                        <Col span="18">
                        {{item.WorkUnit.model}}
                        </Col>
                        <Col span="6" :offset="item.WorkUnit.model ? 0 : 18">
                            <div class="btn-toolbar">
                                <Button type="text" @click="handleEdit(index)" size="small"><Icon type="edit" size="16" class="pr5"></Icon> 编辑</Button>
                                <Button type="text" @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button>
                            </div>
                        </Col>
                    </Row>
                    <Row  class="t-grey" style="font-size:12px">
                        <Col span="18" >
                         {{item.job.model}}
                        </Col>
                        <Col span="6" class="tr"  v-if="item.workTime.model[0]">{{moment(item.workTime.model[0]).format('YYYY/MM/DD')}} - {{moment(item.workTime.model[1]).format('YYYY/MM/DD')}}</Col>
                    </Row>
                    <Row  class="t-grey pt10" style="font-size:12px">
                        <Col span="24" >
                         {{item.detail.model}}
                        </Col>
                    </Row>
                </div>
            </Card>
        </Form>
        <Modal
            v-model="isShow"
            :title="title"
            :mask-closable="false"
            width="800px">
            <div class="pd40">
                <Form ref="formItem" :model="formItem" label-position="left" :label-width="100" :rules="rules">
                    <!-- <Row :gutter="32">
                        <Col :span="12"> -->
                            <Row :gutter="32">
                                <Col :span="20">
                                    <Form-item prop="WorkUnit" label="工作单位">
                                        <Input v-model="formItem.WorkUnit.model" @on-change="onChange" :maxlength="20"></Input>
                                    </Form-item>
                                </Col>
                                <Col :span="4">
                                    <Switch  v-model="formItem.WorkUnit.status" @on-change="onChange"   size="large">
                                        <span slot="open">公开</span>
                                        <span slot="close">隐藏</span>
                                    </Switch>
                                </Col>
                            </Row>
                        <!-- </Col>
                        <Col :span="12"> -->
                            <Row :gutter="32">
                                <Col :span="20">
                                    <Form-item prop="job" label="工作职位">
                                        <Input v-model="formItem.job.model" @on-change="onChange" :maxlength="20"></Input>
                                    </Form-item>
                                </Col>
                                <Col :span="4">
                                    <Switch  v-model="formItem.job.status" @on-change="onChange"  size="large">
                                        <span slot="open">公开</span>
                                        <span slot="close">隐藏</span>
                                    </Switch>
                                </Col>
                            </Row>
                        <!-- </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="12"> -->
                            <Row :gutter="32">
                                <Col :span="20">
                                    <Form-item prop="workTime" label="工作时间">
                                        <DatePicker v-model="formItem.workTime.model" :editable="false" type="daterange" @on-change="handleTimeChange" format="yyyy/MM/dd"  :options="options3" style="width:100%" split-panels placeholder="请选择时间"></DatePicker>
                                    </Form-item>
                                </Col>
                                <Col :span="4">
                                    <Switch  v-model="formItem.workTime.status" @on-change="onChange"  size="large">
                                        <span slot="open">公开</span>
                                        <span slot="close">隐藏</span>
                                    </Switch>
                                </Col>
                            </Row>
                        <!-- </Col>
                    </Row>
                     <Row :gutter="32">
                        <Col :span="24"> -->
                            <Row :gutter="32">
                                <Col :span="20">
                                    <Form-item prop="detail" label="工作详情">
                                        <Input v-model="formItem.detail.model" type="textarea" @on-change="onChange"  :autosize="{minRows:4,maxRows: 5}" :maxlength="500"></Input>
                                    </Form-item>
                                </Col>
                                <Col :span="4">
                                    <Switch  v-model="formItem.detail.status"  size="large">
                                        <span slot="open">公开</span>
                                        <span slot="close">隐藏</span>
                                    </Switch>
                                </Col>
                            </Row>
                        <!-- </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="24"> -->
                            <Row :gutter="32">
                                <Col :span="20">
                                    <Form-item  label="实时预览">
                                        <Input v-model="content" readonly type="textarea" :autosize="{minRows:4,maxRows: 5}"></Input>
                                    </Form-item>
                                </Col>
                            </Row>
                        <!-- </Col>
                    </Row> -->
                </Form>
            </div>
            <div slot="footer" class="tc">
                <Button type="default" @click="isShow = false">取消</Button>
                <Button type="primary" @click="handleOk">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import {isEmpty} from '~utils/validate'
export default {
    data () {
        return {
            showMajor:true,
            options3: {
                disabledDate(date) {
                    return date && date.valueOf() > Date.now() - 86400000;
                }
            },  
            isShow:false,
            formItem:{
                WorkUnit:{model:'',name:'工作单位',status:true},
                job:{model:'',name:'工作职位',status:true},
                workTime:{model:['',''],name:'工作时间',status:true},
                detail: {model:'',name:'工作详情',status:true}
            },
            data:[],
            content:'',
            rules:{
                WorkUnit:[{required:true},{validator:isEmpty,trigger:'blur'}],
                job:[{required:true},{validator:isEmpty,trigger:'blur'}]
            },
            isAdd:true,
            index:0,
            title: '新增工作经历'
        }
    },
    methods:{
        //接收数据
        getData(val){
            this.data = val
        },
        //下一步表单验证
        handleSubmit(){ 
            var data = this.data
            for(var i = 0 ;i < data.length ;i++){
                if (data[i].name == '工作时间') {
                    var starTime = data[i].workTime.model[0]
                    var endTime = data[i].workTime.model[1]
                    if(starTime && endTime){
                        data[i].model[0] = this.moment(starTime).format('YYYY/MM/DD')
                        data[i].model[1] = this.moment(endTime).format('YYYY/MM/DD')
                    }
                } 
            }
            this.data = data
            this.$emit('on-submit',true)
        },
        handleAdd () {
            this.formItem = {
                WorkUnit:{model:'',name:'工作单位',status:true},
                job:{model:'',name:'工作职位',status:true},
                workTime:{model:['',''],name:'工作时间',status:true},
                detail: {model:'',name:'工作详情',status:true}
            }
            this.title = '新增工作经历'
            this.isShow = true
            this.isAdd = true
        },
        //日期
        handleTimeChange (formartval) {
            if(formartval[0] && formartval[1]){
                this.formItem.workTime.model =  formartval
            }else{
                this.formItem.workTime.model =  []
            }
            this.onChange()
        },
        //当数据发生变化或者操作隐藏
        onChange(){
            let formItem = this.formItem
            let content = ''
            for(var key  in formItem){
                if(formItem[key] && formItem[key].model && formItem[key].status){
                    if(formItem[key].name == '工作时间'){
                        var starTime = formItem[key].model[0]
                        var endTime = formItem[key].model[1]
                        if(starTime && endTime){
                            content +=  `${formItem[key].name}：${this.moment(starTime).format('YYYY/MM/DD')}-${this.moment(endTime).format('YYYY/MM/DD')}，`
                        }
                    }else{
                        content +=  `${formItem[key].name}：${formItem[key].model}，`
                    }
                }
            }
            content = content.substring(0,content.length-1)+'。'
            this.content = content
        },
        // 删除
        handleDel(index){
            this.$Modal.confirm({
                title: '是否确定删除',
                content: '是否确认删除？',
                onOk:()=>{
                    this.data.splice(index,1)
                },
                okText:'确定',
                cancelText:'取消'
            });
            
        },
        // 编辑
        handleEdit(index){
            // this.formItem = this.data[index]
            this.isShow = true
            this.isAdd = false
            this.index = index
            // this.formItem =  Object.assign({}, this.data[index])
            var obj = this.data[index]
            var formItem = {}
            for (var k in obj) {
                if(k == 'WorkUnit' || k == 'job' || k == 'workTime' || k == 'detail'){
                    formItem[k]= {'model': obj[k].model,'name': obj[k].name,'status': obj[k].status, }
                }
                
            }
            this.title = '编辑工作经历'
            this.formItem = formItem
            this.onChange()
        },
        //确定
        handleOk(){
            this.$refs.formItem.validate ( (valid) => {
                if(valid) {
                    if (this.formItem.workTime.model.length) {
                        var starTime = this.formItem.workTime.model[0]
                        var endTime = this.formItem.workTime.model[1]
                        if (starTime && endTime) {
                            this.formItem.workTime.model[0] = this.moment(starTime).format('YYYY/MM/DD')
                            this.formItem.workTime.model[1] = this.moment(endTime).format('YYYY/MM/DD')
                        } else {
                            this.formItem.workTime.model = []
                        }
                    }
                    if (this.isAdd) {                        
                        this.data.unshift(this.formItem)
                    } else {
                        this.data[this.index] = this.formItem
                    }               
                    this.isShow = false
                }else{
                    this.$Message.error('请核对表单信息')
                }
            })    
          
        },
    }
  
}
</script>

<style lang="scss">
</style>


