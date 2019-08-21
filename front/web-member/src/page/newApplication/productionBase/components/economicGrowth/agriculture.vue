<template>
  <div class="pd20">
    <Title :title="title" :id="id" edit></Title>
    <agriculture-list title="农产品" type="1" ref="agriculture" @on-init="handleInit" @on-numAdd="handleNumAdd" :id="id"></agriculture-list>
    <agriculture-list title="林业产品" type="2" ref="forestry" @on-init="handleInit" @on-numAdd="handleNumAdd" :id="id"></agriculture-list>
    <agriculture-list title="畜牧业产品" type="3" ref="animal" @on-init="handleInit" @on-numAdd="handleNumAdd" :id="id"></agriculture-list>
    <agriculture-list title="水产品" type="4" ref="water" @on-init="handleInit" @on-numAdd="handleNumAdd" :id="id">></agriculture-list>
      <div style="background: rgb(0, 197, 135); margin-left: -36px; margin-right: -36px;" class="mt40 mb30">
        <div class="tr" style="padding: 20px 36px; color: #fff; font-size: 18px;">
          产值总计：{{total}}万元
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
import agricultureList from './components/agricultureList'
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
    agricultureList
  },
  data () {
    return {
        preview: '',
        total: 0,
        title: '农产品信息',
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
        str += `全村农产品产值${this.total}万元。其中，农产品产值达到${this.$refs['agriculture'].total}万元；其中，林业产品产值达到${this.$refs['forestry'].total}万元；其中，畜牧业产品产值达到${this.$refs['animal'].total}万元；其中，水产品产值达到${this.$refs['water'].total}万元。`
      }
      this.preview = str
    },
    handleNumAdd () {
      let num = 0
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['agriculture'].total ? this.$refs['agriculture'].total : 0).toFixed(2))
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['forestry'].total ? this.$refs['forestry'].total : 0).toFixed(2))
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['animal'].total ? this.$refs['animal'].total : 0).toFixed(2))
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['water'].total ? this.$refs['water'].total : 0).toFixed(2))
      
      this.total = num.toFixed(2)
      if (this.index > 3) { 
        //  有4个模块，每个模块取到数据之后会调用计算的方法， index+1 ，
        // 当4个都调用完成后 index > 3 时，即是用户在修改数据时候，那么就调用文字预览的拼接方法
        this.changePreview()
      } else {
        this.index += 1
      }
    },
     //  初始化数据
    handleInit (type) {
        this.$api.post('/member-reversion/productionBase/ecoSocial/findFarmProduct',{
            account: this.$user.loginAccount,
            dictId: this.id,
            baseId: this.baseId
        }).then(response => {
            if (response.code == 200) {
              // 保存后更新 重新获取数据
              if (type == 1) {
                // 农产品
                response.data.agriculture.length ? this.$refs['agriculture'].getData(response.data.agriculture) : ''
              } else if (type == 2) {
                // 林业产品
                response.data.forestry.length ? this.$refs['forestry'].getData(response.data.forestry) : ''
              } else if (type == 3) {
                // 畜牧业产品
                response.data.animalHusbandry.length ? this.$refs['animal'].getData(response.data.animalHusbandry) : ''
              } else if (type == 4) {
                // 水产品
                response.data.waterIndustry.length ? this.$refs['water'].getData(response.data.waterIndustry): ''
              } else {
                this.preview = response.data.textPreview
                response.data.agriculture.length ? this.$refs['agriculture'].getData(response.data.agriculture) : this.index += 1
                response.data.forestry.length ? this.$refs['forestry'].getData(response.data.forestry) : this.index += 1
                response.data.animalHusbandry.length ? this.$refs['animal'].getData(response.data.animalHusbandry) : this.index += 1
                response.data.waterIndustry.length ? this.$refs['water'].getData(response.data.waterIndustry): this.index += 1
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
