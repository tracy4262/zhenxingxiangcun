<template>
    <Modal v-model="show" :mask-closable="false">
        <p slot="header">移动收藏夹</p>
        <div>
            <Tree :data="data" :render="renderContent" ref="tree"  empty-text="请添加收藏夹"></Tree>
        </div>
        <div slot="footer">
            <Button type="text" @click="cancel">取消</Button>
            <Button type="primary" @click="onSave">确定</Button>
        </div>
    </Modal>
</template>
<script>
    export default {
        name: "move",
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
                data: [],
                chekcGroup: {}
            }
        },
        methods: {
            init () {
                this.show = true
                this.getCollectDir()
            },
            cancel () {
                this.show = false
            },
            onSave () {
                if (this.chekcGroup.id) {
                    this.$api.post('/member/report/updateCollect', {
                            id: this.itemId,
                            collectId: this.chekcGroup.id
                        }).then(response => {
                            if (200 === response.code){
                                this.$Message.success('收藏成功!')
                                this.show = false
                                this.$emit('refresh')
                            } else {
                                this.$Message.error('收藏失败!')
                            }
                        })
                } else {
                    this.$Message.warning('请选择！')
                }
            },
            getCollectDir () {
                this.$api.post('/member-reversion/indivi/findIndividInfo',{
                    account:this.$user.loginAccount,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.CollectData) {
                            this.data = response.data.CollectData
                        } else {
                            this.data = []
                        }
                    }
                })
            },
             renderContent (h, { root, node, data }) {
                return h('div',
                    { 
                        style: {
                            position: 'relative',
                            display: 'inline-block'
                        },
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
<style scoped>
</style>