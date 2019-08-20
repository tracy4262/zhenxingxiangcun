<template>
  <Row type="flex" align="bottom" class="mb20">
    <Col span="12">
      <div class="mb10">
        <Upload
          name="file"
          ref="upload"
          :action="action"
          :show-upload-list="false"
          :format="['jpg', 'png']"
          :max-size="2048"
          :on-success="handleUploadSuccess"
          :on-exceeded-size="handleMaxSize"
          :on-format-error="handleUploadFormatError">
          <Button type="default" icon="md-add-circle">上传图书封面</Button>
          <!-- <span class="ml5 mt5 t-grey" style="font-size:12px">添加图书封面，让你的图书更有吸引力</span> -->
        </Upload>
      </div>
      <vui-cropper
      style="height: 270px; overflow:hidden"
      ref="cropper"
      :img="cropper.img"
      :outputSize="cropper.size"
      :outputType="cropper.outputType"
      :info="cropper.info"
      :canScale="cropper.canScale"
      :autoCrop="cropper.autoCrop"
      :autoCropWidth="cropper.autoCropWidth"
      :autoCropHeight="cropper.autoCropHeight"
      :fixed="cropper.fixed"
      :fixedNumber="cropper.fixedNumber"
      @realTime="realTime"
      ></vui-cropper>
    </Col>
    <Col span="9" offset="2">
      <div class="show-preview" style="width:172px;height:240px;overflow:hidden;margin:5px;margin:auto;background: #eee">
    		<div :style="previews.div">
    			<img :src="previews.url" :style="previews.img">
    		</div>
    	</div>
      <p class="t-grey mt10 tc" style="font-size:12px">拖动或缩放左侧上传区图片以调整最终显示效果</p>

    </Col>
  </Row>
</template>
<script>
import vuiCropper from 'vue-cropper'
export default {
  components: {
    vuiCropper
  },
  data () {
    return {
      action: `${this.$url.uploadBase64}/imgbase64/converter`,
      cropper: {
        img: '',
        info: true,
        size: 1,
        outputType: 'jpeg',
        canScale: true,
        autoCrop: true,
        // 只有自动截图开启 宽度高度才生效
        autoCropWidth: 172,
        autoCropHeight: 240,
        // 开启宽度和高度比例
        fixed: true,
        fixedNumber: [3, 4]
      },
      previews: {}
    }
  },
  methods: {
    // 上传成功
    handleUploadSuccess (res, file) {
      var n = res.data.name
      var type = n.substring(n.lastIndexOf('.') + 1, n.length)
      this.cropper.img = `data:image/${type === 'jpeg' ? 'jpg' : 'png'};base64,${res.data.base64}`
      this.$emit('on-get-bookPage', this.cropper.img)
    },
    // 格式错误
    handleUploadFormatError (file) {
      this.$Message.warning('文件类型不支持')
    },
    realTime (data) {
      this.previews = data
      this.$emit('on-get-base64', this.$refs.cropper)
    },
      //文件大小
    handleMaxSize (file) {
        this.$Message.warning('请上传不超过2M的文件!')
     },
  }
}
</script>
<style lang="scss" scoped>
</style>
