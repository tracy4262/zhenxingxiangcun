<template>
<div class="vui-paper" :class="paperClass" :style="paperStyle">
  <slot></slot>
</div>
</template>

<script>
export default {
  name: 'vui-paper',
  props: {
    circle: Boolean,
    round: Boolean,
    size: String,
    small: Boolean,
    min: Boolean,
    max: Boolean,
    level: {
      type: Number,
      default: 1
    }
  },
  computed: {
    paperClass () {
      var arr = []
      if (this.circle) arr.push('vui-paper-circle')
      if (this.round) arr.push('vui-paper-round')
      arr.push('vui-paper-' + this.level)
      return arr
    },
    paperStyle () {
      return {
        'width': this.size,
        'height': this.size
      }
    }
  }
}
</script>

<style lang="scss">
.vui-paper {
  &-round {
    border-radius: 10px;
  }

  &-circle {
    border-radius: 50%;
  }

  &-1 {
    box-shadow: rgba(0, 0, 0, 0.117647) 0px 1px 6px, rgba(0, 0, 0, 0.117647) 0px 1px 4px;
  }

  &-2 {
    box-shadow: rgba(0, 0, 0, 0.156863) 0px 3px 10px, rgba(0, 0, 0, 0.227451) 0px 3px 10px;
  }

  &-3 {
    box-shadow: rgba(0, 0, 0, 0.188235) 0px 10px 30px, rgba(0, 0, 0, 0.227451) 0px 6px 10px;
  }

  &-4 {
    box-shadow: rgba(0, 0, 0, 0.247059) 0px 14px 45px, rgba(0, 0, 0, 0.219608) 0px 10px 18px;
  }

  &-5 {
    box-shadow: rgba(0, 0, 0, 0.298039) 0px 19px 60px, rgba(0, 0, 0, 0.219608) 0px 15px 20px;
  }
}

</style>
