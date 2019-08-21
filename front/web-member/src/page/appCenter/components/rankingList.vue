<template>
  <div>
     <Row type="flex" align="middle" v-for="(item, index) in data" :key="index" class="pb20"  v-if="data.length">
        <Col span="3">
        <div class="index tc" :class="index < 3 ? 'top-active' : ''">{{index+1}}</div>
        </Col>
        <Col span="6" class="tc">
          <img src="../../../img/broadcast.png" alt=""class="mr10" width="35px" height="35px"> 
        </Col>
        <Col span="15">
        <Tooltip placement="top" :content="item.appName" :delay="1000">
          <p class="message ell" :title="item.appName">{{item.appName}}</p>
        </Tooltip>
          <p class="user-number">使用人数：{{item.number}}</p>
        </Col>
      </Row>
    <div class="tc pb20" v-if="!data.length">
      <p>暂无相关数据</p>
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
      }
    },
    data () {
      return {

      }
    }
  }
</script>
