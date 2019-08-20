<template>
    <div>
      <!-- 相关服务 -->
        <div style="background-color: #fafafa; padding-top: 1px; padding-bottom: 1px;">
            <Row type="flex" align="middle">
                <Col span="16"><Title title="相关服务" class="ml10"></Title></Col>
                <Col span="8" class="tr">
                    <a @click="goRelatedService" class="new-title-16 mr10">查看更多</a>
                </Col>
            </Row>
        </div>
        <Row :gutter="8" class="pt15">
            <Col span="8" v-for="(item, index) in data" class="tc"  @click.native="detail(item)">
             <!-- 咨询服务 -->
            <div v-if="item.type === '5'">
                <img v-if="item.personalPicture" :src="item.personalPicture" style="width: 100%; height: 70px; cursor: pointer;">
                <img v-else src="../../../../../static/img/goods-list-no-picture1.png" style="cursor: pointer; width: 100%; height: 70px;" />
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
                <img v-if="item.image_url && item.image_url[0]" :src="item.image_url[0]" style="width: 100%; height: 70px; cursor: pointer;" />
                <img v-else src="../../../../../static/img/goods-list-no-picture1.png" style="cursor: pointer; width: 100%; height: 70px;" />
                <p style="font-size: 12px; color: #4A4A4A; line-height: 14px;" class="mt10 ell" :title="item.service_name">{{ item.service_name }}</p>
                <Row class="mt5 ell t-orange" style="font-size: 12px;" span="24" v-if="item.type === '0'" :title="`${item.timeCharging ? '按垂钓时间收费' : ''}${item.timeVariety ? '按垂钓品种收费' : ''}`">
                    <span v-if="item.timeCharging">按垂钓时间收费</span>
                    <span v-if="item.timeVariety">按垂钓品种收费</span>
                </Row>
                <Row class="mt5 ell t-orange" style="font-size: 12px;" span="24" v-else-if="item.type === '1'" :title="`${item.timeVariety ? '按采摘品种收费' : ''}`">
                    <span v-if="item.timeVariety">按采摘品种收费</span>
                </Row>
                <Row class="mt5 t-orange ell" span="24" style="font-size: 12px;" v-else>
                    <span v-if="item.price">
                        <span>{{item.price ? parseFloat(item.price).toFixed(2) : parseFloat(0).toFixed(2)}}</span> 元起
                    </span>
                    <span v-else>
                        暂无价格
                    </span>
                </Row>
            </div>
            </Col>
        </Row>
    </div>
</template>
<script>
import Title from '../../components/title'
export default {
    components: {
      Title
    },
    props: {
        item: {
            type: Object
        }
    },
    data () {
      return {
        data: []
      }
    },
    created() {
      this.getServiceList()
    },
    methods: {
        goRelatedService () {
            let url = `/51index/serviceList/all`
            window.open(url, "_blank")
        },
        getServiceList () {
            this.$api.post('/member/fishing/findProductServiceList', {
              isToPage: 0,
              pageNum: 1,
              pageSize: 3,
              isHomeplay: '0'
            }).then(res => {
                console.log('resservice', res)
                if (res.code == 200 && res.data) {
                    this.data = res.data.dataList
                }
            })
        },
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
<style lang="scss" scoped>
.new-title-16{
    color: #4A4A4A;
    font-size: 12px;
    &:hover{
        color: #00c587;
    }
}
</style>

