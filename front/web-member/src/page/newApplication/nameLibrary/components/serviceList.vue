<template>
  <div>
    <Row>
       <Col span="3" class="tc pb30">
        <div class="species-list species-list-preview" @click="handleAdd">
            <Icon type="md-add" size="38" class="mt30"/>
        </div>
      </Col>
      <Col span="3" v-for="(item, index) in data" class="tc pb30"  v-if="data.length" :key="index">
        <div class="species-list species-list-preview" v-if="!edit" @click="cancelFocus(item, index)">
          <Avatar class="name" v-if="item.serviceName.length > 4" :title="item.serviceName">{{item.serviceName.substring(0, 5)}}...</Avatar>
          <Avatar class="name" v-else>{{item.serviceName}}</Avatar>
          <span class="focus">取消收藏</span>
        </div>
        <div class="species-list species-list-edit" :class="item.check ? 'species-list-check' : ''" v-if="edit" @click="handleCheck(item, index)">
          <div class="check" v-if="item.check">
            <Icon type="md-checkmark" />
          </div>
          <div class="unCheck" v-if="!item.check">
            <Icon type="md-checkmark" />
          </div>
          <Avatar class="name" v-if="item.serviceName.length > 4" :title="item.serviceName">
            {{item.serviceName.substring(0, 5)}}...
          </Avatar>
          <Avatar class="name" v-else>{{item.serviceName}}</Avatar>
        </div>
      </Col>
    </Row>
    <div class="tc pt40 pb20" v-if="data.length">
      <Page :total="pages.total" @on-change="getNextPage" :page-size="pages.pageSize" :current="pages.pageNum"></Page>
    </div>
    <div class="tc pt30 pb20" v-else>
      <p>没有相关数据！</p>
    </div>
  </div>
</template>
<script>
  export default {
    props: {
      data: {
        type: Array,
        default: () => {
          return []
        }
      },
      edit: {
        type: Boolean,
        default: false
      },
      type: {
        type: String,
        default: '0'
      },
      path: {
        type: String,
        default: 'addService'
      },
      defaultSel: {
        type: Array,
        default: () => {
          return []
        }
      },
      pages: {
        type: Object,
        default: () => {
          return {
            pageSize: 24,
            pageNum: 1,
            total: 0
          }
        }
      }
    },
    data () {
      return {
      }
    },
    methods: {
      // 翻页
      getNextPage (e) {
        // this.pageNum = e
        this.$emit('on-init', e)
      },
      // 点击添加收藏
      handleAdd () {
        this.$emit('on-add')
      },
      // 点击取消 收藏 或者删除
      cancelFocus (item, index) {
        this.$emit('on-cancel', item, index)
      },
      // 多选模式 选中
      handleCheck (item, index) {
        item.check = !item.check
        this.data.splice(index, 1, item)
        if (item.check) {
          this.defaultSel.push(item)
        } else {
          this.filterSel(this.defaultSel, item.id)
        }
      },
      // 过滤结果
      filterSel (data, id) {
        data.forEach((item, index) => {
          if (item.id === id) {
            data.splice(index, 1)
          }
        })
      },
      detail(item) {
        let e = item.id
        //  <!-- 待审核：不能编辑 不能删除    审核通过：不能删除，可以编辑  审核未通过： 可以删除，可以编辑-->
        if (item.aduitStatus === '审核未通过' || item.aduitStatus === '审核通过') { // 可编辑
          this.$router.push(`/nameLibrary/${this.path}?id=${e}`)
        } else { // 不可编辑
          this.$router.push(`/nameLibrary/${this.path}?id=${e}&edit=1`)
        }
      }
    }
  }

</script>

<style lang="scss">
.focus-management-layouts{
  .species-list{
    width: 66px;
    line-height: 66px;
    height: 66px;
    border: 1px solid #EEEDED;
    border-radius: 50%;
    margin: 0 auto;
    font-size: 14px;
    color: #4a4a4a;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    .ivu-avatar{
      width: 66px;
      height: 66px;
      background:#fff;
      color: #4a4a4a;
      *{
      line-height: 66px;

      }
    }
  }
  .species-list-preview{
    .focus{
      display: none;
    }
    &:hover{
      background: #0EC98D;
      color:#fff;
      .name{
        display: none;
      }
      .focus{
        display: block;
      }
    }
  }
  .species-list-edit{
    .check{
      background: #0EC98D;
      color: #fff;
      width: 100%;
      height: 15px;
      line-height: 15px;
      position: absolute;
      z-index: 99;
    }
    .unCheck{
      background: #D8D8D8;
      color: #A6A6A6;
      width: 100%;
      height: 15px;
      line-height: 15px;
      position: absolute;
      z-index: 99;
    }
    &:hover{
      border: 1px solid #0EC98D;
      .ivu-avatar{
        color: #0EC98D;
      }
      .unCheck{
        background: #0EC98D;
        color: #fff;
      }
    }
  }
  .species-list-check{
    border: 1px solid #0EC98D;
    .ivu-avatar{
      color: #0EC98D;
    }
  }
}
</style>
