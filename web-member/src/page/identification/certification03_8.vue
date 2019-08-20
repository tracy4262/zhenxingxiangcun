<template>
<!--3设置栏目第八步开始-->
<div>
    <div id="content" class="mt20">
      <Row style="text-align: left;font-size: 16px;margin-bottom: 16px;font-weight: 600;background: #fafafa;padding: 10px 8px">
          <Row class="collect-hd mt15">
              <Col span="8">分组名称</Col>
              <Col span="2" offset="14" style="text-align: center;padding-right:18px">操作</Col>
          </Row>
          <Row class="collect-bd mt10">
              <Tree :data="data" :render="renderContent" ></Tree>
          </Row>
      </Row>
    </div>
    <div class="footer-btn" >
        <i-button type="primary" @click="preStep" size="large">上一步</i-button>
        <i-button type="primary" @click="pass" size="large">下一步</i-button>
        <span class="tiaoguo" @click="pass">跳过</span>
	</div>
</div>
<!--3设置栏目第八步结束-->
</template>
<script>
import $ from 'jquery'
export default {
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
                                width: '100%',
                                fontSize: '16px'
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
                                            console.log('初始化data', data)
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
        }
    },
    methods: {
        // 回显收藏
        getCollectDir() {
            this.$api.post('/member/collect/queryAll', {
                account: this.loginuserinfo.loginAccount
            }).then(res => {
                console.log(res)
                if (200 === res.code) {
                    console.log('回显数据res------>', res)
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
        preStep(){
            let type=this.$route.meta.type
            if(1 === type){
                this.$parent.$parent.$router.push('/pro/member/progress6/progress7/progress13')
            }else{
                this.$parent.$parent.$router.push('/pro/member/step6/step7/step13')
            }
        },
        pass(){
            let type=this.$route.meta.type
            if(1 === type){
                this.$parent.$parent.$parent.gotoPathSec(15)
            }else{
                this.$parent.$parent.$parent.gotoPath(15)
            }
        },
        renderContent(h, {root, node, data}) {
            console.log('render', data)
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
                            size: 'small',
                            autofocus: true
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
                            'on-blur': () => {
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
                    title: '新文件夹',
                    expand: true,
                    pid: 0,
                    remark: '',
                    id: -1
                })
                this.$set(data, 'children', children)
            } else {
                if ('' !== data.title) {
                    children.push({
                        title: '新文件夹',
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
            this.save(children[children.length-1])
        },
        save(data){
            console.log('失去焦点', data)
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
        remove(root, node, data) {
            const parentKey = root.find(el => el === node).parent;
            const parent = root.find(el => el.nodeKey === parentKey).node;
            const index = parent.children.indexOf(data)
            console.log(data)
            if (data.id === -1 ){// 未填充数据直接删除

                this.$Modal.confirm({
                    title: '操作提示',
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
                    title: '操作提示',
                    content: '确定删除该目录及该目录下收藏的文章?',
                    onOk: () => {
                        this.$api.post('/member/collect/del', {
                            id: data.id,
                            account: this.loginuserinfo.loginAccount
                        }).then(res => {
                            console.log('res', res)
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
            console.log('data', data)
        },
        saveNode(data) {
            console.log('保存信息',data)
            // 判断是添加还是更新
            // if()
            if('' === data.title){
                this.$Message.error('请先填写分组名称')
            }else {
                // id = - 1 说明是添加,其他说明是更新
                if( -1 === data.id){
                    console.log('add',data)
                    this.$api.post('/member/collect/addCollect', {
                        group_name: data.title,
                        remark: data.remark,
                        id: -1,
                        login_account: this.loginuserinfo.loginAccount,
                        pid: data.pid
                    }).then(res => {
                        if ( 200 === res.code) {
                            console.log('保存',res)
                            this.$Message.success('添加成功')
                            data.id = res.data
                        }else{
                            if(400 === res.code && 'exist' === res.data){
                                this.$Message.error('分组名不能相同')
                            }
                        }
                    })
                }else{
                    console.log('update',data)
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
            /*let loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            console.log('添加---data===>>>', data)
            this.$api.post('/member/collect/add', {
                group_name: data.title,
                remark: data.remark,
                id: 0,
                account: loginuserinfo.loginAccount
            }).then(res => {
                console.log('res', res)
                if ( 200 === res.code) {
                    this.$Message.success('添加成功')
                    //this.data[0].children.push({expand: true, remark: data.remark, title: data.title})

                }
            })*/
        }
    },
    created: function() {
        this.getCollectDir()
        this.$parent.baifen = 50
    }
}
</script>