// 登录状态
export const loginuserinfo = {
  data: () => ({
    loginuserinfo: null
  }),
  created () {
    this.loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
  }
}

// 目录信息
export const catalogData = {
  data: () => ({
    defaultCatalogData: [],
    customCatalogData: [],
    customCatalogDataList: [],
    catalogData: [],
    speciesid: ''
  }),
  created () {
    this.speciesid = this.$route.query.speciesid
    this.handleGetList()
  },
  methods: {
    handleGetList () {
      this.$api.post('wiki/api/wiki/listCatalog', {species_id: this.speciesid}).then(response => {
        if (response.code === 200) {
          let list = response.data
          this.defaultCatalogData = list.slice(0, 4)
          this.customCatalogData = list.slice(4, list.length)
          this.catalogData = [...this.defaultCatalogData, ...this.customCatalogData]
        }
      })
    }
  },
  watch: {
    catalogData (newVal, oldVal) {
      this.catalogData = newVal
    },
    customCatalogData (newVal, oldVal) {
      this.catalogData = [...this.defaultCatalogData, ...this.customCatalogData]
    }
  }
}
