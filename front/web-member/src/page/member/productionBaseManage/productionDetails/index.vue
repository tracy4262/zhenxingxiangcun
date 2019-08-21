<template>
    <div>
    	<div class="ivu-table ivu-table-border ivu-table-small table">
            <table>
                <tbody class="ivu-table-body">
                    <tr>
                        <td colspan="2">地理位置</td>
                        <td colspan="2">
                            <Cascader @on-change="CascaderChange" :data="cityList" :load-data="loadPositionDatas">
                                <Input v-model="detailsData.geographicPosition" readonly placeholder="请选择" />
                            </Cascader>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="4">周边地理位置(经度, 纬度)</td>
                        <td>最东点坐标</td>
                        <td colspan="2"><Input v-model="detailsData.eastCoordinate" placeholder="请选择坐标" readonly @on-focus="onEast" /></td>
                    </tr>
                    <tr>
                        <td>最西点坐标</td>
                        <td colspan="2"><Input v-model="detailsData.westCoordinate" placeholder="请选择坐标" readonly @on-focus="onWest" /></td>
                    </tr>
                    <tr>
                        <td>最南点坐标</td>
                        <td colspan="2"><Input v-model="detailsData.southCoordinate" placeholder="请选择坐标" readonly @on-focus="onSouth" /></td>
                    </tr>
                    <tr>
                        <td>最北点坐标</td>
                        <td colspan="2"><Input v-model="detailsData.northCoordinate" placeholder="请选择坐标" readonly @on-focus="onNorth" /></td>
                    </tr>
                    <tr>
                        <td colspan="2">中心点坐标</td>
                        <td colspan="2"><Input v-model="detailsData.centerCoordinate" placeholder="请选择坐标" readonly @on-focus="onCore" /></td>
                    </tr>
                    <tr>
                        <td colspan="2">东西长</td>
                        <td><Input v-model="detailsData.eastWestLength" /></td>
                        <td>米</td>
                    </tr>
                    <tr>
                        <td colspan="2">南北宽</td>
                        <td><Input v-model="detailsData.southNorthLength" /></td>
                        <td>米</td>
                    </tr>
                    <tr>
                        <td colspan="2">土地总面积</td>
                        <td><Input v-model="detailsData.landArea" /></td>
                        <td>平方米</td>
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
            <vui-map 
            ref="vuiMap"
            @on-get-point="onGetPoint"
            :point="point"
            ></vui-map>
        </div>

        <div class="ma-button">
            <Button type="primary" @click="preservation">保存</Button>
        </div>
    </div>
</template>

<script>
import vuiMap from '../../components/productionMap'
import api from '~api'
export default {
    components:{
        vuiMap
    },
    data() {
        return {
            aaa: [],
            isMap: 0,
			detailsData: {
                geographicPosition: '',
				eastCoordinate: '',
				westCoordinate: '',
				southCoordinate: '',
				northCoordinate: '',
				centerCoordinate: '',
				eastWestLength: '',
				southNorthLength: '',
				landArea: '',
                describe: '',
                locationId:[]
			},
			disabled: true,
			indexSearch: [],
            cityList:[],
            point:''
        }
    },
    created () {
        this.getData()
        this.$api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
            this.cityList = res.data
        })

    },
    methods: {
        // 获取数据
        getData(){
            api.post('/member/product-geographical-position/query', {
                productId: this.$route.query.id
            })
            .then(response => {
                console.log('getdata',response)
                if(response.data === undefined){
                    this.detailsData = this.detailsData
                }else{
                    // this.detailsData = response.data
                    this.detailsData.geographicPosition = response.data.geographicPosition,
                    this.detailsData.westCoordinate = response.data.westCoordinate,
                    this.detailsData.eastCoordinate = response.data.eastCoordinate,
                    this.detailsData.southCoordinate = response.data.southCoordinate,
                    this.detailsData.northCoordinate = response.data.northCoordinate,
                    this.detailsData.centerCoordinate = response.data.centerCoordinate,
                    this.detailsData.eastWestLength = response.data.eastWestLength,
                    this.detailsData.southNorthLength = response.data.southNorthLength,
                    this.detailsData.landArea = response.data.landArea
                    this.detailsData.describe = response.data.describe
                    this.detailsData.locationId = response.data.locationId.split(',')
                }
            })
        },
        // 保存数据
        preservation(){
            let that = this
            api.post('/member/product-geographical-position/save', {
                productId: this.$route.query.id,
                geographicPosition: this.detailsData.geographicPosition,
                westCoordinate: this.detailsData.westCoordinate,
                eastCoordinate: this.detailsData.eastCoordinate,
                southCoordinate: this.detailsData.southCoordinate,
                northCoordinate: this.detailsData.northCoordinate,
                centerCoordinate: this.detailsData.centerCoordinate,
                eastWestLength: this.detailsData.eastWestLength,
                southNorthLength: this.detailsData.southNorthLength,
                landArea: this.detailsData.landArea,
                locationId: this.detailsData.locationId,
            })
            .then(response => {
                if(response.code === 200){
                    // that.getData()
                    window.location.reload()
                }
            })
        },

        // 取坐标
        onGetPoint (point) {
            if (point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
                this.point = `${point.lng},${point.lat}`
                console.log(this.point)
                if( this.isMap === 0){
                    this.detailsData.centerCoordinate = `${point.lng},${point.lat}`
                }else if( this.isMap === 1 ){
                    this.detailsData.eastCoordinate = `${point.lng},${point.lat}`
                }else if( this.isMap === 2 ){
                    this.detailsData.westCoordinate = `${point.lng},${point.lat}`
                }else if( this.isMap === 3 ){
                    this.detailsData.southCoordinate = `${point.lng},${point.lat}`
                }else if( this.isMap === 4 ){
                    this.detailsData.northCoordinate = `${point.lng},${point.lat}`
                }
            }
        },
        //传递坐标
        propsPoint(point){
            this.point = point
        },

        // 中心点坐标
        onCore () {
            this.$refs.vuiMap.showMap = true
            this.isMap = 0
            this.propsPoint(this.detailsData.centerCoordinate)
        },

        // 最东点坐标
        onEast() {
            this.$refs.vuiMap.showMap = true
            this.isMap = 1
            this.propsPoint(this.detailsData.eastCoordinate)            
        },

        // 最西点坐标
        onWest() {
            this.$refs.vuiMap.showMap = true
            this.isMap = 2
            this.propsPoint(this.detailsData.westCoordinate)                        
        },

        // 最南点坐标
        onSouth() {
            this.$refs.vuiMap.showMap = true
            this.propsPoint(this.detailsData.southCoordinate)                        
            this.isMap = 3
        },

        // 最北点坐标
        onNorth() {
            this.propsPoint(this.detailsData.northCoordinate)                        
            this.$refs.vuiMap.showMap = true
            this.isMap = 4
        },

        // 地理位置
        CascaderChange(value, selectedData){
            let locationStr = ''
            selectedData.forEach(item => {
                locationStr += item.label
            })
            this.detailsData.geographicPosition = locationStr
            this.detailsData.locationId = value
        },

        loadPositionDatas (item, callback) {
            item.loading = true
            this.$api.post(`/member/town/next/${item.value}`).then(res => {
                item.loading = false
                item.children = res.data
                callback()
            })
        }
    }
}
</script>
<style scoped>
.ma-button{text-align: center;padding: 20px 0;}
.table .ma_text{padding: 10px 5px;}
</style>