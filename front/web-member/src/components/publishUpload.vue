<template>
    <div class="layout">

        <div class="photo-list">
            <Upload ref="upload" :show-upload-list="false"
                    class="item"
                    name="upfile"
                    :max-size="maxsize"
                    multiple type="drag"
                    :on-exceeded-size="handleMaxSize"
                    :on-success="handleSuccess"
                    :action="action"
                    :format="['jpg','png']"
                    :on-format-error="handleFormatError"
                    style="text-align:center;">
                <div class="upload-add-div">
                    <Icon type="plus-circled"  color="#00c587" :size="32"></Icon>
                    <p class="mb5">点击添加图片</p>
                    <p class="t-grey">支持jpg/png格式</p>
                    <p class="t-grey">不超过2M</p>
                </div>
            </Upload>
            <div class="upload-add-div photo-selector" @click="handlePhotoSelectorModal('identityPhoto')">
                <Icon type="plus-circled"  color="#00c587" :size="32"></Icon>
                <p class="mb5">从文件管理导入</p>
                <p class="t-grey">支持jpg/png格式</p>
                <p class="t-grey">不超过2M</p>
            </div>
            <template v-if="resultPicList != ''">
                <template v-for="(item,index) in resultPicList">
                    <div class="item img-wrap">
                        <img :src="item">
                        <Icon type="close" @click.native="handleRemove(item)"></Icon>
                    </div>
                </template>
            </template>
        </div>
         <!-- 照片选择 -->
        <photoSelector 
        ref="identityPhoto" 
        :photoAlbum="l"
        @on-change="albumChange"
        @on-get-result="handleGetPhotoResult"
        :resultDatas="p"
        />
    </div>
</template>

<script>
    import photoSelector from '~components/photoSelector'
    export default {
        name:'publish-upload',
        components:{
            photoSelector
        },
        data() {
            return {
                action: `${this.$url.upload}/upload/up`,
                uploadList: [],
                modal1: false,
                visible:false,
                src: '',
                imgs:'',
                size:0,
                value1:0,
                resultPicList:[],
                l: [],
                p: []
            }
        },
        props:{
            maxsize:{
                type:Number,
                default:102400
            },
            imgNum:{
                type:Number,
                default:1
            },
            buttonCon:{
                type:String,
                default:'选择图片'
            },
            type:{
                type:String,
                default:'upload'
            },
            format:['jpg','png','avi','mp4','mkv','rmvb','kux','mp3'],
            defaultUploadList:{
                type:String,
                default:''
            }
        }, 
        created(){
            // 从我的风采导入图片 默认显示第一个相册
            this.getAlbum('identityPhoto')
        },
        methods: {
            handleRemove(item) {
                this.resultPicList.splice(this.resultPicList.indexOf(item), 1)
                this.$refs.upload.fileList.splice(this.resultPicList.indexOf(item), 1)
                let imgs = ''
                if (this.resultPicList.length > 0) {
                    this.resultPicList.forEach(i => {
                        imgs += `<img src=`+'http:' + i + `>`
                    })
                }
                this.$emit('on-imgs', imgs)
            },
            handleSuccess(response, file, fileList) {
                let pic = response.data.picName
                this.resultPicList.push(pic)
                let imgs = ''
                if (this.resultPicList.length > 0) {
                    this.resultPicList.forEach(i => {
                        imgs += `<img src=` +'http:'+ i + `>`
                    })
                }
                this.$emit('on-imgs', imgs)
            },
            //文件太大，错误提示
            handleMaxSize (file) {
                this.$Message.error(file.name+'太大，最大上传100M')
            },
            handleFormatError (file) {
                this.$Message.error(file.name+'格式不正确，只支持jpg,png格式')
            },
             // 从我的风采导入图片
            handlePhotoSelectorModal (name) {
                this.$refs[name].photoSelectorShow = true
                this.$refs[name].choosed = []
            },
            albumChange (value) {
                this.p = []
                this.getPhoto({
                    mediaId: value,
                    pageNum: 1,
                    pageSize: 1000
                })
            },
            getAlbum (name) {
                this.$api.post('/member/product-base/media-library-query-all', {
                    account: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
                    mediaType: 1
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.length !== 0) {
                            this.$refs[name].album = response.data[0].mediaId
                        }
                        response.data.forEach(element => {
                            this.l.push({
                                label: element.mediaName,
                                value: element.mediaId
                            })
                        })
                    }
                }).catch(error => {
                    this.$Message.error(error)
                })
            },
            getPhoto (params) {
                this.$api.post("/member/product-base/media-library-detail-query-list", params)
                    .then(response => {
                    if (response.code === 200) {
                        for (let i = 0; i < response.data.list.length; i++) {
                            this.p.push({
                                id: response.data.list[i].id,
                                src: response.data.list[i].mediaUrl,
                                disable: false
                            })
                        }
                    }
                })
            },
            handleGetPhotoResult (result) {
                // 用于限制最大可上传照片的张数
                if (this.$refs.upload.fileList.length + result.length > 100) {
                    this.$Notice.warning({
                        title: '最多上传100张'
                    })
                } else {
                    let imgs = ''
                    result.forEach(element => {
                        this.$refs.upload.fileList.push({
                            response: {
                                data: {
                                    picName: element
                                }
                            },
                            status: 'finished'
                        })
                        this.resultPicList.push(element)                        
                    })
                    if (this.resultPicList.length > 0) {
                        this.resultPicList.forEach(i => {
                            imgs += `<img src=` +'http:'+ i + `>`
                        })
                    }
                    this.$emit('on-imgs', imgs)
                }
            },
            handleBeforeUpload () {
                const check = this.$refs.upload.fileList.length < 100              
                if (!check) {
                    this.$Notice.warning({
                        title: '最多上传100张'
                    })
                }
                return check
            },
        }
    }
</script>

<style lang="scss" scoped>
    .photo-list{
        .item {
            display: inline-block;
            width: 140px;
            height: 140px;
            position: relative;
        }
        .img-wrap{
            vertical-align: top;
            &:hover{
                &:after{
                    content: '';
                }
                .ivu-icon{
                    display: block;
                    z-index: 999;
                }
            }
            &:after{
                position: absolute;
                top:0;
                right: 0;
                bottom: 0;
                left: 0;
                background: rgba(0,0,0,.3);
            }
            img{
                height: 100%;
                width: 100%;
            }
            .ivu-icon{
                display: none;
                color: #fff;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate3d(-50%,-50%,0);
                font-size: 24px;
                cursor: pointer;

            }
        }
        .upload-add-div {
            border: 1px;
            height: 140px;
            width: 140px;
            background: #F6F6F6;
        }
         .photo-selector {
            display: inline-block;
            position: relative;
            text-align: center;
            border: 1px #dddee1 dashed;
            cursor: pointer;
        }
        .photo-selector:hover {
            border: 1px #00c587 dashed;
        }
    }


</style>