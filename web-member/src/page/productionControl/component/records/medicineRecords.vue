<template>
<div class="seedRecords">
  <Row>
    <Col span="8">
      <span class="mr10">施药时间</span>
      <DatePicker
        v-model="times"
        @on-change="timeChange"
        format="yyyy/MM/dd"
        type="daterange"
        placement="bottom-end"
        placeholder="请选择" style="width:200px"></DatePicker>
       <!-- <DatePicker type="date" v-model="search.medicineTime" style="width:200px"></DatePicker> -->
    </Col>
    <Col span="8">
      <span class="mr10">农药名称</span>
      <Input v-model="search.medicineName" style="width:200px"/>
    </Col>
    <Col span="8">
      <Button type="primary" @click="getSearch">查找</Button>
    </Col>
  </Row>
  <div>
    <div class="seed_flex">
      <p>农药使用记录表：</p>
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
                  <Input v-model="formInfo.serialNumber"   style="width:240px" readonly  v-if="title === '编辑农药使用记录'"/>
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
              <Input v-model="formInfo.varietyName" style="width:240px" readonly/>
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
            <FormItem label="施药面积" prop="sownArea">
              <Input v-model="formInfo.sownArea"  style="width:240px" :maxlength="20"><span slot="append">亩</span></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="施药时间" prop="medicineTime">
              <DatePicker type="date" v-model="formInfo.medicineTime" style="width:240px" @on-change="medicineTimeChange"></DatePicker>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="施药数量" prop="medicineCount">
                <Input v-model="formInfo.medicineCount"  style="width:240px" :maxlength="20" @on-change="previewChange">
                  <span slot="append">{{formInfo.unit}}</span>
                </Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="施药人" prop="medicineUser">
              <Input v-model="formInfo.medicineUser"  style="width:240px" :maxlength="10" @on-change="previewChange"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="防治对象" prop="prevent">
              <Input v-model="formInfo.prevent"  style="width:240px" :maxlength="20"></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="施药浓度" prop="concentration">
                <Row>
                  <Col span="14">
                    <Input v-model="formInfo.concentration">
                    </Input>
                  </Col>
                  <Col span="10" class="pl10">
                    <vuiUnit :value="formInfo.concentrationUnit" @on-get-data="onGetUnit($event)" :disabled="!edit"></vuiUnit>
                  </Col>
                </Row>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="农药编码"  prop="medicineCode">
              <Select v-model="formInfo.medicineCode" style="width:240px"  @on-change="seedCodeChange">
                <Option v-for="item in medicineCodes" :value="item.productCode" :key="item.productCode">{{ item.productCode }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="农药名称" prop="medicineName">
              <Input v-model="formInfo.medicineName"  style="width:240px" readonly></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="生产商" prop="producer">
              <Input v-model="formInfo.producer"  style="width:240px" :maxlength="20" @on-change="previewChange"></Input>
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
      <Button @click="isShow = false">取消</Button>
      <Button type="primary" @click="onSave">保存并出库</Button>
    </div>
  </Modal>
  <outStore ref="outStore"></outStore>
</div>
</template>

<script>
import {isMoney3} from '~utils/validate'
import outStore from '../outStore'
import vuiUnit from '~components/vui-unit'
export default {
  components: {
    outStore,
    vuiUnit
  },
  props: {
    activeId: String
  },
  data () {
    return {
      times: [],
      search: {
        medicineTime: '',
        medicineName: '',
        beginTime: '',
        endTime: '',
      },
      number: '',
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
          title: '施药时间',
          align: 'center',
          width: 120,
          key: 'medicineTime'
        },
        {
          title: '农药编码',
          align: 'center',
          width: 120,
          key: 'medicineCode'
        },
        {
          title: '农药名称',
          align: 'center',
          width: 120,
          key: 'medicineName'
        },
        {
          title: '防治对象',
          align: 'center',
          width: 120,
          key: 'prevent'
        },
        {
          title: '施药数量',
          align: 'center',
          width: 120,
          key: 'medicineCount',
          render: (h, params) => {
            return h('div', `${params.row.medicineCount ? params.row.medicineCount : ''}${params.row.medicineCountUnit ? params.row.medicineCountUnit : ''}`);
          }
        },
        {
          title: '施药浓度',
          align: 'center',
          width: 120,
          key: 'concentration',
          render: (h, params) => {
            return h('div', `${params.row.concentration ? params.row.concentration : ''}${params.row.concentrationUnit ? params.row.concentrationUnit : ''}`);
          }
        },
        {
          title: '生产商',
          align: 'center',
          width: 120,
          key: 'producer'
        },
        {
          title: '地块编号',
          align: 'center',
          width: 150,
          key: 'plotNumber',
          render: (h, params) => {
            return h('div', `${params.row.land ? params.row.land.join('、') : ''}`);
          }
        },
        {
          title: '施药人',
          align: 'center',
          width: 120,
          key: 'medicineUser'
        },
        {
          title: '施药面积',
          align: 'center',
          width: 120,
          key: 'sownArea',
          render: (h, params) => {
            return h('div', `${params.row.sownArea ? params.row.sownArea : ''}亩`);
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
                              this.title = '编辑农药使用记录'
                              let info = Object.assign({}, params.row)
                              info.baseName = info.baseName.join('、')
                              info.plotNumber = info.land.join('、')
                              this.formInfo = info
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
        medicineTime: [
          {required: true, message: '请选择施药时间', trigger: 'blur' }
        ],
        medicineCount: [
          {required: true, message: '请输入施药数量', trigger: 'blur' },
          {validator:isMoney3, trigger: 'blur'}
        ],
        medicineUser: [
          {required: true, message: '请输入施药人', trigger: 'blur' }
        ],
        prevent: [
          {required: true, message: '请输入防治对象', trigger: 'blur' }
        ],
        concentration: [
          {required: true, message: '请输入施药浓度', trigger: 'blur' },
          {validator:isMoney3, trigger: 'blur'}
        ],
        medicineCode: [
          {required: true, message: '请选择农药编码', trigger: 'change' }
        ],
        medicineName: [
          {required: true, message: '请输入农药名称', trigger: 'change' }
        ],
        producer: [
          {required: true, message: '请输入生产商', trigger: 'blur' }
        ]
      },
      serialNumbers: [],
      medicineCodes: [],
      id: '',
      name: '',
      yearId: '',
      serialNumber: '',
      type: '3'
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
    seedCodeChange () {
      if (this.formInfo.medicineCode) {
        this.medicineCodes.forEach(e => {
          if (e.productCode === this.formInfo.medicineCode) {
            this.formInfo.medicineName = e.productName
            this.formInfo.unit = e.unit[0] ? e.unit[0] : ''
          }
        })
      } else {
        this.formInfo.medicineName = ''
        this.formInfo.unit = ''
      }
      this.previewChange()
    },
    // 获取 产品编码 列表数据
    getInventory () {
      this.$api.post('/shop/inventory/basicSetting/productCodeList', {
        account: this.$user.loginAccount
      }).then(response => {
        console.log('code', response)
        if (response.code === 200) {
          this.medicineCodes = response.data
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
        // medicineTime: this.search.medicineTime ? this.moment(this.search.medicineTime).format('YYYY-MM-DD') : '', // 施药时间
        beginTime: this.search.beginTime ? this.moment(this.search.beginTime).format('YYYY-MM-DD') : '', // 开始时间
        endTime: this.search.endTime ? this.moment(this.search.endTime).format('YYYY-MM-DD') : '', // 结束时间
        medicineName: this.search.medicineName, // 农药名称
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
        baseName: '', // 基地名称
        plotNumber: '', // 地块编号
        sownArea: '', // 施药面积
        medicineTime: '', // 施药时间
        medicineCount: '', // 施药数量
        medicineCountUnit: 'kg', // 施药数量 单位
        medicineUser: '', // 施药人
        prevent: '', // 防治对象
        concentration: '', // 施药浓度
        concentrationUnit: 'kg', // 施药浓度 单位
        medicineCode: '', // 农药编码
        medicineName: '', // 农药名称
        producer: '', // 生产商
        preview: '', // 文字预览
      }
      this.title = '新增农药使用记录'
      this.isShow = true
    },
    onSave () {
      this.formInfo.medicineTime =  this.formInfo.medicineTime ? this.moment(this.formInfo.medicineTime).format('YYYY-MM-DD') : ''
      this.$refs['formInfo'].validate((valid) => {
          if (valid) {
              let productName = this.formInfo.medicineName
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
                  this.$refs['outStore'].outStoreInit(productName)
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
            this.formInfo.sownArea = list.sownArea
            this.formInfo.baseName = list.baseName.join('、')
            this.formInfo.plotNumber = list.land.join('、')
            this.previewChange()
          }
        })
      }
    },
    medicineTimeChange () {
      this.formInfo.medicineTime = this.formInfo.medicineTime ? this.moment(this.formInfo.medicineTime).format('YYYY-MM-DD') : ''
      this.previewChange()
    },
    // preview 编辑文字预览
    previewChange () {
      let str = ''
      let string = ''
      if (this.formInfo.fertilizeCount) {
        string = Math.round(this.formInfo.fertilizeCount / this.formInfo.sownArea * 10000) / 100.00
      }
      str += `${this.formInfo.medicineTime ? this.moment(this.formInfo.medicineTime).format('YYYY年MM月DD日') : ''}，为防治${this.formInfo.prevent}，由${this.formInfo.medicineUser}将${this.formInfo.producer}生产的${this.formInfo.medicineName}药施于${this.formInfo.baseName}的${this.formInfo.plotNumber}上，总使用量为${this.formInfo.medicineCount}${this.formInfo.medicineCountUnit}，浓度为${this.formInfo.concentration}${this.formInfo.concentrationUnit}。`
      this.formInfo.preview = str
    },
    // 取 数据 单位
    onGetUnit($event){
      this.formInfo.concentrationUnit = $event
    }
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
