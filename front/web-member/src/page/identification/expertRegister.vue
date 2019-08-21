<template>
	<div class="wrapper layout mt20">
			<Form ref="expertInfo2" :model="expertInfo2" label-position="right" :label-width="150" :rules="ruleInline2">
				<Row>
	                <Col span="11">
	                	<Form-item prop="expert_type" class="input" label="专家分类：">
							<i-select v-model="expertInfo2.expert_type">
								<i-option v-for="(item,index) in expertTypeList" :value="item.value" :key="index">{{ item.label }}</i-option>
							</i-select>
						</Form-item>
	                </Col>
	                <Col span="11" offset="1">
	                	<Form-item prop="adept_field" class="input" label="擅长领域：">
							<!-- <i-select multiple v-model="expertInfo2.adept_field">
								<i-option v-for="(item,index) in adeptFieldList" :value="item.value" :key="index">{{ item.label }}</i-option>
							</i-select> -->
							<Input v-model="expertInfo2.adept_field" />
						</Form-item>
	                </Col>
	            </Row>
				<Row>
	                <Col span="11">
	                	<Form-item prop="adept_species" class="input" label="擅长物种：">
							<Input v-model="expertInfo2.adept_species" readonly @on-focus="handleFilterModal('speciFilter2')" />
						</Form-item>
	                </Col>
	                <Col span="11" offset="1">
	                	<Form-item prop="speci" class="input" label="相关物种：">
							<Input v-model="expertInfo2.speci" readonly @on-focus="handleFilterModal('speciFilter')" />
						</Form-item>
	                </Col>
	            </Row>
				 <Row>
	                <Col span="11">
	                	<Form-item prop="trade" class="input" label="相关行业：">
							<Input v-model="expertInfo2.trade" readonly @on-focus="handleFilterModal('tradeFilter')" />
						</Form-item>
	                </Col>
	                <Col span="11" offset="1">
	                	<Form-item prop="product" class="input" label="相关商品：">
							<Input v-model="expertInfo2.product" />
						</Form-item>
	                </Col>
	            </Row>
				 <Row>
	                <Col span="11">
	                	<Form-item prop="service" class="input" label="相关服务：">
							<Input v-model="expertInfo2.service" readonly @on-focus="handleFilterModal('serviceFilter')" />
						</Form-item>
	                </Col>
	                <Col span="11" offset="2">
	                </Col>
	            </Row>
	            <!--<Form-item  >
	                 <Checkbox v-model="expertInfo2.single">同意<span style="color: #00BBFF;"><<农事无忧机关服务协议>></span></Checkbox>
	            </Form-item>-->
			</Form>
			<Form ref="expertInfo5" :model="expertInfo5" label-position="right" :label-width="150" :rules="ruleInline5">
                <Form-item prop="personal_picture_list" class="" label="上传个人照片：">
                    <div class="demo-upload-list" v-for="(item,index) in expertInfo5.personal_picture_list" :key="index">
                        <template v-if="item.status === 'finished'">
                            <img :src="item.picName">
                            <div class="demo-upload-list-cover">
                                <Icon class="mt70" type="ios-trash-outline" @click.native="handleRemove3(item)"></Icon>
                            </div>
                        </template>
                    </div>
                    <Upload ref="upload3" :show-upload-list="false"
                            name="upfile"
                            :format="['jpg','png']"
                            :max-size="204800"
                            :on-success="handleSuccess3"
                            :before-upload="handleBeforeUpload3"
                            type="drag"
                            :action="action"
                            style="display: inline-block;">
                        <div class="upload-img-cer">
							<Icon type="plus-circled"  color="#00c587" :size="32"></Icon>
							<p class="mb5">点击添加图片</p>
							<p class="t-grey">支持jpg/png格式</p>
							<p class="t-grey"> 不超过2M</p>
                        </div>
                    </Upload>
					<div class="upload-add-div1 photo-selector1" @click="handlePhotoSelectorModal('personPhoto')">
						<Icon type="plus-circled"  color="#00c587" :size="32"></Icon>
						<p class="mb5">从文件管理导入</p>
						<p class="t-grey">支持jpg/png格式</p>
						<p class="t-grey"> 不超过2M</p>
					</div>
                </Form-item>
            </Form>
            <Form ref="expertInfo4" :model="expertInfo4" label-position="right" :label-width="150" :rules="ruleInline4">
                <Form-item prop="qualification_certificate_picture_list" class="" label="上传资质证书：">
                    <div class="demo-upload-list" v-for="(item,index) in expertInfo4.qualification_certificate_picture_list" :key="index">
                        <template v-if="item.status === 'finished'">
                            <img :src="item.picName">
                            <div class="demo-upload-list-cover">
                                <Icon class="mt70" type="ios-trash-outline" @click.native="handleRemove2(item)"></Icon>
                            </div>
                        </template>
                    </div>
                    <Upload ref="upload2" :show-upload-list="false"
                            name="upfile"
                            :format="['jpg','png']"
                            :max-size="204800"
                            :on-success="handleSuccess2"
                            :before-upload="handleBeforeUpload2"
                            type="drag"
                            :action="action"
                            style="display: inline-block;">
                        <div  class="upload-img-cer">
							<Icon type="plus-circled"  color="#00c587" :size="32"></Icon>
							<p class="mb5">点击添加图片</p>
							<p class="t-grey">支持jpg/png格式</p>
							<p class="t-grey"> 不超过2M</p>
                        </div>
                    </Upload>
					<div class="upload-add-div1 photo-selector1" @click="handlePhotoSelectorModal('qualificationPhoto')">
						<Icon type="plus-circled"  color="#00c587" :size="32"></Icon>
						<p class="mb5">从文件管理导入</p>
						<p class="t-grey">支持jpg/png格式</p>
						<p class="t-grey"> 不超过2M</p>
					</div>
                </Form-item>
            </Form>
			<Form ref="expertInfo3" :model="expertInfo3" label-position="right" :label-width="150" :rules="ruleInline3">
				<Form-item prop="honor_certificate_picture_list" class="" label="上传荣誉证书：">
					<div class="demo-upload-list" v-for="(item,index) in expertInfo3.honor_certificate_picture_list" :key="index">
						<template v-if="item.status === 'finished'">
							<img :src="item.picName">
							<div class="demo-upload-list-cover">
								<Icon class="mt70"  type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
							</div>
						</template>
					</div>
					<Upload ref="upload" :show-upload-list="false"
							name="upfile"
							:format="['jpg','png']"
							:max-size="204800"
							:on-success="handleSuccess"
							:before-upload="handleBeforeUpload"
							type="drag"
							:action="action"
							style="display: inline-block;">
						<div class="upload-img-cer">
							<Icon type="plus-circled"  color="#00c587" :size="32"></Icon>
							<p class="mb5">点击添加图片</p>
							<p class="t-grey">支持jpg/png格式</p>
							<p class="t-grey"> 不超过2M</p>
						</div>
					</Upload>
					<div class="upload-add-div1 photo-selector1" @click="handlePhotoSelectorModal('identityPhoto')">
						<Icon type="plus-circled"  color="#00c587" :size="32"></Icon>
						<p class="mb5">从文件管理导入</p>
						<p class="t-grey">支持jpg/png格式</p>
						<p class="t-grey"> 不超过2M</p>
					</div>

				</Form-item>
			</Form>
			<Modal v-model="modal2" width="500" @on-ok="asyncOK"  :closable="false" style="text-align: center;">
				<img src="../../img/chenggong.png" class="mb45" />
				<h2 style="color: #4B8EFF" class="mb35">您已提交专家认证信息，审核工作将在
				<p style="color: red">三个工作日</p>内完成，请耐心等待</h2>
			</Modal>
			<div  class="footer-btn">
				<i-button type="primary" @click="preStep" size="large">上一步</i-button>
				<i-button type="primary" @click="submit" size="large">下一步</i-button>
				<span class="tiaoguo" @click="pass">跳过</span>
			</div>
		<!-- 相关行业tradeFilter -->
        <!-- <vui-filter
        ref="tradeFilter"
        :simple="true"
		:pageShow="true"
        :total="total2"
        :pageCur="pageCur2"
        :resultDatas="tradeResultDatas"
        @on-search="handleTradeSearch"
		@on-page-change="handleTradePageChange"
        @on-get-result="handleGetTradeResult"/> -->

		<!-- 相关行业 -->
		<vui-filter
		ref="tradeFilter"
		:num="4"
		:simple="true"
		:pageShow="true"
		:cols="2"
		:total="total3"
        :pageCur="pageCur3"
		:resultDatas="tradeResultDatas"
		@on-search="handleTradeSearch"
		@on-page-change="handleTradePageChange"
		@on-get-result="handleGetTradeResult" />
	
		<!-- 相关服务 -->
		<vui-filter ref="serviceFilter"  :num="4" :simple="true" :cols="2" :resultDatas="serviceResultDatas" @on-search="handleServiceSearch" @on-get-result="handleGetServiceResult" />
	
		<!-- 相关物种 -->
		<vui-filter ref="speciFilter" :cols="2" :num="4" :pageShow="true" :total="total" :pageCur="pageCur" :classifyDatas="speciClassifyDatas" :resultDatas="speciResultDatas" :load-data="loadSpeciDatas" @on-search="handleSpeciSearch" @on-get-classify="handleGetSpeciClassify" @on-get-result="handleGetSpeciResult" @on-page-change="handleSpeciPageChange" />
	
		<!-- 擅长物种 -->
		<vui-filter ref="speciFilter2" :cols="2" :num="4" :pageShow="true" :total="total2" :pageCur="pageCur2" :classifyDatas="speciClassifyDatas" :resultDatas="speciResultDatas2" :load-data="loadSpeciDatas" @on-search="handleSpeciSearch2" @on-get-classify="handleGetSpeciClassify2" @on-get-result="handleGetSpeciResult2" @on-page-change="handleSpeciPageChange2" />
	
		<Back-top :height="400" :bottom="80">
			<div class="top">返回顶端</div>
		</Back-top>

		<!-- 照片选择 -->
		<photoSelector
				ref="identityPhoto"
				:photoAlbum="l"
				@on-change="albumChange"
				@on-get-result="handleGetPhotoResult"
				:resultDatas="p"
		/>

		<photoSelector
				ref="qualificationPhoto"
				:photoAlbum="l2"
				@on-change="albumChange2"
				@on-get-result="handleGetPhotoResult2"
				:resultDatas="p2"
		/>

		<photoSelector
				ref="personPhoto"
				:photoAlbum="l3"
				@on-change="albumChange3"
				@on-get-result="handleGetPhotoResult3"
				:resultDatas="p3"
		/>


	</div>
</template>


<script>
	import api from '~api'
	import vuiFilter from '~components/vuiFilter/filter'
    import photoSelector from '~components/photoSelector'
	export default {
		components: {
			vuiFilter,
            photoSelector
		},
		data() {
			return {
				
				modal2:false,
				speciesResult:[],//最终物种
				adpetSpecies:[],
				service:[],
				products:[],
				classfy:[],//相关行业
				uploadType:'',
				total: 0,
                pageCur: 1,
                total2: 0,
				pageCur2: 1,
				total3: 0,
                pageCur3: 1,
				displayName: '',
                resultPicList:[],
                resultPicList2:[],
                resultPicList3:[],
                l: [],
                p: [],
                l2: [],
                p2: [],
                l3: [],
                p3: [],
				loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
				action: `${this.$url.upload}/upload/up`,
				expertInfo2: {
					expert_type: '',
					adept_field: '',
					adept_species: '',
					speci: '',
					trade: '',
					product: '',
					service: '',
					coordinatePoint:'',
					single:true,
				},
				expertInfo3: {
					honor_certificate_picture_list: []
				},
				expertInfo4: {
                    qualification_certificate_picture_list: []
                },
                expertInfo5: {
                    personal_picture_list: []
                },
				ruleInline2: {
					expert_type: [{
						required: true,
						message: '请选择专家分类',
						trigger: 'change'
					}],
					adept_field: [{
						required: true,
						message: '请输入擅长领域',
						trigger: 'blur'
					}],
					adept_species: [{
						required: true,
						message: '请选择擅长物种'
					}],
					speci: [{
						required: true,
						message: '请选择相关物种'
					}],
					/*product: [{
						required: true,
						message: '请选择相关商品'
					}],*/
					trade: [{
						required: true,
						message: '请选择相关行业'
					}],
					service: [{
						required: true,
						message: '请选择相关服务'
					}]
				},
				
				ruleInline3: {
                    honor_certificate_picture_list: [
                        { required: true, message: '请上传荣誉证书', type: 'array', min: 1, trigger: 'change' }
                    ]
                },
                ruleInline4: {
                    qualification_certificate_picture_list: [
                        { required: true, message: '请上传资质证书', type: 'array', min: 1, trigger: 'change' }
                    ]
                },
                ruleInline5: {
                    personal_picture_list: [
                        { required: true, message: '请上传个人照片', type: 'array', min: 1, trigger: 'change' }
                    ]
                },
				positionList: [{
						value: '董事长',
						label: '董事长'
					},
					{
						value: '总经理',
						label: '总经理'
					},
					{
						value: '销售经理',
						label: '销售经理'
					},
					{
						value: '人事经理',
						label: '人事经理'
					},
					{
						value: '文员',
						label: '文员'
					},
					{
						value: '会计',
						label: '会计'
					}
				],
				expertTypeList: [
					{
						value: '农业',
						label: '农业'
					},
					{
						value: '畜牧',
						label: '畜牧'
					},
					{
						value: '渔业',
						label: '渔业'
					},
					{
						value: '林业',
						label: '林业'
					},
					{
						value: '土肥',
						label: '土肥'
					},
					{
						value: '饲料',
						label: '饲料'
					},
					{
						value: '农机',
						label: '农机'
					},
					{
						value: '法律',
						label: '法律'
					},
					{
						value: '兽药',
						label: '兽药'
					},
					{
						value: '加工',
						label: '加工'
					},
					{
						value: '农药',
						label: '农药'
					},
					{
						value: '包装',
						label: '包装'
					},
					{
						value: '质检',
						label: '质检'
					},
					{
						value: '运输',
						label: '运输'
					},
					{
						value: '信息传输',
						label: '信息传输'
					},
					{
						value: '金融',
						label: '金融'
					},
					{
						value: '保险业',
						label: '保险业'
					}
				],
				adeptFieldList: [{
						value: '产前领域：种子、饲料、农药、农田、农田水利',
						label: '产前领域：种子、饲料、农药、农田、农田水利'
					},
					{
						value: '产中领域：农产品生产',
						label: '产中领域：农产品生产'
					},
					{
						value: '产后领域 ：农产品加工与流通',
						label: '产后领域 ：农产品加工与流通'
					}
				],
				adeptSpeciesList: [{
						value: 'tuzi',
						label: '兔子'
					},
					{
						value: 'mianyang',
						label: '绵羊'
					},
					{
						value: 'qingcai',
						label: '青菜'
					}
				],
				tradeResultDatas: [],
				serviceResultDatas: [],
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
				],
				speciResultDatas2: []
			}
		},
		created() {
            // 从我的风采导入图片 默认显示第一个相册
            this.getAlbum('identityPhoto',this.l)
            this.getAlbum('qualificationPhoto',this.l2)
            this.getAlbum('personPhoto',this.l3)

			/*
			 * TODO 表单具体的验证（有粗略的验证）
			 * TODO 请求后台接口获取下拉框的数据
			 * TODO 请求后台接口获取我的资料用于导入本页面
			 * */

			// 取相关行业数据
			/* api.post('/member/industry/findByCharacter', {
				character: '',
				keyword: ''
			}).then(res => {
				var data = res.data
				data.forEach((item, index) => {
					item.checked = false
				})
				this.tradeResultDatas = data
			}) */
			// 取行业类型数据
			this.$api.post('/member/system-dict/getSystemDict', {
				typeName:'行业分类',
				pageNum: 1
			}).then(res => {
				console.log('reshhh', res)
				var data = res.data.list
				this.total3 = res.data.total
				this.tradeResultDatas = data
			})
			// 取相关服务数据
			api.post('/member/service/find', {}).then(res => {
				var data = res.data
				data.forEach((item, index) => {
					item.checked = false
				})
				this.serviceResultDatas = data
			})

			// 取相关物种结果
			this.loadSpeciResult('', '', [], this.pageCur, [])

			// 取擅长物种结果
			this.loadSpeciResult2('', '', [], this.pageCur2, [])
			
			//获得回显数据
			api.get(`/member/expertInfo/getExpertInfo/${this.loginuserinfo.loginAccount}`).then(res => {
				var data = res.data
				console.log("expert",res)
				if(data){
					this.expertInfo2.expert_type=data.expertType
					/* if(data.adeptField){
					    let arrTemp = data.adeptField
						if(arrTemp.indexOf('产前领域：种子、饲料、农药、农田、农田水利') !== -1)
                            this.expertInfo2.adept_field.push('产前领域：种子、饲料、农药、农田、农田水利')
                        if(arrTemp.indexOf('产中领域：农产品生产') !== -1)
                            this.expertInfo2.adept_field.push('产中领域：农产品生产')
                        if(arrTemp.indexOf('产后领域 ：农产品加工与流通') !== -1)
                            this.expertInfo2.adept_field.push('产后领域 ：农产品加工与流通')
						this.expertInfo2.adept_field.push(data.adeptField)
					} */
					this.expertInfo2.adept_field = data.adeptField
					this.expertInfo2.trade=data.relatedIndustry
					this.expertInfo2.product=data.relatedProduct
					this.expertInfo2.service= data.relatedService
					this.expertInfo2.adept_species = data.adeptSpecies
					this.expertInfo2.speci=data.relatedSpecies
					if(data.speciesId){
						let arr=data.speciesId.split(' ')
						arr.forEach(e=>this.speciesResult.push(e))
					}
					if(data.adeptId){
						let arr=data.adeptId.split(' ')
						arr.forEach(e=>this.adpetSpecies.push(e))
					}
					if(data.industryId){//相关行业回显
						let arr=data.industryId.split(' ')
						arr.forEach(e=>this.classfy.push(e))
					}
					if(data.serviceId){
						let arr=data.serviceId.split(' ')
						arr.forEach(e=>this.service.push(e))
					}
					if(data.listHonor.length){
						data.listHonor.forEach(val=>{
							this.expertInfo3.honor_certificate_picture_list.push({
								status:'finished',
								picName:val.addr,
								column:'荣誉',
								type:2
							})
						})
					}
					if(data.listPic.length){
						data.listPic.forEach(val=>{
							this.expertInfo5.personal_picture_list.push({
								status:'finished',
								picName:val.addr,
								column:'个人',
								type:2
							})
						})
					}
					if(data.listCertificate.length){
						data.listCertificate.forEach(val=>{
							this.expertInfo4.qualification_certificate_picture_list.push({
								status:'finished',
								picName:val.addr,
								column:'资质',
								type:2
							})
						})
					}
//				console.log("expert",data,this.expertInfo2)
				}
			})

		},
		computed: {},
		methods: {
            // 从我的风采导入图片
            handlePhotoSelectorModal (name) {
                this.$refs[name].photoSelectorShow = true
                this.$refs[name].choosed = []
            },
            albumChange (value) {
                this.p = []
                this.getPhoto({
                    mediaId: value,
                    pageNum: 1,
                    pageSize: 1000
                },this.p)
            },
            getAlbum (name, array) {
                this.$api.post('/member/product-base/media-library-query-all', {
                    account: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
                    mediaType: 1
                }).then(response => {
//                    console.log('res', response)
                    if (response.code === 200) {
                        if (response.data.length !== 0) {
                            this.$refs[name].album = response.data[0].mediaId
                        }
                        response.data.forEach(element => {
                            array.push({
                                label: element.mediaName,
                                value: element.mediaId
                            })
                        })
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            getPhoto (params,  array) {
                this.$api.post("/member/product-base/media-library-detail-query-list", params)
                    .then(response => {
//                        console.log("res", response)
                        if (response.code === 200) {
                            for (let i = 0; i < response.data.list.length; i++) {
                                array.push({
                                    id: response.data.list[i].id,
                                    src: response.data.list[i].mediaUrl,
                                    disable: false
                                })
                            }
                        }
                    })
            },
            handleGetPhotoResult (result) {
                console.log('this.$refs.upload.fileList', this.$refs.upload.fileList)
                // 用于限制最大可上传照片的张数
                if (this.expertInfo3.honor_certificate_picture_list.length + result.length > 50) {
                    this.$Notice.warning({
                        title: '最多只能上传50张图片'
                    })
                } else {
                    result.forEach(element => {
                        this.expertInfo3.honor_certificate_picture_list.push({
                            column:'荣誉',
                            type:2,
                            picName:element,
                            status:'finished'
                        })
                    })
                }
                this.handleSubmit('expertInfo3')
            },
            albumChange2 (value) {
                this.p2 = []
                this.getPhoto({
                    mediaId: value,
                    pageNum: 1,
                    pageSize: 1000
                }, this.p2)
            },
            handleGetPhotoResult2 (result) {
//                console.log('photo2', result)
                // 用于限制最大可上传照片的张数
                if (this.expertInfo4.qualification_certificate_picture_list.length + result.length > 50) {
                    this.$Notice.warning({
                        title: '最多只能上传 50 张图片'
                    })
                } else {
                    result.forEach(element => {
                        this.expertInfo4.qualification_certificate_picture_list.push({
                            column:'资质',
                            type:2,
                            picName:element,
                            status:'finished'
                        })
                    })
                }
                this.handleSubmit('expertInfo4')
            },
            albumChange3 (value) {
                this.p3 = []
                this.getPhoto({
                    mediaId: value,
                    pageNum: 1,
                    pageSize: 1000
                }, this.p3)
            },
            handleGetPhotoResult3 (result) {
//                console.log('photo3', result)
                // 用于限制最大可上传照片的张数
                if (this.expertInfo5.personal_picture_list.length + result.length > 50) {
                    this.$Notice.warning({
                        title: '最多只能上传 50 张图片'
                    })
                } else {
                    result.forEach(element => {
                        this.expertInfo5.personal_picture_list.push({
                            column:'个人',
                            type:2,
                            picName:element,
                            status:'finished'
                        })
                    })
                }
                this.handleSubmit('expertInfo5')
            },

			preStep(){
                let type=this.$route.meta.type
                if(1 === type){
                    this.$parent.$parent.$parent.$router.push('/pro/member/progress23/progress32')
                }else{
                    this.$parent.$parent.$parent.$router.push('/pro/member/step23/step32')
                }
			},
			pass(){
				let type=this.$route.meta.type
				if(1 === type){
					this.$parent.$parent.$parent.gotoPathSec(34)
				}else{
					this.$parent.$parent.$parent.gotoPath(34)
				}
			},
			 // 回退
            back() {
                this.$router.push({
                    path: '/member/registerIndex'
                })
            },
			// 表单验证
			handleSubmit(name) {
				let flag = false
				this.$refs[name].validate((valid) => {
					if(valid) {
						flag = true
					}
				})
				return flag
			},

			// 提交
			submit() {
				let flag2 = this.handleSubmit('expertInfo2')
				let flag3 = this.handleSubmit('expertInfo3')
				let flag4 = this.handleSubmit('expertInfo4')
				let flag5 = this.handleSubmit('expertInfo5')
				if(flag2 && flag3 && flag4 && flag5) {
					let data = {
						expert_type: this.expertInfo2.expert_type,
						adept_field: this.expertInfo2.adept_field,
						adept_species: this.adpetSpecies.join(' '),
						speci: this.speciesResult.join(' '),
						trade: this.classfy.join(' '),
						product: this.expertInfo2.product,
						service: this.service.join(' '),

						honor_certificate_picture_list: this.expertInfo3.honor_certificate_picture_list,
						qualification_certificate_picture_list:this.expertInfo4.qualification_certificate_picture_list,
						personal_picture_list: this.expertInfo5.personal_picture_list
					}
					console.log('data', data)
					let _that = this
					api.post('/member/expertInfo/saveExpert', data).then(response => {
//						console.log(response)
						if(response.data === 1) {
							if(this.loginuserinfo.userType === 0){
								this.modal2=true
							}else{
								this.pass()
							}
						}else{
							_that.$Message.error('注册失败!')
						}
					})
				}
			},
			asyncOK(){
				this.pass()
			},
			handleBeforeUpload() {
				const check = this.expertInfo3.honor_certificate_picture_list.length < 50
				if(!check) {
					this.$Notice.warning({
						title: '最多只能上传 50 张图片。'
					})
				}
				return check
			},
			handleBeforeUpload2() {
				const check = this.expertInfo4.qualification_certificate_picture_list.length < 50
				if(!check) {
					this.$Notice.warning({
						title: '最多只能上传 50 张图片。'
					})
				}
				return check
			},
			handleBeforeUpload3() {
				const check = this.expertInfo5.personal_picture_list.length < 50
				if(!check) {
					this.$Notice.warning({
						title: '最多只能上传 50 张图片。'
					})
				}
				return check
			},
			// 图片上传
			handleSuccess(response, file, fileList) {
				if (response.code === 500) {
                    this.$Message.error('上传失败!')
                } else {
                    this.$Message.success('上传成功!')
                    	this.expertInfo3.honor_certificate_picture_list.push({
                    	    column:'荣誉',
                    	    type:2,
                    	    picName:'http:'+ response.data.picName,
                    	     status:'finished'
                    	})
                    this.handleSubmit('expertInfo3')
                }
			},
			handleSuccess2(response, file, fileList) {
				if(response.code === 500) {
					this.$Message.error('上传失败!')
				} else {
					this.$Message.success('上传成功!')
                    	this.expertInfo4.qualification_certificate_picture_list.push({
                    	    column:'资质',
                    	    type:2,
                    	    picName:'http:'+ response.data.picName,
                    	    status:'finished'
                    	})
//					console.log("资质",this.expertInfo4.qualification_certificate_picture_list)
					this.handleSubmit('expertInfo4')
				}
			},
			handleSuccess3(response, file, fileList) {
				if(response.code === 500) {
					this.$Message.error('上传失败!')
				} else {
					this.$Message.success('上传成功!')
                    	this.expertInfo5.personal_picture_list.push({
                    	    column:'个人',
                    	    type:2,
                    	    picName:'http:'+response.data.picName,
                    	    status:'finished'
                    	})
                  
//					console.log("个人-up",this.expertInfo5.personal_picture_list,response)
					this.handleSubmit('expertInfo5')
				}
			},
			// 图片删除
			handleRemove(file) {
				// 先从服务器上把图片删除
				const fileList = this.expertInfo3.honor_certificate_picture_list
				this.expertInfo3.honor_certificate_picture_list.splice(fileList.indexOf(file), 1)
                this.$refs.upload.fileList.splice(this.$refs.upload.fileList.indexOf(file), 1)
				this.handleSubmit('expertInfo3')
			},
			handleRemove2(file) {
				const fileList = this.expertInfo4.qualification_certificate_picture_list
				this.expertInfo4.qualification_certificate_picture_list.splice(fileList.indexOf(file), 1)
				this.handleSubmit('expertInfo4')
			},
			handleRemove3(file) {
				const fileList = this.expertInfo5.personal_picture_list
				this.expertInfo5.personal_picture_list.splice(fileList.indexOf(file), 1)
				this.handleSubmit('expertInfo5')
//				console.log("个人-up",this.expertInfo5.personal_picture_list)
			},
			// 高级搜索弹窗
            handleFilterModal (name) {
                this.$refs[name].highFilterShow = true
            },
			// 相关行业搜索
			/* handleTradeSearch(letter, keyword, result) {
				api.post(`/member/industry/findByCharacter`, {
					character: letter === '全部' ? '' : letter,
					keyword: keyword
				}).then(res => {
					var d = res.data
					this.tradeResultDatas = []
					if(!d.length) return
					if(result.length) {
						result.forEach(item => {
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
					this.tradeResultDatas = d
					this.$Message.success('筛选完成！')
				})
			},
			handleGetTradeResult(classify, result) {
				var arr = []
				this.classfy = []
				result.forEach(item => arr.push(item.label))
				this.expertInfo2.trade = arr.join(' ')
				result.forEach(item => this.classfy.push(item.value))
				
			}, */
			// 行业类型
            handleTradeSearch (letter, keyword, result) {
                this.$api.post(`/member/system-dict/getSystemDict`, {
					dictName : keyword,
					typeName : '行业分类',
					character: letter === '全部' ? '' : letter,
					pageNum: 1
				}).then(res => {
					var d = res.data.list
					this.total3 = res.data.total
					this.pageCur3 = 1
					this.tradeResultDatas = []
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
					this.tradeResultDatas = d
				})
            },
            handleGetTradeResult (classify, result) {
                var arr = []
				this.classfy = []
				result.forEach(item => arr.push(item.label))
				this.expertInfo2.trade = arr.join(' ')
				result.forEach(item => this.classfy.push(item.value))
            },
            handleTradePageChange (letter, keyword, classify, num, result) {
                this.pageCur3 = num
                this.$api.post('/member/system-dict/getSystemDict', {
					typeName:'行业分类',
					character: letter === '全部' ? '' : letter,
					dictName: keyword,
					pageNum: num
				}).then(res => {
					var data = res.data.list
					this.total3 = res.data.total
					this.tradeResultDatas = data
				})
                this.$Message.success('筛选完成！')
            },
			// 相关服务
			handleServiceSearch(letter, keyword, classify) {
				api.post(`/member/service/find`, {
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
				this.service = []
				result.forEach(item => arr.push(item.label))
				this.expertInfo2.service = arr.join(' ')
				result.forEach(item => this.service.push(item.value))
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
				this.expertInfo2.speci = arr.join(' ')
				
			},
			handleSpeciPageChange(letter, keyword, classify, num, result) {
				this.pageCur = num
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
				this.$Message.success('筛选完成！')
			},
			loadSpeciDatas(item, callback) {
				item.loading = true
				api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
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
				api.post('/member/specicesClass/findSpecies', {
					keywords: keyword,
					fpinyin: letter === '全部' ? '' : letter,
					fclassifiedid: arr,
					type: type,
					pageNum: num,
					pageSize: 32
				}).then(res => {
					var data = res.data
					this.total = data.total
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
			// 擅长物种
			handleGetSpeciResult2(classify, result) {
				var arr = []
				this.adpetSpecies = []
				result.forEach(item => arr.push(item.label))
				this.expertInfo2.adept_species = arr.join(' ')
				result.forEach(item => this.adpetSpecies.push(item.value))
				
			},
			handleSpeciSearch2(letter, keyword, classify, result) {
				this.loadSpeciResult2(letter, keyword, classify, this.pageCur2, result)
				this.$Message.success('筛选完成！')
			},
			handleGetSpeciClassify2(letter, keyword, classify, result) {
				this.loadSpeciResult2(letter, keyword, classify, this.pageCur2, result)
				this.$Message.success('筛选完成！')
			},
			handleSpeciPageChange2(letter, keyword, classify, num, result) {
				this.pageCur2 = num
				this.loadSpeciResult2(letter, keyword, classify, this.pageCur2, result)
				this.$Message.success('筛选完成！')
			},
			loadSpeciResult2(letter, keyword, classify, num, result) {
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
				api.post('/member/specicesClass/findSpecies', {
					keywords: keyword,
					fpinyin: letter === '全部' ? '' : letter,
					fclassifiedid: arr,
					type: type,
					pageNum: num,
					pageSize: 32
				}).then(res => {
					var data = res.data
					this.total2 = data.total
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
					this.speciResultDatas2 = data.list
				})
			},
			
		}
	}
</script>

<style lang="scss" scoped>
 .wrapper{
 	.uploadPicture{
 		width: 58px;
 		height:58px;
 		line-height: 58px;
 		
 	}
 	.upload-img-cer{
	  	border: 1px !important;
        height: 170px;
        width: 170px;
        background: #F6F6F6;

 	}
 	.demo-upload-list{
 		width: 170px !important;
 		height: 170px !important;
 	}
	 .upload-add-div1 {
		 border: 1px;
		 height: 170px;
		 width: 170px;
		 background: #F6F6F6;
	 }
	 .photo-selector1 {
		 display: inline-block;
		 position: relative;
		 text-align: center;
		 vertical-align: top;
		 border: 1px #dddee1 dashed;
		 cursor: pointer;
	 }
	 .photo-selector1:hover {
		 border: 1px #00c587 dashed;
	 }
 }
     
</style>