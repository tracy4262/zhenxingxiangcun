<template>
  <wrapper :data="tabsData"></wrapper>
</template>
<script>
import wrapper from "./components/wrapper";
export default {
  components: {
    wrapper
  },
  data: () => ({
    tabsData: [],
    loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
    account: ""
  }),
  created() {
    this.account = this.loginUser.loginAccount;
    this.queryConfig();
  },
  methods: {
    // 查询用户角色配置的表单信息
    queryConfig() {
      this.$api
        .post("/member/perfectInfo/findSysUserInfo", {
          account: this.account
        })
        .then(response => {
          if (response.code === 200) {
            var data = response.data.tabsData;
            this.tabsData = data;
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>
<style lang="scss">
.layouts {
  width: 1200px;
  margin: 0 auto;
}
</style>
