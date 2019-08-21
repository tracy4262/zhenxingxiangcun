<template>
    <div>
        <div v-for="(item, index) in list" :key="index">
            <preview :ref="`preview${item.mode}`" :item="item" :yearId="yearId" :templateId="templateId" @refresh="refresh" />
        </div>
    </div>
</template>
<script>
import preview from './components/preview'
export default {
    components: {
        preview
    },
    props: {
        yearId: {
            type: String
        }
    },
    watch: {
        yearId: {
            handler (newValue, oldValue) {
                this.list = []
                this.init()
            },
            deep: true
        }
    },
    created () {
        this.templateId = this.$route.query.templateId
        if (this.yearId !== undefined && this.yearId !== '') {
            this.init()
        }
    },
    data () {
        return {
            list: [],
            templateId: ''
        }
    },
    methods: {
        init () {
            this.$api.post('/member-reversion/user/perfect/findAllTextPreviewList', {
                account: this.$user.loginAccount,
                yearId: this.yearId,
                level: '0',
                templateId: this.templateId
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
