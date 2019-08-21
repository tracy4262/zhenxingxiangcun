<template>
  <div>
    <ul class="service-list">
      <li v-for="(item, index) in dataList" :key="index" :class="column === 5 ? 'li-5' : 'li-4'">
        <serviceItem :item="item" @click.native="handleDetail(item)"></serviceItem>
        <!-- <img v-if="item.image_url && item.image_url.length" :src="item.image_url[0]" alt="" height="159px" width="100%">
        <img v-else src="../../../../static/img/goods-list-no-picture1.png" alt="" height="159px" width="100%">
        <div class="pd10">
          <p class="name ell" :title="item.service_name">{{item.service_name}}</p>
          <p class="ell" v-if="item.contact && item.contact.length" :title="item.contact[0].detailAddress"><Icon type="md-pin" />{{item.contact[0].detailAddress}}</p>
        </div> -->
      </li>
    </ul>
        <div v-if="dataList.length == 0">
          <p class="tc pd30">暂无数据</p>
        </div>
  </div>
</template>
<script>
import serviceItem from '../ruralGate/components/service-item'
export default {
  props: {
      dataList: {
          type:Array
      },
      small:{
          typr:Boolean,
          default:false
      },
      column: {
        type: Number, // 默认 5
        default: 5 
      }
  },
  components: {
    serviceItem
  },
  data () {
    return {

    }
  },
  methods: {
    handleDetail (item) {
      // /consultationService/detail?id=${item.id}
      if (item.type == '5') {
        this.$router.push(`/consultationService/detail?id=${item.id}`)
      } else {
        this.$router.push(`/InforMation/serviceDetail?id=${item.id}&uid=${item.account}&type=${item.type}`)
      }
    }
  },
}
</script>
<style lang="scss" scoped>
.service-list{
  .li-5{
    margin: 25px 10px 0 10px;
    width: calc(100% / 5 - 20px);
  }
  .li-4{
    margin: 25px 10px 0 10px;
    width: calc(100% / 4 - 20px);
  }
  li{
    // height: 260px;
    position: relative;
    background: #fff;
    display: inline-block;
    list-style: none;
    box-shadow: 2px 5px 14px 0px rgba(0, 0, 0, 0.1);
    vertical-align: middle;
    &:hover{
      box-shadow: 0px 0px 0px 2px rgba(0,197,135,1);
    }
    p{
      height: 34px;
      line-height: 34px;
    }
    .name{
      font-size: 16px;
    }
  }
}
</style>

