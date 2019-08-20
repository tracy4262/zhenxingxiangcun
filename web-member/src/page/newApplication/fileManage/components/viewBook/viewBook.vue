<template>
  <Row type="flex" justify="start">
    <Col span="6">
      <Card style="width:200px">
        <dl class="vui-book-cata">
          <div v-for="(item,index) in bookData" :key="index">
            <dt @click="handleToggle(item)">第{{index+1}}章：{{item.title}}</dt>
            <div v-for="(i,index2) in item.children" class="ml20">
              <dt v-if="item.expand" @click="showText(index,index2)">{{i.title}}</dt>
            </div>
          </div>
        </dl>
        <!-- <div v-html="i.content"></div> -->
      </Card>
    </Col>
    <Col span="17">
      <Row type="flex" justify="space-between" align="middle">
        <Col>
          <p>{{bookData[Tid].title}} / {{bookData[Tid].children[secId].title}}</p>
        </Col>
        <Col>
          <Button type="text" @click="getIsView" icon="ios-arrow-back">返回图书页</Button>
        </Col>
      </Row>
      <div style="height:1px;background:gray"></div>
      <div v-html="bookData[Tid].children[secId].content" class="mt10"></div>
      <div>
        <vue-pdfjs
          ref="pdfjs"
          :url="bookData[Tid].children[secId].file"
          :type="1"
          viewer="../static/"
        ></vue-pdfjs>
      </div>
    </Col>
  </Row>
</template>
<script>
import vuePdfjs from "vue-pdfjs";
export default {
  props: ["bookData"],
  components: {
    vuePdfjs
  },
  data() {
    return {
      Tid: 0,
      secId: 0,
      isView: false
    };
  },
  methods: {
    handleToggle(item) {
      item.expand = !item.expand;
    },
    showText(index, index2) {
      this.Tid = index;
      this.secId = index2;
      if (this.bookData[this.Tid].children[this.secId].file === undefined) {
        this.$refs.pdfjs.loadPdf("");
      } else {
        console.log(this.bookData[this.Tid].children[this.secId].file);

        this.$refs.pdfjs.loadPdf(
          this.bookData[this.Tid].children[this.secId].file
        );
      }
    },
    getIsView() {
      this.$emit("getIsView", this.isView);
    }
  },
  created() {
    this.bookData.forEach(element => {
      element.expand = false;
    });
  }
};
</script>
<style scoped lang='scss'>
.vui-book-cata {
  * {
    cursor: pointer;
  }
  .active {
    color: #00c587;
  }
  .ivu-tooltip-rel {
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}
</style>
