<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Row :gutter="32">
           <Col span="12">
                <Form-item label="服务">
                    <Button type="primary" @click="handleAdd"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
           </Col>
        </Row>
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" :bordered="false">
            <div class="btn-toolbar-card">
                <div class="btn-toolbar">
                    <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                </div>
            </div>
            <!-- <Row>
                <Col span="22"> -->
                <Form  :model="item" label-position="left" :label-width="82" :rules="ruleInline">
                    <Row :gutter="16">
                        <Col span="24">
                            <Form-item  label="权限">
                                <i-switch  v-model="item.provide_status" size="large">
                                    <span slot="open">公开</span>
                                    <span slot="close">隐藏</span>
                                </i-switch>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item  prop="serviceName" label="服务名称">
                                <Input v-model="item.serviceName" :maxlength="50"></Input>
                                <!-- <Input v-model="item.species"></Input> -->
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="classification" label="服务分类">
                                 <Input  v-model="item.classification" class="pinput-clear" icon="ios-close" readonly @on-focus="handleFilterModal('serviceFilter',index)" @on-click="inputClear(index)" />  
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="createTimes" label="创建时间">
                                <DatePicker v-model="item.createTimes" type="date"  :options="options3" placement="bottom-end" ></DatePicker>
                                <!-- <Input v-model="item.createTimes"></Input>    -->
                            </Form-item>
                        </Col>
                        <Col span="24">
                            <Form-item prop="pictureList" label="实景图片">  
                                <div @click="getIndex(index)">             
                                    <vui-upload 
                                    :ref="`index${index}`"
                                        @on-getPictureList="getPictureList"
                                        :pictureLists="item.pictureList"
                                        :hint="'支持拓格式jpg/png'"
                                    ></vui-upload>                                
                                </div>   
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
                <!-- </Col>
                <Col span="2" class="btn-toolbar-card">
                    &nbsp;
                    <div class="btn-toolbar">
                        <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
            </Row> -->
        </Card>
      </Form>
      <!-- 相关服务 -->
        <vui-filter
        ref="serviceFilter"
        :simple="true"
        :cols="2"
        :resultDatas="serviceResultDatas"
        @on-search="handleServiceSearch"
        @on-get-result="handleGetServiceResult" />
  </div>
</template>
<script>
    import vuiFilter from '~components/vuiFilter/filter'
    import vuiUpload from '~components/vui-upload'
    export default{
        components: {
            vuiFilter,
            vuiUpload
        },
        data () {
            return {
                serviceResultDatas: [],
                options3:{
                    disabledDate (date) {
                        return date && date.valueOf() > Date.now();
                    }
                },
                data: [],
                ruleInline:{

                },
                index:0,
                submit:true
            }
        },
        created() {
            // 取相关服务数据
            this.$api.post("/member/service/find", {}).then(res => {
                var data = res.data;
                data.forEach((item, index) => {
                    item.checked = false;
                });
                this.serviceResultDatas = data;
            });
        },
        updated (){
            for(var i = 0 ;i < this.data.length ; i++){
                var index = `index${i}`
                this.$refs[index][0].handleGive(this.data[i].pictureList)
            }
        },
        methods: {
            getData(val){
                this.data = val
            },
             //表单验证
            handleSubmit () { 
                this.data.map(e => {
                    if (e.createTimes) {
                        e.createTimes = this.moment(e.createTimes).format('YYYY/MM/DD')
                    }
                })
                this.submit = true               
                this.$emit('on-submit',this.submit)
            },
            // 获取实景图片
            getPictureList(e){
                var arr = []
                e.forEach( element => {
                    arr.push(element.response.data.picName)
                })
                this.data[this.index].pictureList = arr
            },
            getIndex (index) {
                this.index = index
            },
            //增加
            handleAdd () {
                this.data.unshift(
                    {
                        provide_status: true,
                        serviceName: '',
                        classification: '',
                        createTimes: '',
                        pictureList: []
                    }
                )               
            },
            //删除
            handleDel (index) {
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
            //清空物种文本框
            inputClear (index) {
                this.data[index].classification = ''
                this.$refs['serviceFilter'].handleReset()
            },
             // 高级搜索弹窗
            handleFilterModal (name,index) {
                this.$refs[name].highFilterShow = true
                this.index = index
                this.$refs['serviceFilter'].handleReset()
            },
            // 服务
            handleServiceSearch(letter, keyword, classify) {
                this.$api.post(`/member/service/find`, {
                    character: letter === "全部" ? "" : letter,
                    keyword: keyword
                    })
                    .then(res => {
                    var d = res.data;
                    this.serviceResultDatas = [];
                    if (d.length) {
                        if (classify.length) {
                        classify.forEach(item => {
                            d.forEach(child => {
                            if (child.label === item.label) {
                                child.checked = true;
                            }
                            });
                        });
                        } else {
                        d.forEach(child => {
                            child.checked = false;
                        });
                        }
                        this.serviceResultDatas = d;
                        this.$Message.success("筛选完成！");
                    }
                });
            },
            handleGetServiceResult(classify, result) {
                if (result.length) {
                     var arr = [];
                    var val = [];
                    result.forEach(item => arr.push(item.label));
                    result.forEach(item => val.push(item.value));
                    this.data[this.index].classification = arr.join(' ')
                } else {

                }
            },
        }
    }
</script>
