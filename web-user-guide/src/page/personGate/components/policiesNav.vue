<template>
    <div class="layouts">
        <div class="tc t-white mb50 hidden">
            <h5 class="mt30">政策</h5>
            <p class="mt10">Laws</p>
        </div>
        <Row class="laws-list compaIndex-list">
            <template v-for="item in lawsData.lawsDataBefor">
                <Col span="6">
                    <img :src="item.src" height="236" width="100%">
                </Col>
                <Col span="6">
                    <Card :padding="30" class="tc">
                        <h4 class="ell">{{item.title}}</h4>
                        <p class="info t-grey mt30 mb30 ell-3">{{item.detail}}</p>
                        <Button  v-if="item.columnType === '图书'"  type="default" class="per-btn-theme" @click="bookClick(item.informationDetailId,item.id)">了解详情</Button>
                        <Button v-else type="default" class="per-btn-theme" @click="lawsGo(item.informationDetailId)">了解详情</Button>
                    </Card>
                </Col>
            </template>
        </Row>
        <Row class="laws-list pb50 compaIndex-list">
            <template v-for="item in lawsData.lawsDataAfter">
                <Col span="6">
                    <Card :padding="30" class="tc">
                        <h4 class="ell">{{item.title}}</h4>
                        <p class="info t-grey mt30 mb30 ell-3">{{item.detail}}</p>
                        <Button  v-if="item.columnType === '图书'"  type="default" class="per-btn-theme" @click="bookClick(item.informationDetailId,item.id)">了解详情</Button>
                        <Button v-else type="default" class="per-btn-theme" @click="lawsGo(item.informationDetailId)">了解详情</Button>
                    </Card>
                </Col>
                <Col span="6">
                    <img :src="item.src" height="236" width="100%">
                </Col>
            </template>
        </Row>
    </div>
</template>
<script>
export default {
    props: {
        lawsData: {
            type: Object
        }
    },
    data() {
        return {
        }
    },
    created(){
    },
    methods:{
        lawsGo(id){
            this.$emit('lawsGo',id)
        },
        bookClick(informationDetailId,id){
            this.$router.push({
                path: '/InforMation/bookBlurb',
                query: {
                    id: id,
                    informationDetailId:informationDetailId,
                    book_type:'policy'
                }
            })
        }
    }
}
</script>

<style lang="scss">
</style>
