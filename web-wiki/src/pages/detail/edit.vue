<template>
  <Modal
    :scrollable="false"
    v-model="show"
    title="编辑"
    width="800"
    :mask-closable="false"
    class="catalog-modal"
    @on-visible-change="handleModalChange">
    <Row>
      <Col span="5">
        <ul class="catalog-list" ref="left" :style="{height: height, 'min-height': '540px'}">
          <li
            v-for="(item, index) in catalogData"
            :class="{active: item.checked || active === index}"
            @click="handleClick(item, index)" :key="index">
            <Input
            v-model.trim="item.catalog_name"
            placeholder="请输入目录名称"
            size="small"
            :ref="`input${index}`"
            :maxlength="8"
            @on-blur="handleChange(item, index)"
            v-if="item.edit"></Input>
              <Row type="flex" align="middle" v-else>
                <Col span="18">
                  <p class="ell">{{item.catalog_name}}</p>
                </Col>
                <Col span="6" v-if="index > 3">
                  <Icon type="compose" :size="16" @click.native="handleEdit(item, index)"></Icon>
                  <Poptip
                  transfer
                  confirm
                  title="您确认删除吗？"
                  @on-ok="handleDel(item, index)">
                  <Icon type="close-round" :size="14" class="ml5"></Icon>
                </Poptip>
              </Col>
            </Row>
          </li>
          <li class="add-catalog-btn">
            <Button type="primary" long icon="plus-round" @click="handleAddCatalog">添加目录</Button>
          </li>
        </ul>
      </Col>
      <Col span="19" :style="{height: height}">
        <div ref="right" >
          <div class="pd20">
            <describe v-if="active === 0" ref="describe"></describe>
            <disease v-if="active === 1" :classType="classType"></disease>
            <pests v-if="active === 2"></pests>
            <variety v-if="active === 3" :speciesName="speciesName"></variety>
            <div :key="index" v-for="(item, index) in customCatalogData" v-if="active === (index + 4)">
              <custom :ref="`custom${index}`"></custom>
            </div>
          </div>
        </div>
      </Col>
    </Row>
    <div slot="footer"></div>
  </Modal>
</template>
<script>
import {catalogData} from '~components/mixins'
import describe from './edit-modal/describe'
import disease from './edit-modal/disease'
import pests from './edit-modal/pests'
import variety from './edit-modal/variety'
import custom from './edit-modal/custom'
export default {
  mixins: [catalogData],
  components: {
    describe,
    disease,
    pests,
    variety,
    custom
  },
  props: {
    speciesName: {
      type: String,
      default: ''
    },
    classType: {
      type: String,
      default: '植物'
    }
  },
  data: () => ({
    show: false,
    active: -1,
    height: '',
    describeData: {
      fisprotectionInfo: {},
      findustriaclassifiedidInfo: {},
      fclassifiedidInfo: {}
    },
    speciesid: '',
    pageSize: 12
  }),
  methods: {
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
        // 设置等高
        this.setHeight()
      }
    },
    setHeight () {
      this.$nextTick(() => {
        let li = this.$refs.left.querySelectorAll('li')
        let right = this.$refs.right.offsetHeight
        if (li > right) {
          this.height = `${li.length * 50 + 60}px`
        } else {
          this.height = `${right + 40}px`
        }
      })
    },
    // 添加目录
    handleAddCatalog () {
      this.addNode(this.customCatalogData)
      this.$nextTick(() => {
        this.$refs[`input${this.catalogData.length - 1}`][0].focus()
      })
      this.setHeight()
    },
    addNode (obj) {
      obj.push({
        catalog_name: '',
        edit: true,
        speciesid: this.speciesid
      })
    },
    // 编辑目录
    handleEdit (item, index) {
      item.edit = true
      setTimeout(() => {
        this.$refs[`input${index}`][0].focus()
      }, 100)
    },
    // 删除目录
    handleDel (item, index) {
      let list = {speciesid: item.species_id, propertyid: item.propertyid}
      this.$api.post('wiki/api/property/deletePropertyById/', list).then(response => {
        if (response.code === 200) {
          this.$Message.success({
            content: '删除成功！请等待审核，审核通过后数据将会更新。',
            duration: 3
          })
          this.handleGetList()
          this.active = index - 1
          this.setHeight()
        } else {
          this.$Message.error('删除失败')
        }
      })
    },
    // 保存新增目录
    handleChange (item, index) {
      let list = item
      if (item.indexid) {
        list.propertytitle = list.catalog_name
        list.speciesid = list.species_id
        this.$api.post('wiki/api/property/updateSpeciesProperty', item).then(response => {
          if (response.code === 200) {
            this.handleGetList()
            this.$Message.success({
              content: '保存成功！请等待审核，审核通过后数据将会更新',
              duration: 3
            })
          } else {
            this.$Message.error('保存失败')
          }
        })
      } else {
        if (list.catalog_name) {
          list.propertytitle = list.catalog_name
          list.auditstatus = 2
          this.$api.post('wiki/api/property/saveSpeciesProperty', list).then(response => {
            if (response.code === 200) {
              this.handleGetList()
              this.$Message.success({
                content: '保存成功！请等待审核，审核通过后数据将会更新。',
                duration: 3
              })
            } else {
              this.$Message.error('保存失败')
            }
          })
        } else {
          this.customCatalogData.pop()
        }
      }
    }
  },
  created () {
    this.indexid = this.$route.query.indexid
    this.speciesid = this.$route.query.speciesid
  },
  watch: {
    active (newVal, oldVal) {
      this.active = newVal
      let index = newVal - 4
      if (this.active >= 4) {
        let indexid = this.customCatalogData[index].propertyid
        this.$api.get('wiki/api/property/getSpeciesProperty/' + indexid).then(response => {
          if (response.code === 200) {
            this.$refs[`custom${index}`][0].handleGetData(response.data)
          }
        })
      } else if (this.active === 0) {
        // 查询详情
        this.$api.get('wiki/api/species/getSpecies/' + this.indexid).then(response => {
          if (response.code === 200) {
            let describeData = response.data
            let val = describeData.fclassifiedidInfo.val
            let type = val.split('/')[0]
            let arr = [type, describeData.fclassifiedidInfo.id]
            describeData.fclassifiedidInfo.value = arr
            if (describeData.otherClassifyInfo) {
              let otherVal = describeData.otherClassifyInfo.val
              let otherType = otherVal.split('/')[0]
              let otherArr = [otherType, describeData.otherClassifyInfo.id]
              describeData.otherClassifyInfo.value = otherArr
            } else {
              describeData.otherClassifyInfo = {value: []}
              describeData.otherClassify = ''
            }
            this.$refs['describe'].handleGetData(describeData)
          }
        })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.catalog-list{
  padding: 10px 0;
  background: #F3F7F5;
  position: relative;
  li{
    padding: 8px 15px;
    border-left: 2px solid transparent;
    margin-bottom: 15px;
    cursor: pointer;
    overflow: hidden;
    &.active{
      border-left-color: $green;
      background: #fff;
    }
    &:hover{
      .ivu-icon{
          transition: transform .3s;
          transform: translateY(0);
        }
    }
    .ivu-icon{
      transform: translateY(-200%);
    }
  }
  .add-catalog-btn{
    position: absolute;
    left: -2px;
    right: 0;
    bottom: 0;
    margin-bottom: 0;
    padding: 0;
    .ivu-btn{
      border-radius: 0;
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
