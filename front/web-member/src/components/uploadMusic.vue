<template>
    <div class="audio">
        <Upload ref="uploadAudio"
                :show-upload-list="false"
                name="upfile"
                :max-size="1024000"
                :on-success="handleSuccess"
                :on-exceeded-size="handleMaxSize"
                :on-format-error="handleFormatError"
                :format="['mp3']"
                multiple
                type="drag"
                :action="action"

        ><Button type="primary">选择音频</Button>
        </Upload>
        <div class="mt10">
            <Row type="flex" align="middle" v-for="(item,index) in videoList" :key="index" class="mb5">
                <Col span="7">
                    <p class="ell">
                        <Avatar size="small" icon="music-note" style="background-color:#00c587;vertical-align: middle;" />
                        {{item.musicName}}
                    </p>
                </Col>
                <Col span="13">
                    <Input  @on-change="saveDescribe" v-model="item.describe" style="width: 300px;"/>
                </Col>
                <Col span="3">
                    <p class="ell">{{item.musicSize}} M</p>
                </Col>
                <Col span="1">
                    <Icon type="close" @click.native="handleRemove(item)"></Icon>
                </Col>
            </Row>
        </div>

    </div>
</template>

<script>
    import api from "~api";
    import PublishUpload from "./publishUpload";
    export default {
        components: {
            PublishUpload
        },
        data() {
            return {
                action: `${this.$url.upload}/upload/up`,
                videoList:[]
            };
        },
        props:{
            songs:{
                type:Array,
                default() {
                    return []
                }
            },
            AplayerShow: {
                type: Boolean,
                default: false
            },
        },
        mounted() {
        },
        methods: {
            reset () {
                this.videoList = []
                this.saveDescribe()
            },
            aplay() {
                this.aplayer.play();
            },
            aplayAudio(url){
                this.aplayer.music.url = url;
                this.AplayerShow = true;
            },
            uploadAudio(){
                this.$api.post(this.action)
            },
            // 上传视频
            handleSuccess(response, file, fileList) {
                let musicName = file.name
                let musicSize =  (file.size/1024/1024).toFixed(2)
                if (response.code === 500) {
                    this.$Message.error("上传失败!");
                } else {
                    this.$Message.success("上传成功!");
                    console.log(this.videoList)
                    this.videoList.push({url:'http:'+response.data.picName,describe:'',type:1,musicName:musicName,musicSize:musicSize})
                    this.saveDescribe()
                }
            },
            //保存描述信息
            saveDescribe(){
                this.$emit('videoResult',this.videoList)
            },
            // 删除
            handleRemove(file) {
                this.videoList.splice(this.videoList.indexOf(file), 1);
                this.saveDescribe()
            },
            //  大小限制
            handleMaxSize(file) {
                this.$Message.success("音频  " + file.name + " 过大，应不超过100M。");
            },
            //  格式限制
            handleFormatError(file) {
                this.$Message.success("音频 " + file.name + " 格式不正确，请选择 mp3 格式。")
            }
        }
    };
</script>
<style lang="scss">
    .audio{
        .ivu-upload-drag{
            text-align: left;
        }
    }
</style>