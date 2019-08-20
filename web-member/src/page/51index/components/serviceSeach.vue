<template>
  <div>
    <Row type="flex" align="middle" class="pt30 pb20">
      <Col span="10" offset="7">
        <Input v-model="info.service_name" search enter-button :placeholder="placeholder" @on-search="onSearch"/>
      </Col>
      <Col span="7" class="pl10">
      <Button @click="clickShow = !clickShow" class="t-green" type="text">
        <Icon type="ios-funnel-outline" size="18" /> 高级搜索
      </Button>
      </Col>
    </Row>
    <Form :label-width="90" v-if="clickShow" class="pt10">
      <Row>
        <Col span="6" offset="3">
            <FormItem label="行政区划">
              <Cascader 
                :data="locationList" 
                change-on-select 
                :render-format="formats" 
                :load-data="loadPositionDatas"></Cascader>
              <!-- <vui-cascander :values="info.address" @handle-get-result="handleGetData"></vui-cascander> -->
            </FormItem>
        </Col>
        <Col span="6">
            <FormItem label="相关物种">
              <vuiSpecies :values="info.species" ref="vuiSpecies" @on-save="onSaveSpecies" @on-save-id="onSaveSpeciesId" :num="1"></vuiSpecies>
            </FormItem>
        </Col>
        <Col span="6">
            <FormItem label="相关行业">
               <vuiTrade :values="info.industry" ref="vuiTrade" @on-save="onSaveTrade" @on-save-id="onSaveTradeId" :num="1"></vuiTrade>
            </FormItem>
          </Col>
      </Row>
    </Form>
  </div>
</template>
<script>
import vuiSpecies from '~components/vui-species'
import vuiTrade from '~components/vui-trade'
import vuiCascander from '~components/vuiCascader/index'
export default {
  components: {
    vuiSpecies,
    vuiTrade,
    vuiCascander
  },
  props: {
    keyWord: {
      type: String
    },
    placeholder: {
      type: String,
      default: '请输入服务名称进行搜索'
    }
  },
  data () {
    return {
      info: {
        address: '',
        species: '',
        speciesId: '',
        industry: '',
        industryId: '',
        service_name: this.keyWord
      },
      clickShow: false,
      locationList: [],
    }
  },
  created() {
    // 取地址
    this.$api.post("/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816").then(res => {
      this.locationList = res.data
    });
  },
  methods: {
    onSearch () {
      console.log(this.info)
      this.$emit('on-search', this.info)
    },
    loadPositionDatas(item, callback) {
      item.loading = true;
      this.$api.post(`/member/town/next/${item.value}`).then(res => {
        item.loading = false;
        item.children = res.data;
        callback();
      });
    },
    formats (labels, selectedData) {
      var label =''          
      for(var i = 0;i<labels.length;i++){
        if(i==labels.length-1){
          label+=labels[i]
        }else{
          label+=labels[i]+'/'
        } 
      }
      this.info.address = label
      return label
    },
    // handleGetData (value, selectedData) {
    //   let labelArr = []
    //   selectedData.forEach(element => {
    //       labelArr.push(element.label)
    //   })
    //   this.info.address = labelArr.join('/')
    // },
    // 物种
    onSaveSpecies (e) {
      this.info.species = e
      console.log(this.info)
    },
    onSaveSpeciesId (e) {
      this.info.speciesId = e
      console.log(this.info)
    },
    // 行业分类
    onSaveTrade (e) {
      this.info.industry = e
    },
    onSaveTradeId (e) {
      this.info.industryId = e
    },
  },
}
</script>