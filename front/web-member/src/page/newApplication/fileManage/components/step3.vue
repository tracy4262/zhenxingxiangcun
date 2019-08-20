<template>
  <Form
    :label-width="80"
    label-position="left"
    ref="mydynamic"
    :rules="ruleValidate"
    :model="mydynamic"
  >
    <Row>
      <Col span="22">
        <FormItem label="关联物种" prop="species">
          <Input v-model="mydynamic.species" readonly @on-focus="bookparent('speciFilter')"/>
        </FormItem>
      </Col>
    </Row>
    <Row>
      <Col span="22">
        <FormItem label="通用商品名" prop="products">
          <Input v-model="mydynamic.products" readonly @on-focus="commodityName"/>
        </FormItem>
      </Col>
    </Row>
    <Row>
      <Col span="22">
        <FormItem label="通用服务名" prop="service">
          <Input readonly v-model="mydynamic.service" @on-focus="bookparent('serviceFilter')"/>
        </FormItem>
      </Col>
    </Row>
    <Row>
      <Col span="22">
        <FormItem label="行业分类" prop="industryName">
          <Input v-model="mydynamic.industryName" readonly @on-focus="bookparent('tradeFilter')"/>
        </FormItem>
      </Col>
    </Row>

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
    <vuiCommodity ref="vuiCommodity" @on-save="onSaveFocus" :input="false" :num="1"/>
  </Form>
</template>
<script>
import vuiFilter from "~components/vuiFilter/filter";
import vuiCommodity from "~components/vui-commodity";
export default {
  props: ["viewId", "bookDetail"],
  components: {
    vuiFilter,
    vuiCommodity
  },
  data() {
    return {
      mydynamic: {
        species: null, //关联物种
        speciesId: null, //物种id,
        products: null, //通用商品名
        productsId: null,
        service: null, //通用服务名
        industryId: null,
        industryName: null, //行业分类
        serviceId: null
      },
      total: 0,
      total2: 0,
      pageCur: 1,
      pageCur2: 1,
      tradeResultDatas: [],
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
      speciesResult: "", //最终物种
      cityList: [],
      ruleValidate: {
        species: [
          {
            required: true,
            type: "string",
            message: "请选择至少一个关联物种！",
            trigger: "change"
          }
        ],
        products: [
          {
            required: true,
            type: "string",
            message: "请选择通用商品名！",
            trigger: "change"
          }
        ],
        service: [
          {
            required: true,
            type: "string",
            message: "请选择通用服务名！",
            trigger: "change"
          }
        ],
        industryName: [
          {
            required: true,
            type: "string",
            message: "请选择行业分类！",
            trigger: "change"
          }
        ]
      }
    };
  },
  created() {
    // //获得名字
    // this.$api
    //   .post("/member/login/findCurrentUser", {
    //     account: this.$user.loginAccount
    //   })
    //   .then(res => {
    //     if (res.data.displayName) {
    //       this.author = res.data.displayName;
    //     }
    //   });
    // // 取地址
    // api.post("/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816").then(res => {
    //   this.cityList = res.data;
    // });
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
    this.$api.post("/member/service/find", {}).then(res => {
      var data = res.data;
      data.forEach((item, index) => {
        item.checked = false;
      });
      this.serviceResultDatas = data;
    });
  },
  methods: {
    bookparent(name) {
      this.handleFilterModal(name);
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
      this.mydynamic.industryId = arrId.join(" ");
      this.mydynamic.industryName = arr.join(" ");
    },
    loadData(item, callback) {
      item.loading = true;
      this.$api.post(`/member/town/next/${item.value}`).then(res => {
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
      this.$api
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
      this.$api
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
      this.mydynamic.speciesIds = ids.join(" ");
    },
    // 相关服务
    handleServiceSearch(letter, keyword, classify) {
      this.$api
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
      // this.service = service.join(" ");
      this.mydynamic.service = service.join(" ");
      this.mydynamic.serviceId = arr.join(" ");
    },
    // 通用服务名
    getService(result) {
      var arr = [];
      result.forEach(item => arr.push(item.value));
      var service = [];
      result.forEach(item => service.push(item.label));
      // this.service = service.join(" ");
      this.mydynamic.service = service.join(" ");
      this.mydynamic.serviceId = arr.join(" ");
    },
    // 通用商品名
    getProducts(result) {
      var arr = [];
      result.forEach(item => arr.push(item.value));
      var products = [];
      result.forEach(item => products.push(item.label));
      this.mydynamic.products = products.join(" ");
      this.mydynamic.productsId = arr.join(" ");
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

    onSaveFocus(e) {
      if (e.length > 0) {
        this.mydynamic.productsId = e[0].commodityId;
        this.mydynamic.products = e[0].commodityName;
      } else {
        this.mydynamic.productsId = "";
        this.mydynamic.products = "";
      }
    },
    // 通用商品名
    commodityName() {
      this.$refs["vuiCommodity"].handleFilterModal();
    }
  },
  mounted() {
    if (this.viewId || this.viewId === 0) {
      this.mydynamic.species = this.bookDetail[this.viewId].species;
      this.mydynamic.products = this.bookDetail[this.viewId].products;
      this.mydynamic.industryName = this.bookDetail[this.viewId].industryName;
      this.mydynamic.service = this.bookDetail[this.viewId].service;
    }
  },
  watch: {
    viewId: {
      handler(newVal, val) {
        if (this.bookDetail[newVal] !== undefined) {
          this.mydynamic.species = this.bookDetail[newVal].species;
          this.mydynamic.products = this.bookDetail[newVal].products;
          this.mydynamic.industryName = this.bookDetail[newVal].industryName;
          this.mydynamic.service = this.bookDetail[newVal].service;
        }
      }
    }
  }
};
</script>
