<template>
	<div class="pd20">
    <Title :title="title" edit :id="id" :yearId="yearId" edit></Title>
    <Form ref="data" label-position="left"  class="pd20 mt40" :rules="ruleInline"  :label-width="80" :model="data">
        <Row :gutter="32">
          <Col span="12">
              <FormItem label="上传头像">
                  <vui-upload
                      ref="upload"
                      :disabled="edit"
                      @on-getPictureList="getPictureList"
                      :total="1"
                      :multiple="false"
                      :hint="'图片大小小于2M'"
                      :size="[80,80]"
                      :cover="true"
                      ></vui-upload>
              </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
              <FormItem label="姓名">
                <Input v-model="data.userName.model" readonly :disabled="edit" style="width: 220px" class="mr20"/>
                <i-switch v-model="data.userName.status" size="large" :disabled="edit">
                  <span slot="open">公开</span>
                  <span slot="close">隐藏</span>
                </i-switch>
              </FormItem>
          </Col>
          <Col span="12">
              <FormItem label="性别">
                  <Select v-model="data.sex.model" style="width: 220px" class="mr20" :disabled="edit">
                      <Option v-for="(item,index) in sexs" :key="index" :value="item.value">{{ item.label }}</Option>
                  </Select>
                  <i-switch  v-model="data.sex.status" size="large" :disabled="edit">
                      <span slot="open">公开</span>
                      <span slot="close">隐藏</span>
                  </i-switch>
              </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
                <FormItem label="民族">
                    <Select v-model="data.ethnicGroup.model" style="width: 220px" class="mr20"  filterable :disabled="edit">
                        <Option v-for="(f,index) in families" :value="f.value" :key="index">{{ f.label }}</Option>
                    </Select>
                    <i-switch  v-model="data.ethnicGroup.status" size="large" :disabled="edit">
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </FormItem>
            </Col>
            <Col span="12">
                <FormItem label="生日">
                    <DatePicker type="date" :options="options3" style="width: 220px" :disabled="edit" class="mr20" :editable="false" v-model="data.birthday.model" ></DatePicker>
                    <i-switch  v-model="data.birthday.status" size="large" :disabled="edit">
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </FormItem>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
                <FormItem label="职业">
                    <Input v-model="data.profession.model" style="width: 220px" :disabled="edit" class="mr20" @on-focus="handleFilterModal('professionFilter')" />
                    <i-switch  v-model="data.profession.status" size="large" :disabled="edit">
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </FormItem>
            </Col>
            <Col span="12">
                <FormItem label="职称">
                    <Input v-model="data.professionalTitle.model" :disabled="edit" style="width: 220px" class="mr20"  @on-focus="handleFilterModal('zhichengFilter')" />
                    <i-switch  v-model="data.professionalTitle.status" size="large" :disabled="edit">
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </FormItem>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
                <FormItem label="擅长物种">
                    <Input v-model="data.species.model" :disabled="edit" readonly @on-focus="handleFilterModal('speciFilter')" style="width: 220px" class="mr20" />
                    <i-switch v-model="data.species.status" size="large" :disabled="edit">
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </FormItem>
            </Col>
            <Col span="12">
                <FormItem label="手机号码" prop="phone">
                    <Input v-model="data.phone.model" style="width: 220px" :disabled="edit" class="mr20" :maxlength="11"/>
                    <i-switch  v-model="data.phone.status" size="large" :disabled="edit">
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </FormItem>
            </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
              <FormItem label="门牌号">
                  <Input v-model="data.number.model"  style="width: 220px;" class="mr20" :disabled="edit"></Input>
                  <i-switch v-model="data.number.status" size="large" :disabled="edit">
                      <span slot="open">公开</span>
                      <span slot="close">隐藏</span>
                  </i-switch>
              </FormItem>
          </Col>
          <Col span="12">
              <FormItem prop="coordinatePoint" label="坐标位置">
                  <Input v-model="data.coordinatePoint.model" readonly :disabled="edit" @on-focus="onSelectPoint" style="width: 220px" class="mr20"  />
                  <i-switch  v-model="data.coordinatePoint.status" size="large" :disabled="edit">
                      <span slot="open">公开</span>
                      <span slot="close">隐藏</span>
                  </i-switch>
              </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
                <FormItem label="邮政编码" prop="postalCode">
                    <Input v-model="data.postalCode.model" :maxlength="6" :disabled="edit" style="width: 220px" class="mr20"/>
                    <i-switch  v-model="data.postalCode.status" size="large" :disabled="edit">
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </FormItem>
            </Col>
            <Col span="12">
                <FormItem label="座机号码" prop="tel">
                    <Input v-model="data.tel.model" placeholder="027-88888888" :disabled="edit" :maxlength="15" style="width: 220px" class="mr20" />
                    <i-switch  v-model="data.tel.status" size="large" :disabled="edit">
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </FormItem>
            </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
              <FormItem label="常驻地">
                  <vui-cascander :values="data.addr.model" @handle-get-result="handleGetData" :disabled="edit" style="width: 220px; float: left;" class="mr20"></vui-cascander>
                  <i-switch v-model="data.addr.status" size="large" :disabled="edit">
                      <span slot="open">公开</span>
                      <span slot="close">隐藏</span>
                  </i-switch>
              </FormItem>
          </Col>
            <Col span="12">
              <FormItem label="详细地址">
                  <Input v-model="data.addrDetail.model" style="width: 220px;" :disabled="edit" class="mr20" :maxlength="50"/>
                  <i-switch v-model="data.addrDetail.status" size="large" :disabled="edit">
                      <span slot="open">公开</span>
                      <span slot="close">隐藏</span>
                  </i-switch>
              </FormItem>
          </Col>
        </Row>
    </Form>
    <Title title="文字预览"></Title>
    <div class="pd20">
        <Input v-model="textPreview.text_preview" type="textarea"  :autosize="{minRows: 4,maxRows: 4}"/>
    </div>
    <div class="tc pt40">
        <Button type="primary" @click="handleSubmit">保存</Button>
    </div>

    <!-- 相关物种 -->
    <vui-filter ref="speciFilter" 
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
		<!--
        	作者：786905664@qq.com
        	时间：2018-01-27
        	描述：地图
        -->
    <vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
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
import vuiFilter from "~components/vuiFilter/filter"
import vuiMap from '../../../member/components/productionMap'
import vuiUpload from '~components/vui-upload'
import vuiCascander from '~components/vuiCascader/index'
import {isPhoneDeep,isPostCodeDeep,isTelDeep} from '~utils/validate'
import Title from '../../components/title'
export default {
  components: {
    vuiFilter,
    vuiMap,
    vuiUpload,
    vuiCascander,
    Title
  },
  props:{
    id: String,
    base:{
      type:Boolean,
      default:true
    },
     yearId: {
      type: String
    },
    appId: {
      type: String
    }
  },
    data() {			
        return {
            edit: true,
            size:'',
            sexs:[
                {value:'男',label:'男'},
                {value:'女',label:'女'}
            ],
            options3: {
                disabledDate(date) {
                    return date && date.valueOf() > Date.now() - 86400000;
                }
            },
            total3: 0,
            pageCur3: 1,
            zhichengResultDatas:[],
                
            total2: 0,
            pageCur2: 1,
            professionResultDatas:[],
                
            pageCur: 1,
            total: 0,
            speciResultDatas: [],
            speciClassifyDatas: [
                {
                    label: "动物",
                    value: "0",
                    classId: "",
                    loading: false,
                    checked: false,
                    children: []
                },
                {
                    label: "植物",
                    value: "1",
                    classId: "",
                    loading: false,
                    checked: false,
                    children: []
                }
            ],
            families:[],
            title: '',
            data:{
                avatar:'',
                userName:{model:'',name:'姓名',status:true},
                sex:{model:'',name:'性别',status:true},
                ethnicGroup:{model:'',name:'民族',status:true},
                birthday:{model:'',name:'生日',status:true},
                profession:{model:'',name:'职业',status:true},
                professionalTitle:{model:'',name:'职称',status:true},
                species:{model:'',name:'擅长物种',status:true},
                phone:{model:'',name:'手机号码',status:true},
                addr:{model:'',name:'常住地',status:true},
                addrDetail:{model:'',name:'详细地址',status:true},
                coordinatePoint:{model:'',name:'坐标位置',status:true},
                postalCode:{model:'',name:'邮政编码',status:true},
                tel:{model:'',name:'座机号码',status:true},
                number:{model:'',name:'门牌号',status:true}
            },
            textPreview: {},
            sys_dict_id: '',
            privateInformation_name: '',
            ruleInline:{
                phone:[{validator:isPhoneDeep,trigger: 'blur'}],
                postalCode:[{validator:isPostCodeDeep,trigger: 'blur'}],
                tel:[{validator:isTelDeep,trigger: 'blur'}]
            },
            account: ''
        };
    },
    created() {
        this.account = this.$user.loginAccount
        this.$api.get("/member/race").then(response => {
            this.families = response.data
        });
        // 取相关物种结果
        this.loadSpeciResult("", "", [], this.pageCur, [])
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
        handleInit () {
            this.$api.post('/member-reversion/privacyInfo/findPrivacyInfo', {
            templateId: this.$template.id, parent_id: this.id, user_id: this.$user.loginAccount}).then(response => {
                console.log(response)
                if (response.code === 200) {
                    this.title = response.data.privateInformation_name
                    this.data = response.data.privateInformation
                    if (!response.data.textPreview.text_preview) {
                        response.data.textPreview.text_preview = `姓名（），性别（），民族（），生日（），职业（），职称（），擅长物种（），手机号码（），门牌号（），坐标位置（），邮政编码（），座机号码（），常驻地（），详细地址（）。`
                    }
                    this.textPreview = response.data.textPreview
                    this.sys_dict_id = this.id
                    this.$refs['upload'].handleGive(this.data.avatar)
                }
            })
        },
        //下一步表单验证
        handleSubmit(){
            this.textPreview.is_complete = true
            let list = {
                    privateInformation: this.data, 
                    textPreview: this.textPreview, 
                    sys_dict_id: this.sys_dict_id,
                    privateInformation_name: this.title,
                    user_id : this.account,
                    templateId: this.$template.id,
                    // yearId : this.yearId
                }
            this.$refs.data.validate ( (valid) => {
                if(valid) {
                    this.$api.post('/member-reversion/privacyInfo/insertPrivacyInfo', list).then(response =>{
                        if (response.code === 200) {
                            this.$Message.success('保存成功')
                            this.$emit('on-save')
                            this.handleInit()
                        } else {
                            this.$Message.error('保存失败')
                        }
                    })
                }else{
                    this.$Message.error('请核对表单信息')
                }
            })
        },
        //获取头像
        getPictureList(e){
            if(e[0]){
                this.data.avatar =  e[0].response.data.picName
            }
        },
        //组件弹窗
        handleFilterModal(name) {
            this.$refs[name].highFilterShow = true
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
            this.data.professionalTitle.model = arr.join(' ')

            this.industryDatas = result.map(function(item){
                return item.value
            })
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
            this.data.profession.model = arr.join(' ')
            this.industryDatas = result.map(function(item){
                return item.value
            })
        },
        //地区
        handleGetData (value, selectedData) {
                    let labelArr = []
                    selectedData.forEach(element => {
                        labelArr.push(element.label)
                    })
                    this.data.addr.model = labelArr.join('/')
        },
        // 取相关物种
        handleSpeciSearch(letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
        },
        loadSpeciDatas(item, callback) {
            item.loading = true;
            this.$api.post(`/member/specicesClass/findByParentId/${item.value}`)
                .then(res => {
                    item.loading = false
                    var d = res.data
                    d.forEach(child => {
                        child.checked = false
                        child.label = child.className
                    });
                    item.children = d
                    callback()
                });
        },
        handleGetSpeciClassify(letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
        },
        handleSpeciPageChange(letter, keyword, classify, num, result) {
            this.pageCur = num
            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
        },
        loadSpeciResult(letter, keyword, classify, num, result) {
            if(classify.length) {
                var arr = [];
                var type = ''
                classify.forEach(item => {
                    arr.push(item.classId)
                    if(item.value !== undefined) type = item.value
                });
            } else {
                arr = null;
            }
            this.$api.post("/member/specicesClass/findSpecies", {
                    keywords: keyword,
                    fpinyin: letter === "全部" ? "" : letter,
                    fclassifiedid: arr,
                    type: type,
                    pageNum: num,
                    pageSize: 32
                })
                .then(res => {
                    var data = res.data
                    this.total = data.total
                    if(result) {
                        result.forEach(item => {
                            data.list.forEach((child, index) => {
                                if(child.label === item.label) {
                                    child.checked = true
                                }
                            });
                        });
                    } else {
                        data.list.forEach(child => {
                            child.checked = false
                        });
                    }
                    this.speciResultDatas = data.list
                });
        },
        handleGetSpeciResult(classify, result) {
            var arr = []
            this.resultValue = []
            result.forEach(item => arr.push(item.label))
            result.forEach(item => this.resultValue.push(item.value))
            this.data.species.model = arr.join(" ")
        },
      //坐标
      onSelectPoint() {
            this.$refs.experMap.showMap = true
        },
        // 取坐标
        onGetPoint(point) {
            if(point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
                this.data.coordinatePoint.model = `${point.lng},${point.lat}`
            } else {
                this.data.coordinatePoint.model = ''
            }
        }
    }
}
</script>