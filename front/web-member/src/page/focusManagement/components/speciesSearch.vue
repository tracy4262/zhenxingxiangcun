<template>
  <div>
    <Row>
      <Col span="12">
        <Input placeholder="请输入..." style="width: 267px;" v-model="keyWord" @on-enter="onSaerch" @on-change="onChange">
            <Icon type="ios-search" slot="suffix" @click="onSaerch"/>
        </Input>
      </Col>
      <Col span="12" class="tr">
        <Button type="primary" icon="md-add" v-if="!edit" class="mr10" @click="addFocus">添加关注</Button>
        <Button v-if="!edit"  @click="handleEdit">批量操作</Button>

        <Button type="primary" v-if="edit && focusType === '0'" class="mr10"  @click="cancelFocus">取消关注</Button>
        <Button type="primary" v-if="edit && focusType == '1'" class="mr10"  @click="onAdd">添加关注</Button>
        <Button v-if="edit" @click="handleEdit">退出批量操作</Button>
      </Col>
    </Row>
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
      followValue: {
        type: String,
        default: ''
      }
    },
    data () {
      return {
        keyWord: ''
      }
    },
    watch: {
      followValue: {
        handler(curVal, oldVal) {
          console.log('curVal', curVal, 'oldVal', oldVal)
          this.keyWord = curVal
        },
        deep: true
      }
    },
    methods: {
      onChange () {
        this.$emit('on-change', this.keyWord)
      },
      onSaerch () {
        console.log('搜索')
        this.$emit('on-search', this.keyWord)
      },
      // 点击添加关注
      addFocus () {
        this.$emit('on-add')
      },
      // 点击取消关注
      cancelFocus () {
        this.$emit('on-cancel')
      },
      onAdd () {
        this.$emit('on-focus')
      },
      // 切换多选状态
      handleEdit () {
        this.$emit('on-edit')
      },
    }
  }

</script>