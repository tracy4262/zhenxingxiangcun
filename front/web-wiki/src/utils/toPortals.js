export function toPortals (account) {
  // 查询用户是否实名
  this.$api.post('/member-reversion/realStep/findEnableStep', {
    account: account
  }).then(response => {
    if (response.code === 200) {
      if (response.data) {
        let step = Number(response.data.step)
        if (step >= 7) {
          this.$api.post('/member-reversion/user/realCertification/findMemberClassByAccount', {
            account: account
          }).then(response => {
            if (response.code === 200) {
              console.log('userType', response.data.member_class)
              if (response.data.member_class === '专家') {
                // 专家门户
                this.$router.push(`/expertPortal/index?uid=${account}&id=0`)
              } else if (response.data.member_class === '法人/其他法人') {
                // 乡村门户
                this.$router.push(`/ruralPortal/index?uid=${account}&id=0`)
              } else if (response.data.member_class === '法人/企业法人/农业龙头企业') {
                // 农业龙头企业
                this.$router.push(`/farmHeadPortal/index?uid=${account}&id=0`)
              } else if (response.data.member_class === '法人/企业法人/农民合作社') {
                // 农民合作社
                this.$router.push(`/cooperativePortal/index?uid=${account}&id=0`)
              } else {
                // 其余都进通用门户
                this.$router.push(`/portals/index?uid=${account}&id=0`)
              }
            }
          })
        } else {
          this.$Message.error('请完成实名认证！')
        }
      } else { // 说明没走过认证
        this.$Message.error('用户未实名')
      }
    }
  }).catch(error => {
    console.log(error)
    this.$Message.error('服务器异常！')
  })
}
