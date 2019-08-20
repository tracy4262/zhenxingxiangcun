<template>
    <div>
        <!-- 照片文件夹 -->
        <template v-if="photoFolderShow">
            <Button type="primary" class="mr10" @click="uploadPhoto">上传照片</Button>
            <Button type="default" @click="createdPhotoFolder">新建相册</Button>
            <!-- 循环生成照片文件夹 -->
            <Row :gutter="16" class="album mt20">
                <i-col span="6" v-for="(item,index) in photoFolderList" :key="index">
                  <figure class="wrap mb10 border drop-out pointer">
                      <img :src="item.src" width="100%" @click="lookPhoto(item.id)">
                      <div class="photo-num">共 {{item.number}} 张</div>
                  </figure>
                  <p class="ell" :title="item.title">{{item.title}}</p>
                  <p class="t-grey">更新于{{item.time}}</p>
                </i-col>
            </Row>
            <Row>
                <Page :total="total2" :page-size="pageSize2" show-total @on-change="getNextPage2" style="float: right; margin-top: 20px;"></Page>
            </Row>
            <Row>
              <Col span="3" v-for="(item,index) in productionBasePhotoUrl" :key="index" style="margin-top: 20px;">
                <img :src="item" style="width: 100px; height: 100px;">
                <Button type="primary" shape="circle" icon="close" class="close-btn" @click="delChoosedPhoto(index)"></Button>
              </Col>
            </Row>
        </template>
        <!-- 照片列表页面 -->
        <Modal v-model="photoShow" width="1000" title="图片" :mask-closable="false" :closable="false">
          <template>
            <!-- <Button type="primary" @click="back">返回</Button> -->
            <Row :gutter="16" class="album mt20">
                <div class="tip" v-if="isEmpty">该相册空空如也，请上传照片！</div>
                <Checkbox-group v-model="choosed">
                  <i-col span="6" v-for="(item,index) in photoList" :key="index">
                    <figure class="wrap mb10 border drop-out pointer">
                      <Checkbox :label="item.src" style="position: absolute; z-index: 999; right: -10px; top: 5px;" :disabled="item.disable"><span>&nbsp;&nbsp;</span></Checkbox>
                        <img :src="item.src" class="preview-img" width="100%" @click="show(index)">
                    </figure>
                    <p class="ell" :title="item.title">{{item.title}}</p>
                    <p class="t-grey">更新于{{item.time}}</p>
                  </i-col>
                </Checkbox-group>
            </Row>
            <Row>
                <Page :total="total" @on-change="getNextPage" show-elevator :page-size="pageSize" class="pages"></Page>
            </Row>
          </template>
          <div slot="footer">
              <Button type="text" size="large" @click="selectPhotoCancel()">取消</Button>
              <Button type="primary" size="large" @click="selectPhotoOk()">确定</Button>
          </div>
        </Modal>
        <!-- 上传照片 -->
        <Modal v-model="uploadPhotoShow" title="上传照片" width="600" :mask-closable="false">
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
                                    :format="['jpg','png']"
                                    :max-size="10240"
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
        <!-- 创建文件夹 -->
        <Modal v-model="createPhotoFolderShow" width="600" title="创建相册" :mask-closable="false">
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
    </div>
</template>

<script>
import api from "~api";
import vueShare from "~components/vueShare";

export default {
  components: {
    vueShare
  },
  props: {
    pid: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      index: -1,
      id: -1,
      photoFolderId: -1,
      total: 0,
      pageSize: 8,
      page: 1,
      total2: 0,
      pageSize2: 8,
      page2: 1,
      photoFolderShow: true,
      photoShow: false,
      uploadPhotoShow: false,
      createPhotoFolderShow: false,
      uploadPhotoSuccessShow: false,
      isEmpty: false,
      action: `${this.$url.upload}/upload/upWithWaterMark`,
      choosed: [],
      productionBasePhotoUrl: [],
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
          { required: true, message: "相册名称不能为空", trigger: "blur" }
        ]
      },
    };
  },
  created() {
    // 如果是修改 则回写数据
    if (this.pid !== undefined && this.pid !== '') {
      let _that = this
      this.$api.post('/member/product-base/media-library-detail-query', {productId: this.pid}).then(response => {
        console.log('response', response)
        if (response.code === 200) {
          response.data.forEach(item => {
            _that.productionBasePhotoUrl.push(item.photoUrl)
          })
          // console.log(_that.productionBasePhotoUrl)
          _that.$emit('urls',_that.productionBasePhotoUrl)
        }
      }).catch(error => {
        console.log(error)
      })
    }
    /*
    *   TODO 请求接口获取用户所有的相册数据push到photoList
    * */
    this.query({
      mediaType: 1,
      account: this.loginuserinfo.loginAccount,
      pageNum: 1,
      pageSize: this.pageSize2
    })
  },
  methods: {
    // 列表查询
    query(data) {
      let _that = this;
      api
        .post("/member/product-base/media-library-query", data)
        .then(response => {
          console.log("res", response);
          if (response.code === 200) {
            for (let i = 0; i < response.data.list.length; i++) {
              _that.photoFolderList.push({
                id: response.data.list[i].mediaId,
                src: response.data.list[i].imageUrl,
                number: response.data.list[i].detailCount,
                title: response.data.list[i].mediaName,
                time: response.data.list[i].createTime
              });
            }
            _that.total2 = response.data.total
          }
        });
    },
    queryPhoto (params) {
      let _that = this;
      api
        .post("/member/product-base/media-library-detail-query-list", params)
        .then(response => {
          console.log("res", response);
          if (response.code === 200) {
            for (let i = 0; i < response.data.list.length; i++) {
              _that.photoList.push({
                id: response.data.list[i].id,
                src: response.data.list[i].mediaUrl,
                title: response.data.list[i].name,
                time: response.data.list[i].createTime,
                disable: false
              });
              _that.previewList.push({
                src: response.data.list[i].mediaUrl,
                title: response.data.list[i].name,
                w: 640,
                h: 425
              });
            }
            if (_that.photoList.length === 0) {
              _that.isEmpty = true;
            }
            _that.total = response.data.total;
            _that.pageSize = response.data.pageSize;
            _that.$emit("showPic", _that.previewList);
            this.choosed.forEach(item => {
              this.photoList.forEach(photo => {
                if (item === photo.src) {
                  photo.disable = true
                }
              })
            })
          }
        });
    },
    getNextPage(page) {
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
      this.photoToWhereList = []
      this.query({
        mediaType: 1,
        account: this.loginuserinfo.loginAccount,
        pageNum: page,
        pageSize: this.pageSize2
      })
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
    back() {
      this.photoFolderShow = !this.photoFolderShow;
      this.isEmpty = false
      this.photoList = []
      this.previewList = []
    },
    uploadPhoto() {
      this.uploadPhotoShow = true
      // 查询所有的相册
      let _that = this
      this.$api.post('/member/product-base/media-library-query-all', {
        account: this.loginuserinfo.loginAccount,
        mediaType: 1
      }).then(response => {
        console.log('res', response)
        if (response.code === 200) {
          response.data.forEach(element => {
            _that.photoToWhereList.push({
              label: element.mediaName,
              value: element.mediaId
            })
          })
        }
      }).catch(error => {
        console.log(error)
      })
    },
    createdPhotoFolder() {
      this.uploadPhotoShow = false
      this.createPhotoFolderShow = true
    },
    lookPhoto(id) {
      // this.photoFolderShow = false
      this.choosed = this.productionBasePhotoUrl
      console.log('123')
      this.photoShow = true
      console.log("id", id)
      this.photoFolderId = id
      let params = { mediaId: id, pageNum: 1, pageSize: this.pageSize }
      this.queryPhoto(params)
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
      console.log("data", data);
      api.post("/member/media/saveMediaLibraryDetail", data).then(response => {
        console.log("res", response);
        if (response.code === 200) {
          // _that.uploadPhotoSuccessShow = true
          _that.photoUploadInfo.toWhere = ""
          _that.photoUploadInfo.photo_list = []
          _that.$refs.upload.fileList = []
          _that.photoFolderList = []
          _that.photoToWhereList = []
          _that.query({
            mediaType: 1,
            account: this.loginuserinfo.loginAccount,
            pageNum: this.page2,
            pageSize: this.pageSize2
          })
          _that.uploadPhotoShow = false
        }
      });
    },
    uploadPhotoCancel() {
      this.photoUploadInfo.toWhere = ""
      this.photoUploadInfo.photo_list = []
      this.$refs.upload.fileList = []
      this.uploadPhotoShow = false
    },
    // 上传照片
    handleSuccess(response, file, fileList) {
      if (response.code === 500) {
        this.$Message.error("上传失败!")
      } else {
        this.$Message.success("上传成功!")
        console.log("res", response)
        this.photoUploadInfo.photo_list = this.$refs.upload.fileList
        this.handleSubmit("photoUploadInfo")
        console.log("list", this.photoUploadInfo.photo_list)
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
        desc: "照片  " + file.name + " 过长，应不超过10M。",
        duration: 6
      });
    },
    // 照片格式限制
    handleFormatError(file) {
      this.$Notice.warning({
        title: "照片格式有误",
        desc: "照片 " + file.name + " 格式不正确，请选择jpg、jpeg、png格式。",
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
        imageUrl: "../../../static/datas/img/myStyle/no-picture.png",
        account: this.loginuserinfo.loginAccount,
        productId: this.$route.query.id
      };
      console.log("data", data)
      api.post("/member/product-base/media-library-save", data)
        .then(function(response) {
          console.log('response', response)
          if (response.code === 500) {
            if (response.msg !== '') {
              _that.$Message.error(response.msg)
            }
          } else if (response.code === 200) {
            _that.photoFolderList = []
            _that.photoToWhereList = []
            _that.photoFolderInfo.photo_folder_name = ""
            _that.query({
              mediaType: 1,
              account: _that.loginuserinfo.loginAccount,
              pageNum: _that.page2,
              pageSize: _that.pageSize2
            })
            _that.createPhotoFolderShow = false
          }
        })
        .catch(function(error) {
          console.log(error)
        });
    },
    createPhotoFolderCancel() {
      this.photoFolderInfo.photo_folder_name = ""
      this.createPhotoFolderShow = false
    },
    show(index) {
      this.$emit("index", index)
    },
    selectPhotoCancel () {
      this.photoShow = false
      this.isEmpty = false
      this.photoList = []
      this.previewList = []
      this.choosed = []
    },
    selectPhotoOk () {
      console.log('choosed', this.choosed)
      this.choosed.forEach(item => {
        if (this.productionBasePhotoUrl.indexOf(item) === -1) {
          this.productionBasePhotoUrl.push(item)
        }
      })
      console.log('productionBasePhotoUrl', this.productionBasePhotoUrl)
      this.$emit('urls', this.productionBasePhotoUrl)
      this.photoShow = false
      this.isEmpty = false
      this.photoList = []
      this.previewList = []
      this.choosed = []
    },
    delChoosedPhoto (index) {
      this.productionBasePhotoUrl.splice(index, 1)
      this.$emit('urls', this.productionBasePhotoUrl)
    }
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
.close-btn {
  position: absolute;
  z-index: 999;
  right: 5px;
  top: -15px;
  background-color: gray;
  border-color: gray;
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
.vui-share {
  &:hover {
    .social-share {
      display: block;
      background: #fff;
    }
  }
  .social-share-icon {
    display: block;
  }
  .social-share {
    position: absolute;
    z-index: 9;
    display: none;
  }
}
</style>