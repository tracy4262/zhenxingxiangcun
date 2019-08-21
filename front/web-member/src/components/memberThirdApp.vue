<template>
  <div>
    <div class="vui-member-base-app">
        <h5 class="vui-member-base-app-title">{{name}}</h5>
        <ul class="vui-member-base-app-list">
        <li v-for="(item, index) in basedata" :key="index">
            <a :href="item.url" v-if="item.status">• {{item.title}}</a>
        </li>
        </ul>
    </div>
    <Divider />
    <div class="vui-member-base-app">
        <h5 class="vui-member-base-app-title">商品</h5>
        <ul class="vui-member-base-app-list">
        <li v-for="(item, index) in shoppingCartData" :key="index" @click="handleGoods(item.url)">
            <a v-if="item.status">• {{item.title}}</a>
        </li>
        </ul>
    </div>
  </div>
</template>

<script>
export default {
	name:'baseApp',
	props: {
        name: String
    },
    data () {
        return {
            shoppingCartData: [
                {title: '发布商品', url: '/release-goods/step1' ,status: true},
                {title: '商品货架', url: '/goods/goods-shelf' ,status: true},
                {title: '正在出售', url: '/goods/on-sale' ,status: true},
                {title: '定价商品', url: '/goods/index' ,status: true},
                {title: '地址管理', url: '/address' ,status: true},
                {title: '我的购物车', url: '/shop-cart' ,status: true},
                {title: '订单管理', url: '/orderDetails/purchasedGoods' ,status: true}
            ],
            basedata: [],
            loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
        }
    },
    created () {
        this.$api.post('/member/bank/findPersonApp', {
            level : 3,
            account: this.loginUser.loginAccount
        }).then(response => {
            console.log('res123321', response)
            if (response.data) {
                response.data.forEach(e=>{
                    this.basedata.push({title: e.name, url: e.url, status: e.checked})
                })
            }
        }).catch(error =>{
            console.error(error)
        })
    },
    methods: {
        // 检查认证到了第几步
        checkAuth () {
            this.$api.post('/member-reversion/realStep/findEnableStep', {
                account: this.$user.loginAccount
            }).then(response => {
                console.log('check auth response', response)
                if (response.code === 200) {
                    if (response.data) {
                        // 说明已经进行了实名认证根据步骤跳转到相应的实名认证步骤 step为7说明实名认证已完成
                        if (response.data.step !== '7') {
                            this.$Notice.config({
                                top: 120,
                                duration: 10
                            })
                            this.$Notice.info({
                                title: '实名认证',
                                render: h => {
                                    return h('span', [
                                        '实名认证后才可发布商品，是否 ',
                                        h('a', {
                                            style: {
                                                color: '#00c587'
                                            },
                                            on: {
                                                click: () => {
                                                    if (response.data.step === '6') {
                                                        this.$router.push({
                                                            path: `/auth/step6`,
                                                            query: {
                                                                templateId: response.data.templateId
                                                            }
                                                        })
                                                    } else if (response.data.step === '6.4') {
                                                        this.$router.push({
                                                            path: `/auth/step7`,
                                                            query: {
                                                                templateId: response.data.templateId
                                                            }
                                                        })
                                                    } else {
                                                        let step = parseInt(response.data.step) + 1
                                                        this.$router.push({
                                                            path: `/auth/step${step}`,
                                                            query: {
                                                                templateId: response.data.templateId
                                                            }
                                                        })
                                                    }
                                                    this.$Notice.destroy()
                                                }
                                            }
                                        }, '继续认证 '), '？'
                                    ])
                                }
                            })
                        } else {
                            // 如果已经实名认证则跳转发布产品
                            this.$router.push('/release-goods/step1')
                        }
                    } else {
                        // 说明还未进行过实名认证
                        this.$Notice.config({
                            top: 120,
                            duration: 10
                        })
                        this.$Notice.info({
                            title: '实名认证',
                            render: h => {
                                return h('span', [
                                    '实名认证后才可发布商品，是否 ',
                                    h('a', {
                                        style: {
                                            color: '#00c587'
                                        },
                                        on: {
                                            click: () => {
                                                this.$router.push({
                                                    path: `/auth/step1`
                                                })
                                                this.$Notice.destroy()
                                            }
                                        }
                                    }, '继续认证 '), '？'
                                ])
                            }
                        })
                    }
                }
            })
        },
        handleGoods (url) {
            if (url === '/release-goods/step1') {
                this.checkAuth()
            } else {
                this.$router.push(`${url}`)
            }
        }
    }
}
</script>

<style lang="scss">
.vui-member-base-app{
  padding:0 10px;
  &-title{
    font-size: 16px ;
    padding:10px 0;
  }
  &-list{
    li {
        width: 110px;
        margin-left: 14px;
        margin: 5px 0px;
    }
    a {
        font-size: 14px;
        color: #333;
    }
  }
  &-fold{
    text-align: center;
    padding:10px 0;
  }
}
</style>
