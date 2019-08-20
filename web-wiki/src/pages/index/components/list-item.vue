<template>
  <div class="pt30 pb10 mb20">
    <Row :gutter="16" class="wiki-list" v-if="data.length">
      <Col span="3" :key="index" v-for="(item,index) in data">
      <!-- item.name 说明是单独去的动物或者植物，接口与取全部的数据接不一样，所以字段不一致 -->
        <router-link v-if="item.name"
          :to="{
            name: 'detail',
            query: {
              indexid: item.indexid,
              speciesid: item.speciesid,
              classId: item.id
            }
          }"
          class="wiki-list-item">
          <img :src="item.ficon ? `${item.ficon}` : './static/imgs/default-img.png'">
          <div class="bd">
            <p class="title ell" v-if="item.name">{{item.name}}</p>
            <p class="title ell" v-else>{{item.fname}}</p>
          </div>
        </router-link>
        <router-link v-else
          :to="{
            name: 'detail',
            query: {
              indexid: item.indexid,
              speciesid: item.speciesid,
              classId: item.fclassifiedid,
              speciesName: item.fname
            },
            params: {
              speciesName: item.fname
            }
          }"
          class="wiki-list-item">
          <div v-if="item.fimage">
            <img v-if="item.fimage.length" :src="item.fimage[0]">
            <img v-else :src="item.ficon ? `${item.ficon}` : './static/imgs/default-img.png'">
          </div>
          <div v-else>
            <img :src="item.ficon && item.ficon[0] ? `${item.ficon[0]}` : './static/imgs/default-img.png'">
          </div>
          <div class="bd">
            <p class="title ell" v-if="item.name">{{item.name}}</p>
            <p class="title ell" v-else>{{item.fname}}</p>
          </div>
        </router-link>
      </Col>
    </Row>
    <div class="tc pd20" v-else>
      <img src="../../../assets/imgs/no-result.png" height="200" alt="">
      <p class="t-grey">暂无搜索结果，可点击  <span @click="handleAdd" style="color:#33d19f;text-decoration: underline;cursor: pointer;">添加</span></p>
    </div>
    <div class="tc mt50 t-grey" v-if="more && data.length">
      <divider text="暂无更多数据" solid></divider>
    </div>
  </div>
</template>
<script>
import divider from '~components/divider'
import {loginuserinfo} from '~components/mixins'
export default {
  props: {
    data: Array,
    more: Boolean
  },
  components: {
    divider
  },
  mixins: [loginuserinfo],
  data: () => ({
  }),
  watch: {
    more (newVal, oldVal) {
      this.more = newVal
    }
  },
  methods: {
    handleAdd () {
      if (this.loginuserinfo === null) {
        this.$Message.error('请先登录')
        this.$refs['loginRegister'].loginuser()
      } else {
        window.location.href = `${window.location.origin}/nameLibrary/addSpecies`
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.wiki{
  &-list{
    &-item{
      position: relative;
      display: block;
      height:160px;
      margin-bottom: 16px;
      overflow: hidden;
      &:before{
        content: '';
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        background: #000;
        opacity: .1
      }
      &:hover:before{
        opacity: 0;
        transition: opacity .4s
      }
      img{
        height: 136px;
        width: 100%;
      }
      .bd{
        color: #fff;
        position: absolute;
        right: 0;
        bottom: 0;
        left: 0;
        background-color: rgba(0,0,0,.4);
        padding: 5px 10px;
        transition: top .3s;
        overflow: hidden;
      }
      .title{
        font-size: 14px;
      }
    }
  }
}
</style>
