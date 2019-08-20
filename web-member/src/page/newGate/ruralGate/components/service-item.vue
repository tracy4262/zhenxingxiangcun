<template>
    <div>
        <Card :padding="10" style="min-height: 290px; cursor: pointer;" @click.native="detail(item)">
            <!-- 咨询服务 -->
            <div v-if="item.type === '5'">
                <img :src="item.personalPicture" style="width: 100%; height: 170px; cursor: pointer;">
                <p style="font-size: 16px; color: #4A4A4A; line-height: 14px;" class="ell mt20" :title="item.expertName">{{item.expertName}}&nbsp;</p>
                <Row type="flex" align="middle" class="mt10">
                    <Col span="24" class="ell" :title="item.adeptSpecies">
                        擅长物种：{{ item.adeptSpecies }}
                    </Col>
                </Row>
                <Row type="flex" align="middle" class="mt10">
                    <Col span="24" class="ell" :title="item.adeptField">
                        擅长领域：{{ item.adeptField }}
                    </Col>
                </Row>
            </div>
            <!-- 其他服务 -->
            <div v-else>
                <img v-if="item.image_url" :src="item.image_url[0]" style="width: 100%; height: 170px; cursor: pointer;" />
                <img v-else src="../../../../../static/img/goods-list-no-picture1.png" style="cursor: pointer; width: 100%; height: 170px;" />
                <p style="font-size: 16px; color: #4A4A4A; line-height: 14px;" class="mt20 ell" :title="item.service_name">{{ item.service_name }}</p>
                <Row class="mt10 ell" span="24" v-if="item.type === '0'" :title="`${item.timeCharging ? '按垂钓时间收费' : ''}${item.timeVariety ? '按垂钓品种收费' : ''}`">
                    <span v-if="item.timeCharging">按垂钓时间收费</span>
                    <span v-if="item.timeVariety">按垂钓品种收费</span>
                </Row>
                <Row class="mt10 ell" span="24" v-else-if="item.type === '1'" :title="`${item.timeVariety ? '按采摘品种收费' : ''}`">
                    <span v-if="item.timeVariety">按采摘品种收费</span>
                </Row>
                <Row class="mt10" span="24" v-else>
                    <span v-if="item.price">
                        <span>{{item.price ? parseFloat(item.price).toFixed(2) : parseFloat(0).toFixed(2)}}</span> 元起
                    </span>
                    <span v-else>
                        暂无价格
                    </span>
                </Row>
                <p class="ell mt10" v-if="item.contact && item.contact.length" :title="item.contact[0].detailAddress">{{item.contact[0].detailAddress}}</p>
            </div>
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
            let url = ''
            if (item.type === '5') {
                url = `/consultationService/detail?id=${item.id}`
            } else {
                url = `/InforMation/serviceDetail?id=${item.id}&uid=${item.account}&type=${item.type}`
                
            }
            window.open(url, '_blank')
        }
    }
}
</script>
