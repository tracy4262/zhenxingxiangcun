<template>
  <div>
    <Row>
      <Col span="17">
          <Select
              :disabled="disabled"
              placeholder="支持下拉模糊输入搜索"
              v-model="value"
              filterable
              remote
              @on-open-change="queryChange"
              @on-change="opctionChange"
              @on-clear="onClear"
              :remote-method="remoteMethod"
              :clearable="clearable"
              :loading="loading" class="mr60" style="width:100%;">
              <Option v-for="(option, index) in commonProductNameList" :value="option.label" :key="index">{{option.label}}</Option>
              </Select>
      </Col>
      <Col span="7">
          <Button type="primary" v-if="isShowAddBtn" @click="add">添加</Button>
          <Button type="primary" v-if="editBtn" @click="modify">补充</Button>  
      </Col>
    </Row>
  </div>
</template>
<script>
  export default {
    props: {
      values: String,
      disabled: {
        type: Boolean,
        default: false
      },
      clearable: {
        type: Boolean,
        default: false
      }
    },
    data () {
      return {
        commonProductNameList: [],
        isShowAddBtn: false,
        editBtn: false,
        value: '',
        loading: false,
        id: ''
      }
    },
    watch: {
      values: {
        handler(curVal, oldVal) {
          console.log('productName', curVal)
          this.value = curVal
        },
        deep: true
      }
    },
    created () {
      this.value = this.values
    },
    methods: {
      // 根据通用商品名带出产品分类
      opctionChange (value) {
        if (this.commonProductNameList.length) {
            this.commonProductNameList.forEach(element => {
              if (element.label === value) {
                this.id = element.value
                if (element.qualityCustomContent.length === 0) {
                  // this.$Message.info('该通用商品名缺失质量标准和安全标准，请补充完整！')
                  this.editBtn = true
                  this.isShowAddBtn = false
                } else {
                  this.editBtn = false
                  this.isShowAddBtn = false
                }
              }
            })
          } else {
            this.editBtn = false
            this.isShowAddBtn = false
          }
      },
      queryChange (flag) {
        if (!flag) {
          this.$emit('handle-get-result', this.value)
        }
      },
      onClear () {
        console.log('qingk')
        this.value = ''
        this.$emit('handle-get-result', this.value)
        this.commonProductNameList = []
        this.isShowAddBtn = true
        this.editBtn = false
      },
      // 新增通用商品名
      add () {
          this.$router.push('/pro/addCommonProduct')
      },
      modify () {
          this.$router.push({
              path: '/pro/addCommonProduct',
              query: {
              id: this.id
              }
          })
      },
      // 通用商品名 回显时要用到promise函数 先请求到数据才能根据value值回显
      remoteMethod (query) {
          var p = new Promise((resolve, reject) => {
            if (query !== '') {
                this.loading = true
                this.$api.post('/portal/shopCommdoity/findCurrencyCommodity', {
                    name: query
                }).then(response => {
                    if (response.code === 200) {
                        this.loading = false
                        this.commonProductNameList = []
                        response.data.forEach(element => {
                            this.commonProductNameList.push({
                            label: element.commodityName,
                            value: element.id,
                            productCode: element.productCode,
                            productName: element.productName,
                            qualityCustomContent: element.qualityCustomContent
                            })
                        })
                        resolve('success')
                    } else if (response.code === 300) { // 查询无此数据，添加按钮显示
                        this.loading = false
                        this.commonProductNameList = []
                        this.isShowAddBtn = true
                        this.editBtn = false
                        resolve('failed')
                    }
                }).catch(error => {
                    this.$Message.error('通用商品名查询错误！')
                    resolve('failed')
                })
            } else {
              this.commonProductNameList = []
              this.isShowAddBtn = true
              this.editBtn = false
              resolve('failed')
            }
          })
          return p
      },
    }
  }
</script>
