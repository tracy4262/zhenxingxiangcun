<template>
  <!-- 循环图书文件夹内部列表 -->
  <div>
    <div style="height:2px;background:#f5f5f5"></div>
    <Row :gutter="16" v-if="showBookDetail" class="pl20 pr20">
      <Col span="4" v-for="(item,index) in bookDetail" :key="index">
        <div class="photo-content mt20" @click="viewBook(index)">
          <img :src="item.cover_photo" class="getimg">
          <p class="ell" :title="item.title">{{item.title}}</p>
        </div>
      </Col>
    </Row>
    <Row v-if="isEmpty">
      <h2>啥都没有</h2>
    </Row>
    <!-- 循环图书内容 -->
    <Row
      v-if="!showBookDetail&&!isView"
      class="main-body code-row-bg"
      type="flex"
      justify="space-between"
    >
      <div style="width:1000px;background:white">
        <Button class="mt25 returnBtn" @click="showDetailBtn" type="text" icon="ios-arrow-back">返回</Button>
        <Row type="flex" justify="center" class="code-row-bg mt10">
          <Col span="5">
            <img :src="bookDetail[viewId].cover_photo" class="getimg1">
          </Col>
          <Col span="16">
            <Row type="flex" justify="start" align="bottom" class="code-row-bg">
              <Col style="padding-right:5px">
                <h2 style="font-size:20px;font-weight:bold">{{bookDetail[viewId].title}}</h2>
              </Col>
              <Col>
                <Tag type="border" color="green">{{bookDetail[viewId].source}}</Tag>
              </Col>
            </Row>
            <div class="mt10">
              <p
                style="font-size:14px;font-family: PingFangSC-Regular;height:90px"
              >{{bookDetail[viewId].summary}}</p>
            </div>
            <Row type="flex" justify="start" align="middle" class="code-row-bg">
              <Col>
                <p>标签：</p>
              </Col>
              <Col v-for="(tag,index) in bookDetail[viewId].label" :key="index">
                <Tag type="border" color="green">{{tag}}</Tag>
              </Col>
            </Row>
            <div class="mt10 bookBtnArea">
              <Button type="primary" @click="readBook">开始阅读</Button>
              <Button @click="updateBook">编辑</Button>
              <Button @click="deleteBook">删除</Button>
            </div>
          </Col>
        </Row>
        <div>
          <div class="form-bar">
            <div class="left-bar"></div>
            <h4>基本信息</h4>
          </div>
          <Row type="flex" justify="center" class="code-row-bg mt20 bookData">
            <Col span="7">
              <p>作者：{{bookDetail[viewId].author}}</p>
            </Col>
            <Col span="7">
              <p>版次：{{bookDetail[viewId].edition}}</p>
            </Col>
            <Col span="7">
              <p>关联物种：{{bookDetail[viewId].species}}</p>
            </Col>
            <Col class="book-border" span="21"></Col>
          </Row>
          <Row type="flex" justify="center" class="code-row-bg bookData">
            <Col span="7">
              <p>出版发行：{{bookDetail[viewId].publish}}</p>
            </Col>
            <Col span="7">
              <p>印张：{{bookDetail[viewId].sheet}}</p>
            </Col>
            <Col span="7">
              <p>通用商品名：{{bookDetail[viewId].products}}</p>
            </Col>
            <Col class="book-border" span="21"></Col>
          </Row>
          <Row type="flex" justify="center" class="code-row-bg bookData">
            <Col span="7">
              <p>经销：{{bookDetail[viewId].distribution}}</p>
            </Col>
            <Col span="7">
              <p>开版：{{bookDetail[viewId].broadsheet}}</p>
            </Col>
            <Col span="7">
              <p>通用服务名：{{bookDetail[viewId].service}}</p>
            </Col>
            <Col class="book-border" span="21"></Col>
          </Row>
          <Row type="flex" justify="center" class="code-row-bg bookData">
            <Col span="7">
              <p>印刷时间：{{bookDetail[viewId].print_time | getTime}}</p>
            </Col>
            <Col span="7">
              <p>字数：{{bookDetail[viewId].word_count}}</p>
            </Col>
            <Col span="7">
              <p>行业分类：{{bookDetail[viewId].industryName}}</p>
            </Col>
            <Col class="book-border" span="21"></Col>
          </Row>
          <Row type="flex" justify="center" class="code-row-bg bookData">
            <Col span="7">
              <p>出版时间：{{bookDetail[viewId].pub_date | getTime}}</p>
            </Col>
            <Col span="7">
              <p>纸张：{{bookDetail[viewId].paper}}</p>
            </Col>
            <Col span="7">
              <p>图书标签：{{bookDetail[viewId].label | getTag}}</p>
            </Col>
            <Col class="book-border" span="21"></Col>
          </Row>
        </div>
        <!-- 图书章节 -->
        <div>
          <div class="form-bar">
            <div class="left-bar"></div>
            <h4>目录({{bookDetail[viewId].book_data.length}}章)</h4>
          </div>
          <div v-for="(item,index) in bookDetail[viewId].book_data" class="ml60">
            <div v-if="index<=2">
              <h4 class="book_title">{{item.title}}</h4>
              <p v-for="(p,index2) in item.children" class="ml10">{{p.title}}</p>
            </div>
          </div>
          <div v-for="(item,index1) in bookDetail[viewId].book_data" class="ml60">
            <div v-if="index1>2&&showBtn === false">
              <h4 class="book_title">{{item.title}}</h4>
              <p v-for="(p,index2) in item.children" class="ml10">{{p.title}}</p>
            </div>
          </div>
          <div class="showAll" @click="showAllBtn" v-if="showBtn">显示全部</div>
        </div>
      </div>
    </Row>

    <Row>
      <div v-if="bookDetail.length !== 0&& showBookDetail===true" class="mt30">
        <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange"/>
      </div>
    </Row>
    <div v-if="isView">
      <!-- <Button type="text" @click="isView = false" class="mt10 mb10" icon="ios-arrow-back">返回</Button> -->
      <ViewBook :bookData="bookDetail[viewId].book_data" @getIsView="getIsView"></ViewBook>
    </div>

    <Modal v-model="updateBookModal" width="800" @on-cancel="upCancel">
      <Steps :current="current" style="padding-left:90px" class="mt20">
        <Step title="基本信息"></Step>
        <Step title="详细详细"></Step>
        <Step title="关联信息"></Step>
      </Steps>
      <Step1
        v-show="current === 0"
        ref="step1"
        :books="books"
        :bookDetail="bookDetail"
        :viewId="viewId"
        v-if="flag"
      ></Step1>
      <Step2
        v-show="current === 1"
        ref="step2"
        :bookDetail="bookDetail"
        :viewId="viewId"
        v-if="flag"
      ></Step2>
      <Step3
        v-show="current === 2"
        ref="step3"
        :bookDetail="bookDetail"
        :viewId="viewId"
        v-if="flag"
      ></Step3>
      <div slot="footer" style="text-algin:center">
        <Button @click="preStep" v-if="current != 0">上一步</Button>
        <Button @click="next" v-if="current != 2">下一步</Button>
        <Button v-if="current === 2" @click="formSubmit">提交</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import ViewBook from "./viewBook/viewBook";
import Step1 from "./step1";
import Step2 from "./step2";
import Step3 from "./step3";
export default {
  props: ["Bid", "books", "activeId", "source"],
  components: {
    ViewBook,
    Step1,
    Step2,
    Step3
  },
  data() {
    return {
      current: 0,
      updateBookModal: false,
      showBookDetail: true,
      bookDetail: [],
      viewId: 0,
      pageNum: 1,
      pageSize: 8,
      isEmpty: false,
      total: 0,
      showBtn: true,
      isView: false,
      flag: false
    };
  },
  created() {
    this.queryBookDetail();
  },
  methods: {
    queryBookDetail() {
      this.$api
        .post("/member/media/findMediaBookList", {
          media_id: this.Bid,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        })
        .then(res => {
          this.bookDetail = res.data;
          this.flag = true;
          this.total = this.bookDetail.length;
          this.$emit("getTotal", this.total);
          if (this.bookDetail.length === 0) {
            this.isEmpty = true;
            this.flag = false;
          }
        });
    },
    viewBook(index) {
      this.viewId = index;
      console.log(this.viewId);
      this.showBookDetail = false;
      this.$emit("getShowBookDetail", this.showBookDetail);
    },
    pageChange(page) {
      this.pageNum = page;
      this.bookDetail = [];
      this.queryBookDetail();
    },
    showDetailBtn() {
      this.showBookDetail = true;
      this.showBtn = true;
      this.$emit("getShowBookDetail", this.showBookDetail);
    },
    showAllBtn() {
      this.showBtn = false;
    },
    readBook() {
      this.isView = true;
    },
    getIsView(getIsView) {
      this.isView = getIsView;
    },
    // 编辑图书
    updateBook() {
      this.updateBookModal = true;
    },
    preStep() {
      this.current -= 1;
    },
    next() {
      switch (this.current) {
        case 0:
          var s1 = this.$refs.step1.handleSubmit("mydynamic");
          if (s1) {
            this.current += 1;
            // this.source = this.$refs.step1.mydynamic.source;
          } else if (s1 === false) {
            this.$Message.error("请检查是否完善表单");
          }
          break;
        case 1:
          var s2 = this.$refs.step2.handleSubmit("mydynamic");
          if (s2) {
            this.current += 1;
          } else if (s2 === false) {
            this.$Message.error("请检查是否完善表单");
          }
          break;
      }
    },
    upCancel() {
      this.current = 0;
    },
    formSubmit() {
      if (this.current === 2) {
        var s3 = this.$refs.step3.handleSubmit("mydynamic");
        if (s3) {
          let mydynamic = Object.assign(
            { media_id: this.$refs.step1.mydynamic.toWhere },
            { flag: 1 },
            this.$refs.step1.mydynamic,
            this.$refs.step2.mydynamic,
            this.$refs.step3.mydynamic
          );
          // console.log('mydynamic',mydynamic)
          mydynamic.book_data.forEach(element => {
            element.file = [];
            element.children.forEach(res => {
              if (typeof res.file === String) {
                let name = res.file_name;
                let file = res.file;
                res.file = [];
                res.file.push({
                  name: name,
                  src: file
                });
                element.fileName = name
              }
            });
          });
          console.log(mydynamic);
          this.$api.post("/member/media/saveBookInfo", mydynamic).then(res => {
            if (res.code === 200) {
              this.updateBookModal = false;
              this.showBookDetail = true;
              this.queryBookDetail();
              this.$Message.success("修改成功！");
              this.$emit("getShowBookDetail", this.showBookDetail);
              this.current = 0;
            } else {
              this.$Message.error("修改失败！");
            }
          });
        } else {
          this.$Message.error("请检查是否完善表单");
        }
      }
    },
    deleteBook() {
      this.$Modal.confirm({
        title: "操作提示",
        content: "<p>是否确认删除这个文件？</p>",
        onOk: () => {
          // /member/media/deleteMediaBook/{id}
          this.$api
            .get(
              "/member/media/deleteMediaBook/" + this.bookDetail[this.viewId].id
            )
            .then(res => {
              if (res.code === 200) {
                this.$Message.success("删除成功！");
              } else {
                this.$Message.error("删除失败！");
              }
              this.showBookDetail = true;
              this.$emit("getShowBookDetail", this.showBookDetail);
              this.queryBookDetail();
            });
        },
        onCancel: () => {}
      });
    }
  },
  filters: {
    getTime(val) {
      if (val === undefined) {
        return val;
      } else return val.slice(0, 10);
    },
    getTag(val) {
      return val.join("、");
    }
  }
};
</script>
<style scoped lang='scss'>
.button-area {
  padding: 21px;
  background: #ffffff;
  width: 1000px;
  button {
    margin-right: 14px;
  }
}
.main-body {
  width: 1000px;
  padding-bottom: 30px;
}
.photo-content {
  border: 1px solid #bebebe;
  transition: 0.5s;
  &:hover {
    cursor: pointer;
    box-shadow: 0px 4px 8px 4px rgba(0, 0, 0, 0.15);
  }
  p {
    text-align: center;
    height: 30px;
    font-size: 14px;
    line-height: 30px;
  }
}
.getimg {
  width: 100%;
  padding: 4px;
}
.getimg1 {
  width: 151px;
}
.form-bar {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  height: 30px;
  margin-top: 25px;
}
.left-bar {
  width: 6px;
  height: 18px;
  background: #56b07d;
  line-height: 30px;
  margin-left: 45px;
  margin-right: 5px;
}
.form-bar h4 {
  font-family: PingFangSC-Medium;
  color: #4a4a4a;
  font-weight: bold;
  font-size: 16px;
  margin-right: 20px;
}
.returnBtn span {
  font-family: PingFangSC-Regular;
  color: #4a4a4a;
  font-size: 16px;
}
.book-border {
  border-bottom: 1px dashed #a19292;
}
.bookData p {
  height: 40px;
  line-height: 40px;
  font-size: 14px;
  font-family: PingFangSC-Regular;
  color: #4a4a4a;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellispsis;
}
.bookBtnArea button {
  margin-right: 10px;
  width: 84px !important;
}
.book_title {
  font-weight: bold;
  font-size: 14px;
}
.showAll {
  font-family: PingFangSC-Medium;
  color: #56b07d;
  font-size: 14px;
  width: 84px;
  height: 20px;
  text-align: center;
  line-height: 20px;
  margin: 17px 0 33px 42px;
  &:hover {
    cursor: pointer;
  }
}
</style>
<style lang="less">
.vertical-center-modal {
  display: flex;
  align-items: center;
  justify-content: center;

  .ivu-modal {
    top: 0;
  }
}
</style>