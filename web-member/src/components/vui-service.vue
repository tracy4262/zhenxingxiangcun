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

        <!-- 相关服务 -->
        <vui-filter
        ref="serviceFilter"
        :simple="true"
        :cols="2"
        :num="num"
        :resultDatas="serviceResultDatas"
        @on-search="handleServiceSearch"
        @on-get-result="handleGetServiceResult" />
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
      }
    },
    data () {
      return {
        value: '',
        serviceResultDatas: [],
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
      inputClear () {
        this.value = ''
        this.$refs.serviceFilter.handleReset();
        this.handleGetServiceResult('', []);
        this.$emit('on-save', this.value)
      },
      // 高级搜索弹窗
      handleFilterModal () {
        this.handleServiceSearch('', '', [])
        this.$refs['serviceFilter'].highFilterShow = true
      },
       // 相关服务
      handleServiceSearch(letter, keyword, classify) {
        this.$api
          .post(`/member/service/find`, {
            character: letter === "全部" ? "" : letter,
            account: this.$user ? this.$user.loginAccount : '',
            keyword: keyword
          })
          .then(res => {
            var d = res.data;
            this.serviceResultDatas = [];
            if (d.length) {
              if (classify.length) {
                classify.forEach(item => {
                  d.forEach(child => {
                    if (child.label === item.label) {
                      child.checked = true;
                    }
                  });
                });
              } else {
                d.forEach(child => {
                  child.checked = false;
                });
              }
              this.serviceResultDatas = d;
              this.$Message.success("筛选完成！");
            }
          });
      },
      handleGetServiceResult(classify, result) {
         if (this.input) {
            var arr = []
            result.forEach(item => arr.push(item.label))
            this.value = arr.join(' ')
            this.$emit('on-save', this.value)
          } else {
            this.$emit('on-save', result)
          }
      }
    }
  }
</script>
