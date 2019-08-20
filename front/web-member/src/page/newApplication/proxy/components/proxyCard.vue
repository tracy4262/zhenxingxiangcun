<!--
    作者：chenqim
    时间：2018-12-14
    描述：代理card
-->
<template>
    <div class="mt20 ml10 mr10 proxy-card-shadow">
        <Row class="pd20" type="flex" align="top">
            <Col span="6">
                <Avatar v-if="item.avatar !== ''" class="cus" size="large" :src="item.avatar" />
                <Avatar v-else class="cus" size="large" src="../../../../../static/img/user-icon-big.png" />
            </Col>
            <Col span="18">
                <div class="proxy-name ell" :title="item.name">{{ item.name === undefined || item.name === '' ? '暂无会员名称' : item.name }}</div>
                <div class="proxy-account mt5">登录名：{{ item.account }}</div>
                <div v-if="type === 1 || type === 2" class="proxy-account mt5">注册时间：{{ item.registerTime.substr(0, 10) }}</div>
                <div v-else class="proxy-account mt5">代理时间：{{ item.proxyTime.substr(0, 10) }}</div>
            </Col>
        </Row>
        <!-- 已代理页面中的card -->
        <Row class="proxy-button-bar tc" type="flex" align="middle" v-if="type === 0">
            <Col span="12">
                <a class="proxy-button" @click="memberCenter">会员中心</a>
            </Col>
            <Col span="12" style="border-left: 1px solid #ececec;">
                <a class="proxy-button" @click="cancelProxy">取消代理</a>
            </Col>
        </Row>
        <!-- 申请代理页面中 查找账号 的card -->
        <Row class="proxy-button-bar tc" type="flex" align="middle" v-if="type === 1">
            <Col span="24">
                <a v-if="item.type === 1 && item.status === 2" class="proxy-button disabled">代理审核中</a>
                <a v-else-if="item.type === 0 && item.status === 2" class="proxy-button disabled">取消代理审核中</a>
                <a v-else-if="item.type === 1 && item.status === 1" class="proxy-button disabled">已被代理</a>
                <a v-else-if="item.account === $user.loginAccount" class="proxy-button disabled">不能代理自己</a>
                <a v-else class="proxy-button" @click="wantToProxy">我要代理</a>
            </Col>
        </Row>
        <!-- 申请代理页面中 完善资料中 的card -->
        <Row class="proxy-button-bar tc" type="flex" align="middle" v-if="type === 2">
            <Col span="12">
                <a class="proxy-button" @click="perfectInfo">完善资料</a>
            </Col>
            <Col span="12" style="border-left: 1px solid #ececec;">
                <a class="proxy-button" @click="notProxy">暂不代理</a>
            </Col>
        </Row>
        <!-- 取消代理弹出框 -->
        <Modal v-model="cancelProxyModal" width="500" title="取消代理" :mask-closable="false">
            <Form ref="cancelProxyForm" :model="cancelProxyModel" :rules="cancelProxyRule" :label-width="80">
                <!-- <FormItem label="取消代理协议模板">
                    <Button @click="download">下载模板</Button>
                </FormItem>
                <FormItem prop="uploadProtocol" label="取消代理协议模板">
                    <vuiUploadFile
                        ref="uploadCancelProxyProtocol"
                        :format="format"
                        cover
                        @on-getFileList="getList($event)">
                    </vuiUploadFile>
                </FormItem> -->
                <FormItem prop="cancelReason" label="解除理由">
                  <Select v-model="cancelProxyModel.cancelReason">
                    <Option value="代理协议到期">代理协议到期</Option>
                    <Option value="双方协商解除代理">双方协商解除代理</Option>
                    <Option value="其他原因">其他原因</Option>
                  </Select>
                </FormItem>
                <FormItem prop="otherReason" label="其他原因" v-if="cancelProxyModel.cancelReason === '其他原因'">
                  <Input v-model="cancelProxyModel.otherReason" type="textarea"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancelCancelProxy">取消</Button>
                <Button type="primary" @click="okCancelProxy">确定</Button>
            </div>
        </Modal>
        <!-- 代理弹出框 -->
        <Modal v-model="proxyModal" width="500" title="我要代理" :mask-closable="false">
            <Form ref="proxyForm" :model="proxyModel" :rules="proxyRule">
                <FormItem label="代理协议模板">
                    <Button @click="download2">下载模板</Button>
                </FormItem>
                <FormItem prop="uploadProtocol" label="代理协议模板">
                    <vuiUploadFile
                        ref="uploadProxyProtocol"
                        :format="format"
                        cover
                        @on-getFileList="getList2($event)">
                    </vuiUploadFile>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancelProxy2">取消</Button>
                <Button type="primary" @click="okProxy">确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import vuiUploadFile from '~components/vui-upload-file'
export default {
    name: 'proxyCard',
    components: {
        vuiUploadFile
    },
    props: {
        item: {
            type: Object
        },
        type: {
            type: Number,
            default: 0
        }
    },
    data () {
        return {
            cancelProxyModal: false,
            cancelProxyModel: {
                // protocolTemplate: '',
                // uploadProtocol: ''
                cancelReason: '',
                otherReason: ''
            },
            cacelUploadPdf: '',
            cancelProxyRule: {
                // uploadProtocol: [
                //     { required: true, message: '请上传取消代理协议模板', trigger: 'change' }
                // ]
                cancelReason: [
                  { required: true, message: '请选择解除理由', trigger: 'change' }
                ],
                otherReason: [
                  { required: true, message: '请填写其他原因', trigger: 'blur' }
                ]
            },
            format: ['doc', 'docx', 'png', 'jpg'],
            proxyModal: false,
            proxyModel: {
                protocolTemplate: '',
                uploadProtocol: ''
            },
            uploadPdf: '',
            proxyRule: {
                uploadProtocol: [
                    { required: true, message: '请上传代理协议模板', trigger: 'change' }
                ]
            },
            proxyProtocal: '',
            cancelProxyProtocal: ''
        }
    },
    created () {

    },
    methods: {
        // 获取 代理/取消代理 协议模板
        getProtocol (fileType) {
            this.$api.post('/member/reversionProxy/proxyTemplate', {
                fileType: fileType  //1:代理模版 2、取消代理模版
            }).then(response => {
                console.log('res', response)
                if (response.code === 200) {
                    if (fileType === 1) {
                        this.proxyProtocal = response.data.fileUrl
                        this.proxyModel.protocolTemplate = response.data.fileUrl
                    } else if (fileType === 2) {
                        this.cancelProxyProtocal = response.data.fileUrl
                        this.cancelProxyModel.protocolTemplate = response.data.fileUrl
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        memberCenter () {
            window.open(`${window.location.origin}/pro/member?uid=${this.item.account}&type=proxy`, "_blank")
        },
        cancelProxy () {
            // this.getProtocol(2)
            this.cancelProxyModal = true
        },
        cancelCancelProxy () {
            this.cancelProxyModal = false
        },
        okCancelProxy () {
            this.$refs['cancelProxyForm'].validate((valid) => {
                if (valid) {
                    this.$api.post('/member/reversionProxy/proxyOrCancle', {
                        account: this.item.account, //需要被代理的账号
                        proxyAccount: this.$user.loginAccount,  //代理人账号
                        // cacelProtocolTemplate: this.cancelProxyModel.protocolTemplate,  //取消代理协议模板url
                        // cacelUploadProtocol: this.cancelProxyModel.uploadProtocol,  //上传取消代理协议url
                        // cancelUploadPdf: this.cancelUploadPdf,
                        type: 0,  //0:取消代理模板，1:代理模板
                        cancelReason: this.cancelProxyModel.cancelReason,
                        otherReason: this.cancelProxyModel.otherReason
                    }).then(response => {
                        console.log('res', response)
                        if (response.code === 200) {
                            this.cancelProxyModal = false
                            this.$emit('refresh')
                            this.$Message.success('取消代理成功，审核工作将在三个工作日内完成！')
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        },
        // 下载取消代理协议模板
        download () {
            if (this.cancelProxyProtocal !== '') {
                window.location.href = this.cancelProxyProtocal
                this.$Message.success('下载成功！')
            } else {
                this.$Message.error('下载失败！')
            }
        },
        // 下载代理协议模板
        download2 () {
            if (this.proxyProtocal !== '') {
                window.location.href = this.proxyProtocal
                this.$Message.success('下载成功！')
            } else {
                this.$Message.error('下载失败！')
            }
        },
        getList ($event) {
            if ($event[0]) {
                console.log('res', $event[0].response)
                this.cancelProxyModel.uploadProtocol = $event[0].response.data.origin
                this.cacelUploadPdf = $event[0].response.data.src
                this.$refs['cancelProxyForm'].validate()
            } else {
                this.cancelProxyModel.uploadProtocol = ''
                this.cacelUploadPdf = ''
            }
        },
        getList2 ($event) {
            if ($event[0]) {
                console.log('res', $event[0].response)
                this.proxyModel.uploadProtocol = $event[0].response.data.origin
                this.uploadPdf = $event[0].response.data.src
                this.$refs['proxyForm'].validate()
            } else {
                this.proxyModel.uploadProtocol = ''
                this.uploadPdf = ''
            }
        },
        wantToProxy () {
            // this.getProtocol(1)
            // this.$api.post('/member/reversionProxy/toProxy', {
            //     account: this.item.account,
	          //   proxyAccount: this.$user.loginAccount
            // }).then(response => {
            //     if (response.code === 200) {
                    
            //     } else {
            //         this.$Message.error('服务器异常！')
            //     }
            // }).catch(error => {
            //     this.$Message.error('服务器异常！')
            // })
            // this.proxyModal = true
            this.$emit('want-to-proxy', this.item.account)
        },
        perfectInfo () {
            // this.getProtocol(1)
            // this.proxyModal = true
            this.$emit('want-to-proxy', this.item.account)
        },
        cancelProxy2 () {
            this.proxyModal = false
        },
        okProxy () {
            this.$refs['proxyForm'].validate((valid) => {
                if (valid) {
                    this.$api.post('/member/reversionProxy/proxyOrCancle', {
                        account: this.item.account, //需要被代理的账号
                        proxyAccount: this.$user.loginAccount,  //代理人账号
                        protocolTemplate: this.proxyModel.protocolTemplate,  //协议模板url
                        uploadProtocol: this.proxyModel.uploadProtocol,  //上传代理协议url
                        uploadPdf: this.uploadPdf,
                        type: 1  //0:取消代理模板，1:代理模板
                    }).then(response => {
                        console.log('res', response)
                        if (response.code === 200) {
                            this.proxyModal = false
                            this.$emit('refresh')
                            this.$Message.success('代理协议模板上传成功，审核工作将在三个工作日内完成！')
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        },
        notProxy () {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认暂不代理？',
                onOk:()=>{
                    if (this.item.id) {
                        this.$api.post('/member/reversionProxy/noProxy', {
                            id: this.item.id
                        }).then(response => {
                            console.log('res', response)
                            if (response.code === 200) {
                                this.$Message.success('暂不代理操作成功！')
                                this.$emit('refresh')
                            }
                        }).catch(error => {
                            this.$Message.error('服务器异常！')
                        })
                    } else {
                        this.$Message.error('服务器异常！')
                    }
                },
                okText:'确定',
                cancelText:'取消'
            })
        }
    }
}
</script>
<style lang="scss" scoped>
    .proxy-card-shadow {
        border: 1px solid #f5f5f5;
        &:hover {
            transition: 0.5s;
            box-shadow: 0 5px 5px 0 rgba(18,88,48,.09);
        }
    }
    .proxy-name {
        font-size: 16px;
        color: #000 85%;
    }
    .proxy-account {
        color: #9B9B9B;
    }
    .proxy-button-bar {
        border-top: 1px solid #f5f5f5;
        background-color: #f6f9fa;
        height: 48px;
    }
    .proxy-button {
        color: #9c9fa0;
        &:hover {
            color: #00c882;
        }
    }
    .cus.ivu-avatar-large {
        width: 40px;
        height: 40px;
        line-height: 39px;
        border-radius: 20px;
    }
    .disabled {
        cursor: not-allowed;
    }
</style>
