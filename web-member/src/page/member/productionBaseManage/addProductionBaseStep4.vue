<template>
    <div class="warp">
        <Icon type="ios-checkmark" size="100" class="mt-5"></Icon>
        <p class="mt-5">已为您建立生产基地信息档案，请点击完成按钮进入生产基地管理界面。</p>
        <div class="mt-5">
            <Button type="primary" class="btn" @click="complete">完成</Button>
        </div>
    </div>
</template>

<script>
    export default {
        components:{
        },
        data() {
            return {
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            }
        },
        methods: {
            complete () {
                this.$router.push({
                    path: '/member/productionBaseList',
                    query: {
                        uid: this.loginuserinfo.loginAccount
                    }
                })
            }
        }
    }
</script>
<style scoped>
    .warp {
        text-align: center;
    }
    .mt-5 {
        margin-top: 25px;
    }
    .btn {
        width: 100px;
    }
</style>