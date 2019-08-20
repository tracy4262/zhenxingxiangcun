<template>
  <div>
    <Form ref="proxyForm" :model="proxyModel" :rules="proxyRule" class="mt20">
      <FormItem label="代理协议模板" style="width: 200px; margin-left: auto; margin-right: auto;">
        <Button @click="download2">下载模板</Button>
      </FormItem>
      <FormItem prop="uploadProtocol" label="代理协议模板" style="width: 200px; margin-left: auto; margin-right: auto;">
        <vuiUploadFile
          ref="uploadProxyProtocol"
          :format="format"
          cover
          @on-getFileList="getList2($event)">
        </vuiUploadFile>
      </FormItem>
    </Form>
    <div class="tc pt30 pb20">
      <Button @click="last">返回上一步</Button>
      <Button type="primary" @click="handleNext">保存并下一步</Button>
    </div>
  </div>
</template>

<script>
import vuiUploadFile from '~components/vui-upload-file'
export default {
  components: {
    vuiUploadFile
  },
  props: {
    account: String
  },
  data () {
    return {
      format: ['doc', 'docx', 'png', 'jpg'],
      proxyModel: {
        protocolTemplate: '',
        uploadProtocol: ''
      },
      proxyRule: {
        uploadProtocol: [
            { required: true, message: '请上传代理协议模板', trigger: 'change' }
        ]
      },
      proxyProtocal: '',
      uploadPdf: ''
    }
  },
  created () {
    this.getProtocol()
  },
  methods: {
    // 获取代理协议模板
    getProtocol () {
        this.$api.post('/member/reversionProxy/proxyTemplate', {
          fileType: 1
        }).then(response => {
          console.log('res', response)
          if (response.code === 200) {
            this.proxyProtocal = response.data.fileUrl
            this.proxyModel.protocolTemplate = response.data.fileUrl
          }
        }).catch(error => {
          this.$Message.error('服务器异常！')
        })
    },
    // 下载代理协议模板
    download2 () {
        if (this.proxyProtocal !== '') {
            window.location.href = this.proxyProtocal
            this.$Message.success('下载成功！')
        } else {
            this.$Message.error('下载失败！')
        }
    },
    getList2 ($event) {
      if ($event[0]) {
        console.log('res', $event[0].response)
        this.proxyModel.uploadProtocol = $event[0].response.data.origin
        this.uploadPdf = $event[0].response.data.src
        this.$refs['proxyForm'].validate()
      } else {
        this.proxyModel.uploadProtocol = ''
        this.uploadPdf = ''
      }
    },
    handleNext () {
      this.$refs['proxyForm'].validate((valid) => {
        if (valid) {
          this.$api.post('/member/reversionProxy/proxyOrCancle', {
            account: this.account, //需要被代理的账号
            proxyAccount: this.$user.loginAccount,  //代理人账号
            protocolTemplate: this.proxyModel.protocolTemplate,  //协议模板url
            uploadProtocol: this.proxyModel.uploadProtocol,  //上传代理协议url
            uploadPdf: this.uploadPdf,
            type: 1  //0:取消代理模板，1:代理模板
          }).then(response => {
            console.log('res', response)
            if (response.code === 200) {
              this.$Message.success('保存成功！')
              this.$emit('next')
              // this.proxyModal = false
              // this.$emit('refresh')
              // this.$Message.success('代理协议模板上传成功，审核工作将在三个工作日内完成！')
            }
          }).catch(error => {
            this.$Message.error('服务器异常！')
          })
        }
      })
    },
    last () {
      this.$emit('last')
    }
  }
}
</script>

<style>

</style>
