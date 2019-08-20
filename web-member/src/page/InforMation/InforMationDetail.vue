<template>
	<div class="searchModal">
		<topNav :address="false" ref="top" />
		<div class="container">
			<Breadcrumb separator=">" class="mt20 mb10">
				<Breadcrumb-item to="/51index">首页</Breadcrumb-item>
				<Breadcrumb-item to="/51index/inforMationList?flag=1">资讯</Breadcrumb-item>
				<Breadcrumb-item>正文</Breadcrumb-item>
			</Breadcrumb>
			<Row>
				<Col span="18">
				<h3 class="zx-title">{{title}}</h3>
				<div class="zx-time clear">
					<span>{{createTime}}</span>
					<span>{{source}}</span>
					<a @click="toPortals" :title="editer" v-if="editer" class="mr10">
						<span v-if="editer.length > 20">发布人：{{editer.substring(0,12) }}...</span>
						<span v-else>发布人：{{editer}}</span>
					</a>
					<span>浏览次数：{{browseNumber}}</span>
					<div class="zx-favorite">
						<Button @click.native="collect" v-if="!isCollect">
							<Icon type="md-star" size="16"></Icon> 收藏</Button>
						<Button @click.native="collect" v-if="isCollect" class="t-green is-collect">
							<Icon type="md-star" size="16" class="t-green"></Icon> 已收藏</Button>
					</div>
				</div>
				<div v-html="content"></div>
				<Row type="flex" align="middle" v-if="columnType == '视频' || columnType  == '音频'">
					<!-- swiper1 -->
					<template v-if="columnType == '视频'">
						<swiper :options="swiperOptionTop" class="gallery-top" @slideChangeTransitionEnd="clickMe" ref="swiperTop" style="padding-bottom:20px">
							<swiper-slide v-for="(item,index) in videoList" :key="index">
								<div style="position:relative;text-align:center">
									<video :src="item.addr" height="400px" style="max-width: 100%" controls="controls" />
								</div>
							</swiper-slide>
							<div class="swiper-button-next swiper-button-white" slot="button-next"></div>
							<div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
							<div class="swiper-pagination" slot="pagination" style="bottom: 0" v-if="videoList.length>1"></div>
						</swiper>
						<p style="line-height: 26px;width: 900px;font-family: PingFangSC-Regular;padding-bottom: 70px;padding-top: 30px;color: #333333;font-size: 18px;text-indent: 2em;" v-if="videoList.length>0">{{videoDescribe[rIndex]}}</p>
					</template>
					<template v-if="AplayerShow && songs.length>0">
						<Col span="24" v-for="(v,key) in songs" :key="key">
						<p style="line-height: 24px;width: 900px;padding-bottom: 10px;color: #666;font-size: 14px;text-indent: 2em;word-wrap:break-word">{{v.describe}}</p>
						<a-player v-if="AplayerShow" :music="songs[key]" style="width: 100%;"></a-player>
						</Col>
					</template>

				</Row>
				<!--<Row type="flex" align="middle">-->
				<!--<p style="line-height: 26px;width: 900px;font-family: PingFangSC-Regular;padding-bottom: 70px;padding-top: 30px;color: #333333;font-size: 18px;text-indent: 2em;">{{videoList[rIndex].describe}}</p>-->
				<!--</Row>-->

				<Row type="flex" align="middle" class="pt10">
					<Col span="10">
					<h3 class="xgzx ell">
						上一篇：
						<template v-if="upPage !== -1">
							<a :href="['/InforMation/bookBlurb?id='+informationId+'&informationDetailId='+upPage+'&book_type=information']" v-show="column_type==='图书'">
								<span style="color: #00c587;">{{upPageTitle}}</span>
							</a>
							<a :href="['/InforMation/findInforMationDetail?id='+upPage+'&book_type=information']" v-show="column_type!=='图书'">
								<span style="color: #00c587;">{{upPageTitle}}</span>
							</a>
						</template>
						<template v-if="upPage === -1">
							<span style="color: #00c587;">没有上一篇</span>
						</template>
					</h3>
					<h3 class="xgzx ell">下一篇：
						<template v-if="nextPage !== -1">
							<a :href="['/InforMation/bookBlurb?id='+informationNextId+'&informationDetailId='+nextPage+'&book_type=information']" v-show="column_next_type==='图书'">
								<span style="color: #00c587;">{{nextPageTitle}}</span>
							</a>
							<a :href="['/InforMation/findInforMationDetail?id='+nextPage+'&book_type=information']" v-show="column_next_type!=='图书'">
								<span style="color: #00c587;">{{nextPageTitle}}</span>
							</a>
						</template>
						<template v-if="nextPage === -1">
							<span style="color: #00c587;">没有下一篇</span>
						</template>
					</h3>
					</Col>
					<collect-modal ref="collectModal" @on-init="chckeCollect" v-on:collectForm="collectForm" :itemId="itemId" :itemType="itemType" :link="link" :collectTitle="collectTitle"></collect-modal>
					<Col span="4">
					<div class="zanBtn">
						<Button @click="handleLike($route.query.id)" shape="circle" :class="{'like': zan}">
							<span>
								<i class="icon-laud" style="font-size:16px"></i>
							</span>
							<span>赞</span>
							<span>|</span>
							<span>{{thumbUpNum}}</span>
						</Button>
					</div>
					</Col>
					<Col span="2" push="8">
					<Button type="text" @click="jubaoAdd">
						<Icon type="md-information-circle" size="16"></Icon> 举报</Button>
					</Col>
				</Row>
				<!--
                    	作者：786905664@qq.com
                    	时间：2017-12-12
                    	描述：评论
                    -->
				<div class="mb30">
					<div class="comment-detail">
						<div>
							<h5>
								<Icon type="md-text"> </Icon>
							</h5>
							<div class="comment-count">{{tote}} 评论</div>
						</div>

						<div>
							<div class="text-user-icon">
								<img src="../../img/user-icon.png">
							</div>
							<Input v-model="huifu" @on-focus="checkUser" type="textarea" @on-enter="reply($route.query.id)" :autosize="{minRows: 3,maxRows: 5}" placeholder="请输入..." class="text-area"></Input>
						</div>
						<Button type="primary" size="small" class="text-button" @click="reply($route.query.id)">
							发送
						</Button>
					</div>
				</div>
				<div class="CommentTab">
					<!-- <span class="CommentBars">
                            <span>{{tote}}</span>评论</span> -->
					<Tabs value="name1" @on-click="getMyCommnet">
						<Tab-pane label="最新评论" name="name1">
							<comments class="pd20" :data="commentList" v-if="commentList.list" @on-NextPage="handleNextPage" :dataType="'动态'" />
						</Tab-pane>
						<Tab-pane label="我的评论" name="name2" v-if="loginuserinfo != ''">
							<comments class="pd20" :data="ownerlist" v-if="ownerlist.list" @on-NextPage="handleOwnerNextPage" :dataType="'动态'" />
						</Tab-pane>
					</Tabs>
				</div>
				</Col>
				<Col span="5" offset="1" class="xg-content">
				<information-detail-left :itemId="itemId" :itemType="itemType"></information-detail-left>
				</Col>
			</Row>
		</div>
		<!--举报的弹框-->
		<Modal v-model="jubaoClick">
			<p slot="header" style="text-align:center;">举报</p>
			<div>
				<Row class="jubao-list">
					<Col span="8" v-for="(btn,index) in btnlist" :key="index">
					<Button :class="{activeEle:active===index}" @click="addActive($event,btn.text,index)">{{btn.text}}</Button>
					</Col>
				</Row>
				<h3>截图证明</h3>
				<div class="upload-inline">
					<template v-if="reportPicList.length > 0">
						<div class="demo-upload-list" v-for="(item,index) in reportPicList" :key="index" style="width: 78px ;height: 78px">
							<img :src="item" class="upload-img">
							<div class="demo-upload-list-cover">
								<Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
							</div>
						</div>
					</template>
				</div>
				<div class="upload-inline">
					<Upload ref="upload" :show-upload-list="false" name="upfile" :format="['jpg','png']" :max-size="204800" :on-success="handleSuccess" multiple type="drag" :action="action" class="psup-btn">
						<div>
							<Icon type="md-add-circle" size="20"></Icon>
						</div>
					</Upload>
				</div>
				<div>
					<h3>举报详情</h3>
					<textarea v-model="jubaoDetail" rows="5" style="width:100%;border-radius:5px;margin-top:15px;"></textarea>
				</div>
			</div>
			<div slot="footer" style="text-align:center;">
				<Button type="primary" shape="circle" class="btnReset" style="width:200px;height:50px;" @click="submit">举报</Button>
			</div>
		</Modal>
		<!-- 播放视频 -->
		<Modal :transfer="false" v-model="playVideoShow" @on-ok="playVideoShow=false" title="播放视频" width="600px" height="300px" :mask-closable="false">
			<d-player ref="playerInfo" :video="video" :loop="false"></d-player>
		</Modal>
	</div>
</template>
<script>
import topNav from '~src/top'
import api from '~api'
import $ from 'jquery'
import collectModal from '~src/components/collectModal'
import VueDPlayer from "~components/vuedplayer";
import VueAplayer from 'vue-aplayer'
import { swiper, swiperSlide } from 'vue-awesome-swiper'
import informationDetailLeft from './components/informationDetailLeft'
import sha1 from '../../page/member/proxy/sha1.js'
import comments from '~components/vui-comments/item.vue'
export default {
	components: {
		topNav,
		collectModal,
		"d-player": VueDPlayer,
		"a-player": VueAplayer,
		informationDetailLeft,
		swiper,
		swiperSlide,
		comments
	},
	data() {
		return {
			swiperOptionTop: {
				spaceBetween: 10,
				navigation: {
					nextEl: '.swiper-button-next',
					prevEl: '.swiper-button-prev'
				},
				pagination: {
					el: '.swiper-pagination',
					clickable: true
				}
			},

			informationId: '',
			informationNextId: '',
			column_type: '',
			column_next_type: '',
			rIndex: 0,
			itemId: 0,
			itemType: 1,
			link: this.$route.fullPath,
			action: `${this.$url.upload}/upload/up`,
			text: '垃圾广告',
			editerUrl: '',
			editerAccount: '',
			//举报图片
			reportPicList: [],
			//视频
			player: {},
			video: {
				url: ""
			},
			playVideoShow: false,
			//音频
			songs: [],
			showlrc: 3,
			AplayerShow: false,
			zan: false,
			huifu: '', //回复内容
			ownerlist: [], //我的评论
			collectShow: false,
			loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
			portal: '',
			loginUserMyNo: true,
			theme1: 'light',
			currentPage: 1,
			pageSize: 10,
			total: 0,
			content: '',
			videoList: [],
			videoDescribe: [],
			replyContent: false,
			jubaoClick: false,
			active: 0,
			commentList: [],
			myComments: [],
			value: '',
			values: '',
			Numberofpoints: '',
			thumbUpNum: 0,
			tote: 0,
			title: '',
			label: '',
			createTime: '',
			source: '',
			browseNumber: '',
			isReport: '',
			editer: '',
			previousId: '',
			previousTitle: '',
			nextId: '',
			nextTitle: '',
			value13: '',
			columnType: '',
			collectMd: true,
			btnlist: [{
				text: '垃圾广告'
			},
			{
				text: '色情淫秽'
			},
			{
				text: '政治反动'
			},
			{
				text: '钓鱼诈骗'
			},
			{
				text: '网络敲诈'
			},
			{
				text: '其他'
			}
			],
			jubao: {
				type: '',
				link: '',
				description: ''
			},
			url: [],
			jubaoDetail: '',
			upPage: 0,
			nextPage: 0,
			upPageTitle: '',
			nextPageTitle: '',
			detailThumbCount: 0, // 点赞数
			book_type: '',
			collectTitle: '',
			detailId: '',
			isCollect: false
		}
	},
	mounted() {
		this.player = this.$refs.playerInfo.dp;
	},
	computed: {
		swiper() {
			return this.$refs.swiperTop.swiper
		}
	},
	created() {
		this.book_type = this.$route.query.book_type;
		this.detailId = this.$route.query.id
		this.fetchData()
		//获取最新评论
		this.findComment(1)
		//获取我的评论
		this.findOwnerComment(1)
		this.getUpAndNextPage()
		this.getThumbNum()
		// 看是否有收藏过
		this.chckeCollect()
		this.$nextTick(() => {
			const swiperTop = this.$refs.swiperTop.swiper
			const swiperThumbs = this.$refs.swiperThumbs.swiper
			swiperTop.controller.control = swiperThumbs
			swiperThumbs.controller.control = swiperTop
		})
	},
	methods: {
		toPortals () {
			this.$toPortals(this.editerAccount)
		},
		clickMe(e) {
			this.rIndex = this.swiper.realIndex

		},
		hideReplyListChildren(item) {
			item.replyChildren = []
			item.showOrHideComment = true
		},
		playVideo(url) {
			this.player.video.src = url;
			this.playVideoShow = true;
		},
		checkUser() {
			if (!this.loginuserinfo) {
				this.$refs.top.loginuser()
				this.$Message.error('请先登录！')
			}
		},
		collectForm(e) {
		},
		fetchData: function () {
			this.itemId = parseInt(this.$route.query.id)
			api.get('/member/inforMation/findInforMationDetail?id=' + this.$route.query.id)
				.then(response => {
					this.content = response.data.content
					this.title = response.data.title
					this.collectTitle = response.data.title;
					this.createTime = response.data.createTime
					this.source = response.data.source
					this.browseNumber = response.data.browseNumber
					this.editer = response.data.editer
					this.thumbUpNum = response.data.thumbUpNum
					this.previousTitle = response.data.previousTitle
					this.nextTitle = response.data.nextTitle
					this.previousId = response.data.previousId
					this.nextId = response.data.nextId
					this.label = response.data.label
					this.editerAccount = response.data.account
					this.columnType = response.data.columnType
					this.editerUrl = `/portals/index?uid=${this.editerAccount}`
					if (response.data.videoImgs && response.data.videoImgs.length) {
						response.data.videoImgs.forEach(v => {
							if (v.type === 1) {
								this.songs.push({ url: v.addr, title: v.mediaName, author: this.editer, describe: v.describe })
							} else {
								this.videoList.push(v)
								this.videoDescribe.push(v.describe)
							}
						})
						if (this.songs.length) {
							this.AplayerShow = true
						}
					}
				})
		},
		getUpAndNextPage() {
			let type = this.book_type
			api.get('/member/inforMation/upAndNextPage/' + this.$route.query.id)
				.then(resp => {
					if (200 === resp.code) {
						this.upPage = resp.data.upPage
						this.nextPage = resp.data.nextPage
						if (-1 !== this.upPage) {
							api.get('/member/inforMation/findInforMationDetail?id=' + this.upPage)
								.then(response => {
									this.upPageTitle = response.data.title
								})
							this.$api.post('/member/inforMation/findInformationByDetailID', { information_detail_id: this.upPage, book_type: 'information' }).then(response => {
								let result = response.data
								if (result != '') {
									this.informationId = result.id
									this.column_type = result.column_type
								}
							}).catch(error => {
								console.error(error)
							})

						}
						if (-1 !== this.nextPage) {
							api.get('/member/inforMation/findInforMationDetail?id=' + this.nextPage)
								.then(response => {
									this.nextPageTitle = response.data.title
								})
							this.$api.post('/member/inforMation/findInformationByDetailID', { information_detail_id: this.nextPage, book_type: 'information' }).then(response => {
								let result = response.data
								if (result != '') {
									this.informationNextId = result.id
									this.column_next_type = result.column_type
								}
							}).catch(error => {
								console.error(error)
							})

						}

					}
				})
		},
		//默认显示最新评论
		findComment(currentPage) {
			this.$api.post('/member/columnSettings/findComments', {
				information_detail_id: this.detailId,
				pageNum: currentPage,
				type: 'information',
				account: ''
			}).then(response => {
				console.log(response)
				if (response.code == 200) {
					this.commentList = response.data
					this.tote = response.data.total
				}
			})
		},
		//最新评论分页
		handleNextPage(e) {
			this.findComment(e)
			this.findOwnerComment(1)
		},
		//我的评论分页
		handleOwnerNextPage(e) {
			this.findOwnerComment(e)
			this.findComment(1)
		},
		//默认显示我的评论
		findOwnerComment(currentPage) {
			console.log(currentPage)
			this.$api.post('/member/columnSettings/findComments', {
				information_detail_id: this.detailId,
				account: this.loginuserinfo.loginAccount,
				pageNum: currentPage,
				type: 'information'
			}).then(response => {
				console.log(response)
				if (response.code == 200) {
					this.ownerlist = response.data
				}
			})
		},
		/**
		 *验证是否登录
		 * */
		showHuiFuChi(item) {
			if (this.loginuserinfo) {
				item.showReplychi = !item.showReplychi
			} else {
				//alert("请先登录！")
				this.$Message.error('请先登录!')
			}
		},
		/*
		 * 对详情的回复
		 * */
		reply(id) {
			if (this.huifu === '' || this.huifu === null) {
				this.$Message.info('评论内容不能为空！')
				return
			}
			this.$api.post(
				'/member/inforMation/saveHuiFu', {
					dynamicId: id,
					comment: this.huifu,
					account: this.$user.loginAccount
				}
			).then(response => {
				var flag = response.data
				if (flag === 1) {
					this.findComment(1)
					this.findOwnerComment(1)
					this.huifu = ""
				} else {
					this.$Message.error('回复失败!')
				}
			})

		},
		/**
		 * 详情的点赞
		 * */
		handleLike(id) {
			// thumbDetailNum
			// 详情页点赞
			if (this.loginuserinfo) {
				api.post('/member/thumb/detailThumbAdd', {
					account: this.loginuserinfo.loginAccount,
					detailId: parseInt(id),
					detailType: 'information'
				}).then(response => {
					if (response.code === 200) {
						if (response.data === 'exist') {
							this.$Message.error('您已点赞')
						} else {
							this.$Message.success('点赞成功')
							this.thumbUpNum++
						}
					}
				})
			} else {
				this.$Message.error('请先登录')
			}

			this.getThumbNum()
		},
		getThumbNum() {
			// 查询点赞数
			api.post('/member/thumb/detailThumbCount', {
				detailId: parseInt(this.$route.query.id),
				detailType: 'information'
			}).then(response => {
				if (response.code === 200) {
					this.detailThumbCount = response.data
				}
			})
		},
		getMyCommnet(item) {
			if (item === 'name2') {
				//再查一遍我的评论
				this.findOwnerComment(1)

			} else {
				this.findComment(1)
			}
		},
		addActive(event, value, index) {
			this.text = value
			this.active = index
			this.jubaoClick = true
		},
		//举报
		submit() {
			api.post('/member/report/saveReport', {
				type: this.text,
				link: this.$route.fullPath,
				reportPicList: this.reportPicList,
				description: this.jubaoDetail
			}).then(response => {
				if (response.data === 1) {
					this.$Message.success('举报成功!')
					this.jubaoClick = false
				} else {
					this.$Message.error('举报失败!')
				}
			})
		},
		//举报图片
		//移除图片
		handleRemove(item) {
			this.reportPicList.splice(this.reportPicList.indexOf(item), 1)
		},
		//上传图片
		handleSuccess(response, file, fileList) {
			if (this.reportPicList.length === 3) {
				this.$Message.error('举报图片不能超过3个')
			} else {
				var picname = response.data.picName
				this.reportPicList.push(picname)
			}
		},
		jubaoAdd() {
			if (this.loginuserinfo != null) {
				api.post('/member/report/reportIsExist', {
					link: this.$route.fullPath,
					account: this.loginuserinfo.loginAccount
				})
					.then(resp => {
						if (200 === resp.code) {
							if (1 === resp.data) {
								this.$Message.error('您已举报,请勿重复举报!')
							} else {
								// 弹出举报窗口
								this.jubaoClick = true
							}
						}
					})
			} else {
				this.$Message.error('请先登录!')
			}
		},
        // 是否收藏
		chckeCollect() {
			if (this.$user.loginAccount != null) {
				// 是否已经收藏了
				this.$api.post('/member/report/isExist', {
					link: this.$route.fullPath,
					account: this.$user.loginAccount
				}).then(resp => {
					if (200 === resp.code) {
						if (resp.data === 1) { // 已收藏
							this.isCollect = true
						} else {
							this.isCollect = false
						}
					}
				})
			}
		},
        // 点击收藏
		collect() {
			if (this.loginuserinfo != null) {
				console.log(this.isCollect)
				if (this.isCollect) {
					this.$Message.error('请勿重复收藏')
				} else {
					this.$refs['collectModal'].show = true
				}
			} else {
				//alert("请先登录！")
				this.$Message.error('请先登录!')
			}
		},
		follow() {
			if (this.loginuserinfo != null) {
				api.post('/member/report/saveFollow', {
					type: 'inforMation',
					link: this.$route.fullPath,
					label: this.label
				}).then(response => {
					this.flag = response.data
					if (this.flag == '1') {
						alert("关注成功!")

					} else {
						alert("已关注!");
					}
				})
			} else {
				alert("请先登录！")
			}
		},
		routeTo(e) {
			if (e == '/portal') {
				this.portal = this.$url.shop + '/center/gateway.htm?uid=' + this.loginuserinfo.uniqueId
				window.open(this.portal)
			} else {
				this.$router.push(e)
			}
		},
		goToPrevious(id) {
			api.get('/member/inforMation/findInforMationDetail?id=' + id)
				.then(response => {
					this.content = response.data.content
					this.title = response.data.title
					this.createTime = response.data.createTime
					this.source = response.data.source
					this.browseNumber = response.data.browseNumber
					this.editer = response.data.editer
					this.thumbUpNum = response.data.thumbUpNum
					this.previousTitle = response.data.previousTitle
					this.nextTitle = response.data.nextTitle
					this.previousId = response.data.previousId
					this.nextId = response.data.nextId
					this.label = response.data.label
				})
		},
		logout() {
			api.get('/member/login/logout')
				.then(response => {
				})
			sessionStorage.removeItem("user")
			this.$router.push('/index')
		}
	}
}
</script>
<style  lang="scss">
.is-collect.ivu-btn {
  border-color: #33d19f;
}
.comment-detail {
  position: relative;
  width: 900px;
  border: 1px solid #f8f8f8;
  border-top: 3px solid #00c587;
  overflow: hidden;
  h5 {
    color: #333;
    font-size: 16px;
    padding: 15px;
    span {
      color: #3f3f3f;
      font-size: 14px;
      em {
        color: #333;
        font-style: normal;
      }
    }
  }
  .text-user-icon {
    width: 10%;
    padding: 10px;
    text-align: center;
    display: inline-block;
  }
  .text-button {
    float: right;
    margin: 10px 45px;
    width: 94px;
    height: 39px;
    width: 75px;
    height: 30px;
    font-size: 14px;
  }
  .text-area {
    width: 85%;
    margin: 10px 10px 10px 0px;
  }
}
.searchModal .dplayer-video {
  height: 300px !important;
}
.zanBtn .like span {
  color: #f90;
}

.searchModal img {
  max-width: 100%;
}

.searchModal .hr-sty {
  margin: 5px 0px;
}

.searchModal .video-play {
  position: absolute;
  top: 36%;
  left: 50%;
  transform: translate3d(-50%, -50%, 0);
  font-size: 45px;
  color: #fff;
}
.comment-count {
  position: absolute;
  top: 15px;
  right: 42px;
  font-size: 14px;
  color: #333333;
}
.searchModal .video-circle {
  position: absolute;
  top: 36%;
  left: 50%;
  transform: translate3d(-50%, -50%, 0);
  font-size: 70px;
  color: #fff;
}

.searchModal .btn-audio {
  height: 186px;
  background: url(../../img/defaultAudio.jpg) no-repeat center bottom;
  background-size: cover;
}
.activeEle {
  color: green;
}

.upload-img {
  width: 78px;
  height: 78px;
}

.upload-inline {
  display: inline-block;
}

.gallery-top {
  height: 80% !important;
  width: 100%;
}
.gallery-thumbs {
  height: 20% !important;
  box-sizing: border-box;
  padding: 10px 0;
}
.gallery-thumbs .swiper-slide {
  width: 25%;
  height: 100%;
  opacity: 0.4;
}
.gallery-thumbs .swiper-slide-active {
  opacity: 1;
}
</style>
