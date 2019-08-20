<!--
    作者：chenqim
    时间：2018-12-27
    描述：收藏内容
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <div class="pt10 pl10 pr10 pb20">
            <!-- 搜索栏 -->
            <Row class="mb30">
                <Col span="10">
                    <span style="font-size: 20px;">我的收藏内容</span>
                </Col>
                <Col span="5">
                    <Cascader :data="favoriteList" v-model="favorites" :render-format="format" change-on-select>
                    </Cascader>
                </Col>
                <Col span="5" offset="1">
                    <Input v-model="key" placeholder="查找关键词" />    
                </Col>
                <Col span="2" offset="1">
                    <Button type="primary" @click="search">查找</Button>
                </Col>
            </Row>
            <!-- 收藏内容 -->
            <Row v-for="(item, index) in list" :key="index" type="flex" align="middle" class="favorite-card">
                <Col span="18">
                    <Row><a style="font-size: 14px; color: #5b6478;" :href="item.path" target="_blank">{{ item.title }}</a></Row>
                    <Tag color="success" class="mt5">{{ item.favorite }}</Tag>
                </Col>
                <Col span="6" class="tr">
                    <Button type="primary" @click="move(item.id)">移动收藏夹</Button>
                    <Button type="text" @click="cancel(item.id)">取消收藏</Button>
                </Col>
            </Row>
            <Page v-if="list.length !== 0" :total="total" :current="pageNum" :page-size="pageSize" @on-change="pageChange" class="mt20 tr"></Page>
        </div>
        <div class="tc" style="height: 400px; line-height: 400px; font-size: 16px;" v-if="list.length === 0">暂无收藏内容</div>
        <!-- 移动收藏 -->
        <move ref="move" :itemId="itemId" :templateId="templateId" @refresh="refresh"></move>
    </div>
</template>

<script>
    import collection from './collection'
    import move from './move'
    export default {
        components: {
            collection,
            move
        },
        data() {
            return {
                favorite: '',
                favorites: [],
                key: '',
                favoriteList: [],
                list: [],
                total: 0,
                pageNum: 1,
                pageSize: 10,
                itemId: 0,
                templateId: ''
            }
        },
        created () {
            this.$api.post('/member-reversion/realStep/findEnableStep', {
                account: this.$user.loginAccount
            }).then(response => {
                if (response.code === 200) {
                    if (response.data) {
                        this.templateId = response.data.templateId
                        this.init()
                        this.initFavoriteList()
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        methods: {
            init () {
                this.$api.post('/member/report/findCollect', {
                    account: this.$user.loginAccount,
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    collectId: this.favorite,
                    title: this.key,
                    templateId: this.templateId
                }).then(res => {
                    if (200 === res.code) {
                        this.list = res.data.list.list
                        this.total = res.data.list.total
                    }
                })
            },
            initFavoriteList () {
                this.$api.post('/member-reversion/collect/queryFavorite', {
                    account: this.$user.loginAccount,
                    templateId: this.templateId
                }).then(res => {
                    if (res.code === 200) {
                        this.favoriteList = res.data
                    }
                })
            },
            search () {
                this.pageNum = 1
                this.init()
            },
            move (id) {
                this.$refs['move'].init()
                this.itemId = id
            },
            cancel (id) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确定取消收藏？',
                    onOk: () => {
                        this.$api.post('/member/report/delFollow', {
                            id :id
                        }).then(res => {
                            if (res.code === 200) {
                                this.$Message.success('取消成功！')
                                this.search()
                            }
                        })
                    }
                })
            },
            pageChange (page) {
                this.pageNum = page
                this.init()
            },
            format (labels, selectedData) {
                if (selectedData.length > 0) {
                    this.favorite = selectedData[selectedData.length - 1].value
                } else {
                    this.favorite = ''
                }
                return labels.join('/')
            },
            refresh () {
                this.init()
            }
        }
    }
</script>
<style scoped>
.favorite-card {
    border: 1px solid #e8e8e8;
    padding: 10px 20px;
    margin-top: 10px;
    border-radius: 5px;
}
</style>