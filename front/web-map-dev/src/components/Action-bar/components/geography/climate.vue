
<template>
 <!-- 气候信息 -->
  <div class="pd20 vui-climate">
    <Form label-position="left" ref="formItem" :model="data">
        <FormItem label="气候类型" prop="XZQHLX">
          <Select multiple v-model="data.XZQHLX">
            <Option v-for="item in genders" :value="item.label" :key="item.label">{{ item.label }}</Option>
          </Select>
        </FormItem>
        <FormItem label="年平均气温" prop="NPJQW">
          <div style="width: 100%;display: inline-block;">
            <Row>
              <Col span="10">
                <Input v-model="data.NPJQW[0]" :maxlength="20"></Input>
              </Col>
              <Col span="2" class="tc">
                <span>到</span>
              </Col>
              <Col span="10">
                <Input v-model="data.NPJQW[1]" :maxlength="20"></Input>
              </Col>
              <Col span="2" class="tc">
                <span>℃</span>
              </Col>
            </Row>
          </div>
        </FormItem>
        <FormItem label="年平均降水量" prop="NPJJSL">
          <div style="width: 100%;display: inline-block;">
            <Row>
              <Col span="10">
                <Input v-model="data.NPJJSL[0]" :maxlength="20"></Input>
              </Col>
              <Col span="2" class="tc">
                <span>到</span>
              </Col>
              <Col span="10">
                <Input v-model="data.NPJJSL[1]" :maxlength="20"></Input>
              </Col>
              <Col span="2" class="tc">
                <span>mm</span>
              </Col>
            </Row>
          </div>
        </FormItem>
    </Form>
    <div class="tc">
      <Button type="primary" @click="handleSave" class="mt40">保存</Button>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      genders: [
        {label: '热带季风', value: '热带季风'},
        {label: '亚热带季风', value: '亚热带季风'},
        {label: '温带季风', value: '温带季风'},
        {label: '高原山地', value: '高原山地'},
        {label: '热带大陆性', value: '热带大陆性'},
        {label: '热带雨林', value: '热带雨林'},
      ],
      data: {
        XZQHLX: [], // 气候类型
        NPJQW: [], // 年平均气温
        NPJJSL: [], // 年平均降水量
      },
      textPreview: {},
      title: '气候信息',
      status: true,
      templateId: '',
      isLoading: true
    }
  },
  created () {
  },
  methods: {
    initShow (list) {
      // 初始化页面置空
      if (list) {
        for (const key in list) {
          if (this.data.hasOwnProperty(key)) {
            this.data[key] = list[key].split(',')
          }
        }
      } else {
        // 初始化页面置空
        this.$refs.formItem.resetFields()
      }
    },
    save () {
      for (const key in this.data) {
        this.data[key] = this.data[key].join(',')
      }
      return true
      // this.$refs['formItem'].validate(v => {
      //   if (!v) {
      //     return false
      //   }
      // })
    }
  }
}
</script>

<style lang="less">
.vui-climate{
  .ivu-form-item{
    margin-bottom: 14px;
  }
  .ivu-form .ivu-form-item-label{
    line-height: 20px;
  }
  .ivu-form-item-content{
    margin-top:4px;
  }
}
</style>
