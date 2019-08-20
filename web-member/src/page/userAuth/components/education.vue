<template>
    <div class="education pt30 pl10 pr10">
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="教育经历">
                        <Button type="primary" @click="handleAddEducation"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <Card class="mb20" v-for="(item,index) in data" :key="index">
                <div >
                    <Row class="pb10">
                        <Col span="18">
                        {{item.school.model}}
                        </Col>
                        <Col span="6" :offset="item.school.model ? 0 : 18">
                            <div class="btn-toolbar">
                                <Button type="text" @click="handleEdit(index)" size="small"><Icon type="edit" size="16" class="pr5"></Icon> 编辑</Button>
                                <Button type="text" @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button>
                            </div>
                        </Col>
                    </Row>
                    <Row  class="t-grey" style="font-size:12px">
                        <Col span="18" >
                        <span class="pr20">{{item.degree.model}} </span> 
                        <span class="pr20" v-if="item.recruitment.model == '是'"> 统招  </span> 
                        <span class="pr20" v-if="item.recruitment.model == '否'"> 非统招 </span> 
                        <span class="pr20">{{item.major.model}}</span>
                        </Col>
                        <Col span="6" class="tr"  v-if="item.graduationTime.model[0]">
                        {{moment(item.graduationTime.model[0]).format('YYYY/MM/DD')}} - {{moment(item.graduationTime.model[1]).format('YYYY/MM/DD')}}
                        </Col>
                    </Row>
                </div>
            </Card>
        </Form>
        <Modal
            v-model="educationShow"
            :title="title"
            :mask-closable="false"
            width="60%">
            <div class="pd20">
                <Form ref="educationForm" :model="educationForm" label-position="left" :label-width="100" :rules="rules">
                    <Row :gutter="32">
                        <Col :span="12">
                            <Row>
                                <Col :span="20">
                                    <Form-item prop="school" label="学校名称">
                                        <Input v-model="educationForm.school.model"  @on-change="onChange" :maxlength="20"></Input>
                                    </Form-item>
                                </Col>
                                <Col :span="4" class="switch">
                                    <Switch  v-model="educationForm.school.status"  @on-change="onChange" size="large">
                                        <span slot="open">公开</span>
                                        <span slot="close">隐藏</span>
                                    </Switch>
                                </Col>
                            </Row>
                        </Col>
                        <Col :span="12">
                            <Row>
                                <Col :span="20">
                                    <Form-item prop="degree" label="学历">
                                        <!-- <Input v-model="educationForm.degree"></Input> -->
                                        <Select v-model="educationForm.degree.model" @on-change="degreeChange">
                                            <Option v-for="item in degrees" :value="item.value" :key="item.value">{{ item.label }}</Option>
                                        </Select>
                                    </Form-item>
                                </Col>
                                <Col :span="4" class="switch">
                                    <Switch  v-model="educationForm.degree.status"  @on-change="onChange" size="large">
                                        <span slot="open">公开</span>
                                        <span slot="close">隐藏</span>
                                    </Switch>
                                </Col>
                            </Row>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="12" v-if="showMajor">
                            <Row>
                                <Col :span="20">
                                    <Form-item prop="major" label="专业名称">
                                        <Input v-model="educationForm.major.model"  @on-change="onChange" :maxlength="20"></Input>
                                    </Form-item>
                                </Col>
                                <Col :span="4" class="switch">
                                    <Switch  v-model="educationForm.major.status"   @on-change="onChange" size="large">
                                        <span slot="open">公开</span>
                                        <span slot="close">隐藏</span>
                                    </Switch>
                                </Col>
                            </Row>
                        </Col>
                        <Col :span="12" v-if="showRecruitment">
                            <Row>
                                <Col :span="20">
                                    <Form-item prop="recuitment" label="是否统招">
                                        <Select v-model="educationForm.recruitment.model"  @on-change="onChange">
                                            <Option v-for="item in recruitmentList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                                        </Select>
                                    </Form-item>
                                </Col>
                                <Col :span="4" class="switch">
                                    <Switch  v-model="educationForm.recruitment.status"   @on-change="onChange" size="large">
                                        <span slot="open">公开</span>
                                        <span slot="close">隐藏</span>
                                    </Switch>
                                </Col>
                            </Row>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="12">
                            <Row>
                                <Col :span="20">
                                    <Form-item prop="graduationTime" label="入学/毕业时间">
                                        <DatePicker v-model="educationForm.graduationTime.model" @on-change="handleTimeChange" type="daterange" format="yyyy/MM/dd"  :options="options3" split-panels placeholder="请选择时间" :editable="false"></DatePicker>
                                    </Form-item>
                                </Col>
                                <Col :span="4" class="switch">
                                    <Switch  v-model="educationForm.graduationTime.status"  @on-change="onChange"  size="large">
                                        <span slot="open">公开</span>
                                        <span slot="close">隐藏</span>
                                    </Switch>
                                </Col>
                            </Row>
                        </Col>
                    </Row>
                    <Row :gutter="32">
                        <Col :span="24">
                            <Row>
                                <Col :span="24">
                                    <Form-item  label="实时预览">
                                        <Input v-model="content" readonly type="textarea" :autosize="{minRows:4,maxRows: 5}"></Input>
                                    </Form-item>
                                </Col>
                            </Row>
                        </Col>
                    </Row>
                </Form>
            </div>
            <div slot="footer" class="tc">
                <Button type="default" @click="educationShow = false">取消</Button>
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
            showRecruitment:true,
            options3: {
                disabledDate(date) {
                    return date && date.valueOf() > Date.now() - 86400000;
                }
            },  
            educationShow:false,
            educationForm:{
                school:{model:'',name:'学校名称',status:true},
                degree:{model:'',name:'学历',status:true},
                major:{model:'',name:'专业名称',status:true},
                recruitment: {model:'',name:'是否统招',status:true},
                graduationTime:{model:['',''],name:'入学/毕业时间',status:true},
            },
            recruitmentList:[
                {label:'是',value:'是'},
                {label:'否',value:'否'},
            ],
            data:[],
            content:'',
            rules:{
                school:[{required:true,validator:isEmpty,message:'请输入学校名称',trigger:'blur'}],
                degree:[{required:true},{validator:isEmpty,trigger:'blur'}]
            },
            degrees:[
                {
                    value: '小学',
                    label: '小学'
                },
                {
                    value: '初中',
                    label: '初中'
                },
                {
                    value: '高中',
                    label: '高中'
                },
                {
                    value: '高职高专',
                    label: '高职高专'
                },
                {
                    value: '大专',
                    label: '大专'
                },
                {
                    value: '本科',
                    label: '本科'
                },
                {
                    value: '研究生',
                    label: '研究生'
                },
                {
                    value: '博士',
                    label: '博士'
                },
            ],
            isAdd:true,
            index:0,
            title: '新增教育经历'
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
                if (data[i].name == '入学/毕业时间') {
                    var starTime = data[i].graduationTime.model[0]
                    var endTime = data[i].graduationTime.model[1]
                    if(starTime && endTime){
                        data[i].model[0] = this.moment(starTime).format('YYYY/MM/DD')
                        data[i].model[1] = this.moment(endTime).format('YYYY/MM/DD')
                    }
                } 
            }
            this.data = data
            this.$emit('on-submit',true)
        },
        handleAddEducation () {
            this.educationForm = {
                school:{model:'',name:'学校名称',status:true},
                degree:{model:'',name:'学历',status:true},
                major:{model:'',name:'专业名称',status:true},
                recruitment: {model:'',name:'是否统招',status:true},
                graduationTime:{model:['',''],name:'入学/毕业时间',status:true},
            }
            this.title = '新增教育经历'
            this.educationShow = true            
            this.isAdd = true
        },
        degreeChange(e){
            // console.log(e)
            if(e == '小学' || e == '初中' || e == '高中'){
                //专业名称为空，且不显示
                this.educationForm.major.model = ''
                this.educationForm.major.status = false
                this.showMajor = false
                this.onChange()
            }else{
                //否则专业名称显示
                this.showMajor = true
                this.educationForm.major.status = true
            }
            if(e == '小学' || e == '初中'){
                //是否统招名称为空，且不显示
                this.educationForm.recruitment.model = ''
                this.educationForm.recruitment.status = false
                this.showRecruitment = false
                this.onChange()
            }else{
                //否则是否统招显示
                this.showRecruitment = true
                this.educationForm.recruitment.status = true
            }
        },
        //日期
        handleTimeChange (formartval) {
            if(formartval[0] && formartval[1]){
                this.educationForm.graduationTime.model =  formartval
            }else{
                this.educationForm.graduationTime.model =  []
            }
            this.onChange()
        },
        //当数据发生变化或者操作隐藏
        onChange(){
            let formItem = this.educationForm
            let content = ''
            for(var key  in formItem){
                if(formItem[key] && formItem[key].model && formItem[key].status){
                    if(formItem[key].name == '入学/毕业时间'){
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
            this.educationShow = true
            this.isAdd = false
            this.index = index
            // this.educationForm =  Object.assign({}, this.data[index])
            var obj = this.data[index]
            var formItem = {}
            for (var k in obj) {
                if(k == 'school' || k == 'degree' || k == 'major' || k == 'recruitment' || k == 'graduationTime'){
                    formItem[k]= {'model': obj[k].model,'name': obj[k].name,'status': obj[k].status, }
                }
                
            }
            this.title = '编辑教育经历'
            this.educationForm = formItem
            this.onChange()
        },
        //确定
        handleOk(){
            this.$refs.educationForm.validate ( (valid) => {
                if(valid) {
                    if (this.educationForm.graduationTime.model.length) {
                        var starTime = this.educationForm.graduationTime.model[0]
                        var endTime = this.educationForm.graduationTime.model[1]
                        if (starTime && endTime) {
                            this.educationForm.graduationTime.model[0] = this.moment(starTime).format('YYYY/MM/DD')
                            this.educationForm.graduationTime.model[1] = this.moment(endTime).format('YYYY/MM/DD')
                        } else {
                            this.educationForm.graduationTime.model = []
                        }
                    }
                    if(this.isAdd){
                        this.data.unshift(this.educationForm)
                    }else{
                        this.data[this.index] = this.educationForm
                    }
                    this.educationShow = false
                }else{
                    this.$Message.error('请核对表单信息')
                }
            })            
        },
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


