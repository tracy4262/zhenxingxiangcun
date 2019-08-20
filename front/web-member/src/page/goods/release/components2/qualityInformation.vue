<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="reference_standard" label="质量参考标准">
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
                    <Form-item prop="detection_date" label="检测日期">
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
                    <Form-item prop="standard" label="本产品质量标准">
                      <quil-editor @quilCon="getCon" :content="data.standard"></quil-editor>
                    </Form-item>
                </Col>
                <!-- <Col span="24">
                    <Form-item prop="exterior" label="外观指标">
                      <quilEditor ref="exterior" :content="data.exterior" @quilCon="getExterior" uploadId="exterior" myQuillEditor="exterior">
                      </quilEditor>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="quality" label="品质指标">
                      <quilEditor ref="quality" :content="data.quality" @quilCon="getQuality" uploadId="quality" myQuillEditor="quality">
                      </quilEditor>
                    </Form-item>
                </Col> -->
            </Row>
            <view-panel v-if="data.customData && data.customData.length"
                :edit="false"
                title="添加自定义字段"
                :data="data.customData"
                @on-data="handleGetSafeForm"
                @on-add="handleAddBtn"></view-panel>
        </Form>
        <!-- 添加面板 -->
        <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
    </div>
</template>
<script>
import quilEditor from '~components/vuequilEditor'
import vuiUpload from '~components/vui-upload'
import vuiCascander from '~components/vuiCascader/index'
import addPanel from '../../../goods/components/vui-form-control/add-panel'
import viewPanel from '../../../goods/components/vui-form-control/view-panel'
  export default {
    components: {
      quilEditor,
      vuiUpload,
      vuiCascander,
      addPanel,
      viewPanel
    },
    data () {
      return {
        data: {
          reference_standard: '', // 质量参考标准
          standard_type: '', // 标准类型
          standard_name: '', // 标准名称
          standard_number: '', // 标准号
          standard_address: '', // 标准颁布国家和地区
          is_test_report: '是', // 是否上传检测报告
          report_name: '', // 报告名称
          detection_date: '', // 检测日期
          detection_mechanism: '', // 检测机构
          detection_image: [], // 检测报告
          standard: '', // 本产品质量标准
          quality: '', // 外观指标
          exterior: '', // 品质指标
          customData: [], // 自定义字段
        },
        referenceStandards: [
          {value: '有', label: '有'},
          {value: '无', label: '无'}
        ],
        testReports: [
          {value: '是', label: '是'},
          {value: '否', label: '否'}
        ],
        standardTypes: [ // 国家标准/行业标准/地方标准/企业标准/企业自控标准
          {value: '国家标准', label: '国家标准'},
          {value: '地方标准', label: '地方标准'},
          {value: '行业标准', label: '行业标准'},
          {value: '企业标准', label: '企业标准'},
          {value: '企业自控标准', label: '企业自控标准'}
        ],
        ruleInline: {},
        options3: {
					disabledDate(date) {
						return date && date.valueOf() > Date.now();
					}
        },
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
      // 添加组件
      handleControlBtn (data) {
          this.data.customData.push(data)
      },
      handleAddBtn () {
      this.$refs.addPanel.showAddPanel = true
      },
      // 取商品安全数据
      handleGetSafeForm (data) {
      console.log(data)
      },
      // 获取数据
      getData (val) {
        this.data = Object.assign(this.data, val)
        this.$refs['detectionimage'].handleGive(this.data.detection_image)
        this.hanleChange()
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
      getCon (msg) {
        this.data.standard = msg
      },
      getExterior (msg) {
        this.data.exterior = msg
      },
      getQuality (msg) {
        this.data.quality = msg
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