<template>
    <div class="pt30 pl10 pr10">
        <Form  ref="data" :model="data" label-position="left" :label-width="150">
            <Form-item prop="honorInfo" label="商品荣誉信息">
                <vuequilleditor ref="honorInfo" :content="data.honorInfo" @quilCon="getCon" uploadId="honor" myQuillEditor="honor">
                </vuequilleditor>
            </Form-item>
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
import vuequilleditor from '~components/vuequilEditor'
import addPanel from '../../../goods/components/vui-form-control/add-panel'
import viewPanel from '../../../goods/components/vui-form-control/view-panel'
    export default {
        components: {
            vuequilleditor,
            addPanel,
            viewPanel
        },
        data () {
            return {
                data: {
                    honorInfo: '', // 商品荣誉信息
                    customData: [], // 自定义字段
                }
            }
        },
        methods:{
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
            // 商品荣誉信息
            getCon (msg) {
                this.data.honorInfo = msg
            },
            getData (val) {
                this.data = Object.assign(this.data, val)
            },
            handleSubmit () {
                this.$emit('on-submit',true)
            }
        }
    }
</script>
<style>

</style>
