<template>
  <div class="base-list">
    <Row :gutter="16">
      <Col span="6" v-for="(map, index) in dataList" :key="index" class="list">
        <Card :padding="0" @click.native="detail(map)">
          <img v-if="map.imageUrl" :src="map.imageUrl" alt="" style="width: 100%; height:168px;">
          <img v-else src="../../../../static/img/goods-list-no-picture1.png" alt="" style="width: 100%; height:168px;">
           <!-- <img :src="`//api.map.baidu.com/staticimage?width=286&height=180&center=${map.coordinate.split(',')[0]},${map.coordinate.split(',')[1]}&zoom=11&markers=${map.coordinate.split(',')[0]},${map.coordinate.split(',')[1]}`" style="width: 100%;"> -->
          <p class="ell pd10 name">{{map.productionBaseName}}</p>
        </Card>
      </Col>
    </Row>
    <baseDetail ref="detail"></baseDetail>
  </div>
  <!-- <div class="service-list">
    <ul >
      <li v-for="(item, index) in dataList">
        <img v-if="item.image" :src="item.image" alt="" height="149px" width="100%">
        <img v-else src="../../../../static/img/goods-list-no-picture1.png" alt="" height="149px" width="100%">
        <div class="pd10">
          <p class="name ell pt5 pb10" :title="item.productionBaseName">{{item.productionBaseName}}</p>
          <p class="ell-3 t-grey description" :title="item.description">{{item.description}} 简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介</p>
        </div>
      </li>
    </ul>
  </div> -->
</template>
<script>
import baseDetail from '../../goods/detail/components/productionBaseDetail'
export default {
    props: {
        dataList: {
            type:Array
        },
        small:{
            typr:Boolean,
            default:false
        }
    },
    components: {
      baseDetail
    },
  data () {
    return {

    }
  },
  methods: {
    detail (item) {
      this.$refs['detail'].init(item.account, item.id)
    }
  },
}
</script>
<style lang="scss" scoped>
.base-list{
  .list{
    height: 235px;
  }
  .name{
    background: #F9F9F9;
    color: #4A4A4A;
  }
}
</style>
<style lang="scss" scoped>
.service-list{
  li{
    height: 270px;
    position: relative;
    background: #fff;
    margin: 25px 10px 0 10px;
    display: inline-block;
    width: calc(100% / 5 - 20px);
    list-style: none;
    vertical-align: middle;
    box-shadow: 2px 5px 14px 0px rgba(0, 0, 0, 0.1);
    &:hover{
      box-shadow: 0px 0px 0px 2px rgba(0,197,135,1);
    }
    // p{
    //   height: 34px;
    //   line-height: 34px;
    // }
    .name{
      font-size: 16px;
      // line-height: 34px;
    }
  }
}
</style>

