<template>
  <div class="pd20">
    <div>
      <Row>
        <Col span="7">
          <Input v-model="search.name" placeholder="请输入模板名称" style="width: 300px" />
        </Col>
        <Col span="7">
            <Select v-model="search.type" style="width: 300px" clearable>
              <Option v-for="(item,index) in productTypes" :key="index" :value="item.productCode" >{{ item.className }}</Option>
            </Select>
        </Col>
        <Col span="7">
          <Button icon="md-search" type="primary" @click="onSearch">查询</Button>
        </Col>
      </Row>
    </div>
    <ul class="template-list">
      <li>
        <div class="tc pt10" style="cursor: pointer;" @click="handleAddUniversal">
          <Icon type="ios-add" size="80" color="#00C587" />
          <p class="t-green">新建通用模板</p>
        </div>
      </li>
      <li>
        <div class="tc pt10" style="cursor: pointer;" @click="handleAddPersonalized">
          <Icon type="ios-add" size="80" color="#00C587" />
          <p class="t-green">新建个性化模板</p>
        </div>
      </li>
    </ul>
    <Title title="通用模板" class="mt50"></Title>
    <ul class="template-list">
      <li class="list" v-for="(item, index) in data1" :key="index" :class="[item.id === activeId ? 'checked': '']" @click="handleChecked(item, index, '1')">
        <!-- <span class="status" :data-status="Number(item.step) >= 5 ? '已完' : '未完'" :class="{finish: Number(item.step) >= 5}"></span> -->
          <div>
            <Tooltip placement="top" :transfer="true">
              <p style="color:#4b4b4b; font-size: 16px; font-weight: 700; max-width: 180px;" class="ell">{{item.templateName}}</p>
              <div slot="content" style="white-space: normal;">
                {{item.templateName}}
              </div>
            </Tooltip>
            <!-- <p class="t-green ell pt5 pb5">{{item.userType}}</p> -->
            <p  class="t-grey" >{{item.introduction}}</p>
          </div>
          <div class="del">
            <Button type="primary" size="small"  @click="handleEdit(item, index, 1)">编辑</Button>
            <!-- <Button type="error" size="small"  @click="handleDel(item, index)">删除</Button> -->
          </div>
      </li>
    </ul>
    <Title title="个性化模板" class="mt50"></Title>
    <ul class="template-list">
      <li class="list" v-for="(item, index) in data2" :key="index" :class="[item.id === activeId ? 'checked': '']" @click="handleChecked(item, index, '2')">
        <!-- <span class="status" :data-status="Number(item.step) >= 5 ? '已完' : '未完'" :class="{finish: Number(item.step) >= 5}"></span> -->
          <div>
            <Tooltip placement="top" :transfer="true">
              <p style="color:#4b4b4b; font-size: 16px; font-weight: 700; max-width: 180px;" class="ell">{{item.templateName}}</p>
              <div slot="content" style="white-space: normal;">
                {{item.templateName}}
              </div>
            </Tooltip>
            <!-- <p class="t-green ell pt5 pb5">{{item.userType}}</p> -->
            <p  class="t-grey" >{{item.introduction}}</p>
          </div>
          <div class="del">
            <Button type="primary" size="small"  @click="handleEdit(item, index, 2)">编辑</Button>
            <Button type="error" size="small"  @click="handleDel(item, index)">删除</Button>
          </div>
      </li>
    </ul>
    <div class="tc pd30">
      <Button type="primary" @click="handleNext">确定并下一步</Button>
    </div>
    <Modal
        v-model="idAdd1"
        :mask-closable="false"
        width="680"
        title="新建通用模板">
        <div class="pd20">
          <Form label-position="left" :label-width="100" ref="universalList" :rules="ruleInline1" :model="universalList">
             <Form-item prop="productCategoryId" label="产品大类">
                <Select v-if="!universalList.id" v-model="universalList.productCategoryId"  style="width:300px;" label-in-value @on-change="productCategoryChange">
                  <Option v-for="(item,index) in productTypes" :key="index" :value="item.productCode" :disabled="item.disabled">{{ item.className }}</Option>
                </Select>
                <Input v-else readonly v-model="universalList.productCategory" style="width:300px;" icon="ios-arrow-down"/>
              </Form-item>
              <Form-item prop="templateName" label="模板名称">
                <Input readonly v-model="universalList.templateName" :maxlength="20" style="width:300px;" />
              </Form-item>
              <Form-item prop="introduction" label="模板简介">
                <Input v-model="universalList.introduction" type="textarea"  :autosize="{minRows: 3,maxRows: 5}" :maxlength="50" />
              </Form-item>
          </Form>
        </div>
        <div slot="footer" class="tr">
            <Button  @click="handleAddUniversal">取消</Button>
             <Button type="primary" @click="handleSave(1)">确定</Button>
        </div>
    </Modal>
    <Modal
        v-model="idAdd2"
        :mask-closable="false"
        width="680"
        title="新建个性化模板">
        <div class="pd20">
          <Form label-position="left" :label-width="100" ref="personalizedList" :rules="ruleInline2" :model="personalizedList">
             <Form-item prop="commodityName" label="通用商品名称">
                <vuiCommodity v-if="!personalizedList.id" type="1" :values="personalizedList.commodityName" ref="vuiCommodity" @on-save="onSaveCommodity" :num="1"></vuiCommodity>
                <Input v-else readonly v-model="personalizedList.commodityName" style="width:300px;"/>
              </Form-item>
              <Form-item prop="productCategory" label="产品大类">
                <Input readonly v-model="personalizedList.productCategory" style="width:300px;" icon="ios-arrow-down"/>
              </Form-item>
              <Form-item prop="productType" label="产品分类">
                <Input readonly v-model="personalizedList.productType" :maxlength="20" style="width:300px;" />
              </Form-item>
              <Form-item prop="industryType" label="行业分类">
                <Input readonly v-model="personalizedList.industryType" :maxlength="20" style="width:300px;" />
              </Form-item>
              <Form-item prop="templateName" label="模板名称">
                <Input readonly v-model="personalizedList.templateName" :maxlength="20" style="width:300px;" />
              </Form-item>
              <Form-item prop="introduction" label="模板简介">
                <Input v-model="personalizedList.introduction" type="textarea"  :autosize="{minRows: 3,maxRows: 5}" :maxlength="50" />
              </Form-item>
          </Form>
        </div>
        <div slot="footer" class="tr">
            <Button  @click="handleAddPersonalized">取消</Button>
            <Button type="primary" @click="handleSave(2)">确定</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
import Title from '../components/title'
import vuiCommodity from '~components/vui-commodity'
  export default {
    components: {
      Title,
      vuiCommodity
    },
    data () {
      return {
        universalList:{
          introduction: '', // 模板简介
          templateName: '', // 模板名称
          productCategory: '', // 产品大类
          productCategoryId: '', // 产品大类 id
          isUse: '0',  //0 未使用,1已使用
          templateType: '0', // 0通用模版，1个性化模版
        },
        personalizedList: {
          introduction: '', // 模板简介
          templateName: '', // 模板名称
          commodityName: '', // 通用商品名称
          commodityNameId: '', // 通用商品名称 id
          productCategory: '', // 产品大类
          productCategoryId: '', // 产品大类 id
          industryType: '', // 行业分类
          industryTypeId: '', // 行业分类 id
          productType: '', // 产品分类
          productTypeId: '', // 产品分类 id
          isUse: '0',  //0 未使用,1已使用
          templateType: '1', // 0通用模版，1个性化模版
        },
        productTypes: [],
        ruleInline1: {
          introduction: [
            { required: true, message: '请输入模板介绍', trigger: 'blur' }
          ],
          templateName: [
              { required: true, message: '请输入模板名称', trigger: 'blur' }
          ],
          productCategoryId: [
            { required: true, message: '请选择产品大类', trigger: 'change' }
          ],
        },
        ruleInline2: {
          industryType: [
            { required: true, message: '请输入行业分类', trigger: 'change' }
          ],
          productCategory: [
            { required: true, message: '请输入产品大类', trigger: 'change' }
          ],
          productType: [
            { required: true, message: '请输入产品分类', trigger: 'change' }
          ],
          introduction: [
            { required: true, message: '请输入模板介绍', trigger: 'blur' }
          ],
          commodityName: [
            { required: true, message: '请选择通用商品名称', trigger: 'change' }
          ],
          templateName: [
              { required: true, message: '请输入模板名称', trigger: 'blur' }
          ]
        },
        data1: [],
        data2: [],
        activeId: '',
        activeData: {},
        idAdd1: false,
        idAdd2: false,
        saveSetpFlag: true,
        search: {
          name: '',
          type: ''
        }
      }
    },
    created() {
      this.initType()
      this.getData()
    },
    methods: {
      // 查询
      onSearch () {
        this.getData()
      },
      // 产品大类发生改变
      productCategoryChange (arr) {
        this.universalList.productCategory = arr.label
        this.universalList.templateName = `${arr.label}通用模板`
      },
      // 初始化产品大类下拉数据
      initType () {
        this.$api.post('/shop/mallClassification/findMallClassification',{}).then(res => {
          if (res.code === 200) {
            this.productTypes = res.data
              this.compared()
          }
        })
      },
      // 获取列表数据
      getData () {
        this.$api.post('/shop/pushTemplate/findTemplateList',{
            account: this.$user.loginAccount,
            templateType: '0',
            templateName: this.search.name,
            productCategoryId: this.search.type,
          }).then(res => {
            if (res.code === 200) {
              this.data1 = res.data
              console.log(this.data1)
              this.data1.forEach(e => {
                if (e.isUse == '1') {
                  this.activeId = e.id
                  this.activeData = e
                  this.$parent.$parent.findSetp(e)
                }
              })
              this.compared()
            }
        })
        this.$api.post('/shop/pushTemplate/findTemplateList',{
          account: this.$user.loginAccount,
          templateType: '1',
          templateName: this.search.name,
          productCategoryId: this.search.type,
        }).then(res => {
          if (res.code === 200) {
            this.data2 = res.data
            this.data2.forEach(e => {
              if (e.isUse == '1') {
                this.activeId = e.id
                this.activeData = e
                this.$parent.$parent.findSetp(e)
              }
            })
          }
        })
      },
      compared () {
        this.$api.post('/shop/pushTemplate/findTemplateList',{
            account: this.$user.loginAccount,
            templateType: '0',
            templateName: '',
            productCategoryId: '',
          }).then(res => {
            if (res.code === 200) {
              let data1 = res.data
              this.productTypes.forEach(e => {
                data1.forEach(element => {
                  if (e.productCode == element.productCategoryId) {
                    e.disabled = true
                  }
                })
              })
            }
        })
      },
      // 通用商品名称发生变化
      onSaveCommodity (list) {
        console.log(list)
        let id = ''
        if (list.length) {
          this.personalizedList.commodityName = list[0].label
          this.personalizedList.commodityNameId = list[0].value
          this.personalizedList.templateName = `${list[0].label}模板`
          id = list[0].commodityId
          this.$api.post('/portal/currencyCommodity/findCommodityById',{id: id}).then(response => {
            if (response.code === 200) {
              this.personalizedList.industryType = response.data.industryType
              this.personalizedList.industryTypeId = response.data.industryTypeId
              this.personalizedList.productType = response.data.productType
              this.personalizedList.productTypeId = response.data.productTypeId
              this.personalizedList.productCategoryId = response.data.productCategoryId
              this.personalizedList.productCategory = response.data.productCategory
            } else {
              this.$Message.error('查询失败')
            }
          })
        } else {
          this.personalizedList.commodityName = ''
          this.personalizedList.commodityNameId = ''
          this.personalizedList.templateName = ''
          this.personalizedList.industryType = ''
          this.personalizedList.industryTypeId = ''
          this.personalizedList.productType = ''
          this.personalizedList.productTypeId = ''
          this.personalizedList.productCategoryId = ''
          this.personalizedList.productCategory = ''
        }
      },
      // 添加通用模板
      handleAddUniversal () {
        this.$refs['universalList'].resetFields()
        this.idAdd1 = !this.idAdd1
        this.universalList.isAdd = true
        this.universalList = {
          introduction: '', // 模板简介
          templateName: '', // 模板名称
          productCategory: '', // 产品大类
          productCategoryId: '', // 产品大类 id
          isUse: '0',  //0 未使用,1已使用
          templateType: '0', // 0通用模版，1个性化模版
        }
      },
      // 新建个性化模板
      handleAddPersonalized () {
        this.$refs['personalizedList'].resetFields()
        this.idAdd2 = !this.idAdd2
        this.personalizedList = {
          introduction: '', // 模板简介
          templateName: '', // 模板名称
          commodityName: '', // 通用商品名称
          commodityNameId: '', // 通用商品名称 id
          productCategory: '', // 产品大类
          productCategoryId: '', // 产品大类 id
          industryType: '', // 行业分类
          industryTypeId: '', // 行业分类 id
          productType: '', // 产品分类
          productTypeId: '', // 产品分类 id
          isUse: '0',  //0 未使用,1已使用
          templateType: '1', // 0通用模版，1个性化模版
        }
      },
      handleChecked (item, index, type) {
        this.activeId = item.id
        this.activeData = item
        
        this.$parent.$parent.findSetp(item)
      },
      // 编辑
      handleEdit (item, index, type) {
        if (type === 1) {
          let obj = Object.assign({}, item)
          this.universalList = obj
          this.idAdd1 = !this.idAdd1
        } else {
          let obj = Object.assign({}, item)
          this.personalizedList = obj
          this.idAdd2 = !this.idAdd2
        }
      },
      // 点击删除模板
      handleDel (item, index) {
         this.$Modal.confirm({
                title: '是否确定删除',
                content: '是否确认删除？',
                onOk: () => {
                  this.$api.post('/shop/pushTemplate/deleteTemplate', {id: item.id}).then(response => {
                      if (response.code === 200) {
                        this.$Message.success('删除成功')
                        // 如果删除的模板是 刚选择的模板 name就先移除 sessionStorage 不能点击切换
                        if (this.activeId == item.id) {
                          this.activeId = ''
                        }
                        this.data2.splice(index, 1)
                      }
                    })
                },
                okText: '确定',
                cancelText: '取消'
            })
      },
      // 点击确定保存模板
      handleSave (type) {
        if (type === 1) { // 通用模板
          this.$refs['universalList'].validate((e) => {
            if (e) {
              this.universalList.account = this.$user.loginAccount
              this.universalList.isUse ? '' : this.universalList.isUse = '0' //0 未使用,1已使用
              this.universalList.templateType ? '' : this.universalList.templateType = '0' //0通用模版，1个性化模版
              console.log(this.universalList)
              let url = ''
              if (this.universalList.id) { // 更新
                url = '/shop/pushTemplate/updateTemplate'
              } else {
                url = '/shop/pushTemplate/savePush'
              }
              this.$api.post(url,this.universalList).then(res => {
                if (res.code === 200) {
                  this.$Message.success('保存成功')
                  this.idAdd1 = false
                  this.onSearch()
                }
              })
            } else {
              this.$Message.error('请核对表单信息')
            }
          })
        } else { // 个性化模板
        console.log(this.personalizedList)
          this.$refs['personalizedList'].validate((e) => {
            if (e) {
              this.personalizedList.account = this.$user.loginAccount
              this.personalizedList.isUse ?'' : this.personalizedList.isUse = '0' //0 未使用,1已使用
              this.personalizedList.templateType ?'' : this.personalizedList.templateType = '1' //0通用模版，1个性化模版
              let url = ''
              if (this.personalizedList.id) { // 更新
                url = '/shop/pushTemplate/updateTemplate'
              } else {
                url = '/shop/pushTemplate/savePush'
              }
              this.$api.post(url,this.personalizedList).then(res => {
                if (res.code === 200) {
                  this.$Message.success('保存成功')
                  this.idAdd2 = false
                  this.onSearch()
                }
              })
            } else {
              this.$Message.error('请核对表单信息')
            }
          })
        }
      },
      // 下一步
      handleNext () {
        if (this.activeId) {
          this.$api.post('/shop/pushTemplate/saveTemplateStep', {id: this.activeData.id, isUse: '1', account: this.$user.loginAccount}).then(response => {
              if (response.code === 200) {
                this.$router.push(`/good/step2?templateName=${this.activeData.templateName}&templateId=${this.activeData.id}&templateType=${this.activeData.templateType}&categoryId=${this.activeData.productCategoryId}`)
              }
            })
        } else {
          this.$Message.error('请选择模板')
        }
      }
    }
  }
</script>
<style lang="scss" scoped>
.template-list{
  li{
    vertical-align: top;
    height: 178px;
    position: relative;
    background: #fff;
    margin: 15px 15px 0 0;
    display: inline-block;
    width: calc(100% / 5 - 15px);
    list-style: none;
    padding: 23px 16px;
    border: 1px solid rgba(237,237,237,0.62);
    transition: box-shadow .2s cubic-bezier(.47,0,.745,.715);
    &:nth-child(5n){
      margin-right: 0;
    }
  }
  .list {
    position: relative;
    overflow: hidden;
    &:hover{
      box-shadow: 0 0 0 2px #00c587;
      .del{
        bottom: 0;
        transition: bottom .3s
      }
    }
    .del{
      position: absolute;
      text-align: center;
      padding: 5px 0;
      bottom: -100px;
      left: 0;
      right: 0;
    }
    .status{
      right: 0;
      &,
      &:after, 
      &:before{
        position: absolute;
        top: 0;
      }
      &:after{
        right: 0;
        border-style: solid;
        border-width: 0 46px 46px 0;
        border-color: transparent #fff2ef transparent transparent;
        content: '';
      }
      &:before{
        content: attr(data-status);
        transform: rotate(45deg);
        right: 5px;
        top: 7px;
        z-index: 99;
        font-size: 12px;
        color: #ed4014;
        white-space: nowrap;
      }
    }
    .finish{
      &:after{
        border-color: transparent #e2fff1 transparent transparent;
      }
      &:before{
        color: #19be6b;
      }
    }
  }
  .checked {box-shadow: 0 0 0 2px #00c587;}
}
</style>
