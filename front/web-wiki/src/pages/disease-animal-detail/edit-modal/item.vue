<template>
  <Form ref="data" :model="data" :label-width="80" :rules="ruleInline">
    <FormItem label="栏目标题" prop="catalog_name">
      <Input v-model.trim="data.catalog_name" readonly placeholder="请输入栏目标题最多8个字" :maxlength="8" style="width: 300px"></Input>
    </FormItem>
    <FormItem label="正文内容" prop="data">
      <Input v-model.trim="data.data" type="textarea" placeholder="请输入正文内容最多500字" :rows="6" :maxlength="500"></Input>
    </FormItem>
    <div class="tc mt30">
      <Button type="primary" @click="handleSave" class="mr10">保存</Button>
      <Button type="ghost" @click="handleCancel">取消</Button>
    </div>
  </Form>
</template>
<script>
export default {
  props: {
    data: {
      type: Object,
      default: {}
    },
    id: {
      type: Number,
      default: 0
    }
  },
  data: () => ({
    ruleInline: {
      data: [{required: true, message: '请输入正文内容', trigger: 'blur'}]
    },
    indexid: '',
    loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
    account: ''
  }),
  created () {
    this.account = this.loginUser.loginAccount
    this.indexid = this.$route.query.indexid
  },
  methods: {
    // 保存
    handleSave () {
      this.$refs.data.validate((valid) => {
        if (valid) {
          var list = {indexid: this.indexid, fid: this.data.fid, speciesid: this.data.speciesid, fcreatorid: this.account}
          if (this.id === 1) { // 保存病原学
            list.fcausediseasesubject = this.data.data
          } else if (this.id === 2) { // 保存流行特点
            list.fcommonfeature = this.data.data
          } else if (this.id === 3) { // 保存病理剖检
            list.fpathologycheck = this.data.data
          } else if (this.id === 4) { // 保存诊断
            list.fdiagnose = this.data.data
          } else if (this.id === 5) { // 保存防治
            list.fprevention = this.data.data
          }
          this.$api.post('wiki/api/wiki/updateSpeciesDisease', list).then(response => {
            if (response.code === 200) {
              this.$Message.success({
                content: '保存成功！请等待审核，审核通过后数据将会更新。',
                duration: 3
              })
              this.parent.show = false
              this.parent.handleReload()
            }
          })
        }
      })
    },
    // 取消
    handleCancel () {
      this.parent.show = false
    }
  },
  mounted () {
    this.parent = this.$parent.$parent.$parent.$parent
  }
}
</script>
