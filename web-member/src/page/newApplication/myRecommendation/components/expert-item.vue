<template>
    <div>
        <Card :padding="0" style="height:300px;">
            <div class="ivu-server-item">
                <img v-if="item.personalPicture" :src="item.personalPicture" style="cursor: pointer;" width="100%" height="170">
                <img v-else src="../../../../../static/img/goods-list-no-picture1.png" style="cursor: pointer;" width="100%" height="170">
            </div>
            <div class="pd10">
                <p style="height:35px; line-height:35px;" class="ell" :title="item.expertName">{{item.expertName}}&nbsp;</p>
                <Row type="flex" align="middle" class="pb5">
                    <Col span="24" class="ell" :title="item.adeptSpecies">
                        擅长物种：{{ item.adeptSpecies }}
                    </Col>
                </Row>
                <Row type="flex" align="middle" class="pb5">
                    <Col span="24" class="ell" :title="item.adeptField">
                        擅长领域：{{ item.adeptField }}
                    </Col>
                </Row>
                <Row>
                    <Col span="12">
                        <Button :type="item.isRecommend === '未推荐' ? 'primary' : 'info'" size="small" @mouseover.native="over(item.isRecommend)" @mouseout.native="out(item.isRecommend)" @click="click(item)">{{ text }}</Button>
                    </Col>
                    <Col span="12" class="tr"><Button type="default" size="small" @click="detail(item)">详情 <Icon type="ios-arrow-forward"></Icon></Button></Col>
                </Row>
            </div>
        </Card>
    </div>
</template>
<script>
export default {
    props: {
        item: Object
    },
    components: {
    },
    data () {
        return {
            text: this.item.isRecommend
        }
    },
    created () {
    },
    methods: {
        detail (item) {
            this.$router.push({
                path: `/portals/index`,
                query: {
                    uid: item.account,
                    id: 0
                }
            })
        },
        over (isRecommend) {
            if (isRecommend === '未推荐') {
                this.text = '添加推荐'
            } else {
                this.text = '取消推荐'
            }
        },
        out (isRecommend) {
            if (isRecommend === '未推荐') {
                this.text = '未推荐'
            } else {
                this.text = '已推荐'
            }
        },
        click (item) {
            if (item.isRecommend === '未推荐') {
                // 调用添加推荐的接口
                this.op(1, [{id: item.id}])
            } else {
                // 调用取消推荐的退口
                this.op(0, [{id: item.id}])
            }
        },
        op (flag, list) {
            this.$Modal.confirm({
                title: '操作提示',
                content: flag === 1 ? '设置为推荐的专家将在您的门户对外宣传展示！请确认是否设置为推荐专家！' : '取消推荐的专家将从您的门户删除！请确认是否取消推荐！',
                onOk: () => {
                    this.$api.post('/member-reversion/myRecommend/operation', {
                        account: this.$user.loginAccount,
                        flag: flag, // 0:取消推荐, 1:推荐
                        type: 3, // 1:推荐服务, 2:推荐基地, 3:推荐专家
                        list: list
                    }).then(response => {
                        if (response.code === 200) {
                            this.$Message.success(flag === 0 ? '取消推荐成功！' : '推荐成功！')
                            this.$emit('refresh')
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        }
    }
}
</script>
<style lang="scss" scoped>
.ivu-server-item {
    position: relative;
    .tip{
        position: absolute;
        display: block;
        width: 65px;
        height: 25px;
        top: 0px;
        left: 0px;
        line-height: 25px;
        text-align: center;
        background: rgba(102, 102, 102, 0.86);
        color: #fff;
        font-size: 12px;
    }
    .related-service{
        position: absolute;
        display: block;
        bottom: 0px;
        left: 0px;
        color: #fff;
        line-height: 25px;
        width: 100%;
    }
}
</style>