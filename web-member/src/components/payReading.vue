<template>
    <div class="wrapper layout">
        <top :address="false" />

        <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="20">
                        <div class="wrapper-container">
                            <div class="content">
                                <Form ref="payInfo" :model="payInfo" label-position="right" :label-width="240" :rules="ruleInline">
                                    <Form-item prop="" class="input" label="有偿阅读：">
                                        <i-switch size="large">
                                            <span slot="open">有偿</span>
                                            <span slot="close">无偿</span>
                                        </i-switch>
                                    </Form-item>
                                    <Tabs type="card" class="tabs">
                                        <TabPane label="本条消息金额">
                                            <Form-item prop="" class="input" label="设置金额：">
                                                <Input class="item">
                                                    <span slot="prepend">￥</span>
                                                </Input>
                                                <!--<span class="tip">您的实际入账金额为用户付款金额的80%</span>-->
                                            </Form-item>
                                            <Form-item prop="" class="input" label="引导介绍：">
                                                <Input type="textarea" class="item"
                                                       :maxlength="150" placeholder="请输入内容"
                                                       :autosize="{minRows: 2,maxRows: 5}" />
                                            </Form-item>
                                            <Form-item prop="" class="input" label="设置预览：">
                                                <i-switch size="large">
                                                    <span slot="open">有</span>
                                                    <span slot="close">无</span>
                                                </i-switch>
                                            </Form-item>
                                            <FormItem prop="" label="文件类型：">
                                                <RadioGroup v-model="payInfo.file_type">
                                                    <Radio label="file">文件</Radio>
                                                    <Radio label="video">视频</Radio>
                                                    <Radio label="audio">音频</Radio>
                                                    <Radio label="picture">图片</Radio>
                                                </RadioGroup>
                                            </FormItem>
                                            <Form-item prop="" label="上传预览文件：">
                                                <div class="demo-upload-list" v-for="(item,index) in payInfo.preview_list" :key="index">
                                                    <template v-if="item.status === 'finished'">
                                                        <img style="width:58px; height: 58px;" :src="'http://'+item.response.data.picName">
                                                        <div class="demo-upload-list-cover">
                                                            <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
                                                        </div>
                                                    </template>
                                                    <template v-else>
                                                        <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
                                                    </template>
                                                </div>
                                                <Upload ref="upload" :show-upload-list="false"
                                                        name="upfile"
                                                        :format="['jpg','png']"
                                                        :max-size="204800"
                                                        :on-success="handleSuccess"
                                                        :before-upload="handleBeforeUpload"
                                                        type="drag"
                                                        :action="action"
                                                        style="display: inline-block;width:58px;">
                                                    <div style="width: 58px;height:58px;line-height: 58px;">
                                                        <Icon type="md-add-circle" size="20"></Icon>
                                                    </div>
                                                </Upload>
                                            </Form-item>
                                            <Form-item prop="" label="上传完整文件：">
                                                <div class="demo-upload-list" v-for="(item,index) in payInfo.full_list" :key="index">
                                                    <template v-if="item.status === 'finished'">
                                                        <img style="width:58px; height: 58px;" :src="'http://'+item.response.data.picName">
                                                        <div class="demo-upload-list-cover">
                                                            <Icon type="ios-trash-outline" @click.native="handleRemove2(item)"></Icon>
                                                        </div>
                                                    </template>
                                                    <template v-else>
                                                        <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
                                                    </template>
                                                </div>
                                                <Upload ref="upload2" :show-upload-list="false"
                                                        name="upfile"
                                                        :format="['jpg','png']"
                                                        :max-size="204800"
                                                        :on-success="handleSuccess2"
                                                        :before-upload="handleBeforeUpload2"
                                                        type="drag"
                                                        :action="action"
                                                        style="display: inline-block;width:58px;">
                                                    <div style="width: 58px;height:58px;line-height: 58px;">
                                                        <Icon type="md-add-circle" size="20"></Icon>
                                                    </div>
                                                </Upload>
                                            </Form-item>
                                            <div style="margin-left: 220px; margin-top: 40px;">
                                                <Button type="primary" shape="circle" class="button">保存</Button>&nbsp;&nbsp;
                                                <Button type="primary" shape="circle" class="button">取消</Button>
                                            </div>
                                        </TabPane>
                                        <TabPane label="订阅我的消息">
                                            <FormItem prop="" label="订阅时间：">
                                                <RadioGroup v-model="payInfo.subscription_time">
                                                    <Radio label="one_month">一个月</Radio>
                                                    <Radio label="three_month">三个月</Radio>
                                                    <Radio label="half_year">半年</Radio>
                                                    <Radio label="one_year">一年</Radio>
                                                </RadioGroup>
                                                <p class="tip">订阅用户将能免费查看您发布的所有有偿信息</p>
                                            </FormItem>
                                            <Form-item prop="" class="input" label="设置金额：">
                                                <Input class="item">
                                                    <span slot="prepend">￥</span>
                                                </Input>
                                                <span class="tip">您的实际入账金额为用户付款金额的80%</span>
                                            </Form-item>
                                            <div style="margin-left: 220px; margin-top: 40px;">
                                                <Button type="primary" shape="circle" class="button">保存</Button>&nbsp;&nbsp;
                                                <Button type="primary" shape="circle" class="button">取消</Button>
                                            </div>
                                        </TabPane>
                                    </Tabs>
                                </Form>
                            </div>
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
        <foot></foot>
    </div>
</template>

<script>
    import top from '../top'
    import foot from '../foot'
    export default {
        components: {
            top,
            foot
        },
        data () {
            return {
                action: `${this.$url.upload}/upload/up`,
                payInfo: {
                    file_type: 'file',
                    subscription_time: 'one_month',
                    preview_list: [],
                    full_list: []
                },
                ruleInline: {
                    corp_name: [
                        { required: true, message: '企业名称不能为空', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            handleBeforeUpload () {
                const check = this.$refs.upload.fileList.length < 5
                if (!check) {
                    this.$Notice.warning({
                        title: '最多只能上传 5 张图片。'
                    })
                }
                return check
            },
            // 图片上传
            handleSuccess (response, file, fileList) {
                if (response.code === 500) {
                    this.$Message.error('上传失败!')
                } else {
                    this.$Message.success('上传成功!')
                    this.payInfo.preview_list = this.$refs.upload.fileList
                }
            },
            // 从 upload 实例删除数据
            handleRemove (file) {
                // 先从服务器上把图片删除
                const fileList = this.payInfo.preview_list
                this.payInfo.preview_list.splice(fileList.indexOf(file), 1)
            },
            handleBeforeUpload2 () {
                const check = this.$refs.upload2.fileList.length < 5
                if (!check) {
                    this.$Notice.warning({
                        title: '最多只能上传 5 张图片。'
                    })
                }
                return check
            },
            // 图片上传
            handleSuccess2 (response, file, fileList) {
                if (response.code === 500) {
                    this.$Message.error('上传失败!')
                } else {
                    this.$Message.success('上传成功!')
                    this.payInfo.full_list = this.$refs.upload2.fileList
                }
            },
            // 从 upload 实例删除数据
            handleRemove2 (file) {
                // 先从服务器上把图片删除
                const fileList = this.payInfo.full_list
                this.payInfo.full_list.splice(fileList.indexOf(file), 1)
            }
        }
    }
</script>

<style scoped>
    .tabs {
        margin-left: 150px;
    }
    .item {
        width: 300px;
    }
    .tip {
        color: #999999;
        font-size: small;
    }
    .button {
        width: 110px;
        height: 30px;
    }
 </style>

