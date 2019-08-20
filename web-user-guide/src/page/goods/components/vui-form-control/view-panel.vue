<template>
  <div class="sales pl10 pr10">
    <Form label-position="left" :label-width="edit ? 120 : 150">&nbsp;
      <FormItem :label="title">
        <Button v-if="edit" type="primary" @click="handleAdd">添加</Button>
      </FormItem>
      <template v-for="(item, index) in data" >
         
        <FormItem
          :label="item.label"
          :key="index"
          v-if="item.type !== 'pesticidePick' && item.type !== 'pollutePick'">
          <Row>
            <Col span="22">
              <!-- 输入控件 -->
              <Input
                v-if="item.type === 'text'"
                :type="item.type"
                v-model="item.value"
                :disabled="item.disabled"
                :maxlength="item.maxlength"
                style="width: 50%"
              ></Input>
              <Input
                v-if="item.type === 'textarea'"
                :type="item.type"
                v-model="item.value"
                :rows="5"
                :disabled="item.disabled"
                :maxlength="item.maxlength"
              ></Input>

              <!-- 下拉控件 -->
              <Select
                v-if="item.type === 'select'"
                v-model="item.value"
                :disabled="item.disabled">
                <Option v-for="child in item.list" :value="child.value" :key="child.value">{{ child.value }}</Option>
              </Select>

              <!-- 单选框 -->
              <RadioGroup
                v-if="item.type === 'radio'"
                v-model="item.value.value">
                <Radio :label="child.value" :disabled="item.disabled" :key="child.value" v-for="child in item.list"></Radio>
              </RadioGroup>

              <!-- 复选框 -->
              <CheckboxGroup
                v-if="item.type === 'checkbox'"
                v-model="item.value">
                <Checkbox :label="child.value" :disabled="item.disabled" :key="child.value" v-for="child in item.list"></Checkbox>
              </CheckboxGroup>

              <!-- 开关 -->
              <Switch v-model="item.value" size="large" v-if="item.type === 'switch'">
                <span slot="open">{{item.open}}</span>
                <span slot="close">{{item.close}}</span>
              </Switch>
            </Col>
            <Col span="2" class="tr" v-if="edit">
              <Button
              class="ml10"
              type="default"
              shape="circle"
              size="small"
              @click.stop="handleDel(index)"><Icon type="md-trash"></Icon></Button>
            </Col>
          </Row>
        </FormItem>
        <Row type="flex" align="middle" v-else>
          <Col span="22">
            <!-- 农药指标 -->
              <pesticide-pick
              v-if="item.type === 'pesticidePick'"
              :data="item.list">
              </pesticide-pick>

              <!-- 污染物指标 -->
              <pollute-pick
              v-if="item.type === 'pollutePick'"
              :data="item.list">
              </pollute-pick>
          </Col>
          <Col span="2" class="tr" v-if="edit">
            <Button
            class="ml10"
            type="default"
            shape="circle"
            size="small"
            @click.stop="handleDel(index)"><Icon type="md-trash"></Icon></Button>
          </Col>
        </Row>
      </template>
      <!-- <div class="tc" v-if="data.length">
        <Button type="primary" class="mr20" @click="handleSave">保存</Button>
        <Button type="default">取消</Button>
      </div> -->
    </Form>
  </div>
</template>
<script>
import pesticidePick from './components/pesticide-pick'
import pollutePick from './components/pollute-pick'
export default {
  props: {
    data: Array,
    title: {
      type: String,
      default: '自定义控件'
    },
    edit: {
      type: Boolean,
      default: true
    }
  },
  components: {
    pesticidePick,
    pollutePick
  },
  data: () => ({
  }),
  methods: {
    // 新增
    handleAdd () {
      this.$emit('on-add')
    },
    // 删除
    handleDel (index) {
      this.data.splice(index, 1)
    },
    // 保存
    handleSave () {
      console.log(this.data)
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
