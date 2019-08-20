<template>
	<div class="web-site">
			<Form :model="website" :rules="ruleInline" ref="website" label-position="right" :label-width="100" style="margin-top: 50px;font-size: 14px;">
				<Form-item label="网站名称" prop="name">
					<Input type="text" v-model="website.name" style="width: 250px;" :maxlength='20'/>
					<Checkbox v-model="website.status" style="margin-left: 50px;color: #828c99;">网站名称是否显示</Checkbox>
				</Form-item>
				<Form-item label="网站标志" prop="logo">
					<Button type="primary" @click="uploadPicture" class="mr10" :disabled="logodis === 1">上传图片</Button>
					<Button type="primary" @click="handlePhotoSelectorModal('identityPhoto','logo')" class="mr10" :disabled="logodis === 1">从文件管理导入</Button>
					<span style="color: #9EA7B4;font-family: '微软雅黑';font-size: 14px;">支持JPEG、PNG、JPG格式，大小不超过200K</span>
						<Upload ref="uploadLogoList" :show-upload-list="false"
								name="upfile"
								:max-size="logoMaxsize"
								:on-exceeded-size="handleMaxSize"
								:on-success="handleSuccess"
								:action="action"
								:format="['jpg','png']"
								:on-format-error="handleFormatError"
								style="text-align:center;">
                           <input ref="uploadLogo" style="display: none;"/>
						</Upload>
						<img src="http://www.ns51.cn:8999/00/42/wKgHPVppeweABedNAAAIFOsQ8YU636.jpg" width="58" v-if="website.logo === ''"/>
						<div class="demo-upload-list" v-else>
							<template>
								<img :src="website.logo">
								<div class="demo-upload-list-cover">
									<Icon type="ios-trash-outline" @click.native="handleRemoveLogo()"></Icon>
								</div>
							</template>
						</div>
						<!-- <upload @imgs='getLogo' :defaultUploadList="website.logo" ref="uploadWeb" class="mt10"></upload> -->
				</Form-item>
				<Form-item label="上传横幅" prop="banner" >
					<Button type="primary" @click="uploadPicture1" class="mr10" :disabled="bannerdis === 1">上传图片</Button>
					<Button type="primary" @click="handlePhotoSelectorModal('identityPhoto','banner')" class="mr10" :disabled="bannerdis === 1">从文件管理导入</Button>
					<span style="color: #9EA7B4;font-family: '微软雅黑';font-size: 14px;">支持JPEG、PNG、JPG格式，大小不超过200K，建议文件尺寸1920PX*600PX</span>
					<Upload ref="uploadBannerList" :show-upload-list="false"
								name="upfile"
								:max-size="bannerMaxsize"
								:on-exceeded-size="handleMaxSize"
								:on-success="bannerHandleSuccess"
								:action="action"
								:format="['jpg','png']"
								:on-format-error="handleFormatError"
								:before-upload="handleBeforeUpload"
								style="text-align:center;">
                           <input ref="uploadBanner" style="display: none;"/>
						</Upload>
					
					
					<!-- <upload @imgs='getBanner' :uploadList="bannerList" :defaultUploadList="website.banner" :listShow="false" ref="uploadWeb1" class="mt10"></upload> -->
					<img src="http://www.ns51.cn:8999/00/42/wKgHPVpph_GAPj-qAAAUwH1g5Dc784.jpg" width="500" height="157" v-if="bannerList.length === 0"/>
					<div class="banner-upload-list" v-for="(item,index) in bannerList" :key="index" v-else>
						<template v-if="item.status === 'finished'">
							<img  width="500" height="157" :src="item.url">
							<div class="demo-upload-list-cover">
								<Icon type="ios-trash-outline" @click.native="handleRemove(index)"></Icon>
							</div>
						</template>
						<template v-else>
							<Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
						</template>
					</div>
					<!-- <swiper :options="swiperOption" class="web-site-swiper" v-if="bannerList.length">
			            <swiper-slide v-for="(item,index) in bannerList" :key="index">
				            <template v-if="item.status === 'finished'">
								 <Avatar class="close" style="background-color: #87d068" icon="close-round" @click.native="handleRemove(index)"/>
				                 <img :src="item.url" width="500" height="157"/>
							</template>
			            </swiper-slide>
			          <div class="swiper-pagination" width="100%" slot="pagination" ></div>
			        </swiper> -->
				</Form-item>
				<Form-item label="网站简介" prop="summary">
					<Input type="textarea" v-model="website.summary" :maxlength='40' placeholder="最多输入40字"/>
				</Form-item>
				<Form-item label="网站介绍" prop="introduce">
					<vuequilleditor ref="abc" :content="website.introduce" @quilCon="getCon" :uploadId='refid' :myQuillEditor="myQuillEditor">
					</vuequilleditor>
				</Form-item>
			</Form>
		<div class="footer-btn">
			<i-button type="primary" @click="saveWebsite" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
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
	import upload from '~components/upload'
	import vuequilleditor from '~components/vuequilEditor'
	import axios from 'axios'
	import { swiper, swiperSlide } from 'vue-awesome-swiper'
    import photoSelector from '~components/photoSelector'
	export default {
		name: 'websitename',
		components: {
			upload,
			vuequilleditor,
			swiper,
        	swiperSlide,
			photoSelector
		},
		data() {
			return {
				swiperOption: {
		            pagination: {
		                el: '.swiper-pagination',
		                clickable: true
		            }
		        },
				value1:0,
				logodis:0,
				bannerdis:0,
				current: 0,
				bannerList:[],
				action: `${this.$url.upload}/upload/up`,
				loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
				website: {
					name: '',
					status: true,
					position: '居左',
					logo: '',
					banner: '',
					summary: '',
					introduce: '',
					template: '',
					type:0,
					modular: ''
				},
				uploadList: '',
				contentLogo: '上传图片',
				contentBanner: '选择文件',
				refid: 'website',
				myQuillEditor: 'webquill',
				logoMaxsize:200,
				bannerMaxsize:1024,
				maxsize: 204800,
				ruleInline: {
					name: [{
						required: true,
						message: '名称不能为空',
						trigger: 'blur'
					}],
					summary: [{
						required: true,
						message: '网站简介不能为空',
						trigger: 'blur'
					}, ],
					introduce: [{
						required: true,
						message: '网站内容不能为空',
						trigger: 'blur'
					}]
				},
                l: [],
                p: [],
				type:'',
                contract1:''
			}
		},
		watch: {
			/*website: {
				handler(curVal, oldVal) {
				},
				deep: true
			}*/

		},
		created: function() {
			this.$parent.current = 0
			this.echo()
			// 从我的风采导入图片 默认显示第一个相册
            this.getAlbum('identityPhoto')
			this.showContact()
			// 回显选中模版
			this.$api.get('/member/website/find/' + this.loginuserinfo.loginAccount).then(response=>{
				if (response.code === 200 && response.data) {
					this.website.modular = response.data.modular
				}
			})
		},
		methods: {
			//文件太大，错误提示
			handleMaxSize (file) {
				this.$Message.error(file.name+'太大')
            },
			//文件太大的提示
			handleFormatError (file) {
               this.$Message.error(file.name+'格式不正确，只支持.jpg .png .jpeg .mp4 .avi .mkv .rmvb .kux') 
            },
			//上传成功的回调
			handleSuccess(response, file, fileList) {
				console.log("返回图片")
				if(response.code==500){
					this.$Message.error('上传失败!')
				}else{
					this.$Message.success('上传成功!')
					var img =`http:${response.data.picName}`
					this.website.logo = img
					this.logodis = 1
				}
			},
			//网站横幅上传成功的回调
			bannerHandleSuccess(response, file, fileList){
				if(response.code==500){
					this.$Message.error('上传失败!')
				}else{
					if(this.bannerList.length <= 1){
						this.$Message.success('上传成功!')					
						var img =`http:${response.data.picName}`
						this.bannerList.push({status:'finished',url:img})
						this.bannerdis =  this.bannerList.length 
						var arr = []
						this.bannerList.forEach(item => {
							arr.push(item.url)
						})
						this.website.banner = arr.join(' ')
					}else{
						this.$Notice.warning({
							title: '网站横幅最多上传1张'
						});
					}
				}
			},
			//网站横幅上传之前的回调
			handleBeforeUpload(){
				console.log(this.bannerList.length)
				if(this.bannerList.length >= 1){
					this.$Notice.warning({
                        title: '网站横幅最多上传1张'
					});
					return false
				}
			},
			//删除照片
			handleRemoveLogo() {
				this.website.logo = ''
				this.logodis = 0
			},
			handleRemove(index){
				this.bannerList.splice(index, 1);
				this.bannerdis =  this.bannerList.length
				this.website.banner = ''
			},
			 // 从我的风采导入图片logo
            handlePhotoSelectorModal (name,type) {
				this.type = type
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
                    console.log('res', response)
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
                    console.log(error)
                })
            },
            getPhoto (params) {
                this.$api.post("/member/product-base/media-library-detail-query-list", params)
                    .then(response => {
                    console.log("res", response)
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
                console.log('photo', result)
                // 用于限制最大可上传照片的张数
				if(this.type === 'logo'){
					if (this.website.logo || result.length > 1) {
						this.$Notice.warning({
							title: '网站标志只能上传一张'
						})
					} else {
						result.forEach(element => {
							this.website.logo = 'http:'+element                      
						})
						this.logodis = 1                 
					}					
				}else if(this.type === 'banner'){
					if (this.bannerList.length + result.length > 1) {
						this.$Notice.warning({
							title: '横幅最多上传1张'
						})
					} else {
						let imgs = ''
						result.forEach(element => {
							var img =`http:${element}`
							this.bannerList.push({status:'finished',url:img})   
						})  
						this.bannerdis =  this.bannerList.length
						var arr = []
						this.bannerList.forEach(item => {
							arr.push(item.url)
						})
						this.website.banner = arr.join(' ')
					}	
				}
            },			
			uploadPicture(){
				this.$refs.uploadLogo.click()
			},
			uploadPicture1(){
				this.$refs.uploadBanner.click()
			},
			preStep() {
				this.$parent.$parent.$parent.$router.go(-1)
			},
			pass() {
				let type = this.$route.meta.type
				if(1 === type) {
					this.$parent.$parent.$parent.gotoPathSec(37)
				} else {
					this.$parent.$parent.$parent.gotoPath(37)
				}
			},
			//网站设置
			saveWebsite() {
                this.$refs.website.validate((valid) => {
                    if (valid) {
						let data = {
							name: this.website.name,
							position: this.website.position,
							logo: this.website.logo,
							status: this.website.status,
							banner: this.website.banner,
							summary: this.website.summary,
							introduce: this.website.introduce,
							template: this.website.template,
							step: this.$route.path,
							type:this.website.type,
							modular: this.website.modular,
							websiteStep: '1'
						}
						console.log('data', data)
                        this.$api.post('/member/website/insert', data).then(response => {
							console.log('response', response)
                            if (response.code === 200) {
								this.$Message.success('设置成功!')
                                this.pass()
                            } else {
                                this.$Message.error('设置失败！')
                            }
                        })
                    }
                })
			},
			getCon(msg) {
				this.website.introduce = msg
			},
			echo() {
				var loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
				if(loginuserinfo === null || loginuserinfo === undefined) {
					return
				}else{
					this.$api.get(`/member/website/findByAccount/${loginuserinfo.loginAccount}`)
					.then(response => {
						console.log('response', response)
						if(response.code === 200 && response.data) {
							let websettings = response.data
							this.website.name = websettings.name
							this.website.position = websettings.position
							this.website.summary = websettings.summary
							if(websettings.banner){
								this.website.banner=websettings.banner
								let arr  = websettings.banner.split(' ')
								arr.forEach(e=>{
									this.bannerList.push({
										status:"finished",
										url:e,
									})
								})
								this.bannerdis = arr.length
							}
							if(websettings.logo){
								this.website.logo = websettings.logo
								this.logodis = 1
							}
							if(websettings.introduce){
								this.website.introduce = websettings.introduce
							}else{
								this.checkIntroduce()
							}
							
							if(websettings.status === 'true') {
								this.website.status = true
							} else {
								this.website.status = false
							}
						}else{
                            this.checkIntroduce()
						}
					})
				}
				
			},
            //获得网络信息
            showContact() {
                this.$api.get('/member/userFullInfo/findContact')
                    .then(response => {
                        if(response.data) {

                            console.log("信息展示2", response.data);
                            var res = response.data
							if(res.qq){
                                this.contract1 = 'QQ号码:' + res.qq
							}
                            if(res.email){
                                this.contract1 += ',邮箱:' + res.email
                            }
                            if(res.domain){
                                this.contract1 += ',域名：' + res.domain
                            }
                        }

                    })
            },
			//查询介绍
			checkIntroduce(){
				let type=this.loginuserinfo.userType
				let account=this.loginuserinfo.loginAccount
				if(!this.website.introduce){
					if(type === 4 || type === 0){//专家、个人
						this.$api.get('/member/userFullInfo/findUserFullInfo')
		                    .then(response => {
								console.log('res222', response)
		                        if (response.data) {
									this.website.introduce = ''
									if (response.data && response.data.contract1 && response.data.contract1 !== '') {
										this.website.introduce += '隐私信息：'+response.data.contract1 + '<br/>'
									}
									if (this.contract1 && this.contract1 !== '') {
										this.website.introduce += '网络信息：'+this.contract1 + '<br/>'
									}
									if (response.data && response.data.education && response.data.education !== '') {
										this.website.introduce += '教育信息：'+response.data.education + '<br/>'
									}
									if (response.data && response.data.work && response.data.work !== '') {
										this.website.introduce += '工作经历：'+response.data.work + '<br/>'
									}
									if (response.data && response.data.policial1 && response.data.policial1 !== '') {
										this.website.introduce += '政治面貌：'+response.data.policial1 + '<br/>'
									}
									if (response.data && response.data.religion1 && response.data.religion1 !== '') {
										this.website.introduce += '宗教信仰：'+response.data.religion1
									}
		                        }
		                    })
					}else if(type === 1){//企业
						this.$api.get('/member/corpInfo/findByAccount/'+account).then(res=>{
							if(res.data){
								this.website.introduce=res.data.companyProfile
							}
						})
					}
				}
			}
		}
	}
</script>
<style lang="scss" scoped>
	.web-site-swiper{
	    .close{
	        position: absolute;
	        color: #fff;
	        z-index: 999;
	        cursor: pointer;
			margin-left: 95%;
    	}
	}
	.banner-upload-list {
        display: inline-block;
        height: 142px;
        text-align: center;
        line-height: 60px;
        border: 1px solid transparent;
        border-radius: 4px;
        overflow: hidden;
        background: #fff;
        position: relative;
        box-shadow: 0 1px 1px rgba(0,0,0,.2);
        margin-right: 4px;
    }
    .banner-upload-list:hover .demo-upload-list-cover{
        display: block;
    }
</style>
