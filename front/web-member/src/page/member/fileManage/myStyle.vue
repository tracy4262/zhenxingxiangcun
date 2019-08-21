<template>
    <div>
        <Tabs style="margin-left: 15px;">
            <Tab-pane label="相册">
                <c-picture  @showPic="showPic22" @index="index22"></c-picture>
            </Tab-pane>
            <Tab-pane label="视频">
                <c-video></c-video>
            </Tab-pane>
            <Tab-pane label="文档">
                <c-file></c-file>
            </Tab-pane>
        </Tabs>
        <!-- 照片预览 -->
        <Preview :list="previewList" ref="preview" :options="options" />
    </div>
</template>
<script>
import File from './file'
import Video from './video'
import Picture from './picture'
import Preview from '~components/preview'
import VueDPlayer from '~components/vuedplayer'

export default {

    components: {
        'd-player': VueDPlayer,
        'c-picture': Picture,
        'c-video': Video,
        'c-file': File,
        Preview
    },
    data() {
        return {
            previewList: [],
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
        /*
        *   TODO 请求接口获取用户所有的相册数据push到albumList
        * */
    },
    methods: {
        showPic22 (previewList) {
            this.previewList = previewList
        },
        index22 (index) {
            this.$refs.preview.open(index, '.preview-img')
        }
    }
}
</script>