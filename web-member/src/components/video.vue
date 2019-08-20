<template>
  <div class="mt20 pub-video">
    <!-- 上传视频 -->
    <Row>
      <Col span="10" class="up-video1" v-for="(item,index) in videoList" :key="index">
        <video :src="item.url" width="300" height="112%" controls="controls"/>
        <Button
          type="default"
          shape="circle"
          icon="close-round"
          @click.native="handleRemove(item)"
          class="demo-upload-list-cover"
        ></Button>
        <p class="ell">{{item.name}}</p>
        <Row style="margin-left: -60px;margin-top: 20px;">
          <Col span="6">
            <span>描述</span>
          </Col>
          <Col span="16">
            <Input
              type="textarea"
              @on-change="saveDescribe"
              v-model="item.describe"
              style="width: 300px;"
            />
          </Col>
        </Row>
      </Col>
    </Row>
    <Upload
      :show-upload-list="false"
      name="upfile"
      :max-size="1024000"
      :on-success="handleSuccess"
      :on-exceeded-size="handleMaxSize"
      :on-format-error="handleFormatError"
      :format="['avi','mp4','mkv','rmvb','kux']"
      multiple
      type="drag"
      :action="action"
      style="margin-top: 30px;"
    >
      <div ref="uploadButton">
        <Button type="primary">选择视频</Button>
        <p style="color: red;">( 仅支持avi、mp4、mkv、rmvb、kux格式 )</p>
      </div>
    </Upload>

    <!-- 播放视频 -->
    <Modal
      :transfer="false"
      v-model="playVideoShow"
      @on-ok="playVideoFolderOk()"
      @on-cancel="playVideoFolderCancel()"
      title="播放视频"
      width="600px"
      height="300px"
      :mask-closable="false"
    >
      <d-player ref="player" @play="play" :video="video" :loop="false"></d-player>
    </Modal>
  </div>
</template>

<script>
import VueDPlayer from "~components/vuedplayer";
import api from "~api";

export default {
  name: "video",
  components: {
    "d-player": VueDPlayer
  },
  data() {
    return {
      action: `${this.$url.upload}/upload/up`,
      loop: false,
      player: {},
      video: {
        url: ""
      },
      videoList: [],
      result: [],
      playVideoShow: false
    };
  },
  mounted() {
    this.player = this.$refs.player.dp;
  },
  methods: {
    //保存描述信息
    saveDescribe() {
      this.$emit("saveDescribe", this.videoList);
    },
    //点击上传
    clickUp() {
      this.$refs.uploadButton.click();
    },
    play() {
      this.player.play();
    },

    playVideo(url) {
      this.player.video.src = url;
      this.playVideoShow = true;
    },
    playVideoFolderOk() {
      this.playVideoShow = false;
    },
    playVideoFolderCancel() {
      this.playVideoShow = false;
    },
    // 上传视频
    handleSuccess(response, file, fileList) {
      if (response.code === 500) {
        this.$Message.error("上传失败!");
      } else {
        this.$Message.success("上传成功!");
        this.videoList.push({
          url: "http:" + response.data.picName,
          describe: "",
          type: 0
        });
        this.saveDescribe();
        console.log("e1", response, this.videoList, fileList);
      }
    },
    // 删除视频
    handleRemove(item) {
      this.videoList.splice(this.videoList.indexOf(item), 1);
      this.saveDescribe();
      console.log("del", this.videoList);
    },
    // 视频大小限制
    handleMaxSize(file) {
      this.$Message.success("视频  " + file.name + " 过长，应不超过100M。");
    },
    // 视频格式限制
    handleFormatError(file) {
      this.$Message.success(
        "视频 " +
          file.name +
          " 格式不正确，请选择avi、mp4、mkv、rmvb、kux格式。"
      );
    }
  }
};
</script>
<style >
.pub-video .video-play {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate3d(-50%, -50%, 0);
  font-size: 34px;
  color: #fff;
}

.pub-video .video-circle {
  position: absolute;
  top: 50%;
  left: 48.5%;
  transform: translate3d(-50%, -50%, 0);
  font-size: 55px;
  color: #fff;
}

.pub-video .dplayer-video {
  height: 300px;
}
.pub-video .video-circle {
  position: absolute;
  top: 50%;
  left: 48.5%;
  transform: translate3d(-50%, -50%, 0);
  font-size: 55px;
  color: #fff;
}
.pub-video .demo-upload-list-cover {
  background-color: #00bbff;
}
</style>
<style scoped lang="scss">
.pub-video .up-video1 {
  display: inline-block;
  width: 150px;
  height: 150px;
  text-align: center;
  border-radius: 4px;
  background: #fff;
  position: relative;
  margin-right: 100px;
  margin-top: 10px;
  margin-bottom: 140px;
  vertical-align: top;
}
</style>