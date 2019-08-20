<template>
  <div class="pd20">
    <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh"></Title>
    <Form :label-width="100" label-position="left" class="pd20 mt40" ref="data" :rules="ruleInline" :model="data">
      <FormItem label="权限">
        <Switch class="ml20"  size="large" v-model="status">
          <span slot="open">公开</span>
          <span slot="close">隐藏</span>
        </Switch>
      </FormItem>
      <FormItem label="选择地形">
       <div>
          <Select v-model="data.topographic" multiple filterable clearable style="width:300px" @on-open-change="terrainChange">
            <Option v-for="item in terrains" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
       </div>
        <Input :value="data.terrainPreview" class="mt20" type="textarea" readonly :autosize="{minRows: 2,maxRows: 3}" ></Input>
      </FormItem>
      <FormItem label="选择地貌">
        <Select v-model="data.features" multiple filterable style="width:300px" @on-open-change="landformChange">
            <Option v-for="item in landforms" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
        <Input :value="data.landformPreview"  class="mt20" type="textarea" readonly :autosize="{minRows: 2,maxRows: 3}" ></Input>
      </FormItem>
      <FormItem label="平均海拔" prop="avg_altitude">
        <Input v-model="data.avg_altitude" style="width:300px;" @on-change="changePreview" :maxlength="20" >
        <span slot="append">米</span>
        </Input>
      </FormItem>
      <FormItem label="最高海拔" prop="max_altitude">
        <Input v-model="data.max_altitude" style="width:300px;" @on-change="changePreview" :maxlength="20" >
         <span slot="append">米</span>
        </Input>
      </FormItem>
      <FormItem label="最低海拔" prop="min_alititude">
        <Input v-model="data.min_alititude" style="width:300px;" @on-change="changePreview" :maxlength="20" >
         <span slot="append">米</span>
        </Input>
      </FormItem>

    </Form>
    <Title title="文字预览" class="mt40"></Title>
    <div class="pd20 tc pt30">
      <Input v-model="textPreview.text_preview" type="textarea" :autosize="{minRows: 4,maxRows: 10}" ></Input>
      <Button type="primary" v-if="isLoading" class="mt40">保存</Button>
      <Button type="primary" v-else @click="handleSave" class="mt40">保存</Button>
    </div>
  </div>
</template>

<script>
import Title from '../../components/title'
import {isMoney3} from '~utils/validate'
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
      ruleInline: {
        min_alititude: [
          {validator: isMoney3, trigger: 'blur'},
        ],
        max_altitude: [
          {validator: isMoney3, trigger: 'blur'},
        ],
        avg_altitude: [
          {validator: isMoney3, trigger: 'blur'},
        ]
      },
      textPreview: {},
      title: '地形地貌信息',
      status: true,
      units: [],
      templateId: '',
      flag: false,
      isLoading: true
    }
  },
  created() {
    this.templateId = this.$route.query.templateId
  },
  watch: {
    data: {
      handler: function (newName, oldName) {
        if (this.flag) {
          this.terrainChange()
          this.landformChange()
        }
      },
      deep: true
    }
  },
  methods: {
    initTitle () {
      this.$api.post('/member-reversion/user/perfect/findTableHead', {
        account: this.$user.loginAccount,
        yearId: this.yearId,
        dictId: this.id,
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          if (response.data.propertyName) {
            this.title = response.data.propertyName
          }
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    //初始化取数据
    handleInit () {
      this.$api.post('/member-reversion/physicalGeography/findTopographicFeature', {
        user_id: this.$user.loginAccount,
        year_id: this.yearId,
        parent_id: this.id,
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          this.isLoading = false
          // this.title = response.data.topographicFeature_name
          let data = response.data.topographicFeature
          let arr = Object.keys(data)
          arr.length ? this.data = data : ''
          this.status = response.data.status
          this.textPreview = response.data.textPreview
          if (this.data.topographic.length) {
            this.data.terrainPreview = `属于${this.data.topographic.join('、')}地形。`
          } else {
            this.data.terrainPreview = ''
          }
          if (this.data.features.length) {
            this.data.landformPreview = `属于${this.data.features.join('、')}地貌。`
          } else {
            this.data.landformPreview = ''
          }
          this.flag = true
        }
      })
    },
    // 保存
    handleSave () {

      this.$refs['data'].validate(v => {
          if (v) {
            this.isLoading = true
            this.textPreview.is_complete = '1'
            let list = {
              topographicFeature: this.data,
              status: this.status,
              topographicFeature_name: this.title,
              textPreview: this.textPreview,
              sys_dict_id: this.id,
              yearId: this.yearId,
              user_id: this.$user.loginAccount,
              templateId: this.templateId
            }
            this.$api.post('/member-reversion/physicalGeography/saveTopographicFeature', list).then(response => {
              if (response.code === 200) {
                this.$Message.success('保存成功')
                this.$emit('on-save')
                this.handleInit()
              }
            })
          } else {
            this.$Message.error('请核对表单信息')
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
        this.data.landformPreview = `属于${this.data.features.join('、')}。`
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
        str += `地貌：属于${this.data.features.join('、')}，`
      }
      if (this.data.avg_altitude) {
        str += `平均海拔：${this.data.avg_altitude} ${this.data.avg_altitude_unit}米，`
      }
      if (this.data.max_altitude) {
        str += `最高海拔：${this.data.max_altitude} ${this.data.max_altitude_unit}米，`
      }
      if (this.data.min_alititude) {
        str += `最低海拔：${this.data.min_alititude} ${this.data.min_alititude_unit}米，`
      }
      if (str) {
        this.textPreview.text_preview = `${str.substring(0,str.length-1)}。`
      }
    },
    leftRefresh () {
        this.$emit('left-refresh')
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
