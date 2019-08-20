<template>
  <div>
    <Row>
       <Col span="7" v-if="showType">
        <Cascader :data="types" :load-data="loadData"  clearable
            change-on-select
            style="width: 220px;"
            @on-change="handleGetData">
           <Input placeholder="请选择" v-model="type" icon="ios-arrow-down" readonly/>
        </Cascader>
      </Col>
      <Col span="7">
        <Input placeholder="请输入" style="width: 220px;" v-model="keyWord" @on-change="onChange">
        </Input>
      </Col>
      <Col :span="showType ? '3' : '10'">
        <Button icon="ios-search" class="mr10" @click="onSaerch">查询</Button>
      </Col>
      <Col span="7" class="tr">
        <Button icon="md-add" v-if="!edit && focusType == '1'" class="mr10" @click="addSpecies">新增</Button>
        <Button v-if="!edit"  @click="handleEdit">批量操作</Button>
        <!-- focusType 0收藏 1新增 -->
        <Button type="primary" v-if="edit && focusType == '0'" class="mr10"  @click="cancelFocus">取消收藏</Button>
        <Button type="primary" v-if="edit && focusType == '1'" class="mr10"  @click="del">删除</Button>
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
      showType: {
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
      },
      followType: {
        type: String,
        default: ''
      },
      path: {
        type: String,
        default: 'addSpecies'
      }
    },
    data () {
      return {
        keyWord: '',
        type: '',
        types: [
          {
            label: '动物',
            value: '0',
            classId: '',
            loading: false,
            checked: false,
            children: []
          },
          {
            label: '植物',
            value: '1',
            classId: '',
            loading: false,
            checked: false,
            children: []
          }
        ],
      }
    },
    created() {
      this.keyWord = this.followValue
      this.type = this.followType
    },
    watch: {
      followValue: {
        handler(curVal, oldVal) {
          console.log('curVal', curVal, 'oldVal', oldVal)
          this.keyWord = curVal
        },
        deep: true
      },
      followType: {
        handler(curVal, oldVal) {
          console.log('curVal', curVal, 'oldVal', oldVal)
          this.type = curVal
        },
        deep: true
      }
    },
    methods: {
      inputClear () {
        this.handleGetData([], [])
      },
      loadData (item, callback) {
        item.loading = true
        this.$api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
          item.loading = false
          var d = res.data
          d.forEach(child => {
            child.checked = false
            child.label = child.className
            child.value = child.indexid
          })
          item.children = d
          callback()
        })
      },
      handleGetData (value, selectedData) {
        let arr = []
        let str = ''
        selectedData.forEach(e => {
          arr.push(e.label)
        })
        str = arr.join('/')
        this.$emit('on-type-change', str)
      },
      onChange () {
        this.$emit('on-change', this.keyWord)
      },
      onSaerch () {
        this.$emit('on-search', {keyWord: this.keyWord, type: this.type})
      },
      // 点击添加关注
      addSpecies () {
        this.$router.push(`/nameLibrary/${this.path}`)
      },
      // 点击取消关注
      cancelFocus () {
        this.$emit('on-cancel')
      },
      del () {
        this.$emit('on-del')
      },
      // 切换多选状态
      handleEdit () {
        this.$emit('on-edit')
      },
    }
  }

</script>
