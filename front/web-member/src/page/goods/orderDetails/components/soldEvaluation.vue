<template>
    <div class="evaluation">
        <Modal
            v-model="show"
            title="评价订单"
            width="600"
            :mask-closable="false">
                <div class="">
                    <div class="pl40 pr40 pb30 pt30 evaluation" v-for="(item, index) in data" :key="index">
                        <Form :ref="`data${index}`" :model="item" :label-width="100" label-position="left" :rules="ruleInline">
                            <FormItem label="商品名称：" >
                                <Row>
                                    <Col span="7" class="tc" v-if="item.img">
                                        <img :src="item.img" alt="" width="80px" height="80px">
                                    </Col>
                                    <Col span="17">
                                        <div class="pd10">{{item.describe}}</div> 
                                    </Col>
                                </Row>
                            </FormItem>
                            <FormItem label="评分：" prop="valueHalf">
                                <Rate allow-half v-model="item.valueHalf"></Rate>
                            </FormItem>
                            <FormItem label="评语：" prop="reviews">
                                <Input v-model="item.reviews" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200"></Input>
                            </FormItem>
                        </Form>
                    </div>
                </div>
                <div slot="footer" class="tc">
                    <Button type="default" @click="show = false">取消</Button>
                    <Button type="primary" @click.native="ok">确定</Button>
                </div>
        </Modal>
    </div>
</template>
<script>
    import {isPhone2} from '~utils/validate'
    import vuiUpload from '~components/vui-upload'
    export default {
        components: {
            vuiUpload
        },
        data () {
            return {
                show: false,
                data:[],
                cancelForReturns: [
                    {label: '质量问题'},
                    {label: '与承诺不符'},
                    {label: '诚信问题'},
                    {label: '其他问题'}
                ],
                ruleInline: {
                    valueHalf: [
                        {required: true,message: '请选择评分', type: 'number' ,trigger: 'blur' }
                    ]
                },
                submit: true
            }
        },
        methods: {
            // 点击确认
            ok () {
                var data = this.data
                this.submit = true
                for(var i = 0 ;i < data.length ; i++){
                    this.$refs[`data${i}`][0].validate((valid)=>{
                        if(!valid){
                            this.submit = false
                        }
                    })
                }
                if (this.submit) {
                    this.show = false
                } else {
                    this.$Message.error('请核对表单信息')
                }
            },
            // 对话框显示
            showModal (e) {
                this.show = true
                this.data = e
            },
        }
    }
</script>
