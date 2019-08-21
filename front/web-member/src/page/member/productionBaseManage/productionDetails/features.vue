<template>
	<div>
		<div class="ivu-table ivu-table-border ivu-table-small table">
	        <table>
	            <tbody class="ivu-table-body">
	                <tr>
	                    <td>地形</td>
	                    <td colspan="2">
	                    	<Select v-model="detailsData.topography" placeholder="请选择地形">
						        <Option v-for="item in terrainList" :value="item.value" :key="item.value">{{ item.name }}</Option>
						    </Select>
	                    </td>
	                </tr>

	                <tr>
	                    <td>地貌</td>
	                    <td colspan="2">
	                    	<Select v-model="detailsData.physiognomy" placeholder="请选择地貌">
						        <Option v-for="item in landformsList" :value="item.value" :key="item.value">{{ item.name }}</Option>
						    </Select>
	                    </td>
	                </tr>

	                <tr>
	                    <td>平均海拔</td>
	                    <td><Input v-model="detailsData.avgElevation" /></td>
	                    <td>米／千米</td>
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
			terrainList: [
				{
					name: '丘陵',
					value: '丘陵'
				},
				{
					name: '平原',
					value: '平原'
				}
			],
			landformsList: [
				{
					name: '丘陵地貌',
					value: '丘陵地貌'
				},
				{
					name: '平原地貌',
					value: '平原地貌'
				}
			],
			detailsData: {
				topography: '',
				physiognomy: '',
				avgElevation: '',
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
            api.post('/member/product-topography-physiognomy/query', {
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
        	api.post('/member/product-topography-physiognomy/save', {
                productId: this.$route.query.id,
                topography: this.detailsData.topography,
                physiognomy: this.detailsData.physiognomy,
                avgElevation: this.detailsData.avgElevation
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
