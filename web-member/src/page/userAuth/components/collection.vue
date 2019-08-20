<template>
  <div class="pt30 pl10 pr10 pb20">
      <Form label-position="left" :label-width="150" >
        <Form-item label="我的收藏">
            <Button type="primary"  @click="handleAddCollection"> <Icon type="plus"></Icon> 增加</Button>
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
                account:'',
                data:[]
            }
        },
        created(){
            this.account = this.loginuserinfo.loginAccount
            this.getInit()
        },
        methods:{
            //初始化获取收藏夹数据
            getInit(){
                this.$api.post('/member/indivi/findIndividInfo',{account:this.account}).then(response => {
                    if (response.code == 200) {
                        if(response.data.CollectData){
                            this.data = response.data.CollectData
                        }else{
                            this.data = []
                        }
                    }
                })
            },
            // 保存收藏夹
            saveCollections(data){
                //保存操作，保存完成后调重新获取数据
                console.log(data)
                if(this.checkCollection(data.title)){
                    this.$api.post('/member/collect/addCollect', {
                        group_name: data.title,
                        remark: data.remark,
                        id: -1,
                        login_account: this.account,
                        pid: data.pid
                    }).then(res => {
                        if ( 200 === res.code) {
                            console.log('保存',res)
                            this.$Message.success('添加成功')
                            this.getInit()
                        }else{
                            if(400 === res.code && 'exist' === res.data){
                                this.$Message.error('分组名不能相同')
                                this.getInit()
                            }
                        }
                    })
                }
            },

            //
            updateCollect(data,title){
                //修改操作，修改完成后调重新获取数据
                console.log(data)
                if(this.checkCollection(title)){
                    this.$api.post('/member/collect/updateCollect', {
                        group_name: title,
                        id: data.id,
                    }).then(res => {
                        console.log(res)
                        if ( 200 === res.code) {
                            this.$Message.success('修改成功')
                            this.getInit()
                        }
                    })
                }
            },
            //点击增加按钮
            handleAddCollection(){
                let parent = {
                    id: -1,
                    title:`新建文件夹${this.data.length+1}`,
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
                                    type: 'ghost',
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
                                        type: 'ghost',
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
                        ])
                    },
                    children:[]
                }
                this.data.push(parent)
                // 调用保存方法
                this.saveCollections(this.data[this.data.length-1])
            },
            //添加
            append (data) {
                console.log(data)
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
                console.log(node)
                if (node.node.children.length) { 
                    // 说明下级还有子文件夹
                    this.$Message.warning('请先删除下级收藏夹')
                } else {
                    this.$Modal.confirm({
                        title: '是否确定删除',
                        content: '是否确认删除？',
                        onOk:()=>{
                            // root坐在父节点的所有元素，包含父元素下所有子元素的children，nodeKey
                            console.log(root)
                            console.log(node)
                            console.log(data)
                            this.$api.post('/member/collect/del', {
                                id: data.id,
                                account: this.loginuserinfo.loginAccount
                            }).then(res => {
                                console.log('res', res)
                                if ( 200 === res.code) {
                                    if (true === res.data) {
                                        this.$Message.success('删除成功')
                                        this.getInit()
                                    }
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
                console.log(this.data)
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
                                //     console.log(e)
                                //     console.log(data)
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
                                    console.log(data)
                                    console.log(e.target.value)
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
                            type: 'ghost',
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
                                type: 'ghost',
                                size: 'small',
                                icon: 'minus'
                            },
                            on: {
                                click: () => {
                                    console.log('删除')
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
<style lang="scss">
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

