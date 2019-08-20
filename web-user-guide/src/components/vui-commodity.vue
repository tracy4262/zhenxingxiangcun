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
        default: 1
      },
      //  type 1 通用商品名。2 通用服务名
      type: {
        type: String,
        default: '1'
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
      // 通用商品名
      this.value = this.values
      // type 1 通用商品名。2 通用服务名
      this.$api.post('/member/nameLibrary/findShopCurrencyCommodity', {
        type: this.type,
        dictName:'',
        character: '',
        pageNum: 1,
        pageSize: 32
      }).then(res => {
        var data = res.data.dataList
        var d = []
        this.total2 = res.data.total
        data.forEach(element => {
          let list = Object.assign({
                label: element.commodityName,
                value: element.commodityId
            },element)
            d.push(list)
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
      // 通用商品名
      handleTradeSearch (letter, keyword, result) {
        this.$api.post(`/member/nameLibrary/findShopCurrencyCommodity`, {
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
                  child.label = child.commodityName
                  child.value = child.commodityId
                }
              })
            })
          } else {
            d.forEach(child => {
              child.checked = false
              child.label = child.commodityName
              child.value = child.commodityId
            })
          }
          this.tradeResultDatas = d
        })
      },
      handleTradePageChange (letter, keyword, classify, num, result) {
        this.pageCur2 = num
        this.$api.post('/member/nameLibrary/findShopCurrencyCommodity', {
          type: this.type,
          character: letter === '全部' ? '' : letter,
          dictName: keyword,
          pageNum: num,
          pageSize: 32
        }).then(res => {
          var data = res.data.dataList
          var d = []
          this.total2 = res.data.total
          data.forEach(element => {
              let list = Object.assign({
                label: element.commodityName,
                value: element.commodityId
            },element)
            d.push(list)
          })
          this.tradeResultDatas = d
        })
        this.$Message.success('筛选完成！')
      },
      handleGetTradeResult(classify, result) {
        this.$emit('on-save', result)
      },
    }
  }
</script>
<style lang="scss">

.pinput-clear .ivu-icon {
    display: none;
    cursor: pointer;
}

.pinput-clear:hover .ivu-icon {
    display: block;
}
</style>
