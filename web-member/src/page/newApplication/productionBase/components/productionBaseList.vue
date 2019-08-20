<!--
    作者：chenqim
    时间：2019-01-07
    描述：生产基地管理列表页
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <Row type="flex" justify="center" class="mt20">
            <Col span="12">
                <Input prefix="ios-search" size="large" v-model="key" placeholder="可输入基地名称进行查询" @on-enter="search" class="search-input" />
            </Col>
            <Col span="2">
                <Button type="primary" size="large" @click="search" class="search-btn">查询</Button>
            </Col>
        </Row>
        <Row class="mt20">
            <div v-for="(item, index) in list" :key="index" class="base-card">
                <base-card :item="item" :index="index" @refresh="refresh"></base-card>
            </div>
        </Row>
        <div class="mt20 tr" v-if="list.length !== 0">
            <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange" />
        </div>
    </div>
</template>
<script>
import baseCard from './baseCard'
export default {
    name: 'productionBaseList',
    components: {
        baseCard
    },
    data () {
        return {
            key: '',
            list: [
                // 这个是新增生产基地按钮
                {
                    id: 0
                }
            ],
            total: 0,
            pageSize: 8,
            pageNum: 1
        }
    },
    created () {
        this.init()
    },
    methods: {
        // 初始化列表查询
        init () {
            this.$api.post('/member-reversion/productionBase/list', {
                account: this.$user.loginAccount,
                pageSize: this.pageSize,
                pageNum: this.pageNum,
                key: this.key
            }).then(response => {
                if (response.code === 200) {
                    response.data.list.forEach(element => {
                        this.list.push({
                            id: element.id,
                            account: element.account,
                            productionBaseName: element.productionBaseName,
                            contactName: element.contactName,
                            phoneNumber: element.phoneNumber,
                            coordinate: element.coordinate
                        })
                    })
                    this.total = response.data.total
                } else {
                    this.$Message.error('服务器异常！')
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        search () {
            this.pageNum = 1
            this.list = [{
                id: 0
            }]
            this.init()
        },
        refresh () {
            this.list = [{
                id: 0
            }]
            this.init()
        },
        pageChange (page) {
            this.pageNum = page
            this.list = [{
                id: 0
            }]
            this.init()
        }
    }
}
</script>
<style lang="scss" scoped>
    .base-card {
        width: calc(100% / 3);
        display: inline-block;
    }
    .search-btn {
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
    }
</style>
<style>
    .search-input input {
        border-top-right-radius: 0;
        border-bottom-right-radius: 0;
    }
</style>

