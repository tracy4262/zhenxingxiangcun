<template>
  <div class="pd20">
    <Form label-position="left"  ref="formItem" :rules="ruleInline" :model="data">
      <FormItem label="选择地形" prop="XZDX">
          <Select v-model="data.XZDX" multiple filterable clearable style="100%" >
            <Option v-for="item in terrains" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
      </FormItem>
      <FormItem label="选择地貌" prop="XZDM">
        <Select v-model="data.XZDM" multiple filterable style="100%" >
            <Option v-for="item in landforms" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
      </FormItem>
      <FormItem label="平均海拔" prop="PJHB">
        <Input v-model="data.PJHB" style="100%;" :maxlength="20" >
        <span slot="append">米</span>
        </Input>
      </FormItem>
      <FormItem label="最高海拔" prop="ZGHB">
        <Input v-model="data.ZGHB" style="100%;" :maxlength="20" >
         <span slot="append">米</span>
        </Input>
      </FormItem>
      <FormItem label="最低海拔" prop="min_alititude">
        <Input v-model="data.min_alititude" style="100%;" :maxlength="20" >
         <span slot="append">米</span>
        </Input>
      </FormItem>
    </Form>
  </div>
</template>

<script>
import {isMoney3} from '@/utils/validate'
export default {
  data () {
    return {
      terrains: [
        {value: '山地', label: '山地'},
        {value: '盆地', label: '盆地'},
        {value: '丘陵', label: '丘陵'},
        {value: '平原', label: '平原'},
        {value: '高原', label: '高原'}
      ],
      landforms: [ // 后期甲方提供
        {value: '丹霞地貌', label: '丹霞地貌'},
        {value: '喀斯特地貌', label: '喀斯特地貌'},
        {value: '海岸地貌', label: '海岸地貌'},
        {value: '海底地貌', label: '海底地貌'},
        {value: '风积地貌', label: '风积地貌'},
        {value: '风蚀地貌', label: '风蚀地貌'},
        {value: '河流地貌', label: '河流地貌'},
        {value: '冰川地貌', label: '冰川地貌'},
        {value: '冰缘地貌', label: '冰缘地貌'}
      ],
      data: {
        XZDX: [],
        XZDM: [],
        PJHB: '',
        ZGHB: ''
      },
      ruleInline: {
        ZGHB: [
          {validator: isMoney3, trigger: 'blur'}
        ],
        PJHB: [
          {validator: isMoney3, trigger: 'blur'}
        ]
      },
      title: '地形地貌信息'
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
            if (key == 'XZDX' || key == 'XZDM') {
              this.data[key] = list[key].split(',')
            } else {
              this.data[key] = list[key]
            }
          }
        }
      } else {
        // 初始化页面置空
        this.$refs.formItem.resetFields()
      }
    },
    save () {
      for (const key in this.data) {
        if (key == 'XZDX' || key == 'XZDM') {
          this.data[key] = this.data[key].join(',')
        }
      }
      return true
      // this.$refs['formItem'].validate(v => {
      //   if (!v) {
      //     return false
      //   }
      // })
    }
  }
}
</script>

<style lang="less" scoped>

</style>
