<template>
  <div>
    <variety-pest ref="variety" title="新增品种" label="品种名称" :picData="speciesVarietey" @success="ok"  @on-changePage="handleChange"  :speciesName="speciesName"></variety-pest>
  </div>
</template>
<script>
import varietyPest from '../components/variety-pest'
export default {
  components: {
    varietyPest
  },
  props: {
    speciesName: {
      type: String,
      default: ''
    }
  },
  data: () => ({
    speciesVarietey: {
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
    this.handleSpeciesVarietey(1)
  },
  methods: {
    handleChange (e) {
      this.speciesVarietey.current = e
      this.handleSpeciesVarietey(e)
    },
    // 品种获取
    handleSpeciesVarietey (e) {
      this.$api.post('wiki/api/wiki/listSpeciesVarietey', {speciesid: this.speciesid, pageSize: this.speciesVarietey.pageSize, pageNum: e}).then(response => {
        if (response.code === 200) {
          this.speciesVarietey.current = e
          this.speciesVarietey.data = response.data
          this.speciesVarietey.total = response.total
        }
      })
    },
    // 保存
    ok (info) {
      let list = Object.assign({speciesid: this.speciesid, auditstatus: 2, fcreatorid: this.account}, info)
      this.$api.post('wiki/api/wiki/saveSpeciesVarietey', list).then(response => {
        if (response.code === 200) {
          this.$Message.success({
            content: '新增成功！请等待审核，审核通过后数据将会更新。',
            duration: 3
          })
          this.handleSpeciesVarietey(this.speciesVarietey.current)
        }
      })
      this.$refs.variety.modal = false
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
