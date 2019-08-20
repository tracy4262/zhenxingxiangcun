<template>
    <div class="pd20">

        <!-- 物种标题 -->        
        <Row class="pt20 pb20" type="flex" align="middle">
            <Col span="12">
               <h3>房间分类</h3>
            </Col>
            <Col span="12" class="tr">
                <Button 
                    type="default" 
                    icon="android-add"
                    @click="handleAddTicket" >
                    添加分类
                </Button>
            </Col>
        </Row>
        <div class="pb30">
            <Table  :columns="columns1" :data="ticketDatas"></Table>
        </div>
         <div class="tc pb50">
            <Page :total="total" :page-size="pageSize"  @on-change="hanhdleChangePage"></Page>
        </div>
        <Modal v-model="show" width="560" :title="title" :mask-closable="false">
            <div class="pd20">
              <Form  ref="data" :model="data" :label-width="100" :rules="ruleInline">
                <FormItem  label="房间分类名称" prop="roomClassName">
                  <Input v-model="data.roomClassName" :maxlength="10"></Input>
                </FormItem>
                <FormItem  label="房间分类价格" prop="roomClassPrice">
                  <Input v-model="data.roomClassPrice" :maxlength="10"> <span slot="append">元</span></Input>
                </FormItem>
              </Form>
            </div>
            <div slot="footer">
                <Button type="text" @click="handleCancel">取消</Button>
                <Button type="primary" @click="handleOk">确定</Button>
            </div>
        </Modal>
    </div> 
</template>
<script>
import {isMoney3 } from '~utils/validate'
    export default {
        name: 'roomType',
        data () {
            return {
                height:0,
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                account:'',
                columns1: [
                   {
                        title: '房间分类',
                        align: 'center',
                        key: 'roomClassName'
                    },
                    {
                        title: '房间价格',
                        align: 'center',
                        key: 'roomClassPrice',
                        render: (h, params) => {
                            return h('span',`￥ ${params.row.roomClassPrice}`)
                        }
                    },
                    {
                        title: '操作',
                        key: 'todo',
                        // width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                               h('Button', {
                                  props: {
                                      type: 'text',
                                      size: 'small'
                                  },
                                  style: {
                                      color: '#57A97B'
                                  },
                                  on: {
                                      click: () => {
                                          this.edit(params.row, params.index)
                                      }
                                  }
                              }, '编辑'),
                              h('Button', {
                                  props: {
                                      type: 'text',
                                      size: 'small'
                                  },
                                  style: {
                                      color: '#8C8C8C'
                                  },
                                  on: {
                                      click: () => {
                                          this.delete(params.row, params.index)
                                      }
                                  }
                              }, '删除')
                            ]);
                        }
                    }
                ],
                ticketDatas: [],
                show: false,
                title: '添加分类',
                data: {},
                ruleInline: {
                  roomClassName: [
                    { required: true, message: '请填写房间分类名称', trigger: 'blur' }
                  ],
                  roomClassPrice: [
                    { required: true, message: '请填写房间分类价格', trigger: 'blur' },
                     { required: true, validator: isMoney3, trigger: 'blur' }
                  ]
                },
                activeIndex: 0,
                total: 0,
                pageSize: 10,
                pageNum: 1
            }
        },
        created(){
            this.account = this.loginuserinfo.loginAccount
            this.handleInitList()
        },
        methods: {
          // 点击添加房间
          handleAddTicket () {
            this.$refs['data'].resetFields()
            this.title = '添加分类'
            this.data = {
                  roomClassName: '',
                  roomClassPrice: '',
                }
            this.show = true
          },
           // 初始化查询房间分类
            handleInitList () {
                this.$api.post('/member/accommodation/findRoomClass', 
                {account: this.account, pageNum: this.pageNum, pageSize: this.pageSize})
                .then(response => {
                    if (response.code === 200) {
                        this.ticketDatas = response.data.list
                        this.total = response.data.total
                    }
                })
            },
             // 翻页
            hanhdleChangePage (e) {
                this.pageNum = e
                this.handleInitList()
            },
          handleCancel () {
            this.show = false
          },
          handleOk () {
              this.$refs['data'].validate((v) => {
                  if (v) {
                        this.data.account = this.account
                        let url = ''
                        if (this.title == '编辑分类') { // 调更新接口
                            url = '/member/accommodation/updateRoomClass'
                        } else { // 调插入接口
                            url = '/member/accommodation/insertRoomClass'
                        }
                        this.$api.post(url, this.data).then(response => {
                            if (response.code === 200) {
                                this.show = false
                                this.$Message.success('保存成功')
                                this.pageNum = 1
                                this.handleInitList()
                            } else if (response.code === 300) {
                                this.$Message.error('房间名称重复')
                            }
                        })
                  } else {
                      this.$Message.error('请核对输入信息')
                  }
              })
             
          },
          // 编辑
          edit (item, index) {
            this.$api.post('/member/accommodation/findRoomList', 
                {account: this.account, pageNum: 1, pageSize: 999999, status: '', roomClassName: item.roomClassName})
                .then(response => {
                    if (response.code === 200) {
                        if (response.data.total) {
                            // 说明当前房间分类下有房间
                            this.$Message.error({
                                content: '此房间类型下已有房间，请先删除房间后再编辑房间类型。',
                                duration: 5
                            });
                        } else {
                            // 说明当前房间分类下没有房间
                            this.title = '编辑分类'
                            this.show = true
                            this.activeIndex = index
                            this.data = Object.assign({},item)
                        }
                    }
                })
          },
          // 删除
          delete (item, index) {
               this.$api.post('/member/accommodation/findRoomList', 
                {account: this.account, pageNum: 1, pageSize: 999999, status: '', roomClassName: item.roomClassName})
                .then(response => {
                    if (response.code === 200) {
                        if (response.data.total) {
                            // 说明当前房间分类下有房间
                            this.$Message.error({
                                content: '此房间类型下已有房间，请先删除房间后再删除房间类型。',
                                duration: 5
                            });
                        } else {
                            // 说明当前房间分类下没有房间
                            this.$Modal.confirm({
                                title: '操作提示',
                                content: '确定删除该分类？',
                                onOk: () => {
                                    this.$api.post('/member/accommodation/deleteRoomClass', {
                                        id : item.id
                                    }).then(res => {
                                        if(res.code === 200){
                                            this.$Message.success('删除成功')
                                            if (this.pageNum !== 1 && this.ticketDatas.length == 1) {
                                                this.pageNum -=1
                                            }
                                            this.handleInitList()
                                        }else{
                                            this.$Message.error('删除失败')
                                        }
                                    })
                                }
                            })
                        }
                    }
                })
          }
        }
    }
</script>

