<template>
    <div class="searchModal">
        <top-bar :address="false" ref="top"></top-bar>
        <div class="container">
            <Breadcrumb separator=">" class="mt20 mb10">
                <Breadcrumb-item to="/51index">首页</Breadcrumb-item>
                <Breadcrumb-item to="/51index/standardList?flag=2">标准</Breadcrumb-item>
                <Breadcrumb-item>正文</Breadcrumb-item>
            </Breadcrumb>
            <Row>
                <Col span="18">
                    <!-- 标准详情页头部 -->
                    <h3 class="zx-title">{{ standard.title }}</h3>
                    <div class="zx-time clear">
                        <div class="ivu-tag ivu-tag-border-custom ivu-tag-checked"
                            :style="standard.standardTrait === '强制性标准' ? 'border-width: 1px; border-style: solid; border-color: #FF7921;' : 'border-width: 1px; border-style: solid; border-color: #F5A623;'">
                            <span class="ivu-tag-text" :style="standard.standardTrait === '强制性标准' ? 'color: #FF7921;margin-right: 0px;' : 'color: #F5A623;margin-right: 0px;'">{{ standard.standardTrait }}</span>
                        </div>
                        <div class="ivu-tag ivu-tag-border-custom ivu-tag-checked"
                            :style="standard.standardStatus === '现行' ? 'border-width: 1px; border-style: solid; border-color: #4AB344;' : 'border-width: 1px; border-style: solid; border-color: #9B9B9B;'">
                            <span class="ivu-tag-text" :style="standard.standardStatus === '现行' ? 'color: #4AB344;margin-right: 0px;' : 'color: #9B9B9B;margin-right: 0px;'">{{ standard.standardStatus }}</span>
                        </div>
                        <span style="margin-left: 120px;">{{ standard.createTime }}</span>
                        <span>{{ standard.source }}</span>
                        <a  @click="toPortals" :title="standard.author" v-if="standard.author" class="mr10">
                            <span v-if="standard.author.length > 9">发布人：{{standard.author.substring(0,12) }}...</span>
                            <span v-else>发布人：{{standard.author}}</span>
                        </a>
                        <span>浏览次数：{{ standard.browseNumber }}</span>
                        <div class="zx-favorite">
                            <Button @click.native="collect" v-if="!isCollect">
                                <Icon type="md-star" size="16"></Icon> 收藏</Button>
                            <Button @click.native="collect" v-if="isCollect" class="t-green is-collect">
                                <Icon type="md-star" size="16" class="t-green"></Icon> 已收藏</Button>
                          <!-- <Button type="text" @click.native="collect"><Icon type="md-star" size="16"></Icon> 收藏</Button> -->
                        </div>
                    </div>
                    <div style="height: 1px;background-color: #e6e6e6;"></div>
                    <!-- 标准详情页数据 -->
                    <Row class="standard-detail">
                        <Row>
                            <Col span="4"><span>标准号</span></Col>
                            <Col span="20">{{ standard.standardNumber }}</Col>
                        </Row>
                        <Row>
                            <Col span="4"><span>中文标准名称</span></Col>
                            <Col span="20">{{ standard.chineseStandardName }}</Col>
                        </Row>
                        <Row>
                            <Col span="4"><span>英文标准名称</span></Col>
                            <Col span="20">{{ standard.englishStandardName }}</Col>
                        </Row>
                    </Row>
                    <div style="height: 1px;background-color: #e6e6e6;"></div>
                    <Row class="standard-detail">
                        <Row>
                            <Col span="5"><span>中国标准分类号（CCS）</span></Col>
                            <Col span="6">{{ standard.ccs }}</Col>
                            <Col span="5"><span>国际标准分类号（ICS）</span></Col>
                            <Col span="8">{{ standard.ics }}</Col>
                        </Row>
                        <Row>
                            <Col span="5"><span>主管部门</span></Col>
                            <Col span="6">{{ standard.competentDepartment }}</Col>
                            <Col span="5"><span>归口单位</span></Col>
                            <Col span="8">{{ standard.focalPoint }}</Col>
                        </Row>
                        <Row>
                            <Col span="5"><span>发布日期</span></Col>
                            <Col span="6">{{ standard.publishDate }}</Col>
                            <Col span="5"><span>实施日期</span></Col>
                            <Col span="8">{{ standard.implementDate }}</Col>
                        </Row>
                        <Row>
                            <Col span="5"><span>发布单位</span></Col>
                            <Col span="19">{{ standard.releaseUnit }}</Col>
                        </Row>
                    </Row>
                    <!-- 标准详情页文本与PDF -->
                    <div style="border: solid 1px #DEDEDE;margin-top: 20px;">
                        <Row style="line-height: 60px;border-bottom: solid 1px #DEDEDE;">
                            <Col span="12">
                                <span style="margin-left: 20px;" v-if="isTXT">正文阅读模式</span>
                                <span style="margin-left: 20px;" v-else>PDF阅读模式</span>
                            </Col>
                            <Col span="12" style="text-align: right;">
                                <Button type="default" v-if="isTXT && standard.standardFile" @click="change" style="margin-right: 20px;">转为PDF阅读模式</Button>
                                <Button type="default" v-if="!isTXT && standard.standardText" @click="change" style="margin-right: 20px;">转为正文阅读模式</Button>
                            </Col>
                        </Row>
                        <Row>
                            <Col v-if="isTXT" span="24" style="height: 500px; overflow-y: scroll;padding: 20px;">
                                <div v-html="standard.standardText"></div>
                            </Col>
                            <vue-pdfjs v-if="!isTXT && format === 'pdf'" viewer="../static/" :url="standardFile" :type="1"></vue-pdfjs>
                            <img v-if="!isTXT && format === 'pic'" :src="standardFile" />
                        </Row>
                    </div>
                    <!-- 上一篇下一篇以及点赞 -->
                    <Row type="flex" align="middle" class="pt10">
                        <Col span="10">
                            <h3 class="xgzx ell">
                                上一篇：
                                <template v-if="standard.previousMap === undefined">
                                    <span style="color: #00c587;">没有上一篇</span>
                                </template>
                                <template v-else>
                                    <a :href="['/InforMation/standardDetail?id='+standard.previousMap.previousId+'&status='+status]"><span style="color: #00c587;">{{standard.previousMap.title}}</span></a>
                                </template>
                            </h3>
                            <h3 class="xgzx ell">
                                下一篇：
                                <template v-if="standard.nextMap === undefined">
                                    <span style="color: #00c587;">没有下一篇</span>
                                </template>
                                <template v-else>
                                    <a :href="['/InforMation/standardDetail?id='+standard.nextMap.previousId+'&status='+status]"><span style="color: #00c587;">{{standard.nextMap.title}}</span></a>
                                </template>
                            </h3>
                        </Col>
                        <collect-modal ref="collectModal" @on-init="chckeCollect" v-on:collectForm="collectForm" :itemId="itemId" :itemType="itemType" :link="link" :collectTitle="collectTitle"/>
                        <Col span="4">
                            <div class="zanBtn">
                                <Button @click="handleLike($route.query.id)" shape="circle" :class="{'like': zan}">
                                    <span ><i class="icon-laud" style="font-size:16px"></i></span>
                                    <span>赞</span>
                                    <span>|</span>
                                    <span>{{ standard.thumbNum }}</span>
                                </Button>
                            </div>
                        </Col>
                        <Col span="2" push="8" style="text-align: right;">
                            <Button type="text" style="padding-right: 0px" @click="jubaoAdd"><Icon type="md-information-circle" size="16"></Icon> 举报</Button>
                        </Col>
                    </Row>
                    <!-- 标准详情页评论 -->
                    <div class="mb30">
                        <div class="comment-detail">
                            <div>
                                <h5 ><Icon type="md-text"> </Icon></h5>
                                <div class="comment-count">{{tote}} 条评论</div>
                            </div>
                            <div>
                                <div class="text-user-icon">
                                    <img src="../../img/user-icon.png" >
                                </div>
                                <Input v-model="huifu" @on-focus="checkUser" type="textarea" @on-enter="reply($route.query.id)"  :autosize="{minRows: 3,maxRows: 5}" placeholder="请输入..." class="text-area"></Input>
                            </div>
                            <Button type="primary" size="small" class="text-button"  @click="reply($route.query.id)">
                                发送
                            </Button>
                        </div>
                    </div>
                    <div class="CommentTab">
                        <Tabs value="name1" @on-click="getMyCommnet">
                            <Tab-pane label="最新评论" name="name1">
                                <comments :data="commentList" v-if="commentList.list" @on-NextPage="handleNextPage" @on-replyComment="replyComment" @on-like="commentAndReplyLike" :dataType="'标准'"/>
                            </Tab-pane>
                            <Tab-pane label="我的评论" name="name2" v-if="loginuserinfo != ''">
                                <comments :data="myComments" v-if="myComments.list" @on-NextPage="handleOwnerNextPage" @on-replyComment="replyComment" @on-like="commentAndReplyLike" :dataType="'标准'"/>
                            </Tab-pane>
					    </Tabs>
                    </div>
                </Col>
                <!-- 标准详情页右侧相关数据 itemId：详情id，itemType：资讯1政策2知识3标准4 -->
                <Col span="5" offset="1" class="xg-content">
                    <information-detail-left :itemId="itemId" :itemType="itemType"></information-detail-left>
                </Col>
            </Row>
        </div>
        <!--举报的弹框-->
        <Modal v-model="modal1">
            <p slot="header" style="text-align:center;">举报</p>
            <div>
                <Row class="jubao-list">
                    <Col span="8" v-for="(btn,index) in btnlist" :key="index">
                        <Button :class="{activeEle:active===index}" @click="addActive($event,btn.text,index)">{{btn.text}}</Button>
                    </Col>
                </Row>
                <div>
                    <h3>截图证明</h3>
                    <div class="upload-inline">
                        <template v-if="reportPicList.length > 0">
                            <div class="demo-upload-list" v-for="(item,index) in reportPicList" style="width: 78px ;height: 78px" :key="index">
                                <img :src="item" class="upload-img">
                                <div class="demo-upload-list-cover">
                                    <Icon type="ios-trash-outline"
                                          @click.native="handleRemove(item)"></Icon>
                                </div>
                            </div>
                        </template>
                    </div>
                    <div class="upload-inline">
                        <Upload ref="upload" :show-upload-list="false"
                                name="upfile"
                                :format="['jpg','png']"
                                :max-size="204800"
                                :on-success="handleSuccess"
                                multiple
                                type="drag"
                                :action="action"
                                class="psup-btn">
                            <div>
                                <Icon type="md-add-circle" size="20"></Icon>
                            </div>
                        </Upload>
                    </div>
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
    </div>
</template>
<script>
import api from '~api';
import $ from 'jquery';
import top from '~src/top.vue';
import collectModal from '~src/components/collectModal';
import sha1 from '../../page/member/proxy/sha1.js';
import informationDetailLeft from './components/informationDetailLeft';
import { swiper, swiperSlide } from 'vue-awesome-swiper';
import vuePdfjs from 'vue-pdfjs';
// import { fetchDataMixin} from '../../service/information/knowledgeDetailMixins';
import comments from '~components/vui-comments/item.vue'
export default {
    // mixins: [fetchDataMixin],
    components: {
    	'top-bar': top,
    	collectModal,
        informationDetailLeft,
        swiper,
        swiperSlide,
        vuePdfjs,
        comments
  	},
    data() {
        return {
            standard: {},
            isTXT: false,
            format: '',
            index: 0,
            standardFile: '',
            link: this.$route.fullPath,
        	collectShow: false,
            loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            huifu: '',
            modal1: false,
            active: 0,
            commentList: [],
            myComments: [],
            tote: 0,
            title: '',
            previousId: '',
            jubaoDetail:'',
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
            modal2: false,
            user: sessionStorage.getItem(sessionStorage.getItem('key')),
            editerUrl: '',
            zan: false,
            text: '垃圾广告',
            reportPicList: [],
            action: `${this.$url.upload}/upload/up`,
            itemId: 0,
            itemType: 4,
            status: 1,
            isShowCommentReply: false,
            collectTitle:'',
            ownerCurrent:1,
            current:1,
            isCollect: false
        }
    },
    computed: {
    },
    mounted () {
    },
    created() {
        // this.itemId = parseInt(this.$route.query.id);
        // this.status = this.$route.query.status;
        // let data = {
        //     standardDetailId: this.$route.query.id,
        //     status: this.$route.query.status
        // };
        this.fetchData();
        this.chckeCollect()
    },
    methods: {
        toPortals () {
            this.$toPortals(this.editerAccount)
        },
        checkUser () {
            if(!this.loginuserinfo) {
                this.$refs.top.loginuser()
                this.$Message.error('请先登录！')
            }
        },
    	//获得收藏列表
    	collectForm (e){
            var loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            this.itemId = e.classify
            api.post('/member/report/saveCollect',
                {
                    type: 'policy',
                    link: this.$route.fullPath,
                    title: this.title,
                    collectId: this.itemId,
                    account: loginuserinfo.loginAccount
                }
            ).then(response => {
                this.flag = response.data
                if (this.flag == '1') {
                    alert('收藏成功!')
                } else {
                }
            })

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
				if (this.isCollect) {
					this.$Message.error('请勿重复收藏')
				} else {
					this.$refs['collectModal'].show = true
				}
			} else {
				this.$Message.error('请先登录!')
			}
		},
        addActive (event, value,index) {
            this.text = value
            this.active = index
        },
        // 评论的回复框的显示隐藏
        showComment (item) {
            if (this.loginuserinfo) {
                item.showReplychi = !item.showReplychi
            } else {
                this.$Message.error('请先登录!')
            }
        },
        getMyCommnet (item) {
            if (item === 'name2') {
                this.myComment(1)
            } else {
                this.findComment(1)
            }
        },
        jubaoAdd () {
            if (this.loginuserinfo != null) {
                api.post('/member/report/reportIsExist', {
                    link: this.$route.fullPath,
                    account: this.loginuserinfo.loginAccount
                }).then(resp => {
                        if (200 === resp.code) {
                            if (1 === resp.data) {
                                this.$Message.error('您已举报,请勿重复举报!')
                            } else {
                                // 弹出举报窗口
                                this.modal1 = true
                            }
                        }
                    })
            } else {
                this.$Message.error('请先登录!')
            }
        },
        submit () {
            api.post('/member/report/saveReport', {
                type: this.text,
                link: this.$route.fullPath,
                reportPicList : this.reportPicList,
                description: this.jubaoDetail
            }).then(response => {
                if(response.data=== 1) {
                    this.$Message.success('举报成功!')
                    this.modal1 = false
                }else {
                    this.$Message.error('举报失败!')
                }
            })
        },
        //移除图片
        handleRemove (item) {
            this.reportPicList.splice(this.reportPicList.indexOf(item), 1)
        },
        //上传图片
        handleSuccess (response, file, fileList) {
            if (this.reportPicList.length === 3) {
                this.$Message.error('举报图片不能超过3个')
            } else {
                var picname = response.data.picName
                this.reportPicList.push(picname)
            }
        },
        // 标准文本与PDF互相转换
        change () {
            this.isTXT = !this.isTXT
        },
        // 评论点赞以及评论回复点赞
        commentAndReplyLike (item) {
            console.log(item)
            if (this.loginuserinfo) {
                api.post('/member/thumb/detailThumbCommentAdd',{
                    account: this.loginuserinfo.loginAccount,
                    commentId:item.id,
                    commentType: 'standard'
                }).then(response => {
                    if (response.code === 200) {
                        if(response.data === 'exist'){
                            this.$Message.error('您已点赞')
                        } else{
                            this.$Message.success('点赞成功')
                            this.findComment(1)
                            this.myComment(1)
                            // item.commentThumbnum ++
                        }
                    }
                })
            }else {
                this.$Message.error('请先登录')
            }
        },
        // 文章点赞
        handleLike (id) {
            if (this.loginuserinfo) {
                api.post('/member/thumb/detailThumbAdd',{
                    account: this.loginuserinfo.loginAccount,
                    detailId: parseInt(id),
                    detailType: 'standard'
                } ).then(response => {
                    if (response.code === 200) {
                        if(response.data === 'exist'){
                            this.$Message.error('您已点赞')
                        } else{
                            this.$Message.success('点赞成功')
                            this.standard.thumbNum ++
                        }
                    }
                })
            } else {
                this.$Message.error('请先登录')
            }
        },
        fetchData () {
            this.itemId = parseInt(this.$route.query.id)
            this.status = this.$route.query.status
            let data = {
                standardDetailId: this.$route.query.id,
                status: this.$route.query.status
            }
            api.post('/member/standard/getStandardDetail', data).
            then(response => {
                console.log('response', response)
                if (response.code === 200) {
                    this.standard = response.data
                    this.collectTitle = response.data.title
                    this.findComment(1)
                    this.myComment(1)
                    if (this.standard.standardText === '' && this.standard.standardFile !== '') {
                        this.isTXT = false
                    } else if (this.standard.standardText !== '' && this.standard.standardFile === '') {
                        this.isTXT = true
                    }
                    // 获取标准文件进行显示 如果是照片的直接显示 如果是PDF则调用PDF的插件进行显示
                    this.standardFile = this.standard.standardFile
                    let a = this.standardFile.lastIndexOf(".")
                    let b = this.standardFile.length
                    let format = this.standardFile.substring(a, b)
                    if (format === '.jpg' || format === '.jpeg' || format === '.png') {
                        this.format = 'pic'
                    } else if (format === '.pdf') {
                        this.format = 'pdf'
                    }
                    this.editerUrl = `/portals/index?uid=${this.standard.account}`
                }
            })
        },
        //最新评论
        findComment (currentPage) {
            api.post('/member/standard/getStandardComment', {
                standard_id: this.standard.standardId,
                pageNum:currentPage,
            }).then(response => {
                this.commentList = response.data
                this.tote = response.data.total
                console.log('comment response', response)
            })
        },
        //最新评论的翻页
        handleNextPage(e){
            this.current = e
            this.findComment(this.current)
            this.myComment(this.ownerCurrent)
        },
        //我的评论的翻页
        handleOwnerNextPage(e){
            this.ownerCurrent = e
            this.findComment(this.current)
            this.myComment(this.ownerCurrent)
        },
        //我的评论
        myComment (currentPage) {
            if (this.user) {
                api.post('/member/standard/getStandardComment', {
                    standard_id: this.standard.standardId,
                    account: this.loginuserinfo.loginAccount,
                    pageNum:currentPage,
                }).then(response => {
                    this.myComments = response.data
                })
            }
        },
        reply (id) {
            if (this.huifu === '' || this.huifu === null) {
                this.$Message.info('评论内容不能为空！')
                return
            }
            api.post('/member/standard/publish-comment',
                {
                    standardId: this.standard.standardId,
                    content: this.huifu,
                    account: this.loginuserinfo.loginAccount
                }
            ).then(response => {
                console.log('resp111', response)
                if (response.code === 200) {
                    this.huifu = ''
                    this.findComment(1)
                    this.myComment(1)
                }
            })
        },
        // 评论回复的回复框的显示与隐藏
        showReply (item) {
            if (this.loginuserinfo) {
                item.showReply = !item.showReply
            } else {
                this.$Message.error('请先登录!')
            }
        },
        // 查看回复
        checkUpReply (item) {
            console.log('item', item)
            api.post('/member/standard/query-detail-comment-reply', {
                commentId: item.commentId
            }).then(response => {
                console.log('response', response)
                if (response.code === 200) {
                    // 先处理数据后赋值 先后顺序颠倒会导致showReply的值不能双向绑定
                    response.data.list.forEach(l => {
                        l.showReply = false
                    })
                    item.replyChildren = response.data.list
                    item.showOrHideComment = false
                }
            }).catch(error => {
                console.log('error', error)
            })
        },
        // 收起回复
        packUpReply (item) {
            item.replyChildren = []
            item.showOrHideComment = true
        },
        // 回复评论的回复
        replyComment(e){
            console.log(e)
            this.replyCommentReply(e.replyAccount,e.id,e.content)
        },
        replyCommentReply (replyAccount, commentId,content) {
            // if (this.huifu === '' || this.huifu === null) {
            //     this.$Message.info('评论内容不能为空！')
            //     return
            // }
            this.$api.post('/member/standard/publish-comment', {
                standardId: this.standard.standardId,
                commentId: commentId,
                content: content,
                account: this.loginuserinfo.loginAccount,
                replyAccount: replyAccount
            }).then(response => {
                if(response.code == 200){
                    console.log('reply response', response)
                    this.$Message.success('评论成功')
                    this.findComment(1)
                    this.myComment(1)
                }else{
                    this.$Message.error('评论失败!')
                }
            })
        },
        // 回复评论
        // replyComment (item) {
        //     if (this.huifu === '' || this.huifu === null) {
        //         this.$Message.info('评论内容不能为空！')
        //         return
        //     }
        //     console.log('reply comment', item)
        //     api.post('/member/standard/publish-comment', {
        //         standardId: this.standard.standardId,
        //         commentId: item.commentId,
        //         content: this.huifu,
        //         account: this.loginuserinfo.loginAccount,
        //         replyAccount: item.commentAccount
        //     }).then(response => {
        //         console.log('reply response', response)
        //         this.huifu = ''
        //         this.findComment(1)
        //         this.myComment(1)
        //     })
        // }
    }
}
</script>
<style scoped>
    .ivu-tag-border-custom {
        height: 24px;
        line-height: 24px;
        /* border: 1px solid #e9eaec!important; */
        color: #657180!important;
        background: #fff!important;
        position: relative;
    }
    .standard-detail {
        line-height: 50px;
        font-family: PingFang SC;
        color: #4A4A4A;
        font-weight: bold;
    }
</style>
