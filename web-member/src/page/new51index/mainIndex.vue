<template>
  <div>
    <Row></Row>
    <Row>
      <Col span="16">
        <Row>
          <Col span="11">
            <Carousel autoplay v-model="value2" loop>
              <CarouselItem v-if="index<4" v-for="(i,index) in news" :key="index">
                <router-link class="demo-carousel pointer" tag='div' to=''>{{i}}</router-link>
              </CarouselItem>
            </Carousel>
          </Col>
          <Col span="13" style="padding:0 8px">
            <div v-if="index>=4" v-for="(i,index) in news" :key="index">
              <router-link tag='div' to='' class="pointer mainNews">{{i}}</router-link>
            </div>
          </Col>
        </Row>
      </Col>
      <Col span="8" class="tianqi">
        <iframe
          scrolling="no"
          src="https://tianqiapi.com/api.php?style=ti&skin=pitaya"
          frameborder="0"
          width="400"
          height="261"
          allowtransparency="true"
        ></iframe>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      value2: 0,
      news: [
        "新闻1",
        "新闻2",
        "新闻3",
        "新闻4",
        "新闻5",
        "新闻6",
        "新闻7",
        "新闻8",
        "新闻9",
        "新闻10",
        "新闻11",
        "新闻12",
      ]
    };
  }
};
</script>
<style lang="scss" scoped>
.demo-carousel {
  height: 333px;
  background: #01c487;
}
.pointer{
  &:hover{
    cursor: pointer;
  }
}
.tianqi {
  border: 3px solid #efefef;
  height: 333px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.mainNews{
  border-bottom:2px dashed #efefef
}
</style>
