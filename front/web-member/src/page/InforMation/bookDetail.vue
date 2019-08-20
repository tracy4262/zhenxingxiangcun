<template>
    <div class="wrapper">
        <top :address="false" active="1"  ref="top"/>
        <section class="layouts">
            <Row class="pt20">
                <Col span="4">
                &nbsp;
                  <Affix :offset-top="74">
                    <vuiBookCata @on-get-data="handleGetData"></vuiBookCata>
                  </Affix>
                </Col>
                <Col span="18" offset="1">
                  <h4 class="mb20 tc"  style="color:#4a4a4a;"> {{title}} </h4>
                  <Row class="mb10">
                    <Col span="12">
                      <p style="color:#4a4a4a;font-size:15px;font-weight:bold;margin-left:10px;">出版发行：{{publish}}</p>
                      <p class="mt5 t-grey" style="margin-top:15px;margin-left:10px;">
                        <span class="mr20" style="color:#999999;"> 版次：{{edition}} </span>
                        <span class="mr20" style="color:#999999;"> 印张：{{sheet}} </span>
                        <span style="color:#999999;"> 开版：{{broadsheet}} 开 </span>
                      </p>
                    </Col>
                    <Col span="12" class="tr">
                      <p style="color:#4a4a4a;font-size:15px;font-weight:bold;">出版时间：{{pub_date}} <span class="ml20">作者： {{author}}</span></p>
                      <p class="mt5 t-grey" style="margin-top:15px;">
                        <span class="mr20" style="color:#999999;;margin-top:15px;"> 字数：{{word_count}}千字</span>
                        <span style="color:#999999;;margin-top:15px;"> 纸张：{{paper}} </span>
                      </p>
                    </Col>
                  </Row>
                  <divider solid></divider>
                <div style="margin-top:15px">
                    <span style="margin-left:10px;color:#999999;">  {{informationBookDetailTitles}} </span>
                    <span style="color:#00c587"> {{informationBookDetailTitle}} </span>
                    <div class="zx-time clear" style="margin-top:10px;margin-left:10px;color:#7A7A7A;">
                        <span>{{createTime}}</span>
                        <span>{{source}}</span>
                        <a  @click="toPortals" :title="editer" v-if="editer"  class="mr10">
                            <span v-if="editer.length > 20">发布人：{{editer.substring(0,15) }}...</span>
                            <span v-else>发布人：{{editer}}</span>
                        </a>
                        <span>浏览次数：{{browseNumber}}</span>
                        <div class="zx-favorite">
                            <Button @click.native="collect" v-if="!isCollect">
                                <Icon type="md-star" size="16"></Icon> 收藏</Button>
                            <Button @click.native="collect" v-if="isCollect" class="t-green is-collect">
                                <Icon type="md-star" size="16" class="t-green"></Icon> 已收藏</Button>
                            <!-- <Button type="text" @click.native="collect"><Icon type="star" size="16"></Icon> 收藏</Button> -->
                        </div>
                    </div>
                    <div style="margin-left:10px;color:#636363;text-indent:2em;font-weight:bold;margin-top:-15px; " v-html="informationBookDetailContent"></div>
                    <!--<div style="margin-left:10px;color:#4a4a4a">
                        &lt;!&ndash;<p style="text-indent:2em;line-height:2;font-weight:bold;">
                            {{informationBookDetailContent}}
                         </p>&ndash;&gt;
                    </div>-->
                </div>
                <div style="border: solid 1px #DEDEDE;margin-top: 20px;" v-show="bookFile != '' && format === 'pdf'">
                    <Row style="line-height: 60px;border-bottom: solid 1px #DEDEDE;">
                        <Col span="12">
                        <span style="margin-left: 20px;" >PDF阅读模式</span>
                        </Col>
                        <Col span="12" style="text-align: right;">
                        </Col>
                    </Row>
                    <Row>
                        <template v-if="!isTXT && format === 'pdf'">
                            <vuePdfjs ref="vuepdf"  viewer="../static/" :url="bookFile"></vuePdfjs>
                        </template>


                    </Row>
                </div>
                <Row type="flex" align="middle" class="pd10">
                    <Col span="10">
                    <h3 class="xgzx">
                        上一篇：
                        <template v-if="upPage !== -1 && book_type ==='information'">
                        <a :href="['/InforMation/bookBlurb?id='+informationId+'&informationDetailId='+upPage+'&book_type=information']" v-show="column_type==='图书'"><span style="color: #00c587;">{{upPageTitle}}</span></a>
                        <a :href="['/InforMation/findInforMationDetail?id='+upPage+'&book_type=information']" v-show="column_type!=='图书'"><span style="color: #00c587;">{{upPageTitle}}</span></a>
                        </template>
                        <template v-if="upPage !== -1 && book_type ==='knowledge'">
                            <a :href="['/InforMation/bookBlurb?id='+informationId+'&informationDetailId='+upPage+'&book_type=knowledge']" v-show="column_type ==='图书'"><span style="color: #00c587;">{{upPageTitle}}</span></a>
                            <a :href="['/InforMation/knowledgeDetail?id='+upPage+'&book_type=knowledge']" v-show="column_type!=='图书'"><span style="color: #00c587;">{{upPageTitle}}</span></a>
                        </template>
                        <template v-if="upPage !== -1 && book_type ==='policy'">
                            <a :href="['/InforMation/bookBlurb?id='+informationId+'&informationDetailId='+upPage+'&book_type=policy']" v-show="column_type ==='图书'"><span style="color: #00c587;">{{upPageTitle}}</span></a>
                            <a :href="['/InforMation/policyDetail?id='+upPage+'&book_type=policy']" v-show="column_type!=='图书'"><span style="color: #00c587;">{{upPageTitle}}</span></a>
                        </template>
                        <template v-if="upPage === -1">
                            <span style="color: #00c587;">没有上一篇</span>
                        </template>
                    </h3>
                    <h3 class="xgzx">下一篇：
						<template v-if="nextPage !== -1 && book_type ==='information'">
                        <a :href="['/InforMation/bookBlurb?id='+informationNextId+'&informationDetailId='+nextPage+'&book_type=information']" v-show="column_next_type==='图书'"><span style="color: #00c587;">{{nextPageTitle}}</span></a>
                        <a :href="['/InforMation/findInforMationDetail?id='+nextPage+'&book_type=information']" v-show="column_next_type!=='图书'"><span style="color: #00c587;">{{nextPageTitle}}</span></a>
                        </template>
                        <template v-if="nextPage !== -1 && book_type ==='knowledge'">
                            <a :href="['/InforMation/bookBlurb?id='+informationNextId+'&informationDetailId='+nextPage+'&book_type=knowledge']" v-show="column_next_type==='图书'"><span style="color: #00c587;">{{nextPageTitle}}</span></a>
                            <a :href="['/InforMation/knowledgeDetail?id='+nextPage+'&book_type=knowledge']" v-show="column_next_type!=='图书'"><span style="color: #00c587;">{{nextPageTitle}}</span></a>
                        </template>
                        <template v-if="nextPage !== -1 && book_type ==='policy'">
                         <a :href="['/InforMation/bookBlurb?id='+informationNextId+'&informationDetailId='+nextPage+'&book_type=policy']" v-show="column_next_type==='图书'"><span style="color: #00c587;">{{nextPageTitle}}</span></a>
                        <a :href="['/InforMation/policyDetail?id='+nextPage+'&book_type=policy']" v-show="column_next_type!=='图书'"><span style="color: #00c587;">{{nextPageTitle}}</span></a>
                        </template>
                        <template v-if="nextPage === -1">
                            <span style="color: #00c587;">没有下一篇</span>
                        </template>
                    </h3>
                    </Col>
                    <collect-modal ref="collectModal" @on-init="chckeCollect" :itemId="itemId" :itemType="itemType" :link="link" :collectTitle="collectTitle"></collect-modal>
                    <Col span="4">
                    <div class="zanBtn">
                        <Button @click="handleLike($route.query.id)" shape="circle" :class="{'like': zan}">
                            <span ><i class="icon-laud" style="font-size:16px"></i></span>
                            <span>赞</span>
                            <span>|</span>
                            <span>{{thumbUpNum}}</span>
                        </Button>
                    </div>
                    </Col>
                    <Col span="2" push="8">
                    <Button type="text" @click="jubaoAdd"><Icon type="alert-circled" size="16"></Icon> 举报</Button>
                    </Col>
                </Row>              
                <div class="mb30">
                    <div class="comment-detail">
                        <div>
                            <h5 ><Icon type="md-text"> </Icon> &nbsp;  网友评价 </h5>
                            <div class="comment-count">{{tote}} 评论</div>
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
                            <comments class="pd20" :data="commentList" v-if="commentList.list" @on-NextPage="handleNextPage" :dataType="book_type"/>
                        </Tab-pane>
                        <Tab-pane label="我的评论" name="name2" v-if="loginuserinfo != ''">
                            <comments :data="ownerlist" v-if="ownerlist.list" @on-NextPage="handleOwnerNextPage" :dataType="book_type"/>
                        </Tab-pane>
                    </Tabs>
                </div>
                </Col>
            </Row>
            <!--举报的弹框-->
            <Modal v-model="jubaoClick">
                <p slot="header" style="text-align:center;">举报</p>
                <div>
                    <Row class="jubao-list">
                        <Col span="8" v-for="(btn,index) in btnlist" :key="index">
                        <Button :class="{activeEle:active===index}" @click="addActive($event,btn.text,index)">{{btn.text}}</Button>
                        </Col>
                    </Row>	<h3>截图证明</h3>
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
                    <div>
                        <h3>举报详情</h3>
                        <textarea v-model="jubaoDetail" rows="5" style="width:100%;border-radius:5px;margin-top:15px;"></textarea>
                    </div>
                </div>
                <div slot="footer" style="text-align:center;">
                    <Button type="primary" shape="circle" class="btnReset" style="width:200px;height:50px;" @click="submit">举报</Button>
                </div>
            </Modal>
        </section>
        <foot></foot>
    </div>
</template>
<script>
    import api from '~api'
    import $ from 'jquery'
    import top from '../../top'
    import foot from '../../foot'
    import mallNewTitle from '~components/mallNewTitle'
    import divider from '~components/divider'
    import collectModal from '~components/collectModal'
    import vuiBookCata from '~components/vuiBookCata'
    import vuePdfjs from 'vue-pdfjs'
    import comments from '~components/vui-comments/item.vue'

    export default {
        components: {
            top,
            foot,
            divider,
            collectModal,
            mallNewTitle,
            vuiBookCata,
            vuePdfjs,
            comments
        },
        data() {
            return {
                bookFile:'',
                format:'',
                //图书
                text: '',
                process: 1,
                title: '',
                abstracts:'',
                author: '',
                edition: '',
                publish: '',
                sheet: '',
                distribution: '',
                broadsheet: '',
                print_time: '',
                word_count: '',
                pub_date: '',
                paper: '',
                label: '',
                informationDetail:[],
                informationBookInfo:[],
                informationBookDetail: [],
                informationBookDetailTitle:'',
                informationBookDetailTitles:'',
                informationBookDetailContent:'',
                informationId:'',
                informationNextId:'',
                column_type:'',
                column_next_type:'',
                itemId:0,
                itemType:5,
                link:this.$route.fullPath,
                action: `${this.$url.upload}/upload/up`,
                text:'垃圾广告',
                //举报图片
                reportPicList:[],
                zan: false,
                huifu: '', //回复内容
                ownerlist: [], //我的评论
                collectShow: false,
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                modal2: false,
                currentPage: 1,
                pageSize: 10,
                total: 0,
                content: '',
                jubaoClick: false,
                active: 0,
                commentList: [],
                value: '',
                values: '',
                thumbUpNum: 0,
                tote: 0,
                createTime: '',
                editerUrl: '',
                source: '',
                browseNumber: '',
                editer: '',
                formInline: {
                    user: '',
                    password: ''
                },
                ruleInline: {
                    user: [{
                        required: true,
                        message: '请填写用户名',
                        trigger: 'blur'
                    }],
                    password: [{
                        required: true,
                        message: '请填写密码',
                        trigger: 'blur'
                    },
                        {
                            type: 'string',
                            min: 6,
                            message: '密码长度不能小于6位',
                            trigger: 'blur'
                        }
                    ],
                    /*methods: {
                        del() {
                            this.modal_loading = true
                            setTimeout(() => {
                                this.modal_loading = false
                                this.modal2 = false
                                this.$Message.success('删除成功')
                            }, 2000)
                        },
                        ok() {
                            this.$Message.info('点击了确定')
                        },
                        cancel() {
                            this.$Message.info('点击了取消')
                        }
                    }*/
                },
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
                jubaoDetail: '',
                upPage:0,
                nextPage:0,
                upPageTitle:'',
                nextPageTitle:'',
                detailThumbCount :0, // 点赞数
                book_type:'',
                isTXT:false,
                collectTitle:'',
                detailId:'',
                isCollect: false
            }
        },
        created() {
            this.book_type =  this.$route.query.book_type;
			this.detailId =  this.$route.query.id
            this.showBookDetail();
            this.findComment(1)
            this.findOwnerComment(1)
            this.getUpAndNextPage()
            this.getThumbNum()
            // 看是否有收藏过
            this.chckeCollect()
        },
        methods: {
            toPortals () {
                this.$toPortals(this.editerUrl)
            },
            showBookDetail(){
                this.$api.post('/member/inforMation/findInFormationBookInfo', {id:this.$route.query.informationId,book_type:this.book_type,flag:1}).then(response => {
                    let result = response.data
                    if(result!=''){
                        this.title = result.infomation_data.title;
                        this.collectTitle = result.infomation_data.title;
                        this.abstracts = result.infomation_data.abstracts;
                        this.author = result.book_info_data.author;
                        this.edition = result.book_info_data.edition;
                        this.publish = result.book_info_data.publish;
                        this.sheet = result.book_info_data.sheet;
                        this.distribution = result.book_info_data.distribution;
                        this.broadsheet = result.book_info_data.broadsheet;
                        this.print_time = result.book_info_data.print_time;
                        this.word_count = result.book_info_data.word_count;
                        let time = this.moment(result.book_info_data.pub_date).format('YYYY-MM-DD');
                        let date  = time.split("-");
                        this.pub_date = date[0]+'年'+date[1]+'月'+date[2]+'日';
                        this.paper = result.book_info_data.paper;
                        this.cover_photo =  result.book_info_data.cover_photo;
                        let bookname = result.book_info_data.label;
                        this.label = bookname.split(",");
                        this.informationBookDetail = result.book_detail_data;
                        this.createTime = response.data.detail_data.create_time
                        this.source = response.data.detail_data.source
                        this.browseNumber = response.data.detail_data.browse_number
                        this.editer = response.data.detail_data.editer
                        this.informationBookDetailTitle = response.data.book_detail_data[0].children[0].title;
                        let content = response.data.book_detail_data[0].children[0].content;
                        this.informationBookDetailContent= response.data.book_detail_data[0].children[0].content;
                        this.informationBookDetailTitles = response.data.book_detail_data[0].title;
                        this.thumbUpNum = response.data.detail_data.thumb_up_num
                        // 获取标准文件进行显示 如果是照片的直接显示 如果是PDF则调用PDF的插件进行显示
                        // 如果是文档类型则调用文档转PDF接口进行转换 再显示
                        this.bookFile= response.data.book_detail_data[0].children[0].file;
                        let a = this.bookFile.lastIndexOf(".")
                        let b = this.bookFile.length
                        let format = this.bookFile.substring(a, b)
                        if (format === '.jpg' || format === '.jpeg') {
                            this.format = 'pic'
                        } else if (format === '.pdf') {
                            this.format = 'pdf'
                        } else if (format === '.doc' || format === '.docx') {
                            // 先请求接口返回pdf路径 将返回路径赋值给bookFile即可
                            this.format = 'pdf'
                        }
                        this.editerUrl = response.data.detail_data.account
                    }
                }).catch(error => {
                    console.error(error)
                })
            },
          // 点击目录取数据
            handleGetData (title,data) {
                this.bookFile = '';
                this.informationBookDetailTitle = data.title;
                this.informationBookDetailContent= data.content;
                this.informationBookDetailTitles = title;
                let a = data.file.lastIndexOf(".")
                let b = data.file.length
                let format = data.file.substring(a, b)
                if (format === '.jpg' || format === '.jpeg' || format  === '.png') {
                    this.format = 'pic'
                } else if (format === '.pdf') {
                    this.format = 'pdf'
                } else if (format === '.doc' || format === '.docx') {
                    // 先请求接口返回pdf路径 将返回路径赋值给bookFile即可
                    this.format = 'pdf'
                }
                if(data.file!=''){
                    this.bookFile= data.file;
                    this.$refs.vuepdf.loadPdf(data.file);
                }else{
                    this.bookFile = '';
                }

            },
            hideReplyListChildren (item) {
                item.replyChildren = []
                item.showOrHideComment = true
            },
            checkUser() {
                if(!this.loginuserinfo) {
					this.$refs.top.loginuser()
					this.$Message.error('请先登录！')
                }
            },
            getUpAndNextPage(){
                let type = this.book_type
                if(type==='information'){
                    api.get('/member/inforMation/upAndNextPage/'+this.$route.query.id)
                        .then(resp => {
                            if(200 === resp.code){
                                this.upPage = resp.data.upPage
                                this.nextPage =resp.data.nextPage
                                if(-1 !== this.upPage){
                                    api.get('/member/inforMation/findInforMationDetail?id=' + this.upPage)
                                        .then(response => {
                                            this.upPageTitle = response.data.title
                                        })
                                    this.$api.post('/member/inforMation/findInformationByDetailID', {information_detail_id:this.upPage,book_type:'information'}).then(response => {
                                        let result = response.data
                                        if(result!=''){
                                            this.informationId= result.id
                                            this.column_type = result.column_type
                                        }
                                    }).catch(error => {
                                        console.error(error)
                                    })

                                }
                                if(-1 !== this.nextPage){
                                    api.get('/member/inforMation/findInforMationDetail?id=' + this.nextPage)
                                        .then(response => {
                                            this.nextPageTitle = response.data.title
                                        })
                                    this.$api.post('/member/inforMation/findInformationByDetailID', {information_detail_id:this.nextPage,book_type:'information'}).then(response => {
                                        let result = response.data
                                        if(result!=''){
                                            this.informationNextId= result.id
                                            this.column_next_type = result.column_type
                                        }
                                    }).catch(error => {
                                        console.error(error)
                                    })

                                }

                            }
                        })
                }else if(type==='knowledge'){
                    api.get('/member/knowLege/upAndNextPage/'+this.$route.query.id)
                        .then(resp => {
                            if(200 === resp.code){
                                this.upPage = resp.data.upPage
                                this.nextPage =resp.data.nextPage
                                if(-1 !== this.upPage){
                                    api.get('/member/knowLege/findKnowLedgeDetail?id=' + this.upPage)
                                        .then(response => {
                                            this.upPageTitle = response.data.title
                                        })
                                    this.$api.post('/member/inforMation/findInformationByDetailID', {information_detail_id:this.upPage,book_type:'knowledge'}).then(response => {
                                        let result = response.data
                                        if(result!=''){
                                            this.informationId= result.id
                                            this.column_type = result.column_type
                                        }
                                    }).catch(error => {
                                        console.error(error)
                                    })

                                }
                                if(-1 !== this.nextPage){
                                    api.get('/member/knowLege/findKnowLedgeDetail?id=' + this.nextPage)
                                        .then(response => {
                                            this.nextPageTitle = response.data.title
                                        })
                                    this.$api.post('/member/inforMation/findInformationByDetailID', {information_detail_id:this.nextPage,book_type:'knowledge'}).then(response => {
                                        let result = response.data
                                        if(result!=''){
                                            this.informationNextId= result.id
                                            this.column_next_type = result.column_type
                                        }
                                    }).catch(error => {
                                        console.error(error)
                                    })

                                }

                            }
                        })
                }else if(type==='policy'){
                    api.get('/member/policy/upAndNextPage/'+this.$route.query.id)
                        .then(resp => {
                            if(200 === resp.code){
                                this.upPage = resp.data.upPage
                                this.nextPage =resp.data.nextPage
                                if(-1 !== this.upPage){
                                    api.get('/member/policy/findPolicyDetail?id=' + this.upPage)
                                        .then(response => {
                                            this.upPageTitle = response.data.title
                                        })
                                    this.$api.post('/member/inforMation/findInformationByDetailID', {information_detail_id:this.upPage,book_type:'policy'}).then(response => {
                                        let result = response.data
                                        if(result!=''){
                                            this.informationId= result.id
                                            this.column_type = result.column_type
                                        }
                                    }).catch(error => {
                                        console.error(error)
                                    })
                                }
                                if(-1 !== this.nextPage){
                                    api.get('/member/policy/findPolicyDetail?id=' + this.nextPage)
                                        .then(response => {
                                            this.nextPageTitle = response.data.title
                                        })
                                    this.$api.post('/member/inforMation/findInformationByDetailID', {information_detail_id:this.nextPage,book_type:'policy'}).then(response => {
                                        let result = response.data
                                        if(result!=''){
                                            this.informationNextId= result.id
                                            this.column_next_type = result.column_type
                                        }
                                    }).catch(error => {
                                        console.error(error)
                                    })
                                }

                            }
                        })
                }
            },
            //默认显示最新评论
            findComment(currentPage) {
                this.$api.post('/member/columnSettings/findComments',{
                        information_detail_id:this.detailId,
                        account:'',
                        pageNum:currentPage,
                        type:this.book_type
                    }).then(response=>{
                        console.log(response)
                        if(response.code == 200){
                            this.commentList = response.data
                            this.tote = response.data.total
                        }
                })
            },
            //最新评论分页
			handleNextPage(e){
				this.findComment(e)
				this.findOwnerComment(1)
			},
			//我的评论分页
			handleOwnerNextPage(e){
				this.findOwnerComment(e)
				this.findComment(1)
			},
            //默认显示我的评论
            findOwnerComment(currentPage) {
                this.$api.post('/member/columnSettings/findComments',{
                        information_detail_id:this.detailId,
                        account:this.loginuserinfo.loginAccount,
                        pageNum:currentPage,
                        type:this.book_type
                    }).then(response=>{
                        console.log(response)
                        if(response.code == 200){
                            this.ownerlist = response.data
                        }
                })
            },
            /**
             *验证是否登录
             * */
            showHuiFuChi(item) {
                if(this.loginuserinfo) {
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
                let type = this.book_type
                let url ='';
                if(type==='information'){
                    url = '/member/inforMation/saveHuiFu';
                }else if(type==='knowledge'){
                    url = '/member/knowLege/saveHuiFu';
                }else if(type==='policy'){
                    url = '/member/policy/saveHuiFu';
                }
                api.post(
                    url, {
                        dynamicId: id,
                        comment: this.huifu,
                        account: this.$user.loginAccount
                    }
                ).then(response => {
                    var flag = response.data
                    if(flag === 1) {
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
                if(this.loginuserinfo){
                    let type = this.book_type
                    api.post('/member/thumb/detailThumbAdd',{
                        account: this.loginuserinfo.loginAccount,
                        detailId:parseInt(id),
                        detailType:type
                    } ).then(response => {
                        if (response.code === 200) {
                            if(response.data === 'exist'){
                                this.$Message.error('您已点赞')
                            } else{
                                this.$Message.success('点赞成功')
                                this.thumbUpNum++
                            }
                        }
                    })
                }else {
                    this.$Message.error('请先登录')
                }

                this.getThumbNum()
            },
            getThumbNum() {
                let type = this.book_type
                // 查询点赞数
                api.post('/member/thumb/detailThumbCount',{
                    detailId:parseInt(this.$route.query.id),
                    detailType:type
                } ).then(response => {
                    if (response.code === 200) {
                        this.detailThumbCount =response.data
                    }
                })
            },
            getMyCommnet(item){
                if(item === 'name2'){
                    //再查一遍我的评论
                    this.findOwnerComment(1)

                }else {
                    this.findComment(1)
                }
            },
            addActive(event, value,index) {
                this.text = value
                this.active = index
                this.jubaoClick =true
            },
            //举报
            submit() {
                api.post('/member/report/saveReport', {
                    type: this.text,
                    link: this.$route.fullPath,
                    reportPicList : this.reportPicList,
                    description: this.jubaoDetail
                }).then(response => {
                    if(response.data=== 1) {
                        this.$Message.success('举报成功!')
                        this.jubaoClick = false
                    }else {
                        this.$Message.error('举报失败!')
                    }
                })
            },
            //举报图片
            //移除图片
            handleRemove(item){
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
                    if (this.isCollect) {
                        this.$Message.error('请勿重复收藏')
                    } else {
                        this.$refs['collectModal'].show = true
                    }
                } else {
                    this.$Message.error('请先登录!')
                }
            },
            follow() {
                let type = this.book_type
                if(this.loginuserinfo != null) {
                    api.post('/member/report/saveFollow', {
                        type: type,
                        link: this.$route.fullPath,
                        label: this.label
                    }).then(response => {
                        this.flag = response.data
                        if(this.flag == '1') {
                            alert("关注成功!")

                        } else {
                            alert("已关注!");
                        }
                    })
                } else {
                    alert("请先登录！")
                }
            },
            /*routeTo(e) {
                if(e == '/portal') {
                    this.portal = this.$url.shop + '/center/gateway.htm?uid=' + this.loginuserinfo.uniqueId
                    window.open(this.portal)
                } else {
                    this.$router.push(e)
                }
            },*/
        }
    }
</script>
<style scoped>
    .xgzx{
        font-size:14px;
        color:#333;
        margin-bottom:10px;
        font-weight:normal;
    }
    .book-detail {
         position: relative;
         border: 1px solid #f8f8f8;
         border-top: 3px solid #00c587;
         overflow: hidden;
     }
</style>
