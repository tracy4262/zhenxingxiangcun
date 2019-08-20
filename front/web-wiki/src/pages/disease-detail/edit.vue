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
            <describe :id="index" ref="describe" :data="item" v-if="active === 0" @on-save="handleSave" @on-cancel="show = false"></describe>
            <item :id="index" :data="item" v-else></item>
          </div>
        </div>
      </Col>
    </Row>
    <div slot="footer"></div>
  </Modal>
</template>
<script>
import item from './edit-modal/item'
import describe from './edit-modal/describe'
export default {
  components: {
    item,
    describe
  },
  data: () => ({
    catalogData: [{
      catalog_name: '病害',
      checked: false,
      fname: '',
      fpinyin: '',
      fimagesrc: '',
      fid: '',
      speciesid: ''
    }, {
      catalog_name: '危害症状',
      checked: false,
      data: '',
      fid: '',
      speciesid: ''
    }, {
      catalog_name: '发生规律',
      checked: false,
      data: '',
      fid: '',
      speciesid: ''
    }, {
      catalog_name: '防治办法',
      checked: false,
      data: '',
      fid: '',
      speciesid: ''
    }],
    show: false,
    active: 0,
    height: ''
  }),
  methods: {
    // 病害点击保存之后，调用
    handleSave () {
      this.show = false
      this.handleReload()
    },
    // 获取病数据并赋值给组件
    getDescribeData (e) {
      this.catalogData[0].fname = e.fname
      this.catalogData[0].fid = e.fid
      this.catalogData[0].speciesid = e.speciesid
      this.catalogData[0].fpinyin = e.fpinyin
      this.catalogData[0].fimagesrc = e.fimagesrc
      this.$nextTick(() => {
        if (this.$refs['describe'][0]) {
          this.$refs['describe'][0].getData(this.catalogData[0])
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
      this.$nextTick(() => {
        if (this.$refs['describe'][0]) {
          this.$refs['describe'][0].getData(this.catalogData[0])
          this.setHeight()
        }
      })
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
