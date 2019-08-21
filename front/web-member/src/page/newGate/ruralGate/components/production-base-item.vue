<template>
    <div>
        <Card :padding="10" style="min-height: 290px; cursor: pointer;" @click.native="detail(item)">
            <img v-if="item.imageUrl" :src="item.imageUrl" style="width: 100%; height: 170px;" />
            <img v-else src="../../../../../static/img/goods-list-no-picture1.png" style="width: 100%; height: 170px;" />
            <p style="font-size: 16px; color: #4A4A4A; line-height: 14px;" class="mt20 ell" :title="item.productionBaseName">{{ item.productionBaseName }}</p>
            <p style="font-size: 12px; color: #9B9B9B; line-height: 19px;" class="mt20 ell-2" :title="item.introduction">{{ item.introduction }}</p>
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
            let url = `/member/productionBaseDetail?id=${item.id}&account=${item.account}`
            window.open(url, '_blank')
        }
    }
}
</script>
