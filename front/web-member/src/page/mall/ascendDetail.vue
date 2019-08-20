<template>
    <div class="wrapper">
        <top :address="false" goShop />
        <!-- banner -->
        <section class="layouts">
            <div class="detail-info mt20" style="position:relative">
                <img src="../../../static/datas/img/ascend-detail.png" width="100%">
            </div>
            <div class="mb20">
                <img src="../../../static/datas/img/detail-recom.png" width="100%">
            </div>
            <Row>
                <Col span="4">
                    <img src="../../../static/datas/img/shop-info.png" width="100%">
                </Col>
                <Col span="20" class="pl20">
                    <Tabs value="name1" :animated="false" class="tab-detail">
                        <Tab-pane label="商品介绍" name="name1">
                            <img src="../../../static/datas/img/product-info.png" width="100%">
                        </Tab-pane>
                        <Tab-pane label="包装规格" name="name2">
                            <img src="../../../static/datas/img/product-spec.png" width="100%">
                        </Tab-pane>
                        <Tab-pane label="售后服务" name="name3">
                            <img src="../../../static/datas/img/product-service.png" width="100%">
                        </Tab-pane>
                        <Tab-pane label="商品评价（788）" name="name4">
                            <img src="../../../static/datas/img/product-eval.png" width="100%">
                        </Tab-pane>
                        <Tab-pane label="追溯与防伪" name="name5">
                            <template v-if="landInfo">
                                <Row class="h6">
                                    <Col span="4">生产基地：</Col>
                                    <Col span="20"><a href="javascript:;" @click="handleSprout">江夏种苗育种示范基地</a></Col>
                                    <Col span="4">大气质量：</Col>
                                    <Col span="10">达到国家大气质量标准（GB3095-1996）2级</Col>
                                    <Col span="10"><a href="javascript:;" @click="handleAtmos">江夏种苗育种示范基地大气质量状况表</a></Col>
                                </Row>
                                <div span="4">地块信息：</div>
                                <p class="h6 tc mt10 mb5">土地利用现状表</p>
                                <Table :columns="massifTable.columns" size="small" :data="massifTable.data"></Table>
                            </template>
                            <template v-if="soilInfo">
                                <Tabs value="1" class="land-info">
                                    <Tab-pane label="土壤营养（化学）指标" name="1">
                                        <Table :columns="soilTable.table1.columns" border  size="small" :data="soilTable.table1.data" />
                                    </Tab-pane>
                                    <Tab-pane label="土壤物理性状指标" name="2">
                                        <Table :columns="soilTable.table2.columns" border  size="small" :data="soilTable.table2.data" />
                                    </Tab-pane>
                                    <Tab-pane label="土壤生物学指标" name="3">
                                        <Table :columns="soilTable.table3.columns" border  size="small" :data="soilTable.table3.data" />
                                    </Tab-pane>
                                    <Tab-pane label="土壤环境指标" name="4">
                                        <Table :columns="soilTable.table4.columns" border  size="small" :data="soilTable.table4.data" />
                                    </Tab-pane>
                                    <Tab-pane label="土壤微量元素含量" name="5">
                                        <Table :columns="soilTable.table5.columns" border  size="small" :data="soilTable.table5.data" />
                                    </Tab-pane>
                                </Tabs>
                                <div class="tc mt20">
                                    <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                </div>
                            </template>
                            <template v-if="waterInfo">
                                <Table :columns="waterTable.columns" border  size="small" :data="waterTable.data" />
                                <div class="tc mt20">
                                    <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                </div>
                            </template>
                            <!-- 生产基地 -->
                            <template v-if="produceBase">
                                <slick ref="produceSlick" :options="slickOptions" class="slick" style="height:150px;width:100%">
                                    <a :href="item.url" v-for="item in slickList"><img :src="item.src"></a>
                                </slick>
                                <img src="../../../static/datas/img/map.png">
                                <p class="h6">生产基地位于中国湖北咸宁市咸安区向阳湖奶牛良种场农业合作社生活区，其四周坐标点分别为：最东点坐标(29.911366,114.220648)，最西点坐标(29.910175,114.215477)，最南点坐标(29.909264,114.21979)，最北点坐标(29.913214,114.217679)。中间点坐标为(29.914665,114.224203)。东西长200.00米，南北宽250.00米。总土地面积120060.00平方米。</p>
                                <p class="h6 mb20">联系人：沈强       联系电话：18727989369</p>
                                <Tabs value="ascend1">
                                    <Tab-pane label="地理位置" name="ascend1">
                                        <img src="../../../static/datas/img/dlwz.jpg" width="100%">
                                        <div class="tc mt20">
                                            <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                        </div>
                                    </Tab-pane>
                                    <Tab-pane label="地形地貌" name="ascend2">
                                        <img src="../../../static/datas/img/dxdm.jpg" width="100%">
                                        <div class="tc mt20">
                                            <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                        </div>
                                    </Tab-pane>
                                    <Tab-pane label="气候条件" name="ascend3">
                                        <img src="../../../static/datas/img/qhtj.jpg" width="100%">
                                        <div class="tc mt20">
                                            <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                        </div>
                                    </Tab-pane>
                                    <Tab-pane label="土地利用现状" name="ascend4">
                                        <img src="../../../static/datas/img/tdlyxz.jpg" width="100%">
                                        <div class="tc mt20">
                                            <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                        </div>
                                    </Tab-pane>
                                    <Tab-pane label="生产环境" name="ascend5">
                                        <Tabs value="ambience1">
                                            <Tab-pane label="大气质量" name="ambience1">
                                                <img src="../../../static/datas/img/dqzl.jpg" width="100%">
                                            </Tab-pane>
                                            <Tab-pane label="灌溉水质" name="ambience2">
                                                <img src="../../../static/datas/img/ggsz.jpg" width="100%">
                                            </Tab-pane>
                                            <Tab-pane label="加工水质" name="ambience3">
                                                <img src="../../../static/datas/img/jgsz.jpg" width="100%">
                                            </Tab-pane>
                                            <Tab-pane label="土壤肥力" name="ambience4">
                                                <img src="../../../static/datas/img/trfl.jpg" width="100%">
                                            </Tab-pane>
                                        </Tabs>
                                        <div class="tc mt20">
                                            <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                        </div>
                                    </Tab-pane>
                                    <Tab-pane label="交通条件" name="ascend6">
                                        <img src="../../../static/datas/img/jttj.jpg" width="100%">
                                        <div class="tc mt20">
                                            <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                        </div>
                                    </Tab-pane>
                                    <Tab-pane label="水利条件" name="ascend7">
                                        <img src="../../../static/datas/img/sltj.jpg" width="100%">
                                        <div class="tc mt20">
                                            <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                        </div>
                                    </Tab-pane>
                                    <Tab-pane label="电力条件" name="ascend8">
                                        <img src="../../../static/datas/img/dltj.jpg" width="100%">
                                        <div class="tc mt20">
                                            <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                        </div>
                                    </Tab-pane>
                                    <Tab-pane label="网络通信条件" name="ascend">
                                        <img src="../../../static/datas/img/wltxtj.jpg" width="100%">
                                        <div class="tc mt20">
                                            <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                        </div>
                                    </Tab-pane>
                                </Tabs>
                            </template>
                            <template v-if='atmosStatus'>
                                <img src="../../../static/datas/img/dqzlzkb.jpg" width="100%">
                                <div class="tc mt20">
                                    <Button type="default" @click="handleClick" style="width:100px;">关闭</Button>
                                </div>
                            </template>
                        </Tab-pane>

                    </Tabs>
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                </Col>
            </Row>
        </section>
    </div>
</template>
<script>
import  top from '../../top'
import  clocker from '~components/clocker'
import slick from 'vue-slick'
export default {
    components:{
        top,
        clocker,
        slick
    },
    data () {
        return {
            landInfo:true,
            soilInfo:false,
            waterInfo:false,
            produceBase:false,
            atmosStatus:false,
            massifTable:{
                columns: [
                    {
                        title: '一级土地分类',
                        width: 110,
                        key: 'lv1Land',
                    },{
                        title: '二级土地分类',
                        width: 110,
                        key: 'lv2Land'
                    },{
                        title: '地块名称',
                        key: 'landName'
                    },{
                        title: '面积',
                        key: 'area'
                    },{
                        title: '面积计量单位',
                        width: 140,
                        key: 'areaUnit'
                    },{
                        title: '地图标示',
                        key: 'mapMark'
                    },{
                        title: '实况视频',
                        key: 'video'
                    },{
                        title: '土壤信息',
                        width: 110,
                        key: 'soilInfo',
                        render: (h, params) => {
                            return h('a', {
                                on: {
                                    click: () => {
                                        this.landInfo=false
                                        this.soilInfo=true
                                        this.waterInfo=false
                                        this.produceBase=false
                                        this.atmosStatus=false
                                    }
                                }
                            }, '查看土壤信息')
                        }
                    },{
                        title: '水质信息',
                        width: 110,
                        key: 'waterInfo',
                        render: (h, params) => {
                            return h('a', {
                                on: {
                                    click: () => {
                                        this.landInfo=false
                                        this.soilInfo=false
                                        this.waterInfo=true
                                        this.produceBase=false
                                        this.atmosStatus=false
                                    }
                                }
                            }, '查看水质信息')
                        }
                    }
                ],
                data: [
                    {
                        lv1Land: '耕地',
                        lv2Land: '旱地',
                        landName: '菜地',
                        area:'49358.00',
                        areaUnit:'33',
                        mapMark:'',
                        video:'',
                        soilInfo:'',
                        waterInfo:''
                    }
                ]
            },
            soilTable:{
                table1:{
                    columns: [
                        {
                            title: ' ',
                            width: 200,
                            key: 'name'
                        },{
                            title: ' ',
                            key: 'lv2Land'
                        }
                    ],
                    data: [
                        {name: '全氮'},
                        {name: '全磷'},
                        {name: '全钾'},
                        {name: '碱解氮'},
                        {name: '有效磷'},
                        {name: '有效钾'},
                        {name: '速效磷'},
                        {name: '速效钾'},
                        {name: '阳离子交换量'},
                        {name: '碳氮比'},
                        {name: '碱解氮'},
                        {name: '有机质'}
                    ]
                },
                table2:{
                    columns: [
                        {
                            title: ' ',
                            width: 400,
                            key: 'name'
                        },{
                            title: ' ',
                            key: 'lv2Land'
                        }
                    ],
                    data: [
                        {name: '质地'},
                        {name: '容重'},
                        {name: '水稳性团聚体'},
                        {name: '孔隙度（总孔隙度、毛管孔隙度、非毛管孔隙度）'},
                        {name: '土壤耕层温度变幅'},
                        {name: '土层厚度'},
                        {name: '土壤含水量'},
                        {name: '粘粒含量'}
                    ]
                },
                table3:{
                    columns: [
                        {
                            title: ' ',
                            width: 400,
                            key: 'name'
                        },{
                            title: ' ',
                            key: 'lv2Land'
                        }
                    ],
                    data: [
                        {name: '有机质'},
                        {name: '腐殖酸（富里酸、胡敏酸）'},
                        {name: '微生物态碳'},
                        {name: '土壤酶活性（腺酶、蛋白酶、过氧化氢酶、转化酶、磷酸酶等）'}
                    ]
                },
                table4:{
                    columns: [
                        {
                            title: ' ',
                            width: 400,
                            key: 'name'
                        },{
                            title: ' ',
                            key: 'lv2Land'
                        }
                    ],
                    data: [
                        {name: '土壤PH'},
                        {name: '地下水深度'},
                        {name: '坡度'}
                    ]
                },
                table5:{
                    columns: [
                        {
                            title: ' ',
                            width: 400,
                            key: 'name'
                        },{
                            title: ' ',
                            key: 'lv2Land'
                        }
                    ],
                    data: [
                        {name: '有效铜'},
                        {name: '有效锌'},
                        {name: '有效铁'},
                        {name: '有效锰'},
                        {name: '有效钼'},
                        {name: '有效硼'}
                    ]
                }
            },
            waterTable:{
                columns: [
                    {
                        type: 'index',
                        width: 60,
                        align: 'center'
                    },{
                        title: '类别',
                        key: 'category'
                    },{
                        title: '指标（mg/L）',
                        key: 'quota'
                    },{
                        title: '本企业数据',
                        key: 'unit'
                    }
                ],
                data: [
                    {
                        category: 'PH',
                        quota: '',
                        unit:''
                    },{
                        category: '氰化物',
                        quota: '',
                        unit:''
                    },{
                        category: '氟化物',
                        quota: '',
                        unit:''
                    },{
                        category: '氯化物',
                        quota: '',
                        unit:''
                    },{
                        category: '砷',
                        quota: '',
                        unit:''
                    },{
                        category: '汞',
                        quota: '',
                        unit:''
                    },{
                        category: '铅',
                        quota: '',
                        unit:''
                    },{
                        category: '镉',
                        quota: '',
                        unit:''
                    },{
                        category: '铬(+6)',
                        quota: '',
                        unit:''
                    },{
                        category: '粪大肠杆菌(个/升)',
                        quota: '',
                        unit:''
                    }
                ]
            },
            slickList:[
                {
                    src:'../static/datas/img/kaocha01.png',
                    url:'javascript:;'
                },{
                    src:'../static/datas/img/kaocha02.png',
                    url:'javascript:;'
                },{
                    src:'../static/datas/img/kaocha03.png',
                    url:'javascript:;'
                },{
                    src:'../static/datas/img/kaocha04.png',
                    url:'javascript:;'
                },{
                    src:'../static/datas/img/kaocha05.png',
                    url:'javascript:;'
                },{
                    src:'../static/datas/img/kaocha06.png',
                    url:'javascript:;'
                },{
                    src:'../static/datas/img/kaocha01.png',
                    url:'javascript:;'
                },{
                    src:'../static/datas/img/kaocha02.png',
                    url:'javascript:;'
                },{
                    src:'../static/datas/img/kaocha03.png',
                    url:'javascript:;'
                },{
                    src:'../static/datas/img/kaocha04.png',
                    url:'javascript:;'
                }
            ],
            slickOptions: {}
        }
    },
    created(){
        this.slickOptions = {
            slidesToShow: 6,
            autoplay:true,
            slidesToScroll: 1,
            autoplaySpeed: 500,
            arrows:false
        }
    },
    methods: {
        handleClick(){
            this.landInfo=true
            this.soilInfo=false
            this.waterInfo=false
            this.produceBase=false
            this.atmosStatus=false
        },
        handleSprout(){
            this.landInfo=false
            this.soilInfo=false
            this.waterInfo=false
            this.produceBase=true
            this.atmosStatus=false

        },
        handleAtmos(){
            this.landInfo=false
            this.soilInfo=false
            this.waterInfo=false
            this.produceBase=false
            this.atmosStatus=true

        }
    }
}
</script>
<style lang="scss">

</style>
