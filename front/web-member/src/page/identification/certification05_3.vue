<template>
	<div id="cer5">
		<div style="margin: 20px">
			<Form ref="formInline" label-position="right"  :rules="infoline"  :label-width="80" :model="info" inline>
				<Row>
					<Col span="12">
						<FormItem label="上传头像">
							<div style="float: left;">
								<Upload ref="upload" :show-upload-list="false"
									name="upfile"
									:format="['jpg', 'png']"
									:max-size="2048"
									multiple
									:on-success="handleSuccess"
									:before-upload="handleBeforeUpload"
									:on-format-error="handleFormatError"
									:on-exceeded-size="handleMaxSize"
									type="drag"
									:action="action">
									<Button type="primary" shape="circle" style="margin-top: 2px;" size="small" :disabled="size === 1">选择图片</Button>
								</Upload>
								<Button type="primary" shape="circle" style="margin-top: 2px;" size="small" :disabled="size === 1" @click.native="handlePhotoSelectorModal('identityPhoto')">从文件管理导入</Button>
							</div>
							<div class="demo-upload-list" v-for="(item,index) in uploadList" :key="index" style="margin-left: 10px;">
								<template v-if="item.status === 'finished'">
									<img :src="item.response.data.picName">
									<div class="demo-upload-list-cover">
										<Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
									</div>
								</template>
								<template v-else>
									<Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
								</template>
							</div>
						</FormItem>
					</Col>
					<Col span="12">
						<FormItem label="姓名">
							<Input v-model="info.name" readonly  style="width: 200px" v-if="info.name != ''"/>
							<Input value="未实名认证" readonly  style="width: 200px" v-else/>
							<i-switch style="margin-left: 30px" v-model="info.switch2" size="large">
								<span slot="open">公开</span>
								<span slot="close">隐藏</span>
							</i-switch>
						</FormItem>
					</Col>
				</Row>
				<FormItem label="性别">
					<Select v-model="info.sex" style="width:200px" :transfer='transfer'>
						<Option v-for="(item,index) in sexs" :key="index" :value="item.value">{{ item.label }}</Option>
					</Select>
					<i-switch style="margin-left: 30px" v-model="info.switch3" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</FormItem>
				<FormItem label="民族">
					<Select v-model="info.family" style="width:200px" :transfer='transfer' filterable>
						<Option v-for="(f,index) in families" :value="f.value" :key="index">{{ f.label }}</Option>
					</Select>
					<i-switch style="margin-left: 30px" v-model="info.switch4" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</FormItem>
				<FormItem label="生日">
					<DatePicker type="date" :options="options3" style="width: 200px" v-model="info.birth" :transfer='transfer'></DatePicker>
					<i-switch style="margin-left: 30px" v-model="info.switch5" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</FormItem>
				<FormItem label="职业">
					<Input v-model="info.profession" style="width:200px"  @on-focus="handleFilterModal('professionFilter')" />
					<i-switch style="margin-left: 30px" v-model="info.switch8" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</FormItem>
				<FormItem label="职称">
					<Input v-model="info.zhicheng" style="width:200px"  @on-focus="handleFilterModal('zhichengFilter')" />
					<i-switch style="margin-left: 30px" v-model="info.switch7" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</FormItem>
				<FormItem label="擅长物种">
					<Input v-model="info.specy" readonly @on-focus="handleFilterModal('speciFilter')" style="width: 200px" />
					<i-switch style="margin-left: 30px" v-model="info.switch6" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</FormItem>
				<FormItem label="手机号码" prop="phone">
					<Input v-model="info.phone" style="width: 200px" :maxlength="11" />
					<i-switch style="margin-left: 30px" v-model="info.switch1" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</FormItem>
				<FormItem label="常驻地">
					<vui-cascander :values="info.addr" @handle-get-result="handleGetData" style="width: 200px; float: left;"></vui-cascander>
					<i-switch style="margin-left: 30px" v-model="info.switch9" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</FormItem>
				<FormItem prop="coordinatePoint" label="坐标位置">
					<Input v-model="info.coordinatePoint" readonly @on-focus="onSelectPoint" style="width: 200px;" />
					<i-switch style="margin-left: 30px" v-model="info.switch10" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</FormItem>
				<FormItem label="邮政编码" prop="postalcode">
					<Input v-model="info.postalcode" :maxlength="6" style="width:200px;"/>
					<i-switch style="margin-left: 30px" v-model="info.switch11" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</FormItem>
				<FormItem label="座机号码" prop="zuoji">
					<Input v-model="info.zuoji" placeholder="027-88888888" :maxlength="15" style="width:200px;"/>
					<i-switch style="margin-left: 30px" v-model="info.switch12" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
				</FormItem>
			</Form>
		</div>
		<div style="text-align: center;">
			<h2>实时预览</h2>
			<Input v-model="con.content" type="textarea" :rows="4"  style="width: 90%;" />
		</div>

		<!-- 相关物种 -->
		<vui-filter ref="speciFilter" :cols="2" :num="4" :pageShow="true" :total="total" :pageCur="pageCur" :classifyDatas="speciClassifyDatas" :resultDatas="speciResultDatas" :load-data="loadSpeciDatas" @on-search="handleSpeciSearch" @on-get-classify="handleGetSpeciClassify" @on-get-result="handleGetSpeciResult" @on-page-change="handleSpeciPageChange" />
		<!--
        	作者：786905664@qq.com
        	时间：2018-01-27
        	描述：地图
        -->
		<vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>

		<div class="footer-btn" v-if="base">
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="saveInfo" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>
		<div class="footer-btn" v-if="!base">
			<i-button type="primary" @click="saveInfo" size="large">确定</i-button>
		</div>
		  <!-- 照片选择 -->
        <photoSelector
        ref="identityPhoto"
        :photoAlbum="l"
        @on-change="albumChange"
        @on-get-result="handleGetPhotoResult"
        :resultDatas="p"
        />
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
	import api from "~api"
	import vuiFilter from "~components/vuiFilter/filter"
	import vuiMap from '../member/components/productionMap'
	import photoSelector from '~components/photoSelector'
	import vuiCascander from '~components/vuiCascader/index'
	export default {
		name: "cer5_3",
		components: {
			vuiFilter,
			vuiMap,
			photoSelector,
			vuiCascander
		},
		props:{
			base:{
				type:Boolean,
				default:true
			}
		},
		data() {
			const checkModle = (rules, value, callback) => {
				var modle = /^1(3|4|5|6|7|8|9)\d{9}$/;
                if (value && !modle.test(value)) {
                    return callback(new Error('请填写正确的手机号'));
                }else{
					callback();
				}
			};
			const checkPhone = (rules, value, cb) => {
				this.$store.dispatch("checkPhone", { rules, value, cb });
			};
			// 自定义用户名验证
			const checkPostalcode = (rule, value, callback) => {
				var uPattern = /^[0-9]{6}$/
				if (value && !uPattern.test(value)) {
					return callback(new Error('请填写正确的邮政编号'));
				}else {
					callback()
				}
			}
			return {
                zhichengResultDatas:[],
                professionResultDatas:[],
                total2: 0,
                pageCur2: 1,
                total3: 0,
                pageCur3: 1,
				format: ["jpg", "jpeg", "png"],
				pageCur: 1,
				total: 0,
				transfer: true,
				cityList: [],
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
				action: `${this.$url.upload}/upload/up`,
				transfer: true,
				options3: {
					disabledDate(date) {
						return date && date.valueOf() > Date.now() - 86400000;
					}
				},
				infoline:{
					phone:[{
						validator: checkModle,
						trigger: 'blur'
					}],
					zuoji: [{
						validator: checkPhone,
						trigger: 'blur'
					}],
					postalcode:[{
						validator: checkPostalcode,
						trigger: 'blur'
					}]
				},
				info: {
					name: "",
					switch1: true,
					switch2: true,
					switch3: true,
					switch4: true,
					switch5: true,
					switch6: true,
					switch7: true,
					switch8: true,
					switch9: true,
					switch10: true,
					switch11: true,
					switch12: true,

					avatar: "",
					addr: '',
					family: "",
					sex: "",
					birth: "",
					specy: "",
					zhicheng: "",
					profession: "",
					coordinatePoint: '',
					phone:'',
					zuoji: '',
					postalcode: ''
				},
				info1: {
					name: "",
					avatar: "",
					addr: "",
					family: "",
					sex: "",
					birth: "",
					specy: "",
					zhicheng: "",
					profession: "",
					coordinatePoint: '',
					phone:'',
					zuoji: '',
					postalcode: ''
				},
				con: {
					content: "", //保存所有数据
					content1: "" //保存公开可见的数据
				},
				resultValue: [],
				size: 0,
				//头像上传
				uploadList: [],
				modal1: false,
				families: [],
				professions: [{
						value: "会计",
						label: "会计"
					},
					{
						value: "公务员",
						label: "公务员"
					},
					{
						value: "医生",
						label: "医生"
					},
					{
						value: "律师",
						label: "律师"
					}
				],
				species: [],
				sexs: [{
						value: "男",
						label: "男"
					},
					{
						value: "女",
						label: "女"
					}
				],
				addres: '',
				l: [],
                p: []
			};
		},
		watch: {
			info: {
				handler(curVal, oldVal) {
					this.change()
					var user = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
					user.avatar = curVal.avatar
					sessionStorage.setItem("user", JSON.stringify(user))
				},
				deep: true
			},
			info1: {
				handler(curVal, oldVal) {
					this.change1()
				},
				deep: true
			}
		},
		created: function() {
			this.find()
			this.showInfo()
			this.showContact()
			this.initFamily()
			//this.initSpecices()
			// 取相关物种结果
			this.loadSpeciResult("", "", [], this.pageCur, [])
			 // 从我的风采导入图片 默认显示第一个相册
            this.getAlbum('identityPhoto')
            // 职称数据
            api.post('/member/system-dict/getSystemDict', {
                typeName:'职称',
                pageNum: 1,
                pageSize: 32
            }).then(res => {
                var data = res.data.list
                this.total3 = res.data.total
                this.zhichengResultDatas = data
            })
            // 职业数据
            api.post('/member/system-dict/getSystemDict', {
                typeName:'职业',
                pageNum: 1,
                pageSize: 32
            }).then(res => {
                var data = res.data.list
                this.total2 = res.data.total
                this.professionResultDatas = data
            })
			//查询手机号码
            api.get('/member/Certification/find').then(res=>{
				if (res.code === 200 && res.data) {
					this.info.phone = res.data.mobile
				}
			})
		},
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
                })
            },
            getAlbum (name) {
                this.$api.post('/member/product-base/media-library-query-all', {
                    account: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
                    mediaType: 1
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.length !== 0) {
                            this.$refs[name].album = response.data[0].mediaId
                        }
                        response.data.forEach(element => {
                            this.l.push({
                                label: element.mediaName,
                                value: element.mediaId
                            })
                        })
                    }
                }).catch(error => {
                    this.$Message.error('获取相册出错！')
                })
            },
            getPhoto (params) {
                this.$api.post("/member/product-base/media-library-detail-query-list", params)
                    .then(response => {
                    if (response.code === 200) {
                        for (let i = 0; i < response.data.list.length; i++) {
                            this.p.push({
                                id: response.data.list[i].id,
                                src: response.data.list[i].mediaUrl,
                                disable: false
                            })
                        }
                    }
                })
            },
            handleGetPhotoResult (result) {
                // 用于限制最大可上传照片的张数
                if (this.$refs.upload.fileList.length >= 1 || result.length > 1) {
                    this.$Notice.warning({
                        title: '头像只能上传一张'
                    })
                } else {
                    result.forEach(element => {
                        this.$refs.upload.fileList.push({
                            response: {
                                data: {
                                    picName: element
                                }
                            },
                            status: 'finished'
                        })
                    })
                   	this.uploadList = this.$refs.upload.fileList
					this.info.avatar =
						"http:" + this.$refs.upload.fileList[0].response.data.picName
					this.size = this.$refs.upload.fileList.length
                }
            },
            // 职业搜索
            handleProfessionSearch (letter, keyword, result) {
                api.post(`/member/system-dict/getSystemDict`, {
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
                api.post('/member/system-dict/getSystemDict', {
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
                this.info.profession = arr.join(' ')
                this.industryDatas = result.map(function(item){
                    return item.value
                })
            },
            // 职称搜索
            handleZhichengSearch (letter, keyword, result) {
                api.post(`/member/system-dict/getSystemDict`, {
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
                api.post('/member/system-dict/getSystemDict', {
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
                this.info.zhicheng = arr.join(' ')

                this.industryDatas = result.map(function(item){
                    return item.value
                })
            },
			preStep() {
				//this.$parent.$parent.$parent.$router.go(-1)
                let type=this.$route.meta.type
                if(1 === type){
                    this.$parent.$parent.$parent.$router.push('/pro/member/progress23/progress24')
                }else{
                    this.$parent.$parent.$parent.$router.push('/pro/member/step23/step24')
                }
			},
			pass() {
				let type = this.$route.meta.type
				if(1 === type) {
					this.$parent.$parent.$parent.gotoPathSec(26)
				} else {
					this.$parent.$parent.$parent.gotoPath(26)
				}
			},
			saveInfo() {
				var _this = this
				this.$refs['formInline'].validate((valid) => {
					if (valid) {
						var path = ''
						if(this.base){
							// 重启认证
							path = this.$route.path
						}
						api.post(
							'/member/userFullInfo/info', {
								name: this.info.name,
								statusN: this.info.switch2,
								statusPhone: this.info.switch1,
								addr: this.info.addr,
								statusAddr: this.info.switch9,
								family: this.info.family,
								sex: this.info.sex === '女' ? '0' : '1',
								statusE: this.info.switch3,
								birth: this.moment(this.info.birth).format('YYYY-MM-DD'),
								statusB: this.info.switch5,
								statusR: this.info.switch4,
								specy: this.resultValue.join(' '),
								statusS: this.info.switch6,
								zhicheng: this.info.zhicheng,
								statusA: this.info.switch7,
								profession: this.info.profession,
								statusJ: this.info.switch8,
								avatar: this.info.avatar,
								phone:this.info.phone,
								statusPhone:this.info.switch1,
								point:this.info.coordinatePoint,
								statusCoord:this.info.switch10,
								mobile: this.info.zuoji,
								mobileStatus: this.info.switch11,
								postalcode: this.info.postalcode,
								postalcodeStatus: this.info.switch12,
								step: path
							}
						).then(response => {
							const flag = response.code
							if(flag === 200) {
								this.saveAll()
							} else {
								_this.$Message.error('提交失败！')
							}
						})
					}
				})
			},
			saveAll() {
				api.post(
					'/member/userFullInfo/insert', {
						contract: this.con.content,
						contract1: this.con.content1,
					}
				).then(response => {
					const flag = response.code
					if(flag === 200) {
						this.$Message.success('提交成功！')
						if(this.base){
							// 重启认证
							this.pass()
						}else{
							// 我的资料
							this.$emit('success')
						}
					} else {
						this.$Message.error('提交失败！')
					}
				})
			},
			// 相关物种
			handleFilterModal(name) {
				this.$refs[name].highFilterShow = true
			},
			// 取相关物种
			handleSpeciSearch(letter, keyword, classify, result) {
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
			},
			loadSpeciDatas(item, callback) {
				item.loading = true;
				api.post(`/member/specicesClass/findByParentId/${item.value}`)
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
				api.post("/member/specicesClass/findSpecies", {
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
				this.info.specy = arr.join(" ")
			},
			// 取坐标
			onGetPoint(point) {
				if(point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
					this.info.coordinatePoint = `${point.lng},${point.lat}`
				} else {
					this.info.coordinatePoint = ''
				}
			},
			onSelectPoint() {
				this.$refs.experMap.showMap = true
			},
			//info为绑定的全部数据，content为其拼接后的字符串，
			//info1为出去隐藏后的所有数据，content1为其拼接后的字符串，
			//当info发生改变的时候，同步改变content与info1的拼接数据
			change() {
				if(this.info.avatar) {
					this.con.content = this.info.avatar
					this.info1.avatar = this.info.avatar
				}
				if(this.info.name) {
					this.con.content = '姓名：' + this.info.name
					if(this.info.switch2) {
						this.info1.name = '姓名：' + this.info.name
					} else {
						this.info1.name = ""
					}
				} else {
					this.con.content = ""
				}
				if(this.info.sex) {
					this.con.content += "，性别:" + this.info.sex
					if(this.info.switch3) {
						this.info1.sex = "，性别:" + this.info.sex
					} else {
						this.info1.sex = ""
					}
				}
				if(this.info.family) {
					this.con.content += "，名族:" + this.info.family
					if(this.info.switch4) {
						this.info1.family = "，名族:" + this.info.family
					} else {
						this.info1.family = ""
					}
				}
				if(this.info.birth) {
					this.con.content += '，生日：' +
						 this.moment(this.info.birth).format("YYYY-MM-DD")
					if(this.info.switch5) {
						this.info1.birth ='，生日：' + this.moment(this.info.birth).format("YYYY-MM-DD")
					} else {
						this.info1.birth = ""
					}
				}
				if(this.info.specy) {
					this.con.content += "，擅长物种：" + this.info.specy
					if(this.info.switch6) {
						this.info1.specy ="，擅长物种：" + this.info.specy
					} else {
						this.info1.specy = ""
					}
				}
				if(this.info.zhicheng) {
					this.con.content += "，职称：" + this.info.zhicheng
					if(this.info.switch7) {
						this.info1.zhicheng = "，职称：" +this.info.zhicheng
					} else {
						this.info1.zhicheng = ""
					}
				}
				if(this.info.profession) {
					this.con.content += "，职业：" + this.info.profession
					if(this.info.switch8) {
						this.info1.profession = "，职业：" +this.info.profession
					} else {
						this.info1.profession = ""
					}
				}
				if(this.info.addr.length) {
					this.con.content += "，常驻地：" + this.info.addr
					if(this.info.switch9) {
						this.info1.addr = "，常驻地：" +this.info.addr
					} else {
						this.info1.addr = ""
					}
				}

				if(this.info.coordinatePoint) {
					this.con.content += "，坐标位置：" + this.info.coordinatePoint
					if(this.info.switch10) {
						this.info1.coordinatePoint = "，坐标位置：" +this.info.coordinatePoint
					} else {
						this.info1.coordinatePoint = ""
					}
				}
				if(this.info.phone) {
					this.con.content += "，手机号码：" + this.info.phone
					if(this.info.switch1) {
						this.info1.phone = "，手机号码：" +this.info.phone
					} else {
						this.info1.phone = ""
					}
				}
				if(this.info.zuoji) {
					this.con.content += '，座机号码：' + this.info.zuoji
					if(this.info.switch2) {
						this.info1.zuoji = '，座机号码：' + this.info.zuoji
					} else {
						this.info1.zuoji = ''
					}
				}
				if(this.info.postalcode) {
					this.con.content += '，邮政编码：' + this.info.postalcode
					if(this.info.switch2) {
						this.info1.postalcode = '，邮政编码：' + this.info.postalcode
					} else {
						this.info1.postalcode = ''
					}
				}
			},
			//当info1发生改变时同步修改content1的拼接数据
			change1() {
				if(this.info1.name) {
					this.con.content1 = this.info1.name
				} else {
					this.con.content1 = ""
				}
				if(this.info1.sex) {
					this.con.content1 += this.info1.sex
				}
				if(this.info1.family) {
					this.con.content1 += this.info1.family
				}
				if(this.info1.birth) {
					this.con.content1 +=  this.info1.birth
				}
				if(this.info1.specy) {
					this.con.content1 +=  this.info1.specy
				}
				if(this.info1.zhicheng) {
					this.con.content1 +=  this.info1.zhicheng
				}
				if(this.info1.profession) {
					this.con.content1 +=  this.info1.profession
				}
				if(this.info1.addr) {
					this.con.content1 +=  this.info1.addr
				}
				if(this.info1.coordinatePoint) {
					this.con.content1 +=  this.info1.coordinatePoint
				}
				if(this.info1.phone) {
					this.con.content1 +=  this.info1.phone
				}
				if(this.info1.zuoji) {
					this.con.content1 += this.info1.zuoji
				}
				if(this.info1.postalcode) {
					this.con.content1 += this.info1.postalcode
				}
			},
			saveBasic() {},
			handleView(name) {
				this.imgName = name
				this.visible = true
			},
			//获得姓名
			find() {
				var _this = this
				this.$api.get("/member/Certification/find").then(response => {
					const flag = response.code
					if(flag == 200) {
						if(response.data && response.data.realname) {
							_this.info.name = response.data.realname
						}
					}
				});
			},
			//回显
			showInfo() {
				this.$api.get("/member/userFullInfo/findUserInfo").then(response => {
					console.log('response111', response)
					if(response.code === 200) {
						if(response.data.statusPhone === 0) {
							this.info.switch1 = false
						} else {
							this.info.switch1 = true
						}
						if(response.data.statusN === 0) {
							this.info.switch2 = false
						} else {
							this.info.switch2 = true
						}
						if(response.data.statusE === 0) {
							this.info.switch3 = false
						} else {
							this.info.switch3 = true
						}
						if(response.data.statusR === 0) {
							this.info.switch4 = false
						} else {
							this.info.switch4 = true
						}
						if(response.data.statusB === 0) {
							this.info.switch5 = false
						} else {
							this.info.switch5 = true
						}
						if(response.data.statusJ === 0) {
							this.info.switch8 = false
						} else {
							this.info.switch8 = true
						}
						if(response.data.statusA === 0) {
							this.info.switch7 = false
						} else {
							this.info.switch7 = true
						}
						if(response.data.statusS === 0) {
							this.info.switch6 = false
						} else {
							this.info.switch6 = true
						}
						if(response.data.statusAddr === 0) {
							this.info.switch9 = false
						} else {
							this.info.switch9 = true
						}
						if(response.data.statusCoord === 0) {
							this.info.switch10 = false
						} else {
							this.info.switch10 = true
						}

						var res = response.data;
						console.log('res111', res)
						if(res) {
							this.info.sex = res.sex === '0' ? '女' : '男';
							this.info.family = res.rece;
							this.info.birth = res.birdhday;
							this.info.specy = res.species;
							this.info.zhicheng = res.acaTitle;
							this.info.profession = res.job;
							this.info.addr = res.addr;
							this.info.avatar = res.avatar;
							this.info.phone = res.phone
							this.info.coordinatePoint = res.coordinatePoint
							if (this.info.avatar !== '' && this.info.avatar !== undefined) {
								this.size = 1
								this.uploadList.push({
									status: 'finished',
									response: {
										data: {
											picName: this.info.avatar
										}
									}
								})
							}
							if(res.speciesId) {
								let arr = res.speciesId.split(' ')
								arr.forEach(item => this.resultValue.push(item))
							}
							if(res.realName) {
								this.info.name = res.realName
							}
						}
					}
				});
			},
			//联系信息回显
			showContact() {
				api.get('/member/userFullInfo/findContact')
					.then(response => {
						if(response.data) {
							if(response.data.mobileStatus === 0) {
								this.info.switch11 = false
							} else {
								this.info.switch11 = true
							}
							if (response.data.postalcodeStatus === 0) {
								this.info.switch12 = false
							} else {
								this.info.switch12 = true
							}
							var res = response.data
							this.info.zuoji = res.mobile
							this.info.postalcode = res.postalcode
						}

					})
			},
			//获取民族数据回显
			initFamily() {
				this.$api.get("/member/race").then(response => {
					this.families = response.data
				});
			},
			handleGetData (value, selectedData) {
				let labelArr = []
				selectedData.forEach(element => {
					labelArr.push(element.label)
				})
				this.info.addr = labelArr.join('/')
			},
			// 照片大小限制
            handleMaxSize(file) {
                this.$Notice.warning({
                    title: "照片大小超出限制",
                    desc: "照片大小过大，应不超过2M。",
                    duration: 6
                });
            },
            // 照片格式限制
            handleFormatError(file) {
                this.$Notice.warning({
                    title: "照片格式有误",
                    desc: "照片格式不正确，请选择jpg、png格式。",
                    duration: 6
                });
			},
			// 图片上传
            handleSuccess(response, file, fileList) {
                if (response.code === 500) {
                    this.$Message.error('上传失败!')
                } else {
					this.$Message.success('上传成功!')
					// this.corpInfo.legal_person_picture_list.push(file)
					this.uploadList = this.$refs.upload.fileList
					console.log('shangchuan', this.uploadList)
					this.info.avatar = this.$refs.upload.fileList[0].response.data.picName
					this.size = this.$refs.upload.fileList.length
                }
			},
			// 从 upload 实例删除数据
            handleRemove (file) {
                // 先从服务器上把图片删除
				// this.corpInfo.legal_person_picture_list.splice(0, 1)
				this.size = 0
                this.$refs.upload.fileList.splice(0, 1)
			},
			handleBeforeUpload () {
                const check = this.$refs.upload.fileList.length < 1 &&
                this.uploadList.length + this.$refs.upload.fileList.length < 1
                if (!check) {
                    this.$Notice.warning({
                        title: '最多只能上传 1 张图片。'
                    })
                }
                return check
            }
		},
		mounted() {
			this.uploadList = this.$refs.upload.fileList
		}
	};
</script>

<style lang="scss" scoped>
	.demo-upload-list {
		display: inline-block;
		width: 60px;
		height: 60px;
		text-align: center;
		line-height: 60px;
		border: 1px solid transparent;
		border-radius: 4px;
		overflow: hidden;
		background: #fff;
		position: relative;
		box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
		margin-right: 4px;
	}

	.demo-upload-list img {
		width: 100%;
		height: 100%;
	}

	.demo-upload-list:hover .demo-upload-list-cover {
		display: block;
	}

	.demo-upload-list-cover {
		color: #fff;
		font-size: 16px;
		display: none;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		background: rgba(0, 0, 0, 0.6);
	}
</style>
