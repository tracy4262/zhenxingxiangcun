<template>
  <div>
    <pests-pest ref="pest" title="新增虫害" label="虫害名称" :picData="speciesPest" @success="ok" @on-changePage="handleChange"></pests-pest>
  </div>
</template>
<script>
import pestsPest from '../components/pests-pest'
export default {
  components: {
    pestsPest
  },
  data: () => ({
    speciesPest: {
      data: [],
      total: 0,
      current: 1,
      pageSize: 11
    },
    speciesid: '',
    loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
    account: ''
  }),
  created () {
    this.account = this.loginUser.loginAccount
    this.speciesid = this.$route.query.speciesid
    this.handleSpeciesPest(1)
  },
  methods: {
    handleChange (e) {
      this.speciesPest.current = e
      this.handleSpeciesPest(e)
    },
    // 常见病害
    handleSpeciesPest (e) {
      this.$api.post('wiki/api/wiki/listSpeciesPest', {speciesid: this.speciesid, pageSize: this.speciesPest.pageSize, pageNum: e}).then(response => {
        if (response.code === 200) {
          this.speciesPest.current = e
          this.speciesPest.data = response.data
          this.speciesPest.total = response.total
        }
      })
    },
    ok (info) {
      let list = Object.assign({speciesid: this.speciesid, auditstatus: 2, fcreatorid: this.account}, info)
      this.$api.post('wiki/api/wiki/saveSpeciesPest', list).then(response => {
        if (response.code === 200) {
          this.$Message.success({
            content: '新增成功！请等待审核，审核通过后数据将会更新。',
            duration: 3
          })
          this.handleSpeciesPest(this.speciesPest.current)
        }
      })
      this.$refs.pest.modal = false
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
