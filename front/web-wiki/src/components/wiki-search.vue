<template>
  <Row type="flex" justify="center" class="wiki-search">
    <Col span="10">
      <template v-if="select">
        <Select
        class="wiki-search-input"
        placeholder="请输入物种关键字"
        v-model="keyIndex"
        filterable
        remote
        :remote-method="remoteMethod"
        :loading="loading">
        <Option v-for="(item, index) in options" :value="index" :key="index" @click.native="handleSelect(item)">{{item.fname}}</Option>
      </Select>
      </template>
      <template v-else>
        <Input
        v-model="keyword"
        placeholder="请输入物种关键字"
        class="wiki-search-input"
        @on-change="handleInputChange"
        @on-keyup.enter="handlekeyWord" />
      </template>

    </Col>
    <Col span="2">
      <Button type="primary" long class="wiki-search-btn" @click.native="handlekeyWord">百科一下</Button>
    </Col>
  </Row>
</template>
<script>
export default {
  props: {
    select: {
      type: Boolean,
      default: false
    }
  },
  data: () => ({
    keyword: '',
    keyIndex: '',
    loading: false,
    options: [],
    list: []
  }),
  methods: {
    remoteMethod (query) {
      if (query !== '') {
        this.loading = true
        this.$api.post('wiki/api/species/listSpecies', {
          keywords: query,
          pageNum: 1,
          pageSize: 6
        }).then(res => {
          this.loading = false
          this.options = res.data
        })
      } else {
        this.options = []
      }
    },
    handleInputChange (event) {
      this.$emit('on-change', event.target.value)
    },
    handleSelect (item) {
      this.$emit('on-get-keyword', item)
    },
    handlekeyWord () {
      if (this.select) {
        let item = this.options[0] || null
        if (item === null) {
          this.$router.push('/')
        } else {
          this.$emit('on-get-keyword', item)
        }
      } else {
        this.$emit('on-get-keyword', this.keyword)
      }
    }
  }
}
</script>
<style lang="scss">
.wiki{
  &-search{
    padding: 20px 0 50px;
    &-input .ivu-input,
    .ivu-select-input,
    .ivu-select-selection,
    &-btn{
      height:40px;
      border-radius: 0;
    }
  }
}
</style>
