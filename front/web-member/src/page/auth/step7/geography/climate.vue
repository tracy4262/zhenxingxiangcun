
<template>
 <!-- 气候信息 -->
  <div class="pd20 vui-climate">
    <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh"></Title>
    <Form :label-width="100" label-position="left" class="pd20 mt40">
        <FormItem label="权限">
          <Switch class="ml20"  size="large" v-model="status">
            <span slot="open">公开</span>
            <span slot="close">隐藏</span>
          </Switch>
        </FormItem>
        <FormItem label="气候类型">
            <Select multiple style="width:360px;" v-model="data.climate_class" @on-change="onChange">
                <Option v-for="item in genders" :value="item.label" :key="item.label">{{ item.label }}</Option>
            </Select>
            <Input  type="textarea" :value="data.climate_class_type"  readonly :autosize="{minRows: 2,maxRows: 3}" @on-change="changePreview" class="mt20"></Input>
        </FormItem>
         <FormItem label="全年总辐射量">
            <Input style="width:150px" v-model="data.radiation_dose" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">千卡/平方厘米</span>
        </FormItem>
        <FormItem label="全年平均日照时间">
            <Input style="width:150px" v-model="data.sunshine_time[0]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">
              到</span> 
            <Input style="width:150px" v-model="data.sunshine_time[1]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">小时</span>
        </FormItem>
        <FormItem label="年平均气温">
            <Input style="width:150px" v-model="data.average_temperature[0]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">到</span> 
            <Input style="width:150px" v-model="data.average_temperature[1]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">℃</span>
        </FormItem>
        <FormItem label="≥10℃年积温">
            <Input style="width:150px" v-model="data.accumulated_temperature[0]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">到</span> 
            <Input style="width:150px" v-model="data.accumulated_temperature[1]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">℃</span>
        </FormItem>
        <FormItem label="日温差">
            <Input style="width:150px" v-model="data.diurnal_temperature_difference[0]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">到</span> 
            <Input style="width:150px" v-model="data.diurnal_temperature_difference[1]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">℃</span>
        </FormItem>
        <FormItem label="极端最高气温">
            <Input style="width:150px" v-model="data.max_temperature" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">℃</span> 
            <span class="pd30">维持的日数</span> <Input style="width:150px" v-model="data.max_days" @on-change="changePreview" :maxlength="20"></Input>
        </FormItem>
        <FormItem label="极端最低气温">
            <Input style="width:150px"  v-model="data.min_temperature" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">℃</span> 
            <span class="pd30">维持的日数</span> <Input style="width:150px"  v-model="data.min_days" @on-change="changePreview" :maxlength="20"></Input>
        </FormItem>
        <FormItem label="极端最高气温多年平均值气温">
            <Input style="width:150px" v-model="data.max_avg_temperature" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">℃</span>
        </FormItem>
         <FormItem label="极端最低气温多年平均值气温">
            <Input style="width:150px" v-model="data.min_avg_temperature" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">℃</span>
        </FormItem>
        <FormItem label="无霜期">
            <Input style="width:150px" v-model="data.no_frost_date[0]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">到</span> 
            <Input style="width:150px" v-model="data.no_frost_date[1]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">天</span>
        </FormItem>
        <FormItem label="年平均降水量">
            <Input style="width:150px" v-model="data.avg_precipitation[0]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">到</span> 
            <Input style="width:150px" v-model="data.avg_precipitation[1]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">mm</span>
        </FormItem>
        <FormItem label="年平均蒸发量">
            <Input style="width:150px" v-model="data.avg_vaporization[0]" @on-change="changePreview" :maxlength="20"></Input>  <span class="pd20">到</span> 
            <Input style="width:150px" v-model="data.avg_vaporization[1]" @on-change="changePreview" :maxlength="20"></Input> <span class="pd20">mm</span>
        </FormItem>
        
        <FormItem label="年平均降水日">
            <Input style="width:150px" v-model="data.avg_precipitation_day[0]" @on-change="changePreview" :maxlength="20"></Input>  <span class="pd20">到</span> 
            <Input style="width:150px" v-model="data.avg_precipitation_day[1]" @on-change="changePreview" :maxlength="20"></Input><span class="pd20">天</span>
        </FormItem>
        <FormItem label="多年平均干燥度">
            <Input style="width:360px" v-model="data.dryness" @on-change="changePreview" :maxlength="20"></Input>
        </FormItem>
        <FormItem label="多年平均湿润度">
            <Input style="width:360px" v-model="data.wetness" @on-change="changePreview" :maxlength="20"></Input>
        </FormItem>
        <FormItem label="降水量最集中期">
            <Input style="width:150px" v-model="data.precipitation_period[0]" @on-change="changePreview" :maxlength="20"></Input>  <span class="pd20">到</span> 
            <Input style="width:150px" v-model="data.precipitation_period[1]" @on-change="changePreview" :maxlength="20"></Input> 月
        </FormItem>
        
        <FormItem label="自然灾害">
            <Input type="textarea" v-model="data.natural_disaster" @on-change="changePreview" :autosize="{minRows: 4,maxRows: 6}" :maxlength="200"></Input>
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
      genders: [
        {label: '热带季风', value: '热带季风'},
        {label: '亚热带季风', value: '亚热带季风'},
        {label: '温带季风', value: '温带季风'},
        {label: '高原山地', value: '高原山地'},
        {label: '热带大陆性', value: '热带大陆性'},
        {label: '热带雨林', value: '热带雨林'},
      ],
      data: {
        climate_class: [], // 气候类型
        climate_class_type: '',
        radiation_dose: '', // 全年总辐射量
        sunshine_time: [], // 全年平均日照时间
        average_temperature: [], // 年平均气温
        accumulated_temperature: [], //≥10℃年积温
        diurnal_temperature_difference: [], // 日温差
        max_temperature: '', // 极端最高气温
        max_days: '', // 维持的日数
        min_temperature: '', // 极端最低气温
        min_days: '', // 维持的日数
        max_avg_temperature: '', // 极端最高气温多年平均值气温
        min_avg_temperature: '', // 极端最低气温多年平均值气温
        no_frost_date: [], //无霜期
        avg_precipitation: [], // 年平均降水量
        avg_vaporization: [], // 年平均蒸发量
        avg_precipitation_day: [], // 年平均降水日
        dryness: '', // 多年平均干燥度
        wetness: '', // 多年平均湿润度
        precipitation_period: [], // 降水量最集中期
        natural_disaster: '' // 自然灾害
      },
      textPreview: {},
      title: '地形地貌信息',
      status: true,
      templateId: '',
      isLoading: true
    }
  },
  created () {
    this.templateId = this.$route.query.templateId
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
      this.$api.post('/member-reversion/physicalGeography/findClimateInfo', {
        user_id: this.$user.loginAccount,
        year_id: this.yearId,
        parent_id: this.id,
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          this.isLoading = false
          // this.title = response.data.climateInfo_name
          let data = response.data.climateInfo
          let arr = Object.keys(data)
          arr.length ? this.data = data : ''
          this.status = response.data.status
          this.textPreview = response.data.textPreview
        }
      })
    },
    // 保存
    handleSave () {
      this.isLoading = true
      this.textPreview.is_complete = '1'
      let list = {
        climateInfo: this.data,
        status: this.status,
        climateInfo_name: this.title,
        textPreview: this.textPreview,
        sys_dict_id: this.id,
        yearId: this.yearId,
        user_id: this.$user.loginAccount,
        templateId: this.templateId
      }
      this.$api.post('/member-reversion/physicalGeography/saveClimateInfo', list).then(response => {
        if (response.code === 200) {
          this.$Message.success('保存成功')
          this.$emit('on-save')
          this.handleInit()
        }
      })
    },
    // 数据变更
    onChange () {
      let str = ''
      if (this.data.climate_class.length) {
        str += `属于：${this.data.climate_class.join('、')}，`
      }
      if (str) {
        this.data.climate_class_type = `${str.substring(0,str.length-1)}。`
      }
    },
    // 文字预览 拼接
    changePreview () {
      let str = ''
      if (this.data.climate_class_type) {
        str += `${this.data.climate_class_type.substring(0,this.data.climate_class_type.length-1)}，`
      }
      if (this.data.radiation_dose) {
        str += `全年总辐射量：${this.data.radiation_dose} 千卡/平方厘米，`
      }
      if (this.data.sunshine_time[0] && this.data.sunshine_time[1]) {
        str += `全年平均日照时间：${this.data.sunshine_time[0]} 到 ${this.data.sunshine_time[1]} 小时，`
      }
      
      if (this.data.average_temperature[0] && this.data.average_temperature[1]) {
        str += `年平均气温：${this.data.average_temperature[0]} 到 ${this.data.average_temperature[1]} ℃，`
      }
      if (this.data.accumulated_temperature[0] && this.data.accumulated_temperature[1]) {
        str += `≥10℃年积温：${this.data.accumulated_temperature[0]} 到 ${this.data.accumulated_temperature[1]} ℃，`
      }
      if (this.data.diurnal_temperature_difference[0] && this.data.diurnal_temperature_difference[1]) {
        str += `日温差：${this.data.diurnal_temperature_difference[0]} 到 ${this.data.diurnal_temperature_difference[1]} ℃，`
      }
      
      if (this.data.max_temperature) {
        str += `极端最高气温：${this.data.max_temperature} ℃，`
      }
      if (this.data.max_days) {
        str += `维持的日数：${this.data.max_days} 天，`
      }
      if (this.data.min_temperature) {
        str += `极端最低气温：${this.data.min_temperature} ℃，`
      }
      if (this.data.min_days) {
        str += `维持的日数：${this.data.min_days} 天，`
      }
      if (this.data.max_avg_temperature) {
        str += `极端最高气温多年平均值气温：${this.data.max_avg_temperature} ℃，`
      }
      if (this.data.min_avg_temperature) {
        str += `极端最高气温多年平均值气温：${this.data.min_avg_temperature} ℃，`
      }

      if (this.data.no_frost_date[0] && this.data.no_frost_date[1]) {
        str += `无霜期：${this.data.no_frost_date[0]} 到 ${this.data.no_frost_date[1]} 天，`
      }
      if (this.data.avg_precipitation[0] && this.data.avg_precipitation[1]) {
        str += `年平均降水量：${this.data.avg_precipitation[0]} 到 ${this.data.avg_precipitation[1]} mm，`
      }
      if (this.data.avg_vaporization[0] && this.data.avg_vaporization[1]) {
        str += `年平均蒸发量：${this.data.avg_vaporization[0]} 到 ${this.data.avg_vaporization[1]} mm，`
      }

      if (this.data.avg_precipitation_day[0] && this.data.avg_precipitation_day[1]) {
        str += `年平均降水日：${this.data.avg_precipitation_day[0]} 到 ${this.data.avg_precipitation_day[1]} 天，`
      }

      if (this.data.dryness) {
        str += `多年平均干燥度：${this.data.dryness}，`
      }
      if (this.data.wetness) {
        str += `多年平均湿润度：${this.data.wetness}，`
      }
      if (this.data.precipitation_period[0] && this.data.precipitation_period[1]) {
        str += `降水量最集中期：${this.data.precipitation_period[0]} 到 ${this.data.precipitation_period[1]} 月，`
      }
      if (this.data.natural_disaster) {
        str += `自然灾害：${this.data.natural_disaster}，`
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

<style lang="scss">
.vui-climate{
  .ivu-form-item{
    margin-bottom: 14px;
  }
  .ivu-form .ivu-form-item-label{
    line-height: 20px;
  }
  .ivu-form-item-content{
    margin-top:4px;
  }
}
</style>
