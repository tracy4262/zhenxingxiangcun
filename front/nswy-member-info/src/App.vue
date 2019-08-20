<template>
  <div id="app">
   <div v-if="!detail">
      <index @on-detail="handleDetail"/>
   </div>
    <detail v-else :id="id" :title="title" @on-close="close"></detail>
  </div>
</template>
<script lang="js">
import index from './views/new-index'
import detail from './views/detail'
export default {
  components: {
    index,
    detail
  },
  data () {
    return {
      detail: false,
      id: '',
      title: ''
    }
  },
  methods: {
    handleDetail (item) {
      this.detail = true
      this.id = item.id
      this.title = item.memberName
    },
    close () {
      this.detail = false
    }
  }
}
</script>
<style lang="scss" scoped>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #333;
}
</style>
