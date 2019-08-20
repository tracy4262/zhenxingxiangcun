<template>
    <div class="pub-video" >
        <!-- 上传视频 -->
        <Upload
            :show-upload-list="false"
            name="upfile"
            :max-size="1024000"
            :on-success="handleSuccess"
            :on-exceeded-size="handleMaxSize"
            :on-format-error="handleFormatError"
            :format="['avi','mp4','mkv','rmvb','kux', 'ogg']"
            multiple
            type="drag"
            class="mb10"
            :action="action"
        >       <Button type="primary">选择视频</Button>

        </Upload>
        <swiper :options="swiperOption">
            <swiper-slide v-for="(item,index) in videoList" :key="index">
                <Card :padding="5">
                    <div style="position:relative;">
                        <Icon type="close-round" @click.native="handleRemove(item)" class="close"></Icon>
                        <p  class="ell video-name">{{item.musicName}}</p>
                        <video :src="item.url" width="100%" />
                    </div>
                    <Input type="textarea" placeholder="描述" @on-change="saveDescribe" v-model="item.describe" />
                </Card>
            </swiper-slide>
            <div class="swiper-pagination" slot="pagination"></div>
        </swiper>


    </div>
</template>

<script>
    import { swiper, swiperSlide } from 'vue-awesome-swiper'
import api from "~api";


export default {
    components:{
        swiper,
        swiperSlide
    },
  data() {
    return {
        swiperOption: {
            slidesPerView: 2,
            spaceBetween: 20,
            pagination: {
                el: '.swiper-pagination',
                clickable: true
            }
        },
      action: `${this.$url.upload}/upload/up`,
      videoList: [],
      result:[]
    };
  },
   mounted() {
   },
  methods: {
    reset () {
        this.videoList = []
        this.saveDescribe()
    },
  	//保存描述信息
  	saveDescribe(){
  		this.$emit('saveDescribe',this.videoList)
  	},
  	//点击上传
  	clickUp(){
  		this.$refs.uploadButton.click()
  	},
  	// 上传视频
    handleSuccess(response, file, fileList) {
      if (response.code === 500) {
        this.$Message.error("上传失败!");
      } else {
        this.$Message.success("上传成功!");
        this.videoList.push({url:'http:'+response.data.picName,describe:'',type:0,musicName:file.name})
        this.saveDescribe()
      }
    },
    // 删除视频
    handleRemove(item) {
      this.videoList.splice(this.videoList.indexOf(item), 1);
      this.saveDescribe()
    },
    // 视频大小限制
    handleMaxSize(file) {
      this.$Message.success("视频  " + file.name + " 过长，应不超过100M。");
    },
    // 视频格式限制
    handleFormatError(file) {
      this.$Message.success("视频 " + file.name + " 格式不正确，请选择avi、mp4、mkv、rmvb、kux格式。")
    }
  }
};
</script>

<style lang="scss">
.pub-video {
    .ivu-upload-drag{
        text-align: left;
    }
    .swiper-slide{
        margin-bottom:30px;
    }
    .swiper-pagination{
        bottom: 0 !important;
    }
    .close{
        position: absolute;
        right: 8px;
        top: 13px;
        color: #fff;
        z-index: 999;
        cursor: pointer;
    }
    .video-name{
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        padding: 5px 25px 5px 5px;
        background: rgba(0,0,0,.5);
        color: #fff;
    }
}
.video-add{
    position: relative;
    left: -276px;
}

</style>