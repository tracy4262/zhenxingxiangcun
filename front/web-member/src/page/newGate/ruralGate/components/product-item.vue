<template>
    <div>
        <Card :padding="10" style="min-height: 290px; cursor: pointer;" @click.native="detail(item)">
            <img v-if="item.notarizationCertificate" :src="item.notarizationCertificate[0]" style="width: 100%; height: 110px;">
            <img v-else src="../../../../../static/img/goods-list-no-picture1.png" style="width: 100%; height: 110px;" />
            <div style="height:35px; line-height:35px;" class="mt20 t-orange">
                <div v-if="item.salesWay === '竞价销售'" class="ell" :title="item.startPrice"><span>起拍价：</span>￥<span style="font-size: 20px;">{{ item.startPrice }}</span></div>
                <div v-if="item.salesWay === '预售'" class="ell"><span>预售价：</span>￥<span style="font-size: 20px;">{{ item.orderPrice }}</span></div>
                <div v-if="item.salesWay === '定价销售'" class="ell"><span>时价：</span>￥<span style="font-size: 20px;">{{ item.discountPrice === '' ? item.currentPrice : item.discountPrice }}</span></div>
                <div v-if="item.salesWay === '团购销售'" class="ell"><span>时价：</span>￥<span style="font-size: 20px;">{{ item.groupBuyingPrice === '' ? item.originalPrice : item.groupBuyingPrice }}</span></div>
                <div v-if="item.salesWay === '面议'" class="ell"><span>价格：</span>￥<span style="font-size: 20px;">面议</span></div>
            </div>
            <p style="font-size: 16px; color: #4A4A4A; line-height: 14px;" class="mt20 ell" :title="item.commodityName">{{ item.commodityName }}</p>
            <p style="font-size: 12px; color: #9B9B9B; line-height: 19px;" class="mt20 ell" :title="item.productLocation">{{ item.productLocation }}</p>
            <p style="font-size: 12px; color: #9B9B9B; line-height: 17px;text-decoration:underline;color:#b1b1b1;" class="mt20 ell" :title="item.name">{{ item.name }}</p>
        </Card>
    </div>
</template>
<script>
export default {
    props: {
        item: {
            type: Object
        }
    },
    methods: {
        detail (item) {
            let url = `/goods/newDetail?id=${item.id}&account=${item.account}`
            window.open(url, '_blank')
        }
    }
}
</script>
