<template>
  <div class="pd20">
    <Form ref="formItem" :model="data" :label-position="view ? 'left' : 'top'"  :rules="view ? {} : formItemInline">
      <Form-item label="基本农田" prop="JBNT">
        <div v-if="view">
          <span v-if="data.JBNT == '0'"> 否 </span>
          <span v-if="data.JBNT == '1'"> 是 </span>
        </div>
        <Select v-else v-model="data.JBNT">
            <Option value="0">否</Option>
            <Option value="1">是</Option>
        </Select>
      </Form-item>
      <Form-item  prop="TDLYQLRMC" label="权利人">
        <!-- 单选 -->
        <span v-if="view"> {{data.TDLYQLRMC}} </span>
        <Input v-else v-model="data.TDLYQLRMC" :maxlength="20" />
      </Form-item>
      <!-- <Form-item prop="DKBM" label="地块编码">
          <Input v-model="data.DKBM" :maxlength="20" ></Input>
      </Form-item> -->
      <Form-item prop="DKMC" label="地块名称">
        <span v-if="view"> {{data.DKMC}} </span>
        <Input v-else v-model="data.DKMC" :maxlength="50" ></Input>
      </Form-item>
      <!-- <Form-item prop="TDLYXZ" label="土地利用现状">
          <Cascader :data="landUseStatuss" v-model="data.TDLYXZ">
          </Cascader>
      </Form-item> -->
      <Form-item prop="SCMJ" label="实测面积">
        <span v-if="view"> {{ data.SCMJ }} <span v-if="data.SCMJ">平方米</span></span>
        <Input v-else v-model="data.SCMJ" :maxlength="20" >
          <span slot="append">平方米</span>
        </Input>
      </Form-item>
      <Form-item prop="HCMJ" label="航测面积" >
        <span v-if="view"> {{ data.HCMJ }} <span v-if="data.HCMJ">平方米</span></span>
        <Input v-else v-model="data.HCMJ" :maxlength="20">
          <span slot="append">平方米</span>
        </Input>
      </Form-item>
      <!-- <Form-item prop="HTMJ" label="合同面积" >
          <Input v-model="data.HTMJ" :maxlength="20">
          <span slot="append">平方米</span>
          </Input>
      </Form-item>
      <Form-item prop="QDFS" label="取得方式">
          <Select v-model="data.QDFS" style="width: 100%" >
              <Option v-for="(item, index) in getTypes" :value="item.label" :key="index">{{item.label}}</Option>
          </Select>
      </Form-item>
      <Form-item prop="QDSJ" label="取得时间" >
        <DatePicker type="date" v-model="data.QDSJ" style="width: 100%;"></DatePicker>
      </Form-item>
      <Form-item prop="SYQLX" label="使用权类型" >
          <Select v-model="data.SYQLX" style="width: 100%">
              <Option v-for="(item, index) in userTypes" :value="item.label" :key="index">{{item.label}}</Option>
          </Select>
      </Form-item> -->
    </Form>
  </div>
</template>

<script>
import {isTelephoneAndphone, isPostCode, isMoney3} from '@/utils/validate'
import { debuglog } from 'util';
export default {
  props: {
    view: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      title: '土地使用权信息',
      landUseStatuss: [
          {
              label: '农用地',
              value: '1',
              children: []
          },
          {
              label: '建设用地',
              value: '2',
              children: []
          },
          {
              label: '未利用地',
              value: '3',
              children: []
          }
      ],
      getTypes: [
        {label: '划拨', value: '划拨'},
        {label: '出让', value: '出让'},
        {label: '划定（村集体占用）', value: '划定（村集体占用）'},
        {label: '承包', value: '承包'},
        {label: '经营', value: '经营'},
      ],
      userTypes: [
        {label: '土地承包经营权', value: '土地承包经营权'},
        {label: '集体土地使用权', value: '集体土地使用权'},
        {label: '建设用地使用权', value: '建设用地使用权'},
        {label: '宅基地使用权', value: '宅基地使用权'},
        {label: '林地林木所有权', value: '林地林木所有权'},
        {label: '经济养殖水面所有权', value: '经济养殖水面所有权'},
      ],
      data: {
          JBNT: '', // 基本农田
          TDLYQLRMC: '', // 土地使用权利人名称
          // DKBM: '', // 地块编码
          DKMC: '', // 地块名称
          // TDLYXZ: [], // 土地利用现状
          SCMJ: '', // 实测面积
          HCMJ: '', // 航测面积
          // HTMJ: '', // 合同面积
          // QDFS: '', // 取得方式
          // QDSJ: '', // 取得时间
          // SYQLX: '', // 使用权类型
      },
      formItemInline: {
        TDLYQLRMC: [
          { required: true, message: '请填写权利人', trigger: 'blur' }
        ],
        DKMC: [
          { required: true, message: '请填写地块名称', trigger: 'blur' }
        ],
        SCMJ: [
          {validator: isMoney3, trigger: 'blur'},
        ],
        HCMJ: [
          {validator: isMoney3, trigger: 'blur'},
        ],
        HTMJ: [
          {validator: isMoney3, trigger: 'blur'},
        ]
      }
    }
  },
created() {
  // 取初始化下拉列表的数据
  this.handleSelectList()
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
      // this.data.TDLYXZ = this.data.TDLYXZ.join(',')
      if (this.data.QDSJ) {
        this.data.QDSJ = this.moment(this.data.QDSJ).format('YYYY/MM/DD HH:mm:ss')
      }
      this.$refs['formItem'].validate(v => {
        this.$emit('on-save', v)
      })
    },
    // 取初始化下拉列表的数据
    handleSelectList () {
        // 农用地
        this.$api.post('/member-reversion/landUse/dict', {
            type : '1',
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
            this.landUseStatuss[0].children = response.data
            }
        })
        // 建设用地
        this.$api.post('/member-reversion/landUse/dict', {
            type : '2',
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
                this.landUseStatuss[1].children = response.data
            }
        })
        // 未利用地
        this.$api.post('/member-reversion/landUse/dict', {
            type : '3',
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
                this.landUseStatuss[2].children = response.data
            }
        })
    }
  }
}
</script>

<style lang="less">
.group-number{
    .ivu-form-item-content{
        margin-left: 0px  !important;
    }
}
</style>
