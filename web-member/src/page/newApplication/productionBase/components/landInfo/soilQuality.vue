<template>
  <div class="pd20">
    <Title :title="title" edit :id="id" ></Title>
    <div v-for="(item,index) in data" :key="index" class="mt40 pd20" style="background:#f9f9f9;">
        <Form :ref="`formItem${index}`" :model="item" label-position="left" :label-width="100" :rules="formItemInline">
            <Row>
                <Col span="8" offset="16" class="tr mb10">
                    <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.isAdd">编辑</span>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="地块编码">
                        <Input v-model="item.landCode" readonly :disabled="!item.isAdd" :maxlength="20"  @on-change="depictChange(item, index)"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="实测面积">
                        <Input v-model="item.factArea" :disabled="!item.isAdd" :maxlength="20"><span slot="append">平方米</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="检测时间">
                        <DatePicker type="date" :disabled="!item.isAdd" v-model="item.checkTime" placeholder="请选择检测时间"></DatePicker>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="pH值≤" prop="ph">
                        <Input v-model="item.ph" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="镉" prop="cadmium">
                        <Input v-model="item.cadmium" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="汞" prop="mercury">
                        <Input v-model="item.mercury" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="砷" prop="arsenic">
                        <Input v-model="item.arsenic" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="铅" prop="lead">
                        <Input v-model="item.lead" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="铬" prop="chromium">
                        <Input v-model="item.chromium" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="铜" prop="copper">
                        <Input v-model="item.copper" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="镍" prop="nickel">
                        <Input v-model="item.nickel" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="锌" prop="zinc">
                        <Input v-model="item.zinc" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="六六六总量" prop="six">
                        <Input v-model="item.six" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="滴滴涕总量" prop="cried">
                        <Input v-model="item.cried" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="苯并[a]芘" prop="benzene">
                        <Input v-model="item.benzene" :maxlength="20" :disabled="!item.isAdd"  @on-change="depictChange(item, index)"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
                
                <Col span="24">
                    <Form-item label="土壤检测报告">
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
                    <Form-item label="地块土壤质量描述">
                        <Input v-model="item.depict" type="textarea" :disabled="!item.isAdd" :maxlength="200"><span slot="append">mg/kg</span></Input>
                    </Form-item>
                </Col>
            </Row>
            <div class="pd20 tc"  v-if="item.isAdd">
                <Button type="primary" :loading="loading" @click="handleSave(item, index)" >保存</Button>
            </div>
        </Form>
    </div>
  </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    import Title from '../../components/title'
    import vuiCascander from '~components/vuiCascader/index'
    import {isNumber, isMoney3} from '~utils/validate'
    export default {
        components: {
            Title,
            vuiUpload,
            vuiCascander
        },
        props: {
            id: {
                type: String
            }
        },
        data () {
            return {
                title: '地块土壤质量信息',
                formItemInline: {
                    ph:[{validator:isMoney3,trigger: 'blur'}],
                    cadmium:[{validator:isMoney3,trigger: 'blur'}],
                    mercury:[{validator:isMoney3,trigger: 'blur'}],
                    arsenic:[{validator:isMoney3,trigger: 'blur'}],
                    lead:[{validator:isMoney3,trigger: 'blur'}],
                    chromium:[{validator:isMoney3,trigger: 'blur'}],
                    copper:[{validator:isMoney3,trigger: 'blur'}],
                    nickel:[{validator:isMoney3,trigger: 'blur'}],
                    zinc:[{validator:isMoney3,trigger: 'blur'}],
                    six:[{validator:isMoney3,trigger: 'blur'}],
                    cried:[{validator:isMoney3,trigger: 'blur'}],
                    benzene:[{validator:isMoney3,trigger: 'blur'}],
                },
                data: [{
                    status: true,
                    isAdd: true,
                    landCode: '', // 地块编码
                    factArea: '', // 实测面积
                    checkTime: '', // 检测时间
                    ph: '', // pH值≤
                    cadmium: '', // 镉
                    mercury: '', // 汞
                    arsenic: '', // 砷
                    lead: '', // 铅
                    chromium: '', // 铬
                    copper: '', // 铜
                    nickel: '', // 镍
                    zinc: '', // 锌
                    six: '', // 六六六总量
                    cried: '', // 滴滴涕总量
                    benzene: '', // 苯并[a]芘
                    pictureList: [], // 土壤检测报告
                    depict: '' // 地块土壤质量描述
                }],
                index: 0,
                baseId: '',
                loading: true
            }
        },
        created () {
            this.baseId = this.$route.query.id
        },
        methods: {
            initTitle () {
                this.$api.post('/member-reversion/productionBase/findTableHead', {
                    account: this.$user.loginAccount,
                    dictId: this.id
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
                this.$api.post('/member-reversion/productionBase/landInfo/findSoilQuality',{
                    account: this.$user.loginAccount,
                    dictId: this.id,
                    baseId: this.baseId
                }).then(response => {
                    if (response.code == 200) {
                        this.preview = response.data.preview
                        this.data = response.data.list
                        this.data.forEach(e => {
                            e.status = e.status == '1' ? true : false
                            e.isAdd = false
                        })
                        this.$nextTick(e => {
                            this.data.forEach((item, index) => {
                                this.$refs[`picture${index}`][0].handleGive(item.pictureList)
                            })
                        })
                        this.loading = false
                    }
                })
            },
            // 地块土壤质量描述
            depictChange (item, index) {
                let str = ``
                if (item.ph) {
                    str += `pH值≤${item.ph}，`
                }
                if (item.cadmium) {
                    str += `镉为${item.cadmium}mg/kg，`
                }
                if (item.mercury) {
                    str += `汞为${item.mercury}mg/kg，`
                }
                if (item.arsenic) {
                    str += `砷为${item.arsenic}mg/kg，`
                }
                if (item.lead) {
                    str += `铅为${item.lead}mg/kg，`
                }
                if (item.chromium) {
                    str += `铬为${item.chromium}mg/kg，`
                }
                if (item.copper) {
                    str += `铜为${item.copper}mg/kg，`
                }
                if (item.nickel) {
                    str += `镍为${item.nickel}mg/kg，`
                }
                if (item.zinc) {
                    str += `锌为${item.zinc}mg/kg，`
                }
                if (item.six) {
                    str += `六六六总量为${item.six}mg/kg，`
                }
                if (item.cried) {
                    str += `滴滴涕总量为${item.cried}mg/kg，`
                }
                if (item.benzene) {
                    str += `苯并[a]芘为${item.benzene}mg/kg，`
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
            },
            handleEdit (item, index) {
                item.isAdd = true
                this.data.splice(index, 1, item)
            },
            // 保存单个信息
            handleSave (item, index) {
                this.$refs[`formItem${index}`][0].validate(v => {
                    if (!v) {
                        this.$Message.error('请核对表单信息')
                    } else {
                        item.account = this.$user.loginAccount
                        item.dictId = this.id
                        item.baseId = this.baseId
                        this.loading = true
                        this.$api.post('/member-reversion/productionBase/landInfo/updateSoilQuality', item).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('保存成功')
                                this.init()
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