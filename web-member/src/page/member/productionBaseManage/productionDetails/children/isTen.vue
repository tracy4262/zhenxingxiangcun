<template>
    <div>
        <Row>
            <Col span="24">
                <Tabs type="card">
                  <TabPane label="土壤营养（化学）指标">
                    <div class="ivu-table ivu-table-border ivu-table-small table">
                      <table>
                        <thead class="ivu-table-header tc">
                              <tr>
                                  <th>名称</th>
                                  <th>内容</th>
                              </tr>
                          </thead>
                          <tbody class="ivu-table-body">
                              <tr>
                                  <td>全氮</td>
                                  <td><p>{{nutrition.nitrogen}}</p></td>
                              </tr>

                              <tr>
                                  <td>全磷</td>
                                  <td><p>{{nutrition.phosphorus}}</p></td>
                              </tr>

                              <tr>
                                  <td>全钾</td>
                                  <td><p>{{nutrition.kalium}}</p></td>
                              </tr>

                              <tr>
                                  <td>碱解氮</td>
                                  <td><p>{{nutrition.alkaliHydrolysableNitrogen}}</p></td>
                              </tr>

                              <tr>
                                  <td>有效磷</td>
                                  <td><p>{{nutrition.effectivePhosphorus}}</p></td>
                              </tr>

                              <tr>
                                  <td>有效钾</td>
                                  <td><p>{{nutrition.availablePotassium}}</p></td>
                              </tr>

                              <tr>
                                  <td>速效磷</td>
                                  <td><p>{{nutrition.rapidAvailablePhosphorus}}</p></td>
                              </tr>

                              <tr>
                                  <td>速效钾</td>
                                  <td><p>{{nutrition.rapidlyAvailablePotassium}}</p></td>
                              </tr>

                              <tr>
                                  <td>阳离子交换量</td>
                                  <td><p>{{nutrition.cationExchangeCapacity}}</p></td>
                              </tr>

                              <tr>
                                  <td>碳氮比</td>
                                  <td><p>{{nutrition.carbonNitrogenRatio}}</p></td>
                              </tr>

                              <tr>
                                  <td>有机质</td>
                                  <td><p>{{nutrition.organicMatter}}</p></td>
                              </tr>
                          </tbody>
                      </table>
                      <div class="ma-button">
                        <Button type="primary" @click="isOk">确定</Button>
                      </div>
                    </div>
                  </TabPane>

                  <TabPane label="土壤物理性状指标">
                    <div class="ivu-table ivu-table-border ivu-table-small table">
                      <table>
                        <thead class="ivu-table-header tc">
                              <tr>
                                  <th>名称</th>
                                  <th>内容</th>
                              </tr>
                          </thead>
                          <tbody class="ivu-table-body">
                              <tr>
                                  <td>质地</td>
                                  <td><p>{{physics.physicalCharacter}}</p></td>
                              </tr>

                              <tr>
                                  <td>容重</td>
                                  <td><p>{{physics.volumeWeight}}</p></td>
                              </tr>

                              <tr>
                                  <td>水稳性团聚体</td>
                                  <td><p>{{physics.waterstableAggregate}}</p></td>
                              </tr>

                              <tr>
                                  <td>孔隙度（总孔隙度、毛管孔隙度、非毛管孔隙度）</td>
                                  <td><p>{{physics.porosity}}</p></td>
                              </tr>

                              <tr>
                                  <td>土壤耕层温度变幅</td>
                                  <td><p>{{physics.ploughTemperature}}</p></td>
                              </tr>

                              <tr>
                                  <td>土层厚度</td>
                                  <td><p>{{physics.soilThickness}}</p></td>
                              </tr>

                              <tr>
                                  <td>土壤含水量</td>
                                  <td><p>{{physics.soilWaterContent}}</p></td>
                              </tr>

                              <tr>
                                  <td>粘粒含量</td>
                                  <td><p>{{physics.clayContent}}</p></td>
                              </tr>
                          </tbody>
                      </table>
                      <div class="ma-button">
                        <Button type="primary" @click="isOk">确定</Button>
                      </div>
                    </div>
                  </TabPane>

                  <TabPane label="土壤生物学指标">
                    <div class="ivu-table ivu-table-border ivu-table-small table">
                      <table>
                        <thead class="ivu-table-header tc">
                              <tr>
                                  <th>名称</th>
                                  <th>内容</th>
                              </tr>
                          </thead>
                          <tbody class="ivu-table-body">
                              <tr>
                                  <td>有机质</td>
                                  <td><p>{{biology.organicMatter}}</p></td>
                              </tr>

                              <tr>
                                  <td>腐殖酸（富里酸、胡敏酸）</td>
                                  <td><p>{{biology.humicAcid}}</p></td>
                              </tr>

                              <tr>
                                  <td>微生物态碳</td>
                                  <td><p>{{biology.soilMicrobialBiomassCarbon}}</p></td>
                              </tr>

                              <tr>
                                  <td>微生物态氮</td>
                                  <td><p>{{biology.soilMicrobialNitrogen}}</p></td>
                              </tr>

                              <tr>
                                  <td>土壤酶活性（腺酶、蛋白酶、过氧化氢酶、转化酶、磷酸酶等）</td>
                                  <td><p>{{biology.soilEnzymeActivity}}</p></td>
                              </tr>
                          </tbody>
                      </table>
                      <div class="ma-button">
                        <Button type="primary" @click="isOk">确定</Button>
                      </div>
                    </div>
                  </TabPane>

                  <TabPane label="土壤环境指标">
                    <div class="ivu-table ivu-table-border ivu-table-small table">
                      <table>
                        <thead class="ivu-table-header tc">
                              <tr>
                                  <th>名称</th>
                                  <th>内容</th>
                              </tr>
                          </thead>
                          <tbody class="ivu-table-body">
                              <tr>
                                  <td>土壤ph</td>
                                  <td><p>{{science.soilPH}}</p></td>
                              </tr>

                              <tr>
                                  <td>地下水深度</td>
                                  <td><p>{{science.groundWaterDepth}}</p></td>
                              </tr>

                              <tr>
                                  <td>坡度</td>
                                  <td><p>{{science.gradient}}</p></td>
                              </tr>

                              <tr>
                                  <td>林网化水平</td>
                                  <td><p>{{science.networkForest}}</p></td>
                              </tr>
                          </tbody>
                      </table>
                      <div class="ma-button">
                        <Button type="primary" @click="isOk">确定</Button>
                      </div>
                    </div>
                  </TabPane>

                  <TabPane label="土壤微量元素含量">
                    <div class="ivu-table ivu-table-border ivu-table-small table">
                      <table>
                        <thead class="ivu-table-header tc">
                              <tr>
                                  <th>名称</th>
                                  <th>内容</th>
                              </tr>
                          </thead>
                          <tbody class="ivu-table-body">
                              <tr>
                                  <td>有效铜</td>
                                  <td><p>{{trace.effectiveCopper}}</p></td>
                              </tr>

                              <tr>
                                  <td>有效锌</td>
                                  <td><p>{{trace.availableZinc}}</p></td>
                              </tr>

                              <tr>
                                  <td>有效铁</td>
                                  <td><p>{{trace.availableIron}}</p></td>
                              </tr>

                              <tr>
                                  <td>有效猛</td>
                                  <td><p>{{trace.availableMn}}</p></td>
                              </tr>

                              <tr>
                                  <td>有效钼</td>
                                  <td><p>{{trace.availableMo}}</p></td>
                              </tr>

                              <tr>
                                  <td>有效磞</td>
                                  <td><p>{{trace.availableBoron}}</p></td>
                              </tr>
                          </tbody>
                      </table>
                      <div class="ma-button">
                        <Button type="primary" @click="isOk">确定</Button>
                      </div>
                    </div>
                  </TabPane>
              </Tabs>
            </Col>
        </Row>
    </div>
</template>
<script>
import api from '~api'
export default {
	data() {
		return {
      // 土壤营养2-1
      nutrition: {
          nitrogen: '',
          phosphorus: '',
          kalium: '',
          alkaliHydrolysableNitrogen: '',
          effectivePhosphorus: '',
          availablePotassium: '',
          rapidAvailablePhosphorus: '',
          rapidlyAvailablePotassium: '',
          cationExchangeCapacity: '',
          carbonNitrogenRatio: '',
          organicMatter: ''
      },

      // 土壤物理2-2
      physics: {
          physicalCharacter: '',
          volumeWeight: '',
          waterstableAggregate: '',
          porosity: '',
          ploughTemperature: '',
          soilThickness: '',
          soilWaterContent: '',
          clayContent: ''
      },

      // 土壤生物2-3
      biology: {
          organicMatter: '',
          humicAcid: '',
          soilMicrobialBiomassCarbon: '',
          soilMicrobialNitrogen: '',
          soilEnzymeActivity: ''
      },

      // 土壤环境2-4
      science: {
          soilPH: '',
          groundWaterDepth: '',
          gradient: '',
          networkForest: ''
      },

      // 土壤微量2-5
      trace: {
          effectiveCopper: '',
          availableZinc: '',
          availableIron: '',
          availableMn: '',
          availableMo: '',
          availableBoron: ''
      },
		}
	},
  props: {
    tenData: Object
  },
  created(){
    this.getData()
	},
  methods: {
    getData(){
      let that = this
      api.post('/member/product-land-use-quo/query-all', {
            landId: that.tenData.landId
        })
        .then(response => {
            if(response.data.landNutritiveMap !== undefined){
                that.nutrition = response.data.landNutritiveMap
            }

            if(response.data.landPhysicalMap !== undefined){
                that.physics = response.data.landPhysicalMap
            }

            if(response.data.landBiologicalMap !== undefined){
                that.biology = response.data.landBiologicalMap
            }

            if(response.data.landEnvironmentalMap !== undefined){
                that.science = response.data.landEnvironmentalMap
            }

            if(response.data.landSoilMicroelementlMap !== undefined){
                that.trace = response.data.landSoilMicroelementlMap
            }
        })
    },

  	isOk(){
        this.$emit('isOk')
    }
	}
}
</script>
<style scoped>
    .expand-row{
        margin-bottom: 16px;
    }
    .ma-button{text-align: center;padding: 20px 0;}
</style>