<template>
  <div  class="pd20">
     <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh" ></Title>
    <Form :label-width="82" label-position="left" style="background:#f9f9f9;" class="pd20 mt40" :model="item"  :rules="ruleInline" v-for="(item, index) in data" :key="item">
          <Row :gutter="16">
              <Col span="8">
                  <Form-item label="权限">
                      <i-switch size="large"  v-model="item.status" :disabled="!item.edit">
                          <span slot="open">公开</span>
                          <span slot="close">隐藏</span>
                      </i-switch>
                  </Form-item>
              </Col>
              <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.edit">编辑</span>
                <span class="auth-btn-toolbar" @click="handleDel(item, index)" v-if="data.length != 1 && item.edit">删除</span>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item  prop="species" label="物种">
                      <Input v-model="item.species"  :disabled="!item.edit" icon="ios-close" class="pinput-clear" readonly @on-focus="handleFilterModal('speciFilter', index)" @on-click="inputClear(index)"/>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="scale" label="生产规模">
                      <Input v-model="item.scale" :maxlength="20" :disabled="!item.edit"></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="commodityName" label="通用商品名">

                  <!-- 从通用商品名中选择 -->
                  <vui-product-name :disabled="!item.edit" :values="item.commodityName" @handle-get-result="handleResult($event, item, index)"></vui-product-name>
                      <!-- <Row>
                          <Col span="17">
                              <Select
                                :disabled="!item.edit"
                                  placeholder="支持下拉模糊输入搜索"
                                  v-model="item.commodityName"
                                  filterable
                                  remote
                                  :remote-method="remoteMethod"
                                  :loading="loading" class="mr60" style="width:100%;">
                                  <Option v-for="(option, index) in commonProductNameList" :value="option.label" :key="index">{{option.label}}</Option>
                                  </Select>
                          </Col>
                          <Col span="7">
                              <Button type="primary" v-if="isShowAddBtn" @click="add">添加</Button>
                              <Button type="primary" v-if="editBtn" @click="modify">补充</Button>
                          </Col>
                      </Row> -->
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="productName" label="产品名称">
                      <Input v-model="item.productName" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="units" label="产量单位">
                       <vuiUnit :value="item.units" @on-get-data="onGetUnit($event, index)" :disabled="!item.edit"></vuiUnit>
                      <!-- <Select v-model="item.units"  filterable style="width: 100%" @on-change="changePreview" class="units"  placeholder="单位" :disabled="!item.edit">
                          <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                      </Select>        -->
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="product" label="产品产量">
                      <Input v-model="item.product" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="conversion" label="折算产量">
                      <Input v-model="item.conversion" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
          </Row>
          <div class="tc pd20" v-if="item.edit">
            <Button type="primary" @click="handleSave(item, index)">保存</Button>
          </div>
    </Form>
    <Button type="primary" ghost @click="handleAdd" class="mt20 btn-light-primary" icon="md-add">增加</Button>
    <Title title="文字预览" class="mt50"></Title>
    <div class="pd20 pt30">
      <Input type="textarea" v-model="preview" :autosize="{minRows: 3,maxRows: 5}"></Input>
    </div>
    <div class="tc pd20">
    <Button type="primary" v-if="isLoading">保存</Button>
    <Button type="primary" @click="onSave" v-else>保存</Button>
    </div>
      <!-- 相关物种 -->
		<vui-filter
            ref="speciFilter"
            :cols="2"
            :num="1"
            :pageShow="true"
            :total="total"
            :pageCur="pageCur"
            :classifyDatas="speciClassifyDatas"
            :resultDatas="speciResultDatas"
            :load-data="loadSpeciDatas"
            @on-search="handleSpeciSearch"
            @on-get-classify="handleGetSpeciClassify"
            @on-get-result="handleGetSpeciResult"
            @on-page-change="handleSpeciPageChange" />
  </div>
</template>
<script>
import vuiProductName from '~components/vui-productName'
import vuiFilter from '~components/vuiFilter/filter'
import Title from '../../components/title'
import vuiUnit from '~components/vui-unit'
export default {
  components: {
      vuiFilter,
      Title,
      vuiProductName,
      vuiUnit
  },
  props: {
    yearId: {
      type: String
    },
    id: {
      type: String
    },
    appId: {
      type: String
    }
  },
  data () {
    return {
      total:0,
      pageCur:1,
      speciClassifyDatas: [{
          label: '动物',
          value: '0',
          classId: '',
          loading: false,
          checked: false,
          children: []
        },
        {
          label: '植物',
          value: '1',
          classId: '',
          loading: false,
          checked: false,
          children: []
        }],
      speciResultDatas: [],
      data: [{
            edit: true,
            status: true,
            species: '',
            scale: '',
            commodityName: '',
            productName: '',
            product: '',
            conversion: '',
            units: ''
          }],
      ruleInline:{},
      index:0,
      submit:true,
      commonProductNameList: [],
      loading: false,
      isShowAddBtn: false,
      editBtn: false,
      units: [],
      preview: '',
      title: '产出产品',
      templateId: '',
      isLoading: true
    }
  },
  created() {
      this.templateId = this.$route.query.templateId
      // 取相关物种结果
      this.loadSpeciResult('', '', [], this.pageCur, [])
  },
  methods: {
    initTitle () {
        this.$api.post('/member-reversion/user/perfect/findTableHead', {
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
                if (response.data.propertyName) {
                    this.title = response.data.propertyName
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    //  初始化数据
    handleInit (type) {
        this.$api.post('/member-reversion/outputProduct/find',{
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            templateId: this.templateId
        }).then(response => {
            if (response.code == 200) {
                this.isLoading = false
                if (type) { // 说明是更新数据
                    // this.changePreview()
                } else {
                    this.preview = response.data.preview
                    // this.title = response.data.propertyName
                }
                if (response.data.list.length) {
                    this.data = response.data.list
                    this.data.forEach(e => {
                        e.edit = false
                        if (e.status === '1') {
                            e.status = true
                        } else if (e.status === '0'){
                            e.status = false
                        }
                    })
                }

            }
        })
    },
    // 取下拉数据
    onGetUnit($event, index){
      this.data[index].units = $event
    },
    handleResult ($event, item, index) {
        this.data[index].commodityName = $event
        this.changePreview()
    },
    // 文字预览
    changePreview () {
        let str = ''
        if (this.data.length) {
            str =  `所在地有`
        }
        this.data.forEach(e => {
            if (e.productName) {
                str += `${e.productName}、`
            }
        })
        if (str) {
            str = `${str.substring(0,str.length-1)}。`
        }
        this.preview = str
    },
    // 编辑
    handleEdit (item, index) {
      item.edit = true
      this.data.splice(index,1, item)
    },
    // 单条 保存
    handleSave (item, index) {
        item.account = this.$user.loginAccount
        item.yearId = this.yearId
        item.dictId = this.id
        item.templateId = this.templateId
        this.$api.post('/member-reversion/outputProduct/save', item).then(response => {
            if (response.code === 200) {
                this.$Message.success('保存成功')
                item.edit = false
                this.handleInit(1)
            }
        })
    },
    // 保存
    onSave () {
        let list = {
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            textPreview: this.preview,
            isComplete: true,
            templateId: this.templateId
        }
        this.isLoading = true
        this.$api.post('/member-reversion/perfect/saveTextPreview', list).then(response => {
            if (response.code === 200) {
                this.$Message.success('保存成功')
                this.handleInit()
                this.$emit('on-save')
            }
        })
    },
    // 添加
    handleAdd () {
        this.data.push(
          {
            edit: true,
            status: true,
            species: '',
            scale: '',
            commodityName: '',
            productName: '',
            product: '',
            conversion: '',
            units: ''
          }
        )
    },
    handleDel (item, index) {
        this.$Modal.confirm({
            title: '是否确定删除',
            content: '是否确认删除？',
            onOk:()=>{
                if (item.id) {
                    this.$api.post('/member-reversion/outputProduct/delete', {id: item.id}).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('删除成功！')
                            this.data.splice(index,1)
                            this.changePreview()
                        }
                    })
                } else {
                    this.$Message.success('删除成功！')
                    this.data.splice(index,1)
                    this.changePreview()
                }
            },
            okText:'确定',
            cancelText:'取消'
        });
    },
    //清空物种文本框
    inputClear (index) {
        this.data[index].species = ''
        this.$refs['speciFilter'].handleReset()
    },
    // 高级搜索弹窗
    handleFilterModal (name,index) {
        this.$refs[name].highFilterShow = true
        this.index = index
        this.$refs['speciFilter'].handleReset()
    },
    //物种
    loadSpeciDatas(item, callback) {
      item.loading = true
      this.$api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
        item.loading = false
        var d = res.data
        d.forEach(child => {
          child.checked = false
          child.label = child.className
        })
        item.children = d
        callback()
      })
    },
    handleSpeciSearch(letter, keyword, classify, result) {
      this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
      this.$Message.success('筛选完成！')
    },
    handleGetSpeciClassify(letter, keyword, classify, result) {
      this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
      this.$Message.success('筛选完成！')
    },
    handleGetSpeciResult(classify, result) {

      if (result.length) {
          var arr = [],
          list = []
          result.forEach(item => arr.push(item.label))
          result.forEach(item => list.push(item.value))
          this.data[this.index].species = arr.join(' ')
      } else {

      }
    },
    handleSpeciPageChange(letter, keyword, classify, num, result) {
      this.pageCur = num
      this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
      this.$Message.success('筛选完成！')
    },
    loadSpeciResult(letter, keyword, classify, num, result) {
      if (classify.length) {
        var arr = []
        var type = ''
        classify.forEach(item => {
          arr.push(item.classId)
          if (item.value !== undefined) type = item.value
        })
      } else {
        arr = null
      }
      this.$api.post('/member/specicesClass/findSpecies', {
          keywords: keyword,
          fpinyin: letter === '全部' ? '' : letter,
          fclassifiedid: arr,
          type: type,
          pageNum: num,
          pageSize: 32
        }).then(res => {
          var data = res.data
          this.total = data.total
          if (result) {
            result.forEach(item => {
              data.list.forEach((child, index) => {
                if(child.label === item.label) {
                  child.checked = true
                }
              })
            })
          } else {
            data.list.forEach(child => {
              child.checked = false
            })
          }
          this.speciResultDatas = data.list
        })
    },
    leftRefresh () {
        this.$emit('left-refresh')
    }
  }
}
</script>
