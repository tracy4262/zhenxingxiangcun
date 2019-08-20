<template>
  <div >
      <Modal
        v-model="isShow"
        title="编辑"
        :mask-closable="false"
        :on-visible-change="onChange"
        :closable="false"
        width="560">
      <div class="pt30 pb10">
        <Form ref="form" :model="data" label-position="left" :label-width="80" :rules="formItemInline">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="姓名">
                        <Input v-model="data.groupFriendAccountName" :maxlength="50"  />
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item label="性别">
                      <Select v-model="data.sex" >
                        <Option value="男">男</Option>
                        <Option value="女">女</Option>
                      </Select>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                     <Form-item label="身份证号" prop="card">
                        <Input v-model="data.card" :maxlength="18"  />
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item label="联系方式" prop="phone">
                        <Input v-model="data.phone" :maxlength="11"  />
                    </Form-item>
                </Col>
            </Row>
        </Form>
      </div>
      <div slot="footer" class="tc">
        <Button @click="cancel">取消</Button>
        <Button @click="onSaveTitle" type="primary">确定</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import {isIdCard2, isPhone2} from '~utils/validate'
export default {
  data () {
    return {
      isShow: false,
      data: {},
      formItemInline: {
        card: [
            {validator: isIdCard2, trigger: 'blur'},
        ],
        phone: [
            {validator: isPhone2, trigger: 'blur'},
        ],
      },
    }
  },
  created() {
  },
  methods: {
    init (data) {
      this.data = Object.assign({groupFriendAccountName: '', sex: '', card: '', phone: ''}, data)
      this.isShow = true
    },
    onChange (e) {
      console.log('eeee', e)
    },
    cancel () {
      this.isShow = false
    },
    resetField () {
      this.$refs['form'].resetFields()
    },
    onSaveTitle () { 
      console.log(this.data)
      this.$refs['form'].validate( (valid) => {
            if (valid) {
              this.$api.post('/member/staffGateway/updateStaffOfIdentity', this.data).then(response => {
                if (response.code === 200) {
                  this.cancel()
                  this.$Message.success('保存成功！')
                  this.$emit('on-save', this.data)
                } else{
                  this.$Message.error('保存失败！')
                }
              })
            } else {
              this.$Message.error('请核对表单信息！')
            }
        })
    },
  }
}
</script>
