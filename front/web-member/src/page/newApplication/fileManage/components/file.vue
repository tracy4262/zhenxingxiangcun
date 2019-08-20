<!--
//     作者：wh
//     时间：2019-1-21
//     描述：文件组件
// -->
<template>
  <div>
    <!-- 循环文件夹 -->
    <div v-if="fileShow" style="background:#f5f5f5">
      <div class="button-area">
        <Button :type="buttonIndex === 0 ? 'primary' : null" @click="uploadFileBtn">＋上传文件</Button>
        <Button :type="buttonIndex === 1 ? 'primary' : null" @click="newProject">新建文件夹</Button>
      </div>
      <Row class="main-body">
        <Col span="6" class="photo-content" v-for="(item,index) in files" :key="index">
          <div class="photo-top">
            <img
              src="../../../../../static/datas/img/myStyle/wjj.png"
              class="getimg"
              @click="lookFiles(index)"
            >
            <p>{{item.mediaName}}</p>
          </div>

          <div class="btn-edit" @click="editFile(index)">
            <div>编辑</div>
          </div>
        </Col>
      </Row>

      <Row class="mt30">
        <div v-if="files.length !== 0">
          <Page
            :total="pagetotal"
            :page-size="pageSize"
            :current="pageNum"
            @on-change="pageChange"
          />
        </div>
      </Row>
    </div>

    <!-- 文件目录 -->
    <div v-else>
      <div class="button-area">
        <Row>
          <Col span="4">
            <img
              src="../../../../../static/datas/img/myStyle/wjj.png"
              style="width:120px;height:80px"
            >
          </Col>
          <Col span="18">
            <h2>{{files[activeId].mediaName}}</h2>
            <p>共{{total}}个</p>
            <p>{{files[activeId].mediaDescribe}}</p>
          </Col>
        </Row>
        <div class="mt20" style="margin-left:150px">
          <Button :type="buttonIndex2 === 0 ? 'primary' : null" @click="uploadFileBtn">＋上传课件</Button>
          <Button :type="buttonIndex2 === 1 ? 'primary' : null" @click="editFile(activeId)">编辑</Button>
          <Button @click="deleteBtn">删除</Button>
          <Button @click="fileShow = true">返回</Button>
        </div>
      </div>
      <fileDetail :Fid="Fid" :author="author" ref="child1" @getTotal="getByTotal"></fileDetail>
    </div>
    <!-- 上传文件模态框 -->
    <Modal v-model="uploadFile" title="上传文件" width="800" class-name="modal1 vertical-center-modal">
      <div style="min-height:300px">
        <Row type="flex" align="middle">
          <label>
            <p style="font-size:16px" class="pr20">上传到</p>
          </label>
          <Select class="select1" size="large" @on-change="getindex">
            <Option
              v-for="(item,index) in selFile"
              :key="index"
              :value="item.mediaId"
              class="option1"
            >{{item.mediaName}}</Option>
          </Select>
        </Row>
        <vupload
          class="mt20"
          style="margin-left:68px"
          :buttonText="buttonText"
          :format="format"
          :pictureSize="pictureSize"
          @on-getPictureList="getPictureList($event)"
          :total="100"
          :hint="'支持拓展名称：pdf/doc/txt/ppt/pptx'"
        ></vupload>
      </div>

      <div slot="footer">
        <Button type="text" @click="uploadFile = false">取消</Button>
        <Button type="primary" @click="uploadSubmit">确认</Button>
      </div>
    </Modal>
    <!-- 模态框——新建文件夹 -->
    <Modal
      title="新建文件夹"
      v-model="newFileModal"
      class-name="vertical-center-modal"
      @on-cancel="editCancel"
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
      <div slot="footer">
        <Button @click="newFileModal = false">取消</Button>
        <Button type="primary" @click="newFileBtn">保存</Button>
      </div>
    </Modal>
    <!-- 模态框——编辑文件夹 -->
    <Modal
      title="编辑文件夹"
      v-model="editModal"
      class-name="vertical-center-modal"
      @on-cancel="editCancel"
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
        <Button type="primary" @click="editBtn">保存</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import vupload from "~components/vui-upload";
import fileDetail from "../components/fileDetail";
export default {
  components: {
    fileDetail,
    vupload
  },
  data() {
    return {
      fileShow: true,
      files: [],
      pagetotal: 0,
      pageNum: 1,
      pageSize: 8,
      formlist: {
        input1: "",
        input2: ""
      },
      selFile: [],
      total: 0,
      buttonText: "上传文件",
      format: "pdf/doc/txt/ppt/pptx",
      pictureSize: 100,
      Fid: 0, //自动分配的ID
      activeId: 0, //激活的ID
      editModal: false,
      newFileModal: false,
      buttonIndex: 0,
      buttonIndex2: 0,
      uploadFile: false,
      list: [], //上传视频文件夹临时集合
      photoTime: "",
      author: ""
    };
  },
  methods: {
    //查询文件夹
    queryFiles() {
      this.$api
        .post("/member/media/listMediaLibrary", {
          mediaType: 3,
          account: this.$user.loginAccount,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        })
        .then(res => {
          this.files = res.data;
          this.pagetotal = res.total;
        });
    },
    getPhotoTime(val) {
      this.photoTime = val;
    },
    //翻页
    pageChange(page) {
      this.pageNum = page;
      this.files = [];
      this.queryFiles();
    },
    lookFiles(index) {},
    //编辑文件夹
    editFile(index) {
      this.editModal = true;
      this.activeId = index;
      this.Fid = this.files[index].mediaId;
      this.formlist.input1 = this.files[index].mediaName;
      this.formlist.input2 = this.files[index].mediaDescribe;
      this.formlist.input3 =
        this.files[index].author === ""
          ? this.author
          : this.files[index].author;
      this.photoTime =
        this.files[index].photoTime === ""
          ? this.files[index].createTime
          : this.files[index].photoTime;
      this.buttonIndex2 = 1;
    },

    //上传文件按钮
    uploadFileBtn() {
      this.buttonIndex = 0;
      this.buttonIndex2 = 0;
      this.uploadFile = true;
    },
    newProject() {
      this.newFileModal = true;
      this.buttonIndex = 1;
      this.formlist.input1 = "";
      this.formlist.input2 = "";
      this.formlist.input3 = this.author;
    },
    //上传列表获取下拉菜单
    getindex(val) {
      this.Fid = val;
    },
    //确认上传
    uploadSubmit() {
      if (this.list.length === 0) {
        this.$Message.error("上传的视频不能为空！");
      } else if (this.Mid === 0) {
        this.$Message.error("请选择一个文件夹！");
      } else {
        this.$api
          .post("/member/media/saveMediaLibraryDetail", {
            mediaId: this.Fid,
            mediaUrl: this.list
          })
          .then(res => {
            this.uploadFile = false;
            this.queryFiles();
            if (this.fileShow === false) {
              this.$refs.child1.queryFileDetail();
            }
          });
      }
    },
    newFileBtn() {
      if (this.formlist.input1 === "") {
        this.$Message.error("文件夹名不能为空！");
      } else {
        this.$api
          .post("/member/media/saveMediaLibrary", {
            mediaName: this.formlist.input1,
            mediaDescribe: this.formlist.input2,
            author: this.formlist.input3,
            // photoTime: this.photoTime,
            mediaType: 3,
            account: this.$user.loginAccount
          })
          .then(response => {
            if (response.data === 1) {
              this.queryFiles();
              this.$Message.info("新建成功");
              this.newFileModal = false;
              this.queryFilesAll()
              this.formlist.input1 = "";
              this.formlist.input2 = "";
              this.formlist.input3 = "";
            }
          });
      }
    },
    deleteBtn() {
      this.$Modal.confirm({
        title: "操作提示",
        content: "<p>是否确认删除这个文件夹？</p>",
        onOk: () => {
          this.$api
            .get("/member/media/deleteMediaLibrary/" + this.Fid)
            .then(response => {
              if (response.data === 1) {
                this.queryFiles();
                this.fileShow = true;
                this.$Message.info("删除成功");
                this.editModal = false;
              }
            });
        },
        onCancel: () => {}
      });
    },
    editBtn() {
      this.$api
        .post("/member/media/updateMediaLibrary", {
          mediaId: this.Fid,
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
            this.queryFiles();
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
    //上传控件部分
    getPictureList($event) {
      var arr = [];
      $event.forEach(element => {
        if (element.response) {
          arr.push({
            name: element.name,
            url: element.response.data.picName
          });
        }
      });
      // console.log($event);
      this.list = arr;
    },
    //查看视频文件夹
    lookFiles(index) {
      this.fileShow = false;
      this.activeId = index;
      this.Fid = this.files[index].mediaId;
    },
    // 获取子组件的总数
    getByTotal(getTotal) {
      this.total = getTotal;
    },
    queryFilesAll() {
      this.$api
        .post("/member/media/listMediaLibrary", {
          mediaType: 3,
          account: this.$user.loginAccount,
          pageNum: 1,
          pageSize: 9999
        })
        .then(res => {
          this.selFile = res.data;
        });
    }
  },
  created() {
    this.queryFiles();
    this.queryFilesAll()
    this.$api
      .post("/member/login/findCurrentUser", {
        account: this.$user.loginAccount
      })
      .then(res => {
        this.author = res.data.displayName;
      });
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
.select1 {
  width: 260px;
}
</style>
