<template>
<div class="seedRecords">
  <Row>
    <Col span="8">
      <span class="mr10">收获时间</span>
      <DatePicker 
        v-model="times" 
        @on-change="timeChange" 
        format="yyyy/MM/dd" 
        type="daterange" 
        placement="bottom-end" 
        placeholder="请选择" style="width:200px"></DatePicker>
       <!-- <DatePicker type="date" v-model="search.harvestTime" style="width:200px"></DatePicker> -->
    </Col>
    <Col span="8">
      <span class="mr10">收获产品</span>
      <Input v-model="search.harvestProduct" style="width:200px"/>
    </Col>
    <Col span="8">
      <Button type="primary"  @click="getSearch">查找</Button>
    </Col>
  </Row>
  <div>
    <div class="seed_flex">
      <p>收获记录表：</p>
      <Button type="primary" @click="addNew">添加</Button>
    </div>
    <Table border :columns="columns" :data="data" class="mb20"></Table>
    <Page class="tc" :total="total" @on-change="getNextPage" :page-size="pageSize" :current="pageNum"></Page>
  </div>

  <Modal
    v-model="isShow"
    :mask-closable="false"
    :width="820"
    :title="title">
    <div>
      <Form :label-width="120" :model="formInfo" :rules="rules" ref="formInfo" class="mr30 pt20">
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="生产序号" prop="serialNumber">
                  <Input v-model="formInfo.serialNumber"   style="width:240px" readonly  v-if="title === '编辑收获记录'"/>
                  <Select v-model="formInfo.serialNumber" style="width:240px" @on-change="serialNumberChange" v-else>
                    <Option v-for="item in serialNumbers" :value="item.serialNumber" :key="item.id">{{ item.serialNumber }}</Option>
                  </Select>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="物种名称" prop="species">
              <Input v-model="formInfo.species"   style="width:240px" readonly/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="品种名称" prop="varietyName">
              <Input v-model="formInfo.varietyName"   style="width:240px" readonly/>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="品种来源" prop="varietySource">
              <Input v-model="formInfo.varietySource"  style="width:240px" readonly/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="基地名称" prop="baseName">
              <Input v-model="formInfo.baseName" style="width:240px" readonly/>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="地块编号"  prop="plotNumber">
              <Input v-model="formInfo.plotNumber" style="width:240px" readonly/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="播种面积" prop="sownArea">
              <Input v-model="formInfo.sownArea" style="width:240px" readonly>
                <span slot="append">亩</span>
              </Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="播种时间" prop="sowingTime">
              <Input v-model="formInfo.sowingTime" style="width:240px" readonly/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="产品名称" prop="productName">
                <Select v-model="formInfo.productNameId" label-in-value style="width:240px" @on-change="productNameChange">
                  <Option v-for="item in productNames" :value="item.id" :key="item.id">{{ item.productName }}</Option>
                </Select>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="收获日期" prop="harvestTime">
              <DatePicker type="date" v-model="formInfo.harvestTime" style="width:240px" @on-change="harvestTimeChange"></DatePicker>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="收获产品" prop="harvestProduct">
                <Select v-model="formInfo.harvestProductCode" label-in-value style="width:240px" @on-change="harvestProductCodeChange">
                  <Option v-for="item in harvestProductCodes" :value="item.productCode" :key="item.productCode">{{ item.productName }}</Option>
                </Select>
              <!-- <Input v-model="formInfo.harvestProduct" style="width:240px" @on-change="previewChange"/> -->
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="采收面积" prop="harvestArea">
              <Input v-model="formInfo.harvestArea" style="width:240px"  @on-change="compute" :maxlength="20">
                <span slot="append">亩</span>
              </Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="收获地块" prop="harvestPlot">
                <Select v-model="formInfo.harvestPlot" label-in-value style="width:240px" multiple @on-change="harvestPlothange">
                  <Option v-for="(item, index) in harvestPlots" :value="item" :key="index">{{ item }}</Option>
                </Select>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="收获数量" prop="harvestTotal">
              <Input v-model="formInfo.harvestTotal" style="width:240px" @on-change="compute" :maxlength="20">
                <span slot="append">{{formInfo.unit}}</span>
              </Input>
            </FormItem>
          </Col>
        </Row>
        
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="平均亩产" prop="averageOutput">
              <Input v-model="formInfo.averageOutput" style="width:240px" readonly>
                <span slot="append">{{formInfo.unit}}</span>
              </Input>
            </FormItem>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="收获人" prop="harvestUser">
              <Input v-model="formInfo.harvestUser" style="width:240px" :maxlength="10" @on-change="previewChange">
              </Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="24">
            <FormItem label="文字预览">
                <Input v-model="formInfo.preview" type="textarea" :autosize="{minRows: 4,maxRows: 6}" :maxlength="500"/>
            </FormItem>
          </Col>
        </Row>
      </Form>
    </div>
    <div slot="footer" class="tc">
      <Button @click="cancel">取消</Button>
      <!-- this.$Message.warning('该产品已收货！') -->
      <Button type="primary" @click="onSave" v-if="isHarvest">保存并入库</Button>
      <Button  type="primary" @click="$Message.warning('该产品已收货！')" v-else>保存并入库</Button>
    </div>
  </Modal>
  <inStore ref="inStore"></inStore>
</div>
</template>

<script>
import inStore from '../inStore'
import {isMoney3} from '~utils/validate'
export default {
  components: {
    inStore
  },
  props: {
    activeId: String
  },
  data () {
    return {
      times: [],
      search: {
        harvestTime: '',
        harvestProduct: '',
        beginTime: '',
        endTime: '',
      },
      pageNum: 1,
      pageSize: 10,
      total: null,
      isShow: false,
      title: '',
      columns: [
        {
          title: '生产序号',
          key: 'serialNumber',
          width: 100,
          align: 'center',
          fixed: 'left'
        },
        {
          title: '收货日期',
          align: 'center',
          width: 120,
          key: 'harvestTime'
        },
        {
          title: '品种名称',
          align: 'center',
          width: 120,
          key: 'varietyName'
        },
        {
          title: '收获产品',
          align: 'center',
          width: 120,
          key: 'harvestProduct'
        },
        {
          title: '收获地块',
          align: 'center',
          width: 150,
          key: 'harvestPlot',
          render: (h, params) => {
            return h('div', `${params.row.harvestPlot ? params.row.harvestPlot.join('、') : ''}`);
          }
        },
        {
          title: '采收面积',
          align: 'center',
          width: 120,
          key: 'harvestArea',
          render: (h, params) => {
            return h('div', `${params.row.harvestArea ? params.row.harvestArea : ''}亩`);
          }
        },
        {
          title: '收获数量',
          align: 'center',
          width: 120,
          key: 'harvestTotal',
          render: (h, params) => {
            return h('div', `${params.row.harvestTotal ? params.row.harvestTotal : ''}${params.row.unit ? params.row.unit : ''}`);
          }
        },
        {
          title: '平均亩产',
          align: 'center',
          width: 120,
          key: 'averageOutput',
          render: (h, params) => {
            return h('div', `${params.row.averageOutput ? params.row.averageOutput : ''}${params.row.unit ? params.row.unit : ''}`);
          }
        },
        {
          title: '收获人',
          width: 120,
          align: 'center',
          key: 'harvestUser'
        },
        {
          title: '操作',
          align: 'center',
          width: 160,
          fixed: 'right',
          key: '',
            render: (h, params) => {
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                              this.flag = false
                              this.isShow = true
                              this.title = '编辑收获记录'
                              let info = Object.assign({}, params.row)
                              info.baseName = info.baseName.join('、')
                              info.plotNumber = info.land.join('、')
                              this.harvestPlots = info.land
                              this.formInfo = info
                              // 查询产品名称下拉
                              let list = {
                                wikiId: this.id,
                                yearId: this.yearId,
                                account: this.$user.loginAccount,
                                pageNum: this.pageNum,
                                pageSize: this.pageSize,
                                serialNumber: this.formInfo.serialNumber, // 根据生产序号查询
                              }
                              this.$api.post('/shop/plant/findPlantOutputInfo', list).then(response => {
                                if (response.code === 200) {
                                  // this.total = response.data.total
                                  this.productNames = response.data.list
                                }
                              })
                            }
                        }
                    }, '编辑'),
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                              this.$Modal.confirm({
                                  title: '操作提示',
                                  content: '<p>您确定删除？</p>',
                                  cancelText: '取消',
                                  onOk: () => {
                                      this.$api.post('/shop/plant/deletePlantProductionPlanInfo', {id: params.row.id, type: this.type}).then(response => {
                                        if (response.code === 200) {
                                          this.getNextPage(1)
                                          this.$Message.success('删除成功！')
                                        }
                                      })
                                  }
                              })
                            }
                        }
                    }, '删除')
                ]);
            }
        }
      ],
      data: [],
      formInfo: {},
      rules: {
        serialNumber: [
          {required: true, message: '请选择生产序号', trigger: 'change' }
        ],
        productName: [
          {required: true, message: '请选择产品名称', trigger: 'change' }
        ],
        harvestTime: [
          {required: true, message: '请选择收获日期', trigger: 'blur' }
        ],
        harvestProduct: [
          {required: true, message: '请选择收获产品', trigger: 'blur' }
        ],
        harvestArea: [
          {required: true, message: '请输入采收面积', trigger: 'blur' },
          {validator:isMoney3, trigger: 'blur'}
        ],
        harvestPlot: [
          {required: true, message: '请选择收货地块', type: 'array', min: 1, trigger: 'change' }
        ],
        harvestTotal: [
          {required: true, message: '请输入收获数量', trigger: 'blur' },
          {required: true, validator:isMoney3, trigger: 'blur'}
        ],
        averageOutput: [
          {required: true, message: '请输入收获数量及采收面积', trigger: 'change' }
        ],
        harvestUser: [
          {required: true, message: '请输入收获人', trigger: 'blur' }
        ]
      },
      serialNumbers: [],
      productNames: [],
      harvestPlots: [],
      id: '',
      name: '',
      yearId: '',
      serialNumber: '',
      type: '5',
      flag: false,
      harvestProductCodes: [],
      isHarvest: true
    }
  },
  created() {
    if (this.$route.query.yearId) {
      this.yearId = this.$route.query.yearId
      this.$parent.$parent.yearId = this.$route.query.yearId
    }
    if (this.$route.query.year) {
      this.$parent.$parent.year = this.$route.query.year
    }
    if (this.$route.query.id) {
      this.id = this.$route.query.id
      this.$parent.$parent.id = this.$route.query.id
      this.getInit()
    }
    if (this.$route.query.name) {
      this.$parent.$parent.name = this.$route.query.name
    }
    this.getList()
    this.getInventory()
  },
  methods: {
    // 查询时间发生改变
    timeChange () {
      if (this.times[0]) {
        this.search.beginTime = this.moment(this.times[0]).format('YYYY-MM-DD')
      } else {
        this.search.beginTime = ''
      }
      if (this.times[1]) {
        this.search.endTime = this.moment(this.times[1]).format('YYYY-MM-DD')
      } else {
        this.search.endTime = ''
      }
    },
    // 获取 产品编码 列表数据
    getInventory () {
      this.$api.post('/shop/inventory/basicSetting/productCodeList', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.code === 200) {
          this.harvestProductCodes = response.data
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    getInit () {
      let data = {
        type: this.type,
        plantParentId: this.activeId,
        wikiId: this.id,
        yearId: this.yearId,
        account: this.$user.loginAccount,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        // harvestTime: this.search.harvestTime ? this.moment(this.search.harvestTime).format('YYYY-MM-DD') : '', // 调产时间
        beginTime: this.search.beginTime ? this.moment(this.search.beginTime).format('YYYY-MM-DD') : '', // 开始时间
        endTime: this.search.endTime ? this.moment(this.search.endTime).format('YYYY-MM-DD') : '', // 结束时间
        harvestProduct: this.search.harvestProduct, // 产品名称
      }
      this.$api.post('/shop/plant/findPlantProductionPlanInfo', data).then(response => {
        if (response.code === 200) {
          this.total = response.data.total
          this.data = response.data.list
        }
      })
    },
    getSearch () {
      this.getNextPage(1)
    },
    getNextPage (e) {
      this.pageNum = e
      this.getInit()
    },
    addNew () {
      this.$refs['formInfo'].resetFields()
      this.formInfo = {
        serialNumber: '', // 生产序号
        species: '', // 物种名称
        varietyName: '', // 品种名称
        varietyNameId: '',
        varietySource: '', // 品种来源 
        sownArea: '', // 播种面积
        sowingTime: '', // 播种时间
        baseName: '', // 基地名称
        plotNumber: '', // 地块编号

        productName: '', // 产品名称
        productNameId: '', // 产品名称 id
        harvestTime: '', // 收获日期
        harvestProduct: '', // 收获产品
        harvestProductCode: '', // 收获产品 的产品编码
        harvestArea: '', // 采收面积
        harvestPlot: [], // 收获地块
        harvestTotal: '', // 收获数量 
        averageOutput: '', // 平均亩产 
        harvestUser: '', // 收获人 
        unit: '', // 单位
      }
      this.title = '新增收获记录'
      this.isShow = true
    },
    onSave () {
      this.formInfo.harvestTime =  this.formInfo.harvestTime ? this.moment(this.formInfo.harvestTime).format('YYYY-MM-DD') : ''
       this.$refs['formInfo'].validate((valid) => {
          if (valid) {
             let harvestProductCode = this.formInfo.harvestProductCode
              this.formInfo.id = this.formInfo.id ? this.formInfo.id : ''
              this.formInfo.plantParentId = this.activeId
              this.formInfo.wikiId = this.id
              this.formInfo.yearId = this.yearId
              this.formInfo.account = this.$user.loginAccount
              this.formInfo.type = this.type
              console.log(this.formInfo)
              this.$api.post('/shop/plant/saveOrUpdateProductionPlanInfo', this.formInfo).then(response => {
                if (response.code === 200) {
                  this.getNextPage(1)
                  this.isShow = false
                  this.$Message.success('保存成功！')
                  this.$refs['formInfo'].resetFields()
                  this.$refs['inStore'].initAdd(harvestProductCode)
                }
              })
          } else {
            this.$Message.error('请核对表单信息！');
          }
      })
    },
    cancel () {
      this.isShow = false
    },
    // 取生产序号下拉
    getList () {
      let list = {}
      list.wikiId = this.$route.query.id
      list.yearId = this.$route.query.yearId
      list.account = this.$user.loginAccount
      this.$api.post('/shop/plant/findPlantProductionNumber', list).then(response => {
        if (response.code === 200) {
          this.serialNumbers = response.data
        }
      })
    },
    // 发生变化
    serialNumberChange (e) {
      console.log(e)
      if (e) {
        let data = {
          wikiId: this.$route.query.id,
          yearId: this.$route.query.yearId,
          account: this.$user.loginAccount,
          pageNum: 1,
          pageSize: 1,
          serialNumber: this.formInfo.serialNumber, // 根据生产序号查询
        }
        this.$api.post('/shop/plant/findPlantProductionInfo', data).then(response => {
          if (response.code === 200) {
            let list = response.data.list[0]
            this.formInfo.productionId = list.id
            this.formInfo.species = list.species
            this.formInfo.varietyName = list.varietyName
            this.formInfo.varietyNameId = list.varietyNameId
            this.formInfo.varietySource = list.varietySource
            this.formInfo.sowingTime = list.sowingTime
            
            this.formInfo.sownArea = list.sownArea
            this.formInfo.baseName = list.baseName.join('、')
            this.formInfo.plotNumber = list.land.join('、')
            this.harvestPlots = list.land
            this.compute()
            this.previewChange()
          }
        })
        // 清除 产品名称 产品名称 单位 预计产量 以及预计产量单位 
        this.formInfo.productNameId = ''
        this.formInfo.productName = ''
        this.formInfo.production = ''
        this.formInfo.unit = ''
        this.formInfo.sownArea = ''
        let list = {
          wikiId: this.id,
          yearId: this.yearId,
          account: this.$user.loginAccount,
          serialNumber: this.formInfo.serialNumber, // 根据生产序号查询
        }
        this.$api.post('/shop/plant/findHarvestProductInfo', list).then(response => {
          if (response.code === 200) {
          // isHarvest 1 已收货 0未收货
            this.productNames = response.data
          }
        })
      }
    },
    // 收获产品 发生改变
    harvestProductCodeChange (data) {
      if (data) {
        this.formInfo.harvestProduct = data.label
      } else {
        this.formInfo.harvestProduct = ''
      }
    },
    // 产品名称发生改变
    productNameChange (data) {
      console.log('data', data)
      if (data) {
        this.productNames.forEach(e => {
          console.log(this.isHarvest)
          if (data.value == e.id) {
            // isHarvest 1 已收货 0未收货
            if (e.rewardType == '一次性收获') {
              if (e.isHarvest) { //
                this.isHarvest = false
                this.$Message.warning('该产品已收货！')
              } else {
                this.isHarvest = true
              }
            } else {
              this.isHarvest = true
            }
            this.formInfo.productNameId = e.id
            this.formInfo.productName = e.productName
            this.formInfo.production = e.production
            this.formInfo.unit = e.unit
          }
        })
      }
      this.previewChange()
      console.log(this.formInfo)
    },
    // 收获数量 跟采收面积发生改变，计算平均亩产
    compute () {
      if (parseFloat(this.formInfo.harvestArea) > parseFloat(this.formInfo.sownArea)) {
        this.formInfo.harvestArea = this.formInfo.sownArea
      }
      if (this.formInfo.harvestTotal && this.formInfo.harvestArea) {
        let string = ''
        string = Math.round(this.formInfo.harvestTotal / this.formInfo.harvestArea * 100) / 100.00
        this.formInfo.averageOutput = parseFloat(string).toFixed(2) + ''
      } else {
        this.formInfo.averageOutput = ''
      }
      this.previewChange()
    },
    harvestPlothange () {
      if (this.flag) {
        this.previewChange()
      } else {
        this.flag = true
      }
    },
    // 收货 日期改变
    harvestTimeChange () {
      this.formInfo.harvestTime = this.formInfo.harvestTime ? this.moment(this.formInfo.harvestTime).format('YYYY-MM-DD') : ''
      this.previewChange()
    },
    // preview 编辑文字预览
    previewChange () {
      let str = ''
      str += `${this.formInfo.harvestTime ? this.moment(this.formInfo.harvestTime).format('YYYY年MM月DD日') : ''}，由${this.formInfo.harvestUser}在${this.formInfo.baseName}基地的${this.formInfo.harvestPlot.join('、')}上收获${this.formInfo.productName}产品${this.formInfo.harvestTotal}${this.formInfo.unit}。`
      this.formInfo.preview = str
    },
  }
}
</script>

<style lang="scss">
.seedRecords{
  padding: 18px 46px 10px;
  .seed_flex{
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 30px 0 20px;
  }
}
</style>
