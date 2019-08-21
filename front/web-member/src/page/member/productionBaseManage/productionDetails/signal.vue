<template>
    <div>
    	<div class="ivu-table ivu-table-border ivu-table-small table">
            <table>
            	<thead class="ivu-table-header tc">
                    <tr>
                        <th>通信形式</th>
                        <th>是否拥有</th>
                    </tr>
                </thead>
                <tbody class="ivu-table-body">
                    <tr>
                        <td>邮电业务</td>
                        <td>
                        	<RadioGroup v-model="detailsData.postTelephoneService">
    					        <Radio label="是"></Radio>
    					        <Radio label="否"></Radio>
    					    </RadioGroup>
                        </td>
                    </tr>

                    <tr>
                        <td>固定电话</td>
                        <td>
                        	<RadioGroup v-model="detailsData.fixedTelephone">
    					        <Radio label="是"></Radio>
    					        <Radio label="否"></Radio>
    					    </RadioGroup>
                        </td>
                    </tr>

                    <tr>
                        <td>宽带端口</td>
                        <td>
                        	<RadioGroup v-model="detailsData.broadbandPort">
    					        <Radio label="是"></Radio>
    					        <Radio label="否"></Radio>
    					    </RadioGroup>
                        </td>
                    </tr>

                    <tr>
                        <td>TV端口</td>
                        <td>
                        	<RadioGroup v-model="detailsData.tvPort">
    					        <Radio label="是"></Radio>
    					        <Radio label="否"></Radio>
    					    </RadioGroup>
                        </td>
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
				postTelephoneService: '',
				fixedTelephone: '',
				broadbandPort: '',
				tvPort: '',
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
            api.post('/member/product-network-communications/query', {
                productId: this.$route.query.id
            })
            .then(response => {
                if(response.data === undefined){
                    this.detailsData = this.detailsData
                }else{
                    if(response.data.postTelephoneService === 'Y'){
                        response.data.postTelephoneService = '是'
                    }else{
                        response.data.postTelephoneService = '否'
                    }

                    if(response.data.fixedTelephone === 'Y'){
                        response.data.fixedTelephone = '是'
                    }else{
                        response.data.fixedTelephone = '否'
                    }

                    if(response.data.broadbandPort === 'Y'){
                        response.data.broadbandPort = '是'
                    }else{
                        response.data.broadbandPort = '否'
                    }

                    if(response.data.tvPort === 'Y'){
                        response.data.tvPort = '是'
                    }else{
                        response.data.tvPort = '否'
                    }

                    this.detailsData = response.data
                }
            })
        },

        preservation(){
            let that = this

            if(this.detailsData.postTelephoneService === '是'){
                this.detailsData.postTelephoneService = 'Y'
            }else{
                this.detailsData.postTelephoneService = 'N'
            }

            if(this.detailsData.fixedTelephone === '是'){
                this.detailsData.fixedTelephone = 'Y'
            }else{
                this.detailsData.fixedTelephone = 'N'
            }

            if(this.detailsData.broadbandPort === '是'){
                this.detailsData.broadbandPort = 'Y'
            }else{
                this.detailsData.broadbandPort = 'N'
            }

            if(this.detailsData.tvPort === '是'){
                this.detailsData.tvPort = 'Y'
            }else{
                this.detailsData.tvPort = 'N'
            }


            api.post('/member/product-network-communications/save', {
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