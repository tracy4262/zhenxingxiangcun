<template>
  <div class="related-services pt10" v-if="data.length">
    <Divider>相关服务</Divider>
    <div class="related-services-lists" v-if="data.length">
      <div class="related-services-list" ref="list">
        <div v-for="(item, index) in data" class="pt15" style="height: 225px;" @click="handleDetail(item, index)">
          <img v-if="item.image_url && item.image_url[0]" :src="item.image_url[0]" alt="" height="160px" width="100%">
          <img v-else src="../../../../../static/img/goods-list-no-picture1.png" alt="" height="160px" width="100%">
          <p class="t-grey ell tc mt10" :title="item.service_name">{{item.service_name}}</p>
          <p v-if="item.contact" class="ell tc mt5" :title="item.contact[0] ? item.contact[0].detailAddress : ''"><Icon type="md-pin" />{{item.contact[0] ? item.contact[0].detailAddress : ''}}</p>
        </div>
      </div>
    </div>
    <div class="tc pt5" v-if="data.length">
      <Button type="text" @click="hadleUp"><Icon type="ios-arrow-up" size="32"/></Button>
      <Button type="text" @click="hadleDown"> <Icon type="ios-arrow-down" size="32"/></Button>
    </div>
    <div v-if="!data.length" class="tc pt50">
      <p>暂无相关服务</p>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      data: [],
      end: 0,
      active: 0
    }
  },
  created() {
    this.end = this.data.length ? this.data.length -2 : this.data.length
  },
  methods: {
    handleDetail (item, index) {
      console.log('item', item)
      let url = ''
      if (item.id == 5) {
        window.open(`${window.location.origin}/consultationService/detail?id=${item.id}`, '_bank')
      } else {
        window.open(`${window.location.origin}/InforMation/serviceDetail?id=${item.id}&uid=${item.account}&type=${item.type}`, '_bank')
      }
    },
    init (data) {
      this.data = data
      this.end = this.data.length ? this.data.length -2 : this.data.length
    },
    hadleUp () {
      if (this.active > 0) {
        this.active --
        this.active --
        if (this.active < 0) {
          this.active = 0
          this.$refs.list.style.top = `-${this.active*225}px`
        } else {
          this.$refs.list.style.top = `-${this.active*225}px`
        }
        
      }
    },
    hadleDown () {
      if (this.end > 0) {
        this.active ++
        this.active ++
        if (this.active < this.end) {
          this.$refs.list.style.top = `-${this.active*225}px`
        } else {
          this.active = this.end
          this.$refs.list.style.top = `-${this.active*225}px`
        }
      }
      // 225px
      console.log(this.$refs.list.style.top)
    }
  },
}
</script>
<style scoped>
.related-services .related-services-lists{
  height: 460px;
  overflow: hidden;
  position: relative;
}
.related-services .related-services-list{
  position: absolute;
  top: 0px;
  width: 100%;
  left:0px;
}
  /* .related-services p{
    line-height: 24px;
  } */
</style>
