<template>
  <div>
     <Modal
        v-model="isShow"
        :mask-closable="false"
        width="600px"
        title="添加成员">
          <div class="tc">
                <p>为您所要添加的员工新建一个账号或使用已有账号，添加成功后你们将会建立好友关系</p>
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
                      <Input v-model="registerInfo.group" placeholder="请选择所在分组" :maxlength="18" style="width: 300px;" readonly @on-focus="handleGroupList"/>
                    </Form-item>
                </Form>
            </div>
            <div v-else>
                <Form ref="loginInfo" :model="loginInfo" label-position="right" :label-width="150" :rules="ruleInline2" class="mt20">
                    <Form-item prop="loginAccount" label="账号">
                        <Input v-model="loginInfo.loginAccount" :maxlength="16" style="width: 300px;" />
                    </Form-item>
                    <Form-item prop="password" label="登录密码">
                        <Input v-model="loginInfo.password" :maxlength="18" style="width: 300px;" type="password" />
                    </Form-item>
                    <Form-item prop="group" label="选择分组">
                      <Input v-model="loginInfo.group"  :maxlength="18" placeholder="请选择所在分组" style="width: 300px;" readonly @on-focus="handleGroupList"/>
                    </Form-item>
                </Form>
            </div>
            <div class="tc">
                <span class="tips mr10">小提示：成员账号登录初始密码为身份证后6位</span>
                <Button v-if="isRegister" @click="haveAccount" class="account-btn" style="font-weight: bold;">已有账号</Button>
                <Button v-else @click="newAccount" class="account-btn" style="font-weight: bold;">新建帐号</Button>
            </div>
            <div slot="footer">
                <Button type="text" @click="isShow = false">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
            </div>
        <div slot="footer" class="tc">
        </div>
    </Modal>
    <groupList @on-save="onSave" ref="groupList"></groupList>
  </div>
</template>
<script>
import groupList from './groupList'
import { isIdCard, isUserName } from '~utils/validate'
export default {
  components: {
    groupList
  },
  data () {
    return {
        isShow: false,
        isRegister: true,
        loginInfo: {
            loginAccount: '',
            password: '',
            group: ''
        },
        registerInfo: {
            group: '',
            account: '',
            idCard: ''
        },
        ruleInline: {
            account: [
                { required: true, validator: isUserName, tiger: 'blur' }
            ],
            idCard: [
                { required: true, validator: isIdCard, tiger: 'blur' }
            ],
            group: [
                { required: true, message: '请选择分组', tiger: 'change' }
            ],
        },
        ruleInline2: {
            loginAccount: [
                { required: true, validator: isUserName, tiger: 'blur' }
            ],
            password: [
                { required: true, message: '请填写登录密码', tiger: 'blur' }
            ],
            group: [
                { required: true, message: '请选择分组', tiger: 'change' }
            ],
        },
    }
  },
  created() {
  },
  methods: {
    init () {
      this.isShow = true
      if (this.isRegister) {
        this.$refs['registerInfo'].resetFields()
       } else {
        this.$refs['loginInfo'].resetFields()
       }
    },
    haveAccount () {
        this.loginInfo = {
            loginAccount: '',
            password: '',
            group: ''
        }
        this.isRegister = false
        // this.$nextTick(() => {
        //     this.$refs['loginInfo'].resetFields()
        // })
    },
    newAccount () {
        this.registerInfo = {
            group: '',
            account: '',
            idCard: ''
        }
        this.isRegister = true
        // this.$nextTick(() => {
        //     this.$refs['registerInfo'].resetFields()
        // })
    },
    ok () {
        // type 0 新建账号 1 已有账号 300 站好已存在 301账号密码错误 302
        if (this.isRegister) {
            let data = {
                account: this.$user.loginAccount,
                idCard: this.registerInfo.idCard,
                groupId: this.registerInfo.groupId,
                friendAccount: this.registerInfo.account,
                type: 0
            }
            this.$refs['registerInfo'].validate((valid) => {
                if (valid) {
                    this.$api.post('/member/staffGateway/insertNewStaff', data).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('操作成功！')
                            this.isShow = false
                            this.$emit('on-ok')
                        } else if (response.code === 500) {
                            this.$Message.error('操作失败！')
                        } else {
                            this.$Message.error(response.msg)
                        }
                    })
                } else {
                    this.$Message.error('请核对表单信息！')
                }
            })
        } else {
            let data = {
                account: this.$user.loginAccount,
                password: this.loginInfo.password,
                groupId: this.loginInfo.groupId,
                friendAccount: this.loginInfo.loginAccount,
                type: 1
            }
            if (this.$user.loginAccount === this.loginInfo.loginAccount) {
                this.$Message.warning('不能添加自己！')
                return 
            }
            this.$refs['loginInfo'].validate((valid) => {
                if (valid) {
                    this.$api.post('/member/staffGateway/insertNewStaff', data).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('操作成功！')
                            this.isShow = false
                            this.$emit('on-ok')
                        } else if (response.code === 500) {
                            this.$Message.error('操作失败！')
                        } else {
                            this.$Message.error(response.msg)
                        }
                    })
                } else {
                    this.$Message.error('请核对表单信息！')
                }
            })
        }
    },
    handleGroupList () {
      this.$refs['groupList'].init()
    },
    onSave (group) {
      if (this.isRegister) {
          console.log('xinjianzhanghao')
        this.registerInfo.group = group.groupName
        this.registerInfo.groupId = group.id
          console.log(this.registerInfo)
      } else {
          console.log('已有账号')
        this.loginInfo.group = group.groupName
        this.loginInfo.groupId = group.id
          console.log(this.loginInfo)
      }
    this.$refs['groupList'].isShow = false
      console.log(group)
    }
  },
  
}
</script>


