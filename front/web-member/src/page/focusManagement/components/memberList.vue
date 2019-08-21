<template>
  <div>
    <Row :gutter="16">
      <Col span="6" v-for="(item, index) in data" :key="index" class="tc pb30">
        <div class="member-list" :class="{'member-list-edit': edit, 'member-list-check': item.check && edit}">
          <!-- 1 关注我的 关注我的状态是 0 的时候可以操作批量关注 -->
          <div class="check-box" :class="item.check ? 'isCheck' : ''" v-if="edit && focusType != '1'" @click="handleCheck(item, index)">
            <Icon type="md-checkmark" />
          </div>
          <div class="check-box" :class="item.check ? 'isCheck' : ''" v-if="edit && focusType === '1' && item.followType === '0'" @click="handleCheck(item, index)">
            <Icon type="md-checkmark" />
          </div>
          <Row tyle="flex" align="middle" class="pt20">
            <!-- focusType 2 控件部分的关注列表显示 0 我关注的  1 关注我的-->
            <Col span="9" v-if="focusType === '1'">
              <img :src="item.followAvatar" class="user-img" width="50px" height="50px" v-if="item.followAvatar"></img>
              <img src="../../../img/default_header.png" class="user-img" width="50px" height="50px" v-else></img>
            </Col>
            <Col span="9" v-else>
              <img :src="item.avatar" class="user-img" width="50px" height="50px" v-if="item.avatar"></img>
              <img src="../../../img/default_header.png" class="user-img" width="50px" height="50px" v-else></img>
            </Col>
            <Col span="15">
            <!-- focusType 2 控件部分的关注列表显示 0 我关注的  1 关注我的-->
              <div class="pr20">
                <Tooltip :content="item.memberName" max-width="400" :delay="500" placement="top" v-if="focusType === '0'">
                  <p class="display-name ell" @click="goGate(item.followAccount)">{{item.memberName}}</p>
                </Tooltip>
                 <Tooltip :content="item.followAccountName" max-width="400" :delay="500" placement="top" v-if="focusType === '1'">
                  <p class="display-name ell" @click="goGate(item.account)">{{item.followAccountName}}</p>
                </Tooltip>
                <Tooltip :content="item.memberName" max-width="400" :delay="500" placement="top" v-if="focusType === '2'">
                  <p class="display-name ell" @click="goGate(item.account)">{{item.memberName}}</p>
                </Tooltip>
              </div>
              <div class="pr20">
                 <Tooltip :content="item.followAccount" max-width="400" :delay="500" placement="top" v-if="focusType === '0'">
                  <p class="account ell" @click="goGate(item.followAccount)">{{item.followAccount}}</p>
                </Tooltip>
                <Tooltip :content="item.account" max-width="400" :delay="500" placement="top" v-if="focusType === '1'">
                  <p class="account ell" @click="goGate(item.account)">{{item.account}}</p>
                </Tooltip>
                 <Tooltip :content="item.account" max-width="400" :delay="500" placement="top" v-if="focusType === '2'">
                  <p class="account ell" @click="goGate(item.account)">{{item.account}}</p>
                </Tooltip>
              </div>
            </Col>
          </Row>
          <div class="tc member-list-bottom" @click="cancelFocus(item, index)">
            <!-- followType 1 已关注 0 未关注 -->
            <!-- focusType 2 控件部分的关注列表显示 0 我关注的  1 关注我的-->
              <div class="status">
                <span v-if="item.followType === '0'">未关注</span>
                <span v-if="item.followType === '1'">已关注</span>
              </div>
              <div class="edit" v-if="!edit">
                <span v-if="item.followType === '1'">取消关注</span>
                <span v-if="item.followType === '0'">添加关注</span>
              </div>
          </div>
        </div>
      </Col>
    </Row>
    <div class="tc pt40 pb20" v-if="data.length">
      <Page :total="pages.total" @on-change="getNextPage" :page-size="pages.pageSize" :current="pages.pageNum"></Page>
    </div>
    <div class="tc pt30 pb20" v-else>
      <p>没有相关数据！</p>
    </div>
  </div>
</template>
<script>
  export default {
    props: {
      data: Array,
      edit: {
        type: Boolean,
        default: false
      },
      focusType: {
        type: String,
        default: '0'
      },
      defaultSel: {
        type: Array,
        default: () => {
          return []
        }
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
      }
    },
    methods: {
      // 点击进入会员门户
      goGate (account) {
        this.$toPortals(account)
        // this.$router.push(`/portals/index?uid=${account}`)
      },
      // 翻页
      getNextPage (e) {
        // this.pageNum = e
        this.$emit('on-init', e)
      },
      // 多选模式 选中
      handleCheck (item, index) {
        item.check = !item.check
        this.data.splice(index, 1, item)
        if (item.check) {
          this.defaultSel.push(item)
        } else {
          this.filterSel(this.defaultSel, item.id)
        }
      },
      // 过滤结果
      filterSel (data, id) {
        data.forEach((item, index) => {
          if (item.id === id) {
            data.splice(index, 1)
          }
        })
      },
      // 点击取消关注
      cancelFocus (item, index) {
        if (!this.edit) {
          if (item.followType === '1') {
            this.$emit('on-cancel', item, index)
          } else {
            if (item.account === this.$user.loginAccount) {
              this.$Message.warning('不能邀请自己为好友！')
            } else {
              this.$emit('on-cancel', item, index)
            }
          }
        }
      }
    }
  }

</script>

<style lang="scss" scoped>
.focus-management-layouts{
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
      .edit{
        display: none;
      }
      &:hover{
        background: #00C587;
        color: #fff;
        .status{
          display: none;
        }
        .edit{
          display: block;
        }
      }
    }
  }
  .member-list-check{
    border: 1px solid rgba(0,197,135,1);
  }
  .member-list-edit{
    position: relative;
    .check-box{
      width: 30px;
      height: 20px;
      position: absolute;
      top:0px;
      right: 0px;
      background: #D8D8D8;
      color: #C9C9C9;
      line-height: 20px;
      text-align: center;
      z-index: 99;
    }
    .isCheck {
      background: #00C587;
      color: #fff;
    }
    .member-list-bottom{
      cursor: pointer;
      &:hover{
        background: #F7F9FA;
        color: #AFB0B1;
        .status{
          display: block;
        }
        .edit{
          display: none;
        }
      }
    }
  }
}
</style>
