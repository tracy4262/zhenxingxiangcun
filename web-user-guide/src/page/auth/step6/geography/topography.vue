<template>
  <div class="pd20">
    <Title :title="title" edit :id="id" :yearId="yearId"></Title>
    <Form :label-width="100" label-position="left" class="pd20 mt40">
      <FormItem label="权限">
        <Switch class="ml20"  size="large" v-model="status" :disabled="true">
          <span slot="open">公开</span>
          <span slot="close">隐藏</span>
        </Switch>
      </FormItem>
      <FormItem label="选择地形">
       <div>
          <Select v-model="data.topographic" multiple filterable style="width:300px"  :disabled="true"  @on-open-change="terrainChange">
            <Option v-for="item in terrains" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
       </div>
        <Input :value="data.terrainPreview" class="mt20" :disabled="true" type="textarea" readonly :autosize="{minRows: 2,maxRows: 3}" ></Input>
      </FormItem>
      <FormItem label="选择地貌">
        <Select v-model="data.features" multiple filterable style="width:300px"  :disabled="true"  @on-open-change="landformChange">
            <Option v-for="item in landforms" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
        <Input :value="data.landformPreview"  class="mt20" :disabled="true" type="textarea" readonly :autosize="{minRows: 2,maxRows: 3}" ></Input>
      </FormItem>
      <FormItem label="平均海拔">
        <Input v-model="data.avg_altitude" style="width:300px;" @on-change="changePreview" :maxlength="20" :disabled="true">
        <span slot="append">米</span>
        </Input>
      </FormItem>
      <FormItem label="最高海拔">
        <Input v-model="data.max_altitude" style="width:300px;" @on-change="changePreview" :maxlength="20" :disabled="true">
        <span slot="append">米</span>
        </Input>
      </FormItem>
      <FormItem label="最低海拔">
        <Input v-model="data.min_alititude" style="width:300px;" @on-change="changePreview" :maxlength="20" :disabled="true">
        <span slot="append">米</span>
        </Input>
      </FormItem>

    </Form>
    <Title title="文字预览" class="mt40"></Title>
    <div class="pd20 tc pt30">
      <Input v-model="textPreview.text_preview" type="textarea" :autosize="{minRows: 4,maxRows: 10}" ></Input>
      <Button type="primary" @click="handleSave" class="mt40">保存</Button>
    </div>
  </div>
</template>

<script>
import Title from '../../components/title'
export default {
  props: {
    yearId: {
      type: String
    },
    id: {
      type: String
    },
    appId: {
      type: String
    }
  },
  components: {
    Title
  },
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
        topographic: [],
        terrainPreview: '',
        features: [],
        landformPreview: '',
        avg_altitude: '',
        avg_altitude_unit: '',
        max_altitude: '',
        max_altitude_unit: '',
        min_alititude: '',
        min_alititude_unit: '',
      },
      textPreview: {},
      title: '',
      status: true
    }
  },
  created() {
  },
  methods: {
    //初始化取数据
    handleInit () {
      this.$api.post('/member-reversion/physicalGeography/findTopographicFeature',{
        templateId: this.$template.id, user_id: this.$user.loginAccount, year_id: this.yearId, parent_id: this.id
      }).then(response => {
        if (response.code === 200) {
          this.title = response.data.topographicFeature_name
          let data = response.data.topographicFeature
          let arr = Object.keys(data)
          arr.length ? this.data = data :
          this.status = response.data.status
          if (!response.data.textPreview.text_preview) {
            response.data.textPreview.text_preview = `属于（）地形，属于（）地貌，平均海拔（）米，最高海拔（）米，最低海拔（）米。`
          }
          this.textPreview = response.data.textPreview
        }
      })
    },
    // 保存
    handleSave () {
      this.textPreview.is_complete = true
      let list = {
        topographicFeature: this.data,
        status: this.status,
        topographicFeature_name: this.title,
        textPreview: this.textPreview,
        sys_dict_id: this.id,
        yearId: this.yearId,
        templateId: this.$template.id,
        user_id: this.$user.loginAccount
      }
      this.$api.post('/member-reversion/physicalGeography/saveTopographicFeature', list).then(response => {
        if (response.code === 200) {
          this.$Message.success('保存成功')
          this.$emit('on-save')
          this.handleInit()
        }
      })
    },
    // 地形改变
    terrainChange () {
      if (this.data.topographic.length) {
        this.data.terrainPreview = `属于${this.data.topographic.join('、')}地形。`
      } else {
        this.data.terrainPreview = ''
      }
      this.changePreview()
    },
    // 地貌改变
    landformChange () {
      if (this.data.features.length) {
        this.data.landformPreview = `属于${this.data.features.join('、')}地貌。`
      } else {
        this.data.landformPreview = ''
      }
      this.changePreview()
    },
    // 文字预览
    changePreview () {
      let str = ''
      if (this.data.topographic.length) {
        str += `地形：属于${this.data.topographic.join('、')}地形，`
      }
      if (this.data.features.length) {
        str += `地貌：属于${this.data.features.join('、')}地貌，`
      }
      if (this.data.avg_altitude) {
        str += `平均海拔：${this.data.avg_altitude} 米，`
      }
      if (this.data.max_altitude) {
        str += `最高海拔：${this.data.max_altitude}米，`
      }
      if (this.data.min_alititude) {
        str += `最低海拔：${this.data.min_alititude} 米，`
      }
      if (str) {
        this.textPreview.text_preview = `${str.substring(0,str.length-1)}。`
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
