<template>
  <div class="productionPlans">
    <search-ipt type="1" @on-search="onSearch" ref="search"></search-ipt>
    <Divider />
    <div class="table_top">
      <div class="table_top_title">{{$parent.displayName}}生产计划表</div>
      <Button type="primary" @click="onAdd">添加</Button>
    </div>
    <div class="table_main">
      <Table border :columns="columns" :data="data" ></Table>
    </div>
    <div class="tc pt20" v-if="data.length">
       <Page :total="total" @on-change="getNextPage" :page-size="pageSize" :current="pageNum"></Page>
    </div>
    <addPlans ref="add"  @on-save="onSave"></addPlans>
  </div>
</template>

<script>
import searchIpt from './component/searchIpt'
import addPlans from './component/addPlans'
export default {
  components: {
    searchIpt,
    addPlans
  },
  data () {
    return {
      pageNum: 1,
      total: 0,
      pageSize: 10,
      columns: [
        {
          title: '生产序号',
          key: '',
          width: 100,
          align: 'center',
          fixed: 'left',
          key: 'serialNumber'
        },
        {
          title: '年度',
          align: 'center',
          width: 100,
          key: 'fileName'
        },
        {
          title: '物种名称',
          align: 'center',
          width: 120,
          key: 'species'
        },
        {
          title: '品种名称',
          align: 'center',
          width: 120,
          key: 'varietyName'
        },
        {
          title: '品种来源',
          align: 'center',
          width: 120,
          key: 'varietySource'
        },
        {
          title: '播种时间',
          align: 'center',
          width: 120,
          key: 'sowingTime'
        },
        {
          title: '播种面积',
          align: 'center',
          width: 120,
          key: 'sownArea',
          render: (h, params) => {
            return h('div', `${params.row.sownArea}亩`);
          }
        },
        {
          title: '基地名称',
          align: 'center',
          key: 'baseName',
          width: 150,
            render: (h, params) => {
              return h('div', `${params.row.baseName.join('、')}`);
            }
        },
        {
          title: '地块编号',
          align: 'center',
          width: 150,
          key: 'land',
            render: (h, params) => {
              return h('div', `${params.row.land.join('、')}`);
            }
        },
        {
          title: '操作',
          align: 'center',
          width: 160,
          fixed: 'right',
          key: '',
            render: (h, params) => {
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => { 
                              let data = params.row
                              data.varietyNameId = Number(data.varietyNameId)
                                this.edit (data)
                            }
                        }
                    }, '编辑'),
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                              this.del(params.row)
                            }
                        }
                    }, '删除')
                ]);
            }
        }
      ],
      data: [],
      id: '',
      name: '',
      yearId: '',
      serialNumber: ''
    }
  },
  created() {
    if (this.$route.query.yearId) {
      this.yearId = this.$route.query.yearId
      this.$parent.yearId = this.$route.query.yearId
    }
    if (this.$route.query.year) {
      this.$parent.year = this.$route.query.year
    }
    if (this.$route.query.id) {
      this.id = this.$route.query.id
      this.$parent.id = this.$route.query.id
      this.getInit()
    }
    if (this.$route.query.name) {
      this.$parent.name = this.$route.query.name
    }
  },
  mounted() {
    this.$refs['search'].init()
  },
  methods: {
    getInit () {
      this.$refs['search'] ? this.$refs['search'].init() : ''
      let data = {
        wikiId: this.id,
        yearId: this.yearId,
        account: this.$user.loginAccount,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        serialNumber: this.serialNumber, // 根据生产序号查询
      }
      this.$api.post('/shop/plant/findPlantProductionInfo', data).then(response => {
        if (response.code === 200) {
          this.total = response.data.total
          this.data = response.data.list
        }
      })
    },
    // 保存成功
    onSave (list) {
      list.wikiId = this.id
      list.yearId = this.yearId
      list.account = this.$user.loginAccount
      this.$api.post('/shop/plant/saveOrUpdatePlantProduction', list).then(response => {
        if (response.code === 200) {
          this.getNextPage(1)
          this.$refs['add'].isShow = false
          this.$Message.success('保存成功！')
          this.$refs['add'].$refs['info'].resetFields()
        }
      })
    },
    edit (item) {
      let arr = item.baseId
      let baseId = []
      arr.forEach(e => {
        baseId.push(Number(e))
      })
      item.baseId = baseId
      this.$refs['add'].init('编辑生产计划', item)
    },
    del (item) {
    this.$Modal.confirm({
          title: '操作提示',
          content: '<p>您确定删除？</p>',
          cancelText: '取消',
          onOk: () => {
              this.$api.post('/shop/plant/deletePlantProductionInfo', {id: item.id}).then(response => {
                if (response.code === 200) {
                  this.getNextPage(1)
                  this.$Message.success('删除成功！')
                }
              })
          }
      })
    },
    // 点击查询
    onSearch (form) {
      this.serialNumber = form.productNumber
      this.getInit()
    },
    // 点击添加
    onAdd () {
      let list = {
        serialNumber: '', // 生产序号
        species: this.$route.query.name, // 物种名称
        varietyName: '', // 品种名称
        varietyNameId: '',
        varietySource: '', // 品种来源 
        sownArea: '', // 播种面积
        sowingTime: '', // 播种时间
        baseId: [], // 基地名称
        baseName: [], // 基地名称
        plotNumber: [], // 地块编号
      }
      this.$refs['add'].init('新增生产计划', list)
    },
    getNextPage (e) {
      this.pageNum = e
      this.getInit()
    },
  }
}
</script>

<style lang="scss" scoped>
.productionPlans{
  width: 1000px;
  min-height: 800px;
  margin: 0 auto;
  background-color: #fff;
  .table_top{
    display: flex;
    justify-content: space-between;
    padding:26px;
    .table_top_title{
      font-size: 16px;
      color: #000 90%;
    }
  }
  .table_main{
    padding: 0 26px;
  }
}
</style>
