<template>
  <Modal v-model="modalShow" width="1200" :mask-closable="false" title="入库" :styles="{top: '60px'}">
    <div>
      <!-- 用户填写入库单，但暂无产品时，给出弹出消息提示 -->
      <p>注：如果您没有添加过该商品，请您前往库存管理完成该商品的添加！</p>
      <Form ref="info" :model="info" label-position="right" :label-width="100" :rules="ruleInline">
        <div class="store_info">基本信息</div>
        <Row :gutter="32" class="pl20 pr20">
          <Col span="12">
            <FormItem label="产品编码" prop="productCode">
              <!-- <Input v-model="info.productCode" readonly /> -->
              <Select v-model="info.productCode" @on-change="onChange">
                  <Option v-for="(item,index) in productCodeList" :value="item.productCode" :key="index">{{ item.productCode }}-{{item.productName}}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="产品名称" prop="productName">
              <Input v-model="info.productName" readonly />
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32" class="pl20 pr20">
          <Col span="12">
            <FormItem label="通用商品名称" prop="commodityName">
              <Input v-model="info.commodityName" readonly />
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="产品分类" prop="productClassifyName">
              <Input v-model="info.productClassifyName" readonly />
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32" class="pl20 pr20">
          <Col span="12">
            <FormItem label="数量" prop="number">
              <InputNumber :max="100000000" :min="0" :step="1" :precision="2" :active-change="false" v-model="info.number" style="width: 100%;" @on-change="change"></InputNumber>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="计量单位" prop="unit">
              <Select v-model="info.unit" filterable>
                <Option v-for="(item, index) in units" :value="item" :key="index">{{ item }}</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32" class="pl20 pr20">
          <Col span="12">
            <FormItem label="单价" prop="price">
              <InputNumber :max="100000000" :min="0" :step="1" :precision="2" :active-change="false" v-model="info.price" style="width: 95%;" @on-change="change"></InputNumber>&nbsp;&nbsp;元
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="合计" prop="totalPrice">
              <Input v-model="info.totalPrice" readonly />
            </FormItem>
          </Col>
        </Row>
        <div class="store_info">入库信息</div>
        <Row :gutter="32" class="pl20 pr20">
          <Col span="12">
            <FormItem label="批次号" prop="batchNumber">
              <Input v-model="info.batchNumber" readonly />
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="入库仓库" prop="inStore">
              <Select v-model="info.inStore">
                  <Option v-for="(item,index) in inStoreList" :value="item.id" :key="index">{{ item.storeName }}</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32" class="pl20 pr20">
          <Col span="12">
            <FormItem label="自定义子类" prop="customName">
              <Input v-model="info.customName" readonly />
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="入库类型" prop="inStoreType">
              <Select v-model="info.inStoreType">
                <Option v-for="(item,index) in inStoreTypeList" :value="item.id" :key="index">{{ item.type }}</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32" class="pl20 pr20">
          <Col span="12">
            <FormItem label="经手人" prop="operatorAccount">
              <Input v-model="info.operatorAccount" readonly />
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="入库日期" prop="createTime">
              <Input v-model="info.createTime" readonly />
            </FormItem>
          </Col>
        </Row>
      </Form>
      <div class="tc mt20">
        <Button type="default" @click="modalShow = false">取消</Button>
        <Button type="primary" class="ml10" @click="saveStore(0)">保存并关闭</Button>
        <Button type="primary" class="ml10" @click="saveStore(1)">保存并录入下一条</Button>
      </div>
    </div>
    <div slot="footer">
    </div>
    <storage ref="storage"></storage>
  </Modal>
</template>

<script>
import storage from '../../../inventoryControl/component/storage'
import {numAdd, numMulti} from '~utils/utils'
export default {
  components: {
    storage
  },
  data () {
    return {
      showTip: false,
      number: '',
      inStoreList: [],
      inStoreTypeList: [],
      units: [],
      info: {
        productCode: '',
        productName: '',
        commodityName: '',
        productClassifyName: '',
        number: 0,
        unit: '',
        price: 0,
        totalPrice: '',
        batchNumber: '',
        inStore: '', // id
        customName: '',
        inStoreType: '', // id
        operatorAccount: '',
        createTime: ''
      },
      ruleInline: {
        productCode: [
          { required: true, type: 'string', message: '请选择产品编码', trigger: 'change' }
        ],
        productName: [
          { required: true, type: 'string', message: '请填写产品名称', trigger: 'change' }
        ],
        commodityName: [
          { required: true, type: 'string', message: '请填写通用商品名', trigger: 'change' }
        ],
        productClassifyName: [
          { required: true, type: 'string', message: '请填写产品分类', trigger: 'change' }
        ],
        number: [
          { required: true, type: 'number', message: '请填写产品数量', trigger: 'change' }
        ],
        unit: [
          { required: true, type: 'string', message: '请填写计量单位', trigger: 'change' }
        ],
        price: [
          { required: true, type: 'number', message: '请填写产品单价', trigger: 'change' }
        ],
        totalPrice: [
          { required: true, type: 'number', message: '请填写产品合计', trigger: 'change' }
        ],
        batchNumber: [
          { required: true, type: 'string', message: '请填写批次号', trigger: 'change' }
        ],
        inStore: [
          { required: true, type: 'number', message: '请选择入库仓库', trigger: 'change' }
        ],
        customName: [
          { required: true, type: 'string', message: '请填写自定义子类', trigger: 'change' }
        ],
        inStoreType: [
          { required: true, type: 'number', message: '请填写入库类型', trigger: 'change' }
        ],
        operatorAccount: [
          { required: true, type: 'string', message: '请填写经手人', trigger: 'change' }
        ],
        createTime: [
          { required: true, type: 'string', message: '请填写入库日期', trigger: 'change' }
        ]
      },
      pageSize: 10,
      pageNum: 1,
      total: 0,
      modalShow: false,
      productCodeList: []
    }
  },
  created () {
      this.initStore()
      this.initProductCode()
      this.initInType()
  },
  methods: {
    // 根据入库单号 获得入库单数据显示入库单
    getEnterOrderList (order) {
      console.log('关闭')
      this.modalShow = false
      if (order) {
        this.$api.post('/shop/inventory/basicSetting/enterOrder', {
          account:this.$user.loginAccount,
          order: order
        }).then(response => {
          if (response.code === 200) {
          this.$refs['storage'].init(response.data, response.data.list)
          }
        })
      }
    },
    // 初始化入库仓库
    initStore () {
      this.$api.post('/shop/inventory/basicSetting/storeFind', {
        account: this.$user.loginAccount,
        pageSize: 1000,
        pageNum: 1,
        key: '',
        status: 1
      }).then(response => {
        if (response.code === 200) {
          this.inStoreList = response.data.list
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 初始化入库类型
    initInType () {
      this.$api.post('/shop/inventory/basicSetting/inStoreList', {
        account: this.$user.loginAccount,
        pageSize: 1000,
        pageNum: 1
      }).then(response => {
        if (response.code === 200) {
          this.inStoreTypeList = response.data
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 根据通用商品名称的 id 在入库的时候查询，有没有这个商品，如果有就不提示，没有就显示提示语
    searchProduct (id) {
      this.$api.post('/shop/inventory/basicSetting/storeCombineList', {
        account: this.$user.loginAccount,
        pageNum: 1,
        pageSize: 10,
        productClassify: '',
        productName: '',
        productCode: '', // 这个页面没有这几个查询条件的字段
        inStore: '',
        customId: '',
        commodityId: id
      }).then(response => {
        console.log('list', response)
        if (response.code === 200) {
          let data = response.data.list
          if (data.length) {
            this.showTip = false
          } else {
            this.showTip = true
          }
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 数据初始化
    initAdd (goodList) {
      // 清空已选择数据
      console.log('goodlist', goodList)
      this.$refs['info'].resetFields()
      this.info.batchNumber = 'PCH' + new Date().getTime()
      this.info.createTime = this.moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
      this.modalShow = true
    },
    // 初始化产品编码
    initProductCode () {
      this.$api.post('/shop/inventory/basicSetting/productCodeList', {
        account: this.$user.loginAccount
      }).then(response => {
        console.log('code', response)
        if (response.code === 200) {
          this.productCodeList = response.data
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 选择产品编码后
    onChange (value) {
      this.productCodeList.forEach(element => {
        if (value === element.productCode) {
          this.info.productName = element.productName
          this.info.commodityName = element.commodityName
          this.info.commodityName = element.commodityName
          this.info.productClassifyName = element.classifyName
          this.info.customName = element.customName
          this.info.operatorAccount = element.operatorAccount
          this.units = element.unit
        }
      })
    },
    // 改变数量或者单价
    change () {
      this.info.totalPrice = parseFloat((numMulti(this.info.price, this.info.number)).toFixed(2))
    },
    // 点击保存
    saveStore (type) {
      this.$refs['info'].validate((valid) => {
        if (valid) {
          this.info.account = this.$user.loginAccount
          this.$api.post('/shop/inventory/basicSetting/enterStoreSave', this.info).then(response => {
            console.log('res', response)
            if (response.code === 200) {
              this.$Message.success('入库成功！')
              if (type === 0) { // 保存并关闭
                this.getEnterOrderList(response.data)
                // this.modalShow = false
              } else { // 保存并录入下一条
                this.initAdd()
              }
            } else if (response.code === 400) {
              this.$Message.info('产品名称已存在！')
            } else {
              this.$Message.error('服务器异常！')
            }
          }).catch(error => {
            this.$Message.error('服务器异常！')
          })
        } else {
            this.$Message.error('请核对表单字段！')
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.store_info{
  color: #4A4A4A;
  font-size: 14px;
  padding-left: 10px;
  border-left: 6px solid #56B07D;
  margin: 20px 0;
}
.mark_name{
  display: inline-block;
  width: 90px;
  text-align: right;
  margin-right: 10px;
  margin-bottom: 26px;
}

</style>
