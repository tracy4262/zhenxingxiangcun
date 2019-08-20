<template>
    <div class="pd20">

        <!-- 物种标题 -->        
        <Row class="pt20 pb20">
            <Col span="12">
               <h3 class="pl20">物种列表</h3>
            </Col>
            <Col span="12" class="tr pr20">
                <Button 
                    type="default" 
                    icon="android-add"
                    @click="handleFilterModal('speciFilter')" >
                    添加物种
                </Button>
            </Col>
        </Row>
        <div class="pb30">            
            <Table  :columns="columns1" :data="speciesDatas"></Table>
        </div>
        <!-- 相关物种 -->
        <vui-filter
          ref="speciFilter"
          :cols="2"
          :pageShow="true"
          :total="total"
          :pageCur="pageCur"
          :classifyDatas="speciClassifyDatas"
          :resultDatas="speciResultDatas"
          :load-data="loadSpeciDatas"
          @on-search="handleSpeciSearch"
          @on-get-classify="handleGetSpeciClassify"
          @on-get-result="handleGetSpeciResult"
          @on-page-change="handleSpeciPageChange"/>
    
    </div> 
</template>
<script>
    import vuiFilter from '~components/vuiFilter/filter'
    export default {
        name: 'speciesList',
        components: {
            vuiFilter
        },
        data () {
            return {
                height:0,
                columns1: [
                    {
                        title: '物种名称',
                        key: 'species_name'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        color: '#8C8C8C'
                                    },
                                    on: {
                                        click: () => {
                                            this.deleteSpeciesInfo(params.row.species_id)
                                        }
                                    }
                                }, '删除')
                            ])
                        }
                    }
                ],
                speciesDatas: [],
                total: 0,
                pageCur: 1,
                speciResultDatas: [],
                speciClassifyDatas: [
                    {
                        label: '动物',
                        value: '0',
                        classId: '',
                        loading: false,
                        checked: false,
                        children: []
                    },
                    {
                        label: '植物',
                        value: '1',
                        classId: '',
                        loading: false,
                        checked: false,
                        children: []
                    }
                ],
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                account:''
            }
        },
        created(){
            this.account = this.loginuserinfo.loginAccount
            // 获取物种列表
            this.getSpeciesInfo()
            // 取相关物种结果
            this.loadSpeciResult('', '', [], this.pageCur, [])
        },
        methods: {
            // 获取物种列表
            getSpeciesInfo () {
                this.$api.post('/member/fishing/getSpeciesInfo', {
                        account: this.account,
                        type: '0'
                    }).then(res => {
                        if (res.code === 200) {
                            this.speciesDatas = res.data
                        }
                    })
            },
            // 删除物种列表
            deleteSpeciesInfo (id) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '确定删除该物种？',
                    onOk: () => {
                        this.$api.post('/member/fishing/deleteSpeciesInfo', {
                            account: this.account,
                            speciesId : id,
                            type: '0'
                        }).then(res => {
                            if(res.code === 200){
                                this.$Message.success('删除成功')
                                this.getSpeciesInfo()
                            } else if (res.code === 400) {
                                this.$Message.error('此物种已与产品相关联，无法删除')
                            } else{
                                this.$Message.error('删除失败')
                            }
                        })
                    }
                })
            },
            // 添加物种列表
            insertSpeciesInfo (result) {
                const speciesInfoDatas = [] 
                result.forEach(item => speciesInfoDatas.push({
                    speciesId: item.value, 
                    speciesName: item.label
                    }))
                this.$api.post('/member/fishing/insertSpeciesInfo', {
                    account: this.account,
                    speciesInfo: speciesInfoDatas,
                    type: '0'
                }).then(response=>{
                    if(response.code === 200){
                        this.$Message.success('添加成功')
                        this.getSpeciesInfo()
                    } else if (response.code === 201) {
                        // 所有的都重复
                        this.$Message.error({
                            content: `物种已存在，请不要重复添加。`,
                            duration: 3
                        })
                    } else if (response.code === 202) {
                        // 部分重复
                        this.$Message.error({
                            content: `${response.msg}`,
                            duration: 6
                        })
                        this.getSpeciesInfo()
                    }
                })
            },
            // 高级搜索弹窗
            handleFilterModal (name) {
                this.$refs[name].highFilterShow = true
                this.$refs.speciFilter.handleReset();
                this.handleGetSpeciResult('', []);
            },
            // 取相关物种
            handleSpeciSearch (letter, keyword, classify, result) {
              this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
            },
            loadSpeciDatas (item, callback) {
              item.loading = true
              this.$api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
                item.loading = false
                var d = res.data
                d.forEach(child => {
                  child.checked = false
                  child.label = child.className
                })
                item.children = d
                callback()
              })
            },
            // 保存选择分类结果
            handleGetSpeciClassify (letter, keyword, classify, result) {
              this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
            },
            // 切页
            handleSpeciPageChange (letter, keyword, classify, num, result) {
              this.pageCur = num
              this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
            },
            loadSpeciResult (letter, keyword, classify, num, result) {
              if (classify.length) {
                var arr = []
                classify.forEach(item => {
                  arr.push(item.classId)
                })
              } else {
                arr = null
              }
              this.$api.post('/member/specicesClass/findSpecies', {
                keywords: keyword,
                fpinyin: letter === '全部' ? '' : letter,
                fclassifiedid: arr,
                pageNum: num,
                pageSize: 32
              }).then(res => {
                  console.log('1111', res)
                var data = res.data
                this.total = data.total
                if (result) {
                  result.forEach(item => {
                    data.list.forEach((child, index) => {
                      if (child.label === item.label) {
                        child.checked = true
                      }
                    })
                  })
                } else {
                  data.list.forEach(child => {
                    child.checked = false
                  })
                }
                this.speciResultDatas = data.list
              })
            },
            // 保存最后筛选结果
            handleGetSpeciResult (classify, result) {
                if(result.length){
                    this.insertSpeciesInfo(result)
                }
            }
        }
    }
</script>

