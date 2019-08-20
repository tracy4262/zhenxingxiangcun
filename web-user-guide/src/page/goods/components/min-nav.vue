<template>
  <ul class="min-nav">
    <li class="item" v-for="item in data" :key="item.id">
      <Row type="flex">
        <Col span="6">
          <i :class="item.icon" class="h5"></i>
        </Col>
        <Col span="16"> {{item.label}}</Col>
        <Col span="2">
          <Icon type="ios-arrow-right"></Icon>
        </Col>
      </Row>
      <div class="sub-item scroll">
        <div v-for="(son, index) in item.children" class="vui-flex mt10 mb10" :key="index">
          <a :href="`/goods/index?productCode=${son.value}`" class="sub-name tc">{{son.label}}
            <Icon type="ios-arrow-right"></Icon>
          </a>
          <div class="pb5 mb5 vui-flex-item tl" style="border-bottom: 1px dotted #ddd;">
            <a v-for="(grandson, index) in son.children"
            :key="index"
            :href="`/goods/index?productCode=${grandson.value}`"
            class="sub-link">{{grandson.label}}</a>
          </div>
        </div>
      </div>
    </li>
  </ul>
</template>

<script>
export default {
  components: {},
  data () {
    return {
      data: []
    }
  },
  created () {
    this.$api.get('/portal/shopCommdoity/findMallClassification').then(res => {
      this.data = res.data
    })
  },
  methods: {}
}
</script>

<style lang="scss" scoped>
.min-nav{
  position: absolute;
  top: 0;
  left: 0;
  padding: 15px 0;
  background: rgba(255,255,255,.87);
  z-index: 9;
  height: 385px;
  .sub-item{
    left: 135px;
    width: 1060px;
    height: 385px;
  }
  .item{
    list-style: none;
    font-size: 14px;
    color: #4A4A4A;
    padding:9px 15px;
    width: 135px;
    &.on,
    &:hover{
      color: #fff;
      background: #00c587;
    }
    &:hover{
      .sub-item{
        display: block;
      }
    }
    .sub-item{
      display: none;
      position: absolute;
      top:0;
      background: #fff;
      padding:15px;
      overflow: auto;
    }
    .sub-name,
    .sub-link{
      display: inline-block;
      &:hover{
        color:  #00c587;
      }
        
    }

    .sub-name{
      display: block;
      width: 124px;
      color: #646464;
    }
    .sub-link{
      padding:0 10px;
      border:1px solid #E5E5E5;
      border-top: 0;
      border-bottom: 0;
      color: #8D8D8D;
      margin:0 -1px 10px 0;
    }
  }
  .shop-logo{
    margin: -1px;
    display: block;
    height: 84px;
    border: 1px solid #ddd;
    img{
      width: 100%;
      height: 100%;
    }

  }
}
</style>
