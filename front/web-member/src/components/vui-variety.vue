<template>
  <div>
      <Input
        v-if="input"
        v-model="value"
        readonly
        :disabled="disabled"
        class="pinput-clear"
        @on-focus="handleFilterModal"
        :icon="disabled ? '' :'ios-close-circle'"
        @on-click="inputClear"
        @on-change="change" />
        <slot></slot>
    <!-- 品种-->
     <vui-filter
        ref="tradeFilter"
        :num="num"
        :simple="true"
        :pageShow="true"
        :transfer="true"
        :total="total2"
        :pageCur="pageCur2"
        :resultDatas="tradeResultDatas"
        @on-search="handleTradeSearch"
        @on-page-change="handleTradePageChange"
        @on-get-result="handleGetTradeResult"/>
  </div>
</template>
<script>
import vuiFilter from './vuiFilter/filter'
  export default {
    components: {
      vuiFilter
    },
    props: {
      values: String,
      disabled: {
        type: Boolean,
        default: false
      },
      input: {
        type: Boolean,
        default: true
      },
      num: {
        type: Number,
        default: 4
      },
      // type 0 品种 病害1 虫害2
      type: {
        type: String,
        default: '0'
      }
    },
    data () {
      return {
        value: '',
        id:'',
        total2: 0,
        pageCur2: 1,
        tradeResultDatas: [],
        id: ''
      }
    },
    created () {
      this.value = this.values
    },
    watch: {
      values: {
        handler(curVal, oldVal) {
          this.value = curVal
        },
        deep: true
      }
    },
    methods: {
      change () {

      },
      init () {
        // type 0 品种 病害1 虫害2
        this.$api.post('/member/nameLibrary/findVarietyList', {
          account: this.$user ? this.$user.loginAccount : '',
          type: this.type,
          dictName:'',
          character: '',
          pageNum: 1,
          pageSize: 32
        }).then(res => {
          var data = res.data.dataList
          this.total2 = res.data.total
          this.tradeResultDatas = data
        })
      },
      inputClear () {
        this.value = ''
        this.id = ''
        this.$refs.tradeFilter.handleReset();
        this.handleGetTradeResult('', []);
        this.$emit('on-save', this.value)
        this.$emit('on-save-id', this.id)
      },
      // 高级搜索弹窗
      handleFilterModal () {
        this.init()
        this.$refs['tradeFilter'].highFilterShow = true
      },
      // 行业类型
      handleTradeSearch (letter, keyword, result) {
        this.$api.post(`/member/nameLibrary/findVarietyList`, {
          account: this.$user ? this.$user.loginAccount : '',
          type: this.type,
          dictName : keyword,
          character: letter === '全部' ? '' : letter,
          pageNum: 1,
          pageSize: 32
        }).then(res => {
          var d = res.data.dataList
          this.total2 = res.data.total
          this.pageCur2 = 1
          this.tradeResultDatas = []
          if (!d.length) return
          if (result.length) {
            result.forEach(item => {
              d.forEach(child => {
                if (child.label === item.label) {
                  child.checked = true
                  child.label = child.label
                  child.value = child.value
                }
              })
            })
          } else {
            d.forEach(child => {
              child.checked = false
              child.label = child.label
              child.value = child.value
            })
          }
          this.tradeResultDatas = d
        })
      },
      handleTradePageChange (letter, keyword, classify, num, result) {
        this.pageCur2 = num
        this.$api.post('/member/nameLibrary/findVarietyList', {
          account: this.$user ? this.$user.loginAccount : '',
          type: this.type,
          character: letter === '全部' ? '' : letter,
          dictName: keyword,
          pageNum: num,
          pageSize: 32
        }).then(res => {
          var data = res.data.dataList
          this.total2 = res.data.total
          this.tradeResultDatas = data
        })
        this.$Message.success('筛选完成！')
      },
      handleGetTradeResult(classify, result) {
        if (this.input) {
          var arr = []
          var arrId = []
          result.forEach(item => arr.push(item.label))
          result.forEach(item => arrId.push(item.value))
          this.value = arr.join(' ')
          this.id = arrId.join(' ')
          this.$emit('on-save', this.value)
          this.$emit('on-save-id', this.id)
        } else {
          this.$emit('on-save', result)
        }
      },
    }
  }
</script>
