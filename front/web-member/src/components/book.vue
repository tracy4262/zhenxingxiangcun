<template>
    <div>
        <Form ref="showbook" :model="mydynamic" style="margin-top: 27px;" :rules="ruleInline" label-position="right" :label-width="100" v-show="process === 4 && indexpane <= 2">
            <FormItem label="标题" prop="title">
                <Input type="text" v-model="mydynamic.title" placeholder="标题">
                </Input>
            </FormItem>
            <!-- 分类的修改 -->
            <FormItem label="分类" prop="type">
                <Cascader  v-if="options.infoShow===true" @on-change="getMydynamicType" :data="infoTypeDatas" v-model="infoType" :load-data="loadInfoTypeDatas"  filterable ></Cascader>
                <Cascader  v-if="options.policyShow===true" @on-change="getMydynamicType" :data="policyTypeDatas" v-model="infoType" :load-data="loadInfoTypeDatas"  filterable ></Cascader>
                <Cascader  v-if="options.knowledgeShow===true" @on-change="getMydynamicType" :data="knowledgeTypeDatas" v-model="infoType" :load-data="loadInfoTypeDatas"  filterable></Cascader>
            </FormItem>
            <FormItem label="栏目类别" prop="category">
                <Input v-model="category" readonly><Icon type="ios-arrow-down" slot="suffix"/></Input>
                <!-- <Select v-model="mydynamic.category"  >
                    <Option v-for="(item,index) in options.categorys" :key="index" :value="item.value">{{ item.label }}</Option>
                </Select> -->
            </FormItem>
            <FormItem label="信息来源" prop="source" style="text-align: left;">
                <Radio-group v-model="mydynamic.source" @on-change="showAuthor(mydynamic)">
                    <Radio label="原创">
                    </Radio>
                    <Radio label="转载">
                    </Radio>
                </Radio-group>
            </FormItem>
            <FormItem label="发布人" prop="author">
                <Input type="text" v-model="mydynamic.author" readonly placeholder="发布人">
                </Input>
            </FormItem>
            <FormItem label="原创作者" prop="ycauthor" style="display: none;" class="ycauthor">
                <Input type="text" v-model="mydynamic.ycauthor"  placeholder="请输入原创作者">
                </Input>
            </FormItem>
            <FormItem label="来源网站" prop="network" style="display: none;" class="network">
                <Input type="text" v-model="mydynamic.network" placeholder="请输入来源网站">
                </Input>
            </FormItem>
            <FormItem label="摘要" prop="summary">
                <Input type="textarea" v-model="mydynamic.summary" placeholder="摘要">
                </Input>
            </FormItem>
            <FormItem prop="district" label="适用区域">
                <Cascader :data="cityList" :render-format="format" v-model="mydynamic.district" :load-data="loadData"  v-show="selectedLocation" change-on-select>
                </Cascader>
            </FormItem>

        </Form>

        <Form ref="showbook2" :rules="ruleInline" style="margin-top: 27px;" :model="mydynamic"  label-position="right" :label-width="100" v-show="process === 5">
            <vui-cropper @on-get-base64="handleGetBase64" @on-get-bookPage="handleGetBookPage"></vui-cropper>
            <Row>
                <Col span="11">
                <Form-item label="作者" prop="book_author">
                    <Input v-if="mydynamic.source=='转载'" readonly type="text" v-model="mydynamic.book_author"  placeholder="作者">
                    </Input>
                    <Input v-if="mydynamic.source=='原创'"  type="text" v-model="mydynamic.book_author"  placeholder="作者">
                    </Input>
                </Form-item>
                </Col>

                <Col span="11" offset="1">
                <Form-item label="版次" prop="book_edition">
                    <Input type="text" v-model="mydynamic.book_edition" placeholder="版次">
                    </Input>
                </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                <Form-item label="出版发行" prop="book_publish">
                    <Input type="text" v-model="mydynamic.book_publish" placeholder="出版发行">
                    </Input>
                </Form-item>
                </Col>
                <Col span="11" offset="1">
                <Form-item label="印张" prop="book_sheet">
                    <Input type="text" v-model="mydynamic.book_sheet" placeholder="印张">
                    </Input>
                </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                <Form-item label="经销" prop="book_distribution">
                    <Input type="text" v-model="mydynamic.book_distribution" placeholder="经销">
                    </Input>
                </Form-item>
                </Col>
                <Col span="11" offset="1">
                <Form-item label="开版" prop="book_broadsheet">
                    <Input type="text" v-model="mydynamic.book_broadsheet" placeholder="开版">
                    </Input>
                </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                <Form-item label="印刷时间" prop="book_print_time">
                    <DatePicker type="date"  v-model="mydynamic.book_print_time" :transfer='transfer'>
                    </DatePicker>
                </Form-item>
                </Col>
                <Col span="11" offset="1">
                <Form-item label="字数" prop="book_word_count">
                    <Input type="text" v-model="mydynamic.book_word_count" placeholder="字数">
                    </Input>
                </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                <Form-item label="出版时间" prop="book_pub_date">
                    <DatePicker type="date"  v-model="mydynamic.book_pub_date" :transfer='transfer'>
                    </DatePicker>
                </Form-item>
                </Col>
                <Col span="11" offset="1">
                <Form-item label="纸张" prop="book_paper">
                    <Select v-model="mydynamic.book_paper" :transfer="true">
                        <Option v-for="(item,index) in zhizhang" :key="index" :value="item.value" >{{ item.label }}</Option>
                    </Select>
                </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="16">
                <Form-item label="图书标签" prop="book_label">
                    <Input type="text" v-model="mydynamic.book_label" placeholder="使用逗号或分号分隔，按回车添加，最多支持3个" @keyup.enter.native="addTag(mydynamic.book_label)">
                    </Input>
                </Form-item>
                </Col>
                <Col span="6" offset="1">
                <Button type="primary"  @click.native="addTag(mydynamic.book_label)">增加</Button>
                </Col>
            </Row>
            <Row>
                <Col span="16">
                <Form-item prop="bookTags">
                    <Tag v-for="item in mydynamic.booktags" :key="item" :name="item" closable @on-close="tagClose" v-show="item !=''">{{item}}</Tag>
                </Form-item>
                </Col>
            </Row>
            <vui-book ref="bookTree" @on-get-book="handleGetBook"></vui-book>
        </Form>
        <Form ref="showbook3" :model="mydynamic" style="margin-top: 27px;"  label-position="right" :label-width="100" v-show="process === 6">
            <Row>
                <Col span="22">
                <FormItem label="关联物种" prop="species" >
                    <Input v-model="mydynamic.species" readonly @on-focus="bookparent('speciFilter')" />
                </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="22">
                <FormItem label="通用商品名" prop="goodsname">
                    <vuiCommodity ref="products" @on-save="getProducts" type="1" :values="products"></vuiCommodity>
                    <!-- <Input readonly v-model="products" readonly @on-focus="bookparent('goodFilter')" /> -->
                </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="22" >
                <FormItem label="通用服务名" prop="servicename">
                    <vuiCommodity ref="service" @on-save="getService" type="2" :values="service"></vuiCommodity>
                    <!-- <Input  readonly v-model="service" @on-focus="bookparent('serviceFilter')" /> -->
                </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="22">
                <FormItem label="行业分类" prop="industry">
                    <Input v-model="mydynamic.industryName" readonly @on-focus="bookparent('tradeFilter')" />
                </FormItem>
                </Col>
            </Row>
        </Form>
        <Form ref="showbook4" style="margin-top: 27px;" :model="mydynamic"  label-position="right" :label-width="100" v-show="process===7">
            <FormItem label="好友提醒" prop="tixing">
                <Input type="text" v-model="mydynamic.tixing" placeholder="好友提醒">
                </Input>
            </FormItem>
            <FormItem label="提醒内容" prop="con">
                <Input type="textarea" v-model="mydynamic.con" placeholder="提醒内容">
                </Input>
            </FormItem>
            <Row>
                <Col span="11">
                <FormItem label="发送短信" prop="sms" style="text-align: left;">
                    <Radio-group v-model="mydynamic.sms">
                        <Radio label="是">
                        </Radio>
                        <Radio label="否">
                        </Radio>
                    </Radio-group>
                </FormItem>
                </Col>
                <Col span="11" offset="1">
                <FormItem label="提交首页显示" prop="xianshi">
                    <Radio-group v-model="mydynamic.xianshi" @on-change="submitIndex(mydynamic)">
                        <Radio label="是">
                        </Radio>
                        <Radio label="否">
                        </Radio>
                    </Radio-group>
                </FormItem>
                </Col>
            </Row>
            <FormItem label="公开范围" prop="security" style="text-align: left;">
                <Radio-group v-model="mydynamic.security">
                    <Radio label="所有人可见">
                    </Radio>
                    <Radio label="仅好友可见">
                    </Radio>
                    <Radio label="仅自己可见">
                    </Radio>
                    <Radio label="仅指定组可见">
                    </Radio>
                </Radio-group>
            </FormItem>
            <FormItem label="是否有偿" prop="money">
                <Radio-group v-model="mydynamic.money" @on-change="changeMoney(mydynamic)">
                    <Radio label="收费">
                    </Radio>
                    <Radio label="免费">
                    </Radio>
                </Radio-group>
            </FormItem>
        </Form>
        <div class="disclaimer"  v-show="process === 7">
            <div class="disclaimer-title"><a href="javascript:void(0);" @click="onDisclaimer"><span>免责声明</span></a></div>
            <div class="disclaimer-content"v-show="disclaimer">
                <p>1.本网站只提供一个信息交流平台，不控制发布内容。发布内容及因之产生的后果，均由发布者自行负责，</p>
                <p>与本网站无关。我们对用户使用本网站所产生的一切后果，不承担任何责任；</p>
                <p>2.发贴内容须符合中华人民共和国和所在国法律，符合当地道德规范和风俗文化。内容真实，无欺诈；</p>
                <p>3.谢绝一切与本网站主题无关的内容，尤其不涉及政治、宗教、种族等议题</p>
                <p>4.不发布涉及他人版权的内容，不发布与栏目无关之内容；</p>
                <p>5.不发布与传销、直销及其他事涉不规范经济活动有关的内容。</p>
            </div>
        </div>
    </div>
</template>
<script>
    import api from "~api";
    import $ from "jquery";
    import vuiCropper from '~components/vuiCropper'
    import vuiBook from '~components/vuiBookList'
    import vuiCommodity from "~components/vui-commodity";
    export default {
        components: {
          vuiCropper,
          vuiBook,
          vuiCommodity
        },
        props: {
            options: {
                type: Object
            },
            process:{
                type: Number
            },
            mydynamic:{
                type:Object
            },
            indexpane:{
                type: Number
            },
            category: {
                type: String,
                default:''
            }
        },
        data() {
            return {
                scrollable: false,
                isHide:false,//当前页是全部时隐藏
                country:'China',
                selectedLocation:true,
                infoTypeDatas:[{
                    value: 'business',
                    label: '商务资讯',
                    loading: false,
                    children: []
                }, {
                    value: 'official ',
                    label: '公务资讯',
                    loading: false,
                    children: []
                }],
                policyTypeDatas:[{
                    value: 'party',
                    label: '党务',
                    loading: false,
                    children: []
                }, {
                    value: 'affairs',
                    label: '政务',
                    loading: false,
                    children: []
                }],
                knowledgeTypeDatas:[{
                    value: 'animalHusbandry',
                    label: '农林牧鱼',
                    loading: false,
                    children: []
                }
                    ,{
                        value: 'foodScience',
                        label: '食品科学',
                        loading: false,
                        children: []
                    },{
                        value: 'zs07',
                        label: '经济'
                    },{
                        value: 'zs08',
                        label: '法律'
                    }
                ],
                disclaimer:false,//免责声明
                infoType: [],
                zhizhang:[{
                    value: '铜板纸',
                    label: '铜板纸'
                }, {
                    value: '胶板纸',
                    label: '胶板纸'
                }],


                type: 'publish',

                transfer: true,
                ruleInline: {
                    book_author: [
                        { type: 'string',
                        max: 20,
                        message: '作者不能超过20个字',
                        trigger: 'blur'
                    }],
                    book_publish: [
                    { type: 'string',
                        max: 20,
                        message: '出版发行不能超过20个字',
                        trigger: 'blur'
                    }],
                    book_broadsheet: [
                    { type: 'string',
                        max: 20,
                        message: '开版不能超过20个字',
                        trigger: 'blur'
                    }],
                    book_word_count: [
                    { type: 'string',
                        max: 20,
                        message: '字数不能超过20个字',
                        trigger: 'blur'
                    }],
                    book_sheet: [
                    { type: 'string',
                        max: 20,
                        message: '印张不能超过20个字',
                        trigger: 'blur'
                    }],
                    summary: [ 
                        // { 
                        //     type: 'string',
                        //     max: 300,
                        //     message: '摘要不能超过300个字',
                        //     trigger: 'blur'
                        // }
                        {
                            required: true,
                            message: "摘要不能为空",
                            trigger: "blur"
                        }
                    ],
                    category: [{
                        required: true,
                        message: "栏目类型不能为空",
                        trigger: "blur"
                    }],
                    title: [{
                        required: true,
                        message: "标题不能为空",
                        trigger: "blur"
                    },
                        { type: 'string',
                            max: 40,
                            message: '标题不能超过40个字',
                            trigger: 'blur'
                        }
                    ],
                    type: [{
                        required: true,
                        message: "分类不能为空",
                        trigger: "blur"
                    }],
                    district: [
                        {
                            required: true,
                            type: "array",
                            message: "地区不能为空",
                            trigger: "change"
                        }
                    ]
                },
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                show: true,
                service:'',
                products:'',
                classfy:'',
                cityList: [],
                picture:false,
                audios:false,
                video: false,
                imgs: false,
                weibo: false,
                msm: false,
                con: true,
                length: 200,
                leibie: "",
                buttonCon: "",
                step: '',
                number:0,
                knowledgeType: [{
                    value: "种植标准",
                    label: "种植标准"
                },
                    {
                        value: "养殖标准",
                        label: "养殖标准"
                    },
                    {
                        value: "农村能源",
                        label: "农村能源"
                    },
                    {
                        value: "水能应用",
                        label: "水能应用"
                    },
                    {
                        value: "太阳能",
                        label: "太阳能"
                    },
                    {
                        value: "地下热能",
                        label: "地下热能"
                    }
                ],
                infoType: [{
                    value: "农业",
                    label: "农业"
                },
                    {
                        value: "仓储业质检",
                        label: "仓储业质检"
                    },
                    {
                        value: "畜牧",
                        label: "畜牧"
                    },
                    {
                        value: "林业服务业",
                        label: "林业服务业"
                    },
                    {
                        value: "包装",
                        label: "包装"
                    },
                    {
                        value: "渔业",
                        label: "渔业"
                    }
                ],
                policyType: [{
                    value: "组织",
                    label: "组织"
                },
                    {
                        value: "宣传",
                        label: "宣传"
                    },
                    {
                        value: "外交",
                        label: "外交"
                    },
                    {
                        value: "工会",
                        label: "工会"
                    },
                    {
                        value: "群众自治",
                        label: "群众自治"
                    },
                    {
                        value: "共青团",
                        label: "共青团"
                    }
                ]
            };
        },
        created() {
            // 取地址
            api.post("/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816").then(res => {
                this.cityList = res.data;
            });
        },
        methods: {
            // 通用服务名
            getService(result) {
                this.$emit('get-service', result)
            },
            // 通用商品名
            getProducts(result) {
                this.$emit('get-products', result)
            },
          //取图片封面地址
          handleGetBookPage(data){
              this.$emit('on-get-bookPage', data)
          },
          // 取裁切base64地址
          handleGetBase64 (data) {
            this.$emit('on-get-base64', data)
          },
          // 取图书数据
          handleGetBook (data) {
            this.$emit('on-get-book', data)
          },
            //图书增加标签
            addTag(name){
              if(name != ''){
                  var bookname = name;
                  var replaceStr='，';
                  bookname = bookname.split(replaceStr).join(',');
                  replaceStr=';';
                  bookname = bookname.split(replaceStr).join(',');
                  replaceStr='；';
                  bookname = bookname.split(replaceStr).join(',');
                  var tags = bookname.split(",");
                  if(tags.length>3){
                      this.$Message.error('添加标签过多，请保留3个标签!')
                  }else{
                      if(this.number===0){
                          this.mydynamic.booktags = tags
                          this.number++
                      }else if(this.number<3){
                          let booklet = this.mydynamic.booktags +','+bookname +','
                          booklet =  booklet.substring(0, booklet.length - 1);
                          tags = booklet.split(",");
                          if(tags.length>3){
                              this.$Message.error('添加标签过多，请保留3个标签!')
                          }else{
                              this.mydynamic.booktags = tags;
                              this.number++
                          }
                      }else{
                          this.$Message.error('添加标签过多，请保留3个标签!')
                      }
                  }
                  this.mydynamic.book_label = '';
              }else{
                  this.$Message.error('请填写正确的标签!')
              }


            },
            tagClose(event, name){
                this.number--
                this.mydynamic.book_label='';
                const index = this.mydynamic.booktags.indexOf(name);
                this.mydynamic.booktags.splice(index, 1);
                this.mydynamic.booktags.forEach(item => {
                    this.mydynamic.book_label +=item +','
                });
                this.mydynamic.book_label = this.mydynamic.book_label.substring(0, this.mydynamic.book_label.length - 1)

            },
            // 得到动态的分类
            getMydynamicType(value,arr){
                // 赋值最后那个
                if (value.length >= 1){
                    let index = value.length-1
                    this.mydynamic.type = value[index]
                }
            },
            // 取资讯类型
            loadInfoTypeDatas (item, callback) {
                item.loading = true
                if (this.options.infoShow) {
                    api.get(`/member/infoClass/information/${item.value}`).then(res => {
                        item.loading = false
                        item.children = res.data
                        callback()
                    })
                } else if (this.options.policyShow) {
                    api.get(`/member/infoClass/policy/${item.value}`).then(res => {
                        item.loading = false
                        item.children = res.data
                        callback()
                    })
                } else if (this.options.knowledgeShow) {
                    api.get(`/member/infoClass/knowledge/${item.value}`).then(res => {
                        item.loading = false
                        item.children = res.data
                        callback()
                    })
                }
            },
            format(labels, selectedData) {
                if(labels.length < 1) {
                    return ""
                } else {
                    this.mydynamic.district = labels
                    return labels.join("/")
                }
            },
            loadData(item, callback) {
                item.loading = true
                api.post(`/member/town/next/${item.value}`).then(res => {
                    item.loading = false
                    item.children = res.data
                    callback()
                });
            },
            //免责声明
            onDisclaimer(){
                this.disclaimer = !this.disclaimer
            },
            yanzheng(name){
                let flag = false;
                this.$refs[name].validate(valid=> {
                    if(valid) {
                       flag=true;
                    }
                })
                return flag;
            },
            clean(){
                this.mydynamic.title = '';
                this.mydynamic.source = "原创";
                this.mydynamic.ycauthor = '';
                this.mydynamic.network = '';
                this.mydynamic.summary = '';
                this.mydynamic.district = [];
                this.mydynamic.species = '';
                this.mydynamic.industryName = '';
                this.mydynamic.tixing = '';
                this.mydynamic.con = '';
                this.mydynamic.sms = '否';
                this.mydynamic.xianshi = '否';
                this.mydynamic.security = '所有人可见';
                this.mydynamic.money = '免费';
                this.mydynamic.book_author = '';
                this.mydynamic.book_edition = '';
                this.mydynamic.book_publish = '';
                this.mydynamic.book_sheet = '';
                this.mydynamic.book_distribution = '';
                this.mydynamic.book_broadsheet = '';
                this.mydynamic.book_print_time = new Date();
                this.mydynamic.book_word_count = '';
                this.mydynamic.book_pub_date = new Date();
                this.mydynamic.book_paper = '';
                this.mydynamic.book_label = '';
                this.mydynamic.booktags = [];
            },
            bookparent(name){
                this.$emit('bookparent',name)
            },
            //发布到首页时判断是否实名
            submitIndex(e) {
                if('是' === e.xianshi) {
                    this.mydynamic.xianshi = '是'
                }else{
                    this.mydynamic.xianshi = '否'
                }
            },
            //网站来源框显示
            showAuthor(e) {
                if("转载" === e.source) {
                    $(".network").show();
                    $(".ycauthor").show();

                } else {
                    $(".network").hide();
                    $(".ycauthor").hide();
                }
            },

        }

    }
</script>
<style scoped>
    #ivu-form-item-label-custom::before{
        content: "*";
        display: inline-block;
        margin-right: 4px;
        line-height: 1;
        font-family: SimSun;
        font-size: 14px;
        color: #f30;
    }
    .publish .tabadd {
        float: right;
        margin-bottom: 20px;
        margin: 0px 5px;
    }
    .publish .inputwidth{
        width: 443px;
    }
    .disclaimer {

    }
    .disclaimer-title{
        text-align: center;
        font-size: 14px;
        color: #00c587;
        margin-bottom: 10px;
    }
    .disclaimer-title span{
        color: #00c587;
    }
    .disclaimer-content{
        width: 664px;
        margin-left: 48px;
        background-color: #F8F8F8;
        padding-left: 15px;
        padding-top: 15px;
        padding-bottom: 15px;
    }
</style>
