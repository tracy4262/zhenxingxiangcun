<template>
  <div class="pd20">
    <Title :title="title" edit :id="id" :yearId="yearId"></Title>
    <div  class="mt40 pd20" style="background:#f9f9f9;">
        <Form v-for="(item,index) in data" :key="index" :ref="`formItem${index}`" :model="item" label-position="left" :label-width="100" :rules="formItemInline">
            <Row>
                <Col span="8">
                    <Form-item label="权限">
                        <i-switch  v-model="item.status" size="large" :disabled="!item.isAdd">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
                <!-- <Col span="8" offset="8" class="tr">
                    <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.isAdd">编辑</span>
                </Col> -->
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="地块编码">
                        <Input v-model="item.landCode" readonly :disabled="!item.isAdd" :maxlength="20"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="实测面积">
                        <Input v-model="item.factArea" :disabled="!item.isAdd" :maxlength="20"><span slot="append">平方米</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="检测时间">
                        <DatePicker type="date" v-model="item.checkTime" :disabled="!item.isAdd" placeholder="请选择检测时间" readonly></DatePicker>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="有效磷含量" prop="phosphor">
                        <Input v-model="item.phosphor" :maxlength="20" :disabled="!item.isAdd" @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="有效钾含量" prop="kalium">
                        <Input v-model="item.kalium":maxlength="20" :disabled="!item.isAdd" @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="有机质含量" prop="organic">
                        <Input v-model="item.organic":maxlength="20" :disabled="!item.isAdd" @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="PH值" prop="ph">
                        <Input v-model="item.ph" :maxlength="20" :disabled="!item.isAdd" @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item label="上传图片">
                        <vui-upload
                            :disabled="!item.isAdd"
                            :ref="`picture${index}`"
                            @on-getPictureList="getListgetList($event, index)"
                            :hint="'图片大小小于2MB，支持后缀名png jpg'"
                            :total="10"
                            :size="[80,80]"
                        ></vui-upload>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item label="地块氮磷钾含量描述">
                        <Input v-model="item.depict" type="textarea" :disabled="!item.isAdd" :maxlength="200"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
            </Row>
            <div class="pd20 tc"  v-if="item.isAdd">
            <Button type="primary" @click="handleSave(item, index)" >保存</Button>
            </div>
        </Form> 
    </div>
    <!-- <div class="pd20 tc pt30">
        <Button type="primary" @click="onSave()" class="mt40">保存</Button>
    </div> -->
    
  </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    import Title from '../../components/title'
    import {isNumber, isMoney3} from '~utils/validate'
    export default {
        components: {
            Title,
            vuiUpload
        },
        props: {
            id: {
                type: String
            },
            yearId: {
                type: String
            }
        },
        data () {
            return {
                title: '',
                formItemInline: {
                    phosphor:[{validator:isMoney3,trigger: 'blur'}],
                    kalium:[{validator:isMoney3,trigger: 'blur'}],
                    organic:[{validator:isMoney3,trigger: 'blur'}],
                    ph:[{validator:isMoney3,trigger: 'blur'}],
                },
                data: [{
                    status: true,
                    isAdd: false,
                    landCode: '', // 地块编码
                    factArea: '', // 实测面积
                    checkTime: '', // 检测时间
                    phosphor: '', // 有效磷含量
                    kalium: '', // 有效钾含量
                    organic: '', // 有机质含量
                    ph: '', // PH值
                    pictureList: [], // 上传图片
                    depict: '' // 地块氮磷钾含量描述
                }],
                index: 0
            }
        },
        created () {
        },
        methods: {
            // 初始化加载数据
            init () {
                this.$api.post('/member-reversion/landInfo/findSoilContent',{
                    account: this.$user.loginAccount,
                    templateId: this.$template.id,
                    yearId: this.yearId,
                    dictId: this.id
                }).then(response => {
                    if (response.code == 200) {
                        this.preview = response.data.preview
                        // this.data = response.data.list
                        this.title = response.data.propertyName
                        // this.data.forEach(e => {
                        //     e.status = e.status == '1' ? true : false
                        //     e.isAdd = false
                        // })
                        this.$nextTick(e => {
                            this.data.forEach((item, index) => {
                                this.$refs[`picture${index}`][0].handleGive(item.pictureList)
                            })
                        })
                    }
                })
            },
            // 地块氮磷钾含量描述
            depictChange (item, index) {
                let str = ``
                if (item.phosphor) {
                    str += `有效磷含量为${item.phosphor}mg/kg，`
                }
                if (item.kalium) {
                    str += `有效钾含量为${item.kalium}mg/kg，`
                }
                if (item.organic) {
                    str += `有机质含量为${item.organic}mg/kg，`
                }
                if (item.ph) {
                    str += `PH值为${item.ph}，`
                }
                if (item.pictureList.length) {
                    str += `${item.pictureList.join('、')}，`
                }
                if (str) {
                    str = `经检测，本块土壤${str.substring(0, str.length - 1)}。`
                }
                item.depict = ''
                item.depict = str
                
            },
            getListgetList (e, index) {
                var arr = []
                e.forEach(element => {
                     if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data[index].pictureList = arr
                this.depictChange(this.data[index], index)
            },
            handleEdit (item, index) {
                item.isAdd = true
                this.data.splice(index, 1, item)
            },
            handleDel (item, index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确认删除？',
                    onOk:()=>{
                        if (item.id) {
                            this.$api.post('/member-reversion/administrationDivision/deleteManagerialStaff', {id: item.id}).then(response => {
                                if (response.code === 200) {
                                    this.$Message.success('保存成功')
                                    this.data.splice(index, 1)
                                }
                            })
                        } else {
                            this.$Message.success('保存成功')
                            this.data.splice(index, 1)
                        }
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            // 保存预览信息
            save (item, index) {
                
            },
            // 保存单个信息
            handleSave (item, index) {
                this.$refs[`formItem${index}`][0].validate(v => {
                    if (!v) {
                        this.$Message.error('请核对表单信息')
                    } else {
                        item.account = this.$user.loginAccount
                        item.yearId = this.yearId
                        item.dictId = this.id
                        this.$api.post('/member-reversion/landInfo/updateSoilContent', item).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('保存成功')
                                this.$emit('on-save')
                            }
                        })
                    }
                })
            }
        }
    }
</script>
<style lang="scss" scoped>
    
</style>