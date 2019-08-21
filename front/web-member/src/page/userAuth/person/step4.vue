<template>
  <appSet @on-back="handleClickBack" @on-next="handleClickNext" @on-over="handleClickOver"></appSet>
</template>
<script>
import appSet from '../components/app-set'
export default {
  components: {
    appSet
  },
  data: () => ({
  }),
  methods: {
    handleClickBack () {
      this.$router.push('/auth/personAuth/step3')
    },
    handleClickNext () {
      this.$router.push('/auth/personAuth/step5')
    },
    handleClickOver () {
      this.$router.push('/auth/personAuth/step5')
    }
  }
}
</script>
<style lang="scss">
</style>
