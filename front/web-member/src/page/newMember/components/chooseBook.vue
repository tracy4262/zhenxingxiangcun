<template>
  <div>
    <Modal
      v-model="isShow"
      :mask-closable="false"
      width="750"
      heigth="700px"
      title="选择图书">
        <div>
          <Table :columns="columns" :data="data"></Table>
           <Page class="tc pt40 pb20" :total="total" :current="pageNum" :page-size="pageSize" @on-change="onChange"/>
        </div>
        <div slot="footer">
            <Button type="text" class="pr30 pl30" shape="circle" @click="isShow = false">取消</Button>
            <Button type="primary" class="pr30 pl30" shape="circle" @click="next">确定</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
export default {
  components: {
  },
  data () {
    return {
      isShow: false,
      data: [],
      columns: [
        {
          width: 50,
          title: ' ',
          render: (h, params) =>{
              return h('Radio', {
                props: {
                  value: params.row.checked
                },
                on: {
                  'on-change': (val) => {
                    params.row.checked = true
                    this.data.forEach((item, index) => {
                      if (item.checked) {
                        item.checked = false
                        this.data.splice(index, 1, item)
                      }
                    })
                    this.data.splice(params.index, 1, params.row)
                    this.checkedData[0] = params.row
                  }
                }
              })
            }
        }, {
          title: '图书标题',
          width: 300,
          key: 'title'
        }, {
          title: '作者',
          key: 'author'
        }, {
          title: '所属文件夹',
          key: 'mediaName'
        }
      ],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      checkedData: []
    }
  },
  created() {
  },
  methods: {
    init () {
      this.isShow = true
      this.checkedData = []
      this.$api.post('/member/memberIntroduce/findMediaLibraryInfo', {
        account: this.$user.loginAccount,
      }).then(response => {
        if (response.code === 200) {
          this.data = response.data
          this.data.forEach(e => {
            e.checked = false
          })
        } else {
          this.$Message.error('查询失败')
        }
      })
    },
    onChange (e) {
      this.pageNum = e
      this.init()
    },
    next () {
      if (this.checkedData.length) {
        this.$api.post('/member/memberIntroduce/findMediaBookDetail', {
          id: this.checkedData[0].id,
          media_id: this.checkedData[0].mediaId,
        }).then(response => {
          if (response.code == 200) {
            this.$emit('on-save', response.data[0])
            this.isShow = false
          } else {
            this.$Message.warning('请重新选择')
          }
        })
      } else {
        this.$Message.warning('请选择图书！')
      }
    }
  },
}
</script>