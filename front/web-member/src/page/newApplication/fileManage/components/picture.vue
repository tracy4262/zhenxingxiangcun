<!--
//     作者：wh
//     时间：2019-1-2
//     描述：相册组件
// -->
<template>
  <div>
    <!-- 循环遍历相册 -->
    <div class="xc01" v-if="photoShow">
      <div class="button-area">
        <Button :type="buttonIndex === 0 ? 'primary' : null" @click="uploadPhoto">＋上传照片</Button>
        <Button :type="buttonIndex === 1 ? 'primary' : null" @click="createPhotoFolder">新建相册</Button>
      </div>
      <Row class="main-body">
        <Col span="6" class="photo-content" v-for="(item,index) in photos" :key="index">
          <div class="photo-top">
            <img :src="item.imageUrl" class="getimg" @click="lookPhotos(index)">
            <p>{{item.mediaName}}</p>
          </div>

          <div class="btn-edit" @click="editPhoto(index)">
            <div>编辑</div>
          </div>
        </Col>
      </Row>

      <Row class="mt30">
        <div v-if="photos.length !== 0">
          <Page
            :total="pagetotal"
            :page-size="pageSize"
            :current="pageNum"
            @on-change="pageChange"
          />
        </div>
      </Row>
      <div v-if="!photos.length" class="bg-white pd20">
        <h2>该文件夹空空如也，请上传照片！</h2>
      </div>
    </div>
    <!-- 遍历相册内容 -->
    <div v-else class="main-body">
      <div class="button-area">
        <Row>
          <Col span="4">
            <img :src="photos[Mid].imageUrl" style="width:120px;height:80px">
          </Col>
          <Col span="18">
            <h2>{{photos[Mid].mediaName}}</h2>
            <p>共{{total}}张</p>
            <p>{{photos[Mid].mediaDescribe}}</p>
          </Col>
        </Row>
        <div class="mt20" style="margin-left:150px">
          <Button :type="buttonIndex2 === 0 ? 'primary' : null" @click="uploadPhoto">＋上传照片</Button>
          <Button :type="buttonIndex2 === 1 ? 'primary' : null" @click="editPhoto(Mid)">编辑</Button>
          <Button :type="buttonIndex2 === 2 ? 'primary' : null" @click="goDel">删除</Button>
          <Button @click="photoShowBtn">返回</Button>
        </div>
      </div>
      <Row>
        <Col span="6" class="photoLists" v-for="(item,index) in photoLists" :key="index">
          <img :src="item.mediaUrl" @click="lookPhotoDetail(index)">
          <Dropdown style="margin-left: 20px" placement="bottom-end" class="setBtn">
            <a href="javascript:void(0)">设置</a>
            <DropdownMenu slot="list">
              <DropdownItem name="edit" @click.native="setEdit(index)">
                <Icon type="md-create" style="padding-right:10px"/>编辑
              </DropdownItem>
              <DropdownItem name="delete" @click.native="setDelete(index)">
                <Icon type="ios-trash" style="padding-right:10px"/>删除
              </DropdownItem>
              <DropdownItem name="cover" @click.native="setCover(index)">
                <Icon type="md-cog" style="padding-right:10px"/>设为封面
              </DropdownItem>
              <DropdownItem name="cover" @click.native="onMove(item, index)">
                <Icon type="ios-move" style="padding-right:10px"/>移动到相册
              </DropdownItem>
            </DropdownMenu>
          </Dropdown>
          <p>{{item.name}}</p>
        </Col>
      </Row>
      <Row class="mt30">
        <div v-if="photoLists.length !== 0">
          <Page
            :total="total"
            :page-size="pageSize"
            :current="photoNum"
            @on-change="photoPageChange"
          />
        </div>
      </Row>
      <div v-if="!photoLists.length"  class="bg-white pd20">
        <h2>该文件夹空空如也，请上传照片！</h2>
      </div>
    </div>
    <!-- 模态框——编辑相册 -->
    <Modal title="编辑相册" v-model="editPhoto1" class-name="vertical-center-modal">
      <Form label-position="left" :label-width="100">
        <FormItem label="相册名">
          <Input v-model="formlist.input1"></Input>
        </FormItem>
        <FormItem label="相册描述">
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
        <Button icon="ios-trash" type="text" @click="goDel">删除相册</Button>
        <Button @click="editCancel">取消</Button>
        <Button type="primary" @click="updatePhotoList">保存</Button>
      </div>
    </Modal>
    <!-- 模态框——相册创建 -->
    <Modal title="创建相册" v-model="createPhoto1" class-name="vertical-center-modal">
      <Form :model="formlist" label-position="left" :label-width="100">
        <FormItem label="相册名">
          <Input v-model="formlist.input1"></Input>
        </FormItem>
        <FormItem label="相册描述">
          <Input v-model="formlist.input2" type="textarea"></Input>
        </FormItem>
        <FormItem label="创建人">
          <Input v-model="formlist.input3"></Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button @click="createPhoto1 = false">取消</Button>
        <Button type="primary" @click="newPhotoList">保存</Button>
      </div>
    </Modal>

    <!-- 是否删除相册 -->
    <Modal v-model="isdelete" width="360" class-name="vertical-center-modal">
      <p slot="header" style="color:green;text-align:center">
        <Icon type="ios-information-circle"></Icon>
        <span>删除相册</span>
      </p>
      <div style="text-align:center">
        <p>确定删除吗？</p>
      </div>
      <div slot="footer">
        <Button type="success" size="large" long @click="del">确定删除</Button>
      </div>
    </Modal>
    <!-- 模态框——照片上传 -->
    <Modal
      v-model="uploadPhoto1"
      title="上传照片"
      class-name="modal1 vertical-center-modal"
      width="800"
      >
      <div style="min-height:300px">
        <div>
          <label>
            <p>上传到</p>
          </label>
          <Select class="select1" size="large" @on-change="getindex" v-model="selectedValue">
            <Option
              v-for="(item,index) in selPhoto"
              :key="index"
              :value="item.mediaId"
              class="option1"
            >
              <img :src="item.imageUrl" style="width:35px;height:35px;margin:0 4px">
              {{item.mediaName}}
            </Option>
          </Select>
        </div>
        <vupload
          class="mt20"
          style="margin-left:73px"
          :ref="`upload${activeIndex}`"
          @on-getPictureList="getPictureList($event,activeIndex)"
          :total="100"
          :hint="'支持拓展名称：png jpg'"
        ></vupload>
      </div>

      <div slot="footer">
        <Button @click="updatePhotoFolder" type="primary">确定上传</Button>
        <Button @click="cancel1">取消</Button>
      </div>
    </Modal>
    <!-- 显示单个相片 -->
    <Modal v-model="photoSrc" width="900" :footer-hide="footerHide">
      <div class="photoSrc mt30">
        <Icon type="ios-arrow-dropleft" size="50" @click="dropleft"/>
        <img v-if="photoSrc" :src="photoLists[Pid].mediaUrl" style="width:708px;height:472px">
        <!-- 放大按钮 -->
        <div class="scanner" @click="Zoom = true">
          <Icon type="ios-search-outline" size="30" color="white"/>
        </div>
        <Icon type="ios-arrow-dropright" size="50" @click="dropright"/>
        <Row type="flex" justify="space-between" class="code-row-bg mt30">
          <Col span="1">
            <Icon type="ios-arrow-dropleft" size="30" @click="dropleft" class="mt15"/>
          </Col>
          <Col span="22" class="photoPreview">
            <div v-for="(photo,index) in photoLists" :key="index" @click="lookPreview(index)">
              <img
                :src="photo.mediaUrl"
                class="previewDetail"
                :class="{photoActive:prePid === index}"
              >
            </div>
          </Col>
          <Col span="1">
            <Icon type="ios-arrow-dropright" size="30" @click="dropright" class="mt15"/>
          </Col>
        </Row>
        <!-- 照片说明 -->
        <div class="mt30" v-if="photoSrc">
          <h2>{{photoLists[Pid].name}}</h2>
          <p>相片描述：{{photoLists[Pid].mediaDescribe}}</p>
          <p>拍摄人：{{photoLists[Pid].author===''?author:photoLists[Pid].author}}</p>
          <p>拍摄时间：{{photoLists[Pid].photoTime===''?photoTime:photoLists[Pid].photoTime | getTime}}</p>
          <p>拍摄地点：{{photoLists[Pid].photoAddress===''?'无':photoLists[Pid].photoAddress}}</p>
        </div>
      </div>
    </Modal>

    <!-- 模态框——编辑相片 -->
    <Modal
      title="编辑相片"
      v-model="adminPhoto"
      class-name="vertical-center-modal"
      @on-cancel="cancelBtn"
      >
      <Form label-position="left" :label-width="100">
        <FormItem label="相片名">
          <Input v-model="formlist.input1"></Input>
        </FormItem>
        <FormItem label="相片描述">
          <Input v-model="formlist.input2" type="textarea"></Input>
        </FormItem>
        <FormItem label="拍摄人">
          <Input v-model="formlist.input3"></Input>
        </FormItem>
        <FormItem label="拍摄时间">
          <DatePicker
            type="date"
            v-model="photoTime"
            placeholder="Select date"
            format="yyyy-MM-dd"
            @on-change="getPhotoTime"
            style="width:388px"
          ></DatePicker>
        </FormItem>
        <FormItem label="拍摄地点">
          <Input v-model="formlist.input5"></Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button @click="cancelBtn">取消</Button>
        <Button type="primary" @click="saveButton">保存</Button>
      </div>
    </Modal>

    <!-- 图片放大 -->
    <Modal
      v-model="Zoom"
      class-name="vertical-center-modal"
      :footer-hide="footerHide"
      @on-cancel="Zoom = false"
      fullscreen
      >
      <div style="display:flex;justify-content:center;height:100%;align-items:center">
        <img v-if="photoSrc" :src="photoLists[Pid].mediaUrl">
      </div>
    </Modal>
    <Modal
        title="移动分组"
        v-model="isMove"
        class-name="vertical-center-modal"
        >
        <Row :gutter="16">
          <Col span="6" v-for="(item, index) in allphotos" class="pb10" v-if="moveItem.mediaId != item.mediaId">
            <div style="position: relative;" @click="handleChecked(index)" :class="item.checked ? 'movechecked' : ''">
              <img :src="item.imageUrl" alt="" width="100%" height="72px">
              <p class="ell pd5">{{item.mediaName}}</p>
              <Checkbox v-if="item.checked" v-model="item.checked" style="position: absolute; top: 3px; left: 3px;"></Checkbox>
            </div>
          </Col>
        </Row>
        <div slot="footer">
          <!-- <Button @click="isMove = false">取消</Button> -->
          <Button type="primary" @click="hendleMove">移动</Button>
        </div>
    </Modal>
  </div>
</template>

<script>
import vupload from "~components/vui-upload";
import { create } from "domain";
export default {
  data() {
    return {
      formlist: {
        input1: "",
        input2: "",
        input3: "",
        // input4: "",
        input5: ""
      },
      isEmpty: false,
      selPhoto: [],
      Zoom: false,
      footerHide: true,
      selectedValue: "",
      photoShow: true, //进入相册与返回
      isdelete: false,
      uploadPhoto1: false,
      editPhoto1: false,
      createPhoto1: false,
      buttonIndex: 0,
      buttonIndex2: 0,
      activeIndex: 0, //照片文件夹自动生成的编号
      mediaId: 0,
      mediaName: "",
      mediaDescribe: "",
      modal: false,
      list: [],
      pagetotal: 0,
      total: 0,
      pageSize: 8,
      pageNum: 1,
      photoNum: 1,
      Mid: 0, //照片文件夹序列index
      Pid: 0, //照片index
      photos: [], //照片文件夹
      photoLists: [], //照片浏览
      photoSrc: false, //查看单个照片模态
      adminPhoto: false,
      isActive: false, //当前激活的预览图片
      prePid: -1, //预览图的id
      author: "", //创建人和拍摄人
      photoTime: "",
      isMove: false,
      moveItem: {},
      moveMediaId: '',
      allphotos: []
    };
  },
  components: {
    vupload
  },
  methods: {
    // 点击 移动到相册
    onMove (item, index) {
      // 获取全部的相册 用于移动分组
      this.$api.post("/member/media/listMediaLibrary", {
          mediaType: 1,
          account: this.$user.loginAccount,
          pageNum: 2,
          pageSize: 9999999
        })
        .then(res => {
            if (res.code === 200) {
              this.allphotos = res.data;
            }
        });
      this.moveItem = item
      this.moveMediaId = ''
      this.isMove = true
    },
    // 移动相册 点击确定移动
    hendleMove () {
      if (this.moveMediaId) {
        this.$api.post('/member/media/updateMediaLibraryDetail', {id: this.moveItem.id, mediaId: this.moveMediaId}).then(res => {
          if (res.code == 200) {
            this.isMove= false
            this.$Message.success('移动成功')
            if (this.photoLists.length == 1) {
              if (this.photoNum == 1) {
                this.photoPageChange(1)
              } else {
                this.photoPageChange(this.photoNum - 1)
              }
            } else {
              this.photoPageChange(this.photoNum)
            }
          } else {
            this.isMove= false
            this.$Message.error('移动失败')
          }
        })
      } else {
        this.$Message.warning('请选择要移动的相册')
      }
    },
    // 移动相册 中选择相册
    handleChecked (index) {
      this.allphotos.forEach((e, i) => {
        let item = this.allphotos[i]
        if (index == i) {
          item.checked = true
          this.moveMediaId = e.mediaId
          this.allphotos.splice(i, 1, item)
        } else {
          item.checked = false
          this.allphotos.splice(i, 1, item)
        }
      })
    },
    //查询分页
    queryData() {
      this.$api
        .post("/member/media/listMediaLibrary", {
          mediaType: 1,
          account: this.$user.loginAccount,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        })
        .then(res => {
          this.photos = res.data;
          console.log(res);
          this.pagetotal = res.total;
        });
    },
    // 翻页
    onChange() {
      this.pageNum = 1;
    },
    pageChange(page) {
      this.pageNum = page;
      this.photos = [];
      this.queryData();
    },
    photoPageChange(page) {
      this.photoNum = page;
      this.photoLists = [];
      this.$api
        .post("/member/media/listMediaLibraryDetail", {
          mediaId: this.photos[this.Mid].mediaId,
          pageNum: page,
          pageSize: this.pageSize
        })
        .then(res => {
          this.photoLists = res.data;
          this.total = res.total;
        });
    },
    //打开上传框
    uploadPhoto() {
      this.uploadPhoto1 = true;
      this.buttonIndex = 0;
      this.buttonIndex2 = 0;
    },
    cancel1() {
      this.uploadPhoto1 = false;
    },
    updatePhotoFolder() {
      if (this.list.length === 0) {
        this.$Message.error("上传的图片不能为空！");
      } else if (this.activeIndex === 0) {
        this.$Message.error("请选择一个相册！");
      } else {
        this.$api
          .post("/member/media/saveMediaLibraryDetail", {
            mediaId: this.activeIndex,
            mediaUrl: this.list
          })
          .then(res => {
            this.uploadPhoto1 = false;
            console.log(res);
            this.lookPhotos(this.Mid);
          });
      }
    },
    editCancel() {
      this.editPhoto1 = false;
    },
    updatePhotoList() {
      this.$api
        .post("/member/media/updateMediaLibrary", {
          mediaId: this.photos[this.Mid].mediaId,
          mediaName: this.formlist.input1,
          mediaDescribe: this.formlist.input2,
          author: this.formlist.input3,
          photoTime: this.photoTime
        })
        .then(res => {
          this.queryData();
          if (res.code === 200) {
            this.$Message.success("修改成功！");
            this.formlist.input1 = "";
            this.formlist.input2 = "";
            this.formlist.input3 = "";
            this.photoTime = "";
          } else {
            this.$Message.error("修改失败！");
          }
        });
      this.editPhoto1 = false;
    },
    //获取相册自动生成的id
    getindex(val) {
      this.activeIndex = val;
      console.log(val);
    },
    getPhotoTime(val) {
      this.photoTime = val;
    },
    //编辑相册
    editPhoto(index) {
      this.editPhoto1 = true;
      this.Mid = index;
      this.formlist.input1 = this.photos[index].mediaName;
      this.formlist.input2 = this.photos[index].mediaDescribe;
      this.formlist.input3 =
        this.photos[index].author === ""
          ? this.author
          : this.photos[index].author;
      this.photoTime =
        this.photos[index].photoTime === ""
          ? this.photos[index].createTime
          : this.photos[index].photoTime;
      this.buttonIndex2 = 1;
    },

    //创建相册
    createPhotoFolder() {
      this.formlist.input1 = "";
      this.formlist.input2 = "";
      this.formlist.input3 = this.author;
      this.buttonIndex = 1;
      this.createPhoto1 = true;
    },
    newPhotoList() {
      if (this.formlist.input1 === "") {
        this.$Message.error("相册名不能为空！");
      } else {
        this.$api
          .post("/member/media/saveMediaLibrary", {
            mediaName: this.formlist.input1,
            mediaDescribe: this.formlist.input2,
            author: this.formlist.input3,
            // photoTime:this.formlist.input4,
            mediaType: 1,
            image_url: "../static/datas/img/myStyle/no-picture.png",
            account: this.$user.loginAccount
          })
          .then(res => {
            if (res.data === 1) {
              this.queryData();
              this.formlist.input1 = "";
              this.formlist.input2 = "";
              this.formlist.input3 = "";
              this.createPhoto1 = false;
              this.queryDataAll()
              this.$Message.success("创建成功！");
            } else if (res.code === 1000 || response.code === "1000") {
              this.$Message.error("相册已存在！");
            }
          });
      }
    },
    //删除相册
    goDel() {
      this.isdelete = true;
      this.buttonIndex2 = 2;
    },
    del() {
      this.$api
        .get(
          "/member/media/deleteMediaLibrary/" + this.photos[this.Mid].mediaId
        )
        .then(res => {
          if (res.data === 1) {
            this.$Message.success("删除成功！");
          }
          this.queryData();
        });
      this.isdelete = false;
      this.editPhoto1 = false;
      console.log(this.photos);
    },
    // 上传相片的插件
    getPictureList($event, e) {
      var arr = [];
      $event.forEach(element => {
        if (element.response) {
          arr.push({
            name: element.name,
            url: element.response.data.picName
          });
        }
      });
      console.log($event);
      this.list = arr;
    },
    //查询相册内容
    lookPhotos(index) {
      this.photoLists = [];
      this.photoNum = 1;
      this.photoShow = false;
      this.Mid = index;
      this.$api
        .post("/member/media/listMediaLibraryDetail", {
          mediaId: this.photos[index].mediaId,
          pageNum: this.photoNum,
          pageSize: this.pageSize
        })
        .then(res => {
          this.photoLists = res.data;
          console.log(res);
          this.total = res.total;
          if (this.photoLists.length === 0) {
            this.isEmpty = true;
          }
        });
    },
    //查看单张相片
    lookPhotoDetail(index) {
      this.photoSrc = true;
      console.log(index);
      this.Pid = index;
      this.lookPreview(index);
    },
    //预览点击事件
    lookPreview(index) {
      this.prePid = index;
      this.Pid = index;
    },
    dropleft() {
      if (this.Pid === 0 && this.photoNum === 1) {
        this.$Message.error("已经到顶啦！");
      } else if (this.Pid === 0) {
        this.$api
          .post("/member/media/listMediaLibraryDetail", {
            mediaId: this.photos[this.Mid].mediaId,
            pageNum: this.photoNum - 1,
            pageSize: this.pageSize
          })
          .then(res => {
            this.photoNum--;
            this.Pid = this.pageSize - 1;
            this.photoLists = res.data;
            this.lookPreview(this.Pid);
          });
      } else {
        this.Pid--;
      }
      this.lookPreview(this.Pid);
    },
    dropright() {
      if (
        this.Pid === (this.total % this.pageSize) - 1 &&
        this.photoNum === parseInt(this.total / this.pageSize + 1)
      ) {
        this.$Message.error("已经到底啦！");
      } else if (this.Pid === this.pageSize - 1) {
        this.$api
          .post("/member/media/listMediaLibraryDetail", {
            mediaId: this.photos[this.Mid].mediaId,
            pageNum: this.photoNum + 1,
            pageSize: this.pageSize
          })
          .then(res => {
            this.photoNum++;
            this.Pid = 0;
            this.photoLists = res.data;
            this.lookPreview(this.Pid);
          });
      } else {
        this.Pid++;
      }
      this.lookPreview(this.Pid);
    },
    //设置相册封面
    setCover(index) {
      this.$api
        .post("/member/product-base/media-library-cover", {
          imageUrl: this.photoLists[index].mediaUrl,
          mediaId: this.photoLists[index].mediaId
        })
        .then(response => {
          if (response.code === 200) {
            this.$Message.success("相册封面设置成功！");
            this.queryData();
          } else {
            this.$Message.error("相册封面设置失败！");
          }
        })
        .catch(error => {
          this.$Message.error("相册封面设置失败！");
        });
    },
    //编辑照片
    setEdit(index) {
      this.Pid = index;
      this.adminPhoto = true;
      console.log(this.photoLists[index]);
      this.formlist.input1 = this.photoLists[index].name;
      this.formlist.input2 = this.photoLists[index].mediaDescribe;
      this.formlist.input3 =
        this.photoLists[index].author === ""
          ? this.author
          : this.photoLists[index].author;
      this.photoTime =
        this.photoLists[index].photoTime === ""
          ? this.photoLists[index].createTime
          : this.photoLists[index].photoTime;
      this.formlist.input5 =
        this.photoLists[index].photoAddress == ""
          ? "请输入拍摄地址"
          : this.photoLists[index].photoAddress;
    },
    saveButton() {
      this.$api
        .post("/member/media/updateMediaLibraryDetail", {
          id: this.photoLists[this.Pid].id,
          name: this.formlist.input1,
          mediaDescribe: this.formlist.input2,
          author: this.formlist.input3,
          photoTime: this.photoTime,
          photoAddress: this.formlist.input5
        })
        .then(res => {
          if (res.code === 200) {
            this.$Message.success("修改成功！");
            this.formlist.input1 = "";
            this.formlist.input2 = "";
            this.formlist.input3 = "";
            this.photoTime = "";
            this.formlist.input5 = "";
            this.lookPhotos(this.Mid);
            this.adminPhoto = false;
          } else {
            this.$Message.error("修改失败！");
          }
        });
    },
    cancelBtn() {
      this.adminPhoto = false;
      this.formlist.input1 = "";
      this.formlist.input2 = "";
      this.formlist.input3 = "";
      this.photoTime = "";
      this.formlist.input5 = "";
    },
    // 照片删除
    setDelete(index) {
      this.$Modal.confirm({
        title: "操作提示",
        content: "<p>是否确认删除这张照片？</p>",
        onOk: () => {
          this.$api
            .get(
              "/member/media/deleteMediaLibraryDetail/" +
                this.photoLists[index].id
            )
            .then(response => {
              if (response.data === 1) {
                this.lookPhotos(this.Mid);
                this.$Message.info("删除成功");
              }
            });
        },
        onCancel: () => {}
      });
    },
    photoShowBtn() {
      this.photoShow = true;
      this.isEmpty = false;
    },
    queryDataAll() {
      this.$api
        .post("/member/media/listMediaLibrary", {
          mediaType: 1,
          account: this.$user.loginAccount,
          pageNum: 1,
          pageSize: 9999
        })
        .then(res => {
          this.selPhoto = res.data;
        });
    }
  },
  created() {
    this.queryData();
    this.queryDataAll()
    this.$api
      .post("/member/login/findCurrentUser", {
        account: this.$user.loginAccount
      })
      .then(res => {
        this.author = res.data.displayName;
      });
  },
  filters: {
    getTime(val) {
      if (val != undefined) {
        return val.slice(0, 10);
      }
    }
  }
};
</script>

<style lang='scss' scoped>
.movechecked {
  box-shadow: 0px 0px 0px 2px #00c587;
}
</style>

<style lang='scss' scoped>
.xc01 {
  background: #f5f5f5;
}
.button-area {
  padding: 21px;
  background: #ffffff;
  width: 1000px;
}
.button-area button {
  margin-right: 14px;
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
  transition: 0.3s;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  margin: 22px 8px 0;
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
  div {
    font-family: PingFangSC-Regular;
    color: #4a4a4a;
    text-align: center;
    font-size: 14px;
    line-height: 20px;
    width: 40px;
    height: 20px;
  }
  .modal1 {
    background: #ffffff;
    box-shadow: 0px 4px 12px 0px rgba(0, 0, 0, 0.2);
  }
}
label p {
  font-family: PingFangSC-Regular;
  color: #4a4a4a 100%;
  font-size: 16px;
  display: inline-block;
  padding-right: 22px;
}
.select1 {
  width: 260px;
}

.demo-upload-list {
  margin-top: 38px;
  display: inline-block;
  width: 150px;
  height: 150px;
  text-align: center;
  border: 1px solid transparent;
  border-radius: 4px;
  overflow: hidden;
  background: #fff;
  position: relative;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
  margin-right: 4px;
  img {
    width: 100%;
    height: 100%;
  }
  &:hover {
    .demo-upload-list-cover {
      display: block;
    }
  }
}
.demo-upload-list-cover {
  display: none;
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 30px;
  background: rgba(0, 0, 0, 0.6);
  i {
    color: #fff;
    font-size: 20px;
    cursor: pointer;
    padding-left: 100px;
    line-height: 30px;
  }
}
//单个相片封面
.photoLists {
  position: relative;
  left: -8px;
  width: 238px;
  height: 286px;
  margin: 16px 8px 0;
  background: #ffffff;
  transition: 0.3s;
  img {
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
  &:hover {
    box-shadow: 0px 12px 18px 4px rgba(0, 0, 0, 0.15);
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
  .setBtn {
    display: none;
    a {
      width: 36px;
      padding-left: 6px;
      display: inline-block;
      font-family: PingFangSC-Regular;
      color: #4a4a4a !important;
    }
  }
}
.photoSrc {
  text-align: center;
  position: relative;
}
.photoSrc i:hover {
  cursor: pointer;
}
.photoPreview {
  display: flex;
  justify-content: center;
}
.previewDetail {
  width: 96px;
  height: 58px;
  float: left;
}
.photoActive {
  border: 2px solid green;
  box-sizing: border-box;
}
.scanner {
  position: absolute;
  background: rgba(0, 0, 0, 0.42);
  right: 10%;
  bottom: 30%;
  width: 40px;
  height: 40px;
  text-align: center;
  line-height: 40px;
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