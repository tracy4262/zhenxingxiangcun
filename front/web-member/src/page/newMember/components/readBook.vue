<template>
  <Row :gutter="16" class="pt20 vui-book-cata">
    <Col span="6">
      <div style="border: 1px solid #dcdee2; border-radius: 4px;" class="pd10 pt30 pb30">
        <p class="head-line pl5 mb20"><b>目录({{bookData.length}}章)</b></p>
        <div v-for="(item, index) in bookData" :key="index">
          <Tooltip :content="item.title" :delay="500" placement="top">
            <p class="catalog ell" @click="handleExpand(item, index)">
              <Icon type="md-arrow-dropright" v-if="item.children.length && item.expand == 'false'"/>
              <Icon type="md-arrow-dropdown" v-if="item.children.length && item.expand == 'true'"/>
              <b>第{{index+1}}章 {{item.title}}</b>
            </p>
          </Tooltip>
          <div class="pl15 sbuCatalog" v-if="item.expand == 'true'">
              <Tooltip :content="list.title" :delay="500" placement="top" v-for="(list, i) in item.children" :key="i">
                <p :class="[(number2 === i && number1 === index) ? 't-green' : '']"  @click="checkBook(index, i)" class="ell">第{{i+1}}节：{{list.title}}</p>
              </Tooltip>
          </div>
        </div>
      </div>
    </Col>
    <Col span="17" v-if="bookData[number1]" class="pt10 pb30">
      <Row style="border-bottom: 2px solid #eee;" type="flex" align="middle">
        <Col span="18" >
          <b>{{`第${number1+1}章`}} {{title1}}</b> <span style="font-size:12px;">{{`第${number2+1}节`}} {{title2}}</span>
        </Col>
        <Col span="6" class="tr">
          <Button type="text" @click="back" icon="ios-arrow-back">返回</Button>
        </Col>
      </Row>
      <div class="pt20 pb20 pl5 pr5" v-if="content">
        <div v-html="content"></div>
      </div>
      <div v-if="bookFile">
        <vue-pdfjs ref="pdfjs" :url="bookFile" viewer="../static/" ></vue-pdfjs>
      </div>
    </Col>
  </Row>
</template>
<script>
import vuePdfjs from "vue-pdfjs";
export default {
  components: {
    vuePdfjs
  },
  data() {
    return {
      bookData: [],
      number1: 0,
      number2: 0,
      content: '',
      title1: '',
      title2: '',
      bookFile: ''
    };
  },
  created() {
  },
  methods: {
    handleExpand (item, i) {
      item.expand == 'true' ? item.expand = 'false' : item.expand = 'true'
    },
    back () {
      this.$parent.showBook = false
      this.$parent.$parent.show = true
    },
    init (data) {
      this.bookData = data
      this.checkBook(0, 0)
    },
    checkBook (index, i) {
      this.number1 = index
      this.number2 = i
      this.title1 = this.bookData[index].title
      if (this.bookData[index].children.length) {
        this.title2 = this.bookData[index].children[i].title
        this.content = this.bookData[index].children[i].content
        this.bookFile = this.bookData[index].children[i].file
      } else {
        this.title2 = ''
        this.content = ''
        this.bookFile = ''
      }
      console.log(this.number1)
    }
  },
};
</script>
<style scoped lang='scss'>
  .head-line{
    border-left: 5px solid #00c587;
  }
  .catalog{
    line-height: 26px;
    cursor: pointer;
  }
  .sbuCatalog{
    cursor: pointer;
    p{
      font-size: 12px;
      line-height: 24px;
    }
  }
  .vui-book-cata .ivu-tooltip-rel{
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
</style>
