<template>
    <div>
        <!-- 视频文件夹 -->
        <template v-if="videoShow">
            <Button type="primary" class="mr10" @click="uploadVideo" v-if="isshowEdit">上传视频</Button>
            <Button type="default" @click="createdVideoFolder" v-if="isshowEdit">新建文件夹</Button>
            <!-- 循环生成视频文件夹 -->
            <Row :gutter="16" class="album mt20">
                <i-col span="6" v-for="(item,index) in videoFolderList" :key="index" class="photo-content">
                    <figure class="wrap mb10 border drop-out pointer" style="border: none;">
                        <Dropdown trigger="click" placement="left-start" class="folder-drop drop-in" v-if="isshowEdit">
                            <Icon type="ios-arrow-down" style="padding:2px 5px;"></Icon>
                            <Dropdown-menu slot="list">
                                <Dropdown-item>
                                    <div @click="videoFolderEdit(item.id,index)"><Icon type="edit"></Icon> 编辑</div>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <div @click="videoFolderDel(item.id,index)"><Icon type="trash-a"></Icon> 删除</div>
                                </Dropdown-item>
                            </Dropdown-menu>
                        </Dropdown>
                        <img src="../../../../static/datas/img/myStyle/folder.png" @click="lookVideo(item.id)" alt="">
                    </figure>
                    <p class="ell" :title="item.name">{{item.name}}</p>
                    <p class="t-grey">创建于{{item.time}}</p>
                </i-col>
            </Row>
            <Row style="text-align: center; height: 100px;">
                <Page :total="total2" @on-change="getNextPage2" :page-size="pageSize2" :current="page2" class="pages"></Page>
            </Row>
        </template>
        <!-- 视频列表页面 -->
        <template v-else>
            <Button type="primary" @click="back">返回</Button>
            <Row :gutter="16" class="album mt20">
                <div class="tip" v-if="isEmpty">该文件夹空空如也，请上传视频！</div>
                <i-col span="6" v-for="(item,index) in videoList" :key="index" class="photo-content">
                    <figure class="wrap mb10 border drop-out pointer" style="border: none;">
                        <Dropdown :transfer="true" trigger="click" placement="left-start" class="video-drop drop-in" v-if="isshowEdit">
                            <Icon type="ios-arrow-down" style="padding:2px 5px;"></Icon>
                            <Dropdown-menu slot="list">
                                <Dropdown-item>
                                    <div @click="editVideo(item.id,index)"><Icon type="edit"></Icon> 编辑</div>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <div class="btn-2" :data-clipboard-text="item.url"><Icon type="share"></Icon> 引用</div>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <div @click="delVideo(item.id,index)"><Icon type="trash-a"></Icon> 删除</div>
                                </Dropdown-item>
                            </Dropdown-menu>
                        </Dropdown>
                        <vue-share 
                            :config="{
                                sites: ['qzone', 'qq', 'wechat', 'weibo'],
                                url: 'http:'+item.url
                            }"
                            class="share">
                        </vue-share>
                        <img :src="item.src" width="100%" @click="playVideo(item.url)">
                        <!-- <Icon type="play" class="video-play" @click.native="playVideo(item.url)"></Icon>
                        <Icon type="ios-circle-outline" class="video-circle" @click.native="playVideo(item.url)"></Icon> -->
                    </figure>
                    <p class="ell" :title="item.title">{{item.title}}</p>
                    <p class="t-grey">上传于{{item.time}}</p>
                </i-col>
            </Row>
            <Row style="text-align: center; height: 100px;">
                <Page :total="total" @on-change="getNextPage" :page-size="pageSize" class="pages"></Page>
            </Row>
        </template>
        <!-- 上传视频 -->
        <Modal v-model="uploadVideoShow" title="上传视频" width="600" :mask-closable="false" :closable="false">
            <Form ref="videoUploadInfo" :model="videoUploadInfo" label-position="right" :label-width="100" :rules="ruleInline">
                <Form-item label="上传到：" prop="toWhere">
                    <i-select v-model="videoUploadInfo.toWhere" class="mb10"  style="width:200px">
                        <Option v-for="(item,index) in videoToWhereList" :value="item.value" :key="index">{{ item.label }}</Option>
                    </i-select>
                    <Button type="text" style="color: #108EE9;" size="small" @click="createdVideoFolder">创建文件夹</Button>
                </Form-item>
                <Form-item label="视频：" prop="video_list">
                    <div class="up-video" v-for="(item,index) in videoUploadInfo.video_list" :key="index">
                        <template v-if="item.status === 'finished'">
                            <img src="../../../../static/datas/img/myStyle/video.png" style="width: 60px; height:60px;">
                            <p style="line-height: 1;" class="ell" :title="item.name">{{item.name}}</p>
                            <div class="demo-upload-list-cover">
                                <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
                            </div>
                        </template>
                        <template v-else>
                            <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
                        </template>
                    </div>
                    <Upload ref="upload" :show-upload-list="false"
                            name="upfile"
                            :format="['avi','mp4','mkv','rmvb','kux']"
                            :max-size="1024000"
                            :on-success="handleSuccess"
                            :on-exceeded-size="handleMaxSize"
                            :on-format-error="handleFormatError"
                            multiple
                            type="drag"
                            :action="action"
                            style="display: inline-block;width:58px;">
                        <div style="width: 58px;height:58px;line-height: 58px;">
                            <Icon type="md-add-circle" size="20"></Icon>
                        </div>
                    </Upload>
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="uploadVideoOkCancel()">取消</Button>
                <Button type="primary" size="large" @click="uploadVideoOk()">确定</Button>
            </div>
        </Modal>
        <!-- 创建文件夹 -->
        <Modal v-model="createVideoFolderShow" title="创建文件夹" width="600" :mask-closable="false" :closable="false">
            <Form ref="videoFolderInfo" :model="videoFolderInfo" label-position="right" :label-width="120" :rules="ruleInline2">
                <Form-item label="文件夹名称：" prop="video_folder_name">
                    <Input v-model="videoFolderInfo.video_folder_name" :maxlength="15" placeholder="名称不能超过15字" />
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="createVideoFolderCancel()">取消</Button>
                <Button type="primary" size="large" @click="createVideoFolderOk()">确定</Button>
            </div>
        </Modal>
        <!-- 编辑文件夹 -->
        <Modal v-model="editVideoFolderShow" title="编辑文件夹" width="600" :mask-closable="false" :closable="false">
            <Form ref="editVideoFolderInfo" :model="editVideoFolderInfo" label-position="right" :label-width="120" :rules="ruleInline3">
                <Form-item label="文件夹名称：" prop="video_folder_name">
                    <Input v-model="editVideoFolderInfo.video_folder_name" :maxlength="15" placeholder="名称不能超过15字" />
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="editVideoFolderCancel()">取消</Button>
                <Button type="primary" size="large" @click="editVideoFolderOk()">确定</Button>
            </div>
        </Modal>
        <!-- 播放视频 -->
        <Modal v-model="playVideoShow" @on-ok="playVideoFolderOk()" @on-cancel="playVideoFolderCancel()" title="播放视频" width="600px" height="300px" :mask-closable="false">
            <d-player ref="player" @play="play" :video="video" :loop="loop" :lang="language"></d-player>
        </Modal>
        <!-- 编辑视频 -->
        <Modal v-model="editVideoShow" title="编辑视频" width="600" :mask-closable="false" :closable="false">
            <Form ref="editVideoInfo" :model="editVideoInfo" label-position="right" :label-width="100" :rules="ruleInline4">
                <Form-item label="视频名称：" prop="video_name">
                    <Input v-model="editVideoInfo.video_name" :maxlength="15" placeholder="名称不能超过15字" />
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="editVideoCancel()">取消</Button>
                <Button type="primary" size="large" @click="editVideoOk()">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import VueDPlayer from "~components/vuedplayer";
import api from "~api";
import Vue from "vue";
import Clipboard from "clipboard";
import vueShare from "~components/vueShare";

export default {
  components: {
    "d-player": VueDPlayer,
    Clipboard,
    vueShare
  },
  data() {
    const validateVideoFolderName = (rule, value, callback) => {
        if (value.replace(/(^\s+)|(\s+$)/g, "") === '') {
            callback(new Error('文件夹名称不能为空'));
        } else {
            callback();
        }
    };
    return {
      loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      isshowEdit: true,
      index: -1,
      id: -1,
      editVideoFolderIndex: -1,
      editVideoFolderId: -1,
      videoFolderId: -1,
      total: 0,
      pageSize: 20,
      page: 1,
      total2: 0,
      pageSize2: 12,
      page2: 1,
      videoShow: true,
      uploadVideoShow: false,
      createVideoFolderShow: false,
      editVideoFolderShow: false,
      playVideoShow: false,
      loop: false,
      editVideoShow: false,
      isEmpty: false,
      action: `${this.$url.upload}/upload/up`,
      videoFolderList: [],
      player: null,
      video2: {
        url: "http://www.w3school.com.cn/i/movie.ogg"
      },
      video: {
        url: ""
      },
      language: "zh",
      videoList: [],
      videoUploadInfo: {
        toWhere: "",
        video_list: []
      },
      ruleInline: {
        toWhere: [{ required: true, message: "上传文件夹不能为空" }],
        video_list: [
          {
            required: true,
            type: "array",
            min: 1,
            message: "视频不能为空",
            trigger: "change"
          }
        ]
      },
      videoToWhereList: [],
      videoFolderInfo: {
        video_folder_name: ""
      },
      ruleInline2: {
        video_folder_name: [
          { validator: validateVideoFolderName, trigger: "blur" }
        ]
      },
      editVideoFolderInfo: {
        video_folder_name: ""
      },
      ruleInline3: {
        video_folder_name: [
          { validator: validateVideoFolderName, trigger: "blur" }
        ]
      },
      editVideoInfo: {
        video_name: ""
      },
      ruleInline4: {
        video_name: [{ required: true, message: "视频名称不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    /*
        *   TODO 请求接口获取用户所有的相册数据push到albumList
        * */
    this.query(1);
  },
  mounted() {
    let _that = this;
    this.player = this.$refs.player.dp;
    const clipboard = new Clipboard(".btn");
    clipboard.on("success", function(e) {
      _that.$Message.success({ content: "链接已复制到剪切板", duration: 1.5 });
      e.clearSelection();
    });
    clipboard.on("error", function(e) {
      _that.$Message.success({ content: "该浏览器不支持复制到剪切板功能", duration: 1.5 });
    });
  },
  methods: {
    // 列表查询
    query(page) {
      api
        .post("/member/media/listMediaLibrary", {
          mediaType: 2,
          account: this.loginuserinfo.loginAccount,
          pageSize: this.pageSize2,
          pageNum: page
        })
        .then(response => {
          if (response.code === 200) {
            this.total2 = response.total
            for (let i = 0; i < response.data.length; i++) {
              this.videoFolderList.push({
                id: response.data[i].mediaId,
                name: response.data[i].mediaName,
                time: response.data[i].createTime
              });
              this.videoToWhereList.push({
                label: response.data[i].mediaName,
                value: response.data[i].mediaId
              });
            }
          }
        });
    },
    queryVideo(params) {
      let _that = this;
      api
        .post("/member/media/listMediaLibraryDetail", params)
        .then(response => {
          if (response.code === 200) {
            for (let i = 0; i < response.data.length; i++) {
              _that.videoList.push({
                id: response.data[i].id,
                src: "../../../../static/datas/img/myStyle/video.png",
                url: response.data[i].mediaUrl,
                title: response.data[i].name,
                time: response.data[i].createTime
              });
            }
          }
          if (_that.videoList.length === 0) {
            _that.isEmpty = true;
          }
          _that.total = response.total;
          _that.pageSize = response.page.pageSize;
        });
    },
    getNextPage (page) {
      this.page = page;
      let params = {
        mediaId: this.videoFolderId,
        pageNum: page,
        pageSize: this.pageSize
      };
      this.videoList = [];
      this.queryVideo(params);
    },
    getNextPage2 (page) {
      this.page2 = page
      this.videoFolderList = []
      this.query(page)
    },
    handleSubmit(name) {
      let flag = false;
      this.$refs[name].validate(valid => {
        if (valid) {
          flag = true;
        } else {
        }
      });
      return flag;
    },
    handleReset (name) {
      this.$refs[name].resetFields();
    },
    uploadVideo() {
      this.uploadVideoShow = true;
    },
    play() {
      this.player.play();
      this.playVideoShow = true;
    },
    playVideo(url) {
      this.player.video.src = "http:" + url;
      this.playVideoShow = true;
    },
    back() {
      this.videoShow = !this.videoShow;
      this.videoList = [];
      this.isEmpty = false;
    },
    createdVideoFolder() {
      this.uploadVideoShow = false;
      this.createVideoFolderShow = true;
    },
    lookVideo(id) {
      this.videoShow = false;
      this.videoFolderId = id;
      let params = { mediaId: id, pageNum: 1, pageSize: this.pageSize };
      this.queryVideo(params);
    },
    videoFolderEdit(id, index) {
      this.editVideoFolderShow = true;
      this.editVideoFolderIndex = index;
      this.editVideoFolderId = id;
    },
    videoFolderDel(id, index) {
      this.$Modal.confirm({
        title: '操作提示',
        content: '<p>是否确认删除这个文件夹？</p><p class="t-grey">删除文件夹后视频也会随之删除</p>',
        onOk: () => {
          let _that = this;
          api.get("/member/media/deleteMediaLibrary/" + id).then(response => {
            if (response.data === 1) {
              _that.videoFolderList = [];
              _that.videoToWhereList = [];
              _that.query(this.page2);
              _that.$Message.info("删除成功");
            }
          });
        },
        onCancel: () => {}
      });
    },
    uploadVideoOk() {
      let list = [];
      for (let i = 0; i < this.videoUploadInfo.video_list.length; i++) {
        let obj = {
          name: this.videoUploadInfo.video_list[i].name,
          url: this.videoUploadInfo.video_list[i].response.data.picName
        };
        list.push(obj);
      }
      let data = {
        mediaId: this.videoUploadInfo.toWhere,
        mediaUrl: list
      };
      let flag = this.handleSubmit("videoUploadInfo");
      if (!flag) {
        return;
      }
      api.post("/member/media/saveMediaLibraryDetail", data).then(response => {
        if (response.code === 200) {
          this.uploadVideoShow = false;
          this.videoUploadInfo.toWhere = "";
          this.videoUploadInfo.video_list = [];
          this.$refs.upload.fileList = [];
          this.handleReset('videoUploadInfo')
        }
      });
    },
    uploadVideoOkCancel() {
      this.videoUploadInfo.toWhere = "";
      this.videoUploadInfo.photo_list = [];
      this.$refs.upload.fileList = [];
      this.uploadVideoShow = false;
      this.handleReset('videoUploadInfo')
    },
    // 上传视频
    handleSuccess(response, file, fileList) {
      if (response.code === 500) {
        this.$Message.error("上传失败!");
      } else {
        this.$Message.success("上传成功!");
        this.videoUploadInfo.video_list = this.$refs.upload.fileList;
        this.handleSubmit("videoUploadInfo");
      }
    },
    // 删除视频
    handleRemove(file) {
      const fileList = this.videoUploadInfo.video_list;
      this.videoUploadInfo.video_list.splice(fileList.indexOf(file), 1);
    },
    // 视频大小限制
    handleMaxSize(file) {
      this.$Notice.warning({
        title: "视频大小超出限制",
        desc: "视频大小过大，应不超过100M。",
        duration: 6
      });
    },
    // 视频格式限制
    handleFormatError(file) {
      this.$Notice.warning({
        title: "视频格式有误",
        desc: "视频格式不正确，请选择avi、mp4、mkv、rmvb、kux格式。",
        duration: 6
      });
    },
    createVideoFolderOk() {
      /*
       *   TODO 请求保存文件夹接口保存文件夹数据
       * */
      let _that = this;
      let name = this.videoFolderInfo.video_folder_name;
      let data = { mediaName: name, mediaType: 2 };
      let flag = this.handleSubmit("videoFolderInfo");
      if (!flag) {
        return;
      }
      api
        .post("/member/media/saveMediaLibrary", data)
        .then(response => {
          if (response.code === 1000 || response.code === '1000') {
            _that.$Message.error('文件夹已存在！')
          }
          if (response.data === 1) {
            _that.videoFolderList = [];
            _that.videoToWhereList = [];
            _that.videoFolderInfo.video_folder_name = "";
            _that.query(1);
            this.page2 = 1
            _that.createVideoFolderShow = false;
          }
        })
        .catch(error => {
          this.$Message.error('创建文件夹失败！')
        });
    },
    createVideoFolderCancel() {
      this.videoFolderInfo.video_folder_name = "";
      this.createVideoFolderShow = false;
      this.handleReset('videoFolderInfo')
    },
    editVideoFolderOk() {
      let flag = this.handleSubmit("editVideoFolderInfo");
      if (!flag) {
        return;
      }
      let name = this.editVideoFolderInfo.video_folder_name;
      let id = this.editVideoFolderId;
      let _that = this;
      api
        .post("/member/media/updateMediaLibrary", {
          mediaId: id,
          mediaName: name
        })
        .then(response => {
          if (response.data === 1) {
            _that.videoFolderList = [];
            _that.videoToWhereList = [];
            _that.editVideoFolderInfo.video_folder_name = "";
            _that.editVideoFolderShow = false;
            _that.query(this.page2);
          }
        });
    },
    editVideoFolderCancel() {
      this.editVideoFolderInfo.video_folder_name = "";
      this.editVideoFolderShow = false;
      this.handleReset('editVideoFolderInfo')
    },
    playVideoFolderOk() {
      this.playVideoShow = false;
    },
    playVideoFolderCancel() {
      this.playVideoShow = false;
    },
    editVideo(id, index) {
      this.editVideoShow = true;
      this.index = index;
      this.id = id;
    },
    delVideo(id, index) {
      this.$Modal.confirm({
        title: "操作提示",
        content: "<p>是否确认删除这个视频？</p>",
        onOk: () => {
          let _that = this;
          api
            .get("/member/media/deleteMediaLibraryDetail/" + id)
            .then(response => {
              if (response.data === 1) {
                _that.videoList = [];
                let params = {
                  mediaId: _that.videoFolderId,
                  pageNum: _that.page,
                  pageSize: _that.pageSize
                };
                this.queryVideo(params);
                _that.$Message.info("删除成功");
              }
            });
        },
        onCancel: () => {}
      });
    },
    editVideoOk() {
      let flag = this.handleSubmit("editVideoInfo");
      if (!flag) {
        return;
      }
      let name = this.editVideoInfo.video_name;
      let id = this.id;
      let _that = this;
      api
        .post("/member/media/updateMediaLibraryDetail", {
          id: id,
          name: name
        })
        .then(response => {
          if (response.data === 1) {
            _that.editVideoShow = false;
            _that.videoList[_that.index].title = name;
            _that.editVideoInfo.video_name = "";
          }
        });
    },
    editVideoCancel() {
      this.editVideoInfo.video_name = "";
      this.editVideoShow = false;
      this.handleReset('editVideoInfo')
    }
  }
};
</script>
<style scoped>
.border {
  width: 174px;
  height: 140px;
}
.folder-drop {
  margin-top: 15px;
}
.video-drop {
  margin-top: 5px;
}
.tip {
  margin-left: 10px;
}
.pages {
  margin-top: 30px;
}
.pointer {
  cursor: pointer;
}
.photo-content {
  height: 190px;
  margin-bottom: 5px;
}
</style>
<style scoped lang="scss">
.video-play {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate3d(-50%, -50%, 0);
  font-size: 34px;
  color: #fff;
}
.video-circle {
  position: absolute;
  top: 50%;
  left: 48.5%;
  transform: translate3d(-50%, -50%, 0);
  font-size: 55px;
  color: #fff;
}
.up-video {
  display: inline-block;
  width: 60px;
  text-align: center;
  border-radius: 4px;
  background: #fff;
  position: relative;
  margin-right: 4px;
  vertical-align: top;
  &:hover {
    .demo-upload-list-cover {
      display: block;
    }
  }
  .demo-upload-list-cover {
    height: 60px;
    line-height: 60px;
  }
}
.drop-out {
  &:hover {
    .drop-in {
      display: block;
    }
    .share {
      display: block;
    }
  }
  .drop-in {
    display: none;
  }
  .share {
    display: none;
    position: absolute;
    background-color: rgba(96, 96, 96, 0.4);
    text-align: center;
    bottom: 0;
    right: 0;
    left: 0;
  }
}
</style>