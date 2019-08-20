<template>
  <Card class="pt30">
      <div v-if="step" style="width: 800px; margin: 0 auto;">
          <Form :model="formItem" ref="formItem" :label-width="120" label-position="right" :rules="formItemRule">
              <FormItem label="虫害名称" prop="fname">
                  <Input v-model="formItem.fname" :disabled="edit" :readonly="pestId ? true : false" placeholder="请输入内容" @on-change="getAddPinyin"  @on-blur="checkAddFname" />
              </FormItem>
              <FormItem label="汉语拼音" prop="fpinyin">
                  <Input v-model="formItem.fpinyin" :disabled="edit" placeholder="由品种名称自动生成拼音" />
              </FormItem>
              <FormItem label="危害物种" prop="speciesid">
                 <vuiSpecies ref="vuiSpecies" :disabled="edit" :values="formItem.specName" @on-save="onSaveName" @on-save-id="onSaveId" :num="1"></vuiSpecies>
                  <!-- <Input v-model="formItem.specName" placeholder="请输入内容" readonly @on-focus="handleFilterModal('speciFilter')" /> -->
              </FormItem>
              <FormItem label="上传图标" prop="fimagesrc">
                  <vui-upload
                      ref="fimagesrc"
                      :disabled="edit"
                      @on-getPictureList="getFormItemFimagesrc"
                      :hint="'图片大小小于2MB，最多上传 1 张'"
                      :total="1"
                      :size="[100,100]"
                  ></vui-upload>
              </FormItem>
              <FormItem label="形态特征" prop="fmainfeatures">
                  <Input v-model="formItem.fmainfeatures" :disabled="edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
              <FormItem label="危害症状" prop="fhabit">
                  <Input v-model="formItem.fhabit" type="textarea" :disabled="edit" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
              <FormItem label="发生规律" prop="fpetsregular">
                  <Input v-model="formItem.fpetsregular" type="textarea" :disabled="edit" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
              <FormItem label="防治方法" prop="fprotectmethod">
                  <Input v-model="formItem.fprotectmethod" type="textarea" :disabled="edit" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
              <FormItem label="备注" prop="fremarks">
                  <Input v-model="formItem.fremarks" type="textarea" :disabled="edit" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
          </Form>
      <div align="center" class="mb30">
          <Button type="primary" class="mr20" @click="next" v-if="!edit">完成</Button>
          <Button type="default"  @click="complete">退出</Button>
      </div>
      </div>
      <div v-else>
          <div class="tc pt50 pb30">
              <h2>您已提交新的虫害信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
          </div>
          <div class="tc pt30 pb50">
              <Button type="primary" @click="complete">完成</Button>
          </div>
      </div>
  </Card>
</template>

<script>
    import vuiUpload from '~components/vui-upload'
    import vuiSpecies from '~components/vui-species'
    export default {
        components: {
            vuiUpload,
            vuiSpecies
        },
        data () {
            return {
                step: true,
                formItem: {
                    specName: '',
                    speciesid: '',
                    fname: '',
                    fpinyin: '',
                    fimagesrc: [],
                    fmainfeatures: '',
                    fhabit: '',
                    fpetsregular: '',
                    fprotectmethod: '',
                    fremarks: '',
                    fcreatorid: ''
                },
                formItemRule: {
                    fname: [
                        {required: true, message: '请填写虫害名称', trigger: 'blur'}
                    ],
                    speciesid: [
                        {required: true, message: '请选择危害物种', trigger: 'change'}
                    ],
                    fimagesrc: [
                        { required: true, type: 'array', min: '1', message: '请上传图标', trigger: 'change' }
                    ]
                },
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                pestId: '',
                edit: true
            }
        },
        created () {
            if (this.$route.query.speciesId) {
                console.log('9999')
                this.pestId = this.$route.query.speciesId
                this.getData()
            }
            if (this.$route.query.edit) {
                this.edit = true
            } else{
                this.edit = false
            }
        },
        methods: {
            getData () {
              this.$api.get('/wiki/api/wiki/getSpeciesPest/' + this.pestId).then(response => {
                    console.log('response.data===>>>', response.data)
                    this.formItem = response.data
                    this.$refs['fimagesrc'].handleGive(this.formItem.fimagesrc)
                }).catch(error => {
                    console.log(error)
                })
            },
            // 获取物种
            onSaveName (e) {
              this.formItem.specName = e
            },
            onSaveId (e) {
              this.formItem.speciesid = e
            },
            getFormItemFimagesrc (e) {
                var arr = []
                e.forEach(element => {
                    if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.formItem.fimagesrc = arr
                if (arr.length) {
                    this.handleSubmit('formItem')
                } else {
                    this.handleSubmit('formItem', '1')
                }
            },
            handleSubmit (name, pic) {
                let flag = false
                this.$refs[name].validate(valid => {
                    if (valid) {
                        flag = true
                    } else {
                        if (!pic) {
                            this.$Message.error('表单验证失败!')
                        }
                    }
                })
                return flag
            },
            //---检测虫害名是否占用-------
            checkAddFname () {
                if (this.formItem.fname && !this.pestId) {
                    this.$api.get('/wiki/api/wiki/existName/' + 4 + '/' + this.formItem.fname).then(response => {
                        console.log("检测物种名是否被占用:==>>", response.data, "<<==")
                        if (1 === response.data) {
                            this.$Message.error('该虫害名称已被占用！')
                            this.formItem.fname = ''
                            this.formItem.fpinyin = ''
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            // 完成
            next () {
                if (this.handleSubmit('formItem')) {
                  if (this.formItem.indexid) { // 更新
                    this.formItem.fupdatorid = this.$user.loginAccount
                    this.formItem.fcreatorid = this.$user.loginAccount
                    this.$api.post('/wiki/api/wiki/updateSpeciesPest', this.formItem).then(response => {
                        if (200 === response.code) {
                          this.$Message.success('更新虫害成功!')
                          this.step = false
                        } else {
                            this.$Message.success('更新虫害失败!')
                        }
                    }).catch(error => {
                        this.$Message.success('更新虫害失败!')
                    })
                  } else { // 新增
                    let data = {
                        fcreatorid: this.loginuserinfo.loginAccount,
                        speciesid: this.formItem.speciesid,
                        fname: this.formItem.fname,
                        fpinyin: this.formItem.fpinyin,
                        fimagesrc: this.formItem.fimagesrc,
                        fmainfeatures: this.formItem.fmainfeatures,
                        fhabit: this.formItem.fhabit,
                        fpetsregular: this.formItem.fpetsregular,
                        fprotectmethod: this.formItem.fprotectmethod,
                        fremarks: this.formItem.fremarks,
                        auditstatus: 2
                    }
                    console.log('data', data)
                    this.$api.post('/wiki/api/wiki/saveSpeciesPest', data).then(response => {
                        console.log("response", response)
                        if (response.code === 200) {
                            this.$Message.success('添加虫害成功!')
                            this.step = false
                        } else {
                            this.$Message.success('添加虫害失败!')
                        }
                    }).catch(error => {
                        this.$Message.success('添加虫害失败!')
                    })
                  }
                }
            },
            // 退出
            complete () {
                this.$router.push('/nameLibrary/pest')
            },
            // 得到汉字的拼音
            getAddPinyin () {
                if ('' != this.formItem.fname) {
                    this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.formItem.fname).then(response => {
                        this.formItem.fpinyin = ''
                        this.formItem.fpinyin = response.data
                    }).catch(error => {
                        console.log(error)
                    })
                } else {
                    this.formItem.fpinyin = ''
                }
            }
        }
    }
</script>
