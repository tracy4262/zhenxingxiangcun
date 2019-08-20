<template>
    <div>
        <template v-if="show">
            <div class="mb10 clear">
                <h6>生成国际商品码</h6>
            </div>
            <Form :model="internaForm" :label-width="100" style="width:400px">
                <FormItem label="商品名称">
                    <Select v-model="internaForm.name">
                        <Option value="1">白茶</Option>
                        <Option value="2">鱼面</Option>
                    </Select>
                </FormItem>
                <FormItem label="国际商品码信息">
                    <Input v-model="internaForm.info" placeholder="请输入"></Input>
                </FormItem>
                <div class="tc mt10">
                    <Button type="primary">立即生成</Button>
                </div>
            </Form>
        </template>
        <template v-else>
            <div class="mb10 clear">
                <h6>国际码查询</h6>
            </div>
            <div class="mb20 clear">
                商品名称
                <Input v-model="search.name" placeholder="请输入关键字" style="width:200px" />&nbsp;&nbsp;
                <Button type="primary">查询</Button>
            </div>
            <Table
            border
            ref="internaTable"
            size="small"
            :columns="internaTable.columns"
            :data="internaTable.data" />
        </template>
    </div>
</template>

<script>
export default {
    props:{
        show:Boolean
    },
    components:{
    },
    data() {
        return {
            internaForm:{
                name:'',
                info:''
            },
            search:{
                name:''
            },
             internaTable:{
                columns:[
                    {
                        type: 'index',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '商品名称',
                        key: 'name'
                    },{
                        title: '国际商品码信息',
                        key: 'info'
                    },{
                        title: '条形码',
                        key: 'code'
                    },{
                        title: '操作',
                        key: 'action',
                        width: 100,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small',
                                        icon:'arrow-down-a'
                                    },
                                    style: {
                                        fontSize: '16px'
                                    },
                                    on: {
                                        click: () => {
                                            this.codeMangeDown(params.index)
                                        }
                                    }
                                }),
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small',
                                        icon:'edit'
                                    },
                                    style: {
                                        fontSize: '16px'
                                    },
                                    // on: {
                                    //     click: () => {
                                    //         this.codeMangeEdit(params.index)
                                    //     }
                                    // }
                                })
                            ]);
                        }
                    }
                ],
                data:[
                    {
                        name: '粮食类',
                        info: '大豆1号',
                        code:'黄豆'
                    },{
                        name: '粮食类',
                        info: '大豆1号',
                        code:'黄豆'
                    }
                ],
                flag:true,
                index:0
            }
        }
    },
    mounted(){
    },
    methods:{
        // 下载
        codeMangeDown (index) {
            this.$refs.internaTable.exportCsv({
                filename: '导出数据',
                columns: this.internaTable.columns.filter((col, index) => index),
                data: this.internaTable.data.filter((data, index) => index)
            })
        },
    }
}
</script>

<style lang="scss">
    
</style>