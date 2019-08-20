<template>
    <div>
        <div v-for="(item, index) in list" :key="index">
            <preview :ref="`preview${item.mode}`" :item="item" @refresh="refresh" />
        </div>
    </div>
</template>
<script>
import preview from './preview'
export default {
    components: {
        preview
    },
    created () {
        this.init()
    },
    data () {
        return {
            list: [],
            templateId: 'd0c615f859b04421ab664e8017a47af7'
        }
    },
    methods: {
        init () {
            this.$api.post('/member-reversion/productionBase/findAllTextPreviewList', {
                account: this.$user.loginAccount,
                level: '0',
                baseId: this.$route.query.id
            }).then(response => {
                console.log('resp', response)
                if (response.code === 200) {
                    response.data.forEach(element => {
                        let content = ''
                        if (element.textPreview.length !== 0) {
                            element.textPreview.forEach(item => {
                                content += item.textPreview
                            })
                        }
                        this.list.push({
                            title: element.appName,
                            content: content,
                            mode: element.url,
                            appId: element.parentId,
                            id: element.textPreview.length !== 0 && element.textPreview[0].textPreviewId !== undefined ? element.textPreview[0].textPreviewId : 0
                        })
                    })
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        refresh () {
            this.list = []
            this.init()
        }
    }
}
</script>
<style lang="scss" scoped>
.info-title {
    color: #4A4A4A;
    font-size: 16px;
}
</style>
