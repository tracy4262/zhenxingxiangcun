<template>
  <div >
      <Modal
        v-model="isShow"
        title="收藏"
        :mask-closable="false"
        width="420">
      <div class="pt20">
        <Form :model="formItem" ref="formItem" :label-width="80" label-position="right" :rules="ruleFormItem">
          <formItem label="物种" prop="speciesId">
            <vuiSpecies ref="vuiSpecies" style="width:260px" :values="formItem.species" @on-save="onSaveName" @on-save-id="onSaveId" :num="1"></vuiSpecies>
          </formItem>
          <formItem label="品种" prop="variety">
            <Select label-in-value v-model="formItem.variety" multiple filterable style="width:260px" @on-change="onChange">
                <Option v-for="(item, index) in varietyData" :value="item.fid" :key="index">{{ item.fname }}</Option>
            </Select>
          </formItem>
        </Form>
      </div>
      <div slot="footer">
        <Button @click="onSaveTitle">确定</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import vuiSpecies from '~components/vui-species'
export default {
  prop: {
    init: {
      type: Boolean,
      default: false
    }
  },
  components: {
    vuiSpecies
  },
  data () {
    return {
      title: '',
      templateId: '',
      isShow: false,
      formItem: {
        species: '',
        speciesId: '',
        variety: '',
      },
      varietyData:[],
      ruleFormItem: {
        speciesId: [
            {required: true, message: '请选择物种名称', trigger: 'change'}
        ],
        variety: [
            {required: true, message: '请填写品种名称', trigger: 'blur'}
        ],
      },
      data: []
    }
  },
  created() {
  },
  methods: {
    init () {
      this.isShow = true
      this.formItem = {
        species: '',
        speciesId: '',
        variety: '',
      }
      this.data = []
    },
    // 物种名称
    onSaveName (e) {
      this.formItem.species = e
    },
    // 物种名称
    onSaveId (e) {
      if (this.formItem.speciesId != e) {
        this.formItem.speciesId = e
        this.getVariety()
      }
    },
    onChange (data) {
      console.log(data)
      this.data = data
    },
    // 根据物种查询品种
    getVariety () {
      this.$api.post('/member/nameLibrary/findVarietyList', {value: this.formItem.speciesId}).then(response => {
        if (response.code === 200) {
          this.varietyData = response.data
        }
      })
    },
    onSaveTitle () {
      if (this.data.length) {
         let data = {
            account: this.$user.loginAccount,
            type: '1',
            dataList: this.data
          }
          console.log(data)
          this.$api.post('/member/nameLibrary/saveLibrary', data).then(response => {
            if (response.code === 200) {
              this.$Message.success('收藏成功！')
              this.isShow = false
              this.$emit('on-save')
              // this.labList[this.active].pageNum = 1
              // this.init( this.labList[this.active] , this.active)
            } else {
              this.$Message.error('收藏失败！')
            }
          })
        } else {
          this.$Message.warning('请选择！')
        }
    }
  }
}
</script>
