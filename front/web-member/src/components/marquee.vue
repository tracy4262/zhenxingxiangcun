<template>
  <div class="vui-marquee">
      <ul class="vui-marquee-box" :style="{ top }">
          <li v-for="item in data">
            <template v-if="item.url">
              <router-link :to="item.url">{{item.title}}</router-link>
            </template>
            <template v-else>{{item.title}}</template>
          </li >  
      </ul>
  </div>
</template>

<script>
export default {
  props: {
    data:Array,
    time: {
      type: Number,
      default: 1000
    }
  },
  data () {
    return {
      activeIndex: 0
    }
  },

  computed: {
    top() {
      return - this.activeIndex * 30 + 'px';
    }
  },

  mounted() {
    setInterval(() => {
      if(this.activeIndex < this.data.length - 1) {
        this.activeIndex++
      } else {
        this.activeIndex = 0
      }
    }, this.time);
  }
}
</script>
<style lang="scss">
.vui-marquee {
  font-size: 14px;
  height: 30px;
  overflow: hidden;
  &-box{
    position: relative;
    transition: top 0.5s;

    li{
      line-height: 30px;
    }
  }
}
</style>
