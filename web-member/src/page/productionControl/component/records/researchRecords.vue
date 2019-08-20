<template>
<div class="seedRecords">
  <Row>
    <Col span="8">
      <span class="mr10">调产时间</span>
      <DatePicker 
        v-model="times" 
        @on-change="timeChange" 
        format="yyyy/MM/dd" 
        type="daterange" 
        placement="bottom-end" 
        placeholder="请选择" style="width:200px"></DatePicker>
       <!-- <DatePicker type="date" v-model="search.researchTime" style="width:200px"></DatePicker> -->
    </Col>
    <Col span="8">
      <span class="mr10">产品名称</span>
      <Input v-model="search.productName" style="width:200px"/>
    </Col>
    <Col span="8">
      <Button type="primary" @click="getSearch">查找</Button>
    </Col>
  </Row>
  <div>
    <div class="seed_flex">
      <p>产量调整记录表：</p>
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
                  <Input v-model="formInfo.serialNumber"   style="width:240px" readonly  v-if="title === '编辑调产记录'"/>
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
              <Input v-model="formInfo.varietySource"  style="width:240px" />
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
            <FormItem label="调产日期" prop="researchTime">
              <DatePicker type="date" v-model="formInfo.researchTime" style="width:240px" @on-change="researchTimeChange"></DatePicker>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="调产选项" prop="researchType">
              <RadioGroup v-model="formInfo.researchType" @on-change="compute">
                <Radio label="调增"></Radio>
                <Radio label="调减"></Radio>
              </RadioGroup>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="总调整量" prop="allResearch">
              <Input v-model="formInfo.allResearch" style="width:240px" @on-change="compute" >
                <span slot="append">{{formInfo.unit}}</span>
              </Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="单位面积调整量" prop="researchArea">
              <Input v-model="formInfo.researchArea" style="width:240px" readonly >
                <span slot="append">{{formInfo.unit}}</span>
              </Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="调整预销产量" prop="researchOutput">
              <Input v-model="formInfo.researchOutput" style="width:240px" readonly>
                <span slot="append">{{formInfo.unit}}</span>
              </Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="24">
            <FormItem label="调产原因" prop="researchReason">
              <Input v-model="formInfo.researchReason" type="textarea" :rows="4" :maxlength="200" @on-change="previewChange"/>
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
      <Button type="primary" @click="onSave">保存</Button>
    </div>
  </Modal>
</div>
</template>

<script>
import {isMoney3} from '~utils/validate'
import {numAdd, numMulti, Subtr} from '~utils/utils'
export default {
  props: {
    activeId: String
  },
  data () {
    return {
      times: [],
      search: {
        researchTime: '',
        productName: '',
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
          title: '品种名称',
          align: 'center',
          width: 120,
          key: 'varietyName'
        },
        {
          title: '基地名称',
          width: 120,
          align: 'center',
          key: 'baseName',
          render: (h, params) => {
            return h('div', `${params.row.baseName ? params.row.baseName.join('、') : ''}`);
          }
        },
        {
          title: '播种面积',
          align: 'center',
          width: 120,
          key: 'sownArea',
          render: (h, params) => {
            return h('div', `${params.row.sownArea ? params.row.sownArea : ''}亩`);
          }
        },
        {
          title: '产品名称',
          align: 'center',
          width: 120,
          key: 'productName'
        },
        {
          title: '调产日期',
          align: 'center',
          width: 120,
          key: 'researchTime'
        },
        {
          title: '调产选项',
          width: 120,
          align: 'center',
          key: 'researchType'
        },
        {
          title: '调产原因',
          align: 'center',
          width: 120,
          key: 'researchReason'
        },
        {
          title: '单位面积调整量',
          align: 'center',
          width: 120,
          key: 'researchArea',
          render: (h, params) => {
            return h('div', `${params.row.researchArea ? params.row.researchArea : ''}${params.row.unit ? params.row.unit : ''}`);
          }
        },
        {
          title: '总调整量',
          align: 'center',
          width: 120,
          key: 'allResearch',
          render: (h, params) => {
            return h('div', `${params.row.allResearch ? params.row.allResearch : ''}${params.row.unit ? params.row.unit : ''}`);
          }
        },
        {
          title: '调整预销产量',
          align: 'center',
          width: 120,
          key: 'researchOutput',
          render: (h, params) => {
            return h('div', `${params.row.researchOutput ? params.row.researchOutput : ''}${params.row.unit ? params.row.unit : ''}`);
          }
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
                              this.isShow = true
                              this.title = '编辑调产记录'
                              let info = Object.assign({}, params.row)
                              info.baseName = info.baseName.join('、')
                              info.plotNumber = info.land.join('、')
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
        researchTime: [
          {required: true, message: '请选择调产日期', trigger: 'blur' }
        ],
        researchType: [
          {required: true, message: '请选择调产选项', trigger: 'change' }
        ],
        researchArea: [
          {required: true, message: '请输入单位面积调整量', trigger: 'change' },
          {validator:isMoney3, trigger: 'blur'}
        ],
        allResearch: [
          {required: true, message: '请输入总调整量', trigger: 'blur' }
        ],
        researchOutput: [
          {required: true, message: '请输入调整预销产量', trigger: 'change' }
        ],
        researchReason: [
          {required: true, message: '请输入调产原因', trigger: 'blur' }
        ]
      },
      serialNumbers: [],
      productNames: [],
      units: [],
      id: '',
      name: '',
      yearId: '',
      serialNumber: '',
      type: '4'
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
    getInit () {
      let data = {
        type: this.type,
        plantParentId: this.activeId,
        wikiId: this.id,
        yearId: this.yearId,
        account: this.$user.loginAccount,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        // researchTime: this.search.researchTime ? this.moment(this.search.researchTime).format('YYYY-MM-DD') : '', // 调产时间
        beginTime: this.search.beginTime ? this.moment(this.search.beginTime).format('YYYY-MM-DD') : '', // 开始时间
        endTime: this.search.endTime ? this.moment(this.search.endTime).format('YYYY-MM-DD') : '', // 结束时间
        productName: this.search.productName, // 产品名称
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
        unit: '', // 产量单位
        production: '', // 预计产量
        researchTime: '', // 调产日期
        researchType: '', // 调产选项
        researchArea: '', // 单位面积调整量
        allResearch: '', // 总调整量   
        researchOutput: '', // 调整预销产量  预计产量+/-总调整量
        researchReason: '', // 调产原因
        preview: '' // 文字预览
      }
      this.title = '新增调产记录'
      this.isShow = true
    },
    cancel () {
      this.isShow = false
    },
    onSave () {
      this.formInfo.researchTime =  this.formInfo.researchTime ? this.moment(this.formInfo.researchTime).format('YYYY-MM-DD') : ''
      this.$refs['formInfo'].validate((valid) => {
          if (valid) {
              this.formInfo.id = this.formInfo.id ? this.formInfo.id : ''
              this.formInfo.plantParentId = this.activeId
              this.formInfo.wikiId = this.id
              this.formInfo.yearId = this.yearId
              this.formInfo.account = this.$user.loginAccount
              this.formInfo.type = this.type
              this.$api.post('/shop/plant/saveOrUpdateProductionPlanInfo', this.formInfo).then(response => {
                if (response.code === 200) {
                  this.getNextPage(1)
                  this.isShow = false
                  this.$Message.success('保存成功！')
                  this.$refs['formInfo'].resetFields()
                }
              })
          } else {
              this.$Message.error('请核对表单信息！');
          }
      })
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
            this.compute()
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
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          serialNumber: this.formInfo.serialNumber, // 根据生产序号查询
        }
        this.$api.post('/shop/plant/findPlantOutputInfo', list).then(response => {
          if (response.code === 200) {
            this.productNames = response.data.list
          }
        })
      }
    },
    // 产品名称发生改变
    productNameChange (data) {
      console.log('data', data)
      if (data) {
        this.productNames.forEach(e => {
          if (data.value == e.id) {
            this.formInfo.productNameId = e.id
            this.formInfo.productName = e.productName
            this.formInfo.production = e.production
            this.formInfo.unit = e.unit
          }
        })
      }
      console.log(this.formInfo)
      this.compute()
    },
    // 计算产量
    compute () {
      if (parseFloat(this.formInfo.allResearch) > parseFloat(this.formInfo.production)) {
        this.formInfo.allResearch = this.formInfo.production
      }
      // 单位面积调整量  总调整量/播种面积
      if (this.formInfo.allResearch && this.formInfo.sownArea) {
        let string = ''
        string = Math.round(this.formInfo.allResearch / this.formInfo.sownArea * 100) / 100.00
        this.formInfo.researchArea = parseFloat(string).toFixed(2) + ''
      } else {
        this.formInfo.researchArea = ''
      }
      // 调整预销产量  预计产量+/-总调整量
      if (this.formInfo.production && this.formInfo.allResearch) {
        console.log('存在')
        if (this.formInfo.researchType == '调增') {
          this.formInfo.researchOutput = numAdd(parseFloat(this.formInfo.production).toFixed(2), parseFloat(this.formInfo.allResearch).toFixed(2)).toFixed(2)
        } else if (this.formInfo.researchType == '调减') {
          this.formInfo.researchOutput = Subtr(parseFloat(this.formInfo.production).toFixed(2), parseFloat(this.formInfo.allResearch).toFixed(2))
        }
      } else {
        this.formInfo.researchOutput = ''
      }
      this.previewChange()
      console.log(this.formInfo)
    },
    // 调产日期改变
    researchTimeChange () {
      this.formInfo.researchTime = this.formInfo.researchTime ? this.moment(this.formInfo.researchTime).format('YYYY-MM-DD') : ''
      this.previewChange()
    },
    // preview 编辑文字预览
    previewChange () {
      console.log('change')
      let str = ''
      str += `${this.formInfo.researchTime ? this.moment(this.formInfo.researchTime).format('YYYY年MM月DD日') : ''}，因${this.formInfo.researchReason}原因，对${this.formInfo.productName}产品进行调产，${this.formInfo.researchType}，预计产量共${this.formInfo.researchOutput}${this.formInfo.unit}。`
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
