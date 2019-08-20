<template>
  <!-- 收藏弹窗 -->
  <Modal v-model="show" :mask-closable="false">
    <p slot="header">收藏</p>
    <Form :model="collectForm" :label-width="80" v-show="false">
      <FormItem label="名字：">
        我的收藏
      </FormItem>
      <FormItem label="分类：">
        <Select v-model="collectForm.classify">
          <Option :value="item.id" v-for="(item,index) in list" :key="index">{{item.group_name}}</Option>
        </Select>
      </FormItem>
    </Form>
    <div>
      <Row class="collect-bd mt10">
        <Tree :data="data" :render="renderContent" ref="tree" empty-text="请添加收藏夹"></Tree>
      </Row>
      <Row>
        <Col span="18" push="10">
        <Button  @click="onSave">点击收藏</Button>
        </Col>
      </Row>
    </div>
    <div slot="footer"></div>
  </Modal>
</template>

<script>
import {VueTreeList, Tree, TreeNode} from '~components/VueTreeList/index'
export default {
  components: {
        VueTreeList
    },
  name: 'colletModal',
  props: {
      value: {
        type: Boolean,
        default: false
      },
      itemId:{
        type: Number
      },
      itemType:{
        type: Number
      },
      link:{
        type:String
      },
      collectTitle:{
        type:String
      }
  },
  data () {
    return {
        show: false,
    	readonly:true,
      collectForm: {
        name: '',
        classify: ''
      },
        title:'',
        list:[],
        type:'',
        loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
        templateId: '',
        data:[],
        chekcGroup: {}

    }
  },
  created () {
    this.$api.post('/member-reversion/realStep/findEnableStep', {
            account: this.$user.loginAccount
        }).then(response => {
        if (response.code === 200) {
            if (response.data) {
                this.templateId = response.data.templateId
                if (this.templateId) {
                    this.getInit()
                }
            }
        }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
  },
    methods: {
        //初始化获取收藏夹数据
        getInit () {
            this.$api.post('/member-reversion/indivi/findIndividInfo',{
                account:this.$user.loginAccount,
                templateId: this.templateId
            }).then(response => {
                if (response.code == 200) {
                    if(response.data.CollectData){
                        this.data = response.data.CollectData
                    }else{
                        this.data = []
                    }
                }
            })
        },
        onSave () {
            if (1 === this.itemType) {//资讯
                this.type ='inforMation';
            } else if (2 === this.itemType) {//政策
                this.type = 'policy';
            } else if (3 === this.itemType) {//知识
                this.type = 'knowLege';
            } else if (4 === this.itemType) {//标准
                this.type = 'standard';
            } else if (5=== this.itemType) {//图书
                this.type = 'book';
            } else if (this.itemType === 'fishService') { // fishService 垂钓
                this.type = 'fishService';
            } else if (this.itemType === 'pickService') { //pickService 采摘 
                this.type = 'pickService';
            } else if (this.itemType === 'scenicSpotService') { // scenicSpotService  3餐饮
                this.type = 'scenicSpotService';
            } else if (this.itemType === 'farmStayService') { // farmStayService  3餐饮
                this.type = 'farmStayService';
            } else if (this.itemType === 'roomService') { // roomService  4住宿。
                this.type = 'roomService';
            }
            let data ={
                type: this.type,
                link: this.link,
                title: this.collectTitle,
                collectId: this.chekcGroup.id,
                account: this.$user.loginAccount
            }
            if (this.chekcGroup.id) {
                this.$api.post('/member/report/saveCollect', data).then(response => {
                    let flag = response.data
                    if(1 === flag) {
                        this.$Message.success('收藏成功!')
                        this.show =false
                        this.$emit('on-init')
                    } else {
                        this.$Message.error('收藏失败!')
                    }
                })
            } else {
                this.$Message.warning('请选择！')
            }
        },
        // getCheckedNodes () {
        //     let seletedNode = this.$refs.tree.getCheckedNodes()
        //     if (1 === this.itemType) {//资讯
        //         this.type ='inforMation';
        //     } else if (2 === this.itemType) {//政策
        //         this.type = 'policy';
        //     } else if (3 === this.itemType) {//知识
        //         this.type = 'knowLege';
        //     } else if (4 === this.itemType) {//标准
        //         this.type = 'standard';
        //     } else if (5=== this.itemType) {//图书
        //         this.type = 'book';
        //     }

        //     if (0 === seletedNode.length) {
        //         this.$Message.error('请选择目录')
        //     } else if (1 === seletedNode.length) {
        //         if (seletedNode[0].id !== 0) { // 说明选的是 我的收藏夹
        //             let data ={
        //                 type: this.type,
        //                 link: this.link,
        //                 title: this.collectTitle,
        //                 collectId: seletedNode[0].id,
        //                 account: this.loginuserinfo.loginAccount
        //             }
        //             console.log('save data1', data)
        //             this.$api.post('/member/report/saveCollect', data).then(response => {
        //                 let flag = response.data
        //                 if(1 === flag) {
        //                     this.$Message.success('收藏成功!')
        //                     this.show =false
        //                 } else {
        //                     this.$Message.error('收藏失败!')
        //                 }
        //             })
        //         } else { // 否则就是选中了两个，那么提示
        //             this.$Message.error('请选择正确的目录')
        //         }
        //     } else {
        //         let count = 0
        //         let index = 0
        //         console.log('seletedNode', seletedNode)
        //         seletedNode.forEach(e => {
        //             if (e.children && !e.children.length) {
        //                 count++
        //             }
        //         })
        //         if (count === 1) {//只能有一个儿子才是正确的
        //             seletedNode.forEach((ee, i) => {
        //                 if (ee.children.length === 0) index = i
        //             })
        //             let data = {
        //                 type: this.type,
        //                 link: this.link,
        //                 title: this.collectTitle,
        //                 collectId: seletedNode[index].id,
        //                 account: this.loginuserinfo.loginAccount
        //             }
        //             console.log('save data2', data)
        //             this.$api.post('/member/report/saveCollect', data).then(response => {
        //                 let flag = response.data
        //                 if(1 === flag) {
        //                     this.$Message.success('收藏成功!')
        //                     this.show =false
        //                 } else {
        //                     this.$Message.error('收藏失败!')
        //                 }
        //             })
        //         } else {
        //             this.$Message.error('收藏目录只能选择一个子目录')
        //         }
        //     }
        // },
        // getCollectDir() {
        //     if (this.loginuserinfo){
        //         this.$api.post('/member/collect/queryAll', {
        //             account: this.loginuserinfo.loginAccount
        //         }).then(res => {
        //             if (200 === res.code) {
        //                 let treeData = res.data.tree
        //                 let elementOne = {}
        //                 // 遍历树
        //                 if('' !== treeData && treeData.length > 0){
        //                     this.data[0].children = res.data.tree
        //                 }
        //             }
        //         })
        //     }

        // },
       /* initDataByType () {
            // this.itemId,this.itemType
            if (1 === this.itemType) {//资讯
                this.type ='inforMation'
                this.$api.get('/member/inforMation/findInforMationDetail?id=' + this.itemId)
                    .then(response => {
                        this.title = response.data.title
                    })
            } else if (2 === this.itemType) {//政策
                this.type = 'policy'
                this.$api.get('/member/policy/findPolicyDetail?id=' + this.itemId)
                    .then(response => {
                        this.title = response.data.title
                    })
            } else if (3 === this.itemType) {//知识
                this.type = 'knowLege'
                this.$api.get('/member/knowLege/findKnowLedgeDetail?id=' + this.itemId)
                    .then(response => {
                        this.title = response.data.title
                    })
            } else if (4 === this.itemType) {//标准
                this.type = 'standard'
                this.$api.post('/member/standard/getStandardDetail', {
                    standardDetailId: this.itemId
                }).then(response => {
                    console.log('response111', response)
                    if (response.code === 200) {
                        this.title = response.data.title
                    }
                })
            }

        },*/
        renderContent(h, {root, node, data}) {
            return h('div',
                { 
                    style: {
                        position: 'relative',
                        display: 'inline-block'
                    }
                },
                [
                    h('span', [
                        h('Radio', {
                            style:{
                                fontSize: '14px'
                            },
                            props: {
                                value: data.check
                            },
                            nativeOn: {
                                click: () => {
                                    root.forEach((e, index) => {
                                        this.$set(root[index].node, 'check', false)
                                    })
                                    this.$set(data, 'check', true)
                                    console.log(data)
                                    this.chekcGroup = data
                                }
                            }
                        },`${data.title}`)
                    ]),
                ]);
        }
    }
}
</script>

<style lang="scss">
</style>