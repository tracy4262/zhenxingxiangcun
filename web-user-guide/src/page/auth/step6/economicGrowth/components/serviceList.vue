<template>
  <div style="background:#f9f9f9;" class="mb40">
   <!-- 第一产业 -->
        <div class="pd20" >
           <Row  :gutter="38" class="pb20">
            <Col span="8">
              <b  style="font-size:14px;">{{title}}</b>
            </Col>
            <!-- <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" @click="handleEdit" v-if="!edit">编辑</span>
            </Col> -->
          </Row>
          <Form :label-width="75" label-position="left"  v-for="(item, index) in data" :ref="`data${index}`" :model="item" :rules="dataLine">
            <Row  :gutter="16">
              <Col span="7">
                <FormItem label="服务业名称">
                  <Input v-model="item.serviceName" :disabled="!edit" readonly  @on-focus="handleFilterModal('serviceFilter', index)"></Input>
                </FormItem>
              </Col>
              <Col span="5">
                <FormItem label="服务能力">
                  <Input v-model="item.ability" :disabled="!edit" :maxlength="20" >
                  </Input>
                </FormItem>
              </Col>
               <Col span="5">
                <FormItem label="单价" prop="price">
                  <Input v-model="item.price" :disabled="!edit" :maxlength="20" ><span slot="append">元</span></Input>
                </FormItem>
              </Col>
               <Col span="5" >
                 <FormItem label="产值" prop="output">
                   <Input v-model="item.output" :disabled="!edit" :maxlength="20"  @on-change="handleNumAdd"> <span slot="append">万元</span></Input>
                </FormItem>
              </Col>
              <Col span="2" class="tr" v-if="edit && data.length != 1">
                <Button  @click="handleDel(item, index)">删除</Button>
              </Col>
            </Row>
          </Form>
          <!-- <div class="pl60">
           <Button type="primary" class="btn-light-primary" icon="md-add" ghost @click="handleAdd">添加</Button>
          </div> -->
          <div class="pd20 tc"  v-if="edit">
            <Button type="primary" @click="handleSave" >保存</Button>
          </div>
        </div>
        <Divider></Divider>
        <div class="pd20" style="background:#f9f9f9;">
          <p class="tr t-orange subtotal">产值小计:{{total}}万元</p>
        </div>
     <!-- 相关服务 -->
		<vui-filter 
            ref="serviceFilter"  
            :num="1" 
            :simple="true" 
            :cols="2" 
            :resultDatas="serviceResultDatas" 
            @on-search="handleServiceSearch" 
            @on-get-result="handleGetServiceResult" />
  </div>
</template>
<script>
import Divider from '~components/divider'
import vuiFilter from '~components/vuiFilter/filter'
import {numAdd} from '~utils/utils'
import {isNumber, isMoney3} from '~utils/validate'
  export default {
    components: {
      Divider,
      vuiFilter
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
        edit :false,
        total: '',
        data: [
          {
            serviceName: '',
            ability: '', // 服务能力
            price: '', //单价
            output: '', // 产值
          }],
        dataLine: {
          price:[{validator:isMoney3,trigger: 'blur'}],
          output:[{validator:isMoney3,trigger: 'blur'}],
        },
        activeIndex: 0,
				serviceResultDatas: [],
      }
    },
    created() {
      // 取相关服务数据
      this.$api.post('/member/service/find', {}).then(res => {
        var data = res.data
        data.forEach((item, index) => {
          item.checked = false
        })
        this.serviceResultDatas = data
      })
    },
    methods: {
       // 获取数据
      getData (data) {
        this.data = data
        this.edit = false
        this.handleNumAdd()
      },
      // 计算小计
      handleNumAdd () {
         this.total = 0
        this.data.forEach(e => {
          this.total = numAdd(parseFloat(this.total ? this.total : 0).toFixed(2), parseFloat(e.output ? e.output : 0).toFixed(2))
        })
        this.$emit('on-numAdd')
      },
      // 删除
      handleDel (item, index) {
        this.$Modal.confirm({
            title: '是否确定删除',
            onOk:()=>{
              if (item.id) {
                  this.$api.post('/member-reversion/ecoSocial/deleteServiceProduct', {id: item.id}).then(response => {
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
        let list = {
          serviceName: '',
          ability: '', // 服务能力
          price: '', //单价
          output: '', // 产值
        }
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
          this.$api.post('/member-reversion/ecoSocial/saveServiceProduct', list).then(response => {
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
      // 服务
      handleServiceSearch(letter, keyword, classify) {
				this.$api.post(`/member/service/find`, {
					character: letter === '全部' ? '' : letter,
					keyword: keyword
				}).then(res => {
					var d = res.data
					this.serviceResultDatas = []
					if(d.length) {
						if(classify.length) {
							classify.forEach(item => {
								d.forEach(child => {
									if(child.label === item.label) {
										child.checked = true
									}
								})
							})
						} else {
							d.forEach(child => {
								child.checked = false
							})
						}
						this.serviceResultDatas = d
						this.$Message.success('筛选完成！')
					}
				})
			},
			handleGetServiceResult(classify, result) {
				var arr = []
				this.relatedServices = []
				result.forEach(item => arr.push(item.label))
				this.data[this.activeIndex].serviceName = arr.join(' ')
			}
    }
  }
</script>
