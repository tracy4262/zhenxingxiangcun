<template>
  <div class="vui-upload-component">
    <Upload ref="upload" :show-upload-list="false"
      name="upfile"
      :format="['jpg', 'png']"
      :max-size="maxsize"
      :multiple="multiple"
      :on-success="handleSuccess"
      :before-upload="handleBeforeUpload"
      :on-format-error="handleFormatError"
      :on-exceeded-size="handleMaxSize"
      :action="action"
      style="display: inline-block;">
      <Button type="ghost" ><Icon type="ios-upload-outline" size="16" class="pr5"></Icon> 上传图片</Button>
    </Upload>
    <p class="t-grey">{{hint}}</p>
    <div class="new-upload-list">
      <div class="new-upload-item" v-for="(item,index) in pictureList" :key="index" :style="{width: `${size[0]}px`, height: `${size[1]}px`}">
        <template v-if="item.status === 'finished'">
          <img :width="`${size[0]}px`" :height="`${size[1]}px`" :src="`${item.response.data.picName}`">
          <div class="upload-list-cover">
            <Icon type="ios-trash-outline" size="30" @click.native="handleRemove(item)"></Icon>
          </div>
        </template>
        <template v-else>
          <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
        </template>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    // 接收绑定数据
    pictureLists: {},
    // 上传张数限制,默认1张
    total: {
      type: Number,
      default: () => {
        return 1
      }
    },
    // 上传大小限制，默认2M
    maxsizes: {
      type: Number,
      default: () => {
        return 2
      }
    },
    // 上传格式限制，默认jpg/png
    format: {
      type: String,
      default: () => {
        return 'jpg/png'
      }
    },
    // 是否支持本地多张上传
    multiple: {
      type: Boolean,
      default: () => {
        return true
      }
    },
    size: {
      type: Array,
      default: () => {
        return [140, 140]
      }
    },
    hint: {
      type: String,
      default: '注：图片大小于2M、请上传正本和副本'
    }
  },
  data () {
    return {
      action: `${this.$url.upload}upload/up`,
      maxsize: 2048,
      pictureList: []
    }
  },
  created () {
    this.maxsize = parseInt(this.maxsizes) * 1024
  },
  methods: {
    // 回显
    handleGive (val) {
      if (val) {
        var arr
        // 判断回显的数据类型，有两种，数组跟字符串
        if (typeof val === 'object') {
          // 数组
          arr = val
        } else {
          // 字符串
          arr = val.split(' ')
        }
        var list = []
        arr.forEach(element => {
          list.push({
            response: {
              data: {
                picName: element
              }
            },
            status: 'finished'
          })
        })
        // 按照一定格式给其赋值
        this.pictureList = this.$refs.upload.fileList = list
      } else {
        this.pictureList = this.$refs.upload.fileList = []
      }
    },
    handleBeforeUpload () {
      const check = this.$refs.upload.fileList.length < this.total
      if (!check) {
        this.$Notice.warning({
          title: `最多只能上传${this.total}张图片。`
        })
      }
      return check
    },
    // 上传成功的回调
    handleSuccess (response, file, fileList) {
      if (response.code === 500) {
        this.$Message.error('上传失败!')
      } else {
        this.$Message.success('上传成功!')
        this.pictureList = this.$refs.upload.fileList
        this.$emit('on-getPictureList', this.$refs.upload.fileList)
      }
    },
    // 删除的方法
    handleRemove (file) {
      this.$refs.upload.fileList.splice(
        this.$refs.upload.fileList.indexOf(file),
        1
      )
      this.pictureList = this.$refs.upload.fileList
      this.$emit('on-getPictureList', this.pictureList)
    },
    // 照片大小限制
    handleMaxSize (file) {
      this.$Notice.warning({
        title: '照片大小超出限制',
        desc: `照片大小过大，应不超过${this.size}。`,
        duration: 6
      })
    },
    // 照片格式限制
    handleFormatError (file) {
      this.$Notice.warning({
        title: '照片格式有误',
        desc: `照片格式不正确，请选择${this.format}格式。`,
        duration: 6
      })
    }
  }
}
</script>
<style lang="scss">
.new-upload-item {
    display: inline-block;
    text-align: center;
    border: 1px solid transparent;
    border-radius: 4px;
    overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0,0,0,.2);
    margin-right: 4px;
    .upload-list-cover{
        color: #fff;
        font-size: 20px;
        cursor: pointer;
        display: none;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0,0,0,.6);
    }
    &:hover .upload-list-cover{
      display: block;
      .ivu-icon{
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate3d(-50%, -50%, 0);
      }
    }
}
</style>

