<template>
<div :class="zoomer_box">
    <div class="preview-box" >
      <img :src="previewImg.url" 
            :data-zoom="previewLargeImg.url" 
            class="responsive-image"
            ref="img"
      />
      <div :id="pane_id" class="pane-container"></div>
    </div>
    <div class="control-box">
      <Row type="flex" align="middle">
        <Col span="1">
          <div @click="moveThumbs('left')" class="control">
            <Icon type="ios-arrow-back" />
          </div>
        </Col>
        <Col span="22" style="overflow: hidden;">
          <div class="thumb-list">
            <img @mouseover="chooseThumb(thumb, $event)" 
              v-show="key < options.scroll_items" 
              :key="key" 
              :src="thumb.url" 
              @click="chooseThumb(thumb, $event)" 
              v-for="(thumb, key) in thumbs" 
              class="responsive-image" 
              v-bind:style="{'boxShadow' : thumb.id === choosedThumb.id ? '0px 0px 0px 2px ' + options.choosed_thumb_border_color : ''}"
              :class="{'choosed-thumb': thumb.id === choosedThumb.id}">
          </div>
        </Col>
        <Col span="1">
          <div @click="moveThumbs('right')" class="control">
            <Icon type="ios-arrow-forward" />
          </div>
        </Col>
      </Row>
    </div>
    
</div>
</template>

<script>
import Drift from './assets/drift-zoom/src/js/Drift.js'

export default {
  name: 'productzoomer',
  props: {
    baseZoomerOptions: {
      type: Object,
      default: function () {
        return {}
      }
    },
    scroll_items: {
      type: Number,
      default: 4
    }
    // baseImages: {
    //   type: Array,
    //   required: true,
    //   default: function () {
    //     return []
    //   }
    // }
  },
  data () {
    return {
      baseImages: [],
      previewImg: {},
      previewLargeImg: {},
      thumbs: [],
      normal_size: [],
      large_size: [],
      choosedThumb: {},
      drift: null,
      options: {
        'zoomFactor': 4,
        'pane': 'container',
        'hoverDelay': 100,
        'namespace': 'container-zoomer',
        'move_by_click': false,
        'scroll_items': 0,
        'choosed_thumb_border_color': '#00c587'
      }
    }
  },
  computed: {
    zoomer_box: function () {
      return this.options.namespace + '-zoomer-box'
    },
    pane_id: function () {
      return this.options.namespace + '-pane-container'
    }
  },
  mounted () {
    document
      .querySelector('.' + this.zoomer_box + ' .thumb-list')
      .setAttribute(
        'style',
        'grid-template-columns: repeat(' +
          this.baseZoomerOptions.scroll_items +
          ', auto)'
      )
    let t = setInterval(() => {
      if (document.readyState === 'complete') {
        if (this.options.pane === 'container-round') {
          this.options.inlinePane = true
        } else {
          this.options.inlinePane = false
          this.options.paneContainer = document.getElementById(this.pane_id)
          let rect = document
            .querySelector('.' + this.zoomer_box)
            .getBoundingClientRect()
          let customStyle = ''
          if (this.options.pane === 'pane') {
            customStyle =
              'width:' +
              rect.width * 1.2 +
              'px;height:' +
              rect.height +
              'px;left:' +
              (rect.right + window.scrollX + 5) +
              'px;top:' +
              (rect.top + window.scrollY) +
              'px;'
          } else {
            customStyle =
              'width:' +
              rect.width +
              // 'px;height:' +
              // rect.height +
              'px;left:' +
              this.$refs.img.clientWidth +
              'px;top: 0' +
              'px;bottom: 0' +
              // (rect.top + window.scrollY) +
              'px;'
          }
          this.options.paneContainer.setAttribute('style', customStyle)
        }

        this.options.injectBaseStyles = true
        let previewImg = '.' + this.zoomer_box + '>div>img'
        this.drift = new Drift(
          document.querySelector(previewImg),
          this.options
        )
        clearInterval(t)
      }
    }, 500)
  },
  watch: {
    choosedThumb: function (thumb) {
      let matchNormalImg = this.normal_size.find(img => {
        return img.id === thumb.id
      })
      let matchLargeImg = this.large_size.find(img => {
        return img.id === thumb.id
      })
      this.previewLargeImg = Object.assign({}, matchLargeImg)
      this.previewImg = Object.assign({}, matchNormalImg)
      if (this.drift !== null) {
        this.drift.setZoomImageURL(matchLargeImg.url)
      }
    }
  },
  created () {
    
  },
  // watch:{
  //   baseImages: {
  //     handler (curVal,oldVal) {  
  //       this.createds()
  //     },  
  //     deep:true 
  //   }
  // },
  methods: {
    createds (e) {
      this.baseImages = e
      this.baseImages.forEach((item, index) => {
        item.id = index
      })
      this.thumbs = this.baseImages
      this.normal_size = this.baseImages
      this.large_size = this.baseImages
      this.choosedThumb = this.thumbs[0]

      if (Object.keys(this.baseZoomerOptions).length > 0) {
        for (const key in this.baseZoomerOptions) {
          if (this.baseZoomerOptions.hasOwnProperty(key)) {
            const element = this.baseZoomerOptions[key]
            this.options[key] = element
          }
        }
      }

      if (
        this.options.pane === 'container-round' ||
        this.options.pane === 'container'
      ) {
        this.options.hoverBoundingBox = false
      } else {
        this.options.hoverBoundingBox = true
      }
      this.options.scroll_items = e.length
    },
    moveThumbs (direction) {
      let len = this.thumbs.length
      if (direction === 'right') {
        const moveThumb = this.thumbs.splice(len - 1, 1)
        this.thumbs = [moveThumb[0], ...this.thumbs]
      } else {
        const moveThumb = this.thumbs.splice(0, 1)
        this.thumbs = [...this.thumbs, moveThumb[0]]
      }
    },
    chooseThumb (thumb, event) {
      let eventType = event.type
      if (eventType === 'mouseover') {
        if (this.options.move_by_click !== true) {
          this.choosedThumb = thumb
        }
      } else {
        this.choosedThumb = thumb
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import "./assets/drift-zoom/src/css/drift-basic.css";
.preview-box{
  position: relative;
  height:400px;
  margin-bottom: 10px;
  img{
    height: 100%;
  }
}
.control-box {
  .control{
    height: 82px;
    cursor: pointer;
    line-height: 82px;
    border: 1px solid #EDEDED;
    text-align: center;
  }
  .thumb-list {
    display: inline-block;
    vertical-align: middle;
    white-space:nowrap;
    img{
      width: 80px;
      height: 80px;
      margin: 0 5px;
      padding: 2px;
      vertical-align: middle;
      border: 1px solid #EDEDED;
    }
  }
}
.choosed-thumb {
  border-radius: 0px;
}
.pane-container {
  display: none;
  position: absolute;
  z-index: 10000;
  pointer-events: none;
}
.responsive-image {
  height: auto;
  width: 100%;
}
</style>
