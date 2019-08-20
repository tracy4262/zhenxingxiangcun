<template>
  <choose-template @on-next="handleClickNext"></choose-template>
</template>
<script>
import chooseTemplate from './components/chooseTemplate'
export default {
  components: {
    chooseTemplate
  },
  data: () => ({
  }),
  methods: {
    handleClickNext (templateId) {
        this.$router.push({
            path: '/auth/step2',
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
