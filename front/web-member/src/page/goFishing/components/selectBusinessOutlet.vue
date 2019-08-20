<template>
  <Modal v-model="show" 
     width="750" 
     heigth="700px"
     title="选择服务网点"
     :mask-closable="false" 
     :styles="{top: '20px'}">
     <div>
         <Form :label-width="100">
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
         </Form>
         
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
      templateId: '',
      yearId: '',
      appId: '',
      dictId: '',
      data: [],
      datas: [],
      columns1: [
        {
            type: 'selection',
            width: 60,
            align: 'center'
        },{
          title: '网点名称',
          key: 'networkName'
        },{
          title: '网点地址',
          key: 'perfectAddress'
        }
      ],
      selectData: []
    }
  },
  created() {
    this.$api.post('/member-reversion/realStep/findEnableStep', {
                account: this.$user.loginAccount
            }).then(response => {
            if (response.code === 200) {
                if (response.data) {
                    this.templateId = response.data.templateId
                    // this.handleSelectData()
                    this.init()
                    this.initFileTags()
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
    // 点击确定
    onOk () {
      this.$emit('on-save', this.selectData)
      this.show=  false
    },
    // 初始化查询年度文件夹信息
    init () {
      this.yearId = ''
      this.$api.post('/member-reversion/perfect/findYearInfo', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.code === 200) {
          let files = []
          response.data.forEach(element => {
            files.push({
              name: element.fileName,
              id: element.id,
              checked: element.fileName.substring(0, 4) === new Date().getFullYear().toString() ? true : false
            })
          })
          // 默认年份id
          files.forEach(element => {
            if (element.checked) {
              this.yearId = element.id
            }
          })
          this.handleInit()
        }
      })
    },
    // 初始化应用标签信息
    initFileTags () {
      this.$api.post('/member-reversion/user/perfect/findModuleInfo', {
        account: this.$user.loginAccount,
        level: '0',
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          response.data.forEach(element => {
            if (element.url === 'businessOutlets') {
              this.appId = element.appId
            }
          })
          this.handleInit()
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    //  初始化数据
    handleInit (type) {
       this.$api.post('/member-reversion/user/perfect/initData', {
            account: this.$user.loginAccount,
            yearId: this.yearId,
            appId: this.appId,
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
              this.tabData = []
              response.data.subModule.forEach((element, index) => {
                if (element.url === 'businessOutlets') {
                  this.dictId = element.dictId
                }
                // this.tabData.push({
                //   title: element.name,
                //   name: element.url,
                //   id: element.dictId,
                //   checked: index === this.activeInidex ? true : false,
                //   status: element.isComplete
                // })
              })
              this.$api.post('/member-reversion/businessOutlets/findBusinessOutletsInfo',{
                  account: this.$user.loginAccount,
                  yearId: this.yearId,
                  dictId: this.dictId,
                  templateId: this.templateId
              }).then(response => {
                  if (response.code == 200) {
                      if (response.data.BusinessOutlets.length) {
                          this.data = response.data.BusinessOutlets
                          this.datas = response.data.BusinessOutlets
                      }
                  }
              })
            }
        })
    },
    // 查询
    onSearch () {
      this.data = this.datas.filter(e => {
        return e.networkName.indexOf(this.keyWord) > -1
      })
    }
  },
}
</script>
