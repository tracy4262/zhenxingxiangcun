<!--
    作者：chenqim
    时间：2018-12-17
    描述：申请代理
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <Button :type="activeIndex === 0 ? 'primary' : 'text'" @click="find">查找账号</Button>
        <Button :type="activeIndex === 1 ? 'primary' : 'text'" @click="perfecting">完善资料中</Button>
        <!-- 查找账号 -->
        <div v-if="activeIndex === 0 && isList" class="mt20">
            <Row>
                <Col span="12">
                    <Input prefix="ios-search" v-model="key" placeholder="查找登录账号或登录名称" @on-enter="search" />
                </Col>
                <Col span="12">
                    <Button type="primary" @click="search">搜索</Button>
                </Col>
            </Row>
            <Row>
                <div v-for="(item, index) in list" :key="index" class="proxy-card">
                    <proxy-card :item="item" :type="1" @refresh="refresh" @want-to-proxy="wantToProxy"></proxy-card>
                </div>
            </Row>
        </div>
        <!-- 完善资料中 -->
        <div v-if="activeIndex === 1 && isList" class="mt20">
            <Row>
                <div v-for="(item, index) in list" :key="index" class="proxy-card">
                    <proxy-card :item="item" :type="2" @refresh="refresh2" @want-to-proxy="wantToProxy"></proxy-card>
                </div>
            </Row>
        </div>
        <perfect-information :account="account" v-if="!isList" @back="back"></perfect-information>
        <div class="mt20 tr" v-if="list.length !== 0 && isList">
            <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange" />
        </div>
        <!-- 账号不存在弹出框弹出框 -->
        <Modal v-model="modal" width="400" :mask-closable="false" :closable="false" footer-hide :styles="{top: '200px'}">
            <Row type="flex" align="top" class="mt20">
                <Col span="3"><Icon type="ios-help-circle" color="#f90" size="30" /></Col>
                <Col span="21"><p style="font-size: 16px; color: #000 85%;">您查找的账号不存在，如需代理该账号，请先完成注册！</p></Col>
            </Row>
            <div class="tr mt20">
                <Button type="text" @click="cancel">取消</Button>
                <Button type="primary" @click="register">注册</Button>
            </div>
        </Modal>
        <!-- 注册 -->
        <login-register ref="loginRegister" type="代理登录注册" @on-success="handleSuccess"></login-register>
    </div>
</template>
<script>
import proxyCard from './proxyCard'
import loginRegister from '~components/loginRegister/index'
import perfectInformation from './perfectInformation/index'
export default {
    name: '',
    components: {
        proxyCard,
        loginRegister,
        perfectInformation
    },
    data () {
        return {
            activeIndex: 0,
            modal: false,
            list: [],
            total: 0,
            pageSize: 9,
            pageNum: 1,
            key: '',
            isList: true
        }
    },
    created () {

    },
    methods: {
      wantToProxy (account) {
        this.isList = false
        this.account = account
      },
      back () {
        this.isList = true
      },
        init () {
            this.$api.post('/member/reversionProxy/queryAccount', {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                key: this.key
            }).then(response => {
                console.log('init res', response)
                if (response.code === 200) {
                    if (response.data.list.length > 0) {
                        this.list = response.data.list
                        this.total = response.data.total
                    } else {
                        // 如果账号不存在
                        this.modal = true
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        init2 () {
            this.$api.post('/member/reversionProxy/queryAccount', {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                key: '',
                status: 0,
	            proxyAccount: this.$user.loginAccount
            }).then(response => {
                console.log('init2 res', response)
                if (response.code === 200) {
                    this.list = response.data.list
                    this.total = response.data.total
                    
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        find () {
            this.activeIndex = 0
            this.pageNum = 1
            this.key = ''
            this.list = []
            this.isList = true
        },
        perfecting () {
            this.activeIndex = 1
            this.pageNum = 1
            this.init2()
        },
        search () {
            // 查询账号
            this.pageNum = 1
            if (this.key === '') {
                this.$Message.info('登录账号或登录名称不能为空！')
            } else {
                this.init()
            }
        },
        cancel () {
            this.modal = false
        },
        register () {
            this.modal = false
            // 注册
            this.$refs['loginRegister'].regist()
        },
        handleSuccess (response) {
            // 刷新注册组件
            this.$refs.loginRegister.active = '登录'
            // 记录注册的账号
            this.key = response.data.key
            this.search()
        },
        pageChange (page) {
            this.pageNum = page
            this.init()
        },
        refresh () {
            this.activeIndex = 0
            this.pageNum = 1
            this.init()
        },
        refresh2 () {
            this.perfecting()
        }
    }
}
</script>
<style lang="scss" scoped>
    .proxy-card {
        width: calc(100% / 3);
        display: inline-block;
    }
    .vertical-center-modal {
        display: flex;
        align-items: center;
        justify-content: center;

        .ivu-modal {
            top: 0;
        }
    }
</style>
