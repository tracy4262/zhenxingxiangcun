<template>
  <router-link to="/shop-cart" class="cart-btn" v-if="show">
    <Icon type="ios-cart"></Icon>
    <p>我的购物车</p>
  </router-link>
</template>

<script>
export default {
  components: {

  },
  data () {
    return {
      show: true
    }
  },
  mounted() {
    // window.onscroll = () => {
    //   let scrollTop = document.body.scrollTop || document.documentElement.scrollTop
    //   if ( scrollTop > 300) {
    //     this.show = true
    //   } else {
    //     this.show = false
    //   }
    // }
  }
}
</script>

<style lang="scss" scoped>
.cart-btn{
  width: 30px;
  background: #00c587;
  position: fixed;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
  padding: 6px 10px;
  line-height: 1.2;
  color: #fff;
  border-radius: 2px 0 0 2px;
}
</style>
