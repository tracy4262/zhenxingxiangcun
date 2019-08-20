
<template>
  <ul class="pl10 pr30">
      <li class="vui-flex" v-for="(item, index) in data" v-if="item.value">
          <i style=" width:20px;" class="mr20"/>
          <div class="vui-flex-item">
          {{item.label}}：<span v-if="item.type === 'text'">{{item.value}}</span>
        </div>
      </li>
  </ul>
</template>
<script>
export default {
  props: {
    data: Array,
  },
  data: () => ({
  }),
  methods: {
  }
}
</script>
<style lang="scss" scoped>
</style>
