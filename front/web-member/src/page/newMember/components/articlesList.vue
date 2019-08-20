<template>
  <div>
    <articles :dataType="item.dataType" :data="item" v-for="(item,index) in columnList" :key="index" class="mb15"/>
    <div class="pd10 tc" v-if="columnList.length === 0">
        <p v-if="dataType == '全部'">没有相关数据！</p>
        <p v-else>没有相关{{dataType}}！</p>
    </div>
    <div class="tc pd20" v-if="columnList.length">
      <Page :total="total" @on-change="getNextPage" :page-size="pageSize" :current="currentPage"></Page>
    </div>
  </div>
</template>
<script>
import articles from './articles.vue'
  export default {
    components: {
      articles
    },
    props: {
      dataType: String,
      docType: String
    },
    data () {
      return {
        columnList: [],
        currentPage: 1,
        pageSize: 10,
        total: 0
      }
    },
    created() {
      this.getList()
    },
    methods:{
      getNextPage (e) {
        this.currentPage = e
        this.getList()
      },
      // 查询政策、知识、资讯的列表
      getList () {
        this.$api.get('/member/columnSettings/findColumnList?label=全部&columnId=' + this.dataType + '&currentPage=' + this.currentPage + "&pageSize=" + this.pageSize + '&account=' + this.$user.loginAccount + '&docType=' + this.docType)
          .then(response => {
            console.log('respklk', response)
              if (response.data) {
                var list = response.data.dataList
                this.total = response.data.total
                list.forEach(item => {
                    item.disabled = false
                    item.showReply = false
                    if(item.list.length) {
                        item.list.forEach(l => {
                            l.disabledchi = false
                            l.showReplychi = false
                            l.replyChildren=[]
                            l.showOrHideComment = true
                        })
                    }
                })
                this.columnList = list
              }
            })
      },
    }
  }
</script>
