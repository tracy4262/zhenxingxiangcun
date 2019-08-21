<template>
    <div class="layouts mt20">
        <div class="vui-search-bar">
            <div class="vui-flex vui-flex-middle search-wrap clear">
                <Input v-model="keywrod" class="vui-flex-item vui-flex-middle"></Input>
                <Button type="primary" @click="titleSearch">搜索</Button>
            </div>
            <Row :gutter="20">
                <Col span="8" v-if="filter.address">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">行政区划</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Cascader v-model="position" @on-change="regionData" :data="cityList"
                                  :load-data="loadPositionDatas" filterable change-on-select>
                        </Cascader>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.speci">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">相关物种</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Input v-model="speci" class="pinput-clear" icon="ios-close" readonly
                               @on-focus="handleFilterModal('speciFilter')" @on-click="inputClear('speci')"/>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.trade">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">相关行业</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Input v-model="trade" class="pinput-clear" icon="ios-close" readonly
                               @on-focus="handleFilterModal('tradeFilter')" @on-click="inputClear('trade')"/>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.product">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">相关产品</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Input v-model="product" class="pinput-clear" icon="ios-close" readonly
                               @on-focus="handleFilterModal('productFilter')" @on-click="inputClear('product')"/>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.service">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">相关服务</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Input v-model="service" class="pinput-clear" icon="ios-close" readonly
                               @on-focus="handleFilterModal('serviceFilter')" @on-click="inputClear('service')"/>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.unit">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">发文单位</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Input v-model="sendUnit" placeholder="武汉市政府农业研究院"></Input>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.wordSize">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">发文字号</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Input v-model="sendWordSize" placeholder="武汉市政府农业研究院"></Input>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.type">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">文档类型</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <RadioGroup @on-change="typeData" v-model="fileType">
                            <Radio label="文章"></Radio>
                            <Radio label="图册"></Radio>
                            <Radio label="音频"></Radio>
                            <Radio label="视频"></Radio>
                            <Radio label="图书"></Radio>
                        </RadioGroup>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.infoType">
                <template v-if="filter.articleType === 'information'">
                    <div class="vui-flex vui-flex-middle mb5">
                        <Tag color="primary">资讯类型</Tag>
                        <div class="vui-flex-item vui-flex-middle">
                            <Cascader @on-change="informationData" :data="infoTypeDatas" v-model="infoType"
                                      :load-data="loadInfoTypeDatas" filterable change-on-select></Cascader>
                        </div>
                    </div>
                </template>
                <template v-if="filter.articleType === 'policy'">
                    <div class="vui-flex vui-flex-middle mb5">
                        <Tag color="primary">政策类型</Tag>
                        <div class="vui-flex-item vui-flex-middle">
                            <Cascader @on-change="informationData" :data="policyTypeDatas" v-model="infoType"
                                      :load-data="loadInfoTypeDatas" filterable change-on-select></Cascader>
                        </div>
                    </div>
                </template>
                <template v-if="filter.articleType === 'knowledge'">
                    <div class="vui-flex vui-flex-middle mb5">
                        <Tag color="primary">知识类型</Tag>
                        <div class="vui-flex-item vui-flex-middle">
                            <Cascader @on-change="informationData" :data="knowledgeTypeDatas" v-model="infoType"
                                      :load-data="loadInfoTypeDatas" filterable change-on-select></Cascader>
                        </div>
                    </div>
                </template>
                <template v-if="filter.articleType === 'standard'">
                    <div class="vui-flex vui-flex-middle mb5">
                        <Tag color="primary">标准分类</Tag>
                        <div class="vui-flex-item vui-flex-middle">
                            <Cascader @on-change="informationData" :data="policyTypeDatas" v-model="infoType"
                                      :load-data="loadInfoTypeDatas" filterable change-on-select></Cascader>
                        </div>
                    </div>
                </template>
                </Col>
                <Col span="8" v-if="filter.corpType">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">公司类型</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Select v-model="corpType" clearable @on-change="reginCorpTypeData">
                            <Option v-for="item in corpTypeList" :value="item.value" :key="item.value">{{ item.label
                                }}
                            </Option>
                        </Select>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.govLevel">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">机构级别</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Select v-model="govLevel" clearable @on-change="reginGovLevelData">
                            <Option v-for="item in govLevelList" :value="item.value" :key="item.value">{{ item.label
                                }}
                            </Option>
                        </Select>
                    </div>
                </div>
                </Col>
                <!-- 专家分类跟擅长领域暂无数据 也没有回传值到父组件 -->
                <Col span="8" v-if="filter.expertType">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">专家分类</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Input v-model="expertType" readonly/>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.adeptField">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">擅长领域</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <!-- <Select multiple v-model="adeptField" @on-change="adeptFieldChange">
                            <Option v-for="(item,index) in adeptFieldList" :value="item.value" :key="index">{{
                                item.label }}
                            </Option>
                        </Select> -->
                        <Input v-model="adeptField" @on-change="adeptFieldChange" />
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.ics">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">ICS分类&nbsp;</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Select v-model="ics" filterable clearable @on-change="ICSChange">
                            <Option v-for="item in icsList" :value="item.value" :key="item.value">{{ item.label }}
                            </Option>
                        </Select>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.ccs">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">CCS分类</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Select v-model="ccs" filterable clearable @on-change="CCSChange">
                            <Option v-for="item in cssList" :value="item.value" :key="item.value">{{ item.label }}
                            </Option>
                        </Select>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.standardTrait">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">标准特点</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Select v-model="standardTrait" clearable :transfer="true" @on-change="standardTraitChange">
                            <Option value="M" key="M">强制性标准</Option>
                            <Option value="R" key="R">推荐性标准</Option>
                        </Select>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.standardStatus">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">标准状态</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Select v-model="standardStatus" clearable :transfer="true" @on-change="standardStatusChange">
                            <Option value="A" key="A">现行</Option>
                            <Option value="F" key="F">即将实施</Option>
                        </Select>
                    </div>
                </div>
                </Col>
                <Col span="8" v-if="filter.publishDate">
                <div class="vui-flex vui-flex-middle mb5">
                    <Tag color="primary">发布日期</Tag>
                    <div class="vui-flex-item vui-flex-middle">
                        <Date-picker type="daterange" placement="bottom-end"
                                     @on-change="publishDateChange" style="width: 100%;"></Date-picker>
                    </div>
                </div>
                </Col>
            </Row>
        </div>
        <!-- 相关物种 -->
        <vui-filter
                ref="speciFilter"
                :cols="2"
                :pageShow="true"
                :total="total"
                :pageCur="pageCur"
                :classifyDatas="speciClassifyDatas"
                :resultDatas="speciResultDatas"
                :load-data="loadSpeciDatas"
                @on-search="handleSpeciSearch"
                @on-get-classify="handleGetSpeciClassify"
                @on-get-result="handleGetSpeciResult"
                @on-page-change="handleSpeciPageChange"/>

        <!-- 相关行业 -->
        <vui-filter
                ref="tradeFilter"
                :simple="true"
                :pageShow="true"
                :total="total2"
                :pageCur="pageCur2"
                :resultDatas="tradeResultDatas"
                @on-search="handleTradeSearch"
                @on-page-change="handleTradePageChange"
                @on-get-result="handleGetTradeResult"/>

        <!-- 相关新产品 -->
        <vui-filter
                ref="productFilter"
                :simple="true"
                :resultDatas="[]"
                @on-get-result="productResult"/>

        <!-- 相关服务 -->
        <vui-filter
                ref="serviceFilter"
                :simple="true"
                :resultDatas="serviceResultDatas"
                @on-search="handleServiceSearch"
                @on-get-result="handleGetServiceResult"/>
    </div>
</template>
<script>
    import api from '~api';
    import axios from 'axios';
    import vuiFilter from '~components/vuiFilter/filter';

    export default {
        name: 'app',
        components: {
            vuiFilter
        },
        props: {
            option: Object
        },
        data() {
            return {
                keywrod: this.option.key,
                position: [],
                cityList: [],
                speci: '',
                trade: '',
                product: '',
                service: '',
                sendUnit: '',
                sendWordSize: '',
                corpType: '',
                govLevel: '',
                expertType: '',
                adeptField: '',
                fileType: '',
                infoType: [],
                ics: '',
                ccs: '',
                standardTrait: '',
                standardStatus: '',
                publishDate: [],
                infoTypeDatas: [
                    {
                        value: 'business',
                        label: '商务资讯',
                        loading: false,
                        children: []
                    },
                    {
                        value: 'official ',
                        label: '公务资讯',
                        loading: false,
                        children: []
                    }
                ],
                policyTypeDatas: [
                    {
                        value: 'party',
                        label: '党务',
                        loading: false,
                        children: []
                    },
                    {
                        value: 'affairs',
                        label: '政务',
                        loading: false,
                        children: []
                    }
                ],
                knowledgeTypeDatas: [
                    {
                        value: 'animalHusbandry',
                        label: '农林牧鱼',
                        loading: false,
                        children: []
                    },
                    {
                        value: 'foodScience',
                        label: '食品科学',
                        loading: false,
                        children: []
                    },
                    {
                        value: 'zs07',
                        label: '经济'
                    },
                    {
                        value: 'zs08',
                        label: '法律'
                    }
                ],
                tradeResultDatas: [],
                speciClassifyDatas: [
                    {
                        label: '动物',
                        value: '0',
                        classId: '',
                        loading: false,
                        checked: false,
                        children: []
                    },
                    {
                        label: '植物',
                        value: '1',
                        classId: '',
                        loading: false,
                        checked: false,
                        children: []
                    }
                ],
                speciResultDatas: [],
                serviceResultDatas: [],
                total: 0,
                pageCur: 1,
                corpTypeList: [
                    {
                        value: '有限公司',
                        label: '有限公司'
                    },
                    {
                        value: '实体门店',
                        label: '实体门店'
                    },
                    {
                        value: '农场',
                        label: '农场'
                    },
                    {
                        value: '基地',
                        label: '基地'
                    },
                    {
                        value: '协会',
                        label: '协会'
                    }
                ],
                govLevelList: [
                    {
                        value: '国家级',
                        label: '国家级'
                    },
                    {
                        value: '省级',
                        label: '省级'
                    },
                    {
                        value: '地市级',
                        label: '地市级'
                    },
                    {
                        value: '县市级',
                        label: '县市级'
                    },
                    {
                        value: '乡镇级',
                        label: '乡镇级'
                    },
                    {
                        value: '村级',
                        label: '村级'
                    }
                ],

                // 相关物种
                speciesDatas: [],

                // 相关行业
                industryDatas: [],

                // 相关产品
                productDatas: [],

                // 相关服务
                serviceDatas: [],

                regionDatas: [],
                informationDatas: '',
                adeptFieldList: [
                    {
                        value: '产前领域：种子、饲料、农药、农田、农田水利',
                        label: '产前领域：种子、饲料、农药、农田、农田水利'
                    },
                    {
                        value: '产中领域：农产品生产',
                        label: '产中领域：农产品生产'
                    },
                    {
                        value: '产后领域 ：农产品加工与流通',
                        label: '产后领域 ：农产品加工与流通'
                    }
                ],
                total2: 0,
                pageCur2: 1,
                cssList: [
                    {
                        label: 'A00标准化管理与一般规定',
                        value: 'A00标准化管理与一般规定'
                    },
                    {
                        label: 'A10经济、文化',
                        value: 'A10经济、文化'
                    },
                    {
                        label: 'A20基础科学',
                        value: 'A20基础科学'
                    }
                ],
                icsList: [
                    {
                        label: '01综合、术语学、标准化',
                        value: '01综合、术语学、标准化'
                    },
                    {
                        label: '03社会学、服务、公司',
                        value: '03社会学、服务、公司'
                    },
                    {
                        label: '07数学、自然科学',
                        value: '07数学、自然科学'
                    }
                ]
            };
        },
        created() {
            this.filter = Object.assign({}, {
                address: true,
                speci: true,
                trade: true,
                product: true,
                service: true,
                unit: true,
                wordSize: true,
                type: true,
                corpType: true,
                other: true,
                infoType: true,
                govLevel: true,
                expertType: true,
                adeptField: true,
                ics: false,
                ccs: false,
                standardTrait: false,
                standardStatus: false,
                publishDate: false,
                articleType: ''
            }, this.option);
            // 取地址
            api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
                this.cityList = res.data;
            });

            // 取相关物种结果
            this.loadSpeciResult('', '', [], this.pageCur, []);

            // 取相关物种分类

            // 取相关行业数据
            /* api.post('/member/industry/findByCharacter', {
                character: '',
                keyword: ''
            }).then(res => {
                var data = res.data
                data.forEach((item,index)=>{
                    item.checked = false
                })
                this.tradeResultDatas = data
            }) */
            this.$api.post('/member/system-dict/getSystemDict', {
                typeName: '行业分类',
                pageNum: 1
            }).then(res => {
                var data = res.data.list;
                this.total2 = res.data.total;
                this.tradeResultDatas = data;
            });
            // 取相关服务数据
            api.post('/member/service/find', {}).then(res => {
                var data = res.data;
                data.forEach((item, index) => {
                    item.checked = false;
                });
                this.serviceResultDatas = data;
            });
        },
        methods: {
            // 所在地区
            regionData(value, arr) {
                if (arr != '') {
                    this.regionDatas = arr.map(function (item) {
                        return item.label;
                    });
                } else {
                    this.regionDatas = [];
                }
                let obj = {
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,

                    speci: this.speci,
                    trade: this.trade,
                    service: this.service,

                    fileType: this.fileType,
                    keywrod: this.keywrod,

                    position: this.position,
                    expertType: this.expertType,
                    adeptField: this.adeptField,

                    govLevel: this.govLevel,

                    corpType: this.corpType,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            },

            // 相关产品查询
            productResult(classify, result) {
                if (result != '') {
                    this.productDatas = result.map(function (item) {
                        return item.value;
                    });
                } else {
                    this.productDatas = [];
                }
                let obj = {
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,
                    fileType: this.fileType,
                    keywrod: this.keywrod,

                    position: this.position,
                    expertType: this.expertType,
                    adeptField: this.adeptField,

                    corpType: this.corpType,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            },

            // 资讯类型
            informationData(value, arr) {
                // 得到各个分类
                let types = value;
                if (types !== '' && types.length > 1) {
                    let index = types.length - 1;
                    this.informationDatas = value[index];
                } else if (types.length === 1) {
                    this.informationDatas = value[0];
                } else {
                    this.informationDatas = '';
                }
                let obj = {
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,
                    fileType: this.fileType,
                    keywrod: this.keywrod,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            },

            typeData() {
                let obj = {
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,
                    fileType: this.fileType,
                    keywrod: this.keywrod,
                };
                this.$emit('searchInMa', obj);
            },

            // 首部搜索事件
            titleSearch() {
                let obj = {
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,
                    fileType: this.fileType,
                    keywrod: this.keywrod,

                    position: this.position,
                    expertType: this.expertType,
                    adeptField: this.adeptField,

                    govLevel: this.govLevel,

                    corpType: this.corpType,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            },

            // 取资讯类型
            loadInfoTypeDatas(item, callback) {
                item.loading = true;
                if (this.filter.articleType === 'information') {
                    api.get(`/member/infoClass/information/${item.value}`).then(res => {
                        item.loading = false;
                        item.children = res.data;
                        callback();
                    });
                } else if (this.filter.articleType === 'policy') {
                    api.get(`/member/infoClass/policy/${item.value}`).then(res => {
                        item.loading = false;
                        item.children = res.data;
                        callback();
                    });
                } else if (this.filter.articleType === 'knowledge') {
                    api.get(`/member/infoClass/knowledge/${item.value}`).then(res => {
                        item.loading = false;
                        item.children = res.data;
                        callback();
                    });
                } else if (this.filter.articleType === 'standard') {
                    api.get(`/member/infoClass/policy/${item.value}`).then(res => {
                        item.loading = false;
                        item.children = res.data;
                        callback();
                    });
                }
            },
            //选择地区回调
            loadPositionDatas(item, callback) {
                item.loading = true;
                api.post(`/member/town/next/${item.value}`).then(res => {
                    item.loading = false;
                    item.children = res.data;
                    callback();
                });

            },
            // 高级搜索弹窗
            handleFilterModal(name) {
                this.$refs[name].highFilterShow = true;
            },
            //清空文本框
            inputClear(name) {
                if (name === 'speci') {
                    this.speci = '';
                    this.$refs.speciFilter.handleReset();
                    this.handleGetSpeciResult('', '');
                } else if (name === 'trade') {
                    this.trade = '';
                    this.$refs.tradeFilter.handleReset();
                    this.handleGetTradeResult('', '');
                } else if (name === 'service') {
                    this.service = '';
                    this.$refs.serviceFilter.handleReset();
                    this.handleGetServiceResult('', '');
                } else if (name === 'product') {
                    this.product = '';
                    this.$refs.productFilter.handleReset();
                }
            },
            // 取相关物种
            handleSpeciSearch(letter, keyword, classify, result) {
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
            },
            loadSpeciDatas(item, callback) {
                item.loading = true;
                api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
                    item.loading = false;
                    var d = res.data;
                    d.forEach(child => {
                        child.checked = false;
                        child.label = child.className;
                    });
                    item.children = d;
                    callback();
                });
            },
            handleGetSpeciClassify(letter, keyword, classify, result) {
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
            },
            handleSpeciPageChange(letter, keyword, classify, num, result) {
                this.pageCur = num;
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
            },
            //相关物种搜索
            loadSpeciResult(letter, keyword, classify, num, result) {
                if (classify.length) {
                    var arr = [];
                    var type = '';
                    classify.forEach(item => {
                        arr.push(item.classId);
                        if (item.value !== undefined) type = item.value;
                    });
                } else {
                    arr = null;
                }
                api.post('/member/specicesClass/findSpecies', {
                    keywords: keyword,
                    fpinyin: letter === '全部' ? '' : letter,
                    fclassifiedid: arr,
                    type: type,
                    pageNum: num,
                    pageSize: 32
                }).then(res => {
                    let data = res.data;
                    if (data === undefined) {
                        this.total = 0;
                    } else {
                        this.total = data.total;
                    }
                    if (result) {
                        if (data === undefined) {
                            this.speciResultDatas = [];
                            return false;
                        } else {
                            result.forEach(item => {
                                data.list.forEach((child, index) => {
                                    if (child.label === item.label) {
                                        child.checked = true;
                                    }
                                });
                            });
                        }
                    } else {
                        data.list.forEach(child => {
                            child.checked = false;
                        });
                    }
                    this.speciResultDatas = data.list;
                });
            },

            //相关物种查询
            handleGetSpeciResult(classify, result) {
                var arr = [];
                if (result != '') {
                    result.forEach(item => arr.push(item.label));
                    this.speci = arr.join(' ');

                    this.speciesDatas = result.map(function (item) {
                        return item.value;
                    });
                } else {
                    this.speciesDatas = [];
                }
                let obj = {
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,

                    speci: this.speci,
                    trade: this.trade,
                    service: this.service,

                    fileType: this.fileType,
                    keywrod: this.keywrod,

                    position: this.position,
                    expertType: this.expertType,
                    adeptField: this.adeptField,

                    corpType: this.corpType,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            },

            // 相关行业搜索
            handleTradeSearch(letter, keyword, result) {
                this.$api.post(`/member/system-dict/getSystemDict`, {
                    dictName: keyword,
                    typeName: '行业分类',
                    character: letter === '全部' ? '' : letter,
                    pageNum: 1
                }).then(res => {
                    var d = res.data.list;
                    this.total2 = res.data.total;
                    this.pageCur2 = 1;
                    this.tradeResultDatas = [];
                    if (!d.length) return;
                    if (result.length) {
                        result.forEach(item => {
                            d.forEach(child => {
                                if (child.label === item.label) {
                                    child.checked = true;
                                    child.label = child.label;
                                    child.value = child.value;
                                }
                            });
                        });
                    } else {
                        d.forEach(child => {
                            child.checked = false;
                            child.label = child.label;
                            child.value = child.value;
                        });
                    }
                    this.tradeResultDatas = d;
                });
            },
            handleTradePageChange(letter, keyword, classify, num, result) {
                this.pageCur2 = num;
                this.$api.post('/member/system-dict/getSystemDict', {
                    typeName: '行业分类',
                    character: letter === '全部' ? '' : letter,
                    dictName: keyword,
                    pageNum: num
                }).then(res => {
                    var data = res.data.list;
                    this.total2 = res.data.total;
                    this.tradeResultDatas = data;
                });
                this.$Message.blue('筛选完成！');
            },
            //相关行业查询
            handleGetTradeResult(classify, result) {
                var arr = [];
                if (result != '') {
                    result.forEach(item => arr.push(item.label));
                    this.trade = arr.join(' ');

                    this.industryDatas = result.map(function (item) {
                        return item.value;
                    });
                } else {
                    this.industryDatas = [];
                }
                let obj = {
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,

                    speci: this.speci,
                    trade: this.trade,
                    service: this.service,

                    fileType: this.fileType,
                    keywrod: this.keywrod,
                    corpType: this.corpType,
                    position: this.position,
                    expertType: this.expertType,
                    adeptField: this.adeptField,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            },

            // 相关服务
            handleServiceSearch(letter, keyword, classify) {
                api.post(`/member/service/find`, {
                    character: letter === '全部' ? '' : letter,
                    keyword: keyword
                }).then(res => {
                    var d = res.data;
                    this.serviceResultDatas = [];
                    if (d.length) {
                        if (classify.length) {
                            classify.forEach(item => {
                                d.forEach(child => {
                                    if (child.label === item.label) {
                                        child.checked = true;
                                    }
                                });
                            });
                        } else {
                            d.forEach(child => {
                                child.checked = false;
                            });
                        }
                        this.serviceResultDatas = d;
                    }
                });
            },
            //相关服务查询
            handleGetServiceResult(classify, result) {
                var arr = [];
                if (result != '') {
                    result.forEach(item => arr.push(item.label));
                    this.service = arr.join(' ');

                    this.serviceDatas = result.map(function (item) {
                        return item.value;
                    });
                } else {
                    this.serviceDatas = [];
                }
                let obj = {
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,

                    speci: this.speci,
                    trade: this.trade,
                    service: this.service,

                    fileType: this.fileType,
                    keywrod: this.keywrod,

                    position: this.position,
                    expertType: this.expertType,
                    adeptField: this.adeptField,

                    corpType: this.corpType,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            },
            reginGovLevelData() {
                let obj = {
                    regionDatas: this.regionDatas,
                    govLevel: this.govLevel,
                    keywrod: this.keywrod
                };
                this.$emit('searchInMa', obj);
            },
            reginCorpTypeData() {
                let obj = {
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    keywrod: this.keywrod,

                    position: this.position,

                    corpType: this.corpType
                };
                this.$emit('searchInMa', obj);
            },
            adeptFieldChange() {
                let obj = {
                    keywrod: this.keywrod,
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    expertType: this.expertType,
                    adeptField: this.adeptField,

                    speci: this.speci,
                    trade: this.trade,
                    service: this.service

                };
                this.$emit('searchInMa', obj);
            },
            ICSChange() {
                let obj = {
                    keywrod: this.keywrod,
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            },
            CCSChange() {
                let obj = {
                    keywrod: this.keywrod,
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            },
            standardTraitChange() {
                let obj = {
                    keywrod: this.keywrod,
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            },
            standardStatusChange() {
                let obj = {
                    keywrod: this.keywrod,
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            },
            publishDateChange(date) {
                console.log('date', date);
                if (date[0] !== '') {
                    this.publishDate = date;
                } else {
                    this.publishDate = [];
                }
                let obj = {
                    keywrod: this.keywrod,
                    regionDatas: this.regionDatas,
                    speciesDatas: this.speciesDatas,
                    industryDatas: this.industryDatas,
                    productDatas: this.productDatas,
                    serviceDatas: this.serviceDatas,
                    informationDatas: this.informationDatas,

                    ics: this.ics,
                    ccs: this.ccs,
                    standardTrait: this.standardTrait,
                    standardStatus: this.standardStatus,
                    publishDate: this.publishDate
                };
                this.$emit('searchInMa', obj);
            }
        }
    };
</script>
<style lang="scss">
    .vui-search-bar {
        margin: 40px 0 20px;
        .search-wrap {
            width: 50%;
            margin: 0 auto 10px;
            .ivu-input,
            .ivu-btn {
                height: 36px;
                font-size: 14px;
            }
            .ivu-input {
                padding-left: 20px;
                border-radius: 50px 0 0 50px;
            }
            .ivu-btn {
                border-radius: 0 50px 50px 0;
                padding: 6px 24px
            }
        }
        .ivu-tag {
            margin-right: 10px;
        }
    }

    .pinput-clear .ivu-icon {
        display: none;
        cursor: pointer;
    }

    .pinput-clear:hover .ivu-icon {
        display: block;
    }
</style>