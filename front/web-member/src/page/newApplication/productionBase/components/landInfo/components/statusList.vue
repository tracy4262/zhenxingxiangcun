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
          <Form :label-width="90" label-position="left" :ref="`data${index}`" v-for="(item, index) in data" :key="index" :model="item":rules="dataLine">
            <Row  :gutter="16">
              <Col span="8">
                <FormItem label="地块编码">
                  <!-- <Input v-model="item.number" :disabled="!edit"></Input> -->
                   <Select v-model="item.number" style="width:100" :disabled="!edit" @on-change="codeChange($event, index)">
                      <Option v-for="item in codeList" :value="item.landCode" :key="item.landCode">{{ item.landCode }}</Option>
                  </Select>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="地块名称">
                  <!-- <Input v-model="item.name" :disabled="!edit"></Input> -->
                   <Select v-model="item.number" style="width:100" :disabled="!edit" @on-change="codeChange($event, index)">
                      <Option v-for="item in codeList" :value="item.landCode" :key="item.landCode">{{ item.landName }}</Option>
                  </Select>
                </FormItem>
              </Col>
              <Col span="8">
               <FormItem label="类型编码">
                  <Select v-model="item.numberType" style="width:100" :disabled="!edit" @on-change="typeChange($event, index)">
                      <Option v-for="item in typeList" :value="item.value" :key="item.value">{{ item.value }}</Option>
                  </Select>
                  <!-- <Input v-model="item.numberType" :disabled="!edit"></Input> -->
                </FormItem>
              </Col>
            </Row>
            <Row  :gutter="16">
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
              <Col span="8">
                <FormItem label="折算面积" prop="conversionArea">
                  <Input v-model="item.conversionArea" :disabled="!edit" @on-change="handleNumAdd">
                  <span slot="append">平方千米</span>
                  </Input>
                </FormItem>
              </Col>
            </Row>
            <Row  :gutter="16">
              <Col span="8" offset="16" class="tr" v-if="edit && data.length != 1">
                <FormItem label="">
                  <Button  @click="handleDel(item, index)">删除</Button>
                </FormItem>
              </Col>
            </Row>
          </Form>
          <div class="pl80" v-if="edit">
           <!-- <Button type="primary" class="btn-light-primary" icon="md-add" ghost @click="handleAdd">添加</Button> -->
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
import {isNumber, isMoney3} from '~utils/validate'
  export default {
    components: {
      Divider
    },
    props: {
      title: '',
      type: '',
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
        typeList: [],
        codeList: [],
        data: [
          {
            number: '', // 地块编码
            name: '', // 地块名称
            numberType: '', // 编码类型
            typeName: '', // 类型名称
            area: '',  // 面积
            conversionArea:'', // 折算面积
          }
        ],
        dataLine: {
          area:[{validator:isMoney3,trigger: 'blur'}],
          conversionArea:[{validator:isMoney3,trigger: 'blur'}],
        },
        isConversions: [
          {value: '是', label: '是'},
          {value: '否', label: '否'}
        ],
        baseId: ''
      }
    },
    created() {
      this.baseId = this.$route.query.id
      // 取初始化下拉列表的数据
      this.handleSelectList()
    },
    methods: {
      // 取初始化下拉列表的数据
      handleSelectList () {
        this.$api.post('/member-reversion/landUse/dict', {
          type : this.type
        }).then(response => {
          if (response.code === 200) {
            this.typeList = response.data
          }
        })
        // 地块编码下拉数据
        this.$api.post('/member-reversion/productionBase/landInfo/getLandType',{
            account: this.$user.loginAccount,
            baseId: this.baseId
        }).then(response => {
            if (response.code == 200) {
              this.codeList.push(response.data)
              this.data[0].number = response.data.landCode
              this.data[0].name = response.data.landName
            }
        })
      },
      
      // 地块编码或者地块名称 发生改变的时候
      codeChange ($event, index) {
        this.codeList.forEach(e => {
          if (e.value == $event) {
            this.data[index].name = e.label
          }
        })
      },
      // 类型编码或者类型名称发生改变的时候
      typeChange ($event, index) {
        this.typeList.forEach(e => {
          if (e.value == $event) {
            this.data[index].typeName = e.label
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
      // 删除
      handleDel (item, index) {
        this.$Modal.confirm({
            title: '是否确定删除',
            onOk:()=>{
              if (item.id) {
                  this.$api.post('/member-reversion/landInfo/deleteLandUse', {id: item.id}).then(response => {
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
          number: '', // 地块编码
          name: '', // 地块名称
          numberType: '', // 编码类型
          typeName: '', // 类型名称
          area: '',  // 面积
          conversionArea:'', // 折算面积
        }
        this.data.push(list)
      },
      // 保存
      handleSave () {
        let flag = true
        /* for (let i = 0; i < this.data.length; i++) {
          this.$refs[`data${i}`][0].validate(v => {
            if (!v) {
              this.$Message.error('请核对表单信息')
              flag = v
            }
          })
        }  */
        if (flag) {
          let list = {
            account: this.$user.loginAccount,
            dictId: this.id,
            type: this.type,
            list: this.data,
            baseId: this.baseId
          }
          this.$api.post('/member-reversion/productionBase/landInfo/saveLandUse', list).then(response => {
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
