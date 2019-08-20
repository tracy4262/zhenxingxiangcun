<template>
  <Row type="flex" justify="center" class="code-row-bg mt30">
    <Col span="20">
      <Form
        ref="mydynamic"
        label-position="right"
        :model="mydynamic"
        :label-width="60"
        :rules="ruleValidate"
      >
        <FormItem label="上传到" class="mt30" prop="toWhere">
          <Select size="large" v-model=" mydynamic.toWhere">
            <Option
              v-for="(item,index) in books"
              :key="index"
              :value="item.mediaId"
            >{{item.mediaName}}</Option>
          </Select>
        </FormItem>
        <FormItem label="标题" prop="title">
          <Input v-model="mydynamic.title"></Input>
        </FormItem>
        <FormItem label="信息来源" prop="source">
          <RadioGroup v-model="mydynamic.source" type="button">
            <Radio label="原创">原创</Radio>
            <Radio label="转载">转载</Radio>
          </RadioGroup>
        </FormItem>

        <FormItem label="摘要" prop="summary">
          <Input v-model="mydynamic.summary" type="textarea"></Input>
        </FormItem>
        <FormItem label="适用区域" prop="district">
          <!-- <Cascader
            :data="cityList"
            :render-format="format"
            v-model="mydynamic.district"
            :load-data="loadData"
            v-show="selectedLocation"
            change-on-select
          ></Cascader>-->
          <vuiCascader
            :values="mydynamic.district"
            @handle-get-result="handleGetData"
          ></vuiCascader>
        </FormItem>
      </Form>
    </Col>
  </Row>
</template>
<script>
import vuiCascader from "~components/vuiCascader";
export default {
  components: {
    vuiCascader
  },
  props: {
    books: {
      type: Array
    },
    viewId: {
      type: Number
    },
    bookDetail: {
      type: Array
    }
  },
  data() {
    return {
      cityList: [],
      mydynamic: {
        toWhere: null,
        title: null,
        source: "原创",
        district: null,
        summary: null
      },
      ruleValidate: {
        toWhere: [
          {
            required: true,
            type: "number",
            message: "必须选择一个上传的文件夹！",
            trigger: "change"
          }
        ],
        title: [
          {
            required: true,
            message: "标题不能为空！",
            trigger: "blur"
          }
        ],
        district: [
          {
            required: true,
            type: "string",
            message: "请选择一个地区！",
            trigger: "change"
          }
        ]
      }
    };
  },
  created() {
    // 取地址
    this.$api
      .post("/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816")
      .then(res => {
        this.cityList = res.data;
      });
    if (this.viewId || this.viewId === 0) {
      this.mydynamic.toWhere = this.bookDetail[this.viewId].media_id;
      this.mydynamic.title = this.bookDetail[this.viewId].title;
      this.mydynamic.source = this.bookDetail[this.viewId].source;
      this.mydynamic.district = this.bookDetail[this.viewId].district;
      this.mydynamic.summary = this.bookDetail[this.viewId].summary;
    }
  },
  watch: {
    viewId: {
      handler(newVal, val) {
        if (this.bookDetail[newVal] !== undefined) {
          this.mydynamic.toWhere = this.bookDetail[newVal].media_id;
          this.mydynamic.title = this.bookDetail[newVal].title;
          this.mydynamic.source = this.bookDetail[newVal].source;
          this.mydynamic.district = this.bookDetail[newVal].district;
          this.mydynamic.summary = this.bookDetail[newVal].summary;
        }
      }
    },
    district:{
      handler(curVal,oldVal) {
        if(oldVal != '') {
          newVal.join('/')
        }
      }
    }
  },
  methods: {
    handleReset(name) {
      this.$refs[name].resetFields();
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
    format(labels, selectedData) {
      if (labels.length < 1) {
        return "";
      } else {
        this.mydynamic.district = labels;
        return labels.join("/");
      }
    },
    loadData(item, callback) {
      item.loading = true;
      this.$api.post(`/member/town/next/${item.value}`).then(res => {
        item.loading = false;
        item.children = res.data;
        callback();
      });
    },
    // 地区控件选择后的返回值
    handleGetData(value, selectedData) {
      let labelArr = [];
      selectedData.forEach(element => {
        labelArr.push(element.label);
      });
      this.mydynamic.district = labelArr.join("/");
    }
  }
};
</script>
