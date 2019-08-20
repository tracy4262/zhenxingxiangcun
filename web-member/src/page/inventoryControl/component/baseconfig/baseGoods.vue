<template>
  <div>
    <Row class="base_content">
      <!-- 左侧 -->
      <Col span="4" class="base_content_left">
        <div>
          <p class="left_title">自定义分类</p>
          <Button class="base_addbtn" type="success" @click="classifyAdd">添加分类</Button>
          <ul class="left_ul">
            <li v-for="(item, index) in categories" :key="index"  :style="index === activeIndex ? 'color: #00c161;' : ''" class="ell vui-flex" :title="item.customName">
              <div class="vui-flex-item" @click="categoriesClick(item, index)">{{item.customName}}</div>
              <Poptip trigger="click" title="是否确认删除" confirm @on-ok="handleDelList(item, index)">
                  <span class="del" ><Icon type="md-close" size="18"/></span>
              </Poptip>

            </li>
          </ul>
        </div>
      </Col>
      <!-- 右侧 -->
      <Col span="20" class="base_content_right">
        <p class="right_title">商品所属分类：{{title}}</p>
        <!-- 查询框 -->
        <Row class="pt30 pb30">
          <Col span="9">
              <label class="mr10">产品名称</label>
              <Input v-model="searchInfo.productName" style="width: 200px"/>
          </Col>
          <Col span="9">
            <label class="mr10" v-if="activeIndex === 0">所属自定义分类</label>
            <Select v-model="searchInfo.customId" clearable style="width: 200px" v-if="activeIndex === 0">
                <Option v-for="(item,index) in categories2" :value="item.id" :key="index">{{ item.customName }}</Option>
            </Select>
          </Col>
          <Col span="6" class="tr" :offset="activeIndex === 0 ? '0' : '9'">
            <Button type="success" @click="query" class="mr20">查询</Button>
            <Button type="success" @click="addInit">新增商品</Button>
          </Col>
        </Row>
        <!-- 列表 -->
        <Table border :columns="columns" :data="data"></Table>
        <!-- 分页 -->
        <div class="base_page">
          <Page :total="total" :current="pageNum" :page-size="pageSize" @on-change="pageChange" />
        </div>
      </Col>
      <!-- 添加自定义分类 -->
      <Modal class="classify" v-model="classify" title="新增自定义分类" :mask-closable="false">
        <Form ref="info" :model="info" label-position="right" :label-width="100" :rules="ruleInline">
          <FormItem label="产品分类" prop="productTypeName">
            <Input v-model="info.productTypeName" readonly @on-focus="handleFilterModal('productTypeFilter')" />
          </FormItem>
          <FormItem label="自定义分类名称" prop="customTypeName">
            <Input v-model="info.customTypeName" :maxlength="30" />
          </FormItem>
        </Form>
        <div slot="footer">
            <Button type="text" @click="classify=false">取消</Button>
            <Button type="primary" @click="addClassify">确定</Button>
        </div>
      </Modal>
      <!-- 新增商品 -->
      <Modal class="classify" v-model="newGoods" title="新增商品" :mask-closable="false">
        <Form ref="info2" :model="info2" label-position="right" :label-width="100" :rules="ruleInline2">
          <FormItem label="产品编码" prop="productCode">
            <Input v-model="info2.productCode" readonly />
          </FormItem>
          <FormItem label="产品名称" prop="productName">
            <Input v-model="info2.productName" :maxlength="30" />
          </FormItem>
          <FormItem label="通用商品名" prop="commodityName">
            <Input v-model="info2.commodityName" readonly @on-focus="commodityName" />
          </FormItem>
          <FormItem label="产品分类" prop="productClassifyName">
            <Input v-model="info2.productClassifyName" readonly :maxlength="30" />
          </FormItem>
          <FormItem label="自定义分类" prop="customId">
            <Select v-model="info2.customId">
                <Option v-for="item in customList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
          <div style="margin-left: 100px; color: #ed4014;" v-if="flag">请先添加该产品分类下的自定义分类</div>
          <FormItem label="单位" prop="unit">
            <vuiUnit :value="info2.unit" @on-get-data="onGetInfo2Unit($event)"></vuiUnit>
          </FormItem>
          <FormItem label="库存上限" prop="upperLimit">
            <Input v-model="info2.upperLimit" :maxlength="10" />
          </FormItem>
          <FormItem label="库存下限" prop="lowerLimit">
            <Input v-model="info2.lowerLimit" :maxlength="10" />
          </FormItem>
        </Form>
        <div slot="footer">
            <Button type="text" @click="newGoods=false">取消</Button>
            <Button type="primary" @click="addNewGoods">确定</Button>
        </div>
      </Modal>
      <!-- 编辑 -->
      <Modal class="classify" v-model="modifyGoods" title="编辑商品" :mask-closable="false">
        <Form ref="info3" :model="info3" label-position="right" :label-width="100" :rules="ruleInline3">
          <FormItem label="产品编码" prop="productCode">
            <Input v-model="info3.productCode" readonly />
          </FormItem>
          <FormItem label="产品名称" prop="productName">
            <Input v-model="info3.productName" :maxlength="30" />
          </FormItem>
          <FormItem label="通用商品名" prop="commodityName">
            <Input v-model="info3.commodityName" readonly @on-focus="commodityName3" />
          </FormItem>
          <FormItem label="产品分类" prop="productClassifyName">
            <Input v-model="info3.productClassifyName" readonly :maxlength="30" />
          </FormItem>
          <FormItem label="自定义分类" prop="customId">
            <Select v-model="info3.customId">
                <Option v-for="(item,index) in customList3" :value="item.value" :key="index">{{ item.label }}</Option>
            </Select>
          </FormItem>
          <div style="margin-left: 100px; color: #ed4014;" v-if="flag3">请先添加该产品分类下的自定义分类</div>
          <FormItem label="单位" prop="unit">
            <vuiUnit :value="info3.unit" @on-get-data="onGetInfo3Unit($event)"></vuiUnit>
          </FormItem>
          <FormItem label="库存上限" prop="upperLimit">
            <Input v-model="info3.upperLimit" :maxlength="10" />
          </FormItem>
          <FormItem label="库存下限" prop="lowerLimit">
            <Input v-model="info3.lowerLimit" :maxlength="10" />
          </FormItem>
        </Form>
        <div slot="footer">
            <Button type="text" @click="modifyGoods=false">取消</Button>
            <Button type="primary" @click="updataGoods">确定</Button>
        </div>
      </Modal>
    </Row>
    <!-- 商品分类控件 -->
    <vui-filter
      ref="productTypeFilter"
      :num="1"
      :simple="true"
      :pageShow="true"
      :cols="2"
      :total="total2"
      :pageCur="pageCur2"
      :transfer="true"
      :resultDatas="productTypeResultDatas"
      @on-search="handleProductTypeSearch"
      @on-page-change="handleProductTypePageChange"
      @on-get-result="handleGetProductTypeResult" />
    <!-- 通用商品名控件 -->
    <vuiCommodity ref="vuiCommodity" @on-save="onSaveFocus" :input="false" :num="1"></vuiCommodity>
    <vuiCommodity ref="vuiCommodity3" @on-save="onSaveFocus3" :input="false" :num="1"></vuiCommodity>
  </div>
</template>

<script>
import vuiFilter from '~components/vuiFilter/filter'
import vuiCommodity from '~components/vui-commodity'
import { isDecimal2 } from '~utils/validate'
import vuiUnit from '~components/vui-unit'
export default {
  components: {
    vuiFilter,
    vuiCommodity,
    vuiUnit
  },
  data () {
    return {
      activeIndex: 0,
      classify: false,
      newGoods: false,
      modifyGoods: false,
      proName: '',
      title: '全部商品信息',
      searchInfo: {
        productName: '',
        customId: ''
      },
      categories: [],
      categories2: [],
      columns: [
        {
          title: '自定义分类',
          key: 'customName'
        },
        {
          title: '产品编码',
          key: 'productCode'
        },
        {
          title: '产品名称',
          key: 'productName'
        },
        {
          title: '通用商品名',
          key: 'commodityName'
        },
        {
          title: '产品分类',
          key: 'classifyName'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 200,
          render: (h, params) => {
            return h('div', [
              h('a', {
                  style: {
                      marginRight: '10px',
                      color: '#19be6b'
                  },
                  on: {
                      click: () => {
                        this.editInit(params.row)
                      }
                  }
              }, '编辑'),
              h('a', {
                  style: {
                      marginRight: '10px',
                      color: '#ed4014'
                  },
                  on: {
                      click: () => {
                        this.delGoods(params.row)
                      }
                  }
              }, '删除'),
              h('a', {
                  style: {
                      marginRight: '10px',
                      color: '#2c92ff'
                  },
                  on: {
                      click: () => {
                        this.$router.push(`/release-goods/step1?productName=${params.row.productName}&productCode=${params.row.productCode}`)
                        // this.$Message.info('完善商品信息！')
                      }
                  }
              }, '完善商品信息')
            ])
          }
        }
      ],
      data: [],
      // 商品分类
      total2: 0,
      pageCur2: 1,
      productTypeResultDatas: [],
      info: {
        productTypeName: '',
        productTypeId: '',
        customTypeName: ''
      },
      ruleInline: {
        productTypeName: [
          { required: true, type: 'string', message: '请选择产品分类', trigger: 'change' }
        ],
        customTypeName: [
          { required: true, type: 'string', message: '请填写自定义分类名称', trigger: 'blur' }
        ]
      },
      flag: false,
      info2: {
        productCode: '',
        productName: '',
        commodityId: '',
        commodityName: '',
        productClassifyName: '', // name
        productClassify: '', // id
        customId: '',
        unit: '',
        upperLimit: '',
        lowerLimit: ''
      },
      ruleInline2: {
        productCode: [
          { required: true, type: 'string', message: '请填写产品编码', trigger: 'blur' }
        ],
        productName: [
          { required: true, type: 'string', message: '请填写产品名称', trigger: 'blur' }
        ],
        commodityName: [
          { required: true, type: 'string', message: '请选择通用商品名', trigger: 'change' }
        ],
        productClassifyName: [
          { required: true, type: 'string', message: '请填写产品分类', trigger: 'change' }
        ],
        customId: [
          { required: true, type: 'string', message: '请选择自定义分类', trigger: 'change' }
        ],
        unit: [
          { required: true, type: 'string', message: '请选择单位', trigger: 'change' }
        ],
        upperLimit: [
          { required: false, validator: isDecimal2, tigger: 'blur' }
        ],
        lowerLimit: [
          { required: false, validator: isDecimal2, tigger: 'blur' }
        ]
      },
      pageSize: 10,
      pageNum: 1,
      total: 0,
      customList: [],
      flag3: false,
      info3: {
        productCode: '',
        productName: '',
        commodityId: '',
        commodityName: '',
        productClassifyName: '', // name
        productClassify: '', // id
        customId: '',
        unit: '',
        upperLimit: '',
        lowerLimit: ''
      },
      ruleInline3: {
        productCode: [
          { required: true, type: 'string', message: '请填写产品编码', trigger: 'blur' }
        ],
        productName: [
          { required: true, type: 'string', message: '请填写产品名称', trigger: 'blur' }
        ],
        commodityName: [
          { required: true, type: 'string', message: '请选择通用商品名', trigger: 'change' }
        ],
        productClassifyName: [
          { required: true, type: 'string', message: '请填写产品分类', trigger: 'change' }
        ],
        customId: [
          { required: true, type: 'string', message: '请选择自定义分类', trigger: 'change' }
        ],
        unit: [
          { required: true, type: 'string', message: '请选择单位', trigger: 'change' }
        ],
        upperLimit: [
          { required: false, validator: isDecimal2, tigger: 'blur' }
        ],
        lowerLimit: [
          { required: false, validator: isDecimal2, tigger: 'blur' }
        ]
      },
      customList3: []
    }
  },
  created () {
    this.initClassify()
    this.initGoods()
    // 商品分类数据初始化
    this.$api.post('/portal/shopCommdoity/findProductClassification', {
        pinyin: '',
        name: '',
        num: 1,
        size: 32
    }).then(res => {
        this.total2 = res.data.total
        res.data.list.forEach(element => {
            this.productTypeResultDatas.push({
                label: element.className,
                value: element.productCode
            })
        })
    })
  },
  methods: {
    // 删除 左侧自定义分类
    handleDelList (item, index) {
      console.log(item)
      this.$api.post('/shop/inventory/basicSetting/customDelete', {
        id: item.id,
        account: item.account
      }).then(res => {
        if (res.code === 200) {
          this.$Message.success('删除成功')
          this.categories.splice(index, 1)
        } else {
          this.$Message.error(res.msg)
        }
      })
    },
    // 取下拉数据
    onGetInfo2Unit($event){
      this.info2.unit = $event
    },
    // 取下拉数据
    onGetInfo3Unit($event){
      this.info3.unit = $event
    },
    // 左侧自定义分类点击事件
    categoriesClick (item, index) {
      this.title = item.customName
      this.searchInfo.customId = item.id
      this.activeIndex = index
      this.pageNum = 1
      this.initGoods()
    },
    // 新增商品初始化
    addInit () {
      // 清空已选择数据
      this.customList = []
      this.$refs['info2'].resetFields()
      this.info2.productCode = 'CP' + new Date().getTime()
      this.newGoods = true
    },
    classifyAdd () {
      this.$refs['info'].resetFields();
      this.classify = true
    },
    // 初始化自定义分类
    initClassify () {
      this.$api.post('/shop/inventory/basicSetting/customFind', {
        account: this.$user.loginAccount
      }).then(response => {
        console.log('res', response)
        if (response.code === 200) {
          this.categories = response.data
          let arr = this.categories
          this.categories2 = arr.slice(1)
          console.log('ca', this.categories)
          console.log('ca2', this.categories2)
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 添加分类
    addClassify () {
      this.$refs['info'].validate((valid) => {
        if (valid) {
          this.$api.post('/shop/inventory/basicSetting/customSave', {
            account: this.$user.loginAccount,
            productClassify: this.info.productTypeId,
            customName: this.info.customTypeName
          }).then(response => {
            console.log('res', response)
            if (response.code === 200) {
              this.$Message.success('添加成功！')
              this.initClassify()
              this.classify = false
            } else if (response.code === 400) {
              this.$Message.info('自定义分类名称已存在！')
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
    },
    // 初始化商品信息
    initGoods () {
      this.$api.post('/shop/inventory/basicSetting/productFind', {
        account: this.$user.loginAccount,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        productName: this.searchInfo.productName,
        customId: this.searchInfo.customId
      }).then(response => {
        console.log('res', response)
        if (response.code === 200) {
          this.data = response.data.list
          this.total = response.data.total
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    pageChange (page) {
      this.pageNum = page
      this.initGoods()
    },
    // 添加商品
    addNewGoods () {
      // 如果上下限都填写了的话就要比较大小
      if (this.info2.upperLimit !== '' && this.info2.lowerLimit !== '') {
        if (parseFloat(this.info2.upperLimit) <= parseFloat(this.info2.lowerLimit)) {
          this.$Message.info('库存上限应该大于库存下限')
          return
        }
      }
      this.$refs['info2'].validate((valid) => {
        if (valid) {
          this.info2.account = this.$user.loginAccount
          this.$api.post('/shop/inventory/basicSetting/productSave', this.info2).then(response => {
            console.log('res', response)
            if (response.code === 200) {
              this.$Message.success('添加成功！')
              this.initGoods()
              this.newGoods = false
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
    },
    // 查询按钮
    query () {
      this.pageNum = 1
      this.initGoods()
    },
    // 通用商品名
    commodityName () {
      this.$refs['vuiCommodity'].handleFilterModal()
    },
    // 通用商品名控件返回值
    onSaveFocus (e) {
      console.log('eee', e)
      if (e.length > 0) {
        // 清空已选择的数据
        this.customList = []
        this.info2.customId = ''
        // 给对应的数据源赋值
        this.info2.commodityId = e[0].commodityId
        this.info2.commodityName = e[0].commodityName
        this.info2.productClassify = e[0].productId
        // 根据产品分类id去查询产品分类的中文名显示上去
        this.$api.post('/shop/inventory/basicSetting/getProductName', {
          productClassify: e[0].productId
        }).then(response => {
          console.log('ress', response)
          if (response.code === 200) {
            this.info2.productClassifyName = response.data.classifyName
          } else {
            this.$Message.error('服务器异常！')
          }
        })
        // 根据产品分类去查询自定义分类的数据
        this.$api.post('/shop/inventory/basicSetting/customFind', {
          account: this.$user.loginAccount,
          productClassify: e[0].productId
        }).then(response => {
          console.log('res', response)
          if (response.code === 200) {
            if (response.data.length > 0) {
              this.flag = false
              response.data.forEach(element => {
                this.customList.push({
                  label: element.customName,
                  value: element.id
                })
              })
            } else {
              this.flag = true
            }
          } else {
            this.$Message.error('服务器异常！')
          }
        }).catch(error => {
          this.$Message.error('服务器异常！')
        })
      }
    },
    // 编辑商品初始化
    editInit (item) {
      console.log('item', item)
      // 清空已选择的数据
      this.customList3 = []
      this.$refs['info3'].resetFields()
      this.modifyGoods = true
      // 回显数据
      this.info3.id = item.id // 编辑时回显id
      this.info3.productCode = item.productCode
      this.info3.productName = item.productName
      this.info3.commodityId = item.commodityId
      this.info3.commodityName = item.commodityName
      this.info3.productClassify = item.productClassify
      this.info3.unit = item.unit
      this.info3.upperLimit = item.upperLimit === '不限' ? '' : item.upperLimit
      this.info3.lowerLimit = item.lowerLimit === '不限' ? '' : item.lowerLimit
      // 根据产品分类id去查询产品分类的中文名显示上去
      this.$api.post('/shop/inventory/basicSetting/getProductName', {
        productClassify: item.productClassify
      }).then(response => {
        console.log('ress', response)
        if (response.code === 200) {
          this.info3.productClassifyName = response.data.classifyName
        } else {
          this.$Message.error('服务器异常！')
        }
      })
      // 根据产品分类去查询自定义分类的数据
      this.$api.post('/shop/inventory/basicSetting/customFind', {
        account: this.$user.loginAccount,
        productClassify: item.productClassify
      }).then(response => {
        console.log('res', response)
        if (response.code === 200) {
          if (response.data.length > 0) {
            this.flag3 = false
            response.data.forEach(element => {
              this.customList3.push({
                label: element.customName,
                value: element.id
              })
            })
            this.info3.customId = item.customId // 回显的时候得手动赋值customId
          } else {
            this.flag3 = true
          }
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    delGoods (item) {
      this.$Modal.confirm({
        title: '操作提示',
        content: '确定删除该产品？',
        onOk: () => {
          this.$api.post('/shop/inventory/basicSetting/productDelete', {
            account: this.$user.loginAccount,
            id: item.id,
            productCode: item.productCode
          }).then(response => {
            if (response.code === 200) {
              this.$Message.success('删除成功！')
              this.pageNum = 1
              this.initGoods()
            } else if (response.code === 400) {
              this.$Message.info('该产品已进行过出入库操作，无法删除！')
            } else {
              this.$Message.error('服务器异常！')
            }
          }).catch(error => {
            this.$Message.error('服务器异常！')
          })
        }
      })
    },
    // 编辑时通用商品名
    commodityName3 () {
      this.$refs['vuiCommodity3'].handleFilterModal()
    },
    // 编辑时通用商品名控件返回值
    onSaveFocus3 (e) {
      console.log('eee', e)
      if (e.length > 0) {
        // 清空已选择的数据
        this.customList3 = []
        this.info3.customId = ''
        // 给对应的数据源赋值
        this.info3.commodityId = e[0].commodityId
        this.info3.commodityName = e[0].commodityName
        this.info3.productClassify = e[0].productId
        // 根据产品分类id去查询产品分类的中文名显示上去
        this.$api.post('/shop/inventory/basicSetting/getProductName', {
          productClassify: e[0].productId
        }).then(response => {
          console.log('ress', response)
          if (response.code === 200) {
            this.info3.productClassifyName = response.data.classifyName
          } else {
            this.$Message.error('服务器异常！')
          }
        })
        // 根据产品分类去查询自定义分类的数据
        this.$api.post('/shop/inventory/basicSetting/customFind', {
          account: this.$user.loginAccount,
          productClassify: e[0].productId
        }).then(response => {
          console.log('res', response)
          if (response.code === 200) {
            if (response.data.length > 0) {
              this.flag3 = false
              response.data.forEach(element => {
                this.customList3.push({
                  label: element.customName,
                  value: element.id
                })
              })
            } else {
              this.flag3 = true
            }
          } else {
            this.$Message.error('服务器异常！')
          }
        }).catch(error => {
          this.$Message.error('服务器异常！')
        })
      }
    },
    // 编辑商品
    updataGoods () {
      this.$refs['info3'].validate((valid) => {
        if (valid) {
          this.info3.account = this.$user.loginAccount
          this.$api.post('/shop/inventory/basicSetting/productSave', this.info3).then(response => {
            console.log('res', response)
            if (response.code === 200) {
              this.$Message.success('编辑成功！')
              this.initGoods()
              this.modifyGoods = false
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
    },
    // 高级搜索弹窗
    handleFilterModal (name) {
      this.$refs[name].highFilterShow = true
    },
    // 商品分类
    handleProductTypeSearch (letter, keyword, result) {
      this.$api.post(`/portal/shopCommdoity/findProductClassification`, {
          name : keyword,
          pinyin: letter === '全部' ? '' : letter,
          num: 1,
          size: 32
      }).then(res => {
          console.log('123', res)
          var data = []
          if (res.data.list) {
              res.data.list.forEach(element => {
                  data.push({
                      label: element.className,
                      value: element.productCode
                  })
              })
          }
          this.total2 = res.data.total
          this.productTypeResultDatas = []
          // 选中回显
          if(result) {
              result.forEach(item => {
                  data.forEach((child, index) => {
                      if(child.label === item.label) {
                          child.checked = true
                      }
                  })
              })
          } else {
              data.forEach(child => {
                  child.checked = false
              })
          }
          this.productTypeResultDatas = data
        })
    },
    handleProductTypePageChange (letter, keyword, classify, num, result) {
        this.pageCur2 = num
        this.$api.post('/portal/shopCommdoity/findProductClassification', {
            name : keyword,
            pinyin: letter === '全部' ? '' : letter,
            num: num,
            size: 32
        }).then(res => {
            var data = []
            res.data.list.forEach(element => {
                data.push({
                    label: element.className,
                    value: element.productCode
                })
            })
            this.total2 = res.data.total
            this.productTypeResultDatas = []
            // 选中回显
            if(result) {
                result.forEach(item => {
                    data.forEach((child, index) => {
                        if(child.label === item.label) {
                            child.checked = true
                        }
                    })
                })
            } else {
                data.forEach(child => {
                    child.checked = false
                })
            }
            this.productTypeResultDatas = data
        })
        this.$Message.success('筛选完成！')
    },
    handleGetProductTypeResult (classify, result) {
      let arr = []
      let arrId = []
      result.forEach(item => arr.push(item.label))
      result.forEach(item => arrId.push(item.value))
      this.info.productTypeName = arr.join(' ')
      this.info.productTypeId = arrId.join(' ')
    }
  }
}
</script>

<style lang="scss">
.base_content{
  .base_content_left{
    padding: 20px 12px;
    background: #F0F2F5;
    .left_title{
      color: #000 65%;
      font-size: 16px;
      padding-bottom: 12px;
      border-bottom: 1px solid #eee;
    }
    .base_addbtn{
      width: 110px;
      border-radius: 0px;
      margin: 10px 0;
    }
    .left_ul{
      // padding-top: 20px;
      li{
        padding: 20px 5px 0;
        cursor: pointer;
        word-wrap: break-word;
        .del{
          display: none;
        }
        &:hover{
          .del{
            display: inline-block;
          }
        }
      }
    }
  }
  .base_content_right{
    padding: 10px 24px;
    .right_title{
      color: #4A4A4A;
      font-size: 16px;
      padding-left: 10px;
      border-left: 6px solid #56B07D;
    }
    .base_page{
      text-align: center;
      margin-top: 26px;
    }
  }
}
// 添加分类
.classify{
  .lable_name{
    height: 32px;
    line-height: 32px;
    margin-right: 20px;
  }
}
</style>
