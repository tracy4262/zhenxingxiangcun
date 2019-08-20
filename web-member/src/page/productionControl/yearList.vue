<template>
  <div class="year">
    <div class="year_title">年度文件</div>
    <Row class="year_list">
      <Col
        span="3"
        v-for="(item, index) in files"
        :key="index"
      >
        <div
          class="item tc mt15"
          @click="onFileSelect(item)"
          style="cursor:pointer"
        >
          <img :src="`../static/img/${item.checked ? 'icon-file-active.png' : 'icon-file-default.png'}`" />
          <p class="ell">{{item.fileName}}</p>
          <Icon
            type="ios-close-circle"
            color="#ed4014"
            size="20"
            @click.stop="onFileDel(item)"
          />
        </div>
      </Col>
      <Col span="3">
        <div
          class="mt15 tc"
          style="cursor:pointer"
          @click="onFileAdd"
        >
          <img src="../../../static/img/icon-file-add.png" />
          <p>添加</p>
        </div>
      </Col>
    </Row>
     <!-- 添加弹窗 -->
    <Modal
    v-model="fileAddModel"
    title="添加年度文件夹"
    :mask-closable="false"
    class-name="vertical-center-modal"
    width="360">
      <div>
        <DatePicker type="year" placeholder="请选择年份" format="yyyy年度" editable @on-change="getYear" 
        :open="open"
         @on-clear="open = false"
         @on-ok="open = false" style="width: 100%;">
          <a href="javascript:void(0)" @click="open = !open">
            <Input v-model="fileName" readonly icon="ios-calendar-outline" @on-blur="open = false"/>
          </a>
        </DatePicker>
      </div>
      <div slot="footer">
          <Button type="text" @click="cancel">取消</Button>
          <Button type="primary" @click="onSaveFileName">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  data () {
    return {
      files: [],
      fileAddModel: false,
      open: false,
      fileName: '',
    }
  },
  created () {
    this.init()
  },
  methods: {
    // 初始化查询年度文件夹信息
    init () {
      this.$api.post('/shop/plant/findPlantYearInfo', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.code === 200) {
          this.files = response.data
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    onFileSelect (item) {
      this.$router.push(`/productionControl/plantList?yearId=${item.id}&year=${item.fileName}`)
    },
    onFileDel (item) {
      this.$Modal.confirm({
        title: '操作提示',
        content: '<p>您确认要删除该文件吗？</p>',
        onOk: () => {
          this.$api.post('/shop/plant/deletePlantYearInfo', {
            id: item.id
          }).then(response => {
            if (response.code === 200) {
              this.$Message.info('删除成功！')
              this.init()
            }
          }).catch(error => {
            this.$Message.error('删除失败！')
          })
        }
      })
    },
    onFileAdd () {
      this.fileAddModel = true
    },
    getYear (v1, v2) {
      this.fileName = v1
    },
    // 新增年度文件夹
    onSaveFileName() {
      if (this.fileName !== '') {
        this.$api.post('/shop/plant/savePlantYearInfo', {
          account: this.$user.loginAccount,
          fileName: this.fileName
        }).then(response => {
          if (response.code === 200) {
            this.$Message.success('添加成功！')
            this.files = []
            this.init()
            this.fileAddModel = false
            this.fileName = ''
          } else if (response.code === 300) {
            this.$Message.error('该年度文件夹已存在！')
          }
        }).catch(error => {
          this.$Message.error('服务器异常！')
        })
      }
    },
    cancel () {
      this.fileAddModel = false
      this.fileName = ''
    },
  }
}
</script>

<style lang="scss" scoped>
.year {
  width: 1000px;
  min-height: 800px;
  margin: 0 auto;
  background-color: #fff;
  padding: 48px;
  .year_title {
    height: 22px;
    line-height: 22px;
    font-size: 16px;
    color: #4a4a4a;
    padding-left: 10px;
    border-left: 9px solid #00c587;
    font-weight: bold;
  }
  .year_list {
    padding: 38px 0;
    margin-top: 8px;
    border-top: 1px solid #e8e8e8;
    // border-bottom: 1px solid #e8e8e8;
    .item {
      position: relative;
      overflow: hidden;
      &:hover {
        .ivu-icon {
          right: 15px;
        }
      }
      .ivu-icon {
        position: absolute;
        top: 0;
        right: -100px;
        transition: all 0.3s;
      }
    }
  }
  .add {
    text-align: center;
  }
}
</style>
