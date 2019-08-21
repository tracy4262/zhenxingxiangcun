<template>
  <div>
    <Form :label-width="90" :model="form" ref="form" label-position="right">
      <Row>
        <Col span="8">
          <FormItem label="通用服务名称">
            <Input placeholder="请输入"  v-model="form.commonProductName"></Input>
          </FormItem>
        </Col>
        <Col span="8">
          <FormItem label="服务分类">
            <vuiService
                :values="form.serviceType"
                ref="vuiSpecies"
                @on-save="onSaveService"
                @on-save-id="onSaveServiceId" :num="1"></vuiService>
          </FormItem>
        </Col>
        <Col span="8" class="tr">
          <Button icon="md-add" v-if="!edit && focusType == '1'" class="mr10" @click="addSpecies">新增</Button>
          <Button v-if="!edit"  @click="handleEdit">批量操作</Button>
          <!-- focusType 0收藏 1新增 -->
          <Button type="primary" v-if="edit && focusType == '0'" class="mr10"  @click="cancelFocus">取消收藏</Button>
          <Button type="primary" v-if="edit && focusType == '1'" class="mr10"  @click="del">删除</Button>
          <Button v-if="edit" @click="handleEdit" >退出批量操作</Button>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <FormItem label="行业分类">
            <vuiTrade
              :values="form.relatedIndustry"
              ref="vuiTrade"
              @on-save="onSaveTrade"
              @on-save-id="onSaveTradeId":num="1"></vuiTrade>
          </FormItem>
        </Col>
        <Col span="8">
          <FormItem label="关联物种">
            <vuiSpecies
                :values="form.relatedSpeciesName"
                ref="vuiSpecies"
                @on-save="onSaveSpecies"
                @on-save-id="onSaveSpeciesId":num="1"></vuiSpecies>
          </FormItem>
        </Col>
        <Col :span="'8'" class="tr">
          <Button icon="ios-search"  @click="onSaerch">查询</Button>
        </Col>
      </Row>
    </Form>
  </div>
</template>
<script>
import vuiSpecies from '~components/vui-species'
import vuiTrade from '~components/vui-trade'
import vuiProduct from '~components/vui-product'
import vuiProductName from '~components/vui-productName'
import vuiService from '~components/vui-service'
  export default {
    components: {
      vuiSpecies,
      vuiTrade,
      vuiProduct,
      vuiProductName,
      vuiService
    },
    props: {
      edit: {
        type: Boolean,
        default: false
      },
      focusType: {
        type: String,
        default: '0'
      },
      path: {
        type: String,
        default: 'addService'
      },
      searchList: {
        type: Object,
        default: () => {
          return {
            commonProductName: '', // 通用服务名称
            serviceType: '', // 服务分类
            serviceTypeId: '', // 传的是商品分类的ID
            relatedIndustry: '', // 行业分类
            relatedIndustryId: '',
            relatedSpeciesName: '', // 关联物种
            relatedSpeciesId: '',
          }
        }
      }
    },
    data () {
      return {
        form: {
          commonProductName: '',
          productTypeName: '',
          productType: '', // 传的是商品分类的ID
          relatedIndustry: '',
          relatedIndustryId: '',
          relatedSpeciesName: '',
          relatedSpeciesId: '',
        },
      }
    },
    watch: {
      searchList: {
        handler(curVal, oldVal) {
          console.log('curVal', curVal, 'oldVal', oldVal)
          this.form = curVal
        },
        deep: true
      }
    },
    created() {
      this.keyWord = this.followValue
      this.type = this.followType
    },
    methods: {
      // 关联物种
      onSaveSpecies (e) {
        this.form.relatedSpeciesName = e
        this.$emit('on-change', this.form)
      },
      onSaveSpeciesId (e) {
        this.form.relatedSpeciesId = e
        this.$emit('on-change', this.form)
      },
      // 行业分类
      onSaveTrade (e) {
        this.form.relatedIndustry = e
        this.$emit('on-change', this.form)
      },
      onSaveTradeId (e) {
        this.form.relatedIndustryId = e
        this.$emit('on-change', this.form)
      },
      // 服务分类
      onSaveService (e) {
        console.log(e)
        this.form.serviceType = e
        this.$emit('on-change', this.form)
      },
      onSaveServiceId (e) {
        this.form.serviceTypeId = e
        this.$emit('on-change', this.form)
      },
      // 点击查询
      onSaerch () {
        console.log(this.form)
        this.$emit('on-search', this.form)
      },
      // 点击添加关注
      addSpecies () {
        this.$router.push(`/nameLibrary/${this.path}`)
      },
      // 点击取消 收藏
      cancelFocus () {
        this.$emit('on-cancel')
      },
      // 点击删除
      del () {
        this.$emit('on-del')
      },
      // 切换多选状态
      handleEdit () {
        this.$emit('on-edit')
      },
    }
  }

</script>
