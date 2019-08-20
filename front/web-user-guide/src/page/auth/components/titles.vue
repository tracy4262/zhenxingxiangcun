<template>
  <div class="user-auth-title mt10 mb10">
    <div class="vui-flex">
      <div style="color:#4a4a4a;">{{name}}</div>
      <div class="vui-flex-item subtitle">
        <span class="t-grey ml20" v-if="subTitle">{{subTitle}}</span>
        <a href="javascript:;" v-if="edit" class="ml20 t-grey" @click="onEdit">编辑名称</a>
      </div>
      <slot></slot>
    </div>

    <Modal
    v-model="editNameModel"
    title="编辑名称"
    class-name="vertical-center-modal"
    width="360" @on-ok="onSaveName">
      <div>
        <Input v-model="val" :maxlength="20" placeholder="名称不得超过20个汉字"></Input>
      </div>
    </Modal>
  </div>
</template>
<script>
export default {
  props: {
    titles: {
      type: Array,
      default: ''
    },
    index: {
      type: Number,
      default: 0
    },
    url: {
      type: String,
      default: '/member-reversion/perfect/updatePropertyStringInfo'
    },
    id: {
      type: String,
      default: ''
    },
    yearId: {
      type: String,
      default: ''
    },
    edit: {
      type: Boolean,
      default: false
    },
    subTitle: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      editNameModel: false,
      name: '',
      val: ''
    }
  },
  methods: {
    // 编辑名称
    onEdit () {
      this.editNameModel = true
    },
    onSaveName () {
      console.log('保存名称')
      if (this.val !== '') {
        this.titles[this.index] = this.val
        let list = {
          account: this.$user.loginAccount,
          templateId: this.$template.id,
          propertyName: this.titles,
          dictId: this.id,
          // yearId: this.yearId
        }
        this.$api.post(this.url, list).then(response =>{
          if (response.code === 200) {
            this.$Message.success('保存成功')
            this.name = this.val
          }
        })
      }
    }
  },
  watch: {
    titles: {
      handler (newValue, oldValue) {
        console.log('watch title', oldValue, newValue)
        this.name = newValue[this.index]
        this.val = newValue[this.index]
      },
      deep: true
    }
  },
  mounted() {
    this.name = this.title
    this.val = this.title
  }
}
</script>
<style lang="scss">
.user-auth-title{
  padding-left: 10px;
  border-left: 6px solid #00c587;
  position: relative;
  font-weight: 700;
  &:before{
    content: '';
    position: absolute;
    bottom: -5px;
    left: 10px;
    right: 0;
    height: 1px;
    background: #eee;
  }
  .subtitle{
    font-size: 12px;
    cursor: pointer;
  }
}
</style>
