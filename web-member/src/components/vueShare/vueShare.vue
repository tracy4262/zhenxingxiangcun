<template>
  <div class="social-share"></div>
</template>

<script>
import 'social-share.js/dist/js/social-share.min.js'
import './css/share.css'
export default {
  name: 'share',
  props: {
    config: {
      type: Object
    }
  },
  mounted() {
    window.socialShare(this.$el, this.config)
  }
}
</script>

