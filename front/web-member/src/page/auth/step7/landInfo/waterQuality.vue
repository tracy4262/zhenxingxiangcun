<template>
  <div class="pd20">
    <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh"></Title>
    <div v-for="(item,index) in data" :key="index" class="mt40 pd20" style="background:#f9f9f9;">
        <Form :ref="`data${index}`" label-position="left" :label-width="100"  :model="item" :rules="formItemInline">
            <Row>
                <Col span="8">
                    <Form-item label="权限">
                        <i-switch  v-model="item.status" size="large" :disabled="!item.isAdd">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
                <Col span="8" offset="8" class="tr">
                    <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.isAdd">编辑</span>
                    <span class="auth-btn-toolbar" @click="handleDel(item, index)" v-if="data.length != 1">删除</span>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="地块编码">
                        <Input v-model="item.landCode" readonly :maxlength="20"  :disabled="!item.isAdd" @on-change="depictChange(item, index)"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="实测面积">
                        <Input v-model="item.factArea" :maxlength="20"  :disabled="!item.isAdd"><span slot="append">平方米</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="检测时间">
                        <DatePicker type="date"  :disabled="!item.isAdd" v-model="item.checkTime" placeholder="请选择检测时间"></DatePicker>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="重铬酸盐指数（CODcr）" prop="chromium">
                        <Input v-model="item.chromium" :maxlength="20"  :disabled="!item.isAdd" @on-change="depictChange(item, index)">
                            <span slot="append">mg/kg</span>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="高锰酸盐指数(CODmn）"  prop="manganese">
                        <Input v-model="item.manganese" :maxlength="20"  :disabled="!item.isAdd" @on-change="depictChange(item, index)">
                            <span slot="append">mg/kg</span>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="生化需氧量（BOD5）"  prop="oxygen">
                        <Input v-model="item.oxygen" :maxlength="20"  :disabled="!item.isAdd" @on-change="depictChange(item, index)">
                            <span slot="append">mg/kg</span>
                        </Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="氨氮" prop="ammonia">
                        <Input v-model="item.ammonia" :maxlength="20"  :disabled="!item.isAdd" @on-change="depictChange(item, index)">
                            <span slot="append">mg/kg</span>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="总氮" prop="nitrogen">
                        <Input v-model="item.nitrogen" :maxlength="20"  :disabled="!item.isAdd" @on-change="depictChange(item, index)">
                            <span slot="append">mg/kg</span>
                        </Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="总磷" prop="phosphorus">
                        <Input v-model="item.phosphorus" :maxlength="20"  :disabled="!item.isAdd"  @on-change="depictChange(item, index)">
                            <span slot="append">mg/kg</span>
                        </Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="营养级别">
                        <Select v-model="item.nutrition"  :disabled="!item.isAdd">
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
                    <Form-item label="水质现状">
                        <Input v-model="item.waterQuality" :maxlength="20"  :disabled="!item.isAdd"></Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="24">
                    <Form-item label="地块水质检测报告">
                        <vui-upload
                            :disabled="!item.isAdd"
                            :ref="`picture${index}`"
                            @on-getPictureList="getListgetList($event, index)"
                            :hint="'图片大小小于2MB，支持后缀名png jpg'"
                            :total="9999999"
                            :size="[80,80]"
                        ></vui-upload>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item label="地块水质质量描述">
                        <Input v-model="item.depict" type="textarea" :maxlength="200"  :disabled="!item.isAdd"></Input>
                    </Form-item>
                </Col>
            </Row>
            <div class="pd20 tc"  v-if="item.isAdd">
                <Button type="primary" @click="handleSave(item, index)" >保存</Button>
            </div>
        </Form>
    </div>
  </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    import Title from '../../components/title'
    import {isNumber, isMoney3} from '~utils/validate'
    export default {
        components: {
            Title,
            vuiUpload,
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
                title: '地块水质信息',
                formItemInline: {
                    chromium:[{validator:isMoney3,trigger: 'blur'}],
                    manganese:[{validator:isMoney3,trigger: 'blur'}],
                    oxygen:[{validator:isMoney3,trigger: 'blur'}],
                    ammonia:[{validator:isMoney3,trigger: 'blur'}],
                    nitrogen:[{validator:isMoney3,trigger: 'blur'}],
                    phosphorus:[{validator:isMoney3,trigger: 'blur'}],
                },
                data: [{
                    status: true,
                    isAdd: true,
                    landCode: '', // 地块编码
                    factArea: '', // 实测面积
                    checkTime: '', // 检测时间
                    chromium: '', // 重铬酸盐指数（CODcr）
                    manganese: '', // 高锰酸盐指数(CODmn）
                    oxygen: '', // 生化需氧量（BOD5）
                    ammonia: '', // 氨氮
                    nitrogen: '', // 总氮
                    nutrition: '', // 营养级别
                    waterQuality: '', // 水质现状
                    pictureList: [], // 地块水质检测报告
                    depict: '' // 地块水质质量描述
                }],
                index: 0,
                templateId: ''
            }
        },
        created () {
            this.templateId = this.$route.query.templateId
        },
        methods: {
            initTitle () {
                this.$api.post('/member-reversion/user/perfect/findTableHead', {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.id,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.propertyName) {
                            this.title = response.data.propertyName
                        }
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 初始化加载数据
            init () {
                this.$api.post('/member-reversion/landInfo/findWaterQuality',{
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.id,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code == 200) {
                        this.preview = response.data.preview
                        this.data = response.data.list
                        // this.title = response.data.propertyName
                        this.data.forEach(e => {
                            e.status = e.status == '1' ? true : false
                            e.isAdd = false
                        })
                        this.$nextTick(e => {
                            this.data.forEach((item, index) => {
                                this.$refs[`picture${index}`][0].handleGive(item.pictureList)
                            })
                        })
                    }
                })
            },
            // 地块水质质量描述
            depictChange (item, index) {
                let str = ``
                if (item.chromium) {
                    str += `重铬酸盐指数（CODcr）为${item.chromium}mg/kg，`
                }
                if (item.manganese) {
                    str += `高锰酸盐指数(CODmn）为${item.manganese}mg/kg，`
                }
                if (item.oxygen) {
                    str += `生化需氧量（BOD5）为${item.oxygen}mg/kg，`
                }
                if (item.ammonia) {
                    str += `氨氮为${item.ammonia}mg/kg，`
                }
                if (item.nitrogen) {
                    str += `总氮为${item.nitrogen}mg/kg，`
                }
                if (item.phosphorus) {
                    str += `总磷为${item.phosphorus}mg/kg，`
                }
                if (item.pictureList.length) {
                    str += `${item.pictureList.join('、')}，`
                }
                if (str) {
                    str = `地块${item.landCode}的${str.substring(0, str.length - 1)}。`
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
            },handleEdit (item, index) {
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
            // 保存单个信息
            handleSave (item, index) {
                this.$refs[`data${index}`][0].validate(v => {
                    if (!v) {
                        this.$Message.error('请核对表单信息')
                    } else {
                        item.account = this.$user.loginAccount
                        item.yearId = this.yearId
                        item.dictId = this.id
                        item.templateId = this.templateId
                        this.$api.post('/member-reversion/landInfo/updateWaterQuality', item).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('保存成功')
                                this.init()
                                this.$emit('on-save')
                            }
                        })
                    }
                })
            },
            leftRefresh () {
                this.$emit('left-refresh')
            }
        }
    }
</script>
<style lang="scss" scoped>
    
</style>