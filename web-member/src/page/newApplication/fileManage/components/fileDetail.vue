<template>
  <div>
    <div style="background:#f5f5f5">
      <!-- <div >点击复制</div> -->
      <Row class="main-body">
        <Col span="6" class="file-content" v-for="(item,index) in fileDetail" :key="index">
          <img src="../../../../../static/datas/img/myStyle/wjj.png" class="play-img">
          <Dropdown style="margin-left: 20px" placement="bottom-end" class="setBtn">
            <a
              href="javascript:void(0)"
              @click.stop="preventBtn"
              @mouseenter="getCopyIndex(index)"
            >设置</a>
            <DropdownMenu slot="list">
              <DropdownItem name="edit" @click.native.stop="setEdit(index)">
                <Icon type="md-create" style="padding-right:10px"/>编辑
              </DropdownItem>
              <DropdownItem name="delete" @click.native.stop="setDelete(index)">
                <Icon type="ios-trash" style="padding-right:10px"/>删除
              </DropdownItem>
              <DropdownItem name="delete" @click.native.stop="setDownload(index)">
                <Icon type="md-download" style="padding-right:10px"/>下载
              </DropdownItem>
              <DropdownItem name="delete">
                <div :data-clipboard-text="item.mediaUrl" class="copy" @click="getCopy">
                  <Icon type="md-share" style="padding-right:10px"/>引用
                </div>
              </DropdownItem>
            </DropdownMenu>
          </Dropdown>
          <p>{{fileDetail[index].name}}</p>
        </Col>
      </Row>

      <Row class="mt30">
        <div v-if="fileDetail.length !== 0">
          <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange"/>
        </div>
      </Row>
    </div>
    <div v-if="isEmpty">
      <h2>该文件夹空空如也，请上传文件！</h2>
    </div>
    <!-- 模态框——编辑文件 -->
    <Modal title="编辑文件" v-model="editModal" class-name="vertical-center-modal">
      <Form label-position="left" :label-width="100">
        <FormItem label="文件名">
          <Input v-model="formlist.input1"></Input>
        </FormItem>
        <FormItem label="文件描述">
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
        <Button @click="editModal = false">取消</Button>
        <Button type="primary" @click="editBtn">保存</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import Clipboard from "clipboard";
export default {
  components: {
    Clipboard
  },
  data() {
    return {
      footerHide: true,
      formlist: {
        input1: "",
        input2: "",
        input3: ""
      },
      editModal: false,
      pageNum: 1,
      pageSize: 8,
      total: 0,
      activeId: 0,
      fileDetail: [],
      isEmpty: false,
      copyFile: "",
      photoTime: ""
    };
  },
  props: ["Fid", "author"],
  methods: {
    queryFileDetail() {
      this.$api
        .post("/member/media/listMediaLibraryDetail", {
          mediaId: this.Fid,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        })
        .then(res => {
          this.fileDetail = res.data;
          console.log(this.fileDetail);
          this.total = res.total;
          this.$emit("getTotal", this.total);
          if (this.fileDetail.length === 0) {
            this.isEmpty = true;
          }
        });
    },
    setEdit(index) {
      this.activeId = index;
      this.formlist.input1 = this.fileDetail[index].name;
      this.formlist.input2 = this.fileDetail[index].mediaDescribe;
      this.formlist.input3 =
        this.fileDetail[index].author === ""
          ? this.author
          : this.fileDetail[index].author;
      this.photoTime =
        this.fileDetail[index].photoTime === ""
          ? this.fileDetail[index].createTime
          : this.fileDetail[index].photoTime;
      this.editModal = true;
    },
    //删除视频
    setDelete(index) {
      this.$Modal.confirm({
        title: "操作提示",
        content: "<p>是否确认删除这个文件？</p>",
        onOk: () => {
          this.$api
            .get(
              "/member/media/deleteMediaLibraryDetail/" +
                this.fileDetail[index].id
            )
            .then(response => {
              if (response.data === 1) {
                this.queryFileDetail();
                this.$Message.info("删除成功");
              }
            });
        },
        onCancel: () => {}
      });
    },
    //引用链接
    setCopy(index) {
      this.copyFile = this.fileDetail[index].mediaUrl;
      console.log(this.copyFile);
    },
    // 下载文件
    setDownload(index) {
      window.open(this.fileDetail[index].mediaUrl);
    },
    pageChange(page) {
      this.pageNum = page;
      this.fileDetail = [];
      this.queryFileDetail();
    },
    editBtn() {
      this.$api
        .post("/member/media/updateMediaLibraryDetail", {
          id: this.fileDetail[this.activeId].id,
          name: this.formlist.input1,
          mediaDescribe: this.formlist.input2,
          author: this.formlist.input3,
          photoTime: this.photoTime
        })
        .then(res => {
          if (res.code === 200) {
            this.$Message.success("修改名称成功！");
            this.formlist.input1 = "";
            this.formlist.input2 = "";
            this.formlist.input3 = "";
            this.photoTime = "";
            this.queryFileDetail();
            this.editModal = false;
          } else {
            this.$Message.error("修改名称失败！");
          }
        });
    },
    //阻止事件冒泡
    preventBtn() {},
    getCopy() {
      let clipboard = new Clipboard(".copy");
      this.$Message.success("复制成功！");
    },
    getCopyIndex(index) {
      this.activeId = index;
    },
    getPhotoTime(val) {
      this.photoTime = val;
    }
  },
  mounted() {},
  created() {
    this.queryFileDetail();
  }
};
</script>

<style scoped lang='scss'>
.main-body {
  width: 1016px;
  background: #f5f5f5;
}
.file-content {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  left: -8px;
  width: 238px;
  height: 286px;
  margin: 16px 8px 0;
  background: #ffffff;
  transition: 0.3s;
  &:hover {
    cursor: pointer;
    box-shadow: 0px 2px 12px 0px rgba(0, 0, 0, 0.11);
    .setBtn {
      display: block;
      position: absolute;
      background: #f5f5f5;
      opacity: 0.95;
      height: 24px;
      top: 5%;
      right: 5%;
    }
  }
  .play-img {
    width: 100%;
    height: 238px;
  }
  p {
    height: 48px;
    line-height: 48px;
    font-family: PingFangSC-Regular;
    color: #4a4a4a 100%;
    font-size: 14px;
    padding-left: 11px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
  .setBtn {
    display: none;
    a {
      width: 36px;
      padding-left: 5px;
      display: inline-block;
      font-family: PingFangSC-Regular;
      color: #4a4a4a !important;
    }
  }
}
.play-btn {
  width: 50px;
  z-index: 10;
  height: 40px;
  background: rgba(255, 255, 255, 0.3);
  position: absolute;
  left: 41%;
  top: 38%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 6px;
}
</style>
