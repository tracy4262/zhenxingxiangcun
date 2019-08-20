<template>
	<div style="background: #F9F9F9;">
		<!-- 导航栏 -->
		<Affix>
			<top :address="false"></top>
		</Affix>
        <div :style="{'min-height': height}">
            <div class="layouts">
                <Row type="flex" align="middle">
                    <Col span="12">
                        <Breadcrumb class="pd20">
                        <BreadcrumbItem to="/index">首页</BreadcrumbItem>
                        <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
                        <BreadcrumbItem>我关注的内容</BreadcrumbItem>
                        </Breadcrumb>
                    </Col>
                    <Col span="12">
                    </Col>
                </Row>
                <Row>
                    <Col span="4">
                        <Card class="mb40">
                            <Row class="tc">
                                <Col span="24">
                                    <img style="width: 20px; height: 20px; margin-bottom: 6px;" src="../../../img/follow.png" />
                                    <span style="font-size: 18px;">我的关注</span>
                                </Col>
                                <Col span="24" v-for="(item, index) in tags" :key="index" class="mt20">
                                    <Button :type="index === activeIndex ? 'primary' : 'text'" @click="onSelect(index)" style="width: 90px; height: 38px;">{{ item.name }}</Button>
                                </Col>
                            </Row>
                        </Card>
                    </Col>
                    <Col span="20">
                        <div class="ml20">
                            <Row>
                                <Col span="22">
                                    <Input v-model="searchKey" placeholder="请输入您想要搜索的内容" />
                                </Col>
                                <Col span="2">
                                    <Button type="primary" @click="search" style="width: 100%;">搜索</Button>
                                </Col>
                            </Row>
                            <Row class="mt20 mb40">
                                <Card>
                                    <div class="tc" v-if="dataList.length === 0">{{ text }}</div>
                                    <div v-else>
                                        <articles :dataType="type" :data="item" v-for="(item,index) in dataList" :key="index" class="mb15"/>
                                        <div class="tc">
                                            <Button v-if="pageNum < totalPages" @click="more" size="large" type="text">查看更多<Icon type="ios-arrow-down" /></Button>
                                            <span v-else style="font-size: 14px;">暂无更多</span>
                                        </div>
                                    </div>
                                </Card>
                            </Row>
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
		<foot></foot>
  </div>
</template>
<script>
import top from '../../../top'
import foot from '../../../foot'
import articles from '../components/articles'
    export default {
        components: {
            top,
            foot,
            articles
        },
        data () {
            return {
                height: 0,
                activeIndex: 0,
                searchKey: '',
                tags: [
                    { name: '动态' },
                    { name: '政策' },
                    { name: '知识' },
                    { name: '产品' },
                    { name: '服务' },
                    { name: '标准' },
                ],
                dataList: [],
                type: '动态',
                queryType: '动态',
                pageNum: 1,
                pageSize: 5,
                totalPages: 0,
                text: ''
            }
        },
        created () {
            this.init()
        },
        methods: {
            init () {
                this.$api.post('/member/columnSettings/findColumnListDany', {
                    columnId: this.queryType,
                    account: this.$user.loginAccount,
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    key: this.searchKey
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.list.length > 0) {
                            this.totalPages = response.data.pages
                            response.data.list.forEach(element => {
                                this.dataList.push(element)
                            })
                        } else {
                            // 解决有数据的时候 暂无数据 会一闪而过的问题
                            this.text = '暂无数据'
                        }
                    } else if (response.code === 0) {
                        // 产品跟服务暂时是没数据的
                        this.text = '暂无数据'
                    }
                })
            },
            onSelect (index) {
                this.activeIndex = index
                // 重置查询条件要用到的type
                this.queryType = this.tags[index].name
                // 重置组件要用到的type
                this.type = this.tags[index].name
                // 清除查询条件
                this.searchKey = ''
                // 重置text
                this.text = ''
                // 重置页码与数据源
                this.pageNum = 1
                this.dataList = []
                this.init()
            },
            search () {
                this.pageNum = 1
                this.dataList = []
                this.init()
            },
            more () {
                // 不需要重置数据源 继续叠加
                this.pageNum += 1
                this.init()
            },
        },
        mounted () {
            this.height = `${window.innerHeight}px`
        }
    }
</script>
<style scoped>
</style>
