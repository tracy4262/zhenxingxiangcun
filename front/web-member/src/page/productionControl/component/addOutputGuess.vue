<template>
    <Modal
        v-model="isShow"
        :mask-closable="false"
        :width="820"
        :title="title">
        <div>
          <Form :label-width="100" :model="info" :rules="ruleinline" ref="info" class="mr30 pt20">
            <Row :gutter="32">
              <Col span="12">
                <FormItem label="生产序号" prop="serialNumber">
                  <Input v-model="info.serialNumber"   style="width:240px" readonly  v-if="title === '编辑产量测算'"/>
                  <Select v-model="info.serialNumber" style="width:240px" @on-change="serialNumberChange" v-else>
                    <Option v-for="item in serialNumbers" :value="item.serialNumber" :key="item.id">{{ item.serialNumber }}</Option>
                  </Select>
                </FormItem>
              </Col>
               <Col span="12">
                <FormItem label="物种名称" prop="species">
                  <Input v-model="info.species"   style="width:240px" readonly/>
                </FormItem>
              </Col>
            </Row>
             <Row :gutter="32">
              <Col span="12">
                <FormItem label="品种名称" prop="varietyName">
                  <Input v-model="info.varietyName"   style="width:240px" readonly/>
                </FormItem>
              </Col>
               <Col span="12">
                  <FormItem label="品种来源" prop="varietySource">
                    <Input v-model="info.varietySource"  style="width:240px" readonly/>
                  </FormItem>
              </Col>
            </Row>
            <Row :gutter="32">
              <Col span="12">
                <FormItem label="播种面积" prop="sownArea">
                  <Input v-model="info.sownArea"  style="width:240px" readonly><span slot="append">亩</span></Input>
                </FormItem>
              </Col>
               <Col span="12">
                  <FormItem label="播种时间" prop="sowingTime">
                    <DatePicker type="date" v-model="info.sowingTime" style="width:240px" readonly></DatePicker>
                  </FormItem>
              </Col>
            </Row>

            <Row :gutter="32">
              <Col span="12">
                <FormItem label="基地名称" prop="baseName">
                  <Input v-model="info.baseName"  style="width:240px" readonly/>
                </FormItem>
              </Col>
               <Col span="12">
                  <FormItem label="地块编号"  prop="plotNumber">
                    <Input v-model="info.plotNumber"  style="width:240px" readonly/>
                  </FormItem>
              </Col>
            </Row>
            <Row :gutter="32">
              <Col span="12">
                <FormItem label="收获方式"  prop="rewardType">
                  <RadioGroup v-model="info.rewardType" @on-change="rewardTypeChange">
                      <Radio label="一次性收获"></Radio>
                      <Radio label="按批次收获"></Radio>
                  </RadioGroup>
                </FormItem>
              </Col>
               <Col span="12">
                <FormItem label="产出时间"  prop="outputTime">
                  <DatePicker type="daterange" v-model="outputTime" style="width:240px"  v-if="info.rewardType ==='按批次收货'"></DatePicker>
                  <DatePicker type="date" v-model="info.outputTime" style="width:240px" v-else></DatePicker>
                </FormItem>
              </Col>
            </Row>
            <Row :gutter="32">
              <Col span="12">
                <FormItem label="产品名称"  prop="productName">
                  <Input v-model="info.productName"  style="width:240px" :maxlength="20"/>
                </FormItem>
              </Col>
               <Col span="12">
                  <FormItem label="预计产量"  prop="production">
                    <Row>
                      <Col span="14">
                        <Input v-model="info.production"  style="width:240px" :maxlength="20">
                        </Input>
                      </Col>
                      <Col span="10" class="pl10">
                        <vuiUnit :value="info.unit" @on-get-data="onGetUnit($event)" :disabled="!edit"></vuiUnit>
                      </Col>
                    </Row>
                    <!-- <Input v-model="info.production"  style="width:240px" :maxlength="20">
                      <Select v-model="info.unit" filterable  slot="append" style="width: 100px" placeholder="请选择单位" >
                          <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{ item.unit_name }}</Option>
                      </Select>
                    </Input> -->
                  </FormItem>
              </Col>
            </Row>
          </Form>
        </div>
        <div slot="footer" class="tc">
          <Button @click="cancel">取消</Button>
          <Button type="primary" @click="onSave">确定</Button>
        </div>
    </Modal>
</template>
<script>
import {isMoney3} from '~utils/validate'
import vuiUnit from '~components/vui-unit'
export default {
  components: {
    vuiUnit
  },
  data () {
    return {
      title: '新增生产计划',
      isShow: false,
      info: {
        serialNumber: '', // 生产序号
        species: '', // 物种名称
        varietyName: '', // 品种名称
        varietyNameId: '',
        varietySource: '', // 品种来源
        sownArea: '', // 播种面积
        sowingTime: '', // 播种时间
        baseName: '', // 基地名称
        plotNumber: '', // 地块编号
        rewardType: '一次性收获', // 收获方式
        outputTime: '', // 产出时间
        productName: '', // 产品名称
        production: '', // 预计产量
        unit: 'kg', // 单位
      },
      outputTime: [],
      serialNumbers: [],
      varietyNames: [],
      ruleinline: {
        serialNumber: [
          {required: true, message: '请选择生产序号', trigger: 'blur' }
        ],
        rewardType: [
          {required: true, message: '请选择收获方式', trigger: 'change' }
        ],
        productName: [
          {required: true, message: '请输入产品名称', trigger: 'blur' }
        ],
        production: [
          {required: true, message: '请输入预计产量', trigger: 'blur' },
          {validator:isMoney3, trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 取 数据 单位
    onGetUnit($event){
      this.info.unit = $event
    },
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
    serialNumberChange () {
      let data = {
        wikiId: this.$route.query.id,
        yearId: this.$route.query.yearId,
        account: this.$user.loginAccount,
        pageNum: 1,
        pageSize: 1,
        serialNumber: this.info.serialNumber, // 根据生产序号查询
      }
      this.$api.post('/shop/plant/findPlantProductionInfo', data).then(response => {
        if (response.code === 200) {
          let list = response.data.list[0]
          this.info.productionId = list.id
          this.info.species = list.species
          this.info.varietyName = list.varietyName
          this.info.varietyNameId = list.varietyNameId
          this.info.varietySource = list.varietySource
          this.info.sownArea = list.sownArea
          this.info.sowingTime = list.sowingTime
          this.info.baseName = list.baseName.join('、')
          this.info.plotNumber = list.land.join('、')
        }
      })
    },
    // 触发
    init (title, form) {
      this.title = title
      if (form) {
        let info = Object.assign({}, form)
        if (info.rewardType == '按批次收货') {
          this.outputTime = info.outputTime.split(' — ')
        } else {
          this.outputTime = []
        }
        this.info = info
      }
      this.isShow = true
    },
    // 取物种
    onSaveSpecies (e) {
      this.info.species = e
    },
    // 取物种 id
    onSaveSpeciesId (e) {
       this.info.speciesId = e
    },
    // 取消
    cancel () {
      this.isShow = false
    },
    // 收货方式改变
    rewardTypeChange () {
      if (this.info.rewardType == '一次性收获') {
        this.info.outputTime = ''
      } else {
        this.outputTime = []
      }
    },
    onSave () {
      if (this.info.rewardType == '一次性收获') {
        this.info.outputTime =  this.info.outputTime ? this.moment(this.info.outputTime).format('YYYY-MM-DD') : ''
      } else {
        if (this.outputTime[0] && this.outputTime[1]) {
          this.outputTime[0] = this.moment(this.outputTime[0]).format('YYYY-MM-DD')
          this.outputTime[1] = this.moment(this.outputTime[1]).format('YYYY-MM-DD')
          this.info.outputTime = this.outputTime.join(' — ')
        } else {
          this.info.outputTime = ''
        }
      }
      this.info.sowingTime =  this.info.sowingTime ? this.moment(this.info.sowingTime).format('YYYY-MM-DD') : ''
      this.$refs['info'].validate((valid) => {
          if (valid) {
              this.$emit('on-save', this.info)
          } else {
              this.$Message.error('请核对表单信息！');
          }
      })
    }
  },
}
</script>
<style>

</style>

