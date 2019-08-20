<template>
  <div>
    <Modal
      v-model="isShow"
      :mask-closable="false"
      width="750"
      heigth="700px"
      :styles="{top: '20px'}"
      title="发布会员介绍">
        <div>
          <RadioGroup v-model="mydynamic1.leixin" type="button" class="mb20">
            <Radio :disabled="mydynamic1.leixin !== item.label  && currentStep > 1" :label="item.label" v-for="(item, index) in RadioGroups"></Radio>
          </RadioGroup>
          <div class="pb20" v-if="mydynamic1.leixin == '图书'">
            <vui-steps :data="stepDataSec" :active="currentStep-1" ></vui-steps>
          </div>
          <div class="pt20" v-if="mydynamic1.leixin == '图书'">
              <publishStep1 :type="mydynamic1.leixin" :data="mydynamic1" ref="step1" @on-echo-book="onEchoBook" @on-next="onNext" v-if="currentStep == 1"></publishStep1>
              <publishBook :source="mydynamic1.source" :data="mydynamic2" ref="step2" @on-next="onNext" v-if="currentStep == 2"></publishBook>
              <publishStep2 :type="mydynamic1.leixin" :data="mydynamic3" ref="step3" @on-next="onNext" v-if="currentStep == 3"></publishStep2>
              <publishStep3 :type="mydynamic1.leixin" :data="mydynamic4" ref="step4" @on-next="onNext" v-if="currentStep == 4"></publishStep3>
          </div>
          <div class="pt20" v-if="mydynamic1.leixin !== '图书'">
              <publishStep1 :type="mydynamic1.leixin" :data="mydynamic1" ref="step1" @on-next="onNext" v-if="currentStep == 1"></publishStep1>
              <publishStep2 :type="mydynamic1.leixin" :data="mydynamic3" ref="step2" @on-next="onNext" v-if="currentStep == 2"></publishStep2>
              <publishStep3 :type="mydynamic1.leixin" :data="mydynamic4" ref="step3" @on-next="onNext" v-if="currentStep == 3"></publishStep3>
          </div>
        </div>
        <div slot="footer">
          <div class="tr pr30" v-if="mydynamic1.leixin !== '图书'">
            <Button type="primary" class="pr30 pl30" shape="circle" v-if="currentStep > 1" @click="Prev">上一步</Button>
            <Button type="primary" class="pr30 pl30" shape="circle" @click="next" v-if="currentStep < 3">下一步</Button>
            <Button type="primary" class="pr30 pl30" shape="circle" @click="next" v-if="currentStep > 2">发布</Button>
          </div>
          <div class="tr pr30" v-else>
            <Button type="primary" class="pr30 pl30" shape="circle" v-if="currentStep > 1" @click="Prev">上一步</Button>
            <Button type="primary" class="pr30 pl30" shape="circle" @click="next" v-if="currentStep < 4">下一步</Button>
            <Button type="primary" class="pr30 pl30" shape="circle" @click="next" v-if="currentStep > 3">发布</Button>
          </div>
        </div>
    </Modal>
  </div>
</template>
<script>
import vuiSteps from "~components/vui-steps";
import publishBook from "~components/publishBook";
import publishStep1 from "./publishStep1";
import publishStep2 from "./publishStep2";
import publishStep3 from "./publishStep3";
export default {
  components: {
    vuiSteps,
    publishStep1,
    publishStep2,
    publishStep3,
    publishBook
  },
  data () {
    return {
      isShow: false,
      disclaimer: false,
      // 发布的类型
      RadioGroups: [
        {label: '文章'},
        {label: '图册'},
        {label: '音频'},
        {label: '视频'},
        {label: '图书'},
        {label: '短信'},
      ],
      // 发布图书
      stepDataSec: [
        { name: "基本信息" },
        { name: "详细信息" },
        { name: "关联信息" },
        { name: "发布设置" }
      ],
      // 当前第几步
      currentStep: 1,
      mydynamic1: {},
      mydynamic2: {},
      mydynamic3: {},
      mydynamic4: {},
    }
  },
  created() {
    
  },
  methods: {
    onEchoBook (data) {
      this.mydynamic1.title = data.title ? data.title : ''
      this.mydynamic1.source = data.source ? data.source : ''
      this.mydynamic1.summary = data.summary ? data.summary : ''
      this.mydynamic1.network = data.network ? data.network : ''
      this.mydynamic1.ycauthor = data.ycauthor ? data.ycauthor : ''
      this.mydynamic2.book_author = data.author ? data.author : ''
      this.mydynamic2.book_edition = data.edition ? data.edition : ''
      this.mydynamic2.book_publish = data.publish ? data.publish : ''
      this.mydynamic2.book_sheet = data.sheet ? data.sheet : ''
      this.mydynamic2.book_broadsheet = data.broadsheet ? data.broadsheet : ''
      this.mydynamic2.book_distribution = data.distribution ? data.distribution : ''
      this.mydynamic2.book_print_time = data.print_time ? data.print_time : ''
      this.mydynamic2.book_word_count = data.word_count ? data.word_count : ''
      this.mydynamic2.book_pub_date = data.pub_date ? data.pub_date : ''
      this.mydynamic2.book_paper = data.paper ? data.paper : ''
      this.mydynamic2.book_label = data.label ? data.label : ''
      if (data.book_data && data.book_data.length) {
        data.book_data.forEach((e, index) => {
          e.checked == 'false' ? e.checked = false : e.checked = true
          e.edit == 'false' ? e.edit = false : e.edit = true
          e.expand == 'false' ? e.expand = false : e.expand = true
          e.children.forEach((element,i) => {
            element.checked == 'false' ? element.checked = false : element.checked = true
            element.edit == 'false' ? element.edit = false : element.edit = true
            element.expand == 'false' ? element.expand = false : element.expand = true
            element.file ? element.file = [{name: element.file_name, src: element.file}] : element.file = []
          })
        })
      }
      this.mydynamic2.book_data = data.book_data
      this.mydynamic2.book_cover_photo = data.cover_photo ? data.cover_photo : ''      
      this.mydynamic3.serviceId = data.serviceId ? data.serviceId : ''
      this.mydynamic3.servicename = data.service ? data.service : ''
      this.mydynamic3.goodsId = data.productsId ? data.productsId : ''
      this.mydynamic3.goodsname = data.products ? data.products : ''
      this.mydynamic3.industryId = data.industryId ? data.industryId : ''
      this.mydynamic3.industryName = data.industryName ? data.industryName : ''
      this.mydynamic3.species = data.species ? data.species : ''
      this.mydynamic3.speciesId = data.speciesId ? data.speciesId : ''
      this.mydynamic3.district = data.district ? data.district : ''
    },
    init () {
      // 查询 之前是否有发布过 
      this.$api.post('/member/memberIntroduce/findMemberIntroduceInfo', {account: this.$user.loginAccount}).then(response => {
        if (response.code === 200 && response.data) { //发布过 查询发布数据，回显
          this.$api.post('/member/memberIntroduce/findMemberIntroduceInfoById', {id: response.data.member_introduce_detail_id}).then(response => {
            if (response.code === 200) {
              if (response.data) { // 是编辑
                let data = response.data
                this.mydynamic1 = {
                  custom: '全部', // 小标签 之前有，现在不用了，默认全部
                  leixin: data.columnType, // 发布的大的分类
                  title: data.title, // 标题
                  type: data.typeList ? data.typeList[data.typeList.length-1] : '', // 分类 取数组最后一个 
                  typeList: data.typeList ? data.typeList : [], // 分类存的数组
                  category: data.docType, // 栏目类型
                  source: data.source ? data.source : '', // 是否原创
                  author: data.editer ? data.editer : '', // 发布人
                  summary: data.abstracts ? data.abstracts : '', // 摘要
                  ycauthor: data.ycauthor ? data.ycauthor : '', // 转载的 作者
                  network: data.network ? data.network : '', // 转载的地址
                  content: data.content ? data.content : '', // 正文内容 （保存 文章 图册 短信的正文内容 时候用的字段）
                  imageAdd: data.image_add ? data.image_add : '', // 封面
                  video: data.videoImgs ? data.videoImgs : [], // 音频视频的正文内容
                  atlasList: data.atlasList ? data.atlasList : [] // 图册的内容，用于回显（因为图册保存的是html 字符串）
                }
                if (this.mydynamic1.leixin == '文章') {
                  this.mydynamic1.articleContent = data.content
                } else {
                  this.mydynamic1.content = ''
                }
                if (this.mydynamic1.leixin == '短信') {
                  this.mydynamic1.smsContent = data.content
                }
                if (data.book_info && data.book_info.length) {
                  this.mydynamic2 = {
                    book_author: data.editer ? data.editer : '', //图书作者
                    book_edition: data.book_info[0].edition ? data.book_info[0].edition : '', // 版次
                    book_publish: data.book_info[0].publish ? data.book_info[0].publish : '', // 出版发行
                    book_sheet: data.book_info[0].sheet ? data.book_info[0].sheet : '', // 印张
                    book_distribution: data.book_info[0].distribution ? data.book_info[0].distribution : '', // 经销
                    book_broadsheet: data.book_info[0].broadsheet ? data.book_info[0].broadsheet : '', // 开版
                    book_print_time: data.book_info[0].print_time ? this.moment(data.book_info[0].print_time).format() : new Date(), // 印刷时间
                    book_word_count: data.book_info[0].word_count ? data.book_info[0].word_count : '', // 字数
                    book_pub_date: data.book_info[0].pub_date ? this.moment(data.book_info[0].pub_date).format() : new Date(), // 出版时间
                    book_paper: data.book_info[0].paper ? data.book_info[0].paper : '', // 纸张
                    book_labels: data.labels ? data.labels : '', // 图书标签 输入框的绑定字段
                    book_label: data.book_info[0].label ? data.book_info[0].label : [], // 图书标签，点击添加后的字段，多个字段 ，数组
                    book_cover_photo: data.book_info[0].cover_photo ? data.book_info[0].cover_photo : '', // 裁切base64地址 book_cover_photo
                    book_full_photo: data.book_info[0].full_photo ? data.book_info[0].full_photo : '', // 图片封面地址 book_book_book_full_photo
                    book_data: data.book_info[0].book_data ? data.book_info[0].book_data : [], // 图书数据
                    isBook: 1, // 是否是图书
                  }
                } else {
                  this.mydynamic2 = {
                    book_author: data.editer ? data.editer : '', //图书作者
                    book_edition: "", // 版次
                    book_publish: "", // 出版发行
                    book_sheet: "", // 印张
                    book_distribution: "", // 经销
                    book_broadsheet: "", // 开版
                    book_print_time: new Date(), // 印刷时间
                    book_word_count: "", // 字数
                    book_pub_date: new Date(), // 出版时间
                    book_paper: "", // 纸张
                    book_labels: "", // 图书标签 输入框的绑定字段
                    book_label: [], // 图书标签，点击添加后的字段，多个字段 ，数组
                    book_cover_photo: "", // 裁切base64地址 book_cover_photo
                    book_full_photo: "", // 图片封面地址 book_book_book_full_photo
                    book_data: [], // 图书数据
                    isBook: 1,
                  }
                }
                // 更改数据类型
                if (this.mydynamic2.book_data && this.mydynamic2.book_data.length) {
                  this.mydynamic2.book_data.forEach((e, index) => {
                    e.checked == 'false' ? e.checked = false : e.checked = true
                    e.edit == 'false' ? e.edit = false : e.edit = true
                    e.expand == 'false' ? e.expand = false : e.expand = true
                    e.children.forEach((element,i) => {
                      element.checked == 'false' ? element.checked = false : element.checked = true
                      element.edit == 'false' ? element.edit = false : element.edit = true
                      element.expand == 'false' ? element.expand = false : element.expand = true
                      element.file ? element.file = [{name: element.file_name, src: element.file}] : element.file = []
                    })
                  })
                }
                this.mydynamic3 = {
                  speciesId: data.speciesId ? data.speciesId : '', // 物种 id
                  species: data.species ? data.species : '', // 物种名字
                  goodsId: data.goodsId ? data.goodsId : '', // 通用商品 id
                  goodsname: data.goodsname ? data.goodsname : '', // 通用商品 名字
                  servicename: data.servicename ? data.servicename : '', // 服务  名字
                  serviceId: data.serviceId ? data.serviceId : '', // 服务 id
                  industryId: data.industryId ? data.industryId : '', // 行业分类 id
                  industryName: data.industryName ? data.industryName : '', // 行业分类  名字
                  district: data.district ? data.district : '', //地址
                }
                this.mydynamic4 = { // 0 是 1 是否
                  sms: data.sms > 0 ? '是' : '否',
                  xianshi: data.xianshi > 0 ? '是' : '否',
                  security: data.security ? data.security : 0,
                  money: data.money ? data.money : '免费',
                  tixing: data.tixing ? data.tixing : '',
                  con: data.con ? data.con : '',
                }
                this.currentStep = 1
                this.isShow = true
              }
            }
          })
        }else { // 之前没有发布过 直接 数据重置 发布
          this.mydynamic1 = {
            custom: '全部',
            leixin: '文章',
            title: '',
            type: '',
            category: '',
            source: '原创',
            author: '',
            summary: '',
            ycauthor: '',
            network: '',
            content: '',
            imageAdd: '',
            video: [],
            typeList: [],
            atlasList: []
          }
          this.mydynamic2 = {
            book_author: "", //图书作者
            book_edition: "", // 版次
            book_publish: "", // 出版发行
            book_sheet: "", // 印张
            book_distribution: "", // 经销
            book_broadsheet: "", // 开版
            book_print_time: new Date(), // 印刷时间
            book_word_count: "", // 字数
            book_pub_date: new Date(), // 出版时间
            book_paper: "", // 纸张
            book_labels: "", // 图书标签 输入框的绑定字段
            book_label: [], // 图书标签，点击添加后的字段，多个字段 ，数组
            book_cover_photo: "", // 裁切base64地址 book_cover_photo
            book_full_photo: "", // 图片封面地址 book_book_book_full_photo
            book_data: [], // 图书数据
            isBook: 1,
          }
          this.mydynamic3 = {
            speciesId: '',
            species: '',
            goodsname: '',
            goodsId: '',
            serviceId: '',
            servicename: '',
            industryId: '',
            industryName: '',
            district: '',
          }
          this.mydynamic4 = {
            sms: '否',
            xianshi: '是',
            security: 0,
            money: '免费',
            tixing: '',
            con: '',
          }
          this.currentStep = 1
          this.isShow = true
        }
      })
    },
    // 上一步
    Prev () {
      this.currentStep --
    },
    // 组件中 触发下一步 之后返回的
    onNext (e) {
      if (e) {
        if (this.mydynamic1.leixin == '图书') {
          if (this.currentStep == 1) {
            this.currentStep ++
            this.mydynamic1 = this.$refs['step1'].mydynamic
          } else if (this.currentStep == 2) {
            this.currentStep ++
            this.mydynamic2 = this.$refs['step2'].mydynamic
          } else if (this.currentStep == 3) {
            this.currentStep ++
            this.mydynamic3 = this.$refs['step3'].mydynamic
          } else if (this.currentStep == 4) {
            // this.currentStep ++ 调用发布的接口
            this.mydynamic4 = this.$refs['step4'].mydynamic
            let list = Object.assign({}, this.mydynamic1)
            list = Object.assign(list, this.mydynamic2)
            list = Object.assign(list, this.mydynamic3)
            list = Object.assign(list, this.mydynamic4)
            list.account = this.$user.loginAccount
            console.log(list)
            list.book_print_time = this.moment(list.book_print_time).format("YYYY-MM-DD HH:mm:ss"),
            list.book_pub_date = this.moment(list.book_print_time).format("YYYY-MM-DD HH:mm:ss"),
            this.$api.post('/member/memberIntroduce/publish', list).then(response => {
              if (response.code == 200) {
                this.$Message.success('发布成功')
                this.$emit('on-init')
                this.isShow = false
              } else {
                this.$Message.success('发布失败')
              }
            })
          }
        } else {
          if (this.currentStep == 1) {
            this.currentStep ++
            this.mydynamic1 = this.$refs['step1'].mydynamic
            console.log(this.mydynamic1)
          } else if (this.currentStep == 2) {
            this.currentStep ++
            this.mydynamic3 = this.$refs['step2'].mydynamic
          } else if (this.currentStep == 3) {
            // this.currentStep ++ 调用发布的接口
            this.mydynamic4 = this.$refs['step3'].mydynamic
            let list = Object.assign({}, this.mydynamic1)
            list = Object.assign(list, this.mydynamic3)
            list = Object.assign(list, this.mydynamic4)
            if (list.leixin == '短信') {
              list.content = list.smsContent
            } else if (list.leixin == '文章') {
              list.content = list.articleContent
            }
            list.account = this.$user.loginAccount
            console.log(list)
            this.$api.post('/member/memberIntroduce/publish', list).then(response => {
              if (response.code == 200) {
                this.$Message.success('发布成功')
                this.$emit('on-init')
                this.isShow = false
              } else {
                this.$Message.success('发布失败')
              }
            })
          }
        }
      }
    },
    // 点击 下一步
    next () {
      this.$refs[`step${this.currentStep}`].next()
    }
  },
}
</script>