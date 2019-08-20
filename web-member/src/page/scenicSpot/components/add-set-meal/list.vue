<template>
  <div class="pr20">
    <!-- <div class="fliter">
      <p class="h6 mb10">所有分类：</p>
      <Row>
        <Col span="6" v-for="(item,index) in filterData" :key="index">
          <Button type="text" long>{{item.name}}</Button>
        </Col>
      </Row>
    </div> -->
    <Table :columns="setMealCol" :data="data"></Table>
  </div>
</template>

<script>
export default {
  props: {
    data: Array
  },
  components: {
  },
  data () {
    return {
      // filterData: [{
      //   name: '全部',
      //   checked: false
      // }, {
      //   name: '湘菜',
      //   checked: false
      // }, {
      //   name: '海鲜类',
      //   checked: false
      // }, {
      //   name: '田园时蔬',
      //   checked: false
      // }, {
      //   name: '金牌主打',
      //   checked: false
      // }, {
      //   name: '冷盘助兴',
      //   checked: false
      // }]
      setMealCol: [{
        title: '名称',
        key: 'name'
      }, {
        title: '价格',
        key: 'price',
        width: 90,
        render: (h, params) => {
            return h('span',`￥ ${params.row.price}`)
        }
      },{
        title: '操作',
        key: 'action',
        width: 90,
        align: 'center',
        render: (h, params) => {
          return h('Button', {
            props: {
              type: params.row.checked ? 'primary' : 'default',
              size: 'small',
              long: true
            },
            on: {
              click: () => {
                if (!params.row.checked) {
                  // params.row.checked = true
                  this.data[params.index].checked = true
                  this.checkData.forEach((item, index) => {
                    if (item.name === params.row.name) {
                      this.checkData.splice(index, 1)
                    }
                  })
                  this.checkData.push(params.row)
                  this.$emit('on-get-data', this.checkData)
                }
              }
            }
        }, params.row.checked ? '已选购' : '选购')}
      }],
      checkData: []
    }
  },
  methods: {
    handleInit (e) {
      this.checkData = e
      this.$emit('on-get-data', this.checkData)
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
