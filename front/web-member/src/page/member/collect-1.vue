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
                    <Row>
                        <div class="pd20">
                            <h3>收藏管理</h3>
                            <Form :model="specForm" :label-width="90" class="mt15">
                                <Row>
                                    <Col span="10">
                                    <FormItem label="名称：">
                                        <Input v-model="specForm.name" placeholder="请输入内容"/>
                                    </FormItem>
                                    </Col>
                                    <Col span="10">
                                    <FormItem label="备注信息：" v-show="false">
                                        <Input v-model="specForm.mark" placeholder="请输入内容"/>
                                    </FormItem>
                                    </Col>
                                    <Col span="2">
                                    <Button type="default" @click="goSearch">查询</Button>
                                    </Col>
                                    <Col span="2">
                                    <Button type="default" @click="resetSearch">重置</Button>
                                    </Col>
                                </Row>
                            </Form>
                            <Row class="collect-hd mt15">
                                <Col span="8">
                                分组名称</Col>
                                 <Col span="12"   style="text-align: center"></Col>
                                <Col span="4" offset="12" style="text-align: center">
                                操作</Col>
                            </Row>
                            <Row class="collect-bd mt10">
                                <Tree :data="data" :render="renderContent" :on-select-change="selectedSearch"></Tree>
                            </Row>
                            <h3 class="mt20 mt10">内容管理</h3>
                            <div>
                                <dt>
                                    <Row class="collect-hd mt15">
                                        <Col span="8">
                                        标题</Col>
                                        <Col span="12"   style="text-align: center"></Col>
                                        <Col span="4" offset="12" style="text-align: center">
                                        操作</Col>
                                    </Row>
                                </dt>
                                <template v-if="contentList.length>0">
                                    <template v-for="(item,index) in contentList" >
                                        <Row class="mt10 pl10">
                                            <Col span="18">
                                            <a :href="item.path">{{item.title}}</a>
                                            </Col>
                                            <Col span="6" class="tc">
                                            <Button type="default" @click=" editCla(item.id)">编辑分类</Button>
                                            <Button type="default" @click="delCla(item.id)">删除</Button>
                                            </Col>
                                        </Row>
                                    </template>
                                </template>
                                <template v-else>
                                    <Row>
                                        <h3 class="collect-noData">暂无数据</h3>
                                    </Row>
                                </template>
                            </div>
                            <div class="clear mt20 tc">
                                <Page :total="total" :current="currentPage"
                                      :page-size="pageSize" @on-change="pageChange"
                                      show-total></Page>
                            </div>
                        </div>
                    </Row>
                    </Col>
                </Row>
            </div>
        </div>
        <edit-collect  v-model="collectModal" :itemId="itemId"></edit-collect>
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
    export default {
        components: {
            top,
            foot,
            VueTreeList,
            editCollect,
            appBanner
        },

        data() {
            return {
                data: [
                    {
                        id: 0,
                        title: '我的收藏',
                        expand: true,
                        render: (h, {root, node, data}) => {
                            return h('span', {
                                style: {
                                    display: 'inline-block',
                                    width: '100%'
                                }
                            }, [
                                h('span', [
                                    h('Icon', {
                                        props: {
                                            type: 'ios-folder-outline'
                                        },
                                        style: {
                                            marginRight: '8px'
                                        }
                                    }),
                                    h('span', data.title)
                                ]),
                                h('span', {
                                    style: {
                                        display: 'inline-block',
                                        float: 'right',
                                        marginRight: '32px'
                                    }
                                }, [
                                    h('Button', {
                                        props: {
                                            icon: 'plus',
                                            type: 'primary',
                                            size: 'small'
                                        },
                                        style: {
                                            width: '52px'
                                        },
                                        on: {
                                            click: () => {
                                                this.append(data)
                                            }
                                        }
                                    })
                                ])
                            ]);
                        },
                        children: []
                    }],
                contentList: [],
                contentForm: {
                    title: ''
                },
                classifyLayerShow: false,
                specForm: {
                    name:'',
                    mark:''
                },
                classifyForm: {
                    id:'',
                    itemId: ''
                },
                name: '',
                mark: '',
                displayName: '',
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                signature: '还没有签名！',
                favorite: 0,
                num: 0,
                value: '',
                list:[],
                total:0,
                currentPage:1,
                pageSize:5,
                collectId:'',
                collectModal:false,
                itemId:0

            }
        },
        created: function () {
            this.shouTop()
            this.getContList()
            this.getCollectDir()
        },

        methods: {
            edit(){

            },
            // 回显收藏
            getCollectDir() {

                this.$api.post('/member/collect/queryAll', {
                    account: this.loginuserinfo.loginAccount
                }).then(res => {
                    if (200 === res.code) {
                        let treeData = res.data.tree
                        let elementOne = {}
                        // 遍历树
                        if('' !== treeData && treeData.length > 0){
                            this.data[0].children = res.data.tree
                        }


                        /*res.data.forEach(e =>
                            this.data[0].children.push({expand: true, remark: e.remark, title: e.group_name })
                        )*/
                    }
                })
            },
            resetSearch() {
                this.collectId = ''
                this.specForm.name= ''
                this.specForm.mark= ''
                this.getContList()
            },
            //内容管理列表
            getContList() {
                this.$api.post('/member/report/findCollect', {
                    account: this.loginuserinfo.loginAccount,
                    pageNum :this.currentPage,
                    pageSize:5,
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
            editCla(index) {
                this.collectModal = true
                this.itemId = index
            },
            //内容管理删除
            delCla(id) {
                let v = this
                this.total =0
                this.$Modal.confirm({
                    title: '系统提示',
                    content: '确定是否删除?',
                    onOk: () => {
//                        this.contentList.splice(index, 1)
                        this.$api.post('/member/report/delFollow', {
                            id :id
                        }).then(res => {
                            if (200 === res.code) {
                                this.goSearch()
                            }
                        })
                        this.$Message.success('删除成功')
                    },
                    onCancel: () => {
                        this.$Message.info('取消删除');
                    }
                })
            },
            goSearch(){
                this.getContList()
            },
            renderContent(h, {root, node, data}) {
                return h('span', {
                    style: {
                        display: 'inline-block',
                        width: '100%'
                    }
                }, [
                    h('span', [
                        h('Icon', {
                            props: {
                                type: 'ios-paper-outline'
                            },
                            style: {
                                marginRight: '8px'
                            }
                        }),
                        h('Input', {
                            props: {
                                value: data.title,
                                size: 'small'
                            },
                            nativeOn: {
                                change: (e, data) => {
                                    node.node.title = e.target.value
                                }
                            },
                            style: {
                                width: '100px'
                            },
                            on: {
                                'on-focus': () => {
                                    this.selectedSearch(data)
                                },
                                'on-blur' :() =>{
                                    this.save(data)
                                }
                            }

                        }),
                        /*h('Button', {
                            props: {
                                type: 'ghost',
                                size: 'small',
                                icon: 'checkmark-round'
                            },
                            style: {
                                marginLeft: '5px'
                            },
                            on: {
                                click: () => {
                                    if(node.node.title.length>20){
                                        this.$Message.error('输入字数不能超过20个字！')
                                        return false
                                    }else{
                                        this.saveNode(data)
                                    }
                                }
                            }
                        })*/
                    ]),
                    h('span', {
                        style: {
                            display: 'inline-block',
                            float: 'right',
                            marginRight: '32px'
                        }
                    }, [
                       /* h('Input', {
                            props: {
                                value: data.remark,
                                size: 'small'
                            },
                            nativeOn: {
                                change: (e) => {
                                    node.node.remark = e.target.value
                                }
                            },
                            style: {
                                marginRight: '420px',
                                width: '100px'
                            }
                        }),*/
                        // 添加按钮
                        h('Button', {
                            props: {
                                type: 'primary',
                                size: 'small',
                                icon:'plus'
                            },
                            style: {
                                marginRight: '8px'
                            },
                            on: {
                                click: () => { 
                                    this.append(data) 
                                }
                            }
                        }),
                        // 删按钮
                        h('Button', {
                            props: {
                                type: 'error',
                                size: 'small',
                                icon: 'minus'
                            },
                            on: {
                                click: () => {
                                    this.remove(root, node, data)
                                }
                            }
                        })
                    ])
                ]);
            },
            append(data) {
                const children = data.children || []
                if (0 === data.nodeKey) {//添加时带父id pid=0
                    children.push({
                        title: '默认目录',
                        expand: true,
                        pid: 0,
                        remark: '',
                        id: -1
                    })
                    this.$set(data, 'children', children)
                } else {
                    if ('' !== data.title) {
                        children.push({
                            title: '默认目录',
                            expand: true,
                            pid: data.id,
                            remark: '',
                            id: -1
                        })
                        this.$set(data, 'children', children)
                    } else {
                        this.$Message.error('请先填写上级目录')
                    }
                }

            },
            remove(root, node, data) {
                const parentKey = root.find(el => el === node).parent;
                const parent = root.find(el => el.nodeKey === parentKey).node;
                const index = parent.children.indexOf(data)
                if (data.id === -1 ){// 未填充数据直接删除

                    this.$Modal.confirm({
                        content: '确定删除未保存目录?',
                        onOk: () => {
                            parent.children.splice(index, 1)
                        },
                        /*onCancel: () => {
                            this.$Message.info('取消删除');
                        }*/
                    })
                }else {
                    this.$Modal.confirm({
                        content: '确定删除该目录及该目录下收藏的文章?',
                        onOk: () => {
                            this.$api.post('/member/collect/del', {
                                id: data.id,
                                account: this.loginuserinfo.loginAccount
                            }).then(res => {
                                if ( 200 === res.code) {
                                    if (true === res.data) {
                                        this.$Message.success('删除成功')
                                        parent.children.splice(index, 1)
                                        this.goSearch()
                                    } else {
                                        this.$Message.error('该目录下还有内容请清空后删除')
                                    }
                                }
                            })
                        },
                        /*onCancel: () => {
                            this.$Message.info('取消删除');
                        }*/
                    })
                }
            },
            saveNode(data) {
                // 判断是添加还是更新
               // if()
                if('' === data.title){
                    this.$Message.error('请先填写分组名称')
                }else {
                    // id = - 1 说明是添加,其他说明是更新
                    if( -1 === data.id){
                        this.$api.post('/member/collect/addCollect', {
                            group_name: data.title,
                            remark: data.remark,
                            id: -1,
                            login_account: this.loginuserinfo.loginAccount,
                            pid: data.pid
                        }).then(res => {
                            if ( 200 === res.code) {
                                this.$Message.success('添加成功')
                                data.id = res.data
                            }else{
                                if(400 === res.code && 'exist' === res.data){
                                    this.$Message.error('分组名不能相同')
                                }
                            }
                        })
                    }else{
                        this.$api.post('/member/collect/updateCollect', {
                            group_name: data.title,
                            id: data.id,
                        }).then(res => {
                            if ( 200 === res.code) {
                                //this.$Message.success('更新成功')
                            }
                        })
                    }
                }

            },
            pageChange(e){
                //当前页数
                this.currentPage = e
                this.getContList()
            },
            save(data){
                if(data.title.length>20){
                    this.$Message.error('输入字数不能超过20个字！')
                    return false
                }else if (data.title === ''){
                    this.$Message.error('请输入目录名称！')
                    return false
                }else{
                    this.saveNode(data)
                }
            },
            /**
             * 头部数据显示
             */
            shouTop() {
                api.get('/member/memberCenter/index')
                    .then(response => {
                        this.favorite = response.data.favorite
                        this.num = response.data.number
                        this.signature = response.data.signature
                        this.displayName = response.data.displayName
                    })
                    .catch(function (error) {
                        this.$Message.error(error)
                    })
            },
            /**
             * 点击显示收藏了的文章
             * @param e
             * @param data
             */
            selectedSearch(e){
                //id 不是-1 时即输入框没填写完全
                if(e.id !== -1){
                  this.collectId = e.id
                  this.getContList()
                }
            }
        },

    }
</script>
<style>


    .collect-hd {
        line-height: 40px;
        background: #f8f8f9
    }


    .collect-list dt {
        line-height: 40px;
        background: #f8f8f9;
    }
    /* .collect-list {
        height: 200px;
    } */
    .collect-noData{
        color: #00c261;
        text-align: center;
        margin-top: 54px;
    }
</style>