<template>
  <div class="pd20">
    <Form label-position="left" >
      <FormItem label="矿产类别"> 矿产名称 </FormItem>
      <FormItem :label="item.minerals_class" v-for="(item, index) in data" :key="index">
        <!-- 能源矿产 -->
        <Select v-model="item.minerals_name" multiple style="100%"  v-if="index == 0">
            <Option v-for="list in energy" :value="list.minerals_name" :key="list.id">{{ list.minerals_name }}</Option>
        </Select>
        <!-- 金属矿产 -->
         <Select v-model="item.minerals_name" multiple style="100%"  v-if="index == 1">
            <Option v-for="list in metal" :value="list.minerals_name" :key="list.id">{{ list.minerals_name }}</Option>
        </Select>
        <!-- 非金属矿产 -->
         <Select v-model="item.minerals_name" multiple style="100%"  v-if="index == 2">
            <Option v-for="list in nonmetal" :value="list.minerals_name" :key="list.id">{{ list.minerals_name }}</Option>
        </Select>
        <!-- 水气矿产 -->
         <Select v-model="item.minerals_name" multiple style="100%"  v-if="index == 3">
            <Option v-for="list in waterVapor" :value="list.minerals_name" :key="list.id">{{ list.minerals_name }}</Option>
        </Select>
      </FormItem>
    </Form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      mineralNames: [
        {value: '山地', label: '山地'},
        {value: '盆地', label: '盆地'},
        {value: '丘陵', label: '丘陵'},
        {value: '平原', label: '平原'},
        {value: '高原', label: '高原'}
      ],
      textPreview: {},
      status: true,
      title: '地质矿产信息',
      metal: [],
      energy: [],
      nonmetal: [],
      waterVapor: [],
      data: [
        {minerals_class: '能源矿产', minerals_name: []},
        {minerals_class: '金属矿产', minerals_name: []},
        {minerals_class: '非金属矿产', minerals_name: []},
        {minerals_class: '水气矿产', minerals_name: []},
      ],
      templateId: '',
      flag: false,
      isLoading: true
    }
  },
  created() {
    // 金属矿产 1ef403f2bf594eedb1b452661f0218d6
    this.$api.post('/member-reversion/physicalGeography/findSysMineralsDict',{parent_id: '1ef403f2bf594eedb1b452661f0218d6'}).then(response => {
      if (response.code === 200) {
        this.metal = response.data
      }
    })
    // 能源矿产 7f6dbb5e8cde4ae0a0c893ae506a974a
    this.$api.post('/member-reversion/physicalGeography/findSysMineralsDict',{parent_id: '7f6dbb5e8cde4ae0a0c893ae506a974a'}).then(response => {
      if (response.code === 200) {
        this.energy = response.data
      }
    })
    // 非金属矿产 a0941a3282904c5f9c7a7458d8c7d68c
    this.$api.post('/member-reversion/physicalGeography/findSysMineralsDict',{parent_id: 'a0941a3282904c5f9c7a7458d8c7d68c'}).then(response => {
      if (response.code === 200) {
        this.nonmetal = response.data
      }
    })
    // 水气矿产 b521fb424da645e2a34742087c7c0bd5
    this.$api.post('/member-reversion/physicalGeography/findSysMineralsDict',{parent_id: 'b521fb424da645e2a34742087c7c0bd5'}).then(response => {
      if (response.code === 200) {
        this.waterVapor = response.data
      }
    })
  },
  methods: {
    initShow (list) {
      // 初始化页面置空
      if (list) {
        // this.data = Object.assign(this.data, list)
      } else {
        // 初始化页面置空
       this.data = [
          { minerals_class: '能源矿产', minerals_name: [] },
          { minerals_class: '金属矿产', minerals_name: [] },
          { minerals_class: '非金属矿产', minerals_name: [] },
          { minerals_class: '水气矿产', minerals_name: [] }
        ]
      }
    },
    save () {
      // 不需要验证直接返回 true
      return true
    }
  }
}
</script>

<style lang="less" scoped>

</style>
