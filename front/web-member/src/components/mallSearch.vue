<template>
<div class="vui-search-wrap">
  <div class="vui-search-box">
      <Select
          style="width:494px;"
          placeholder="搜索关键字"
          v-model="datas.value"
          filterable
          remote
          :remote-method="handleSearch"
          :loading="datas.loading">
          <Option v-for="option in datas.defOpt" :value="option.value" :label="option.label" :key="option.value">
              <span>{{option.label}}</span>
              <span style="float:right;color:#ccc">约有{{option.value}}个商品</span>
          </Option>
      </Select><Button type="primary">搜索</Button>
  </div>
  <!-- <div class="vui-search-hot-tag">
    <template v-for="item in datas.hotTag">
      <a :href="item.url" class="item">{{item.text}}</a>|
    </template>
  </div> -->
</div>
</template>

<script>
export default {
  props: {
    datas:{
      type:Object,
      default:{}
    }
  },
  data() {
    return{
      search:[],
      value:''
    }
  },
  methods:{
     // 搜索
      handleSearch (query) {
          const search = this.datas;
          if (query !== '') {
              search.loading = true;
              setTimeout(() => {
                  search.loading = false;
                  search.defOpt = search.filterOpt.filter(item => item.label === query)
              }, 200);
          } else {
              search.defOpt = [];
          }
          this.value = query
          this.$emit('on-search',search)
      }
  }
}
</script>

<style lang="scss">
.vui-search{
  &-wrap{
    width:625px;
    margin: 30px auto;
    .ivu-select-single .ivu-select-selection,
    .ivu-select-input{
        height:38px;
        line-height:38px;
        border-radius: 50px 0 0 50px;
    }
    .ivu-btn{
        font-size:16px;
        width: 131px;
        border-radius: 0 50px 50px 0;
    }
  }
  &-hot-tag{
      margin-top:10px;
      font-size:14px;
      &,.item{color:#9B9B9B;}
      .item{
          display: inline-block;
          margin:0 10px;
          text-align:center;
      }
  }
}
</style>
