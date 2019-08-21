<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150" :rules="ruleInline">
            <Row :gutter="32">               
                <Col span="12">
                    <Form-item prop="reference_standard" label="质量参考标准">
                      <Select v-model="data.reference_standard" style="width: 100%">
                        <Option v-for="item in testReports" :value="item.value" :key="item.value">{{ item.label }}</Option>
                      </Select>
                    </Form-item>
                </Col>
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
            </Row>
        </Form>
    </div>
</template>
<script>
import quilEditor from '~components/vuequilEditor'
import vuiUpload from '~components/vui-upload'
import vuiCascander from '~components/vuiCascader/index'
  export default {
    components: {
      quilEditor,
      vuiUpload,
      vuiCascander
    },
    data () {
      return {
        data: {
          standard: '',
          is_test_report: '是',
          report_name: '',
          detection_date: '',
          detection_mechanism: '',
          detection_image: [],
          reference_standard: '',
          standard_address: '',
          standard_type: '',
          standard_name: '',
          standard_number: ''
        },
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
        ruleInline: {},
        options3: {
					disabledDate(date) {
						return date && date.valueOf() > Date.now();
					}
        },
      }
    },
    methods: {
      // 获取数据
      getData (e) {
        this.data = e 
        this.$refs['detectionimage'].handleGive(this.data.detection_image)
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