<template>
  <div class="bg-white member-file-list">
    <!-- 我的档案列表 -->
    <Card :bordered="false">
      <div class="tc mt20">
        <img class="user-img" :src="avatar" width="80px" height="80px" v-if="avatar !== ''">
        <img class="user-img" src="../../../img/default_header.png" width="80" height="80" v-else>
        <p class="mt15 user-name">
          {{ displayName }}
          <img src="../../../img/tuijian-vip.png">
        </p>
        <p v-if="editSignature" class="mt20 user-signature" :title="signature">
          {{ signature }}
          <Icon
            type="ios-create-outline"
            size="20"
            @click="changeSignature"
            style="cursor: pointer;"
          />
        </p>
        <Input
          v-else
          type="text"
          class="mt20"
          v-model.trim="signature"
          @on-blur="saveSignature"
          autofocus
          :maxlength="50"
        />
        <p class="mt10 user-signature">农事无忧ID：{{ nswyId }}</p>
      </div>
      <Row class="mt20">
        <Col span="12" class="tc" style="border-right: 1px solid #E8E8E8;">
          <div class="link-a" @click="myData">我的资料</div>
        </Col>
        <Col span="12" class="tc">
            <div class="link-a" @click="myPortal">我的门户</div>
        </Col>
      </Row>
    </Card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      displayName: "暂未实名",
      avatar: "",
      signature: "暂无签名！",
      editSignature: true,
      nswyId: "",
      flag: "",
      templateId: ""
    };
  },
  created() {
    this.init();
    this.checkAuth();
  },
  methods: {
    init() {
      this.$api
        .post("/member/login/findCurrentUser", {
          account: this.$user.loginAccount
        })
        .then(response => {
          console.log("res123", response);
          if (response.data.displayName) {
            this.displayName = response.data.displayName;
          }
          if (response.data.avatar) {
            this.avatar = response.data.avatar;
          }
          if (response.data.signaTure) {
            this.signature = response.data.signaTure;
          }
          if (response.data.nswyIdModel) {
            this.nswyId = response.data.nswyIdModel;
          }
        });
    },
    //修改签名
    changeSignature() {
      this.editSignature = !this.editSignature;
    },
    saveSignature() {
      if (this.signature && this.signature !== "") {
        this.$api
          .get(
            "/member/login/editSignature?signature=" +
              this.signature +
              "&account=" +
              this.$user.loginAccount
          )
          .then(response => {
            if (response.code === 200) {
              this.$Message.success("修改成功！");
              this.editSignature = !this.editSignature;
            } else {
              this.$Message.error("修改失败！");
            }
          });
      }
    },
    myData() {
      if (this.flag >= 6.4) {
        this.$router.push({
          path: `/auth/step7`,
          query: {
            templateId: this.templateId
          }
        });
      } else {
        this.$Message.error('请先完成实名认证！')
      }
    },
    checkAuth() {
      this.$api
        .post("/member-reversion/realStep/findEnableStep", {
          account: this.$user.loginAccount
        })
        .then(response => {
          // console.log("check auth response", response);
          if (response.code === 200) {
            this.flag = response.data.step;
            this.templateId = response.data.templateId;
            // if (response.data.step === "6.4") {
            //   this.$router.push({
            //     path: `/auth/step7`,
            //     query: {
            //       templateId: response.data.templateId
            //     }
            //   });
            // }
          }
        });
    },
    myPortal () {
      // 查询用户是否实名
      this.$toPortals(this.$user.loginAccount)
    }
  }
};
</script>
<style lang="scss">
.member-file-list {
  color: #4a4a4a;
  .user-img {
    border-radius: 40px;
  }
  .user-name {
    font-size: 18px;
    font-weight: 700;
    font-family: PingFangSC-Semibold;
  }
  .user-signature {
    font-size: 12px;
    font-family: PingFangSC-Regular;
  }
  .link-a {
    display: inline-block;
    padding: 5px 15px;
    color: #4a4a4a;
    font-family: PingFangSC-Regular;
    &:hover {
      color: #00c587;
      cursor: pointer;
    }
  }
}
</style>
