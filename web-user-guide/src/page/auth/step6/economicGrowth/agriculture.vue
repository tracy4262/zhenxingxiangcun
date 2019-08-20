<template>
  <div class="pd20">
    <Title :title="title" :id="id" :yearId="yearId" edit></Title>
    <div class="pd20">
      <Form :label-width="80" label-position="left" ref="data">
        <Row  :gutter="38">
            <Col span="8">
              <FormItem label="权限">
                 <i-switch size="large"  v-model="status" :disabled="true">
                      <span slot="open">公开</span>
                      <span slot="close">隐藏</span>
                  </i-switch>
              </FormItem>
            </Col>
        </Row>
      </Form>
    </div>
    <agriculture-list title="农产品" type="1" ref="agriculture" @on-init="handleInit" @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></agriculture-list>
    <agriculture-list title="林业产品" type="2" ref="forestry" @on-init="handleInit" @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></agriculture-list>
    <agriculture-list title="畜牧业产品" type="3" ref="animal" @on-init="handleInit" @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></agriculture-list>
    <agriculture-list title="水产品" type="4" ref="water" @on-init="handleInit" @on-numAdd="handleNumAdd" :yearId="yearId" :id="id">></agriculture-list>
      <div style="background: rgb(0, 197, 135); width: 925px; margin-left: -36px;" class="mt40 mb30">
        <div class="tr" style="padding: 20px 36px; color: #fff; font-size: 18px;">
          产值总计：{{total}}万元
        </div>
      </div>
    <Title title="文字预览"></Title>
    <div class="pd20 pt30">
      <Input type="textarea" v-model="preview" :autosize="{minRows: 3,maxRows: 5}"></Input>
    </div>
    <div class="tc pd40">
      <Button type="primary" v-if="isLoading">保存</Button>
      <Button type="primary" v-else @click="onSave">保存</Button>
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
    Title,
    Divider,
    agricultureList
  },
  data () {
    return {
        status: true, 
        preview: '',
        total: 0,
        title: '',
        index: 0,
        isLoading: true
    }
  },
  created() {
    // this.handleInit()
  },
  methods: {
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
      
      this.total = num
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
        this.$api.post('/member-reversion/ecoSocial/findFarmProduct',{
            account: this.$user.loginAccount,
            templateId: this.$template.id,
            yearId: this.yearId,
            dictId: this.id
        }).then(response => {
            if (response.code == 200) {
              this.isLoading = false
              if (response.data.preview) {
                this.preview = response.data.preview
              } else {
                this.preview =`全村农产品产值（）万元。其中，农产品产值达到（）万元；其中，林业产品产值达到（）万元；其中，畜牧业产品产值达到（）万元；其中，水产品产值达到（）万元。`
              }
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
                  this.title = response.data.propertyName
                  this.status = response.data.status ? true : false
                  response.data.agriculture.length ? this.$refs['agriculture'].getData(response.data.agriculture) : this.index += 1
                  response.data.forestry.length ? this.$refs['forestry'].getData(response.data.forestry) : this.index += 1
                  response.data.animalHusbandry.length ? this.$refs['animal'].getData(response.data.animalHusbandry) : this.index += 1
                  response.data.waterIndustry.length ? this.$refs['water'].getData(response.data.waterIndustry): this.index += 1
                }
            }
        })
    },
    // 保存文字预览
    onSave () {
      let s = 0 
      this.status ? s = 1 : s = 0
        let list = {
            status: s,
            account: this.$user.loginAccount,
            templateId: this.$template.id,
            yearId: this.yearId,
            dictId: this.id,
            textPreview: this.preview,
            isComplete: true
        }
        this.isLoading = true
        this.$api.post('/member-reversion/perfect/saveTextPreview', list).then(response => {
            if (response.code === 200) {
                this.$Message.success('保存成功')
                this.$emit('on-save')
                this.handleInit()
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
