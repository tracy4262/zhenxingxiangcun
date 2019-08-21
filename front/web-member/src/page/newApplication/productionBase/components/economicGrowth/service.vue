<template>
  <div class="pd20">
    <Title :title="title" :id="id" edit></Title>
    <service-list title="农业服务业" type="1" ref="agricultural" @on-init="handleInit" @on-numAdd="handleNumAdd" :id="id"></service-list>
    <service-list title="其他服务业" type="2" ref="other" @on-init="handleInit" @on-numAdd="handleNumAdd" :id="id"></service-list>
      <div style="background: rgb(0, 197, 135); margin-left: -36px; margin-right: -36px;" class="mt40 mb30">
        <div class="tr" style="padding: 20px 36px; color: #fff; font-size: 18px;">
          产值总计：{{total}} 万元
        </div>
      </div>
    <Title title="文字预览"></Title>
    <div class="pd20 pt30">
      <Input type="textarea" v-model="preview" :autosize="{minRows: 3,maxRows: 5}"></Input>
    </div>
    <div class="tc pd40">
      <Button type="primary" :loading="loading" @click="onSave">保存</Button>
    </div>
  </div>
</template>

<script>
import Title from '../../components/title'
import Divider from '~components/divider'
import serviceList from './components/serviceList'
import {numAdd} from '~utils/utils'
export default {
  props: {
    id: {
      type: String
    },
    appId: {
      type: String
    }
  },
  components: {
    Title,
    Divider,
    serviceList
  },
  data () {
    return {
      preview: '',
      total: 0,
      title: '服务产品信息',
      index: 0,
      baseId: '',
      loading: true
    }
  },
 created () {
   this.baseId = this.$route.query.id
  },
  methods: {
    initTitle () {
      this.$api.post('/member-reversion/productionBase/findTableHead', {
        account: this.$user.loginAccount,
        dictId: this.id
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
    // 文字预览
    changePreview () {
      let str = ''
      if (this.total) {
        str += `全村服务产品产值${this.total}万元。其中，农业服务业产值达到${this.$refs['agricultural'].total}万元；其中，其他服务业产值达到${this.$refs['other'].total}万元。`
      }
      this.preview = str
    },
    handleNumAdd () {
      let num = 0
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['agricultural'].total ? this.$refs['agricultural'].total : 0).toFixed(2))
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['other'].total ? this.$refs['other'].total : 0).toFixed(2))
      
      this.total = num.toFixed(2)
      if (this.index > 1) { 
        //  有2个模块，每个模块取到数据之后会调用计算的方法， index+1 ，
        // 当2个都调用完成后 index > 1 时，即是用户在修改数据时候，那么就调用文字预览的拼接方法
        this.changePreview()
      } else {
        this.index += 1
      }
    },
     //  初始化数据
    handleInit () {
        this.$api.post('/member-reversion/productionBase/ecoSocial/findServiceProduct',{
            account: this.$user.loginAccount,
            dictId: this.id,
            baseId: this.baseId
        }).then(response => {
            if (response.code == 200) {
              if (this.type == 1) {
                // 农业服务业
                response.data.agriculturalProduct.length ? this.$refs['agricultural'].getData(response.data.agriculturalProduct) : ''
              } else if (this.type == 2) {
                // 其他服务业
                response.data.otherService.length ? this.$refs['other'].getData(response.data.otherService): ''
              } else {
                this.preview = response.data.textPreview
                response.data.agriculturalProduct.length ? this.$refs['agricultural'].getData(response.data.agriculturalProduct) : this.index += 1
                response.data.otherService.length ? this.$refs['other'].getData(response.data.otherService): this.index += 1
                // this.title = response.data.propertyName
              }
              this.loading = false
            }
        })
    },
    // 保存文字预览
    onSave () {
      this.loading = true
        let list = {
            account: this.$user.loginAccount,
            dictId: this.id,
            textPreview: this.preview,
            baseId: this.baseId
        }
        this.isLoading = true
        this.$api.post('/member-reversion/productionBase/common/saveTextPreview', list).then(response => {
            if (response.code === 200) {
                this.$Message.success('保存成功')
                this.handleInit()
                this.$emit('on-save')
            }
        })
    }
  }
}
</script>

<style lang="scss" scoped>
.subtotal{
  font-size: 16px;
}
</style>
