<template>
  <div>
    <ul class="new-name-library">
      <!-- type 0 我收藏的 1 我新增的 -->
       <li v-if="type === '0'"  :class="type === '0' ? 'collection' : 'add'">
        <div class="conent">
          <div class="add-member tc" @click="handleAdd">
            <Icon type="md-add" size="38" class="mt30"/>
            <p class="pt10">添加收藏</p>
          </div>
        </div>
      </li>
      <li :class="type === '0' ? 'collection' : 'add'" v-for="(item, index) in data">
          <!-- type 0 我收藏的 1 我新增的 -->
        <div class="check-box" :class="item.check ? 'isCheck' : ''" v-if="edit && type === '0'" @click="handleCheck(item, index)">
          <Icon type="md-checkmark" />
        </div>
        <div class="check-box" :class="item.check ? 'isCheck' : ''" v-if="edit && type === '1' && item.auditstatus === 4" @click="handleCheck(item, index)">
          <Icon type="md-checkmark" />
        </div>
        <!-- type 0 收藏  1新增 -->
        <div class="conent"  :class="item.check && edit ? 'conentBorder' : ''" v-if="type === '0' ">
          <img src="../../../../../static/img/goods-list-no-picture1.png" alt="" v-if="!item.image.length" width="100%" height="100%">
          <img :src="item.image[0] ? item.image[0] : '../../../../../static/img/goods-list-no-picture1.png'" alt="" v-else width="100%" height="100%">
          <div class="cover" v-if="!edit" @click="handleCancels(item, index)">
            取消收藏
          </div>
        </div>
        <div class="conent"  :class="item.check && edit ? 'conentBorder' : ''" v-if="type === '1' ">
          <div v-if="item.ficon" style="height: 118px;">
            <img src="../../../../../static/img/goods-list-no-picture1.png" alt="" v-if="!item.ficon.length" width="100%" height="100%">
            <img :src="item.ficon[0] ? item.ficon[0] : '../../../../../static/img/goods-list-no-picture1.png'" alt="" v-else width="100%" height="100%">
          </div>
           <div v-if="item.fimagesrc" style="height: 118px;">
            <img src="../../../../../static/img/goods-list-no-picture1.png" alt="" v-if="!item.fimagesrc.length" width="100%" height="100%">
            <img :src="item.fimagesrc[0] ? item.fimagesrc[0] : '../../../../../static/img/goods-list-no-picture1.png'" alt="" v-else width="100%" height="100%">
          </div>
          <div class="cover" v-if="!edit && item.auditstatus === 4" @click="handleCancels(item, index)">
            删除
          </div>
        </div>
        
        <p class="tc name ell" v-if="item.name" @click="detail(item)">{{item.name}}</p>
        <p class="tc name ell" v-if="item.fname" @click="detail(item)">{{item.fname}}</p>
        <p class="tc status">
          <!-- 0, 2, 3待审核：不能编辑 不能删除  1 通过的：不能删除，可以编辑  4 未通过： 可以删除，可以编辑 -->
          <!-- auditstatus  0 更新待审核  1 审核通过  2  新增待审核  3 删除待审核 4 未审核通过 -->
          <span v-if="item.auditstatus === 4" class="t-red">未通过</span>
          <span v-if="item.auditstatus === 0 || item.auditstatus === 2 || item.auditstatus === 3" class="t-orange">审核中</span>
          <span v-if="item.auditstatus === 1" class="t-grey">已通过</span>
        </p>
      </li>
    </ul>
    <div class="tc pt40 pb20" v-if="data.length">
      <Page :total="pages.total" @on-change="getNextPage" :page-size="pages.pageSize" :current="pages.pageNum"></Page>
    </div>
    <div class="tc pt30 pb20" v-if="!data.length && type === '1'">
      <p>没有相关数据！</p>
    </div>
  </div>
</template>
<script>
  export default {
    props: {
      data: Array,
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
        default: 'addSpecies'
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
        this.$emit('on-init', e)
      },
      // 点击添加收藏
      handleAdd () {
        this.$emit('on-add')
      },
      // 点击取消收藏
      handleCancels (item, index) {
        console.log('点击了')
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
        let e = item.indexid
        if (item.auditstatus === 1 || item.auditstatus === 4) { // 可编辑
          this.$router.push(`/nameLibrary/${this.path}?speciesId=${e}`)
        } else { // 不可编辑
          this.$router.push(`/nameLibrary/${this.path}?speciesId=${e}&edit=1`)
        }
      }
    }
  }

</script>
<style lang="scss" scoped>
.new-name-library{
  .collection{
    height: 160px;
  }
  .add{
    height: 200px;
  }
  li{
      position: relative;
      background: #fff;
      margin: 15px 15px 0 0;
      display: inline-block;
      width: calc(100% / 5 - 15px);
      list-style: none;
      transition: box-shadow .2s cubic-bezier(.47,0,.745,.715);
      vertical-align: middle;
      position: relative;
      &:nth-child(5n){
        margin-right: 0;
      }
      .check-box{
        width: 30px;
        height: 20px;
        position: absolute;
        top:0px;
        right: 0px;
        background: #D8D8D8;
        color: #C9C9C9;
        line-height: 20px;
        text-align: center;
        z-index: 99;
      }
      .isCheck {
        background: #00C587;
        color: #fff;
      }
      .name{
        font-size: 14px;
        color: #4A4A4A;
        line-height: 40px;
        cursor: pointer;
      }
      .status{
        font-size: 14px;
        color: #4A4A4A;
        // line-height: 40px;
      }
      .conent {
        position: relative;
        height: 120px;
        border-radius: 2px;
        border: 1px solid rgba(237,237,237,0.62);
        .cover{
          position: absolute;
          top: 0px;
          left: 0px;
          display: block;
          width: 100%;
          height: 100%;
          background: rgba(129, 129, 129, 0.55);
          color: #fff;
          line-height: 120px;
          text-align: center;
          border-radius: 2px;
          display: none;
        }
        &:hover{
          .cover{
            display: block;
            cursor: pointer;
          }
        }
      }
      .conentBorder{
        border: 1px solid rgba(0,197,135,1);
      }
  }
}
</style>
