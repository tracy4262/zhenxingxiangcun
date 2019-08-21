<template>
  <div>
      <Row class="pt30 pb30">
          <Col span="6" offset="4">
                <Cascader :data="data" @on-change="onChange" placeholder="请选择产品分类" v-model="info.type" change-on-select></Cascader>
          </Col>
          <Col span="6" offset="1">
            <Input v-model="info.name" placeholder="请输入商品名称" />
          </Col>
          <Col span="5" offset="1">
            <Button type="primary" @click="search">查询</Button>
          </Col>
      </Row>
  </div>
</template>
<script>
export default {
  data () {
    return {
        data: [],
        info:{
            type: [],
            name: '',
        }
    };
  },
  components: {
  },
  created () {
      // 商品查询
      this.$api
        .get("/portal/shopCommdoity/findMallClassification")
        .then(res => {
          let data = []
          res.data.forEach(element => {
            data.push(element.children[0])
          });
          this.data = data
        });
  },
  methods: {
      onChange (e) {
        console.log(e)
      },
      search () {
          this.$emit('on-search', this.info)
      }
  }
}
</script>
<style lang='scss' scoped>
</style>