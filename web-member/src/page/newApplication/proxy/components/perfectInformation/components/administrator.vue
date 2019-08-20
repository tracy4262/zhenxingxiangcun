<template>
  <div class="identity">
    <div class="certification pd20 mb20 pt30" v-for="(item, index) in data">
      <Form :label-width="80" label-position="left" :ref="`identity${index}`" :model="item" :rules="dataLine">
        <Row :gutter="38">
          <Col span="8">
            <FormItem label="权限">
              <Switch class="ml20"  size="large" v-model="item.status" :disabled="!item.isEdit">
                  <span slot="open">公开</span>
                  <span slot="close">隐藏</span>
                </Switch>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="8">
            <FormItem label="会员全称" prop="member_name">
              <Select v-model="item.member_name_id" style="width:100%" :disabled="!item.isEdit" @on-change="selectChange(index)">
                <Option v-for="item in selectData" :value="item.member_identity_info_id" :key="item.member_identity_info_id">{{ item.member_name }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="名称简写" prop="member_abbreviation">
              <Select v-model="item.member_name_id" style="width:100%" :disabled="!item.isEdit" @on-change="selectChange(index)">
                <Option v-for="item in selectData" :value="item.member_identity_info_id" :key="item.member_identity_info_id">{{ item.member_abbreviation }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="网站管理员姓名" prop="name">
              <Input v-model="item.name" :disabled="!item.isEdit" :maxlength="20"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="8">
            <FormItem label="性别" prop="sex">
              <Select v-model="item.sex" style="width:100%" :disabled="!item.isEdit" >
                <Option v-for="item in sexs" :value="item.value" :key="item.value">{{ item.value }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="身份证号" prop="card">
              <Input v-model="item.card" :maxlength="18" :disabled="!item.isEdit"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="手机" prop="phone">
              <Input v-model="item.phone" :maxlength="11" :disabled="!item.isEdit" ></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="8">
            <FormItem label="职务" prop="job">
              <Input v-model="item.job" :disabled="!item.isEdit" @on-focus="handleFilterModal('professionFilter', index)" />
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="职称" prop="job_title">
              <Input v-model="item.job_title" :disabled="!item.isEdit"  @on-focus="handleFilterModal('zhichengFilter', index)" />
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="邮箱" prop="email">
              <Input v-model="item.email" :maxlength="30" :disabled="!item.isEdit" />
            </FormItem>
          </Col>
        </Row>
         <Row :gutter="38">
          <Col span="24">
            <FormItem label="职责" prop="duty">
              <Input v-model="item.duty" :disabled="!item.isEdit" :maxlength="200"/>
            </FormItem>
          </Col>
        </Row>
         <Row :gutter="38">
          <Col span="12">
            <FormItem label="身份证照片(非公开)" prop="card_image">
              <vui-upload
                  :ref="`card${index}`"
                  @on-getPictureList="cardPictureList($event, index)"
                  :total="2"
                  :hint="'支持拓展名称：png jpg'"
                  :size="[80,80]"
                  :disabled="!item.isEdit"
                  ></vui-upload>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="授权证书（非公开）" prop="empower_image">
              <!-- empower_image -->
              <vui-upload
                  :ref="`empower${index}`"
                  @on-getPictureList="empowerPictureList($event, index)"
                  :total="9999999"
                  :hint="'支持拓展名称：png jpg'"
                  :size="[80,80]"
                  :disabled="!item.isEdit"
                  ></vui-upload>
            </FormItem>
          </Col>
        </Row>
        
      </Form>
      <div class="tc pt20" v-if="item.isEdit">
        <Button @click="last">返回上一步</Button>
        <Button type="primary" @click="handleSave(item, index)">保存</Button>
      </div>
    </div>
     <!-- 职业 -->
		<vui-filter
				ref="professionFilter"
				:num="1"
				:simple="true"
				:pageShow="true"
				:total="total2"
				:pageCur="pageCur2"
				:resultDatas="professionResultDatas"
				@on-search="handleProfessionSearch"
				@on-page-change="handleProfessionPageChange"
				@on-get-result="handleGetProfessionResult"/>
		<!-- 职称 -->
		<vui-filter
				ref="zhichengFilter"
				:num="1"
				:simple="true"
				:pageShow="true"
				:total="total3"
				:pageCur="pageCur3"
				:resultDatas="zhichengResultDatas"
				@on-search="handleZhichengSearch"
				@on-page-change="handleZhichengPageChange"
				@on-get-result="handleGetZhichengResult"/>
  </div>
</template>
<script>
import vuiUpload from '~components/vui-upload'
import {isPhone2, isIdCard2, isEmail2} from '~utils/validate'
	import vuiFilter from "~components/vuiFilter/filter"
  export default {
    components: {
      vuiUpload,
      vuiFilter
    },
    props: {
      account: String
    },
    data () {
      return {
        data: [{
          user_id: this.account,
          status: true,
          member_name: '',
          member_abbreviation: '',
          sex: '',
          name: '',
          phone: '',
          card: '',
          job: '',
          job_title: '',
          email: '',
          duty: '',
          card_image: [],
          empower_image: [],
          isEdit: true,
          member_name_id: ''
        }],
        dataLine: {
           member_name: [
            { required: true, message: '请选择会员全称', trigger: 'change' }
          ],
          member_abbreviation: [
            { required: true, message: '请选择名称简写', trigger: 'change' }
          ],
          name: [
            { required: true, message: '请输入网站管理员姓名', trigger: 'blur' }
          ],
          sex: [
            { required: true, message: '请选择性别', trigger: 'change' }
          ],
          card: [
             {required: true, message: '请输入身份证号', trigger: 'blur'},
             {validator: isIdCard2}
          ],
          phone: [
             {required: true, message: '请输入手机号码', trigger: 'blur'},
             {validator: isPhone2}
          ],
          email:[
            {validator: isEmail2},
          ],
          card_image: [
            { required: true, type: 'array', min: 2, message: '请上身份证照片（正反面）', trigger: 'change' }
          ],
          empower_image: [
            { required: true, type: 'array', min: 1, message: '请上授权证书', trigger: 'change' }
          ]
        },
        total3: 0,
        pageCur3: 1,
        zhichengResultDatas:[],
        
        total2: 0,
        pageCur2: 1,
        professionResultDatas:[],
        activeIndex: 0,
        sexs: [
          {lable: '男', value: '男'},
          {lable: '女', value: '女'}
        ],
        selectData: [],
        templateId: '',
        stepId: '',
        saveisloading: false
      }
    },
    created() {
      // this.templateId = this.$route.query.templateId
      // this.account = this.$user.loginAccount
      // 查询模板 步骤
      // this.$api.post('/member-reversion/realStep/findStep', {
      //       account: this.$user.loginAccount,
      //       templateId: this.templateId
      //   }).then(response => {
      //       if (response.code === 200) {
      //           // reload 存在， 说明是点击模板后查询的 如果 response.data 存在 那么则需要修改url
      //           if (response.data) {
      //               this.stepId = response.data.id
      //           }
      //       }
      //   })
      this.handleSelectData()
      this.handleInit('init')
      // 职称数据
      this.$api.post('/member/system-dict/getSystemDict', {
          typeName:'职称',
          pageNum: 1,
          pageSize: 32
      }).then(res => {
          var data = res.data.list
          this.total3 = res.data.total
          this.zhichengResultDatas = data
      })
      // 职业数据
      this.$api.post('/member/system-dict/getSystemDict', {
          typeName:'职业',
          pageNum: 1,
          pageSize: 32
      }).then(res => {
          var data = res.data.list
          this.total2 = res.data.total
          this.professionResultDatas = data
      })
    },
    methods: {
      // 数据改变
      selectChange (index) {
        this.selectData.forEach(e => {
          if (e.member_identity_info_id === this.data[index].member_name_id) {
            this.data[index].member_abbreviation = e.member_abbreviation
            this.data[index].member_name = e.member_name
          }
        })
      },
       // 查询下拉列表数据
      handleSelectData () {
        this.$api.post('/member-reversion/user/realCertification/findMemberAdministratorOfByIdentity', {
          user_id: this.account,
          isProxy: 1
        }).then(response => {
          if (response.code === 200) {
             this.selectData = response.data
          }
        })
      },
      handleInit (e) {
        this.$api.post('/member-reversion/user/realCertification/findMemberAdministrator', {
          user_id: this.account,
          isProxy: 1
        }).then(response => {
          if (response.code === 200) {
            let data = []
            if (response.data.length) {
              data = response.data
            } else {
              data = this.data
            }
            if (data.length == 1 && e) {
              data[0].isEdit = true
            } else {
              for(var i = 0 ;i < data.length ; i++){
                  data[i].isEdit = true
              }
            }
            this.data = data
            this.$nextTick(e => {
              for(var i = 0 ;i < data.length ; i++){
                  var card = this.data[i].card_image
                  var empower = this.data[i].empower_image
                  this.$refs[`card${i}`][0].handleGive(card)
                  this.$refs[`empower${i}`][0].handleGive(empower)
              }
            })
          }
        })
      },
      // 点击编辑
      handleEdit (item, index) {
        item.isEdit = true
      },
      // 点击删除
      handleDel (item, index) {
        this.$Modal.confirm({
            title: '是否确定删除',
            // content: '是否确认删除？',
            onOk:()=>{
              if (item.member_administrator_info_id) {
                this.$api.post('/member-reversion/user/realCertification/deleteMemberAdministrator',{member_administrator_info_id: item.member_administrator_info_id}).then(response => {
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
        });
      },
      // 上传身份证照片
      cardPictureList ($event, e) {
        this.data[e].card_image = []
        var arr = []
        $event.forEach(element => {
              if(element.response){
                arr.push(element.response.data.picName)
            }
        });
        this.data[e].card_image = arr
        this.$refs[`identity${e}`][0].validate()
      },
       // 上传授权证书照片
      empowerPictureList ($event, e) {
        var arr = []
        $event.forEach(element => {
              if(element.response){
                arr.push(element.response.data.picName)
            }
        })
        this.data[e].empower_image = arr
        this.$refs[`identity${e}`][0].validate()
      },
      // 点击保存
      handleSave (item, index) {
        if (!this.saveisloading) {
          this.saveisloading = true
          let url = ''
          if (item.member_administrator_info_id) { // 更新
            url = '/member-reversion/user/realCertification/updateMemberAdministrator'
          } else { // 保存
            url = '/member-reversion/user/realCertification/saveMemberAdministrator'
          }
          item.user_id = this.account
          // item.templateId = this.templateId
          // item.loginStep ={
          //   id: this.stepId ? this.stepId : 0,
          //   account: this.$user.loginAccount,
          //   templateId: this.templateId,
          //   step: 6.4
          // }
          item.isProxy = 1
          this.$refs[`identity${index}`][0].validate(v => {
            if (v) {
              this.$api.post(url, {
                data: item,
                isProxy: 1
              }).then(response => {
                this.saveisloading = false
                if (response.code === 200) {
                  // item.isEdit = false
                  this.$Message.success('保存成功')
                  this.handleInit()
                }
              })
            } else {
              this.saveisloading = false
              this.$Message.error('请核对表单信息')
              
            }
          })
        }
      },
      last () {
        this.$emit('last')
      },
      // 点击添加
      handleAdd () {
        let list = {
          user_id: this.account,
          status: true,
          member_name: '',
          member_abbreviation: '',
          sex: '',
          name: '',
          phone: '',
          card: '',
          job: '',
          job_title: '',
          duty: '',
          card_image: [],
          empower_image: [],
          isEdit: true,
          member_name_id: ''
        }
        this.data.push(list)
      },
      //组件弹窗
      handleFilterModal(name, index) {
        this.$refs[name].highFilterShow = true
        this.activeIndex = index
      },
      // 职业搜索
      handleProfessionSearch (letter, keyword, result) {
          this.$api.post(`/member/system-dict/getSystemDict`, {
              dictName : keyword,
              typeName : '职业',
              character: letter === '全部' ? '' : letter,
              pageNum: 1,
              pageSize: 32
          }).then(res => {
              var d = res.data.list
              this.total2 = res.data.total
              this.pageCur2 = 1
              this.professionResultDatas = []
              if (!d.length) return
              if (result.length) {
                  result.forEach(item => {
                      d.forEach(child => {
                          if (child.label === item.label) {
                              child.checked = true
                              child.label = child.label
                              child.value = child.value
                          }
                      })
                  })
              } else {
                  d.forEach(child => {
                      child.checked = false
                      child.label = child.label
                      child.value = child.value
                  })
              }
              this.professionResultDatas = d
          })
      },
      // 职业分页
      handleProfessionPageChange (letter, keyword, classify, num, result) {
          this.pageCur2 = num
          this.$api.post('/member/system-dict/getSystemDict', {
              typeName:'职业',
              character: letter === '全部' ? '' : letter,
              dictName: keyword,
              pageNum: num,
              pageSize: 32
          }).then(res => {
              var data = res.data.list
              this.total2 = res.data.total
              this.professionResultDatas = data
          })
          this.$Message.success('筛选完成！')
      },
      handleGetProfessionResult (classify, result) {
          var arr = []
          result.forEach(item => arr.push(item.label))
           this.data[this.activeIndex].job = arr.join(' ')
          // this.industryDatas = result.map(function(item){
          //     return item.value
          // })
      },
      // 职称搜索
      handleZhichengSearch (letter, keyword, result) {
          this.$api.post(`/member/system-dict/getSystemDict`, {
              dictName : keyword,
              typeName : '职称',
              character: letter === '全部' ? '' : letter,
              pageNum: 1,
              pageSize: 32
          }).then(res => {
              var d = res.data.list
              this.total3 = res.data.total
              this.pageCur3 = 1
              this.zhichengResultDatas = []
              if (!d.length) return
              if (result.length) {
                  result.forEach(item => {
                      d.forEach(child => {
                          if (child.label === item.label) {
                              child.checked = true
                              child.label = child.label
                              child.value = child.value
                          }
                      })
                  })
              } else {
                  d.forEach(child => {
                      child.checked = false
                      child.label = child.label
                      child.value = child.value
                  })
              }
              this.zhichengResultDatas = d
          })
      },
      handleZhichengPageChange (letter, keyword, classify, num, result) {
          this.pageCur3 = num
          this.$api.post('/member/system-dict/getSystemDict', {
              typeName:'职称',
              character: letter === '全部' ? '' : letter,
              dictName: keyword,
              pageNum: num,
              pageSize: 32
          }).then(res => {
              var data = res.data.list
              this.total3 = res.data.total
              this.zhichengResultDatas = data
          })
          this.$Message.success('筛选完成！')
      },
      handleGetZhichengResult (classify, result) {
          var arr = []
          result.forEach(item => arr.push(item.label))
          this.data[this.activeIndex].job_title = arr.join(' ')

          // this.industryDatas = result.map(function(item){
          //     return item.value
          // })
      },
    }
  }
</script>
<style lang="scss">
.certification{
  background-color: #F9F9F9;
}
.new-auth{
  .auth-btn-toolbar{
    color: rgba(0, 0, 0, 0.43) ;
    cursor: pointer;
    &:hover{
      color:#00C587;
    }
  }
  .identity{
    .ivu-form-item-label{
      line-height: 20px;
    }
  }
}
</style>

