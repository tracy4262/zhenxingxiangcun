<template>
    <div id="qualification">
      <Row class="title">
        <Col span="2">个人信息</Col>
        <Col span="2" offset="16"><a href="##">修改</a></Col>
      </Row>
      <Row>
        <p>物种百科专属达人认证待审核！</p>
        <p>达人认证</p>
        <p>认证信息</p>
        <Col span="6">
          <img src="../../../img/timg.jpg" width="50" height="100"/>
        </Col>
       <p>
         <span><i>性别：</i><i>女</i></span>
         <span class="con"><i>擅长领域：</i><i>农药施肥</i></span>
       </p>
        <p>
          <span><i>专业：</i><i>农业</i></span>
          <span class="con"><i>职称：</i><i>高级农艺师</i></span>
        </p>
      </Row>
    </div>
</template>

<script>

    export default {
      data() {
            return {}
        }
    }
</script>

<style>
  .con{position: relative;padding-left: 200px}
    #qualification .ivu-row{background-color: white;}
</style>
