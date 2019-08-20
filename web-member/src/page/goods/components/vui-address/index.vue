<template>
  <Row class="vui-address-list" :style="{display: edit ? '' : 'flex', 'flex-wrap': edit ? '' : 'nowrap'}">
    <Col span="6" v-for="(item, index) in data" :key="index" class="pd10">
    <Card class="card mb10" @click.native="handleSelect(item, index)">
      <transition name="fade">
        <span class="triangle" v-if="item.isDefault"> <Icon type="md-checkmark" v-if="item.isDefault"/></span>
      </transition>
      <template v-if="edit">
        <Tag color="primary" v-if="!item.isDefault" class="set-default" @click.native="handleSetDef(item)">设为默认</Tag>
      </template>

      <div class="item vui-flex vui-flex-middle">
        <Icon type="ios-person" class="t-grey mr10"></Icon>
        <div class="vui-flex-item">{{item.linkman}}</div>
      </div>
      <div class="item vui-flex vui-flex-middle" style="height: 53px;">
        <Icon type="ios-pin" class="t-grey mr10"></Icon>
        <div class="vui-flex-item">
          <p class="ell-2" :title="`${item.addArea}${item.addDetail}`">{{item.addArea}} {{item.addDetail}}</p>
        </div>
      </div>
      <div class="item vui-flex vui-flex-middle">
        <Icon type="ios-call" class="t-grey mr10"></Icon>
        <div class="vui-flex-item">{{item.mobile | filterPhone}}</div>
        <div class="oper" v-if="edit">
          <Button type="text" size="small" shape="circle" icon="md-create" class="mr10" @click="handleEdit(index)"></Button>
          <Poptip transfer confirm title="你确定要删除当前地址吗？" @on-ok="handleDel(item, inedx)" >
            <Button type="text" size="small" shape="circle" icon="md-trash"></Button>
          </Poptip>
        </div>
      </div>

    </Card>
    </Col>
  </Row>
</template>

<script>
export default {
  props: {
    data: Array,
    edit: {
      type: Boolean,
      default: true
    }
  },
  components: {},
  data () {
    return {      
      account: '',
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
    }
  },
  created () {
    this.account = this.loginUser.loginAccount
  },
  methods: {
    handleSelect (item, index) {
      if (!this.edit) {
        this.data.forEach(child => { child.isDefault = false })
        item.isDefault = true
        this.$emit('on-select', item)
      }
    },
    // 设置默认
    handleSetDef (item) {
      console.log(item)
      this.$api.post('/nswy-portal-service/shop/address/update/default', {account: this.account, id: item.id}).then(response => {
        if ((response.code === 200)) {
          this.data.forEach(child => { child.isDefault = false })
          item.isDefault = true
          this.$Message.success('设置成功')
        } else {
          this.$Message.error('设置失败')
        }
      })
    },
    // 删除
    handleDel (item,index) {
      this.$api.post('/nswy-portal-service/shop/address/delete', {account: this.account, id: item.id}).then(response => {
        if ((response.code === 200)) {
          this.data.splice(index, 1)
          if (item.isDefault) {
            this.data[0].isDefault = true
          }
          this.$Message.success('删除成功')
        } else {
          this.$Message.error('删除失败')
        }
      })
    },
    handleEdit (index) {
      this.$emit('on-edit', index)
    }
  },
  filters: {
    filterPhone (val) {
      return `${val.substr(0, 3)}*****${val.substr(8)}`
    }
  },
  mounted () {
    this.data.forEach(child => {
      if (child.isDefault && !this.edit) {
        this.$emit('on-select', child)
      }
    })
  }
}
</script>

<style lang="scss" scoped>
.vui-address-list {
  overflow: auto;
  font-size: 14px;
  .card {
    background: url(../../../../img/address-bg.png) center repeat-x;
    background-size: 100% 100%;;
    overflow: hidden;
    height: 158px;
    .item {
      &:not(:last-child) {
        padding-bottom: 10px;
        border-bottom: 1px dotted #dddee1;
        margin-bottom: 10px;
      }
    }
    .set-default {
      position: absolute;
      top: -30px;
      right: 5px;
      border-radius: 0;
    }
    .oper {
      position: absolute;
      bottom: -30px;
      right: 15px;
    }
    &:hover {
      .set-default {
        top: -2px;
        transition: top 0.3s;
      }
      .oper {
        bottom: 15px;
        transition: bottom 0.3s;
      }
    }
  }
  .triangle {
    border-radius: 4px 0 0 0;
    overflow: hidden;
    width: 30px;
    height: 30px;
    padding-left: 3px;
    padding-top: 2px;
    &,
    &:before,
    &:after {
      position: absolute;
      top: 0;
      left: 0;
    }
    &:before {
      content: '';
      border-style: solid;
      border-width: 30px 30px 0 0;
      border-color: #00c587 transparent transparent transparent;
    }
    .ivu-icon-md-checkmark{
      position: absolute;
      color: #fff;
      font-weight: 700;
    }
    // &:after {
    //   left: 4px;
    //   top: 0;
    //   font-family: Ionicons;
    //   content: '\F121';
    //   color: #fff;
    //   font-size: 12px;
    // }
  }
}
</style>
