<template>
  <div>
    <Form :label-width="100" :model="form" ref="form" label-position="right">
      <Row>
        <Col span="8">
          <FormItem label="通用商品名称">
             <!-- <vui-product-name
             :values="form.commonProductName"
             @handle-get-result="handleResult"></vui-product-name> -->
            <Input placeholder="请输入"  v-model="form.commonProductName"></Input>
          </FormItem>
        </Col>
        <Col span="8">
          <FormItem label="产品分类">
            <vuiProduct :values="form.productTypeName" ref="vuiSpecies" @on-save="onSaveProduct" @on-save-id="onSaveProductId" :num="1"></vuiProduct>
            <!-- <Input placeholder="请输入"  v-model="form.productTypeName">
            </Input> -->
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
            <vuiTrade :values="form.relatedIndustry" ref="vuiTrade" @on-save="onSaveTrade"  @on-save-id="onSaveTradeId":num="1"></vuiTrade>
            <!-- <Input placeholder="请输入"  v-model="form.relatedIndustry">
            </Input> -->
          </FormItem>
        </Col>
        <Col span="8">
          <FormItem label="关联物种">
            <vuiSpecies :values="form.relatedSpeciesName" ref="vuiSpecies" @on-save="onSaveSpecies"  @on-save-id="onSaveSpeciesId":num="1"></vuiSpecies>
            <!-- <Input placeholder="请输入"  v-model="form.relatedSpeciesName">
            </Input> -->
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
  export default {
    components: {
      vuiSpecies,
      vuiTrade,
      vuiProduct,
      vuiProductName
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
        default: 'addCommodity'
      },
      searchList: {
        type: Object,
        default: () => {
          return {
            commonProductName: '',
            productTypeName: '',
            productType: '', // 传的是商品分类的ID
            relatedIndustry: '',
            relatedIndustryId: '',
            relatedSpeciesName: '',
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
      onSaveSpecies (e) {
        this.form.relatedSpeciesName = e
        this.$emit('on-change', this.form)
      },
      onSaveSpeciesId (e) {
        this.form.relatedSpeciesId = e
        this.$emit('on-change', this.form)
      },
      onSaveTrade (e) {
        this.form.relatedIndustry = e
        this.$emit('on-change', this.form)
      },
      onSaveTradeId (e) {
        this.form.relatedIndustryId = e
        this.$emit('on-change', this.form)
      },
      onSaveProduct (e) {
        console.log(e)
        this.form.productTypeName = e
        this.$emit('on-change', this.form)
      },
      onSaveProductId (e) {
        this.form.productType = e
        this.$emit('on-change', this.form)
      },
      handleResult (e) {
        this.form.commonProductName = e
        this.$emit('on-change', this.form)
      },
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
