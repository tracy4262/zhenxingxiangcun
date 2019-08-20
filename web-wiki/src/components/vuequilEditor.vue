<template>
  <div class="vui-layout mt30 mb30">
    <quill-editor  
      ref="quillEditor"
      :content="content"
      @change="onEditorChange($event)">
    </quill-editor>   
    <Upload :show-upload-list="false"
      style="display: none;"
      ref="up"
      name="upfile"
      :format="accept"
      :max-size="maxsize"
       multiple type="drag"
      :on-exceeded-size="handleMaxSize"
      :on-success="handleSuccess"
      :action="`${$url.upload}upload/up`" >
      <input id="uploadInput" />
    </Upload>
  </div>
</template>

<script>
import { quillEditor } from 'vue-quill-editor'
export default {
  components: {
    quillEditor
  },
  props: {
    accept: {
      type: Array
    },
    maxsize: {
      type: Number
    },
    content: {
      type: String
    }
  },
  data () {
    return {
      visible: false,
      src: '',
      uploadList: []
    }
  },
  created () {
  },
  computed: {
    editor () {
      return this.$refs.quillEditor.quill
    }
  },
  mounted () {
    this.uploadList = this.$refs.up.fileList
    this.$refs.quillEditor.quill.getModule('toolbar').addHandler('image', this.imgClick)
    this.$refs.quillEditor.quill.getModule('toolbar').addHandler('video', this.imgClick)
  },

  methods: {
    // 点击图片触发事件
    imgClick () {
      this.trigger('click', 'uploadInput')
    },
    trigger (event, ele) {
      // IE
      if (document.all) {
        document.getElementById(ele).click()
      } else {
        var e = document.createEvent('MouseEvents')
        // 这里的click可以换成你想触发的行为
        e.initEvent(event, true, true)
        // 这里的clickME可以换成你想触发行为的DOM结点
        document.getElementById(ele).dispatchEvent(e)
      }
    },
    // 查看图片
    handleView (name) {
      this.src = 'http://' + name
      this.visible = true
    },
    // 删除图片
    handleRemove (file) {
      // 从 upload 实例删除数据
      const fileList = this.$refs.up.fileList
      this.$refs.up.fileList.splice(fileList.indexOf(file), 1)
    },
    // 成功回调
    handleSuccess (response, file, fileList) {
      if (response.code === 500) {
        this.$Message.error('上传失败!')
      } else {
        this.$Message.success('上传成功!')
        // 把图片插入指定的位置
        this.editor.insertEmbed(this.editor.getSelection().index, 'image', 'http://' + response.data.picName)
      }
    },
    // 文件太大，错误提示
    handleMaxSize (file) {
      this.$Notice.warning({
        title: 'Exceeding file size limit',
        desc: 'File  ' + file.name + ' is too large, no more than 100M.'
      })
    },
    onEditorChange ({editor, html, text}) {
      this.$emit('quill-change', html)
    }
  }
}
</script>
<style>
  .ql-container .ql-editor {
    height: 30em;
  }
</style>