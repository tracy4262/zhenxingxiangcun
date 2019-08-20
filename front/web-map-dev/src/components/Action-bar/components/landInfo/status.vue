<template>
  <div class="pd20">
    <Form ref="formItem" :model="data" label-position="left" :rules="formItemInline">
      <FormItem label="类型编码" prop="LXBM">
        <Select v-model="data.LXBM" style="width:100" @on-change="typeChange($event)">
            <Option v-for="(item, index) in typeList" :value="item.value" :key="index">{{ item.value }}</Option>
        </Select>
      </FormItem>
      <FormItem label="类型名称" prop="LXMC">
          <Select v-model="data.LXBM" style="width:100%" @on-change="typeChange($event)">
            <Option v-for="(item, index) in typeList" :value="item.value" :key="index">{{ item.label }}</Option>
        </Select>
      </FormItem>
    </Form>
  </div>
</template>
<script>
export default {
  data () {
    return {
      title: '土地利用现状信息',
      typeList: [],
      formItemInline: {},
      data: {
        LXBM: '', // 编码类型
        LXMC: '', // 类型名称
      }
    }
  },
  created () {
      this.handleSelectList()
  },
    // 公式 1 平方米 = 0.0015亩 1平方米(㎡)=1e-6平方千米(km²)
  methods: {
    initShow (list) {
      // 初始化页面置空
      if (list) {
        for (const key in list) {
          if (this.data.hasOwnProperty(key)) {
            this.data[key] = list[key]
          }
        }
        console.log('this.data', this.data)
      } else {
        this.$refs.formItem.resetFields()
      }
    },
    save () {
      this.$refs['formItem'].validate(v => {
        this.$emit('on-save', v)
      })
    },
    // 类型编码或者类型名称发生改变的时候
    typeChange ($event) {
      this.typeList.forEach(e => {
        if (e.value == $event) {
          this.data.LXMC = e.label
        }
      })
    },
    // 取初始化下拉列表的数据
    handleSelectList () {
      this.$api.post('/member-reversion/landUse/dict', {
        type : this.type,
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          this.typeList = response.data
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>

</style>
