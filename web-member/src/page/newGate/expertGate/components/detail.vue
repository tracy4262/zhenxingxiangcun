<template>
<div>
	<div class="pt20 pl5 pr5 information-new-detail" v-for="(item, index) in dataList" v-if="dataList.length">
    <h3 class="ell pb10">  
      <a @click="detail(item)">{{item.title}}</a> 
    </h3>
    <div class="vui-flex">
      <div v-if="item.imageAdd" class="pr15">
        <img :src="item.imageAdd" alt="" height="94px" width="170px">
      </div>
       <div class="vui-flex-item">
         <p class="ell-3">{{item.summary}}</p>
      </div>
    </div>
    <!-- <Row>
      <Col span="7" v-if="item.imageAdd">
        <img :src="item.imageAdd" alt="" height="94px" width="170px">
      </Col>
      <Col :span="item.imageAdd ? 17 : 24">
        <p class="ell-3">{{item.summary}}</p>
      </Col>
    </Row> -->
    <p class="t-grey pt15 pb20">
        <Icon class="icon-laud" size="18"></Icon> 赞 {{item.thumbUpNum}}
        <span class="pl10 pr10">|</span>
        <Icon type="ios-text-outline" size="18"/> {{item.postNum}} 条评论
    </p>
  </div>
  <p class="pd50 t-grey tc" v-if="!dataList.length">暂无相关数据</p>
</div>
</template>
<script>
import {goToPath} from '../../mixins/commonMixins'
  export default {
    mixins: [goToPath],
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
      detail (item) {
        this.goDetail(item)
      }
    }
  };
</script>
<style lang="scss" scoped>
.information-new-detail{
  border-bottom: 1px solid#E8E8E8;
  h3{
    font-size: 18px;
    line-height: 25px;
    font-weight: 700;
    color: #4a4a4a;
    a{
    //   color: rgba(0,0,0,0.85);
        color: #4a4a4a;
      &:hover{
        // color: rgba(0,0,0,0.65);
        color: #4a4a4a;
      }
    }
  }
  img{
      border-radius: 4px;
  }
  p{
    color: #4a4a4a;
    font-size: 14px;
    line-height: 24px;
  }
}
</style>