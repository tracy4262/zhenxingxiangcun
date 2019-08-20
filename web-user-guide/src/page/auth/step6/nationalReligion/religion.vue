<template>
  <div class="pd20">
    <Title :title="title" :id="id" edit :yearId="yearId"></Title>
    <div class="pd20">
      <Form :label-width="100" label-position="left" ref="data">
        <Row  :gutter="38">
            <Col span="8">
              <FormItem label="权限">
                <Switch class="ml20"  size="large" v-model="status" :disabled="true">
                  <span slot="open">公开</span>
                  <span slot="close">隐藏</span>
                </Switch>
              </FormItem>
            </Col>
        </Row>
         <Row  :gutter="16" class="pt20 pb20" type="flex" align="middle">
            <Col span="16" style="line-height:24px;">
            <span>总计：{{total}}人，</span>
            <span v-for="(item, index) in typeList">
              {{item.name}}: {{item.number}} 
              <span v-if="index == typeList.length-1">。</span>
              <span v-else>，</span>
            </span>
            </Col>
             <!-- <Col span="8" class="tr">
               <Button  @click="handleUpdate">更新宗教信息</Button>
               <Button class="ml20" @click="exportData">导出</Button>
            </Col> -->
        </Row>
        <div class="pd5 pt20">
          <Table :columns="columns" border :data="dataList" size="small" ref="table" class="religion"></Table>
          <div class="tc pt20">
            <Page :total="total" :page-size="pageSize" show-elevator show-sizer @on-change="handleChange" @on-page-size-change="pageSizeChange"/>
          </div>
        </div>
      </Form>
    </div>
    <Title title="文字预览"></Title>
    <div class="pd20 pt30">
      <Input type="textarea" v-model="preview" :autosize="{minRows: 3,maxRows: 5}"></Input>
    </div>
    <div class="tc pd20">
      <Button type="primary" @click="onSave">保存</Button>
    </div>
  </div>
</template>

<script>
import Title from '../../components/title'
export default {
  props: {
    yearId: {
      type: String
    },
    id: {
      type: String
    },
    appId: {
      type: String
    }
  },
  components: {
    Title
  },
  data () {
    return {
      status: true,
      dataList: [],
      preview: '',
      title: '',
      typeList: [],
      total: 0,
      pageSize: 10,
      pageNum: 0,
      columns: [
        {
          title: '编号',
          width: 100,
          key: 'number',
          align: 'center',
          tooltip: true,
        },{
          title: '姓名',
          width: 120,
          align: 'center',
          tooltip: true,
          key: 'name'
        },{
          title: '身份证号码',
          width: 160,
          align: 'center',
          tooltip: true,
          key: 'card'
        },{
          title: '联系方式',
          width: 120,
          align: 'center',
          tooltip: true,
          key: 'phone'
        },{
          title: '宗教派别',
          width: 110,
          align: 'center',
          tooltip: true,
          key: 'faction'
        },{
          title: '职务',
          key: 'position',
          tooltip: true,
          align: 'center',
          render: (h, params) => {
            return h('div',[
                h('Input',{
                  props: {
                    size: 'small',
                    value: params.row.position,
                    search: true,
                    'enter-button': '保存'
                  },
                  style: {
                    marginBottom: '1px',
                  },
                  on: {
                    'on-search': (val) => {
                      console.log(val)
                      this.handleSave(val, params)
                        console.log('event', event)
                        console.log('event', event.target.value)
                        console.log('params', params)
                      }
                    }
                  }
                )
            ])
          }
        }
      ]
    }
  },
  methods: {
    //  初始化数据
    handleInit () {
        this.$api.post('/member-reversion/nationalReligion/find',{
            account: this.$user.loginAccount,
            templateId: this.$template.id,
            yearId: this.yearId,
            dictId: this.id,
            pageSize: this.pageSize,
            pageNum: this.pageNum
        }).then(response => {
            if (response.code == 200) {
               if (response.data.status === '1') {
                  this.status = true
                } else if (response.data.status === '0'){
                  this.status = false
                }
                if (response.data.preview) {
                  this.preview = response.data.preview
                }
                this.title = response.data.propertyName
                this.total = response.data.dataList.total
                this.dataList = response.data.dataList.list
                this.dataList.forEach(e =>{
                  if (!e.position) {
                    e.position = ''
                  }
                })
                this.typeList = response.data.typeList
            }
        })
    },
    // 预览
    previewChange () {
      let str = ``
      if (this.total) {
        str += `所在地信教群众有${this.total}，其中：`
        this.typeList.forEach(e => {
          if (e.number) {
            str += `${e.name}${e.number}人，`
          }
        })
      }
      if (str) {
        str = `${str.substring(0,str.length-1)}。`
      }
      this.preview = str
    },
     // 保存
    onSave () {
      let s = 0 
      this.status ? s = 1 : s = 0
        let list = {
            account: this.$user.loginAccount,
            templateId: this.$template.id,
            yearId: this.yearId,
            dictId: this.id,
            textPreview: this.preview,
            status: this.status,
            isComplete: true,
        }
        this.$api.post('/member-reversion/perfect/saveTextPreview', list).then(response => {
            if (response.code === 200) {
                this.$Message.success('保存成功')
                this.$emit('on-save')
                this.handleInit()
            }
        })
    },
    pageSizeChange (pageSize) {
      this.pageSize = pageSize
      this.pageNum = 0
      this.handleInit()
    },
    // 翻页
    handleChange (num) {
      this.pageNum = num
      this.handleInit()
    },
    handleSave (value, params) {
      let item = params.row
      item.position = value
      item.account = this.$user.loginAccount
      this.$api.post('/member-reversion/nationalReligion/save', item).then(response => {
        if (response.code === 200) {
         this.$Message.success('保存成功')
        }
      })
    },
    // 导出
    exportData () {
      this.$api.post('/member-reversion/nationalReligion/findAll',{
            account: this.$user.loginAccount,
            yearId: this.yearId
        }).then(response => {
            if (response.code == 200) {
                this.$refs.table.exportCsv({
                    filename: this.title,  //文件名
                    columns: this.columns,   //导出列
                    data:  response.data,  //导出数据
                    original: false,
                })
            }
        })
    },
    // 更新宗教信息
    handleUpdate () {
      this.$api.post('/member-reversion/nationalReligion/update', {
        account: this.$user.loginAccount,
        yearId: this.yearId,
        dictId: this.id
      }).then(response => {
        if (response.code === 200) {
          this.pageNum = 0
          this.handleInit()
        }
      })
    }
  },
  mounted () {
      this.preview = `所在地信教群众有（），其中：（）人。`
  }
}
</script>

<style lang="scss" scoped>

</style>
