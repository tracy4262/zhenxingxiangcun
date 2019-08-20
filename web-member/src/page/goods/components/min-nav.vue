<template>
  <ul class="min-nav">
    <li class="item" v-for="item in data" :key="item.id">
      <Row type="flex">
        <Col span="6">
          <i :class="item.icon" class="h5"></i>
        </Col>
        <Col span="16">{{item.label}}</Col>
        <Col span="2">
          <Icon type="ios-arrow-dropright"/>
        </Col>
      </Row>
      <div class="sub-item scroll">
        <div v-for="(son, index) in item.children" class="vui-flex mt10 mb10" :key="index">
          <a v-if="type === '0'" :href="`/goods/search?code=${son.value}&name=${son.label}`" class="sub-name tc">{{son.label}}
            <Icon type="ios-arrow-dropright" />
          </a>
          <a v-if="type === '1'" :href="`/51index/serviceList/all?productCode=${son.value}`" class="sub-name tc">{{son.label}}
            <Icon type="ios-arrow-dropright" />
          </a>
          <div class="pb5 mb5 vui-flex-item tl" style="border-bottom: 1px dotted #ddd;">
            <a  v-if="type === '0'" v-for="(grandson, index) in son.children"
            :key="index"
            :href="`/goods/search?code=${grandson.value}&name=${grandson.label}&parentName=${son.label}&parentCode=${son.value}`"
            class="sub-link">{{grandson.label}}</a>
            <a  v-if="type === '1'" v-for="(grandson, index) in son.children"
            :key="index"
            :href="`/51index/serviceList/all?productCode=${grandson.value}`"
            class="sub-link">{{grandson.label}}</a>
          </div>
        </div>
        <!-- <div v-for="(son, index) in item.children" class="vui-flex mt10 mb10" :key="index">
          <a class="sub-name tc" @click="getFilter(son)">
            {{son.label}}
            <Icon type="ios-arrow-dropright"/>
          </a>
          <div class="pb5 mb5 vui-flex-item tl" style="border-bottom: 1px dotted #ddd;">
            <a
              v-for="(grandson, index) in son.children"
              :key="index"
              class="sub-link"
              @click="getFilter2(son,grandson)"
            >{{grandson.label}}</a>
          </div>
        </div> -->
      </div>
    </li>
  </ul>
</template>

<script>
export default {
  components: {},
  props: {
    type: {
      type: String,
      default: "0"
    }
  },
  data() {
    return {
      data: []
    };
  },
  created() {
    if (this.type === "0") {
      // 商品查询
      this.$api
        .get("/portal/shopCommdoity/findMallClassification")
        .then(res => {
          this.data = res.data;
        });
    } else if (this.type === "1") {
      // 服务查询
      let data = [
        {
          label: "住宿服务",
          icon: "ivu-icon ivu-icon-ios-home",
          children: [
            {
              label: "住宿服务",
              value: "icon-rice",
              children: [
                {
                  label: "民宿",
                  value: ""
                }
              ]
            }
          ]
        },
        {
          label: "娱乐服务",
          icon: "ivu-icon ivu-icon-ios-basket",
          children: [
            {
              label: "娱乐服务",
              value: "icon-rice",
              children: [
                {
                  label: "景区",
                  value: ""
                },
                {
                  label: "采摘",
                  value: ""
                },
                {
                  label: "垂钓",
                  value: ""
                }
              ]
            }
          ]
        },
        {
          label: "农林生产",
          icon: "ivu-icon ivu-icon-ios-leaf",
          children: [
            {
              label: "农林生产",
              value: "icon-rice",
              children: [
                {
                  label: "耕整服务",
                  value: ""
                },
                {
                  label: "植保服务",
                  value: ""
                },
                {
                  label: "移栽服务",
                  value: ""
                },
                {
                  label: "幼苗服务",
                  value: ""
                },
                {
                  label: "收货服务",
                  value: ""
                }
              ]
            }
          ]
        },
        {
          label: "仓储服务",
          icon: "ivu-icon ivu-icon-md-archive"
        },
        {
          label: "加工服务",
          icon: "ivu-icon ivu-icon-md-cube"
        },
        {
          label: "包装服务",
          icon: "ivu-icon ivu-icon-md-mail"
        },
        {
          label: "运输服务",
          icon: "ivu-icon ivu-icon-md-car"
        },
        {
          label: "餐饮服务",
          icon: "ivu-icon ivu-icon-md-pizza",
          children: [
            {
              label: "餐饮服务",
              value: "icon-rice",
              children: [
                {
                  label: "农家乐",
                  value: ""
                }
              ]
            }
          ]
        },
        {
          label: "咨询服务",
          icon: "ivu-icon ivu-icon-md-call",
          children: [
            {
              label: "咨询服务",
              value: "icon-rice",
              children: [
                {
                  label: "咨询服务",
                  value: ""
                }
              ]
            }
          ]
        }
      ];
      this.data = data;
      // this.$api.post('/member/applicationCentrality/findServiceTypeList', {}).then(res => {
      //   this.data = res.data
      // })
    }
  },
  methods: {
    getFilter(item) {
      this.$router.push({
        path: "/goods/showGoods",
        query: { name: item.label, code: item.value }
      });
    },

    getFilter2(son,grandson) {
      this.$router.push({
        path: "/goods/showGoods",
        query: { name: grandson.label, code: grandson.value, parentName:son.label, parentCode:son.value }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.min-nav {
  position: absolute;
  top: 0;
  left: 0;
  padding: 15px 0;
  background: rgba(255, 255, 255, 0.87);
  z-index: 9;
  height: 385px;
  .sub-item {
    left: 135px;
    width: 600px;
    height: 385px;
  }
  .item {
    list-style: none;
    font-size: 14px;
    color: #4a4a4a;
    padding: 9px 15px;
    width: 135px;
    &.on,
    &:hover {
      color: #fff;
      background: #00c587;
    }
    &:hover {
      .sub-item {
        display: block;
      }
    }
    .sub-item {
      display: none;
      position: absolute;
      top: 0;
      background: #fff;
      padding: 15px;
      overflow: auto;
    }
    .sub-name,
    .sub-link {
      display: inline-block;
      &:hover {
        color: #00c587;
      }
    }

    .sub-name {
      display: block;
      width: 124px;
      color: #646464;
    }
    .sub-link {
      padding: 0 10px;
      border: 1px solid #e5e5e5;
      border-top: 0;
      border-bottom: 0;
      color: #8d8d8d;
      margin: 0 -1px 10px 0;
    }
  }
  .shop-logo {
    margin: -1px;
    display: block;
    height: 84px;
    border: 1px solid #ddd;
    img {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
