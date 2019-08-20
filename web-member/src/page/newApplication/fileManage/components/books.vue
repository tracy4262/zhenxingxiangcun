<!--
//     作者：wh
//     时间：2019-1-23
//     描述：图书组件
// -->
<template>
  <div>
    <!-- 循环图书文件夹 -->
    <div v-if="bookShow" style="background:#f5f5f5">
      <div class="button-area">
        <Button :type="buttonIndex === 0 ? 'primary' : null" @click="updateBookBtn">＋上传图书</Button>
        <Button :type="buttonIndex === 1 ? 'primary' : null" @click="newProject">新建文件夹</Button>
      </div>
      <Row class="main-body">
        <Col span="6" class="photo-content" v-for="(item,index) in books" :key="index">
          <div class="photo-top" @click="lookBook(index)">
            <img src="../../../../../static/datas/img/myStyle/wjj.png" class="getimg">
            <p>{{item.mediaName}}</p>
          </div>
          <div class="btn-edit" @click="editBook(index)">
            <div>编辑</div>
          </div>
        </Col>
      </Row>

      <Row class="mt30">
        <div v-if="books.length !== 0">
          <Page
            :total="pagetotal"
            :page-size="pageSize"
            :current="pageNum"
            @on-change="pageChange"
          />
        </div>
      </Row>
    </div>
    <!-- 循环图书详情 -->
    <div v-else>
      <div class="button-area" v-if="showBookDetail">
        <Row>
          <Col span="4">
            <img
              src="../../../../../static/datas/img/myStyle/wjj.png"
              style="width:120px;height:80px"
            >
          </Col>
          <Col span="18">
            <h2>{{books[activeId].mediaName}}</h2>
            <p>共{{total}}个</p>
            <p>{{books[activeId].mediaDescribe}}</p>
          </Col>
        </Row>
        <div class="mt20" style="margin-left:150px">
          <Button :type="buttonIndex2 === 0 ? 'primary' : null" @click="updateBookBtn">＋上传图书</Button>
          <!-- <Button :type="buttonIndex2 === 1 ? 'primary' : null">编辑</Button> -->
          <Button @click="deleteBtn">删除</Button>
          <Button @click="bookShow = true">返回</Button>
        </div>
      </div>
      <bookDetail
        :Bid="Bid"
        :activeId="activeId"
        ref="bookDetail"
        @getTotal="getByTotal"
        :books="books"
        :source="source"
        @getBookShow="getBookShow"
        @getShowBookDetail="getShowBookDetail"
      ></bookDetail>
    </div>
    <!-- 上传图书 -->
    <Modal title="发布图书" v-model="updateBookModal" width="800" @on-cancel="upCancel">
      <Steps :current="current" style="padding-left:90px" class="mt20">
        <Step title="基本信息"></Step>
        <Step title="详细详细"></Step>
        <Step title="关联信息"></Step>
      </Steps>
      <Step1 v-show="current === 0" ref="step1" :books="selBook"></Step1>
      <Step2
        v-show="current === 1"
        ref="step2"
        :source="source"
        :author="author"
        :current="current"
      ></Step2>
      <Step3 v-show="current === 2" ref="step3"></Step3>
      <div slot="footer" style="text-algin:center">
        <Button @click="preStep" v-if="current != 0">上一步</Button>
        <Button @click="next" v-if="current != 2">下一步</Button>
        <Button v-if="current === 2" @click="formSubmit">提交</Button>
      </div>
    </Modal>

    <!-- 新建图书文件夹-->
    <Modal
      title="新建文件夹"
      v-model="newBookModal"
      @on-cancel="editCancel"
      class-name="vertical-center-modal"
    >
      <Form label-position="left" :label-width="100">
        <FormItem label="文件夹名">
          <Input v-model="formlist.input1"></Input>
        </FormItem>
        <FormItem label="文件夹描述">
          <Input v-model="formlist.input2" type="textarea"></Input>
        </FormItem>
        <FormItem label="创建人">
          <Input v-model="formlist.input3"></Input>
        </FormItem>
      </Form>
      <div slot="footer" style="text-algin:center">
        <Button @click="editCancel">取消</Button>
        <Button @click="newBooksBtn">提交</Button>
      </div>
    </Modal>

    <!-- 模态框——编辑图书文件夹 -->
    <Modal
      title="编辑图书文件夹"
      v-model="editModal"
      @on-cancel="editCancel"
      class-name="vertical-center-modal"
    >
      <Form label-position="left" :label-width="100">
        <FormItem label="文件夹名">
          <Input v-model="formlist.input1"></Input>
        </FormItem>
        <FormItem label="文件夹描述">
          <Input v-model="formlist.input2" type="textarea"></Input>
        </FormItem>
        <FormItem label="创建人">
          <Input v-model="formlist.input3"></Input>
        </FormItem>
        <FormItem label="创建时间">
          <DatePicker
            type="date"
            v-model="photoTime"
            placeholder="Select date"
            format="yyyy-MM-dd"
            @on-change="getPhotoTime"
            style="width:388px"
          ></DatePicker>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button icon="ios-trash" type="text" @click="deleteBtn">删除文件夹</Button>
        <Button @click="editModal = false">取消</Button>
        <Button type="primary" @click="editSubmit">保存</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import vuiUpload from "~components/vui-upload";
import vuiBook from "~components/vuiBookList";
import vuiCropper from "~components/vuiCropper";
import vuiFilter from "~components/vuiFilter/filter";
import bookDetail from "./bookDetail";
import Step1 from "./step1";
import Step2 from "./step2";
import Step3 from "./step3";
export default {
  components: {
    vuiUpload,
    vuiBook,
    vuiCropper,
    vuiFilter,
    Step1,
    Step2,
    Step3,
    bookDetail
  },
  data() {
    return {
      formlist: {
        input1: "",
        input2: ""
      },
      process: 0,
      indexpane: 0,
      Bid: 0,
      current: 0,
      activeId: 0,
      updateBookModal: false,
      newBookModal: false,
      editModal: false,
      bookShow: true,
      books: [],
      pageNum: 1,
      pageSize: 8,
      pagetotal: 0,
      buttonIndex2: 0,
      buttonIndex: 0,
      source: "",
      total: 0,
      showBookDetail: true,
      photoTime: "",
      author: "",
      selBook: []
    };
  },

  created() {
    this.queryBooks();
    this.queryBooksAll();
    this.$api
      .post("/member/login/findCurrentUser", {
        account: this.$user.loginAccount
      })
      .then(res => {
        if (res.data.displayName) {
          this.author = res.data.displayName;
        }
      });
  },
  watch: {
    bookShow: {
      handler(newVal, val) {
        // this.bookTotal = this.books[this.Bid].length;
        if (newVal === false) {
          // console.log("this.bookTotal", this.$refs.bookDetail.total);
          console.log("this.bookTotal", this.Bid);
        }
      }
    }
  },
  methods: {
    //查询图书文件夹
    queryBooks() {
      this.$api
        .post("/member/media/listMediaLibrary", {
          mediaType: 4,
          account: this.$user.loginAccount,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        })
        .then(res => {
          this.books = res.data;
          console.log(res);
          this.pagetotal = res.total;
          console.log(res.total);
        });
    },
    queryBooksAll() {
      this.$api
        .post("/member/media/listMediaLibrary", {
          mediaType: 4,
          account: this.$user.loginAccount,
          pageNum: 1,
          pageSize: 9999
        })
        .then(res => {
          this.selBook = res.data;
        });
    },
    getPhotoTime(val) {
      this.photoTime = val;
    },
    // 获取子组件的总数
    getByTotal(getTotal) {
      this.total = getTotal;
    },
    // 上传图书按钮
    updateBookBtn() {
      this.updateBookModal = true;
      this.buttonIndex = 0;
      this.current = 0;
    },
    //新建文件夹
    newProject() {
      this.newBookModal = true;
      this.buttonIndex = 1;
      this.formlist.input1 = "";
      this.formlist.input2 = "";
      this.formlist.input3 = this.author;
    },
    newBooksBtn() {
      if (this.formlist.input1 === "") {
        this.$Message.error("文件夹名不能为空！");
      } else {
        this.$api
          .post("/member/media/saveMediaLibrary", {
            mediaName: this.formlist.input1,
            mediaDescribe: this.formlist.input2,
            author: this.formlist.input3,
            mediaType: 4,
            account: this.$user.loginAccount
          })
          .then(response => {
            if (response.data === 1) {
              this.queryBooks();
              this.queryBooksAll();
              this.$Message.info("新建成功");
              this.newBookModal = false;
              this.formlist.input1 = "";
              this.formlist.input2 = "";
              this.formlist.input3 = "";
            }
          });
      }
    },
    upCancel() {
      this.$refs.step1.handleReset("mydynamic");
      this.$refs.step2.handleReset("mydynamic");
      this.$refs.step3.handleReset("mydynamic");
    },
    //编辑图书文件夹
    editBook(index) {
      this.editModal = true;
      this.Bid = this.books[index].mediaId;
      this.activeId = index;
      this.formlist.input1 = this.books[index].mediaName;
      this.formlist.input2 = this.books[index].mediaDescribe;
      this.formlist.input3 =
        this.books[index].author === ""
          ? this.author
          : this.books[index].author;
      this.photoTime =
        this.books[index].photoTime === ""
          ? this.books[index].createTime
          : this.books[index].photoTime;
      this.buttonIndex2 = 1;
    },
    editSubmit() {
      this.$api
        .post("/member/media/updateMediaLibrary", {
          mediaId: this.Bid,
          mediaName: this.formlist.input1,
          mediaDescribe: this.formlist.input2,
          author: this.formlist.input3,
          photoTime: this.photoTime
        })
        .then(res => {
          if (res.code === 200) {
            this.$Message.success("修改成功！");
            this.formlist.input1 = "";
            this.formlist.input2 = "";
            this.formlist.input3 = "";
            this.photoTime = "";
            this.editModal = false;
            this.queryBooks();
            this.queryBooksAll();
          } else {
            this.$Message.error("修改失败！");
          }
        });
    },
    editCancel() {
      this.formlist.input1 = "";
      this.formlist.input2 = "";
      this.formlist.input3 = "";
      this.photoTime = "";
    },
    deleteBtn() {
      this.$Modal.confirm({
        title: "操作提示",
        content: "<p>是否确认删除这个文件夹？</p>",
        onOk: () => {
          this.$api
            .get("/member/media/deleteMediaLibrary/" + this.Bid)
            .then(response => {
              if (response.data === 1) {
                this.queryBooks();
                this.queryBooksAll();
                this.bookShow = true;
                this.$Message.info("删除成功");
                this.editModal = false;
              }
            });
        },
        onCancel: () => {}
      });
    },
    pageChange(page) {
      this.pageNum = page;
      this.books = [];
      this.queryBooks();
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
            this.source = this.$refs.step1.mydynamic.source;
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
    formSubmit() {
      if (this.current === 2) {
        var s3 = this.$refs.step3.handleSubmit("mydynamic");
        if (s3) {
          let mydynamic = Object.assign(
            { media_id: this.$refs.step1.mydynamic.toWhere },
            { flag: 0 },
            this.$refs.step1.mydynamic,
            this.$refs.step2.mydynamic,
            this.$refs.step3.mydynamic
          );
          this.$api.post("/member/media/saveBookInfo", mydynamic).then(res => {
            this.updateBookModal = false;
            this.queryBooks();
            this.$Message.success("上传成功！");
            this.$refs.step1.handleReset("mydynamic");
            this.$refs.step2.handleReset("mydynamic");
            this.$refs.step3.handleReset("mydynamic");
            this.$refs.bookDetail.isEmpty = false;
            if (this.bookShow === false) {
              this.$refs.bookDetail.queryBookDetail();
              // this.$refs.bookDetail.isEmpty= false
            }
          });
        } else {
          this.$Message.error("请检查是否完善表单");
        }
      }
    },
    lookBook(index) {
      this.bookShow = false;
      this.activeId = index;
      this.Bid = this.books[index].mediaId;
    },
    getBookShow(getBookShow) {
      this.bookShow = getBookShow;
    },
    getShowBookDetail(getShowBookDetail) {
      this.showBookDetail = getShowBookDetail;
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
  width: 1016px;
  background: #f5f5f5;
}
.photo-content {
  position: relative;
  left: -8px;
  width: 238px;
  height: 294px;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  margin: 22px 8px 0;
  transition: 0.3s;
  &:hover {
    box-shadow: 0px 12px 18px 4px rgba(0, 0, 0, 0.15);
  }
}
.photo-top {
  position: relative;
  &:hover {
    cursor: pointer;
  }
  p {
    position: absolute;
    top: 0%;
    color: #ffffff;
    font-size: 14px;
    margin-left: 8px;
    margin-top: 13px;
    font-family: PingFangSC-Semibold;
  }
}
.getimg {
  width: 217px;
  height: 216px;
  background: rgba(0, 0, 0, 0.06);
}
.btn-edit {
  width: 217px;
  height: 40px;
  background: #e8e8e8;
  display: flex;
  justify-content: center;
  align-items: center;
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
