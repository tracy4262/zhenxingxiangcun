<template>
    <div>
        <Table border :columns="columns" :data="data"></Table>
        <Modal v-model="addSameLevelModal" width="500" title="增加同级" :closable="false" :mask-closable="false">
            <div>
                <Form ref="addSameLevelFormItem" :model="addSameLevelForm" label-position="left" :label-width="100" :rules="addSameLevelFormItemInline">
                    <Form-item label="科目代码"><!-- 增加一级的同级 科目代码用户手动输入 其余的是自动生成 -->
                        <Input v-model="addSameLevelForm.subjectCode" :readonly="!(addSameLevelForm.subjectLevel === 1)" />
                    </Form-item>
                    <Form-item label="科目级别">
                        <Input v-model="addSameLevelForm.subjectLevel" readonly />
                    </Form-item>
                    <Form-item label="科目名称">
                        <Input v-model="addSameLevelForm.subjectName" />
                    </Form-item>
                    <Form-item label="余额方向">
                        <Select v-model="addSameLevelForm.balanceDirection">
                            <Option value="借方">借方</Option>
                            <Option value="贷方">贷方</Option>
                        </Select>
                    </Form-item>
                    <Form-item label="科目说明">
                        <Input v-model="addSameLevelForm.subjectExplain" />
                    </Form-item>
                </Form>
            </div>
            <div slot="footer">
                <Button type="text" @click="addSameLevelCalcel">取消</Button>
                <Button type="primary" @click="addSameLevelOk">确定</Button>
            </div>
        </Modal>
        <Modal v-model="EditModal" width="500" title="编辑" :closable="false" :mask-closable="false">
            <div>
                <Form ref="editFormItem" :model="editForm" label-position="left" :label-width="100" :rules="editFormItemInline">
                    <Form-item label="科目代码"><!-- 只有一级的科目代码可编辑 -->
                        <Input v-model="editForm.subjectCode" :readonly="!(editForm.subjectLevel === 1)" />
                    </Form-item>
                    <Form-item label="科目级别">
                        <Input v-model="editForm.subjectLevel" readonly />
                    </Form-item>
                    <Form-item label="科目名称">
                        <Input v-model="editForm.subjectName" />
                    </Form-item>
                    <Form-item label="余额方向">
                        <Select v-model="editForm.balanceDirection">
                            <Option value="借方">借方</Option>
                            <Option value="贷方">贷方</Option>
                        </Select>
                    </Form-item>
                    <Form-item label="科目说明">
                        <Input v-model="editForm.subjectExplain" />
                    </Form-item>
                </Form>
            </div>
            <div slot="footer">
                <Button type="text" @click="EditCalcel">取消</Button>
                <Button type="primary" @click="EditOk">确定</Button>
            </div>
        </Modal>
        <Modal v-model="addLowerLevelModal" width="500" title="增加下级" :closable="false" :mask-closable="false">
            <div>
                <Form ref="addLowerLevelFormItem" :model="addLowerLevelForm" label-position="left" :label-width="100" :rules="addLowerLevelFormItemInline">
                    <Form-item label="科目代码">
                        <Input v-model="addLowerLevelForm.subjectCode" readonly />
                    </Form-item>
                    <Form-item label="科目级别">
                        <Input v-model="addLowerLevelForm.subjectLevel" readonly />
                    </Form-item>
                    <Form-item label="科目名称">
                        <Input v-model="addLowerLevelForm.subjectName" />
                    </Form-item>
                    <Form-item label="余额方向">
                        <Select v-model="addLowerLevelForm.balanceDirection">
                            <Option value="借方">借方</Option>
                            <Option value="贷方">贷方</Option>
                        </Select>
                    </Form-item>
                    <Form-item label="科目说明">
                        <Input v-model="addLowerLevelForm.subjectExplain" />
                    </Form-item>
                </Form>
            </div>
            <div slot="footer">
                <Button type="text" @click="addLowerLevelCalcel">取消</Button>
                <Button type="primary" @click="addLowerLevelOk">确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
export default {
    props: {
        type: {
            type: String
        },
        status: {
            type: Boolean
        },
        modeId: {
            type: String
        },
        yearId: {
            type: String
        }
    },
    data () {
        return {
            columns: [
                {
                    title: '科目代码',
                    key: 'subjectCode'
                },
                {
                    title: '科目级别',
                    key: 'subjectLevel'
                },
                {
                    title: '科目名称',
                    key: 'subjectName'
                },
                {
                    title: '余额方向',
                    key: 'balanceDirection'
                },
                {
                    title: '科目说明',
                    key: 'subjectExplain'
                },
                {
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
                                    disabled: params.row.id === undefined || params.row.id === '' ? true : false
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.addSameLevel(params.row)
                                    }
                                }
                            }, '增加同级'),
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
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
                                    size: 'small',
                                    disabled: params.row.id === undefined || params.row.id === '' ? true : false
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.addLowerLevel(params.row)
                                    }
                                }
                            }, '增加下级'),
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small',
                                    disabled: params.row.id === undefined || params.row.id === '' ? true : false
                                },
                                on: {
                                    click: () => {
                                        this.del(params.row)
                                    }
                                }
                            }, '删除'),
                        ]);
                    }
                }
            ],
            data: [
                {
                    subjectCode: '',
                    subjectLevel: 1,
                    subjectName: '',
                    balanceDirection: '',
                    subjectExplain: ''
                }
            ],
            EditModal: false,
            addSameLevelModal: false,
            addLowerLevelModal: false,
            addSameLevelForm: {
                subjectCode: '',
                subjectLevel: 0,
                subjectName: '',
                balanceDirection: '',
                subjectExplain: ''
            },
            addSameLevelFormItemInline: {},
            editForm: {
                subjectCode: '',
                subjectLevel: 0,
                subjectName: '',
                balanceDirection: '',
                subjectExplain: ''
            },
            editFormItemInline: {},
            addLowerLevelForm: {
                subjectCode: '',
                subjectLevel: 0,
                subjectName: '',
                balanceDirection: '',
                subjectExplain: ''
            },
            addLowerLevelFormItemInline: {},
            editIndex: -1,
            faninceStatusId: ''
        }
    },
    methods: {
        addSameLevel (row) {
            console.log('row', row)
            console.log('this.data', this.data)
            this.addSameLevelModal = true
            if (row.subjectLevel === 1) {
                this.addSameLevelForm = {
                    id: row.id,
                    pid: row.pid,
                    subjectCode: '',
                    subjectLevel: row.subjectLevel,
                    subjectName: '',
                    balanceDirection: '',
                    subjectExplain: ''
                }
            } else {
                let subjectCode = 0
                // 找出pid相等的集合 即同级的有多少
                let arr = this.data.filter(element => {
                    return element.pid === row.pid
                })
                // 如果不存在则拼接上一级科目代码并增加两位从01开始编号 如果存在则找出最大的并在最后两位+1
                if (arr.length === 0) {
                    // 能点击添加同级的情况下arr是不可能为空的
                } else {
                    // 找出最大的subjectCode
                    let sc = arr[arr.length - 1].subjectCode
                    // 截取最后两位转int并+1
                    let index = parseInt(sc.substring(sc.length - 2)) + 1
                    // 再拼接回去
                    subjectCode = sc.substring(0, sc.length - 1) + index
                }
                this.addSameLevelForm = {
                    id: row.id,
                    pid: row.pid,
                    subjectCode: subjectCode,
                    subjectLevel: row.subjectLevel,
                    subjectName: '',
                    balanceDirection: '',
                    subjectExplain: ''
                }
            }
            console.log('this.addSameLevelForm', this.addSameLevelForm)
        },
        addLowerLevel (row) {
            console.log('row', row)
            console.log('this.data', this.data)
            this.addLowerLevelModal = true

            let subjectCode = 0
            // 找出以此行id为pid的集合 即下级的有多少
            let arr = this.data.filter(element => {
                return element.pid === row.id
            })
            // 如果不存在则拼接上一级科目代码并增加两位从01开始编号 如果存在则找出最大的并在最后两位+1
            if (arr.length === 0) {
                // 能点击添加同级的情况下arr是有可能为空的
                subjectCode = row.subjectCode + '01'
            } else {
                // 找出最大的subjectCode
                let sc = arr[arr.length - 1].subjectCode
                // 截取最后两位转int并+1
                let index = parseInt(sc.substring(sc.length - 2)) + 1
                // 再拼接回去
                subjectCode = sc.substring(0, sc.length - 1) + index
            }
            this.addLowerLevelForm = {
                id: row.id,
                pid: row.pid,
                subjectCode: subjectCode,
                subjectLevel: row.subjectLevel + 1,
                subjectName: '',
                balanceDirection: '',
                subjectExplain: ''
            }
        },
        edit (row) {
            this.EditModal = true
            console.log('row', row)
            this.editForm = row
            this.editIndex = row._index
        },
        del (row) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认删除？',
                onOk:()=>{
                    if (row.id !== undefined && row.id !== '') {
                        this.$api.post('/member-reversion/finance/deleteAccountingSubjectsInfo', {
                            id: row.id
                        }).then(response => {
                            console.log('response', response)
                            if (response.code === 200) {
                                this.$Message.success('删除成功！')
                                this.init()
                            } else if (response.code === 300) {
                                this.$Message.info('请先删除下级！')
                            }
                        }).catch(error => {
                            this.$Message.error('服务器异常！')
                        })
                    }
                },
                okText:'确定',
                cancelText:'取消'
            })
        },
        addSameLevelCalcel () {
            this.addSameLevelModal = false
        },
        addSameLevelOk () {
            // this.data.push(this.addSameLevelForm)
            this.save(this.addSameLevelForm, 'same')
            this.addSameLevelModal = false
        },
        EditCalcel () {
            this.EditModal = false
        },
        EditOk () {
            // this.data[this.editIndex] = this.editForm
            this.save(this.editForm, 'edit')
            this.EditModal = false
        },
        addLowerLevelCalcel () {
            this.addLowerLevelModal = false
        },
        addLowerLevelOk () {
            // this.data.push(this.addLowerLevelForm)
            this.save(this.addLowerLevelForm, 'lower')
            this.addLowerLevelModal = false
        },
        init () {
            this.$api.post('/member-reversion/finance/findAccountingSubjectsInfo', {
                account: this.$user.loginAccount,
                yearId: this.yearId,
                parentId: this.modeId,
                type: this.type
            }).then(response => {
                console.log('response', response)
                if (response.code === 200) {
                    if (response.data.faninceStatusId !== undefined && response.data.faninceStatusId !== '') {
                        this.faninceStatusId = response.data.faninceStatusId
                    }
                    if (response.data.propertyName !== undefined && response.data.propertyName !== '') {
                        // this.title = response.data.propertyName
                        this.$emit('handleGiveTitle', response.data.propertyName)
                    }
                    this.$emit('handleGiveStatus', response.data.status)
                    if (response.data.accountingSubjects && response.data.accountingSubjects.length !== 0) {
                        this.data = []
                        response.data.accountingSubjects.forEach(element => {
                            this.data.push({
                                id: element.id,
                                pid: element.pid,
                                subjectCode: element.subjectCode,
                                subjectLevel: parseInt(element.subjectLevel),
                                subjectName: element.subjectName,
                                balanceDirection: element.balanceDirection,
                                subjectExplain: element.subjectExplain
                            })
                            /* if(element.children && element.children.length > 0) {
                                this.getSonList(element.children)
                            } */
                        })
                    }
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        save (form, str) {
            let data = {}
            if (str === 'edit') {
                data = {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    parentId: this.modeId,
                    status: this.status,
                    faninceStatusId: this.faninceStatusId === '' ? 0 : this.faninceStatusId,
                    accountingSubjects: {
                        type: this.type,
                        pid: form.pid !== undefined && form.pid !== '' ? form.pid : 0,
                        subjectCode: form.subjectCode,
                        subjectLevel: form.subjectLevel,
                        subjectName: form.subjectName,
                        balanceDirection: form.balanceDirection,
                        subjectExplain: form.subjectExplain,
                        id: form.id !== undefined && form.id !== '' ? form.id : 0
                    }
                }
            } else if (str === 'same') {
                data = {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    parentId: this.modeId,
                    status: this.status,
                    faninceStatusId: this.faninceStatusId === '' ? 0 : this.faninceStatusId,
                    accountingSubjects: {
                        type: this.type,
                        pid: form.subjectLevel === 1 ? 0 : form.pid !== undefined && form.pid !== '' ? form.pid : 0,
                        subjectCode: form.subjectCode,
                        subjectLevel: form.subjectLevel,
                        subjectName: form.subjectName,
                        balanceDirection: form.balanceDirection,
                        subjectExplain: form.subjectExplain,
                        id: 0
                    }
                }
            } else if (str === 'lower') {
                data = {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    parentId: this.modeId,
                    status: this.status,
                    faninceStatusId: this.faninceStatusId === '' ? 0 : this.faninceStatusId,
                    accountingSubjects: {
                        type: this.type,
                        pid: form.subjectLevel === 1 ? 0 : form.id !== undefined && form.id !== '' ? form.id : 0,
                        subjectCode: form.subjectCode,
                        subjectLevel: form.subjectLevel,
                        subjectName: form.subjectName,
                        balanceDirection: form.balanceDirection,
                        subjectExplain: form.subjectExplain,
                        id: 0
                    }
                }
            }
            console.log('data', data)
            this.$api.post('/member-reversion/finance/saveAccountingSubjectsInfo', data).then(response => {
                console.log('response', response)
                if (response.code === 200) {
                    this.$Message.success('保存成功！')
                    this.init()
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        getSonList (arr) {
            arr.forEach(element => {
                this.data.push({
                    id: element.id,
                    subjectCode: element.subjectCode,
                    subjectLevel: parseInt(element.subjectLevel),
                    subjectName: element.subjectName,
                    balanceDirection: element.balanceDirection,
                    subjectExplain: element.subjectExplain
                })
                if(element.children && element.children.length > 0) {
                    this.getSonList(element.children)
                }
            })
        }
    }
}
</script>
