<template>
    <div class="mt40">
        <ul class="goods-list mb20" v-if="serverData.length > 0">
            <li v-for="(item, index) in serverData" :key="index">
                <div @click="handleDetail(item)">
                    <img v-if="item.image_url" :src="item.image_url[0]" width="100%" height="216">
                    <img v-else src="../../../static/img/goods-list-no-picture1.png" width="100%" height="216">
                    <div class="pd5">
                        <p class="name ell mt5" :title="item.service_name">{{item.service_name}}&nbsp;</p>
                    </div>
                    <div class="pd5">
                        <p class="adddress ell" :title="item.address">{{item.address}}&nbsp;</p>
                    </div>
                    <div class="pd5">
                        <p class="adddress ell" :title="item.service_class_id">服务分类：{{item.service_class_id}}</p>
                    </div>
                    <div class="pd5">
                        <p class="adddress ell" :title="item.service_class_id">服务项目：加工服务</p>
                    </div>
                </div>
            </li>
        </ul>
        <div class="tc mt30 mb60" v-if="serverData.length > 0">
            <Button type="default" class="more-btn" @click="service()">更多</Button>
        </div>
        <div class="ma-polic-img" v-else>
            <img src="../../img/ma-img-002.png"> 
            <p style="margin-top: 10px;">暂无数据</p>
        </div>
    </div>
</template>
<script>
import serverItem from '../personGate/components/server-item'
export default {
    name: 'index-person-service-list',
    components: {
        serverItem
    },
    data () {
        return {
            serverData: [],
            loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
        }
    },
    created () {
        this.handleInit()
    },
    methods: {
        handleInit () {
            this.$api.post('/member/fishing/findProductServiceList', {
                account: '',
                service_name: '',
                pageNum: 1,
                pageSize: 10,
                type: '' // 0垂钓 1采摘 2景区 3餐饮 4住宿 空为全部
            }).then(response => {
                if (response.code === 200) {
                    this.serverData = response.data.list
                }
            })
        },
        service () {
            this.$router.push('/51index/serviceList/all')
        },
        // 到详情页
        handleDetail (item) {
            this.$router.push(`/InforMation/serviceDetail?id=${item.id}&uid=${item.account}&${item.type}`)
        }
    }
}
</script>
<style lang="scss" scoped>
.goods-list{
  li{
    height: 360px;
    position: relative;
    background: #fff;
    margin: 15px 10px 0 5px;
    display: inline-block;
    width: calc(100% / 5 - 15px);
    list-style: none;
    padding: 2px;
    border: 1px solid rgba(237,237,237,0.62);
    transition: box-shadow .2s cubic-bezier(.47,0,.745,.715);
    &:nth-child(5n){
      margin-right: 0;
    }
    .name {
        color: #4a4a4a;
        font-size: 16px;
    }
    .address {
        color: #9B9B9B;
        font-size: 12px;
    }
    &:hover{
      box-shadow: 0 0 0 2px #00c587;
    }
    a {
      display: block;
    }
  }
}
.more-btn {
    width: 198px;
    height: 32px;
}
</style>
