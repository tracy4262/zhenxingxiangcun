<template>
    <div class="pr15 mt35" style="min-height: 500px">
        <Row class="mt20 pl20">
            <Col span="24">
                <span v-for="(item,index) in typeList" :key="index" class="pl10 pr10">
                    <span @click="chooseType(item, index)" :class="{'farm-group-btn-active': index === activeType, 'farm-group-btn': true}">
                        {{ item.name }}
                    </span>
                </span>
            </Col>
        </Row>
        <Row class="mt20">
            <Col span="24">
                <Table :columns="columns" :data="data"></Table>
            </Col>
        </Row>
        <Row class="mt20 mb20">
            <Col span="24">
                <Page :total="total" :current="currentPage" @on-change="handleGetNextPage" class="tr"></Page>
            </Col>
        </Row>
    </div>
</template>
<script>
export default {
    name: 'orderList',
    components: {
    },
    props: {
        type: {
            type: String,
            required: true
        }
    },
    data () {
        return {
            status: '',
            typeList: [
                {
                    id: '',
                    name: '全部订单'
                },
                {
                    id: '1',
                    name: '待处理'
                },
                {
                    id: '2',
                    name: '已完成'
                }
            ],
            activeType: 0,
            columns: [
                {
                    title: '订单信息',
                    key: 'orderInfo',
                    align: 'center',
                    render: (h, params) => {
                        return h('Row', {
                            props: {
                                type: 'flex',
                                justify: 'center',
                                align: 'middle'
                            }
                        }, [
                            h('Col', {
                                props: {
                                    span: '12'
                                }
                            }, [
                                h('img', {
                                    attrs: {
                                        width: '150px',
                                        height: '90px',
                                        src: params.row.url === '' ? '../../../static/img/goods-list-no-picture.png' : params.row.url,
                                        class: 'pd5'
                                    }
                                })
                            ]),
                            h('Col', {
                                props: {
                                    span: '12'
                                }
                            }, [
                                h('div', {style: {marginTop: '10px'}}, params.row.serviceName),
                                h('div', {style: {marginTop: '5px'}}, '成交时间：' + params.row.dealTime),
                                h('div', {style: {marginTop: '5px', marginBottom: '10px'}}, '订单编号：' + params.row.orderNo)
                            ])
                        ])
                    }
                },
                {
                    title: '数量',
                    key: 'count',
                    align: 'center',
                    width: 100
                },
                {
                    title: '总价',
                    key: 'price',
                    align: 'center',
                    width: 100
                },
                {
                    title: '客户信息',
                    key: 'customerInfo',
                    align: 'center',
                    width: 200,
                    render: (h, params) => {
                        return h('Row', [
                            h('Row', params.row.customerName),
                            h('Row', {style: {marginTop: '5px'}}, params.row.customerPhone)
                        ])
                    }
                },
                {
                    title: '订单状态',
                    key: 'orderStatus',
                    align: 'center',
                    width: 150
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    width: 180,
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
                                        this.confirm(params.row.id)
                                    }
                                }
                            }, '确认订单'),
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    color: '#8C8C8C'
                                },
                                on: {
                                    click: () => {
                                        this.detail(params.row.id)
                                    }
                                }
                            }, '订单详情')
                        ])
                    }
                }
            ],
            data: [],
            total: 0,
            currentPage: 1,
            loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
        }
    },
    created () {
        this.init()
    },
    methods: {
        init () {
            this.$api.post('/member/fishing/findOrderList', {
                type: this.type,
                account: this.loginUser.loginAccount,
                status: this.status,
                pageSize: 10,
                pageNum: this.currentPage
            }).then(response => {
                console.log('rs', response)
                if (response.code === 200) {
                    response.data.list.forEach(element => {
                        this.data.push({
                            url: element.imageUrl ? element.imageUrl.split(',')[0] : '',
                            orderNo: element.orderCode,
                            serviceName: element.serviceName,
                            dealTime: element.create_time,
                            count: 1,
                            price: element.discountPrice === undefined || element.discountPrice === '' ? element.price + '元' : element.discountPrice + '元',
                            customerName: element.nameDetail.name,
                            customerPhone: element.nameDetail.phone,
                            orderStatus: element.status === 1 ? '待处理' : '已完成'
                        })
                    })
                    this.total = response.data.total
                }
            }).catch(error => {

            })
        },
        chooseType (item, index) {
            console.log('item', item)
            this.activeType = index
            this.status = item.id
            this.data = []
            this.init()
        },
        handleGetNextPage (page) {
            this.currentPage = page
            this.data = []
            this.init()
        },
        confirm (id) {},
        detail (id) {}
    }
}
</script>
<style scoped>
    .farm-group-btn {
        color: #9B9B9B;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
    .farm-group-btn-active {
        color: #00c587;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
</style>