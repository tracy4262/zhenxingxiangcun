<template>
    <Card class="pt30">
        <div v-if="step" style="width: 500px; margin: 0 auto;">
            <Form :model="formItem" :label-width="80" label-position="right" ref="formItem" :rules="formItemRules">
                <FormItem label="类别" prop="type">
                   <Select v-model="formItem.type" :disabled="edit" >
                        <Option v-for="(item, index) in types" :value="item.value" :key="index">{{ item.label }}</Option>
                    </Select>
                </FormItem>
                <FormItem label="单位名称" prop="name">
                    <Input 
                      v-model="formItem.name"  
                      :readonly="id ? true : false" 
                      :disabled="edit" 
                      :maxlength="10"
                      placeholder="请输入内容" 
                      @on-change="getAddPinyin"
                      @on-blur="checkAddFname" />
                </FormItem>
                <FormItem label="单位符号" prop="symbol">
                    <Input v-model="formItem.symbol" :disabled="edit"/>
                </FormItem>
                <FormItem label="说明" prop="explain">
                    <Input v-model="formItem.explain" :disabled="edit" :maxlength="200" placeholder="请输入内容" />
                </FormItem>
        </Form>
        <div align="center" class="mb30">
            <Button type="primary" class="mr20" @click="next()" v-if="!edit">完成</Button>
            <Button type="default" @click="complete">退出</Button>
        </div>
        </div>
        <div v-else>
            <div class="tc pt50 pb30">
                <h2>您已提交新的单位信息，审核工作将在<strong>三个工作日</strong>内完成，请耐心等待</h2>
            </div>
            <div class="tc pt30 pb50">
                <Button type="primary" @click="complete">完成</Button>
            </div>
        </div>
    </Card>
</template>

<script>
    export default {
        data () {
            return {
                step: true,
                formItem: {
                  type: '',
                  name: '',
                  symbol: '',
                  explain: '',
                  fpinyin: ''
                },
                //新增表单验证
                formItemRules: {
                    type: [
                      {required: true, message: '请选择单位类别', trigger: 'change'}
                    ],
                    name: [
                      {required: true, message: '请填单位名称', trigger: 'blur'}
                    ],
                    symbol: [
                      {required: true, message: '请填单位符号', trigger: 'blur'}
                    ]
                },
                id: '',
                edit: true,
                types:[]
            }
        },
        created () {
            if (this.$route.query.id) {
                console.log('9999')
                this.id = this.$route.query.id
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
              // this.$api.get('/wiki/api/wiki/getSpeciesVarietey/' + this.varietyId).then(response => {
              //       response.data.fistransgene = response.data.fistransgene+ ''
              //       this.formItem = response.data
              //       this.$refs['formItemFicon'].handleGive(this.formItem.ficon)
              //   }).catch(error => {
              //       console.log(error)
              //   })
            },
            // 添加时获得拼音
            getAddPinyin () {
                if ('' != this.formItem.fname) {
                    this.$api.get('/wiki/api/species/getSpeciesPinYin/' + this.formItem.fname).then(response => {
                        this.formItem.fpinyin = ''
                        this.formItem.fpinyin = response.data
                    }).catch(function (error) {
                        console.log(error)
                    })
                } else {
                    this.formItem.fpinyin = ''
                }
            },
            next () {
                if (this.handleSubmit('formItem')) {
                    if (this.varietyId) { // 更新
                    } else { // 新增
                     
                    }
                }
            },
            complete () {
                this.$router.push('/nameLibrary/unit')
            },
            checkAddFname () {
                if (this.formItem.fname && !this.id) {
                    // this.$api.get('/wiki/api/wiki/existName/' + 2 + '/' + this.formItem.fname).then(response => {
                    //     console.log("检测物种名是否被占用:==>>", response.data, "<<==")
                    //     if (1 === response.data) {
                    //         this.$Message.error("该品种名已被占用!")
                    //         this.formItem.fname = ''
                    //     }
                    // }).catch(function (error) {
                    //     console.log(error)
                    // })
                }
            },
            handleSubmit (name) {
                let flag = false;
                this.$refs[name].validate(valid => {
                    if (valid) {
                        flag = true;
                    } else {
                        this.$Message.error('请核对表单信息')
                    }
                });
                return flag
            }
        }
    }
</script>
