<template>
  <div>
     <div class="app ml10 mr10 tc mt20" v-for="(item, index) in data" :key="index" v-if="data.length">
        <div class="pd20">
          <div><img :src="item.logo"  width="64px" height="50px"></div>
          <Tooltip placement="top" :content="item.appName" :delay="1000" class="mt20">
              <div class="app-name ell">{{ item.appName }}</div>
          </Tooltip>
          <div class="mt10 app-number">使用人数：{{ item.number }}</div>
          <div class="mt10 app-price" v-if="!item.cost">免费</div>
          <div class="mt10 app-price" v-if="item.cost">收费</div>
          <div class="mt15">
              <Button type="primary" v-if="!item.checked" @click="add(item, index)" style="width: 84px;">添加</Button>
              <Button v-else @click="cancel(item, index)" style="width: 84px;">取消</Button>
          </div>
        </div>
        <div class="bg-greens">
          <div class="pd20">
          <p class="tl">{{item.applicationAbstract}}</p>
          </div>
          <div class="pd20 button">
            <Button type="primary" class="add" v-if="!item.checked" @click="add(item, index)" style="width: 84px;">添加</Button>
            <Button v-else class="del" @click="cancel(item, index)" style="width: 84px;">取消</Button>
          </div>
        </div>
    </div>
    <div class="tc pd50" v-if="!data.length">
      <p>暂无相关数据</p>
    </div>
  </div>
</template>
<script>
  export default {
    props: {
      data: {
        type: Array,
        default: () => {
          return []
        }
      },
      templateId: String
    },
    data () {
      return {
        loading: false
      }
    },
    methods: {
      add (item, index) {
        if (!this.loading) {
          this.loading = true
          this.$api.post('/member/applicationCentrality/saveOrCancelAppInfo',{
            account: this.$user.loginAccount,
            appId: item.appSettingId,
            appName: item.appName,
            type: 1,
            templateId: this.templateId
          }).then(response =>{
            this.loading = false
            if (response.code === 200) {
              this.$Message.success('添加成功')
              item.checked = true
              item.number++
              // 改变 其他tab页面的数据
              this.$emit('on-change', item)
            } else {
              this.$Message.error('添加失败')
            }
          })
        }
      },
      cancel (item, index) {
        if (!this.loading) {
          this.loading = true
          this.$api.post('/member/applicationCentrality/saveOrCancelAppInfo',{
            account: this.$user.loginAccount,
            appId: item.appSettingId,
            appName: item.appName,
            type: 0,
            templateId: this.templateId
          }).then(response =>{
            this.loading = false
            if (response.code === 200) {
              this.$Message.success('取消成功')
              item.checked = false
              item.number--
            } else {
              this.$Message.error('取消失败')
            }
          })
        }
      }
    }
  }
</script>
<style lang="scss" scoped>
.app {
  width: calc(100% / 5 - 20px);
	height: 252px;
	background: #FFFFFF;
	border: 1px solid #E8E8E8;
	border-radius: 3px;
	display: inline-block;
  position: relative; 
  &:hover{
    .bg-greens{
      animation-name: changebox ;
      animation-duration: 1s;
      animation-timing-function: ease-in-out;
      animation-fill-mode: forwards;
      animation-iteration-count: 1;
    }
    @keyframes changebox {
      10% {background: #00C587;opacity: 0;}
      100% {background: #00C587;opacity: 1;}
    }
  }
  .bg-greens{
    background: #00C587;
    opacity: 0;
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0px;
    p {
      color: #D8F5F0;
      font-size: 14px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: normal;
      display: -webkit-box;
      -webkit-line-clamp: 8;
      -webkit-box-orient: vertical;
    }
    .button{
      position: absolute;
      bottom: 5px;
      text-align: center;
      display: block;
      width: 100%;
      .add{
        border: 1px  solid #B3EBE3;
        border-color: #BFEEE3;
        color: #B3EBE3;
        background-color: #32C495;
      }
      .del{
        border: 1px  solid #B3EBE3;
        border-color: #BFEEE3;
        color: #B3EBE3;
        background-color: #32C495;
      }
    }
  }
}
.app-name {
    color: #4A4A4A;
    font-size: 16px;
    font-weight: bold;
}
.app-number {
    color: #4A4A4A;
    font-size: 12px;
}
.app-price {
    color: #00C587;
}
</style>
<style lang="scss">
.ivu-app-center{
  .app-ranking{
    .index{
      width:20px;
      height: 20px;
      border-radius: 10px;
      background: #CFD4D7;
      color: #fff;
      line-height: 20px;
    }
    .top-active{
      background: #00c587;
    }
    .user-number{
      font-size:12px
    }
  }
}
</style>

