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
                        <td>变电站</td>
                        <td><Input v-model="detailsData.transformerSubstation" /></td>
                        <td>KV</td>
                    </tr>

                    <tr>
                        <td>最大供电</td>
                        <td><Input v-model="detailsData.maxPowerSupply" /></td>
                        <td>MW</td>
                    </tr>

                    <tr>
                        <td>配变容量</td>
                        <td><Input v-model="detailsData.distributionTransformCapacity" /></td>
                        <td>KMA</td>
                    </tr>

                    <tr>
                        <td>用电负荷</td>
                        <td><Input v-model="detailsData.electricalLoad" /></td>
                        <td>MW</td>
                    </tr>

                    <tr>
                        <td>用电单价</td>
                        <td><Input v-model="detailsData.electricalPrice" /></td>
                        <td>元/度</td>
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
				transformerSubstation: '',
                maxPowerSupply: '',
                distributionTransformCapacity: '',
                electricalLoad: '',
                electricalPrice: '',
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
            api.post('/member/product-electric-power/query', {
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
            api.post('/member/product-electric-power/save', {
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
