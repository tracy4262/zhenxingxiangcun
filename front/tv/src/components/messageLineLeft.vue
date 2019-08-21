<template>
<div class="ivu-message-line">
    <div v-for="(item,index) in data" :class="isShow ? '' :'show'">        
        <div class="line" v-if="item.type == 2">
            <div class="message-line fl">
                 <Tooltip :content="item.name" placement="top-start">
                     <div class="ivu-message-name">{{item.name}} </div>  <img src="../../static/img/v.png" alt="">
                </Tooltip>
               
                <div class="ivu-message-text">
                    <span class="arrow"></span>                            
                    <div>{{item.text}}</div>
                </div>
            </div>
        </div>
        <div class="line" v-else>
            <div class="message-line-right fr">
                <div class="ivu-message-text">
                    <span class="arrow"></span>                       
                    <div>{{item.text}}</div>
                </div>
                <div class="ivu-message-name">{{item.name}}  </div>  
            </div>
        </div>
    </div>   
</div>    
</template>
<script>
     export default{
         props:{
             data:{
                 type:Array,
                 default:true
             },
             isShow:{
                 type:Boolean,
                 default:true
             }
         },
         data () {
             return{

             }
         }
     }
</script>
<style>
.ivu-message-line{
    overflow: hidden;
    padding: 10px 0px;
}
.ivu-message-line .line{
    padding:10px 0px;
    overflow: hidden;
}
.ivu-message-line .ivu-message-name,.ivu-message-line .ivu-message-text{
    display: inline-block;
}
.ivu-message-line .ivu-message-name{
    padding-top:6px;
    vertical-align: top;
    font-size: 16px;
    max-width: 70px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.ivu-message-line .message-line-right .ivu-message-name{
    padding-left:15px;
}
.message-line  img{
    vertical-align: top;
    padding: 10px 18px 0px 5px;
}
.ivu-message-line .ivu-message-text{    
    padding: 10px;
    background: #fff;
    border-radius: 4px;
    border: 1px solid #D3D3D3;
    position: relative;
    max-width: 260px;
    font-size: 16px;
}
.ivu-message-line .show .ivu-message-name{
    max-width:100px;
}
.ivu-message-line .show .ivu-message-text {    
    max-width: 660px;
}
.ivu-message-line .message-line-right .ivu-message-text{
     border: 1px solid #259F5A;
     background: #4FAC77;
     color:#fff;
}
.ivu-message-line .arrow{
    position: absolute;
    height: 10px;
    width: 10px;   
    transform: rotate(45deg);
    border-radius: 2px;
}
.ivu-message-line .message-line .arrow{
    border: 1px solid #D3D3D3;
    left: -5px;
    top: 10px;
    background: #fff;
    border-top: none;
    border-right: none;    
}
.ivu-message-line .message-line-right .arrow{
    right: -5px;
    top:10px;
    border: 1px solid #259F5A;
    background: #4FAC77;    
    border-bottom: none;
    border-left: none;
}
</style>
