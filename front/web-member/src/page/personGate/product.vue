<template>
  <div class="layouts">
    <list :pageClass="'country'"></list>
  </div>
</template>

<script>
import list from '../goods/index/components/list'
import { navStatus } from '~page/companyGate/mixins/commonMixins'
export default {
  mixins: [navStatus],
  components: {
    list
  },
  data () {
    return {
      index: 7
    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>

</style>
