<template>
    <div>
    	<div class="ivu-table ivu-table-border ivu-table-small table">
            <table>
                <tbody class="ivu-table-body">
                    <tr>
                        <td>气候带</td>
                        <td colspan="4">
                        	<Select v-model="detailsData.climaticZone" placeholder="请选择气候带">
    					        <Option v-for="item in climaticList" :value="item.value" :key="item.value">{{ item.name }}</Option>
    					    </Select>
                        </td>
                    </tr>

                    <tr>
                        <td>全年总辐射量</td>
                        <td colspan="3"><Input v-model="detailsData.totalAnnualRadiation" /></td>
                        <td>千卡/平方厘米</td>
                    </tr>

                    <tr>
                        <td>平均日照时间</td>
                        <td><Input v-model="detailsData.avgSunshineTime" /></td>
                        <td>-</td>
                        <td><Input v-model="detailsData.avgSunshineTimeFoot" /></td>
                        <td>小时</td>
                    </tr>

                    <tr>
                        <td>年平均气温</td>
                        <td><Input v-model="detailsData.yearAvgTemperature" /></td>
                        <td>-</td>
                        <td><Input v-model="detailsData.yearAvgTemperatureFoot" /></td>
                        <td>℃</td>
                    </tr>

                    <tr>
                        <td>≥10℃年积温</td>
                        <td><Input v-model="detailsData.accumulatedTemperature" /></td>
                        <td>-</td>
                        <td><Input v-model="detailsData.accumulatedTemperatureFoot" /></td>
                        <td>℃</td>
                    </tr>

                    <tr>
                        <td>日温差</td>
                        <td><Input v-model="detailsData.dailyTemperatureDifference" /></td>
                        <td>-</td>
                        <td><Input v-model="detailsData.dailyTemperatureDifferenceFoot" /></td>
                        <td>℃</td>
                    </tr>

                    <tr>
                        <td>极端最高气温</td>
                        <td><Input v-model="detailsData.maxTemperature" /></td>
                        <td>℃</td>
                        <td><Input v-model="detailsData.maxTemperatureFoot" /></td>
                        <td>维持/天</td>
                    </tr>

                    <tr>
                        <td>极端最低气温</td>
                        <td><Input v-model="detailsData.minTemperature" /></td>
                        <td>℃</td>
                        <td><Input v-model="detailsData.minTemperatureFoot" /></td>
                        <td>维持/天</td>
                    </tr>

                    <tr>
                        <td>极端最高气温多年平均值气温</td>
                        <td><Input v-model="detailsData.avgMaxTemperature" /></td>
                        <td>℃</td>
                        <td><Input v-model="detailsData.avgMaxTemperatureFoot" /></td>
                        <td>维持/天</td>
                    </tr>

                    <tr>
                        <td>极端最低气温多年平均值气温</td>
                        <td><Input v-model="detailsData.avgMinTemperature" /></td>
                        <td>℃</td>
                        <td><Input v-model="detailsData.avgMinTemperatureFoot" /></td>
                        <td>维持/天</td>
                    </tr>

                    <tr>
                        <td>无霜期</td>
                        <td><Input v-model="detailsData.frostFreeSeason" /></td>
                        <td>-</td>
                        <td><Input v-model="detailsData.frostFreeSeasonFoot" /></td>
                        <td>维持/天</td>
                    </tr>

                    <tr>
                        <td>年平均降水量</td>
                        <td><Input v-model="detailsData.avgAnnualPrecipitation" /></td>
                        <td>-</td>
                        <td><Input v-model="detailsData.avgAnnualPrecipitationFoot" /></td>
                        <td>mm</td>
                    </tr>

                    <tr>
                        <td>年平均蒸发量</td>
                        <td><Input v-model="detailsData.avgEvaporationCapacity" /></td>
                        <td>-</td>
                        <td><Input v-model="detailsData.avgEvaporationCapacityFoot" /></td>
                        <td>mm</td>
                    </tr>

                    <tr>
                        <td>年平均降水日</td>
                        <td><Input v-model="detailsData.avgPrecipitationDay" /></td>
                        <td>-</td>
                        <td><Input v-model="detailsData.avgPrecipitationDayFoot" /></td>
                        <td>天</td>
                    </tr>

                    <tr>
                        <td>多年平均干燥度</td>
                        <td colspan="4"><Input v-model="detailsData.avgDryness" /></td>
                    </tr>

                    <tr>
                        <td>多年平均湿润度</td>
                        <td colspan="4"><Input v-model="detailsData.avgWettability" /></td>
                    </tr>

                    <tr>
                        <td>降水量最集中期</td>
                        <td><Input v-model="detailsData.precipitationConcentration" /></td>
                        <td>-</td>
                        <td><Input v-model="detailsData.precipitationConcentrationFoot" /></td>
                        <td>月</td>
                    </tr>

                    <tr>
                        <td>自然灾害</td>
                        <td colspan="4"><Input v-model="detailsData.naturalHazard" /></td>
                    </tr>
                </tbody>
                <tfoot class="ivu-table-foot">
                    <tr>
                        <td colspan="100%">
                            <p class="ma_text">{{detailsData.describe}}</p>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <div class="ma-button">
            <Button type="primary" @click="preservation">保存</Button>
        </div>
    </div>
</template>

<script>
import api from '~api'
export default {
	data() {
		return {
			climaticList: [
				{
					name: '温带季风气候',
					value: '温带季风气候'
				},{
					name: '热带季风气候',
					value: '热带季风气候'
				}
			],
			value1:'',
			isModify: '修改',
			detailsData: {
                avgSunshineTimeFoot: '',
                yearAvgTemperatureFoot: '',
                accumulatedTemperatureFoot: '',
                dailyTemperatureDifferenceFoot: '',
                maxTemperatureFoot: '',
                minTemperatureFoot: '',
                avgMaxTemperatureFoot: '',
                avgMinTemperatureFoot: '',
                frostFreeSeasonFoot: '',
                avgAnnualPrecipitationFoot: '',
                avgEvaporationCapacityFoot: '',
                avgPrecipitationDayFoot: '',
                precipitationConcentrationFoot: '',

                climaticZone: '',
				totalAnnualRadiation: '',
				avgSunshineTime: '',
				yearAvgTemperature: '',
				accumulatedTemperature: '',
				accumulatedTemperature: '',
                dailyTemperatureDifference: '',
				maxTemperature: '',
				minTemperature: '',
				avgMaxTemperature: '',
				avgMinTemperature: '',
				frostFreeSeason: '',
				avgAnnualPrecipitation: '',
				avgEvaporationCapacity: '',
				avgPrecipitationDay: '',
				avgDryness: '',
				avgWettability: '',
				precipitationConcentration: '',
				naturalHazard: '',
				describe: ''
			},

			disabled: true
		}
	},
	watch: {
	},
	created(){
        this.getData()
	},
	methods: {
        // 获取数据
        getData(){
            api.post('/member/product-weather-conditions/query', {
                productId: this.$route.query.id
            })
            .then(response => {
                if(response.data === undefined){
                    this.detailsData = this.detailsData
                }else{
                    this.detailsData = response.data
                }
            })
        },

        preservation(){
            let that = this

            console.log(that.detailsData.avgSunshineTime)
            api.post('/member/product-weather-conditions/save', {
                productId: this.$route.query.id,
                data: that.detailsData
            })
            .then(response => {
                if(response.code === 200){
                    that.getData()
                }
                
            })
        }
	}
}
</script>

<style scoped>
.ma-button{text-align: center;padding: 20px 0;}
.ma_text{padding: 10px 5px;}
</style>
