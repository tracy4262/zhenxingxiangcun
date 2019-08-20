<template>
  <Affix >
    <Card :padding="0" shadow>
      <div class="pd10">
        <div class="h5 b ell">{{name}}</div>
        <a href="javascript:;" class="t-grey" @click="onEdit">编辑名称</a>
      </div>
      <Divider style="margin: 14px 0 24px" />
        <div
        class="vui-flex vui-flex-middle item"
        :class="{active: item.checked}"
        v-for="(item, index) in data"
        @click="onCellClick(item, index)">
          <div class="vui-flex-item">
            <Tooltip class="block" placement="left" :content="item.title" theme="light" v-if="item.title.length > 8">
              <p class="ell">{{item.title}} {{item.id}}</p>
            </Tooltip>
            <p v-else>{{item.title}}</p>
          </div>
          <p>({{item.status ? '已完成' : '待完善'}})</p>
        </div>
    </Card>

    <Modal
    v-model="editNameModel"
    title="编辑名称"
    class-name="vertical-center-modal"
    width="360" @on-ok="onSaveName">
      <div>
        <Input v-model="val" :maxlength="10" placeholder="名称不得超过10个汉字"></Input>
      </div>
    </Modal>

  </Affix>
</template>

<script>
export default {
  props: {
    title: String,
    id: String,
    data: {
      type: Array,
      default () {
        return []
      }
    },
    appId: {
      type: String
    }
  },
  data () {
    return {
      editNameModel: false,
      name: '',
      val: ''
    }
  },
  watch: {
    title: {
      handler (newValue, oldValue) {
        console.log('watch title', oldValue, newValue)
        this.name = newValue
        this.val = newValue
      },
      deep: true
    }
  },
  methods: {
    onCellClick (d, index) {
      this.data.forEach(item => item.checked = false)
      d.checked = true
      this.$emit('on-click', d.name, d, index)
    },
    onEdit () {
      this.editNameModel = true
    },
    onSaveName () {
      if (this.val !== '') {
        let list = {
            account: this.$user.loginAccount,
            templateId: this.$template.id,
            appName: this.val,
            appId: this.appId
        }
        this.$api.post('/member-reversion/perfect/modifyModule', list).then(response => {
          if (response.code === 200) {
            this.$Message.success('保存成功')
            this.$emit('handleEdit')
          }
        })
      }
    }
  },
  mounted() {
    this.name = this.title
    this.val = this.title
  }
}
</script>

<style lang="scss" scoped>
.item{
  cursor: pointer;
  padding: 6px 15px;
  &:hover{
    background: #f8f8f8;
  }
}
.active{
  color: #00C587;
  &,&:hover{background: #e4fff6;}
}
</style>
