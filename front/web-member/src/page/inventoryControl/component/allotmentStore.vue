<template>
<div>
  <div>
    <div class="store_info">选择调拨的产品</div>
    <Form :label-width="100" ref="info" :model="info">
      <Row>
        <Col span="5">
          <FormItem label="产品分类">
            <vuiProduct ref="vuiProduct" 
              :values="info.productClassifyName" 
              @on-save="onSaveProductClassify" 
              @on-save-id="onSaveProductClassifyId" 
              :num="1"></vuiProduct>
          </FormItem>
        </Col>
        <Col span="5">
          <FormItem label="产品名称">
            <Input v-model="info.productName"/>
          </FormItem>
        </Col>
        <Col span="5">
          <FormItem label="所在仓库">
            <Select v-model="info.inStore" clearable>
                <Option v-for="(item,index) in inStoreList" :value="item.id" :key="index">{{ item.storeName }}</Option>
            </Select>
          </FormItem>
        </Col>
        <Col span="5">
          <FormItem label="自定义分类">
            <Select v-model="info.customId" clearable>
                <Option v-for="(item,index) in customIds" :value="item.id" :key="index">{{ item.customName }}</Option>
            </Select>
          </FormItem>
        </Col>
        <Col span="4" class="tr pr20">
          <Button type="success" @click="onSearch">查询</Button>
        </Col>
      </Row>
    </Form>
    <div style="padding:20px">
      <Table border ref="selection" 
      :columns="columns" 
      :data="dataList" 
      @on-selection-change="selectChange"></Table>
      <Page class="tr pt30 pb10" :total="total" @on-change="getNextPage" :page-size="pageSize" :current="pageNum"></Page>
    </div>
    <div class="choose ">
      <p class="choose_text">已选择：</p>
      <ul>
        <li v-for="(item, index) in selectData">
          <span class="choose_name">{{item.productName}}</span>
          <span class="choose_count">{{item.number}}{{item.unit}}</span>
        </li>
      </ul>
      <div class="mt20 pl30">合计：{{totalPrice}} 元</div>
    </div>
    <Divider />
    <div class="store_info">填写调拨信息</div>
     <Form ref="transfer" :model="transfer" label-position="right" :label-width="120" :rules="ruleInline">
        <Row>
          <Col span="8">
            <FormItem label="调拨的目的仓库：" prop="targetStore">
             <Select v-model="transfer.targetStore" style="width:240px" >
                <Option v-for="(item,index) in inStoreList" :value="item.id" :key="index">{{ item.storeName }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="经手人：" prop="operatorAccount">
              <Input v-model="transfer.operatorAccount" readonly />
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="调拨日期：" prop="createTime">
              <Input v-model="transfer.createTime" readonly />
            </FormItem>
          </Col>
        </Row>
      </Form>
  </div>
  <div class="tc mt20">
      <Button type="default" @click="$emit('close')">取消</Button>
      <Button type="primary" class="ml10" @click="saveStore(0)">确定调拨并关闭</Button>
      <!-- <Button type="success" class="ml10" @click="saveStore(1)">确定调拨并继续</Button> -->
  </div>
</div>
</template>

<script>
import {numAdd, numMulti} from '~utils/utils'
import vuiProduct from '~components/vui-product'
export default {
  components: {
    vuiProduct
  },
  data () {
    return {
      info: {
        productClassifyName: '', // 产品分类：
        productClassifyId: '',
        productName: '', // 产品名称：
        inStore: '', // 所在仓库：
        customId: '', // 自定义分类：
        productCode: '',
        commodityId: '',
      },
      total: 20,
      pageSize: 10,
      pageNum: 1,
      columns: [
        {
          type: 'selection',
          width: 60,
          align: 'center',
          fixed: 'left'
        },
        {
          title: '调拨数量',
          key: 'number',
          align: 'center',
          width: 150,
          fixed: 'left',
          render: (h, params) => {
            return h('div', [
              h('InputNumber', {
                props: {
                  max: parseFloat(params.row.totalStore),
                  min: 0,
                  step: 1,
                  precision: 2,
                  activeChange: false,
                  value: params.row.number
                },
                on: {
                  'on-change': (value) => {
                    // 改变number绑定的值
                    this.dataList[params.index].number = value
                    console.log(this.dataList[params.index])
                    if (this.selectDatas[this.pageNum]) {
                      this.selectDatas[this.pageNum].forEach((element, index) => {
                        if (element.id === this.dataList[params.index].id) {
                          console.log('有一样的')
                          let obj = Object.assign({},this.dataList[params.index])
                          this.selectDatas[this.pageNum].splice(index, 1, obj)
                        }
                      })
                      this.selectDataChange()
                    }
                  }
                }
              })
            ])
          }
        },
        {
          title: '产品编码',
          key: 'productCode',
          align: 'center',
          width: 150,
          fixed: 'left'
        },
        {
          title: '产品名称',
          key: 'productName',
          align: 'center',
          width: 100,
          fixed: 'left'
        },
        {
          title: '通用商品名称',
          key: 'commodityName',
          align: 'center',
          width: 120,
          fixed: 'left'
        },
        {
          title: '产品分类',
          key: 'classifyName',
          align: 'center',
          width: 100,
          fixed: 'left'
        },
        {
          title: '当前库存',
          key: 'totalStore',
          align: 'center',
          width: 100,
          fixed: 'left'
        },
        {
          title: '计量单位',
          key: 'unit',
          align: 'center',
          width: 100,
        },
        {
          title: '所在仓库',
          key: 'storeName',
          align: 'center',
          width: 100,
        },
        {
          title: '单价(元)',
          key: 'price',
          align: 'center',
          width: 100,
        },
        {
          title: '合计(元)',
          key: 'totalPrice',
          align: 'center',
          width: 100,
        },
        {
          title: '自定义分类',
          key: 'customName',
          align: 'center',
          width: 150,
        }
      ],
      dataList: [],
      transfer: {
        targetStore: '', // 目的仓库
        operatorAccount: '',
        createTime: '',
      },
      ruleInline: {
        targetStore: [
          { required: true, type: 'number', message: '请选择调拨的目的仓库', trigger: 'change' }
        ],
        operatorAccount: [
          { required: true, type: 'string', message: '请填写经手人', trigger: 'blur' }
        ],
        createTime: [
          { required: true, type: 'string', message: '请填写出库日期', trigger: 'blur' }
        ]
      },
      totalPrice: null,
      customIds: [],
      inStoreList: [],
      selectDatas:[], // 存的是每页选中的数据，如果触发了搜索 这个值要重置为空
      selectData:[], // 下方显示的 已选中数据 根据 selectDatas 每页选择中的数据循环拼接来的 如果触发了搜索 这个值要重置为空
    }
  },
  created() {
  },
  methods: {
    getDisplayName () {
      this.transfer.createTime = this.moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
      // 获取经手人
      this.$api.post('/member/login/findCurrentUser', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.data.displayName) {
          this.transfer.operatorAccount = response.data.displayName
        }
      })
    },
    reset () {
      this.$refs['info'].resetFields()
      this.$refs['transfer'].resetFields()
      this.getDisplayName()
      // 初始化列表
      this.init()
      // 初始化自定义分类
      this.initClassify()
      // 初始化入库仓库
      this.initStore()
      this.totalPrice = null
      this.pageNum = 1
      this.onSearch()
    },
    init () {
      this.info.account = this.$user.loginAccount
      this.info.pageNum = this.pageNum
      this.info.pageSize = this.pageSize
      this.$api.post('/shop/inventory/basicSetting/storeList', this.info).then(response => {
        console.log('list', response)
        if (response.code === 200) {
          this.dataList = response.data.list
          this.dataList.forEach(element => {
            element.number = 0
          })
          // 判断 当前页数 的选中 有没有数据 （之前在这一页有没有选过） 
          // 选过 需要根据从后来获取的当前页的数据 每条数据的id 跟选中的当前页数据做对比，如果有一样的name就  _checked = true 默认选中
          if (this.selectDatas[this.pageNum] && this.selectDatas[this.pageNum].length) {
            this.dataList.forEach((item, index) => {
              this.selectDatas[this.pageNum].forEach((e, index) => {
                if (item.id === e.id) {
                  item._checked = true
                } else {
                  item._checked = false
                }
              })
            })
          }
          this.total = response.data.total
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    onSearch () {
      this.selectDatas = []
      this.selectData = []
      this.init()
    },
    // 初始化自定义分类
    initClassify () {
      this.$api.post('/shop/inventory/basicSetting/customFind', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.code === 200) {
          this.customIds = response.data.slice(1)
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
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
    // 产品分类
    onSaveProductClassify (e) {
      this.info.productClassifyName = e
    },
    onSaveProductClassifyId (e) {
      this.info.productClassify = e
    },
    saveStore (type) {
      let data = this.transfer
      data.list = this.selectData
      data.account = this.$user.loginAccount
      if (this.selectData.length) {
        let flag = true
        this.selectData.forEach(e => {
          if (!e.number) { // 有调拨量为 0 的
            flag = false
          }
        })
        if (flag) {
          this.$refs['transfer'].validate((v) => {
            if (v) {
              this.$api.post('/shop/inventory/basicSetting/storeTransfer', data).then(response => {
                if (response.code === 200) {
                  this.$Message.success('操作成功！')
                  if (type === 0) {
                    this.$emit('close')
                  } else {
                    this.reset()
                  }
                } else {
                  this.$Message.error('操作失败！')
                }
              })
            }
          })
        } else {
          this.$Message.warning('调拨数量不能为零！')
        }
      } else {
        this.$Message.warning('请选择需要调拨的商品！')
      }
    },
    // 只要选中项发生变化时就会触发，返回值为 selection，已选项。
    selectChange (selection) {
      console.log('selection', selection)
      // 当前页已选值 改变 重新赋值
      this.selectDatas[this.pageNum] = selection
      // 改变下面显示已选值的数据
      this.selectDataChange()
    },
    selectDataChange () {
      let data = []
      let total = 0
      // 遍历每一页的 选择 数组相连接
      this.selectDatas.forEach((element, index) => {
        if (element && element.length) {
          data = data.concat(element)
        }
      })
      // 遍历 计算 总价格
      data.forEach(element => {
        console.log()
        let totalPrice = parseFloat((numMulti(element.number, element.price)).toFixed(2))
        total = numAdd(parseFloat(total).toFixed(2), parseFloat(totalPrice).toFixed(2)).toFixed(2)
      })
      this.totalPrice = total
      this.selectData = data
    },
    // 翻页
    getNextPage (e) {
      this.pageNum = e
      this.init()
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
.choose{
  position: relative;
  margin-top: 20px;
  font-size: 14px;
  .choose_text{
    position: absolute;
    left: 20px;
    line-height: 40px;
  }
  ul {
    padding-left: 70px;
    display: flex;
    flex-wrap: wrap;
    min-height: 40px;
    li{
      margin-right: 20px;
      padding: 10px;
      .choose_name{
        padding: 10px;
        background-color: #e8e8e8;
        margin-right: 6px;
      }
    }
  }
}
</style>
