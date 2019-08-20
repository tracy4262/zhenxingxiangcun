<template>
  <div>
    <!-- 农业 -->
    <div style="background:#f9f9f9;" class="mb40">
      <div class="pd20" >
          <Row  :gutter="38" class="pb20">
            <Col span="8">
              <b  style="font-size:14px;">{{title}}</b>
            </Col>
            <!-- <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" v-if="!edit" @click="handleEdit">编辑</span>
            </Col> -->
          </Row>
          <Form :label-width="60" label-position="left" v-for="(item, index) in data" :ref="`data${index}`" :model="item" :rules="dataLine">
            <Row  :gutter="16" >
              <Col span="8">
                <FormItem label="物种名称">
                  <Input :disabled="!edit" v-model="item.speciName" readonly  @on-focus="handleFilterModal('speciFilter', index)"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row  :gutter="16" >
              <Col span="8">
                <FormItem label="规模" prop="scale">
                  <Row>
                    <Col span="14">
                      <Input :disabled="!edit" v-model="item.scale" :maxlength="20" >
                      </Input>
                    </Col>
                    <Col span="10" class="pl10">
                      <vuiUnit :value="item.scaleUnit" @on-get-data="onGetScaleUnit($event, index)" :disabled="!edit"></vuiUnit>
                    </Col>
                  </Row>
                  <!-- <Input :disabled="!edit" v-model="item.scale" :maxlength="20" >
                    <Select v-model="item.scaleUnit" filterable  :disabled="!edit"  slot="append" style="width: 80px" placeholder="请选择单位">
                        <Option v-for="item in units" :value="item.unit_name" :key="item.unit_name">{{ item.unit_name }}</Option>
                    </Select>
                  </Input> -->
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="产量" prop="Yield">
                  <Row>
                    <Col span="14">
                      <Input :disabled="!edit" v-model="item.Yield" :maxlength="20"  >
                      </Input>
                    </Col>
                    <Col span="10" class="pl10">
                      <vuiUnit :value="item.YieldUnit" @on-get-data="onGetYieldUnit($event, index)" :disabled="!edit"></vuiUnit>
                    </Col>
                  </Row>
                  <!-- <Input :disabled="!edit" v-model="item.Yield" :maxlength="20" >
                    <Select v-model="item.YieldUnit" filterable  :disabled="!edit"  slot="append" style="width: 80px" placeholder="请选择单位">
                        <Option v-for="item in units" :value="item.unit_name" :key="item.unit_name">{{ item.unit_name }}</Option>
                    </Select>
                  </Input> -->
                </FormItem>
              </Col>
              <Col span="6" >
                <FormItem label="产值" prop="output">
                  <Input :disabled="!edit" v-model="item.output" :maxlength="20" @on-change="handleNumAdd"> <span slot="append">万元</span></Input>
                </FormItem>
              </Col>
              <Col span="2" v-if="edit && data.length > 1" >
                <Button  @click="handleDel(item, index)" >删除</Button>
              </Col>
            </Row>
          </Form>
          <div v-if="edit" class="pl60">
            <Button type="primary" class="btn-light-primary" icon="md-add" ghost @click="handleAdd" >添加</Button>
          </div>
          <div class="tc pd20" v-if="edit">
          <Button type="primary" @click="handleSave">保存</Button>
        </div>
      </div>
        <Divider></Divider>
        <div class="pd20">
          <p class="tr t-orange subtotal">产值小计:{{total}}万元</p>
        </div>
    </div>
    <!-- 物种 -->
    <vui-filter
            ref="speciFilter"
            :cols="2"
            :num="1"
            :pageShow="true"
            :total="speciTotal"
            :pageCur="pageCur"
            :classifyDatas="speciClassifyDatas"
            :resultDatas="speciResultDatas"
            :load-data="loadSpeciDatas"
            @on-search="handleSpeciSearch"
            @on-get-classify="handleGetSpeciClassify"
            @on-get-result="handleGetSpeciResult"
            @on-page-change="handleSpeciPageChange" />
    <!-- 服务 -->
  </div>
</template>
<script>
import Divider from '~components/divider'
import vuiFilter from '~components/vuiFilter/filter'
import {numAdd} from '~utils/utils'
import {isNumber, isMoney3} from '~utils/validate'
import vuiUnit from '~components/vui-unit'
  export default {
    components: {
      Divider,
      vuiFilter,
      vuiUnit
    },
    props: {
      title: '',
      type: '',
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
    data () {
      return {
        activeIndex: 0,
        edit :false,
        speciTotal: 0,
        preview: '',
        data: [
          {
           speciName: '', scale: '',scaleUnit: '', Yield: '',YieldUnit: '', output: ''
          }
        ],
        dataLine: {
          Yield:[{validator:isMoney3,trigger: 'blur'}],
          scale:[{validator:isMoney3,trigger: 'blur'}],
          output:[{validator:isMoney3,trigger: 'blur'}],
        },
        total: 0,
        pageCur: 1,
        speciResultDatas: [],
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
        ]
      }
    },
    created() {
      // 取相关物种结果
			this.loadSpeciResult('', '', [], this.pageCur, [])
    },
    methods: {
      onGetYieldUnit($event, index){
        this.data[index].YieldUnit = $event
      },
      onGetScaleUnit($event, index){
        this.data[index].scaleUnit = $event
      },
      // 获取数据
      getData (data) {
        this.data = data
        this.edit = false
        this.handleNumAdd()
      },
      // 计算小计
      handleNumAdd () {
        this.total = 0
        let str = ''
        this.data.forEach(e => {
          this.total = numAdd(parseFloat(this.total ? this.total : 0).toFixed(2), parseFloat(e.output ? e.output : 0).toFixed(2))
          if (e.speciName) {
            str += `${e.speciName}，`
            if (e.scale) {
              str += `面积${e.scale}${e.scaleUnit}，`
            }
            if (e.Yield) {
              str += `产量达${e.Yield}${e.YieldUnit}，产值${e.output}万元;`
            }
            if (e.output) {
              str += `产值${e.output}万元;`
            }
          }
        })
        if (str) {
          this.preview = `${str.substring(0,str.length-1)},`
        }
        this.$emit('on-numAdd')
      },
      // 删除
      handleDel (item, index) {
        this.$Modal.confirm({
            title: '是否确定删除',
            onOk:()=>{
              if (item.id) {
                  this.$api.post('/member-reversion/ecoSocial/deleteBreed', {id: item.id}).then(response => {
                      if (response.code === 200) {
                          this.data.splice(index, 1)
                          this.$Message.success('删除成功')
                      }
                  })
              } else {
                  this.data.splice(index, 1)
                  this.$Message.success('删除成功')
              }
            },
            okText:'确定',
            cancelText:'取消'
        })
      },
      // 添加
      handleAdd () {
        let list = {speciName: '', scale: '',scaleUnit: '', Yield: '',YieldUnit: '', output: ''}
        this.data.push(list)
      },
      // 保存
      handleSave () {
        let flag = true
        for (let i = 0; i < this.data.length; i++) {
          this.$refs[`data${i}`][0].validate(v => {
            if (!v) {
              this.$Message.error('请核对表单信息')
              flag = v
            }
          })
        }
        if (flag) {
          let list = {
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            type: this.type,
            list: this.data
          }
          this.$api.post('/member-reversion/ecoSocial/saveBreed', list).then(response => {
              if (response.code === 200) {
                  this.$Message.success('保存成功')
                  this.edit = false
                  this.$emit('on-init', this.type)
              }
          })
        }
      },
      handleEdit () {
        this.edit = true
      },
      // 高级搜索弹窗
      handleFilterModal (name, index) {
        this.activeIndex = index
        this.$refs[name].highFilterShow = true
      },
      // 物种
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
					this.speciTotal = data.total
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
       // 相关物种
			handleSpeciSearch(letter, keyword, classify, result) {
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
				this.$Message.success('筛选完成！')
			},
			handleGetSpeciClassify(letter, keyword, classify, result) {
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
				this.$Message.success('筛选完成！')
			},
			handleGetSpeciResult(classify, result) {
				var arr = []
				this.speciesResult = []
				result.forEach(item => arr.push(item.label))
				result.forEach(item => this.speciesResult.push(item.value))
				this.data[this.activeIndex].speciName = arr.join(' ')
			},
			handleSpeciPageChange(letter, keyword, classify, num, result) {
				this.pageCur = num
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
				this.$Message.success('筛选完成！')
      },
    }
  }
</script>
