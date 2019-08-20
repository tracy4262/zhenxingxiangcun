<template>
  <div class="pr20">
    <div class="fliter mb10">
      <p class="h6 mb10">所有分类：</p>
      <Row>
        <Col span="6" v-for="(item,index) in filterData" :key="index">
          <Button 
          :type="item.checked ? 'primary' : 'text'"
          long
          size="small"
          @click="handleClick(item)">{{item.name}}</Button>
        </Col>
      </Row>
    </div>
    <Table :columns="setMealCol" :data="tableData"></Table>
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
      tableData: this.data,
      filterData: [
        {
          name: '全部',
          checked: true
        }
      ],
      setMealCol: [
        {
          title: '名称',
          key: 'name'
        },
        {
          title: '价格',
          key: 'price',
          width: 90,
          render: (h, params) => {
              return h('span',`￥ ${params.row.price}`)
          }
        },
        {
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
                  // params.row.checked = true
                  if (!params.row.checked) {
                    this.tableData[params.index].checked = true
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
        }
      ],
      checkData: []
    }
  },
  created () {
    this.init()
  },
  methods: {
    handleClick (item) {
      this.filterData.forEach(item => {
        item.checked = false
      })
      item.checked = true
      // 每次点击分类重置数据源
      this.tableData = this.data
      // 若不是全部选择项则根据菜品分类过滤数据
      if (item.name !== '全部') {
        let arr = this.tableData.filter(element => element.foodClassName === item.name)
        this.tableData = arr
      }
    },
    init () {
        this.$api.post('/member/restaurant/findRestaurant', {
            account: this.$user.loginAccount,
            pageNum: 1,
            pageSize: 1000
        }).then(response => {
            if (response.code === 200) {
                response.data.list.forEach(element => {
                    this.filterData.push({
                        name: element.foodClassName,
                        checked: false
                    })
                })
            }
        }).catch(error => {
            this.$Message.error('查询菜品分类失败！')
        })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
