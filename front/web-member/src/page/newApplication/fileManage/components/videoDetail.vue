<template>
  <div>
    <!-- 循环视频 -->
    <div style="background:#f5f5f5">
      <Row class="main-body">
        <Col
          span="6"
          class="video-content"
          v-for="(item,index) in videoDetail"
          :key="index"
          @click.native="viewVideo(index)"
        >
          <div class="play-btn">
            <Icon type="md-play" color="white" size="30"/>
          </div>
          <div class="timer">
            <p>{{videoTime[index]}}</p>
          </div>
          <div class="play-img"></div>
          <Dropdown style="margin-left: 20px" placement="bottom-end" class="setBtn">
            <a href="javascript:void(0)" @click.stop="preventBtn">设置</a>
            <DropdownMenu slot="list">
              <DropdownItem name="edit" @click.native.stop="setEdit(index)">
                <Icon type="md-create" style="padding-right:10px"/>编辑
              </DropdownItem>
              <DropdownItem name="delete" @click.native.stop="setDelete(index)">
                <Icon type="ios-trash" style="padding-right:10px"/>删除
              </DropdownItem>
            </DropdownMenu>
          </Dropdown>
          <p>{{videoDetail[index].name}}</p>
        </Col>
      </Row>

      <Row class="mt30">
        <div v-if="videoDetail.length !== 0">
          <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange"/>
        </div>
      </Row>
    </div>
    <div v-if="isEmpty">
      <h2>该文件夹空空如也，请上传视频！</h2>
    </div>
    <!-- 模态框——编辑视频 -->
    <Modal title="编辑视频" v-model="editModal" class-name="vertical-center-modal">
      <Form label-position="left" :label-width="100">
        <FormItem label="视频名">
          <Input v-model="formlist.input1"></Input>
        </FormItem>
        <FormItem label="视频描述">
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

    <!-- 播放视频 -->
    <Modal
      :transfer="false"
      v-model="playVideoShow"
      @on-cancel="playVideoFolderCancel()"
      width="600px"
      height="300px"
      :mask-closable="false"
      :footer-hide="footerHide"
    >
      <VueDPlayer
        ref="player"
        @play="play"
        :video="video"
        :loop="false"
        class="mt30"
        :lang="language"
      ></VueDPlayer>
    </Modal>
  </div>
</template>
<script>
import VueDPlayer from "~components/vuedplayer";
import { setTimeout, setInterval } from "timers";
export default {
  components: {
    VueDPlayer
  },
  data() {
    return {
      language: "zh",
      footerHide: true,
      formlist: {
        input1: "",
        input2: ""
      },
      editModal: false,
      loop: false,
      video: {
        url: ""
      },
      player: {},
      pageNum: 1,
      pageSize: 8,
      total: 0,
      activeId: 0,
      Vid: 0,
      videoDetail: [],
      videoTime: [],
      playVideoShow: false,
      isEmpty: false,
      currentTime: "",
      Dplayers: [],
      photoTime: ""
    };
  },
  props: ["Mid", "author"],
  methods: {
    queryVideoDetail() {
      this.$api
        .post("/member/media/listMediaLibraryDetail", {
          mediaId: this.Mid,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        })
        .then(res => {
          this.videoDetail = res.data;
          console.log(this.videoDetail);
          this.total = res.total;
          this.$emit("getTotal", this.total);
          if (this.videoDetail.length === 0) {
            this.isEmpty = true;
          }
        });
    },
    //编辑视频
    setEdit(index) {
      this.activeId = index;
      this.formlist.input1 = this.videoDetail[index].name;
      this.formlist.input2 = this.videoDetail[index].mediaDescribe;
      this.formlist.input3 =
        this.videoDetail[index].author === ""
          ? this.author
          : this.videoDetail[index].author;
      this.photoTime =
        this.videoDetail[index].photoTime === ""
          ? this.videoDetail[index].createTime
          : this.videoDetail[index].photoTime;
      this.editModal = true;
    },
    editBtn() {
      this.$api
        .post("/member/media/updateMediaLibraryDetail", {
          id: this.videoDetail[this.activeId].id,
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
            this.queryVideoDetail();
            this.editModal = false;
          } else {
            this.$Message.error("修改名称失败！");
          }
        });
    },
    //阻止事件冒泡
    preventBtn() {},
    setDelete(index) {
      this.$Modal.confirm({
        title: "操作提示",
        content: "<p>是否确认删除这个视频？</p>",
        onOk: () => {
          this.$api
            .get(
              "/member/media/deleteMediaLibraryDetail/" +
                this.videoDetail[index].id
            )
            .then(response => {
              if (response.data === 1) {
                this.queryVideoDetail();
                this.$Message.info("删除成功");
              }
            });
        },
        onCancel: () => {}
      });
    },
    getPhotoTime(val) {
      this.photoTime = val;
    },
    pageChange(page) {
      this.pageNum = page;
      this.videoDetail = [];
      this.queryVideoDetail();
    },
    //看视频
    viewVideo(index) {
      this.playVideoShow = true;
      this.player.video.src = "http:" + this.videoDetail[index].mediaUrl;
    },
    play() {
      this.player.play();
    },
    playVideoFolderCancel() {
      this.playVideoShow = false;
      this.player.pause();
    }
  },
  created() {
    this.queryVideoDetail();
  },
  mounted() {
    this.player = this.$refs.player.dp;
  },
  // filters: {
  //   getTimer(val) {
  //     function get(i) {
  //       if (i < 10) {
  //         return "0" + i;
  //       } else return i;
  //     }
  //     var hours = parseInt(val / 3600);
  //     var minites = parseInt((val % 3600) / 60);
  //     var seconds = parseInt(val % 60);
  //     return get(hours) + ":" + get(minites) + ":" + get(seconds);
  //   }
  // }
};
</script>

<style scoped lang='scss'>
.main-body {
  width: 1016px;
  background: #f5f5f5;
}
.video-content {
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
    box-shadow: 0px 2px 12px 0px rgba(0, 0, 0, 0.11);
    cursor: pointer;
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
    background: #434343;
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
.timer {
  position: absolute;
  width: 50px;
  height: 30px;
  background: #1f1f1f;
  right: 5%;
  bottom: 21%;
  p {
    line-height: 30px;
    text-align: center;
    color: #ffffff;
    font-size: 12px;
  }
}
</style>
