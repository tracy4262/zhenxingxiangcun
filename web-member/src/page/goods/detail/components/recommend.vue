<template>
  <div>
    <detail-title :name="shopName"></detail-title>
    <ul class="list">
      <li class="item" v-for="(item, index) in data" :key="index">
        <router-link :to="item.url">
          <div class="info">
            <p class="h6">{{item.name}}</p>
            <p class="mt5 t-orange">￥{{item.price}}</p>
          </div>
          <img :src="item.src" height="150">
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import detailTitle from './title'
export default {
  components: {
    detailTitle
  },
  data () {
    return {
      shopName: '商品推荐',
      data: [{
        name: '土昆山药',
        price: 20,
        url: '',
        src: 'http://192.168.7.42/dist/5396122cc993bfbfe9a9705a39b9cb7f.png'
      }, {
        name: '土昆山药',
        price: 20,
        url: '',
        src: 'http://192.168.7.42/dist/5396122cc993bfbfe9a9705a39b9cb7f.png'
      }]
    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
.list{
  .item{
    position: relative;
    border: 1px solid #efefef;
    border-top: none;
    overflow: hidden;
    a{
      color: #333;
      display: block
    }
    &:hover{
      img{
        transform: translateX(0)
      }
    }
    img{
      max-width: 100%;
      transform: translateX(20px);
      transition:transform .2s cubic-bezier(0.470,  0.000, 0.745, 0.715);
    }
  }
  .info{
    position: absolute;
    z-index:2;
    top: 20px;
    left: 20px;
  }
}
</style>
