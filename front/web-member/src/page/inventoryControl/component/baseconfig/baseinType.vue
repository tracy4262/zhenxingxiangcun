<template>
<div class="pd15">
  <Row type="flex" justify="end">
    <Col>
      <Button type="success" @click="addInit">新增入库类型</Button>
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
  <!-- 新增 -->
  <Modal  v-model="addNewShow" title="新增入库类型" :mask-closable="false">
    <Form ref="info" :model="info" label-position="right" :label-width="100" :rules="ruleInline">
      <FormItem label="入库类型" prop="type">
        <Input v-model="info.type" :maxlength="30" />
      </FormItem>
    </Form>
    <div slot="footer">
      <Button type="text" @click="addNewShow=false">取消</Button>
      <Button type="primary" @click="inTypeAdd">确定</Button>
    </div>
  </Modal>
  <!-- 编辑 -->
  <Modal v-model="editShow" title="编辑入库类型" :mask-closable="false">
    <Form ref="info2" :model="info2" label-position="right" :label-width="100" :rules="ruleInline2">
      <FormItem label="入库类型" prop="type">
        <Input v-model="info2.type" :maxlength="30" />
      </FormItem>
    </Form>
    <div slot="footer">
      <Button type="text" @click="editShow=false">取消</Button>
      <Button type="primary" @click="inTypeEdit">确定</Button>
    </div>
  </Modal>
</div>
</template>

<script>
export default {
  data () {
    return {
      addNewShow: false,
      editShow: false,
      columns: [
        {
          title: '序号',
          key: 'order',
          width: 100,
          align: 'center'
        },
        {
          title: '入库类型',
          key: 'type',
          align: 'center'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 200,
          render: (h, params) => {
            if (params.row.flag === 0) {
              return h('div', '暂无操作')
            } else if (params.row.flag === 1) {
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
                        content: '确定删除该入库类型？',
                        onOk: () => {
                          this.$api.post('/shop/inventory/basicSetting/inStoreDelete', {
                            account: this.$user.loginAccount,
                            id: params.row.id
                          }).then(response => {
                            if (response.code === 200) {
                              this.$Message.success('删除成功！')
                              this.pageNum = 1
                              this.initInType()
                            } else if (response.code === 400) {
                              this.$Message.info('该入库类型已被使用，无法删除！')
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
        }
      ],
      data: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      info: {
        type: ''
      },
      ruleInline: {
        type: [
          { required: true, type: 'string', message: '请填写入库类型', trigger: 'blur' }
        ]
      },
      info2: {
        type: ''
      },
      ruleInline2: {
        type: [
          { required: true, type: 'string', message: '请填写入库类型', trigger: 'blur' }
        ]
      },
    }
  },
  created () {
    this.initInType()
  },
  methods: {
    initInType () {
      this.$api.post('/shop/inventory/basicSetting/inStoreFind', {
        account: this.$user.loginAccount,
        pageSize: this.pageSize,
        pageNum: this.pageNum
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
      this.$refs['info'].resetFields()
      this.addNewShow = true
    },
    inTypeAdd () {
      this.$refs['info'].validate((valid) => {
        if (valid) {
          this.$api.post('/shop/inventory/basicSetting/inStoreSave', {
            account: this.$user.loginAccount,
            type: this.info.type
          }).then(response => {
            console.log('res', response)
            if (response.code === 200) {
              this.$Message.success('添加成功！')
              this.initInType()
              this.addNewShow = false
            } else if (response.code === 400) {
              this.$Message.info('入库类型已存在！')
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
    pageChange (page) {
      this.page = page
      this.initInType()
    },
    editInit (item) {
      this.$refs['info2'].resetFields()
      this.editShow = true
      this.info2.id = item.id // 修改时要回显id
      this.info2.type = item.type
    },
    inTypeEdit () {
      this.$refs['info2'].validate((valid) => {
        if (valid) {
          this.info2.account = this.$user.loginAccount
          this.$api.post('/shop/inventory/basicSetting/inStoreSave', this.info2).then(response => {
            console.log('res', response)
            if (response.code === 200) {
              this.$Message.success('编辑成功！')
              this.initInType()
              this.editShow = false
            } else if (response.code === 400) {
              this.$Message.info('入库类型已存在！')
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
  .mark_name{
    width: 80px;
    text-align: right;
    display: inline-block;
  }
</style>
