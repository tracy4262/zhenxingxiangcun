<template>
    <div>
        <Tabs :value="isName">
            <TabPane label="土地信息" :disabled="isDisableo" name="name1">
                <Row class="ma-addSimilar">
                    <Col span="22" offset="1">
                        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="150">
                            <FormItem label="土地利用分类" prop="landUseClasses">
                                <search :objData="objData" @changeName="changeName"></search>
                                <Input v-model="formValidate.landUseClasses" :disabled="isDisabled" placeholder="请选择土地类型" readonly @on-focus="onModelMa" />
                            </FormItem>
                            <FormItem label="地块名称" prop="plotName">
                                <Input v-model="formValidate.plotName" placeholder="请输入地块名称"></Input>
                            </FormItem>
                            <FormItem label="土地权属人" prop="landowner">
                                <Input v-model="formValidate.landowner" placeholder="请输入土地权属人"></Input>
                            </FormItem>
                            <FormItem label="土地编号" prop="landNumber">
                                <Input v-model="formValidate.landNumber" placeholder="请输入土地编号"></Input>
                            </FormItem>
                            <FormItem label="经纬度">
                                <Input v-model="formValidate.longitudeLatitude" placeholder="请选择坐标" readonly @on-focus="onLongitude"></Input>
                            </FormItem>
                            <FormItem label="面积" prop="landArea">
                                <Input v-model="formValidate.landArea" placeholder="请输入面积"></Input>
                            </FormItem>
                            <FormItem label="面积单位" prop="unitArea">
                                <Select v-model="formValidate.unitArea" placeholder="请选择面积单位">
                                    <Option value="平方米">平方米</Option>
                                    <Option value="亩">亩</Option>
                                    <Option value="公顷">公顷</Option>
                                </Select>
                            </FormItem>
                            <FormItem label="利用现状" prop="situation">
                                <Select v-model="formValidate.situation" placeholder="请选择利用现状">
                                    <Option value="利用">利用</Option>
                                    <Option value="荒芜">荒芜</Option>
                                    <Option value="出租">出租</Option>
                                </Select>
                            </FormItem>
                            <FormItem>
                                <Row>
                                    <Col span="6" offset="9">
                                       <Button type="primary" @click="oSubmit('formValidate')">下一步</Button>
                                        <Button type="default" @click="handleReset('formValidate')" style="margin-left: 8px">取消</Button>
                                    </Col>
                                </Row>
                            </FormItem>
                        </Form>
                    </Col>
                </Row>

                <vui-map 
                ref="vuiMap"
                @on-get-point="onGetPoint"
                ></vui-map>
            </TabPane>

            <TabPane label="土壤信息" :disabled="isDisable" name="name2">
                <Row>
                    <Col span="24">
                        <Tabs type="card">
                            <TabPane label="土壤营养（化学）指标">
                              <div class="ivu-table ivu-table-border ivu-table-small table">
                                <table>
                                  <thead class="ivu-table-header tc">
                                        <tr>
                                            <th>名称</th>
                                            <th>内容</th>
                                        </tr>
                                    </thead>
                                    <tbody class="ivu-table-body">
                                        <tr>
                                            <td>全氮</td>
                                            <td><Input v-model="nutrition.nitrogen" /></td>
                                        </tr>

                                        <tr>
                                            <td>全磷</td>
                                            <td><Input v-model="nutrition.phosphorus" /></td>
                                        </tr>

                                        <tr>
                                            <td>全钾</td>
                                            <td><Input v-model="nutrition.kalium" /></td>
                                        </tr>

                                        <tr>
                                            <td>碱解氮</td>
                                            <td><Input v-model="nutrition.alkaliHydrolysableNitrogen" /></td>
                                        </tr>

                                        <tr>
                                            <td>有效磷</td>
                                            <td><Input v-model="nutrition.effectivePhosphorus" /></td>
                                        </tr>

                                        <tr>
                                            <td>有效钾</td>
                                            <td><Input v-model="nutrition.availablePotassium" /></td>
                                        </tr>

                                        <tr>
                                            <td>速效磷</td>
                                            <td><Input v-model="nutrition.rapidAvailablePhosphorus" /></td>
                                        </tr>

                                        <tr>
                                            <td>速效钾</td>
                                            <td><Input v-model="nutrition.rapidlyAvailablePotassium" /></td>
                                        </tr>

                                        <tr>
                                            <td>阳离子交换量</td>
                                            <td><Input v-model="nutrition.cationExchangeCapacity" /></td>
                                        </tr>

                                        <tr>
                                            <td>碳氮比</td>
                                            <td><Input v-model="nutrition.carbonNitrogenRatio" /></td>
                                        </tr>

                                        <tr>
                                            <td>有机质</td>
                                            <td><Input v-model="nutrition.organicMatter" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="ma-button">
                                  <Button type="primary" @click="addDataOne">确定</Button>
                                  <Button type="primary" @click="handleReset('formValidate')">取消</Button>
                                </div>
                              </div>
                            </TabPane>

                            <TabPane label="土壤物理性状指标">
                              <div class="ivu-table ivu-table-border ivu-table-small table">
                                <table>
                                  <thead class="ivu-table-header tc">
                                        <tr>
                                            <th>名称</th>
                                            <th>内容</th>
                                        </tr>
                                    </thead>
                                    <tbody class="ivu-table-body">
                                        <tr>
                                            <td>质地</td>
                                            <td><Input v-model="physics.physicalCharacter" /></td>
                                        </tr>

                                        <tr>
                                            <td>容重</td>
                                            <td><Input v-model="physics.volumeWeight" /></td>
                                        </tr>

                                        <tr>
                                            <td>水稳性团聚体</td>
                                            <td><Input v-model="physics.waterstableAggregate" /></td>
                                        </tr>

                                        <tr>
                                            <td>孔隙度（总孔隙度、毛管孔隙度、非毛管孔隙度）</td>
                                            <td><Input v-model="physics.porosity" /></td>
                                        </tr>

                                        <tr>
                                            <td>土壤耕层温度变幅</td>
                                            <td><Input v-model="physics.ploughTemperature" /></td>
                                        </tr>

                                        <tr>
                                            <td>土层厚度</td>
                                            <td><Input v-model="physics.soilThickness" /></td>
                                        </tr>

                                        <tr>
                                            <td>土壤含水量</td>
                                            <td><Input v-model="physics.soilWaterContent" /></td>
                                        </tr>

                                        <tr>
                                            <td>粘粒含量</td>
                                            <td><Input v-model="physics.clayContent" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="ma-button">
                                  <Button type="primary" @click="addDataTwo">确定</Button>
                                  <Button type="primary" @click="handleReset('formValidate')">取消</Button>
                                </div>
                              </div>
                            </TabPane>

                            <TabPane label="土壤生物学指标">
                              <div class="ivu-table ivu-table-border ivu-table-small table">
                                <table>
                                  <thead class="ivu-table-header tc">
                                        <tr>
                                            <th>名称</th>
                                            <th>内容</th>
                                        </tr>
                                    </thead>
                                    <tbody class="ivu-table-body">
                                        <tr>
                                            <td>有机质</td>
                                            <td><Input v-model="biology.organicMatter" /></td>
                                        </tr>

                                        <tr>
                                            <td>腐殖酸（富里酸、胡敏酸）</td>
                                            <td><Input v-model="biology.humicAcid" /></td>
                                        </tr>

                                        <tr>
                                            <td>微生物态碳</td>
                                            <td><Input v-model="biology.soilMicrobialBiomassCarbon" /></td>
                                        </tr>

                                        <tr>
                                            <td>微生物态氮</td>
                                            <td><Input v-model="biology.soilMicrobialNitrogen" /></td>
                                        </tr>

                                        <tr>
                                            <td>土壤酶活性（腺酶、蛋白酶、过氧化氢酶、转化酶、磷酸酶等）</td>
                                            <td><Input v-model="biology.soilEnzymeActivity" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="ma-button">
                                  <Button type="primary" @click="addDataThtee">确定</Button>
                                  <Button type="primary" @click="handleReset('formValidate')">取消</Button>
                                </div>
                              </div>
                            </TabPane>

                            <TabPane label="土壤环境指标">
                              <div class="ivu-table ivu-table-border ivu-table-small table">
                                <table>
                                  <thead class="ivu-table-header tc">
                                        <tr>
                                            <th>名称</th>
                                            <th>内容</th>
                                        </tr>
                                    </thead>
                                    <tbody class="ivu-table-body">
                                        <tr>
                                            <td>土壤ph</td>
                                            <td><Input v-model="science.soilPH" /></td>
                                        </tr>

                                        <tr>
                                            <td>地下水深度</td>
                                            <td><Input v-model="science.groundWaterDepth" /></td>
                                        </tr>

                                        <tr>
                                            <td>坡度</td>
                                            <td><Input v-model="science.gradient" /></td>
                                        </tr>

                                        <tr>
                                            <td>林网化水平</td>
                                            <td><Input v-model="science.networkForest" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="ma-button">
                                  <Button type="primary" @click="addDataFour">确定</Button>
                                  <Button type="primary" @click="handleReset('formValidate')">取消</Button>
                                </div>
                              </div>
                            </TabPane>

                            <TabPane label="土壤微量元素含量">
                              <div class="ivu-table ivu-table-border ivu-table-small table">
                                <table>
                                  <thead class="ivu-table-header tc">
                                        <tr>
                                            <th>名称</th>
                                            <th>内容</th>
                                        </tr>
                                    </thead>
                                    <tbody class="ivu-table-body">
                                        <tr>
                                            <td>有效铜</td>
                                            <td><Input v-model="trace.effectiveCopper" /></td>
                                        </tr>

                                        <tr>
                                            <td>有效锌</td>
                                            <td><Input v-model="trace.availableZinc" /></td>
                                        </tr>

                                        <tr>
                                            <td>有效铁</td>
                                            <td><Input v-model="trace.availableIron" /></td>
                                        </tr>

                                        <tr>
                                            <td>有效猛</td>
                                            <td><Input v-model="trace.availableMn" /></td>
                                        </tr>

                                        <tr>
                                            <td>有效钼</td>
                                            <td><Input v-model="trace.availableMo" /></td>
                                        </tr>

                                        <tr>
                                            <td>有效磞</td>
                                            <td><Input v-model="trace.availableBoron" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="ma-button">
                                  <Button type="primary" @click="addDataFive">确定</Button>
                                  <Button type="primary" @click="handleReset('formValidate')">取消</Button>
                                </div>
                              </div>
                            </TabPane>
                        </Tabs>
                    </Col>
                </Row>
            </TabPane>

            <TabPane label="水质信息" :disabled="isDisable" name="name3" class="ma-table-height">
                <div class="ivu-table ivu-table-border ivu-table-small table">
                  <table>
                    <thead class="ivu-table-header tc">
                          <tr>
                              <th>序号</th>
                              <th>项目类别</th>
                              <th>指标</th>
                              <th>单位</th>
                              <th>本企业数据</th>
                          </tr>
                      </thead>
                      <tbody class="ivu-table-body">
                          <tr>
                              <td>1</td>
                              <td>ph</td>
                              <td>6.8-8.5</td>
                              <td>mg/L</td>
                              <td><Input v-model="formData.waterPH" /></td>
                          </tr>

                          <tr>
                              <td>2</td>
                              <td>氰化物</td>
                              <td>≤0.05</td>
                              <td>mg/L</td>
                              <td><Input v-model="formData.cyanide" /></td>
                          </tr>

                          <tr>
                              <td>3</td>
                              <td>氟化物</td>
                              <td>≤1.5</td>
                              <td>mg/L</td>
                              <td><Input v-model="formData.fluoride" /></td>
                          </tr>

                          <tr>
                              <td>4</td>
                              <td>氯化物</td>
                              <td>≤250</td>
                              <td>mg/L</td>
                              <td><Input v-model="formData.chloride" /></td>
                          </tr>

                          <tr>
                              <td>5</td>
                              <td>砷</td>
                              <td>≤0.05</td>
                              <td>mg/L</td>
                              <td><Input v-model="formData.arsenic" /></td>
                          </tr>

                          <tr>
                              <td>6</td>
                              <td>汞</td>
                              <td>≤0.001</td>
                              <td>mg/L</td>
                              <td><Input v-model="formData.mercury" /></td>
                          </tr>

                          <tr>
                              <td>7</td>
                              <td>铅</td>
                              <td>≤0.05</td>
                              <td>mg/L</td>
                              <td><Input v-model="formData.lead" /></td>
                          </tr>

                          <tr>
                              <td>8</td>
                              <td>镉</td>
                              <td>≤0.01</td>
                              <td>mg/L</td>
                              <td><Input v-model="formData.cadmium" /></td>
                          </tr>

                          <tr>
                              <td>9</td>
                              <td>铬（+6）</td>
                              <td>≤0.05</td>
                              <td>mg/L</td>
                              <td><Input v-model="formData.chromium" /></td>
                          </tr>

                          <tr>
                              <td>10</td>
                              <td>粪大肠杆菌（个／升）</td>
                              <td>≤10</td>
                              <td>mg/L</td>
                              <td><Input v-model="formData.fecalColiform" /></td>
                          </tr>
                      </tbody>
                  </table>
                </div>
                <br>
                <Row>
                    <h4 class="ma-addSimilarH4">上传检测报告</h4>
                    <Row>
                        <Col span="22" offset="1">
                            <!-- 头像上传 -->
                            <div>   
                                <template v-if="uploadList.length>0">
                                    <div class="demo-upload-list"
                                         v-for="item in uploadList">
                                        <img :src="item">
                                        <div class="demo-upload-list-cover">
                                            <Icon type="ios-trash-outline"
                                                  @click.native="handleRemove1(item)"></Icon>
                                        </div>
                                    </div>
                                </template>
                            </div>

                            <Upload ref="upload1" :show-upload-list="false"
                                    name="upfile"
                                    :format="['jpg', 'png']"
                                    :max-size="2048"
                                    multiple
                                    :on-success="handleSuccess1"
                                    :before-upload="handleBeforeUpload1"
                                    type="drag"
                                    :action="action"
                                    style="display: inline-block;width:140px;">
                                <div class="upload-add-div">
                                    <Icon type="plus-circled" color="#00c587" :size="32"></Icon>
                                    <p class="mb5">点击添加图片</p>
                                    <p class="t-grey">支持jpg/png格式</p>
                                    <p class="t-grey">不超过2M</p>
                                </div>
                            </Upload>

                            <div class="upload-add-div photo-selector" style="top: -6px;" @click="handlePhotoSelectorModal('identityPhoto1')">
                                <Icon type="plus-circled" color="#00c587" :size="32"></Icon>
                                <p class="mb5">从文件管理导入</p>
                                <p class="t-grey">支持jpg/png格式</p>
                                <p class="t-grey">不超过2M</p>
                            </div>



                            <!-- <div class="upload-list" v-for="(item,index) in uploadList" :key="index">
                                <template v-if="item.status === 'finished'">
                                    <img :src="'http://'+item.response.data.picName">
                                    <div class="upload-list-cover">
                                        <Icon type="ios-trash-outline" @click.native="removeUpload(item)"></Icon>
                                    </div>
                                </template>
                                <template v-else>
                                    <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
                                </template>
                            </div>
                            <Upload 
                            ref="upload" 
                            :show-upload-list="false"
                            name="upfile"
                            :format="['jpg','png','pdf','ppt','avi']"
                            :max-size="204800"
                            :on-success="handleSuccess"
                            :before-upload="handleBeforeUpload"
                            type="drag"
                            :action="action"
                            style="display: inline-block;width:58px;">
                                <div style="width: 58px;height:58px;line-height: 58px;">
                                    <Icon type="md-add-circle" size="20"></Icon>
                                </div>
                            </Upload> -->
                        </Col>
                    </Row>
                </Row>
                <br>
                <Row>
                    <Col span="24">
                        <p class="ma_text">{{formData.describe}}</p>
                    </Col>
                </Row>
                <br>
                <Row>
                    <Col span="6" offset="9">
                       <Button type="primary" @click="addData">保存</Button>
                        <Button type="default" @click="handleReset('formValidate')" style="margin-left: 8px">取消</Button>
                    </Col>
                </Row>
                <br>
            </TabPane>
        </Tabs>

        <!-- 照片选择 -->
        <photoSelector 
        ref="identityPhoto1" 
        :photoAlbum="l1"
        @on-change="albumChange1"
        @on-get-result="handleGetPhotoResult1"
        :resultDatas="p1"
        :transfer="true"
        />
    </div>
</template>
<script>
import api from '~api'
import vuiMap from '../../../components/productionMap'
import search from './search.vue'
import photoSelector from '~components/photoSelector'
export default {
    components:{
        vuiMap,
        search,
        photoSelector
    },
    data() {
		return {
            formValidate: {
                plotName: '',
                landUseClasses: '',
                longitudeLatitude: '',
                landArea: '',
                unitArea: '',
                landowner: '',
                landNumber: '',
                situation: ''
            },
            ruleValidate: {
                landUseClasses: [{ required: true, type: 'string',message: '不能为空！', trigger: 'blur' } ],
                plotName: [{ required: true, message: '不能为空！', trigger: 'blur' }],
                landowner: [{ required: true, message: '不能为空！', trigger: 'blur' }],
                landArea: [{ required: true, message: '不能为空！', trigger: 'blur' }
                ],
                landNumber: [{ required: true, message: '不能为空！', trigger: 'blur' } ],
                landUseClasses:  [{ required: true, message: '不能为空！', trigger: 'change' }],
                situation:  [{ required: true, message: '不能为空！', trigger: 'change' }],
                unitArea:  [{ required: true, message: '不能为空！', trigger: 'change' }]
            },

            action: `${this.$url.upload}/upload/up`,
            imgName: '',
            visible: false,
            uploadList: [],
            isDisabled: false,

            // 土壤营养2-1
            nutrition: {
                nitrogen: '',
                phosphorus: '',
                kalium: '',
                alkaliHydrolysableNitrogen: '',
                effectivePhosphorus: '',
                availablePotassium: '',
                rapidAvailablePhosphorus: '',
                rapidlyAvailablePotassium: '',
                cationExchangeCapacity: '',
                carbonNitrogenRatio: '',
                organicMatter: ''
            },

            // 土壤物理2-2
            physics: {
                physicalCharacter: '',
                volumeWeight: '',
                waterstableAggregate: '',
                porosity: '',
                ploughTemperature: '',
                soilThickness: '',
                soilWaterContent: '',
                clayContent: ''
            },

            // 土壤生物2-3
            biology: {
                organicMatter: '',
                humicAcid: '',
                soilMicrobialBiomassCarbon: '',
                soilMicrobialNitrogen: '',
                soilEnzymeActivity: ''
            },

            // 土壤环境2-4
            science: {
                soilPH: '',
                groundWaterDepth: '',
                gradient: '',
                networkForest: ''
            },

            // 土壤微量2-5
            trace: {
                effectiveCopper: '',
                availableZinc: '',
                availableIron: '',
                availableMn: '',
                availableMo: '',
                availableBoron: ''
            },

            // 水质信息
            formData: {
                waterPH: '',
                cyanide: '',
                fluoride: '',
                chloride: '',
                arsenic: '',
                mercury: '',
                lead: '',
                cadmium: '',
                chromium: '',
                fecalColiform: '',
                describe: ''
            },

            objData: {
                modalMa: false
            },

            isDisable: true,
            isDisableo: false,
            isName: 'name1',
            landId: '',
            l1: [],
            p1: []
            
	    }
	},
    props: {
        isEdit: false,
        ismMassif: Object,
        isTrue: '',
        isAddor: '',
        isAddorData: ''
    },
    created(){
        // 从我的风采导入图片 默认显示第一个相册
        this.getAlbum1('identityPhoto1')

        this.isName = 'name1'
        this.isDisableo = false
        if(this.isAddor === 1){         //新增同类地块
            this.formValidate.landUseClasses = this.isAddorData
            this.isDisabled = true
        }else if(this.isAddor === 0){   //新增地块
            this.isDisabled = false
        }else if(this.isAddor === 2){   //编辑
            let that = this
            this.isDisabled = false
            this.ismMassif.landArea = this.ismMassif.landArea.toString()
            this.ismMassif.landNumber = this.ismMassif.landNumber.toString()
            
            this.formValidate = this.ismMassif
            this.formValidate.landUseClasses = this.isAddorData

            api.post('/member/product-land-use-quo/query-all', {
                landId: that.ismMassif.landId
            })
            .then(response => {
                if(response.code === 200){
                    if(response.data.landNutritiveMap !== undefined){
                        that.nutrition = response.data.landNutritiveMap
                    }

                    if(response.data.landPhysicalMap !== undefined){
                        that.physics = response.data.landPhysicalMap
                    }

                    if(response.data.landBiologicalMap !== undefined){
                        that.biology = response.data.landBiologicalMap
                    }

                    if(response.data.landEnvironmentalMap !== undefined){
                        that.science = response.data.landEnvironmentalMap
                    }

                    if(response.data.landSoilMicroelementlMap !== undefined){
                        that.trace = response.data.landSoilMicroelementlMap
                    }

                    if(response.data.landWaterQualityMap !== undefined){
                        that.formData = response.data.landWaterQualityMap
                    }

                    if(response.data.landWaterQualityMap.describe === undefined || response.data.landWaterQualityMap.describe === ''){
                        that.formData.describe = ''
                    }
                    
                    response.data.reportMap.forEach(element => {
                        console.log(element.reportUrl)
                        this.uploadList.push(element.reportUrl)
                    })
                }
            })
        }
	},
    mounted(){
        //this.uploadList = this.$refs.upload.fileList
    },
    methods: {
        // 从我的风采导入图片--上传图标 1
        // 1
        handlePhotoSelectorModal (name) {
            this.$refs[name].photoSelectorShow = true
            this.$refs[name].choosed = []
        },

        albumChange1 (value) {
            this.p1 = []
            this.getPhoto1({
                mediaId: value,
                pageNum: 1,
                pageSize: 1000
            })
        },

        handleGetPhotoResult1 (result) {
            // 用于限制最大可上传照片的张数
            if (this.uploadList.length + result.length > 4) {
                this.$Notice.warning({
                    title: '最多只能上传 4 张图片。'
                })
            } else {
                result.forEach(element => {
                    this.uploadList.push(element)
                })
            }
        },

        getAlbum1 (name) {
            this.$api.post('/member/product-base/media-library-query-all', {
                account: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
                mediaType: 1
            }).then(response => {
                if (response.code === 200) {
                    if (response.data.length !== 0) {
                        this.$refs[name].album = response.data[0].mediaId
                    }
                    response.data.forEach(element => {
                        this.l1.push({
                            label: element.mediaName,
                            value: element.mediaId
                        })
                    })
                }
            }).catch(error => {
                console.log(error)
            })
        },

        getPhoto1 (params) {
            this.$api.post("/member/product-base/media-library-detail-query-list", params)
                .then(response => {
                if (response.code === 200) {
                    for (let i = 0; i < response.data.list.length; i++) {
                        this.p1.push({
                            id: response.data.list[i].id,
                            src: response.data.list[i].mediaUrl,
                            disable: false
                        })
                    }
                }
            })
        },

        handleBeforeUpload1 () {
            const check = this.$refs.upload1.fileList.length < 4 && this.uploadList.length < 4
            if (!check) {
                this.$Notice.warning({
                    title: '最多只能上传 4 张图片。'
                })
            }
            return check
        },

        handleSuccess1(response, file, fileList) {
            if (response.code === 500) {
                this.$Message.error('上传失败!')
            } else {
                this.$Message.success('上传成功!')
                this.uploadList.push(file.response.data.picName)
            }
        },

        handleRemove1 (file) {
            const fileList = this.uploadList
            this.uploadList.splice(fileList.indexOf(file), 1)
            const fileList1 = this.$refs.upload1.fileList
            this.$refs.upload1.fileList.splice(fileList1.indexOf(file), 1)
        },


        // 新增地块-土地信息保存
      	oSubmit(name){
            this.$refs[name].validate((valid) => {
                if (valid) {
                    this.$Message.success('保存成功!')
                    this.isDisable = false
                    this.isDisableo = true
                    this.isName = 'name2'
                    if(this.isAddor === 2){
                        this.getEdit()
                    }else{
                        this.getSubmit()
                    }
                } else {
                    this.$Message.error('验证失败!')
                    this.isDisable = true
                }
            })
        },

        // 新增地块-土地信息保存接口
        getSubmit(){
            api.post('/member/product-land-use-quo/plot-save', {
                productId: this.$route.query.id,
                data: this.formValidate
            })
            .then(response => {
                this.landId = response.data.landId
            })
        },

        // 编辑-土地信息保存接口
        getEdit(){
            api.post('/member/product-land-use-quo/plot-save', {
                data: this.formValidate
            })
            .then(response => {
            })
        },

        // 数据清空 并 取消验证
        emptyData(){
            this.formValidate = {
                plotName: '',
                landUseClasses: '',
                longitudeLatitude: '',
                landArea: '',
                unitArea: '',
                landowner: '',
                map: '',
                landNumber: ''
            }

            this.nutrition = {
                nitrogen: '',
                phosphorus: '',
                kalium: '',
                alkaliHydrolysableNitrogen: '',
                EffectivePhosphorus: '',
                availablePotassium: '',
                rapidAvailablePhosphorus: '',
                rapidlyAvailablePotassium: '',
                cationExchangeCapacity: '',
                carbonNitrogenRatio: '',
                organicMatter: ''
            }

            this.physics = {
                physicalCharacter: '',
                volumeWeight: '',
                waterstableAggregate: '',
                porosity: '',
                ploughTemperature: '',
                soilThickness: '',
                soilWaterContent: '',
                clayContent: ''
            }

            this.biology = {
                organicMatter: '',
                humicAcid: '',
                soilMicrobialBiomassCarbon: '',
                soilMicrobialNitrogen: '',
                soilEnzymeActivity: ''
            }

            this.science = {
                soilPH: '',
                groundWaterDepth: '',
                gradient: '',
                networkForest: ''
            },

            this.trace = {
                effectiveCopper: '',
                availableZinc: '',
                availableIron: '',
                availableMn: '',
                availableMo: '',
                availableBoron: ''
            },

            this.formData = {
                waterPH: '',
                cyanide: '',
                fluoride: '',
                chloride: '',
                arsenic: '',
                mercury: '',
                lead: '',
                cadmium: '',
                chromium: '',
                fecalColiform: ''
            }

            this.$refs['formValidate'].resetFields()
        },

        // 土壤营养2-1 保存事件
        addDataOne(){
            let landId
            if(this.isAddor === 2){
                landId = this.ismMassif.landId
            }else{
                landId = this.landId
            }
            api.post('/member/product-land-use-quo/land-info-nutritive-save', {
                landId: landId,
                data: this.nutrition
            })
            .then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功！')
                }
            })   
        },

        // 土壤物理2-2 保存事件
        addDataTwo(){
            let landId
            if(this.isAddor === 2){
                landId = this.ismMassif.landId
            }else{
                landId = this.landId
            }
            api.post('/member/product-land-use-quo/land-info-physical-save', {
                landId: landId,
                data: this.physics
            })
            .then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功！')
                }
            })
        },

        // 土壤生物2-3 保存事件
        addDataThtee(){
            let landId
            if(this.isAddor === 2){
                landId = this.ismMassif.landId
            }else{
                landId = this.landId
            }
            api.post('/member/product-land-use-quo/land-info-biological-save', {
                landId: landId,
                data: this.biology
            })
            .then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功！')
                }
            })
        },

        // 土壤环境2-4 保存事件
        addDataFour(){
            let landId
            if(this.isAddor === 2){
                landId = this.ismMassif.landId
            }else{
                landId = this.landId
            }
            api.post('/member/product-land-use-quo/land-info-environmental-save', {
                landId: landId,
                data: this.science
            })
            .then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功！')
                }
            })
        },

        // 土壤微量2-5 保存事件
        addDataFive(){
            let landId
            if(this.isAddor === 2){
                landId = this.ismMassif.landId
            }else{
                landId = this.landId
            }
            api.post('/member/product-land-use-quo/land-info-microelements-save', {
                landId: landId,
                data: this.trace
            })
            .then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功！')
                }
            })
        },

        // 取消事件
        handleReset(){
            this.$emit('reset')
            this.isName = 'name1'
            this.isDisableo = false
            this.isDisable = true
            this.emptyData()
        },
        
        // 取坐标
        onGetPoint(point){
          this.formValidate.longitudeLatitude = `${point.lng},${point.lat}`
        },
        onLongitude(){
          this.$refs.vuiMap.showMap = true
        },

        changeName(e){
          this.formValidate.landUseClasses = e
        },

        onModelMa(){
          this.objData.modalMa = true
        },

        // 最终保存 添加数据
        addData(){
            let landId
            let upList = []
            let datas = {}
            this.uploadList.map(item => {
                let obj = {
                    url: item
                }
                upList.push(obj)
            })
            datas = this.formData
            datas.upList = upList
            if(this.isAddor === 2){
                landId = this.ismMassif.landId
            }else{
                landId = this.landId
            }
            api.post('/member/product-land-use-quo/land-info-water-quality-save', {
                landId: landId,
                data: datas
            })
            .then(response => {
                if(response.code === 200){
                    this.$emit('add')
                }
            })
            
            this.isName = 'name1'
            this.isDisableo = false
            this.isDisable = true
            this.emptyData()
        }
    }
}
</script>
<style scoped>
    .ma-button{text-align: center;padding: 20px 0;}
    .expand-row{
        margin-bottom: 16px;
    }
    .ma-addSimilar{margin-bottom: 40px;}
    .ma-addSimilarImg{width: 100%;}
    .ma-addSimilarH3{line-height: 40px;text-align: center;}
    .ma-addSimilarH4{line-height: 30px;text-align: center;}
    .ma-table-height .ivu-table{height: auto;}
    .ma_text{padding: 10px 5px;}
</style>