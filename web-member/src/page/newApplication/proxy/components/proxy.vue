<!--
    作者：chenqim
    时间：2018-12-14
    描述：已代理页面
-->
<template>
    <div class="pd20" style="min-height: 500px;">
      <Row>
        <Button v-for="(item, index) in typeList" :key="index" :type="activeIndex === index ? 'primary' : 'text'" @click="typeChange(index)">{{ item.label }}</Button>
      </Row>
      <Form ref="form" :model="form" :rules="rules" :label-width="80" label-position="left" class="mt20">
        <Row :gutter="36">
          <Col span="12">
            <FormItem label="会员名称">
              <Input v-model="form.memberName"></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="用户名">
              <Input v-model="form.account"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="36">
          <Col span="12">
            <FormItem label="代理时间">
              <DatePicker v-model="form.time" type="daterange" placement="bottom-end" style="width: 100%" @on-change="dateChange"></DatePicker>
            </FormItem>
          </Col>
          <Col span="12">
            <Button type="primary" @click="query">查询</Button>
          </Col>
        </Row>
      </Form>
      <!-- <Row type="flex" align="middle" class="tc">
        <Input prefix="ios-search" v-model="searchKey" placeholder="查找登录账号或登录名称" @on-change="onChange" />
      </Row> -->
      <Row>
        <div v-for="(item, index) in list" :key="index" class="proxy-card">
          <proxy-card :item="item" :type="0" @refresh="refresh"></proxy-card>
        </div>
        <div class="mt20 tr" v-if="list.length !== 0">
          <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange" />
        </div>
      </Row>
    </div>
</template>
<script>
import proxyCard from './proxyCard'
export default {
    name: 'proxy',
    components: {
        proxyCard
    },
    data () {
        return {
            searchKey: '',
            form: {
              memberName: '',
              account: '',
              time: [],
              lowTime: '',
              upperTime: '',
              memberClass: ''
            },
            rules: {},
            list: [],
            total: 0,
            pageSize: 9,
            pageNum: 1,
            activeIndex: 0,
            typeList: [
              { label: '全部', value: '' }
            ]
        }
    },
    created () {
        this.init()
        this.addSession()
        // 获取会员类型数据
        this.$api.post('/member-reversion/realCertification/getMemberType', {
          pid: '0'
        }).then(response => {
          if (response.code == 200) {
            response.data.forEach(element => {
              this.typeList.push(element)
            })
          }
        })
    },
    methods: {
      typeChange (index) {
        this.activeIndex = index
        this.form.memberClass = this.typeList[index].value
        this.query()
      },
      dateChange (arr) {
        this.form.lowTime = arr[0]
        this.form.upperTime = arr[1]
      },
      query () {
        this.pageNum = 1
        this.init()
      },
      init () {
        this.$api.post('/member/reversionProxy/queryAccount', {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            key: this.form.account,
            status: 1,
            proxyAccount: this.$user.loginAccount,
            memberClass: this.form.memberClass,
            memberName: this.form.memberName,
            lowTime: this.form.lowTime,
            upperTime: this.form.upperTime
        }).then(response => {
            console.log('init res', response)
            if (response.code === 200) {
                this.list = response.data.list
                this.total = response.data.total
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
      },
      addSession () {
          this.$api.post('/member/reversionProxy/proxyList', {
            proxyAccount: this.$user.loginAccount
          }).then(response => {
              console.log('add res', response)
              if (response.code === 200) {
                  sessionStorage.setItem('key', response.data.key)
                  response.data.proxy.forEach(element => {
                      sessionStorage.setItem(element.account, JSON.stringify(element.session))
                  })
              }
          }).catch(error => {
              this.$Message.error('服务器异常！')
          })
      },
      onChange () {
          this.pageNum = 1
          this.init()
      },
      pageChange (page) {
          this.pageNum = page
          this.init()
      },
      refresh () {
          this.init()
      }
    }
}
</script>
<style lang="scss" scoped>
    .proxy-card {
        width: calc(100% / 3);
        display: inline-block;
    }
</style>
