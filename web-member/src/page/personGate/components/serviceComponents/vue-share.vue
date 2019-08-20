<template>
  <div class="vui-share">
    <ul>
      <li class="icon-wechat">
        <img src="../../../../img/weixin-icon.png" alt="" width="28px" height="28px"  class="mr10">
        <div class="wechat-qrcode">
          <h5>微信扫一扫：分享</h5>
          <canvas id="canvas" ref="canvas" class="qrcode" style="width:160px;height 160px;"></canvas>
          <div>微信里点“发现”，扫一下<br>二维码便可将本文分享至朋友圈。</div>
        </div>
      </li>
      <li>
        <img src="../../../../img/QQ.png" alt="" width="28px" height="28px"  @click="shareTo('1')">
      </li>
      <li>
        <img src="../../../../img/weibo.png" alt="" width="28px" height="28px" @click="shareTo('2')">
      </li>
    </ul>
  </div>
</template>
<script>
import QRCode from 'qrCode'
export default {
  components: {
    QRCode
  },
  data: () => ({
    src: [],
    url: window.location.href,
    fullPath: '',
    title: '湖北省乡村振兴公共服务云平台--专注农业服务的平台',
    desc: '专注农业服务的平台',
    image: '',
  }),
  mounted () {
    console.log(window.location.href)
    var canvas = this.$refs['canvas']
    QRCode.toCanvas(canvas, window.location.href, function (error) {
      if (error) console.error(error)
      console.log('QRCode success!')
    })
  },
  created () {
  },
  methods: {
    shareTo (type) {
      let url = ''
      if (type == '1') { // 1 QQ 2 微博
        url = `http://connect.qq.com/widget/shareqq/index.html?url=${encodeURIComponent(this.url)}&title=${this.title}&source=${this.url}&desc=${this.desc}&pics=`
      } else if (type == '2') {
        url = `http://service.weibo.com/share/share.php?url=${encodeURIComponent(this.url)}&title=${this.title}&pic=${this.image}&appkey=''`
      }
      window.open(url)
    }
  },
}
</script>
<style lang="scss" scoped>
.vui-share{
  ul{
    li{
      list-style: none;
      width: 28px;
      height: 28px;
      float: left;
      margin-right: 20px;
    }
  }
}
.icon-wechat {
  .wechat-qrcode{
      line-height: 1.5;
      display: none;
      border: 1px solid #eee;
      position:absolute;
      z-index:9;
      bottom: 40px;
      // left: 50%;
      left: 12px;
      padding: 10px;
      transform: translateX(-50%);
      color:#666;
      font-size:12px;
      text-align:center;
      background-color:#fff;
      box-shadow:0 2px 10px #aaa;
      transition:all 200ms;
     &:after{
       content:'';
       position:absolute;
       left:50%;
       margin-left:-6px;
       bottom:-13px;
       width:0;
       height:0;
       border-width:8px 6px 6px 6px;
       border-style:solid;
       border-color:#fff transparent transparent transparent
     }
  }
  
&:hover .wechat-qrcode{
      display: block;
    }
  .wechat-qrcode.bottom {
     top:40px;
     left:-84px;
     &:after {
       display:none;
     }
   }
   .qrcode{
     width: 150px !important;
     height: 150px !important;
     margin:10px auto;
   }
  
}

</style>
