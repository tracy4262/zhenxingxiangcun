<template>
  <div style="background:#f9f9f9;" class="mb40">
   <!-- 第一产业 -->
        <div class="pd20" >
           <Row  :gutter="38" class="pb20">
            <Col span="8">
              <b  style="font-size:14px;">{{title}}</b>
            </Col>
            <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" @click="handleEdit" v-if="!edit">编辑</span>
            </Col>
          </Row>
          <Form :label-width="90" label-position="left" :rules="dataLine" v-for="(item, index) in data" :key="index" :ref="`data${index}`" :model="item">
            <Row  :gutter="16">
              <Col span="8">
               <FormItem label="类型编码">
                  <Select v-model="item.numberType" style="width:100" :disabled="!edit" @on-change="typeChange($event, index)">
                      <Option v-for="item in typeList" :value="item.value" :key="item.value">{{ item.value }}</Option>
                  </Select>
                  <!-- <Input v-model="item.numberType" :disabled="!edit"></Input> -->
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="类型名称">
                   <Select v-model="item.numberType" style="width:100%" :disabled="!edit" @on-change="typeChange($event, index)">
                      <Option v-for="item in typeList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                  </Select>
                  <!-- <Input v-model="item.typeName" :disabled="!edit"></Input> -->
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="面积" prop="area">
                  <Input v-model="item.area" :disabled="!edit">
                    <span slot="append">平方米</span>
                  </Input>
                </FormItem>
              </Col>
            </Row>
            <Row  :gutter="16">
              <Col span="8">
                <FormItem label="折算面积" prop="conversionArea">
                  <Input v-model="item.conversionArea" :disabled="!edit" @on-change="handleNumAdd">
                  <span slot="append">平方千米</span>
                  </Input>
                </FormItem>
              </Col>
              <Col span="8" offset="8" class="tr" v-if="edit && data.length != 1">
                <FormItem label="">
                  <Button  @click="handleDel(item, index)">删除</Button>
                </FormItem>
              </Col>
            </Row>
          </Form>
          <div class="pl80" v-if="edit">
           <Button type="primary" class="btn-light-primary" icon="md-add" ghost @click="handleAdd">添加</Button>
          </div>
          <div class="pd20 tc"  v-if="edit">
            <Button type="primary" @click="handleSave" >保存</Button>
          </div>
        </div>
        <Divider></Divider>
        <div class="pd20" style="background:#f9f9f9;">
          <p class="tr t-orange subtotal">小计:{{total}}平方千米</p>
        </div>
  </div>
</template>
<script>
import Divider from '~components/divider'
import {numAdd} from '~utils/utils'
import {isMoney3} from '~utils/validate'
  export default {
    components: {
      Divider
    },
    props: {
      title: '',
      type: '',
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
        activeIndex: 0,
        edit :true,
        total: '',
        data: [
          {
            type: '', // 数据联动
            number: '', // 地块编码
            name: '', // 地块名称
            numberType: '', // 编码类型
            typeName: '', // 类型名称
            area: '',  // 面积
            conversionArea:'', // 折算面积
          }
        ],
        dataLine: {
          area: [
            {validator: isMoney3, trigger: 'blur'},
          ],
          conversionArea: [
            {validator: isMoney3, trigger: 'blur'},
          ]
        },
        typeList: [],
        templateId: ''
      }
    },
    created() {
      this.templateId = this.$route.query.templateId
      // 取初始化下拉列表的数据
      this.handleSelectList()
    },
    methods: {
      // 取初始化下拉列表的数据
      handleSelectList () {
        this.$api.post('/member-reversion/landUse/dict', {
          type : this.type,
          templateId: this.templateId
        }).then(response => {
          if (response.code === 200) {
            this.typeList = response.data
          }
        })
      },
      // 获取数据
      getData (data) {
        this.data = data
        this.edit = false
        this.handleNumAdd()
      },
      // 计算小计
      handleNumAdd () {
        this.total = 0
        this.data.forEach(e => {
          this.total = numAdd(parseFloat(this.total ? this.total : 0).toFixed(2), parseFloat(e.conversionArea ? e.conversionArea : 0).toFixed(2))
        })
        this.$emit('on-numAdd')
      },
      typeChange ($event, index) {
        this.typeList.forEach(e => {
          if (e.value == $event) {
            this.data[index].typeName = e.label
          }
        })
      },
      // 删除
      handleDel (item, index) {
        this.$Modal.confirm({
            title: '是否确定删除',
            onOk:()=>{
              if (item.id) {
                  this.$api.post('/member-reversion/landUse/delete', {id: item.id}).then(response => {
                      if (response.code === 200) {
                          this.data.splice(index, 1)
                          this.$Message.success('删除成功！')
                          this.handleNumAdd()
                      }
                  })
              } else {
                  this.data.splice(index, 1)
                  this.$Message.success('删除成功！')
                  this.handleNumAdd()
              }
            },
            okText:'确定',
            cancelText:'取消'
        })
      },
      // 添加
      handleAdd () {
        let list =  {
          
        }
        this.data.push(list)
      },
      // 保存
      handleSave () {
        let flag = true
        for (let i = 0; i < this.data.length; i++) {
          this.$refs[`data${i}`][0].validate(v => {
            if (!v) {

              this.$Message.error('请核对表单信息')
              flag = v
            }
          })
        } 
        if (flag) {
          let list = {
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            type: this.type,
            list: this.data,
            templateId: this.templateId
          }
          this.$api.post('/member-reversion/landUse/save', list).then(response => {
              if (response.code === 200) {
                  this.$Message.success('保存成功')
                  this.edit = false
                  this.$emit('on-init',this.type)
              }
          })
        }
      },
      handleEdit () {
        this.edit = true
      }
    }
  }
</script>
