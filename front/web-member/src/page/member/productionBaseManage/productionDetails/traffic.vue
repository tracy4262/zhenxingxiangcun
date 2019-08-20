<template>
    <div class="ivu-table ivu-table-border ivu-table-small ma-table">
        <Row v-if="show">
    		<Row class="margin-bottom-20">
                <Col span="24">
                	<Table border :columns="trafficColumns" :data="trafficData"></Table>
                </Col>
        	</Row>
        </Row>
        <Row v-else>
    		<Row class="margin-bottom-20">
                <Col span="24">
                	<Table border :columns="detailsColumns" :data="detailsData"></Table>
                </Col>
        	</Row>
        	<Row class="ma-maTrafficRetrue">
    	    	<Col span="24"><Button type="primary" @click="back">返回</Button></Col>
    	    </Row>
        </Row>
    </div>
</template>

<script>
import api from '~api'
export default {
	data() {
		return {
			show: true,
			trafficColumns: [
                {
                    title: '出发站点',
                    key: 'departurePoint'
                },
                {
                    title: '重要交通站点地点',
                    key: 'importantSiteFirst'
                },
                {
                    title: '重要交通站点名称',
                    key: 'importantSiteSecond'
                },
                {
                    title: '分路段介绍详情',
                    key: 'details',
                    width: 200,
                    render: (h, params) => {
	                  return h('div', [
	                        h('a', {
		                        attrs:
		                            {
		                           		href: 'javascript:'
		                            },
		                        props: {},
		                        style: {
                                    color: '#74bd94'
                                },
		                        on: {
		                            click: () => {
		                            	this.show = false
                                        this.preservation()
		                            }
		                        }
	                        },'路段详情')
	                  ]);
	                }
                }
            ],
            trafficData: [
            ],
            trafficid: '',


            detailsColumns: [
                {
                    title: '路段名称',
                    key: 'roadName'
                },
                {
                    title: '出发站点',
                    key: 'departurePoint'
                },
                {
                    title: '沿公路名称',
                    key: 'alongRoadName'
                },
                {
                    title: '公路通行能力等级',
                    width: 150,
                    key: 'highwayGrade'
                },
                {
                    title: '公路行政等级',
                    width: 130,
                    key: 'highwayAdministrative'
                },
                {
                    title: '公路路面等级',
                    width: 130,
                    key: 'roadLevel'
                },
                {
                    title: '路段终点',
                    key: 'terminus'
                },
                {
                    title: '公里数',
                    key: 'mileage'
                },
                {
                    title: '最大载货吨位',
                    key: 'maximalTonnage'
                }
            ],
            detailsData: [
                {
                    roadName: '',
                    departurePoint: '',
                    alongRoadName: '',
                    highwayGrade: '',
                    highwayAdministrative: '',
                    roadLevel: '',
                    terminus: '',
                    mileage: '',
                    maximalTonnage: ''
                },
                {
                    roadName: '合计',
                    departurePoint: '',
                    alongRoadName: '',
                    highwayGrade: '',
                    highwayAdministrative: '',
                    roadLevel: '',
                    terminus: '',
                    mileage: '',
                    maximalTonnage: ''
                }
            ]
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
            api.post('/member/product-traffic/query', {
                productId: this.$route.query.id
            })
            .then(response => {
                if(response.data === undefined){
                    this.trafficData = this.trafficData
                }else{
                    let resualt = []
                    resualt[0] = response.data
                    this.trafficData = resualt

                    this.trafficid = response.data.trafficid
                }
            })
        },

        preservation(){
            let that = this
            api.post('/member/product-traffic/detail', {
                trafficid: this.trafficid
            })
            .then(response => {
                if(response.code === 200){
                    let resualt = []
                    resualt[0] = response.data
                    resualt[1] = {
                        roadName: '合计',
                        departurePoint: '',
                        alongRoadName: '',
                        highwayGrade: '',
                        highwayAdministrative: '',
                        roadLevel: '',
                        terminus: '',
                        mileage: response.data.mileage,
                        maximalTonnage: response.data.maximalTonnage
                    }
                    this.detailsData = resualt
                }
            })
        },


		back(){
			this.show = true
		}
	}
}
</script>

<style scoped>
.ma-maTrafficRetrue{text-align: center;padding: 20px 0;}
.ma-table{padding: 0;overflow: hidden;
    text-overflow: ellipsis;
    white-space: normal;
    word-break: break-all;
    box-sizing: border-box;
    margin-top: 30px;
}
</style>
