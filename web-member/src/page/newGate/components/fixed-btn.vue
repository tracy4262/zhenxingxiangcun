<template>
  <div class="fixed-btn">
    <div class="btn">
      <p>我要投诉</p>
    </div>
    <Poptip trigger="click" placement="left-start">
      <div class="btn mt15">
        <p>在线咨询</p>
      </div>
      <div class="advisory-box" slot="content">
        <span class="button">在线联系</span>
        <span class="button">我要留言</span>
        <span class="button">我要聘请</span>
      </div>
    </Poptip>
  </div>
</template>

<script>
export default {
  components: {

  },
  data () {
    return {
      show: true
    }
  },
  mounted() {
  }
}
</script>

<style lang="scss" scoped>
.fixed-btn{
  position: fixed;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
  .btn{
    width: 30px;
    background: #00c587;
    padding: 8px 10px;
    line-height: 1.2;
    color: #fff;
    border-radius: 2px 0 0 2px;
    cursor: pointer;
  }
  .advisory-box{
    width: 100px;
    // height: 50px;
    color: #B2B2B2;
    .button{
      display: block;
      color: #4A4A4A;
      font-size: 14px;
      text-align: center;
      line-height: 35px;
      background: #eee;
      margin: 8px 0px;
      border-radius: 4px;
      cursor: pointer;
    }
  }
}
</style>
