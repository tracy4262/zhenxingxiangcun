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
                this.$router.push('/auth/comAuth/step4')
            },
             // 下一步
            handleClicNext () {                
                window.location.href=`${window.location.origin}/auth/comAuth/step6`
                // this.$router.push('/auth/comAuth/step6')
            },
            handleJump () {
                this.$router.push('/auth/comAuth/step6')
            },
        }
    }

</script>
<style lang="scss">
.company-auth-5{
    .ivu-card .ivu-card{
      overflow: hidden;
      .btn-toolbar{
        position: absolute;
        right: 20px;
        top: -60px;
        transition: top .3s
      }
      &:hover{
        .btn-toolbar{
          top: -10px;
        }
      }
    }
    .family-deatil {
        .card.ivu-card{
            overflow: visible;
            &:hover{
                .btn-toolbar{
                top: 0px;
                }
            }
        }
    }
}
</style>
