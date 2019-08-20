<template>
	<div class="weibo">
		<quill-editor ref="myWeiboEditor" v-model="contents" :options="editorOption" @change="onEditorChange($event)">
			>
			<div id="toolbar" slot="toolbar" class="wb-title">
				<button class="wb-button" type="button">
                    	<!-- <vui-emoji @select="selectIcon"></vui-emoji> -->
                    </button>
				<button type="button" class="wb-button" @click="upImg">图片</button>
				<button type="button" class="wb-button" @click="upImg">视频</button>
				<button class="wb-button" type="button">话题</button>
				<button class="wb-content" type="button">热门文章</button>
				<button class="wb-content" type="button">直播间</button>
			</div>
		</quill-editor>
		<Upload :show-upload-list="false" style="display: none;" ref="weiboUp" name="upfile" :format="accept" :max-size="maxsize" multiple type="drag" :on-exceeded-size="handleMaxSize" :on-success="handleSuccess" :action="action">
			<input :ref="uploadId" />
		</Upload>
		<div class="up-video1" v-for="(item,index) in videoList" :key="index">
			<video :src="item.url" width="300" height="112%" controls="controls"/>
			<!--<Icon type="play" class="video-play" @click.native="playVideo(item.url)"></Icon>
			<Icon type="ios-circle-outline" class="video-circle" @click.native="playVideo(item.url)"></Icon>--> 
			<Button type="default" shape="circle" icon="close-round" @click.native="handleRemove(item)" class="demo-upload-list-cover"></Button>
		</div>
		<!-- 播放视频 -->
		<Modal :transfer="false" v-model="playVideoShow" @on-ok="playVideoFolderOk()" @on-cancel="playVideoFolderCancel()" title="播放视频" width="600px" height="300px" :mask-closable="false">
			<d-player ref="playerWeibo" @play="play" :video="video" :loop="false"></d-player>
		</Modal>

	</div>
</template>

<script>
	import { quillEditor } from 'vue-quill-editor'
	// import vuiEmoji from './vuiEmoji'
	import VueDPlayer from "~components/vuedplayer";
	export default {
		name: 'quill-weibo',
		components: {
			quillEditor,
			// vuiEmoji,
			"d-player": VueDPlayer,
		},
		data: function() {
			return {
				action: `${this.$url.upload}/upload/up`,
				uploadList: [],
				editorOption: {
					placeholder:'请输入内容',
					modules: {
						toolbar: '#toolbar',
					}
				},
				player: {},
				video: {
					url: ""
				},
				videoList: [],
				result: [],
				playVideoShow: false,
			}
		},
		props: {
			accept: {
				type: Array
			},
			maxsize: {
				type: Number
			},
			content: {
				type: String,
				default:' '
			},
			uploadId: {
				type: String,
				default: 'weiboup'
			},
		},
		computed: {
			editor() {
				return this.$refs.myWeiboEditor.quill
			},
			contents: {
				get() {
					return this.content
				},
				set(value) {
					this.$emit('input', value)
				}
			}
		},
		mounted() {
			this.player = this.$refs.playerWeibo.dp;
		},
		methods: {
			play() {
				this.player.play();
			},
			playVideo(url) {
				this.player.video.src =  url;
				this.playVideoShow = true;
			},
			playVideoFolderOk() {
				this.playVideoShow = false;
			},
			playVideoFolderCancel() {
				this.playVideoShow = false;
			},
			upImg() {
				this.$refs[this.uploadId].click()
			},
			//获得表情
			selectIcon(val) {
				console.log(val)
				this.editor.insertEmbed(this.editor.getSelection().index, 'image', val)
			},

			//删除图片
			handleRemove(file) {
				// 从 upload 实例删除数据
				this.$refs.weiboUp.fileList.splice(this.videoList.indexOf(file), 1);
				this.$emit('videoResult', this.videoList)
			},
			//成功回调
			handleSuccess(response, file, fileList) {
				if(response.code == 500) {
					this.$Message.error('上传失败!')
				} else {
					this.$Message.success('上传成功!')
					if(response.data.status === 0) {
						this.videoList.push({
							url: 'http:' + response.data.picName,
							describe: '',
							type:0
						})
						this.$emit('videoResult', this.videoList)
						console.log('weibo', this.videoList)
					} else {
						//把图片插入指定的位置
						this.editor.insertEmbed(this.editor.getSelection().index, 'image', 'http://' + response.data.picName)
					}
				}
			},
			//文件太大，错误提示
			handleMaxSize(file) {
				this.$Notice.warning({
					title: 'Exceeding file size limit',
					desc: 'File  ' + file.name + ' is too large, no more than 100M.'
				});
				console.log('File  ' + file.name + ' is too large, no more than 100M.');
			},
			onEditorChange({
				editor,
				html,
				text
			}) {
				this.$emit('quilWeibo', html)
			}

		}
	}
</script>

<style>
	.weibo .quill-editor .ql-editor {
		height: 150px !important;
	}
	
	.weibo .quill-editor {
		border-top: 1px solid #ccc;
	}
	
	.weibo .quill-editor img {
		max-width: 100%;
	}
	
	.weibo .wb-title {
		position: absolute;
		top: 95%;
		border: none !important;
		font-size: 16px;
	}
	
	.weibo .wb-button {
		cursor: pointer;
		margin-right: 10px;
		width: 50px !important;
	}
	
	.weibo .wb-content {
		cursor: pointer;
		margin-right: 10px;
		width: 75px !important;
	}
</style>
<style>
	.weibo .video-play {
		position: absolute;
		top: 50%;
		left: 97%;
		transform: translate3d(-50%, -50%, 0);
		font-size: 34px;
		color: #fff;
	}
	
	.weibo .video-circle {
		position: absolute;
		top: 50%;
		left: 96%;
		transform: translate3d(-50%, -50%, 0);
		font-size: 55px;
		color: #fff;
	}
	
	.weibo .demo-upload-list-cover {
		background-color: #00bbff;
	}
</style>
<style scoped lang="scss">
	.weibo .up-video1 {
		display: inline-block;
		width: 150px;
		height: 150px;
		text-align: center;
		border-radius: 4px;
		background: #fff;
		position: relative;
		margin: 15px;
		vertical-align: top;
	}
</style>