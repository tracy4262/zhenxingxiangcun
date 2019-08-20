<template>
    <div class="pd20">

        <!-- 物种标题 -->        
        <Row class="pt20 pb20" type="flex" align="middle">
            <Col span="12">
               <h3>门票列表</h3>
            </Col>
            <Col span="12" class="tr">
                <Button 
                    type="default" 
                    icon="android-add"
                    @click="handleAddTicket" >
                    添加门票
                </Button>
            </Col>
        </Row>
        <div class="pb30">
            <Table  :columns="columns1" :data="ticketDatas"></Table>
        </div>
        <div class="pt30 tc pb50">
            <Page :total="total" :page-size="pageSize"  @on-change="hanhdleChangePage"></Page>
        </div>
          <Modal v-model="show" width="560" :title="title" :mask-closable="false">
            <div class="pd30">
              <Form  ref="data" :model="data" :label-width="80" :rules="ruleInline" label-position="left">
                <FormItem  label="门票名称" prop="ticketName">
                  <Input v-model="data.ticketName" :maxlength="10"></Input>
                </FormItem>
                <FormItem  label="门票价格" prop="ticketPrice">
                  <Input v-model="data.ticketPrice" :maxlength="10" @on-change="handleChange"> <span slot="append">元</span></Input>
                </FormItem>
                 <FormItem  label="打折价格" prop="discountPrice">
                  <Input v-model="data.discountPrice" style="width:180px" @on-change="handleChange"></Input>  <span class="pr10 pl10"></span>
                  <Input v-model="data.discountProportion" :maxlength="10" readonly style="width:182px"  placeholder="自动生成打折比例"></Input>
                </FormItem>
                 <FormItem  label="门票描述" prop="scenicDescribe">
                  <Input v-model="data.scenicDescribe" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200"></Input>
                </FormItem>
                <FormItem  label="状态" prop="status">
                    <!-- 1:上架 0 下架', -->
                    <RadioGroup v-model="data.status">
                        <Radio :label="1">上架</Radio>
                        <Radio :label="0">下架</Radio>
                    </RadioGroup>
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
import {numMulti} from '~utils/utils'
    export default {
        name: 'ticket',
        data () {
            return {
                pageSize: 10,
                total: 1,
                pageNum: 1,
                height:0,
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                account:'',
                columns1: [
                   {
                        title: '门票名称',
                        align: 'center',
                        key: 'ticketName'
                    },
                    {
                        title: '门票价格',
                        align: 'center',
                        key: 'ticketPrice',
                        render: (h, params) => {
                            return h('span',`￥ ${params.row.ticketPrice}`)
                        }
                    },
                    {
                        title: '状态',
                        align: 'center',
                        key: 'status',
                        // render: (h, params) => {
                        //   if (params.row.status == 1) {
                        //     return h('div',{
                        //       style: {
                        //         color: '#57A97B'
                        //       },
                        //     },'热卖中')
                        //   } else {
                        //     return h('div',{
                        //       style: {
                        //         color: '#8C8C8C'
                        //       },
                        //     },'已下架')
                        //   }
                        // }
                    },
                    {
                        title: '操作',
                        key: 'todo',
                        width: 150,
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
                title: '添加门票',
                data: {},
                ruleInline: {
                  ticketName: [
                    { required: true, message: '请填写房间分类名称', trigger: 'blur' }
                  ],
                  ticketPrice: [
                    { required: true, message: '请填写门票价格', trigger: 'blur' },
                    { required: true, validator: isMoney3, trigger: 'blur' }
                  ],
                  discountPrice: [
                    {validator: isMoney3, trigger: 'blur' }
                  ],
                  status: [
                      { required: true}
                  ]
                },
                activeIndex: 0,
                discountProportion: 1
            }
        },
        created(){
            this.account = this.loginuserinfo.loginAccount
            this.handleInitList()
        },
        methods: {
            // 点击添加门票
            handleAddTicket () {
                this.show = true
                this.title = '添加门票'
                this.data = {
                  ticketName: '',
                  ticketPrice: '',
                  discountProportion: '',
                  discountPrice: '',
                  scenicDescribe: '',
                  status: 1 //1:上架 0 下架',
                }
            },
            // 初始化查询门票列表
            handleInitList () {
                this.$api.post('/member/scenicSpot/findScenicSpot', {account: this.account, pageNum: this.pageNum, pageSize: this.pageSize}).then(response => {
                    if (response.code === 200) {
                        this.ticketDatas = response.data.list
                        this.total = response.data.total
                    }
                })
            },
            // 分页
            hanhdleChangePage (e) {
                this.pageNum = e
                this.handleInitList()
            },
            handleCancel () {
                this.show = false
            },
            handleOk () {
                if (this.data.flag) {
                     this.$Message.error({
                        content: '此门票已被关联无法编辑，请先解除关联后再进行编辑。',
                        duration: 5
                    });
                    return
                }
                if (this.isDiscount == 2) {
                    this.$Message.error('折扣价格必须小于房间价格')
                    return
                }
                this.data.account = this.account
                let url = ''
                if (this.title == '编辑门票') { // 调更新接口
                    url = '/member/scenicSpot/updateScenicSpot'
                } else { // 调插入接口
                    url = '/member/scenicSpot/insertScenicSpot'
                }
                this.$api.post(url, this.data).then(response => {
                    if (response.code === 200) {
                        this.show = false
                        this.$Message.success('保存成功')
                        this.handleInitList()
                    } else if (response.code === 300) {
                        this.$Message.error('门票名称重复')
                    }
                })
            },
            // 编辑
            edit (item, index) {
                // if (item.flag === 0) {
                    this.title = '编辑门票'
                    this.show = true
                    this.activeIndex = index
                    this.data = Object.assign({},item)
                    if (this.data.status == '已停售') {
                        this.data.status = 0
                    } else if (this.data.status == '热卖中') {
                        this.data.status = 1
                    }
                // } else {
                //     this.$Message.error({
                //         content: '此门票已被关联无法编辑，请先解除关联后再进行编辑。',
                //         duration: 5
                //     });
                // }
            },
             // 计算折扣价格
            handleChange () {
                let reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/
                if (this.data.discountPrice &&
                    this.data.ticketPrice &&
                    reg.test(this.data.discountPrice) &&
                    reg.test(this.data.ticketPrice)) {
                    let discountProportion  = parseFloat(this.data.discountPrice / this.data.ticketPrice * 100).toFixed(2)
                    if (discountProportion > 100) {
                        this.$Message.error('折扣价格必须小于门票价格')
                        this.data.discountProportion = ''
                        this.isDiscount = 2
                    } else {
                        this.data.discountProportion = discountProportion + '%'
                        this.isDiscount = 3
                    }
                } else {
                    this.isDiscount = 1
                    this.data.discountProportion = ''
                }
            },
            // 删除
            delete (item, index) {
                if (item.flag === 0) { // 说明没有被关联
                    this.$Modal.confirm({
                        title: '操作提示',
                        content: '确定删除该门票？',
                        onOk: () => {
                            this.$api.post('/member/scenicSpot/deleteScenicSpot', {
                                id : item.id
                            }).then(res => {
                                if (res.code === 200) {
                                    this.$Message.success('删除成功')
                                    if (this.pageNum !== 1 && this.ticketDatas.length == 1) {
                                        this.pageNum -=1
                                    }
                                    this.handleInitList()
                                } else{ 
                                    this.$Message.error('删除失败')
                                }
                            })
                        },
                        okText:'确定',
                        cancelText:'取消'
                    })
                } else {
                    this.$Message.error({
                        content: '此门票已被关联无法删除，请先解除关联后再进行删除。',
                        duration: 5
                    });
                }
            }
        }
    }
</script>

