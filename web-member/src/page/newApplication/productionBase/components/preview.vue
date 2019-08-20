<template>
    <div>
        <Row type="flex" align="middle">
            <Col span="12"><div class="info-title">{{ item.title }}</div></Col>
            <Col span="12" class="tr"><Button type="text" @click="save" style="color: #00bb80;">保存</Button></Col>
        </Row>
        <Row class="mt10 mb40">
            <Input v-model="item.content" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        </Row>
    </div>
</template>
<script>
export default {
    props: {
        item: {
            type: Object
        }
    },
    data () {
        return {
        }
    },
    methods: {
        save () {
            console.log('item', this.item)
            this.$api.post('/member-reversion/productionBase/saveAllTextPreview', {
                textPreview: {
                    account: this.$user.loginAccount,
                    textPreview: this.item.content,
                    parentId: this.item.appId,
                    textPreviewId: this.item.id,
                    baseId: this.$route.query.id
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
