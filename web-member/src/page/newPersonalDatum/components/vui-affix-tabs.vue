<template>
  <Row>
    <Col span="4">
      <Affix @on-change="handleAffixChange" :offset-top="90">
        <Card :padding="25">
          <span class="scrollactive-item block mb10" style="font-weight: 700;"><Icon type="person"></Icon> 我的资料</span>
          <scrollactive ref="scrollactive"
            :offset="52"
            :always-track="false"
            :duration="600"
            :click-to-scroll="true"
            bezier-easing-value=".5,0,.35,1"
            @itemchanged="handleItemChange">
            <div class="vui-affix-vertical-tabs">
              <a
              :id="`item${index}`"
              v-for="(item, index) in data"
              :key="index" :href="`#${item.url}`"
              class="scrollactive-item block"
              @click="handleItemClick(index)">{{item.appName}}</a>
            </div>
            <div class="vui-affix-tabs-line" :style="{'width': `${tabWidth}px`,'transform': lineStyle}"></div>
          </scrollactive>
        </Card>
    </Affix>
    </Col>
    <Col span="20" class="pl20">
      <slot></slot>
    </Col>
  </Row>
</template>
<script>
import {EventTrigger} from '~utils/utils'
export default {
  props: {
    data: Array
  },
  data: () => ({
    elements: [],
    height: '',
    lineStyle: 0,
    tabWidth: 0,
    flag: false,
    hash:'',
    index: 0,
    loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
    account:'',
    datum:''
  }),
  created(){
    var hash = this.$router.history.current.hash
    if(hash){
      this.hash = hash.split('#')[1]
    }
    this.account = this.$route.query.uid
    if(!this.account){
        this.account = this.loginUser.loginAccount
    }
  },
  methods: {
    // 滚动改变
    handleItemChange (event, currentItem, lastActiveItem) {
      if (currentItem) {
        if (currentItem.index) {
          this.index = currentItem.index - 1
        } else {
          this.index = 0
        }
      }
    },
    // 点击
    handleItemClick (index) {
      if (index) {
        this.index = index
      } else {
        this.index = 0
      }
    },
    handleAffixChange (flag) {
      this.flag = flag
    }
  },
  watch: {
    flag (oldVal, newVal) {
      if (oldVal) {
        this.height =`${window.innerHeight + 98}px`
      } else {
        this.height = `${window.innerHeight - 98}px`
      }
    }
  },
  mounted () {
    this.height = `${window.innerHeight - 98}px`
    // 锚点跳转
    if(this.hash){
      setTimeout(() => {
        for(var i = 0; i < this.data.length; i++){
          if(this.data[i].name == this.hash){
            this.index = i
          }
        }
        EventTrigger('click', `item${this.index}`)
      }, 500)
    }

  },
  updated() {
    this.elements = this.$el.querySelectorAll('.scrollactive-item')
    this.elements.forEach((item, index) => {
      this.elements[index].index = index
    })
  }
}
</script>
<style lang="scss">
.scrollactive-nav{
  position: relative;
}
.vui-affix-vertical-tabs {
  a{
    padding: 2px 25px;
    color: #333;
    &.is-active{
      color: #3DBD7D;
    }
  }
  .block{
    display: block;
  }
}
</style>
