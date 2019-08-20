<template>
    <div class="pd20">
        <Row>
            <Col span="24">
                <span>分类</span>
                <span v-for="(item,index) in typeList" :key="index" class="pl10 pr10">
                    <span @click="chooseType(item, index)" :class="{'farm-group-btn-active': index === activeType, 'farm-group-btn': true}">
                        {{ item.roomClassName }}
                    </span>
                </span>
            </Col>
        </Row>
        <Row class="mt20 pb20">
            <Col span="24">
                <span>状态</span>
                <span v-for="(item,index) in statusList" :key="index" class="pl10 pr10">
                    <span @click="chooseStatus(item, index)" :class="{'farm-group-btn-active': index === activeStatus, 'farm-group-btn': true}">
                        {{ item.statusName }}
                    </span>
                </span>
            </Col>
        </Row> 
        <Row class="pt20 pb20" type="flex" align="middle">
            <Col span="12">
                <h3>房间列表</h3>
                <!-- <Input v-model="keyWord" icon="ios-search" placeholder="请输入关键字" style="width: 200px" @on-enter="handleSearch" @on-click="handleSearch" /> -->
            </Col>
            <Col span="12" class="tr">
                <Button 
                    type="default" 
                    icon="android-add"
                    @click="handleAddTicket" >
                    添加房间
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
            <div class="pd30">
              <Form  ref="data" :model="data" :label-width="80" :rules="ruleInline" label-position="left">
                <FormItem  label="房间分类" prop="roomClassId">
                  <Select v-model="data.roomClassId" style="width:100%;" @on-change="roomClassChange">
                      <Option v-for="(item, index) in typeList" :value="item.id" v-if="index">{{item.roomClassName}}</Option>
                  </Select>
                </FormItem>
                 <FormItem  label="房间名称" prop="roomName">
                  <Input v-model="data.roomName" :maxlength="10"></Input>
                </FormItem>
                <FormItem  label="房间价格" prop="roomPrice">
                  <Input v-model="data.roomPrice" :maxlength="10"  @on-change="handleChange"> <span slot="append">元</span></Input>
                </FormItem>
                 <FormItem  label="折扣价格" prop="discountPrice">
                  <Input v-model="data.discountPrice" :maxlength="10" style="width:180px" @on-change="handleChange"></Input>  <span class="pr10 pl10"></span>
                  <Input v-model="data.discountProportion"  readonly style="width:182px"  placeholder="自动生成打折比例"></Input>
                </FormItem>
                 <FormItem  label="房间描述" prop="roomDescribe">
                  <Input v-model="data.roomDescribe" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200"></Input>
                </FormItem>
                <FormItem  label="房间状态" prop="status">
                   <RadioGroup v-model="data.status">
                        <Radio :label="0">空闲中</Radio>
                        <Radio :label="1">使用中</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="房间图片" prop="roomImage">
                     <vui-upload
                      ref="upload"
                      @on-getPictureList="getPictureList"
                      :total="1"
                      :multiple="false"
                      :hint="'图片大小小于2M'"
                     ></vui-upload>
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
import vuiUpload from '~components/vui-upload'
    export default {
        name: 'roomList',
        components: {
            vuiUpload
        },
        data () {
            return {
                height:0,
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                account:'',
                columns1: [
                   {
                        title: '房间名称',
                        align: 'center',
                        key: 'roomName'
                    },
                    {
                        title: '房间类型',
                        align: 'center',
                        key: 'roomClassName'
                    },
                    {
                        title: '房间价格',
                        align: 'center',
                        key: 'roomPrice',
                        render: (h, params) => {
                            return h('span',`￥ ${params.row.roomPrice}`)
                        }
                    },
                    {
                        title: '房间状态',
                        align: 'center',
                        key: 'status',
                        // render: (h, params) => {
                        //   if (params.row.status == 1) {
                        //     return h('div',{
                        //     //   style: {
                        //     //     color: '#57A97B'
                        //     //   },
                        //     },'使用中')
                        //   } else {
                        //     return h('div',{
                        //     //   style: {
                        //     //     color: '#8C8C8C'
                        //     //   },
                        //     },'空闲中')
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
                typeList: [],
                statusList: [
                    {statusName: '全部状态', id: -1},
                    {statusName: '空闲中', id: '0'},
                    {statusName: '使用中', id: '1'}
                ],
                activeType: 0,
                activeStatus: 0,
                roomClassName: '',
                status: '',
                keyWord: '',
                show: false,
                title: '添加房间',
                data: {},
                ruleInline: {
                  roomClassId: [
                    { required: true, message: '请填写房间分类', type: 'number', trigger: 'change' }
                  ],
                  roomName: [
                    { required: true, message: '请填写房间名称', trigger: 'blur' }
                  ],
                  roomPrice: [
                    { required: true, message: '请填写房间价格', trigger: 'blur' },
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
                total: 0,
                pageSize: 10,
                pageNum: 1,
                isDiscount: 1
            }
        },
        created(){
            this.account = this.loginuserinfo.loginAccount
            this.handleInitList()
            this.handleTypeList()
        },
        methods: {
            // 弹出框中选择房间分类改变
            roomClassChange (e) {
                this.typeList.forEach(element => {
                    if (element.id == e) {
                        this.data.roomPrice = element.roomClassPrice
                    }
                })
            },
            // 获取房间分类
            handleTypeList () {
                this.$api.post('/member/accommodation/findRoomClass', 
                {account: this.account, pageNum: 1, pageSize: 100000})
                .then(response => {
                    if (response.code === 200) {
                        let arr =  [{roomClassName: '所有分类', id: -1}]
                        this.typeList = arr.concat(response.data.list)
                    }
                })
            },
            getPictureList (e) {
                // 获取图片
                var arr = []
                e.forEach(element => {
                        if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.data.roomImage = arr
            },
            // 搜索
            handleSearch () {
            },
             // 初始化查询房间列表
            handleInitList () {
                this.$api.post('/member/accommodation/findRoomList', 
                {account: this.account, pageNum: this.pageNum, pageSize: this.pageSize, status: this.status, roomClassName: this.roomClassName})
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
            // 选择分类
            chooseType (item, index) {
                this.activeType = index
                this.roomClassName = item.id
                if (item.id > -1) {
                    this.roomClassName = item.roomClassName
                } else {
                    this.roomClassName = ''
                }
                this.handleInitList()
            },
            // 选择状态
            chooseStatus (item, index) {
                console.log(item)
                this.activeStatus = index
                if (item.id  > -1) {
                    this.status = item.id
                } else {
                    this.status = ''
                }
                this.handleInitList()
            },
            // 点击添加房间
            handleAddTicket () {
                this.$refs['data'].resetFields()
                this.title = '添加房间'
                this.data = {
                    roomClassId: null,
                    roomName: '',
                    roomPrice: '',
                    discountProportion: '',
                    discountPrice: '',
                    roomDescribe: '',
                    status: 0,
                    roomImage: []
                }
                this.$refs['upload'].handleGive(this.data.roomImage)
                this.show = true
            },
            // 取消
            handleCancel () {
                this.show = false
            },
            // 确定
            handleOk () {
                if (this.data.flag) {
                     this.$Message.error({
                        content: '此房间已被关联无法编辑，请先解除关联后再进行编辑。',
                        duration: 5
                    });
                    return
                }
                if (this.isDiscount == 2) {
                    this.$Message.error('折扣价格必须小于房间价格')
                    return
                }
                this.$refs['data'].validate((v) => {
                    if (v) {
                        this.data.account = this.account
                        let url = ''
                        if (this.title == '编辑房间') { // 调更新接口
                            url = '/member/accommodation/updateRoomList'
                        } else { // 调插入接口
                            url = '/member/accommodation/insertRoomList'
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
                // if (item.flag === 0) { // 说明没有被关联
                    this.show = true
                    this.title = '编辑房间'
                    this.activeIndex = index
                    this.data = Object.assign({},item)
                    //  0 对应 下架(空闲中)， 1对应 上架 （使用中）
                    if (this.data.status == '使用中') {
                        this.data.status = 1
                    } else if (this.data.status == '空闲中') {
                        this.data.status = 0
                    }
                    this.data.roomClassId = Number(this.data.roomClassId)
                    this.$refs['upload'].handleGive(this.data.roomImage)
                // } else {
                //     this.$Message.error({
                //         content: '此房间已被关联无法编辑，请先解除关联后再进行编辑。',
                //         duration: 5
                //     });
                // }
            },
            // 计算折扣价格
            handleChange () {
                let reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/
                if (this.data.discountPrice &&
                    this.data.roomPrice &&
                    reg.test(this.data.discountPrice) &&
                    reg.test(this.data.roomPrice)) {
                    let discountProportion = parseFloat(this.data.discountPrice / this.data.roomPrice * 100).toFixed(2)
                    if (discountProportion > 100) {
                        this.$Message.error('折扣价格必须小于房间价格')
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
                        content: '确定删除该房间？',
                        onOk: () => {
                            this.$api.post('/member/accommodation/deleteRoomList', {
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
                } else {
                    this.$Message.error({
                        content: '此房间被关联无法删除，请先解除关联后再进行删除。',
                        duration: 5
                    });
                }
            }
        }
    }
</script>
<style scoped>
    .farm-group-btn {
        color: #9B9B9B;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
    .farm-group-btn-active {
        color: #00c587;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
</style>


