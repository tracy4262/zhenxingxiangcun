<template>
  <div class="publish">
    <Modal
      v-model="options.showPublish"
      width="750"
      heigth="700px"
      @on-cancel="cancel('mydynamic')"
      :title="'发布'+options.title"
      :mask-closable="false"
      :styles="{top: '20px'}"
      @on-visible-change="refresh"
    >
      <div class="tc" v-show=" indexpane >= 3">
        <img src="../../../img/developing.png">
        <p>开发中,敬请期待···！</p>
      </div>

      <RadioGroup
        v-model="mydynamic.leixin"
        type="button"
        @on-change="check(mydynamic)"
        class="mb20"
      >
        <Radio :disabled="!wenzhang" label="文章"></Radio>
        <Radio :disabled="!tuce" label="图册"></Radio>
        <Radio :disabled="!yinping" label="音频"></Radio>
        <Radio :disabled="!shiping" label="视频"></Radio>
        <Radio :disabled="!tushu" label="图书"></Radio>
        <Radio :disabled="!duanxin" label="短信"></Radio>
      </RadioGroup>

      <div style="margin-top: 10px;">
        <vui-steps
          :data="stepDataSec"
          :active="current"
          v-show="process === 4 ||process === 5 ||process === 6 ||process === 7"
        ></vui-steps>
      </div>

      <book
        @on-get-bookPage="handleGetBookPage"
        @on-get-base64="handleGetBase64"
        @on-get-book="handleGetBook"
        ref="bookData"
        :options="options"
        :process="process"
        :category="category"
        :mydynamic="mydynamic"
        :indexpane="indexpane"
        @get-service="getService"
        @get-products="getProducts"
      ></book>

      <Form
        ref="mydynamic"
        :model="mydynamic"
        style="margin-top: 27px;"
        :rules="ruleInline"
        label-position="right"
        :label-width="100"
        v-show="process === 1 && indexpane <= 2"
      >
        <FormItem label="标题" prop="title">
          <Input type="text" v-model="mydynamic.title" placeholder="标题"></Input>
        </FormItem>
        <!-- 分类的修改 -->
        <FormItem label="分类" prop="type">
          <Cascader
            v-if="options.infoShow===true"
            @on-change="getMydynamicType"
            :data="infoTypeDatas"
            v-model="infoType"
            :load-data="loadInfoTypeDatas"
            filterable
          ></Cascader>
          <Cascader
            v-if="options.policyShow===true"
            @on-change="getMydynamicType"
            :data="policyTypeDatas"
            v-model="infoType"
            :load-data="loadInfoTypeDatas"
            filterable
          ></Cascader>
          <Cascader
            v-if="options.knowledgeShow===true"
            @on-change="getMydynamicType"
            :data="knowledgeTypeDatas"
            v-model="infoType"
            :load-data="loadInfoTypeDatas"
            filterable
          ></Cascader>
        </FormItem>
        <FormItem label="栏目类别" prop="category">
          <Input v-model="category" readonly><Icon type="ios-arrow-down" slot="suffix"/></Input>
          <!-- <Select v-model="mydynamic.category">
            <Option
              v-for="(item,index) in options.categorys"
              :key="index"
              :value="item.value"
            >{{ item.label }}</Option>
          </Select> -->
        </FormItem>
        <Row>
          <Col span="8">
            <FormItem label="信息来源" prop="source" style="text-align: left;">
              <Radio-group v-model="mydynamic.source" @on-change="changeSource(mydynamic)">
                <Radio label="原创"></Radio>
                <Radio label="转载"></Radio>
              </Radio-group>
            </FormItem>
          </Col>
          <Col span="12" push="4"></Col>
        </Row>
        <FormItem label="发布人" prop="author">
          <Input type="text" v-model="mydynamic.author" readonly placeholder="发布人"></Input>
        </FormItem>
        <FormItem label="摘要" prop="summary">
          <Input type="textarea" v-model="mydynamic.summary" placeholder="摘要"></Input>
        </FormItem>
        <FormItem label="原创作者" prop="ycauthor" style="display: none;" class="ycauthor">
          <Input type="text" v-model="mydynamic.ycauthor" placeholder="请输入原创作者"></Input>
        </FormItem>
        <FormItem label="来源网站" prop="network" style="display: none;" class="network">
          <Input type="text" v-model="mydynamic.network" placeholder="请输入来源网站"></Input>
        </FormItem>
        <FormItem label="视频" v-show="video" prop="music">
          <upload-video @saveDescribe="saveDescribe" ref="music"></upload-video>
        </FormItem>
        <FormItem label="图册" prop="content" v-show="picture">
          <vui-upload @on-getPictureList="getCon" ref="atlasPicture" :hint="'图片大小小于2MB，最多上传 50 张'" :total="50" :size="[100,100]"></vui-upload>
        </FormItem>
        <FormItem label="音频" v-show="audios" prop="video">
          <upload-music @videoResult="saveDescribe" ref="video"></upload-music>
        </FormItem>
        <FormItem label="短信内容" prop="content" v-show="msm">
          <Input
            type="textarea"
            v-model="mydynamic.content"
            placeholder="最多200个字"
            :maxlength="length"
          ></Input>
        </FormItem>
        <FormItem label="微博正文" prop="content" v-show="weibo">
          <quil-weibo @quilWeibo="getCon" :uploadId="options.uploadId" @videoResult="saveDescribe"></quil-weibo>
        </FormItem>

        <FormItem label="详细内容" prop="content" v-show="con">
          <quil-editor
            @quilCon="getDetail"
            :content="mydynamic.content"
            :uploadId="options.uploadId"
            :paneType="mydynamic.leixin"
          ></quil-editor>
        </FormItem>
        <FormItem label="封面图片">
          <vui-upload @on-getPictureList="getCover" ref="coverPicture"  :hint="'图片大小小于2MB，最多上传 1 张'" :total="1" :multiple="false" :size="[100,100]"></vui-upload>
        </FormItem>
      </Form>
      <Form
        ref="pubstep2"
        :model="mydynamic"
        style="margin-top: 27px;"
        :rules="ruleInline2"
        label-position="right"
        :label-width="100"
        v-show="process === 2"
      >
        <FormItem label="关联物种" prop="species">
          <Input v-model="mydynamic.species" readonly @on-focus="handleFilterModal('speciFilter')"/>
        </FormItem>
        <FormItem label="章节" prop="chapter" v-if="options.knowledgeShow">
          <Input type="text" v-model="mydynamic.chapter" placeholder="章节"></Input>
        </FormItem>
        <template v-if="options.policyShow">
          <Form-item label="解读专家" prop="expert">
            <Input type="text" v-model="mydynamic.expert" placeholder="解读专家"></Input>
          </Form-item>
          <Form-item label="发文单位" prop="company">
            <Input type="text" v-model="mydynamic.company" placeholder="发文单位"></Input>
          </Form-item>
          <Form-item label="颁布时间" prop="promulgation">
            <DatePicker type="date" v-model="mydynamic.promulgation" :transfer="transfer"></DatePicker>
          </Form-item>
          <Form-item label="发文字号" prop="font">
            <Input type="text" v-model="mydynamic.font" placeholder="发文字号"></Input>
          </Form-item>
        </template>
        <FormItem label="通用商品名" prop="goodsname">
          <vuiCommodity ref="products" @on-save="getProducts" type="1" :values="products"></vuiCommodity>
          <!-- <Input v-model="products" readonly @on-focus="handleFilterModal('goodFilter')" /> -->
        </FormItem>
        <FormItem label="通用服务名" prop="servicename">
          <!-- getService -->
          <vuiCommodity ref="service" @on-save="getService" type="2" :values="service"></vuiCommodity>
          <!-- <Input readonly v-model="service" @on-focus="handleFilterModal('serviceFilter')" /> -->
        </FormItem>
        <FormItem label="行业分类" prop="industry">
          <Input
            v-model="mydynamic.industryName"
            readonly
            @on-focus="handleFilterModal('tradeFilter')"
          />
        </FormItem>
        <FormItem prop="district" label="适用区域">
          <Cascader
            :data="cityList"
            :render-format="format"
            v-model="mydynamic.district"
            :load-data="loadData"
            v-show="selectedLocation"
            change-on-select
          ></Cascader>
        </FormItem>
      </Form>
      <Form
        ref="pubstep3"
        style="margin-top: 27px;"
        :model="mydynamic"
        label-position="right"
        :label-width="100"
        v-show="process === 3 "
      >
        <FormItem label="好友提醒" prop="tixing">
          <Input type="text" v-model="mydynamic.tixing" placeholder="好友提醒"></Input>
        </FormItem>
        <FormItem label="提醒内容" prop="con">
          <Input type="textarea" v-model="mydynamic.con" placeholder="提醒内容"></Input>
        </FormItem>
        <Row>
          <Col span="11">
            <FormItem label="发送短信" prop="sms" style="text-align: left;">
              <Radio-group v-model="mydynamic.sms">
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </Radio-group>
            </FormItem>
          </Col>
          <Col span="11" offset="1">
            <FormItem label="提交首页显示" prop="xianshi">
              <Radio-group v-model="mydynamic.xianshi" @on-change="submitIndex(mydynamic)">
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </Radio-group>
            </FormItem>
          </Col>
        </Row>
        <FormItem label="公开范围" prop="security" style="text-align: left;">
          <Radio-group v-model="mydynamic.security">
            <Radio label="所有人可见"></Radio>
            <Radio label="仅好友可见"></Radio>
            <Radio label="仅自己可见"></Radio>
            <Radio label="仅指定组可见"></Radio>
          </Radio-group>
        </FormItem>
        <FormItem label="是否有偿" prop="money">
          <Radio-group v-model="mydynamic.money" @on-change="changeMoney(mydynamic)">
            <Radio label="收费"></Radio>
            <Radio label="免费"></Radio>
          </Radio-group>
        </FormItem>
      </Form>
      <div class="disclaimer" v-show="process === 3">
        <div class="disclaimer-title">
          <a href="javascript:void(0);" @click="onDisclaimer">
            <span>免责声明</span>
          </a>
        </div>
        <div class="disclaimer-content" v-show="disclaimer">
          <p>1.本网站只提供一个信息交流平台，不控制发布内容。发布内容及因之产生的后果，均由发布者自行负责，</p>
          <p>与本网站无关。我们对用户使用本网站所产生的一切后果，不承担任何责任；</p>
          <p>2.发贴内容须符合中华人民共和国和所在国法律，符合当地道德规范和风俗文化。内容真实，无欺诈；</p>
          <p>3.谢绝一切与本网站主题无关的内容，尤其不涉及政治、宗教、种族等议题</p>
          <p>4.不发布涉及他人版权的内容，不发布与栏目无关之内容；</p>
          <p>5.不发布与传销、直销及其他事涉不规范经济活动有关的内容。</p>
        </div>
      </div>
      <div slot="footer" style="text-algin:center" v-show="indexpane <= 2">
        <div style="text-algin:center">
          <Button
            type="primary"
            shape="circle"
            @click.native="preStep"
            style="width:100px;margin-right: 20px;"
            v-if="process !=1 && process !=4"
          >上一步</Button>
          <Button
            type="primary"
            shape="circle"
            @click.native="next('mydynamic')"
            style="width:100px;margin-right: 20px;"
            v-if="process ===1"
          >下一步</Button>
          <Button
            type="primary"
            shape="circle"
            @click.native="next('pubstep2')"
            style="width:100px;margin-right: 20px;"
            v-if="process ===2"
          >下一步</Button>
          <Button
            type="primary"
            shape="circle"
            @click.native="publishSubmit('pubstep3')"
            style="width:100px"
            v-if="process === 3"
          >发布</Button>
          <Button
            type="primary"
            shape="circle"
            @click.native="next('showbook')"
            style="width:100px;margin-right: 20px;"
            v-if="process ===4"
          >下一步</Button>
          <Button
            type="primary"
            shape="circle"
            @click.native="next('showbook2')"
            style="width:100px;margin-right: 20px;"
            v-if="process ===5"
          >下一步</Button>
          <Button
            type="primary"
            shape="circle"
            @click.native="next('showbook3')"
            style="width:100px;margin-right: 20px;"
            v-if="process === 6"
          >下一步</Button>
          <Button
            type="primary"
            shape="circle"
            @click.native="publishSubmit('showbook4')"
            style="width:100px"
            v-if="process === 7"
          >发布</Button>
        </div>
      </div>

      <!-- 相关行业tradeFilter -->
      <vui-filter
        ref="tradeFilter"
        :num="1"
        :simple="true"
        :pageShow="true"
        :total="total2"
        :pageCur="pageCur2"
        :resultDatas="tradeResultDatas"
        @on-search="handleTradeSearch"
        @on-page-change="handleTradePageChange"
        @on-get-result="handleGetTradeResult"
      />
      <!-- 相关物种 -->
      <vui-filter
        ref="speciFilter"
        :num="5"
        :cols="2"
        :pageShow="true"
        :total="total"
        :pageCur="pageCur"
        :classifyDatas="speciClassifyDatas"
        :resultDatas="speciResultDatas"
        :load-data="loadSpeciDatas"
        @on-search="handleSpeciSearch"
        @on-get-classify="handleGetSpeciClassify"
        @on-get-result="handleGetSpeciResult"
        @on-page-change="handleSpeciPageChange"
      />

      <!-- 相关服务 -->
      <vui-filter
        ref="serviceFilter"
        :num="5"
        :simple="true"
        :resultDatas="serviceResultDatas"
        @on-search="handleServiceSearch"
        @on-get-result="handleGetServiceResult"
      />
      <!-- 相关商品名 -->
      <vui-filter ref="goodFilter" :num="5" :simple="true" :resultDatas="[]"/>
    </Modal>
  </div>
</template>
<script>
import api from "~api";
import axios from "axios";
import quilEditor from "../../../components/vuequilEditor";
import quilWeibo from "../../../components/vuequilEditorWeibo";
import upload from "../../../components/upload";
import $ from "jquery";
import payment from "../../../components/payment";
import vuiFilter from "../../../components/vuiFilter/filter";
import dvideo from "../../../components/video";
import aplayer from "../../../components/aplayer";
import publishUpload from "../../../components/publishUpload";
import uploadMusic from "../../../components/uploadMusic";
import uploadVideo from "../../../components/uploadVideo";
import uploadCover from "../../../components/uploadCover";
import vuiSteps from "../../../components/vui-steps";
import book from "../../../components/book.vue";
import vuiUpload from "../../../components/vui-upload";
import vuiCommodity from "~components/vui-commodity";
export default {
  name: "dany",
  components: {
    quilEditor,
    upload,
    payment,
    vuiFilter,
    dvideo,
    quilWeibo,
    aplayer,
    publishUpload,
    uploadMusic,
    uploadVideo,
    uploadCover,
    vuiSteps,
    book,
    vuiUpload,
    vuiCommodity
  },
  props: {
    options: {
      type: Object
    }
  },
  data() {
    return {
      wenzhang: "wenzhang",
      tuce: "tuce",
      yinping: "yinping",
      shiping: "shiping",
      tushu: "tushu",
      duanxin: "duanxin",
      current: 0, //进度步骤
      stepDataSec: [
        { name: "基本信息" },
        { name: "详细信息" },
        { name: "关联信息" },
        { name: "发布设置" }
      ],
      speciesResult: "", //最终物种
      speciesIds: "", //物种id
      videoList: [],
      process: 1,
      scrollable: false,
      total: 0,
      total2: 0,
      pageCur2: 1,
      pageCur: 1,
      paneShow: false, //显示增加页签弹框
      modalp: false, //显示页签增加
      labelName: "", //页签名
      // disabled: false, //增加页签是否可点击
      indexpane: 0, //页签的顺序
      tabs: 0,
      isHide: false, //当前页是全部时隐藏
      country: "China",
      selectedLocation: true,
      infoTypeDatas: [
        {
          value: "business",
          label: "商务资讯",
          loading: false,
          children: []
        },
        {
          value: "official ",
          label: "公务资讯",
          loading: false,
          children: []
        }
      ],
      policyTypeDatas: [
        {
          value: "party",
          label: "党务",
          loading: false,
          children: []
        },
        {
          value: "affairs",
          label: "政务",
          loading: false,
          children: []
        }
      ],
      knowledgeTypeDatas: [
        {
          value: "animalHusbandry",
          label: "农林牧鱼",
          loading: false,
          children: []
        },
        {
          value: "foodScience",
          label: "食品科学",
          loading: false,
          children: []
        },
        {
          value: "zs07",
          label: "经济"
        },
        {
          value: "zs08",
          label: "法律"
        }
      ],
      disclaimer: false, //免责声明
      infoType: [],
      columns: [
        {
          value: "动态",
          label: "动态"
        },
        {
          value: "政策",
          label: "政策"
        },
        {
          value: "知识",
          label: "知识"
        },
        {
          value: "产品",
          label: "产品"
        },
        {
          value: "服务",
          label: "服务"
        },
        {
          value: "专家",
          label: "专家"
        },
        {
          value: "企业",
          label: "企业"
        },
        {
          value: "机关",
          label: "机关"
        }
      ],
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
      modal1: false,
      type: "publish",
      tradeResultDatas: [],
      industrys: [],
      transfer: true,
      servicenames: [],
      goodsnames: [],
      ruleInline: {
        summary: [
          // {
          //   type: "string",
          //   max: 200,
          //   message: "摘要不能超过200个字",
          //   trigger: "blur"
          // }
          {
            required: true,
            message: "摘要不能为空",
            trigger: "blur"
          }
        ],
        category: [
          {
            required: true,
            message: "栏目类型不能为空",
            trigger: "blur"
          }
        ],
        title: [
          {
            required: true,
            message: "标题不能为空",
            trigger: "blur"
          },
          {
            type: "string",
            max: 100,
            message: "标题不能超过100个字",
            trigger: "blur"
          }
        ],
        type: [
          {
            required: true,
            message: "分类不能为空",
            trigger: "blur"
          }
        ]
      },
      ruleInline2: {
        district: [
          {
            required: true,
            type: "array",
            message: "地区不能为空",
            trigger: "change"
          }
        ]
      },
      loginuserinfo: JSON.parse(
        sessionStorage.getItem(sessionStorage.getItem("key"))
      ),
      show: true,
      service: "",
      products: "",
      classfy: "",
      cityList: [],
      picture: false,
      audios: false,
      video: false,
      imgs: false,
      weibo: false,
      msm: false,
      con: true,
      length: 200,
      leibie: "",
      buttonCon: "",
      step: "",
      informationBookDetail: [],
      mydynamic: {},
      smallPaneData: [],
      allSmallPaneData: [],
      author: "",
      category: ''
    };
  },
  created() {
    //获得名字
    this.$api
      .post("/member/login/findCurrentUser", {
        account: this.loginuserinfo.loginAccount
      })
      .then(res => {
        if (res.data.displayName) {
          this.author = res.data.displayName;
        }
      });
    // 取地址
    api.post("/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816").then(res => {
      this.cityList = res.data;
    });
    // 取相关行业数据
    this.$api
      .post("/member/system-dict/getSystemDict", {
        typeName: "行业分类",
        pageNum: 1
      })
      .then(res => {
        var data = res.data.list;
        this.total2 = res.data.total;
        this.tradeResultDatas = data;
      });
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
  },
  methods: {
    //取图片封面地址
    handleGetBookPage(data) {
      let fileName = "test.jpg";
      let fileData = data;
      axios
        .post(`${this.$url.uploadBase64}/imgbase64/upload`, {
          fileName: fileName,
          fileData: fileData
        })
        .then(response => {
          if (response.data.code === "0") {
            this.full_photo = response.data.data.src;
          } else {
            this.$Message.error(response.data.msg);
          }
        });
    },
    // 取裁切base64地址
    handleGetBase64(data) {
      data.getCropData(d => {
        let fileName = "test.jpg";
        let fileData = d;
        axios
          .post(`${this.$url.uploadBase64}/imgbase64/upload`, {
            fileName: fileName,
            fileData: fileData
          })
          .then(response => {
            if (response.data.code === "0") {
              this.cover_photo = response.data.data.src;
            } else {
              this.$Message.error(response.data.msg);
            }
          });
      });
    },
    // 取图书数据
    handleGetBook(data) {
      this.informationBookDetail = data;
    },
    // 得到动态的分类
    getMydynamicType(value, arr) {
      // 赋值最后那个
      if (value.length >= 1) {
        let index = value.length - 1;
        this.mydynamic.type = value[index];
      }
    },
    // 取资讯类型
    loadInfoTypeDatas(item, callback) {
      item.loading = true;
      if (this.options.infoShow) {
        api.get(`/member/infoClass/information/${item.value}`).then(res => {
          item.loading = false;
          item.children = res.data;
          callback();
        });
      } else if (this.options.policyShow) {
        api.get(`/member/infoClass/policy/${item.value}`).then(res => {
          item.loading = false;
          item.children = res.data;
          callback();
        });
      } else if (this.options.knowledgeShow) {
        api.get(`/member/infoClass/knowledge/${item.value}`).then(res => {
          item.loading = false;
          item.children = res.data;
          callback();
        });
      }
    },
    //选择页签
    changeLabel(e) {
      this.mydynamic.custom = e;
      if (this.mydynamic.custom === "全部") {
        this.isHide = false;
      } else {
        this.isHide = true;
      }
    },
    //显示我的政策 知识 咨询下面的页签
    showMydynamic(index, category, categoryLabel) {
      console.log('categoryLabel', categoryLabel)
      this.category = categoryLabel
      this.indexpane = index;
      this.infoType = [];
      this.mydynamic = {
        book_author: "", //图书作者
        book_edition: "",
        book_publish: "",
        book_sheet: "",
        book_distribution: "",
        book_broadsheet: "",
        book_print_time: new Date(),
        book_word_count: "",
        book_pub_date: new Date(),
        book_paper: "",
        book_label: "",
        booktags: [], //图书字段
        cover_photo: "",
        full_photo: "",
        book_data: [],
        videoUrl: [],
        custom: "全部", //自定义分类
        promulgation: new Date(),
        font: "",
        company: "",
        expert: "",
        chapter: "",
        title: "",
        author: this.author,
        ycauthor: "",
        xianshi: "否",
        leixin: "文章",
        sms: "否",
        tixing: "",
        con: "",
        type: "",
        category: "020101",
        source: "原创",
        money: "免费",
        money1: 0,
        network: "",
        district: [],
        goodsname: "",
        servicename: "",
        species: "",
        summary: "",
        industry: "",
        industryName: "",
        security: "所有人可见",
        content: "",
        contentType: "information",
        imageAdd: "",
        isBook: "",
        account: ""
      };
      this.mydynamic.leixin = "文章";
      this.wenzhang = "wenzhang";
      this.tushu = "tushu";
      this.shiping = "shiping";
      this.tuce = "tuce";
      this.duanxin = "duanxin";
      this.yinping = "yinping";
      this.$refs.bookData.infoType = [];
      this.$refs.bookData.clean();
      this.current = 0;
      if (index === 1) {
        // 政策
        this.showPol();
        this.mydynamic.category = category;
      } else if (index === 2) {
        // 知识
        this.showLed();
        this.mydynamic.category = category;
      } else if (index === 0) {
        // 动态
        this.showDany();
        this.mydynamic.category = category;
      }
      this.$refs['atlasPicture'].handleGive('')
      this.$refs['coverPicture'].handleGive('')
      this.$refs['music'].videoList = []
      this.$refs['video'].videoList = []
    },
    //发布到首页时判断是否实名
    submitIndex(e) {
      if ("是" === e.xianshi) {
        this.mydynamic.xianshi = "是";
      } else {
        this.mydynamic.xianshi = "否";
      }
    },
    // 高级搜索弹窗
    handleFilterModal(name) {
      this.$refs[name].highFilterShow = true;
    },
    // 相关行业搜索
    // 行业类型
    handleTradeSearch(letter, keyword, result) {
      this.$api
        .post(`/member/system-dict/getSystemDict`, {
          dictName: keyword,
          typeName: "行业分类",
          character: letter === "全部" ? "" : letter,
          pageNum: 1
        })
        .then(res => {
          var d = res.data.list;
          this.total2 = res.data.total;
          this.pageCur2 = 1;
          this.tradeResultDatas = [];
          if (!d.length) return;
          if (result.length) {
            result.forEach(item => {
              d.forEach(child => {
                if (child.label === item.label) {
                  child.checked = true;
                  child.label = child.label;
                  child.value = child.value;
                }
              });
            });
          } else {
            d.forEach(child => {
              child.checked = false;
              child.label = child.label;
              child.value = child.value;
            });
          }
          this.tradeResultDatas = d;
        });
    },
    handleTradePageChange(letter, keyword, classify, num, result) {
      this.pageCur2 = num;
      this.$api
        .post("/member/system-dict/getSystemDict", {
          typeName: "行业分类",
          character: letter === "全部" ? "" : letter,
          dictName: keyword,
          pageNum: num
        })
        .then(res => {
          var data = res.data.list;
          this.total2 = res.data.total;
          this.tradeResultDatas = data;
        });
      this.$Message.success("筛选完成！");
    },
    handleGetTradeResult(classify, result) {
      var arr = [];
      var arrId = [];
      result.forEach(item => {
        arr.push(item.label);
        arrId.push(item.value);
      });
      //this.corpInfo2.trade = arr.join(' ')
      this.mydynamic.industry = arrId.join(" ");
      this.mydynamic.industryName = arr.join(" ");
    },
    loadData(item, callback) {
      item.loading = true;
      api.post(`/member/town/next/${item.value}`).then(res => {
        item.loading = false;
        item.children = res.data;
        callback();
      });
    },
    // 取相关物种
    handleSpeciSearch(letter, keyword, classify, result) {
      this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
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
    },
    handleSpeciPageChange(letter, keyword, classify, num, result) {
      this.pageCur = num;
      this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
    },
    loadSpeciResult(letter, keyword, classify, num, result) {
      if (classify.length) {
        var arr = [];
        var type = "";
        classify.forEach(item => {
          arr.push(item.classId);
          if (item.value !== undefined) type = item.value;
        });
      } else {
        arr = null;
      }
      api
        .post("/member/specicesClass/findSpecies", {
          keywords: keyword,
          fpinyin: letter === "全部" ? "" : letter,
          fclassifiedid: arr,
          type: type,
          pageNum: num,
          pageSize: 32
        })
        .then(res => {
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
    handleGetSpeciResult(classify, result) {
      /*var arr= []
			let speciesResult=[]
			result.forEach(item => arr.push(item.value))
			result.forEach(item => speciesResult.push(item.label))
			*/
      //this.speciesResult=arr.join(' ')
      let ids = [];
      let names = [];
      result.forEach(item => {
        ids.push(item.value);
        names.push(item.label);
      });
      this.mydynamic.species = names.join(" ");
      this.speciesIds = ids.join(",");
      this.speciesResult = names.join(",");
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
          }
        });
    },
    handleGetServiceResult(classify, result) {
      var arr = [];
      result.forEach(item => arr.push(item.value));
      var service = [];
      result.forEach(item => service.push(item.label));
      this.service = service.join(" ");
      this.$refs.bookData.service = service.join(" ");
      this.mydynamic.servicename = arr.join(" ");
    },
    // 通用服务名
    getService(result) {
      var arr = [];
      result.forEach(item => arr.push(item.value));
      var service = [];
      result.forEach(item => service.push(item.label));
      this.service = service.join(" ");
      this.$refs.bookData.service = service.join(" ");
      this.mydynamic.servicename = arr.join(" ");
    },
    // 通用商品名
    getProducts(result) {
      var arr = [];
      result.forEach(item => arr.push(item.value));
      var products = [];
      result.forEach(item => products.push(item.label));
      this.products = products.join(" ");
      this.$refs.bookData.products = products.join(" ");
      this.mydynamic.goodsname = arr.join(" ");
    },
    format(labels, selectedData) {
      if (labels.length < 1) {
        return "";
      } else {
        this.mydynamic.district = labels;
        return labels.join("/");
      }
    },
    getCountry(value) {
      if (value === "China") {
        this.selectedLocation = true;
        this.mydynamic.district = [];
      } else {
        this.selectedLocation = false;
        this.mydynamic.district = [];
        this.mydynamic.district.push("unlimited");
      }
    },
    showDany() {
      //资讯发布
      this.options.infoShow = true;
      this.options.policyShow = false;
      this.options.knowledgeShow = false;
      this.options.realUrl = "/inforMation/publish";
      this.mydynamic.contentType = "dynamic";
      this.options.categorys = [
        {
          label: "会员动态",
          value: "020107"
        },
        {
          label: "行业动态",
          value: "020101"
        },
        {
          label: "企业动态",
          value: "020102"
        },
        {
          label: "专家动态",
          value: "020103"
        },
        {
          label: "乡村动态",
          value: "020104"
        },
        {
          label: "群协动态",
          value: "020105"
        },
        {
          label: "国际动态",
          value: "020106"
        },
        {
          label: "城乡互动",
          value: "020108"
        }
      ];
    },
    showLed() {
      //发布知识
      this.options.knowledgeShow = true;
      this.options.policyShow = false;
      this.options.infoShow = false;
      this.options.realUrl = "/knowLege/publish";
      this.mydynamic.contentType = "knowledge";
      this.options.categorys = [
        {
          label: "科普",
          value: "040101"
        },
        {
          label: "论文",
          value: "040102"
        },
        {
          label: "图书",
          value: "040103"
        },
        {
          label: "专利",
          value: "040104"
        },
        {
          label: "种养标准",
          value: "040105"
        },
        {
          label: "科技成果",
          value: "040106"
        },
        {
          label: "经验交流",
          value: "040107"
        }
      ];
    },
    //免责声明
    onDisclaimer() {
      this.disclaimer = !this.disclaimer;
    },
    bookparent(name) {
      this.handleFilterModal(name);
    },

    showPol(id, name) {
      //发布政策
      this.options.showAll = false;
      this.options.policyShow = true;
      this.options.knowledgeShow = false;
      this.options.infoShow = false;
      this.options.realUrl = "/policy/publish";
      this.mydynamic.contentType = "policy";
      this.options.categorys = [
        {
          label: "政策法规",
          value: "030201"
        },
        {
          label: "政策解读",
          value: "030202"
        },
        {
          label: "通知公告",
          value: "030203"
        },
        {
          label: "党务动态",
          value: "030209"
        },
        {
          label: "政务动态",
          value: "030210"
        },
        {
          label: '招商引资',
          value: '030211'
        }
      ];
      //如果是乡村的
      if (5 === this.loginuserinfo.userType) {
        this.options.categorys.push({ label: "村务公开", value: "030204" });
        this.options.categorys.push({ label: "党务公开", value: "030205" });
        this.options.categorys.push({ label: "公告公示", value: "030306" });
      }
      if (3 === this.loginuserinfo.userType) {
        this.options.categorys.push({ label: "政务法规", value: "030207" });
        this.options.categorys.push({ label: "政务公开", value: "030208" });
      }
    },

    publishSubmit(name) {
      this.$emit("showDany", false);
      var _this = this;
      $(".network").css("display", "none");
      var txt = this.mydynamic.security;
      if (txt == "所有人可见") {
        txt = 0;
      } else if (txt == "仅好友可见") {
        txt = 1;
      } else if (txt == "仅自己可见") {
        txt = 2;
      } else if (txt == "仅指定组可见") {
        txt = 3;
      }
      var leixin = this.mydynamic.leixin;
      if (leixin == "图书") {
        this.mydynamic.book_label = this.$refs.bookData.mydynamic.booktags;
        api
          .post("/member" + this.options.realUrl, {
            promulgation: this.moment(this.mydynamic.promulgation).format(
              "YYYY-MM-DD HH:mm:ss"
            ),
            font: this.mydynamic.font,
            company: this.mydynamic.company,
            expert: this.mydynamic.expert,
            chapter: this.mydynamic.chapter,
            custom: this.mydynamic.custom,
            title: this.mydynamic.title,
            xianshi: this.mydynamic.xianshi,
            leixin: this.mydynamic.leixin,
            sms: this.mydynamic.sms,
            tixing: this.mydynamic.tixing,
            con: this.mydynamic.con,
            type: this.mydynamic.type,
            category: this.mydynamic.category,
            source: this.mydynamic.source,
            money: this.mydynamic.money,
            money1: this.mydynamic.money,
            network: this.mydynamic.network,
            district: this.mydynamic.district.join("/"),
            goodsname: this.mydynamic.goodsname,
            servicename: this.mydynamic.servicename,
            species: this.speciesResult,
            speciesId: this.speciesIds,
            summary: this.mydynamic.summary,
            industry: this.mydynamic.industry,
            author: this.mydynamic.author,
            ycauthor: this.mydynamic.ycauthor,
            tixing: this.mydynamic.tixing,
            content: this.mydynamic.content,
            security: txt,
            video: this.mydynamic.videoUrl,
            imageAdd: this.mydynamic.imageAdd,
            book_author: this.$refs.bookData.mydynamic.book_author,
            book_edition: this.$refs.bookData.mydynamic.book_edition,
            book_publish: this.$refs.bookData.mydynamic.book_publish,
            book_sheet: this.$refs.bookData.mydynamic.book_sheet,
            book_distribution: this.$refs.bookData.mydynamic.book_distribution,
            book_broadsheet: this.$refs.bookData.mydynamic.book_broadsheet,
            book_print_time: this.moment(
              this.$refs.bookData.mydynamic.book_print_time
            ).format("YYYY-MM-DD HH:mm:ss"),
            book_word_count: this.$refs.bookData.mydynamic.book_word_count,
            book_pub_date: this.moment(
              this.$refs.bookData.mydynamic.book_pub_date
            ).format("YYYY-MM-DD HH:mm:ss"),
            book_paper: this.$refs.bookData.mydynamic.book_paper,
            book_label: this.mydynamic.book_label,
            book_cover_photo: this.cover_photo,
            book_full_photo: this.full_photo,
            book_data: this.informationBookDetail,
            isBook: 1,
            account: this.$user.loginAccount
          })
          .then(response => {
            if (response.code === 200) {
              _this.$Message.success("发布成功!");
              this.$emit("on-init", this.mydynamic.contentType);
              // window.location.reload()
            } else {
              _this.$Message.error("发布失败!");
            }
          });
      } else {
        api
          .post("/member" + this.options.realUrl, {
            promulgation: this.moment(this.mydynamic.promulgation).format(
              "YYYY-MM-DD HH:mm:ss"
            ),
            font: this.mydynamic.font,
            company: this.mydynamic.company,
            expert: this.mydynamic.expert,
            chapter: this.mydynamic.chapter,
            custom: this.mydynamic.custom,
            title: this.mydynamic.title,
            xianshi: this.mydynamic.xianshi,
            leixin: this.mydynamic.leixin,
            sms: this.mydynamic.sms,
            tixing: this.mydynamic.tixing,
            con: this.mydynamic.con,
            type: this.mydynamic.type,
            category: this.mydynamic.category,
            source: this.mydynamic.source,
            money: this.mydynamic.money,
            money1: this.mydynamic.money,
            network: this.mydynamic.network,
            district: this.mydynamic.district.join("/"),
            goodsname: this.mydynamic.goodsname,
            servicename: this.mydynamic.servicename,
            species: this.speciesResult,
            speciesId: this.speciesIds,
            summary: this.mydynamic.summary,
            industry: this.mydynamic.industry,
            author: this.mydynamic.author,
            ycauthor: this.mydynamic.ycauthor,
            tixing: this.mydynamic.tixing,
            content: this.mydynamic.content,
            security: txt,
            video: this.mydynamic.videoUrl,
            imageAdd: this.mydynamic.imageAdd,
            account: this.$user.loginAccount
          })
          .then(response => {
            if (response.code === 200) {
              _this.$Message.success("发布成功!");
              this.$emit("on-init", this.mydynamic.contentType);
              // window.location.reload()
            } else {
              _this.$Message.error("发布失败!");
            }
          });
      }
    },
    changeMoney(e) {
      if ("收费" === e.money) {
        window.open("/payReading");
      }
    },
    //网站来源框显示
    changeSource(e) {
      if ("转载" === e.source) {
        $(".network").show();
        $(".ycauthor").show();
      } else {
        $(".network").hide();
        $(".ycauthor").hide();
      }
    },
    //通过文稿类型展示不同界面
    check(e) {
      if ("视频" === e.leixin) {
        this.process = 1;
        this.video = true;
        this.audios = false;
        this.picture = false;
        this.weibo = false;
        this.con = false;
        this.msm = false;
      } else if ("音频" === e.leixin) {
        this.process = 1;
        this.audios = true;
        this.video = false;
        this.picture = false;
        this.weibo = false;
        this.con = false;
        this.msm = false;
      } else if ("图册" === e.leixin) {
        this.process = 1;
        this.buttonCon = "选择图片";
        this.audios = false;
        this.video = false;
        this.picture = true;
        this.weibo = false;
        this.con = false;
        this.msm = false;
      } else if ("微博" === e.leixin) {
        this.audios = false;
        this.video = false;
        this.picture = false;
        this.weibo = true;
        this.con = false;
        this.msm = false;
      } else if ("短信" === e.leixin) {
        this.process = 1;
        this.audios = false;
        this.video = false;
        this.picture = false;
        this.weibo = false;
        this.con = false;
        this.msm = true;
      } else if ("文章" === e.leixin) {
        this.process = 1;
        this.audios = false;
        this.video = false;
        this.picture = false;
        this.weibo = false;
        this.con = true;
        this.msm = false;
      } else if ("图书" === e.leixin) {
        this.process = 4;
      }
    },
    //发布文章获取详细内容的数据
    getDetail(msg) {
      this.mydynamic.content = msg;
    },
    //获取图册的图片
    getCon(msg) {
      var str = "";
      msg.forEach(element => {
        if (element.response) {
          str += `<img src=${element.response.data.picName}>`;
        }
      });
      this.mydynamic.content = str;
      console.log(this.mydynamic.content);
    },
    cancel(name) {
      this.$emit("showDany", false);
      this.$refs[name].resetFields();
      this.mydynamic.leixin = "文章";
      (this.wenzhang = "wenzhang"),
        (this.tushu = "tushu"),
        (this.shiping = "shiping"),
        (this.tuce = "tuce"),
        (this.duanxin = "duanxin"),
        (this.yinping = "yinping"),
        (this.$refs.bookData.infoType = []),
        this.$refs.bookData.clean();
      this.current = 0;
    },
    reset(name) {
      this.$refs[name].resetFields();
    },
    preStep() {
      if ("微博" === this.mydynamic.leixin) {
        this.process = 1;
      } else {
        this.process--;
      }
      if (this.current < 4) {
        this.current--;
      } else {
        this.current = 3;
      }
    },
    next(name) {
      if ("mydynamic" === name || "showbook" === name) {
        if ("微博" === this.mydynamic.leixin) {
          this.$refs[name].validate(valid => {
            if (valid) {
              this.process = 3;
            }
          });
        } else if ("音频" === this.mydynamic.leixin) {
          (this.wenzhang = ""),
            (this.tushu = ""),
            (this.shiping = ""),
            (this.tuce = ""),
            (this.duanxin = "");
          if (
            this.mydynamic.videoUrl === "" ||
            this.mydynamic.videoUrl.length <= 0
          ) {
            this.$Message.error("请上传至少一个音频!");
          } else {
            this.process++;
          }
        } else if ("视频" === this.mydynamic.leixin) {
          (this.wenzhang = ""),
            (this.tushu = ""),
            (this.yinping = ""),
            (this.tuce = ""),
            (this.duanxin = "");
          if (
            this.mydynamic.videoUrl === "" ||
            this.mydynamic.videoUrl.length <= 0
          ) {
            this.$Message.error("请上传至少一个视频!");
          } else {
            this.process++;
          }
        } else if ("文章" === this.mydynamic.leixin) {
            (this.tuce = ""),
            (this.tushu = ""),
            (this.yinping = ""),
            (this.shiping = ""),
            (this.duanxin = "");
          if (this.mydynamic.content === "") {
            this.$Message.error("详细内容不能为空!");
          } else if (this.mydynamic.content.length > 10000) {
            this.$Message.error("详细内容不能超过10000字!");
          } else {
            this.$refs[name].validate(valid => {
              if (valid) {
                this.process++;
              }
            });
          }
        } else if ("图册" === this.mydynamic.leixin) {
          (this.wenzhang = ""),
            (this.tushu = ""),
            (this.yinping = ""),
            (this.shiping = ""),
            (this.duanxin = "");
          if (this.mydynamic.content === "") {
            this.$Message.error("图册不能为空!");
          } else {
            this.$refs[name].validate(valid => {
              if (valid) {
                this.process++;
              }
            });
          }
        } else if ("图书" === this.mydynamic.leixin) {
          (this.wenzhang = ""),
            (this.tuce = ""),
            (this.yinping = ""),
            (this.shiping = ""),
            (this.duanxin = "");
          let flag = this.$refs.bookData.yanzheng(name);
          if (flag) {
            this.current++, this.process++;
          }
          if (this.mydynamic.source == "转载") {
            this.mydynamic.book_author = this.mydynamic.ycauthor;
          } else {
            this.mydynamic.book_author = "";
          }
        } else {
          this.$refs[name].validate(valid => {
            if (valid) {
              this.process++;
            }
          });
        }
      } else if (
        "showbook2" === name ||
        "showbook3" === name ||
        "showbook4" === name
      ) {
        let flag = this.$refs.bookData.yanzheng(name);
        if (flag) {
          if (this.current === 4) {
            this.current = 0;
          } else {
            this.current++;
          }
          this.process++;
        }
      } else {
        this.$refs[name].validate(valid => {
          if (valid) {
            if (this.current === 4) {
              this.current = 0;
            } else {
              this.current++;
            }
            this.process++;
          }
        });
      }
    },
    saveDescribe(val) {
      if (val.length) {
        val.forEach(e => {
          e.column = this.options.title;
        });
        this.mydynamic.videoUrl = val;
      } else {
        this.mydynamic.content = [];
      }
    },
    //保存封面
    getCover(e) {
      var arr = [];
      e.forEach(element => {
        if (element.response) {
          arr.push(element.response.data.picName);
        }
      });
      this.mydynamic.imageAdd = arr[0];
    },
    refresh(value) {
      // 每次打开发布的页面 都初始化里面的数据
      if (value) {
        this.process = 1;
        this.audios = false;
        this.video = false;
        this.picture = false;
        this.weibo = false;
        this.con = true;
        this.msm = false;
      }
    }
  }
};
</script>
<style scoped>
#ivu-form-item-label-custom::before {
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
.publish .inputwidth {
  width: 443px;
}
.disclaimer {
}
.disclaimer-title {
  text-align: center;
  font-size: 14px;
  color: #00c587;
  margin-bottom: 10px;
}
.disclaimer-title span {
  color: #00c587;
}
.disclaimer-content {
  width: 664px;
  margin-left: 48px;
  background-color: #f8f8f8;
  padding-left: 15px;
  padding-top: 15px;
  padding-bottom: 15px;
}
</style>
