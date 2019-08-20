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
          @click="handleClick(item)">{{item.roomClassName}}</Button>
        </Col>
      </Row>
    </div>
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
      filterData: [
      //   {
      //   name: '全部',
      //   checked: true
      // }, {
      //   name: '标准间',
      //   checked: false
      // }, {
      //   name: '商务房',
      //   checked: false
      // }, {
      //   name: '情侣房',
      //   checked: false
      // }, {
      //   name: '双人房',
      //   checked: false
      // }, {
      //   name: '大床房',
      //   checked: false
      // }
      ],
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
  created() {
    this.handleTypeList()
  },
  methods: {
    // 获取房间分类
    handleTypeList () {
        this.$api.post('/member/accommodation/findRoomClass', 
        {account: this.$user.loginAccount, pageNum: 1, pageSize: 100000})
        .then(response => {
            if (response.code === 200) {
                let arr =  [{roomClassName: '所有分类', id: -1,checked: true}]
                let data = response.data.list
                data.forEach(e => {
                  e.checked = false
                })
                this.filterData = arr.concat(data)
            }
        })
    },
    handleClick (item) {
      this.filterData.forEach(item => {
        item.checked = false
      })
      item.checked = true
      this.$emit('on-checked', item.id)
    },
    handleInit (e) {
      this.checkData = e
      this.$emit('on-get-data', this.checkData)
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
