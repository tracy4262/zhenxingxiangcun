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
          <Form :label-width="80" label-position="left" v-for="(item, index) in data" :ref="`data${index}`" :key="index" :model="item" :rules="dataLine">
            <Row  :gutter="38" >
              <Col span="9">
                <FormItem label="产业名称">
                  <national-economy-control :value="item.name" :active="index"  @on-get-data="onGetEconomyData($event, index)" :disabled="!edit"></national-economy-control>
                </FormItem>
              </Col>
              <Col span="9">
                <FormItem label="增加值" prop="price">
                  <Input v-model="item.price" :maxlength="20"  :disabled="!edit" @on-change="handleNumAdd"> <span slot="append">万元</span></Input>
                </FormItem>
              </Col>
              <Col span="6" class="tr" v-if="edit && data.length != 1">
                <Button  @click="handleDel(item, index)">删除</Button>
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
          <p class="tr t-orange subtotal">产值小计:{{total}}万元</p>
        </div>
  </div>
</template>
<script>
import Divider from '~components/divider'
import nationalEconomyControl from '~components/national-economy-control'
import {numAdd} from '~utils/utils'
import {isNumber, isMoney3} from '~utils/validate'
  export default {
    components: {
      Divider,
      nationalEconomyControl
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
        edit :true,
        total: '',
        data: [{
            name: '',
            price: ''
          }],
        dataLine: {
          price:[{validator:isMoney3,trigger: 'blur'}],
        },
        templateId: '',
        saveisloading: false
      }
    },
    // watch: {
    //   data: {
    //     handler (newVal, oldVal) {
    //       this.getData(newVal)
    //     },
    //     deep: true
    //   }
    // },
    created () {
      this.templateId = this.$route.query.templateId
    },
    methods: {
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
          this.total = numAdd(parseFloat(this.total ? this.total : 0).toFixed(2), parseFloat(e.price ? e.price : 0).toFixed(2))
        })
        this.$emit('on-numAdd')
      },
      // 删除
      handleDel (item, index) {
        this.$Modal.confirm({
            title: '是否确定删除',
            onOk:()=>{
              if (item.id) {
                  this.$api.post('/member-reversion/ecoSocial/deleteIndustry', {id: item.id}).then(response => {
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
      // 获取产业名称
      onGetEconomyData ($event, index) {
        this.data[index].name = $event
      },
      // 添加
      handleAdd () {
        let list = {
          name: '',
          price: ''
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
          this.$api.post('/member-reversion/ecoSocial/saveIndustry', list).then(response => {
              if (response.code === 200) {
                  this.$Message.success('保存成功')
                  this.edit = false
                  this.$emit('on-init', this.type)
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
