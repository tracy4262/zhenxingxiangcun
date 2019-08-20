<template>
<div>
	<div class="pt20 pd20 information-new-detail" v-for="(item, index) in dataList" v-if="dataList.length">
    <h3 class="ell pb10" @click="detail(item)">  
      <a >{{item.title}}</a> 
    </h3>
    <Row :gutter="16">
      <Col :span="item.imageAdd || item.coverPhoto ? 17 : 24">
        <Tag class="pr10" v-if="item.docType">{{item.docType}}</Tag>
        <Tag class="pr10" v-if="item.speciesName">{{item.speciesName}}</Tag>
        <Tag v-if="item.columnType">{{item.columnType}}</Tag>
        <div style="min-height:100px;">
          <p class="ell-3 pt15">{{item.summary}}</p>
        </div>
        <Row class="pt15" type="flex" align="middle">
          <Col span="18">
            <p class="t-grey">
              <Icon class="icon-laud" size="18"></Icon>
              {{item.thumbUpNum}}
              <span class="pl10 pr10">|</span> 
              <Icon type="ios-text-outline" size="18"/>
              {{item.postNum}}
            </p>
          </Col>
          <Col span="6" class="tr">
            <span style="color: rgba(0,0,0,0.25);">{{item.createTime}}</span>
          </Col>
        </Row>
      </Col>
      <Col span="7" class="tr" v-if="item.imageAdd || item.coverPhoto">
        <img :src="item.imageAdd" alt="" height="168px" width="100%" v-if="item.imageAdd">
        <img :src="item.coverPhoto" alt="" height="168px" width="100%" v-else>
      </Col>
    </Row>
  </div>
  <div v-if="dataList.length == 0">
    <p class="tc pd30">暂无数据</p>
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
      detail (item) {
        this.$parent.goDetail(item)
      }
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
      cursor: pointer;
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