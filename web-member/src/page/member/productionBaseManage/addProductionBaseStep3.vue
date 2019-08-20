<template>
    <div>
        <c-picture ref="base" :pid="pid" @showPic="showPic22" @index="index22" @urls="getUrls" style="margin-left: 40px; margin-top: 20px;"></c-picture>
        <div style="margin-top: 30px; text-align: center;">
            <Button type="default" @click="last">上一步</Button>&nbsp;&nbsp;&nbsp;&nbsp;
            <Button type="primary" @click="next">下一步</Button>&nbsp;&nbsp;&nbsp;&nbsp;
            <Button type="default" @click="exit">退出</Button>
        </div>
        <!-- 照片预览 -->
        <Preview :list="previewList" ref="preview" :options="options" />
    </div>
</template>

<script>
    // import picture from '../../profile/components/picture'
    import picture from './picture'
    import Preview from '~components/preview'

    export default {
        components:{
           'c-picture': picture,
           Preview
        },
        data() {
            return {
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                previewList: [],
                urls: [],
                pid: '',
                options: {
                    //  动画显示
                    showHideOpacity: true,
                    // 背景透明
                    bgOpacity: 0.8
                    // 显示第几张
                    // index: 2
                }
            }
        },
        created () {
            if (this.$route.query.id !== undefined && this.$route.query.id !== '') {
                this.pid = this.$route.query.id
            }
        },
        methods: {
            last () {
                let lastStep = 1
                this.$emit('last', lastStep)
                this.$parent.$router.push({
                    path: '/member/addProductionBase/addProductionBaseStep2',
                    query: {
                        id: this.$route.query.id
                    }
                })
            },
            next () {
                let nextStep = 3
                this.$emit('next', nextStep)
                let data = {
                    id: this.$route.query.id,
                    data: {
                        urls: this.urls
                    }
                }
                let _that = this
                this.$api.post('/member/product-base/media-library-detail-save', data).then(response => {
                    console.log('response', response)
                    if (response.code === 200) {
                        _that.$parent.$router.push({
                            path: '/member/addProductionBase/addProductionBaseStep4',
                            query: {
                                id: _that.$route.query.id
                            }
                        })
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            exit () {
                this.$router.push({
                    path: '/member/productionBaseList',
                    query: {
                        uid: this.loginuserinfo.loginAccount
                    }
                })
            },
            showPic22 (previewList) {
                this.previewList = previewList
            },
            index22 (index) {
                this.$refs.preview.open(index, '.preview-img')
            },
            getUrls (urls) {
                this.urls = urls
                console.log('urls', urls)
            }
        }
    }
</script>
<style scoped>
</style>