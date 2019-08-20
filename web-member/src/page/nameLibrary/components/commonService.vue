<template>
    <div>
        <div v-if="listShow" style="width: 100% ;height: 100%">
            <Row>
                <Col span="20">
                <Form :model="formInline6" inline :label-width="100">
                    <Row>
                        <Col span="8">
                        <FormItem label="通用服务名：">
                            <Input v-model="formInline6.tyfwmc" placeholder="请输入内容"/>
                        </FormItem>
                        </Col>
                        <Col span="8">
                        <FormItem label="服务分类：">
                            <Cascader :data="specData" v-model="formInline6.fwfl"></Cascader>
                        </FormItem>
                        </Col>
                        <Col span="8">
                        <FormItem label="行业分类：">
                            <Cascader :data="specData" v-model="formInline6.hyfl"></Cascader>
                        </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="8">
                        <FormItem label="关联物种：">
                            <Cascader :data="specData" v-model="formInline6.glwz"></Cascader>
                        </FormItem>
                        </Col>
                    </Row>
                </Form>
                </Col>
                <Col span="2">
                <ButtonGroup size="large">
                    <Button type="default"
                            style="background-color: #2db7f5; color: white;border-color: #2db7f5;">
                        查找
                    </Button>
                </ButtonGroup>
                <br/>
                <br/>
                <ButtonGroup size="large">
                    <Button type="default" @click="addCommonService()"
                            style="background-color: #2db7f5; color: white;border-color: #2db7f5;">
                        新增通用服务名
                    </Button>
                </ButtonGroup>
                </Col>
            </Row>
            <Row style="margin: 10px;">
                <Col span="24">
                <i-table border :content="self" :columns="columns6" :data="data6"></i-table>
                </Col>
            </Row>
            <Row>
                <Col span="11" offset="13">
                <Page :total="100" show-elevator></Page>
                </Col>
            </Row>
        </div>
        <Col v-if="addShow" span="24">
            <h2>新增通用服务名</h2>
            <div style="margin-top:50px; display: flex; justify-content: center; align-items: center;" >
                <div style="width: 50%; margin-left: 100px;">
                    <Steps :current="currentStep" >
                        <Step title="通用服务名信息"></Step>
                        <Step title="提交审核"></Step>
                    </Steps>
                </div>
            </div>
            <div align="left" style="margin-top: 5%;margin-left: 30%;margin-right: 30%" >
                <Form :model="formItem" :label-width="140" label-position="right">
                    <FormItem label="通用服务名称：">
                        <Input v-model="formItem.tyfwmc" placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="通用服务名拼音：">
                        <Input v-model="formItem.tyfwmpy" placeholder="由通用服务名称自动生成拼音"></Input>
                    </FormItem>
                    <FormItem label="俗名别名：">
                        <Input v-model="formItem.smbm" placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="行业分类：">
                        <Cascader :data="specData" v-model="formItem.hyfl"></Cascader>
                    </FormItem>
                    <FormItem label="服务分类：">
                        <Cascader :data="specData" v-model="formItem.fwfl"></Cascader>
                    </FormItem>
                    <FormItem label="关联物种：">
                        <Cascader :data="specData" v-model="formItem.glwz"></Cascader>
                    </FormItem>
                    <FormItem label="释义：">
                        <Input v-model="formItem.sy" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></Input>
                    </FormItem>
                </Form>
            </div>
            <div align="center">
                <Button class="ivu-btn-primary" type="default" @click="next()">下一步</Button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <Button type="default" @click="complete()">退出</Button>
            </div>
            <div style="height: 60px;"></div>
        </Col>
        <Col v-if="addFinsihedShow" span="24">
            <h2>新增通用服务名</h2>
            <div style="margin-top: 5%; display: flex; justify-content: center; align-items: center;" >
                <div style="width: 50%; margin-left: 100px;">
                    <Steps :current="currentStep" >
                        <Step title="通用服务名信息"></Step>
                        <Step title="提交审核"></Step>
                    </Steps>
                </div>
            </div>
            <div align="center" class="tipcomit">
                <h2>您已提交新的通用服务信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
            </div>
            <div align="center" style="margin-top: 5%">
                <Button type="primary"  @click="complete">完成</Button>
            </div>
        </Col>
    </div>
</template>

<script>
    export default {
        components: {

        },
        data () {
            return {
                listShow: true,
                addShow: false,
                addFinsihedShow: false,
                self: this,
                currentStep: 0,
                formItem: {
                    tyfwmc: '',
                    tyfwmpy: '',
                    smbm: '',
                    hyfl: [],
                    fwfl: [],
                    glwz: '',
                    sy: ''
                },
                formInline6: {
                    tyfwmc: '',
                    fwfl: [],
                    hyfl: [],
                    glwz: []
                },
                specData: [{
                    value: '0',
                    label: '动物',
                    children: [
                        {
                            value: 'WZ0201',
                            label: '肉用类'
                        },
                        {
                            value: 'WZ0202',
                            label: '肉蛋兼用类'
                        },
                        {
                            value: 'WZ0203',
                            label: '肉奶兼用类'
                        },
                        {
                            value: 'WZ0204',
                            label: '肉毛(绒)兼用类'
                        },
                        {
                            value: 'WZ0206',
                            label: '肉皮兼用类'
                        },
                        {
                            value: 'WZ0207',
                            label: '肉药兼用类'
                        },
                        {
                            value: 'WZ0208',
                            label: '役力类'
                        },
                        {
                            value: 'WZ0209',
                            label: '丝蜜类'
                        },
                        {
                            value: 'WZ0210',
                            label: '宠娱观赏类'
                        }
                        ,
                        {
                            value: 'WZ0211',
                            label: '实验类'
                        },
                        {
                            value: 'WZ0213',
                            label: '饲饵类'
                        }
                    ]
                },
                    {
                        value: '1',
                        label: '植物',
                        children: [
                            {
                                value: 'WZ0101',
                                label: '粮食类'
                            },
                            {
                                value: 'WZ0102',
                                label: '纤维类'
                            },
                            {
                                value: 'WZ0103',
                                label: '油料类'
                            },
                            {
                                value: 'WZ0104',
                                label: '糖料类'
                            },
                            {
                                value: 'WZ0111',
                                label: '嗜好类'
                            },
                            {
                                value: 'WZ0114',
                                label: '瓜果蔬类'
                            },
                            {
                                value: 'WZ0116',
                                label: '食用菌藻类'
                            },
                            {
                                value: 'WZ0105',
                                label: '饮料类'
                            },
                            {
                                value: 'WZ0106',
                                label: '调料类'
                            }
                            ,
                            {
                                value: 'WZ0113',
                                label: '香料类'
                            },
                            {
                                value: 'WZ0112',
                                label: '药用类'
                            },
                            {
                                value: 'WZ0117',
                                label: '花卉类'
                            },
                            {
                                value: 'WZ0121',
                                label: '绿化观赏林木'
                            },
                            {
                                value: 'WZ0107',
                                label: '染料类'
                            },
                            {
                                value: 'WZ0108',
                                label: '漆料类'
                            },
                            {
                                value: 'WZ0109',
                                label: '胶液料类'
                            },
                            {
                                value: 'WZ0110',
                                label: '鞣料类'
                            },
                            {
                                value: 'WZ0118',
                                label: '饲料类'
                            },
                            {
                                value: 'WZ0119',
                                label: '肥料类'
                            },
                            {
                                value: 'WZ0120',
                                label: '防护用材林木'
                            },
                            {
                                value: 'WZ0115',
                                label: '能源类'
                            }
                        ]
                    }
                ],
                columns6: [
                    {
                        title: '名称',
                        key: 'name',
                        align: 'center',
                    },
                    {
                        title: '关联物种',
                        key: 'glwz',
                        align: 'center',
                    },
                    {
                        title: '服务分类',
                        key: 'fwfl',
                        align: 'center',
                    },
                    {
                        title: '审核状态',
                        key: 'shzt',
                        align: 'center',
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 200,
                        align: 'center',
                        render: (h, params) => {
                            if (params.row.shzt === '待审核') {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'error',
                                            size: 'small'
                                        },
                                        style: {
                                            marginRight: '5px',
                                            backgroundColor: '#0c6',
                                            borderColor: '#0c6'
                                        },
                                        on: {
                                            click: () => {
                                                alert("编辑")
                                            }
                                        }
                                    }, '编辑'),
                                    h('Button', {
                                        props: {
                                            type: 'error',
                                            size: 'small'
                                        },
                                        style: {
                                            marginRight: '5px',
                                            backgroundColor: '#f30',
                                            borderColor: '#f30'
                                        },
                                        on: {
                                            click: () => {
                                                this.remove6(params.index)
                                            }
                                        }
                                    }, '删除'),
                                    h('Button', {
                                        props: {
                                            type: 'error',
                                            size: 'small'
                                        },
                                        style: {
                                            backgroundColor: '#f90',
                                            borderColor: '#f90'
                                        },
                                        on: {
                                            click: () => {
                                                alert("图库")
                                            }
                                        }
                                    }, '图库')
                                ])
                            } else if (params.row.shzt === '已通过') {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'error',
                                            size: 'small'
                                        },
                                        style: {
                                            marginLeft: '5px',
                                            backgroundColor: '#2db7f5',
                                            borderColor: '#2db7f5'
                                        },
                                        on: {
                                            click: () => {
                                                this.showInfo6(params.index)
                                            }
                                        }
                                    }, '查看')
                                ])
                            }
                        }
                    }
                ],
                data6: [
                    {
                        name: '畜（禽）饲养服务',
                        glwz: '黄牛,猪,山羊,鸡',
                        fwfl: '畜牧生产',
                        shzt: '待审核'
                    },
                    {
                        name: '畜（禽）饲养服务',
                        glwz: '黄牛,猪,山羊,鸡',
                        fwfl: '畜牧生产',
                        shzt: '已通过'
                    },
                    {
                        name: '畜（禽）饲养服务',
                        glwz: '黄牛,猪,山羊,鸡',
                        fwfl: '畜牧生产',
                        shzt: '已通过'
                    },
                    {
                        name: '畜（禽）饲养服务',
                        glwz: '黄牛,猪,山羊,鸡',
                        fwfl: '畜牧生产',
                        shzt: '已通过'
                    }
                ]
            }
        },
        created () {

        },
        methods: {
            // 点击跳转新增通用服务名称
            addCommonService() {
                this.listShow = false
                this.addShow = true
                this.addFinsihedShow = false
            },
            showInfo6(index) {
                this.$Modal.info({
                    title: '服务信息',
                    content: `名称：${this.data6[index].name}<br>关联物种：${this.data6[index].glwz}<br>服务分类：${this.data6[index].fwfl}<br>审核状态：${this.data6[index].shzt}`
                })
            },
            remove6(index) {
                this.data6.splice(index, 1);
            },
            next () {
                this.listShow = false
                this.addShow = false
                this.addFinsihedShow = true
            },
            complete () {
                this.listShow = true
                this.addShow = false
                this.addFinsihedShow = false
            }
        }
    }
</script>

<style scoped>

</style>