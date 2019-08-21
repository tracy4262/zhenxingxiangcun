<template>
  <div>
    <Input v-model="values" class="pinput-clear"  icon="ios-close-circle" :disabled="disabled" readonly @on-focus="handleFilterModal" @on-click="inputClear " />
    <!-- 相关物种 -->
    <vui-filter
      ref="filter"
      :num="num"
      :cols="cols"
      :pageShow="true"
      :total="total"
      :pageCur="pageCur"
      :classifyDatas="classifyDatas"
      :resultDatas="resultDatas"
      :load-data="loadDatas"
      @on-search="handleSearch"
      @on-get-classify="handleGetClassify"
      @on-get-result="handleGetResult"
      @on-page-change="handlePageChange"/>

  </div>
</template>

<script>
import vuiFilter from '~components/vuiFilter/filter';
export default {
  components: {vuiFilter},
  props: {
    disabled: {
      type: Boolean,
      default: false
    },
    num: {
      type: Number,
      default: 1
    },
    value: String
  },
  data() {
    return {
      total: 0,
      pageCur: 1,
      cols: 3,
      index: 0,
      classifyDatas: [],
      resultDatas: [],
      values: ''
    }
  },
    watch: {
      value: {
        handler(curVal, oldVal) {
          this.values = curVal
        },
        deep: true
      }
    },
  created() {
    this.values = this.value
    this.loadResult('', '', [], 1)
    // 获取分类数据
    this.$api.post('/member-reversion/ecoSocial/findByParentId/0').then(response => {
      if (response.code === 200) {
        response.data.forEach(e => {
          e.checked = false
          e.loading = false
          e.label = e.className
          e.value = e.classId
        })
        this.classifyDatas = response.data
      }
    })
  },
  methods: {
    // 高级搜索弹窗
    handleFilterModal(name) {
      this.$refs.filter.highFilterShow = true
    },
    //清空文本框
    inputClear(name) {
      this.values = ''
      this.$refs.filter.handleReset()
      this.$emit('on-get-data', this.values)
    },
    // 取相关物种
    handleSearch(letter, keyword, classify, result) {
      this.loadResult(letter, keyword, classify, this.pageCur, result)
    },
    // 点击类目 获取下级小类
    loadDatas(item, callback) {
      item.loading = true
      // 判断是否点击的是第一个类目，如果是的则index回到1 ， index用于判断 是否是第三类，如果是第三类，则不再 有loading = false
      if (item.parentId == 0) {
        this.index = 1
      }
      this.$api.post(`/member-reversion/ecoSocial/findByParentId/${item.value}`).then(res => {
        if (res.code === 200) {
          item.loading = false
          this.index += 1
          var d = res.data
          d.forEach(child => {
            child.checked = false
            child.value = child.classId
            child.label = child.className
            if (this.index < this.cols && child.children) {
              child.loading = false
            }
          })
          item.children = d
          callback()
        }
      })
    },
    // 根据类目筛选
    handleGetClassify(letter, keyword, classify, result) {
      this.loadResult(letter, keyword, classify, this.pageCur, result)
    },
    // 翻页
    handlePageChange(letter, keyword, classify, num, result) {
      this.pageCur = num
      this.loadResult(letter, keyword, classify, this.pageCur, result)
    },
    // 国民经济发展 搜索
    loadResult(letter, keyword, classify, num, result) {
      if (classify.length) {
        var arr = []
        var type = ''
        classify.forEach(item => {
          arr.push(item.classId)
          if (item.value !== undefined) type = item.value
        })
      } else {
        arr = []
      }
      this.$api
        .post('/member-reversion/ecoSocial/findEcoClassify', {
          keywords: keyword,
          fpinyin: letter === '全部' ? '' : letter,
          fclassifiedid: arr,
          type: type,
          pageNum: num,
          pageSize: 32
        })
        .then(res => {
          let data = res.data
          if (data === undefined) {
            this.total = 0
          } else {
            this.total = data.total
          }
          if (result) {
            if (data === undefined) {
              this.resultDatas = []
              return false
            } else {
              result.forEach(item => {
                data.list.forEach((child, index) => {
                  if (child.label === item.label) {
                    child.checked = true
                  } else {
                    child.checked = false
                  }
                })
              })
            }
          } else {
            data.list.forEach(child => {
              child.checked = false
            })
          }
          this.resultDatas = data.list
        })
    },

    // 国民经济发展 获取数据
    handleGetResult(classify, result) {
      let arr = []
      let speeci = ''
      result.forEach(item => arr.push(item.label))
      speeci = arr.join(' ')
      console.log(speeci)
      this.$emit('on-get-data', speeci)
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
