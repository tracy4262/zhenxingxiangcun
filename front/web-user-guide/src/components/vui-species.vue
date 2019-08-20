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
    <!-- 相关物种 -->
    <vui-filter 
        ref="speciFilter" 
        :cols="2" 
        :num="num" 
        :pageShow="true" 
        :total="total" 
        :pageCur="pageCur" 
        :classifyDatas="speciClassifyDatas" 
        :resultDatas="speciResultDatas" 
        :load-data="loadSpeciDatas" 
        @on-search="handleSpeciSearch" 
        @on-get-classify="handleGetSpeciClassify" 
        @on-get-result="handleGetSpeciResult"
        @on-page-change="handleSpeciPageChange" />
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
      disabled: {
        type: Boolean,
        default: false
      },
      num: {
        type: Number,
        default: 4
      }
    },
    data () {
      return {
        value: '',
        id: '',
        total: 0,
        pageCur: 1,
        speciClassifyDatas: [
          {
            label: '动物',
            value: '0',
            classId: '',
            loading: false,
            checked: false,
            children: []
          },
          {
            label: '植物',
            value: '1',
            classId: '',
            loading: false,
            checked: false,
            children: []
          }
        ],
        speciResultDatas: []
      }
    },
    created () {
      // 取相关物种结果
      this.loadSpeciResult('', '', [], this.pageCur, [])
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
      inputClear () {
        this.value = ''
        this.id = ''
        this.$refs.speciFilter.handleReset();
        this.handleGetSpeciResult('', []);
        this.$emit('on-save', this.value)
        this.$emit('on-save-id', this.id)
      },
      // 高级搜索弹窗
      handleFilterModal () {
        this.$refs['speciFilter'].highFilterShow = true
      },
      loadSpeciDatas(item, callback) {
        item.loading = true
        this.$api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
          item.loading = false
          var d = res.data
          d.forEach(child => {
            child.checked = false
            child.label = child.className
          })
          item.children = d
          callback()
        })
      },
      // 相关物种
      handleSpeciSearch(letter, keyword, classify, result) {
        this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
        this.$Message.success('筛选完成！')
      },
      handleGetSpeciClassify(letter, keyword, classify, result) {
        this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
        this.$Message.success('筛选完成！')
      },
      handleGetSpeciResult(classify, result) {
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
      handleSpeciPageChange(letter, keyword, classify, num, result) {
        this.pageCur = num
        this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
        this.$Message.success('筛选完成！')
      },
      loadSpeciResult(letter, keyword, classify, num, result) {
        if(classify.length) {
          var arr = []
          var type = ''
          classify.forEach(item => {
            arr.push(item.classId)
            if(item.value !== undefined) type = item.value
          })
        } else {
          arr = null
        }
        this.$api.post('/member/specicesClass/findSpecies', {
          keywords: keyword,
          fpinyin: letter === '全部' ? '' : letter,
          fclassifiedid: arr,
          type: type,
          pageNum: num,
          pageSize: 32
        }).then(res => {
          var data = res.data
          this.total = data.total
          if(result) {
            result.forEach(item => {
              data.list.forEach((child, index) => {
                if(child.label === item.label) {
                  child.checked = true
                }
              })
            })
          } else {
            data.list.forEach(child => {
              child.checked = false
            })
          }
          this.speciResultDatas = data.list
        })
      },
    }
  }
</script>
