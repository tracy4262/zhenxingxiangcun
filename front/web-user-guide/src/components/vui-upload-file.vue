<template>
  <div class="vui-upload-component">
    <div class="new-upload-list">
      <div v-for="(item,index) in pictureList" :key="index">
        <template v-if="item.status === 'finished'">
          <p class="t-green pb10">{{item.response.data.name}}</p>
        </template>
        <template v-else>
          <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
        </template>
      </div>
    </div>
    <div>
      <Upload ref="upload" :show-upload-list="false"
        name="file"
        :format="format.split('/')"
        :max-size="maxsize"
        :multiple="multiple"
        :on-success="handleSuccess"
        :before-upload="handleBeforeUpload"
        :on-format-error="handleFormatError"
        :on-exceeded-size="handleMaxSize"
        :action="action"
        style="display: inline-block;">
        <Button type="default" :disabled="disabled" v-if="!preview"><Icon type="ios-cloud-upload-outline" size="16" class="pr5"></Icon> 上传文件</Button>
      </Upload>
      <Button type="default" :disabled="disabled" @click="handlePreview" v-if="pictureList.length"><Icon type="ios-eye-outline" size="16" class="pr5"/>预览</Button>
      <Button type="default" :disabled="disabled" @click="handleDownload" v-if="pictureList.length"><Icon type="ios-cloud-download-outline" size="16" class="pr5"/> 下载</Button>
      <Button type="default" :disabled="disabled" @click="handleRemove" v-if="pictureList.length && !preview"><Icon type="ios-trash-outline" size="16" class="pr5"/> 删除</Button>
      <p class="t-grey pt5">{{hint}}</p>
    </div>
    <iframe src="about:blank" name="hiddenIframe" v-show="showPdf"></iframe>
    <form target="hiddenIframe" :action="targetAction" type="get" ref="form"></form>
    <Modal
        class-name="vui-pdf-modal"
        v-model="showPdf"
        :mask-closable="false"
        :styles="{top: '0px'}"
        fullscreen
        :title="title">
        <div class="vui-pdf">
            <vue-pdfjs v-if="showPdf" viewer="../../static" :url="url" :type="1"></vue-pdfjs>
        </div>
    <div slot="footer"></div>
    </Modal>
  </div>
</template>
<script>
import vuePdfjs from 'vue-pdfjs';
export default {
    components: {
        vuePdfjs
    },
    props:{
        // 是否禁用
        disabled: {
            type:Boolean,
            default:()=>{
                return false
            }
        },
        // 文件数量 上传张数限制,默认1张
        total:{
            type:Number,
            default:()=>{
                return 1
            }
        },
        //上传大小限制，默认2M
        pictureSize:{
            type:Number,
            default:()=>{
                return 2
            }
        },
        //上传格式限制，默认xlsx/xls
        format:{
            type:String,
            default:()=>{
                return 'xlsx/xls'
            }
        },
        //是否支持本地多张上传
        multiple:{
            type:Boolean,
            default:()=>{
                return false
            }
        },
        hint: {
          type: String,
          default: '注：文件小于2M'
        },
        cover: {
            type: Boolean,
            default: false
        },
        // 是否是 模板
        preview: {
            type: Boolean,
            default: false
        }
    },
  data(){
      return{
            targetAction: '',
            showPdf: false,
            title: '',
            url: '',
            l: [],
            p: [],
            action: `${this.$url.uploadBase64}/pdf/upload/file`,
            maxsize:2048,
            pictureList:[]
        }
  },
  created(){
    this.maxsize = parseInt(this.pictureSize) *1024
  },
  methods: {
    // 点击预览
    handlePreview () {
        if (this.pictureList[0].response.data.origin) {
            this.title = this.pictureList[0].response.data.name
            this.url = this.pictureList[0].response.data.src+''
            this.showPdf = true
        }
    },
    // 下载
    handleDownload () {
      if (this.pictureList[0].response.data.origin) {
          this.targetAction = this.pictureList[0].response.data.origin
          this.$nextTick(() => {
              this.$refs['form'].submit()
          })
        // window.open(this.pictureList[0].response.data.origin)
      }
    },
    // 回显
    handleGive(origin, name, src){
        if(origin){
            //按照一定格式给其赋值
            this.pictureList = this.$refs.upload.fileList = [
                {
                        response: {
                            data: {
                                origin: origin,
                                name: name,
                                src: src
                            }
                        },
                        status: 'finished'
                    }
            ]
        }else{
            this.pictureList = this.$refs.upload.fileList = []
        } 
    },
    handleBeforeUpload () {
        if (this.cover) {
            // 是覆盖的话先把原有文件清空
            this.pictureList = []
            this.$refs.upload.fileList = []
            return true
        } else {
            const check = this.$refs.upload.fileList.length < this.total
            if (!check) {
                this.$Notice.warning({
                    title: `最多只能上传${this.total}个文件。`
                })
            }
            return check
        }
    },
    //上传成功的回调
    handleSuccess (response, file, fileList) {
        if (response.code === 500) {
            this.$Message.error('上传失败!')
        } else {
            this.$Message.success('上传成功!')
            this.pictureList = this.$refs.upload.fileList
            this.$emit('on-getFileList',this.$refs.upload.fileList)
        }
    },
    // 删除的方法
    handleRemove () {
        this.$refs.upload.fileList = []
        this.pictureList = this.$refs.upload.fileList
        this.$emit('on-getFileList',this.pictureList)
    },
    // 文件大小限制
    handleMaxSize (file) {
        this.$Notice.warning({
            title: "文件大小超出限制",
            desc: `文件大小过大，应不超过${this.pictureSize}。`,
            duration: 6
        });
    },
    // 文件格式限制
    handleFormatError (file) {
        this.$Notice.warning({
            title: "文件格式有误",
            desc: `文件格式不正确，请选择${this.format}格式。`,
            duration: 6
        });
    }
  }
}
</script>
<style lang="scss">
.vui-pdf-modal .ivu-modal-fullscreen .ivu-modal-body {
    bottom: 20px;
}
.vui-pdf-modal .vui-pdf {
    height: 100% !important;
    .pdfobject-container{
        height: 100% !important;
    }
}
</style>

