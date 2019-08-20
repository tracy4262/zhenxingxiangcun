<template>
  <div class="vui-flex">
    <slot name="tab"></slot>
    <div class="vui-flex-item">
      <Card>
        <slot name="content">
        </slot>
      </Card>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  data () {
    return {

    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>

</style>
