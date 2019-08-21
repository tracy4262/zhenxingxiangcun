<template>
  <column-set  @on-next="handleNext" @on-jump="handleJump" @on-back="handleClickBack"></column-set>
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
    //下一步
    handleNext(){
      this.$router.push('/auth/ruralAuth/step3')
    },
    // 上一步
    handleClickBack(){
      this.$router.push('/auth/ruralAuth/step1')
    },
    // 下一步
    handleJump(){
      this.$router.push('/auth/ruralAuth/step3')
    }
  }
}
</script>
<style lang="scss">
</style>
