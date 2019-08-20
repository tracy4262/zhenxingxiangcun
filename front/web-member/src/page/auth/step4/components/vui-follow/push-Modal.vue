<template>
  <div>
    <vui-title :title="`关注的知识类型（${follow.knowledge.length}）`" class="mt20"></vui-title>
    <releva-item title="知识" class="mt10" :data="follow.knowledge"></releva-item>
    <vui-title :title="`关注的资讯类型（${follow.info.length}）`" class="mt20"></vui-title>
    <releva-item title="资讯" class="mt10" :data="follow.info"></releva-item>
    <vui-title :title="`关注的政策类型（${follow.policy.length}）`" class="mt20"></vui-title>
    <releva-item title="政策" class="mt10" :data="follow.policy"></releva-item>

    <vui-title class="mt20" :title="`关键词（${species.length + product.length + service.length}）`"></vui-title>
    <releva-item title="物种" class="mt10" :data="species"></releva-item>
    <releva-item title="产品" class="mt10" :data="product"></releva-item>
    <releva-item title="服务" class="mt10" :data="service"></releva-item>

    <div class="mt10">
      <Checkbox v-model="checkbox" @on-change="handleChange"> 推送相关信息</Checkbox>
    </div>

  </div>
</template>
<script>
import vuiTitle from './title'
import relevaItem from './releva-item'
export default {
  props: {
    follow: {
      type: Object,
      default () {
        return {
          knowledge: [],
          info: [],
          policy: []
        }
      }
    },
    species: {
      type: Array,
      default () {
        return []
      }
    },
    product: {
      type: Array,
      default () {
        return []
      }
    },
    service: {
      type: Array,
      default () {
        return []
      }
    }
  },
  components: {
    vuiTitle,
    relevaItem
  },
  data: () => ({
    checkbox: true
  }),
  methods: {
    handleChange (flag) {
      this.$emit('on-get-data', flag)
    }
  }
}
</script>
