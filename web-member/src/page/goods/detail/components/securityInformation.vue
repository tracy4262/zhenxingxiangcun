<template>
    <div class="pl20 pr20 pb20">
        <Form  ref="data" :model="data" label-position="left" :label-width="150">
            <Row :gutter="32">               
                <Col span="12">
                    <Form-item prop="reference_standard" label="安全参考标准：">
                      {{data.reference_standard}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_type" label="标准类型：">
                      {{data.standard_type}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_name" label="标准名称：">
                      {{data.standard_name}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_number" label="标准号：">
                      {{data.standard_number}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_address" label="标准颁布国家和地区：">
                      {{data.standard_address}}
                    </Form-item>
                </Col>
                 <Col span="12">
                    <Form-item prop="is_test_report" label="是否上传检测报告：">
                      {{data.is_test_report}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.is_test_report === '是'">
                    <Form-item prop="report_name" label="报告名称：">
                      {{data.report_name}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="data.is_test_report === '是'">
                    <Form-item prop="detection_date" label="报告日期：">
                      {{data.detection_date}}
                    </Form-item>
                </Col>
                 <Col span="12" v-if="data.is_test_report === '是'">
                    <Form-item prop="detection_mechanism" label="检测机构：">
                      {{data.detection_mechanism}}
                    </Form-item>
                </Col>
                <Col span="24" v-if="data.is_test_report === '是'">
                    <Form-item prop="detection_image" label="检测报告：">
                      <Row :gutter="16">
                            <Col span="6" v-for="(item, index) in data.detection_image" :key="index" class="pb20">
                                <div style="width:100%;height:100%">
                                    <img :src="item" alt="" width="100%" height="130px;">
                                </div>
                            </Col>
                        </Row>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="standard" label="本产品安全标准：">
                      <div v-html="data.standard"></div>
                    </Form-item>
                </Col>
            </Row>
        </Form>
    </div>
</template>
<script>
  export default {
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
        }
      }
    },
    methods: {
      // 获取数据
      getData (e) {
        this.data = e
      }
    }
  }
</script>