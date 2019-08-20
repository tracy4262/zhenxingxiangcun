<template>
    <div>
        <app-banner
          src="../../../static/img/app-banner-farm-family.png"
          title="种养户管理">
        </app-banner>

        <div class="pt10 pb10 mt20">
            <div class="user-auth-title">
                <Input v-if="farmFamilyEdit" v-model="title" @on-blur="saveFarmFamilyName" style="width: 300px;" ref="farmFamilyName" />
                <span v-else>{{title}}</span>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <Icon type="edit" @click.native="editFarmFamilyName"></Icon>
            </div>
        </div>
        <Row class="mt20 pl10">
            <!-- 左侧添加分组 -->
            <Col span="3">
                <Row><Button type="default" @click="addGroup" class="add-btn">+添加分组</Button></Row>
                <Row v-for="(item,index) in groupList" :key="index" class="mt20">
                    <span @click="chooseGroup(item, index)" :class="{'farm-group-btn-active': index === active, 'farm-group-btn': true}">{{ item.group_name }}</span>
                </Row>
            </Col>
            <!-- 右侧列表 -->
            <Col span="21">
                <Row>
                    <Col span="12">
                        <Input v-if="groupNameEdit" v-model="groupName" @on-blur="saveGroupName" style="width: 300px;" ref="groupName" />
                        <span v-else>{{ groupName }}</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <Icon type="edit" v-if="groupName !== '全部种养户'" @click.native="editGroupName"></Icon>
                    </Col>
                    <Col span="12" class="tr">
                        <span>总户数：{{ totalHouseholds }}户</span>&nbsp;&nbsp;&nbsp;&nbsp;
                        <span>总人数：{{ totalPeople }}人</span>
                        <span v-if="groupName === '全部种养户'">&nbsp;&nbsp;&nbsp;&nbsp;组数：{{ groupList.length - 1 }}组</span>
                    </Col>
                </Row>
                <Row class="mt20">
                    <Col span="6">姓名<Input v-model="queryName" @on-change="queryByName" style="width: 150px;" class="ml10" /></Col>
                    <Col span="6" v-if="groupName === '全部种养户'">
                        组别
                        <Select v-model="queryGroupId" @on-change="queryById" style="width: 150px; margin-left: 10px;">
                            <Option v-for="(item,index) in groupList" :key="index" :value="item.group_id">{{ item.group_name }}</Option>
                        </Select>
                    </Col>
                    <!-- <Col span="7">门牌号<Input style="width: 150px;" class="ml10" /></Col> -->
                    <Col :span="groupName === '全部种养户' ? '12' : '18'" class="tr"><Button type="default" @click="addPeople" class="add-btn">+添加成员</Button></Col>
                </Row>
                <Row class="mt20">
                    <Table border :columns="columns" :data="data"></Table>
                </Row>
                <Row class="mt20 mb20">
                    <Page :total="total" :current="currentPage" @on-change="handleGetNextPage" class="tr"></Page>
                </Row>
            </Col>
        </Row>
        <!-- 添加种养户弹出框 -->
        <Modal v-model="modal" width="560" title="添加种养户" :mask-closable="false">
            <div class="tc">
                <p>为您所要添加的种养户新建一个账号或使用已有账号，添加成功后你们将会建立好友关系</p>
            </div>
            <div v-if="isRegister">
                <Form v-if="isRegister" ref="registerInfo" :model="registerInfo" label-position="right" :label-width="150" :rules="ruleInline" class="mt20">
                    <Form-item prop="account" label="新账号">
                        <Input v-model="registerInfo.account" :maxlength="16" style="width: 300px;" />
                    </Form-item>
                    <Form-item prop="idCard" label="身份证号">
                        <Input v-model="registerInfo.idCard" :maxlength="18" style="width: 300px;" />
                    </Form-item>
                    <Form-item prop="group" label="选择分组">
                        <Select v-model="registerInfo.group" style="width: 300px;" :placeholder="groupList.length === 1 ? '暂无种养户分组，请先新增种养户分组' : '请选择种养户分组'">
                            <Option v-for="(item,index) in groupList.slice(1)" :value="item.group_id" :key="index">{{ item.group_name }}</Option>
                        </Select>
                    </Form-item>
                </Form>
            </div>
            <div v-else>
                <Form ref="loginInfo" :model="loginInfo" label-position="right" :label-width="150" :rules="ruleInline2" class="mt20">
                    <Form-item prop="loginAccount" label="种养户账号">
                        <Input v-model="loginInfo.loginAccount" :maxlength="16" style="width: 300px;" />
                    </Form-item>
                    <Form-item prop="password" label="种养户登录密码">
                        <Input v-model="loginInfo.password" :maxlength="18" style="width: 300px;" type="password" />
                    </Form-item>
                    <Form-item prop="farmGroup" label="种养户所属分组">
                        <Select v-model="loginInfo.farmGroup" style="width: 300px;" :placeholder="groupList.length === 1 ? '暂无种养户分组，请先新增种养户分组' : '请选择种养户分组'">
                            <Option v-for="(item,index) in groupList.slice(1)" :value="item.group_id" :key="index">{{ item.group_name }}</Option>
                        </Select>
                    </Form-item>
                </Form>
            </div>
            <span class="tips">小提示：种养户账号登录初始密码为身份证后6位</span>
            <Button v-if="isRegister" @click="haveAccount" class="account-btn" style="font-weight: bold;">已有账号</Button>
            <Button v-else @click="newAccount" class="account-btn" style="font-weight: bold;">新建帐号</Button>
            <div slot="footer">
                <Button type="text" @click="cancel">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
            </div>
        </Modal>
        <!-- 添加分组弹出框 -->
        <Modal v-model="modal2" width="560" title=" 添加分组" :mask-closable="false">
            <Form ref="groupInfo" :model="groupInfo" label-position="right" :label-width="150" :rules="ruleInline3" class="mt20">
                <Form-item prop="name" label="分组名称">
                    <Input v-model="groupInfo.name" :maxlength="10" style="width: 300px;" />
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancel2">取消</Button>
                <Button type="primary" @click="ok2">确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
    import { isIdCard, isUserName } from '~utils/validate'
    import appBanner from '~components/app-banner'
    export default {
        name: 'farmFamilyList',
        components: {
          appBanner
        },
        data () {
            return {
                modal: false,
                modal2: false,
                isRegister: true,
                farmFamilyEdit: false,
                groupNameEdit: false,
                active: 0,
                queryName: '',
                queryGroupId: -1,
                queryGroupLeftId: -1,
                id: -1,
                loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                registerInfo: {
                    account: '',
                    idCard: '',
                    group: ''
                },
                ruleInline: {
                    account: [
                        { required: true, validator: isUserName, tiger: 'blur' }
                    ],
                    idCard: [
                        { required: true, validator: isIdCard, tiger: 'blur' }
                    ],
                    group: [
                        { required: true, type: 'number', message: '请选择分组', tiger: 'change' }
                    ],
                },
                loginInfo: {
                    loginAccount: '',
                    password: '',
                    farmGroup: ''
                },
                ruleInline2: {
                    loginAccount: [
                        { required: true, validator: isUserName, tiger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请填写种养户登录密码', tiger: 'blur' }
                    ],
                    farmGroup: [
                        { required: true, type: 'number', message: '请选择种养户所属分组', tiger: 'change' }
                    ],
                },
                groupInfo: {
                    name: ''
                },
                ruleInline3: {
                    name: [
                        { required: true, message: '请填写分组名称', tiger: blur }
                    ]
                },
                title: '',
                groupName: '全部种养户',
                totalHouseholds: 0,
                totalPeople: 0,
                total: 0,
                currentPage: 1,
                pageNum: 1,
                pageSize: 10,
                groupList: [
                    {
                        group_id: -1,
                        group_name: '全部种养户'
                    }
                ],
                columns: [
                    {
                        title: '组别',
                        key: 'groupName',
                        width: 150,
                        fixed: 'left',
                        align: 'center'
                    },
                    {
                        title: '种养户帐号',
                        key: 'farmAccount',
                        width: 150,
                        align: 'center'
                    },
                    {
                        title: '真实姓名',
                        key: 'realName',
                        width: 100,
                        align: 'center'
                    },
                    {
                        title: '性别',
                        key: 'gender',
                        width: 100,
                        align: 'center'
                    },
                    {
                        title: '身份证',
                        key: 'idCard',
                        width: 200,
                        align: 'center'
                    },
                    {
                        title: '手机号',
                        key: 'mobile',
                        width: 150,
                        align: 'center'
                    },
                    {
                        title: '所在地',
                        key: 'location',
                        width: 200,
                        align: 'center'
                    },
                    /* {
                        title: '门牌号',
                        key: 'houseNumber',
                        width: 100,
                        align: 'center'
                    }, */
                    {
                        title: '操作',
                        key: 'action',
                        fixed: 'right',
                        width: 250,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        color: '#FF7921'
                                    },
                                    on: {
                                        click: () => {
                                            this.goToMemberCenter(params.row.farmAccount)
                                        }
                                    }
                                }, '会员中心'),
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        color: '#FF7921'
                                    },
                                    on: {
                                        click: () => {
                                            this.goToPortal(params.row.farmAccount)
                                        }
                                    }
                                }, '门户网站'),
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        color: '#FF7921'
                                    },
                                    on: {
                                        click: () => {
                                            this.delete(params.row.id)
                                        }
                                    }
                                }, '删除'),
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        color: '#FF7921'
                                    },
                                    on: {
                                        click: () => {
                                            this.goToDetail(params.row.id)
                                        }
                                    }
                                }, '修改')
                            ]);
                        }
                    }
                ],
                data: []
            }
        },
        created () {
            this.initTitleName()
            this.initGroup()
            // 初始化加载第一页数据
            this.initTable({
                login_account: this.loginUser.loginAccount,
                display_name: '',
                group_id: '',
                pageNum: 1,
                pageSize: 10
            })
            // 初始化总人数
            this.initCount()
        },
        methods: {
            // 一进页面就初始化种养合作社的名称
            initTitleName () {
                this.$api.post('/member/farmFamily/insertTitle', {
                    title_name: '种养合作社',
                    login_account: this.loginUser.loginAccount
                }).then(response => {
                    console.log('resName2', response)
                    if (response.code === 200 || response.code === 300) {
                        // 默认新增成功
                        this.queryTitleName()
                    }
                })
            },
            queryTitleName () {
                this.$api.post('/member/farmFamily/findTitle', {
                    login_account: this.loginUser.loginAccount
                }).then(response => {
                    console.log('resName', response)
                    if (response.code === 200) {
                        if (response.data.length !== 0) {
                            this.title = response.data[0].title_name
                            this.id = response.data[0].title_id
                        }
                    }
                }).catch(error => {
                    this.$Message.error('查询种养合作社名称错误！')
                })
            },
            goToPortal(farmAccount){
                this.goInTo(farmAccount)                
            },
            // 判断用户类型进入门户
            toPortal (farmAccount) {
                this.$api.post('/member/login/findCurrentUser', {
                        account: farmAccount
                    })
                    .then(response => {
                        if (response.code === 200) {
                            if (response.data.userType !== undefined) {
                                let userType = response.data.userType
                                // 0个人用户 1企业 2种养户 3机关 4专家 5乡村
                                if (userType === 1) {
                                    // 企业
                                    window.location.href=`${window.location.origin}/companyGate/index?uid=${farmAccount}`
                                } else if (userType == 2) {
                                    // 种养户也去个人门户
                                    window.location.href=`${window.location.origin}/personGate/index?uid=${farmAccount}`
                                } else if (userType === 3) {
                                    // 机关
                                    window.location.href=`${window.location.origin}/govGate/index?uid=${farmAccount}`
                                } else if (userType === 4) {
                                    // 专家
                                    window.location.href=`${window.location.origin}/expertGate/index?uid=${farmAccount}`
                                } else if (userType === 5) {
                                    // 乡村
                                    window.location.href=`${window.location.origin}/ruralGate/index?uid=${farmAccount}`
                                } else {
                                    // 个人
                                    window.location.href=`${window.location.origin}/personGate/index?uid=${farmAccount}`
                                }
                            }                           
                        }
                    })
            },
            // 移除当前用户数据，自动登录现点击用户账户，并调用判断用户类型接口，进入该用户门户
            goInTo (account) {
				// 先移除原有账户的session
				sessionStorage.removeItem('user')
				// console.log(sha1.SHA2('ccdtgf9qsessionSALT'))
				// TODO 通过account查询带被代理机构的帐号密码 直接进行登录（登录前进行提示）
				this.$api.get('/member/login/findbyname/' + account).then(response => {
					if (response.code === 200) {
						// 登录改为加密传输 传输过程中密码进行sha1加密 后台拿到密文直接进行比对（此步骤获取的密码已经为密文密码）
						this.$api.post('/member/login/login', {
							username: response.data.loginAccount,
							pwd: response.data.loginPasswd
						}).then(response => {
							if (response.data) {
								sessionStorage.setItem("user", JSON.stringify(response.data))
                                this.toPortal(account)
							} else {
								this.$Message.error('代理登录失败!')
							}
						})
					}
				}).catch(error => {
					this.$Message.error('代理登录失败！')
				})
			},
            initCount () {
                this.$api.post('/member/farmFamily/findCountFarm', {
                    login_account: this.loginUser.loginAccount,
                    group_id: this.queryGroupLeftId === -1 ? this.queryGroupId === -1 ? '' : this.queryGroupId : this.queryGroupLeftId
                }).then(response => {
                    console.log(response)
                    if (response.code === 200) {
                        this.totalPeople = response.data
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            //修改
            goToDetail(id){
                 this.$router.push({ path: '/family/deatil',query:{id:id}})
            },
            initGroup () {
                this.$api.post('/member/farmFamily/findGroup', {
                    login_account: this.loginUser.loginAccount
                }).then(response => {
                    console.log(response)
                    if (response.code === 200) {
                        response.data.forEach(element => {
                            this.groupList.push(element)
                        })
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            initTable (data) {
                this.$api.post('/member/farmFamily/findFarmFamilyList', data).then(response => {
                    console.log(response)
                    if (response.code === 200) {
                        response.data.list.forEach(element => {
                            this.data.push({
                                id: element.farm_family_id,
                                groupName: element.groupName,
                                farmAccount: element.farm_account,
                                realName: element.realName === undefined ? '未实名' : element.realName,
                                gender: element.gender === 'null' ? '' : element.gender,
                                idCard: element.idCard,
                                mobile: element.mobile,
                                location: element.location
                            })
                        })
                        this.total = response.data.total
                        this.totalHouseholds = response.data.total
                        this.initCount()
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            addGroup () {
                this.$refs['groupInfo'].resetFields()
                this.modal2 = true
            },
            chooseGroup (item, index) {
                this.groupName = item.group_name
                this.queryGroupLeftId = item.group_id
                this.active = index
                this.data = []
                this.queryName = ''
                this.queryGroupId = -1
                this.currentPage = 1
                this.initCount()
                this.initTable({
                    login_account: this.loginUser.loginAccount,
                    display_name: '',
                    group_id: item.group_id === -1 ? '' : item.group_id,
                    pageNum: 1,
                    pageSize: 10
                })
            },
            queryByName () {
                this.data = []
                this.currentPage = 1
                this.initTable({
                    login_account: this.loginUser.loginAccount,
                    display_name: this.queryName,
                    group_id: this.queryGroupLeftId === -1 ? this.queryGroupId === -1 ? '' : this.queryGroupId : this.queryGroupLeftId,
                    pageNum: 1,
                    pageSize: 10
                })
            },
            queryById () {
                this.data = []
                this.currentPage = 1
                this.initCount()
                this.initTable({
                    login_account: this.loginUser.loginAccount,
                    display_name: this.queryName,
                    group_id: this.queryGroupLeftId === -1 ? this.queryGroupId === -1 ? '' : this.queryGroupId : this.queryGroupLeftId,
                    pageNum: 1,
                    pageSize: 10
                })
            },
            addPeople () {
                this.isRegister = true
                this.$nextTick(() => {
                    this.$refs['registerInfo'].resetFields()
                })
                this.modal = true
            },
            handleGetNextPage (page) {
                this.currentPage = page
                this.data = []
                this.initTable({
                    login_account: this.loginUser.loginAccount,
                    display_name: this.queryName,
                    group_id: this.queryGroupLeftId === -1 ? this.queryGroupId === -1 ? '' : this.queryGroupId : this.queryGroupLeftId,
                    pageNum: page,
                    pageSize: 10
                })
            },
            cancel () {
                this.modal = false
            },
            ok () {
                if (this.isRegister) {
                    this.$refs['registerInfo'].validate((valid) => {
                        if (valid) {
                            let data = {
                                // 0表示是新账号添加种养户
                                farm_type: 0,
                                farm_account: this.registerInfo.account,
                                idCard: this.registerInfo.idCard,
                                group_id: this.registerInfo.group,
                                login_account: this.loginUser.loginAccount
                            }
                            console.log('data', data)
                            this.$api.post('/member/farmFamily/insertFarm', data).then(response => {
                                console.log('res', response)
                                if (response.code === 200) {
                                    this.modal = false
                                    this.$Message.success('添加成功！')
                                    // 重新加载表格数据
                                    this.data = []
                                    this.queryName = ''
                                    this.queryGroupLeftId = -1
                                    this.queryGroupId = -1
                                    this.active = 0
                                    this.groupName = '全部种养户'
                                    this.initTable({
                                        login_account: this.loginUser.loginAccount,
                                        display_name: '',
                                        group_id: '',
                                        pageNum: 1,
                                        pageSize: 10
                                    })
                                    this.initCount()
                                } else if (response.code === 300) {
                                    this.$Message.error('该帐号已存在，请重新填写新帐号！')
                                }
                            }).catch(error => {
                                console.log(error)
                            })
                        }
                    })
                } else {
                    this.$refs['loginInfo'].validate((valid) => {
                        if (valid) {
                            let data = {
                                // 1表示是已有账号添加种养户
                                farm_type: 1,
                                farm_account: this.loginInfo.loginAccount,
                                password: this.loginInfo.password,
                                group_id: this.loginInfo.farmGroup,
                                login_account: this.loginUser.loginAccount
                            }
                            console.log('data', data)
                            this.$api.post('/member/farmFamily/insertFarm', data).then(response => {
                                console.log('res', response)
                                if (response.code === 200) {
                                    this.modal = false
                                    this.$Message.success('添加成功！')
                                    // 重新加载表格数据
                                    this.queryName = ''
                                    this.queryGroupId = -1
                                    this.queryGroupLeftId = -1
                                    this.active = 0
                                    this.groupName = '全部种养户'
                                    this.data = []
                                    this.initTable({
                                        login_account: this.loginUser.loginAccount,
                                        display_name: '',
                                        group_id: '',
                                        pageNum: 1,
                                        pageSize: 10
                                    })
                                } else if (response.code === 302) {
                                    this.$Message.error('此帐号已经是您的种养户，请勿重复添加！')
                                } else if (response.code === 301) {
                                    this.$Message.error('此种养户帐号或密码错误，请核对！')
                                }
                            }).catch(error => {
                                console.log(error)
                            })
                        }
                    })
                }
            },
            cancel2 () {
                this.modal2 = false
            },
            ok2 () {
                this.$refs['groupInfo'].validate((valid) => {
                    if (valid) {
                        if (this.groupInfo.name === '全部种养户') {
                            this.$Message.error('该分组名称已存在，请勿重复添加！')
                            return
                        }
                        this.$api.post('/member/farmFamily/insertGroup', {
                            group_name: this.groupInfo.name,
                            login_account: this.loginUser.loginAccount
                        }).then(response => {
                            console.log('res', response)
                            if (response.code === 200) {
                                this.modal2 = false
                                this.$Message.success('添加成功！')
                                // 清空分组 重新加载
                                let element = this.groupList[0]
                                this.groupList = []
                                this.groupList.push(element)
                                this.initGroup()
                            } else if (response.code === 300) {
                                this.$Message.error('该分组名称已存在，请勿重复添加！')
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    }
                })
            },
            editFarmFamilyName () {
                this.farmFamilyEdit = true
                this.$nextTick(() => {
                    this.$refs.farmFamilyName.focus()
                })
            },
            saveFarmFamilyName () {
                this.$api.post('/member/farmFamily/updateTitle', {
                    title_name: this.title,
                    title_id: this.id
                }).then(response => {
                    console.log('resasa', response)
                    if (response.code === 200) {
                        this.$Message.success('修改种养户名称成功！')
                        this.farmFamilyEdit = false
                    }
                }).catch(error => {
                    this.$Message.error('修改种养户名称失败！')
                })
            },
            saveGroupName () {
                if (this.groupName.replace(/(^\s*)|(\s*$)/g,'') === '') {
                    this.$Message.error('分组名称不能为空！')
                    return
                }
                let data = {
                    group_id: this.queryGroupLeftId,
                    group_name: this.groupName
                }
                console.log('data', data)
                this.$api.post('/member/farmFamily/updateGroup', data).then(response => {
                    console.log(response)
                    if (response.code === 200) {
                        this.$Message.success('分组名称修改成功！')
                        // 初始化列表
                        this.data = []
                        this.initTable({
                            login_account: this.loginUser.loginAccount,
                            display_name: '',
                            group_id: this.queryGroupLeftId === -1 ? this.queryGroupId === -1 ? '' : this.queryGroupId : this.queryGroupLeftId,
                            pageNum: 1,
                            pageSize: 10
                        })
                        // 初始化分组 清空分组 重新加载
                        let element = this.groupList[0]
                        this.groupList = []
                        this.groupList.push(element)
                        this.initGroup()
                        this.groupNameEdit = false
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            editGroupName () {
                this.groupNameEdit = true
                this.$nextTick(() => {
                    this.$refs.groupName.focus()
                })
            },
            haveAccount () {
                this.isRegister = false
                this.$nextTick(() => {
                    this.$refs['loginInfo'].resetFields()
                })
            },
            newAccount () {
                this.isRegister = true
                this.$nextTick(() => {
                    this.$refs['registerInfo'].resetFields()
                })
            },
            // 进入该种养户的会员中心
            goToMemberCenter (account) {
				// 先移除原有账户的session
				sessionStorage.removeItem('user')
				// console.log(sha1.SHA2('ccdtgf9qsessionSALT'))
				// TODO 通过account查询带被代理机构的帐号密码 直接进行登录（登录前进行提示）
				this.$api.get('/member/login/findbyname/' + account).then(response => {
					if (response.code === 200) {
						// 登录改为加密传输 传输过程中密码进行sha1加密 后台拿到密文直接进行比对（此步骤获取的密码已经为密文密码）
						this.$api.post('/member/login/login', {
							username: response.data.loginAccount,
							pwd: response.data.loginPasswd
						}).then(response => {
							if (response.data) {
								sessionStorage.setItem("user", JSON.stringify(response.data))
								this.$Message.success('种养户登录成功!')
								window.location.href=`${window.location.origin}/pro/member`
							} else {
								this.$Message.error('种养户登录失败!')
							}
						})
					}
				}).catch(error => {
					this.$Message.error('代理登录失败！')
				})
            },
            // 删除种养户
            delete (id) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确认删除该种养户？',
                    onOk: () => {
                        this.$api.post('/member/farmFamily/deleteFarmFamily', { farm_family_id: id }).then(response => {
                            console.log(response)
                            if (response.code === 200) {
                                this.$Message.success('删除成功！')
                                // 重新加载表格数据
                                this.queryName = ''
                                this.queryGroupId = -1
                                this.queryGroupLeftId = -1
                                this.active = 0
                                this.groupName = '全部种养户'
                                this.data = []
                                this.initTable({
                                    login_account: this.loginUser.loginAccount,
                                    display_name: '',
                                    group_id: '',
                                    pageNum: 1,
                                    pageSize: 10
                                })
                            } else {
                                this.$Message.error('删除失败！')
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    }
                })
            }
        }
    }
</script>
<style scoped>
    .tips {
        color: #FF7921;
        margin-left: 120px;
    }
    .account-btn {
        color: #00c587;
        margin-left: 20px;
        cursor: pointer;
    }
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
    .add-btn {
        color: #00c587;
        border-color: #00c587;
    }
</style>
