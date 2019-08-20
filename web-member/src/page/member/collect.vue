<template>

    <div class="layout">
        <top :address="false"/>
        <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="24">
                    <app-banner
                        src="../../../../static/img/app-banner-collect.png"
                        title="收藏管理">
                    </app-banner>
                        <div class="pd20">
                            <h3><b>收藏管理</b></h3>
                            <Form :model="specForm" :label-width="90" class="mt15">
                              <Row>
                                <Col span="10">
                                  <FormItem label="名称：">
                                    <Input search v-model="specForm.name" enter-button="查询" placeholder="请输入..." @on-search="goSearch"/>
                                  </FormItem>
                                </Col>
                                <Col span="10" :offset="1">
                                  <Button type="default" @click="resetSearch">重置</Button>
                                </Col>
                                </Row>
                            </Form>
                            <Row class="collect-hd mt15">
                                  <Col span="4" class="tc"> 名称分组</Col>
                                  <Col span="1" offset="17" class="tc"> 操作</Col>
                              </Row>
                          </div>
                          <div class="pl10 pr10">
                            <collection ref="collection"></collection>
                          </div>
                          <div class="pd20">
                              <h3 class="mt20 mt10"><b>内容管理</b></h3>
                                <Row class="collect-hd mt15">
                                    <Col span="4" class="tc"> 标题</Col>
                                    <Col span="2" offset="16" class="tc"> 操作</Col>
                                </Row>
                                <template v-if="contentList.length>0">
                                    <template v-for="(item,index) in contentList" >
                                        <Row class="mt10 pl10" type="flex" align="middle">
                                            <Col span="18">
                                            <a :href="item.path">{{item.title}}</a>
                                            </Col>
                                            <Col span="6" class="tc">
                                            <Button type="default" @click=" editCla(item.id)" class="mr10">编辑分类</Button>
                                            <Button type="default" @click="delCla(item.id)">删除</Button>
                                            </Col>
                                        </Row>
                                    </template>
                                </template>
                                <template v-else>
                                        <h3 class="tc pd20">暂无数据</h3>
                                </template>
                            </div>
                            <div class="tc pb30">
                                <Page :total="total" :current="currentPage"
                                      :page-size="pageSize" @on-change="pageChange"
                                      show-total></Page>
                            </div>
                    </Col>
                </Row>
            </div>
        </div>
        <edit-collect  ref="editCollect" :itemId="itemId" :templateId="templateId"></edit-collect>
        <foot></foot>
    </div>
</template>

<script>
    import top from '../../top'
    import foot from '../../foot'
    import axios from '~src/api/api'
    import $ from 'jquery'
    import api from '~api'
    import {VueTreeList, Tree, TreeNode} from '~components/VueTreeList/index'
    import editCollect from './components/editCollect'
    import appBanner from '~components/app-banner'
    import collection from '../auth/step4/components/collection'
    export default {
        components: {
            top,
            foot,
            VueTreeList,
            editCollect,
            appBanner,
            collection
        },

        data() {
            return {
                contentList: [],
                total: 0,
                currentPage:1,
                pageSize:5,
                collectId: '',
                specForm: {
                    name:'',
                    mark:''
                },
                templateId: '',
                collectModal: false,
                itemId: 0
            }
        },
        created: function () {
           this.$api.post('/member-reversion/realStep/findEnableStep', {
                  account: this.$user.loginAccount
              }).then(response => {
              if (response.code === 200) {
                  if (response.data) {
                      this.templateId = response.data.templateId
                      if (this.templateId) {
                        this.$refs['collection'].templateId = this.templateId
                        this.$refs['collection'].getInit()
                      }
                  }
              }
          }).catch(error => {
              this.$Message.error('服务器异常！')
          })
          this.getContList()
        },

        methods: {
           //内容管理列表
          getContList() {
              this.$api.post('/member/report/findCollect', {
                  account: this.$user.loginAccount,
                  pageNum :this.currentPage,
                  pageSize: this.pageSize,
                  collectId: this.collectId,
                  title: this.specForm.name
              }).then(res => {
                  if (200 === res.code) {
                      this.contentList = res.data.list.list
                      this.total = res.data.list.total
                  }
              })
            },
            //内容管理分类编辑
            editCla(id) {
                this.$refs['editCollect'].init()
                this.itemId = id
            },
            // 内容管理分类 删除
            delCla (id) {
                let v = this
                this.total =0
                this.$Modal.confirm({
                    title: '系统提示',
                    content: '确定是否删除?',
                    onOk: () => {
                        this.$api.post('/member/report/delFollow', {
                            id :id
                        }).then(res => {
                            if (200 === res.code) {
                                this.goSearch()
                            }
                        })
                        this.$Message.success('删除成功')
                    },
                })
            },
            pageChange (e) {
              this.currentPage = e
              this.getContList()
            },
            goSearch(){
                this.currentPage = 1
                this.getContList()
            },
            resetSearch() {
                this.currentPage = 1
                this.collectId = ''
                this.specForm.name= ''
                this.specForm.mark= ''
                this.getContList()
            },
        },
    }
</script>
<style>
.collect-hd{
    line-height: 40px;
    background: #f8f8f9;
}
</style>