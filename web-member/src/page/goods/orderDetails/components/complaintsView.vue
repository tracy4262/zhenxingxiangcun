<template>
    <div>
        <Modal
            v-model="show"
            title="投诉商家"
            width="600"
            :mask-closable="false">
                <div class="pl40 pr20">
                    <Form ref="data" :model="data" :label-width="100" label-position="left">
                        <FormItem label="退货原因：" prop="reason">
                            {{data.reason}}
                        </FormItem>
                        <FormItem label="退款说明：" prop="describeInfo">
                            {{data.describeInfo}}
                        </FormItem>
                        <FormItem label="联系电话：" prop="mobile">
                            {{data.mobile}}
                        </FormItem>
                        <FormItem label="上传凭证：" prop="picUrl">
                            <div>
                                <Row>
                                    <Col span="8" class="pd10" v-for="(item, index) in data.picList" :key="index">
                                        <img :src="item" alt="" width="116px" height="116px">
                                    </Col>
                                </Row>
                            </div>
                        </FormItem>
                    </Form>
                </div>
                <div slot="footer" class="tc">
                    <Button type="default" @click="show = false">取消</Button>
                    <!-- <Button type="primary" @click.native="ok">确定</Button> -->
                </div>
        </Modal>
    </div>
</template>
<script>
    export default {
        data () {
            return {
                show: false,
                data: {
                    reason: '' ,// 退货原因
                    describeInfo:'', // 退货说明
                    mobile:'', // 联系电话
                    picUrl: '' // 上传凭证 5张
                },
                account: '',                
                loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            }
        },
        created() {
            this.account = this.loginUser.loginAccount
        },
        methods: {
            // 对话框显示
            showModal (sellAccount, orderCode) {
                this.data.sellerAccount = sellAccount
                this.data.orderCodeId = orderCode
                this.show = true
                this.$api.post('/nswy-portal-service/shop/complaint/list', {account: this.account, orderCode: orderCode}).then(response => {
                  if (response.code === 200) {
                    this.data = response.data[0]
                  }
                })
            }
        }
    }
</script>