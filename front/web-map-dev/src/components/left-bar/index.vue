<template>
  <div class="left-bar">
    <Card>
      <!-- <Select v-model="address" @on-change="addressChange" placeholder="请选择区域">
        <Option v-for="(item, index) in addressList" :value="item.value" :key="index">{{item.label}}</Option>
      </Select> -->
      <!-- :change-on-select="true" -->
      <Cascader
      placeholder="请选择区域"
      :clearable="false"
      :data="cascader"
      v-model="location"
      :load-data="loadData"
      @on-change="handleGetData"></Cascader>
      <div class="pt10" v-if="!edit">
        <Select v-model="list.parent" clearable filterable @on-change="ListChange" placeholder="请选择一级分类">
          <Option v-for="(item, index) in data" :value="item.value" :key="index">{{item.label}}</Option>
        </Select>
        <Select class="mt10" clearable v-if="showFilter" v-model="list.children" filterable @on-change="childrenChange" placeholder="请选择二级分类">
          <Option v-for="(item, index) in filterData.children" :value="item.value" :key="index">{{item.label}}</Option>
        </Select>
        <Input v-model="list.keyword" class="mt10" placeholder="请输入关键字"></Input>
        <!-- 营业网点 风景名胜 有表单筛选 -->
        <Select class="mt10" clearable v-if="list.parent == '01'" v-model="list.SFMF" placeholder="是否免费">
          <Option value="是">是</Option>
          <Option value="否">否</Option>
        </Select>
        <CheckboxGroup class="mt10" @on-change="WDLXChange" v-if="list.parent == 'businessOutlets'" v-model="WDLXARR" style="width: 100%;">
            <Checkbox label="销售门店" ></Checkbox>
            <Checkbox label="售后网点" ></Checkbox>
        </CheckboxGroup>
        <!-- <Select class="mt10" clearable v-if="list.parent == 'businessOutlets'" v-model="list.WDLX" placeholder="网点类型">
          <Option value="销售门店">销售门店</Option>
          <Option value="售后网点">售后网点</Option>
        </Select> -->

        <div class="tr pd10">
          <Button type="primary" @click="handleSearch">查询</Button>
        </div>
        <ul class="list scroll-y" v-if="filterList.length">
          <li v-for="(item, index) in filterList" :key="index" :class="item.checked ? 'active' : ''">
            <div @click="handleNav(item, index)">
              <Row type="flex" align="middle">
                <Col span="22">
                  <Row type="flex" algin="middle">
                    <Col span="3">
                      <Icon type="ios-pin" size="20" class="t-red"></Icon>
                    </Col>
                    <Col span="21">
                      <p class="ell b">{{item.properties.name}}</p>
                    </Col>
                  </Row>
                </Col>
              </Row>
            </div>
          </li>
        </ul>
        <p v-if="!filterList.length && isSearch" class="tc t-grey pd20">暂无搜索数据</p>
      </div>
    </Card>
  </div>
</template>

<script>
import $ from "jquery"
// import vuiUploadFile from '_c/vui-upload-file'
  export default {
    name: '',
    components: {
      // vuiUploadFile
    },
    props: {
      edit: {
        type: Boolean,
        default: false
      },
      data: {
        type: Array
      }
    },
    data() {
      return {
        showFilter: false,
        filterData: {},
        childrenData: {},
        keyword: '',
        list: {
          parent: '',
          children: '',
          SFMF: '',
          WDLX: '',
          keyword: ''
        },
        WDLXARR: [],
        isSearch: false,
        cascader: [],
        location: [],
        area: '',
        level: 0,
        filterList: [],
        address: '',
        addressList: [
          { 
            label: '颜闸村',
            value: '0' ,
            url: '/api/ovitserver/yanzhacun/wmts',
            // layerName: 'cite:YX',
            bbox: '112.41292069025,29.98405383425,112.45181527375,30.007323108749997',
            borderUrl: '/api/ovitserver/YZC_XZQ/wms',
            borderStyle: 'YZC_XZQ.sld',
            borderLayers: 'YZC_XZQ',
          }
        ],
        firstList: [],
        filterTotal: 0
      }
    },
    created () {
      this.$api.post('/member/town/yanZha/0').then(res => {
        res.data.forEach(e => {
          e.loading = false
        })
        this.cascader = res.data
      })
    },
    methods: {
      WDLXChange (e) {
        this.list.WDLX = this.WDLXARR.join(',')
      },
      // 地址change
      addressChange (e) {
        let list = {}
        this.addressList.forEach(element => {
          if (element.value === e) {
            list = Object.assign(list, element)
          }
        })
        this.list = {
          parent: '',
          children: '',
          SFMF: '',
          WDLX: ''
        }
        this.$emit('on-change', list)
      },
      getFilterData () {
        this.ListChange(this.list.parent)
      },
      // 分类列表 change
      ListChange (e) {
        let list = {}
        this.data.forEach(element => {
          if (element.value === e) {
            this.filterData = $.extend(true, {}, element)
            this.showFilter = this.filterData.children ? true : false
          }
        })
        if (!e) {
          this.filterData = {}
          this.showFilter = this.filterData.children ? true : false
          this.childrenData = {}
          this.list.children = ''
          this.list.SFMF = ''
          this.WDLXARR = []
          this.list.WDLX = ''
        }
      },
      getChildrenData () {
        this.getFilterData()
        this.childrenChange(this.list.children)
      },
      // 二级选项改变
      childrenChange (e) {
        let list = {}
        this.filterData.children.forEach(element => {
          if (element.value === e) {
            this.childrenData = $.extend(true, {}, element)
          }
        })
        if (!e) {
          this.childrenData = {}
          this.list.children = ''
        }
      },
      // 点击查询 查两次 一个图层过滤 一个列表查询
      handleSearch () {
        if (!this.address) {
          // 请选择地区
          this.$Message.warning('请选择地区')
          return
        }
        this.filterTotal = 0
        this.$emit('on-search', this.list,)
        let selectList = []
        if (this.list.parent) {
          // 选了一级
          if (this.list.children) {
            // 选了二级
            selectList = [this.childrenData]
          } else {
            selectList = [this.filterData]
          }
        } else {
          selectList = this.data
        }
        this.isSearch = true
        this.filterTotal = 0
        this.filterList = []
        this.filterSelectList(selectList)
      },
      // 根据筛选条件 遍历 查询列表
      async filterSelectList (selectList) {
        for (const key in selectList) {
          if (selectList[key].list && selectList[key].list.length) {
            let list = selectList[key].list
            let result = false
            for (const i in list) {
              let result = await this.getSearchList(list[i], selectList[key].showLabel, selectList[key].keyword)
            }
            if (result) {
              return
            }
          } else {
            this.filterSelectList(selectList[key].children)
          }
        }
      },
      // 根据不同图层 查询列表数据
      async getSearchList (e, label, keyword) {
        // SSMC LIKE '%1%' AND NLCS LIKE '%1%'
        // DKMC LIKE '%玉米%' AND DKMC LIKE '%玉米%' SSMC = '1' SSMC = '1' AND NLCS = '1' YHM = '${this.$user.loginAccount}'
        let filter = null
        if (this.list.parent == '01' && this.list.SFMF) {
          // filter = `SFMF = '${this.list.SFMF}'`
          filter = `SFMF LIKE '%${this.list.SFMF}%'`
        } else if (this.list.parent == 'businessOutlets' && this.list.WDLX) {
          filter = `WDLX LIKE '%${this.list.WDLX}%'`
          // filter = `WDLX = '${this.list.WDLX}'`
        }
        // +关键字模糊搜索
        if (this.list.keyword) {
          keyword.forEach(e =>{
            filter += `AND ${e.name} ${e.type} '%${this.list.keyword}%'`
          })
        }
        let list = {
          containsgeometry: true,
          count: 20,
          filter: filter,
          name: e.layerName,
          startindex: 1
        }
        if (this.filterTotal < 10) {
          await this.$api.mappost('/api/space/getAttributes', list).then(res => {
            this.filterTotal += res.totalFeatures
            // this.filterList = this.filterList.concat(res.features)
            this.resetName(this.data, e, label, res.features)
            if (this.filterTotal > 10) {
              return true
            }
          })
        } else {
          return true
        }
      },
      // 给列表添加部分属性
      resetName (data, e, label, features) {
        if (data) {
          data.forEach(element => {
            if (element.list && element.list.length) {
              element.list.forEach(i => {
                if (e.layerName == i.layerName) {
                  // 显示的名字 统一修改为name
                  features.forEach(list => {
                    list.properties.name = list.properties[label[0]]
                    list.properties.label = element.label
                    list.properties.value = element.value
                    list.properties.layerName = i.layerName
                    list.properties.checked = false
                  })
                  this.filterList = this.filterList.concat(features)
                  if (this.filterList.length > 10) {
                    this.filterList = this.filterList.splice(0, 10)
                  }
                }
              })
            } else if (element.children && element.children.length) {
              this.resetName(element.children, e, label, features)
            }
          })
        }
      },
      // 列表点击 弹出 详细信息以及 列表选中
      handleNav (item, index) {
        this.filterList.forEach((e, i) => {
          if (i === index) {
            e.checked = true
          } else {
            e.checked = false
          }
          this.filterList.splice(i, 1, e)
        })
        console.log('item.properties', item.properties)
        this.$emit('on-click', item.properties)
      },
      loadData (item, callback) {
        console.log('9999')
        item.loading = true
        this.$api.post(`/member/town/yanZha/${item.value}`).then(res => {
          item.loading = false
          item.children = res.data
          callback()
        })
      },
      // 值改变时
      handleGetData (value, selectedData) {
          console.log(selectedData)
        let list = {}
        if (selectedData.length) {
          list = Object.assign(list, selectedData[selectedData.length - 1])
          this.address = list.value
        }
        this.list = {
          parent: '',
          children: '',
          SFMF: '',
          WDLX: ''
        }
        this.WDLXARR = []
        this.$emit('on-change', list)
      }
    }
  }
</script>

<style lang="less" scoped>
.left-bar{
  position: fixed;
  top: 110px;
  left: 35px;
  z-index: 999;
  width: 300px;
  /deep/.ivu-input {
    border-width: 0 0 1px 0;
    border-radius: 0;
    &:focus {
      box-shadow: none;
    }
  }
  /deep/.ivu-select-selection{
    border-width: 0 0 1px 0;
    border-radius: 0;
    &:focus {
      box-shadow: none;
    }
  }
  /deep/.ivu-select-visible .ivu-select-selection{
    box-shadow: none;
  }
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
    .active{
      background: #F3F3F3;
    }
  }
}

</style>
