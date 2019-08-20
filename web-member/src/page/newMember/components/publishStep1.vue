<template>
  <div>
      <div>
        <Form :label-width="100" :rules="ruleInline1" :model="mydynamic" ref="mydynamic">
          <FormItem label="标题" prop="title">
            <Input v-model="mydynamic.title" @on-focus="onFocus" :placeholder="title" :maxlength="100" :readonly="mydynamic.leixin == '图书'"></Input>
          </FormItem>
          <FormItem label="分类" prop="type">
            <Cascader @on-change="getMydynamicType" :data="infoTypeDatas" v-model="mydynamic.typeList"></Cascader>
          </FormItem>
          <FormItem label="栏目类别" prop="category">
            <Select v-model="mydynamic.category">
              <Option v-for="(item,index) in categorys" :key="index" :value="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
          <FormItem label="信息来源">
            <Radio-group v-model="mydynamic.source">
              <Radio label="原创"></Radio>
              <Radio label="转载"></Radio>
            </Radio-group>
          </FormItem>
          <FormItem label="发布人" prop="author">
            <Input v-model="mydynamic.author" placeholder="标题" readonly></Input>
          </FormItem>
          <FormItem label="摘要" prop="summary">
            <Input type="textarea" v-model="mydynamic.summary" placeholder="摘要"></Input>
          </FormItem>
          <FormItem label="原创作者" prop="ycauthor" v-if="mydynamic.source == '转载'">
            <Input type="text" v-model="mydynamic.ycauthor" placeholder="请输入原创作者"></Input>
          </FormItem>
          <FormItem label="来源网站" prop="network" v-if="mydynamic.source == '转载'">
            <Input type="text" v-model="mydynamic.network" placeholder="请输入来源网站"></Input>
          </FormItem>
          <FormItem label="详细内容" v-if="type == '文章'">
              <quil-editor @quilCon="getDetail" :content="mydynamic.articleContent" ></quil-editor>
          </FormItem>
          <FormItem label="图册" v-if="type == '图册'">
            <vui-upload @on-getPictureList="getCon" ref="atlasPicture" :hint="'图片大小小于2MB，最多上传 50 张'" :total="50" :size="[100,100]"></vui-upload>
          </FormItem>
          <FormItem label="音频" v-if="type == '音频'">
            <upload-music @videoResult="saveDescribe" ref="music"></upload-music>
          </FormItem>
          <FormItem label="视频" v-if="type == '视频'">
            <upload-video @saveDescribe="saveDescribe" ref="video"></upload-video>
          </FormItem>
          <FormItem label="短信内容" v-if="type == '短信'">
            <Input type="textarea" v-model="mydynamic.smsContent" placeholder="最多200个字" :maxlength="200" ></Input>
          </FormItem>
          <FormItem label="封面图片" v-if="type !== '图书'">
            <vui-upload @on-getPictureList="getCover" ref="coverPicture" :hint="'图片大小小于2MB，最多上传 1 张'" :total="1" :multiple="false" :size="[100,100]"></vui-upload>
          </FormItem>
          <Row>
            <Col span="12">
              <FormItem label="农事无忧ID">
                <Input v-model="list.nswyId" placeholder="农事无忧ID" disabled></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="用户名">
                <Input v-model="list.account" placeholder="用户名" disabled></Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem label="昵称">
                <Input v-model="list.realname" placeholder="昵称" disabled></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="门户网站">
                <Input v-model="list.website" placeholder="门户网站" disabled></Input>
              </FormItem>
            </Col>
          </Row>
        </Form>
      </div>
      <chooseBook ref="chooseBook" @on-save="saveBook"></chooseBook>
  </div>
</template>
<script>
import quilEditor from "../../../components/vuequilEditor";
import vuiUpload from '~components/vui-upload'
import uploadMusic from "~components/uploadMusic";
import uploadVideo from "~components/uploadVideo";
import chooseBook from "./chooseBook";
export default {
  components: {
    quilEditor,
    vuiUpload,
    uploadMusic,
    uploadVideo,
    chooseBook,
  },
  props: {
    data: {
      type: Object,
      default: () => {
        return {}
      }
    },
    type: {
      type: String,
      default: '文章'
    }
  },
  data () {
    return {
      title: '标题',
      // 分类
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
      categorys: [
        {
          value: "0",
          label: "乡村介绍",
        },{
          value: "1",
          label: "企业介绍",
        },{
          value: "2",
          label: "专家介绍",
        },{
          value: "3",
          label: "会员介绍",
        }
      ],
      mydynamic: {
        leixin: '文章',
        title: '',
        type: '',
        category: '',
        source: '原创',
        author: '',
        summary: '',
        ycauthor: '',
        network: '',
        content: '', // 提交时用的 内容字段 （分开主要是 短信内容发生改变后，会触发富文本里面的事件）
        articleContent: '', // 文章的详细内容 
        smsContent: '', // 短信的内容
        imageAdd: '', // 封面
        video: [],
        typeList: [],
      },
      ruleInline1: {
        category: [
          {required: true, message: "栏目类型不能为空", trigger: "change"}
        ],
        type: [
          {required: true, message: "分类不能为空", trigger: "change"}
        ],
        title: [
          {required: true, message: "标题不能为空", trigger: "blur"},
          {required: true, message: "标题不能为空", trigger: "change"}
        ]
      },
      infoType: [],
      list: {}
    }
  },
  watch: {
    data: {
      handler(curVal, oldVal) {
        console.log('更新了')
        this.mydynamic = curVal
        this.init()
      },
      deep: true
    }
  },
  created() {
    this.mydynamic = this.data
    this.init()
    // 获取分类数据
    this.infoTypeDatas.forEach((item, index) => {
      this.$api.get(`/member/infoClass/information/${item.value}`).then(res => {
          // item.loading = false;
          item.children = res.data;
        })
    })
    // 查询昵称等信息
    this.$api.post('/member/memberIntroduce/findNswyInfo', {account: this.$user.loginAccount}).then(response => {
      if (response.code === 200) {
        this.list.account = response.data.account
        this.list.nswyId = response.data.nswyId
        this.list.realname = response.data.realname
        this.list.website = `${window.location.origin}/portals/index?uid=${this.mydynamic.account}&id=0`
        this.mydynamic.author ? '' : this.mydynamic.author = this.list.realname
      } else {
        this.$Message.error('查询失败')
      }
    })
  },
  methods: {
    init () {
      if (this.mydynamic.leixin == '图书') {
        this.title = '从文件管理中选择'
      } else {
        this.title = '标题'
      }
      this.mydynamic.author ? '' : this.mydynamic.author = this.list.realname
      // 上传图片组件，图片回显，或者清除
      this.$nextTick (() => {
        let musicList = []
        let videoList = []
        if (this.mydynamic.video) {
          this.mydynamic.video.forEach(e => {
            e.musicName = e.musicName ? e.musicName : e.mediaName
            e.type === 1 ? musicList.push(e) : ''
            e.type === 0 ? videoList.push(e) : ''
          })
        }
        if (this.mydynamic.leixin == '音频') {
          this.$refs['music'].videoList = musicList
        } else if (this.mydynamic.leixin == '视频'){
          this.$refs['video'].videoList = videoList
        } else if (this.mydynamic.leixin == '图册'){
          this.$refs['atlasPicture'].handleGive(this.mydynamic.atlasList)
        }
        if (this.mydynamic.leixin != '图书'){
          this.$refs['coverPicture'].handleGive(this.mydynamic.imageAdd)
        }
      })
    },
    onFocus () {
      if (this.mydynamic.leixin == '图书') {
        this.$refs['chooseBook'].init()
      }
    },
    saveBook (data) {
      console.log(data)
      this.mydynamic.title = data.title
      this.mydynamic.source = data.source
      this.mydynamic.summary = data.summary ? data.summary : ''
      this.mydynamic.network = data.network ? data.network : ''
      this.mydynamic.ycauthor = data.ycauthor ? data.ycauthor : ''
      this.$emit('on-echo-book', data)
    },
    next () {
      let flag = true
      if (this.type == '文章' && !this.mydynamic.articleContent) {
        // 判断详细内容是否为空
        this.$Message.error('请填写详细内容')
        flag = false
      } else if (this.type == '图册' && (!this.mydynamic.atlasList || !this.mydynamic.atlasList.length)) {
        this.$Message.error("图册不能为空!");
        flag = false
      } else if (this.type == '音频' && (!this.mydynamic.video || !this.mydynamic.video.length)) {
        this.$Message.error("音频不能为空!");
        flag = false
      } else if (this.type == '视频' && (!this.mydynamic.video || !this.mydynamic.video.length)) {
        this.$Message.error("视频不能为空!");
        flag = false
      } else if (this.type == '短信' && !this.mydynamic.smsContent) {
        this.$Message.error("短信内容不能为空!");
        flag = false
      }
      this.$refs[`mydynamic`].validate(v => {
        if (!v) {
          flag = false
          this.$Message.error('请核对表单信息')
          this.$emit('on-next', flag)
        } else {
          this.$emit('on-next', flag)
        }
      })
    },
    // 获取资讯类型
    loadInfoTypeDatas (item, callback) {
        this.$api.get(`/member/infoClass/information/${item.value}`).then(res => {
          item.loading = false;
          item.children = res.data;
          callback()
        })
    },
    // 选择分类后 得到动态的分类
    getMydynamicType(value, arr) {
      console.log(value)
      // 赋值最后那个
      if (value.length >= 1) {
        let index = value.length - 1;
        this.mydynamic.type = value[index];
      }
    },
    // 发布文章获取详细内容的数据
    getDetail(msg) {
      this.mydynamic.articleContent = msg;
      console.log('999999999')
    },
    // 封面图片 保存封面
    getCover(e) {
      var arr = [];
      e.forEach(element => {
        if (element.response) {
          arr.push(element.response.data.picName);
        }
      });
      this.mydynamic.imageAdd = arr[0];
    },
    //获取图册的图片
    getCon(msg) {
      var str = "";
      var arr = [];
      msg.forEach(element => {
        if (element.response) {
          str += `<img src=${element.response.data.picName}>`;
          arr.push(element.response.data.picName);
        }
      });
      this.mydynamic.content = str;
      this.mydynamic.atlasList = arr;
      console.log(this.mydynamic.content);
    },
    // 获取音频
    saveDescribe(val) {
      if (val.length) {
        val.forEach(e => {
          e.column = '会员介绍';
        });
        this.mydynamic.video = val;
        console.log('会员介绍', this.mydynamic.video)
      } else {
        this.mydynamic.content = '';
      }
    },
  },
}
</script>