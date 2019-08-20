<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh" />
    <Card v-for="(item,index) in data" :key="index" class="mt40">
        <Form :ref="`formItem${index}`" :model="item" label-position="left" :label-width="100" :rules="formItemInline">
            <Row>
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="item.status" size="large" :disabled="!item.isAdd">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
                <Col span="12" class="tr">
                    <div class="btn-toolbar" style="margin-top: -10px;">
                        <Button type="text" @click="edit(index)" v-if="!item.isAdd"><Icon type="trash-a" size="16" class="pr5"></Icon> 编辑</Button>
                        <Button type="text" @click="del(item, index)" v-if="item.isAdd && data.length > 1"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button>
                    </div>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="通用商品名">
                        <Select
                            :disabled="!item.isAdd"
                            @on-change="change"
                            placeholder="支持下拉模糊输入搜索"
                            v-model="item.name"
                            filterable
                            remote
                            :remote-method="remoteMethod"
                            :loading="loading" class="mr60" style="width:100%;">
                            <Option v-for="(option, index) in commonProductNameList" :value="option.label" :key="index">{{option.label}}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="产品名称">
                        <Input v-model="item.productName" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="产量单位">
                       <vuiUnit :value="item.unit" @on-get-data="onGetUnit($event, index)" :disabled="!item.isAdd"></vuiUnit>
                        <!-- <Select v-model="item.unit" filterable style="width: 100%" class="units" placeholder="单位" :disabled="!item.isAdd">
                            <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                        </Select> -->
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="产品数量">
                        <Input :min="1" v-model="item.total" @on-change="handleTotalAmount('total',index);change()" style="width:100%;" :maxlength="10" :disabled="!item.isAdd" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="产品单价（元）">
                        <Input v-model="item.price" @on-change="handleTotalAmount('price',index);change()" style="width:100%;" :maxlength="10" :disabled="!item.isAdd" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="金额（元）">
                        <Input v-model="item.totalAmount" @on-change="change" readonly :disabled="!item.isAdd" />
                    </Form-item>
                </Col>
            </Row>
        </Form>
        <div class="tc">
            <Button type="primary" v-if="item.isAdd" @click="save(item, index)">保存</Button>
        </div>
    </Card>
    <Form label-position="left" :label-width="150" class="pb20 mt40">
        <Row :gutter="32">
            <Col span="12">
                <Button type="success" ghost @click="handleAdd" icon="md-add" class="btn-light-primary">添加</Button>
            </Col>
        </Row>
    </Form>
    <Title title="文字预览"/>
    <div class="pd20 tc pt30">
        <Input v-model="preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        <Button type="primary" v-if="isLoading" class="mt40">保存</Button>
        <Button type="primary" v-else @click="handleSave()" class="mt40">保存</Button>
    </div>
  </div>
</template>
<script>
    import Title from '../../components/title'
    import vuiCascander from '~components/vuiCascader/index'
    import vuiUnit from '~components/vui-unit'
    export default {
        components: {
            Title,
            vuiCascander,
            vuiUnit
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
                title: '求购信息',
                formItemInline: {},
                data: [{
                    name: '',
                    productName: '',
                    total: '',
                    price: '',
                    totalAmount: '',
                    unit: '',
                    status: true,
                    isAdd: true
                }],
                options3:{
                    disabledDate (date) {
                        return date && date.valueOf() > Date.now();
                    }
                },
                preview: '',
                index: 0,
                commonProductNameList: [],
                loading: false,
                templateId: '',
                isLoading: true
            }
        },
        created () {
            this.templateId = this.$route.query.templateId
            if (this.modeId !== '' && this.modeId !== undefined) {
                this.init()
                this.initTitle()
            }
        },
        watch: {
            modeId: {
                handler (newValue, oldValue) {
                    this.init()
                    this.initTitle()
                },
                deep: true
            }
        },
        methods: {
            initTitle () {
                this.$api.post('/member-reversion/user/perfect/findTableHead', {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
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
            // 取下拉数据
            onGetUnit($event, index){
              this.data[index].unit = $event
            },
            // 通用商品名 回显时要用到promise函数 先请求到数据才能根据value值回显
            remoteMethod (query) {
                var p = new Promise((resolve, reject) => {
                    if (query !== '') {
                    this.loading = true
                    this.$api.post('/portal/shopCommdoity/findCurrencyCommodity', {
                        name: query
                    }).then(response => {
                        console.log('response', response)
                        if (response.code === 200) {
                            this.loading = false
                            this.commonProductNameList = []
                            response.data.forEach(element => {
                                this.commonProductNameList.push({
                                label: element.commodityName,
                                value: element.id,
                                productCode: element.productCode,
                                productName: element.productName
                                })
                            })
                            resolve('success')
                        } else if (response.code === 300) {
                            this.loading = false
                            this.commonProductNameList = []
                            this.isShowAddBtn = true
                            this.editBtn = false
                            resolve('failed')
                        }
                    }).catch(error => {
                        this.$Message.error('通用商品名查询错误！')
                        resolve('failed')
                    })
                    } else {
                        this.commonProductNameList = []
                        this.isShowAddBtn = true
                        this.editBtn = false
                        resolve('failed')
                    }
                })
                return p
            },
            // 初始化加载数据
            init (type = 0) {
                this.$api.post('/member-reversion/wantBuy/find', {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    templateId: this.templateId
                }).then(response => {
                    console.log('init response', response)
                    if (response.code === 200) {
                        this.isLoading = false
                        // if (response.data.propertyName && response.data.propertyName !== '') {
                        //     this.title = response.data.propertyName
                        // }
                        if (response.data.preview && response.data.preview !== '') {
                            this.preview = response.data.preview
                        }
                        if (response.data.list.length !== 0) {
                            this.data = []
                            response.data.list.forEach((element, index) => {
                                this.data.push({
                                    id: element.id,
                                    name: element.name,
                                    productName: element.productName,
                                    total: element.total,
                                    price: element.price,
                                    totalAmount: element.totalAmount,
                                    unit: element.unit,
                                    status: element.status === '1' ? true : false,
                                    isAdd: false
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
            // 增加
            handleAdd () {
                this.data.push({
                    name: '',
                    productName: '',
                    total: '',
                    price: '',
                    totalAmount: '',
                    unit: '',
                    status: true,
                    isAdd: true
                })
                this.index = this.data.length - 1
            },
            edit (index) {
                this.data[index].isAdd = true
                this.index = index
            },
            del (item, index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确认删除？',
                    onOk:()=>{
                        if (item.id !== undefined && item.id !== '') {
                            this.$api.post('/member-reversion/wantBuy/delete', {
                                id: item.id
                            }).then(response => {
                                if (response.code === 200) {
                                    this.$Message.success('删除成功！')
                                    this.data.splice(index, 1)
                                    this.change()
                                }
                            }).catch(error => {
                                this.$Message.error('服务器异常！')
                            })
                        } else {
                            this.$Message.success('删除成功！')
                            this.data.splice(index, 1)
                            this.change()
                        }
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            },
            // 保存单个风景名胜设施信息
            save (item, index) {
                let data = {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    isComplete: '1',
                    name: item.name,
                    productName: item.productName,
                    total: item.total,
                    price: item.price,
                    totalAmount: item.totalAmount,
                    unit: item.unit,
                    status: item.status,
                    id: item.id === '' || item.id === undefined ? '' : item.id,
                    templateId: this.templateId
                }
                console.log('data', data)
                this.isLoading = true
                this.$api.post('/member-reversion/wantBuy/save', data).then(response => {
                    console.log('save response', response)
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init(1)
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 保存预览信息
            handleSave () {
                let data = {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    dictId: this.modeId,
                    isComplete: '1',
                    textPreview: this.preview,
                    templateId: this.templateId
                }
                console.log('data', data)
                this.$api.post('/member-reversion/perfect/saveTextPreview', data).then(response => {
                    console.log('save preview response', response)
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init()
                        this.$emit('on-save')
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            // 拼接文字预览
            change () {
                this.preview = ''
                this.data.forEach(element => {
                    if (element.name && element.name !== '') {
                        this.preview += `通用商品名：${element.name}，`
                    }
                    if (element.productName && element.productName !== '') {
                        this.preview += `产品名称：${element.productName}，`
                    }
                    if (element.unit && element.unit !== '') {
                        this.preview += `产量单位：${element.unit}，`
                    }
                    if (element.total && element.total !== '') {
                        this.preview += `产品数量：${element.total}，`
                    }
                    if (element.price && element.price !== '') {
                        this.preview += `产品单价：${element.price}元，`
                    }
                    if (element.totalAmount && element.totalAmount !== '') {
                        this.preview += `金额：${element.totalAmount}元，`
                    }
                })
                this.preview = this.preview.substring(0, this.preview.length - 1) + '。'
            },
            handleTotalAmount (type, index) {
                var total = this.data[index].total
                var price = this.data[index].price
                let all = total * price
                this.data[index].totalAmount = all.toFixed(2)
            },
            leftRefresh () {
                this.$emit('left-refresh')
            }
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
    .units .ivu-select-item{
        padding: 7px 8px;
    }
</style>
