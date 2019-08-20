<template>
    <Modal v-model="show" :mask-closable="false">
        <p slot="header">编辑收藏</p>
        <div>
            <Row class="collect-bd mt10">
                <Tree :data="data" :render="renderContent" show-checkbox multiple ref="tree"></Tree>
            </Row>
            <Row>
                <Col span="18" push="10">
                <Button  @click="getCheckedNodes">点击收藏</Button>
                </Col>
            </Row>
        </div>
        <div slot="footer"></div>
    </Modal>
</template>
<script>
    export default {
        name: "edit-collect",
        props: {
            value: {
                type: Boolean,
                default: false
            },
            itemId: {
                type:Number
            },
            templateId: String
        },
        data () {
            return {
                show: false,
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                data: [],
            }
        },
        methods: {
            init () {
                this.show = true
                this.getCollectDir()
            },
            getCheckedNodes(){
                let seletedNode = this.$refs.tree.getCheckedNodes()
                console.log(seletedNode)
                if (0 === seletedNode.length) {
                    this.$Message.error('请选择目录')
                } else if (1 === seletedNode.length) {
                    if (seletedNode[0].id !== 0) { // 说明选的是 我的收藏夹
                        console.log('seletedNode[0]', seletedNode[0])
                        this.$api.post('/member/report/updateCollect', {
                            id: this.itemId,
                            collectId: seletedNode[0].id
                        }).then(response => {
                            if (200 === response.code){
                                this.$Message.success('收藏成功!')
                                this.show = false
                            } else {
                                this.$Message.error('收藏失败!')
                            }
                        })
                    } else { // 否则就是选中了两个，那么提示
                        this.$Message.error('请选择正确的目录')
                    }
                }  else {
                    //有儿子但是选择大于2个的不要
                    let count = 0
                    let index = 0
                    seletedNode.forEach(e => {
                        if (e.children && !e.children.length) {
                            count++
                        }
                    })
                    if (count === 1) {//只能有一个儿子才是正确的
                        seletedNode.forEach((ee, i) => {
                            if ('' === ee.children) index = i
                        })
                        this.$api.post('/member/report/updateCollect', {
                            id: this.itemId,
                            collectId: seletedNode[index].id
                        }).then(response => {
                            if (200 === response.code) {
                                this.$Message.success('收藏成功!')
                                this.show = false
                            } else {
                                this.$Message.error('收藏失败!')
                            }
                        })
                    } else {//儿子多余2不能
                        console.log('seletedNode', seletedNode)
                        this.$Message.error('收藏目录只能选择一个子目录')
                    }

                }
                console.log(seletedNode.length)
            },
            getCollectDir() {
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
                                size: 'default',
                                readonly: true
                            },
                            style: {
                                width: '100px'
                            }

                        })
                    ]),
                    h('span', {
                        style: {
                            display: 'inline-block',
                            float: 'right',
                            marginRight: '32px'
                        }
                    }, [

                    ])
                ])
            }
        }
    }
</script>

<style scoped>

</style>