<template>
	<div class="weibo">
		<quill-editor  :ref="myQuillEditor" v-model="contents" :options="options"
		    @change="onEditorChange($event)">
		</quill-editor>	
		<Upload :show-upload-list="false"
			style="display: none;"
			ref="up"
			name="upfile"
			:format="accept"
			:max-size="maxsize"
			 multiple type="drag"
			:on-exceeded-size="handleMaxSize"
			:on-success="handleSuccess"
			:action="action">
				<input :id="uploadId" />
		</Upload>
		
	</div>
</template>

<script>
	import { quillEditor } from 'vue-quill-editor'
	// import vuiEmoji from './vuiEmoji'
	import $ from 'jquery'
	export default {
		name:'quill',
		components: {
			quillEditor,
			// vuiEmoji
		},
		data() {
			return {
				action: `${this.$url.upload}/upload/up`,
				visible: false,
				src: '',
				emoji:'',
				uploadList: [],
				options: {
                    placeholder: '请输入内容',
					modules: {
						toolbar:[
					['bold', 'italic', 'underline', 'strike'],
					[{ 'size': ['small', false, 'large', 'huge'] }, 
					{ 'align': [] }, { 'color': [] }, { 'background': [] }],
					['link', 'image', 'video']
						]
					}
				}
			}
		},
		props:{
			myQuillEditor:{
				type:String,
				default:'myQuillEditor'
			},
			accept:{
				type:Array
			},
			maxsize:{
				type:Number
			},
			uploadId:{
				type:String,
				default:'up'
			},
			content: {
                type: String
			},
			paneType:{
				type:String
			}
		},
		computed: {
			editor() {
				return this.$refs[this.myQuillEditor].quill
			},
			contents: {
				get () {
					return this.content
				},
				set (value) {
					this.$emit('input', value)
				}
			}
		},
		mounted() {	
			this.uploadList = this.$refs.up.fileList;  
            this.$refs[this.myQuillEditor].quill.getModule('toolbar').addHandler('image', this.imgClick)  
            this.$refs[this.myQuillEditor].quill.getModule('toolbar').addHandler('video', this.imgClick) 
		},

		methods: {
			imgClick(){
				var up=this.uploadId
				console.log('up',up)
				$('#'+up).click()
			},
			//删除图片
			handleRemove(file) {
				// 从 upload 实例删除数据
				const fileList = this.$refs.up.fileList;
				this.$refs.up.fileList.splice(fileList.indexOf(file), 1);
			},
			//成功回调
			handleSuccess(response, file, fileList) {
				console.log("返回图片")
				if(response.code == 500) {
					this.$Message.error('上传失败!')
				} else {
					this.$Message.success('上传成功!')
					
					//把图片插入指定的位置
					 this.editor.insertEmbed(this.editor.getSelection().index, 'image', 'http:'+response.data.picName)
				}
			},
			//文件太大，错误提示
			  handleMaxSize (file) {
                this.$Notice.warning({
                    title: 'Exceeding file size limit',
                    desc: 'File  ' + file.name + ' is too large, no more than 100M.'
                });
                console.log('File  ' + file.name + ' is too large, no more than 100M.');
            },

			onEditorChange({editor,html,text}) {
				console.log("quil",html)
				this.$emit('quilCon',html)
			}
		}
	};
</script>
<style>
	/*.info {
		border-radius: 10px;
		line-height: 20px;
		padding: 10px;
		margin: 10px;
		background-color: #ffffff;
	}*/
</style>
<!--
	作者：786905664@qq.com
	时间：2017-12-30
	描述：微博样式
-->
<style >
	.weibo .quill-editor img {
		max-width: 100%;
	}
	.weibo .wb-title{
		position: absolute;
		top: 150px;
		border: none !important;
	}
	.weibo .wb-button{
		margin-right: 10px;
		width: 50px !important;
	}
	.weibo .wb-content{
		margin-right: 20px;
		width: 75px !important;
	}
</style>
<style scoped>
.demo-upload-list {
		display: inline-block;
		width: 60px;
		height: 60px;
		text-align: center;
		line-height: 60px;
		border: 1px solid transparent;
		border-radius: 4px;
		overflow: hidden;
		background: #fff;
		position: relative;
		box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
		margin-right: 4px;
	}

	.demo-upload-list img {
		width: 100%;
		height: 100%;
	}

	.demo-upload-list:hover .demo-upload-list-cover {
		display: block;
	}

	.demo-upload-list-cover {
		color: #fff;
		font-size: 16px;
		display: none;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		background: rgba(0, 0, 0, .6);
	}


</style>
