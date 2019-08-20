<template>
    <div class="searchModal">
        <top-bar :address="false" ref="top"></top-bar>
        <div class="container">
            <Breadcrumb separator=">" class="mt20 mb10">
                <Breadcrumb-item to="/51index">首页</Breadcrumb-item>
                <Breadcrumb-item to="/51index/policyList?flag=2">政策</Breadcrumb-item>
                <Breadcrumb-item>正文</Breadcrumb-item>
            </Breadcrumb>
            <Row>
                <Col span="18">
                <h3 class="zx-title">{{title}}</h3>
                <div class="zx-time clear">
                    <span>{{createTime}}</span>
                    <span>{{source}}</span>
                    <a  @click="toPortals" :title="editer" v-if="editer"  class="mr10">
                        <span v-if="editer.length > 20">发布人：{{editer.substring(0,12) }}...</span>
                        <span v-else>发布人：{{editer}}</span>
                    </a>
                    <span>浏览次数：{{browseNumber}}</span>
                    <div class="zx-favorite">
						<Button @click.native="collect" v-if="!isCollect">
							<Icon type="md-star" size="16"></Icon> 收藏</Button>
						<Button @click.native="collect" v-if="isCollect" class="t-green is-collect">
							<Icon type="md-star" size="16" class="t-green"></Icon> 已收藏</Button>
                        <!-- <Button type="text" @click.native="collect">
                            <Icon type="md-star" size="16"></Icon>
                            收藏
                        </Button> -->
                    </div>
                </div>
                <div v-html="content"></div>
                <Row type="flex" align="middle" v-if="columnType == '视频' || columnType  == '音频'">
                    <!-- swiper1 -->
                    <template v-if="columnType == '视频'">
                    <swiper :options="swiperOptionTop" class="gallery-top" @slideChangeTransitionEnd="clickMe" ref="swiperTop" style="padding-bottom:20px">
                        <swiper-slide v-for="(item,index) in videoList" :key="index">
                            <div style="position:relative;text-align:center">
                                <video :src="item.addr" height="400px" style="max-width: 100%" controls="controls"/>
                            </div>
                        </swiper-slide>
                        <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
                        <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
                        <div class="swiper-pagination" slot="pagination" style="bottom: 0" v-if="videoList.length > 1"></div>
                    </swiper>

                    <p style="line-height: 26px;width: 900px;font-family: PingFangSC-Regular;padding-bottom: 70px;padding-top: 30px;color: #333333;font-size: 18px;text-indent: 2em;" v-if="videoList.length>0">{{videoDescribe[rIndex]}}</p>
                    </template>
                    <template v-if="AplayerShow && songs.length>0">
                        <Col span="24" v-for="(v,key) in songs" :key="key">
                        <p style="line-height: 24px;width: 900px;padding-bottom: 10px;color: #666;font-size: 14px;text-indent: 2em;word-wrap:break-word">{{v.describe}}</p>
                        <a-player v-if="AplayerShow"  :music="songs[key]" style="width: 100%;" ></a-player>
                        </Col>
                    </template>


                </Row>
                <Row type="flex" align="middle" class="pd10">
                    <Col span="10">
                    <h3 class="xgzx ell">
                        上一篇：
                        <template v-if="upPage !== -1">
                            <a :href="['/InforMation/bookBlurb?id='+informationId+'&informationDetailId='+upPage+'&book_type=policy']" v-show="column_type ==='图书'">
                                <span style="color: #00c587;">{{upPageTitle}}</span>
                            </a>
                            <a :href="['/InforMation/policyDetail?id='+upPage+'&book_type=policy']" v-show="column_type!=='图书'">
                                <span style="color: #00c587;">{{upPageTitle}}</span>
                            </a>
                        </template>
                        <template v-if="upPage === -1">
                            <span style="color: #00c587;">没有上一篇</span>
                        </template>
                    </h3>
                    <h3 class="xgzx ell">下一篇：
                        <template v-if="nextPage !== -1">
                            <a :href="['/InforMation/bookBlurb?id='+informationNextId+'&informationDetailId='+nextPage+'&book_type=policy']" v-show="column_next_type==='图书'">
                                <span style="color: #00c587;">{{nextPageTitle}}</span>
                            </a>
                            <a :href="['/InforMation/policyDetail?id='+nextPage+'&book_type=policy']" v-show="column_next_type!=='图书'">
                                <span style="color: #00c587;">{{nextPageTitle}}</span>
                            </a>
                        </template>
                        <template v-if="nextPage === -1">
                            <span style="color: #00c587;">没有下一篇</span>
                        </template>
                    </h3>

                    </Col>
                    <collect-modal ref="collectModal" @on-init="chckeCollect" v-on:collectForm="collectForm" :itemId="itemId" :itemType="itemType" :link="link" :collectTitle="collectTitle"/>
                    <Col span="4">
                    <div class="zanBtn">
                        <Button @click="handleLike($route.query.id)" shape="circle" :class="{'like': zan}">
                            <span><i class="icon-laud" style="font-size:16px"></i></span>
                            <span>赞</span>
                            <span>|</span>
                            <span>{{thumbUpNum}}</span>
                        </Button>
                    </div>
                    </Col>
                    <Col span="2" push="8" style="text-align: right;">
                    <Button type="text" style="padding-right: 0px" @click="jubaoAdd">
                        <Icon type="md-information-circle" size="16"></Icon>
                        举报
                    </Button>
                    </Col>
                </Row>

                <div class="mb30">
                    <div class="comment-detail">
                        <div>
                            <h5>
                                <Icon type="md-text"></Icon>
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
                    <Tabs value="name1" @on-click="getMyCommnet">
                        <Tab-pane label="最新评论" name="name1">
                            <comments :data="commentList" v-if="commentList.list" @on-NextPage="handleNextPage" :dataType="'政策'"/>
                        </Tab-pane>
                        <Tab-pane label="我的评论" name="name2" v-if="loginuserinfo != ''">
                            <comments :data="myComments" v-if="myComments.list" @on-NextPage="handleOwnerNextPage" :dataType="'政策'"/>
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
        <Modal v-model="modal1">
            <p slot="header" style="text-align:center;">举报</p>
            <div>
                <Row class="jubao-list">
                    <Col span="8" v-for="(btn,index) in btnlist" :key="index">
                    <Button :class="{activeEle:active===index}" @click="addActive($event,btn.text,index)">{{btn.text}}
                    </Button>
                    </Col>
                </Row>
                <div>
                    <h3>截图证明</h3>
                    <div class="upload-inline">
                        <template v-if="reportPicList.length > 0">
                            <div class="demo-upload-list"
                                 v-for="(item,index) in reportPicList" :key="index" style="width: 78px ;height: 78px">
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
                    <textarea v-model="jubaoDetail" rows="5"
                              style="width:100%;border-radius:5px;margin-top:15px;"></textarea>
                </div>
            </div>
            <div slot="footer" style="text-align:center;">
                <Button type="primary" shape="circle" class="btnReset" style="width:200px;height:50px;" @click="submit">
                    举报
                </Button>
            </div>
        </Modal>
        <!-- 播放视频 -->
        <Modal :transfer="false" v-model="playVideoShow" @on-ok="playVideoShow=false" title="播放视频" width="600px"
               height="300px" :mask-closable="false">
            <d-player ref="playerPolicy" :video="video" :loop="false"></d-player>
        </Modal>
        <div>
        </div>
    </div>
</template>
<script>
    import api from '~api';
    import $ from 'jquery';
    import top from '~src/top.vue';
    import collectModal from '~src/components/collectModal';
    import VueDPlayer from '~components/vuedplayer';
    import VueAplayer from 'vue-aplayer';
    import sha1 from '../../page/member/proxy/sha1.js';
    import informationDetailLeft from './components/informationDetailLeft';
    import {swiper, swiperSlide} from 'vue-awesome-swiper';
    import {
        fetchDataMixin, myCommentMixin, findCommentMixin
    } from '../../service/information/policyDetailMixins';
    import comments from '~components/vui-comments/item.vue'

    export default {
        mixins: [fetchDataMixin, myCommentMixin, findCommentMixin],
        components: {
            'top-bar': top,
            collectModal,
            'd-player': VueDPlayer,
            'a-player': VueAplayer,
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
                swiperOptionThumbs: {
                    spaceBetween: 10,
                    centeredSlides: true,
                    slidesPerView: 'auto',
                    touchRatio: 0.2,
                    slideToClickedSlide: true
                },
                informationId: '',
                informationNextId: '',
                column_type: '',
                column_next_type: '',
                rIndex: 0,
                index: 0,
                link: this.$route.fullPath,
                collectShow: false,
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                portal: '',
                huifu: '',
                theme1: 'light',
                currentPage: 1,
                pageSize: 10,
                total: 0,
                content: '',
                videoList: [],
                videoDescribe: [],
                replyContent: false,
                modal1: false,
                active: 0,
                commentList: [],
                myComments: [],
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
                value13: '',
                jubaoDetail: '',
                nextTitle: '',
                detailThumbCount: 0,
                values: '',
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
                myfavorite: {
                    type: '',
                    link: '',
                    title: ''
                },
                myfollow: {
                    type: '',
                    link: '',
                    label: ''
                },
                policyComment: {
                    postId: '',
                    informationDetailId: '',
                    content: ''
                },
                modal2: false,
                user: sessionStorage.getItem(sessionStorage.getItem('key')),
                //视频
                player: {},
                video: {
                    url: ''
                },
                playVideoShow: false,
                //音频
                songs: [],
                showlrc: 3,
                AplayerShow: false,
                editerUrl: '',
                editerAccount: '',
                zan: false,
                text: '垃圾广告',
                reportPicList: [],
                action: `${this.$url.upload}/upload/up`,
                upPage: 0,
                nextPage: 0,
                upPageTitle: '',
                nextPageTitle: '',
                itemId: 0,
                itemType: 2,
                book_type: '',
                collectTitle: '',
                isCollect: false
            };
        },
        created() {
            this.book_type = this.$route.query.book_type;
            this.itemId = parseInt(this.$route.query.id)
            this.fetchData(this.$route.query.id);
            // this.findCommentItem(this.currentPage, this.$route.query.id);
            this.findComment(1);
            this.myComment(1);
            // if (this.user) {
            //     this.myComment(this.$route.query.id, this.currentPage, this.pageSize, this.loginuserinfo);
            // }
            this.getUpAndNextPage();
            this.chckeCollect()
            this.$nextTick(() => {
                const swiperTop = this.$refs.swiperTop.swiper;
                const swiperThumbs = this.$refs.swiperThumbs.swiper;
                swiperTop.controller.control = swiperThumbs;
                swiperThumbs.controller.control = swiperTop;
            });
        },
        computed: {
            swiper() {
                return this.$refs.swiperTop.swiper;
            }
        },
        mounted() {
            this.player = this.$refs.playerPolicy.dp;
//            this.getThumbNum()
        },
        methods: {
            toPortals () {
                this.$toPortals(this.editerAccount)
            },
            clickMe(e) {
                this.rIndex = this.swiper.realIndex;
            },
            hideReplyListChildren(item) {
                item.replyChildren = [];
                item.showOrHideComment = true;
            },
            checkUser() {
                if (!this.loginuserinfo) {
                    this.$refs.top.loginuser()
                    this.$Message.error('请先登录！');
                }
            },
            playVideo(url) {
                this.player.video.src = url;
                this.playVideoShow = true;
            },
            //获得收藏列表
            collectForm(e) {
                var loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')));
                this.itemId = e.classify;
                api.post('/member/report/saveCollect',
                    {
                        type: 'policy',
                        link: this.$route.fullPath,
                        title: this.title,
                        collectId: this.itemId,
                        account: loginuserinfo.loginAccount
                    }
                ).then(response => {
                    this.flag = response.data;
                    if (this.flag == '1') {
                        alert('收藏成功!');
                    } else {
                    }
                });

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
            // 所有的评论
            findComment(currentPage) {
                this.$api.post('/member/columnSettings/findComments',{
                        information_detail_id:this.itemId,
                        pageNum:currentPage,
                        type:'policy',
                        account:''
                    }).then(response=>{
                        if(response.code == 200){
                            this.commentList = response.data
                            this.tote = response.data.total
                        }
                })
            },
            //最新评论分页
            handleNextPage(e){
                this.findComment(e)
                this.myComment(1)
            },
            //我的评论分页
            handleOwnerNextPage(e){
                this.myComment(e)
                this.findComment(1)
            },
            //默认显示我的评论
            myComment(currentPage) {
                this.$api.post('/member/columnSettings/findComments',{
                        information_detail_id:this.itemId,
                        account:this.loginuserinfo.loginAccount,
                        pageNum:currentPage,
                        type:'policy'
                    }).then(response=>{
                        if(response.code == 200){
                            this.myComments = response.data
                        }
                })
            },
            pageChange: function (event) {
                alert(event);
            },
            reply(id) {
                if (this.huifu === '' || this.huifu === null) {
                    this.$Message.info('评论内容不能为空！');
                    return;
                }
                api.post('/member/policy/saveHuiFu',
                    {
                        dynamicId: id,
                        comment: this.huifu,
						account: this.$user.loginAccount
                    }
                ).then(response => {
                    var flag = response.data;
                    if (flag === 1) {
                        this.findComment(1);
                        this.myComment(1);
                        this.huifu = '';
                    } else {
                        this.$Message.error('回复失败!');
                    }
                });
            },
            addActive(event, value, index) {
                this.text = value;
                this.active = index;
            },
            getUpAndNextPage() {
                let type = this.book_type;
                api.get('/member/policy/upAndNextPage/' + this.$route.query.id)
                    .then(resp => {
                        if (200 === resp.code) {
                            this.upPage = resp.data.upPage;
                            this.nextPage = resp.data.nextPage;
                            if (-1 !== this.upPage) {
                                api.get('/member/policy/findPolicyDetail?id=' + this.upPage)
                                    .then(response => {
                                        this.upPageTitle = response.data.title;
                                    });
                                this.$api.post('/member/inforMation/findInformationByDetailID', {
                                    information_detail_id: this.upPage,
                                    book_type: 'policy'
                                }).then(response => {
                                    let result = response.data;
                                    if (result != '') {
                                        this.informationId = result.id;
                                        this.column_type = result.column_type;
                                    }
                                }).catch(error => {
                                    console.error(error);
                                });
                            }
                            if (-1 !== this.nextPage) {
                                api.get('/member/policy/findPolicyDetail?id=' + this.nextPage)
                                    .then(response => {
                                        this.nextPageTitle = response.data.title;
                                    });
                                this.$api.post('/member/inforMation/findInformationByDetailID', {
                                    information_detail_id: this.nextPage,
                                    book_type: 'policy'
                                }).then(response => {
                                    let result = response.data;
                                    if (result != '') {
                                        this.informationNextId = result.id;
                                        this.column_next_type = result.column_type;
                                    }
                                }).catch(error => {
                                    console.error(error);
                                });
                            }

                        }
                    });

            },
            /**
             *验证是否登录
             * */
            showHuiFuChi(item) {
                if (this.loginuserinfo) {
                    item.showReplychi = !item.showReplychi;
                } else {
                    //alert("请先登录！")
                    this.$Message.error('请先登录!');
                }
            },
            /**
             * 详情的点赞
             * */
            handleLike(id) {
                if (this.loginuserinfo) {
                    api.post('/member/thumb/detailThumbAdd', {
                        account: this.loginuserinfo.loginAccount,
                        detailId: parseInt(id),
                        detailType: 'policy'
                    }).then(response => {
                        if (response.code === 200) {
                            if (response.data === 'exist') {
                                this.$Message.error('您已点赞');
                            } else {
                                this.$Message.success('点赞成功');
                                this.thumbUpNum++;
                            }
                        }
                    });
                } else {
                    this.$Message.error('请先登录');
                }

            },
            getMyCommnet(item) {
                if (item === 'name2') {
                    //再查一遍我的评论
                    this.myComment(1);
                } else {
                    this.findComment(1);
                }
            },
            jubaoAdd() {
                if (this.loginuserinfo != null) {
                    api.post('/member/report/reportIsExist', {
                        link: this.$route.fullPath,
                        account: this.loginuserinfo.loginAccount
                    }).then(resp => {
                        if (200 === resp.code) {
                            if (1 === resp.data) {
                                this.$Message.error('您已举报,请勿重复举报!');
                            } else {
                                // 弹出举报窗口
                                this.modal1 = true;
                            }
                        }
                    });
                } else {
                    this.$Message.error('请先登录!');
                }
            },
            submit() {
                api.post('/member/report/saveReport', {
                    type: this.text,
                    link: this.$route.fullPath,
                    reportPicList: this.reportPicList,
                    description: this.jubaoDetail
                }).then(response => {
                    if (response.data === 1) {
                        this.$Message.success('举报成功!');
                        this.modal1 = false;
                    } else {
                        this.$Message.error('举报失败!');
                    }
                });
            },
            //举报图片
            //移除图片
            handleRemove(item) {
                this.reportPicList.splice(this.reportPicList.indexOf(item), 1);
            },
            //上传图片
            handleSuccess(response, file, fileList) {
                if (this.reportPicList.length === 3) {
                    this.$Message.error('举报图片不能超过3个');
                } else {
                    var picname = response.data.picName;
                    this.reportPicList.push(picname);
                }
            },
            follow() {
                if (this.loginuserinfo != null) {
                    api.post('/member/report/saveFollow',
                        {
                            type: 'inforMation',
                            link: this.$route.fullPath,
                            label: this.label
                        }
                    ).then(response => {
                        this.flag = response.data;
                        if (this.flag == '1') {
                            alert('关注成功!');

                        } else {
                            alert('已关注!');
                        }
                    }).catch(function (error) {
                        alert('关注失败!');
                    });
                } else {
                    // alert("请先登录！")
                    this.$Message.error('请先登录!');
                }
            },
            routeTo(e) {
                if (e == '/portal') {
                    this.portal = this.$url.shop + '/center/gateway.htm?uid=' + this.loginuserinfo.uniqueId;
                    window.open(this.portal);
                } else {
                    this.$router.push(e);
                }
            }
        }
    };
</script>