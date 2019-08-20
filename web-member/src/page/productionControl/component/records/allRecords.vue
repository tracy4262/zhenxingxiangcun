<template>
<div class="pt20 pb20 pl50 pr50">
  <div>
    <label for="" class="mr10">生产序号</label>
    <Select v-model="serialNumber" style="width:200px" @on-change="getSearch">
      <Option v-for="(item, index) in serialNumberList" :value="item.serialNumber" :key="index">{{ item.serialNumber }}</Option>
    </Select>
  </div>
  <Row v-for="(item, index) in data" :key="index" class="pt30" type="flex" align="middle" v-if="item.name != '自定义'">
    <Col span="18"><b>{{item.name}}记录</b></Col>
    <Col span="6" class="tr"><Button type="text" @click="save(item)">保存</Button></Col>
    <Col span="24" class="pt10">
      <Input v-model="item.textPreview" type="textarea" :autosize="{minRows: 4,maxRows: 6}"/>
    </Col>
  </Row>
</div>
</template>

<script>
export default {
  data () {
    return {
      serialNumber: '',
      serialNumberList: [],
      data: [],
      yearId: '',
      wikiId: '',
      id: '',
      flag: true
    }
  },
  created() {
    if (this.$route.query.yearId) {
      this.yearId = this.$route.query.yearId
      this.$parent.$parent.yearId = this.$route.query.yearId
    }
    if (this.$route.query.year) {
      this.$parent.$parent.year = this.$route.query.year
    }
    if (this.$route.query.id) {
      this.wikiId = this.$route.query.id
      this.$parent.$parent.id = this.$route.query.id
      this.getList()
    }
    if (this.$route.query.name) {
      this.$parent.$parent.name = this.$route.query.name
    }
  },
  methods: {
    // 取生产序号下拉
    getList () {
      let list = {}
      list.wikiId = this.wikiId
      list.yearId = this.yearId
      list.account = this.$user.loginAccount
      this.$api.post('/shop/plant/findPlantProductionNumber', list).then(response => {
        if (response.code === 200) {
          this.serialNumberList = response.data
        }
      })
    },
    getInit () {
      let data = {
        wikiId: this.wikiId,
        yearId: this.yearId,
        account: this.$user.loginAccount,
        serialNumber: this.serialNumber,
      }
      this.$api.post('/shop/plant/findAllTextPreviewList', data).then(response => {
        if (response.code === 200) {
          let data = []
          response.data.forEach(element => {
            let obj = {
              textPreview: '',
              name: element.name,
              account: this.$user.loginAccount,
              wikiId: this.wikiId,
              yearId: this.yearId,
              serialNumber: this.serialNumber,
              plantParentId: element.plantParentId,
              id: element.id ? element.id : 0
            }
            if (element.textPreview.length) {
              element.textPreview.forEach(e => {
                obj.textPreview += e.textPreview
              })
            }
            data.push(obj)
          });
          this.data = data
          this.flag = true
        }
      })
    },
    // 保存
    save (item) {
      if (this.flag) {
        this.flag = false
        this.$api.post('/shop/plant/saveOrUpdateAllTextPreview', item).then(response =>{
          if (response.code === 200) {
            this.$Message.success('保存成功！')
            this.getInit()
          } else {
            this.$Message.error('保存失败！')
          }
        })
      }
    },
    // 根据生产序号查询 对应的文字预览
    getSearch () {
      this.getInit()
    }
  }
}
</script>