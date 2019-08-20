<template>
  <div>
     <Modal
        v-model="isShow"
        :mask-closable="false"
        width="1000px"
        class="relation-manage-modal"
        title="邀请好友">
          <div>
            <div class="vui-tabs tc">
              <span @click="active = index" v-for="(item, index) in data" class="vui-tabs-span" :class="active === index ? 'tabs-active' : ''">{{item.label}}</span>
            </div>
            <div v-for="(item, index) in data">
              <addPage :type="item.type" @on-focus="handleFocus" v-show="active === index" :ref="`add${active}`"></addPage>
            </div>
          </div>
        <groupList ref="groupList" @on-save="onSave"></groupList>
        <div slot="footer" class="tc">
        </div>
    </Modal>
  </div>
</template>
<script>
import addPage from './addPage'
import groupList from './groupList'
export default {
  components: {
    addPage,
    groupList
  },
  data () {
    return {
      isShow: false,
      data: [
        {type: '1', label: '全部', data: []},
        {type: '2', label: '个人', data: []},
        {type: '3', label: '企业', data: []},
        {type: '4', label: '机关', data: []},
        {type: '5', label: '专家', data: []},
      ],
      activeTabs: '1',
      active: 0,
      focusData: []
    }
  },
  created() {
  },
  methods: {
    init () {
      this.isShow = true
    },
    // 选择分组 之后的回调
    onSave (data) {
      let groupId = data[0].id
      // invite 0邀请他  1 接受他  
      this.$api.post('/member/relationshipCircle/insertGroupFriendInfo', 
      {account: this.$user.loginAccount, invite: '0', groupId: groupId, dataList: this.focusData}).then(response => {
        if (response.code === 200) {
          this.$Message.success('邀请成功，请等待对方接受邀请！')
          console.log(this.active)
          this.$refs['groupList'].isShow = false
          this.$refs[`add${this.active}`][0].getInit()
        } else {
          this.$Message.error('邀请失败！')
        }
      })
    },
    handleFocus (arr) {
      this.focusData = arr
      this.$refs['groupList'].init()
    },
  },
  
}
</script>
<style>
.relation-manage-modal-add.ivu-tabs{
  overflow: inherit;
}
</style>
<style lang="scss">
.relation-manage-modal{
  &>.ivu-modal-wrap{
    &>.ivu-modal{
      &>.ivu-modal-content{
        border-radius: 0px;
        &>.ivu-modal-close{
          .ivu-icon-ios-close{
            color:#fff;
          }
        }
        &>.ivu-modal-header{
          background: #00C587;
          border-bottom: none;
          .ivu-modal-header-inner{
            color:#fff;
          }
        }
        &>.ivu-modal-body{
          padding: 0px;
          .vui-tabs{
            background: #00C587;
            overflow: hidden;
            .vui-tabs-span{
              width:80px;
              height: 60px;
              line-height: 60px;
              color: #fff;
              display: inline-block;
              font-size: 16px;
              cursor: pointer;
            }
            .tabs-active{
              position: relative;
              &:before{
                content: '';
                display: inline-block;
                position: absolute;
                width: 20px;
                height: 20px;
                bottom: -15px;
                left: 30px;
                background: #fff;
                transform:rotate(45deg);
                -ms-transform:rotate(45deg); 	/* IE 9 */
                -moz-transform:rotate(45deg); 	/* Firefox */
                -webkit-transform:rotate(45deg); /* Safari 和 Chrome */
                -o-transform:rotate(45deg); 	/* Opera */
              }
            }
          }
        }
        &>.ivu-modal-footer{
          padding: 0px;
          border-bottom: none;
        }
      }
    }
  }
}
</style>


