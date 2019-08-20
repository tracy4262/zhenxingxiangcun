<template>
  <div class="pt20">
    <Table :columns="column" :data="data"></Table>
    <Page class="pt30 tc pb30" :total="total" :page-size="pageSize" @on-change="pageChange"></Page>
  </div>
</template>

<script>
import { download } from './upload'
  export default {
    name: '',
    data() {
      return {
        total: 0,
        pageSize: 10,
        pageNum: 1,
        column: [
          {
            title: '序号',
            width: '100',
            key: 'number',
            render(h, params) {
              return h('span', params.index)
            },
          },
          {
            title: '文件名',
            key: 'name',
            render(h, params) {
              return h('span', params.row.name)
            },
          },
          {
            title: '所属文件夹',
            key: 'number',
            render(h, params) {
              return h('span', params.row.folderName)
            },
          },
          {
            title: '创建时间',
            key: 'createTime',
            render: (h, params)=> {
              return h('span', params.row.createTime)
            },
          },
          {
            title: '文件大小',
            key: 'size',
            render: (h, params)=> {
              return h('span', params.row.size)
            },
          },
          {
            title: '操作',
            render: (h, params)=> {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  style: {
                    'marginRight': '20px'
                  },
                  on: {
                    click: () => {
                      this.handleDownload(params.row, params.index)
                    }
                  }
                }, '下载'),
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.handleDel(params.row, params.index)
                    }
                  }
                }, '删除')
              ])
            },
          }
        ],
        data: [],
        id: ''
      }
    },
    created () {

    },
    methods: {
      init (id) {
        if (id){
          this.id = id
        }
        this.$api.post('/member-reversion/myMap/folderDetail', {
          account: this.$user.loginAccount,
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          id: this.id
        }).then(res => {
          res.data.list.forEach(e => {
            e.size = this.getMathPow(e.size)
          })
          this.data = res.data.list
          this.total = res.data.total
        })
      },
      getMathPow (size) {
        let base = 1024
        let base2 = Math.pow(base,2)
        let base3 = Math.pow(base,3)
        if (size > base3) {
          return `${(size/base3).toFixed(2)}G`
        } else if (size > base2) {
          return `${(size/base2).toFixed(2)}MB`
        } else if (size > base) {
          return `${(size/1024).toFixed(2)}KB`
        }
      },
      // 分页
      pageChange (e) {
        this.pageNum = e
        this.init()
      },
      // 删除
      handleDel (item, index) {
        this.$Modal.confirm({
          title: '是否确定删除',
          onOk:()=>{
            this.$api.get(`/member-reversion/myMap/deleteFile?id=${item.fileId}`).then(res => {
              if (res.code === 200) {
                this.$Message.success('删除成功')
                if (this.data.length === 1) {
                  if (this.total === 1) {
                    this.data = []
                  } else {
                    this.pageChange(1)
                  }
                } else {
                  // this.onChange(this.pageNum)
                  this.data.splice(index, 1)
                }
              } else {
                this.$Message.error('删除失败')
              }
            })
          },
          okText:'确定',
          cancelText:'取消'
        })
      },
      // 下载
      handleDownload (item, index) {
        download(item.url, '文件')
      }
    }
  }
</script>

<style lang="less" scoped>

</style>
