<template>
  <div>
    <div class="standard_top">
      <Select
        size="large"
        v-model="model1"
        style="width:200px"
        class="mb40"
        placeholder="会员类型"
        @on-change="getType"
        v-if="listShow"
      >
        <Option v-for="item in memberType" :value="item.value" :key="item.value">{{ item.label }}</Option>
      </Select>
      <!-- <search :option="filterOpt" @titleSearch="search" @searchInMa="search" :count="total"/> -->
      <search
        :option="filterOpt"
        ref="searchBar"
        :typeNumber="typeNumber"
        :count="total"
        @titleSearch="search"
        @searchInMa="search"
        @clickShow="clickShow"
      />
    </div>
    <Row type="flex" :gutter="40">
      <Col span="6" v-for="(item,index) in memberList" :key="index">
        <div class="get-data mt20">
          <img :src="item.logoUrl" style="width:100%">
          <p>{{item.memberName}}</p>
          <Button @click="toLink(index)">更多信息</Button>
          <!-- <a :href="item.url">更多信息</a> -->
        </div>
      </Col>
    </Row>
    <div class="fenye tc pt30 pb50">
      <Page :total="total" :current="currentPage" @on-change="nextPage"></Page>
    </div>
  </div>
</template>
<script>
import search from "./components/head";
import divider from "~components/divider";
import { setTimeout } from "timers";
export default {
  components: {
    search,
    divider
  },
  data() {
    return {
      model1: "",
      memberType: [
        { value: 1, label: "个人" },
        { value: 2, label: "专家" },
        { value: 3, label: "机关法人" },
        { value: 4, label: "农业局" },
        { value: 5, label: "农委" },
        { value: 6, label: "事业法人" },
        { value: 7, label: "农业科学研究院" },
        { value: 8, label: "农村经济技术研究所" },
        { value: 9, label: "企业法人" },
        { value: 10, label: "农业龙头企业" },
        { value: 11, label: "农业合作社" },
        { value: 12, label: "居民户" },
        { value: 13, label: "乡村居民" },
        { value: 14, label: "机关居民" }
      ],
      typeNumber: 1,
      isShow: false,
      standardList: [],
      memberList: [],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      pageNum: 1,
      listShow: false,
      filterOpt: {
        other: false,
        govLevel: false,
        expertType: false,
        adeptField: false,
        unit: false,
        wordSize: false,
        type: false,
        corpType: false,
        ics: false,
        ccs: false,
        standardTrait: false,
        standardStatus: false,
        publishDate: false,
        key: ""
        // articleType: "standard"
      },
      datas: {
        district: "", 
        species: "",
        industry: "",
        goodname: "",
        servicename: "",
        expertType: "",
        adeptField: "",
        keyword: ""
      }
    };
  },
  created() {
    if (
      this.$route.query.title !== undefined &&
      this.$route.query.title !== ""
    ) {
      this.datas.keywrod = this.$route.query.title;
      this.filterOpt.key = this.$route.query.title;
      this.search(this.datas);
    }
    this.init(1);
  },
  methods: {
    getType(val) {
      if (val === 2) {
        // this.typeNumber = val;
        this.filterOpt.adeptField = true;
        this.filterOpt.expertType = true;
        // console.log("ddd", this.$refs.searchBar.clickShow);
        this.$refs.searchBar.clickShow = false;
        setTimeout(() => {
          this.$refs.searchBar.clickShow = true;
        }, 10);
        // this.$refs.searchBar.clickShow = true;
      } else {
        // this.typeNumber = val;
        this.filterOpt.adeptField = false;
        this.filterOpt.expertType = false;
        // console.log("ddd", this.$refs.searchBar.clickShow);
        this.$refs.searchBar.clickShow = false;
        setTimeout(() => {
          this.$refs.searchBar.clickShow = true;
        }, 10);
      }
    },
    clickShow(clickShow) {
      this.listShow = clickShow;
    },
    init(page) {
      this.$api
        .post("/member/member/find/" + page, this.datas)
        .then(response => {
          console.log("res11", response);
          if (response.code === 200) {
            this.isShow = true;
            this.memberList = response.data.list;
            this.total = response.data.total;
          }
        });
    },
    search(obj) {
      this.currentPage = 1;
      let s = "";
      if (obj.regionDatas !== "" && obj.regionDatas !== undefined) {
        s = obj.regionDatas.join("/");
      }
      let s2 = "";
      if (obj.speciesDatas !== [] && obj.speciesDatas !== undefined) {
        s2 = obj.speciesDatas.join(" ");
      }
      let s3 = "";
      if (obj.industryDatas !== [] && obj.industryDatas !== undefined) {
        s3 = obj.industryDatas.join(" ");
      }
      let s4 = "";
      if (obj.productDatas !== [] && obj.productDatas !== undefined) {
        s4 = obj.productDatas.join(" ");
      }
      let s5 = "";
      if (obj.serviceDatas !== [] && obj.serviceDatas !== undefined) {
        s5 = obj.serviceDatas.join(" ");
      }
      let data = {
        keyword: obj.keywrod,
        district: s,
        species: s2,
        industry: s3,
        goodname: s4,
        servicename: s5,
        expertType: '',
        adeptField: '',
      };
      this.datas = data;
      console.log("data", data);
      this.init(1);
    },
    nextPage(page) {
      this.datas.pageNum = page;
      this.currentPage = page;
      // console.log("datas", this.datas);
      this.init(page);
    },
    goToDetail(id) {
      this.$router.push({
        path: "/inforMation/standardDetail",
        query: {
          id: id,
          status: 1
        }
      });
    },
    toLink(index) {
      // portals/index?uid=chqm
       this.$router.push({
        path: "/portals/index?uid="+this.memberList[index].loginAccount,
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.standard_top {
  margin: 60px 40px 40px;
}
.ivu-tag-border-custom {
  height: 24px;
  line-height: 24px;
  /* border: 1px solid #e9eaec!important; */
  color: #657180 !important;
  background: #fff !important;
  position: relative;
}

.standard-list {
  padding: 10px;
  border: 1px solid #d8d7d7;
  margin-top: 10px;
}
.get-data {
  border: 1px solid #d8d7d7;
  padding: 12px 10px;
  transition: 0.5s;
  height: 340px;
  text-align: center;
  &:hover {
    cursor: pointer;
    box-shadow: 0px 4px 8px 4px rgba(0, 0, 0, 0.15);
  }
  p {
    text-align: center;

    height: 40px;
    font-size: 14px;
    line-height: 40px;
    border-bottom: 1px solid #d8d7d7;
  }
  img {
    height: 240px;
    border: 1px solid #d8d7d7;
  }
  button {
    margin-top: 6px;
  }
}
</style>
