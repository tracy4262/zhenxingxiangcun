<template>
    <div class="pd20">
        <Title :title="title" edit :id="id" :yearId="yearId"></Title>
        <div class="pd20">
            <Form :label-width="82" label-position="left" style="background:#f9f9f9;" class="pd20 mt40" :model="item" :rules="ruleInline" v-for="(item, index) in data" :ref="`data${index}`">
                <Row :gutter="16">
                    <Col span="8">
                    <Form-item label="权限">
                        <i-switch size="large" v-model="item.status" :disabled="!item.edit">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                    </Col>
                    <!-- <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.edit">编辑</span>
                <span class="auth-btn-toolbar" @click="handleDel(item, index)" v-if="data.length != 1 && item.edit">删除</span>
              </Col> -->
                </Row>
                <Row :gutter="16">
                    <Col span="8">
                    <Form-item prop="rightHolderName" label="权利人姓名">
                        <!-- 单选 默认为会员名称，可以从人口信息花名册中选择-->
                        <Select v-model="item.rightHolderName" style="width: 100%" :disabled="!item.edit">
                            <Option v-for="(item, index) in rightHolderNames" :value="item.name" :key="index">{{item.name}}</Option>
                        </Select>
                    </Form-item>
                    </Col>
                    <Col span="8">
                    <Form-item prop="genericName" label="通用名称">
                        <!-- 从通用商品名中选择 -->
                        <vui-product-name :disabled="!item.edit" :values="item.genericName" @handle-get-result="handleResult($event, item, index)"></vui-product-name>
                        <!-- <Input v-model="item.genericName" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input> -->
                    </Form-item>
                    </Col>
                    <Col span="8">
                    <Form-item prop="brandName" label="品牌名称">
                        <Input v-model="item.brandName" :maxlength="20" :disabled="!item.edit"></Input>
                    </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="8">
                    <Form-item prop="model" label="型号">
                        <Input v-model="item.model" :maxlength="20" :disabled="!item.edit"></Input>
                    </Form-item>
                    </Col>
                    <Col span="8">
                    <Form-item prop="quantity" label="数量">
                        <Input v-model="item.quantity" :maxlength="20" :disabled="!item.edit">
                        </Input>
                    </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="8">
                    <Form-item prop="univalent" label="单价">
                        <Input v-model="item.univalent" :maxlength="20" :disabled="!item.edit">
                        <span slot="append">元</span>
                        </Input>
                    </Form-item>
                    </Col>
                    <Col span="8">
                    <Form-item prop="totalPrice" label="总值">
                        <Input v-model="item.totalPrice" :maxlength="20" :disabled="!item.edit" @on-change="changePreview">
                        <span slot="append">元</span>
                        </Input>
                    </Form-item>
                    </Col>
                </Row>
                <!-- <div class="tc pd20" v-if="item.edit">
            <Button type="primary" @click="handleSave(item, index)">保存</Button>
          </div> -->
            </Form>
            <!-- <Button type="primary" ghost icon="md-add" class="mt20 mb40 btn-light-primary" @click="handleAdd">添加</Button> -->
        </div>

        <Title title="文字预览"></Title>
        <div class="pd20 pt30">
            <Input type="textarea" v-model="textPreview.textPreview" :autosize="{minRows: 3,maxRows: 5}"></Input>
        </div>
        <div class="tc pd40">
            <Button type="primary"v-if="isLoading">保存</Button>
            <Button type="primary" v-else @click="onSave">保存</Button>
        </div>
    </div>
</template>

<script>
import Title from '../../components/title'
import { isMoney3 } from '~utils/validate'
import { numAdd } from '~utils/utils'
import vuiProductName from '~components/vui-productName'
export default {
    props: {
        yearId: {
            type: String
        },
        id: {
            type: String
        },
        appId: {
            type: String
        }
    },
    components: {
        Title,
        vuiProductName
    },
    data() {
        return {
            total: 0,
            status: true,
            textPreview: {},
            title: '生产类机械设备信息',
            rightHolderNames: [],
            data: [
                {
                    status: true,
                    rightHolderName: this.displayName, // 权利人姓名
                    genericName: '', // 通用名称
                    brandName: '', // 品牌名称
                    model: '', // 型号
                    quantity: '', // 数量
                    univalent: '', // 单价
                    totalPrice: '', // 总值
                    edit: false
                }
            ],
            ruleInline: {
                univalent: [
                    { validator: isMoney3, trigger: 'blur' },
                ],
                totalPrice: [
                    { validator: isMoney3, trigger: 'blur' },
                ]
            },
            activeIndex: 0,
            textPreviewId: 0,
            displayName: '',
            isLoading: true
        }
    },
    created() {
        this.$user.displayName ? this.displayName = this.$user.displayName : ''
        this.handleSelect()
    },
    methods: {
        // 取下拉数据
        handleSelect() {
            let list = {
                user_id: this.$user.loginAccount,
                year_id: this.yearId
            }
            this.$api.post('/member-reversion/administrationDivision/findRoster', list).then(response => {
                if (response.code === 200) {
                    this.rightHolderNames = response.data
                    this.rightHolderNames.unshift({ name: this.displayName })
                }
            })
        },
        //  初始化数据
        handleInit(type) {
            this.$api.post('/member-reversion/assetSeting/findProductionMachineInfo', {
                account: this.$user.loginAccount,
                templateId: this.$template.id,
                yearId: this.yearId,
                parentId: this.id
            }).then(response => {
                if (response.code == 200) {
                    this.title = response.data.productionMachineInfoName
                    if (response.data.productionMachineInfo.length) {
                        this.data = response.data.productionMachineInfo
                        this.data.forEach(e => {
                            e.edit = false
                        })
                    }
                    if (!response.data.textPreview.textPreview) {
                        response.data.textPreview.textPreview = `权利人姓名（），通用名称（），品牌名称（），型号（），数量（），单价（）元，总值（）元。`
                    }
                    this.textPreview = response.data.textPreview
                    this.textPreviewId = response.data.textPreview.id
                    this.isLoading = false
                }
            })
        },
        handleResult($event, item, index) {
            this.data[index].genericName = $event
            this.changePreview()
        },
        // 编辑
        handleEdit(item, index) {
            item.edit = true
            this.data.splice(index, 1, item)
        },
        // 添加
        handleAdd() {
            this.data.push(
                {
                    status: true,
                    rightHolderName: this.displayName, // 权利人姓名
                    genericName: '', // 通用名称
                    brandName: '', // 品牌名称
                    model: '', // 型号
                    quantity: '', // 数量
                    univalent: '', // 单价
                    totalPrice: '', // 总值
                    edit: true
                }
            )
        },
        // 删除
        handleDel(item, index) {
            this.$Modal.confirm({
                title: '是否确定删除',
                content: '是否确认删除？',
                onOk: () => {
                    if (item.id) {
                        this.$api.post('/member-reversion/assetSeting/deleteProductionMachineInfo', { id: item.id }).then(response => {
                            if (response.code === 200) {
                                this.$Message.success('删除成功')
                                this.data.splice(index, 1)
                            }
                        })
                    } else {
                        this.$Message.success('删除成功')
                        this.data.splice(index, 1)
                    }
                },
                okText: '确定',
                cancelText: '取消'
            });
        },
        // 保存
        handleSave(item, index) {
            this.$refs[`data${index}`][0].validate(v => {
                if (v) {
                    let list = {
                        account: this.$user.loginAccount,
                        yearId: this.yearId,
                        parentId: this.id,
                        productionMachineInfoName: this.title,
                        productionMachineInfo: item
                    }
                    this.$api.post('/member-reversion/assetSeting/saveProductionMachineInfo', list).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('保存成功')
                            item.edit = false
                            this.handleInit(1)
                        }
                    })
                } else {
                    this.$Message.error('请核对表单信息')
                }
            })

        },
        // 文字预览
        changePreview() {
            let str = ''
            this.data.forEach(e => {
                if (e.rightHolderName && e.genericName && e.totalPrice) {
                    str += `${e.rightHolderName}有${e.genericName}，总值${e.totalPrice}元，`
                }
            })
            if (str) {
                str = `${str.substring(0, str.length - 1)}。`
            }
            this.textPreview.textPreview = str
        },
        // 保存文字预览
        onSave() {
            this.textPreview.account = this.$user.loginAccount
            this.textPreview.yearId = this.yearId
            this.textPreview.parentId = this.id
            this.textPreview.templateId = this.$template.id
            this.textPreview.isComplete = this.data.length === 0 ? false : true
            this.textPreview.id = this.textPreviewId === '' || this.textPreviewId === undefined ? 0 : this.textPreviewId,
            this.isLoading = true
                this.$api.post('/member-reversion/assetSeting/saveTextPreview', { textPreview: this.textPreview }).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功')
                        this.$emit('on-save')
                        this.handleInit()
                    }
                })
        },
    }
}
</script>

<style lang="scss" scoped>
.subtotal {
  font-size: 16px;
}
</style>
