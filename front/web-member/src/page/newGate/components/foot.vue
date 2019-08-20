<template>
    <div class="new-portal-footer tc">
        Copyright © 湖北省农科院农业经济研究所
    </div>
</template>
<script>
export default {
    props:{
        data: Object,
        default() {
            return {
                summary: ''
            }
        }

    },
    components:{
    },
    data () {
        return {
        }
    },
    created(){
    }
}
</script>
<style lang="scss">
.new-portal-footer{
    background: #29191A;
    line-height: 91px;
    color: #A09899;
    font-size: 16px;
    min-width: 1200px;
}
</style>
