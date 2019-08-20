<template>
    <div class="ma-table-height">
        <div class="ivu-table ivu-table-border ivu-table-small table">
          <table>
            <thead class="ivu-table-header tc">
                  <tr>
                      <th>序号</th>
                      <th>项目类别</th>
                      <th>指标</th>
                      <th>单位</th>
                      <th>本企业数据</th>
                  </tr>
              </thead>
              <tbody class="ivu-table-body">
                  <tr>
                      <td>1</td>
                      <td>ph</td>
                      <td>6.8-8.5</td>
                      <td>mg/L</td>
                      <td><p>{{formData.waterPH}}</p></td>
                  </tr>

                  <tr>
                      <td>2</td>
                      <td>氰化物</td>
                      <td>≤0.05</td>
                      <td>mg/L</td>
                      <td><p>{{formData.cyanide}}</p></td>
                  </tr>

                  <tr>
                      <td>3</td>
                      <td>氟化物</td>
                      <td>≤1.5</td>
                      <td>mg/L</td>
                      <td><p>{{formData.fluoride}}</p></td>
                  </tr>

                  <tr>
                      <td>4</td>
                      <td>氨化物</td>
                      <td>≤250</td>
                      <td>mg/L</td>
                      <td><p>{{formData.chloride}}</p></td>
                  </tr>

                  <tr>
                      <td>5</td>
                      <td>砷</td>
                      <td>≤0.05</td>
                      <td>mg/L</td>
                      <td><p>{{formData.arsenic}}</p></td>
                  </tr>

                  <tr>
                      <td>6</td>
                      <td>汞</td>
                      <td>≤0.001</td>
                      <td>mg/L</td>
                      <td><p>{{formData.mercury}}</p></td>
                  </tr>

                  <tr>
                      <td>7</td>
                      <td>铅</td>
                      <td>≤0.05</td>
                      <td>mg/L</td>
                      <td><p>{{formData.lead}}</p></td>
                  </tr>

                  <tr>
                      <td>8</td>
                      <td>镉</td>
                      <td>≤0.01</td>
                      <td>mg/L</td>
                      <td><p>{{formData.cadmium}}</p></td>
                  </tr>

                  <tr>
                      <td>9</td>
                      <td>铭（+5）</td>
                      <td>≤0.05</td>
                      <td>mg/L</td>
                      <td><p>{{formData.chromium}}</p></td>
                  </tr>

                  <tr>
                      <td>10</td>
                      <td>粪大肠杆菌（个／升）</td>
                      <td>≤10</td>
                      <td>mg/L</td>
                      <td><p>{{formData.fecalColiform}}</p></td>
                  </tr>
              </tbody>
          </table>
        </div>
        <br>
        <Row>
            <h4 class="ma-addSimilarH4">检测报告</h4>
            <Row class="ma-elevenImg">
                <Col span="24">
                  <template v-for="item in urlList">
                    <img :src="item.reportUrl">
                  </template>
                </Col>
            </Row>
        </Row>
        <br>
        <Row>
            <Col span="24">
                <p class="ma_text">{{formData.describe}}</p>
            </Col>
        </Row>
        <br>
        <div class="ma-button">
          <Button type="primary" @click="isOk">确定</Button>
        </div>
    </div>
</template>
<script>
import api from '~api'
export default {
	data() {
		return {
      formData: {
          waterPH: '',
          cyanide: '',
          fluoride: '',
          chloride: '',
          arsenic: '',
          mercury: '',
          lead: '',
          cadmium: '',
          chromium: '',
          fecalColiform: '',
          describe: ''
      },
      urlList: [
      ]
		}
	},
  props: {
    elevenData: {
      type: Object
    }
  },
  created(){
    this.getData()
	},
  methods: {
    getData(){
      let that = this
      api.post('/member/product-land-use-quo/query-all', {
            landId: that.elevenData.landId
        })
        .then(response => {
            if(response.code === 200){
                that.formData = response.data.landWaterQualityMap
                that.urlList = response.data.reportMap
                if(response.data.landWaterQualityMap.describe === undefined){
                  that.formData.describe = ''
                }
            }
            
        })
    },

  	isOk(){
      this.$emit('isOks')
    }
	}
};
</script>
<style scoped>
    .expand-row{
        margin-bottom: 16px;
    }
    .ma-addSimilarH4{margin-bottom: 10px;}
    .ma-elevenImg img{display: inline-block;border: 1px solid transparent;
      width: 80px;height: 80px;border-radius: 4px;margin-right: 4px;
      box-shadow: 0 1px 1px rgba(0,0,0,.2);
    }
    .ma-table-height .ivu-table{height: auto;}
    .ma-button{text-align: center;padding: 20px 0;}
</style>