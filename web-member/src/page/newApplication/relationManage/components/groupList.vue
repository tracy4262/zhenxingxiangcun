<template>
  <div >
      <Modal
        v-model="isShow"
        title="请选择分组"
        :mask-closable="false"
        width="360">
      <div>
        <Tree :data="data" :render="renderContent" ref="tree"></Tree>
      </div>
      <div slot="footer">
        <Button @click="onSaveTitle">确定</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
export default {
  prop: {
    init: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      title: '',
      templateId: '',
      isShow: false,
      data: [],
      chekcGroup: [],
    }
  },
  created() {
    // 查询模板id
    this.$api.post('/member-reversion/realStep/findEnableStep', {
      account: this.$user.loginAccount
    }).then(response => {
      if (response.code === 200 && response.data) {
        this.templateId = response.data.templateId
      }
    })  
  },
  methods: {
    init (type) {
      this.isShow = true
      this.getData(type)
    },
    // 根据模板id 查询分组
    getData (type) {
      let data = {
        templateId: this.templateId,
        account: this.$user.loginAccount
      }
      this.$api.post('/member/relationshipCircle/findGroupList',data).then(response => {
        if (response.code === 200) {
         if (type) {
            response.data.forEach((e, index) => {
              if (e.groupName === '工作圈') {
                this.data = [e]
              }
            })
         } else {
           this.data = response.data
         }
        }
      })
    },
    onSaveTitle () {
      if (this.chekcGroup.length) {
        this.$emit('on-save', this.chekcGroup)
      }else {
        this.$Message.warning('请选择分组！')
      }
    },
    renderContent (h, { root, node, data }) {
        return h('div',
            { 
              style: {
                position: 'relative',
                display: 'inline-block'
              },
              class: ['li'],
            },
            [
                h('span', [
                    h('Radio', {
                      style:{
                        fontSize: '14px'
                      },
                      props: {
                        value: data.check
                      },
                      nativeOn: {
                          click: () => {
                            root.forEach((e, index) => {
                              this.$set(root[index].node, 'check', false)
                            })
                            this.$set(data, 'check', true)
                            this.chekcGroup = [data]
                          }
                      }
                    },`${data.groupName}`)
                ]),
            ]);
      }
  }
}
</script>
