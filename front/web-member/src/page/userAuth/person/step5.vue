<template>
  <div>
      <identification-step5 @on-next="handleClicNext" @on-jump="handleJump" @on-back="handleClickBack"></identification-step5>
  </div>
</template>
<script>
    import identificationStep5 from '../components/identificationStep5'
    export default{
        components: {
            identificationStep5
        },
        data(){
            return{
            }
        },
		created() {
		},
        methods:{
            //上一步
            handleClickBack(){
                this.$router.push('/auth/personAuth/step4')
            },
             // 下一步
            handleClicNext () {
                window.location.href=`${window.location.origin}/auth/personAuth/step6`
                // this.$router.push('/auth/personAuth/step6')
            },
            handleJump () {
                this.$router.push('/auth/personAuth/step6')
            },
        }
    }

</script>
