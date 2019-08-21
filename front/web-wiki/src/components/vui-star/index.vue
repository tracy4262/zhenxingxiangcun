<template>
  <div class="vui-star">
    <div class="vui-flex">
      <div class="vui-star-icon" @click="toggle" :style='{color:ColorValue}'>
        <Icon slot="icon" type="thumbsup" :class="AnimateClass" :size="20" style="vertical-align: top;"></Icon>
        <p class="vui-star-number">{{num | filterNumber}}</p>
      </div>
    </div>
    <div class="vui-star-animate" :class="{ 'vui-star-animate--active':active}"></div>
  </div>
</template>

<script>
import { isColors } from './colorRe.js'
import 'animate.css/animate.min.css'
export default {
  name: 'vui-star',
  props: {
    animate: {
      type: String,
      default: 'animated tada'
    },
    num: {
      type: Number,
      default: 0
    },
    color: String
  },
  filters: {
    filterNumber (d) {
      let v = d.toString()
      let n = ''
      if (d > 10000) n = `${v.slice(0, 1)}.${v.slice(1, 2)}万`
      if (d > 100000) n = `${v.slice(0, 2)}.${v.slice(2, 3)}万`
      if (d > 1000000) n = `${v.slice(0, 3)}.${v.slice(3, 4)}万`
      if (d < 10000) n = v
      return n
    }
  },
  methods: {
    toggle () {
      this.active = true
      this.toggleAnimate = true
      this.toggleColor = true
      setTimeout(() => {
        this.active = false
        this.toggleAnimate = false
      }, 350)
      this.$emit('on-click')
    }
  },
  data () {
    return {
      active: false,
      toggleAnimate: false,
      toggleColor: false
    }
  },
  computed: {
    AnimateClass () {
      return this.toggleAnimate ? this.animate : ''
    },
    ColorValue () {
      return this.toggleColor ? this.color : ''
    }
  },
  mounted () {
    if (this.color) {
      if (isColors(this.color)) {
        return false
      } else {
        console.error('this color must be hexcolor or rgbcolor  ---vui-star')
      }
    } else {
      return false
    }
  }
}
</script>

<style lang="scss">
 @import 'scss/style';
</style>
