<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" />
    <Form ref="formItem" :model="form" label-position="left" :label-width="100" :rules="formItemInline" class="mt40">
        <Row>
            <Col span="12">
                <Form-item label="权限">
                    <i-switch  v-model="form.status" size="large" :disabled="true" >
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </Form-item>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
                <Form-item label="政治面貌">
                    <Select v-model="form.policy" clearable style="width: 250px;" :disabled="true" >
                        <Option v-for="(item, index) in policyList" :key="index" :value="item.value">{{ item.label }}</Option>
                    </Select>
                </Form-item>
            </Col>
            <Col span="12">
                <Form-item label="加入时间">
                    <DatePicker v-model="form.time" type="month" :options="options" :disabled="true"  format="yyyy年MM月" 
                        style="width: 250px;"></DatePicker>
                </Form-item>
            </Col>
        </Row>
    </Form>
    <Title title="文字预览"/>
    <div class="pd20 tc pt30 mt20">
        <Input v-model="preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        <Button type="primary" @click="handleSave()" class="mt40">保存</Button>
    </div>
  </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    import Title from '../../components/title'
    export default {
        components: {
            vuiUpload,
            Title
        },
        props: {
            modeId: {
                type: String
            },
            yearId: {
                type: String
            },
            appId: {
                type: String
            }
        },
        data () {
            return {
                title: '政治面貌',
                formItemInline: {},
                form: {
                    status: true,
                    policy: '',
                    time: ''
                },
                options: {
                    disabledDate (date) {
                        return date && date.valueOf() > Date.now()
                    }
                },
                preview: '',
                policyList: [
                    {
                        label: '中国共产党',
                        value: '中国共产党'
                    },
                    {
                        label: '中国共青团',
                        value: '中国共青团'
                    },
                    {
                        label: '中国民主同盟',
                        value: '中国民主同盟'
                    },
                    {
                        label: '中国民主建国会',
                        value: '中国民主建国会'
                    },
                    {
                        label: '中国民主促进会',
                        value: '中国民主促进会'
                    },
                    {
                        label: '中国致公党',
                        value: '中国致公党'
                    },
                    {
                        label: '九三学社',
                        value: '九三学社'
                    },
                    {
                        label: '台湾民主自治同盟',
                        value: '台湾民主自治同盟'
                    },
                    {
                        label: '国民党',
                        value: '国民党'
                    },
                    {
                        label: '民主党',
                        value: '民主党'
                    },
                    {
                        label: '无党派',
                        value: '无党派'
                    },
                    {
                        label: '民进党',
                        value: '民进党'
                    }
                ],
                id: ''
            }
        },
        watch: {
            modeId: {
                handler (newValue, oldValue) {
                    this.init()
                },
                deep: true
            }
        },
        created () {
            if (this.modeId !== '' && this.modeId !== undefined) {
                this.init()
            }
        },
        methods: {
            // 初始化加载数据
            init () {
                this.$api.post('/member-reversion/politicalFace/findPoliticalFace', {
                    user_id: this.$user.loginAccount,
                    templateId: this.$template.id,
                    year_id: this.yearId,
                    parent_id: this.modeId
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.politicalFace_name && response.data.politicalFace_name !== '') {
                            this.title = response.data.politicalFace_name
                        }
                        if (response.data.textPreview.text_preview && response.data.textPreview.text_preview !== '') {
                            this.preview = response.data.textPreview.text_preview
                            this.id = response.data.textPreview.id
                        }
                        // 判断对象是否为空
                        if (JSON.stringify(response.data.politicalFace) !== '{}') {
                            this.form = {
                                id: response.data.politicalFace.id,
                                status: response.data.politicalFace.status === 1 ? true : false,
                                policy: response.data.politicalFace.political_face_model,
                                time: response.data.politicalFace.join_time_model
                            }
                        }
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            handleSave () {
                this.$api.post('/member-reversion/politicalFace/savePoliticalFace', {
                    user_id: this.$user.loginAccount,
                    yearId: this.yearId,
                    parent_id: this.appId,
                    politicalFace_name: this.title,
                    status: this.form.status,
                    sys_dict_id: this.modeId,
                    templateId: this.$template.id,
                    textPreview: {
                        id: this.id === '' || this.id === undefined ? 0 : this.id,
                        text_preview: this.preview,
                        is_complete: true
                    },
                    politicalFace: {
                        id: this.form.id === '' || this.form.id  === undefined ? 0 : this.form.id,
                        political_face_model: this.form.policy,
                        join_time_model: this.moment(this.form.time).format('YYYY-MM')
                    }
                }).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init()
                        this.$emit('on-save')
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            }
        },
        mounted () {
            this.preview = `政治面貌（），加入时间（）。`
        }
    }
</script>
<style lang="scss" scoped>
</style>