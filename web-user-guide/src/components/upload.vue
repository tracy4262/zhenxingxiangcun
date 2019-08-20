<template>
	<div class="layout">

		<div>
			<div v-if="listShow">
				<template v-if="uploadList.length != 0">
					<div class="demo-upload-list" v-for="item in uploadList">
						<template v-if="item.status === 'finished'">
							<img :src="item.url">
							<div class="demo-upload-list-cover">
								<Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
							</div>
						</template>
					</div>
				</template>
				<!--
                	作者：786905664@qq.com
                	时间：2017-12-06
                	描述：回显显示
                -->
				<template v-else>
					<div class="demo-upload-list" v-if="defaultUploadList != ''">
						<template>
							<img :src="defaultUploadList">
						</template>
					</div>
				</template>
				
			</div>
			<Upload ref="upload" :show-upload-list="false"
								name="upfile"
								:max-size="maxsize"
								multiple 
								:on-exceeded-size="handleMaxSize"
								:on-success="handleSuccess"
								:action="action"
								:format="format"
								:on-format-error="handleFormatError"
								:before-upload="handleBeforeUpload"
								style="text-align:center;">
                           <input ref="uploadTemp" style="display: none;"></input>
			</Upload>
		</div>

		

	</div>
	</div>
</template>

<script>
	import $ from 'jquery'
	import api from '~api'
	export default {
		name:'upload',
		data() {
			return {
				action: `${this.$url.upload}/upload/up`,
				modal1: false,
				visible:false,
				src: '',
				imgs:'',
				size:0,
			}
		},
		props:{
			uploadList:{
				type:Array,
				default:function(){
					return []
				}
			},
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
			},
			listShow:{
				type:Boolean,
				default:true
			}
		},
		methods: {
			handleBeforeUpload(){
				this.$emit('before-upload',this.$refs.upload.fileList)
			},
			uploadPicture(){
				console.log("up",this)
				this.$refs.uploadTemp.click()
			},
			handleRemove(item) {
				// 从 upload 实例删除数据
				this.uploadList.splice(this.uploadList.indexOf(item), 1);
				this.$emit('imgs',[this.uploadList,this.uploadList.length])
			},
			handleSuccess(response, file, fileList) {
				console.log("返回图片")
				if(response.code==500){
					this.$Message.error('上传失败!')
				}else{
					this.$Message.success('上传成功!')
					var img =`http:${response.data.picName}`
					this.uploadList.push({status:'finished',url:img})
					this.$emit('imgs',[this.uploadList,this.uploadList.length])
				}

				console.log(response)
				console.log(fileList)
				console.log(file)
			},
			//文件太大，错误提示
			  handleMaxSize (file) {
				this.$Message.error(file.name+'太大，最大上传100M')
            },
            handleFormatError (file) {
               this.$Message.error(file.name+'格式不正确，只支持.jpg .png .jpeg .mp4 .avi .mkv .rmvb .kux') 
            },

		},
		mounted() {
			//this.uploadList = this.$refs.upload.fileList;
		}
	}
</script>

<style scoped>
	/*main样式开始*/

	.demo-upload-list {
		display: inline-block;
		width: 120px;
		height:120px;
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
	
	.demo-upload-list-cover i {
	    color: #fff;
	    font-size: 20px;
	    cursor: pointer;
	    position: absolute;
	    top: 40%;
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
<style lang="scss">
	.person-code-tabs {}
</style>