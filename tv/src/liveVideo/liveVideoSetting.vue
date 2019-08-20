<template>
    <div>
        <!-- <top :address="false" /> -->
       <div class="ww">
            <div>
                <div class="live-video-setting">
                    <template>
                        <Tabs :animated="false">
                            <Tab-pane label="直播设置" key="key1">
                                <div class="from">
                                <i-form :model="formItem" :label-width="100" :rules="ruleInline" ref="formItem">
                                    <Form-item label="房间标题" prop="liveName">
                                        <i-input v-model="formItem.liveName" placeholder="请输入"></i-input>
                                    </Form-item>
                                    <Form-item label="房间描述"  prop="liveDescribe">
                                        <i-input type="textarea" :autosize="{minRows: 5,maxRows: 5}" v-model="formItem.liveDescribe" placeholder="请输入"></i-input>
                                    </Form-item>
                                    <!-- <Form-item label="开播提醒">
                                        <div class="point">
                                            <i-input :value.sync="formItem.romTitle" placeholder="请输入"></i-input>
                                            <i-button class="point-btn" type="ghost">开播提醒</i-button>
                                        </div>                                        
                                    </Form-item> -->
                                    <Form-item label="房间封面" prop="liveImage">
                                            <Upload :action="action"                                
                                                ref="upload" :show-upload-list="false"
                                                name="upfile"
                                                :max-size="1024"
                                                :on-success="handleSuccess"
                                                :on-exceeded-size="handleMaxSize"
                                                :before-upload="handleBeforeUpload"
                                                type="drag"
                                                :format="format"
                                            >
                                                <i-button icon="ios-cloud-upload-outline">上传图片</i-button>
                                            </Upload>
                                                <i-button icon="ios-cloud-upload-outline" style="vertical-align: top;" @click.native="handlePhotoSelectorModal('identityPhoto')">从文件管理上传</i-button>
                                                <i-button class="size" type="text">（图片尺寸大小290*160px）</i-button>
                                            <div class="demo-upload-list" v-for="item in uploadList">
                                                <template v-if="item.status === 'finished'">
                                                    <img :src="'http:'+item.response.data.picName">
                                                    <div class="demo-upload-list-cover" >                                                        
                                                        <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
                                                    </div>
                                                </template>
                                                <template v-else>
                                                    <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
                                                </template>
                                            </div>
                                        <template v-if="uploadList.length == 0">
                                            <div class="demo-upload-list">
                                                <img src="../../static/img/avatar.png">
                                            </div>
                                        </template>
                                    </Form-item>
                                    <Form-item label="房间分类" prop="liveCategoryId">
                                        <Tooltip :content="listStr"  placement="top">
                                            <i-button type="text" class="roomClass">{{listStr}}</i-button>
                                        </Tooltip>
                                        <i-button @click="onchange">更改</i-button>
                                    </Form-item>
                                    <Form-item>
                                        <div class="btn">
                                            <i-button type="primary" @click="startLive()" shape="circle" size="large">开始直播</i-button>                                    
                                            <i-button style="margin-left: 8px" size="large" shape="circle" @click="goback">取消</i-button>
                                        </div>                                
                                    </Form-item>
                                </i-form>                       
                                    
                                </div>
                            </Tab-pane>
                        </Tabs>
                    </template>
                </div>
                <Modal
                v-model="roomClass"
                width="600">
                <p slot="header" >
                    <span>房间分类</span>
                </p>
                <div style="text-align:center">
                    <div class="ivu-room-class">
                        <Row>
                            <i-col span="8">
                                <i-button type="primary">所在地区</i-button>
                            </i-col>
                            <i-col span="16">
                                <Cascader :data="locationList" change-on-select v-model="roomClassData.area" :render-format="formats" :load-data="loadPositionDatas"></Cascader>
                            </i-col>
                        </Row>
                        <Row>
                            <i-col span="8">
                                <i-button type="primary">关联物种</i-button>
                            </i-col>
                            <i-col span="16">
                                <Input v-model="roomClassData.species" readonly @on-focus="handleFilterModal('speciFilter')" />
                            </i-col>
                        </Row>
                        <Row>
                            <i-col span="8">
                                <i-button type="primary">相关行业</i-button>
                            </i-col>
                            <i-col span="16">
                                <Input v-model="roomClassData.trade" readonly @on-focus="handleFilterModal('tradeFilter')" />
                            </i-col>
                        </Row>
                        <!-- <Row>
                            <i-col span="8">
                                <i-button type="primary">相关产品</i-button>
                            </i-col>
                            <i-col span="16">
                                <i-input v-model="roomClassData.product" placeholder="请输入"></i-input>
                            </i-col>
                        </Row>
                        <Row>
                            <i-col span="8">
                                <i-button type="primary">相关服务</i-button>
                            </i-col>
                            <i-col span="16">
                                <Input v-model="roomClassData.sevrice" readonly @on-focus="handleFilterModal('serviceFilter')" /> 
                            </i-col>
                        </Row> -->
                    </div>              
           
                </div>
                <div slot="footer">
                    <i-button type="text" size="large" @click="close">取消</i-button>
                    <i-button type="primary" size="large"  @click="asyncOK">确定</i-button>
                </div>
                </Modal>
                <Modal title="查看图片" :visible.sync="visible">
                    <img :src="'https://o5wwk8baw.qnssl.com/' + imgName + '/large'" v-if="visible" style="width: 100%">
                </Modal>

                <!-- 相关行业 -->
                <!-- <vui-filter
                ref="tradeFilter"
                :simple="true"
                :cols="2"
                :resultDatas="tradeResultDatas"
                @on-search="handleTradeSearch"
                @on-get-result="handleGetTradeResult" /> -->

                <!-- 相关行业tradeFilter -->
                <vui-filter
                ref="tradeFilter"
                :simple="true"
                :pageShow="true"
                :transfer="true"
                :total="total2"
                :pageCur="pageCur2"
                :resultDatas="tradeResultDatas"
                @on-search="handleTradeSearch"
                @on-page-change="handleTradePageChange"
                @on-get-result="handleGetTradeResult"/>

                <!-- 相关服务 -->
                <vui-filter
                ref="serviceFilter"
                :simple="true"
                :cols="2"
                :transfer="true"
                :resultDatas="serviceResultDatas"
                @on-search="handleServiceSearch"
                @on-get-result="handleGetServiceResult" />  

                <!-- 相关物种 -->
                <vui-filter
                ref="speciFilter"
                :cols="2"
                :pageShow="true"
                :total="total"
                :transfer="true"
                :pageCur="pageCur"
                :classifyDatas="speciClassifyDatas"
                :resultDatas="speciResultDatas"
                :load-data="loadSpeciDatas"
                @on-search="handleSpeciSearch"
                @on-get-classify="handleGetSpeciClassify"
                @on-get-result="handleGetSpeciResult"
                @on-page-change="handleSpeciPageChange" />
                 <!-- 照片选择 -->
                <photoSelector 
                ref="identityPhoto" 
                :photoAlbum="l"
                @on-change="albumChange"
                @on-get-result="handleGetPhotoResult"
                :resultDatas="p"
                />
            </div>
       </div>
    </div>
</template>
<script>
import api from "../api";
import roomClass from '../components/roomClass.vue'
import vuiFilter from "../components/vuiFilter/filter"
// import top from "../top";
import $ from 'jquery'
import photoSelector from '../components/photoSelector'

     export default {
        components:{
            roomClass,
            vuiFilter,
            // top,
            photoSelector
        },
        data () {
            return {
                total2: 0,
				pageCur2: 1,
                textarea:'',
                avatar:'../../static/img/avatar.png',
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                // loginuserinfo:{loginAccount:'luona'},
                formItem:{
                    account:'',
                    liveName :'',
                    liveDescribe :'',
                    liveImage :'',
                    liveCategoryId :''
                },
                ruleInline:{
                    liveDescribe :[{required: true, message: '请输入房间描述',trigger: 'blur'}],
                    liveName :[{required: true, message: '请输入房间标题',trigger: 'blur'}],
                    liveImage:[{required: true, message: '请上传房间封面',trigger: 'blur'}],
                    liveCategoryId:[{required: true, message: '请上传房间封面',trigger: 'blur'}]
                },
                //支持上传的文件类型
                format: ["jpg", "jpeg", "png"],
                uploadList:[],
                roomClass:false,
                roomClassData:{
                    area:[],
                    trade:'',
                    product:'',
                    sevrice:'',
                    species:''
                },
                roomClassDataPost:{
                    account:'',
                    liveArea :[],
                    liveIndustry :'',
                    liveSpecies :''
                },
                tradeResultDatas: [],
                serviceResultDatas: [],
                total: 0,
                pageCur: 1,
                speciClassifyDatas: [
                    {
                        label: "动物",
                        value: "0",
                        classId: "",
                        loading: false,
                        checked: false,
                        children: []
                    },
                    {
                        label: "植物",
                        value: "1",
                        classId: "",
                        loading: false,
                        checked: false,
                        children: []
                    }
                ],
                speciResultDatas: [],
                serviceResultDatas: [],
                locationList:[],
                action:'',
                imgName:'',
                visible:false,
                listStr:'',
                addr:'',                
                l: [],
                p: [],
            }
        },
        created(){ 
            console.log('chushihua kaishi')
            
            if(!this.loginuserinfo){
                this.loginuserinfo = {}
                this.loginuserinfo.loginAccount = this.$route.query.account
                console.log('取地址栏account')
            }
            // 取地址
            api.post("/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816").then(res => {
                this.locationList = res.data;
            });
            // 取行业类型数据
			api.post('/member/system-dict/getSystemDict', {
				typeName:'行业分类',
				pageNum: 1
			}).then(res => {
				var data = res.data.list
				this.total2 = res.data.total
				this.tradeResultDatas = data
			})
            // 取相关物种结果
            this.loadSpeciResult("", "", [], this.pageCur, []);
            // 取相关服务数据
            api.post("/member/service/find", {}).then(res => {
                var data = res.data;
                data.forEach((item, index) => {
                    item.checked = false;
                });
                this.serviceResultDatas = data;
            });
            // 从我的风采导入图片 默认显示第一个相册
            this.getAlbum('identityPhoto')
            console.log('chushihua jieshu')
        },
        mounted(){     
             // 上传的地址
            this.action = `${this.$url.upload}/upload/up`
        },
        methods:{
            goback(){
                window.open(this.$url.toNswy+'pro/member?uid='+this.loginuserinfo.loginAccount, "_self");
            },
            //点击更改显示弹窗
            onchange(){
                this.roomClass = true
            },
            //选择分类确定按钮
            asyncOK(){
                var data = this.roomClassData
                var listStr = ''
                if(this.addr){
                    listStr = this.addr+ '、'
                }else{
                    this.$Message.warning('请选择所在地区')
                    return false
                }
                if(this.roomClassData.species){
                    listStr += this.roomClassData.species + '、'
                }
                if(this.roomClassData.trade){
                    listStr += this.roomClassData.trade
                }
                this.listStr = listStr
                this.roomClassDataPost.account = this.loginuserinfo.loginAccount
                api.post("/relationship/live/saveOrUpdateCategory", this.roomClassDataPost).then(res => {
                    if(res.code == 200){
                        this.formItem.liveCategoryId = res.data
                        this.roomClass = false
                    }
                });
            },
            close(){
                 this.roomClass = false
            },
            //开始直播按钮
            startLive(){                
                if(!this.formItem.liveCategoryId){
                    this.$Message.warning('请选择房间分类')
                    return
                }else if(!this.formItem.liveName){
                    this.$Message.warning('输入房间标题')
                    return
                }else if(!this.formItem.liveDescribe){
                    this.$Message.warning('输入房间描述')
                    return
                }else if(!this.formItem.liveImage){
                    this.$Message.warning('请选上传房间封面')
                    return
                }else{
                    this.formItem.account = this.loginuserinfo.loginAccount
                    console.log(this.formItem)
                    api.post("/relationship/live/saveOrUpdateLive", this.formItem).then(res => {
                        if(res.code == 200){
                            this.$router.push({ path: "/chatRoom", query: { id:res.data }});
                        }
                    });                   
                }
            },
            //删除图片
            handleRemove (file) {
                // 从 upload 实例删除数据
                const fileList = this.$refs.upload.fileList;
                this.$refs.upload.fileList.splice(0, 1);
                this.uploadList = this.$refs.upload.fileList
            },
            //文件上传成功时的钩子，返回字段为 response, file, fileList
            handleSuccess(response, file, fileList) {
                if (response.code == 500) {
                    this.$Message.error("上传失败!")
                } else {
                    this.$Message.success("上传成功!")
                    this.uploadList = this.$refs.upload.fileList
                    this.formItem.liveImage =
                    "http:" + this.$refs.upload.fileList[0].response.data.picName
                    // this.size = this.$refs.upload.fileList.length
                }
            },
            //文件文件超出指定大小限制时的钩子，返回字段为 file, fileList
            handleMaxSize(){
                this.$Message.success('文件太大')
            },
            handleBeforeUpload(){
                if(this.uploadList.length>0){
                    this.$Notice.warning({
                        title: '封面只能有一张。'
                    });
                    return false
                }
            },
             // 从我的风采导入图片
            handlePhotoSelectorModal (name) {
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
                api.post('/member/product-base/media-library-query-all', {
                    account:  this.loginuserinfo.loginAccount,
                    mediaType: 1
                }).then(response => {
                    if (response.code === 200) {
                        console.log(response)
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
                })
            },
            getPhoto (params) {
                api.post("/member/product-base/media-library-detail-query-list", params)
                    .then(response => {
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
                // 用于限制最大可上传照片的张数
                if (this.$refs.upload.fileList.length >= 1 || result.length > 1) {
                    this.$Notice.warning({
                        title: '封面只能有一张'
                    })
                } else {
                    result.forEach(element => {
                        this.$refs.upload.fileList.push({
                            response: {
                                data: {
                                    picName: element
                                }
                            },
                            status: 'finished'
                        })                      
                    })
                    this.uploadList = this.$refs.upload.fileList    
                    this.formItem.liveImage =
                    "http:" + this.$refs.upload.fileList[0].response.data.picName            
                }
            },
            // 高级搜索弹窗
            handleFilterModal(name) {
                this.$refs[name].highFilterShow = true;
            },
            formats (labels, selectedData) {
                var data = {}
                    if( selectedData[0] ){//省
                        data.provinceName = selectedData[0].label
                        data.provinceId = selectedData[0].value
                    }
                    if( selectedData[1] ){//市
                        data.cityName = selectedData[1].label
                        data.cityId = selectedData[1].value
                    }
                    if( selectedData[2] ){//县
                        data.countyName = selectedData[2].label
                        data.countyId = selectedData[2].value
                    }
                    if( selectedData[3] ){//乡
                        data.townName = selectedData[3].label
                        data.townId = selectedData[3].value
                    }         
                this.roomClassDataPost.liveArea  = data
                var label =''          
                for(var i = 0;i<labels.length;i++){
                    if(i==labels.length-1){
                        label+=labels[i]
                    }else{
                        label+=labels[i]+'/'
                    } 
                }
                this.addr = label
                return label
            },
            loadPositionDatas(item, callback) {
                item.loading = true;
                api.post(`/member/town/next/${item.value}`).then(res => {
                    item.loading = false;
                    item.children = res.data;
                    callback();
                });
            },
            // 相关行业搜索
            // 行业类型
            handleTradeSearch (letter, keyword, result) {
                api.post(`/member/system-dict/getSystemDict`, {
					dictName : keyword,
					typeName : '行业分类',
					character: letter === '全部' ? '' : letter,
					pageNum: 1
				}).then(res => {
					var d = res.data.list
					this.total2 = res.data.total
					this.pageCur2 = 1
					this.tradeResultDatas = []
					if (!d.length) return 
					if (result.length) {
						result.forEach(item => {
							d.forEach(child => {
								if (child.label === item.label) {
									child.checked = true
									child.label = child.label
									child.value = child.value
								}
							})
						})
					} else {
						d.forEach(child => {
							child.checked = false
							child.label = child.label
							child.value = child.value
						})
					}
					this.tradeResultDatas = d
				})
            },
            handleGetTradeResult(classify, result) {
                var arr = [];
                var arrPost = [];
                result.forEach(item => arr.push(item.label));
                result.forEach(item => arrPost.push(item.value));
                this.roomClassData.trade = arr.join("/");
                this.roomClassDataPost.liveIndustry = {industryName:arr.join(","),industryId:arrPost.join(",")};
            },
            handleTradePageChange (letter, keyword, classify, num, result) {
                this.pageCur2 = num
                api.post('/member/system-dict/getSystemDict', {
					typeName:'行业分类',
					character: letter === '全部' ? '' : letter,
					dictName: keyword,
					pageNum: num
				}).then(res => {
					var data = res.data.list
					this.total2 = res.data.total
					this.tradeResultDatas = data
				})
                this.$Message.success('筛选完成！')
            },
            // 相关服务
            handleServiceSearch(letter, keyword, classify) {
                api
                    .post(`/member/service/find`, {
                    character: letter === "全部" ? "" : letter,
                    keyword: keyword
                    })
                    .then(res => {
                    var d = res.data;
                    this.serviceResultDatas = [];
                    if (d.length) {
                        if (classify.length) {
                        classify.forEach(item => {
                            d.forEach(child => {
                            if (child.label === item.label) {
                                child.checked = true;
                            }
                            });
                        });
                        } else {
                        d.forEach(child => {
                            child.checked = false;
                        });
                        }
                        this.serviceResultDatas = d;
                        this.$Message.success("筛选完成！");
                    }
                });
            },
            handleGetServiceResult(classify, result) {
                var arr = [];
                result.forEach(item => arr.push(item.label));
                this.roomClassData.sevrice = arr.join(" ");
            },
            // 相关物种
            handleSpeciSearch(letter, keyword, classify, result) {
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
                this.$Message.success("筛选完成！");
            },
             loadSpeciDatas(item, callback) {
                item.loading = true;
                api
                    .post(`/member/specicesClass/findByParentId/${item.value}`)
                    .then(res => {
                    item.loading = false;
                    var d = res.data;
                    d.forEach(child => {
                        child.checked = false;
                        child.label = child.className;
                    });
                    item.children = d;
                    callback();
                });
            },
            handleGetSpeciClassify(letter, keyword, classify, result) {
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
                this.$Message.success("筛选完成！");
            },
            handleGetSpeciResult(classify, result) {
                var arr = [];
                var arrPost = [];
                result.forEach(item => arr.push(item.label));
                result.forEach(item => arrPost.push(item.value));
                this.roomClassData.species = arr.join("/");
                this.roomClassDataPost.liveSpecies  = {SpeciesName:arr.join(","),speciesId:arrPost.join(",")} ;
            },
            handleSpeciPageChange(letter, keyword, classify, num, result) {
                this.pageCur = num;
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
                this.$Message.success("筛选完成！");
            },
            loadSpeciResult(letter, keyword, classify, num, result) {
                if (classify.length) {
                    var arr = [];
                    var type = ''
                    classify.forEach(item => {
                        arr.push(item.classId);
                        if(item.value !== undefined) type = item.value
                    });
                } else {
                    arr = null;
                }
                api.post("/member/specicesClass/findSpecies", {
                        keywords: keyword,
                        fpinyin: letter === "全部" ? "" : letter,
                        fclassifiedid: arr,
                        pageNum: num,
                        pageSize: 32,
                        type:type
                    }).then(res => {
                    var data = res.data;
                    this.total = data.total;
                    if (result) {
                        result.forEach(item => {
                        data.list.forEach((child, index) => {
                            if (child.label === item.label) {
                            child.checked = true;
                            }
                        });
                        });
                    } else {
                        data.list.forEach(child => {
                        child.checked = false;
                        });
                    }
                    this.speciResultDatas = data.list;
                });
            },
        }
    }

</script>
<style>

.live-video-setting .demo-upload-list{
        display: inline-block;
        width: 300px;
        height: 200px;
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
.live-video-setting  .demo-upload-list img{
        width: 100%;
        height: 100%;
    }
.live-video-setting   .demo-upload-list-cover{
        display: none;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0,0,0,.6);
    }
.live-video-setting  .demo-upload-list:hover .demo-upload-list-cover{
        display: block;
    }
.live-video-setting  .demo-upload-list-cover i{
        color: #fff;
        font-size: 20px;
        cursor: pointer;
        margin: 70px 20px;
    }
.live-video-setting .point {
    position: relative;
}
.live-video-setting .point .point-btn{
    position: absolute;
    top: 1px;
    right: -100px;
}
.live-video-setting .ivu-tooltip-inner{
    white-space: normal;
}
.ww{
    width:1200px;
    margin:0 auto;
}
.live-video-setting{
    width:1000px;
    margin: 20px 100px;
}
.live-video-setting .ivu-upload{
    display: inline-block;
}
.live-video-setting .size{
    vertical-align: top;
}
.live-video-setting .ivu-upload .ivu-upload-drag{
    border:none;
    width: 99px;
    height: 34px;
}
.live-video-setting .from{
    width: 420px;
    margin: 0 auto;
}
.live-video-setting .btn{
    
    padding-left:20px;
}
.live-video-setting .btn button{
    min-width: 80px;
}
.live-video-setting .ivu-form .ivu-form-item-label{
    color:#666;
    font-size:#666;
}
.live-video-setting  .roomClass{
    color:#00c587;
    max-width:230px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    padding:6px 8px;
}
.ivu-room-class .ivu-row{
        padding: 5px 60px 10px 10px;
}
.ivu-room-class .ivu-row .ivu-col{
    text-align: right;
    padding-right: 10px;
}
.ivu-room-class .ivu-row .ivu-col button{
    width:90px;
    height:32px;
    font-size: 14px;
}
.ivu-room-class .ivu-row .ivu-col input{
    font-size: 14px;
}

</style>
