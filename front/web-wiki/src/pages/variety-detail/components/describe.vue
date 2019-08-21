<template>
  <div>
    <Row>
      <Col span="19">
        <span class="h2 b">{{data.fname}} &nbsp;</span>
        <Icon type="edit" @click.native="handleEdit" :size="16" class="ml5" color="#9B9B9B" style="cursor:pointer"></Icon>
      </Col>
      <Col span="5" class="tr">
        <Button type="text" size="small" @click.native="handleEdit"><Icon type="compose" /> 我来纠错</Button>
        <Button type="text" class="vui-share-btn" size="small">
          <Icon type="android-share-alt" /> 分享
          <vue-share></vue-share>
        </Button>
      </Col>
    </Row>
    <!-- <p class="content">{{data.fshapefeatureid}}</p> -->
     <Form label-position="left" :label-width="120" class="vui-form-sm">
      <Row type="flex" justify="center" align="bottom" >
        <Col span="12">
          <Form-item prop="fname" label="品种名称">{{data.fname}}</Form-item>
        </Col>
        <Col span="12">
          <Form-item prop="fname" label="汉语拼音" style="word-wrap:break-word">{{data.fpinyin}}</Form-item>
        </Col>
      </Row>
      <Row type="flex" justify="center" align="bottom" >
        <Col span="12">
          <Form-item prop="fname" label="物种名称">{{speciesName}}</Form-item>
        </Col>
        <Col span="12">
          <Form-item prop="fname" label="品种类型">{{data.fvarietykind}}</Form-item>
        </Col>
      </Row>
      <Form-item prop="fname" label="品种来源">{{data.fvarietyorigin}}</Form-item>
      <Row type="flex" justify="center" align="bottom" >
        <Col span="24">
          <Form-item prop="fname" label="选育单位">{{data.fbreedingunit}}</Form-item>
        </Col>
      </Row>
      <Row type="flex" justify="center" align="bottom" >
        <Col span="12">
          <Form-item prop="fname" label="是否转基因">
            <!-- 0否 1是 -->
            <span v-if="data.fistransgene === 0">否</span>
            <span v-if="data.fistransgene === 1">是</span>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item prop="fname" label="申请日期"><span v-if="data.fapplydate">{{$fecha.format(new Date(data.fapplydate), 'YYYY/MM/DD')}}</span></Form-item>
        </Col>
      </Row>
      <Row type="flex" justify="center" align="bottom" >
        <Col span="12">
          <Form-item prop="fname" label="申请号">{{data.fapplynumber}}</Form-item>
        </Col>
        <Col span="12">
          <Form-item prop="fname" label="申请公众日"><span v-if="data.fapplyannouncedate">{{$fecha.format(new Date(data.fapplyannouncedate), 'YYYY/MM/DD')}}</span></Form-item>
        </Col>
      </Row>
      <Row type="flex" justify="center" align="bottom" >
        <Col span="12">
          <Form-item prop="fname" label="申请公众号">{{data.fapplyannouncenumber}}</Form-item>
        </Col>
        <Col span="12">
          <Form-item prop="fname" label="授权日"><span v-if="data.fauthdate">{{$fecha.format(new Date(data.fauthdate), 'YYYY/MM/DD')}}</span></Form-item>
        </Col>
      </Row>
      <Row type="flex" justify="center" align="bottom" >
        <Col span="12">
          <Form-item prop="fname" label="品种授权号">{{data.fauthnumber}}</Form-item>
        </Col>
        <Col span="12">
          <Form-item prop="fname" label="授权公告日"><span v-if="data.fauthannouncedate">{{$fecha.format(new Date(data.fauthannouncedate), 'YYYY/MM/DD')}}</span></Form-item>
        </Col>
      </Row>
      <Row type="flex" justify="center" align="bottom" >
        <Col span="12">
          <Form-item prop="fname" label="授权公告号">{{data.fauthannouncenumber}}</Form-item>
        </Col>
        <Col span="12">
          <Form-item prop="fname" label="品种权(申请)人">{{data.fvarietyowner}}</Form-item>
        </Col>
      </Row>
      <Row type="flex" justify="center" align="bottom" >
        <Col span="24">
          <Form-item prop="fname" label="培育人">{{data.fgrowpeople}}</Form-item>
        </Col>
      </Row>
      <Row type="flex" justify="center" align="bottom" >
        <Col span="12">
          <Form-item prop="fname" label="审定年份"><span v-if="data.fvarietyapprdate">{{$fecha.format(new Date(data.fvarietyapprdate), 'YYYY')}}</span></Form-item>
        </Col>
        <Col span="12">
          <Form-item prop="fname" label="审定单位">{{data.fvarietyapprunit}}</Form-item>
        </Col>
      </Row>
      <Row type="flex" justify="center" align="bottom" >
        <Col span="24">
          <Form-item prop="fname" label="审定编号">{{data.fvarietyapprnum}}</Form-item>
        </Col>
      </Row>
    </Form>
  </div>
</template>
<script>
import vueShare from '~components/vue-share'
export default {
  props: {
    data: {
      type: Object,
      default: {}
    },
    speciesName: String
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
