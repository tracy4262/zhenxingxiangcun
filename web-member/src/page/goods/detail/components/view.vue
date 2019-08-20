<template>
  <div>
    <vue-product-zoomer
    ref="zoomer"></vue-product-zoomer>
  </div>
</template>

<script>
import vueProductZoomer from '~components/vui-product-zoomer'
export default {
  components: {
    vueProductZoomer
  },
  data () {
    return {
      images: []
    }
  },
  methods: {
    getData (e) {
      if (e[0] && e[0].url) {
        this.images = e
        this.$refs['zoomer'].createds(e)
        console.log('imagesimagesimagesimages', this.images)
      } else {
        this.images = [{id: 1, url: '../../../static/img/goods-list-no-picture1.png'}]
        this.$refs['zoomer'].createds(this.images)
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
