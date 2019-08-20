
<template>
 <!-- 生物资源信息 -->
  <div class="pd20">
    <Form  label-position="left" ref="formItem" :model="data">
      <FormItem label="野生动物名称">
        <vuiSpecies :values="data.YSDWMC" ref="vuiSpecies" @on-save="onSavePlantLists" :num="99999"></vuiSpecies>
      </FormItem>
      <FormItem label="野生植物名称">
        <vuiSpecies :values="data.YSZWMC" ref="vuiSpecies" @on-save="onSaveAnimalLists" :num="999999"></vuiSpecies>
      </FormItem>
    </Form>
    <div class="tc">
      <Button type="primary"  @click="handleSave" class="mt40">保存</Button>
    </div>
  </div>
</template>

<script>
import vuiSpecies from '@/components/vui-species'
export default {
  components: {
    vuiSpecies
  },
  data () {
    return {
      data: {
        YSDWMC: '', // 动物
        YSZWMC: '' // 植物
      },
      plantLists: '', // 所选择的 植物 名称
      animalLists: '', // 所选择的 动物 名称
      title: '生物资源信息'
    }
  },
  created() {
  },
  methods: {
    initShow (list) {
      // 初始化页面置空
      if (list) {
        for (const key in list) {
          if (this.data.hasOwnProperty(key)) {
            this.data[key] = list[key]
          }
        }
      } else {
        this.$refs.formItem.resetFields()
      }
    },
    save () {
      return true
    },
    // 野生动物名称
    onSavePlantLists (e) {
      this.data.YSDWMC = e
    },
    // 野生植物名称
    onSaveAnimalLists (e) {
      this.data.YSZWMC= e
    }
  }
}
</script>

<style lang="less" scoped>

</style>
