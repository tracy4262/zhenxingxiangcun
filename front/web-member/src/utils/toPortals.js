export function  toPortals (account) {
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
                            // console.log('userType', response.data.member_class)
                            if (response.data.member_class === '专家') {
                                // 专家门户
                                this.$router.push(`/expertPortal/index?uid=${account}&id=0`)
                            } else {
                              const member_class = response.data.member_class.join('/')
                              if (member_class === '1cb4efe86cf1499d83ae1138daa999c1/6a9a0d73a3a546b59ee10ab7a2398a0e/c9ef371731d34c3bba29d48c9bc87328/f7c6e9f45780413fa237b36f7384a511') {
                                  // 乡村门户
                                  this.$router.push(`/ruralPortal/index?uid=${account}&id=0`)
                              } else if (member_class === '1cb4efe86cf1499d83ae1138daa999c1/58e0d611a9804efa9817e1231326dcc6/07e0821fe8134a468c0f5be25fc5a3df') {
                                  // 农业龙头企业
                                  this.$router.push(`/farmHeadPortal/index?uid=${account}&id=0`)
                              } else if (member_class === '1cb4efe86cf1499d83ae1138daa999c1/58e0d611a9804efa9817e1231326dcc6/0610764e02b140d49d0fa326390be91a') {
                                  // 农民合作社
                                  this.$router.push(`/cooperativePortal/index?uid=${account}&id=0`)
                              } else {
                                  // 其余都进通用门户
                                  this.$router.push(`/portals/index?uid=${account}&id=0`)
                              }
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
        this.$Message.error('服务器异常！')
    })
  }

export function toMap () {
    window.open(`${window.location.origin}/map/edit`, '_banlk')
}
export function delMapData (name, id) {
    // 删除 地图数据
    var data = {
        name: name, // 图层名字
        features:[
            {
            op: 'Delete',
            id: id // id
            }
        ]
    }
    this.$api.mappost('/api/space/editFeatures', data).then(res => {
    })
}