<template>
    <div>
        <!-- 照片文件夹 -->
        <template v-if="photoShow">
            <Button type="primary" class="mr10" @click="uploadPhoto" v-if="isshowEdit">上传照片</Button>
            <Button type="default" @click="createdPhotoFolder" v-if="isshowEdit">新建相册</Button>
            <!-- 循环生成照片文件夹 -->
            <Row :gutter="16" class="album mt20">
                <i-col span="6" v-for="(item,index) in photoFolderList" :key="index" class="photo-content">
                    <figure class="wrap mb10 border drop-out pointer">
                        <Dropdown trigger="click" placement="left-start" class="drop-in"  v-if="isshowEdit">
                            <Icon type="ios-arrow-down" style="padding:2px 5px;"></Icon>
                            <Dropdown-menu slot="list">
                                <Dropdown-item>
                                    <div @click="photoFolderEdit(item.id,index)"><Icon type="edit"></Icon> 编辑</div>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <div @click="photoFolderDel(item.id,index)"><Icon type="trash-a"></Icon> 删除</div>
                                </Dropdown-item>
                            </Dropdown-menu>
                        </Dropdown>
                        <img :src="item.src" width="100%" @click="lookPhoto(item.id)">
                        <div class="photo-num">共 {{item.number}} 张</div>
                    </figure>
                    <p class="ell" :title="item.title">{{item.title}}</p>
                    <p class="t-grey">创建于{{item.time}}</p>
                </i-col>
            </Row>
            <Row style="text-align: center; height: 100px;">
                <Page :total="total2" @on-change="getNextPage2" :page-size="pageSize2" :current="page2" class="pages"></Page>
            </Row>
        </template>
        <!-- 照片列表页面 -->
        <template v-else>
            <Button type="primary" @click="back">返回</Button>
            <Row :gutter="16" class="album mt20">
                <div class="tip" v-if="isEmpty">该相册空空如也，请上传照片！</div>
                <i-col span="6" v-for="(item,index) in photoList" :key="index" class="photo-content">
                    <figure class="wrap mb10 border drop-out pointer">
                        <Dropdown :transfer="true" trigger="click" placement="left-start" class="drop-in"  v-if="isshowEdit">
                            <Icon type="ios-arrow-down" style="padding:2px 5px;"></Icon>
                            <Dropdown-menu slot="list">
                                <Dropdown-item>
                                    <div @click="editPhoto(item.id,index)"><Icon type="edit"></Icon> 编辑</div>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <div @click="setCover(item.src)"><Icon type="gear-b"></Icon> 设为封面</div>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <div @click="delPhoto(item.id,index)"><Icon type="trash-a"></Icon> 删除</div>
                                </Dropdown-item>
                            </Dropdown-menu>
                        </Dropdown>
                        <vue-share 
                            :config="{
                                sites: ['qzone', 'qq', 'wechat', 'weibo'],
                                url: 'http:'+item.src
                            }"
                            class="share">
                        </vue-share>
                        <img :src="item.src" class="preview-img" width="100%" @click="show(index)">
                    </figure>
                    <p class="ell" :title="item.title">{{item.title}}</p>
                    <p class="t-grey">上传于{{item.time}}</p>
                </i-col>
            </Row>
            <Row style="text-align: center; height: 100px;">
                <Page :total="total" @on-change="getNextPage" :page-size="pageSize" class="pages"></Page>
            </Row>
        </template>
        <!-- 上传照片 -->
        <Modal v-model="uploadPhotoShow" title="上传照片" width="600" :mask-closable="false" :closable="false">
          <Form ref="photoUploadInfo" :model="photoUploadInfo" label-position="right" :label-width="100" :rules="ruleInline">
            <Form-item label="上传到：" prop="toWhere">
                <i-select v-model="photoUploadInfo.toWhere" class="mb10"  style="width:200px">
                    <Option v-for="(item,index) in photoToWhereList" :value="item.value" :key="index">{{ item.label }}</Option>
                </i-select>
                <Button type="text" style="color: #108EE9;" size="small" @click="createdPhotoFolder">新建相册</Button>
            </Form-item>
            <Form-item label="照片：" prop="photo_list">
                <div class="demo-upload-list" v-for="(item,index) in photoUploadInfo.photo_list" :key="index">
                    <template v-if="item.status === 'finished'">
                        <img :src="'http://'+item.response.data.picName">
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
                        :format="['jpg', 'png']"
                        :max-size="2048"
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
              <Button type="text" size="large" @click="uploadPhotoCancel()">取消</Button>
              <Button type="primary" size="large" @click="uploadPhotoOk()">确定</Button>
          </div>
        </Modal>
        <!-- 照片上传成功后续操作 -->
        <Modal v-model="uploadPhotoSuccessShow" @on-ok="uploadPhotoSuccessOk()" @on-cancel="uploadPhotoSuccessCancel()" title="编辑照片信息" width="600px" height="300px" :mask-closable="false">
            <Row style="margin-left: 52px;">
                照片保存成功！&nbsp;&nbsp;
                <Button type="text" style="color: #108EE9;" size="small" @click="uploadBtn()">继续上传</Button>
            </Row>
            <Row style="margin-top: 10px;margin-left: 52px;">
                统一添加照片信息&nbsp;&nbsp;或&nbsp;&nbsp;
                <Button type="text" style="color: #108EE9;" size="small" @click="editEveryPicture()">编辑单张照片信息</Button>
            </Row>
            <Row style="margin-top: 10px;">
                <Form ref="photoInfo" :model="photoInfo" label-position="right" :label-width="100" :rules="ruleInline5">
                    <Form-item label="名称：">
                        <Input v-model="photoInfo.picphoto_name" :maxlength="30" placeholder="名称不能超过30字" />
                    </Form-item>
                    <Form-item label="描述：">
                        <Input type="textarea" v-model="photoInfo.photo_description" :maxlength="1000" placeholder="请输入..." />
                    </Form-item>
                </Form>
            </Row>
        </Modal>
        <!-- 编辑单张照片信息 -->
        <Modal v-model="editEveryPhotoShow" @on-ok="editEveryPhotoOk()" @on-cancel="editEveryPhotoCancel()" title="编辑单张照片信息" width="1000px" height="300px" :mask-closable="false">
            <Row :gutter="16" class="album mt20">
                <i-col span="6" v-for="(item,index) in editEveryPhotoList" :key="index">
                    <figure class="wrap mb10" style="margin-top: 10px;">
                        <img :src="item.src" class="preview-img" width="100%">
                    </figure>
                    <p class="ell">
                        名称：<Input :maxlength="30" style="width: 190px;" :value="item.title" placeholder="名称不能超过30字" />
                    </p>
                    <p class="ell t-grey" style="margin-top: 3px;">
                        描述：<Input type="textarea" style="width: 190px;height:60px;" :maxlength="1000" placeholder="请输入..." />
                    </p>
                </i-col>
            </Row>
        </Modal>
        <!-- 创建文件夹 -->
        <Modal v-model="createPhotoFolderShow" width="600" title="创建相册" :mask-closable="false" :closable="false">
            <Form ref="photoFolderInfo" :model="photoFolderInfo" label-position="right" :label-width="100" :rules="ruleInline2">
                <Form-item label="相册名称：" prop="photo_folder_name">
                    <Input v-model="photoFolderInfo.photo_folder_name" :maxlength="15" placeholder="名称不能超过15字" />
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="createPhotoFolderCancel()">取消</Button>
                <Button type="primary" size="large" @click="createPhotoFolderOk()">确定</Button>
            </div>
        </Modal>
        <!-- 编辑文件夹 -->
        <Modal v-model="editPhotoFolderShow" title="编辑相册" width="600" :mask-closable="false" :closable="false">
            <Form ref="editPhotoFolderInfo" :model="editPhotoFolderInfo" label-position="right" :label-width="100" :rules="ruleInline3">
                <Form-item label="相册名称：" prop="photo_folder_name">
                    <Input v-model="editPhotoFolderInfo.photo_folder_name" :maxlength="15" placeholder="名称不能超过15字" />
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="editPhotoFolderCancel()">取消</Button>
                <Button type="primary" size="large" @click="editPhotoFolderOk()">确定</Button>
            </div>
        </Modal>
        <!-- 编辑照片 -->
        <Modal v-model="editPhotoShow" title="编辑照片" width="600" :mask-closable="false" :closable="false">
            <Form ref="editPhotoInfo" :model="editPhotoInfo" label-position="right" :label-width="100" :rules="ruleInline4">
                <Form-item label="照片名称：" prop="photo_name">
                    <Input v-model="editPhotoInfo.photo_name" :maxlength="15" placeholder="名称不能超过15字" />
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="editPhotoCancel()">取消</Button>
                <Button type="primary" size="large" @click="editPhotoOk()">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import api from "~api";
import vueShare from "~components/vueShare";

export default {
  components: {
    vueShare
  },
  data() {
    const validatePhotoFolderName = (rule, value, callback) => {
        if (value.replace(/(^\s+)|(\s+$)/g, "") === '') {
            callback(new Error('相册名称不能为空'));
        } else {
            callback();
        }
    };
    return {
      loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      isshowEdit: true,
      index: -1,
      id: -1,
      editPhotoFolderIndex: -1,
      editPhotoFolderId: -1,
      photoFolderId: -1,
      total: 0,
      pageSize: 20,
      page: 1,
      total2: 0,
      pageSize2: 12,
      page2: 1,
      photoShow: true,
      uploadPhotoShow: false,
      createPhotoFolderShow: false,
      editPhotoFolderShow: false,
      loop: false,
      editPhotoShow: false,
      uploadPhotoSuccessShow: false,
      editEveryPhotoShow: false,
      isEmpty: false,
      // action: this.$url.api + "/member/upload/upWithWaterMark",
      action: `${this.$url.upload}/upload/upWithWaterMark`,
      photoFolderList: [],
      photoToWhereList: [],
      photoList: [],
      previewList: [],
      photoUploadInfo: {
        toWhere: "",
        photo_list: []
      },
      ruleInline: {
        toWhere: [{ required: true, message: "上传相册不能为空" }],
        photo_list: [
          {
            required: true,
            type: "array",
            min: 1,
            message: "照片不能为空",
            trigger: "change"
          }
        ]
      },
      photoFolderInfo: {
        photo_folder_name: ""
      },
      ruleInline2: {
        photo_folder_name: [
          { validator: validatePhotoFolderName, trigger: "blur" }
        ]
      },
      editPhotoFolderInfo: {
        photo_folder_name: ""
      },
      ruleInline3: {
        photo_folder_name: [
          { validator: validatePhotoFolderName, trigger: "blur" }
        ]
      },
      editPhotoInfo: {
        photo_name: ""
      },
      ruleInline4: {
        photo_name: [{ required: true, message: "照片名称不能为空", trigger: "blur" }]
      },
      options: {
        //  动画显示
        showHideOpacity: true,
        // 背景透明
        bgOpacity: 0.8
        // 显示第几张
        // index: 2
      },
      photoInfo: {
        photo_name: "",
        photo_description: ""
      },
      ruleInline5: {},
      editEveryPhotoList: [
      //   {
      //     src: "../static/datas/img/kaocha01.png",
      //     number: 100,
      //     title: "圈贴插图",
      //     time: "2017-04-13"
      //   },
      //   {
      //     src: "../static/datas/img/kaocha02.png",
      //     number: 210,
      //     title: "专栏插图",
      //     time: "2017-06-11"
      //   },
      //   {
      //     src: "../static/datas/img/kaocha03.png",
      //     number: 131,
      //     title: "动态插图",
      //     time: "2017-07-29"
      //   },
      //   {
      //     src: "../static/datas/img/kaocha04.png",
      //     number: 223,
      //     title: "醋",
      //     time: "2017-08-20"
      //   },
      //   {
      //     src: "../static/datas/img/kaocha01.png",
      //     number: 100,
      //     title: "圈贴插图",
      //     time: "2017-04-13"
      //   },
      //   {
      //     src: "../static/datas/img/kaocha02.png",
      //     number: 210,
      //     title: "专栏插图",
      //     time: "2017-06-11"
      //   },
      //   {
      //     src: "../static/datas/img/kaocha03.png",
      //     number: 131,
      //     title: "动态插图",
      //     time: "2017-07-29"
      //   },
      //   {
      //     src: "../static/datas/img/kaocha04.png",
      //     number: 223,
      //     title: "醋",
      //     time: "2017-08-20"
      //   }
      ],
      shareConfig: {
        sites: ["qzone", "qq", "weibo", "wechat"]
      }
    };
  },
  created() {
    /*
        *   TODO 请求接口获取用户所有的相册数据push到albumList
        * */
    this.query(1);
  },
  methods: {
    // 列表查询
    query (page) {
      let _that = this;
      api
        .post("/member/media/listMediaLibrary", {
          mediaType: 1,
          account: this.loginuserinfo.loginAccount,
          pageNum: page,
          pageSize: this.pageSize2
        })
        .then(response => {
          if (response.code === 200) {
            this.total2 = response.total
            for (let i = 0; i < response.data.length; i++) {
              _that.photoFolderList.push({
                id: response.data[i].mediaId,
                src: response.data[i].imageUrl,
                number: response.data[i].countDetail,
                title: response.data[i].mediaName,
                time: response.data[i].createTime
              });
              _that.photoToWhereList.push({
                label: response.data[i].mediaName,
                value: response.data[i].mediaId
              });
            }
          }
        });
    },
    queryPhoto(params) {
      let _that = this;
      api
        .post("/member/media/listMediaLibraryDetail", params)
        .then(response => {
          if (response.code === 200) {
            for (let i = 0; i < response.data.length; i++) {
              _that.photoList.push({
                id: response.data[i].id,
                src: response.data[i].mediaUrl,
                title: response.data[i].name,
                time: response.data[i].createTime
              });
              _that.previewList.push({
                src: response.data[i].mediaUrl,
                title: response.data[i].name,
                w: 640,
                h: 425
              });
            }
            if (_that.photoList.length === 0) {
              _that.isEmpty = true;
            }
            _that.total = response.total;
            _that.pageSize = response.page.pageSize;
            _that.$emit("showPic", _that.previewList);
          }
        });
    },
    getNextPage (page) {
      this.page = page
      let params = {
        mediaId: this.photoFolderId,
        pageNum: page,
        pageSize: this.pageSize
      };
      this.photoList = []
      this.previewList = []
      this.queryPhoto(params)
    },
    getNextPage2 (page) {
      this.page2 = page
      this.photoFolderList = []
      this.query(page)
    },
    handleSubmit(name) {
      let flag = false
      this.$refs[name].validate(valid => {
        if (valid) {
          flag = true
        }
      });
      return flag
    },
    handleReset (name) {
      this.$refs[name].resetFields();
    },
    back() {
      this.photoShow = !this.photoShow;
      this.isEmpty = false
      this.photoList = []
      this.previewList = []
      this.photoFolderList = []
      this.photoToWhereList = []
      this.query(this.page2)
    },
    uploadPhoto() {
      this.uploadPhotoShow = true
    },
    createdPhotoFolder() {
      this.uploadPhotoShow = false
      this.createPhotoFolderShow = true
    },
    lookPhoto(id) {
      this.photoShow = false
      this.photoFolderId = id
      let params = { mediaId: id, pageNum: 1, pageSize: this.pageSize }
      this.queryPhoto(params)
    },
    photoFolderEdit(id, index) {
      this.editPhotoFolderShow = true
      this.editPhotoFolderIndex = index
      this.editPhotoFolderId = id
    },
    photoFolderDel(id, index) {
      this.$Modal.confirm({
        title: '操作提示',
        content: '<p>是否确认删除这个文件夹？</p><p class="t-grey">删除文件夹后照片也会随之删除</p>',
        onOk: () => {
          let _that = this;
          api.get("/member/media/deleteMediaLibrary/" + id).then(response => {
            if (response.data === 1) {
              _that.photoFolderList = []
              _that.photoToWhereList = []
              _that.query(_that.page2)
              _that.$Message.info("删除成功")
            }
          });
        },
        onCancel: () => {}
      });
    },
    uploadPhotoOk() {
      let flag = this.handleSubmit("photoUploadInfo")
      if (!flag) {
        return
      }
      let _that = this
      let list = []
      for (let i = 0; i < this.photoUploadInfo.photo_list.length; i++) {
        let obj = {
          name: this.photoUploadInfo.photo_list[i].name,
          url: this.photoUploadInfo.photo_list[i].response.data.picName
        };
        list.push(obj)
      }
      let data = {
        mediaId: this.photoUploadInfo.toWhere,
        mediaUrl: list
      };
      api.post("/member/media/saveMediaLibraryDetail", data).then(response => {
        if (response.code === 200) {
          // _that.uploadPhotoSuccessShow = true
          _that.photoUploadInfo.toWhere = ""
          _that.photoUploadInfo.photo_list = []
          _that.$refs.upload.fileList = []
          _that.photoFolderList = []
          _that.photoToWhereList = []
          _that.query(_that.page2)
          _that.uploadPhotoShow = false
          _that.handleReset('photoUploadInfo')
        }
      });
    },
    uploadPhotoCancel() {
      this.photoUploadInfo.toWhere = ""
      this.photoUploadInfo.photo_list = []
      this.$refs.upload.fileList = []
      this.uploadPhotoShow = false
      this.handleReset('photoUploadInfo')
    },
    // 上传照片
    handleSuccess(response, file, fileList) {
      if (response.code === 500) {
        this.$Message.error("上传失败!")
      } else {
        this.$Message.success("上传成功!")
        this.photoUploadInfo.photo_list = this.$refs.upload.fileList
        this.handleSubmit("photoUploadInfo")
      }
    },
    // 删除照片
    handleRemove(file) {
      const fileList = this.photoUploadInfo.photo_list
      this.photoUploadInfo.photo_list.splice(fileList.indexOf(file), 1)
    },
    // 照片大小限制
    handleMaxSize(file) {
      this.$Notice.warning({
        title: "照片大小超出限制",
        desc: "照片大小过大，应不超过2M。",
        duration: 6
      });
    },
    // 照片格式限制
    handleFormatError(file) {
      this.$Notice.warning({
        title: "照片格式有误",
        desc: "照片格式不正确，请选择jpg、png格式。",
        duration: 6
      });
    },
    createPhotoFolderOk() {
      /*
       *   TODO 请求保存文件夹接口保存文件夹数据
       * */
      let flag = this.handleSubmit("photoFolderInfo")
      if (!flag) {
        return
      }
      let _that = this;
      let name = this.photoFolderInfo.photo_folder_name;
      let data = {
        mediaName: name,
        mediaType: 1,
        image_url: "../static/datas/img/myStyle/no-picture.png"
      };
      api.post("/member/media/saveMediaLibrary", data)
        .then(response => {
          if (response.code === 1000 || response.code === '1000') {
            _that.$Message.error('相册已存在！')
          }
          if (response.data === 1) {
            _that.photoFolderList = []
            _that.photoToWhereList = []
            _that.photoFolderInfo.photo_folder_name = ""
            _that.query(1)
            this.page2 = 1
            _that.createPhotoFolderShow = false
          }
        })
        .catch(error => {
          this.$Message.error('创建相册失败！')
        });
    },
    createPhotoFolderCancel() {
      this.photoFolderInfo.photo_folder_name = ""
      this.createPhotoFolderShow = false
      this.handleReset('photoFolderInfo')
    },
    editPhotoFolderOk() {
      let flag = this.handleSubmit("editPhotoFolderInfo")
      if (!flag) {
        return
      }
      let name = this.editPhotoFolderInfo.photo_folder_name
      let id = this.editPhotoFolderId
      let _that = this
      api
        .post("/member/media/updateMediaLibrary", {
          mediaId: id,
          mediaName: name
        })
        .then(response => {
          if (response.data === 1) {
            _that.photoFolderList = []
            _that.photoToWhereList = []
            _that.editPhotoFolderInfo.photo_folder_name = ""
            _that.editPhotoFolderShow = false
            _that.query(_that.page2)
          }
        });
    },
    editPhotoFolderCancel() {
      this.editPhotoFolderInfo.photo_folder_name = ""
      this.editPhotoFolderShow = false
      this.handleReset('editPhotoFolderInfo')
    },
    editPhoto(id, index) {
      this.editPhotoShow = true
      this.index = index
      this.id = id
    },
    delPhoto(id, index) {
      this.$Modal.confirm({
        title: "操作提示",
        content: "<p>是否确认删除这张照片？</p>",
        onOk: () => {
          let _that = this;
          api
            .get("/member/media/deleteMediaLibraryDetail/" + id)
            .then(response => {
              if (response.data === 1) {
                _that.photoList = []
                _that.previewList = []
                let params = {
                  mediaId: _that.photoFolderId,
                  pageNum: _that.page,
                  pageSize: _that.pageSize
                };
                _that.queryPhoto(params)
                _that.$Message.info("删除成功")
              }
            });
        },
        onCancel: () => {}
      });
    },
    editPhotoOk() {
      let flag = this.handleSubmit("editPhotoInfo")
      if (!flag) {
        return
      }
      let name = this.editPhotoInfo.photo_name
      let id = this.id
      let _that = this
      api
        .post("/member/media/updateMediaLibraryDetail", {
          id: id,
          name: name
        })
        .then(response => {
          if (response.data === 1) {
            _that.editPhotoShow = false
            _that.photoList[_that.index].title = name
            _that.editPhotoInfo.photo_name = ""
          }
        });
    },
    editPhotoCancel() {
      this.editPhotoInfo.photo_name = ""
      this.editPhotoShow = false
      this.handleReset('editPhotoInfo')
    },
    show(index) {
      this.$emit("index", index)
    },
    uploadPhotoSuccessOk() {},
    uploadPhotoSuccessCancel() {},
    uploadBtn() {
      this.uploadPhotoSuccessShow = false
      this.uploadPhotoShow = true
    },
    editEveryPicture() {
      this.uploadPhotoSuccessShow = false
      this.editEveryPhotoShow = true
    },
    setCover (src) {
      api.post('/member/product-base/media-library-cover', {
        imageUrl: src,
        mediaId: this.photoFolderId
      }).then(response => {
        if (response.code === 200) {
          this.$Message.success('相册封面设置成功！')
        } else {
          this.$Message.error('相册封面设置失败！')
        }
      }).catch(error => {
        this.$Message.error('相册封面设置失败！')
      })
    },
    editEveryPhotoOk() {},
    editEveryPhotoCancel() {}
  }
};
</script>
<style scoped>
.photo-play {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate3d(-50%, -50%, 0);
  font-size: 34px;
  color: #fff;
}
.border {
  width: 200px;
  height: 140px;
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
<style lang="scss">
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
    z-index: 999;
    background-color: rgba(96, 96, 96, 0.4);
    text-align: center;
    bottom: 0;
    right: 0;
    left: 0;
  }
}
</style>
