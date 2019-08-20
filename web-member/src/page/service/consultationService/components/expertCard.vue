<!--
    作者：chenqim
    时间：2018-12-21
    描述：专家card
-->
<template>
    <div class="mt20 ml10 mr10 proxy-card-shadow">
         <Row class="pd10" type="flex" align="top">
            <Col span="8">
                <img style="width: 50px; height: 50px;" v-if="item.avatar !== ''" :src="item.avatar">
                <img style="width: 50px; height: 50px;" v-else src="../../../../../static/img/user-icon-big.png" alt="">
            </Col>
            <Col span="16">
                <div class="proxy-name ell" :title="item.expertName">{{ item.expertName === '' ? '暂无会员名称' : item.expertName }}</div>
                <div class="proxy-account mt5 ell" :title="item.account">登录名：{{ item.account }}</div>
                <div class="proxy-account mt5 ell-2" style="min-height: 36px;" :title="item.location">{{ item.location }}</div>
            </Col>
        </Row>
        <Row class="proxy-button-bar tc" type="flex" align="middle">
            <Col span="12">
                <a class="proxy-button disabled" v-if="item.account === $user.loginAccount">不能聘请自己</a>
                <a class="proxy-button" v-else-if="item.status === '聘请'" @click="invite">{{ item.status }}</a>
                <a class="proxy-button disabled" v-else>{{ item.status }}</a>
            </Col>
            <Col span="12" style="border-left: 1px solid #ececec;">
                <a class="proxy-button" @click="detail">查看详情</a>
            </Col>
        </Row>
    </div>
</template>
<script>
export default {
    name: 'expertCard',
    components: {

    },
    props: {
        item: {
            type: Object
        }
    },
    data () {
        return {

        }
    },
    created () {

    },
    methods: {
        invite () {
            this.$router.push({
                path: '/consultationService/detail',
                query: {
                    id: this.item.id
                }
            })
        },
        detail () {
            this.$toPortals(this.item.account)
        }
    }
}
</script>
<style lang="scss" scoped>
    .proxy-card-shadow {
        border: 1px solid #f5f5f5;
        &:hover {
            transition: 0.5s;
            box-shadow: 0 5px 5px 0 rgba(18,88,48,.09);
        }
    }
    .proxy-name {
        font-size: 16px;
        color: #000 85%;
    }
    .proxy-account {
        color: #9B9B9B;
    }
    .proxy-button-bar {
        border-top: 1px solid #f5f5f5;
        background-color: #f6f9fa;
        height: 48px;
    }
    .proxy-button {
        color: #9c9fa0;
        &:hover {
            color: #00c882;
        }
    }
    .disabled {
        cursor: not-allowed;
    }
</style>
