<template>
  <div class="pd20">
    <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh"></Title>
    <Form :label-width="100" label-position="left" class="pd20 mt40">
      <Row  :gutter="38">
        <Col span="8">
          <FormItem label="权限">
            <Switch class="ml20"  size="large" v-model="status">
              <span slot="open">公开</span>
              <span slot="close">隐藏</span>
            </Switch>
          </FormItem>
        </Col>
      </Row>
      <Row  :gutter="40" class="pb20 mt10">
        <Col span="5"><b>国土面积信息</b></Col>
        <Col span="5" class="tc"><b>面积</b></Col>
        <Col span="5" class="tc"><b>计量单位</b></Col>
        <Col span="5" class="tc"><b>占比</b></Col>
        <Col span="3"><b class="pl15">操作</b></Col>
      </Row>
      <!-- <Row  :gutter="40" class="pb20">
         <Col span="5"> 国土总面积 </Col>
        <Col span="5">
          <Input v-model="data.land_area_name" @on-change="calculation"></Input>
        </Col>
        <Col span="5" class="tc">
          平方公里
        </Col>
      </Row> -->
      <Row  :gutter="40" v-for="(item, index) in data" :key="index">
        <Col span="5" v-if="index < 6">
          <p style="cursor: pointer;" class="ell">
            <span v-if="index != 0">其中：</span> {{item.land_area}}
          </p>
        </Col>
        <Col span="5" v-if="index >= 6">
          <Input v-if="item.edit" v-model="item.land_area" placeholder="请输入" :ref="`land${index}`" @on-blur="handleOnBlur(item, index, `input${index}`)" :maxlength="20"></Input>
          <p @click="handleEdit(item, index, `land${index}`)" style="cursor: pointer;" v-else class="ell">
            其中：{{item.land_area}}<Icon type="ios-create-outline" size="18" class="ml5"/>
          </p>
        </Col>
        <Col span="5">
        <Form :ref="`data${index}`" :rules="ruleInline" :model="item">
          <FormItem prop="area">
            <Input v-model="item.area" @on-change="calculation(index)" :maxlength="20"></Input>
          </FormItem>
        </Form>
        </Col>
        <Col span="5" class="tc">
          平方公里
        </Col>
        <Col span="5" v-if="index != 0">
          <Input v-model="item.proportion" readonly></Input>
        </Col>
        <Col span="3" v-if="index > 5">
         <Button  @click="handleDel(item, index)" v-if="data.length > 1">删除</Button>
        </Col>
      </Row>
      <Row  :gutter="40">
        <Col span="5"><Button type="primary" @click="handleAdd">增加地址</Button></Col>
      </Row>
    </Form>
    <div class="tc">
      <Button type="primary"  @click="handleSave" class="mt40">保存</Button>
    </div>
  </div>
</template>

<script>
import {isMoney3} from '@/utils/validate'
export default {
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
  components: {
  },
  data () {
    return {
      data: [],
      status: true,
      textPreview: {},
      title: '国土面积信息',
      templateId: '',
      ruleInline: {
        area: [
          {validator: isMoney3, trigger: 'blur'},
        ]
      },
      isLoading: true
    }
  },
  created() {
    this.templateId = this.$route.query.templateId
    // this.handleInit ()
  },
  methods: {
    initShow () {
      // 初始化页面置空
    },
    initTitle () {
      this.$api.post('/member-reversion/user/perfect/findTableHead', {
        account: this.$user.loginAccount,
        yearId: this.yearId,
        dictId: this.id,
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          if (response.data.propertyName) {
            this.title = response.data.propertyName
          }
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    //初始化取数据
    handleInit () {
      this.$api.post('/member-reversion/physicalGeography/findLandAreaInfo', {
        user_id: this.$user.loginAccount,
        year_id: this.yearId,
        parent_id: this.id,
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          this.isLoading = false
          // this.title = response.data.landAreaInfo_name
          this.data = response.data.landAreaInfo
          this.status = response.data.status
          this.textPreview = response.data.textPreview
        }
      })
    },
    // 算数
    calculation (index) {
      let all = this.data[0].area
      let str = ''
      for (let index = 0; index < this.data.length; index++) {
        const element = this.data[index];
        // 如果总面积跟 单个面积都有值，这计算占比
        if (index != 0 && element.area && all) {
          element.proportion = `${parseFloat(element.area/all * 100).toFixed(2)}%`
        }
        if (index == 0 && all) {
          str += `${element.land_area}${element.area}，`
        } else if (element.land_area && element.area && all) {
          str += `其中：${element.land_area}占${element.proportion}，`
        }
      }
      if (str) {
        this.textPreview.text_preview = `${str.substring(0,str.length-1)}。`
      }
    },
    // 保存
    handleSave () {
      let flag = true
      for (let i = 0; i < this.data.length; i++) {
        this.$refs[`data${i}`][0].validate(v => {
          if (!v) {
            flag = false
          }
        })
      }
      if (flag) {
        this.isLoading = true
        this.textPreview.is_complete = '1'
        let list = {
          landAreaInfo: {
            landAreaInfo: this.data,
            status: this.status,
            landAreaInfo_name: this.title,
          },
          textPreview: this.textPreview,
          sys_dict_id: this.id,
          yearId: this.yearId,
          user_id: this.$user.loginAccount,
          templateId: this.templateId
        }
        this.$api.post('/member-reversion/physicalGeography/saveLandAreaInfo', list).then(response => {
          if (response.code === 200) {
            this.$Message.success('保存成功')
            this.$emit('on-save')
            this.handleInit()
          }
        })
      } else {
        this.$Message.error('请核对表单信息')
      }
    },
    // 删除
    handleDel (item, index) {
      this.$Modal.confirm({
          title: '是否确定删除',
          onOk:()=>{
            if (item.id) {
              this.$api.post('/member-reversion/physicalGeography/deleteLandAreaInfo',{id: item.id}).then(response => {
                if (response.code === 200) {
                  this.data.splice(index, 1)
                  this.$Message.success('删除成功!')
                  this.calculation()
                }
              })
            } else {
              this.data.splice(index, 1)
              this.$Message.success('删除成功!')
              this.calculation()
            }
          },
          okText:'确定',
          cancelText:'取消'
      })
    },
    // 双击编辑
    handleEdit (item, index, ref) {
      this.data.forEach(e => {
        e.edit = false
      })
      item.edit = true
      this.data.splice(index, 1, item)
      // 数据更新后自动获取输入框的焦点
      this.$nextTick(e => {
        this.$refs[`land${index}`][0].focus()
      })
    },
    // 失去焦点保存
    handleOnBlur (item, index, ref) {
      item.edit = false
      this.data.splice(index, 1, item)
    },
    // 增加地址
    handleAdd () {
      let list = {land_area: '', area: '', proportion: '', edit: true}
      this.data.push(list)
    },
    leftRefresh () {
        this.$emit('left-refresh')
    }
  }
}
</script>

<style lang="less" scoped>

</style>
