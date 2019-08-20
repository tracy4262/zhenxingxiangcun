<template>
  <div>
    <individuation @on-back="handleClickBack" @on-next="handleClickNext" @on-over="handleClickOver"></individuation>
  </div>
</template>
<script>
import individuation from '../components/individuation'
export default {
  components: {
    individuation
  },
  methods:{
    handleClickBack () {
      this.$router.push('/auth/comAuth/step2')
    },
    handleClickNext () {
      this.$router.push('/auth/comAuth/step4')
    },
    handleClickOver () {
      this.$router.push('/auth/comAuth/step4')
    }
  }
}
</script>
