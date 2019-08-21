<template>
    <div>
        <template v-if="show">
            <div class="mb10 clear">
                <h6>追溯码查询</h6>
            </div>
            <Form :model="ascendForm" :label-width="100" style="width:400px">
                <FormItem label="商品名称">
                    <Select v-model="ascendForm.name">
                        <Option value="1">白茶</Option>
                        <Option value="2">鱼面</Option>
                    </Select>
                </FormItem>
                <FormItem label="产品批次号">
                    <Input v-model="ascendForm.batch" placeholder="请输入"></Input>
                </FormItem>
                <FormItem label="生产日期">
                    <DatePicker type="date" :value="ascendForm.produceDate" placeholder="选择日期" style="width: 200px" />
                </FormItem>
                <FormItem label="质保日期">
                    <DatePicker type="date" :value="ascendForm.warrantyDate" placeholder="选择日期" style="width: 200px" />
                </FormItem>
                <FormItem label="产品图片">
                    <div class="upload-list" v-for="(item,index) in uploadList" :key="index">
                        <template v-if="item.status === 'finished'">
                            <img :src="item.url">
                            <div class="upload-list-cover">
                                <Icon type="ios-trash-outline" @click.native="removeUpload(item)"></Icon>
                            </div>
                        </template>
                        <template v-else>
                            <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
                        </template>
                    </div>
                    <Upload
                        ref="upload"
                        :show-upload-list="false"
                        :default-file-list="defaultList"
                        :on-success="handleSuccess"
                        :format="['jpg','png']"
                        :max-size="2048"
                        :on-format-error="handleFormatError"
                        :on-exceeded-size="handleMaxSize"
                        :before-upload="handleBeforeUpload"
                        multiple
                        type="drag"
                        action="//jsonplaceholder.typicode.com/posts/"
                        style="display: inline-block;width:78px;">
                        <div style="width: 78px;height:78px;line-height: 78px;">
                            <Icon type="camera" size="20"></Icon>
                        </div>
                    </Upload>
                </FormItem>
                <FormItem label="自定义属性" v-for="(item,index) in ascendForm.custom" :key="index">
                    <Row>
                        <Col span="9">
                            <Input type="text" v-model="item.value" placeholder="属性名"></Input>
                        </Col>
                        <Col span="9" offset="1">
                            <Input type="text" v-model="item.value" placeholder="属性值"></Input>
                        </Col>
                        <Col span="4" offset="1">
                            <Button type="default" @click="handleRemove(index)">删除</Button>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label=" ">
                    <Button type="dashed" long @click="handleAdd" icon="md-add-circle">新增</Button>
                </FormItem>
                <div class="tc mt20">
                    <Button type="primary">保存</Button>
                </div>
            </Form>
        </template>
        <template v-else>
            <div class="mb10 clear">
                <h6>追溯码查询</h6>
            </div>
            <div class="mb20 clear">
                商品名称
                <Input v-model="search.name" placeholder="请输入关键字" style="width:200px" />&nbsp;&nbsp;
                <Button type="primary">查询</Button>
            </div>
            <Table
            border
            ref="ascendTable"
            size="small"
            :columns="ascendTable.columns"
            :data="ascendTable.data" />
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
            ascendForm:{
                name:'',
                batch:'',
                produceDate:'',
                warrantyDate:'',
                produceImg:'',
                custom:[],
            },
            search:{
                name:''
            },
             ascendTable:{
                columns:[
                    {
                        title: '产品编码',
                        key: 'produceCode'
                    },{
                        title: '商品名称',
                        key: 'name'
                    },{
                        title: '产品批次号',
                        key: 'batch'
                    },{
                        title: '防伪码',
                        key: 'ascendCode',
                        render:(h,params) => {
                            return h('img',{
                                attrs:{
                                    src:'../../../src/img/' + params.row.ascendCode + '.png'
                                }
                            })
                        }
                    },{
                        title: '操作',
                        key: 'action',
                        width: 160,
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
                                            this.ascendDown(params.index)
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
                                    on: {
                                        // click: () => {
                                        //     this.codeMangeEdit(params.index)
                                        // }
                                    }
                                }),
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small',
                                        icon:'md-trash'
                                    },
                                    style: {
                                        fontSize: '16px'
                                    },
                                    on: {
                                        // click: () => {
                                        //     this.codeMangeEdit(params.index)
                                        // }
                                    }
                                })
                            ]);
                        }
                    }
                ],
                data:[
                    {
                        produceCode: 'CP01',
                        name: '萝卜',
                        batch:'11',
                        ascendCode:'u2657'

                    },{
                        produceCode: 'CP01',
                        name: '萝卜',
                        batch:'11',
                        ascendCode:'u2657'
                    }
                ],
                flag:true,
                index:0
            },
            defaultList: [
                {
                    'name': 'a42bdcc1178e62b4694c830f028db5c0',
                    'url': 'https://o5wwk8baw.qnssl.com/a42bdcc1178e62b4694c830f028db5c0/avatar'
                },
                {
                    'name': 'bc7521e033abdd1e92222d733590f104',
                    'url': 'https://o5wwk8baw.qnssl.com/bc7521e033abdd1e92222d733590f104/avatar'
                }
            ],
            imgName: '',
            visible: false,
            uploadList: []
        }
    },
    mounted(){
        this.uploadList = this.$refs.upload.fileList
        console.log(this.uploadList)
    },
    methods:{
        handleAdd () {
            this.ascendForm.custom.push({
                value: ''
            });
        },
        handleRemove (index) {
            this.$Modal.confirm({
                content: '<p>您确定删除？</p>',
                cancelText: '取消',
                onOk: () => {
                    this.handleRemoves(index)
                },
            });
        },
        handleRemoves (index) {
            this.ascendForm.custom.splice(index, 1);
        },
        // 下载
        ascendDown (index) {
            this.$refs.ascendTable.exportCsv({
                filename: '导出数据',
                columns: this.ascendTable.columns.filter((col, index) => index),
                data: this.ascendTable.data.filter((data, index) => index)
            })
        },
        removeUpload (file) {
            // 从 upload 实例删除数据
            const fileList = this.$refs.upload.fileList;
            this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
        },
        handleSuccess (res, file) {
            // 因为上传过程为实例，这里模拟添加 url
            file.url = 'https://o5wwk8baw.qnssl.com/7eb99afb9d5f317c912f08b5212fd69a/avatar';
            file.name = '7eb99afb9d5f317c912f08b5212fd69a';
        },
        handleFormatError (file) {
            this.$Notice.warning({
                title: '文件格式不正确',
                desc: '文件 ' + file.name + ' 格式不正确，请上传 jpg 或 png 格式的图片。'
            });
        },
        handleMaxSize (file) {
            this.$Notice.warning({
                title: '超出文件大小限制',
                desc: '文件 ' + file.name + ' 太大，不能超过 2M。'
            });
        },
        handleBeforeUpload () {
            const check = this.uploadList.length < 5;
            console.log('属性',this.$refs.upload.fileList)
            if (!check) {
                this.$Notice.warning({
                    title: '最多只能上传 5 张图片。'
                });
            }
            return check;
        }
    }
}
</script>

<style lang="scss">
    .upload-list{
        display: inline-block;
        width: 80px;
        height: 80px;
        text-align: center;
        line-height: 80px;
        border: 1px solid transparent;
        border-radius: 4px;
        overflow: hidden;
        background: #fff;
        position: relative;
        box-shadow: 0 1px 1px rgba(0,0,0,.2);
        margin-right: 4px;
    }
    .upload-list img{
        width: 100%;
        height: 100%;
    }
    .upload-list-cover{
        display: none;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0,0,0,.6);
    }
    .upload-list:hover .upload-list-cover{
        display: block;
    }
    .upload-list-cover i{
        color: #fff;
        font-size: 20px;
        cursor: pointer;
        margin: 0 2px;
    }
</style>