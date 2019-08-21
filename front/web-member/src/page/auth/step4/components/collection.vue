<template>
  <div class="pt30 pl10 pr10 pb20">
      <Form label-position="left" :label-width="150" >
        <Form-item label="我的收藏">
            <Button type="primary"  @click="handleAddCollection"><Icon type="md-add"></Icon>增加</Button>
        </Form-item>
        <Tree v-if="data.length != 0" :data="data" :render="renderContent" class="collection-tree"></Tree>
    </Form>
  </div>
</template>
<script>
    export default{
        components:{

        },
        data(){
            return{
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                account: '',
                data: [],
                templateId: this.templateId
            }
        },
        created(){
            this.templateId = this.$route.query.templateId
            this.account = this.loginuserinfo.loginAccount
            this.getInit()
        },
        methods:{
            //初始化获取收藏夹数据
            getInit(){
                this.$api.post('/member-reversion/indivi/findIndividInfo',{
                    account:this.account,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code == 200) {
                        console.log('indi', response)
                        if (response.data.CollectData === undefined || response.data.CollectData === '') {
                            // 如果为空则创建6个文件夹 资讯 知识 政策 产品 标准 服务
                            this.handleAddCollection('init', '资讯')
                            this.handleAddCollection('init', '知识')
                            this.handleAddCollection('init', '政策')
                            this.handleAddCollection('init', '产品')
                            this.handleAddCollection('init', '标准')
                            this.handleAddCollection('init', '服务')
                        } else {
                            this.data = response.data.CollectData
                        }
                    }
                })
            },
            // 保存收藏夹
            saveCollections(data, type){
                //保存操作，保存完成后调重新获取数据
                if (this.checkCollection(data.title)) {
                    let url = this.templateId === '0' ? '/member-reversion/collect/addCollect' : '/member-reversion/collect/user/addCollect'
                    this.$api.post(url, {
                        group_name: data.title,
                        remark: data.remark,
                        id: -1,
                        login_account: this.account,
                        pid: data.pid,
                        templateId: this.templateId
                    }).then(res => {
                        if (res.code === 200) {
                            if (type !== 'init') {
                                this.$Message.success('添加成功！')
                                this.getInit()
                            }
                        } else {
                            if (400 === res.code && 'exist' === res.data) {
                                this.$Message.error('分组名不能相同！')
                                this.getInit()
                            } else {
                                this.$Message.error('保存失败！')
                            }
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            },

            //
            updateCollect(data,title){
                //修改操作，修改完成后调重新获取数据
                if(this.checkCollection(title)){
                    this.$api.post('/member-reversion/collect/updateCollect', {
                        group_name: title,
                        id: data.id,
                        templateId: this.templateId
                    }).then(res => {
                        if ( 200 === res.code) {
                            this.$Message.success('修改成功')
                            this.getInit()
                        }
                    })
                }
            },
            //点击增加按钮
            handleAddCollection(type, name){
                let parent = {
                    id: -1,
                    title: type === 'init' ? name : `新建文件夹${this.data.length+1}`,
                    remark:'',
                    expand:true,
                    pid:0,
                    render:(h ,{root, node, data}) => {
                        return h('span' ,{
                            style:{
                                padding: '5px 10px',
                                display: 'inline-block',
                                width: '100%'
                            }
                        },[
                            //左侧
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
                                        // change: (e, data) => {
                                        //     // 检查名称长度，是否为空，是否重复 =>存入后台
                                        //     this.checkCollection(e.target.value,node)
                                        // }
                                    },
                                    on: {
                                        'on-blur': () => {
                                            // this.saveCollections(this.data[this.data.length-1])
                                        }
                                    },
                                    style: {
                                        width: '120px'
                                    }
                                }),
                            ]),
                            //右侧
                            h('span', {
                                style: {
                                    display: 'inline-block',
                                    float: 'right',
                                    marginRight: '32px'
                                }
                            }, [
                                //添加按钮
                                h('Button', {
                                props: {
                                    size: 'small',
                                    icon:'md-add'
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
                                        size: 'small',
                                        icon: 'md-remove'
                                    },
                                    on: {
                                        click: () => {
                                            this.remove(root, node, data)
                                        }
                                    }
                                })
                            ])
                        ])
                    },
                    children:[]
                }
                this.data.push(parent)
                // 调用保存方法
                this.saveCollections(this.data[this.data.length-1], type)
            },
            //添加
            append (data) {
                data.children.push({
                    remark:'',
                    pid: data.id,
                    title: `新建文件夹${data.children.length+1}`,
                    expand: true,
                    id: -1,
                    children:[]
                })
                this.saveCollections(data.children[data.children.length-1])
            }, 
            // 移除
            remove (root, node, data) {
                if (node.node.children.length) { 
                    // 说明下级还有子文件夹
                    this.$Message.warning('请先删除下级收藏夹！')
                } else {
                    this.$Modal.confirm({
                        title: '操作提示',
                        content: '是否确认删除该收藏夹？',
                        onOk:()=>{
                            // root坐在父节点的所有元素，包含父元素下所有子元素的children，nodeKey
                            this.$api.post('/member-reversion/collect/del', {
                                id: data.id,
                                account: this.loginuserinfo.loginAccount
                            }).then(res => {
                                if (res.code === 200) {
                                    if (true === res.data) {
                                        this.$Message.success('删除成功！')
                                        this.getInit()
                                    }
                                } else if (res.code === 400) {
                                    this.$Message.info('该收藏夹下有收藏的内容，请先删除收藏的内容！')
                                }
                            })                          
                        },
                        okText:'确定',
                        cancelText:'取消'
                    });
                }
            },
            //判断收藏夹名称长度以及是否存在
            checkCollection (title) {
                //判断是否为空
                if (title) {
                    //判断长度
                    if (title.length > 20) {                        
                        this.$Message.error('输入字数不能超过20个字！')
                        return false
                    } else {
                        return true
                    }
                } else {
                    this.$Message.error('请输入收藏夹名称')
                    return false
                }
            },
            //点击父级的添加按钮
            renderContent (h, {root, node, data}) {
                return h('span' ,{
                    style:{
                        padding: '5px 10px',
                        display: 'inline-block',
                        width: '100%'
                    },
                    attrs:{
                        class:'render-li'
                    }
                },[
                    //左侧
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
                                // change: (e, data) => {
                                //     this.checkCollection(e.target.value,node)
                                // }
                            },
                            style: {
                                width: '120px'
                            },
                            on: {
                                'on-blur': (e) => {
                                    if(e.target.value != data.title){                                        
                                        this.updateCollect(data,e.target.value)
                                    }
                                }
                            }
                        }),
                    ]),
                    //右侧
                    h('span', {
                        style: {
                            display: 'inline-block',
                            float: 'right',
                            marginRight: '32px'
                        }
                    }, [
                        //添加按钮
                         h('Button', {
                        props: {
                            size: 'small',
                            icon: 'md-add'
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
                                size: 'small',
                                icon: 'md-remove'
                            },
                            on: {
                                click: () => {
                                    this.remove(root, node, data)
                                }
                            }
                        })
                    ])
                ])
            }
        }
    }
</script>
<style lang="scss" scoped>
.collection-tree{
    border-bottom: 1px solid #e7e7e7;
}
.collection-tree>.ivu-tree-children>li{
    padding:10px;
    border: 1px solid #E7E7E7;
    border-bottom: none;
    margin:0px;
    .render-li{
        #edit{
            display: none;
        }
        &:hover{
            #edit{
                display: inline-block;
            }
        }
    }
}
</style>

