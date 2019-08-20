<template>
<div class="wrapper layout">
    <top :address="false" />
    <member-header />
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
                        
                        <Tabs value="apply" class="company-code-tabs">
                            <TabPane label="申请公众号" name="apply" class="pd20">
                                <div style="width:500px">
                                    <Input v-model="apply" type="textarea" :rows="8" placeholder="填写申请..." />
                                    <div class="tc mt10">
                                        <Button type="primary">保存</Button>
                                        <Button type="default">取消</Button>
                                    </div>
                                </div>
                            </TabPane>
                            <TabPane label="信息维护" name="mainte" class="pd20">
                                <quill-editor v-model="content"
                                  :options="editorOption" />
                                <div class="tc mt10">
                                    <Button type="primary">保存</Button>
                                    <Button type="default">取消</Button>
                                </div>
                            </TabPane>
                            <TabPane label="关注我的" name="follow" class="pd20">
                                <div class="mb20 clear">
                                    关键字
                                    <Input v-model="search.name" placeholder="请输入关键字" style="width:200px" />&nbsp;&nbsp;
                                    会员类型
                                    <Select v-model="search.type" style="width:200px">
                                        <Option value="1">专家会员</Option>
                                        <Option value="2">普通会员</Option>
                                        <Option value="3">乡村会员</Option>
                                        <Option value="4">企业会员</Option>
                                        <Option value="5">机关会员</Option>
                                    </Select>
                                    <Button type="primary">查询</Button>
                                </div>
                                <Table
                                    border
                                    ref="followTable"
                                    size="small"
                                    :columns="followTable.columns"
                                    :data="followTable.data" />
                            </TabPane>
                            <TabPane label="发送消息" name="sendMsg" class="pd20">
                                <quill-editor v-model="content"
                                  :options="editorOption" />
                                <div class="tc mt10">
                                    <Button type="primary">保存</Button>
                                    <Button type="default">取消</Button>
                                </div>
                            </TabPane>
                            <TabPane label="消息记录" name="msgRecord" class="pd20">
                                <div class="mb20 clear">
                                    起止日期
                                    <DatePicker :value="search.date" format="yyyy/MM/dd" type="daterange" placement="bottom-start" placeholder="选择日期" style="width: 200px;display:inline-block" />&nbsp;&nbsp;
                                    <Button type="primary">查询</Button>
                                </div>
                                <Table
                                    border
                                    size="small"
                                    :columns="msgRecordTable.columns"
                                    :data="msgRecordTable.data" />
                            </TabPane>
                        </Tabs>

                    </Col>
                </Row>
            </div>
        </div>
   </div>
</template>

<script>
import  top from '../../top'
import  highApp from '~components/memberHighApp'
import  BaseApp from '~components/memberBaseApp'

import { quillEditor } from 'vue-quill-editor'
import memberHeader from './components/memberHeader'

export default {
    components:{
        top,
        highApp,
        BaseApp,
        quillEditor,
        memberHeader
    },
    data() {
        return {
            displayName: '吴刚',
            loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            signature: '还没有签名！',
            followTable:{
                columns:[
                    {
                        title: '会员ID',
                        key: 'id'
                    },{
                        title: '会员类型',
                        key: 'type'
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
                                        size: 'small'
                                    },
                                    style:{
                                        color:'#00c587'
                                    }
                                },'查看空间')
                            ]);
                        }
                    }
                ],
                data:[
                    {
                        id: 'UserName',
                        type: '专家会员'

                    },{
                        id: 'UserName',
                        type: '专家会员'

                    },{
                        id: 'UserName',
                        type: '专家会员'

                    },{
                        id: 'UserName',
                        type: '专家会员'

                    }
                ]
            },
            msgRecordTable:{
                columns:[
                    {
                        title: '消息标题',
                        key: 'title'
                    },{
                        title: '消息内容',
                        key: 'cont'
                    },{
                        title: '发送时间',
                        key: 'time'
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
                                        size: 'small'
                                    },
                                    style:{
                                        color:'#00c587'
                                    }
                                },'查看')
                            ]);
                        }
                    }
                ],
                data:[
                    {
                        title: '消息测试',
                        cont: '测试内容',
                        time:'2014/2/26 16:56:00'

                    },{
                        title: '消息测试',
                        cont: '测试内容',
                        time:'2014/2/26 16:56:00'
                    }
                ]
            },
            content: '',
            editorOption: {
                modules: {
                    toolbar: [
                      [{ 'size': ['small', false, 'large'] }],
                      ['bold', 'italic'],
                      [{ 'align': [] }],
                      [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                      ['link', 'image']
                    ]
                },
                placeholder:'',
            },
            apply:'',
            search:{
                name:'',
                type:'',
                date:[]
            }
        }
    },
    created(){
    }
}
</script>

<style lang="scss">
.quill-editor{
    .ql-editor{
    height: 220px;
  }
}
    
</style>