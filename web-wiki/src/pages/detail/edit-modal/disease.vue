<template>
  <div>
    <disease-pest ref="disease" title="新增病害" label="病害名称" :picData="speciesDisease" :classType="classType" @success="ok" @on-changePage="handleChange"></disease-pest>
  </div>
</template>
<script>
import diseasePest from '../components/disease-pest'
export default {
  components: {
    diseasePest
  },
  props: {
    classType: {
      type: String
    }
  },
  data: () => ({
    speciesDisease: {
      current: 1,
      total: 0,
      data: [],
      pageSize: 11
    },
    speciesid: '',
    loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
    account: ''
  }),
  created () {
    this.account = this.loginUser.loginAccount
    this.speciesid = this.$route.query.speciesid
    this.handleSpeciesDisease(1)
  },
  methods: {
    handleChange (e) {
      this.speciesDisease.current = e
      this.handleSpeciesDisease(e)
    },
    // 常见病害
    handleSpeciesDisease (e) {
      this.$api.post('wiki/api/wiki/listSpeciesDisease', {speciesid: this.speciesid, pageSize: this.speciesDisease.pageSize, pageNum: e}).then(response => {
        if (response.code === 200) {
          this.speciesDisease.current = e
          this.speciesDisease.data = response.data
          this.speciesDisease.total = response.total
        }
      })
    },
    ok (info) {
      let list = Object.assign({speciesid: this.speciesid, auditstatus: 2, fcreatorid: this.account}, info)
      this.$api.post('wiki/api/wiki/saveSpeciesDisease', list).then(response => {
        if (response.code === 200) {
          this.$Message.success({
            content: '新增成功！请等待审核，审核通过后数据将会更新。',
            duration: 3
          })
          this.handleSpeciesDisease(this.speciesDisease.current)
        }
      })
      this.$refs.disease.modal = false
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
