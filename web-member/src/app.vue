<template>
   <div id="app">
        <router-view></router-view>
        <template v-if="this.loginuserinfo !== null">
            <vui-layim />
        </template>
        <BackTop :bottom="60"></BackTop>
    </div>
</template>

<script>
    import vuiLayim from '~components/vui-layim'
    export default {
        name: 'app',
        components: {
            vuiLayim,
        },
        data() {
            return {
                loginuserinfo: null
            }
        },
        watch: {
            '$route' (to, from) {
                this.loginuserinfo=JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            }
        },
        created(){
            this.loginuserinfo=JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
        }
    }

</script>
<style lang="scss">
// 弹窗垂直居中
// .ivu-modal-wrap{
//     display: flex;
//     align-items: center;
//     justify-content: center;
//     .ivu-modal{
//         top: 0;
//     }
// }
// tab切换显示边框 重置
:focus {
    outline: #fff auto 0px;
}
// tab切换显示边框 重置

.vui-form-sm{
	.ivu-form-item{
		margin-bottom: 10px
	}
}
.layouts{
	width: 1200px;
	margin: 0 auto;
}
</style>

<style lang="scss">
i.ivu-icon.ivu-icon-ios-loading.ivu-load-loop.ivu-input-icon.ivu-input-icon-validate {
    display: none;
}
.ivu-tabs{
    .ivu-tabs-bar{
        &:after{
            content: "";
            clear: both;
        }
        &:after,
        &:before{
            display: table;
        }
        .ivu-tabs-nav-right{
            margin-bottom: 3px;
        }
    }
}
.ivu-tag {
  &.ivu-tag-border:after{
    background: #e9eaec;
  }
  .ivu-icon-ios-close-empty{
    padding: 0 5px !important;
    margin-left: 0!important;
  }
}
.ivu-tag-border.ivu-tag-closable .ivu-icon-ios-close-empty{
  margin-left: 0!important;
  margin-right: -5px;
}
</style>
