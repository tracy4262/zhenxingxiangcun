<template>
  <div class="pd20">
    <Title :title="title" :id="id" edit :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh"></Title>
    <div class="pd20">
      <Form :label-width="80" label-position="left" ref="data">
        <Row  :gutter="38">
            <Col span="8">
              <FormItem label="权限">
                <Switch class="ml20"  size="large" v-model="status">
                  <span slot="open">公开</span>
                  <span slot="close">隐藏</span>
                </Switch>
              </FormItem>
            </Col>
        </Row>
      </Form>
    </div>
    <status-list title="农用地" type="1" ref="agricultural" @on-init="handleInit"  @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></status-list>
    <status-list title="建设用地" type="2" ref="construction" @on-init="handleInit"  @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></status-list>
    <status-list title="未利用地" type="3" ref="future" @on-init="handleInit"  @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></status-list>
      <div style="background: rgb(0, 197, 135); width: 925px; margin-left: -36px;" class="mt40 mb30">
        <div class="tr" style="padding: 20px 36px; color: #fff; font-size: 18px;">
          面积总计：{{total}} 平方千米
        </div>
      </div>
    <Title title="文字预览"></Title>
    <div class="pd20 pt30">
      <Input type="textarea" v-model="preview" :autosize="{minRows: 3,maxRows: 5}"></Input>
    </div>
    <div class="tc pd40">
      <Button type="primary" v-if="isLoading">保存</Button>
      <Button type="primary" @click="onSave" v-else>保存</Button>
    </div>
  </div>
</template>

<script>
import Title from '../../components/title'
import Divider from '~components/divider'
import statusList from './components/statusList'
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
    statusList
  },
  data () {
    return {
      total: 0,
      status: true, 
      preview: '',
      title: '土地利用现状',
      index: 0,
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
    // 初始化数据
    handleInit (type) {
        this.$api.post('/member-reversion/landUse/find',{
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
              this.isLoading = false
              if (type == 1) {
                // 农用地
                response.data.agricultural.length ? this.$refs['agricultural'].getData(response.data.agricultural) : ''
              } else if (type == 2) {
                // 建设用地
                response.data.construction.length ? this.$refs['construction'].getData(response.data.construction) : ''
              } else if (type == 3) {
                // 未来用地
                response.data.future.length ? this.$refs['future'].getData(response.data.future): ''
              } else if (type == 0) {
                // 只用于保存完文字预览之后回显文字预览
                this.preview = response.data.preview
              }else {
                this.status = response.data.status ? true : false
                this.preview = response.data.preview
                response.data.agricultural.length ? this.$refs['agricultural'].getData(response.data.agricultural) : this.index += 1
                response.data.construction.length ? this.$refs['construction'].getData(response.data.construction) : this.index += 1
                response.data.future.length ? this.$refs['future'].getData(response.data.future): this.index += 1
                // this.title = response.data.propertyName
              }
            }
        })
    },
    // 文字预览
    changePreview () {
      let str = ''
      if (this.total) {
        str += `土地利用面积${this.total}平方千米，其中：农业用地${this.$refs['agricultural'].total}平方千米，建设用地${this.$refs['construction'].total}平方千米，未利用地${this.$refs['future'].total}平方千米。`
      }
      this.preview = str
    },
    // 计算总产值
    handleNumAdd () {
      let num = 0
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['agricultural'].total ? this.$refs['agricultural'].total : 0).toFixed(2))
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['construction'].total ? this.$refs['construction'].total : 0).toFixed(2))
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['future'].total ? this.$refs['future'].total : 0).toFixed(2))
      this.total = num.toFixed(2)
      if (this.index > 2) { 
        //  有3个模块，每个模块取到数据之后会调用计算的方法， index+1 ，
        // 当3个都调用完成后 index > 2 时，即是用户在修改数据时候，那么就调用文字预览的拼接方法
        this.changePreview()
      } else {
        this.index += 1
      }
    },
    // 保存文字预览
    onSave () {
      let s = 0 
      this.status ? s = 1 : s = 0
        let list = {
            status: s,
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            textPreview: this.preview,
            isComplete: true,
            templateId: this.templateId
        }
        this.isLoading = true
        this.$api.post('/member-reversion/perfect/saveTextPreview', list).then(response => {
            if (response.code === 200) {
                this.$Message.success('保存成功')
                this.handleInit(0)
                this.$emit('on-save')
            }
        })
    },
    leftRefresh () {
      this.$emit('left-refresh')
    }
  }
}
</script>

<style lang="scss" scoped>
.subtotal{
  font-size: 16px;
}
</style>
