<template>
  <div>
    <Row type='flex' justify='center'>
      <Col span="4" class="hot-classify">
        <div class="vui-new-title" style="background:#F7F7F7;">
          <Row>
            <Col span="12">
              <span class="vui-new-title-left">热门分类</span>
            </Col>
            <Col span="12" class="tr vui-new-title-right">
              <Icon type="ios-search" :size="20" class="mt5"/>
            </Col>
          </Row>
        </div>
        <Card>
          <router-link
            v-for="(i,index) in bannerList"
            :key="index"
            to
            tag="div"
            class="banner-left"
          >
            <Row align="middle" type="flex" justify="center">
              <Col span="8">
                <Icon :type="i.iconName" size="20" color="#CBCCCC"/>
              </Col>
              <Col span="12">
                <p>{{i.name}}</p>
              </Col>
            </Row>
          </router-link>
        </Card>
      </Col>
      <Col span="19" class="hot-classify ml20">
        <div class="vui-new-title" style="background:#F7F7F7;">
          <span class="vui-new-title-left">服务推荐</span>
        </div>
        <router-view></router-view>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      bannerList: [
        { name: "住宿服务", iconName: "ios-home" },
        { name: "娱乐服务", iconName: "ios-basket" },
        { name: "农林生产", iconName: "ios-leaf" },
        { name: "仓储服务", iconName: "md-archive" },
        { name: "加工服务", iconName: "md-cube" },
        { name: "包装服务", iconName: "md-mail" },
        { name: "运输服务", iconName: "md-car" },
        { name: "餐饮服务", iconName: "md-pizza" },
        { name: "咨询服务", iconName: "md-call" }
      ]
    };
  }
};
</script>
<style lang="scss" scoped>
.banner-left {
  margin: 20px 0;
  p {
    font-size: 15px;
    font-weight: bold;
    color: #717171;
  }
  &:hover {
    cursor: pointer;
  }
}
</style>
