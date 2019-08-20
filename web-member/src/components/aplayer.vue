<template>
    <div class="mt20 audio">
        
        <!-- 上传视频 -->
                    <Row class="up-video ml5" v-for="(item,index) in videoList" :key="index">
                        	<audio :src="item.url" width="100%" controls="controls"/>
                             <Row style="margin-left: -60px;margin-top: 20px;">
                             	<Col span="6">
                             		 <span>描述</span>
                             	</Col>
                             	<Col span="16">
                             		<Input type="textarea" @on-change="saveDescribe" v-model="item.describe" style="width: 300px;"/>
                             	</Col>
                             </Row>          
                    </Row>
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
                            style="margin-top: 30px;"
                            >
                        <div >
                            <Button type="primary">选择音频</Button>
                            <p style="color: red;">仅支持 mp3 格式</p>
                        </div>
                    </Upload>
                        
                    	
                    	
        <!-- 播放音频 -->
        <Modal v-model="AplayerShow" @on-ok="AplayerShow = false"  title="播放音频" width="600px" height="300px" :mask-closable="false">
            <a-player v-if="AplayerShow" :music="songs" ref="aplayer" @play="aplay"></a-player>
        </Modal>
    </div>
</template>

<script>
import VueAplayer from 'vue-aplayer'
import api from "~api";


export default {
  components: {
    "a-player": VueAplayer
  },
  data() {
    return {
      action: `${this.$url.upload}/upload/up`,
      aplayer:{},
      /*songs:{
	      title: 'Monody (Radio Edit)',
		  author: 'TheFatRat',
		  url: 'https://p2.music.126.net/aNplYKQGtlWgDIgqU1KI6Q==/18738976673665483.mp3',
		  pic: 'https://p3.music.126.net/2G40ihs8ysucvUI2GkeTlQ==/3262251007253950.jpg?param=130y130',
      },
      AplayerShow:false,*/
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
    this.aplayer = this.$refs.aplayer.control;
   },
  methods: {
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
      if (response.code === 500) {
        this.$Message.error("上传失败!");
      } else {
        this.$Message.success("上传成功!");
        this.videoList.push({url:'http:'+response.data.picName,describe:'',type:1})
        console.log("videoList",this.videoList)
        this.saveDescribe()
      }
    },
    //保存描述信息
  	saveDescribe(){
  		this.$emit('videoResult',this.videoList)
  	},
    // 删除视频
    handleRemove(file) {
      this.videoList.splice(this.videoList.indexOf(file), 1);
      this.saveDescribe()
    },
    // 视频大小限制
    handleMaxSize(file) {
     this.$Message.success("音频  " + file.name + " 过大，应不超过100M。");
    },
    // 视频格式限制
    handleFormatError(file) {
     this.$Message.success("音频 " + file.name + " 格式不正确，请选择 mp3 格式。")
    }
  }
};
</script>
<style scoped>

.pointer {
  cursor: pointer;
}
</style>
<style scoped lang="scss">

.audio .up-video {
  display: inline-block;
  width: 200px;
  height: 150px;
  text-align: center;
  border-radius: 4px;
  background: #fff;
  position: relative;
  margin-right: 100px;
  margin-left: 1px;
  vertical-align: top;
  &:hover {
    .demo-upload-list-cover {
      display: block;
    }
  }
  }
 .audio  .demo-upload-list-cover {
    height: 60px;
    line-height: 60px;
  }

.audio .drop-out {
  &:hover {
    .drop-in {
      display: block;
    }
    .share {
      display: block;
    }
  }
}
</style>