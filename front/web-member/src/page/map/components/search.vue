<template>
  <Card shadow>
    <Cascader
    :data="cascader"
    v-model="location"
    :change-on-select="true"
    :load-data="loadData"
    @on-change="handleGetData"
    @on-visible-change="handleCascaderChange"></Cascader>
    <Input v-model="keyword" size="large" placeholder="搜索关键字 专家、机关、企业" @on-enter="handleSearch" @on-click="handleSearch" icon="ios-search"></Input>
    <ul class="list scroll-y" v-if="data.length">
      <li v-for="(item,index) in data" :key="index" >
        <div @click="handleNav(item)">
          <Row type="flex" align="middle">
            <Col span="22">
              <Row>
                <Col span="2">
                  <Icon type="ios-location" size="20" class="t-red"></Icon>
                </Col>
                <Col span="22">
                  <p class="ell b">{{item.name}}</p>
                  <p class="ell t-grey" style="font-size:12px">{{item.brief}}</p>
                </Col>
              </Row>
            </Col>
            <Col span="2" class="tc">
              <Icon type="ios-navigate" size="20" class="t-green"></Icon>
            </Col>
          </Row>
        </div>
      </li>
    </ul>
    <p v-if="!data.length && isSearch && (keyword || area)" class="tc t-grey pd20">暂无搜索数据</p>
  </Card>
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
      cascader: [],
      location: [],
      keyword: '',
      searchResultList: [
      //   {
      //   name: '湖北省农业科学研究院',
      //   end: '武汉市洪山区珞瑜路766号',
      //   endCity: '武汉市',
      //   checked: false,
      //   point: {
      //     lng: 114.314029,
      //     lat: 30.009168
      //   },
      // }, {
      //   name: '湖北省农业科学研究院',
      //   end: '湖北省武汉市洪山区南湖瑶苑特1号',
      //   endCity: '武汉市',
      //   checked: false,
      //   point: {
      //     lng: 114.533503,
      //     lat: 30.249684
      //   },
      // }
      ],
      searchPanel: true,
      level: 0,
      area: '',
      isSearch: false
    }
  },
  created() {
    this.$api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
      if (res.data[1].label === '其他') {
        this.cascader = [res.data[0]]
      } else {
        this.cascader = [res.data[1]]
      }
    })
  },
  methods: {
    handleSearch () {
      this.isSearch = true
      this.$emit('on-search', {keyword: this.keyword, area: this.area})
    },
    handleNav (item) {
      this.searchResultList.forEach(child => child.checked = false)
      item.checked = true
      this.$emit('on-click', item)
    },
    loadData (item, callback) {
      item.loading = true
      this.$api.post(`/member/town/next/${item.value}`).then(res => {
        item.loading = false
        let data = []
        if (this.level >= 1) {
          res.data.forEach(e => {
            data.push({label: e.label, value: e.value})
          })
        } else {
          data = res.data
        }
        item.children = data
        callback()
      })
    },
    // 值改变时
    handleGetData (value, selectedData) {
      this.level = selectedData.length
      this.area = ''
      // if (selectedData.length === 3) {
        selectedData.forEach(item => {
          this.area+=`${item.label}/`
        })
        this.area = this.area.slice(0, this.area.length-1)
        if (selectedData.length === 3) {
          if (selectedData[2].label === '市辖区' || selectedData[2].label === '县') {
            this.val = selectedData[1].label
          } else {
            this.val = selectedData[2].label
          }
        }
      // }
    },
    // 选择地区状态改变
    handleCascaderChange (flag) {
      if (!flag) {
        this.$emit('on-search', {keyword: this.keyword, area: this.area}, this.val)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.list{
  overflow: auto;
  margin-top: 20px;
  max-height: 400px;
  li {
    padding: 10px;
    cursor: pointer;
    border-bottom: 1px solid #eee;
    &:last-child{
      border: none;
    }
    &:hover{
      background: #F3F3F3;
    }
  }
}
</style>
