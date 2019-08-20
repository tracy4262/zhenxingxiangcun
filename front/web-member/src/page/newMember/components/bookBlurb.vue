<template>
<div>
  <div class="pd20 book-blurb" v-if="!showBook">
    <Row :gutter="16">
      <Col span="5">
        <img style="width:100%" v-if="bookInfo.cover_photo" :src="bookInfo.cover_photo">
        <img style="width:100%" v-else src="../../../img/tupian.png">
      </Col>
      <Col span="18" class="book-top-left">
        <p class="book-title"><b>{{introduceDetail.title}}</b><span class="ml20">{{bookInfo.author}} 著</span></p>
        <p class="abstracts mt10">{{introduceDetail.abstracts}}</p>
        <div>
          <span class="mr10">标签：</span><Tag type="border" color="#00c587" v-for="(item, index) in bookInfo.label">{{item}}</Tag>
        </div>
        <div class="pt15">
          <Button type="primary" @click="getId(introduceDetail.id)">开始阅读</Button>
        </div>
      </Col>
    </Row>
    <div class="pt30">
      <p class="head-line pl10 mb20"><b>基本信息</b></p>
      <Row class="info-line pt10 pb10">
        <Col span="8">
          <p>作者：{{bookInfo.author}}</p>
        </Col>
        <Col span="8">
          <p>版次：{{bookInfo.edition}}</p>
        </Col>
        <Col span="8">
          <p>字数：{{bookInfo.word_count}}</p>
        </Col>
      </Row>
      <Row class="info-line pt10 pb10">
        <Col span="8">
          <p>出版发行：{{bookInfo.publish}}</p>
        </Col>
        <Col span="8">
          <p>开版：{{bookInfo.broadsheet}}</p>
        </Col>
        <Col span="8">
          <p>纸张：{{bookInfo.paper}}</p>
        </Col>
      </Row>
      <Row class="info-line pt10 pb10">
        <Col span="8">
          <p>印刷时间：{{moment(bookInfo.print_time).format('YYYY年MM月DD日')}}</p>
        </Col>
        <Col span="8">
          <p>出版时间：{{moment(bookInfo.pub_date).format('YYYY年MM月DD日')}}</p>
        </Col>
        <Col span="8">
          <p>印张：{{bookInfo.sheet}}</p>
        </Col>
      </Row>
    </div>
    <div class="pt30">
      <p class="head-line pl10 mb20" v-if="bookInfo.book_data"><b>目录({{bookInfo.book_data.length}}章)</b></p>
      <div v-for="(item, index) in bookInfo.book_data" class="pl15 pr15">
        <p class="catalog"><b>第{{index+1}}章 {{item.title}}</b></p>
        <div class="pl20 sbuCatalog">
          <p v-for="(list, i) in item.children">第{{i+1}}节：{{list.title}}</p>
        </div>
      </div>
    </div>
  </div>
  <readBook ref="readBook" v-else></readBook>
</div>
</template>
<script>
import readBook from './readBook'
export default {
  components: {
    readBook
  },
  props: {
    data: Object
  },
  data () {
    return {
      introduceDetail: {},
      bookInfo: {},
      showBook: false
    }
  },
  created() {
    if (this.data){
      this.bookInfo = this.data.introduceDetail.book_info[0]
      this.introduceDetail = this.data.introduceDetail
    }
  },
  watch: {
    data: {
      handler (newVal,oldVal) {
        if (newVal) {
          this.bookInfo = newVal.introduceDetail.book_info[0]
          this.introduceDetail = newVal.introduceDetail
        }
      },
      deep: true
    }
  },
  methods: {
    getId (id) {
      this.showBook = true
      this.$parent.show = false
      this.$nextTick(() => {
        this.$refs['readBook'].init(this.data.introduceDetail.book_info[0].book_data)
        console.log('this.$refs[].bookData', this.$refs['readBook'].bookData)
      })
    },
  }
}
</script>
<style lang="scss" scoped>
.book-blurb{
  .book-top-left{
    .book-title{
      font-size: 18px;
      span{
        font-size: 12px;
      }
    }
    .abstracts{
      min-height: 100px;
      font-size: 12px;
      line-height: 24px;
      letter-spacing: 0.1em;
    }
  }
  .head-line{
    border-left: 5px solid #00c587;
  }
  .info-line{
    font-size: 12px;
    border-bottom: 1px dashed #ece5e5;
    p{
      padding-left: 20px;
    }
  }
  .catalog{
    line-height: 26px;
  }
  .sbuCatalog{
    p{
      font-size: 12px;
      line-height: 24px;
    }
  }
}
</style>
