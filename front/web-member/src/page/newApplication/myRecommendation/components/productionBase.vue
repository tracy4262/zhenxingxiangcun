<template>
    <div class="pd20" style="min-height: 500px;">
        <Button :type="activeIndex === 0 ? 'primary' : 'text'" @click="find">查找基地</Button>
        <Button :type="activeIndex === 1 ? 'primary' : 'text'" @click="has">已推荐基地</Button>
        <Form ref="queryInfo" :model="queryInfo" label-position="left" :label-width="100" :rules="ruleInline" class="mt20">
            <Row :gutter="32">
                <Col span="7">
                    <Form-item prop="trade" class="input" label="行政区划">
                        <Cascader v-model="queryInfo.locationArr" :render-format="format" :data="locationList" :load-data="loadPositionDatas" change-on-select>
                        </Cascader>
                    </Form-item>
                </Col>
                <Col span="7">
                    <Form-item prop="speci" class="input" label="基地名称">
                        <Input v-model="queryInfo.name" clearable />
                    </Form-item>
                </Col>
                <Col span="7">
                    <Form-item prop="name" class="input" label="单位名称或姓名">
                        <Input v-model="queryInfo.unitOrName" clearable />
                    </Form-item>
                </Col>
                <Col span="3">
                    <Button type="primary" @click="query">查询</Button>
                </Col>
            </Row>
        </Form>
        <Button type="primary" @click="operate" v-if="flag">批量操作</Button>
        <Button type="primary" @click="exitOperate" v-if="!flag">退出批量操作</Button>
        <Button type="primary" @click="add" v-if="!flag"><span v-if="activeIndex === 0">添加推荐</span><span v-else>取消推荐</span></Button>
        <div class="mt20">
            <CheckboxGroup v-model="choosed">
                <Row :gutter="24">
                    <Col span="6" v-for="(item, index) in list" class="mb20">
                        <!-- 查找基地中，所有已推荐未推荐的都会查出来，所以已推荐的不能再次进行选择 -->
                        <Checkbox :label="item.id" v-if="!flag && item.isRecommend === '未推荐'" style="position: absolute; top: 10px; right: 10px; z-index: 1;"><span>&nbsp;</span></Checkbox>
                        <!-- 已推荐基地中所有的都是已推荐基地，所以不用做限制 -->
                        <Checkbox :label="item.id" v-if="activeIndex === 1 && !flag" style="position: absolute; top: 10px; right: 10px; z-index: 1;"><span>&nbsp;</span></Checkbox>
                        <production-base-item :item="item" @refresh="refresh"></production-base-item>
                    </Col>
                </Row>
            </CheckboxGroup>
        </div>
        <div class="mt20 tr" v-if="list.length !== 0">
            <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange" />
        </div>
    </div>
</template>
<script>
import productionBaseItem from './production-base-item'
export default {
    components: {
        productionBaseItem
    },
    data () {
        return {
            activeIndex: 0,
            queryInfo: {
                locationArr: [],
                location: '',
                name: '',
                unitOrName: ''
            },
            ruleInline: {},
            list: [],
            total: 0,
            pageSize: 8,
            pageNum: 1,
            locationList: [],
            flag: true,
            choosed: []
        }
    },
    created () {
        // 取地址
        this.$api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
            this.locationList = res.data
        })
        this.init()
    },
    methods: {
        init () {
            this.list = [] // 必须要加，不然会有意想不到的问题
            this.$api.post('/member-reversion/myRecommend/baseList', {
                account: this.$user.loginAccount,
                flag: this.activeIndex === 0 ? '0' : '1', // 0:查询所有基地, 1:查询已推荐基地
                address: this.queryInfo.location,
                baseName: this.queryInfo.name,
                memberName: this.queryInfo.unitOrName,
                pageNum: this.pageNum,
                pageSize: this.pageSize
            }).then(response => {
                if (response.code === 200) {
                    console.log('res', response)
                    this.list = response.data.list
                    this.total = response.data.total
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        find () {
            this.pageNum = 1
            this.activeIndex = 0
            this.flag = true
            // 清空查询条件
            this.queryInfo = {
                locationArr: [],
                location: '',
                name: '',
                unitOrName: ''
            }
            // 查询所有的基地
            this.init()
        },
        has () {
            this.pageNum = 1
            this.activeIndex = 1
            this.flag = true
            // 清空查询条件
            this.queryInfo = {
                locationArr: [],
                location: '',
                name: '',
                unitOrName: ''
            }
            // 查询已经推荐的基地
            this.init()
        },
        pageChange (page) {
            this.pageNum = page
            this.init()
        },
        query () {
            this.pageNum = 1
            this.init()
        },
        loadPositionDatas (item, callback) {
            item.loading = true
            this.$api.post(`/member/town/next/${item.value}`).then(res => {
                item.loading = false
                item.children = res.data
                callback()
            })
        },
        format (labels, selectedData) {
            let locationStr = labels.join('')
            this.queryInfo.location = locationStr
            return locationStr
        },
        operate () {
            this.flag = false
            this.choosed = []
        },
        exitOperate () {
            this.flag = true
            this.choosed = []
        },
        add () {
            if (this.choosed.length === 0) {
                this.$Message.info(this.activeIndex === 0 ? '请先选择要推荐的基地！' : '请先选择要取消推荐的基地！')
                return
            }
            this.$Modal.confirm({
                title: '操作提示',
                content: this.activeIndex === 0 ? '设置为推荐的基地将在您的门户对外宣传展示！请确认是否设置为推荐基地！' : '取消推荐的基地将从您的门户删除！请确认是否取消推荐！',
                onOk: () => {
                    // 请求添加接口，请求取消接口
                    // 刷新当前页面
                    let list = []
                    this.choosed.forEach(elemenet => {
                        list.push({
                            id: elemenet
                        })
                    })
                    this.$api.post('/member-reversion/myRecommend/operation', {
                        account: this.$user.loginAccount,
                        flag: this.activeIndex === 0 ? 1 : 0, // 0:取消推荐, 1:推荐
                        type: 2, // 1:推荐服务, 2:推荐基地, 3:推荐专家
                        list: list
                    }).then(response => {
                        if (response.code === 200) {
                            console.log('res', response)
                            this.$Message.success('推荐成功！')
                            this.init()
                            this.flag = true
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        },
        refresh () {
            this.pageNum = 1
            this.init()
        }
    }
}
</script>
<style lang="scss" scoped>

</style>
