<template>
<div class="seedRecords">
  <Row>
    <Col span="8">
      <span class="mr10">播种时间</span>
      <DatePicker
        v-model="times"
        @on-change="timeChange"
        format="yyyy/MM/dd"
        type="daterange"
        placement="bottom-end"
        placeholder="请选择" style="width:200px"></DatePicker>
      <!-- <DatePicker type="date" v-model="search.sowingTime" style="width:200px"></DatePicker> -->
    </Col>
    <Col span="8">
      <span class="mr10">种子名称</span>
      <Input v-model="search.seedName" style="width:200px"/>
    </Col>
    <Col span="8">
      <Button type="primary" @click="getSearch">查找</Button>
    </Col>
  </Row>
  <div>
    <div class="seed_flex">
      <p>种子使用记录表：</p>
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
      <Form :label-width="100" :model="formInfo" :rules="rules" ref="formInfo" class="mr30 pt20">
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="生产序号" prop="serialNumber">
               <Input v-model="formInfo.serialNumber"   style="width:240px" readonly  v-if="title === '编辑种子使用记录'"/>
                <Select v-model="formInfo.serialNumber" style="width:240px" @on-change="serialNumberChange" v-else>
                  <Option v-for="item in serialNumbers" :value="item.serialNumber" :key="item.id">{{ item.serialNumber }}</Option>
                </Select>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="物种名称" prop="species">
              <Input v-model="formInfo.species" style="width:240px" readonly/>
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
            <FormItem label="播种面积" prop="sownArea">
              <Input v-model="formInfo.sownArea"  style="width:240px" readonly><span slot="append">亩</span></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="播种时间" prop="sowingTime">
              <DatePicker type="date" v-model="formInfo.sowingTime" readonly style="width:240px"></DatePicker>
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
            <FormItem label="播种数量" prop="sowingCount">
              <Input v-model="formInfo.sowingCount"  style="width:240px" @on-change="previewChange">
              <span slot="append">{{formInfo.unit}}</span>
              </Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="播种人" prop="sownUser">
              <Input v-model="formInfo.sownUser"  style="width:240px" :maxlength="10" @on-change="previewChange"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="种子编码"  prop="seedCode">
              <Select v-model="formInfo.seedCode" style="width:240px"  @on-change="seedCodeChange">
                <Option v-for="item in seedCodes" :value="item.productCode" :key="item.productCode">{{ item.productCode }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="种子名称" prop="seedName">
              <Input v-model="formInfo.seedName"  style="width:240px" readonly></Input>
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
                <Input v-model="formInfo.preview" type="textarea"  :autosize="{minRows: 4,maxRows: 6}" :maxlength="500"/>
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
export default {
  components: {
    outStore,
  },
  props: {
    activeId: String
  },
  data () {
    return {
      times: [],
      search:{
        sowingTime: '',
        seedName: '',
        beginTime: '',
        endTime: '',
      },
      number: '',
      pageNum: 1,
      pageSize: 10,
      total: 0,
      isShow: false,
      title: '',
      textContent: '',
      columns: [
        {
          title: '生产序号',
          key: 'serialNumber',
          width: 100,
          align: 'center',
          fixed: 'left'
        },
        {
          title: '播种时间',
          align: 'center',
          width: 120,
          key: 'sowingTime'
        },
        {
          title: '种子编码',
          align: 'center',
          width: 120,
          key: 'seedCode'
        },
        {
          title: '种子名称',
          align: 'center',
          width: 120,
          key: 'seedName'
        },
        {
          title: '种子数量',
          align: 'center',
          width: 120,
          key: '',
          render: (h, params) => {
            return h('div', `${params.row.sowingCount}${params.row.unit}`);
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
          render: (h, params) => {
            return h('div', `${params.row.land.join('、')}`);
          }
        },
        {
          title: '播种人',
          align: 'center',
          width: 100,
          key: 'sownUser'
        },
        {
          title: '播种面积',
          align: 'center',
          width: 120,
          render: (h, params) => {
            return h('div', `${params.row.sownArea}亩`);
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
                              this.title = '编辑种子使用记录'
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
                              // this.del(params.row)
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
      formInfo: {
      },
      rules: {
        serialNumber: [
          {required: true, message: '请选择生产序号', trigger: 'change' }
        ],
        sowingCount: [
          {required: true, message: '请输入播种数量', trigger: 'blur' },
          {validator:isMoney3, trigger: 'blur'}
        ],
        sowingsownUserCount: [
          {required: true, message: '请输入播种人', trigger: 'blur' }
        ],
        seedCode: [
          {required: true, message: '请选择种子编码', trigger: 'change' }
        ],
        seedName: [
          {required: true, message: '请输入种子名称', trigger: 'change' }
        ],
        producer: [
          {required: true, message: '请输入生产商', trigger: 'blur' }
        ]
      },
      serialNumbers: [],
      seedCodes: [],
      id: '',
      name: '',
      yearId: '',
      serialNumber: '',
      type: '1',
      modalShow: false
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
      if (this.formInfo.seedCode) {
        this.seedCodes.forEach(e => {
          if (e.productCode === this.formInfo.seedCode) {
            this.formInfo.seedName = e.productName
            this.formInfo.unit = e.unit[0] ? e.unit[0] : ''
          }
        })
      } else {
        this.formInfo.seedName = ''
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
          this.seedCodes = response.data
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
        // sowingTime: this.search.sowingTime ? this.moment(this.search.sowingTime).format('YYYY-MM-DD') : '', // 播种时间
        beginTime: this.search.beginTime ? this.moment(this.search.beginTime).format('YYYY-MM-DD') : '', // 开始时间
        endTime: this.search.endTime ? this.moment(this.search.endTime).format('YYYY-MM-DD') : '', // 结束时间
        seedName: this.search.seedName, // 种子名称
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
        sowingCount: '', // 播种数量
        unit: '', // 播种数量 单位
        sownUser: '', // 播种人
        seedCode: '', // 种子编码
        seedName: '', // 种子名称
        producer: '', // 生产商
        preview: '', // 文字预览
      }
      this.title = '新增种子使用记录'
      this.isShow = true
    },
    onSave () {
      this.$refs['formInfo'].validate((valid) => {
          if (valid) {
              let productName = this.formInfo.seedName
              this.formInfo.id = this.formInfo.id ? this.formInfo.id : ''
              this.formInfo.plantParentId = this.activeId
              this.formInfo.sowingTime =  this.formInfo.sowingTime ? this.moment(this.formInfo.sowingTime).format('YYYY-MM-DD') : ''
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
            this.formInfo.sowingTime = list.sowingTime
            this.formInfo.baseName = list.baseName.join('、')
            this.formInfo.plotNumber = list.land.join('、')
            this.previewChange()
          }
        })
      }
    },
    // preview 编辑文字预览
    previewChange () {
      let str = ''
      str += `${this.formInfo.sowingTime ? this.moment(this.formInfo.sowingTime).format('YYYY年MM月DD日') : ''}，由${this.formInfo.sownUser}将${this.formInfo.producer}生产的${this.formInfo.seedName}种子种在${this.formInfo.baseName}的${this.formInfo.plotNumber}上，总播种面积是${this.formInfo.sownArea}亩。`
      this.formInfo.preview = str
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
