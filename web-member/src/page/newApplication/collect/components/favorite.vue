<!--
    作者：chenqim
    时间：2018-12-27
    描述：收藏加管理
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <collection ref="collection"></collection>
    </div>
</template>

<script>
    import collection from './collection'
    export default {
        components: {
            collection
        },
        data() {
            return {
                templateId: ''
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
                            this.$refs['collection'].templateId = this.templateId
                            this.$refs['collection'].getInit()
                        }
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        methods: {
        }
    }
</script>
<style scoped>
</style>