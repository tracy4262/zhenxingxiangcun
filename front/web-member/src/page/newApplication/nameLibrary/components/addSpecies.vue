<template>
  <Card class="pt30">
      <div v-if="step" style="width: 800px; margin: 0 auto;">
          <Form :model="formItem" ref="formItem" :label-width="120" label-position="right" :rules="ruleFormItem">
              <FormItem label="物种分类" prop="selectedSpe">
                  <!-- <Cascader :data="specData" @on-change="getSelectedSpe"></Cascader> -->
                  <Cascader :data="specData" :load-data="loadData" 
                        :disabled="edit"
                      style="width: 250px;"
                      :render-format="formatSelectedSpe">
                    <Input placeholder="请选择" :disabled="edit" v-model="formItem.selectedSpes" icon="ios-arrow-down" readonly />
                  </Cascader>
              </FormItem>
              <FormItem label="物种名称" prop="fname">
                  <Input v-model="formItem.fname" :disabled="edit" :readonly="speciesId ? true : false" placeholder="请输入内容" @on-change="getPinyin" @on-blur="checkFname" :maxlength="50" />
              </FormItem>
              <FormItem label="汉语拼音">
                  <Input v-model="formItem.fpinyin" :disabled="edit" readonly placeholder="由物种名称自动生成拼音" />
              </FormItem>
              <FormItem label="物种俗名">
                  <Input v-model="formItem.sucheng" :disabled="edit" :maxlength="20" />
              </FormItem>
              <FormItem label="其他分类">
                  <!-- <Cascader :data="specData" @on-change="getOtherSelectedSpe"></Cascader> -->
                  <Cascader :data="specData" :load-data="loadData" :disabled="edit"
                      style="width: 250px;"
                      :render-format="formatOtherSelectedSpe">
                    <Input placeholder="请选择" :disabled="edit" v-model="formItem.otherSelectedSpes" icon="ios-arrow-down" readonly />
                  </Cascader>
              </FormItem>
              <FormItem label="产业分类" prop="findustriaclassifiedid">
                  <Select v-model="formItem.findustriaclassifiedid"  :disabled="edit" placeholder="请选择" clearable>
                      <Option value="A01">农业</Option>
                      <Option value="A02">林业</Option>
                      <Option value="A03">畜牧业</Option>
                      <Option value="A04">水产业</Option>
                  </Select>
              </FormItem>
              <FormItem label="是否保护">
                  <Select v-model="formItem.fisprotection" clearable :disabled="edit">
                      <Option value="0">否</Option>
                      <Option value="1">一级保护</Option>
                      <Option value="2">二级保护</Option>
                      <Option value="3">地方重点保护</Option>
                  </Select>
              </FormItem>
              <FormItem label="物种图片">
                  <vui-upload
                        ref="upload"
                        :disabled="edit"
                      @on-getPictureList="getPictureList"
                      :hint="'图片大小小于2MB，最多上传 4 张'"
                      :total="9999999"
                      :size="[100,100]"
                  ></vui-upload>
              </FormItem>
              <FormItem label="主要产品">
                  <Input v-model="formItem.majorProduct" :disabled="edit" :maxlength="100" />
              </FormItem>
              <FormItem label="性状特征">
                  <Input v-model="formItem.fshapefeatureid" :disabled="edit" type="textarea"
                      :autosize="{minRows: 2,maxRows: 5}" :maxlength="500" />
              </FormItem>
              <FormItem label="备注">
                  <Input v-model="formItem.fremarks" :disabled="edit" type="textarea"
                      :autosize="{minRows: 2,maxRows: 5}" :maxlength="500" />
              </FormItem>
          </Form>
          <div class="tc mt20 mb40">
              <Button type="primary" class="mr20" @click="next" v-if="!edit">提交</Button>
              <Button type="default"  @click="complete">退出</Button>
          </div>
      </div>
      <div v-else>
          <div class="tc pt50 pb30">
              <h2>您已提交新的物种信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
          </div>
          <div class="tc pt30 pb50">
              <Button type="primary" @click="complete">完成</Button>
          </div>
      </div>
  </Card>
</template>

<script>
    import vuiUpload from '~components/vui-upload'
    export default {
        components: {
            vuiUpload
        },
        data () {
            return {
                step: true,
                formItem: {
                    selectedSpe: '',
                    selectedSpes: '',
                    fname: '',
                    fpinyin: '',
                    sucheng: '',
                    otherSelectedSpe: '',
                    otherSelectedSpes: '',
                    findustriaclassifiedid: '',
                    fimage: [],
                    majorProduct: '',
                    fshapefeatureid: '',
                    fremarks: '',
                    fisprotection: ''
                },
                ruleFormItem: {
                    selectedSpe: [
                        {required: true, min: 2, message: '请选择物种类型', trigger: 'change'}
                    ],
                    findustriaclassifiedid: [
                        {required: true, message: '请选择产业分类', trigger: 'change'}
                    ],
                    fname: [
                        {required: true, message: '请填写物种名称', trigger: 'blur'}
                    ]
                },
                specData: [
                    {
                        value: '0',
                        label: '动物',
                        loading: false,
                        children: []
                    },
                    {
                        value: '1',
                        label: '植物',
                        loading: false,
                        children: []
                    }
                ],
                speciesId: '',
                edit: true
            }
        },
        created () {
            console.log(this.$route.query.speciesId)
            if (this.$route.query.speciesId) {
                console.log('9999')
                this.speciesId = this.$route.query.speciesId
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
                this.$api.get('/wiki/api/species/getSpecies/' + this.speciesId).then(response => {
                    console.log('edit res', response)
                    if (response.code === 200) {
                        let form = {
                            id: response.data.speciesid,
                            selectedSpe: response.data.fclassifiedidInfo ? response.data.fclassifiedidInfo.id : '',
                            selectedSpes: response.data.fclassifiedidInfo ? response.data.fclassifiedidInfo.val : '',
                            fname: response.data.fname,
                            fpinyin: response.data.fpinyin,
                            sucheng: response.data.speciesVulgo,
                            otherSelectedSpe: response.data.otherClassifyInfo ? response.data.otherClassifyInfo.id : '',
                            otherSelectedSpes: response.data.otherClassifyInfo ? response.data.otherClassifyInfo.val : '',
                            findustriaclassifiedid: response.data.findustriaclassifiedid,
                            fimage: response.data.ficon,
                            majorProduct: response.data.majorProduct,
                            fshapefeatureid: response.data.fshapefeatureid,
                            fremarks: response.data.fremarks,
                            fisprotection: response.data.fisprotection,
                            speciesid: response.data.speciesid,
                        }
                        this.formItem = form
                        console.log('edit res', this.formItem)
                        this.$refs['upload'].handleGive(this.formItem.fimage)
                    }
                }).catch(error=> {
                    this.$Message.error(error)
                })
            },
            // 获取照片
            getPictureList (e) {
                var arr = []
                e.forEach(element => {
                    if (element.response) {
                        arr.push(element.response.data.picName)
                    }
                })
                this.formItem.fimage = arr
            },
            loadData (item, callback) {
              item.loading = true
              this.$api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
                item.loading = false
                var d = res.data
                d.forEach(child => {
                  child.checked = false
                  child.label = child.className
                  child.value = child.indexid
                })
                item.children = d
                callback()
              })
            },
            formatSelectedSpe (labels, selectedData) {
                console.log('selectedData', selectedData)
                if (selectedData.length == 2) {
                    this.formItem.selectedSpe = selectedData[1].classId
                    this.formItem.selectedSpes = labels.join('/')
                }
                console.log(this.formItem)
            },
            formatOtherSelectedSpe (labels, selectedData) {
                console.log(selectedData)
                if (selectedData.length == 2) {
                    this.formItem.otherSelectedSpe = selectedData[1].classId
                    this.formItem.otherSelectedSpes = labels.join('/')
                }
            },
            // 得到汉字的拼音
            getPinyin () {
                if (this.formItem.fname !== '') {
                    this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.formItem.fname).then(response => {
                        this.formItem.fpinyin = ''
                        this.formItem.fpinyin = response.data
                    }).catch(error => {
                        this.$Message.error('获取拼音名称出错！')
                    })
                } else {
                    this.formItem.fpinyin = ''
                }

            },
            // 失去焦点时检测物种名是否被使用
            checkFname () {
                if (this.formItem.fname !== '' && !this.speciesId) {
                    this.$api.get('/wiki/api/species/getIsNameExist/' + this.formItem.fname).then(response => {
                        if (response.data === 1) {
                            this.formItem.fname = ''
                            this.formItem.fpinyin = ''
                            this.$Message.error('此物种已存在！')
                        }
                    }).catch(error => {
                        this.$Message.error('查询物种名称出错！')
                    })
                }
            },
            next () {
                this.$refs['formItem'].validate((valid) => {
                    if (valid) {
                        if (this.speciesId) {
                             //1.提交第一步数据
                            let data = {
                                fclassifiedid: this.formItem.selectedSpe,
                                fname: this.formItem.fname,
                                fpinyin: this.formItem.fpinyin,
                                speciesVulgo: this.formItem.sucheng,
                                otherClassify: this.formItem.otherSelectedSpe,
                                findustriaclassifiedid: this.formItem.findustriaclassifiedid,
                                fisprotection: this.formItem.fisprotection,
                                ficon: this.formItem.fimage,
                                majorProduct: this.formItem.majorProduct,
                                fshapefeatureid: this.formItem.fshapefeatureid,
                                fremarks: this.formItem.fremarks,
                                // auditstatus: '2',
                                fupdatorid: this.$user.loginAccount,
                                fcreatorid: this.$user.loginAccount,
                                speciesid: this.formItem.id
                            }
                            console.log('data', data)
                            this.$api.post('/wiki/api/species/updateSpecies', data).then(response => {
                                if (response.code === 200) {
                                    this.step = false
                                    this.$Message.success('更新物种成功')
                                } else {
                                    this.$Message.success('更新物种失败!')
                                }
                            }).catch(error => {
                                this.$Message.error(error)
                            })
                        } else {
                            let data = {
                                fclassifiedid: this.formItem.selectedSpe,
                                fname: this.formItem.fname,
                                fpinyin: this.formItem.fpinyin,
                                speciesVulgo: this.formItem.sucheng,
                                otherClassify: this.formItem.otherSelectedSpe,
                                findustriaclassifiedid: this.formItem.findustriaclassifiedid,
                                fisprotection: this.formItem.fisprotection,
                                ficon: this.formItem.fimage,
                                majorProduct: this.formItem.majorProduct,
                                fshapefeatureid: this.formItem.fshapefeatureid,
                                fremarks: this.formItem.fremarks,
                                auditstatus: '2',
                                fcreatorid: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                            }
                            console.log(data)
                            console.log('data', data)
                            this.$api.post('/wiki/api/species/saveSpecies/', data).then(response => {
                                console.log('response', response)
                                if (response.code === 200) {
                                    this.step = false
                                } else {
                                    this.$Message.error('新增物种失败！')
                                }
                            }).catch(error => {
                                this.$Message.error('新增物种失败！')
                            })
                        }
                    }
                })
            },
            complete () {
                this.$router.push('/nameLibrary/species')
            }
        }
    }
</script>
