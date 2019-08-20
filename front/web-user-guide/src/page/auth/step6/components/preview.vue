<template>
    <div>
        <Row type="flex" align="middle">
            <Col span="12"><div class="info-title">{{ item.title }}</div></Col>
            <Col span="12" class="tr"><Button type="text" :disabled="true" ghost>保存</Button></Col>
        </Row>
        <Row class="mt10 mb40">
            <Input v-model="item.content" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :disabled="true"/>
        </Row>
    </div>
</template>
<script>
export default {
    props: {
        item: {
            type: Object
        },
        yearId: {
            type: String
        }
    },
    data () {
        return {
        }
    },
    methods: {
        save () {
            console.log('item', this.item)
            this.$api.post('/member-reversion/perfect/saveAllTextPreview', {
                textPreview: {
                    account: this.$user.loginAccount,
                    templateId: this.$template.id,
                    textPreview: this.item.content,
                    parentId: this.item.appId,
                    yearId: this.yearId,
                    textPreviewId: this.item.id
                }
            }).then(response => {
                console.log('resp', response)
                if (response.code === 200) {
                    this.$Message.success('保存成功！')
                    this.$emit('refresh')
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
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
