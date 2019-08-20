<template>
    <div>
        <top :address="false"></top>
        <section style="background: #F9F9F9" class="pb20">
            <goods-head title="商品货架管理">
                <BreadcrumbItem>商品货架</BreadcrumbItem>
            </goods-head>
            <div class="layouts">
                <section class="bg-white mt20 pd30">
                    <Form ref="info" :model="info" label-position="left" :label-width="90" :rules="ruleInline">
                        <Row :gutter="32">
                            <Col span="8">
                                <Form-item prop="commonProductName" label="商品名称">
                                    <Input placeholder="输入商品名称模糊搜索" v-model="info.commonProductName" />
                                </Form-item>
                            </Col>
                            <Col span="8">
                                <FormItem label="商品分类">
                                    <Input v-model="info.productTypeName" readonly @on-focus="handleFilterModal('productTypeFilter')" />
                                </FormItem>
                            </Col>
                            <!-- <Col span="8">
                                <FormItem label="价格区间">
                                    <Input v-model="info.price0" style="width: 80px;" @on-blur="isNum1" />
                                    &nbsp;&nbsp;—&nbsp;&nbsp;
                                    <Input v-model="info.price1" style="width: 80px;" @on-blur="isNum2" />
                                </FormItem>
                            </Col> -->
                        </Row>
                        <Row :gutter="32">
                            <Col span="8">
                                <FormItem label="下架状态">
                                    <Select v-model="info.saleStatus" clearable>
                                        <Option v-for="item in saleStatusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                                    </Select>
                                </FormItem>
                            </Col>
                            <!-- <Col span="8">
                                <FormItem label="审核状态">
                                    <Select v-model="info.status" clearable>
                                        <Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                                    </Select>
                                </FormItem>
                            </Col> -->
                            <Col span="8">
                                <Button type="primary" @click="query">查询</Button>
                                <Button type="text" @click="clear">清空条件</Button>
                            </Col>
                        </Row>
                        <Row type="flex" align="top">
                            <Col span="12">
                                <FormItem label="批量操作">
                                    <Button type="default" size="small" class="mr10" @click="del">删除</Button>
                                    <Button type="default" size="small" @click="up">上架</Button>
                                </FormItem>
                            </Col>
                            <Col span="12" class="tr">
                                <Button type="text" @click="recycle" icon="ios-trash-outline">
                                    <!-- <Icon type="ios-trash-outline" :size="16"></Icon> -->商品回收站
                                </Button>
                            </Col>
                        </Row>
                    </Form>
                    <Row class="goods-shelf-table">
                        <Table 
                            :columns="columns" 
                            :data="data" 
                            @on-sort-change="tableSort" 
                            @on-filter-change="tableFilter" 
                            @on-selection-change="checkedOnChange">
                        </Table>
                    </Row>
                    <Row class="mt20 mb50">
                        <Col span="24">
                            <Page :total="total" @on-change="handleGetNextPage" class="tr" :page-size="pageSize" :current="pageNum"></Page>
                        </Col>
                    </Row>
                </section>
            </div>
        </section>
        <!-- 定价销售快捷修改价格弹出框 -->
        <Modal v-model="modal" width="560" title="修改价格" :mask-closable="false">
            <Form ref="priceInfo" :model="priceInfo" label-position="right" :label-width="150" :rules="ruleInline1" class="mt20">
                <Form-item prop="currentPrice" label="时价">
                    <Input v-model="priceInfo.currentPrice" style="width: 300px;">
                        <span slot="append">元</span>
                    </Input>
                </Form-item>
                <Form-item prop="discountPrice" label="折扣价">
                    <Input v-model="priceInfo.discountPrice" style="width: 300px;">
                        <span slot="append">元</span> 
                    </Input>
                </Form-item>
                 <Form-item label="折扣时限">
                    <DatePicker placeholder="折扣时限" style="width:300px;" type="daterange" :options="options3" :editable="false" v-model="priceInfo.discountPeriod" @on-change="discountPeriodChange" ></DatePicker>
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" @click="modal=false">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
            </div>
        </Modal>
        <!-- 团购销售快捷修改价格弹出框 -->
        <Modal v-model="modal2" width="560" title="修改价格" :mask-closable="false">
            <Form ref="priceInfo2" :model="priceInfo2" label-position="right" :label-width="150" :rules="ruleInline2" class="mt20">
                <Form-item prop="groupBuyingPrice" label="团购价">
                    <Input v-model="priceInfo2.groupBuyingPrice" style="width: 300px;"><span slot="append">元</span></Input>
                </Form-item>
                <Form-item prop="originalPrice" label="原价">
                    <Input v-model="priceInfo2.originalPrice"  style="width: 300px;"><span slot="append">元</span></Input>
                </Form-item>
                <Form-item prop="groupBuyingStartTimeStr" label="团购开始时间">
                    <DatePicker placeholder="生产日期" style="width: 300px;" type="datetime" :options="options3"  @on-change="groupBuyingStartTimeChange" 
                        :editable="false" v-model="priceInfo2.groupBuyingStartTime" format="yyyy-MM-dd HH:mm"></DatePicker>                                
                </Form-item>
                <Form-item prop="groupBuyingEndTimeStr" label="团购结束时间">
                    <DatePicker placeholder="生产日期" style="width: 300px;" type="datetime" :options="options3"  @on-change="groupBuyingEndTimeChange" 
                        :editable="false" v-model="priceInfo2.groupBuyingEndTime" format="yyyy-MM-dd HH:mm"></DatePicker>
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" @click="modal2=false">取消</Button>
                <Button type="primary" @click="ok2">确定</Button>
            </div>
        </Modal>
        <!-- 竞价销售快捷修改价格弹出框 -->
        <Modal v-model="modal3" width="560" title="修改价格" :mask-closable="false">
            <Form ref="priceInfo3" :model="priceInfo3" label-position="right" :label-width="150" :rules="ruleInline3" class="mt20">
                <Form-item prop="minimum" label="竞拍最小加价">
                    <Input v-model="priceInfo3.minimum" style="width: 300px;"><span slot="append">元</span></Input>
                </Form-item>
                <Form-item prop="startPrice" label="起拍价">
                    <Input v-model="priceInfo3.startPrice" style="width: 300px;"><span slot="append">元</span></Input>
                </Form-item>
                <Form-item prop="biddingStartTimeStr" label="竞价开始时间">
                    <DatePicker placeholder="竞价开始时间" style="width: 300px;" type="datetime" :options="options3" @on-change="biddingStartTimeChange" 
                        :editable="false" v-model="priceInfo3.biddingStartTime" format="yyyy-MM-dd HH:mm"></DatePicker>
                </Form-item>
                <Form-item prop="biddingEndTimeStr" label="竞价结束时间">
                    <DatePicker placeholder="竞价结束时间" style="width: 300px;" type="datetime" :options="options3" @on-change="biddingEndTimeChange" 
                        :editable="false" v-model="priceInfo3.biddingEndTime" format="yyyy-MM-dd HH:mm"></DatePicker>
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" @click="modal3=false">取消</Button>
                <Button type="primary" @click="ok3">确定</Button>
            </div>
        </Modal>
        <!-- 预售快捷修改价格弹出框 -->
        <Modal v-model="modal4" width="560" title="修改价格" :mask-closable="false">
            <Form ref="priceInfo4" :model="priceInfo4" label-position="right" :label-width="150" :rules="ruleInline4" class="mt20">
                <Form-item prop="orderPrice" label="定货价格">
                    <Input v-model="priceInfo4.orderPrice" style="width: 300px;"><span slot="append">元</span></Input>
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" @click="modal4=false">取消</Button>
                <Button type="primary" @click="ok4">确定</Button>
            </div>
        </Modal>
        <!-- 商品分类控件 -->
        <vui-filter
            ref="productTypeFilter"
            :num="1"
            :simple="true"
            :pageShow="true"
            :cols="2"
            :total="total2"
            :pageCur="pageCur2"
            :resultDatas="productTypeResultDatas"
            @on-search="handleProductTypeSearch"
            @on-page-change="handleProductTypePageChange"
            @on-get-result="handleGetProductTypeResult" />
        <foot></foot>
    </div>
</template>
<script>
import top from '~src/top'
import foot from '~src/foot'
import goodsHead from '../goods/components/head'
import vuiFilter from '~components/vuiFilter/filter'
import { isDecimal2 } from '~utils/validate'
export default {
    name: 'goodsShelf',
    components: {
        top,
        foot,
        goodsHead,
        vuiFilter
    },
    data () {
        return {
            info: {
                commonProductName: '',
                productTypeName: '',
                productType: '', // 传的是商品分类的ID
                // price0: '',
                // price1: '',
                saleStatus: '',
                // status: '',
                createTime: '',
                inventory: ''
            },
            ruleInline: {},

            modal: false,
            priceInfo: {
                commodityId: '',
                currentPrice: '',
                discountPrice: '',
                discountPeriod: [],
                discountPeriodStr: ''
            },
            ruleInline1: {
                currentPrice:[
                    { required: true, message: '请填写时价', trigger:'blur' },
                    { validator:isDecimal2, trigger:'blur' }
                ],
                discountPrice:[
                    { validator:isDecimal2, trigger:'blur' }
                ]
            },

            modal2: false,
            priceInfo2: {
                commodityId: '',
                groupBuyingPrice: '',
                originalPrice: '',
                groupBuyingStartTime: '',
                groupBuyingStartTimeStr: '',
                groupBuyingEndTime: '',
                groupBuyingEndTimeStr: ''
            },
            ruleInline2: {
                groupBuyingPrice: [
                    { required: true, message: '请填写团购价', trigger:'blur' },
                    { validator: isDecimal2, trigger:'blur' }
                ],
                originalPrice: [
                    { required: true, message: '请填写原价', trigger:'blur' },
                    { validator: isDecimal2, trigger:'blur' }
                ],
                groupBuyingStartTimeStr: [
                    { required: true, message: '请填选择团购开始时间', trigger:'change' }
                ],
                groupBuyingEndTimeStr: [
                    { required: true, message: '请填选择团购结束时间', trigger:'change' }
                ]
            },

            modal3: false,
            priceInfo3: {
                commodityId: '',
                startPrice: '',
                minimum: '',
                biddingStartTime: '',
                biddingStartTimeStr: '',
                biddingEndTime: '',
                biddingEndTimeStr: ''
            },
            ruleInline3: {
                startPrice: [
                    { required: true, message: '请填写起拍价', trigger:'blur' },
                    { validator: isDecimal2, trigger:'blur' }
                ],
                minimum: [
                    { required: true, message: '请填写竞拍最小加价', trigger:'blur' },
                    { validator: isDecimal2, trigger:'blur' }
                ],
                biddingStartTimeStr: [
                    { required: true, message: '请填选择竞价开始时间', trigger:'change' }
                ],
                biddingEndTimeStr: [
                    { required: true, message: '请填选择竞价结束时间', trigger:'change' }
                ]
            },

            modal4: false,
            priceInfo4: {
                commodityId: '',
                orderPrice: ''
            },
            ruleInline4: {
                orderPrice: [
                    { validator: isDecimal2, trigger: 'blur' }
                ]
            },

            saleStatusList: [
                {
                    label: '全部',
                    value: '5'
                },
                {
                    label: '我下架的',
                    value: '1'
                },
                {
                    label: '售完下架',
                    value: '2'
                }
            ],
            statusList: [
                {
                    label: '全部',
                    value: '4'
                },
                {
                    label: '审核中',
                    value: '0'
                },
                {
                    label: '已通过',
                    value: '1'
                },
                {
                    label: '未通过',
                    value: '2'
                }
            ],
            columns: [
                {
                    type: 'selection',
                    width: 60,
                   align: 'center'
                },
                {
                    title: '商品图片',
                    key: 'productPicture',
                    align: 'center',
                    width: 100,
                    render: (h, params) => {
                        return h('img', {
                            attrs: {
                                width: '58px',
                                height: '58px',
                                src: params.row.productPicture === '' ? '../../../static/img/goods-list-no-picture.png' : params.row.productPicture,
                                class: 'pd5'
                            }
                        })
                    }
                },
                {
                    title: '商品名称',
                    key: 'productName',
                    align: 'center'
                },
                {
                    title: '定价方式',
                    key: 'salesWay',
                    align: 'center'
                },
                {
                    title: '商品价格',
                    key: 'productPrice',
                    align: 'center',
                    width: 250,
                    render: (h, params) => {
                        if (params.row.salesWay === '定价销售') {
                            if (params.row.priceInfo.discountPrice && params.row.priceInfo.discountPrice !== '') {
                                return h('div', [
                                    h('div', {
                                        style: {
                                            textDecoration: 'line-through'
                                        },
                                        attrs: {
                                            class: 'pd5'
                                        }
                                    }, '原价：' + params.row.priceInfo.currentPrice + '元'),
                                    h('div', [
                                        h('span', {
                                            attrs: {
                                                class: 'pd5'
                                            }
                                        }, '折扣价：' + params.row.priceInfo.discountPrice + '元'),
                                        h('Icon', {
                                            props: {
                                                type: 'md-create'
                                            },
                                            style: {
                                                marginLeft: '10px',
                                                cursor: 'pointer'
                                            },
                                            nativeOn: {
                                                click: () => {
                                                    this.editPrice(params.row)
                                                }
                                            }
                                        })
                                    ]),
                                    h('div', {
                                        attrs: {
                                            class: 'pd5'
                                        }
                                    }, params.row.priceInfo.discountPeriodStr === 'Invalid date - Invalid date' ? '折扣时限：无限制' : '折扣时限：' + params.row.priceInfo.discountPeriodStr)
                                ])
                            } else {
                                return h('div', [
                                    h('span', {
                                        attrs: {
                                            class: 'pd5'
                                        }
                                    }, '原价：' + params.row.priceInfo.currentPrice + '元'),
                                    h('Icon', {
                                        props: {
                                            type: 'md-create'
                                        },
                                        style: {
                                            marginLeft: '10px',
                                            cursor: 'pointer'
                                        },
                                        nativeOn: {
                                            click: () => {
                                                this.editPrice(params.row)
                                            }
                                        }
                                    })
                                ])
                            }
                        } else if (params.row.salesWay === '团购销售') {
                            return h('div', [
                                h('div', {
                                    style: {
                                        textDecoration: 'line-through'
                                    },
                                    attrs: {
                                        class: 'pd5'
                                    }
                                }, '原价：' + params.row.priceInfo.originalPrice + '元'),
                                h('div', [
                                    h('span', {
                                        attrs: {
                                            class: 'pd5'
                                        }
                                    }, '团购价：' + params.row.priceInfo.groupBuyingPrice + '元'),
                                    h('Icon', {
                                        props: {
                                            type: 'md-create'
                                        },
                                        style: {
                                            marginLeft: '10px',
                                            cursor: 'pointer'
                                        },
                                        nativeOn: {
                                            click: () => {
                                                this.editPrice2(params.row)
                                            }
                                        }
                                    })
                                ]),
                                h('div', {
                                    attrs: {
                                        class: 'pd5'
                                    }
                                }, '团购时限：' + params.row.priceInfo.groupBuyingStartTimeStr + ' - ' + params.row.priceInfo.groupBuyingEndTimeStr)
                            ])
                        } else if (params.row.salesWay === '竞价销售') {
                            return h('div', [
                                h('div', {
                                    attrs: {
                                        class: 'pd5'
                                    }
                                }, '起拍价：' + params.row.priceInfo.startPrice + '元'),
                                h('div', [
                                    h('span', {
                                        attrs: {
                                            class: 'pd5'
                                        }
                                    }, '最小加价：' + params.row.priceInfo.minimum + '元'),
                                    h('Icon', {
                                        props: {
                                            type: 'md-create'
                                        },
                                        style: {
                                            marginLeft: '10px',
                                            cursor: 'pointer'
                                        },
                                        nativeOn: {
                                            click: () => {
                                                this.editPrice3(params.row)
                                            }
                                        }
                                    })
                                ]),
                                h('div', {
                                    attrs: {
                                        class: 'pd5'
                                    }
                                }, '竞价时限：' + params.row.priceInfo.biddingStartTimeStr + ' - ' + params.row.priceInfo.biddingEndTimeStr)
                            ])
                        } else if (params.row.salesWay === '面议') {
                            return h('div', [
                                h('div', '——'),
                            ])
                        } else if (params.row.salesWay === '预售') {
                            return h('div', [
                                h('span', {
                                    attrs: {
                                        class: 'pd5'
                                    }
                                }, params.row.priceInfo.orderPrice === '' ? '——' : '定货价格：' + params.row.priceInfo.orderPrice + '元'),
                                h('Icon', {
                                    props: {
                                        type: 'md-create'
                                    },
                                    style: {
                                        marginLeft: '10px',
                                        cursor: 'pointer'
                                    },
                                    nativeOn: {
                                        click: () => {
                                            this.editPrice4(params.row)
                                        }
                                    }
                                })
                            ])
                        }
                    }
                },
                {
                    title: '库存',
                    key: 'inventory',
                    width: 100,
                    align: 'center',
                    sortable: 'custom'
                },
                {
                    title: '货架状态',
                    key: 'saleStatus',
                    width: 120,
                    align: 'center',
                    filters: [
                        {
                            label: '未上架',
                            value: '0'
                        },
                        {
                            label: '我下架的',
                            value: '1'
                        },
                        {
                            label: '售完下架',
                            value: '2'
                        }
                    ],
                    filterMultiple: false,
                    filterRemote (value, row) {
                    }
                },
                /* {
                    title: '审核状态',
                    key: 'status',
                    width: 125,
                    align: 'center',
                    filters: [
                        {
                            label: '审核中',
                            value: '0'
                        },
                        {
                            label: '已审核',
                            value: '1'
                        },
                        {
                            label: '未通过',
                            value: '2'
                        }
                    ],
                    filterMultiple: false,
                    filterRemote (value, row) {
                    }
                }, */
                {
                    title: '创建时间',
                    key: 'createTime',
                    align: 'center',
                    width: 170,
                    sortable: 'custom'
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    width: 100,
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    color: '#57A97B'
                                },
                                on: {
                                    click: () => {
                                        this.edit(params.row)
                                    }
                                }
                            }, '编辑'),
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    color: '#57A97B'
                                },
                                on: {
                                    click: () => {
                                        this.upOne(params.row.commodityId)
                                    }
                                }
                            }, '上架'),
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    color: '#57A97B'
                                },
                                on: {
                                    click: () => {
                                        this.delOne(params.row.commodityId)
                                    }
                                }
                            }, '删除')
                        ]);
                    }
                }
            ],
            data: [],
            total: 0,
            currentPage: 1,
            pageNum: 1,
            pageSize: 10,
            // 商品分类
            total2: 0,
            pageCur2: 1,
            productTypeResultDatas: [],
            choosedData: [],
            options3: {
                disabledDate(date) {
                    return date && date.valueOf() < Date.now();
                }
            }
        }
    },
    created () {
        // 初始化查询列表所有数据
        this.getData({
            num: 1,
            size: this.pageSize,
            isDelete: '0',   //是否删除，必传
            account: this.$user.loginAccount, //用户名，必传
            storageStatus: '4', //货架状态   0:未上架,1:我下架的,2:售完下架,3:上架到正在出售页面',4:货架查全部
            // approveStatus: '4', //审核状态   0 审核中，1已通过，2未通过，4空为全部
            commodityName: '', //商品名称
            productTypeId: '', //商品分类
            startPrice: '', //最小价格
            endPrice: '', //最大价格
            timeSort: '0', //按创建时间排序，0：降序，1：升序
            storageSort: '' //按库存大小排序，0：降序，1：升序
        })
        // 商品分类数据初始化
        this.$api.post('/portal/shopCommdoity/findProductClassification', {
            pinyin: '',
            name: '',
            num: 1,
            size: 32
        }).then(res => {
            this.total2 = res.data.total
            res.data.list.forEach(element => {
                this.productTypeResultDatas.push({
                    label: element.className,
                    value: element.productCode
                })
            })
        })
    },
    methods: {
        discountPeriodChange (date) {
            if (this.priceInfo.discountPeriod) {
                this.priceInfo.discountPeriodStr = this.moment(this.priceInfo.discountPeriod[0]).format('YYYY-MM-DD') + ' - ' + this.moment(this.priceInfo.discountPeriod[1]).format('YYYY-MM-DD')
            } else {
                this.priceInfo.discountPeriod = []
                this.priceInfo.discountPeriodStr = ''
            }
        },
        groupBuyingStartTimeChange (date) {
            if (this.priceInfo2.groupBuyingStartTime) {
                this.priceInfo2.groupBuyingStartTimeStr = this.moment(this.priceInfo2.groupBuyingStartTime).format('YYYY-MM-DD HH:mm')
            } else {
                this.priceInfo2.groupBuyingStartTime = ''
                this.priceInfo2.groupBuyingStartTimeStr = ''
            }
        },
        groupBuyingEndTimeChange (date) {
            if (this.priceInfo2.groupBuyingEndTime) {
                this.priceInfo2.groupBuyingEndTimeStr = this.moment(this.priceInfo2.groupBuyingEndTime).format('YYYY-MM-DD HH:mm')
            } else {
                this.priceInfo2.groupBuyingEndTime = ''
                this.priceInfo2.groupBuyingEndTimeStr = ''
            }
        },
        biddingStartTimeChange (date) {
            if (this.priceInfo3.biddingStartTime) {
                this.priceInfo3.biddingStartTimeStr = this.moment(this.priceInfo3.biddingStartTime).format('YYYY-MM-DD HH:mm')
            } else {
                this.priceInfo3.biddingStartTime = ''
                this.priceInfo3.biddingStartTimeStr = ''
            }
        },
        biddingEndTimeChange (date) {
            if (this.priceInfo3.biddingEndTime) {
                this.priceInfo3.biddingEndTimeStr = this.moment(this.priceInfo3.biddingEndTime).format('YYYY-MM-DD HH:mm')
            } else {
                this.priceInfo3.biddingEndTime = ''
                this.priceInfo3.biddingEndTimeStr = ''
            }
        },
        getData (data) {
            // 查询列表所有数据
            this.$api.post('/shop/shopStorage/getShopStorageList', data).then(response => {
                console.log('response', response)
                if (response.code === 200 && response.data && response.data.list) {
                    response.data.list.forEach(element => {
                        this.data.push({
                            templateId: element.shopPushTemplateId,
                            templateType: element.templateType,
                            categoryId: element.productCategoryId,
                            goodsId: element.pushShopCommodityId,
                            commodityId: element.commodityId,
                            productPicture: element.commodityImage.length === 0 ? '' : element.commodityImage[0],
                            productName: element.commodityName,
                            salesWay: element.salesWay,
                            priceInfo: element.priceInfo,
                            /* groupBuyingEndTimeStr: element.priceInfo.groupBuyingEndTimeStr,
                            groupBuyingPrice: element.priceInfo.groupBuyingPrice,
                            groupBuyingStartTimeStr: element.priceInfo.groupBuyingStartTimeStr,
                            originalPrice: element.priceInfo.originalPrice, */
                            /* productPrice: `时价：${element.timePrice}`,
                            discountPrice: `折扣价：${element.discountPrice}`,
                            discountTime: `折扣限时：${element.discountDate}`, */
                            inventory: element.productVbep + ' ' + element.unit,
                            saleStatus: element.storageStatus === '0' ? '未上架' : element.storageStatus === '1' ? '我下架的' : '售完下架',
                            // status: element.approveStatus === '0' ? '审核中' : element.approveStatus === '1' ? '已通过' : '未通过',
                            createTime: element.createTime,
                            _disabled: element.productVbep == 0 ? true : false
                        })
                    })
                    this.total = response.data.total
                }
            }).catch(error => {
                this.$Message.error('查询货架数据出错！')
            })
        },
        query () {
            this.data = []
            this.pageNum = 1
            console.log('this.info', this.info)
            let data = {
                num: this.pageNum,
                size: this.pageSize,
                isDelete: '0',   //是否删除，必传
                account: this.$user.loginAccount, //用户名，必传
                storageStatus: this.info.saleStatus === '' || this.info.saleStatus === undefined ? '4' : this.info.saleStatus, //货架状态   0:未上架,1:我下架的,2:售完下架,3:上架到正在出售页面',4:货架查全部
                // approveStatus: this.info.status === '' || this.info.status === undefined ? '4' : this.info.status, //审核状态   0 待审核，1审核通过 ，2审核未通过，4空为全部
                commodityName: this.info.commonProductName, //商品名称
                productTypeId: this.info.productType, //商品分类
                // startPrice: this.info.price0, //最小价格
                // endPrice: this.info.price1, //最大价格
                timeSort: this.info.inventory !== '' ? '' : this.info.createTime === '' ? '0' : this.info.createTime === 'asc' ? '1' : this.info.createTime === 'desc' ? '0' : '0', //按创建时间排序，0：降序，1：升序
                storageSort: this.info.inventory === '' ? '' : this.info.inventory === 'asc' ? '1' : this.info.inventory === 'desc' ? '0' : '' //按库存大小排序，0：降序，1：升序
            }
            console.log('传入参数：', data)
            // 按条件查询
            this.getData(data)
        },
        clear () {
            this.info = {
                commonProductName: '',
                productTypeName: '',
                productType: '',
                // price0: '',
                // price1: '',
                saleStatus: '',
                // status: ''
            }
        },
        // iview table 只要选中项发生变化时就会触发
        checkedOnChange (value) {
            console.log('value', value)
            this.choosedData = value
        },
        // 删除到回收站
        del () {
            if (this.choosedData.length !== 0) {
                this.$Modal.confirm({
					title: '操作提示',
					content: '确定删除已勾选的商品？',
                    onOk: () => {
                        let ids = []
                        this.choosedData.forEach(element => {
                            ids.push({
                                account: this.$user.loginAccount,
                                commodityId: element.commodityId,
                                isDelete: '1'
                            })
                        })
                        console.log('ids', ids)
                        this.$api.post('/shop/shopStorage/updateStorageStatus', ids).then(response => {
                            console.log('resp', response)
                            if (response.code === 200) {
                                this.query()
                                this.$Message.success('删除成功！')
                            }
                        }).catch(error => {
                            this.$Message.error('删除失败！')
                        })
                    }
                })
            } else {
                this.$Message.error('请先勾选要删除的商品！')
            }
        },
        delOne (commodityId) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '确定删除此商品？',
                onOk: () => {
                    let ids = []
                    ids.push({
                        account: this.$user.loginAccount,
                        commodityId: commodityId,
                        isDelete: '1'
                    })
                    console.log('ids', ids)
                    this.$api.post('/shop/shopStorage/updateStorageStatus', ids).then(response => {
                        console.log('resp', response)
                        if (response.code === 200) {
                            this.query()
                            this.$Message.success('删除成功！')
                        }
                    }).catch(error => {
                        this.$Message.error('删除失败！')
                    })
                }
            })
        },
        // 上架
        up () {
            if (this.choosedData.length !== 0) {
                // 审核中跟未通过的状态不能上架
                /* for (let i = 0; i < this.choosedData.length; i++) {
                    if (this.choosedData[i].status === '审核中' || this.choosedData[i].status === '未通过') {
                        this.$Message.error('审核中或者未通过的商品不能上架！')
                        return
                    }
                } */
                this.$Modal.confirm({
					title: '操作提示',
					content: '确定上架已勾选的商品？',
                    onOk: () => {
                        let ids = []
                        this.choosedData.forEach(element => {
                            ids.push({
                                account: this.$user.loginAccount,
                                commodityId: element.commodityId,
                                storageStatus: '3'
                            })
                        })
                        console.log('ids', ids)
                        this.$api.post('/shop/shopStorage/updateStorageStatus', ids).then(response => {
                            console.log('resp', response)
                            if (response.code === 200) {
                                this.query()
                                this.$Message.success('上架成功！')
                            }
                        }).catch(error => {
                            this.$Message.error('上架失败！')
                        })
                    }
                })
            } else {
                this.$Message.error('请先勾选要上架的商品！')
            }
        },
        upOne (commodityId) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '确定上架已勾选的商品？',
                onOk: () => {
                    let ids = []
                    ids.push({
                        account: this.$user.loginAccount,
                        commodityId: commodityId,
                        storageStatus: '3'
                    })
                    console.log('ids', ids)
                    this.$api.post('/shop/shopStorage/updateStorageStatus', ids).then(response => {
                        console.log('resp', response)
                        if (response.code === 200) {
                            this.query()
                            this.$Message.success('上架成功！')
                        }
                    }).catch(error => {
                        this.$Message.error('上架失败！')
                    })
                }
            })
        },
        // 定价销售 快捷修改价格确定
        ok () {
            this.$refs['priceInfo'].validate(valid => {
                if (valid) {
                    if (this.priceInfo.discountPeriod) {
                        this.priceInfo.discountPeriod[0] = this.moment(this.priceInfo.discountPeriod[0]).format('YYYY-MM-DD')
                        this.priceInfo.discountPeriod[1] = this.moment(this.priceInfo.discountPeriod[1]).format('YYYY-MM-DD')
                    }
                    console.log('this.priceInfo', this.priceInfo)
                    this.$api.post('/shop/shopStorage/updateStoragePrice', this.priceInfo).then(response => {
                        console.log('response', response)
                        if (response.code === 200) {
                            this.query()
                            this.modal = false
                            this.$Message.success('商品价格更新成功！')
                        }
                    }).catch(error => {
                        this.$Message.error('更新商品价格出错！')
                    })
                } else {
                    this.$Message.info('请核对表单字段！')
                }
            })
        },
        ok2 () {
            this.$refs['priceInfo2'].validate(valid => {
                if (valid) {
                    if (this.priceInfo2.groupBuyingStartTime) {
                        this.priceInfo2.groupBuyingStartTime = this.moment(this.priceInfo2.groupBuyingStartTime).format('YYYY-MM-DD HH:mm')
                    }
                    if (this.priceInfo2.groupBuyingEndTime) {
                        this.priceInfo2.groupBuyingEndTime = this.moment(this.priceInfo2.groupBuyingEndTime).format('YYYY-MM-DD HH:mm')
                    }
                    console.log('this.priceInfo2', this.priceInfo2)
                    this.$api.post('/shop/shopStorage/updateStoragePrice', this.priceInfo2).then(response => {
                        console.log('response', response)
                        if (response.code === 200) {
                            this.query()
                            this.modal2 = false
                            this.$Message.success('商品价格更新成功！')
                        }
                    }).catch(error => {
                        this.$Message.error('更新商品价格出错！')
                    })
                } else {
                    this.$Message.info('请核对表单字段！')
                }
            })
        },
        ok3 () {
            this.$refs['priceInfo3'].validate(valid => {
                if (valid) {
                    if (this.priceInfo3.biddingStartTime) {
                        this.priceInfo3.biddingStartTime = this.moment(this.priceInfo3.biddingStartTime).format('YYYY-MM-DD HH:mm')
                    }
                    if (this.priceInfo3.biddingEndTime) {
                        this.priceInfo3.biddingEndTime = this.moment(this.priceInfo3.biddingEndTime).format('YYYY-MM-DD HH:mm')
                    }
                    console.log('this.priceInfo3', this.priceInfo3)
                    this.$api.post('/shop/shopStorage/updateStoragePrice', this.priceInfo3).then(response => {
                        console.log('response', response)
                        if (response.code === 200) {
                            this.query()
                            this.modal3 = false
                            this.$Message.success('商品价格更新成功！')
                        }
                    }).catch(error => {
                        this.$Message.error('更新商品价格出错！')
                    })
                } else {
                    this.$Message.info('请核对表单字段！')
                }
            })
        },
        ok4 () {
            this.$refs['priceInfo4'].validate(valid => {
                if (valid) {
                    console.log('this.priceInfo4', this.priceInfo4)
                    this.$api.post('/shop/shopStorage/updateStoragePrice', this.priceInfo4).then(response => {
                        console.log('response', response)
                        if (response.code === 200) {
                            this.query()
                            this.modal4 = false
                            this.$Message.success('商品价格更新成功！')
                        }
                    }).catch(error => {
                        this.$Message.error('更新商品价格出错！')
                    })
                } else {
                    this.$Message.info('请核对表单字段！')
                }
            })
        },
        editPrice (row) {
            console.log('row', row)
            // 编辑价格数据回显
            this.modal = true
            this.priceInfo.account = this.$user.loginAccount
            this.priceInfo.commodityId = row.commodityId
            this.priceInfo.currentPrice = row.priceInfo.currentPrice
            this.priceInfo.discountPrice = row.priceInfo.discountPrice
            this.priceInfo.discountPeriod = row.priceInfo.discountPeriod
            this.priceInfo.discountPeriodStr = row.priceInfo.discountPeriodStr
        },
        editPrice2 (row) {
            console.log('row', row)
            // 编辑价格数据回显
            this.modal2 = true
            this.priceInfo2.account = this.$user.loginAccount
            this.priceInfo2.commodityId = row.commodityId
            this.priceInfo2.originalPrice = row.priceInfo.originalPrice
            this.priceInfo2.groupBuyingPrice = row.priceInfo.groupBuyingPrice
            this.priceInfo2.groupBuyingStartTime = row.priceInfo.groupBuyingStartTime
            this.priceInfo2.groupBuyingStartTimeStr = row.priceInfo.groupBuyingStartTime
            this.priceInfo2.groupBuyingEndTime = row.priceInfo.groupBuyingEndTime
            this.priceInfo2.groupBuyingEndTimeStr = row.priceInfo.groupBuyingEndTimeStr
        },
        editPrice3 (row) {
            console.log('row', row)
            // 编辑价格数据回显
            this.modal3 = true
            this.priceInfo3.account = this.$user.loginAccount
            this.priceInfo3.commodityId = row.commodityId
            this.priceInfo3.startPrice = row.priceInfo.startPrice
            this.priceInfo3.minimum = row.priceInfo.minimum
            this.priceInfo3.biddingStartTime = row.priceInfo.biddingStartTime
            this.priceInfo3.biddingStartTimeStr = row.priceInfo.biddingStartTimeStr
            this.priceInfo3.biddingEndTime = row.priceInfo.biddingEndTime
            this.priceInfo3.biddingEndTimeStr = row.priceInfo.biddingEndTimeStr
        },
        editPrice4 (row) {
            console.log('row', row)
            // 编辑价格数据回显
            this.modal4 = true
            this.priceInfo4.account = this.$user.loginAccount
            this.priceInfo4.commodityId = row.commodityId
            this.priceInfo4.orderPrice = row.priceInfo.orderPrice
        },
        edit (row) {
            console.log('row', row)
            this.$router.push({
                path: '/release-goods/step1',
                query: {
                    templateId: row.templateId,
                    templateType: row.templateType,
                    categoryId: row.categoryId,
                    goodsId: row.goodsId
                }
            })
        },
        handleGetNextPage (page) {
            this.pageNum = page
            this.data = []
            let data = {
                num: page,
                size: this.pageSize,
                isDelete: '0',   //是否删除，必传
                account: this.$user.loginAccount, //用户名，必传
                storageStatus: this.info.saleStatus === '' || this.info.saleStatus === undefined ? '4' : this.info.saleStatus, //货架状态   0:未上架,1:我下架的,2:售完下架,3:上架到正在出售页面',4:货架查全部
                // approveStatus: this.info.status === '' || this.info.status === undefined ? '4' : this.info.status, //审核状态   0 待审核，1审核通过 ，2审核未通过，4空为全部
                commodityName: this.info.commonProductName, //商品名称
                productTypeId: this.info.productType, //商品分类
                // startPrice: this.info.price0, //最小价格
                // endPrice: this.info.price1, //最大价格
                timeSort: this.info.inventory !== '' ? '' : this.info.createTime === '' ? '0' : this.info.createTime === 'asc' ? '1' : this.info.createTime === 'desc' ? '0' : '0', //按创建时间排序，0：降序，1：升序
                storageSort: this.info.inventory  === '' ? '' : this.info.inventory === 'asc' ? '1' : this.info.inventory === 'desc' ? '0' : '' //按库存大小排序，0：降序，1：升序
            }
            console.log('传入参数：', data)
            // 按条件查询
            this.getData(data)
        },
        tableSort (value) {
            console.log('请求数据重新加载')
            console.log(value)
            this.info.createTime = ''
            this.info.inventory = ''
            this.info[value.key] = value.order
            this.query()
        },
        tableFilter (value) {
            console.log('请求数据重新加载')
            console.log(value)
            /* if (value.key === 'status') {
                this.info.status = value._filterChecked[0]
            } */
            if (value.key === 'saleStatus') {
                this.info.saleStatus = value._filterChecked[0]
            }
            this.query()
        },
        recycle () {
            this.$router.push('/goods/recycle-bin')
        },
        /* isNum1 (event) {
            let reg = /^[0-9]+$/
            if (!reg.test(this.info.price0)) {
                this.info.price0 = ''
            } else {
                if (parseInt(this.info.price0) > parseInt(this.info.price1)) {
                    let tmp = ''
                    tmp = this.info.price0
                    this.info.price0 = this.info.price1
                    this.info.price1 = tmp
                }
            }
        },
        isNum2 (event) {
            let reg = /^[0-9]+$/
            if (!reg.test(this.info.price1)) {
                this.info.price1 = ''
            } else {
                if (parseInt(this.info.price0) > parseInt(this.info.price1)) {
                    let tmp = ''
                    tmp = this.info.price0
                    this.info.price0 = this.info.price1
                    this.info.price1 = tmp
                }
            }
        }, */
        // 高级搜索弹窗
        handleFilterModal (name) {
            this.$refs[name].highFilterShow = true
        },
        // 商品分类
        handleProductTypeSearch (letter, keyword, result) {
            this.$api.post(`/portal/shopCommdoity/findProductClassification`, {
                name : keyword,
                pinyin: letter === '全部' ? '' : letter,
                num: 1,
                size: 32
            }).then(res => {
                console.log('123', res)
                var data = []
                if (res.data.list) {
                    res.data.list.forEach(element => {
                        data.push({
                            label: element.className,
                            value: element.productCode
                        })
                    })
                }
                this.total2 = res.data.total
                this.productTypeResultDatas = []
                // 选中回显
                if(result) {
                    result.forEach(item => {
                        data.forEach((child, index) => {
                            if(child.label === item.label) {
                                child.checked = true
                            }
                        })
                    })
                } else {
                    data.forEach(child => {
                        child.checked = false
                    })
                }
                this.productTypeResultDatas = data
            })
        },
        handleProductTypePageChange (letter, keyword, classify, num, result) {
            this.pageCur2 = num
            this.$api.post('/portal/shopCommdoity/findProductClassification', {
                name : keyword,
                pinyin: letter === '全部' ? '' : letter,
                num: num,
                size: 32
            }).then(res => {
                var data = []
                res.data.list.forEach(element => {
                    data.push({
                        label: element.className,
                        value: element.productCode
                    })
                })
                this.total2 = res.data.total
                this.productTypeResultDatas = []
                // 选中回显
                if(result) {
                    result.forEach(item => {
                        data.forEach((child, index) => {
                            if(child.label === item.label) {
                                child.checked = true
                            }
                        })
                    })
                } else {
                    data.forEach(child => {
                        child.checked = false
                    })
                }
                this.productTypeResultDatas = data
            })
            this.$Message.success('筛选完成！')
        },
        handleGetProductTypeResult (classify, result) {
            let arr = []
            let arrId = []
            result.forEach(item => arr.push(item.label))
            result.forEach(item => arrId.push(item.value))
            this.info.productTypeName = arr.join(' ')
            this.info.productType = arrId.join(' ')
        }
    }
}
</script>
<style lang="scss">
.goods-shelf-table{
    .ivu-table-row {
        i {
            display: none;
        }
        &:hover {
            i {
                display: inline-block;
            }
        }
    }
}
</style>

