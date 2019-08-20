<template>
  <div>
    <ul>
      <li  v-for="(item, index) in data" class="pt20 card-li">
         <Card :padding="0">
                <div>
                    <img v-if="item.image_url && item.image_url[0]" :src="item.image_url[0]" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="158px">
                    <img v-else src="../../../../static/img/goods-list-no-picture1.png" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="158px">
                </div>
                <div class="pd20 tc">
                    <p class="ell" @click="detail(item.id, item.type, item.account)" v-if="item.service_name" > &nbsp;{{item.service_name}}</p>
                    <p class="ell" v-else style="line-height:24px;"> &nbsp;</p>
                    <!-- <p class="t-grey pt10 pb10 ell">{{item.time}}</p> -->
                    <Button type="primary" v-if="isRelation" size="small" class="mt10" @click="unLink(item)">已关联</Button>
                    <Button type="default" v-else size="small" class="mt10" @click="beLink(item)">关联</Button>
                </div>
            </Card>
      </li>
    </ul>
    <!-- <Row :gutter="16">
        <Col :span="4" v-for="(item, index) in data" class="pt20">
            <Card :padding="0">
                <div>
                    <img v-if="item.image_url && item.image_url[0]" :src="item.image_url[0]" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="130px">
                    <img v-else src="../../../../static/img/goods-list-no-picture1.png" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="130px">
                </div>
                <div class="pd20 tc">
                    <h3 class="ell" @click="detail(item.id, item.type, item.account)" v-if="item.service_name" > &nbsp;{{item.service_name}}</h3>
                    <h3 class="ell" v-else style="line-height:24px;"> &nbsp;</h3>
                    <Button type="primary" v-if="isRelation" size="small" class="mt10" @click="unLink(item)">已关联</Button>
                    <Button type="default" v-else size="small" class="mt10" @click="beLink(item)">关联</Button>
                </div>
            </Card>
        </Col>
    </Row> -->
    <p v-if="!data.length" class="tc pd20" style="font-size:16px;">暂无相关数据</p>
  </div>
</template>
<script>
export default {
  props: {
    isRelation: {
      type: Boolean,
      default: true
    }, // 默认已关联
    data: {
      type: Array,
      default: () => {
        return []
      }
    }, // 默认已关联
  },
  data () {
    return {
      id: '',
    }
  },
  created() {
    this.id = this.$route.query.id
  },
  methods: {
    detail (id, type, account) {
         this.$router.push({
                path: `/InforMation/serviceDetail`,
                query: {
                    id: id,
                    uid: account,
                    type: type,
                }
            })
    },
    // 未关联 添加关联 0不关联 1 关联
    beLink (item) {
      let data = {
        serviceId: this.id,
        type: item.type,
        joinServiceId: item.id,
        joinService: '1',
      }
      this.$api.post('/member/fishing/saveJoinServiceInfo', data).then(response => {
        if (response.code) {
          this.$Message.success('操作成功！')
          this.$emit('on-init')
        } else {
          this.$Message.error('操作失败！')
        }
      })
    },
    // 已关联 取消关联
    unLink (item) {
      let data = {
        id: item.serviceJoinId,
      }
      this.$api.post('/member/fishing/deleteJoinServiceInfo', data).then(response => {
        if (response.code) {
          this.$Message.success('操作成功！')
          this.$emit('on-init')
        } else {
          this.$Message.error('操作失败！')
        }
      })
    }
  },
}
</script>
<style scpoed>
.card-li{
  width: calc(100% / 5 - 16px);
  display: inline-block;
  margin: 0px 8px 0 8px;
}
</style>
