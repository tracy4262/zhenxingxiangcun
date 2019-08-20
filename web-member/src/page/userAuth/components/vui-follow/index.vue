<template>
  <Form :label-width="150" label-position="left" class="mt20 mb20">
    <Form-item  label="添加关注" v-if="!listData.length">
      <Button type="primary" @click="addFollowModal = true"> <Icon type="plus"></Icon>增加</Button>
    </Form-item>
    <Card v-for="(item, index) in listData" :key="index" class="mb10">
      <Row>
        <Col span="22">
          <div class="mb10 b">关注的知识类型（{{item.follow[0].length}}）</div>
          <releva-item title="知识" :span="2" :data="item.follow[0]"></releva-item>
          <div class="mb10 b mt20">关注的资讯类型（{{item.follow[1].length}}）</div>
          <releva-item title="资讯" :span="2" :data="item.follow[1]"></releva-item>
          <div class="mb10 b mt20">关注的政策类型（{{item.follow[2].length}}）</div>
          <releva-item title="政策" :span="2" :data="item.follow[2]"></releva-item>

          <div class="mt20 b">关键词（{{item.releva[0].length + item.releva[1].length + item.releva[2].length}}）</div>
          <releva-item title="物种" :span="2" :data="item.releva[0]"></releva-item>
          <releva-item title="产品" :span="2" :data="item.releva[1]"></releva-item>
          <releva-item title="服务" :span="2" :data="item.releva[2]"></releva-item>

        </Col>
        <Col span="2" class="tr">
          <Button type="text" size="small" @click="addFollowModal = true"> <Icon type="edit" size="16" class="pr5"></Icon>修改</Button>
          <Button type="text" size="small" @click="handleDelSel(index)" class="mt10"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button>
        </Col>
      </Row>
      <Switch v-model="item.flag" size="large" style="position: absolute;bottom: 15px;right: 15px;">
        <span slot="open">推送</span>
        <span slot="close">不推</span>
      </Switch>
    </Card>

    <!-- 添加关注 -->
    <Modal
    v-model="addFollowModal"
    width="1100"
    :mask-closable="false">
      <div slot="header">
          <span style="font-size:14px;font-weight:700">添加关注 </span>
          <span class="t-grey">基于订阅的“信息分类”和“关键词”，为您推送最新动态</span>
      </div>
      <vui-steps class="mt10" :data="stepsData" :active="active"></vui-steps>

      <div class="pd10">
        <!-- 关键词 -->
        <vui-releva v-show="relevaShow" ref="releva" @on-get-data="handleGetReleva"></vui-releva>
        <!-- 分类 -->
        <vui-follow v-show="followShow" ref="follow" @on-get-data="handleGetFollow"></vui-follow>
        <!-- 推送 -->
        <pushModal
         ref="pushModal"
         v-show="pushShow"
         :follow="followResult"
         :species="speciesSel"
         :product="productSel"
         :service="serviceSel"
         @on-get-data="handleGetPushData"
         ></pushModal>
      </div>
      <template v-if="relevaShow || followShow">
        <div slot="footer" class="tc">
          <Button type="default" @click="handlePrev" v-if="active > 0">上一步</Button>
          <Button type="primary" @click="handleNext">下一步</Button>
        </div>
      </template>

      <div slot="footer" class="tc" v-if="active === 2">
        <Button type="default" @click="handlePrev">上一步</Button>
        <Button type="primary" @click="handleSave">完成</Button>
      </div>
    </Modal>

  </Form>
</template>
<script>
import vuiSteps from '~components/vui-steps'
import vuiReleva from './releva'
import vuiFollow from './follow'
import pushModal from './push-modal'
import relevaItem from './releva-item'
import {Mixins} from './mixins.js'
export default {
  components: {
    vuiSteps,
    vuiReleva,
    vuiFollow,
    pushModal,
    relevaItem
  },
  mixins: [Mixins],
  data: () => ({
    addFollowModal: false,
    followShow: false,
    relevaShow: true,
    pushShow: false,
    active: 0,
    index: 0,
    stepsData: [{
      name: '选择关键词'
    }, {
      name: '选择分类'
    }, {
      name: '设置推送'
    }],
    speciesSel: [],
    productSel: [],
    serviceSel: [],
    followResult: {
      knowledge: [],
      info: [],
      policy: []
    },
    relevaResult: [],
    listData: [],
    flag: true
  }),
  created() {
  },
  methods: {
    // 取关联数据
    handleGetReleva (speciesSel, productSel, serviceSel) {
      this.speciesSel = speciesSel
      this.productSel = productSel
      this.serviceSel = serviceSel
    },
    // 取关注数据
    handleGetFollow (knowledge, info, policy) {
      this.followResult = {knowledge, info, policy}
    },
    // 取推送数据
    handleGetPushData (flag) {
      this.flag = flag
    },
    // 上一步
    handlePrev () {
      this.active--
      if (this.active < 0) this.active = 0
      this.changeShow()
    },
    // 下一步
    handleNext () {
      this.active++
      this.changeShow()
    },
    changeShow () {
      if (this.active === 0) {
        this.followShow = false
        this.relevaShow = true
        this.pushShow = false
      } else if (this.active === 1) {
        this.followShow = true
        this.relevaShow = false
        this.pushShow = false
      } else {
        this.followShow = false
        this.relevaShow = false
        this.pushShow = true
      }
    },
    // 保存
    handleSave () {
      this.listData = []
      this.listData.push({
        follow: [
          this.followResult.knowledge,
          this.followResult.info,
          this.followResult.policy
        ],
        releva: [
          this.speciesSel,
          this.productSel,
          this.serviceSel
        ],
        flag: this.flag
      })
      this.addFollowModal = false
      this.active = 0
      this.followShow = false
      this.relevaShow = true
      this.pushShow = false
      this.$emit('save', this.listData)
    },
    // 重置组件
    handleReset () {
      this.$refs.releva.handleReset()
      this.$refs.follow.handleReset()
      this.active = 0
      this.followData = this.knowledge
      this.followDataSel = []
      this.speciesSel = []
      this.productSel = []
      this.serviceSel = []
      this.addFollowModal = false
      this.followShow = false
      this.relevaShow = true
      this.pushShow = false
    },
    // 删除
    handleDelSel (index) {
      this.$Modal.confirm({
        title: '是否确定删除',
        content: '是否确认删除？',
        onOk: () => {
          this.listData.splice(index, 1)
          this.handleReset()
        },
        okText: '确定',
        cancelText: '取消'
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
