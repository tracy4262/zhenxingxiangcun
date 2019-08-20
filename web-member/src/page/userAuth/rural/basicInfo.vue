<template>
    <div class="basic-info pt30 pl10 pr10">
        <Form ref="basicInfoForm" :model="basicInfoForm" label-position="left" :label-width="150" >
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="乡村名称">
                    <Input v-model="basicInfoForm.gov_name" readonly />
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item label="乡村住所">
                        <Input v-model="basicInfoForm.address" readonly />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="统一社会信用代码">
                        <Input v-model="basicInfoForm.organization_code" readonly />
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item label="行政区划">
                        <Input v-model="basicInfoForm.location" readonly />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="联系电话">
                        <Input v-model="basicInfoForm.phone" readonly />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="24">
                    <Form-item label="乡村简介">
                        <Input v-model="basicInfoForm.gov_profile" type="textarea" :rows="4" readonly />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="地理位置坐标">
                        <Input v-model="basicInfoForm.coordinate" readonly />
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <div style="width: 150px; float: left; color: #657180;">基层群众性自治组织特别法人统一社会信用代码证书</div>
                    <img style="width:140px; height: 140px;" v-if="basicInfoForm.unit_person_picture_list" :src="basicInfoForm.unit_person_picture_list">
                </Col>
            </Row>
         </Form>
    </div>
</template>

<script>
export default {
    data () {
        return {
            basicInfoForm:{}
        }
    }
}
</script>

<style lang="scss">
.basic-info{
    .ft12{
        text-align: center;
    }
}
</style>


