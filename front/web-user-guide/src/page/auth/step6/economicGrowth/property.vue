<template>
  <div class="pd20">
    <Title :title="title" :id="id" edit :yearId="yearId"></Title>
    <div class="pd20">
      <Form :label-width="80" label-position="left" ref="data">
        <Row  :gutter="38">
            <Col span="8">
              <FormItem label="权限">
                <Switch class="ml20"  size="large" v-model="status" :disabled="true">
                  <span slot="open">公开</span>
                  <span slot="close">隐藏</span>
                </Switch>
              </FormItem>
            </Col>
        </Row>
      </Form>
    </div>
    <property-list title="第一产业" type="1" ref="primary" @on-init="handleInit" @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></property-list>
    <property-list title="第二产业" type="2" ref="secondary" @on-init="handleInit" @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></property-list>
    <property-list title="第三产业" type="3" ref="tertiary" @on-init="handleInit" @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></property-list>
      <div style="background: rgb(0, 197, 135); width: 925px; margin-left: -36px;" class="mt40 mb30">
        <div class="tr" style="padding: 20px 36px; color: #fff; font-size: 18px;">
          产值总计：{{total}} 万元
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
import propertyList from './components/propertyList'
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
    propertyList
  },
  data () {
    return {
      total: 0,
      status: true, 
      preview: '',
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
        str += `全村生产总值（GDP）${this.total}万元。其中，第一产业增加值${this.$refs['primary'].total}万元；其中，第二产业增加值${this.$refs['secondary'].total}万元；其中，第三产业增加值${this.$refs['tertiary'].total}万元。`
      }
      this.preview = str
    },
    // 计算总产值
    handleNumAdd () {
      let num = 0
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['primary'].total ? this.$refs['primary'].total : 0).toFixed(2))
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['secondary'].total ? this.$refs['secondary'].total : 0).toFixed(2))
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['tertiary'].total ? this.$refs['tertiary'].total : 0).toFixed(2))
      this.total = num
      if (this.index > 2) { 
        //  有3个模块，每个模块取到数据之后会调用计算的方法， index+1 ，
        // 当3个都调用完成后 index > 2 时，即是用户在修改数据时候，那么就调用文字预览的拼接方法
        this.changePreview()
      } else {
        this.index += 1
      }
    },
     //  初始化数据
    handleInit (type) {
        this.$api.post('/member-reversion/ecoSocial/findIndustry',{
            account: this.$user.loginAccount,
            templateId: this.$template.id,
            yearId: this.yearId,
            dictId: this.id
        }).then(response => {
            if (response.code == 200) {
              this.isLoading = false
              if (type == 1) {
                // 第一产业
                response.data.primaryIndustry.length ? this.$refs['primary'].getData(response.data.primaryIndustry) : ''
              } else if (type == 2) {
                // 第二产业
                response.data.secondaryIndustry.length ? this.$refs['secondary'].getData(response.data.secondaryIndustry) : ''
              } else if (type == 3) {
                // 第三产业
                response.data.tertiaryIndustry.length ? this.$refs['tertiary'].getData(response.data.tertiaryIndustry): ''
              } else {
                this.status = response.data.status ? true : false
                if (response.data.preview) {
                  this.preview = response.data.preview
                } else {
                  this.preview = `全村生产总值（GDP）（）万元。其中，第一产业增加值（）万元；其中，第二产业增加值（）万元；其中，第三产业增加值（）万元。`
                }
                response.data.primaryIndustry.length ? this.$refs['primary'].getData(response.data.primaryIndustry) : this.index += 1
                response.data.secondaryIndustry.length ? this.$refs['secondary'].getData(response.data.secondaryIndustry) : this.index += 1
                response.data.tertiaryIndustry.length ? this.$refs['tertiary'].getData(response.data.tertiaryIndustry): this.index += 1
                this.title = response.data.propertyName
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
    },
  }
}
</script>

<style lang="scss" scoped>
.subtotal{
  font-size: 16px;
}
</style>
