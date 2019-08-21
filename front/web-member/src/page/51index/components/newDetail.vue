<template>
<div>
	<div class="pt20 pd20 information-new-detail" v-for="(item, index) in dataList" :key="index">
    <h3 class="ell pb10">  
      <a :href="item.isSrc">{{item.title}}</a> 
    </h3>
    <Row>
      <Col :span="item.imageAdd ? 17 : 24">
        <Tag class="pr10" v-if="item.docType">{{item.docType}}</Tag><Tag v-if="item.speciesName">{{item.speciesName}}</Tag>
        <p class="ell-3 pt15">{{item.abstracts}}</p>
        <p class="pt15 t-grey">
          <img src="../../../img/man.png" alt=""  height="20px" width="20px" style="border-radius: 50%;"> 
          <span class="pl10 pr10">范芳</span>
          <span class="pr10">发布于</span> 
          <span style="color: rgba(0,0,0,0.25);">{{item.createTime}}</span>
        </p>
        <p class="t-grey pt15">
          <Icon class="icon-laud" size="18"></Icon>
          25
          <span class="pl10 pr10">|</span> 
          <Icon type="ios-text-outline" size="18"/>
          12
        </p>
      </Col>
      <Col span="7" class="tr" v-if="item.imageAdd">
        <img :src="item.imageAdd" alt="" height="168px" width="288px">
      </Col>
    </Row>
  </div>
  
</div>
</template>
<script>
  export default {
    data() {
			return {
			}
		},
    props: {
        dataList: {
            type:Array
        },
        small:{
            typr:Boolean,
            default:false
        }
    },
    created(){
		},
    updated() {
        let Img = document.querySelectorAll('.ma_images img')
        Array.prototype.slice.call(Img).map(function(item){
            item.style.width = '100%'
            item.style.height = '100%'
        })
    },
    methods: {
      
    }
  };
</script>
<style lang="scss" scoped>
.information-new-detail{
  border-bottom: 1px solid#E8E8E8;
  h3{
    font-size: 16px;
    line-height: 24px;
    font-weight: 700;
    a{
      color: rgba(0,0,0,0.85);
      &:hover{
        color: rgba(0,0,0,0.65);
      }
    }
  }
  p{
    color: rgba(0,0,0,0.65);
    font-size: 14px;
    line-height: 24px;
  }
}
</style>
