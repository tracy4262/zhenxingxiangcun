<!--
    作者：chenqim
    时间：2019-01-07
    描述：生产基地基地相册编辑页
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <Title title="基地相册"></Title>
        <Row>
            <photo-select ref="photoSelect" class="mt20 ml20" style="min-height: 300px;" @get-data="getData"></photo-select>
        </Row>
        <div class="tc mt40">
            <Button type="default" @click="quit" style="width: 105px;">退出</Button>
            <Button type="primary" @click="last" style="width: 105px;" class="ml10">上一步</Button>
            <Button type="primary" @click="next" style="width: 105px;" class="ml10">保存并下一步</Button>
        </div>
    </div>
</template>
<script>
import Title from './title2'
import photoSelect from './photoSelect'
export default {
    name: 'photoInfo',
    components: {
        Title,
        photoSelect
    },
    data () {
        return {
            baseId: '',
            data: []
        }
    },
    created () {
        this.baseId = this.$route.query.id
        this.initPhotoInfo()
    },
    methods: {
        initPhotoInfo () {
            this.$api.post("/member-reversion/productionBase/photoList", {
                account: this.$user.loginAccount,
                baseId: this.baseId
            }).then(response => {
                if (response.code === 200) {
                    this.data = response.data.list
                    this.$refs.photoSelect.data = response.data.list
                    this.$refs.photoSelect.nickName = response.data.nickName
                }
            })
        },
        quit () {
            this.$router.push('/member/productionBaseList')
        },
        last () {
            this.$emit('last')
        },
        next () {
            this.$api.post("/member-reversion/productionBase/saveBasePhoto", {
                account: this.$user.loginAccount,
                baseId: this.baseId,
                list: this.data
            }).then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功！')
                    this.$emit('next')
                }
            })
        },
        getData (value) {
            this.data = value
            console.log('value', value)
        }
    }
}
</script>
<style lang="scss" scoped>

</style>
