// 过滤状态
export function Filter (data, name) {
  data.forEach(item => {
    if (item.name === name) {
      item.checked = false
    }
    if (item.children) {
      Filter(item.children, name)
    }
  })
}
// 重置状态
export function Reset (data) {
  data.forEach(item => {
    item.checked = false
    if (item.children) {
      Reset(item.children)
    }
  })
}

export const Mixins = {
  methods: {
    // 选中结果
    handleResultClick (item) {
      item.checked = !item.checked
      if (item.checked) {
        this.defaultSel.push({
          name: item.name,
          id: item.id
        })
      } else {
        this.filterSel(this.defaultSel, item.name)
      }
      this.$emit('on-get-data', this.defaultSel)
    },
    // 过滤结果
    filterSel (data, name) {
      data.forEach((item, index) => {
        if (item.name === name) {
          data.splice(index, 1)
        }
      })
    }
  }
}
