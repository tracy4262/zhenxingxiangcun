<template>
  <div class="add-map min-height-main">
    <div class="w1200 pt20">
      <Card class="pd10">
        <Row>
          <Col span="18">
            <Button type="primary" class="mr20" @click="handleUpload"> <Icon type="ios-cloud-upload-outline" size="18"/>上传</Button>
            <Button @click="handleAdd"> <Icon type="md-add" size="18"/> 新建</Button>
          </Col>
          <Col span="6" v-if="showList" class="tr">
            <Button @click="showList = false"> <Icon type="ios-arrow-back" size="18"/> 返回</Button>
          </Col>
        </Row>
      </Card>
      <div class="mt20 pd20" v-if="!showList">
        <!-- <div class="pb20">
          <Button type="primary">上传</Button>
          <Button>新建</Button>
        </div> -->
        <Row :gutter="16">
          <Col span="4" class="pb20" v-for="(item, index) in data" :key="index" >
            <div class="li">
              <div class="pd15" @click="handleDetail(item, index)">
                <img :src="defaultAvatar" alt="" width="100%" height="130px">
                <p class="tc ell-1 pt3">{{item.name}}</p>
              </div>
              <Row class="edit-tool">
                <Col span="12">
                  <span class="btn edit" @click="handleEdit(item, index)">编辑</span>
                </Col>
                <Col span="12">
                  <span class="btn del" @click="handleDel(item, index)">删除</span>
                </Col>
              </Row>
            </div>
          </Col>
        </Row>
        <Page :total="total" :page-size="pageSize" @on-change="onChange" class="tc pt20 pb30"></Page>
      </div>
      <mapList v-if="showList" ref="mapList"></mapList>
    </div>
    <!-- 新增修改文件夹 -->
    <Modal
      :mask-closable="false"
      v-model="showAdd"
      :title="AddTitle"
      >
      <div>
        <Form ref="addData" :model="addData" :rules="addDataRules" :label-width="80">
          <FormItem label="名称" prop="name">
            <Input v-model="addData.name" :maxlength="20"></Input>
          </FormItem>
          <FormItem label="描述" prop="description">
            <Input v-model="addData.description" type="textarea" :autosize="{minRows: 3,maxRows: 6}" :maxlength="200"></Input>
          </FormItem>
          <FormItem label="创建人" prop="founder">
            <Input v-model="addData.founder" ></Input>
          </FormItem>
          <FormItem label="创建时间" prop="creationTime">
            <Input v-model="addData.creationTime" readonly></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer" class="tc">
        <Button @click="showAdd = false">取消</Button>
        <Button @click="handleSave(2)" type="primary">确定</Button>
      </div>
    </Modal>
    <!-- 上传 -->
     <Modal
      :mask-closable="false"
      v-model="shwoUpload"
      :title="uploadTitle"
      >
      <div>
        <Form ref="uploadData" :model="uploadData" :rules="uploadDataRules" :label-width="80">
          <FormItem label="上传到" prop="folderId">
            <Select v-model="uploadData.folderId">
              <Option v-for="(item, index) in uploadList" :value="item.folderId" :key="index">{{item.folderName}}</Option>
            </Select>
          </FormItem>
          <FormItem label="上传" prop="fileList">
            <vuiUploadFile
              ref="file"
              hint="请上传文件"
              @on-getFileList="getFileList"
              ></vuiUploadFile>
          </FormItem>
        </Form>
      </div>
      <div slot="footer" class="tc">
        <Button @click="shwoUpload = false">取消</Button>
        <Button @click="handleSave(1)" type="primary">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import vuiUploadFile from '_c/vui-upload-file'
  import defaultAvatar from '@/assets/img/folder.jpg';
  import mapList from './mapList';
  export default {
    name: '',
    components: {
      vuiUploadFile,
      mapList
    },
    data() {
      return {
        defaultAvatar: defaultAvatar,
        total: 0,
        pageSize: 12,
        pageNum: 1,
        data: [],
        showAdd: false,
        AddTitle: '新增',
        addDataRules: {
          name: [
            { required: true, message: '请输入文件夹名称', trigger: 'blur' }
          ]
        },
        addData: {
          name: '',
          description: '',
          founder: '',
          creationTime: ''
        },
        uploadDataRules: {
          folderId: [
            { required: true, message: '请选择文件夹', trigger: 'change', type: 'number' }
          ],
          fileList: [
            { required: true, message: '请上传文件', trigger: 'change', type: 'array' }
          ]
        },
        shwoUpload: false,
        uploadTitle: '上传地图',
        uploadList: [],
        uploadData: {
          folderId: '',
          fileList: ''
        },
        showList: false,
        displayName: ''
      }
    },
    created () {
      // 根据用户名查询 头像跟displayName
      this.$api.post('/member/login/findCurrentUser', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.data.displayName) {
          this.displayName = response.data.displayName
        }
      })
      this.getFind()
      this.init()
    },
    methods: {
      init () {
        // 查询文件夹 全部列表 放在下拉列表中
        this.$api.get(`/member-reversion/myMap/allFolder?account=${this.$user.loginAccount}`).then(res => {
          if (res.code === 200) {
            this.uploadList = res.data
          }
        })
      },
      // 查询文件夹列表
      getFind () {
        this.$api.post(`/member-reversion/myMap/folderList`, {
          account: this.$user.loginAccount,
          pageSize: this.pageSize,
          pageNum: this.pageNum
        }).then(res => {
          if (res.code === 200) {
            this.data = res.data.list
            this.total = res.data.total
          }
        })
      },
      // 点击文件夹 查看文件列表
      handleDetail (item, index) {
        this.showList = true
        this.$nextTick(() => {
          this.$refs['mapList'].init(item.id)
        })
      },
      // 文件夹列表 分页改变
      onChange (e) {
        this.pageNum = e
        this.getFind()
      },
      // 点击确定 1 上传地图 2 新建文件夹
      handleSave (type) {
        if (type === 1) { // 上传地图
          this.$refs['uploadData'].validate(e => {
            if (e) {
              this.$api.post('/member-reversion/myMap/uploadMap',this.uploadData).then(res => {
                if (res.code === 200) {
                  this.$Message.success('保存成功')
                  this.shwoUpload = false
                  // 说明是上传的当前打开的文件夹 更新一下当前文件夹数据
                  if (this.showList && this.$refs['mapList'].id == this.uploadData.folderId) {
                    this.$refs['mapList'].pageChange(1)
                  }
                } else {
                  this.$Message.error('保存失败')
                }
              })
            } else {
              this.$Message.error('请核对表单数据')
            }
          })
        } else if (type === 2) { // 新建文件夹
          this.$refs['addData'].validate(e => {
            if (e) {
              this.addData.account = this.$user.loginAccount
              let url = ''
              // 更新 还是 新增
              if (this.addData.id) {
                url = '/member-reversion/myMap/editFolder'
              } else {
                url = '/member-reversion/myMap/createFolder'
              }
              this.$api.post(url, this.addData).then(res =>{
                if (res.code === 200) {
                  this.$Message.success('保存成功')
                  this.showAdd = false
                  // 当前页数据 小于每页显示数据 则更新一下当前页数据
                  if (this.data.length < this.pageSize) {
                    this.onChange(this.pageNum)
                  }
                  this.init()
                } else {
                  this.$Message.error('保存失败')
                }
              })
            } else {
              this.$Message.error('请核对表单数据')
            }
          })
        }
      },
      // 点击上传
      handleUpload () {
        this.shwoUpload = true
        this.$refs['uploadData'].resetFields()
        this.$refs['file'].handleGive('')
      },
      // 文件上传后的回调 要存 文件名称 文件大小 文件路径
      getFileList (list) {
        var arr = []
        list.forEach(element => {
            if(element.response){
              arr.push({
                name: element.name,
                size: element.size,
                url: element.response.data.picName
              })
            }
        })
        this.uploadData.fileList = arr
        this.$nextTick(() => {
          this.$refs.uploadData.validateField('fileList')
        })
      },
      // 点击添加
      handleAdd () {
        this.showAdd = true
        this.AddTitle = '新增'
        this.$refs['addData'].resetFields()
        this.addData.founder = this.displayName
        this.addData.creationTime = this.moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
      },
      // 点击编辑
      handleEdit (item, index) {
        this.showAdd = true
        this.AddTitle = '编辑'
        this.$refs['addData'].resetFields()
        this.addData = Object.assign({}, item)
      },
      // 点击删除
      handleDel (item ,index) {
        this.$Modal.confirm({
          title: '是否确定删除',
          onOk:()=>{
            this.$api.get(`/member-reversion/myMap/deleteFolder?id=${item.id}`).then(res => {
              if (res.code === 200) {
                this.$Message.success('删除成功')
                if (this.data.length === 1) {
                  if (this.total === 1) {
                    this.data = []
                  } else {
                    this.onChange(1)
                  }
                } else {
                  this.data.splice(index, 1)
                }
              } else {
                this.$Message.error('删除失败')
              }
            })
          },
          okText:'确定',
          cancelText:'取消'
        })
      }
    }
  }
</script>

<style lang="less" scoped>
@import '../css/colors.less';
.bg-trey{
  // background: #f5f5f5;
  height: 100%;
}
.add-map{
  .edit-tool{
    .del,.edit{
      display: inline-block;
      width: 100%;
      height: 35px;
      line-height: 35px;
      text-align: center;
      background: #fafafa;
      cursor: pointer;
      &:hover{
        color: @link-color;
      }
    }
    .del{
      border-left: 1px solid #f5f5f5;
    }
  }
  .li{
    box-shadow: 2px 5px 14px 0 rgba(0,0,0,.1);
    position: relative;
    .name{
      position: absolute;
      width: 100%;
      top:10px;
    }
  }
}
</style>
