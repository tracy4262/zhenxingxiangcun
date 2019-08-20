<template>
  <div class="pd20">
    <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh"></Title>
    <Form :label-width="100" label-position="left" class="pd20 mt40">
      <FormItem label="权限">
        <Switch class="ml20"  size="large" v-model="status">
          <span slot="open">公开</span>
          <span slot="close">隐藏</span>
        </Switch>
      </FormItem>
      <FormItem label="矿产类别"> 矿产名称 </FormItem>
      <FormItem :label="item.minerals_class" v-for="(item, index) in data">
        <!-- 能源矿产 -->
        <Select v-model="item.minerals_name" multiple style="width:300px" @on-open-change="changePreview" v-if="index == 0">
            <Option v-for="list in energy" :value="list.minerals_name" :key="list.id">{{ list.minerals_name }}</Option>
        </Select>
        <!-- 金属矿产 -->
         <Select v-model="item.minerals_name" multiple style="width:300px" @on-open-change="changePreview" v-if="index == 1">
            <Option v-for="list in metal" :value="list.minerals_name" :key="list.id">{{ list.minerals_name }}</Option>
        </Select>
        <!-- 非金属矿产 -->
         <Select v-model="item.minerals_name" multiple style="width:300px" @on-open-change="changePreview" v-if="index == 2">
            <Option v-for="list in nonmetal" :value="list.minerals_name" :key="list.id">{{ list.minerals_name }}</Option>
        </Select>
        <!-- 水气矿产 -->
         <Select v-model="item.minerals_name" multiple style="width:300px" @on-open-change="changePreview" v-if="index == 3">
            <Option v-for="list in waterVapor" :value="list.minerals_name" :key="list.id">{{ list.minerals_name }}</Option>
        </Select>
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
      mineralNames: [
        {value: '山地', label: '山地'},
        {value: '盆地', label: '盆地'},
        {value: '丘陵', label: '丘陵'},
        {value: '平原', label: '平原'},
        {value: '高原', label: '高原'}
      ],
      textPreview: {},
      status: true,
      title: '地址矿产信息',
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
    this.templateId = this.$route.query.templateId
    // this.handleInit()
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
  watch: {
    data: {
      handler: function (newName, oldName) {
        if (this.flag) {
          this.changePreview()
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
      this.$api.post('/member-reversion/physicalGeography/findMineralsInfo',{
        user_id: this.$user.loginAccount,
        year_id: this.yearId,
        parent_id: this.id,
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          this.isLoading = false
          // this.title = response.data.mineralsInfo_name
          let data = response.data.mineralsInfo
          let arr = Object.keys(data)
          arr.length ? this.data = data : ''
          
          this.status = response.data.status
          this.textPreview = response.data.textPreview
          this.flag = true
        }
      })
    },
    // 保存
    handleSave () {
      this.isLoading = true
      this.textPreview.is_complete = '1'
      let list = {
        mineralsInfo: this.data,
        status: this.status,
        mineralsInfo_name: this.title,
        textPreview: this.textPreview,
        sys_dict_id: this.id,
        yearId: this.yearId,
        user_id: this.$user.loginAccount,
        templateId: this.templateId
      }
      this.$api.post('/member-reversion/physicalGeography/saveMineralsInfo', list).then(response => {
        if (response.code === 200) {
          this.$emit('on-save')
          this.$Message.success('保存成功')
          this.handleInit()
        }
      })
    },
    // 文字预览
    changePreview (e) { // 下拉面板关闭时触发
      if (!e) {
        let num = 0, str = ''
        this.data.forEach(e => {
          if (e.minerals_name.length) {
            num += e.minerals_name.length
            str += `其中：${e.minerals_class}有${e.minerals_name.join('、')}`
            str = `${str.substring(0,str.length)}，`
          }
        })
        if (str) {
          str = `${str.substring(0,str.length -1)}。`
        }
        if (num) {
          this.textPreview.text_preview = `所在地已发现矿产${num}种，${str}`
        } else {
          this.textPreview.text_preview = ''
        }
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
