<template>
  <div>
    <Row class="traffic-table-bus pt20" v-if="data.busLead.length">
      <Col span="4" class="tc">
        <Icon type="md-bus" class="t-green pl5" size="22" style="vertical-align: top;"/> 公交路线指引
      </Col>
      <Col span="20" :offset="index > 0 ? '4' : '0'" class="tl" v-for="(item, index) in data.busLead" :key="item">
        <p style="word-wrap:break-word;">{{item.name}}</p>
      </Col>
    </Row>
    <Row class="traffic-table-bus pt20" v-if="data.traffic_lead.length">
      <Col span="4" class="tc">
        <Icon type="ios-car" class="t-green pl5" size="22" style="vertical-align: top;"/> 自驾路线指引
      </Col>
      <Col span="20" :offset="index > 0 ? '4' : '0'" class="tl" v-for="(item, index) in data.traffic_lead" :key="item">
        <p style="word-wrap:break-word;">{{item.name}}</p>
      </Col>
    </Row>
    <div v-if="data.contact.length">
      <Row class="traffic-table-bus pd20" v-for="(item, index) in data.contact" :key="item">
        <Col span="24"class="pt30">
            <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.longitude,item.latitude}&output=html`">
                <img :src="`//api.map.baidu.com/staticimage?height=200&center=${item.longitude},${item.latitude}&zoom=15&scale=2&markers=${item.longitude},${item.latitude}`" alt="" width="100%">
            </a>
        </Col>
      </Row>
    </div>
  </div>
</template>

<script>
  export default {
    name: '',
    props: {
      datas: {
        type: Object,
        default: () => {
          return {
            contact: [],
            traffic_lead: [],
            busLead: []
          }
        }
      }
    },
    data() {
      return {
        data: {
          contact: [],
          traffic_lead: [],
          busLead: []
        }
      }
    },
    watch: {
      datas: {
        handler(newInfo, oldInfo) {
          // ...
          this.getInfo(newInfo)
        },
        immediate: true
      }
    },
    created() {
      this.getInfo(this.datas)
    },
    methods: {
      getInfo (newInfo) {
        if (newInfo.contact) {
          this.data.contact = newInfo.contact
        } else {
          this.data.contact = []
        }
        if (newInfo.traffic_lead) {
          this.data.traffic_lead = newInfo.traffic_lead
        } else {
          this.data.traffic_lead = []
        }
        if (newInfo.busLead) {
          this.data.busLead = newInfo.busLead
        } else {
          this.data.busLead = []
        }
      }
    }
  }
</script>

<style lang="less" scoped>

</style>
