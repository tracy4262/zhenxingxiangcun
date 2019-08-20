<template>
<div class="pd15">
  <Row>
    <Col span="7">
        <label class="mr10">仓库名称</label>
        <Input v-model="searchInfo.storeName" style="width: 200px"/>
    </Col>
    <Col span="7">
        <label class="mr10">启用状态</label>
        <Select v-model="searchInfo.status" clearable style="width: 200px">
          <Option value="1">启用</Option>
          <Option value="0">未启用</Option>
        </Select>
    </Col>
    <Col span="10" class="tr">
      <Button type="success" @click="query" class="mr20">查询</Button>
      <Button type="success" @click="addInit">新增仓库</Button>
    </Col>
  </Row>
  <Row class="mt30">
    <Col>
      <Table border :columns="columns" :data="data"></Table>
      <div class="tc mt20 mb20">
        <Page :total="total" :current="pageNum" :page-size="pageSize" @on-change="pageChange" />
      </div>
    </Col>
  </Row>
  <!-- 新增仓库 -->
  <Modal class="addDialog" v-model="addShow" title="新增仓库" :mask-closable="false">
    <Form ref="info" :model="info" label-position="right" :label-width="100" :rules="ruleInline">
      <FormItem label="仓库名称" prop="storeName">
        <Input v-model="info.storeName" :maxlength="30" />
      </FormItem>
      <FormItem label="备注" prop="remark">
        <Input type="textarea" v-model="info.remark" :maxlength="200" />
      </FormItem>
      <FormItem label="启用状态" prop="status">
        <Switch v-model="info.status" />
      </FormItem>
    </Form>
    <div slot="footer">
      <Button type="text" @click="addShow=false">取消</Button>
      <Button type="primary" @click="addStore">确定</Button>
    </div>
  </Modal>
  <!-- 编辑 -->
  <Modal class="addDialog" v-model="editShow" title="编辑仓库" :mask-closable="false">
    <Form ref="info2" :model="info2" label-position="right" :label-width="100" :rules="ruleInline2">
      <FormItem label="仓库名称" prop="storeName">
        <Input v-model="info2.storeName" :maxlength="30" />
      </FormItem>
      <FormItem label="备注" prop="remark">
        <Input type="textarea" v-model="info2.remark" :maxlength="200" />
      </FormItem>
      <FormItem label="启用状态" prop="status">
        <Switch v-model="info2.status" />
      </FormItem>
    </Form>
    <div slot="footer">
      <Button type="text" @click="editShow=false">取消</Button>
      <Button type="primary" @click="editStore">确定</Button>
    </div>
  </Modal>
</div>
</template>

<script>
export default {
  data () {
    return {
      addShow: false,
      editShow: false,
      columns: [
        {
          title: '序号',
          key: 'order',
          width: 100
        },
        {
          title: '仓库名称',
          key: 'storeName'
        },
        {
          title: '备注',
          key: 'remark'
        },
        {
          title: '启用状态',
          key: 'status',
          render: (h, params) => {
            return h('div', params.row.status === 1 ? '启用' : '未启用')
          }
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('a', {
                style: {
                  marginRight: '10px',
                  color: '#19be6b'
                },
                on: {
                  click: () => {
                    this.editInit(params.row)
                  }
                }
              }, '编辑'),
              h('a', {
                style: {
                  marginRight: '10px',
                  color: '#ed4014'
                },
                on: {
                  click: () => {
                    this.$Modal.confirm({
                      title: '操作提示',
                      content: '确定删除该仓库？',
                      onOk: () => {
                        this.$api.post('/shop/inventory/basicSetting/storeDelete', {
                          account: this.$user.loginAccount,
                          id: params.row.id
                        }).then(response => {
                          if (response.code === 200) {
                            this.$Message.success('删除成功！')
                            this.pageNum = 1
                            this.initStore()
                          } else if (response.code === 400) {
                            this.$Message.info('该仓库已被使用，无法删除！')
                          } else {
                            this.$Message.error('服务器异常！')
                          }
                        }).catch(error => {
                          this.$Message.error('服务器异常！')
                        })
                      }
                    })
                  }
                }
              }, '删除')
            ])
          }
        }
      ],
      data: [],
      searchInfo: {
        storeName: '',
        status: ''
      },
      pageSize: 10,
      pageNum: 1,
      total: 0,
      info: {
        storeName: '',
        remark: '',
        status: false
      },
      ruleInline: {
        storeName: [
          { required: true, type: 'string', message: '请填写仓库名称', trigger: 'blur' }
        ],
        remark: [
          { required: false }
        ],
        status: [
          { required: true, type: 'boolean', message: '请选择启用状态', trigger: 'change' }
        ]
      },
      info2: {
        storeName: '',
        remark: '',
        status: false
      },
      ruleInline2: {
        storeName: [
          { required: true, type: 'string', message: '请填写仓库名称', trigger: 'blur' }
        ],
        remark: [
          { required: false }
        ],
        status: [
          { required: true, type: 'boolean', message: '请选择启用状态', trigger: 'change' }
        ]
      }
    }
  },
  created () {
    this.initStore()
  },
  methods: {
    initStore () {
      this.$api.post('/shop/inventory/basicSetting/storeFind', {
        account: this.$user.loginAccount,
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        key: this.searchInfo.storeName,
        status: this.searchInfo.status
      }).then(response => {
        console.log('res', response)
        if (response.code === 200) {
          this.data = response.data.list
          this.total = response.data.total
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    addInit () {
      // 清空已选择数据
      this.$refs['info'].resetFields()
      this.addShow = true
    },
    addStore () {
      this.$refs['info'].validate((valid) => {
        if (valid) {
          this.$api.post('/shop/inventory/basicSetting/storeSave', {
            account: this.$user.loginAccount,
            storeName: this.info.storeName,
            remark: this.info.remark,
            status: this.info.status ? 1 : 0
          }).then(response => {
            console.log('res', response)
            if (response.code === 200) {
              this.$Message.success('添加成功！')
              this.initStore()
              this.addShow = false
            } else if (response.code === 400) {
              this.$Message.info('该仓库名称已存在！')
            } else {
              this.$Message.error('服务器异常！')
            }
          }).catch(error => {
            this.$Message.error('服务器异常！')
          })
        } else {
            this.$Message.error('请核对表单字段！')
        }
      })
    },
    query () {
      this.pageNum = 1
      this.initStore()
    },
    pageChange (page) {
      this.pageNum = page
      this.initStore()
    },
    editInit (item) {
      this.$refs['info2'].resetFields()
      this.editShow = true
      this.info2.id = item.id // 修改时要回显id
      this.info2.storeName = item.storeName
      this.info2.remark = item.remark
      this.info2.status = item.status === 1 ? true : false
    },
    editStore () {
      this.$refs['info2'].validate((valid) => {
        if (valid) {
          this.$api.post('/shop/inventory/basicSetting/storeSave', {
            id: this.info2.id,
            account: this.$user.loginAccount,
            storeName: this.info2.storeName,
            remark: this.info2.remark,
            status: this.info2.status ? 1 : 0
          }).then(response => {
            console.log('res', response)
            if (response.code === 200) {
              this.$Message.success('编辑成功！')
              this.initStore()
              this.editShow = false
            } else if (response.code === 400) {
              this.$Message.info('该仓库名称已存在！')
            } else {
              this.$Message.error('服务器异常！')
            }
          }).catch(error => {
            this.$Message.error('服务器异常！')
          })
        } else {
          this.$Message.error('请核对表单字段！')
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.addDialog{
  .mark_name{
    width: 80px;
    text-align: right;
    display: inline-block;
  }
}
</style>
