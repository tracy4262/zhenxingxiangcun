<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="reference_standard" label="安全参考标准">
                      <Select v-model="data.reference_standard" style="width: 100%">
                        <Option v-for="item in referenceStandards" :value="item.value" :key="item.value">{{ item.label }}</Option>
                      </Select>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="data.reference_standard == '有'">
                <Col span="12">
                    <Form-item prop="standard_type" label="标准类型">
                       <Select v-model="data.standard_type" style="width: 100%">
                          <Option v-for="item in standardTypes" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_name" label="标准名称">
                      <Input v-model="data.standard_name" :maxlength="50"></Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_number" label="标准号">
                      <Input v-model="data.standard_number" :maxlength="50"></Input>
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_address" label="标准颁布国家和地区">
                      <vui-cascander :values="data.standard_address" @handle-get-result="handleGetData"></vui-cascander>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                 <Col span="12">
                    <Form-item prop="is_test_report" label="是否上传检测报告">
                      <Select v-model="data.is_test_report" style="width: 100%">
                        <Option v-for="item in testReports" :value="item.value" :key="item.value">{{ item.label }}</Option>
                      </Select>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.is_test_report === '是'">
                    <Form-item prop="report_name" label="报告名称">
                      <Input v-model="data.report_name" :maxlength="50"></Input>
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.is_test_report === '是'">
                    <Form-item prop="detection_date" label="报告日期">
                      <DatePicker type="date" style="width:100%;"  @on-change="hanleChange" :options="options3" :editable="false" v-model="data.detection_date" ></DatePicker>
                    </Form-item>
                </Col>
                 <Col span="12" v-if="data.is_test_report === '是'">
                    <Form-item prop="detection_mechanism" label="检测机构">
                      <Input v-model="data.detection_mechanism" :maxlength="50"></Input>
                    </Form-item>
                </Col>
                <Col span="24" v-if="data.is_test_report === '是'">
                    <Form-item prop="detection_image" label="检测报告">
                      <vui-upload
                            ref="detectionimage"
                            @on-getPictureList="getdetectionimage"
                            :total="10"
                            :hint="'图片大小小于2M'"
                            :size="[100, 100]"
                            ></vui-upload>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="standard" label="本产品安全标准">
                      <quil-editor @quilCon="getCon" :content="data.standard" :uploadId="'securityInformation'"></quil-editor>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="categoryId == 'CP01' || categoryId == 'CP07' || categoryId == 'CP08'">
                 <Col span="12">
                    <Form-item prop="productQualification" label="产品资质">
                      <Select v-model="data.productQualification" style="width: 100%" @on-change="productQualificationChange">
                        <Option v-for="item in productQualificationS" :value="item.value" :key="item.value">{{ item.label }}</Option>
                      </Select>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="data.productQualification === '国产' && categoryId == 'CP01'">
                <Col span="24">
                    <Form-item prop="salesLicense" label="生产许可证或销售许可证">
                      <Input v-model="data.salesLicense" :maxlength="50" class="mb20"></Input>
                        <vui-upload
                              ref="salesLicenseList"
                              @on-getPictureList="getSalesLicenseList"
                              :total="10"
                              :hint="'图片大小小于2M'"
                              :size="[100, 100]"
                              ></vui-upload>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="varietyNumber" label="品种审定编号">
                      <Input v-model="data.varietyNumber" :maxlength="50" class="mb20"></Input>
                        <vui-upload
                              ref="varietyNumberList"
                              @on-getPictureList="getVarietyNumberList"
                              :total="10"
                              :hint="'图片大小小于2M'"
                              :size="[100, 100]"
                              ></vui-upload>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="originQuarantineCertificate" label="产地检疫合格证">
                      <Input v-model="data.originQuarantineCertificate" :maxlength="50" class="mb20"></Input>
                        <vui-upload
                              ref="originQuarantineCertificateList"
                              @on-getPictureList="getOriginQuarantineCertificateList"
                              :total="10"
                              :hint="'图片大小小于2M'"
                              :size="[100, 100]"
                              ></vui-upload>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="quarantineCertificate" label="检疫证书">
                      <Input v-model="data.quarantineCertificate" :maxlength="50" class="mb20"></Input>
                        <vui-upload
                              ref="quarantineCertificateList"
                              @on-getPictureList="getQuarantineCertificateList"
                              :total="10"
                              :hint="'图片大小小于2M'"
                              :size="[100, 100]"
                              ></vui-upload>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="data.productQualification === '进口' && categoryId == 'CP01'">
                <Col span="24">
                    <Form-item prop="importTradeLicense" label="进出口贸易许可证">
                      <Input v-model="data.importTradeLicense" :maxlength="50" class="mb20"></Input>
                       <vui-upload
                              ref="importTradeLicenseList"
                              @on-getPictureList="getIimportTradeLicenseList"
                              :total="10"
                              :hint="'图片大小小于2M'"
                              :size="[100, 100]"
                              ></vui-upload>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="importNumber" label="进口审批文号">
                      <Input v-model="data.importNumber" :maxlength="50" class="mb20"></Input>
                       <vui-upload
                              ref="importNumberList"
                              @on-getPictureList="getIimportNumberList"
                              :total="10"
                              :hint="'图片大小小于2M'"
                              :size="[100, 100]"
                              ></vui-upload>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="quarantineNumber" label="检疫审批单编号">
                      <Input v-model="data.quarantineNumber" :maxlength="50" class="mb20"></Input>
                       <vui-upload
                              ref="quarantineNumberList"
                              @on-getPictureList="getQuarantineNumberList"
                              :total="10"
                              :hint="'图片大小小于2M'"
                              :size="[100, 100]"
                              ></vui-upload>
                    </Form-item>
                </Col>
            </Row>
            <view-panel v-if="data.customData && data.customData.length"
                :edit="false"
                title="添加自定义字段"
                :data="data.customData"
                @on-data="handleGetSafeForm"
                @on-add="handleAddBtn('custom')"></view-panel>
            <view-panel v-if="data.qualityFormData && data.qualityFormData.length"
                :edit="false"
                title="质量信息表单"
                :data="data.qualityFormData"
                @on-data="handleGetQualityForm"
                @on-add="handleAddBtnAddPanel('quality')" class="mt20"></view-panel>
            <view-panel v-if="data.safeFormData && data.safeFormData.length"
                :edit="false"
                title="安全标准表单"
                :data="data.safeFormData"
                @on-data="handleGetSafeForm"
                @on-add="handleAddBtnSecurity('safe')" class="mt20"></view-panel>
        </Form>
        <!-- 添加面板 -->
        <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
        <add-panel-security ref="security" @on-save="handleControlBtn"></add-panel-security>
    </div>
</template>
<script>
import quilEditor from '~components/vuequilEditor'
import vuiUpload from '~components/vui-upload'
import vuiCascander from '~components/vuiCascader/index'
import addPanel from '../../../goods/components/vui-form-control/add-panel'
import viewPanel from '../../../goods/components/vui-form-control/view-panel'
import addPanelSecurity from '../../../goods/components/vui-form-control/add-panel-security'
  export default {
    components: {
      quilEditor,
      vuiUpload,
      vuiCascander,
      addPanel,
      viewPanel,
      addPanelSecurity
    },
    data () {
      return {
        data: {
          reference_standard: '', // 安全参考标准
          standard_type: '', // 标准类型
          standard_name: '', // 标准名称
          standard_number: '', // 标准号
          standard_address: '', // 标准颁布国家和地区
          is_test_report: '是', // 是否上传检测报告
          report_name: '', // 报告名称
          detection_date: '', // 报告日期
          detection_mechanism: '', // 检测机构
          detection_image: [], // 检测报告
          standard: '', // 本产品安全标准
          customData: [], // 自定义字段
          safeFormData: [], // 安全标准表单
          qualityFormData: [], // 质量信息表单
          productQualification: '', // 产品资质
          salesLicense: '', // 生产许可证或销售许可证
          salesLicenseList: '', // 生产许可证或销售许可证 照片
          varietyNumber: '', // 品种审定编号
          varietyNumberList: '', // 品种审定编号 照片
          originQuarantineCertificate: '', // 产地检疫合格证
          originQuarantineCertificateList: '', // 产地检疫合格证 照片
          quarantineCertificate: '', // 检疫证书
          quarantineCertificateList: '', // 检疫证书 照片
          importTradeLicense: '', // 进出口贸易许可证
          importTradeLicenseList: '', // 进出口贸易许可证 照片
          importNumber: '', // 进口审批文号
          importNumberList: '', // 进口审批文号 照片
          quarantineNumber: '', // 检疫审批单编号
          quarantineNumberList: '', // 检疫审批单编号 照片
        },
        safeFormData: [],
        qualityFormData: [],
        referenceStandards: [
          {value: '有', label: '有'},
          {value: '无', label: '无'}
        ],
        testReports: [
          {value: '是', label: '是'},
          {value: '否', label: '否'}
        ],
        standardTypes: [
          {value: '国家标准', label: '国家标准'},
          {value: '地方标准', label: '地方标准'},
          {value: '行业标准', label: '行业标准'},
          {value: '企业标准', label: '企业标准'},
          {value: '企业自控标准', label: '企业自控标准'}
        ],
        productQualificationS: [
          {value: '国产', label: '国产'},
          {value: '进口', label: '进口'}
        ],
        ruleInline: {},
        options3: {
					disabledDate(date) {
						return date && date.valueOf() > Date.now();
					}
        },
        name: '',
        categoryId: '',
        templateId: '',
        templateType: '',
      }
    },
    created() {
      this.categoryId = this.$route.query.categoryId
      this.templateId = this.$route.query.templateId
      this.templateType = this.$route.query.templateType
    },
    methods: {
      handleAddBtn (name) {
        this.$refs.addPanel.showAddPanel = true
        this.name = name
      },
      // 质量信息添加
      handleAddBtnAddPanel (name) {
          this.$refs.addPanel.showAddPanel = true
          this.name = name
      },
      // 安全标准添加
      handleAddBtnSecurity (name) {
          this.$refs.security.showAddPanel = true
          this.name = name
      },
      // 添加组件
      handleControlBtn (data) {
          if (this.name === 'safe') {
              this.data.safeFormData.push(data)
          } else if (this.name == 'quality'){
              this.data.qualityFormData.push(data)
          } else if (this.name == 'custom'){
              this.data.customData.push(data)
          }
      },
      // 取商品安全数据
      handleGetSafeForm (data) {
      console.log(data)
      },
      // 取商品质量数据
      handleGetQualityForm (data) {
          console.log(data)
      },
      // 获取数据
      getData (val) {
        this.data = Object.assign(this.data, val)
        if (!this.data.safeFormData) {
          this.data.safeFormData = []
        }
        if (!this.data.qualityFormData) {
          this.data.qualityFormData = []
        }
        if (!this.data.customData) {
          this.data.customData = []
        }
        this.$refs['detectionimage'].handleGive(this.data.detection_image)
        this.hanleChange()
        this.productQualificationChange()
      },
      handleGive () {
        // 生产许可证或销售许可证
        if (this.$refs['salesLicenseList']) {
          this.$refs['salesLicenseList'].handleGive(this.data.salesLicenseList)
        }
        // 品种审定编号
        if (this.$refs['varietyNumberList']) {
          this.$refs['varietyNumberList'].handleGive(this.data.varietyNumberList)
        }
        // 产地检疫合格证
        if (this.$refs['originQuarantineCertificateList']) {
          this.$refs['originQuarantineCertificateList'].handleGive(this.data.originQuarantineCertificateList)
        }
        // 检疫证书
        if (this.$refs['quarantineCertificateList']) {
          this.$refs['quarantineCertificateList'].handleGive(this.data.quarantineCertificateList)
        }
        // 进出口贸易许可证
        if (this.$refs['importTradeLicenseList']) {
          this.$refs['importTradeLicenseList'].handleGive(this.data.importTradeLicenseList)
        }
        // 进口审批文号
        if (this.$refs['importNumberList']) {
          this.$refs['importNumberList'].handleGive(this.data.importNumberList)
        }
        // 检疫审批单编号
        if (this.$refs['quarantineNumberList']) {
          this.$refs['quarantineNumberList'].handleGive(this.data.quarantineNumberList)
        }
      },
      // 点击保存
      handleSubmit() {
        this.hanleChange()
        this.$refs['data'].validate((valid) => {
          if (valid) {
            this.$emit('on-submit', true)
          } else {
            this.$emit('on-submit', false)
          }
        })
      },
      // 日期改变
      hanleChange () {
        if (this.data.detection_date) {
          this.data.detection_date = `${this.moment(this.data.detection_date).format('YYYY/MM/DD')}`
        }
      },
      productQualificationChange () {
        this.$nextTick(() => {
          this.handleGive()
        })
      },
      // 获取图片
      getdetectionimage (e) {
        var arr = []
        e.forEach(element => {
          if(element.response){
            arr.push(element.response.data.picName)
          }
        })
        this.data.detection_image = arr
      },
      // 生产许可证或销售许可证
      getSalesLicenseList (e) {
        var arr = []
        e.forEach(element => {
          if(element.response){
            arr.push(element.response.data.picName)
          }
        })
        this.data.salesLicenseList = arr
      },
      // 品种审定编号
      getVarietyNumberList (e) {
        var arr = []
        e.forEach(element => {
          if(element.response){
            arr.push(element.response.data.picName)
          }
        })
        this.data.varietyNumberList = arr
      },
      // 产地检疫合格证
      getOriginQuarantineCertificateList (e) {
        var arr = []
        e.forEach(element => {
          if(element.response){
            arr.push(element.response.data.picName)
          }
        })
        this.data.originQuarantineCertificateList = arr
      },
      // 检疫证书
      getQuarantineCertificateList (e) {
        var arr = []
        e.forEach(element => {
          if(element.response){
            arr.push(element.response.data.picName)
          }
        })
        this.data.quarantineCertificateList = arr
      },
      // 进出口贸易许可证
      getIimportTradeLicenseList (e) {
        var arr = []
        e.forEach(element => {
          if(element.response){
            arr.push(element.response.data.picName)
          }
        })
        this.data.importTradeLicenseList = arr
      },
      // 进口审批文号
      getIimportNumberList (e) {
        var arr = []
        e.forEach(element => {
          if(element.response){
            arr.push(element.response.data.picName)
          }
        })
        this.data.importNumberList = arr
      },
      // 检疫审批单编号
      getQuarantineNumberList (e) {
        var arr = []
        e.forEach(element => {
          if(element.response){
            arr.push(element.response.data.picName)
          }
        })
        this.data.quarantineNumberList = arr
      },
      getCon (msg) {
        this.data.standard = msg
      },
      //地区
      handleGetData (value, selectedData) {
				let labelArr = []
				selectedData.forEach(element => {
					labelArr.push(element.label)
				})
				this.data.standard_address = labelArr.join('/')
      },
    }
  }
</script>