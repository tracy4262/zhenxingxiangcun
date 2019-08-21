<template>
  <Form ref="formValidate" :model="data" :rules="ruleValidate" :label-width="100">
    <Row>
      <Col span="12">
        <FormItem label="自定义属性名" prop="label">
          <Input v-model="data.label" :maxlength="12" placeholder="最多支持12个汉字"></Input>
        </FormItem>
      </Col>
      <Col span="12">
        <FormItem label="自定义属性值">
          <Input v-model="data.value" placeholder="最多支持12个汉字" :maxlength="12" :disabled="data.disabled" style="width: 200px"></Input>
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
    data: {
      type: Object
    }
  },
  mixins: [eleStoreValidate],
  data: () => ({
    ruleValidate: {
      label: [
        { required: true, message: '组件名称必填', trigger: 'blur' }
      ]
    }
  })
}
</script>
<style lang="scss" scoped>
</style>
