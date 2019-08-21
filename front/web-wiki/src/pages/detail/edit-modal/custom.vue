<template>
  <Form ref="formData" :model="formData" :label-width="80" :rules="ruleInline">
    <FormItem label="栏目标题" prop="propertytitle">
      <Input v-model.trim="formData.propertytitle" placeholder="请输入栏目标题最多8个字" :maxlength="8" style="width: 300px"></Input>
    </FormItem>
    <FormItem label="正文内容">
      <Input v-model.trim="formData.propertycontent" type="textarea" placeholder="请输入正文内容最多500字" :rows="6" :maxlength="500"></Input>
    </FormItem>
    <FormItem label="图册">
      <vui-upload
      ref="upload"
      :total="500"
      :size="[120, 90]"
      :hint="'图片大小小于2M'"
      @on-getPictureList="handleGetPhoto"
      >
      </vui-upload>
    </FormItem>
    <div class="tc mt30">
      <Button type="primary" @click="handleSave" class="mr10">保存</Button>
      <Button type="ghost" @click="handleCancel">取消</Button>
    </div>
  </Form>
</template>
<script>
import vuiUpload from '~components/vui-upload'
export default {
  components: {
    vuiUpload
  },
  data: () => ({
    formData: {
      propertytitle: '',
      propertycontent: '',
      propertyimage: []
    },
    ruleInline: {
      propertytitle: [{required: true, message: '请输入栏目标题', trigger: 'blur'}]
    }
  }),
  created () {
  },
  methods: {
    // 获取数据
    handleGetData (val) {
      this.formData = val
      this.$refs['upload'].handleGive(this.formData.propertyimage)
    },
    // 保存
    handleSave () {
      this.$refs.formData.validate((valid) => {
        if (valid) {
          this.$api.post('wiki/api/property/updateSpeciesProperty', this.formData).then(response => {
            if (response.code === 200) {
              this.$Message.success({
                content: '保存成功！请等待审核，审核通过后数据将会更新。',
                duration: 3
              })
              this.parent.show = false
              this.parent.active = -1
            }
          })
        }
      })
    },
    // 取消
    handleCancel () {
      this.parent.show = false
    },
    // 获取图片
    handleGetPhoto (e) {
      var arr = []
      e.forEach(element => {
        if (element.response) {
          arr.push(element.response.data.picName)
        }
      })
      this.formData.propertyimage = arr
    }
  },
  mounted () {
    this.parent = this.$parent.$parent.$parent.$parent
  }
}
</script>