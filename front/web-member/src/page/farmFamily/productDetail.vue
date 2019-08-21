<template>  
  <div  class="pt30 pl10 pr10 family-deatil">
       <Form label-position="left" :label-width="150">
        <Row :gutter="32">
           <Col span="12">
                <Form-item label="产出产品">
                    <Button type="primary" @click="handleAdd"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
           </Col>
        </Row>
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" :bordered="false">
            <div class="btn-toolbar-card">
                <div class="btn-toolbar">
                    <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                </div>
            </div>
            <!-- <Row>
                <Col span="22"> -->
                <Form  :ref="`corpInfo${index}`" :model="item" label-position="left" :label-width="82" :rules="ruleInline">
                    <Row :gutter="16">
                        <Col span="24">
                            <Form-item  label="权限">
                                <i-switch  v-model="item.product_status" size="large">
                                    <span slot="open">公开</span>
                                    <span slot="close">隐藏</span>
                                </i-switch>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item  prop="species" label="物种">
                                <Input v-model="item.species" icon="ios-close" class="pinput-clear" readonly @on-focus="handleFilterModal('speciFilter', index)" @on-click="inputClear(index)"/>
                                <!-- <Input v-model="item.species"></Input> -->
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="scale" label="生产规模">
                                <Input v-model="item.scale" :maxlength="36"></Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="commodityName" label="通用商品名">
                                <!-- <Input v-model="item.commodityName"></Input>    -->
                                <Row>
                                    <Col span="17">
                                        <Select
                                            placeholder="支持下拉模糊输入搜索"
                                            v-model="item.commodityName"
                                            filterable
                                            remote
                                            :remote-method="remoteMethod"
                                            :loading="loading" class="mr60" style="width:100%;">
                                            <Option v-for="(option, index) in commonProductNameList" :value="option.label" :key="index">{{option.label}}</Option>
                                            </Select>
                                    </Col>
                                    <Col span="7">
                                        <Button type="primary" v-if="isShowAddBtn" @click="add">添加</Button>
                                        <Button type="primary" v-if="editBtn" @click="modify">补充</Button>  
                                    </Col>
                                </Row>
                                
                                    <!-- <Button type="primary" v-if="isShowAddBtn" @click="add" style="position: absolute;top: 2px;right: 0;">去添加</Button>     -->
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="productName" label="产品名称">
                                <Input v-model="item.productName" :maxlength="20"></Input>       
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="units" label="产量单位">
                                <Select v-model="item.units"  filterable style="width: 100%" class="units"  placeholder="单位">
                                    <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
                                </Select>       
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="product" label="产品产量">
                                <Input v-model="item.product" :maxlength="50"></Input>                           
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="conversion" label="折算产量">
                                <Input v-model="item.conversion" :maxlength="50"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
                <!-- </Col>
                <Col span="2" class="btn-toolbar-card">
                    &nbsp;
                    <div class="btn-toolbar">
                        <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
            </Row> -->
        </Card>
      </Form>
      <!-- 相关物种 -->
		<vui-filter 
            ref="speciFilter" 
            :cols="2" 
            :num="4" 
            :pageShow="true" 
            :total="total" 
            :pageCur="pageCur" 
            :classifyDatas="speciClassifyDatas" 
            :resultDatas="speciResultDatas" 
            :load-data="loadSpeciDatas" 
            @on-search="handleSpeciSearch" 
            @on-get-classify="handleGetSpeciClassify"
            @on-get-result="handleGetSpeciResult" 
            @on-page-change="handleSpeciPageChange" />
  </div>
</template>
<script>
	import vuiFilter from '~components/vuiFilter/filter'
    export default {
        components: {
            vuiFilter
        },
        data () {
            return {
                total:0,
                pageCur:1,
                speciClassifyDatas: [{
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
				speciResultDatas: [],
                data: [],
                ruleInline:{

                },
                index:0,
                submit:true,                
                commonProductNameList: [],
                loading: false,
                isShowAddBtn: false,
                editBtn: false,
                units: []
            }
        },
        created() {
            // 取相关物种结果
            this.loadSpeciResult('', '', [], this.pageCur, [])
            this.handleGetInit()
        },
        methods: {
            // 取下拉数据
            handleGetInit(){
                this.$api.post('/portal/shopCommdoity/findUnitStandard',{name: ''}).then(response => {
                    if (response.code == 200) {
                        this.units = response.data
                    }
                })
            },
            // 通用商品名 回显时要用到promise函数 先请求到数据才能根据value值回显
            remoteMethod (query) {
                var p = new Promise((resolve, reject) => {
                    if (query !== '') {
                    this.loading = true
                    this.$api.post('/portal/shopCommdoity/findCurrencyCommodity', {
                        name: query
                    }).then(response => {
                        console.log('response', response)
                        if (response.code === 200) {
                            this.loading = false
                            this.commonProductNameList = []
                            response.data.forEach(element => {
                                this.commonProductNameList.push({
                                label: element.commodityName,
                                value: element.id,
                                productCode: element.productCode,
                                productName: element.productName
                                })
                            })
                            resolve('success')
                        } else if (response.code === 300) {
                            this.loading = false
                            this.commonProductNameList = []
                            this.isShowAddBtn = true
                            this.editBtn = false
                            resolve('failed')
                        }
                    }).catch(error => {
                        this.$Message.error('通用商品名查询错误！')
                        resolve('failed')
                    })
                    } else {
                        this.commonProductNameList = []
                        this.isShowAddBtn = true
                        this.editBtn = false
                        resolve('failed')
                    }
                })
                return p
            },
            // 新增通用商品名
            add () {
                this.$router.push('/pro/addCommonProduct')
            },
            modify () {
                this.$router.push({
                    path: '/pro/addCommonProduct',
                    query: {
                    id: this.id
                    }
                })
            },
            getData(val){
                this.data = val
            },
             //表单验证
            handleSubmit () {
                this.submit = true
                this.$emit('on-submit',this.submit)
            },
            handleAdd () {
                this.data.unshift(
                    {
                        product_status: true,
                        species: '',
                        scale: '',
                        commodityName: '',
                        productName: '',
                        product: '',
                        conversion: '',
                        units: ''
                    }
                )
            },
            handleDel (index) {
                this.$Modal.confirm({
                    title: '是否确定删除',
                    content: '是否确认删除？',
                    onOk:()=>{
                        this.data.splice(index,1)
                    },
                    okText:'确定',
                    cancelText:'取消'
                });
            },
            //清空物种文本框
            inputClear (index) {
                this.data[index].species = ''
                this.$refs['speciFilter'].handleReset()
            },
            // 高级搜索弹窗
            handleFilterModal (name,index) {
                this.$refs[name].highFilterShow = true
                this.index = index
                this.$refs['speciFilter'].handleReset()
            },
            //物种
            loadSpeciDatas(item, callback) {
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
            handleSpeciSearch(letter, keyword, classify, result) {
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
				this.$Message.success('筛选完成！')
			},
            handleGetSpeciClassify(letter, keyword, classify, result) {
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
				this.$Message.success('筛选完成！')
			},
			handleGetSpeciResult(classify, result) {
                console.log(result)
                if (result.length) {
                    var arr = [],
                    list = []
                    result.forEach(item => arr.push(item.label))
                    result.forEach(item => list.push(item.value))
                    console.log(arr)
                    console.log(list)
                    this.data[this.index].species = arr.join(' ')
                } else {

                }
				
				// this.expertInfo2.species = arr.join(' ')				
			},
			handleSpeciPageChange(letter, keyword, classify, num, result) {
				this.pageCur = num
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
				this.$Message.success('筛选完成！')
            },
            loadSpeciResult(letter, keyword, classify, num, result) {
				if(classify.length) {
					var arr = []
					var type = ''
					classify.forEach(item => {
						arr.push(item.classId)
						if(item.value !== undefined) type = item.value
					})
				} else {
					arr = null
				}
				this.$api.post('/member/specicesClass/findSpecies', {
					keywords: keyword,
					fpinyin: letter === '全部' ? '' : letter,
					fclassifiedid: arr,
					type: type,
					pageNum: num,
					pageSize: 32
				}).then(res => {
					var data = res.data
					this.total = data.total
					if(result) {
						result.forEach(item => {
							data.list.forEach((child, index) => {
								if(child.label === item.label) {
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
        }
    }
</script>
