<template>
  <ul class="min-nav">
    <li class="item" v-for="(item,index) in serveClassifyData" :key="index">
      <Row type="flex">
        <Col span="6">
          <i :class="item.icon" class="h5"></i>
        </Col>
        <Col span="16">{{item.label}}</Col>
        <Col span="2">
           <Icon type="ios-arrow-dropright" />
        </Col>
      </Row>

      <div class="sub-item scroll">
        <div v-for="(son, index) in item.children" class="vui-flex mt10 mb10" :key="index">
          <a :href="`/goods/index?productCode=${son.value}`" class="sub-name tc">
            {{son.label}}
            <Icon type="ios-arrow-dropright" />
          </a>
          <div class="pb5 mb5 vui-flex-item tl" style="border-bottom: 1px dotted #ddd;">
            <a
              v-for="(grandson, index) in son.children"
              :key="index"
              :href="`/goods/index?productCode=${grandson.value}`"
              class="sub-link"
            >{{grandson.label}}</a>
          </div>
        </div>
      </div>
    </li>
  </ul>
</template>
<script>
export default {
  data() {
    return {
      serveClassifyData: [
        {
          label: "住宿服务",
          icon: "ivu-icon ivu-icon-ios-home",
          children: [
            {
              label: "住宿服务",
              children: [{ label: "民宿", value: "" }],
              value: ""
            }
          ]
        },
        {
          label: "娱乐服务",
          icon: "ivu-icon ivu-icon-ios-basket",
          children: [
            {
              label: "娱乐服务",
              children: [
                { label: "采摘", value: "" },
                { label: "景区", value: "" },
                { label: "垂钓", value: "" },
                ],
              value: ""
            }
          ]
        },
        {
          label: "农林生产",
          icon: "ivu-icon ivu-icon-ios-leaf",
          children: [
            {
              label: "农林生产",
              children: [
                {
                  label: "耕整服务",
                  value: "",
                },
                {
                  label: "植保服务",
                  value: "",
                },
                {
                  label: "移栽服务",
                  value: "",
                },
                {
                  label: "幼苗服务",
                  value: "",
                },
                {
                  label: "收获服务",
                  value: "",
                },
              ],
              value: ""
            },
          ]
        },
        {
          label: "仓储服务",
          icon: "ivu-icon ivu-icon-md-archive",
          children: [
            {
              label: "仓储服务",
              children: [{ label: "冷藏", value: "" }],
              value: ""
            }
          ]
        },
        {
          label: "加工服务",
          icon: "ivu-icon ivu-icon-md-cube",
          children: [
            {
              label: "加工服务",
              children: [{ label: "农产品加工", value: "" }],
              value: ""
            }
          ]
        },
        {
          label: "包装服务",
          icon: "ivu-icon ivu-icon-md-mail",
          children: [
            {
              label: "包装服务",
              children: [{ label: "产品包装", value: "" }],
              value: ""
            }
          ]
        },
        {
          label: "运输服务",
          icon: "ivu-icon ivu-icon-md-car",
          children: [
            {
              label: "运输服务",
              children: [{ label: "冷冻速运", value: "" }],
              value: ""
            }
          ]
        },
        {
          label: "餐饮服务",
          icon: "ivu-icon ivu-icon-md-pizza",
          children: [
            {
              label: "餐饮服务",
              children: [{ label: "农家乐", value: "" }],
              value: ""
            }
          ]
        },
        {
          label: "咨询服务",
          icon: "ivu-icon ivu-icon-md-call",
          children: [
            {
              label: "咨询服务",
              children: [{ label: "服务热线", value: "" }],
              value: ""
            }
          ]
        }
      ]
    };
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
