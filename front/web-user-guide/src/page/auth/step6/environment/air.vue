<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" />
    <div class="mt40">
        <Form ref="formItem" :model="form" label-position="left" :label-width="100" :rules="formItemInline">
            <Row>
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="form.status" size="large" :disabled="true">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item prop="aqi" label="空气质量指数（AQI）">
                        <Input v-model="form.aqi" :maxlength="10" :disabled="true"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item prop="pm25" label="PM2.5浓度">
                        <Input v-model="form.pm25" :maxlength="10" :disabled="true"><span slot="append">μg/m³</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item prop="pm10" label="PM10浓度">
                      <Input v-model="form.pm10" :maxlength="10" :disabled="true"><span slot="append">μg/m³</span></Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item prop="level" label="空气质量等级">
                        <Select v-model="form.level" :disabled="true">
                            <Option value="1">一级</Option>
                            <Option value="2">二级</Option>
                            <Option value="3">三级</Option>
                            <Option value="4">四级</Option>
                            <Option value="5">五级</Option>
                            <Option value="6">六级</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="8">
                    <a href="javascript:;" class="t-grey" @click="open">查看“空气质量状况判断标准表”</a>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item prop="time" label="更新时间">
                        <DatePicker type="datetime" v-model="form.time" :disabled="true" format="yyyy-MM-dd HH:mm"></DatePicker>
                    </Form-item>
                </Col>
            </Row>
            <Form-item label="检测报告">
                <vui-upload
                    ref="air"
                    :disabled="true"
                    @on-getPictureList="getList"
                    :hint="'图片大小小于2MB，支持后缀名png jpg'"
                    :total="10"
                    :size="[80,80]"
                ></vui-upload>
            </Form-item>
        </Form>
    </div>
    <Title title="文字预览"/>
    <div class="pd20 tc pt30">
        <Input v-model="preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        <Button type="primary" @click="handleSave()" class="mt40">保存</Button>
    </div>
    <Modal
        v-model="modal"
        title="空气质量状况判断标准表"
        draggable
        width="360">
        <div>
            <Table border :columns="columns" :data="data"></Table>
        </div>
    </Modal>
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
            }
        },
        data () {
            return {
                title: '空气质量信息',
                modal: false,
                formItemInline: {
                },
                form: {
                    status: true,
                    aqi: '',
                    pm25: '',
                    pm10: '',
                    level: '',
                    time: '',
                    pictureList: []
                },
                preview: '',
                disabled: false,
                columns: [
                    {
                        title: '空气污染指数',
                        key: 'exponent'
                    },
                    {
                        title: '空气质量级别',
                        key: 'level'
                    },
                    {
                        title: '空气质量状况',
                        key: 'situation'
                    }
                ],
                data: [
                    {
                        exponent: '0 - 50',
                        level: '一级',
                        situation: '优',
                    },
                    {
                        exponent: '51 - 100',
                        level: '二级',
                        situation: '良',
                    },
                    {
                        exponent: '101 - 150',
                        level: '三级',
                        situation: '轻度污染',
                    },
                    {
                        exponent: '151 - 200',
                        level: '四级',
                        situation: '中度污染',
                    },
                    {
                        exponent: '201 - 300',
                        level: '五级',
                        situation: '重度污染',
                    },
                    {
                        exponent: '大于300',
                        level: '六级',
                        situation: '严重污染',
                    },
                ]
            }
        },
        created () {
            if (this.modeId !== '' && this.modeId !== undefined) {
                this.init()
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
        methods: {
            init () {
                this.$api.post('/member-reversion/envCondition/findAirQuality', {
                    account: this.$user.loginAccount,
                    templateId: this.$template.id,
                    yearId: this.yearId,
                    dictId: this.modeId
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.aqi) {
                            this.form.aqi = response.data.aqi
                        }
                        if (response.data.pm2Con) {
                            this.form.pm25 = response.data.pm2Con
                        }
                        if (response.data.pm10Con) {
                            this.form.pm10 = response.data.pm10Con
                        }
                        if (response.data.qualityLevel) {
                            this.form.level = response.data.qualityLevel
                        }
                        if (response.data.updateTime) {
                            this.form.time = response.data.updateTime
                        }
                        if (response.data.detectReport) {
                            this.form.pictureList = response.data.detectReport
                            this.$refs['air'].handleGive(this.form.pictureList)
                        }
                        if (response.data.status) {
                            this.form.status = response.data.status === 1 ? true : false
                        }
                        if (response.data.propertyName) {
                            this.title = response.data.propertyName
                        }
                        if (response.data.textPreview) {
                            this.preview = response.data.textPreview
                        }
                        
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            handleSave () {
                let data = {
                    templateId: this.$template.id,
                    account: this.$user.loginAccount,
                    propertyName: this.title,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    isComplete: '1',
                    aqi: this.form.aqi,
                    pm2Con: this.form.pm25,
                    pm10Con: this.form.pm10,
                    qualityLevel: this.form.level,
                    updateTime: this.moment(this.form.time).format('YYYY-MM-DD HH:mm'),
                    detectReport: this.form.pictureList,
                    status: this.form.status,
                    textPreview: this.preview
                }
                this.$api.post('/member-reversion/envCondition/modifyAirQuality', data).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.$emit('on-save')
                        this.init()
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            open () {
                this.modal = true
            },
            getList (e) {
                var arr = []
                e.forEach(element => {
                     if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.form.pictureList = arr
            }
        },
        mounted () {
            this.preview = `空气质量指数(AQI)为（），PM2.5浓度为（）微克/立方米(μg/m3)，PM10 浓度为（）微克/立方米(μg/m3)，达到空气质量（），空气质量状况为（）。`
        }
    }
</script>
<style lang="scss" scoped>
</style>