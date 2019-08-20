<!--
//     作者：wh
//     时间：2019-1-16
//     描述：视频组件
// -->
<template>
  <div>
    <!-- 循环视频文件夹 -->
    <div v-if="videoShow" style="background:#f5f5f5">
      <div class="button-area">
        <Button :type="buttonIndex === 0 ? 'primary' : null" @click="uploadVideoBtn">＋上传视频</Button>
        <Button :type="buttonIndex === 1 ? 'primary' : null" @click="newProject">新建文件夹</Button>
      </div>
      <Row class="main-body">
        <Col span="6" class="photo-content" v-for="(item,index) in videos" :key="index">
          <div class="photo-top">
            <img
              src="../../../../../static/datas/img/myStyle/videoCover1.png"
              class="getimg"
              @click="lookVideos(index)"
            >
            <p>{{item.mediaName}}</p>
          </div>

          <div class="btn-edit" @click="editVideo(index)">
            <div>编辑</div>
          </div>
        </Col>
      </Row>

      <Row class="mt30">
        <div v-if="videos.length !== 0">
          <Page
            :total="pagetotal"
            :page-size="pageSize"
            :current="pageNum"
            @on-change="pageChange"
          />
        </div>
      </Row>
    </div>

    <!-- 视频课件 -->
    <div v-else>
      <div class="button-area">
        <Row>
          <Col span="4">
            <img
              src="../../../../../static/datas/img/myStyle/videoCover1.png"
              style="width:120px;height:80px"
            >
          </Col>
          <Col span="18">
            <h2>{{videos[activeId].mediaName}}</h2>
            <p>共{{total}}个</p>
            <p>{{videos[activeId].mediaDescribe}}</p>
          </Col>
        </Row>
        <div class="mt20" style="margin-left:150px">
          <Button :type="buttonIndex2 === 0 ? 'primary' : null" @click="uploadVideoBtn">＋上传视频</Button>
          <Button :type="buttonIndex2 === 1 ? 'primary' : null" @click="editVideo(activeId)">编辑</Button>
          <Button @click="deleteBtn">删除</Button>
          <Button @click="videoShow = true">返回</Button>
        </div>
      </div>
      <videoDetail :Mid="Mid" :author="author" ref="child1" @getTotal="getByTotal"></videoDetail>
    </div>
    <!-- 上传视频模态框 -->
    <Modal v-model="uploadVideo" title="上传视频" width="800" class-name="vertical-center-modal">
      <div style="min-height:300px">
        <Row type="flex" align="middle">
          <label>
            <p style="font-size:16px" class="pr20">上传到</p>
          </label>
          <Select class="select1" size="large" @on-change="getindex">
            <Option
              v-for="(item,index) in selVideo"
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
          :multiple="false"
          :hint="'支持拓展名称：mp4 avi mkv rmvb kux'"
        ></vupload>
      </div>

      <div slot="footer">
        <Button type="text" @click="uploadVideo = false">取消</Button>
        <Button type="primary" @click="uploadSubmit">确认</Button>
      </div>
    </Modal>

    <!-- 模态框——新建视频文件夹 -->
    <Modal
      title="新建文件夹"
      v-model="newVideoModal"
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
        <Button @click="newVideoModal = false">取消</Button>
        <Button type="primary" @click="newVideoBtn">保存</Button>
      </div>
    </Modal>
    <!-- 模态框——编辑视频文件夹 -->
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
import videoDetail from "../components/videoDetail";
import { setTimeout } from "timers";
export default {
  components: {
    vupload,
    videoDetail
  },
  data() {
    return {
      formlist: {
        input1: "",
        input2: "",
        input3: ""
      },
      selVideo: [],
      photoTime: "",
      author: "",
      total: 0,
      buttonText: "上传视频",
      format: "avi/mp4/mkv/rmvb/kux",
      pictureSize: 1000,
      Mid: 0, //自动分配的ID
      activeId: 0, //激活的ID
      videoShow: true,
      editModal: false,
      newVideoModal: false,
      buttonIndex: 0,
      buttonIndex2: 0,
      uploadVideo: false,
      videos: [], //视频文件夹
      pageNum: 1,
      pageSize: 8,
      pagetotal: 0,
      list: [] //上传视频文件夹临时集合
    };
  },
  methods: {
    //查询视频文件夹
    queryVideos() {
      this.$api
        .post("/member/media/listMediaLibrary", {
          mediaType: 2,
          account: this.$user.loginAccount,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        })
        .then(res => {
          this.videos = res.data;
          this.pagetotal = res.total;
        });
    },
    getPhotoTime(val) {
      this.photoTime = val;
    },
    newProject() {
      this.newVideoModal = true;
      this.buttonIndex = 1;
      this.formlist.input1 = "";
      this.formlist.input2 = "";
      this.formlist.input3 = this.author;
    },
    pageChange(page) {
      this.pageNum = page;
      this.videos = [];
      this.queryVideos();
    },
    uploadVideoBtn() {
      this.buttonIndex = 0;
      this.buttonIndex2 = 0;
      this.uploadVideo = true;
    },
    //编辑视频文件夹
    editVideo(index) {
      this.editModal = true;
      this.activeId = index;
      this.Mid = this.videos[index].mediaId;
      this.formlist.input1 = this.videos[index].mediaName;
      this.formlist.input2 = this.videos[index].mediaDescribe;
      this.formlist.input3 =
        this.videos[index].author === ""
          ? this.author
          : this.videos[index].author;
      this.photoTime =
        this.videos[index].photoTime === ""
          ? this.videos[index].createTime
          : this.videos[index].photoTime;
      this.buttonIndex2 = 1;
    },
    editBtn() {
      this.$api
        .post("/member/media/updateMediaLibrary", {
          mediaId: this.Mid,
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
            this.queryVideos();
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
    newVideoBtn() {
      if (this.formlist.input1 === "") {
        this.$Message.error("文件夹名不能为空！");
      } else {
        this.$api
          .post("/member/media/saveMediaLibrary", {
            mediaName: this.formlist.input1,
            mediaDescribe: this.formlist.input2,
            author: this.formlist.input3,
            mediaType: 2,
            account: this.$user.loginAccount
          })
          .then(response => {
            if (response.data === 1) {
              this.queryVideos();
              this.$Message.info("新建成功");
              this.queryVideosAll()
              this.newVideoModal = false;
              this.formlist.input1 = "";
              this.formlist.input2 = "";
              this.formlist.input3 = "";
            }
          });
      }
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
      this.list = arr;
    },
    //确认上传按钮
    uploadSubmit() {
      if (this.list.length === 0) {
        this.$Message.error("上传的视频不能为空！");
      } else if (this.Mid === 0) {
        this.$Message.error("请选择一个文件夹！");
      } else {
        this.$api
          .post("/member/media/saveMediaLibraryDetail", {
            mediaId: this.Mid,
            mediaUrl: this.list
          })
          .then(res => {
            this.uploadVideo = false;
            this.queryVideos();
            if (this.videoShow === false) {
              this.$refs.child1.queryVideoDetail();
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
            .get("/member/media/deleteMediaLibrary/" + this.Mid)
            .then(response => {
              if (response.data === 1) {
                this.queryVideos();
                this.videoShow = true;
                this.$Message.info("删除成功");
                this.editModal = false;
              }
            });
        },
        onCancel: () => {}
      });
    },
    //上传列表获取下拉菜单
    getindex(val) {
      this.Mid = val;
    },

    //查看视频文件夹
    lookVideos(index) {
      this.videoShow = false;
      this.activeId = index;
      this.Mid = this.videos[index].mediaId;
    },
    // 获取子组件的总数
    getByTotal(getTotal) {
      this.total = getTotal;
    },
    queryVideosAll() {
      this.$api
        .post("/member/media/listMediaLibrary", {
          mediaType: 2,
          account: this.$user.loginAccount,
          pageNum: 1,
          pageSize: 9999
        })
        .then(res => {
          this.selVideo = res.data;
        });
    }
  },
  created() {
    this.queryVideos();
    this.queryVideosAll()
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
    cursor: pointer;
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
  background: #ffffff;
  border: 1px solid rgba(232, 232, 232, 1);
}
</style>
