<template>
<div>
  <!-- <Affix :offset-top="100"> -->
    <Card :padding="0" >
      <div class="pd10">
        <div class="h5 b ell">{{name}}</div>
        <!-- <a href="javascript:;" class="t-grey" @click="onEdit">编辑名称</a> -->
      </div>
      <Divider style="margin: 14px 0 24px" />
        <div
        class="vui-flex vui-flex-middle item"
        :class="{active: item.checked}"
        v-for="(item, index) in data"
        :key="index"
        @click="onCellClick(item, index)">
          <div class="vui-flex-item">
            <Tooltip class="block" placement="left" :content="item.title" theme="light" v-if="item.title.length > 8">
              <p class="ell">{{item.title}}</p>
            </Tooltip>
            <p v-else>{{item.title}}</p>
          </div>
          <!-- <p>({{item.status ? '已完成' : '待完善'}})</p> -->
        </div>
    </Card>

    <Modal
    v-model="editNameModel"
    title="编辑名称"
    class-name="vertical-center-modal"
    width="360">
      <div>
        <Input v-model="val" :maxlength="10" placeholder="名称不得超过10个汉字" />
      </div>
      <div slot="footer">
        <Button type="text" @click="cancel">取消</Button>
        <Button type="primary" @click="onSaveName">确定</Button>
      </div>
    </Modal>

  <!-- </Affix> -->
  
</div>
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
      val: '',
      templateId: ''
    }
  },
  created () {
    this.templateId = this.$route.query.templateId
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
      this.val = this.title
      this.editNameModel = true
    },
    onSaveName () {
      if (this.val !== '') {
        let list = {
            account: this.$user.loginAccount,
            appName: this.val,
            appId: this.appId,
            templateId: this.templateId
        }
        this.$api.post('/member-reversion/user/perfect/modifyModule', list).then(response => {
          if (response.code === 200) {
            this.$Message.success('保存成功')
            this.editNameModel = false
            this.$emit('handleEdit')
          }
        })
      } else {
        this.$Message.info('应用名称不能位空！')
      }
    },
    cancel () {
      this.editNameModel = false
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
