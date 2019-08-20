<template>
  <Modal
    :scrollable="false"
    :mask-closable="false"
    v-model="show"
    title="编辑"
    width="800"
    class="catalog-modal"
    @on-visible-change="handleModalChange">
    <Row>
      <Col span="5">
        <ul class="catalog-list" :style="{height: height}">
          <li
            v-for="(item, index) in catalogData"
            :class="{active: item.checked || active === index}"
            @click="handleClick(item, index)">
              {{item.catalog_name}}
          </li>
        </ul>
      </Col>
      <Col span="19" class="pd20">
        <div ref="content">
          <div :key="index" v-for="(item, index) in catalogData" v-if="active === (index)">
            <brief :id="index" v-if="index == 0" :data="item.data" ref="brief"></brief>
            <item :id="index" v-else :data="item" :speciesid="speciesid"></item>
          </div>
        </div>
      </Col>
    </Row>
    <div slot="footer"></div>
  </Modal>
</template>
<script>
import item from './edit-modal/item'
import brief from './edit-modal/brief'
export default {
  components: {
    item,
    brief
  },
  props: {
    speciesid: String
  },
  data: () => ({
    catalogData: [{
      catalog_name: '简介',
      checked: false,
      data: {},
      fid: ''
    }, {
      catalog_name: '特征特性',
      checked: false,
      data: '',
      fid: ''
    }, {
      catalog_name: '产量',
      checked: false,
      data: '',
      fid: ''
    }, {
      catalog_name: '栽培技术',
      checked: false,
      data: '',
      fid: ''
    }, {
      catalog_name: '适宜区域',
      checked: false,
      data: '',
      fid: ''
    }, {
      catalog_name: '推广现状',
      checked: false,
      data: '',
      fid: ''
    }],
    show: false,
    active: 0,
    height: ''
  }),
  methods: {
    // 获取病数据并赋值给组件
    getDescribeData (e) {
      this.catalogData[0].data = e
      this.$nextTick(() => {
        if (this.$refs['brief'][0]) {
          this.$refs['brief'][0].getData(this.catalogData[0].data)
        }
      })
    },
    handleReload () {
      this.$emit('on-reload')
    },
    // 切换状态
    handleClick (item, index) {
      this.active = index
      this.catalogData.forEach(item => { item.checked = false })
      item.checked = true
      this.setHeight()
    },
    handleModalChange (flag) {
      if (flag) {
        // 清除选中
        this.catalogData.forEach(item => { item.checked = false })
        this.setHeight()
      } else {
        // window.location.reload()
      }
    },
    setHeight () {
      this.$nextTick(() => {
        this.height = `${this.$refs.content.clientHeight + 40}px`
      })
    }
  },
  watch: {
    active (newVal, oldVal) {
      this.active = newVal
    }
  }
}
</script>
<style lang="scss" scoped>
.catalog-list{
  padding: 10px 0;
  background: #F3F7F5;
  li{
    padding: 8px 10px 8px 25px;
    border-left: 2px solid transparent;
    margin-bottom: 15px;
    cursor: pointer;
    &.active{
      border-left-color: $green;
      background: #fff;
    }
  }
}
</style>
<style lang="scss">
.catalog-modal{
  .ivu-modal-body{
    padding: 0
  }
  .ivu-modal-header{
    background: $green;
  }
  .ivu-modal-header-inner,
  a.ivu-modal-close .ivu-icon{
    color: #fff;
  }
  .ivu-modal-footer{
    padding: 0;
    border-top: none;
  }
}
</style>

