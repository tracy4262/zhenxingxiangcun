<template>  
  <div  class="pd20">
    <Title :title="title" edit :id="id" :yearId="yearId"></Title>
    <Form :label-width="82" label-position="left" style="background:#f9f9f9;" class="pd20 mt40" :model="item"  :rules="ruleInline" v-for="(item, index) in data">
          <Row :gutter="16">
              <Col span="8">
                  <Form-item label="权限">
                      <i-switch size="large"  v-model="item.status" :disabled="!item.edit">
                          <span slot="open">公开</span>
                          <span slot="close">隐藏</span>
                      </i-switch>
                  </Form-item>
              </Col>
              <!-- <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.edit">编辑</span>
                <span class="auth-btn-toolbar" @click="handleDel(item, index)" v-if="data.length != 1 && item.edit">删除</span>
              </Col> -->
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item  prop="serviceName" label="服务名称">
                      <Input v-model="item.serviceName" :maxlength="50" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="classification" label="服务分类">
                        <Input v-model="item.classification" :disabled="!item.edit" class="pinput-clear" icon="ios-close" readonly @on-focus="handleFilterModal('serviceFilter',index)" @on-click="inputClear(index)" />  
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="createTimes" label="创建时间">
                      <DatePicker v-model="item.createTimes" type="date" :disabled="!item.edit" :options="options3" placement="bottom-end" ></DatePicker>
                  </Form-item>
              </Col>
              <Col span="24">
                  <Form-item prop="pictureList" label="实景图片">
                          <vui-upload 
                          :ref="`index${index}`"
                          :disabled="!item.edit"
                              @on-getPictureList="getPictureList($event, index)"
                              :pictureLists="item.pictureList"
                              :hint="'支持拓格式jpg/png'"
                          ></vui-upload> 
                  </Form-item>
              </Col>
          </Row>
          <!-- <div class="tc pd20" v-if="item.edit">
            <Button type="primary" @click="handleSave(item, index)">保存</Button>
          </div> -->
    </Form>
    <!-- <Button type="primary" ghost @click="handleAdd" class="mt20 btn-light-primary" icon="md-add">增加</Button> -->
    <Title title="文字预览" class="mt50"></Title>
    <div class="pd20 pt30">
      <Input type="textarea" v-model="preview" :autosize="{minRows: 3,maxRows: 5}"></Input>
    </div>
    <div class="tc pd20">
    <Button type="primary" @click="onSave">保存</Button>
    </div>
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
import Title from '../../components/title'
export default {
    components: {
        vuiFilter,
        vuiUpload,
        Title
    },
    props: {
        yearId: {
        type: String
        },
        id: {
        type: String
        },
        appId: {
        type: String
        }
    },
    data () {
        return {
            serviceResultDatas: [],
            options3:{
                disabledDate (date) {
                    return date && date.valueOf() > Date.now();
                }
            },
            data: [
              {
                edit: false,
                status: true,
                serviceName: '',
                classification: '',
                createTimes: '',
                pictureList: []
              }
            ],
            preview: '',
            ruleInline:{

            },
            submit:true,
            title: '',
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
        // 文字预览
        changePreview () {
            let str = ''
            if (this.data.length) {
                str =  `所在地有`
            }
            this.data.forEach(e => {
                if (e.serviceName) {
                    str += `${e.serviceName}、`
                }
            })
            if (str) {
                str = `${str.substring(0,str.length-1)}。`
            }
            this.preview = str
        },
        //  初始化数据
        handleInit (type) {
            this.$api.post('/member-reversion/provideService/find',{
                account: this.$user.loginAccount,
                templateId: this.$template.id,
                yearId: this.yearId,
                dictId: this.id
            }).then(response => {
                if (response.code == 200) {
                    if (type) { // 数据更新
                        // this.changePreview()
                    } else {
                        if (response.data.preview) {
                            this.preview = response.data.preview
                        }
                        this.title = response.data.propertyName
                    }
                    if (response.data.list.length) {
                        this.data = response.data.list
                        this.data.forEach(e => {
                            e.edit = false
                            if (e.status === '1') {
                                e.status = true
                            } else if (e.status === '0'){
                                e.status = false
                            }
                        })
                    }
                }
            })
        },
        // 获取实景图片
        getPictureList($event, index){
            var arr = []
            $event.forEach( element => {
                arr.push(element.response.data.picName)
            })
            this.data[index].pictureList = arr
        },
        //增加
        handleAdd () {
          this.data.push(
            {
              edit: true,
              status: true,
              serviceName: '',
              classification: '',
              createTimes: '',
              pictureList: []
            }
          )
        },
        // 编辑
        handleEdit (item, index) {
            item.edit = true
            this.data.splice(index,1, item)
        },
         // 保存
        handleSave (item, index) {
            item.createTimes = this.moment(item.createTimes).format('YYYY/MM/DD')
            item.account = this.$user.loginAccount
            item.yearId = this.yearId
            item.dictId = this.id
            this.$api.post('/member-reversion/provideService/save', item).then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功')
                    this.handleInit(1)
                }
            })
        },
        // 保存
        onSave () {
            let list = {
                account: this.$user.loginAccount,
                yearId: this.yearId,
                dictId: this.id,
                templateId: this.$template.id,
                textPreview: this.preview,
                isComplete: this.data.length ? true : false,
            }
            this.$api.post('/member-reversion/perfect/saveTextPreview', list).then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功')
                    this.$emit('on-save')
                    this.handleInit()
                }
            })
        },
        //删除
        handleDel (item, index) {
            this.$Modal.confirm({
                title: '是否确定删除',
                content: '是否确认删除？',
                onOk:()=>{
                    if (item.id) {
                        this.$api.post('/member-reversion/provideService/delete', {id: item.id}).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('删除成功')
                                this.data.splice(index,1)
                            }
                        })
                    } else {
                        this.$Message.success('删除成功')
                        this.data.splice(index,1)
                    }
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
    },
    mounted () {
        this.preview = `服务名称（），服务分类（），创建时间（）。`
    }
}
</script>
