<template>
  <div>
       <Form ref="book" :rules="ruleInline" style="margin-top: 27px;" :model="mydynamic"  label-position="right" :label-width="100">
            <vui-cropper @on-get-base64="handleGetBase64" @on-get-bookPage="handleGetBookPage"></vui-cropper>
            <Row>
                <Col span="11">
                <Form-item label="作者" prop="book_author">
                    <Input v-if="source=='转载'" readonly  v-model="mydynamic.book_author"  placeholder="作者">
                    </Input>
                    <Input v-if="source=='原创'"   v-model="mydynamic.book_author"  placeholder="作者">
                    </Input>
                </Form-item>
                </Col>
                <Col span="11" offset="1">
                <Form-item label="版次" prop="book_edition">
                    <Input  v-model="mydynamic.book_edition" placeholder="版次">
                    </Input>
                </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                <Form-item label="出版发行" prop="book_publish">
                    <Input  v-model="mydynamic.book_publish" placeholder="出版发行">
                    </Input>
                </Form-item>
                </Col>
                <Col span="11" offset="1">
                <Form-item label="印张" prop="book_sheet">
                    <Input  v-model="mydynamic.book_sheet" placeholder="印张">
                    </Input>
                </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                <Form-item label="经销" prop="book_distribution">
                    <Input  v-model="mydynamic.book_distribution" placeholder="经销">
                    </Input>
                </Form-item>
                </Col>
                <Col span="11" offset="1">
                <Form-item label="开版" prop="book_broadsheet">
                    <Input  v-model="mydynamic.book_broadsheet" placeholder="开版">
                    </Input>
                </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                <Form-item label="印刷时间" prop="book_print_time">
                    <DatePicker type="date"  v-model="mydynamic.book_print_time" transfer>
                    </DatePicker>
                </Form-item>
                </Col>
                <Col span="11" offset="1">
                <Form-item label="字数" prop="book_word_count">
                    <Input  v-model="mydynamic.book_word_count" placeholder="字数">
                    </Input>
                </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                <Form-item label="出版时间" prop="book_pub_date">
                    <DatePicker type="date"  v-model="mydynamic.book_pub_date" transfer>
                    </DatePicker>
                </Form-item>
                </Col>
                <Col span="11" offset="1">
                <Form-item label="纸张" prop="book_paper">
                    <Select v-model="mydynamic.book_paper" transfer>
                        <Option v-for="(item,index) in zhizhang" :key="index" :value="item.value" >{{ item.label }}</Option>
                    </Select>
                </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="16">
                <Form-item label="图书标签" prop="book_labels">
                    <Input  v-model="mydynamic.book_labels" placeholder="使用逗号或分号分隔，按回车添加，最多支持3个" @keyup.enter.native="addTag(mydynamic.book_labels)">
                    </Input>
                </Form-item>
                </Col>
                <Col span="6" offset="1">
                <Button type="primary"  @click.native="addTag(mydynamic.book_labels)">增加</Button>
                </Col>
            </Row>
            <Row>
                <Col span="16">
                <Form-item prop="bookTags">
                    <Tag v-for="item in mydynamic.book_label" :key="item" :name="item" closable @on-close="tagClose" v-show="item !=''">{{item}}</Tag>
                </Form-item>
                </Col>
            </Row>
            <vui-book ref="bookTree" @on-get-book="handleGetBook" :bookList="mydynamic.book_data"></vui-book>
        </Form>
  </div>
</template>
<script>
import vuiCropper from '~components/vuiCropper'
import vuiBook from '~components/vuiBookList'
import axios from "axios";
export default {
  components: {
    vuiCropper,
    vuiBook
  },
  props: {
    data: {
      type: Object,
      default: () => {
        return {}
      }
    },
    source: {
      type: String,
      default: '原创'
    }
  },
  data () {
    return {
      mydynamic: {
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
        book_cover_photo: "", // 裁切base64地址
        book_full_photo: "", // 图片封面地址
        book_data: [], // 图书数据
      },
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
            // { type: 'string',
            //   max: 300,
            //   message: '摘要不能超过300个字',
            //   trigger: 'blur'
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
      zhizhang:[{
          value: '铜板纸',
          label: '铜板纸'
      }, {
          value: '胶板纸',
          label: '胶板纸'
      }],
      number: 0
    }
  },
  watch: {
    data: {
      handler(curVal, oldVal) {
        this.mydynamic = curVal
      },
      deep: true
    }
  },
  created() {
    this.mydynamic = this.data
  },
  methods: {
    next () {
      this.$refs['book'].validate(valid=> {
          if(valid) {
            this.$emit('on-next', true)
          } else {
            this.$emit('on-next', false)
          }
      })
    },
    // 取图片封面地址
    handleGetBookPage(data){
        // this.$emit('on-get-bookPage', data)
        let fileName = "test.jpg";
        let fileData = data;
        axios.post(`${this.$url.uploadBase64}/imgbase64/upload`, {
            fileName: fileName,
            fileData: fileData
          })
          .then(response => {
            if (response.data.code === "0") {
              this.mydynamic.book_full_photo = response.data.data.src;
            } else {
              this.$Message.error(response.data.msg);
            }
          });
    },
    // 取裁切base64地址
    handleGetBase64 (data) {
      // this.$emit('on-get-base64', data)
       data.getCropData(d => {
        let fileName = "test.jpg";
        let fileData = d;
        axios.post(`${this.$url.uploadBase64}/imgbase64/upload`, {
            fileName: fileName,
            fileData: fileData
          })
          .then(response => {
            if (response.data.code === "0") {
              this.mydynamic.book_cover_photo = response.data.data.src;
            } else {
              this.$Message.error(response.data.msg);
            }
          });
      });
    },
    // 取 图书数据
    handleGetBook (data) {
      this.mydynamic.book_data = data
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
                  this.mydynamic.book_label = tags
                  this.number++
              }else if(this.number<3){
                  let booklet = this.mydynamic.book_label +','+bookname +','
                  booklet =  booklet.substring(0, booklet.length - 1);
                  tags = booklet.split(",");
                  if(tags.length>3){
                      this.$Message.error('添加标签过多，请保留3个标签!')
                  }else{
                      this.mydynamic.book_label = tags;
                      this.number++
                  }
              }else{
                  this.$Message.error('添加标签过多，请保留3个标签!')
              }
          }
          this.mydynamic.book_labels = '';
      }else{
          this.$Message.error('请填写正确的标签!')
      }


    },
    tagClose(event, name){
        this.number--
        this.mydynamic.book_labels='';
        const index = this.mydynamic.book_label.indexOf(name);
        this.mydynamic.book_label.splice(index, 1);
        this.mydynamic.book_label.forEach(item => {
            this.mydynamic.book_labels +=item +','
        });
        this.mydynamic.book_labels = this.mydynamic.book_labels.substring(0, this.mydynamic.book_labels.length - 1)

    },
  },
}
</script>