<template>
  <div >
    <div class="certification pd20 mb20 pt30" v-for="(item, index) in data">
      <Form :label-width="80" label-position="left" :ref="`data${index}`" :model="item" :rules="dataLine">
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
            <FormItem label="会员类别" prop="member_class">
              <Cascader
                v-model="item.member_class"
                :data="memberClass"
                change-on-select
                @on-change="onChange"
                :disabled="!item.isEdit"
                @on-visible-change="handleChange(index)">
              </Cascader>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="会员全称" prop="member_name">
              <Input v-model="item.member_name" :disabled="!item.isEdit" :maxlength="16"
              @on-change="handleGetPinyin(item, index, 'name')"></Input>
            </FormItem>
          </Col>
           <Col span="8">
            <FormItem label="全称拼音" prop="member_name_pinyin">
              <Input v-model="item.member_name_pinyin" :disabled="!item.isEdit" readonly></Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="8">
            <FormItem label="名称简写" prop="member_abbreviation">
              <Input v-model="item.member_abbreviation" :maxlength="16" :disabled="!item.isEdit"
              @on-change="handleGetPinyin(item, index, 'abbreviation')"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="简称拼音" prop="abbreviation_pinyin">
              <Input v-model="item.abbreviation_pinyin" readonly :disabled="!item.isEdit"></Input>
            </FormItem>
          </Col>
          <Col span="8">
           <!-- v-model="item.aptitude_name"  -->
            <FormItem label="资质名称" prop="aptitude_name">
              <Select v-model="item.aptitude_name" style="width:100%"  :disabled="!item.isEdit" @on-change="aptitudeNameChange(index)">
                <Option v-for="item in aptitudeName" :value="item.label" :key="item.label">{{ item.label }}</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="8">
            <FormItem label="资质编号" prop="aptitude_number">
              <Input v-model="item.aptitude_number" :maxlength="20"  :disabled="!item.isEdit"></Input>
            </FormItem>
          </Col>
        </Row>
        <FormItem label="资质照片" prop="aptitude_image">
              <vui-upload
                  :ref="`upload${index}`"
                  @on-getPictureList="getPictureList($event, index)"
                  :total="total"
                  :hint="'支持拓展名称：png jpg'"
                  :size="[80,80]"
                  :disabled="!item.isEdit"
                  ></vui-upload>
        </FormItem>
        <FormItem label="说明" prop="remark">
          <Input type="textarea" v-model="item.remark"  :disabled="!item.isEdit" :autosize="{minRows: 2,maxRows: 5}" :maxlength="200"></Input>
        </FormItem>
      </Form>
      <div class="tc pt20" v-if="item.isEdit">
        <Button type="primary" @click="handleSave(item, index)">保存并下一步</Button>
      </div>
    </div>
  </div>
</template>
<script>
import vuiUpload from '~components/vui-upload'
  export default {
    components: {
      vuiUpload
    },
    props: {
      account: String
    },
    data () {
      const validateUpload = (rule, value, callback) => {
        if(this.notarizationCertificate && this.notarizationCertificate.length === 0) {
          callback(new Error('请选择会员类别'))
        } else {
          callback()
        }
      }
      return {
        activeIndex: 0,
        aptitudeName: [
          {
            label: '身份证', total: 2
          },
          {
            label: '户口本', total: 3
          },
          {
            label: '机关授权书', total: 1
          },
          {
            label: '事业单位法人证书', total: 1
          },
          {
            label: '企业营业执照', total: 1
          },
          {
            label: '社会团体法人登记证书', total: 1
          },
          {
            label: '其他法人资格证书', total: 9999999
          }
        ],
        memberClass: [],
        total: 1,
        dataLine: {
          member_class: [
            { required: true, message: '请选择会员类别', trigger: 'change', validator: validateUpload}
          ],
          member_name: [
            { required: true, message: '请输入会员全称', trigger: 'blur' }
          ],
          member_name_pinyin: [
            { required: true, message: '请输入全称拼音', trigger: 'change' }
          ],
          member_abbreviation: [
            { required: true, message: '请输入名称简写', trigger: 'blur' }
          ],
          abbreviation_pinyin: [
            { required: true, message: '请输入简称拼音', trigger: 'change' }
          ],
          aptitude_name: [
            { required: true, message: '请选择资质名称', trigger: 'change' }
          ],
          aptitude_number: [
            { required: true, message: '请输入资质编号', trigger: 'blur' }
          ],
          aptitude_image: [
            { required: true, type: 'array', min: 1, message: '请上传资质照片', trigger: 'change' },
          ]
        },
        data: [{
          user_id: this.account,
          status: true,
          member_class: [],
          member_name: '',
          member_name_pinyin: '',
          member_abbreviation: '',
          abbreviation_pinyin: '',
          aptitude_name: '',
          aptitude_number: '',
          aptitude_image: [],
          remark: '',
          isEdit: true
        }],
        item: {},
        // templateId: '',
        // stepId: '',
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
      // 获取会员类型数据
      this.$api.post('/member-reversion/realCertification/getMemberType', {pid: '0'}).then(response => {
        console.log(response)
        if (response.code == 200) {
          this.memberClass = response.data
        }
      })
      this.handleInit('init')
    },
    mounted() {
      this.dataLine.aptitude_image[1] = { required: true, type: 'array', max: this.total, message: `最多只能上传${this.total}张`, trigger: 'change' }
    },
    methods: {
      aptitudeNameChange (index) {
        this.aptitudeName.forEach(e => {
          if (this.data[index].aptitude_name === e.label) {
            this.total = e.total
          }
        })
        this.dataLine.aptitude_image[1] = { required: true, type: 'array', max: this.total, message: `最多只能上传${this.total}张`, trigger: 'change' }
      },
      // 获取拼音
      handleGetPinyin (item, index, type) {
        let data = ''
        if (type === 'abbreviation') { //简称
          data = item.member_abbreviation
        } else if (type === 'name') {
          data = item.member_name
        }
        if (data) {
          this.$api.get('/wiki/api/species/getSpeciesPinYin/' + data).then(response => {
            if (type === 'abbreviation') { //简称
              item.abbreviation_pinyin = response.data
            } else if (type === 'name') {
              item.member_name_pinyin = response.data
            }
          })
        } else {
          if (type === 'abbreviation') { //简称
            item.abbreviation_pinyin = ''
          } else if (type === 'name') {
            item.member_name_pinyin = ''
          }
        }
      },
      // 初始化获取数据
      handleInit (e) {
        this.$api.post('/member-reversion/user/realCertification/findMemberAptitude', {
          user_id: this.account,
          isProxy: 1
        }).then(response => {
          console.log('init response', response)
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
                  data[i].isEdit = false
                  console.log(data[i])
                  // data[i].memberClass = []
                  // data[i].memberClass = data[i].member_class.split('/')
              }
            }

            this.data = data
            this.$nextTick(e => {
              for(var i = 0 ;i < data.length ; i++){
                  var list = this.data[i].aptitude_image
                  this.$refs[`upload${i}`][0].handleGive(list)
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
            content: '是否确认删除？',
            onOk:()=>{
              if (item.member_aptitude_real_info_id) {
                this.$api.post('/member-reversion/user/realCertification/deleteMemberAptitude', {
                  member_aptitude_real_info_id: item.member_aptitude_real_info_id
                }).then(response => {
                  console.log('del response', response)
                  if (response.code === 200) {
                    this.data.splice(index, 1)
                    this.$Message.success('删除成功')
                  } else if (response.code === 301) {
                    this.$Message.error('此会员已有关联数据，请删除关联数据后再操作！')
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
      // 上传资质照片
      getPictureList ($event, e) {
        var arr = []
        $event.forEach(element => {
              if(element.response){
                arr.push(element.response.data.picName)
            }
        });
        this.data[e].aptitude_image = arr
        this.$refs[`data${e}`][0].validate()
      },
      // 点击保存
      handleSave (item, index) {
        if (!this.saveisloading) {
          this.saveisloading = true
          let url = ''
          if (item.member_aptitude_real_info_id) { // 更新
            url = '/member-reversion/user/realCertification/updateMemberAptitude'
          } else {
            url = '/member-reversion/user/realCertification/saveMemberAptitude'
          }
          item.user_id = this.account
          item.isProxy = 1
          // item.templateId = this.templateId
          // item.loginStep ={
          //   id: this.stepId ? this.stepId : 0,
          //   account: this.$user.loginAccount,
          //   templateId: this.templateId,
          //   step: 6
          // }
          this.$refs[`data${index}`][0].validate(v => {
            if (v) {
              this.$api.post(url, {
                data: item,
                isProxy: 1
              }).then(response => {
                this.saveisloading = false
                if (response.code === 200) {
                  item.isEdit = false
                  this.$Message.success('保存成功')
                  this.$emit('next')
                  // this.handleInit()
                }
              })
            } else {
              this.saveisloading = false
              this.$Message.error('请核对表单信息')
            }
          })
        }
      },
      handleChange (index) {
        this.activeIndex = index
      },
      onChange (value, selectedData) {
        // let arr = []
        // selectedData.forEach( e => {
        //   arr.push(e.label)
        // })
        // this.data[this.activeIndex].member_class = arr.join('/')
        console.log(this.data[this.activeIndex])
      },
      // 点击添加
      handleAdd () {
        let list = {
          user_id: this.account,
          status: true,
          member_class: [],
          member_name: '',
          mamber_name_pinyin: '',
          member_abbreviation: '',
          abbreviation_pinyin: '',
          aptitude_name: '',
          aptitude_number: '',
          aptitude_image: '',
          remark: '',
          isEdit: true
        }
        this.data.push(list)
      }
    }
  }
</script>
<style>
.certification{
  background-color: #F9F9F9;
}
</style>

