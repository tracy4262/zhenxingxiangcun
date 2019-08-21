<template>
  <div :class="view ? 'pd12' : 'pd20'">
    <Form ref="formItem" :model="data" :label-position="view ? 'left' : 'top'" :rules="view ? {} : formItemInline" >
      <Form-item prop="WDMC" label="网点名称">
        <span v-if="view"> {{ data.WDMC }} </span>
          <Input v-else v-model="data.WDMC" :maxlength="20" ></Input>
      </Form-item>
      <Form-item prop="WDLX" label="网点类型">
        <div v-if="view">
          <!-- <span v-for="(item, index) in data.WDLX" :key="index"> {{ item }} </span> -->
          <span> {{ data.WDLX }} </span>
        </div>
        <CheckboxGroup v-else v-model="WDLXARR" style="width: 100%;">
            <Checkbox label="销售门店" ></Checkbox>
            <Checkbox label="售后网点" ></Checkbox>
        </CheckboxGroup>
      </Form-item>
      <Form-item prop="LXR" label="联系人">
        <span v-if="view"> {{ data.LXR }} </span>
        <Input v-else v-model="data.LXR" :maxlength="20" ></Input>
      </Form-item>
      <Form-item prop="SJHM" label="手机号码">
        <span v-if="view"> {{ data.SJHM }} </span>
        <Input v-else v-model="data.SJHM" :maxlength="11" ></Input>
      </Form-item>
    </Form>
  </div>
</template>
<script>
import {isPhone2, isTelephoneAndphone} from '@/utils/validate'
export default {
  props: {
    view: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      data: {
        // LXR: "1"
        // SJHM: "1"
        // WDLX: "1"
        // WDMC: "1"
        WDMC: '', // 网点名字
        WDLX: '', // 网点类型
        LXR: '', // 联系人
        SJHM: '' // 手机号码
      },
      WDLXARR: [],
      formItemInline:{
        WDMC: [
          { required: true, message: '请输入网点名字', trigger: 'blur' }
        ],
        SJHM: [{validator: isPhone2, trigger: 'blur'}]
      },
      title: '营业网点'
    }
  },
  created() {
  },
  methods: {
    initShow (list) {
      // 初始化页面置空
      if (list) {
        console.log('list', list)
        let data = Object.assign(this.data, list)
        if (!Array.isArray(data.WDLX)) {
          this.WDLXARR = data.WDLX.split(',')
        } else {
          this.WDLXARR = data.WDLX
        }
        this.data = data
      } else {
        this.WDLXARR = []
        this.data.WDLX = ''
        this.$refs.formItem.resetFields()
      }
    },
    save () {
      let str = ''
      this.WDLXARR.forEach((e, i) => {
        if (e) {
          str += `${e},`
        }
      })
      str = str.substring(0, str.length-1)
      this.data.WDLX = str
      this.$refs['formItem'].validate(v => {
        this.$emit('on-save', v)
      })
    }
  }
}
</script>
