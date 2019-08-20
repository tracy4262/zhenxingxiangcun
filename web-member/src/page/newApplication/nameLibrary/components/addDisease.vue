<template>
  <Card class="pt30">
      <div v-if="step" style="width: 800px; margin: 0 auto;">
          <Form :model="formItem" ref="formItem" :label-width="120" label-position="right" :rules="formItemRules">
              <FormItem label="病害名称" prop="fname">
                  <Input v-model="formItem.fname" :disabled="edit" :readonly="diseaseId ? true : false" placeholder="请输入" @on-change="getAddPinyin" @on-blur="checkAddFname" />
              </FormItem>
              <FormItem label="汉语拼音">
                  <Input v-model="formItem.fpinyin" :disabled="edit" placeholder="由品种名称自动生成拼音" readonly />
              </FormItem>
              <FormItem label="危害物种" prop="speciesid">
                <vuiSpecies ref="vuiSpecies" :disabled="edit" :values="formItem.specName" @on-save="onSaveName" @on-save-id="onSaveId" :num="1"></vuiSpecies>
                  <!-- <Input v-model="formItem.specName" placeholder="请输入内容" readonly @on-focus="handleFilterModal('speciFilter')" /> -->
              </FormItem>
              <FormItem label="上传图标" prop="fimagesrc">
                  <vui-upload
                      ref="formItemFimagesrc"
                      :disabled="edit"
                      @on-getPictureList="getFormItemFimagesrc"
                      :hint="'图片大小小于2MB，最多上传 1 张'"
                      :total="1"
                      :size="[100,100]"
                  ></vui-upload>
              </FormItem>
              <FormItem label="病原学" v-if="animal">
                  <Input v-model="formItem.etiology" :disabled="edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
              <FormItem label="流行特点" v-if="animal">
                  <Input v-model="formItem.epidemiologicalfeatures" :disabled="edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
              <FormItem label="病理剖检" v-if="animal">
                  <Input v-model="formItem.fpathologycheck" :disabled="edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
              <FormItem label="诊断" v-if="animal">
                  <Input v-model="formItem.fdiagnose" :disabled="edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
              <FormItem label="防治" v-if="animal">
                  <Input v-model="formItem.fprevention" :disabled="edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
              <FormItem label="危害症状" v-if="plant">
                  <Input v-model="formItem.ffeature" :disabled="edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
              <FormItem label="发生规律" v-if="plant">
                  <Input v-model="formItem.fdiseaseregular" :disabled="edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
              <FormItem label="防治办法" v-if="plant">
                  <Input v-model="formItem.fprotectmethod" :disabled="edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." />
              </FormItem>
          </Form>
          <div class="tc mt20 mb40">
              <Button type="primary" class="mr20" @click="next" v-if="!edit">提交</Button>
              <Button type="default"  @click="complete">退出</Button>
          </div>
      </div>
      <div v-else>
          <div class="tc pt50 pb30">
              <h2>您已提交新的病害信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
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
                formItem: {
                    specName: '',
                    speciesid: '',
                    fname: '',
                    fpinyin: '',
                    fimagesrc: [],
                    // 动物
                    etiology: '',
                    epidemiologicalfeatures: '',
                    fpathologycheck: '',
                    fdiagnose: '',
                    fprevention: '',
                    // 植物
                    ffeature: '',
                    fdiseaseregular: '',
                    fprotectmethod: ''
                },
                formItemRules: {
                    speciesid: [
                        { required: true, message: '请选择危害物种', trigger: 'change' }
                    ],
                    fname: [
                        { required: true, message: '请填写病害名称', trigger: 'blur' }
                    ],
                    fimagesrc: [
                        { required: true, type: 'array', min: '1', message: '请上传图标', trigger: 'change' }
                    ]
                },
                step: true,
                animal: false,
                plant: false,
                diseaseId: '',
                edit: true
            }
        },
        created () {
            if (this.$route.query.speciesId) {
                console.log('9999')
                this.diseaseId = this.$route.query.speciesId
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
              this.$api.get('/wiki/api/wiki/getSpeciesDisease/' + this.diseaseId).then(response => {
                    console.log(response)
                    if (response.code === 200 && response.data !== undefined) {
                        let data = {
                            id: response.data.specName,
                            indexid:  response.data.indexid,
                            specName: response.data.specName,
                            speciesid: response.data.speciesid,
                            fname: response.data.fname,
                            fpinyin: response.data.fpinyin,
                            fimagesrc: response.data.fimagesrc,
                            // 动物
                            etiology: response.data.fcausediseasesubject,
                            epidemiologicalfeatures: response.data.fcommonfeature,
                            fpathologycheck:  response.data.fpathologycheck,
                            fdiagnose: response.data.fdiagnose,
                            fprevention: response.data.fprevention,
                            // 植物
                            ffeature: response.data.ffeature,
                            fdiseaseregular: response.data.fdiseaseregular,
                            fprotectmethod: response.data.fprotectmethod
                        }
                        this.formItem = data
                         this.$refs['formItemFimagesrc'].handleGive(this.formItem.fimagesrc)
                        // // 判断选择的该物种是动物还是植物
                        this.$api.post('/wiki/api/species/getSpeciesClassify', {
                            speciesid: this.formItem.speciesid
                        }).then(response => {
                            console.log('res', response)
                            if (response.code === 200) {
                                if (response.data.type === '动物') {
                                    // 显示动物病害独有的字段
                                    this.animal = true
                                    this.plant = false
                                } else if (response.data.type === '植物') {
                                    // 显示植物独有的字段
                                    this.plant = true
                                    this.animal = false
                                }
                            }
                        }).catch(error => {
                            this.$Message.error('获取物种类型出错！')
                        })
                    } 
                }).catch(error => {
                    this.$Message.error('获取病害数据出错！')
                })
            },
            // 获取物种
            onSaveName (e) {
              this.formItem.specName = e
            },
            onSaveId (e) {
              this.formItem.speciesid = e
              console.log(this.formItem)
              // 判断选择的该物种是动物还是植物
                this.$api.post('/wiki/api/species/getSpeciesClassify', {
                    speciesid: this.formItem.speciesid
                }).then(response => {
                    console.log('res', response)
                    if (response.code === 200) {
                        if (response.data.type === '动物') {
                            // 显示动物病害独有的字段
                            this.animal = true
                            this.plant = false
                            // 将植物的病害清空
                            this.formItem.ffeature = ''
                            this.formItem.fdiseaseregular = ''
                            this.formItem.fprotectmethod = ''
                        } else if (response.data.type === '植物') {
                            // 显示植物独有的字段
                            this.plant = true
                            this.animal = false
                            // 将动物的病害清空
                            this.formItem.etiology = ''
                            this.formItem.epidemiologicalfeatures = ''
                            this.formItem.fpathologycheck = ''
                            this.formItem.fdiagnose = ''
                            this.formItem.fprevention = ''
                        }
                    }
                }).catch(error => {
                    this.$Message.error('获取物种类型出错！')
                })
            },
            next () {
                if (this.handleSubmit('formItem')) {
                  if (this.formItem.indexid) { // 更新
                  let data = {
                            fid: this.formItem.fid,
                            indexid: this.formItem.indexid,
                            speciesid: this.formItem.speciesid,
                            fname: this.formItem.fname,
                            fpinyin: this.formItem.fpinyin,
                            fimagesrc: this.formItem.fimagesrc,
                            // 动物
                            fcausediseasesubject: this.formItem.etiology,
                            fcommonfeature: this.formItem.epidemiologicalfeatures,
                            fpathologycheck: this.formItem.fpathologycheck,
                            fdiagnose: this.formItem.fdiagnose,
                            fprevention: this.formItem.fprevention,
                            // 植物
                            ffeature: this.formItem.ffeature,
                            fdiseaseregular: this.formItem.fdiseaseregular,
                            fprotectmethod: this.formItem.fprotectmethod,
                            // 账户
                            fupdatorid: this.$user.loginAccount,
                            fcreatorid: this.$user.loginAccount
                        }
                    this.$api.post('/wiki/api/wiki/updateSpeciesDisease', data).then(response => {
                            if (200 === response.code) {
                                this.step = false
                                this.$Message.success('更新病害成功!')
                            } else {
                                this.$Message.success('更新病害失败!')
                            }
                        }).catch(error => {
                            this.$Message.success('更新病害失败!')
                        })
                  } else { // 新增
                    let data = {
                        speciesid : this.formItem.speciesid,
                        fcreatorid : this.$user.loginAccount,
                        fname: this.formItem.fname,
                        fpinyin : this.formItem.fpinyin,
                        fimagesrc:this.formItem.fimagesrc,
                        // 动物
                        fcausediseasesubject : this.formItem.etiology,
                        fcommonfeature : this.formItem.epidemiologicalfeatures,
                        fpathologycheck : this.formItem.fpathologycheck ,
                        fdiagnose : this.formItem.fdiagnose,
                        fprevention : this.formItem.fprevention,
                        // 植物
                        ffeature: this.formItem.ffeature,
                        fdiseaseregular: this.formItem.fdiseaseregular,
                        fprotectmethod: this.formItem.fprotectmethod,
                        auditstatus: 2
                    }
                    console.log('data', data)
                    this.$api.post('/wiki/api/wiki/saveSpeciesDisease', data).then(response => {
                        console.log("res", response)
                        if (response.code === 200) {
                            this.$Message.success('添加病害成功!')
                            this.step = false
                        } else {
                            this.$Message.error('添加病害失败!')
                        }
                    }).catch(error => {
                        this.$Message.error('添加病害失败!')
                    })
                  }
                }
        	},
        	// 退出
            complete () {
                this.$router.push('/nameLibrary/disease')
            },
             //---检测病害名是否占用-------
            checkAddFname () {
                if (this.formItem.fname !== '' && !this.diseaseId) {
                    this.$api.get('/wiki/api/wiki/existName/' + 3 + '/' + this.formItem.fname).then(response => {
                        console.log("检测物种名是否被占用:==>>", response.data, "<<==")
                        if (response.data === 1) {
                            this.$Message.error("该病害名称已被占用!")
                            this.formItem.fname = ''
                            this.formItem.fpinyin = ''
                        }
                    }).catch(error => {
                        this.$Message.error('病害名称检测失败!')
                    })
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
            getFormItemFimagesrc (e) {
                var arr = []
                e.forEach(element => {
                    if (element.response) {
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
            // 得到汉字的拼音
            getAddPinyin () {
                if (this.formItem.fname !== '') {
                    this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.formItem.fname).then(response => {
                        if (response.code === 200 && response.data) {
                            this.formItem.fpinyin = response.data
                        } else {
                            this.$Message.error('获取汉语拼音出错！')
                        }
                    }).catch(error => {
                        this.$Message.error('获取汉语拼音出错！')
                    })
                } else {
                    this.formItem.fpinyin = ''
                }
            },
        }
    }
</script>
