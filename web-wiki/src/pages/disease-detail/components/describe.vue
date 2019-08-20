<template>
  <div>
    <Row>
      <Col span="19">
        <span class="h2 b">{{data.fname}}</span>
        <Icon type="edit" @click.native="handleEdit" :size="16" class="ml5" color="#9B9B9B"></Icon>
      </Col>
      <Col span="5" class="tr">
        <Button type="text" size="small" @click.native="handleEdit"><Icon type="compose" /> 我来纠错</Button>
        <Button type="text" class="vui-share-btn" size="small">
          <Icon type="android-share-alt" /> 分享
          <vue-share></vue-share>
        </Button>
      </Col>
    </Row>
  </div>
</template>
<script>
import vueShare from '~components/vue-share'
export default {
  props: {
    data: {
      type: Object,
      default: {}
    }
  },
  components: {
    vueShare
  },
  data: () => ({
    indexid: '',
    likedcount: 0
  }),
  created () {
    this.indexid = this.$route.query.indexid
  },
  methods: {
    handleEdit () {
      this.$emit('on-edit')
    },
    // 点赞
    handleLiked () {
      this.$api.get('wiki/api/wiki/saveLikedCount/' + this.indexid).then(response => {
        if (response.code === 200) {
          this.likedcount = response.data
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.vui-share-btn{
  position: relative;
  z-index: 889;
  &:hover{
    .vui-share{
      display: block;
    }
  }
}
.vui-star-wrap{
  position: relative;
  .vui-star{
    left: 10px;
    top: -2px;
  }
}
</style>
<style lang="scss" scoped>
.content{
  text-indent: 2em;
  line-height: 24px;
  font-size: 14px;
  margin: 20px 0 30px;
  color: #4A4A4A;
  text-align: justify;
}
.ivu-form-item{
  border-bottom: 1px dotted #D8D8D8;
}
</style>
