<template>
  <div class="pd20">
    <Title :title="title" edit :id="id" ></Title>
    <Form :label-width="100" label-position="left" class="pd20 mt40">
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
        <Col span="5" v-else>
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
    <Title title="文字预览" class="mt40"></Title>
    <div class="pd20 tc pt30">
      <Input v-model="textPreview.text_preview" type="textarea" :autosize="{minRows: 4,maxRows: 10}" ></Input>
      <Button type="primary" :loading="loading" @click="handleSave" class="mt40">保存</Button>
    </div>
  </div>
</template>

<script>
import Title from '../../components/title'
import {isMoney3} from '~utils/validate'
export default {
  props: {
    id: {
      type: String
    },
    appId: {
      type: String
    }
  },
  components: {
    Title
  },
  data () {
    return {
      data: [],
      status: true,
      textPreview: {},
      title: '国土面积信息',
      ruleInline: {
        area: [
          {validator: isMoney3, trigger: 'blur'},
        ]
      },
      loading: true,
      baseId: ''
    }
  },
  created() {
    this.baseId = this.$route.query.id
  },
  methods: {
    initTitle () {
      this.$api.post('/member-reversion/productionBase/findTableHead', {
        account: this.$user.loginAccount,
        dictId: this.id
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
      this.$api.post('/member-reversion/productionBase/physicalGeography/findLandAreaInfo', {
        user_id: this.$user.loginAccount,
        parent_id: this.id,
        baseId: this.baseId
      }).then(response => {
        if (response.code === 200) {
          this.data = response.data.landAreaInfo
          this.status = response.data.status
          this.textPreview = response.data.textPreview
          this.loading = false
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
        this.textPreview.is_complete = '1'
        let list = {
          landAreaInfo: {
            landAreaInfo: this.data,
            status: this.status,
            landAreaInfo_name: this.title,
          },
          textPreview: this.textPreview,
          sys_dict_id: this.id,
          user_id: this.$user.loginAccount,
          baseId: this.baseId
        }
        this.loading = true
        this.$api.post('/member-reversion/productionBase/physicalGeography/saveLandAreaInfo', list).then(response => {
          if (response.code === 200) {
            this.$Message.success('保存成功')
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
              this.$api.post('/member-reversion/productionBase/physicalGeography/deleteLandAreaInfo',{id: item.id}).then(response => {
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
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
