<template>
  <ul class="vui-steps">
    <template v-for="(item, index) in data">
      <li :class="{'vui-finish': active > index || active === index}" @click="handleClick(item)">
        <template v-if="item.url">
          <router-link class="bd" :to="item.url">
            {{index+1}}.{{item.name}}
          </router-link>
        </template>
        <template v-else>
          <div class="bd">
            {{index+1}}.{{item.name}}
          </div>
        </template>
      </li>
    </template>
  </ul>
</template>

<script>
export default {
  props: {
  	active:{
  		type:Number,
  		default:0
  	},
    data: {
      type: Array,
      default: []
    }
  },
  methods: {
    handleClick (item) {

    }
  }
}
</script>

<style lang="scss">
//公用三角形
@mixin triangle($width...){
  border-width:$width;
  border-style:solid;
  border-color:transparent;
}
$steps-default: #eee;
$gray-lighter: #999;
$steps-finish: #00c587;
.vui-steps{
    display:table;
    width:100%;
    overflow: hidden;
    border-radius: 3px;
    li{
        position: relative;
        display:table-cell;
        vertical-align: middle;
        min-width:100px;
        background-color:$steps-default;
        color:$gray-lighter;
        padding:0 10px 0 30px; 
        .bd{
            display:block;
            padding:6px 0;
            white-space: nowrap;
            color:$gray-lighter;
            &:after,&:before{
                content:'';
                position: absolute;
                @include triangle(16px);
            }
            &:after{
                border-left-color:#fff;
                border-width: 20px;
                right: -40px;
                top: -4px;
                z-index: 1;
            }
            &:before{
                border-left-color:$steps-default;
                z-index:2;
                right:-32px;
                top: 0;
            }
        }
        &:first-child{
            padding-left: 10px;
        }
        &:last-child{
            .bd{
                &:after,&:before{display:none;}
            }
        }
    }
    .vui-finish{
        background-color:$steps-finish;
        .bd:before{
            border-left-color:$steps-finish;
        }
        .bd{color:#fff;}
    }
}
</style>
