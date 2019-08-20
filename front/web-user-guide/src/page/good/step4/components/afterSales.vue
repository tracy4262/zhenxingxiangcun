<template>
    <div class="pt30 pl10 pr10">
        <Form ref="data" :model="data" label-position="left" :label-width="120" :rules="ruleInline">
            <Row :gutter="32">
                <Col span="24">
                    <Form-item label="售后服务政策">
                        <Input v-model="data.servicePolicy" type="textarea" :autosize="{minRows: 3,maxRows: 4}" :maxlength="300"></Input>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item label="退换货政策">
                        <Input v-model="data.returnAndRepair" type="textarea" :autosize="{minRows: 3,maxRows: 4}" :maxlength="300"></Input>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item label="售后网点">
                        <Button type="success" ghost @click="chooseInfo" class="btn-light-primary">从基础设置中选择</Button>
                        <Button type="success" ghost @click="addInfo" class="btn-light-primary">添加</Button>
                    </Form-item>
                </Col>
            </Row>
        </Form>
        <!-- 服务网点控件 -->
        <network-station ref="networkStation" :item="data" :type="2"></network-station>
        <view-panel
            title="自定义表单"
            :data="data.formData"
            @on-data="fhandleGetForm"
            @on-add="handleAddBtnAddPanel">
        </view-panel>
        <!-- 添加面板 -->
        <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
    </div>
</template>
<script>
import addPanel from '../../../goods/components/vui-form-control/add-panel'
import viewPanel from '../../../goods/components/vui-form-control/view-panel'
import networkStation from './networkStation'
export default {
    components: {
        addPanel,
        viewPanel,
        networkStation
    },
    data() {
        return {
            data: {
                id: '',
                servicePolicy: '', // 售后服务政策
                returnAndRepair: '', // 退换货政策
                networkStation: [], // 售后网点
                formData: []
            },
            ruleInline: {}
        }
    },
    methods: {
        getData (val) {
            this.data = val
        },
        handleSubmit() {
            /* this.$refs['data'].validate((valid) => {
                if (valid) {
                    this.$emit('on-submit', true)
                } else {
                    this.$emit('on-submit', false)
                }
            }) */
        },
        chooseInfo (index) {
            this.$refs[`networkStation`].chooseInfo()
        },
        addInfo (index) {
            this.$refs[`networkStation`].addInfo()
        },
        // 取商品质量数据
        fhandleGetForm (data) {
        },
        // 质量信息添加
        handleAddBtnAddPanel () {
            this.$refs.addPanel.showAddPanel = true
        },
        // 添加组件
        handleControlBtn (data) {
            this.data.formData.push(data)
        }
    }
}
</script>
<style>

</style>
