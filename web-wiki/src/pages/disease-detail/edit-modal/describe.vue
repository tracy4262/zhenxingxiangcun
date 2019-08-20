<template>
<div>
  <Form ref="data" :model="data" :label-width="80" :rules="ruleInline">
    <Row :gutter="32">
      <Col span="12">
      <Form-item prop="fname" label="病害名称">
        <Input v-model.trim="data.fname" :maxlength="10" @on-blur="handleGetPinyin(data.fname)" readonly/>
      </Form-item>
      </Col>
      <Col span="12">
      <Form-item prop="fpinyin" label="汉语拼音">
        <Input v-model="data.fpinyin" readonly />
      </Form-item>
      </Col>
    </Row>
    <Form-item prop="fimagesrc" label="上传图片">
      <vui-upload ref="upload" :total="1" :size="[120, 90]" :hint="'图片大小小于2M'" @on-getPictureList="handleGetPhoto"></vui-upload>
    </Form-item>
    <div class="tc mt30">
      <Button type="primary" @click="handleSave" class="mr10">保存</Button>
      <Button type="ghost" @click="handleCancel">取消</Button>
    </div>
  </Form>
</div>
</template>
<script>
import vuiUpload from '~components/vui-upload'
export default {
  components: {
    vuiUpload
  },
  props: {
    // data: {
    //   type: Object,
    //   default: {}
    // },
    id: {
      type: Number,
      default: 0
    }
  },
  data () {
    const checkName = (rule, value, callback) => {
      if (value) {
        if (value !== this.fname) {
          this.$api.post('wiki/api/wiki/listSpeciesDisease', {validName: value}).then(response => {
            if (response.code === 200) {
              if (response.total) {
                callback(new Error('该病害名称已存在'))
              } else {
                callback()
              }
            }
          })
        } else {
          callback()
        }
      } else {
        callback(new Error('请填写病害名称'))
      }
    }
    return {
      ruleInline: {
        fname: [{ required: true, validator: checkName, trigger: 'blur' }],
        fimagesrc: [{required: true, message: '请上传病害图片', trigger: 'change', type: 'array', min: 1}]
      },
      indexid: '',
      data: {
        catalog_name: '病害',
        checked: false,
        fname: '',
        fpinyin: '',
        fimagesrc: [],
        fid: '',
        speciesid: ''
      },
      fname: '',
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      account: ''
    }
  },
  created () {
    this.account = this.loginUser.loginAccount
    this.indexid = this.$route.query.indexid
  },
  methods: {
    getData (e) {
      this.data = e
      this.fname = this.data.fname
      this.$refs['upload'].handleGive(this.data.fimagesrc)
    },
    // 获取图片
    handleGetPhoto (e) {
      if (e.length) {
        this.data.fimagesrc = []
        var arr = []
        e.forEach(element => {
          if (element.response) {
            arr.push(element.response.data.picName)
          }
        })
        this.data.fimagesrc = arr
      } else {
        this.data.fimagesrc = []
      }
    },
    // 获取拼音
    handleGetPinyin (e, type) {
      if (e) {
        this.$api.get('wiki/api/species/getSpeciesPinYin/' + e).then(response => {
          this.data.fpinyin = ''
          this.data.fpinyin = response.data
          if (type) {
            var list = Object.assign({indexid: this.indexid, fcreatorid: this.account}, this.data)
            this.$api.post('wiki/api/wiki/updateSpeciesDisease', list).then(response => {
              if (response.code === 200) {
                this.$Message.success({
                  content: '保存成功！请等待审核，审核通过后数据将会更新。',
                  duration: 3
                })
                this.$emit('on-save')
              }
            })
          }
        })
      } else {
        this.data.fpinyin = ''
      }
    },
    // 保存
    handleSave () {
      this.$refs.data.validate((valid) => {
        if (valid) {
          this.handleGetPinyin(this.data.fname, 1)
        }
      })
    },
    // 取消
    handleCancel () {
      this.$emit('on-cancel')
    }
  }
}
</script>
