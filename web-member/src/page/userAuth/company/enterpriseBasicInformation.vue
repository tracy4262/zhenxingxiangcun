<template>
  <div class="pt30 pl10 pr10">
       <Form ref="corpInfo" :model="corpInfo" label-position="left" :label-width="150">
         <Row :gutter="32">
           <Col span="12">
            <Form-item class="input" label="企业名称">
              <Input v-model="corpInfo.corp_name" readonly />
            </Form-item>
           </Col>
           <Col span="12">
              <Form-item class="input" label="企业类型">
                <Input v-model="corpInfo.company_type" readonly />
              </Form-item>
           </Col>
         </Row>
        <Row :gutter="32">
           <Col span="12">
              <Form-item class="input" label="企业住所">
                <Input v-model="corpInfo.company_address" readonly />
              </Form-item>
           </Col>
           <Col span="12">
              <Form-item class="input" label="注册资本">
                  <Input v-model="corpInfo.registered_capital" readonly>
                      <span slot="append">万元</span>
                  </Input>
              </Form-item>
           </Col>
         </Row>
        <Row :gutter="32">
           <Col span="12">
              <Form-item class="input" label="成立日期">
                <Input v-model="corpInfo.establish_date" readonly />
              </Form-item>
           </Col>
         </Row>
          <Form-item class="input" label="经营范围">
              <Input v-model="corpInfo.business_scope" type="textarea" :autosize="{minRows: 3,maxRows: 5}" readonly />
          </Form-item>
         <Row :gutter="32">
            <Col span="12">
                <Form-item label="营业期限">
                   <Input v-model="corpInfo.busniss_term" readonly />
                </Form-item>
            </Col>
            <Col span="12">
                <Form-item label="统一社会信用代码">
                    <Input v-model="corpInfo.credit_code" placeholder="请填写18位统一社会信用代码" readonly />
                </Form-item>
            </Col>
            </Row>
           <Row :gutter="32">
            <Col span="12">
                <Form-item label="行政区划">
                    <Input v-model="corpInfo.location" readonly />
                </Form-item>
            </Col>
            <Col span="12">
                <Form-item label="联系电话">
                    <Input v-model="corpInfo.phone" readonly />
                </Form-item>
            </Col>
            </Row>
           <Row :gutter="32">
            <Col span="12">
                <Form-item label="地理位置坐标">
                    <Input v-model="corpInfo.coordinate" readonly />
                </Form-item>
            </Col>
            </Row>
           <Row :gutter="32">
            <Col span="24">
                <Form-item class="companyProfile" label="企业简介">
                    <Input v-model="corpInfo.company_profile" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
                </Form-item>
            </Col>
            </Row>
            <Form-item label="企业工商营业执照">
                <img style="width:140px; height: 140px;" v-if="corpInfo.business_license_url" :src="corpInfo.business_license_url.split(',')[0]">
                <img style="width:140px; height: 140px;" v-if="corpInfo.business_license_url" :src="corpInfo.business_license_url.split(',')[1]">
            </Form-item>
      </Form>
    </div>
</template>
<script>
    export default {
        data () {
            return {
                corpInfo:{}
            }
        }
    }

</script>
