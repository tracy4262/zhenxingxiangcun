<template>
  <div>
    <ul class="service-list">
      <li v-for="(item, index) in dataList" :key="index" :class="column === 5 ? 'li-5' : 'li-4'">
       <productList :item="item"></productList>
      </li>
    </ul>
        <div v-if="dataList.length == 0">
          <p class="tc pd30">暂无数据</p>
        </div>
  </div>
</template>
<script>
import productList from '../ruralGate/components/product-item'
export default {
  components: {
    productList
  },
    props: {
        dataList: {
            type:Array
        },
        small:{
            type:Boolean,
            default:false
        },
        column: {
          type: Number, // 默认 5
          default: 5 
        }
    },
  data () {
    return {
    }
  },
  methods: {
    handleDetail (item) {
      // this.$router.push(`/goods/newDetail?id=f${item.id}&uid=${item.account}`)
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
    // height: 360px;
    position: relative;
    background: #fff;
    display: inline-block;
    list-style: none;
    box-shadow: 2px 5px 14px 0px rgba(0, 0, 0, 0.1);
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

