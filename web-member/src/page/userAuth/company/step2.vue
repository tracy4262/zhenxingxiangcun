<template>
  <column-set @on-next="handleNext" @on-jump="handlJump" @on-back="handleBack"></column-set>
</template>
<script>
import columnSet from '../components/column-set'
export default {
  components:{
    columnSet
  },
  data: () => ({
  }),
  methods:{
    handleNext () {
      this.$router.push('/auth/comAuth/step3')
    },
    handlJump () {
      this.$router.push('/auth/comAuth/step3')
    },
    handleBack () {
      this.$router.push('/auth/comAuth/step1')
    }
  }
}
</script>
<style lang="scss">
</style>
