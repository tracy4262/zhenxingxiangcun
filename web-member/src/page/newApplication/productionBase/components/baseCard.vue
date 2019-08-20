<!--
    作者：chenqim
    时间：2019-01-07
    描述：生产基地card
-->
<template>
    <div class="mt20 ml10 mr10 base-card-shadow" :style="index === 0 ? 'border-style: dashed dashed dashed dashed;' : '' ">
        <div v-if="index === 0" class="pd15 tc" @click="add">
            <Row>&nbsp;</Row>
            <Row>&nbsp;</Row>
            <Row>&nbsp;</Row>
            <Row>&nbsp;</Row>
            <Row>&nbsp;</Row>
            <Row><Icon type="md-add" size="60" color="#9B9B9B" /></Row>
            <Row style="color: #9B9B9B; font-size: 16px; margin-top: 10px;">添加生产基地</Row>
            <Row>&nbsp;</Row>
            <Row>&nbsp;</Row>
            <Row>&nbsp;</Row>
            <Row>&nbsp;</Row>
            <Row>&nbsp;</Row>
        </div>
        <div v-if="index !== 0">
            <div class="pd10">
                <Row>
                    <Col span="24">
                        <img :src="`//api.map.baidu.com/staticimage?width=286&height=180&center=${item.coordinate.split(',')[0]},${item.coordinate.split(',')[1]}&zoom=11&markers=${item.coordinate.split(',')[0]},${item.coordinate.split(',')[1]}`" style="width: 100%;">
                    </Col>
                </Row>
                <Row>
                    <Col span="24" class="base-name ell mt10" :title="item.productionBaseName">{{ item.productionBaseName }}</Col>
                </Row>
                <Row class="mt10">
                    <Col span="10" class="base-info ell">&nbsp;</Col>
                    <Col span="14" class="base-info ell">&nbsp;</Col>
                </Row>
            </div>
            <Row class="base-button-bar tc" type="flex" align="middle">
                <Col span="8">
                    <a class="base-button" @click="edit">编辑</a>
                </Col>
                <Col span="8" style="border-left: 1px solid #ececec;">
                    <a class="base-button" @click="del">删除</a>
                </Col>
                <Col span="8" style="border-left: 1px solid #ececec;">
                    <a class="base-button" @click="detail">介绍</a>
                </Col>
            </Row>
        </div>
        <!-- 新增基地弹出框 -->
        <Modal v-model="modal" width="500" title="新增基地" :mask-closable="false">
            <Form ref="form" :model="model" :rules="rule" :label-width="100">
                <FormItem label="基地名称" prop="productionBaseName">
                    <Input v-model="model.productionBaseName" :maxlength="30" style="width: 300px;" />
                </FormItem>
                <FormItem label="选择地块" prop="land">
                    <Select v-model="model.land" placeholder="请选择地块" style="width: 300px;" @on-change="changeLand">
                        <Option v-for="(it, index) in landList" :key="index" :value="it.land">{{ it.land }}</Option>
                    </Select>
                    <a class="ml5" href="javascript:void(0);" @click="addLand">新增地块</a>
                </FormItem>
                <FormItem label="所处位置" prop="location">
                    <Input v-model="model.location" style="width: 300px;" />
                </FormItem>
                <FormItem label="基地中心点坐标" prop="coordinate">
                    <Input v-model="model.coordinate" style="width: 300px;" disabled />
                </FormItem>
                <FormItem label="基地简介" prop="introduction">
                    <Input type="textarea" v-model="model.introduction" :maxlength="500" style="width: 300px;" />
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancel">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import { isTelephoneAndphone } from '~utils/validate'
export default {
    name: 'baseCard',
    props: {
        item: {
            type: Object
        },
        index: {
            type: Number
        }
    },
    components: {

    },
    data () {
        return {
            modal: false,
            model: {
                productionBaseName: '',
                land: '',
                landId: '',
                location: '',
                coordinate: '',
                introduction: ''
            },
            rule: {
                productionBaseName: [
                    { required: true, message: '请填写基地名称', trigger: 'blur' }
                ],
                land: [
                    { required: true, message: '请选择地块', trigger: 'change' }
                ],
                location: [
                    { required: false }
                ],
                coordinate: [
                    { required: false }
                ],
                introduction: [
                    { required: false }
                ]
            },
            landList: [],
            templateId: ''
        }
    },
    created () {
        // 查询当前启用模板
        this.$api.post('/member-reversion/user/userTemplateManage/find', {
            account: this.$user.loginAccount
        }).then(response => {
            if (response.code === 200) {
                if (response.data.userTemplate) {
                    this.templateId = response.data.userTemplate.templateId
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
        this.initLandList()
    },
    methods: {
        // 初始化地块信息下拉框列表
        initLandList () {
            this.$api.post('/member-reversion/productionBase/landInfo', {
                account: this.$user.loginAccount
            }).then(response => {
                if (response.code === 200) {
                    this.landList = response.data
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        // 自动填充带过来的数据
        changeLand (value) {
            this.landList.forEach(element => {
                if (element.land === value) {
                    // if (!element.coordinate) {
                    //     let x = (e.x1 * 1000000000000000 + e.x2 * 1000000000000000) / 2000000000000000
                    //     let y = (e.y1 * 1000000000000000 + e.y2 * 1000000000000000) / 2000000000000000
                    //     e.longitude = x
                    //     e.latitude = y
                    // }
                    this.model.landId = element.landId
                    this.model.location = element.location
                    this.model.coordinate = element.coordinate
                    this.model.contactName = element.contactName
                    this.model.contactAccount = this.$user.loginAccount
                }
            })
        },
        edit () {
            this.$router.push({
                path: '/member/editProductionBase',
                query: {
                    id: this.item.id
                }
            })
        },
        del () {
            this.$Modal.confirm({
                title: '操作提示',
                content: '<p>您确定删除该生产基地？</p>',
                cancelText: '取消',
                onOk: () => {
                    this.$api.get('/member-reversion/productionBase/delete?id=' + this.item.id).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('删除成功!')
                            this.$emit('refresh')
                        } else {
                            this.$Message.error('服务器异常！')
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        },
        detail () {
            this.$router.push({
                path: '/member/productionBaseDetail',
                query: {
                    id: this.item.id,
                    account: this.item.account
                }
            })
        },
        add () {
            this.$refs['form'].resetFields()
            this.modal = true
        },
        ok () {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    this.model.account = this.$user.loginAccount
                    this.$api.post('/member-reversion/productionBase/saveOrUpdate', this.model).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('保存成功!')
                            this.modal = false
                            this.$emit('refresh')
                        } else {
                            this.$Message.error('服务器异常！')
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                } else {
                    this.$Message.error('请核对表单字段！')
                }
            })
        },
        cancel () {
            this.modal = false
            this.$refs['form'].resetFields()
        },
        addLand () {
            this.$router.push(`/auth/step7?templateId=${this.templateId}&active=landInfo`)
        }
    }
}
</script>
<style lang="scss" scoped>
    .base-card-shadow {
        border: 1px solid #f5f5f5;
        cursor: pointer;
        &:hover {
            transition: 0.5s;
            box-shadow: 0 5px 5px 0 rgba(18,88,48,.09);
        }
    }
    .base-name {
        color: #000 85%;
        font-size: 16px;
    }
    .base-info {
        color: #7C8C8C;
    }
    .base-button-bar {
        border-top: 1px solid #f5f5f5;
        background-color: #f6f9fa;
        height: 48px;
    }
    .base-button {
        color: #9c9fa0;
        &:hover {
            color: #00c882;
        }
    }
</style>
