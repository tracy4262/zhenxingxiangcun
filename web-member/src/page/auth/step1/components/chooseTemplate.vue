<template>
  <div>
    <Card class="layout">
      <div class="pd20">
        <Title title="选择模板"></Title>
        <Row class="mt20">
            <Col v-for="(item,index) in userTypeList" :key="index" class="mt20" span="3">
                <Button :type="item.type" @click="handleClick(item)">{{ item.userType }}</Button>
            </Col>
        </Row>
        <Row class="mt20">
            <!-- <div v-for="(item,index) in templateList" :key="index" :class="{'template-item': true, 'checked': item.checked}" @click="handleClickItem(item)"> -->
            <div v-for="(item,index) in templateList"  :class="{'template-item': true,}" :key="index" @click="handleClickItem(item)">
                <span class="status finish" v-if="item.checked"></span>
                <div style="font-size: 16px;">{{ item.templateName }}</div>
                <div class="mt10" style="font-size: 12px; color: #00C587;">{{ item.userType }}</div>
                <div class="mt10 ell-5" style="font-size: 12px;" :title="item.introduction">{{ item.introduction }}</div>
            </div>
        </Row>
        <div class="tc pd20">
          <Button type="primary" @click="handleClickNext">保存并下一步</Button>
        </div>
      </div>
    </Card>
  </div>
</template>
<script>
import Title from '../../components/title'
export default {
  components: {
    Title
  },
  data: () => ({
    userTypeList: [
      {
        value: -1,
        userType: '全部',
        type: 'primary'
      }
    ],
    templateListAll: [],
    templateList: [],
    id: '',
    stepId: ''
  }),
  created () {
    this.initUserType()
    this.initTemplate()
  },
  methods: {
    initUserType () {
        this.$api.post('/member-reversion/manage/templateConfig/getUserType', {
            account: this.$user.loginAccount
        }).then(response => {
            console.log('response', response)
            if (response.code === 200) {
                response.data.forEach(element => {
                    this.userTypeList.push({
                        value: element.value,
                        userType: element.label,
                        type: 'text'
                    })
                })
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    initTemplate () {
        this.$api.post('/member-reversion/user/userTemplateManage/find', {
            account: this.$user.loginAccount
        }).then(response => {
            console.log('response', response)
            if (response.code === 200) {
                this.templateListAll = response.data.templateConfig
                this.templateList = this.templateListAll
                if (response.data.userTemplate) {
                    this.id = response.data.userTemplate.id
                    this.findSetp(response.data.userTemplate.templateId)
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    // 查询模板 步骤
    findSetp (val, reload) {
        this.$api.post('/member-reversion/realStep/findStep', {
            account: this.$user.loginAccount,
            templateId: val
        }).then(response => {
            if (response.code === 200) {
                // reload 存在， 说明是点击模板后查询的 如果 response.data 存在 那么则需要修改url
                if (response.data) {
                    this.$parent.$parent.$parent.isIdentityVerification = Number(response.data.step)
                    this.$parent.$parent.$parent.id = response.data.templateId
                    this.stepId = response.data.id
                } else {
                    this.stepId = ''
                    this.$parent.$parent.$parent.isIdentityVerification = 0
                }
            }
        })
    },
    handleClick (item) {
      this.userTypeList.forEach(element => {
        if (element.userType === item.userType) {
            element.type = 'primary'
        } else {
            element.type = 'text'
        }
      })
      this.templateList = this.templateListAll.filter(element => {
        if (item.userType === '全部') {
            return element
        } else {
            return element.userType === item.userType
        }
      })
    },
    handleClickItem (item) {
        this.templateList.forEach(element => {
            if (item.templateName === element.templateName) {
                element.checked = true
            } else {
                element.checked = false
            }
        })
        this.templateListAll.forEach(element => {
            if (item.templateName === element.templateName) {
                element.checked = true
            } else {
                element.checked = false
            }
        })
        this.findSetp(item.id, 'reload')
    },
    // 下一步
    handleClickNext () {
        let arr = this.templateList.filter(element => {
            return element.checked
        })
        if (arr.length === 0) {
            this.$Message.info('请先选择模板！')
            return
        }
        this.$api.post('/member-reversion/user/userTemplateManage/save', {
            account: this.$user.loginAccount,
            userType: arr[0].userType,
            templateId: arr[0].id,
            loginStep:{
              id: this.stepId ? this.stepId : 0,
              account: this.$user.loginAccount,
              templateId: arr[0].id,
              step: 1
            }
            // id: this.id === undefined || this.id === '' ? '' : this.id
        }).then(response => {
            console.log('response', response)
            if (response.code === 200) {
                this.$Message.success('保存成功！')
                this.$emit('on-next', arr[0].id)
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    }
  }
}
</script>
<style lang="scss" scoped>
.back-btn {
  background-color: #9B9B9B;
  border-color: #9B9B9B;
  &:hover {
    background-color: #9B9B9B;
    border-color: #9B9B9B;
  }
}
.layout {
  width: 1000px;
  margin: auto;
  margin-top: 20px;
}
.template-item {
  width: calc(100% / 5 - 20px);
  height: 180px;
  margin: 10px;
  float: left;
  box-shadow: 0px 0px 20px #eee;
  box-sizing: border-box;
  padding: 10px;
  border-radius: 3px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  &:hover{
    box-shadow: 0 0 0 2px #00c587;
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
        content: '已选';
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
.checked {
  border: 2px solid #00C587;
}
</style>
