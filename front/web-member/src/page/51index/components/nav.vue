<template>
  <nav class="mall-nav">
    <div class="layouts" ref="mallNav">
      <template v-for="(item,index) in navList">
        <router-link
          :to="item.url"
          :class="(item.url === $route.path || active === index) ? 'link on' : 'link'"
          :key="index"
        >{{item.text}}</router-link>
      </template>
    </div>
  </nav>
</template>
<script>
export default {
  props: {
    active: {
      type: Number,
      default: -1
    }
  },
  data() {
    return {
      navList: [
        {
          url: "/51index",
          text: "首页"
        },
        {
          url: "/51index/inforMationList",
          text: "资讯"
        },
        {
          url: "/51index/policyList",
          text: "政策"
        },
        {
          url: "/51index/knowledgeList",
          text: "知识"
        },
        {
          url: "/goods/index",
          text: "产品"
        },
        {
          url: "/51index/serviceList/all",
          text: "服务"
        },
        {
          url: "/51index/standardList",
          text: "标准"
        },
        {
          url: "/51index/getMember",
          text: "会员"
        }
        // {
        //     url: '/51index/enterpriseList',
        //     text: '企业'
        // }, {
        //     url: '/51index/departmentList',
        //     text: '机关'
        // }, {
        //     url: '/51index/expertList',
        //     text: '专家'
        // }
      ]
    };
  },
  created() {},
  updated() {},
  methods: {}
};
</script>
<style lang="scss" scoped>
.mall-nav {
  > .layouts {
    position: relative;
  }
  background: #00c587;
  min-width: 1200px;
  .link {
    display: inline-block;
    vertical-align: middle;
    padding: 10px 20px;
    font-size: 16px;
    color: #fff;
    &:hover,
    &.on {
      background: #00af78;
    }
  }
}
</style>
