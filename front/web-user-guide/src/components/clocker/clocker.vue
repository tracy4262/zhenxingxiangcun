<template>
  <div style="display:inline-block;">
    <span v-if="showTimeString">{{timeString}}</span>
    <div class="vui-clocker-tpl"><slot></slot></div>
  </div>
</template>

<script>
import Clocker from './countdown'
export default {
  name: 'vui-clocker',
  props: {
    time: [String, Number],
    format: {
      type: String,
      default: '%D 天 %H 小时 %M 分 %S 秒'
    }
  },
  data () {
    return {
      showTimeString: true,
      timeString: '',
      slotString: '',
      cacheSlotString: ''
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.slot = this.$el.querySelector('.vui-clocker-tpl')
      this.slotString = this.slot.innerHTML
      if (this.slotString !== '') {
        this.showTimeString = false
      }
      this.render()
    })
  },
  methods: {
    render () {
      if (!this.time) return
      this.clocker = new Clocker(this.time)
      .on('tick', event => {
        this.update(event)
        this.$emit('tick', event)
      })
      .on('finish', () => {
        this.timeString = '00:00:00'
        this.$emit('finish')
      })
      .start()
    },
    update (event) {
      if (this.showTimeString) {
        this.timeString = event.strftime(this.format)
      } else {
        let string = event.strftime(this.slotString)
        if (string !== this.cacheSlotString) {
          this.slot.innerHTML = this.cacheSlotString = string
        }
      }
    }
  },
  watch: {
    time () {
      this.clocker.remove()
      this.render()
    }
  },
  beforeDestroy () {
    this.clocker.remove()
    this.clocker = null
  }
}
</script>
