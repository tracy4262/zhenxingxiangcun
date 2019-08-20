<template>
    <div class="pd30">
        <Row class="pt20 pb20">
            <Col span="12">
                <Input v-model="keyWord" icon="ios-search" placeholder="请输入关键字" style="width: 200px" @on-enter="handleSearch" @on-click="handleSearch" />
            </Col>
            <Col span="12" class="tr pr20">
            <Button type="default" icon="md-add" @click="handleAdd">添加服务</Button>
            </Col>
        </Row>
        <Table :columns="columns" :data="data" ></Table>
        <div class="pt30 tc pb50">
         <Page :total="total" :page-size="pageSize"  @on-change="hanhdleChange"></Page>
        </div>
    </div>
</template>
<script>
export default {
    name:'service',
    data(){
        return{
            total:0,
            pageSize:10,
            data:[],
            columns: [
                {
                    title: '服务名称',
                    key: 'service_name',
                    align: 'center',
                    render: (h, params) => {
                        return h('span', {
                            style: {cursor: 'pointer'},
                            on: {
                                click: () => {
                                    this.$router.push({
                                        path: `/InforMation/serviceDetail`,
                                        query: {
                                            id: params.row.id,
                                            uid: params.row.account,
                                            type: params.row.type
                                        }
                                    })
                                }
                            }
                        }, params.row.service_name)
                    }
                },
                // {
                //     title: '服务分类',
                //     key: 'service_class_id',
                //     align: 'center',
                // },
                {
                    title: '服务描述',
                    key: 'simple_describe',
                    align: 'center',
                    render: (h, params) => {
                        if (params.row.simple_describe) {
                            return h('div',{
                                class: ['pt10', 'pb10']
                            },[
                                h('p',{
                                    class: ['ell-3'],
                                    attrs: {
                                        title: params.row.simple_describe
                                    }
                                },`${params.row.simple_describe}`)
                            ])
                        }
                    }
                },
                // {
                //     title: '服务时间',
                //     key: 'service_time',
                //     width: 210,
                //     align: 'center',
                // },
                
                //  {
                //     title: '服务介绍',
                //     key: 'simple_describe',
                //     align: 'center',
                //     render: (h, params) => {
                //         if (params.row.contact && params.row.contact.length) {
                //             return h('div',[
                //                 h('p', 
                //                 {
                //                     style: {
                //                         'padding-top': '10px'
                //                     }
                //                 }, `联系人：${params.row.contact[0].contact_name ? params.row.contact[0].contact_name : ''}`),
                //                 h('p', 
                //                 {
                //                     style: {
                //                         'padding-top': '5px',
                //                         'padding-bottom': '10px'
                //                     }
                //                 },
                //                 `联系电话：${params.row.contact[0].phone ? params.row.contact[0].phone : ''}`)
                //             ])
                //         }
                //     }
                // },
                {
                    title: '创建时间',
                    key: 'create_time',
                    width: 210,
                    align: 'center',
                    render: (h, params) => {
                        return h('span',`${this.moment(params.row.create_time).format('YYYY-MM-DD')}`)
                    }
                },
                {
                    title: '操作',
                    key: 'handle',
                    width: 150,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px',
                                    color: 'rgb(255, 121, 33)'
                                },
                                on: {
                                    click: () => {
                                        this.handleEdit(params.row.id)
                                    }
                                }
                            }, '编辑'),
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px',
                                    color: 'rgb(255, 121, 33)'
                                },
                                on: {
                                    click: () => {
                                        this.handleDel(params.row.id)
                                    }
                                }
                            }, '删除')
                        ]);
                    }
                }
            ],
            keyWord:'',
            account:'',
            loginUser:JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            current:1
        }
    },
    created(){
        this.account = this.loginUser.loginAccount
        this.handleInit()
    },
    methods:{
        //初始化查询列表
        handleInit(){
            this.$api.post('/member/fishing/findProductServiceList',{
                account:this.account,
                service_name:this.keyWord,
                pageNum:this.current,
                type: '2'
            }).then(response => {
                if(response.code == 200){
                    this.data = response.data.dataList
                    this.total = response.data.total
                }
            })
        },
        //翻页
        hanhdleChange(e){
            this.current = e
            this.handleInit()
        },
        // 删除
        handleDel (id) {
            this.$api.post('/member/fishing/findFishingService',{
                account: this.account,
                id: `${id}`,
                pageNum: 1,
                pageSize: 99999,
                type: '2'
            }).then(response => {
                if (response.code == 200) {
                    if (response.data.list[0].setMealName) {
                        this.$Message.error({
                            content: '此服务已有套餐，请删除套餐后再删除服务。',
                            duration: 5
                        });
                    } else {
                        this.$Modal.confirm({
                            title: '是否确定删除',
                            content: '是否确认删除？',
                            onOk:()=>{
                                this.$api.post('/member/fishing/deleteFishingService',{id: id}).then(response => {
                                    if (response.code == 200) {
                                        this.$Message.success('删除成功')
                                        if (this.current !== 1 && this.data.length == 1) {
                                            this.current -=1
                                        }
                                        this.handleInit()
                                    } else {
                                        this.$Message.error('删除失败')
                                    }
                                })
                            },
                            okText:'确定',
                            cancelText:'取消'
                        });
                    }
                }
            })
        },
        //编辑
        handleEdit (id) {
            this.$router.push(`/scenicSpotAddService/step1?id=${id}`)
        },
        //查询
        handleSearch (){
            this.current = 1
            this.handleInit()
        },
        //添加服务
        handleAdd (){
            this.$router.push('/scenicSpotAddService/step1')
        }
    }
}
</script>
