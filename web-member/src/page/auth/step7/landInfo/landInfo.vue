<template>
  <div class="pd20">
    <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh" />
        <Button type="primary" size="small" v-if="!showMap" @click="handleUpdate">更新数据</Button>
    </Title>
    <baidu-map class="mb20" v-if="showMap" ref="map">
        <Button type="primary" @click="handleSearchLand">查找我的地块</Button>
    </baidu-map>
    <div v-for="(item,index) in data" :key="index" class="mt40 pd20" v-else style="background: #f9f9f9;">
        <Form :ref="`data${index}`" :model="item" label-position="left" :label-width="100" :rules="formItemInline">
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
                        <Input v-model="item.landCode" readonly :disabled="!item.isAdd" :maxlength="20"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="地块名称">
                        <Input v-model="item.landName" readonly :disabled="!item.isAdd" :maxlength="20"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="权利人">
                        <Input v-model="item.landUser" readonly @on-change="change" :disabled="!item.isAdd" :maxlength="20"/>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="土地用途">
                        <Input v-model="item.landAffect" @on-change="change" :disabled="!item.isAdd" :maxlength="20"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="地块类型">
                        <Input v-model="item.landType" @on-change="change" :disabled="!item.isAdd" :maxlength="20"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="实测面积">
                        <Input v-model="item.factArea" @on-change="change" :disabled="!item.isAdd" :maxlength="20"> <span slot="append">平方米</span></Input>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="航测面积">
                        <Input v-model="item.airArea" @on-change="change" :disabled="!item.isAdd" :maxlength="20"><span slot="append">平方米</span></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="地力等级">
                        <Input v-model="item.landLevel" @on-change="change" :disabled="!item.isAdd" :maxlength="20"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="基本农田">
                        <Select v-model="item.farmland" @on-change="change" :disabled="!item.isAdd">
                            <Option value="0">否</Option>
                            <Option value="1">是</Option>
                        </Select>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="使用权性质">
                        <Select v-model="item.tenure" @on-change="change" :disabled="!item.isAdd">
                            <Option value="0">国有土地使用权</Option>
                            <Option value="1">集体土地使用权</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="利用类型">
                        <Input v-model="item.useType" @on-change="change" :disabled="!item.isAdd" :maxlength="20"/>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                 <Col span="8">
                    <Form-item label="东经">
                        <Input v-model="item.longitude" readonly @on-change="change" :disabled="!item.isAdd" :maxlength="20"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="北纬">
                        <Input v-model="item.latitude" readonly @on-change="change" :disabled="!item.isAdd" :maxlength="20"/>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Button type="text" ghost @click="handleShowMap(item, index)" class="t-grey">查看地图</Button>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="16" style="z-index: 1;">
                    <Form-item label="所处位置">
                         <Input v-model="item.location" readonly @on-change="change" :disabled="!item.isAdd" :maxlength="20"/>
                        <!-- <vui-cascander :values="item.location" :disabled="!item.isAdd" @handle-get-result="handleGetData" @click.native="getIndex(index)"> -->
                        </vui-cascander>
                    </Form-item>
                </Col>
                <!-- <Col span="8">
                    <Form-item style="margin-left: -100px;">
                        <Input v-model="item.group" :maxlength="5" style="width: 60px; margin-right: 10px;" @on-change="change" :disabled="!item.isAdd"  :maxlength="20"/>组
                        <Input v-model="item.number" :maxlength="5" style="width: 60px; margin-right: 10px; margin-left: 10px;" @on-change="change" :disabled="!item.isAdd"  :maxlength="20"/>号
                    </Form-item>
                </Col> -->
            </Row>
            <div class="pd20 tc" v-if="item.isAdd">
                <Button type="primary" @click="handleSave(item, index)">保存</Button>
            </div>
        </Form>
    </div>
    <div class="pd20 pt40 pb40" v-if="!showMap">
        <Button type="primary" class="btn-light-primary" icon="md-add" ghost @click="handleSearchLand">新增</Button>
    </div>
    <Title title="文字预览" class="mt40"  v-if="!showMap"/>
    <div class="pd20 tc"  v-if="!showMap">
        <Input v-model="preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        <Button type="primary" @click="onSave" class="mt40">保存</Button>
    </div>
    
  </div>
</template>
<script>
    import Title from '../../components/title'
    import baiduMap from './components/map'
    import vuiCascander from '~components/vuiCascader/index'
    import {numAdd, numMulti} from '~utils/utils'
    export default {
        components: {
            Title,
            baiduMap,
            vuiCascander
        },
        props: {
            id: {
                type: String
            },
            appId: {
                type: String
            },
            yearId: {
                type: String
            },
        },
        data () {
            return {
                showMap: true,
                title: '地块信息',
                formItemInline: {},
                data: [{
                    status: true,
                    landCode: '', // 地块编码
                    landName: '', // 地块名称
                    landUser: '', // 权利人
                    landAffect: '', // 土地用途
                    landType: '', // 地块类型
                    factArea: '', // 实测面积
                    airArea: '', // 航测面积
                    landLevel: '', // 地力等级
                    farmland: '', // 基本农田
                    tenure: '', // 使用权性质
                    useType: '', // 利用类型
                    location: '', // 所处位置
                    longitude: '', // 东经
                    latitude: '', // 北纬
                    isAdd: true
                }],
                preview: '',
                index: 0,
                templateId: ''
            }
        },
        created () {
            this.templateId = this.$route.query.templateId
        },
        // 公式 1 平方米 = 0.0015亩 1平方米(㎡)=1e-6平方千米(km²)
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
            // 初始化 查询地图数据
            init (flag) {
                // 说明是从地块地图查询 跳转过来的/点击tab过来的，直接显示地块信息
                if (!flag) {
                    this.showMap = false
                    this.handleInit()
                } else {
                    // 标注点显示 markerDatas为点标识的 可多个
                    let markerDatas = []
                    // center 地图显示的中心区域
                    let center = {}
                    // 框选区域
                    let location = ''
                    console.log(this.$refs['map'])
                    this.$refs['map'].init(center, location, markerDatas, true)
                    this.handleInit()
                }
            },
            // 加载列表数据
            handleInit (type) {
                this.$api.post('/member-reversion/landInfo/findLandInfo',{
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.id,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code == 200) {
                        if (!type) {
                            this.preview = response.data.preview
                        }
                        this.data = response.data.list
                        // this.title = response.data.propertyName
                        this.data.forEach(e => {
                            e.status = e.status == '1' ? true : false
                            e.isAdd = false
                            // if (!e.longitude) {
                                // 计算经纬度
                                let x = (e.x1 * 1000000000000000 + e.x2 * 1000000000000000) / 2000000000000000
                                let y = (e.y1 * 1000000000000000 + e.y2 * 1000000000000000) / 2000000000000000
                                e.longitude = x
                                e.latitude = y
                            // }
                        })
                    }
                })
            },
            // 点击更新数据
            handleUpdate () {
                this.$api.post('/member-reversion/landInfo/updateNkyLand',{
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.id,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code == 200) {
                        this.handleInit()
                    }
                })
            },
            // 点击查看地图
            handleShowMap (item, index) {
                this.$emit('on-map', item)
            },
            // 删除
            handleDel (item, index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确认删除？',
                    onOk:()=>{
                        if (item.id) {
                            let list = {
                                id: item.id,
                                landId: item.landId,
                                yearId: this.yearId,
                                account: this.$user.loginAccount,
                            }
                            this.$api.post('/member-reversion/landInfo/deleteLandInfo', list).then(response => {
                                if (response.code === 200) {
                                    this.$Message.success('删除成功')
                                    this.data.splice(index, 1)
                                }
                            })
                            // 删除 地图数据
                            this.$delMapData(item.serviceName, item,landId)
                        } else {
                            this.$Message.success('删除成功')
                            this.data.splice(index, 1)
                        }
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            // 单个保存
            handleSave (item, index) {
                item.account = this.$user.loginAccount
                item.yearId = this.yearId
                item.dictId = this.id
                item.templateId = this.templateId
                this.$api.post('/member-reversion/landInfo/updateLandInfo', item).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功')
                        this.$emit('on-save')
                        this.handleInit(1)
                    }
                })
                /* this.$refs[`data${index}`][0].validate(v => {
                    console.log('yanzheng')
                    if (!v) {
                        this.$Message.error('请核对表单信息')
                    } else {
                        
                    }
                }) */
            },
            // 保存文字预览
            onSave () {
                let list = {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.id,
                    textPreview: this.preview,
                    isComplete: this.data.length ? true : false,
                    templateId: this.templateId
                }
                this.$api.post('/member-reversion/perfect/saveTextPreview', list).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功')
                        this.handleInit()
                        this.$emit('on-save')
                    }
                })
            },
            // 拼接文字预览
            change () {
                let str = ''
                let factArea = 0
                let mu = 0
                let km = 0
                if (this.data.length) {
                    str = `地块总数：${this.data.length}，`
                    this.data.forEach(e => {
                        if (e.factArea) {
                            factArea = numAdd(parseFloat(factArea ? factArea : 0).toFixed(2), parseFloat(e.factArea ? e.factArea : 0).toFixed(2))
                        }
                    })
                    if (factArea) {
                        str += `实测总面积${factArea}平方米，折算${numMulti(factArea, 0.00015)}亩，${numMulti(factArea, 0.000001)}平方千米，`
                    }
                }
                if (str) {
                    str = `${str.substring(0, str.length - 1)}。`
                }
                this.preview = str
            },
            handleEdit (item, index) {
                item.isAdd = true
                this.data.splice(index, 1, item)
            },
            // 查找地块
            handleSearchLand () {
                this.$toMap()
                // window.open(`http://ditu.ns51.cn:8202/map/map.html?dir=caowuzhen&account=${this.$user.loginAccount}&yearId=${this.yearId}`, '_banlk')
                this.showMap = false
                // this.handleInit()
            },
            leftRefresh () {
                this.$emit('left-refresh')
            }
        }
    }
</script>
<style lang="scss" scoped>
    
</style>