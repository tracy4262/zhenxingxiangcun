<template>
  <div>
    <vui-affix-tabs class="pt30" :data="tabsData">
      <Card class="company-auth-5" >
      </Card>
    </vui-affix-tabs>
  </div>
</template>
<script>
  import Title from '../../userAuth/components/title'
  import vuiAffixTabs from './vui-affix-tabs'
  export default {
    components: {
      Title,
      vuiAffixTabs
    },
    data () {
      return {
        tabsData: [],
        templateId: '',
        account: ""
      }
    },
    created() {
      this.account = this.$user.loginAccount;
      this.queryConfig();
    },
    methods: {
      // 查询用户角色配置的表单信息
      queryConfig() {
        this.$api.post('/member-reversion/user/userTemplateManage/find', {
              account: this.$user.loginAccount
          }).then(response => {
              if (response.code === 200) {
                  if (response.data.userTemplate) {
                      this.templateId = response.data.userTemplate.templateId
                      this.initFileTags()
                  }
              }
          }).catch(error => {
              this.$Message.error('服务器异常！')
          })
      },
      // 初始化应用标签信息
      initFileTags () {
        this.$api.post('/member-reversion/user/perfect/findModuleInfo', {
          account: this.$user.loginAccount,
          level: '0',
          templateId: this.templateId
        }).then(response => {
          console.log('tags response', response)
          if (response.code === 200) {
            this.tabsData = []
            this.tabsData = response.data
            // response.data.forEach(element => {
            //   this.tabsData.push({
            //     id: element.appId,
            //     name: element.appName,
            //     mode: element.url
            //   })
            // })
          }
        }).catch(error => {
          this.$Message.error('服务器异常！')
        })
      }
    }
  }
</script>
<style lang="scss">

</style>

