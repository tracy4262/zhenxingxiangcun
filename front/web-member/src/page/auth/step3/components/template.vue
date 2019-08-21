<template>
  <div class="pd20">
    <Row :gutter="32">
      <Col span="6" v-for="(item, index) in data" class="tc" style="cursor: pointer;" >
      <!-- box-shadow: 0px 1px 14px #ccc;" -->
        <div style="width:128px; margin:0 auto; " :class="[item.type === active ? 'checked': 'no-checked']">
          <img src="../../../../img/country.png" alt="" width="100%" height="210px" @click="handleChecked(item)">
        </div>
        <p class="tc pt20 pb30" @click="handleChecked(item)">{{item.name}}</p>
      </Col>
    </Row>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        data: [
          {
            src: '../../../../img/country.png',
            name: '默认模板',
            type:'D'
          },
          {
            src: '../../../../img/country.png',
            name: '付费模板',
            type:'Y'
          }
        ],
        active: 'D'
      }
    },
    methods: {
      init (data) {
        this.active = data
      },
      handleChecked (item) {
        this.active = item.type
      }
    }
  }
</script>
<style scpoed>
.no-checked {
  border: 2px solid #fff;
}
  .checked {
    border: 2px solid #00c587;
  }
</style>
