<template>
  <div class="gate-honor-list">
    <div  v-for="(item, index) in data">
      <Tooltip class="vui-tooltip" placement="top" :content="`${moment(item.history_time).format('YYYY年MM月DD日')}${item.honorary_name}`" :delay="1000">
        <p class="list ell">{{moment(item.history_time).format('YYYY年MM月DD日')}}  {{item.honorary_name}}</p>
      </Tooltip>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    data: {
      type: Array,
      default: () => {
        return []
      }
    }
  }
}
</script>
<style lang="scss">
.gate-honor-list{
  .list{
    // line-height: 38px;
    padding: 5px 0px;
    cursor: default;
    font-size: 14px;
    color: #4A4A4A;
  }
  .vui-tooltip{
    width: 100%;
  }
}
</style>

