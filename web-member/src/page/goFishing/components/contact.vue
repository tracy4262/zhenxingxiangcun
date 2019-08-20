<template>
  <Modal v-model="show" 
     width="900" 
     heigth="700px"
     title="选择联系人信息"
     :mask-closable="false" 
     :styles="{top: '20px'}">
     <div>
         <!-- <Form :label-width="100">
          <Row>
              <Col span="14" class="tc">
              <FormItem label="网点名称:">
                  <Input  v-model="keyWord"/>
              </FormItem>
              </Col>
              <Col span="3" class="tc">
                <Button @click="onSearch">查询</Button>
              </Col>
          </Row>
         </Form> -->
         
          <Table :columns="columns1" :data="data" class="mt20" @on-selection-change="onChane"></Table>
     </div>
     <div slot="footer">
        <Button @click="show = false">取消</Button>
        <Button type="primary" @click="onOk">确定</Button>
      </div>
    </Modal>
</template>
<script>
export default {
  data () {
    return {
      show: false,
      keyWord: '',
      data: [],
      columns1: [
        {
            type: 'selection',
            width: 60,
            align: 'center'
        },{
          title: '联系人姓名',
          key: 'contact_name'
        },{
          title: '身份证号码',
          width: 180,
          key: 'card'
        },{
          title: '座机电话',
          key: 'seat_phone'
        },{
          title: '手机',
          key: 'phone'
        },{
          title: '邮箱',
          width: 200,
          key: 'email'
        }
      ],
      selectData:[]
    }
  },
  created() {
    this.$api.post('/member-reversion/realStep/findEnableStep', {
                account: this.$user.loginAccount
            }).then(response => {
            if (response.code === 200) {
                if (response.data) {
                    this.templateId = response.data.templateId
                    this.handleInit()
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
  },
  methods: {
    onChane (e) {
      this.selectData = e
    },
    onOk () {
      this.$emit('on-save', this.selectData)
      this.show=  false
    },
    // 初始化获取数据
    handleInit () {
      this.$api.post('/member-reversion/user/realCertification/findMemberContact', {
        user_id: this.$user.loginAccount,
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          this.data = response.data
          this.data.forEach(e => {
              if ('detailAddress' in e) {
                e.detailAddress = ''
              } else {
                e.detailAddress = ''
                if (e.location) {
                  e.detailAddress += '所在位置：' + e.location + '、'
                }
                if (e.address ) {
                  e.detailAddress += '详细地址：' + e.address + '、'
                }
                if (e.house_number) {
                  e.detailAddress += e.house_number + '号、'
                }
                if (e.longitude) {
                  e.detailAddress += '东经：' + e.longitude + '、'
                }
                if (e.latitude) {
                  e.detailAddress += '北纬：' + e.latitude + '、'
                }
                if (e.detailAddress) {
                  e.detailAddress = e.detailAddress.substring(0, e.detailAddress.length - 1) + '。'
                }
              }
          })
        }
      })
    },
    // 查询
    onSearch () {

    }
  },
}
</script>
