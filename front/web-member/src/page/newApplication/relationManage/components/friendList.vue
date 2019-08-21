<template>
  <div class="relation-manage">
    <Row :gutter="16">
      <Col span="6" v-if="focusType === '2'">
        <div class="add-member tc" @click="handleAdd">
          <Icon type="md-add" size="38" class="mt30"/>
          <p class="pt10">添加好友</p>
        </div>
      </Col>
      <Col span="6" v-for="(item, index) in data" :key="index" class="tc pb30">
        <div class="member-list">
          <Row tyle="flex" align="middle" class="pt20">
            <!-- focusType 1 控件部分的关注列表显示 2 显示部分-->
            <Col span="9" v-if="focusType == '2'">
              <img :src="item.groupFriendAvatar" class="user-img" width="50px" height="50px" v-if="item.groupFriendAvatar"></img>
              <img src="../../../../img/default_header.png" class="user-img" width="50px" height="50px" v-else></img>
            </Col>
            <Col span="9" v-if="focusType == '1'">
              <img :src="item.avatar" class="user-img" width="50px" height="50px" v-if="item.avatar"></img>
              <img src="../../../../img/default_header.png" class="user-img" width="50px" height="50px" v-else></img>
            </Col>
            <Col span="15" v-if="focusType != '2'">
              <div class="pr20">
                <Tooltip :content="item.memberName" max-width="400" :delay="500" placement="top">
                  <p class="display-name ell" @click="goGate(item.account)">{{item.memberName}}</p>
                </Tooltip>
              </div>
              <div class="pr20">
                 <Tooltip :content="item.account" max-width="400" :delay="500" placement="top">
                  <p class="account ell" @click="goGate(item.account)">{{item.account}}</p>
                </Tooltip>
              </div>
            </Col>
            <Col span="15" v-if="focusType === '2'">
              <div class="pr20">
                <Tooltip :content="item.memberName" max-width="400" :delay="500" placement="top">
                  <p class="display-name ell" @click="goGate(item.groupFriendAccount)">{{item.memberName}}</p>
                </Tooltip>
              </div>
              <div class="pr20">
                 <Tooltip :content="item.groupFriendAccount" max-width="400" :delay="500" placement="top">
                  <p class="account ell" @click="goGate(item.groupFriendAccount)">{{item.groupFriendAccount}}</p>
                </Tooltip>
              </div>
            </Col>
          </Row>
          <div class="tc member-list-bottom" @click="addFocus(item, index)" v-if="focusType === '1'">
            <!-- // 0 未邀请 1 已邀请 未接受 2 已接受 -->
            <!-- focusType 1 控件部分的关注列表显示 2 显示部分-->
              <div>
                <span v-if="item.followType === '0' || item.followType === '1'">邀请</span>
                <span v-if="item.followType === '2'" class="t-green">已添加</span>
              </div>
          </div>
          <div class="tc member-list-bottom" v-if="focusType === '2'">
            <!-- // 0 未邀请 1 已邀请 未接受 2 已接受 -->
            <!-- focusType 1 控件部分的关注列表显示 2 显示部分-->
            <Row>
              <Col span="8" class="col" @click.native="onMove(item)"><p>移动</p></Col>
              <Col span="8" class="col" @click.native="onChat(item)"><p class="span">私聊</p></Col>
              <Col span="8" class="col" @click.native="onDelete(item)"><p>删除</p></Col>
            </Row>
          </div>
        </div>
      </Col>
    </Row>
      <div class="tc pt30 pb20" v-if="!data.length && focusType != '2'">
        <p>没有相关数据！</p>
      </div>
      <div class="tc pt40 pb20" v-if="data.length && focusType != '2'">
        <Page :total="pages.total" @on-change="getNextPage" :page-size="pages.pageSize" :current="pages.pageNum"></Page>
      </div>
      <div class="tr" style="right: 10px; bottom: 23px; position: absolute;" v-if="data.length && focusType != '2'">
        <Button type="primary" size="small" @click.native="focusAll">一键邀请</Button>
      </div>
  </div>
</template>
<script>
export default {
  props: {
    data: Array,
    focusType: {
      type: String,
      default: '2'
    },
    pages: {
      type: Object,
      default: () => {
        return {
          pageSize: 24,
          pageNum: 1,
          total: 0
        }
      }
    }
  },
  data () {
    return {
      // data: [
      //   {account: '20181010', memberName: '康罗娜', }
      // ]
    }
  },
  created() {
    
  },
  methods: {
    // 点击进入会员门户
    goGate (account) {
      this.$toPortals(account)
      // this.$router.push(`/portals/index?uid=${account}`)
    },
    handleAdd () {
      this.$emit('on-add')
    },
    addFocus (item, index) {
      // 0 未邀请 1 已邀请 未接受 2 已接受
      let type = item.followType
      if (item.account === this.$user.loginAccount) {
        this.$Message.warning('不能邀请自己为好友！')
      } else {
        if (type === '1') {
          this.$Message.warning('已邀请过该用户，请等待对方接受邀请！')
          return
        } else if (type === '0') {
          this.$emit('on-focus', [item])
        }
      }
    },
    // 一键关注
    focusAll () {
      let arr = []
      this.data.forEach((element, index) => {
        if (element.followType === '0') {
          arr.push(element)
        }
      });
      if (arr.length) {
        this.$emit('on-focus', arr)
      }
    },
    // 翻页
    getNextPage (e) {
      // this.pageNum = e
      this.$emit('on-change', e)
    },
    // 点击移动
    onMove (item) {
      this.$emit('on-move', item)
    },
    // 私聊
    onChat (item) {
      console.log(item)
      this.$api.post('/member/user/getUserByQuery',{queryType :1,account :item.groupFriendAccount}).then(res=>{
          layui.layim.chat({
              id: res.data.id,
              name: item.memberName,
              avatar: item.groupFriendAvatar,
              type: 'friend'
          });
      })
    },
    // 删除
    onDelete (item) {
      this.$Modal.confirm({
            title: '操作提示',
            content: '是否确认删除？',
            onOk:()=>{
              let data = {
                    account: this.$user.loginAccount,
                    dataList: [item],
                }
                // 拒绝好友邀请
                this.$api.post('/member/relationshipCircle/deleteGroupFriendInfo', data).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('删除成功！')
                        this.$emit('on-del', 1)
                    } else {
                        this.$Message.error('删除失败！')
                    }
                })
            },
            okText:'确定',
            cancelText:'取消'
        })
    }
  },
}
</script>
<style lang="scss" scoped>
.relation-manage{
  .add-member{
    border: 1px dashed rgba(232,232,232,1);
    height: 126px;
    width: 100%;
    color:#9B9B9B;
  }
  .member-list{
    background: #FFFFFF;
    border: 1px solid rgba(233,233,233,1);
    height: 126px;
    width: 100%;
    position: relative;
    &:hover{
      // border: 1px solid rgba(0,197,135,1);
    }
    .user-img{
      border-radius: 50%;
    }
    p{
      line-height: 25px;
      max-width: 95px;
    }
    .display-name{
      color: #373737;
      font-size: 14px;
      cursor: pointer;
    }
    .account{
      color: #B0B0B0;
      cursor: pointer;
      font-size: 12px;
    }
    .member-list-bottom{
      background: #F7F9FA;
      border: 1px solid rgba(233,233,233,1);
      height: 36px;
      width: 100%;
      line-height: 36px;
      color: #AFB0B1;
      position: absolute;
      bottom: 0px;
      cursor: pointer;
      p{
        font-size: 12px;
        line-height: 16px;
        margin-top:10px;
      }
      .span{
        border-right:1px solid #E9E9E9;
        border-left:1px solid #E9E9E9;
      }
      .col{
        height:36px;
        &:hover{
          .span{
            border: none;
          }
          background: #00C587;
          color: #fff;
        }
      }
    }
  }
}
</style>
