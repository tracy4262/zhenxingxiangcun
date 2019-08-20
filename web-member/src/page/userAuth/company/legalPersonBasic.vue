<template>
  <div class="pt30 pl10 pr10">
    <Form ref="corpInfo2" :model="corpInfo2" label-position="left" :label-width="150">
          <Row :gutter="32">
              <Col span="12">
                <Form-item class="input" label="法定代表人">
                    <Input v-model="corpInfo2.legal_person" readonly />
                </Form-item>
              </Col>
              <Col span="12">
                <Form-item class="input" label="法人身份证号码">
                    <Input v-model="corpInfo2.identification_card" readonly />
                </Form-item>
              </Col>
          </Row>
          <Row :gutter="32">
              <Col span="12">
                <Form-item class="input" label="法人手机号码">
                    <Input v-model="corpInfo2.mobile" readonly />
                </Form-item>
              </Col>
              <Col span="12">
                <Form-item class="input" label="法人邮箱">
                    <Input v-model="corpInfo2.email" readonly />
                </Form-item>
              </Col>
          </Row>
          <Row :gutter="32">
              <Col span="24">
                <Form-item label="法人介绍">
                    <Input v-model="corpInfo2.legal_person_introduce" type="textarea"
                            :autosize="{minRows: 3,maxRows: 5}" readonly />
                </Form-item>
              </Col>
          </Row>
          <Form-item prop="pictureList" label="法人身份证">
            <img style="width:140px; height: 140px;" v-if="corpInfo2.identification_card_url" :src="corpInfo2.identification_card_url.split(',')[0]">
            <img style="width:140px; height: 140px;" v-if="corpInfo2.identification_card_url" :src="corpInfo2.identification_card_url.split(',')[1]">
          </Form-item>
    </Form>
  </div>
</template>
<script>
    export default{
        data () {
            return {
                corpInfo2: {}
            }
        }
    }
</script>
