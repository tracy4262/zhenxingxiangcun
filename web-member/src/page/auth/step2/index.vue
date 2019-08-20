<template>
  <website :userType="3" @on-back="handleClickBack" @on-next="handleClickNext"></website>
</template>
<script>
import website from './components/website'
export default {
  components: {
    website
  },
  data: () => ({
  }),
  methods: {
    handleClickBack (templateId) {
      this.$router.push({
        path: '/auth/step1',
        query: {
          templateId: templateId
        }
      })
    },
    handleClickNext (templateId) {
      this.$router.push({
        path: '/auth/step3',
        query: {
          templateId: templateId
        }
      })
    }
  }
}
</script>
<style lang="scss">
</style>
