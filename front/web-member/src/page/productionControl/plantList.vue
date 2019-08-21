<template>
  <div class="plant">
    <Row :gutter="16">
      <Col span="4"  class="mb5">
          <div class="conent" style="border: 1px dashed rgba(232,232,232,1);">
            <div class="add-member tc" @click="handleAdd">
              <Icon type="md-add" size="38" class="mt30"/>
              <p class="pt10">添加</p>
            </div>
          </div>
      </Col>
      <Col span="4" v-for="(item, index) in data" :key="index" class="mb5">
        <div class="conent">
          <img src="../../../static/img/goods-list-no-picture1.png" alt="" v-if="!item.image.length" width="100%" height="100%">
          <img :src="item.image[0] ? item.image[0] : '../../../static/img/goods-list-no-picture1.png'" alt="" v-else width="100%" height="100%">
          <div class="cover" @click="handleCancels(item, index)">
            删除
          </div>
        </div>
        <p class="tc name ell" v-if="item.speciesName" @click="detail(item)">{{item.speciesName}}</p>
      </Col>
    </Row>
    <div class="tc pt40 pb20" v-if="data.length">
      <Page :total="total" @on-change="getNextPage" :page-size="pageSize" :current="pageNum"></Page>
    </div>
    <vuiSpecies :input="false" ref="vuiSpecies" @on-save="onSaveFocus" :num="1000"></vuiSpecies>
  </div>
</template>
<script>
import vuiSpecies from '~components/vui-species'
export default {
  components:{
    vuiSpecies
  },
  data() {
    return {
      data: [],
      total: 1,
      pageSize: 24,
      pageNum: 1,
      yearId: '',
      year: ''
    }
  },
  created() {
    if (this.$route.query.yearId) {
      this.yearId = this.$route.query.yearId
      this.getInit()
    }
    if (this.$route.query.year) {
      this.$parent.year = this.$route.query.year
      this.year = this.$route.query.year
    }
  },
  methods: {
    getInit () {
      let data = {
        yearId: this.yearId, 
        account: this.$user.loginAccount,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      }
      this.$api.post('/shop/plant/findPlantSpeciesInfo', data).then(response => {
        if (response.code === 200) {
          this.data = response.data.list
          this.total = response.data.total
        }
      })
    },
    // 点击添加
    handleAdd () {
        this.$refs['vuiSpecies'].handleFilterModal()
    },
    // 添加收藏后保存
    onSaveFocus (e) {
      if (e.length) {
        let data = {
          account: this.$user.loginAccount,
          type: this.types,
          yearId: this.yearId,
          dataList: e
        }
        this.$api.post('/shop/plant/savePlantSpeciesInfo', data).then(response => {
          if (response.code === 200) {
            this.$Message.success('添加成功！')
            this.getNextPage(1)
          } else {
            this.$Message.error('添加失败！')
          }
        })
      } else {
        this.$Message.warning('请选择！')
      }
    },
    // 翻页
    getNextPage (e) {
      this.pageNum = e
      this.getInit()
    },
    // 点击进入详情
    detail (item) {
      this.$router.push(`/productionControl/productionPlans?id=${item.id}&name=${item.speciesName}&yearId=${this.yearId}&year=${this.year}`)
    },
    // 删除
    handleCancels (item, index) {
      this.$Modal.confirm({
          title: '操作提示',
          content: '<p>您确定删除</p>',
          cancelText: '取消',
          onOk: () => {
            this.$api.post('/shop/plant/deletePlantSpeciesInfo', {id:item.id}).then(response => {
                if (response.code === 200) {
                  this.$Message.success('删除成功！')
                  this.getNextPage(1)
                } else {
                  this.$Message.error('删除失败！')
                }
              })
          }
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.plant {
  width: 1000px;
  min-height: 800px;
  margin: 0 auto;
  background-color: #fff;
  padding: 48px;
}
.conent{
  position: relative;
  height: 120px;
  width: 100%;
  border-radius: 4px;
  border: 1px solid rgba(232,232,232,1);
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
.name{
  font-size: 14px;
  color: #4A4A4A;
  line-height: 40px;
  cursor: pointer;
}
</style>
