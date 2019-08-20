<template>
  <div>
    <Input v-model="values" class="pinput-clear"  icon="ios-close-circle" :disabled="disabled" readonly @on-focus="handleFilterModal" @on-click="inputClear " />
    <!-- 相关物种 -->
    <vui-filter
      ref="filter"
      :num="num"
      :cols="1"
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
      cols: 1,
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
    this.$api.post('/portal/shopCommdoity/findUnitStandard/0').then(response => {
      if (response.code === 200) {
        response.data.forEach(e => {
          e.checked = false
          e.label = e.className
          e.value = e.classId
          delete e.children
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
    // 搜索
    handleSearch(letter, keyword, classify, result) {
      this.loadResult(letter, keyword, classify, this.pageCur, result)
    },
    // 点击类目 获取下级小类
    loadDatas(item, callback) {
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
    // 单位 搜索
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
        .post('/portal/shopCommdoity/findBasicUnit', {
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

    // 单位 获取数据
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
