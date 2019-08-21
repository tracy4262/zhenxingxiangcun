<template>
  <div class="pd20">
    <ul class="template-list">
      <li>
        <div class="tc pt10" style="cursor: pointer;" @click="handleAdd">
          <Icon type="ios-add" size="80" color="#00C587" />
          <p class="t-green">新建模板</p>
        </div>
      </li>
      <li class="list" v-for="(item, index) in data" :key="index" :class="[index === active ? 'checked': '']" @click="handleChecked(item, index)">
        <span class="status" :data-status="Number(item.step) >= 5 ? '已完' : '未完'" :class="{finish: Number(item.step) >= 5}"></span>
        <div>
          <Tooltip placement="top" :transfer="true">
            <p style="color:#4b4b4b; font-size: 16px; font-weight: 700; max-width: 180px;" class="ell">{{item.templateName}}</p>
            <div slot="content" style="white-space: normal;">
              {{item.templateName}}
            </div>
          </Tooltip>
          <p class="t-green ell pt5 pb5">{{item.userType}}</p>
          <p  class="t-grey" >{{item. introduction}}</p>
        </div>
        <div class="del">
          <Button type="primary" size="small"  @click="handleEdit(item, index)">编辑</Button>
          <Button type="error" size="small"  @click="handleDel(item, index)">删除</Button>
        </div>
      </li>
    </ul>
     <div class="tc pd30">
        <Button type="primary" @click="handleNext">确定</Button>
      </div> 
    <Modal
        v-model="idAdd"
        :mask-closable="false"
        title="添加模板">
        <div class="pd20">
          <Form label-position="left" :label-width="80" ref="list" :rules="ruleInline" :model="list">
             <Form-item prop="userType" label="用户类型">
                <Select v-model="list.userType"  style="width:200px;">
                  <Option v-for="(item,index) in userTypes" :key="index" :value="item.label" >{{ item.label }}</Option>
                </Select>
              </Form-item>
              <Form-item prop="templateName" label="模板名称">
                <Input v-model="list.templateName" :maxlength="20" style="width:200px;" />
              </Form-item>
              <Form-item prop="introduction" label="模板介绍">
                <Input v-model="list.introduction" type="textarea"  :autosize="{minRows: 3,maxRows: 5}" :maxlength="50" />
              </Form-item>
          </Form>
        </div>
        <div slot="footer" class="tr">
            <Button  @click="handleAdd">取消</Button>
             <Button type="primary" @click="handleSave">确定</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        list:{
          introduction: '',
          templateName: '',
          userType: ''
        },
        userTypes: [],
        ruleInline: {
          introduction: [
            { required: true, message: '请输入模板介绍', trigger: 'blur' }
          ],
          templateName: [
              { required: true, message: '请输入模板名称', trigger: 'blur' }
          ],
          userType: [
            { required: true, message: '请输入用户类型', trigger: 'blur' }
          ],
        },
        data: [],
        active: -1,
        idAdd: false,
        saveSetpFlag: true
      }
    },
    created() {
      // 初始化查询
      this.init()
      // 用户类型查询
      this.getUserType()
    },
    methods: {
      // 用户类型查询
      getUserType () {
        this.$api.post('/member-reversion/manage/templateConfig/getUserType').then(response => {
          if (response.code === 200) {
            this.userTypes = response.data
          }
        })
      },
      init (data) {
        this.active = data
        this.$api.post('/member-reversion/manage/templateConfig/find', {account: this.$user.loginAccount}).then(response => {
          if (response.code === 200) {
            let data = response.data
            data.forEach((element, index) => {
              if (this.$template && this.$template.id == element.id) {
                this.handleChecked(element, index)
              }
            })
            this.data = data
          }
        })
      },
      handleChecked (item, index) {
        this.active = index
        // 点击每一个模板的时候 查询模板步骤 并存储 sessionStorage 的模板步骤 以及模板id
        this.findSetp(item)
      },
      // 查询模板 步骤
      findSetp (val, type) {
        this.$api.post('/member-reversion/realStep/findStep', {
          account: this.$user.loginAccount,
          templateId: val.id
        }).then(response => {
          if (response.code === 200) {
            sessionStorage.removeItem("stepData")
            let list = {templateId: ''}
            if (!response.data) {
              // 如果模板步骤没有保存过 （说明是新的模板，一步都没有走过）
              this.saveSetpFlag = true
              // 不能点击，切换
              this.$parent.$parent.$parent.isIdentityVerification = 0
            } else {
              // 说明保存过
              list = response.data
              this.$parent.$parent.$parent.isIdentityVerification = Number(response.data.step)
              this.saveSetpFlag = false
              if (type) { // 说明是点击下一步的时候调用的
                window.location.href = `${window.location.origin}/user-auth-admin/auth/step2`
              }
            }
            sessionStorage.setItem('stepData', JSON.stringify(list))
            sessionStorage.removeItem("templateData")
            sessionStorage.setItem('templateData', JSON.stringify(val))
          }
        })
      },
      // 保存模板 步骤
      saveStep (val) {
        this.$api.post('/member-reversion/realStep/save', {
          loginStep:{
              id: 0,
              account: this.$user.loginAccount,
              templateId: val.id,
              step: 1
            }
        }).then(response => {
          if (response.code === 200) {
            this.$Message.success('保存成功')
            this.findSetp(val, 'type')
          }
        })
      },
      // 点击添加
      handleAdd () {
        this.list = {
          introduction: '',
          templateName: '',
          userType: ''
        }
        this.idAdd = !this.idAdd
      },
      // 编辑
      handleEdit (item, index) {
        let obj = Object.assign({}, item)
        this.list = obj
        this.idAdd = !this.idAdd
      },
      // 点击删除模板
      handleDel (item, index) {
         this.$Modal.confirm({
                title: '是否确定删除',
                content: '是否确认删除？',
                onOk: () => {
                  this.$api.post('/member-reversion/manage/templateConfig/delete', {id: item.id}).then(response => {
                      if (response.code === 200) {
                        this.$Message.success('删除成功')
                        this.data.splice(index, 1)
                        // 如果删除的模板是 刚选择的模板 name就先移除 sessionStorage 不能点击切换
                        if (this.active == index) {
                          this.active = -1
                          sessionStorage.removeItem("stepData")
                          sessionStorage.removeItem("templateData")
                          this.$parent.$parent.$parent.isIdentityVerification = 0
                        }
                      }
                    })
                },
                okText: '确定',
                cancelText: '取消'
            })
      },
      // 点击确定保存模板
      handleSave () {
        this.$refs['list'].validate((valid) => {
          if (valid) {
            console.log(this.list)
            this.list.account = this.$user.loginAccount
            let url = this.list.id ? `/member-reversion/manage/templateConfig/update` : `/member-reversion/manage/templateConfig/save`
            this.$api.post(url, this.list).then(response => {
              if (response.code === 300) {
                this.$Message.error('模板名称已存在')
              } else if (response.code === 200) {
                this.data.push(this.list)
                this.$Message.success('保存成功')
                this.handleAdd()
                this.init()
              } else {
                this.$Message.error('保存失败')
              }
            })
           
          } else {
            this.$Message.error('请核对表单信息')
          }
        })
      },
      // 1、刚进入 选择模板
      // 下一步
      handleNext () {
        if (this.active >= 0) {
          let list = this.data[this.active]
          this.saveStep(list)
        } else {
          this.$Message.error('请选择模板')
        }
      }
    }
  }
</script>
<style lang="scss" scoped>
.template-list{
  li{
    vertical-align: top;
    height: 178px;
    position: relative;
    background: #fff;
    margin: 15px 15px 0 0;
    display: inline-block;
    width: calc(100% / 5 - 15px);
    list-style: none;
    padding: 23px 16px;
    border: 1px solid rgba(237,237,237,0.62);
    transition: box-shadow .2s cubic-bezier(.47,0,.745,.715);
    &:nth-child(5n){
      margin-right: 0;
    }
  }
  .list {
    position: relative;
    overflow: hidden;
    &:hover{
      box-shadow: 0 0 0 2px #00c587;
      .del{
        bottom: 0;
        transition: bottom .3s
      }
    }
    .del{
      position: absolute;
      text-align: center;
      padding: 5px 0;
      bottom: -100px;
      left: 0;
      right: 0;
    }
    .status{
      right: 0;
      &,
      &:after, 
      &:before{
        position: absolute;
        top: 0;
      }
      &:after{
        right: 0;
        border-style: solid;
        border-width: 0 46px 46px 0;
        border-color: transparent #fff2ef transparent transparent;
        content: '';
      }
      &:before{
        content: attr(data-status);
        transform: rotate(45deg);
        right: 5px;
        top: 7px;
        z-index: 99;
        font-size: 12px;
        color: #ed4014;
        white-space: nowrap;
      }
    }
    .finish{
      &:after{
        border-color: transparent #e2fff1 transparent transparent;
      }
      &:before{
        color: #19be6b;
      }
    }
  }
  .checked {box-shadow: 0 0 0 2px #00c587;}
}
</style>
