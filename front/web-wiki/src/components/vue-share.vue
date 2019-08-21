<template>
  <div class="vui-share">
    <a
      target="_blank"
      :href="item.src"
      v-for="(item, index) in src" :class="item.name"></a>
    <a href="javascript:;" class="icon-wechat">
      <div class="wechat-qrcode">
        <h5>微信扫一扫：分享</h5>
        
        <canvas id="canvas" ref="canvas" class="qrcode" style="width:160px;height: 160px;"></canvas>
        <!-- <qr-code :text="url" :size="150" class="qrcode"></qr-code> -->
        <div>微信里点“发现”，扫一下<br>二维码便可将本文分享至朋友圈。</div>
      </div>
    </a>
  </div>
</template>
<script>
// import qrCode from 'vue-qrcode-component'
import QRCode from 'qrcode'
export default {
  components: {
    QRCode
  },
  data: () => ({
    src: [],
    url: window.location.href,
    fullPath: '',
    title: '湖北省乡村振兴公共服务云平台--物种百科',
    desc: '专注农业百科的服务平台',
    image: ''
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
    this.src = [{
      src: `http://connect.qq.com/widget/shareqq/index.html?url=${encodeURIComponent(this.url)}&title=${this.title}&source=${this.url}&desc=${this.desc}&pics=`,
      name: 'icon-qq'
    }, {
      src: `http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=${encodeURIComponent(this.url)}&title=${this.title}`,
      name: 'icon-qzone'
    }, {
      src: `http://service.weibo.com/share/share.php?url=${encodeURIComponent(this.url)}&title=${this.title}&pic=${this.image}&appkey=''`,
      name: 'icon-weibo'
    }]
  }
}
</script>
<style lang="scss" scoped>
.vui-share{
  padding-top: 10px;
  position: absolute;
  z-index: 9;
  display: none;
  width: 100%;
  [class^='icon-'],
  [class*=' icon-'] {
    background: #fff;
    display:block;
    margin: 8px auto;
    width: 32px;
    height: 32px;
    font-size: 20px;
    border-radius: 50%;
    line-height: 32px;
    border:1px solid #666;
    color: #666;
    transition: background 0.6s ease-out 0s;
    &:before{
      vertical-align: top;
    }
    &:hover{
      background: #666;
      color: #fff;
    }
  }
  .icon-weibo{
    color:#ff763b;
    border-color:#ff763b;
    &:hover{
      background:#ff763b;
    }
  }
  .icon-qq{
    color:#56b6e7;
    border-color:#56b6e7;
    &:hover{
      background:#56b6e7;
    }
  }
  .icon-qzone{
    color:#FDBE3D;
    border-color:#FDBE3D;
    &:hover{
      background:#FDBE3D;
    }
  }
  .icon-wechat{
    position:relative;
    color:#7bc549;
    border-color:#7bc549;
    &:hover{
      background:#7bc549;
    }
  }
}

.icon-wechat {
  &:hover .wechat-qrcode{display: block;}
    .wechat-qrcode{
      line-height: 1.5;
      display: none;
      border: 1px solid #eee;
      position:absolute;
      z-index:9;
      bottom: 40px;
      left: 50%;
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
