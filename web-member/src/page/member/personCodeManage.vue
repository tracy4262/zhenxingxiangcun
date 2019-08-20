<template>
<div class="layout">
    <top :address="false" />

    <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="4" class="main-l">
                        <high-app name="高级应用" />
                        <Divider />
                        <base-app name="基础应用" />
                        <Divider />
                        <base-app name="通用应用" />
                    </Col>
                    <Col span="20">
                        <member-header />

                        <Tabs value="customMange" class="person-code-tabs">
                            <TabPane label="自定义子类管理" name="customMange" class="pd20">
                                <div class="mb20 clear">
                                    自定义子类
                                    <Input v-model="customMangeSearch.subClaass" placeholder="请输入关键字" style="width:200px" />
                                    <Button type="primary">查询</Button>
                                    <Button type="default" class="fr" @click.native="addSubClass">添加</Button>
                                </div>
                                <Table
                                border
                                ref="subClassTable"
                                size="small"
                                :columns="subClassTable.columns"
                                :data="subClassTable.data" />
                            </TabPane>
                            <TabPane label="编码管理" name="codeMange" class="pd20">
                                <div class="mb20 clear">
                                    产品分类
                                    <Input v-model="codeMangeSearch.goodsClass" placeholder="请输入关键字" style="width:120px" />&nbsp;&nbsp;
                                    自定义子类
                                    <Input v-model="codeMangeSearch.subClass" placeholder="请输入关键字" style="width:120px" />&nbsp;&nbsp;
                                    产品名
                                    <Input v-model="codeMangeSearch.productName" placeholder="请输入关键字" style="width:120px" />&nbsp;&nbsp;
                                    通用商品名
                                    <Input v-model="codeMangeSearch.goodsName" placeholder="请输入关键字" style="width:120px" />
                                    <Button type="primary">查询</Button>
                                    <Button type="default" class="fr" @click.native="addCodeMange">添加</Button>
                                </div>
                                <Table
                                border
                                ref="codeMangeTable"
                                size="small"
                                :columns="codeMangeTable.columns"
                                :data="codeMangeTable.data" />

                            </TabPane>
                        </Tabs>
                     <!-- <Row :gutter="20" style="margin:10px -10px 0 0">
                            <Col span="18" style="border: 1px solid #ededed;">

                            </Col>
                            <Col span="6">
                                <div style="padding:10px;border: 1px solid #ededed;margin-bottom: 10px">
                                    <h3 style="text-align: center;font-size: 20px;line-height:1.5">好友</h3>
                                    <Input v-model="value" placeholder="请输入..." icon="ios-search" style="width: 100%" />
                                    <div style="height:280px;">
                                        <span style="font-size: 12px;color: #a6a6a6;margin-top: 10px;margin-left: 10px;">还没有好友</span>
                                    </div>
                                </div>
                                <div style="padding: 0px 10px;border: 1px solid #ededed;margin-bottom: 20px;padding-bottom: 20px">
                                    <p style="font-size: 14px;float: right;margin-top: 20px;color: #333">
                                        <router-link to="/circle">逛圈子</router-link>
                                        <Icon type="ios-arrow-right"></Icon>
                                    </p>

                                    <h3 style="text-align: center;font-size: 20px;line-height: 58px">圈子</h3>
                                    <Input v-model="value" placeholder="请输入..." icon="ios-search" />
                                    <Row :gutter="10">
                                        <Col span="8" v-for="item in circleList">
                                            <a :href="item.url" class="t-grey">
                                                <img class="mt10" :src="item.src">
                                                <p class="ell">{{item.title}}</p>
                                            </a>
                                        </Col>
                                    </Row>
                                </div>
                            </Col>
                        </Row> -->

                    </Col>
                </Row>
            </div>
        </div>
        <!-- 弹层 -->
        <Modal
            v-model="subClassModalShow"
            title="自定义分类管理"
            @on-ok="handleSubClassOk"
            @on-cancel="handleSubClassCancel">
            <Form :model="subClassForm" :label-width="100">
                <FormItem label="自定义类名称">
                    <Input v-model="subClassForm.name" placeholder="请输入" />
                </FormItem>
                <FormItem label="产品分类">
                    <Input v-model="subClassForm.subClass" placeholder="请输入" />
                </FormItem>
            </Form>
        </Modal>

        <Modal
            v-model="codeMangeModalShow"
            title="编码管理"
            width="600"
            @on-ok="handleCodeMangeOk"
            @on-cancel="handleCodeMangeCancel">
            <Form :model="codeMangeForm" :label-width="100">
                <FormItem label="选择仓库内产品">
                    <Select v-model="codeMangeForm.WarehProduct">
                        <Option value="粮食类">粮食类</Option>
                        <Option value="糖料类">糖料类</Option>
                        <Option value="豆类">豆类</Option>
                    </Select>
                </FormItem>
                <FormItem label="产品名">
                    <Row>
                        <Col span="9">
                            <Input v-model="codeMangeForm.productName" placeholder="请输入" />
                        </Col>
                        <Col span="6" class="tr">自定义子类&emsp;</Col>
                        <Col span="9">
                            <Input v-model="codeMangeForm.subClass" placeholder="请输入" />
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="单位">
                    <Row>
                        <Col span="9">
                            <Input v-model="codeMangeForm.unit" placeholder="请输入" />
                        </Col>
                        <Col span="6" class="tr">数量&emsp;</Col>
                        <Col span="9">
                            <InputNumber :max="10000" :min="1" v-model="codeMangeForm.number" />
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="防伪码数量">
                    <Row>
                        <Col span="9">
                            <InputNumber :max="10000" :min="1" v-model="codeMangeForm.securityCode" />
                        </Col>
                        <Col span="6" class="tr">国际商品码&emsp;</Col>
                        <Col span="9">
                            <Input v-model="codeMangeForm.internaCode" placeholder="请输入" />
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="是否可追溯">
                    <Row>
                        <Col span="9">
                            <RadioGroup v-model="codeMangeForm.isAscend">
                                <Radio label="是" />
                                <Radio label="否" />
                            </RadioGroup>
                        </Col>
                        <Col span="6" class="tr">是否可防伪&emsp;</Col>
                        <Col span="9">
                            <RadioGroup v-model="codeMangeForm.isSecurity">
                                <Radio label="是" />
                                <Radio label="否" />
                            </RadioGroup>
                        </Col>
                    </Row>
                </FormItem>
                <Row class="tc" :gutter="40">
                    <Col span="12">
                        <p>追溯码二维码</p>
                        <img src="../../../static/datas/img/detail.png" width="80%" height="120" />
                    </Col>
                    <Col span="12">
                        <p>国际码条形码</p>
                        <img src="../../../static/datas/img/detail.png" width="80%" height="120" />
                    </Col>
                </Row>
            </Form>
        </Modal>
   </div>
</template>

<script>
import  top from '../../top'
import  highApp from '~components/memberHighApp'
import  BaseApp from '~components/memberBaseApp'
import memberHeader from './components/memberHeader'

export default {
    components:{
        top,
        highApp,
        BaseApp,
        memberHeader
    },
    data() {
        return {
            displayName: '吴刚',
            loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            signature: '还没有签名！',
            value:'',
            customMangeSearch:{
                subClass:''
            },
            subClassModalShow:false,
            subClassForm: {
                subClass: '',
                name: ''
            },
            subClassTable:{
                columns:[
                    {
                        type: 'index',
                        width: 60,
                        align: 'center'
                    },{
                        title: '商品名称',
                        key: 'name'
                    },{
                        title: '自定义子类',
                        key: 'subClass'
                    },{
                        title: '操作',
                        key: 'action',
                        width: 150,
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
                                            this.subClassDown(params.index)
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
                                        click: () => {
                                            this.subClassEdit(params.index)
                                        }
                                    }
                                })
                            ]);
                        }
                    }
                ],
                data:[
                    {
                        name: '粮食类',
                        subClass: '大豆1号'
                    },{
                        name: '糖料类',
                        subClass: '甘蔗'
                    }
                ],
                flag:true,
                index:0
            },
            codeMangeSearch:{
                goodsClass:'',
                subClass:'',
                productName:'',
                goodsName:''
            },
            codeMangeTable:{
                columns:[
                    {
                        title: '产品分类',
                        key: 'class'
                    },{
                        title: '自定义子类',
                        key: 'subClass'
                    },{
                        title: '通用商品名',
                        key: 'goodsName'
                    },{
                        title: '产品名',
                        key: 'productName'
                    },{
                        title: '数量',
                        key: 'number'
                    },{
                        title: '批次号',
                        key: 'batchNum'
                    },{
                        title: '国际商品码',
                        key: 'internaCode'
                    },{
                        title: '追溯码',
                        key: 'ascendCode'
                    },{
                        title: '防伪码（个）',
                        key: 'securityCode'
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
                                    on: {
                                        click: () => {
                                            this.codeMangeEdit(params.index)
                                        }
                                    }
                                })
                            ]);
                        }
                    }
                ],
                data:[
                    {
                        class: '粮食类',
                        subClass: '大豆1号',
                        goodsName:'黄豆',
                        productName:'黄豆1号',
                        number:1,
                        batchNum:'201708180001',
                        internaCode:'69000457811123',
                        ascendCode:'452525234',
                        securityCode:3
                    },{
                        class: '粮食类',
                        subClass: '大豆1号',
                        goodsName:'黄豆',
                        productName:'黄豆1号',
                        number:1,
                        batchNum:'201708180001',
                        internaCode:'69000457811123',
                        ascendCode:'452525234',
                        securityCode:3
                    }
                ],
                flag:true,
                index:0
            },
            codeMangeModalShow:false,
            codeMangeForm: {
                WarehProduct: '',
                productName: '',
                subClass:'',
                unit:'',
                number:0,
                securityCode:3,
                internaCode:'',
                isAscend:'',
                isSecurity:''
            },
        }
    },
    created(){
    },
    methods:{
        // 自定义子分类
        // 添加
        addSubClass(){
            this.subClassTable.flag = true
            this.subClassModalShow = true
            this.subClassForm = {
                name:'',
                subClass:''
            }
        },
        // 保存
        handleSubClassOk(){
            if(this.subClassTable.flag){
                this.subClassModalShow = false
                this.subClassTable.data.push({
                    name:this.subClassForm.name,
                    subClass:this.subClassForm.subClass
                })
            }else{
                var index = this.subClassTable.index
                this.subClassTable.data[index].name = this.subClassForm.name
                this.subClassTable.data[index].subClass = this.subClassForm.subClass
            }
        },
        // 取消
        handleSubClassCancel(){
            this.subClassModalShow = false
        },
        // 下载
        subClassDown (index) {
            this.$refs.subClassTable.exportCsv({
                filename: '导出数据',
                columns: this.subClassTable.columns.filter((col, index) => index),
                data: this.subClassTable.data.filter((data, index) => index)
            })
        },
        // 修改
        subClassEdit (index) {
            this.subClassTable.flag = false
            this.subClassTable.index = index
            this.subClassForm = {
                name:this.subClassTable.data[index].name,
                subClass:this.subClassTable.data[index].subClass
            }
            this.subClassModalShow = true
        },
        // 编码管理
        addCodeMange(){
            this.codeMangeTable.flag = true
            this.codeMangeModalShow = true
            this.codeMangeForm={
                WarehProduct: '',
                productName: '',
                subClass:'',
                unit:'',
                number:0,
                securityCode:0,
                internaCode:'',
                isAscend:'',
                isSecurity:''
            }
        },
        handleCodeMangeOk(){
            if(this.codeMangeTable.flag){
                this.codeMangeModalShow = false
                this.codeMangeTable.data.push({
                    class:this.codeMangeForm.WarehProduct,
                    subClass: this.codeMangeForm.subClass,
                    goodsName:this.codeMangeForm.goodsName,
                    productName:this.codeMangeForm.productName,
                    number:this.codeMangeForm.number,
                    batchNum:'201708180001',
                    internaCode:'',
                    ascendCode:'',
                    securityCode:this.codeMangeForm.securityCode
                })
            }else{
                var index = this.codeMangeTable.index
                this.codeMangeTable.data[index].class = this.codeMangeForm.WarehProduct
                this.codeMangeTable.data[index].subClass = this.codeMangeForm.subClass
                this.codeMangeTable.data[index].goodsName = this.codeMangeForm.goodsName
                this.codeMangeTable.data[index].productName = this.codeMangeForm.productName
                this.codeMangeTable.data[index].number = this.codeMangeForm.number
                this.codeMangeTable.data[index].securityCode = this.codeMangeForm.securityCode
            }
        },
        handleCodeMangeCancel(){
            this.codeMangeModalShow = false
        },
        codeMangeEdit(index){
            this.codeMangeTable.flag = false
            this.codeMangeTable.index = index
            this.codeMangeForm = {
                WarehProduct: this.codeMangeTable.data[index].class,
                productName: this.codeMangeTable.data[index].productName,
                subClass:this.codeMangeTable.data[index].productName,
                unit:'',
                number:this.codeMangeTable.data[index].number,
                securityCode:this.codeMangeTable.data[index].securityCode,
                internaCode:'',
                ascendCode:''
            }
            this.codeMangeModalShow = true
        },
        // 下载
        codeMangeDown (index) {
            this.$refs.codeMangeTable.exportCsv({
                filename: '导出数据',
                columns: this.codeMangeTable.columns.filter((col, index) => index),
                data: this.codeMangeTable.data.filter((data, index) => index)
            })
        },
        logout() {
            axios.get(this.$url.api + 'login/logout')
                .then(response => {
                    console.log(response.data)
                })
            sessionStorage.removeItem("user")
            this.$router.push('/index')
        },
        show() {
            this.modal2 = true;
            this.$router.push("/pro/member/step1");
        },
        tabsClick(name){
            this.tabsDynaShow = (name === 'name1' ? true : false)
            //this.columnMydynamic=[]
            //showMydynamic(0)
        },
        publish() {
            if(this.publishShow == false) {
                this.publishShow = true
            }
        },
        nextPage(page) {
            this.currentPage = page;
            axios.get(this.$url.api + '/login/friendlist/' + this.currentPage)
                .then(response => {
                    this.friend = response.data.list
                    this.total = response.data.total
                })
        },
    }
}
</script>
