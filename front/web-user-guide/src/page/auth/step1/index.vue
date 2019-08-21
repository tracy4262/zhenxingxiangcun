<template>
    <div class="layouts">
        <template-components ref="template"></template-components>
    </div>
</template>
<script>
import Title from '../components/title'
import templateComponents from './components/template'
export default {
  components: {
    Title,
    templateComponents,
  },
  data: () => ({
    account: ''
  }),
  created() {
    this.account = this.$user.loginAccount
  },
  methods: {
    // 点击下一步保存
    handleNext () {
      this.$refs['template'].onSave()
    },
  }
}
</script>
<style lang="scss">
.new-authlayout {
  width: 1000px;
  margin: auto;
  margin-top: 20px;
}
.layouts {
  .template-name{
    color: #4b4b4b;
    font-size: 18px;
    padding-bottom: 15px;
    font-weight: 700;
  }
}
</style>
