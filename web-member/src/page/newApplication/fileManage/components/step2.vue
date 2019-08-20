<template>
  <Form
    :label-width="60"
    label-position="left"
    :model="mydynamic"
    ref="mydynamic"
    :rules="ruleValidate"
  >
    <div class="form-bar">
      <div class="left-bar"></div>
      <h4>上传图书封面</h4>
      <p>注：添加图书封面、让你的图书更有吸引力</p>
    </div>
    <vui-cropper
      style="margin-left:50px"
      @on-get-base64="handleGetBase64"
      @on-get-bookPage="handleGetBookPage"
      class="mt20"
    ></vui-cropper>
    <div class="form-bar">
      <div class="left-bar"></div>
      <h4>书本信息</h4>
    </div>
    <Row type="flex" justify="space-around" class="code-row-bg mt30">
      <Col span="10">
        <Form-item label="作者" prop="author">
          <Input v-model="mydynamic.author" placeholder="作者"></Input>
        </Form-item>
        <FormItem label="出版发行" prop="book_publish">
          <Input v-model="mydynamic.book_publish"></Input>
        </FormItem>
        <FormItem label="经销" prop="book_distribution">
          <Input v-model="mydynamic.book_distribution"></Input>
        </FormItem>
        <FormItem label="印刷时间" prop="book_print_time">
          <DatePicker
            style="width:260px"
            type="date"
            placeholder="选择日期"
            size="large"
            v-model="mydynamic.book_print_time"
            @on-change="getPrint"
          ></DatePicker>
        </FormItem>
        <FormItem label="出版时间" prop="book_pub_date">
          <DatePicker
            style="width:260px"
            type="date"
            size="large"
            placeholder="选择日期"
            v-model="mydynamic.book_pub_date"
            @on-change="getPublish"
          ></DatePicker>
        </FormItem>
        <FormItem label="图书标签" prop="book_label">
          <Row type="flex">
            <Col>
              <Input v-model="bookTags" placeholder="填写标签，标签不能超过3个"></Input>
            </Col>
            <Col>
              <Button type="primary" @click="addTags">增加</Button>
            </Col>
            <Col v-for="(tag,index) in mydynamic.book_label" :key="index">
              <Tag v-if="show" closable @on-close="handleClose(index)">{{tag}}</Tag>
            </Col>
          </Row>
        </FormItem>
      </Col>
      <Col span="8">
        <FormItem label="版次" prop="book_edition">
          <Input v-model="mydynamic.book_edition"></Input>
        </FormItem>
        <FormItem label="印张" prop="book_sheet">
          <Input v-model="mydynamic.book_sheet"></Input>
        </FormItem>
        <FormItem label="开版" prop="book_broadsheet">
          <Input v-model="mydynamic.book_broadsheet"></Input>
        </FormItem>
        <FormItem label="数字" prop="book_word_count">
          <Input v-model="mydynamic.book_word_count"></Input>
        </FormItem>
        <FormItem label="纸张" prop="book_paper">
          <Select size="large" v-model="mydynamic.book_paper">
            <Option v-for="(item,index) in paperModel" :key="index" :value="item">{{item}}</Option>
          </Select>
        </FormItem>
      </Col>
    </Row>

    <div class="form-bar">
      <div class="left-bar"></div>
      <h4>添加章节</h4>
    </div>
    <vui-book
      ref="bookTree"
      @on-get-book="handleGetBook"
      :bookList="mydynamic.book_data"
      :viewId="viewId"
      class="mt20"
    ></vui-book>
  </Form>
</template>
<script>
import vuiBook from "~components/vuiBookList";
import vuiCropper from "~components/vuiCropper";
import axios from "axios";
export default {
  props: {
    source: {
      type: String
    },
    viewId: {
      type: Number
    },
    bookDetail: {
      type: Array,
      default() {
        return [];
      }
    },
    author:{
      type:String,
      default:''
    },
    current:{
      type:Number,
      default:0
    }
  },
  components: {
    vuiBook,
    vuiCropper
  },
  data() {
    return {
      show: true,
      bookTags: "",
      paperModel: ["铜板纸", "胶板纸"],
      full_photo: "",
      mydynamic: {
        author: '',
        book_publish: null,
        book_distribution: null,
        book_print_time: null,
        book_pub_date: null,
        book_label: [],
        book_edition: null,
        book_sheet: null,
        book_broadsheet: null,
        book_word_count: null,
        book_paper: null,
        book_data: [],
        book_cover_photo: null
      },
      ruleValidate: {
        author: [
          {
            required: true,
            type: "string",
            message: "请输入作者姓名",
            trigger: "blur"
          }
        ],
        book_publish: [
          {
            required: true,
            message: "请输入出版发行商！",
            trigger: "blur"
          }
        ],
        book_distribution: [
          {
            required: true,
            message: "请输入经销商！",
            trigger: "blur"
          }
        ],
        book_print_time: [
          {
            required: true,
            type: "string",
            message: "请输入印刷时间！",
            trigger: "change"
          }
        ],
        book_pub_date: [
          {
            required: true,
            type: "string",
            message: "请输入出版日期！",
            trigger: "change"
          }
        ],
        book_label: [
          {
            required: true,
            type: "array",
            min: 1,
            max: 3,
            message: "请输入至少一个图书标签！",
            trigger: "change"
          }
        ],
        book_edition: [
          {
            required: true,
            type: "string",
            message: "请输入正确的版次！",
            trigger: "blur"
          }
        ],
        book_sheet: [
          {
            required: true,
            type: "string",
            message: "请输入正确的印张！",
            trigger: "blur"
          }
        ],
        book_word_count: [
          {
            required: true,
            type: "string",
            message: "请输入正确的文字数！",
            trigger: "blur"
          }
        ],
        book_broadsheet: [
          {
            required: true,
            type: "string",
            message: "请输入正确的开版信息！",
            trigger: "blur"
          }
        ],
        book_paper: [
          {
            required: true,
            type: "string",
            message: "请选择一种纸张！",
            trigger: "change"
          }
        ]
      }
    };
  },
  watch: {
    viewId: {
      handler(newVal, val) {
        if (this.bookDetail[newVal] !== undefined) {
          this.mydynamic.author = this.bookDetail[newVal].author;
          this.mydynamic.book_publish = this.bookDetail[newVal].publish;
          this.mydynamic.book_distribution = this.bookDetail[
            newVal
          ].distribution;
          this.mydynamic.book_print_time = this.bookDetail[newVal].print_time;
          this.mydynamic.book_pub_date = this.bookDetail[newVal].pub_date;
          this.mydynamic.book_label = this.bookDetail[newVal].label;
          this.mydynamic.book_edition = this.bookDetail[newVal].edition;
          this.mydynamic.book_sheet = this.bookDetail[newVal].sheet;
          this.mydynamic.book_broadsheet = this.bookDetail[newVal].broadsheet;
          this.mydynamic.book_word_count = this.bookDetail[newVal].word_count;
          this.mydynamic.book_paper = this.bookDetail[newVal].paper;
          this.mydynamic.book_data = this.bookDetail[newVal].book_data;
          this.bookDetail[newVal].book_data.forEach(element => {
            if (element.file === '') {
              element.file = []
            }
          });
          this.mydynamic.book_cover_photo = this.bookDetail[newVal].cover_photo;
        }
      },
      deep:true
    },
    current:{
      handler(newVal,val) {
        if(newVal === 1&&this.source === '原创') {
          console.log('newVal',newVal)
          this.mydynamic.author = this.author
        }
      }
    }
  },
  created() {
    if (this.viewId || this.viewId === 0) {
      this.mydynamic.author = this.bookDetail[this.viewId].author;
      this.mydynamic.book_publish = this.bookDetail[this.viewId].publish;
      this.mydynamic.book_distribution = this.bookDetail[
        this.viewId
      ].distribution;
      this.mydynamic.book_print_time = this.bookDetail[this.viewId].print_time;
      this.mydynamic.book_pub_date = this.bookDetail[this.viewId].pub_date;
      this.mydynamic.book_label = this.bookDetail[this.viewId].label;
      this.mydynamic.book_edition = this.bookDetail[this.viewId].edition;
      this.mydynamic.book_sheet = this.bookDetail[this.viewId].sheet;
      this.mydynamic.book_broadsheet = this.bookDetail[this.viewId].broadsheet;
      this.mydynamic.book_word_count = this.bookDetail[this.viewId].word_count;
      this.mydynamic.book_paper = this.bookDetail[this.viewId].paper;
      this.mydynamic.book_data = this.bookDetail[this.viewId].book_data;
      console.log("ccc", this.mydynamic.book_data);
      this.mydynamic.book_cover_photo = this.bookDetail[
        this.viewId
      ].cover_photo;
    }
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
            console.log("预览图片--->", this.full_photo);
          } else {
            this.$Message.error(response.data.msg);
          }
        });
    },
    // 取裁切base64地址
    handleGetBase64(data) {
      // this.$emit("on-get-base64", data);
      // this.mydynamic.bookCover = data.img;
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
              this.mydynamic.book_cover_photo = response.data.data.src;
              console.log("裁剪图片--->", this.mydynamic.book_cover_photo);
            } else {
              this.$Message.error(response.data.msg);
            }
          });
      });
    },
    handleClose(index) {
      this.mydynamic.book_label.splice(index, 1);
    },
    handleGetBook(data) {
      this.mydynamic.book_data = data;
    },
    addTags() {
      if (this.mydynamic.book_label.length === 3) {
        this.$Message.success("不能添加超过3个的标签");
      } else {
        this.mydynamic.book_label.push(this.bookTags);
        this.bookTags = "";
      }
    },
    handleSubmit(name) {
      let flag = false;
      this.$refs[name].validate(valid => {
        if (valid) {
          flag = true;
        } else {
          flag = false;
        }
      });
      return flag;
    },
    handleReset(name) {
      this.$refs[name].resetFields();
    },
    getPrint(val, date) {
      this.mydynamic.book_print_time = val;
    },
    getPublish(val, date) {
      this.mydynamic.book_pub_date = val;
    }
  }
};
</script>
<style scoped lang='scss'>
.form-bar {
  background: rgba(216, 216, 216, 0.27);
  display: flex;
  justify-content: flex-start;
  align-items: center;
  height: 30px;
  margin-top: 25px;
  h4 {
    font-family: PingFangSC-Medium;
    color: #4a4a4a;
    font-weight: bold;
    margin-right: 20px;
  }
  p {
    font-family: PingFangSC-Regular;
    color: #9b9b9b;
  }
}
.left-bar {
  width: 4px;
  height: 17px;
  background: #56b07d;
  line-height: 30px;
  margin-left: 7px;
  margin-right: 15px;
}
</style>

