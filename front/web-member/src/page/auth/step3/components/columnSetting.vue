<template>
   <div class="pd20">
      <Row class="mb20 tc pt30" :gutter="16">
        <Col span="4"> <b>栏目名称</b> </Col>
        <Col span="7"><b>栏目归属</b></Col>
        <Col span="3"><b>是否显示</b></Col>
        <Col span="5"><b>访问权限</b></Col>
        <Col span="5"><b>操作</b></Col>
      </Row>
      <div>
        <draggable v-model="data" :options="{handle: '.draggable-selected'}" @end="update">
          <Row v-for="(item, index) in data" :key="index" type="flex" align="middle" class="pd10 tc" :gutter="16" :style="{'background': draggableIndex === index ? '#f9f9f9' : '#fff'}">
            <Col span="4" v-if="data.length > 1">
              <Input v-if="item.edit" v-model="item.columnName" placeholder="请输入" :ref="`input${index}`" @on-blur="handleOnBlur(item, index, `input${index}`)" :maxlength="6"></Input>
              <p @click="handleEdit(item, index, `input${index}`)" style="cursor: pointer;" v-else>
                {{item.columnName}}
                <Icon type="ios-create-outline" size="18" class="ml5"/>
              </p>
            </Col>
            <Col span="4" v-else>
              <p style="cursor: pointer;">
                {{item.columnName}}
              </p>
            </Col>
            <Col span="7" >
            <Form class="attributions" :ref="`attributions${index}`" :model="item" :rules="attributionRules" :label-width="20">
              <FormItem prop="attributionArr" label=" ">
                <!-- <Select v-model="item.attribution"  @on-change="handleFormat" @on-open-change="handleAttributionChange($event, index)">
                    <Option v-for="(item, index) in attributions" :value="item.label" :key="index">{{ item.label }}</Option>
                </Select> -->
                <Cascader 
                :data="attributions"
                @on-change="handleFormat"
                @on-visible-change="handleAttributionChange($event, index)"
                v-model="item.attributionArr"
                clearable>
                <!-- <Input v-model="item.attribution" icon="md-arrow-dropdown" readonly placeholder="请选择"/> -->
              </Cascader>
              </FormItem>
            </Form>
              <!-- <Cascader 
                :data="attributions" 
                change-on-select 
                @on-change="handleFormat" 
                @on-visible-change="handleAttributionChange($event, index)"
                clearable>
                <Input v-model="item.attribution" icon="md-arrow-dropdown" readonly placeholder="请选择"/>
              </Cascader> -->
            </Col>
            <Col span="3">
              <Switch v-model="item.display" size="large" :disabled="item.columnName === '动态' ? true : false" @on-change="handleSwitchChange($event, item)">
                <span slot="open">启用</span>
                <span slot="close">隐藏</span>
              </Switch>
            </Col>
            <Col span="5">
              <Select v-model="item.authority" @on-change="handleChange($event, item)" style="width:150px;">
                <Option v-for="(item,index) in author" :key="index" :value="item.value" >{{ item.label }}</Option>
              </Select>
            </Col>
            <Col span="5">
                <Button class="draggable-selected mr10">拖动排序</Button>
                <Poptip transfer confirm title="您确定要删除此栏目吗？" @on-ok="handleDel(index)" v-if="data.length > 1">
                    <Button>删除</Button>
                </Poptip>
            </Col>
          </Row>
        </draggable>
      </div>
      <Row class="pt20 tc" :gutter="16" v-if="data.length < 15">
        <Col span="4"><Button type="primary" size="small" @click="handleAdd"> 增加栏目</Button></Col>
      </Row>
  </div>
</template>
<script>
  import draggable from 'vuedraggable'
  export default {
    components: {
      draggable
    },
    data () {
      return{
        attributionRules: {
          attributionArr: [{required: true, type: 'array', min: 1, message: '请填选择栏目归属'}]
        },
        loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
        data: [],
        author: [
          {
            value: 0,
            label: '所有人可见'
          }, {
            value: 1,
            label: '仅自己可见'
          }, {
            value: 2,
            label: '仅好友可见'
          }
        ],
        attributions: [],
        activeIndex: 0,
        draggableIndex: '',
        selectedData: []
      }
    },
    created(){
      // 初始化获取栏目归属数据
      // 获取会员产品的数据
      // this.$api.get('/portal/shopCommdoity/findMallClassification').then(res => {
      //   res.data.forEach(e => {
      //     e.value = e.icon
      //   })
      //   this.attributions[5].children = res.data
      // })
      // 获取会员标准 的 党务 数据
      // this.$api.get('/member/infoClass/policy/party').then(res => {
      //   this.attributions[4].children[0].children = res.data
      // })
      // 获取会员标准 的 政务 数据
      // this.$api.get('/member/infoClass/policy/affairs').then(res => {
      //   this.attributions[4].children[1].children = res.data
      // })
      this.$api.get('/member-reversion/columnSetting/columnDict').then(response => {
        if (response.code === 200) {
          this.attributions = response.data.attributions
        }
      })
    },
    methods:{
      submit () {
        let flag = false
        this.data.forEach((e, index) => {
          this.$refs[`attributions${index}`][0].validate(v => {
            if (!v) {
              flag = true
            }
          })
        })
        if (flag) {
          this.$Message.error('请选择栏目归属')
          return false
        } else {
          return this.data
        }
      },
      init (data) {
        console.log('333333333333')
        data.forEach(e => {
          e.edit = false
          if (e.attributionId) {
            e.attributionArr = e.attributionId.split('/')
          }
        })
        this.data = data
        console.log(this.data)
      },
      // 切换状态
      handleSwitchChange ($event, item) {
        item.display = $event
      },
      // 权限
      handleChange ($event, item) {
        console.log($event)
        item.authority = $event
      },
      // 当栏目归属 下拉框触发
      handleAttributionChange ($event, index) {
        this.activeIndex = index
      },
      // 当选择完成后的回调，
      handleFormat (labels, selectedData) {
        /* let arr = []
        this.attributions.forEach(e => {
          if (e.label == this.data[this.activeIndex].attribution) {
            this.data[this.activeIndex].attributionId = e.value
          }
        }) */
        console.log('selectedData', selectedData)
        let valueArr = []
        let labelArr = []
        selectedData.forEach(element => {
          valueArr.push(element.value)
          labelArr.push(element.label)
        })
        this.data[this.activeIndex].attributionId = valueArr.join('/')
        this.data[this.activeIndex].attribution = labelArr.join('/')
        console.log('this.data[this.activeIndex].attributionId', this.data[this.activeIndex].attributionId )
        console.log('this.data[this.activeIndex].attribution', this.data[this.activeIndex].attribution)
      },
      // 增加栏目
      handleAdd () {
        // if (this.data.length >= 8) {
        //   this.$Message.warning('最多只能添加8个栏目！')
        //   return
        // }
        let obj = {attributionArr: [], attribution: '', attributionId: '', columnName: '', authority: 0, display: true, edit: true}
        this.data.push(obj)
        this.$nextTick(e => {
          this.handleEdit(obj, this.data.length-1, `input${this.data.length-1}`)
        })
      },
      // 编辑栏目名称
      handleEdit (item, index, ref) {
        this.data.forEach(e => {
          e.edit = false
        })
        item.edit = true
        this.data.splice(index, 1, item)
        // 数据更新后自动获取输入框的焦点
        this.$nextTick(e => {
          console.log(ref)
          console.log(this.$refs[`input${index}`])
          console.log(this.$refs)
          this.$refs[`input${index}`][0].focus()
        })
      },
      // 输入框失去焦点后的事件
      handleOnBlur (item, index, ref) {
        // this.data.forEach(e => {
        //   e.edit = false
        // })
        item.edit = false
        if (item.columnName) {
          this.data.splice(index, 1, item)
        } else {
          this.data.splice(index, 1)
        }
      },
      // 拖拽结束后
      update (e) {
        console.log(e)
        e.newIndex
        e.oldIndex
        this.draggableIndex = e.newIndex
      },
      // 点击删除
      handleDel (index) {
         this.data.splice(index, 1)
      }
    }
  }
</script>
<style>
.color-keyword{
  background-color: #f9f9f9;
}
.attributions .ivu-form-item{
  margin-bottom: 0px;
}
</style>
