<template>
  <div>
    <Card>
      <Tabs v-model="active" @on-click="handleClick">
          <TabPane label="资质认证" name="certification" disabled></TabPane>
          <TabPane label="联系方式" name="concat" disabled></TabPane>
          <TabPane label="法人或个人身份" name="identity" disabled></TabPane>
          <TabPane label="法人或个人身份（管理员）" name="administrator" disabled></TabPane>
      </Tabs>
      <div>
          <certification :account="account" v-if="active === 'certification'" ref="certification" @next="next1"></certification>
          <concat :account="account" v-if="active === 'concat'" ref="concat" @next="next2" @last="last2"></concat>
          <identity :account="account" v-if="active === 'identity'" ref="identity" @next="next3" @last="last3"></identity>
          <administrator :account="account" v-if="active === 'administrator'" ref="administrator" @last="last4"></administrator>
      </div>
    </Card>
    <div class="tc pt30 pb20">
      <Button type="primary" @click="handleNext">保存并下一步</Button>
    </div>
  </div>
</template>
<script>
import certification from './certification'
import concat from './concat'
import identity from './identity'
import administrator from './administrator'
  export default {
    components: {
      certification,
      concat,
      identity,
      administrator
    },
    props: {
      account: String
    },
    data () {
      return {
        active: 'certification',
        templateId: ''
      }
    },
    created () {
      this.templateId = this.$route.query.templateId
    },
    methods: {
      next1 () {
        this.active = 'concat'
      },
      next2 () {
        this.active = 'identity'
      },
      next3 () {
        this.active = 'administrator'
      },
      last2 () {
        this.active = 'certification'
      },
      last3 () {
        this.active = 'concat'
      },
      last4 () {
        this.active = 'identity'
      },
      handleClick (e) {
        this.active = e
        if (e != 'certification') {
          this.$nextTick(() => {
            this.$refs[e].handleSelectData()
          })
        }
      },
      handleNext () {
        this.$api.post('/member-reversion/user/realCertification/findMemberAdministrator', {
          user_id: this.account,
          isProxy: 1
        }).then(response => {
          if (response.code === 200) {
            if (response.data.length) {
              this.$emit('next')
            } else {
              this.$Message.info('请先完善实名信息！')
            }
          }
        })
      }
    }
  }
</script>
<style>

</style>
