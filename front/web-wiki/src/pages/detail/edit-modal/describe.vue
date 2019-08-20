<template>
  <div>
    <h6 class="b mb20">概述：</h6>
    <!-- 表单 -->
    <Form ref="describeInfo" :model="describeInfo" label-position="left" :label-width="80" :rules="ruleInline">
      <Row>
        <Col span="12">
          <Form-item prop="fname" label="物种名称">
            <Input v-model="describeInfo.fname" @on-blur="handleGetPinyin" readonly/>
          </Form-item>
        </Col>
      </Row>
      <Form-item prop="fshapefeatureid" label="性状特征">
        <Input v-model="describeInfo.fshapefeatureid" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="500"/>
      </Form-item>
      <Row>
        <Col span="12">
          <Form-item prop="pinyin" label="汉语拼音">
            <Input v-model="describeInfo.fpinyin" readonly/>
          </Form-item>
        </Col>
        <Col span="11" offset="1">
          <Form-item prop="speciesVulgo" label="物种俗称">
            <Input v-model="describeInfo.speciesVulgo" :maxlength="20"/>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item label="保护级别">
            <Select v-model="describeInfo.fisprotectionInfo.id" clearable style="width:100%" transfer @on-change="fisprotectionChange">
              <Option v-for="item in fisprotectionInfos" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </Form-item>
        </Col>
        <Col span="11" offset="1">
          <Form-item prop="industryType" label="产业分类">
            <Select v-model="describeInfo.findustriaclassifiedidInfo.id" style="width:100%" transfer @on-change="findustriaclassifiedidChange">
              <Option v-for="item in findustriaclassifiedidInfos" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item prop="fclassifiedid" label="物种分类">
             <Cascader :data="fclassifiedidInfos" @on-change="change" transfer v-model="describeInfo.fclassifiedidInfo.value"></Cascader>
          </Form-item>
        </Col>
        <Col span="11" offset="1">
          <Form-item prop="otherClassify" label="其他分类">
            <Cascader :data="fclassifiedidInfos" @on-change="changeOther" transfer v-model="describeInfo.otherClassifyInfo.value"></Cascader>
          </Form-item>
        </Col>
      </Row>
      <Row>
        <Col span="12">
          <Form-item prop="majorProduct" label="主要产品">
            <Input v-model="describeInfo.majorProduct" :maxlength="100"/>
          </Form-item>
        </Col>
      </Row>
       <Row>
        <Col span="24">
          <Form-item prop="majorProduct" label="图册">
             <vui-upload
              ref="upload"
              :total="500"
              :size="[120, 90]"
              :hint="'图片大小小于2M'"
              @on-getPictureList="handleGetPhoto"
              >
              </vui-upload>
          </Form-item>
        </Col>
      </Row>
    </Form>
    <div class="tc mt30">
      <Button type="primary" @click="handleSave" class="mr10">保存</Button>
      <Button type="ghost" @click="handleCancel">取消</Button>
    </div>
  </div>
</template>
<script>
import {catalogData} from '~components/mixins'
import divider from '~components/divider'
import vuiUpload from '~components/vui-upload'
export default {
  mixins: [catalogData],
  components: {
    divider,
    vuiUpload
  },
  data: () => ({
    ruleInline: {
      fname: [{required: true, message: '请填写物种名称', trigger: 'blur'}],
      fclassifiedid: [{required: true, min: 2, message: '请选择物种类型', trigger: 'change'}]
    },
    speciesid: '',
    fisprotectionInfos: [
      {value: '0', label: '否'},
      {value: '1', label: '一级保护'},
      {value: '2', label: '二级保护'},
      {value: '3', label: '地方重点保护'}
    ],
    findustriaclassifiedidInfos: [{
      label: '农业',
      value: 'A01'
    }, {
      label: '林业',
      value: 'A02'
    }, {
      label: '畜牧业',
      value: 'A03'
    }, {
      label: '水产业',
      value: 'A04'
    }],
    fclassifiedidInfos: [],
    describeInfo: {
      fisprotectionInfo: {},
      findustriaclassifiedidInfo: {},
      fclassifiedidInfo: {},
      otherClassifyInfo: {},
      speciesAtlas: []
    },
    loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
    account: ''
  }),
  created () {
    this.account = this.loginUser.loginAccount
    this.speciesid = this.$route.query.speciesid
    this.handleLoadFauna(1)
    this.handleLoadFauna(0)
  },
  methods: {
    // 获取数据
    handleGetData (val) {
      this.describeInfo = val
      this.$refs['upload'].handleGive(this.describeInfo.speciesAtlas)
      this.parent.setHeight()
    },
    handleGetPhoto (e) {
      // 获取图册
      var arr = []
      e.forEach(element => {
        if (element.response) {
          arr.push(element.response.data.picName)
        }
      })
      this.describeInfo.speciesAtlas = arr
      this.parent.setHeight()
    },
    // 获取拼音
    handleGetPinyin () {
      this.$api.get('wiki/api/species/getSpeciesPinYin/' + this.describeInfo.fname).then(response => {
        this.describeInfo.fpinyin = ''
        this.describeInfo.fpinyin = response.data
      })
    },
    // 保护级别
    fisprotectionChange () {
      this.describeInfo.fisprotection = this.describeInfo.fisprotectionInfo.id + ''
    },
    // 产业分类
    findustriaclassifiedidChange () {
      this.describeInfo.findustriaclassifiedid = this.describeInfo.findustriaclassifiedidInfo.id
    },
    // 物种分类
    change (value, selectedData) {
      let id = value[1]
      this.describeInfo.fclassifiedid = id
    },
    changeOther (value, selectedData) {
      let id = value[1]
      this.describeInfo.otherClassify = id
    },
    // 取动植物数据
    handleLoadFauna (id) {
      this.$api.post('wiki/speciesclass/listSpeciesclass', {
        parentId: id
      }).then(res => {
        let d = res.data
        let arr = []
        d.forEach(item => {
          arr.push({
            label: item.className,
            value: item.classId
          })
        })
        var list = {}
        if (id === 0) { // 动物
          list = {
            value: '动物',
            label: '动物',
            children: arr
          }
        } else if (id === 1) { // 植物
          list = {
            value: '植物',
            label: '植物',
            children: arr
          }
        }
        this.fclassifiedidInfos.push(list)
      })
    },
    // 保存
    handleSave () {
      this.describeInfo.fcreatorid = this.account
      this.$api.post('wiki/api/species/updateSpecies', this.describeInfo).then(response => {
        if (response.code === 200) {
          this.$Message.success({
            content: '保存成功！请等待审核，审核通过后数据将会更新。',
            duration: 3
          })
          this.parent.show = false
          this.parent.active = -1
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

<style lang="scss">
  #app{
    .ivu-form-item-label:before{
      margin: 0 2px 0 -10px;
    }
  }
  .v-transfer-dom{
    .ivu-form-item-label:before{
      margin: 0 2px 0 -10px;
    }
  }
</style>
