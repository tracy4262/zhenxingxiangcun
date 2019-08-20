<template>
    <div>
    	<div class="ivu-table ivu-table-border ivu-table-small table">
            <table>
            	<thead class="ivu-table-header tc">
                    <tr>
                        <th>项目</th>
                        <th>数量</th>
                        <th>计量单位</th>
                    </tr>
                </thead>
                <tbody class="ivu-table-body">
                    <tr>
                        <td>生活水来源</td>
                        <td>
                        	<RadioGroup v-model="detailsData.domesticWater">
    					        <Radio label="自来水"></Radio>
    					        <Radio label="井水"></Radio>
    					    </RadioGroup>
                        </td>
                        <td></td>
                    </tr>

                    <tr>
                        <td>方便</td>
                        <td>
                        	<RadioGroup v-model="detailsData.convenient">
    					        <Radio label="是"></Radio>
    					        <Radio label="否"></Radio>
    					    </RadioGroup>
                        </td>
                        <td></td>
                    </tr>

                    <tr>
                        <td>安全</td>
                        <td>
                        	<RadioGroup v-model="detailsData.safe">
    					        <Radio label="是"></Radio>
    					        <Radio label="否"></Radio>
    					    </RadioGroup>
                        </td>
                        <td></td>
                    </tr>

                    <tr>
                        <td>水价</td>
                        <td><Input v-model="detailsData.waterPrice" /></td>
                        <td>元／m³</td>
                    </tr>

                    <tr>
                        <td>灌溉能力</td>
                        <td><Input v-model="detailsData.irrigationPower" /></td>
                        <td>m³／s</td>
                    </tr>

                    <tr>
                        <td>排水能力</td>
                        <td><Input v-model="detailsData.drainability" /></td>
                        <td>m³／s</td>
                    </tr>

                    <tr>
                        <td>洪灾</td>
                        <td>
                        	<RadioGroup v-model="detailsData.flood">
    					        <Radio label="不"></Radio>
    					        <Radio label="很少"></Radio>
    					        <Radio label="经常"></Radio>
    					    </RadioGroup>
                        </td>
                        <td></td>
                    </tr>

                    <tr>
                        <td>旱灾</td>
                        <td>
                        	<RadioGroup v-model="detailsData.drought">
    					        <Radio label="不"></Radio>
    					        <Radio label="很少"></Radio>
    					        <Radio label="经常"></Radio>
    					    </RadioGroup>
                        </td>
                        <td></td>
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
			detailsData: {
				domesticWater: '',
				convenient: '',
				safe: '',
                waterPrice: '',
                irrigationPower: '',
                drainability: '',
				flood: '',
				drought: '',
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
            api.post('/member/product-water-condition/query', {
                productId: this.$route.query.id
            })
            .then(response => {
                if(response.data === undefined){
                    this.detailsData = this.detailsData
                }else{
                    if(response.data.domesticWater === 'T'){
                        response.data.domesticWater = '自来水'
                    }else{
                        response.data.domesticWater = '井水'
                    }

                    if(response.data.convenient === 'Y'){
                        response.data.convenient = '是'
                    }else{
                        response.data.convenient = '否'
                    }

                    if(response.data.safe === 'Y'){
                        response.data.safe = '是'
                    }else{
                        response.data.safe = '否'
                    }

                    if(response.data.flood === 'N'){
                        response.data.flood = '不'
                    }else if(response.data.flood === 'L'){
                        response.data.flood = '很少'
                    }else{
                        response.data.flood = '经常'
                    }

                    if(response.data.drought === 'N'){
                        response.data.drought = '不'
                    }else if(response.data.drought === 'L'){
                        response.data.drought = '很少'
                    }else{
                        response.data.drought = '经常'
                    }

                    this.detailsData = response.data
                }
            })
        },

        preservation(){
            let that = this

            if(this.detailsData.domesticWater === '自来水'){
                this.detailsData.domesticWater = 'T'
            }else{
                this.detailsData.domesticWater = 'W'
            }

            if(this.detailsData.convenient === '是'){
                this.detailsData.convenient = 'Y'
            }else{
                this.detailsData.convenient = 'N'
            }

            if(this.detailsData.safe === '是'){
                this.detailsData.safe = 'Y'
            }else{
                this.detailsData.safe = 'N'
            }

            if(this.detailsData.flood === '不'){
                this.detailsData.flood = 'N'
            }else if(this.detailsData.flood === '很少'){
                this.detailsData.flood = 'L'
            }else{
                this.detailsData.flood = 'O'
            }

            if(this.detailsData.drought === '不'){
                this.detailsData.drought = 'N'
            }else if(this.detailsData.drought === '很少'){
                this.detailsData.drought = 'L'
            }else{
                this.detailsData.drought = 'O'
            }

            api.post('/member/product-water-condition/save', {
                productId: this.$route.query.id,
                data: this.detailsData
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
