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
        id: '',
        total2: 0,
        pageCur2: 1,
        tradeResultDatas: [],
      }
    },
    created () {
      this.value = this.values
      // 取产品分类数据
      this.$api.post('/portal/shopCommdoity/findProductClassification', {
        name:'',
        pinyin: '',
        num: 1,
        size: 32
      }).then(res => {
        var data = res.data.list
        var d = []
        this.total2 = res.data.total
        data.forEach(element => {
            d.push({
                label: element.className,
                value: element.productCode
            })
        })
        this.tradeResultDatas = d
      })
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
        this.$refs['tradeFilter'].highFilterShow = true
      },
      // 行业类型
      handleTradeSearch (letter, keyword, result) {
        this.$api.post(`/portal/shopCommdoity/findProductClassification`, {
          name : keyword,
          pinyin: letter === '全部' ? '' : letter,
          num: 1,
          size: 32
        }).then(res => {
          var d = res.data.list
          this.total2 = res.data.total
          this.pageCur2 = 1
          this.tradeResultDatas = []
          if (!d.length) return 
          if (result.length) {
            result.forEach(item => {
              d.forEach(child => {
                if (child.label === item.label) {
                  child.checked = true
                  child.label = child.className
                  child.value = child.productCode
                }
              })
            })
          } else {
            d.forEach(child => {
              child.checked = false
              child.label = child.className
              child.value = child.productCode
            })
          }
          this.tradeResultDatas = d
        })
      },
      handleTradePageChange (letter, keyword, classify, num, result) {
        this.pageCur2 = num
        this.$api.post('/portal/shopCommdoity/findProductClassification', {
          pinyin: letter === '全部' ? '' : letter,
          name: keyword,
          num: num,
          size: 32
        }).then(res => {
          var data = res.data.list
          var d = []
          this.total2 = res.data.total
          data.forEach(element => {
              d.push({
                  label: element.className,
                  value: element.productCode
              })
          })
          this.tradeResultDatas = d
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
