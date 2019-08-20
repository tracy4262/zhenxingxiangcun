<template>
  <div>
    <div class="pd20">
      <Tabs v-model="active" @on-click="handleClick">
          <TabPane label="资质认证" name="certification" disabled></TabPane>
          <TabPane label="联系方式" name="concat" disabled></TabPane>
          <TabPane label="法人或个人身份" name="identity" disabled></TabPane>
          <TabPane label="法人或个人身份（管理员）" name="administrator" disabled></TabPane>
      </Tabs>
    </div>
    <div>
        <certification v-if="active === 'certification'" ref="certification"></certification>
        <concat v-if="active === 'concat'" ref="concat"></concat>
        <identity v-if="active === 'identity'" ref="identity"></identity>
        <administrator v-if="active === 'administrator'" ref="administrator"></administrator>
    </div>
    <div class="tc pt30 pb20">
      <Button class="mr20" @click="handleBack">返回上一步</Button>
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
      handleClick (e) {
        this.active = e
        if (e != 'certification') {
          this.$nextTick(() => {
            this.$refs[e].handleSelectData()
          })
        }
      },
      handleNext () {
        console.log('active', this.active)
        if (this.active === 'certification') {
          // 必须要有数据才能够下一步 后面步骤的数据是跟这一步的数据关联的
          this.$api.post('/member-reversion/user/realCertification/findMemberAptitude', {
            user_id: this.$user.loginAccount,
            templateId: this.templateId
          }).then(response => {
            if (response.code === 200) {
              if (response.data.length) {
                this.active = 'concat'
              } else {
                this.$Message.info('请先填写资质认证！')
              }
            }
          })
        } else if (this.active === 'concat') {
          this.$api.post('/member-reversion/user/realCertification/findMemberContact', {
            user_id: this.$user.loginAccount,
            templateId: this.templateId
          }).then(response => {
            if (response.code === 200) {
              if (response.data.length) {
                this.active = 'identity'
              } else {
                this.$Message.info('请先填写联系方式！')
              }
            }
          })
        } else if (this.active === 'identity') {
          this.$api.post('/member-reversion/user/realCertification/findMemberIdentity', {
            user_id: this.$user.loginAccount,
            templateId: this.templateId
          }).then(response => {
            if (response.code === 200) {
              if (response.data.length) {
                this.active = 'administrator'
              } else {
                this.$Message.info('请先填写法人或个人身份！')
              }
            }
          })
        } else if (this.active === 'administrator') {
          this.$api.post('/member-reversion/user/realCertification/findMemberAdministrator', {
            user_id: this.$user.loginAccount,
            templateId: this.templateId
          }).then(response => {
            if (response.code === 200) {
              if (response.data.length) {
                this.$router.push({
                  path: '/auth/step7',
                  query: {
                    templateId: this.$route.query.templateId
                  }
                })
              } else {
                this.$Message.info('请先填写法人或个人身份（管理员）！')
              }
            }
          })
        }
      },
      handleBack () {
        if (this.active === 'certification') {
          this.$router.push({
            path: '/auth/step5',
            query: {
              templateId: this.$route.query.templateId
            }
          })
        } else if (this.active === 'concat') {
          this.active = 'certification'
        } else if (this.active === 'identity') {
          this.active = 'concat'
        } else if (this.active === 'administrator') {
          this.active = 'identity'
        }
      }
    }
  }
</script>
<style>

</style>
