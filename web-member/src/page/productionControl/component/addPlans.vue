<template>
    <Modal
        v-model="isShow"
        :mask-closable="false"
        :width="520"
        :title="title">
        <div>
          <Form :label-width="120" :model="info" :rules="ruleinline" ref="info">
            <FormItem label="物种名称" prop="species">
              <Input v-model="info.species"   style="width:240px" readonly/>
            </FormItem>
            <FormItem label="品种名称" prop="varietyName">
              <Select v-model="info.varietyNameId" style="width:240px" label-in-value @on-change="varietyNameChange">
                <Option v-for="item in varietyNames" :value="item.id" :key="item.id">{{ item.productName }}</Option>
              </Select>
            </FormItem>
            <FormItem label="品种来源" prop="varietySource">
               <Input v-model="info.varietySource"  style="width:240px" :maxlength="20"/>
            </FormItem>
            <FormItem label="播种面积" prop="sownArea">
              <Input v-model="info.sownArea"  style="width:240px" :maxlength="20"><span slot="append">亩</span></Input>
            </FormItem>
            <FormItem label="播种时间" prop="sowingTime">
              <DatePicker type="date" v-model="info.sowingTime" style="width:240px" @on-change="onChange"></DatePicker>
            </FormItem>
            <FormItem label="基地名称" prop="baseId">
              <Select v-model="info.baseId" style="width:240px" multiple>
                <Option v-for="item in baseList" :value="item.baseId" :key="item.baseId">{{ item.baseName }}</Option>
              </Select>
            </FormItem>
            <FormItem label="地块编号"  prop="baseId">
              <Select v-model="info.baseId" style="width:240px"  multiple>
                <Option v-for="item in baseList" :value="item.baseId" :key="item.baseId">{{ item.land }}</Option>
              </Select>
            </FormItem>
          </Form>
        </div>
        <div slot="footer" class="tc">
          <Button @click="cancel">取消</Button>
          <Button type="primary" @click="onSave">确定</Button>
        </div>
    </Modal>
</template>
<script>
import vuiSpecies from '~components/vui-species'
import {isMoney3} from '~utils/validate'
export default {
  components:{
    vuiSpecies
  },
  data () {
    return {
      title: '新增生产计划',
      isShow: false,
      info: {
        serialNumber: '', // 生产序号
        species: '', // 物种名称
        varietyName: '', // 品种名称
        varietyNameId: '',
        varietySource: '', // 品种来源 
        sownArea: '', // 播种面积
        sowingTime: '', // 播种时间
        baseId: [],
        baseName: [], // 基地名称
        plotNumber: [], // 地块编号
      },
      varietyNames: [],
      ruleinline: {
        species: [
          {required: true, message: '请输入物种名称', trigger: 'blur' }
        ],
        varietyName: [
          {required: true, message: '请选择品种名称', trigger: 'blur' }
        ],
        varietySource: [
          {required: true, message: '请输入品种来源', trigger: 'blur' }
        ],
        sownArea: [
          {required: true, message: '请输入播种面积', trigger: 'blur' },
          {validator:isMoney3, trigger: 'blur'}
        ],
        sowingTime: [
          {required: true, message: '请选择播种时间', trigger: 'blur' }
        ],
        baseId: [
          {required: true, message: '请选择基地名称', type: 'array', min: 1, trigger: 'change' }
        ]
      },
      baseList: []
    }
  },
  created() {
    this.getBaseList()
    this.getList()
  },
  methods: {
    // 品种名称改变
    varietyNameChange (data) {
      if (data) {
        this.info.varietyName = data.label
      } else {
        this.info.varietyName = ''
      }
    },
    // 获取品种名称列表数据
    getList () {
      this.$api.post('/shop/inventory/basicSetting/productFind', {
        account: this.$user.loginAccount,
        pageNum: 1,
        pageSize: 9999999,
        productName: '',
        customId: ''
      }).then(response => {
        console.log('res', response)
        if (response.code === 200) {
          this.varietyNames = response.data.list
        }
      })
    },
    // 获取基地下拉数据
    getBaseList () {
      this.$api.post('/shop/plant/findProductionBase', {account: this.$user.loginAccount}).then(response => {
        if (response.code === 200) {
          this.baseList = response.data
        }
      })
    },
    // 触发 
    init (title, form) {
      this.title = title
      if (form) {
        this.info = Object.assign({}, form)
      }
      this.isShow = true
    },
    // 取物种
    onSaveSpecies (e) {
      this.info.species = e
    },
    // 取物种 id
    onSaveSpeciesId (e) {
       this.info.speciesId = e
    },
    // 取消
    cancel () {
      this.isShow = false
    },
    // 播种时间发生改变
    onChange () {
      this.info.sowingTime = this.moment(this.info.sowingTime).format('YYYY-MM-DD')
    },
    // 保存
    onSave () {
      this.info.sowingTime =  this.info.sowingTime ? this.moment(this.info.sowingTime).format('YYYY-MM-DD') : ''
      this.$refs['info'].validate((valid) => {
          if (valid) {
            this.$emit('on-save', this.info)
          } else {
              this.$Message.error('请核对表单信息！');
          }
      })
    }
  },
}
</script>
<style>

</style>

