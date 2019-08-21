<template>
  <span>{{currentTime}} {{title}}</span>
</template>

<script>
export default {
  props: {
    value: Number,
    title: String,
    start: {
      type: Boolean,
      default: false
    }
  },
  created () {
    this.currentTime = this.time
    if (this.value) this.currentTime = this.value
  },
  methods: {
    tick () {
      let _this = this
      this.interval = setInterval(function () {
        if (_this.currentTime > 0) {
          _this.currentTime--
        } else {
          _this.stop()
          _this.index++
          _this.$emit('finish', _this.index)
        }
      }, 1000)
    },
    stop () {
      clearInterval(this.interval)
    }
  },
  watch: {
    value (val) {
      this.currentTime = val
    },
    currentTime (val) {
      this.$emit('input', val)
    },
    start (newVal, oldVal) {
      if (newVal === true && oldVal === false && this.currentTime > 0) {
        this.tick()
      }
      if (newVal === false && oldVal === true) {
        this.stop()
      }
    }
  },
  mounted () {
    if (this.start) {
      this.tick()
    }
  },
  data () {
    return {
      interval: null,
      index: 0,
      currentTime: 60
    }
  }
}
</script>
