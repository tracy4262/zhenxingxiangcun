<template>
  <Form ref="formValidate" :model="data" :rules="ruleValidate" :label-width="100">
    <Row>
      <Col span="12">
        <FormItem label="自定义属性名" prop="label">
          <Input v-model="data.label" :maxlength="12" placeholder="最多支持12个汉字"></Input>
        </FormItem>
        <!-- <FormItem label="是否禁用">
          <Switch v-model="data.disabled" size="large">
            <span slot="open">禁用</span>
            <span slot="close">可用</span>
          </Switch>
        </FormItem> -->
      </Col>

      <Col span="12">
        <FormItem label="选项参数" prop="list">
          <Row :key="index" v-for="(item, index) in data.list">
            <Col span="10">
              <Input
              size="small"
              v-model="item.value"
              :maxlength="10"
              :class="{active: data.value[0] === item.value}"
              ></Input>
            </Col>
            <Col span="2" class="tr">
              <Tooltip content="删除" placement="left">
                <Button
                type="default"
                size="small"
                shape="circle"
                icon="md-trash"
                @click="handleDel(index)"
                ></Button>
              </Tooltip>
            </Col>
          </Row>
          <Button type="primary" shape="circle" size="small" icon="md-add" @click="handleAddOption"></Button>
        </FormItem>
      </Col>

    </Row>
    <div class="tc">
      <Button type="primary" class="mr20" @click="handleSave('formValidate')">保存</Button>
      <Button type="default" @click="handleCancel('formValidate')">取消</Button>
    </div>
  </Form>
</template>
<script>
import {eleStoreValidate, validOption} from './mixins'
export default {
  props: {
    data: {
      type: Object
    }
  },
  mixins: [eleStoreValidate],
  data () {
    return {
      ruleValidate: {
        label: [
          { required: true, message: '组件名称必填', trigger: 'blur' }
        ],
        list: [
          { validator: validOption, required: true, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 添加选项
    handleAddOption () {
      this.data.list.push({value: `默认选项${this.data.list.length}`})
      this.data.value = []
      this.data.value.push(this.data.list[0].value)
    },
    handleDel (index) {
      this.data.list.splice(index, 1)
    }
  }
}
</script>
<style lang="scss">
.active{
  .ivu-input{
    background: #d9ebff
  }
}
</style>
