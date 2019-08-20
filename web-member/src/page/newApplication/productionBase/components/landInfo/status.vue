<template>
  <div class="pd20">
    <Title :title="title" :id="id" edit ></Title>
    <status-list title="农用地" type="1" ref="agricultural" @on-init="init"  @on-numAdd="handleNumAdd" :id="id"></status-list>
    <status-list title="建设用地" type="2" ref="construction" @on-init="init"  @on-numAdd="handleNumAdd" :id="id"></status-list>
    <status-list title="未来用地" type="3" ref="future" @on-init="init"  @on-numAdd="handleNumAdd" :id="id"></status-list>
      <div style="background: rgb(0, 197, 135); width: 111%; margin-left: -36px;" class="mt40 mb30">
        <div class="tr" style="padding: 20px 36px; color: #fff; font-size: 18px;">
          面积总计：{{total}} 平方千米
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
import statusList from './components/statusList'
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
    statusList
  },
  data () {
    return {
      total: 0,
      preview: '',
      title: '土地利用现状信息',
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
     //  初始化数据
    init (type) {
        this.$api.post('/member-reversion/productionBase/landInfo/findLandUse',{
            account: this.$user.loginAccount,
            dictId: this.id,
            baseId: this.baseId
        }).then(response => {
            if (response.code == 200) {
              if (type == 1) {
                // 农用地
                response.data.agricultural.length ? this.$refs['agricultural'].getData(response.data.agricultural) : ''
              } else if (type == 2) {
                // 建设用地
                response.data.construction.length ? this.$refs['construction'].getData(response.data.construction) : ''
              } else if (type == 3) {
                // 未来用地
                response.data.future.length ? this.$refs['future'].getData(response.data.future): ''
              } else {
                this.preview = response.data.textPreview
                response.data.agricultural.length ? this.$refs['agricultural'].getData(response.data.agricultural) : this.index += 1
                response.data.construction.length ? this.$refs['construction'].getData(response.data.construction) : this.index += 1
                response.data.future.length ? this.$refs['future'].getData(response.data.future): this.index += 1
                // this.title = response.data.propertyName
              }
              this.loading = false
            }
        })
    },
    // 保存文字预览
    onSave () {
      let s = 0 
      let list = {
        account: this.$user.loginAccount,
        dictId: this.id,
        textPreview: this.preview,
        isComplete: true,
        baseId: this.baseId
      }
      this.loading = true
      this.$api.post('/member-reversion/productionBase/common/saveTextPreview', list).then(response => {
        if (response.code === 200) {
            this.$Message.success('保存成功')
            this.init()
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
