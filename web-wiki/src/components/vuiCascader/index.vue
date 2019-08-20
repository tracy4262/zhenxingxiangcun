<template>
  <Cascader
    class="vui-cascader"
    :class="{active: show}"
    :data="data"
    @on-visible-change="handleToggle"
    change-on-select
    :render-format="format"
    :load-data="loadData"
    @on-change="handleGetData"
    :size="size =='default' ? 'small' : size"
    >
      <Input v-if="value" v-model="value" :icon="icon" readonly :size="size"/>
      <Input v-else v-model="values" :icon="icon" readonly :size="size"/>
  </Cascader>
</template>
<script>
export default {
  props: {
    values: String,
    isCheckedCity: {
      type: Boolean,
      default: false
    },
    size: {
      type: String,
      default: 'default'
    }
  },
  data: () => ({
    data: [],
    icon: 'arrow-down-b',
    value: '',
    selectedData: '',
    show: false
  }),
  created () {
    this.value = this.values
    this.$api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
      this.data = res.data
    })
  },
  watch: {
    values: {
      handler (curVal, oldVal) {
        console.log('curVal', curVal, 'oldVal', oldVal)
        this.value = curVal
      },
      deep: true
    }
  },
  methods: {
    format (labels, selectedData) {
      this.value = labels.join('/')
    },
    loadData (item, callback) {
      item.loading = true
      this.$api.post(`/member/town/next/${item.value}`).then(res => {
        item.loading = false
        let data = []
        // isCheckedCity作为判断是否是只需要选择到省市的标识，
        // this.selectedData在on-change的时候改变，该判断执行之前，数据还未变更，所以要判断大于等于1
        if (this.isCheckedCity && this.selectedData.length >= 1) {
          res.data.forEach(e => {
            let obj = {}
            obj.label = e.label
            obj.value = e.value
            data.push(obj)
          })
        } else {
          data = res.data
        }
        item.children = data
        callback()
      })
    },
    handleToggle (flag) {
      if (flag) {
        this.show = true
      } else {
        this.show = false
      }
    },
    handleGetData (value, selectedData) {
      this.$emit('handle-get-result', value, selectedData)
      this.selectedData = selectedData
    }
  }
}
</script>
<style lang="scss">
.vui-cascader {
  &.active{
    .ivu-input-icon{
      transition: all .2s ease-in-out;
      transform: rotate(180deg);
    }
  }
}
</style>
