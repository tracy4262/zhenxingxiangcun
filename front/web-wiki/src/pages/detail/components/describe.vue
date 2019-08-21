<template>
  <div>
    <Row>
      <Col span="18">
        <span class="h2 b">{{data.fname}}</span>
        <!-- <span class="t-grey">（小麦属植物的统称）</span> -->
         <Icon type="edit" @click.native="handleEdit" :size="16" class="ml5" color="#9B9B9B" style="cursor:pointer"></Icon>
      </Col>
      <Col span="6">
        <Button type="text" size="small" @click.native="handleEdit"><Icon type="compose" /> 我来完善</Button>
        <Button type="text" class="vui-share-btn" size="small">
          <Icon type="android-share-alt" /> 分享
          <vue-share></vue-share>
        </Button>
        <span class="vui-star-wrap">
          <vui-star color="#00c587" @on-click="handleLiked" class="vui-star" :num="data.likedcount"></vui-star>
        </span>
      </Col>
    </Row>
    <p class="content">{{data.fshapefeatureid}}</p>
    <Form label-position="left" :label-width="100" class="vui-form-sm">
      <Row :gutter="8">
        <Col span="12">
          <FormItem label="物种名称">{{data.fname}}</FormItem>
        </Col>
        <Col span="12">
          <FormItem label="汉语拼音" style="word-wrap:break-word">{{data.fpinyin}}</FormItem>
        </Col>
      </Row>
      <FormItem label="物种俗名">{{data.speciesVulgo}}</FormItem>
      <Row>
        <Col span="12">
          <FormItem label="保护级别">
            <div v-if="data.fisprotectionInfo">
              {{data.fisprotectionInfo.val}}
            </div>
          </FormItem>
        </Col>
        <Col span="12">
          <FormItem label="产业分类">
            <div v-if="data.findustriaclassifiedidInfo">
              {{data.findustriaclassifiedidInfo.val}}
            </div>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <FormItem label="物种分类">
            <div v-if="data.fclassifiedidInfo">
              {{data.fclassifiedidInfo.val}}
            </div>
          </FormItem>
        </Col>
        <Col span="12">
          <FormItem label="其他分类">
            <div v-if="data.otherClassifyInfo">
              {{data.otherClassifyInfo.val}}
            </div>
          </FormItem>
        </Col>
      </Row>
      <FormItem label="主要产品">{{data.majorProduct}}</FormItem>
    </Form>
  </div>
</template>
<script>
import vueShare from '~components/vue-share'
import vuiStar from '~components/vui-star'
export default {
  props: {
    data: {
      type: Object,
      default: {}
    }
  },
  components: {
    vueShare,
    vuiStar
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
      this.$api.get('/wiki/api/wiki/saveLikedCount/' + this.indexid).then(response => {
        if (response.code === 200) {
          this.data.likedcount = response.data
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
