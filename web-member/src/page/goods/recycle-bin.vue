<template>
    <div>
        <top :address="false"></top>
        <section style="background: #F9F9F9">
            <goods-head title="商品回收站" :content="content">
                <BreadcrumbItem to="/goods/goods-shelf">商品货架</BreadcrumbItem>
                <BreadcrumbItem>商品回收站</BreadcrumbItem>
            </goods-head>
            <div class="layouts">
                <section class="bg-white mt20 mb20 pd30">
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
                            <Col span="8">
                                <Button type="primary" @click="query">查询</Button>
                                <Button type="text" @click="clear">清空条件</Button>
                            </Col>
                        </Row>
                        <Row :gutter="32">
                            <!-- <Col span="8">
                                <FormItem label="价格区间">
                                    <Input v-model="info.price0" style="width: 80px;" @on-blur="isNum1" />
                                    &nbsp;&nbsp;—&nbsp;&nbsp;
                                    <Input v-model="info.price1" style="width: 80px;" @on-blur="isNum2" />
                                </FormItem>
                            </Col> -->
                        </Row>
                        <Row>
                            <Col span="24">
                                <FormItem label="批量操作">
                                    <Button type="default" size="small" class="mr10" @click="recover">恢复到货架</Button>
                                    <Button type="default" size="small" @click="del">永久删除</Button>
                                </FormItem>
                            </Col>
                        </Row>
                    </Form>
                    <Row>
                        <Table 
                            :columns="columns" 
                            :data="data"
                            @on-sort-change="tableSort" 
                            @on-selection-change="checkedOnChange">
                        </Table>
                    </Row>
                    <Row class="mt20 mb50">
                        <Col span="24">
                            <Page :total="total" :current="pageNum" @on-change="handleGetNextPage" class="tr"></Page>
                        </Col>
                    </Row>
                </section>
            </div>
        </section>
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
    </div>
</template>
<script>
import top from '~src/top'
import goodsHead from '../goods/components/head'
import vuiFilter from '~components/vuiFilter/filter'
export default {
    name: 'goodsShelf',
    components: {
        top,
        goodsHead,
        vuiFilter
    },
    data () {
        return {
            content: `不小心删除商品怎么办？选中删除宝贝，点击“<span style="color: #00c587;">恢复到货架</span>”按钮，将立刻恢复`,
            info: {
                commonProductName: '',
                productTypeName: '',
                productType: '',
                // price0: '',
                // price1: '',
                deleteTime: ''
            },
            ruleInline: {},
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
                                        }, '折扣价：' + params.row.priceInfo.discountPrice + '元')
                                    ]),
                                    h('div', {
                                        attrs: {
                                            class: 'pd5'
                                        }
                                    }, params.row.priceInfo.discountPeriodStr === '' ? '无限制' : '折扣时限：' + params.row.priceInfo.discountPeriodStr)
                                ])
                            } else {
                                return h('div', [
                                    h('span', {
                                        attrs: {
                                            class: 'pd5'
                                        }
                                    }, '原价：' + params.row.priceInfo.currentPrice + '元')
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
                                    }, '团购价：' + params.row.priceInfo.groupBuyingPrice + '元')
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
                                    }, '最小加价：' + params.row.priceInfo.minimum + '元')
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
                                h('div', {
                                    attrs: {
                                        class: 'pd5'
                                    }
                                }, params.row.priceInfo.orderPrice === '' ? '——' : '定货价格：' + params.row.priceInfo.orderPrice + '元'),
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
                    title: '删除时间',
                    key: 'deleteTime',
                    align: 'center',
                    width: 170,
                    sortable: 'custom'
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    width: 150,
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
                                        this.recoverOne(params.row.commodityId)
                                    }
                                }
                            }, '恢复到货架'),
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
                            }, '永久删除')
                        ]);
                    }
                }
            ],
            data: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            choosedData: [],
            // 商品分类
            total2: 0,
            pageCur2: 1,
            productTypeResultDatas: []
        }
    },
    created () {
        // 初始化查询列表所有数据
        this.getData({
            num: 1,
            size: this.pageSize,
            isDelete: '1',   //是否删除，必传
            account: this.$user.loginAccount, //用户名，必传
            // storageStatus: '4', //货架状态   0:未上架,1:我下架的,2:售完下架,3:上架到正在出售页面',4:货架查全部
            // approveStatus: '4', //审核状态   0 审核中，1已通过，2未通过，4空为全部
            commodityName: '', //商品名称
            productTypeId: '', //商品分类
            startPrice: '', //最小价格
            endPrice: '', //最大价格
            deleteTime: '0', //按删除时间排序，0：降序，1：升序
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
        getData (data) {
            // 查询列表所有数据
            this.$api.post('/shop/shopStorage/getShopStorageList', data).then(response => {
                if (response.code === 200 && response.data && response.data.list) {
                    console.log('response', response)
                    response.data.list.forEach(element => {
                        this.data.push({
                            commodityId: element.commodityId,
                            productPicture: element.commodityImage.length === 0 ? '' : element.commodityImage[0],
                            productName: element.commodityName,
                            salesWay: element.salesWay,
                            /* productPrice: `时价：${element.timePrice}`,
                            discountPrice: `折扣价：${element.discountPrice}`,
                            discountTime: `折扣限时：${element.discountDate}`, */
                            inventory: element.productVbep + '' + element.unit,
                            priceInfo: element.priceInfo,
                            // saleStatus: element.storageStatus === '0' ? '未上架' : element.storageStatus === '1' ? '我下架的' : '售完下架',
                            // status: element.approveStatus === '0' ? '审核中' : element.approveStatus === '1' ? '已通过' : '未通过',
                            deleteTime: element.deleteTime
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
            let data = {
                num: this.pageNum,
                size: this.pageSize,
                isDelete: '1',   //是否删除，必传
                account: this.$user.loginAccount, //用户名，必传
                // storageStatus: this.info.saleStatus === '' ? '4' : this.info.saleStatus, //货架状态   0:未上架,1:我下架的,2:售完下架,3:上架到正在出售页面',4:货架查全部
                // approveStatus: this.info.status === '' ? '4' : this.info.status, //审核状态   0 待审核，1审核通过 ，2审核未通过，4空为全部
                commodityName: this.info.commonProductName, //商品名称
                productTypeId: this.info.productType, //商品分类
                // startPrice: this.info.price0, //最小价格
                // endPrice: this.info.price1, //最大价格
                deleteTime: this.info.inventory !== '' ? '' : this.info.deleteTime === '' ? '0' : this.info.deleteTime === 'asc' ? '1' : this.info.deleteTime === 'desc' ? '0' : '0', //按创建时间排序，0：降序，1：升序
                storageSort: this.info.inventory === '' ? '' : this.info.inventory === 'asc' ? '1' : this.info.inventory === 'desc' ? '0' : '' //按库存大小排序，0：降序，1：升序
            }
            console.log('传入参数：', data)
            // 按条件查询
            this.getData(data)
        },
        clear () {
            this.info = {
                commonProductName: '',
                productType: '',
                // price0: '',
                // price1: ''
            }
        },
        delOne (commodityId) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '确定永久删除此商品？',
                onOk: () => {
                    let ids = []
                    ids.push({
                        account: this.$user.loginAccount,
                        commodityId: commodityId
                    })
                    console.log('ids', ids)
                    this.$api.post('/shop/shopStorage/deleteCommodityForever', ids).then(response => {
                        console.log('resp', response)
                        if (response.code === 200) {
                            this.query()
                            this.$Message.success('永久删除成功！')
                        }
                    }).catch(error => {
                        this.$Message.error('永久删除失败！')
                    })
                }
            })
        },
        del () {
            if (this.choosedData.length !== 0) {
                this.$Modal.confirm({
					title: '操作提示',
					content: '确定永久删除已勾选的商品？',
                    onOk: () => {
                        let ids = []
                        this.choosedData.forEach(element => {
                            ids.push({
                                account: this.$user.loginAccount,
                                commodityId: element.commodityId
                            })
                        })
                        console.log('ids', ids)
                        this.$api.post('/shop/shopStorage/deleteCommodityForever', ids).then(response => {
                            console.log('resp', response)
                            if (response.code === 200) {
                                this.query()
                                this.$Message.success('永久删除成功！')
                            }
                        }).catch(error => {
                            this.$Message.error('永久删除失败！')
                        })
                    }
                })
            } else {
                this.$Message.error('请先勾选要永久删除的商品！')
            }
        },
        recoverOne (commodityId) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '确定恢复此商品？',
                onOk: () => {
                    let ids = []
                    ids.push({
                        account: this.$user.loginAccount,
                        commodityId: commodityId,
                        isDelete: '0'
                    })
                    console.log('ids', ids)
                    this.$api.post('/shop/shopStorage/updateStorageStatus', ids).then(response => {
                        console.log('resp', response)
                        if (response.code === 200) {
                            this.query()
                            this.$Message.success('恢复到货架成功！')
                        }
                    }).catch(error => {
                        this.$Message.error('恢复到货架失败！')
                    })
                }
            })
        },
        recover () {
            if (this.choosedData.length !== 0) {
                this.$Modal.confirm({
					title: '操作提示',
					content: '确定恢复已勾选的商品？',
                    onOk: () => {
                        let ids = []
                        this.choosedData.forEach(element => {
                            ids.push({
                                account: this.$user.loginAccount,
                                commodityId: element.commodityId,
                                isDelete: '0'
                            })
                        })
                        console.log('ids', ids)
                        this.$api.post('/shop/shopStorage/updateStorageStatus', ids).then(response => {
                            console.log('resp', response)
                            if (response.code === 200) {
                                this.query()
                                this.$Message.success('恢复到货架成功！')
                            }
                        }).catch(error => {
                            this.$Message.error('恢复到货架失败！')
                        })
                    }
                })
            } else {
                this.$Message.error('请先勾选要恢复的商品！')
            }
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
        handleGetNextPage (page) {
            this.pageNum = page
            this.data = []
            let data = {
                num: page,
                size: this.pageSize,
                isDelete: '0',   //是否删除，必传
                account: this.$user.loginAccount, //用户名，必传
                // storageStatus: this.info.saleStatus, //货架状态   0:未上架,1:我下架的,2:售完下架,3:上架到正在出售页面',4:货架查全部
                // approveStatus: this.info.status, //审核状态   0 待审核，1审核通过 ，2审核未通过，4空为全部
                commodityName: this.info.commonProductName, //商品名称
                productTypeId: this.info.productType, //商品分类
                // startPrice: this.info.price0, //最小价格
                // endPrice: this.info.price1, //最大价格
                deleteTime: this.info.inventory !== '' ? '' : this.info.deleteTime === '' ? '0' : this.info.deleteTime === 'asc' ? '1' : this.info.deleteTime === 'desc' ? '0' : '0', //按创建时间排序，0：降序，1：升序
                storageSort: this.info.inventory  === '' ? '' : this.info.inventory === 'asc' ? '1' : this.info.inventory === 'desc' ? '0' : '' //按库存大小排序，0：降序，1：升序
            }
            console.log('传入参数：', data)
            // 按条件查询
            this.getData(data)
        },
        tableSort (value) {
            console.log('请求数据重新加载')
            console.log(value)
            this.info.deleteTime = ''
            this.info.inventory = ''
            this.info[value.key] = value.order
            this.query()
        },
        // iview table 只要选中项发生变化时就会触发
        checkedOnChange (value) {
            console.log('value', value)
            this.choosedData = value
        },
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

