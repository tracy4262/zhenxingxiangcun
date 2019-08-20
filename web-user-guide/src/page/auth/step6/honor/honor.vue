<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" />
    <Card v-for="(item,index) in data" :key="index" class="mt40">
        <Form :ref="`honorFormItem${index}`" :model="item" label-position="left" :label-width="100" :rules="honorFormItemInline">
            <Row>
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="item.status" size="large" :disabled="!item.isAdd">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
                <!-- <Col span="12" class="tr">
                    <div class="btn-toolbar" style="margin-top: -10px;">
                        <Button type="text" @click="edit(index)" v-if="!item.isAdd"><Icon type="trash-a" size="16" class="pr5"></Icon> 编辑</Button> 
                        <Button type="text" @click="del(item)"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col> -->
            </Row>
            <Row>
                <Col span="8">
                    <Form-item label="名称">
                        <Input v-model="item.name" :maxlength="50" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
                <Col span="8" class="ml40">
                    <Form-item label="获得时间">
                        <DatePicker v-model="item.time" type="date" style="width: 200px" :options="options" :disabled="!item.isAdd" @on-change="change"></DatePicker>
                    </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="8">
                    <Form-item label="颁发部门">
                        <Input v-model="item.depart" :maxlength="50" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
            </Row>
            <Form-item label="图片">
                <vui-upload
                    :disabled="!item.isAdd"
                    :ref="`honor${index}`"
                    @on-getPictureList="getHonorList($event, index)"
                    :hint="'图片大小小于2MB，支持后缀名png jpg'"
                    :total="10"
                    :size="[80,80]"
                ></vui-upload>
            </Form-item>
            <Form-item label="说明">
                <Input v-model="item.content" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="500" :disabled="!item.isAdd" @on-change="change" />
            </Form-item>
        </Form>
        <!-- <div class="tc">
            <Button type="primary" v-if="item.isAdd" @click="save(item, index)">保存</Button> -->
        <!-- </div> -->
    </Card>
    <!-- <Form label-position="left" :label-width="150" class="pb20 mt40">
        <Row :gutter="32">
            <Col span="12">
                <Button type="success" ghost @click="handleAddHonor" icon="md-add" class="btn-light-primary">添加</Button>
            </Col>
        </Row>
    </Form> -->
    <Title title="文字预览" class="mt50"/>
    <div class="pd20 tc pt30">
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
                title: '荣誉称号信息',
                honorFormItemInline: {},
                data: [{
                    status: true,
                    name: '',
                    time: '',
                    depart: '',
                    content: '',
                    honorPictureList: [],
                    isAdd: false
                }],
                options: {
                    disabledDate (date) {
                        return date && date.valueOf() > Date.now()
                    }
                },
                preview: '',
                index: 0,
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
            init (type = 0) {
                this.$api.post('/member-reversion/honoraryTitle/findHonoraryTitle', {
                    user_id: this.$user.loginAccount,
                    templateId: this.$template.id,
                    year_id: this.yearId,
                    parent_id: this.modeId
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.honoraryTitle_name && response.data.honoraryTitle_name !== '') {
                            this.title = response.data.honoraryTitle_name
                        }
                        if (response.data.textPreview.text_preview && response.data.textPreview.text_preview !== '') {
                            this.preview = response.data.textPreview.text_preview
                            this.id = response.data.textPreview.id
                        }
                        if (response.data.honoraryTitle.length !== 0) {
                            this.data = []
                            response.data.honoraryTitle.forEach((element, index) => {
                                this.data.push({
                                    id: element.id,
                                    name: element.honorary_name,
                                    time: element.history_time,
                                    depart: element.issuing_department,
                                    content: element.explain,
                                    honorPictureList: element.image.split(';'),
                                    status: element.status,
                                    isAdd: false
                                })
                                this.$nextTick(() => {
                                    this.$refs['honor' + index][0].handleGive(element.image.split(';'))
                                })
                            })
                        }
                        if (type === 1) {
                            this.change()
                        }
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            //增加
            handleAddHonor () {
                this.data.push({
                    status: true,
                    name: '',
                    time: '',
                    depart: '',
                    content: '',
                    isAdd: true
                })
                this.index = this.data.length - 1
            },
            getHonorList (e, index) {
                var arr = []
                e.forEach(element => {
                     if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data[index].honorPictureList = arr
                this.change()
            },
            edit (index) {
                this.data[index].isAdd = true
                this.index = index
            },
            del (item) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确认删除？',
                    onOk:()=>{
                        if (item.id !== undefined && item.id !== '') {
                            this.$api.post('/member-reversion/honoraryTitle/deleteHonoraryTitle', {
                                id: item.id
                            }).then(response => {
                                if (response.code === 200) {
                                    this.$Message.success('删除成功！')
                                    this.data = []
                                    this.init(1)
                                }
                            }).catch(error => {
                                this.$Message.error('服务器异常！')
                            })
                        } else {
                            this.data.splice(index, 1)
                            this.change()
                        }
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            save (item, index) {
                this.$api.post('/member-reversion/honoraryTitle/saveHonoraryTitle', {
                    user_id: this.$user.loginAccount,
                    yearId: this.yearId,
                    parent_id: this.appId,
                    honorary_title_name: this.title,
                    honoraryTitle: {
                        id: item.id === '' || item.id === undefined ? 0 : item.id,
                        honorary_name: item.name,
                        honorary_time: this.moment(item.time).format('YYYY-MM-DD'),
                        issuing_department: item.depart,
                        image: item.honorPictureList,
                        explain: item.content,
                        status: item.status
                    }
                }).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init(1)
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            handleSave () {
                this.$api.post('/member-reversion/honoraryTitle/saveTextPreview', {
                    user_id: this.$user.loginAccount,
                    yearId: this.yearId,
                    sys_dict_id: this.modeId,
                    templateId: this.$template.id,
                    textPreview: {
                        id: this.id === '' || this.id === undefined ? 0 : this.id,
                        text_preview: this.preview,
                        is_complete: this.data.length === 0 ? false : true
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
            },
            change () {
                this.preview = ''
                this.preview += `共获得 ${this.data.length} 项荣誉称号，其中：`
                this.data.forEach(element => {
                    if (element.name && element.name !== '') {
                        this.preview += `${element.name}，`
                    }
                    if (element.time && element.time !== '') {
                        this.preview += `获得时间：${this.moment(element.time).format('YYYY-MM-DD')}，`
                    }
                    if (element.depart && element.depart !== '') {
                        this.preview += `颁发部门：${element.depart}，`
                    }
                    if (element.content && element.content !== '') {
                        this.preview += `说明：${element.content}，`
                    }
                    if (element.honorPictureList && element.honorPictureList.length > 0) {
                        this.preview += `图片链接：${element.honorPictureList}，`
                    }
                    this.preview = this.preview.substring(0, this.preview.length - 1) + '；'
                })
                this.preview = this.preview.substring(0, this.preview.length - 1) + '。'
            }
        },
        mounted () {
            this.preview = `名称（），获得时间（），颁发部门（），说明（）。`
        }
    }
</script>
<style lang="scss" scoped>
    .honor-picture-list {
        width: 10%;
        height: 100px;
        float:left;
        margin-right: 10px;
        &:last-child{
            margin-right: 0;
        }
    }
</style>