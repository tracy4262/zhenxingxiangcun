<template>
  <Form ref="formValidate" :model="data" :rules="ruleValidate" :label-width="100">
    <Row>
      <Col span="10">
        <FormItem label="自定义属性名" prop="label">
          <Input v-model="data.label" :maxlength="12" placeholder="最多支持12个汉字"></Input>
        </FormItem>
      </Col>
      <Col span="6" offset="1">
        <FormItem label="开启时显示" prop="open">
          <Input v-model="data.open" :maxlength="2" placeholder="开启时显示的文本 最多2个汉字"></Input>
        </FormItem>
      </Col>
      <Col span="6" offset="1">
        <FormItem label="关闭时显示" prop="close">
          <Input v-model="data.close" :maxlength="2" placeholder="关闭时显示的文本 最多2个汉字"></Input>
        </FormItem>
      </Col>
      <!-- <Col span="4">
        <FormItem label="是否禁用">
          <Switch v-model="data.disabled" size="large">
            <span slot="open">禁用</span>
            <span slot="close">可用</span>
          </Switch>
        </FormItem>
      </Col> -->
    </Row>
    <div class="tc">
      <Button type="primary" class="mr20" @click="handleSave('formValidate')">保存</Button>
      <Button type="default" @click="handleCancel('formValidate')">取消</Button>
    </div>
  </Form>
</template>
<script>
import {eleStoreValidate} from './mixins'
export default {
  props: {
    data: Object
  },
  mixins: [eleStoreValidate],
  data: () => ({
    ruleValidate: {
      label: [
        { required: true, message: '组件名称必填', trigger: 'blur' }
      ],
      open: [
        { required: true, message: '开启时显示文字必填', trigger: 'blur' }
      ],
      close: [
        { required: true, message: '关闭时显示文字必填', trigger: 'blur' }
      ]
    }
  })
}
</script>
<style lang="scss" scoped>
</style>
