<template>
<div>
  <pdfshower
  :pdfurl="url"
  :scale="scale"
  @onErr="onErr"
  ></pdfshower>
</div>
</template>
<script>
import pdfshower from 'vue-pdf-shower'
export default {
  components: {
    pdfshower
  },
  props: {
      url: String
  },
  data: () => ({
    scale: 1
  }),

  methods: {
    onErr (err) {
        this.$Message.error('pdf加载失败，请重试!')
        this.$Message.error(err)
    }
  }
}
</script>
