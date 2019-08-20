<template>
	<div class="maLand">
	    <Row v-show="isShow">
		    <Row class="ma-btn">
		    	<Col span="4" offset="1">
		    		<DatePicker type="year" @on-change="searchData" placeholder="请选择时间"></DatePicker>
		    	</Col>
		    	<Col span="4" offset="15">
		    		<Button type="primary" @click="addData">新增地块</Button>
		    	</Col>
		    </Row>
		    <Row class="ma-table">
		    	<Col span="24">
		    		<Row>
				    	<Col span="24">
				    		<Table @on-expand="expand" :columns="landHead" :data="landHeadData" accordion></Table>
				    	</Col>
				    </Row>
				    <br>
				    <Row>
				    	<Col span="24">
						    <p class="ma_text">{{describe}}</p>
						</Col>
					</Row>
		    	</Col>
		    </Row>
		</Row>
		<Row v-if="isAddSimilar">
			<Row>
		    	<Col span="24">
				    <add-similar :ismMassif="ismMassif" :isAddor="isAddor" :isAddorData="isAddorData" @add="changeAdd" @reset="changeReset"></add-similar>	
				</Col>
			</Row>
		</Row>
		<Row v-if="isTen">
			<Row>
		    	<Col span="20" offset="2">
				    <is-ten :tenData="tenData" @isOk="changeIsOk"></is-ten>	
				</Col>
			</Row>
		</Row>
		<Row v-if="isEleven">
			<Row>
		    	<Col span="20" offset="2">
				    <is-eleven :elevenData="elevenData" @isOks="changeIsOks"></is-eleven>	
				</Col>
			</Row>
		</Row>

		<vui-map 
        ref="vuiMap"
        @on-get-point="onGetPoint"
        ></vui-map>
	</div>
</template>

<script>
import vuiMap from '../../components/productionMap'
import expandRow from './table-expand.vue'
import addSimilar from './children/addSimilar.vue'
import isTen from './children/isTen.vue'
import isEleven from './children/isEleven.vue'
import api from '~api'
export default {
	components: {
		expandRow,
		addSimilar,
		isTen,
		isEleven,
		vuiMap
	},
	data() {
		return {
			// 是新增同类地块还是新增地块
			isAddor: 0,
			isAddSimilar: false,

			// 土地利用分类
			isAddorData: '',
			isShow: true,
			isTen: false,
			tenData: {},
			isEleven: false,
			elevenData: {},
			ismMassif: {},
			describe: '',

			index: 0,
			landHead: [
				{
                    type: 'expand',
                    width: 50,
                    render: (h, params) => {
                        return h(expandRow, {
                            props: {
                                row: params.row
                            },
                            on: {
                                click: () => {
                                }
                            }
                        })
                    }
                },
				{
                    title: '土地类型',
                    key: 'AllLandUsed'
                },
                {
                    title: '总数量/块',
                    key: 'landUsedCount',
                },
                {
                    title: '占地总面积／平方米',
                    key: 'landUsedTotalArea'
                },
                {
                    title: '操作',
                    key: 'faFour',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                attrs:{
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                    	this.isShow = false
                                    	this.isAddSimilar = true
                                    	this.isAddor = 1
                                    	// 记录土地利用分类
                                    	this.isAddorData = params.row.children.data["0"].landType+ '-'+params.row.children.data["0"].landUsed
                                    }
                                }
                            }, '新增同类地块')
                        ])
                    }
                }
			],
			landHeadData: [
			]
		}
	},
	watch: {
	},
	mounted() {
	},
	created(){
		this.getData()
	},
	methods: {
		// 获取数据
        getData(){
        	let that = this
            api.post('/member/product-land-use-quo/plot-query', {
                productId: this.$route.query.id
            })
            .then(response => {
            	if(response.code === 200){
            		if(response.data === undefined){
	                    this.landHeadData = []
	                    that.describe = ''
	                }else{
	                	let obj = []
						that.landHeadData.length = response.data.list.length
						
						response.data.list.forEach(function(value,index){
							obj[index] = {
								AllLandUsed: response.data.list[index].AllLandUsed,
								landUsedCount: response.data.list[index].landUsedCount,
								landUsedTotalArea: response.data.list[index].landUsedTotalArea,
								index: index,
								children: {
									columns: [
					                    {
					                        title: '土地类型',
					                        key: 'landType'
					                    },
					                    {
					                        title: '土地用途',
					                        key: 'landUsed'
					                    },
					                    {
					                        title: '土地权属人',
					                        key: 'landowner'
					                    },
					                    {
					                        title: '地块编号',
					                        key: 'landNumber'
					                    },
					                    {
					                        title: '地块名称',
					                        key: 'plotName'
					                    },
					                    {
					                        title: '地块面积',
					                        key: 'landArea'
					                    },
					                    {
					                        title: '单位',
					                        key: 'unitArea'
					                    },
					                    {
					                        title: '利用现状',
					                        key: 'situation'
					                    },
					                    {
					                        title: '地图标示',
					                        key: 'mapSign',
					                        render: (h, params) => {
					                        	let fontColor = '#00c587'
					                            return h('div', [
					                                h('a', {
					                                    props: {
					                                    },
					                                    attrs:{
					                                    },
					                                    style: {
					                                        marginRight: '5px',
					                                        color: params.row.mapSign === '已标示'?fontColor = '#00c587':fontColor = '#333'
					                                    },
					                                    on: {
					                                        click: () => {
					                                        	if(params.row.mapSign === '已标示'){
					                                        		that.$refs.vuiMap.showMap = true
					                                        	}else{
					                                        		return false
					                                        	}
					                                            
					                                        }
					                                    }
					                                }, params.row.mapSign)
					                            ]);
					                    	}
					                    },
					                    {
					                        title: '土壤信息',
					                        key: 'soilInfo',
					                        render: (h, params) => {
					                        	let fontColor = '#00c587'
					                            return h('div', [
					                                h('a', {
					                                    props: {
					                                    },
					                                    attrs:{
					                                    },
					                                    style: {
					                                        marginRight: '5px',
					                                        color: params.row.soilInfo === '已上传'?fontColor = '#00c587':fontColor = '#333'
					                                    },
					                                    on: {
					                                        click: () => {
					                                        	if(params.row.soilInfo === '已上传'){
					                                        		that.isShow = false
			                                        				that.isTen = true
			                                        				that.tenData = params.row
					                                        	}else{
					                                        		return false
					                                        	}
					                                            
					                                        }
					                                    }
					                                }, params.row.soilInfo)
					                            ]);
					                    	}
					                    },
					                    {
					                        title: '水质信息',
					                        key: 'waterQualityInfo',
					                        render: (h, params) => {
					                        	let fontColor = '#00c587'
					                            return h('div', [
					                                h('a', {
					                                    props: {
					                                    },
					                                    attrs:{
					                                    },
					                                    style: {
					                                        marginRight: '5px',
					                                        color: params.row.waterQualityInfo === '已上传'?fontColor = '#00c587':fontColor = '#333'
					                                    },
					                                    on: {
					                                        click: () => {
					                                        	if(params.row.waterQualityInfo === '已上传'){
					                                        		that.isShow = false
			                                        				that.isEleven = true
			                                        				that.elevenData = params.row
					                                        	}else{
					                                        		return false
					                                        	}
					                                            
					                                        }
					                                    }
					                                }, params.row.waterQualityInfo)
					                            ]);
					                    	}
					                    },
					                    {
					                        title: '操作',
					                        key: 'twelve',
					                        render: (h, params) => {
					                            return h('div', [
					                                h('Button', {
					                                    props: {
					                                        type: 'primary',
					                                        size: 'small'
					                                    },
					                                    style: {
					                                        marginRight: '5px'
					                                    },
					                                    on: {
					                                        click: () => {
					                                        	that.isShow = false
					                                        	that.isAddSimilar = true
	                                    						that.isAddor = 2
	                                    						that.ismMassif = params.row
	                                    						// 记录土地利用分类
	                                    						that.isAddorData = params.row.landType+ '-'+params.row.landUsed
					                                        }
					                                    }
					                                }, '编辑'),
					                                h('Button', {
					                                    props: {
					                                        type: 'error',
					                                        size: 'small'
					                                    },
					                                    on: {
					                                        click: () => {
					                                        	let id = params.row.landId
					                                            api.post('/member/product-land-use-quo/plot-delete', {
													                landId: id
													            })
													            .then(response => {
													            	that.remove(params.index)
													                that.getData()
													            })
					                                        }
					                                    }
					                                }, '删除')
					                            ]);
			                        		}
					                    },
					                ],	
									data:response.data.list[index]["landUseClasses" + index]
								}
							}
						})
						that.landHeadData = obj
						that.describe = response.data.describe
	                }
            	}
                
            })
        },

		// 列表打开-新增土地快按钮显示
		expand(data,status){
			this.index = data.index
		},

		// 土壤信息 - 查看详情确定事件
		changeIsOk(){
			this.isShow = true
			this.isTen = false
			this.getData()
		},

		// 水质信息 - 查看详情确定事件
		changeIsOks(){
			this.isShow = true
			this.isEleven = false
			this.getData()
		},

		// 保存
		changeAdd(){
			this.isAddSimilar = false
			this.isShow = true
			this.getData()
		},

		// 取消
		changeReset(){
			this.isAddSimilar = false
			this.isShow = true
			this.getData()
		},

		// 新增地块
		addData(){
			let that = this
			this.isShow = false
            this.isAddSimilar = true
            this.isAddor = 0
		},

		// 删除
		remove (index) {
			let obj = this.landHeadData[this.index].children.data
            obj.splice(index, 1)
        },

        // 取坐标
        onGetPoint(){

        },

        searchData(e){
        	let that = this
        	api.post('/member/product-land-use-quo/plot-query', {
                productId: this.$route.query.id,
                createTime: e
            })
            .then(response => {
            	if(response.data === undefined){
                    this.landHeadData = []
                    that.describe = ''
                }else{
                	let obj = []
					that.landHeadData.length = response.data.list.length
					
					response.data.list.forEach(function(value,index){
						obj[index] = {
							AllLandUsed: response.data.list[index].AllLandUsed,
							landUsedCount: response.data.list[index].landUsedCount,
							landUsedTotalArea: response.data.list[index].landUsedTotalArea,
							index: index,
							children: {
								columns: [
				                    {
				                        title: '土地类型',
				                        key: 'landType'
				                    },
				                    {
				                        title: '土地用途',
				                        key: 'landUsed'
				                    },
				                    {
				                        title: '土地权属人',
				                        key: 'landowner'
				                    },
				                    {
				                        title: '地块编号',
				                        key: 'landNumber'
				                    },
				                    {
				                        title: '地块名称',
				                        key: 'plotName'
				                    },
				                    {
				                        title: '地块面积',
				                        key: 'landArea'
				                    },
				                    {
				                        title: '单位',
				                        key: 'unitArea'
				                    },
				                    {
				                        title: '利用现状',
				                        key: 'situation'
				                    },
				                    {
				                        title: '地图标示',
				                        key: 'mapSign',
				                        render: (h, params) => {
				                        	let fontColor = '#00c587'
				                            return h('div', [
				                                h('a', {
				                                    props: {
				                                    },
				                                    attrs:{
				                                    },
				                                    style: {
				                                        marginRight: '5px',
				                                        color: params.row.mapSign === '已标示'?fontColor = '#00c587':fontColor = '#333'
				                                    },
				                                    on: {
				                                        click: () => {
				                                        	if(params.row.mapSign === '已标示'){
				                                        		that.$refs.vuiMap.showMap = true
				                                        	}else{
				                                        		return false
				                                        	}
				                                            
				                                        }
				                                    }
				                                }, params.row.mapSign)
				                            ]);
				                    	}
				                    },
				                    {
				                        title: '土壤信息',
				                        key: 'soilInfo',
				                        render: (h, params) => {
				                        	let fontColor = '#00c587'
				                            return h('div', [
				                                h('a', {
				                                    props: {
				                                    },
				                                    attrs:{
				                                    },
				                                    style: {
				                                        marginRight: '5px',
				                                        color: params.row.soilInfo === '已上传'?fontColor = '#00c587':fontColor = '#333'
				                                    },
				                                    on: {
				                                        click: () => {
				                                        	if(params.row.soilInfo === '已上传'){
				                                        		that.isShow = false
		                                        				that.isTen = true
		                                        				that.tenData = params.row
				                                        	}else{
				                                        		return false
				                                        	}
				                                            
				                                        }
				                                    }
				                                }, params.row.soilInfo)
				                            ]);
				                    	}
				                    },
				                    {
				                        title: '水质信息',
				                        key: 'waterQualityInfo',
				                        render: (h, params) => {
				                        	let fontColor = '#00c587'
				                            return h('div', [
				                                h('a', {
				                                    props: {
				                                    },
				                                    attrs:{
				                                    },
				                                    style: {
				                                        marginRight: '5px',
				                                        color: params.row.waterQualityInfo === '已上传'?fontColor = '#00c587':fontColor = '#333'
				                                    },
				                                    on: {
				                                        click: () => {
				                                        	if(params.row.waterQualityInfo === '已上传'){
				                                        		that.isShow = false
		                                        				that.isEleven = true
		                                        				that.elevenData = params.row
				                                        	}else{
				                                        		return false
				                                        	}
				                                            
				                                        }
				                                    }
				                                }, params.row.waterQualityInfo)
				                            ]);
				                    	}
				                    },
				                    {
				                        title: '操作',
				                        key: 'twelve',
				                        render: (h, params) => {
				                            return h('div', [
				                                h('Button', {
				                                    props: {
				                                        type: 'primary',
				                                        size: 'small'
				                                    },
				                                    style: {
				                                        marginRight: '5px'
				                                    },
				                                    on: {
				                                        click: () => {
				                                        	that.isShow = false
				                                        	that.isAddSimilar = true
                                    						that.isAddor = 2
                                    						that.ismMassif = params.row
                                    						// 记录土地利用分类
                                    						that.isAddorData = params.row.landType+ '-'+params.row.landUsed
				                                        }
				                                    }
				                                }, '编辑'),
				                                h('Button', {
				                                    props: {
				                                        type: 'error',
				                                        size: 'small'
				                                    },
				                                    on: {
				                                        click: () => {
				                                        	let id = params.row.landId
				                                            api.post('/member/product-land-use-quo/plot-delete', {
												                landId: id
												            })
												            .then(response => {
												            	that.remove(params.index)
												                that.getData()
												            })
				                                        }
				                                    }
				                                }, '删除')
				                            ]);
		                        		}
				                    },
				                ],	
								data:response.data.list[index]["landUseClasses" + index]
							}
						}
					})
					that.landHeadData = obj
					that.describe = response.data.describe
                }
            })
        }
	}
}
</script>

<style scoped>
.ma-btn{line-height: 60px;}
.maLand .ivu-row img{width: 100%;}
.ma_text{padding: 10px 5px;}
</style>
