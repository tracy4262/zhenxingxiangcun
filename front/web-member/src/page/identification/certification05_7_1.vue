
<template>
	<div>
		<div style="text-align: center;margin: 20px 0 40px 0" class="vui-collapse-wrap">
			<div v-for="(c,index) in con" class="mb10">
				<div class="vui-flex vui-flex-center vui-flex-middle hd" v-show="c.sch0" @click="show(index)">
					<p class="vui-flex-item tl">
						{{c.space === '' ? '请填写相关信息' :c.content1}}
					</p>
					<i class="ivu-icon ivu-icon-chevron-down"></i>
				</div>
				<div v-show="c.sch1">
					<div style="margin-bottom: 16px" class="content">
						<Row>
							<Col span="18">
								<span style="font-size: 14px;padding-right: 6px;text-align: right">种养物种</span>
								<Input v-model="speci"  readonly  @on-focus="handleFilterModal('speciFilter')" :transfer='transfer' style="width: 200px"/>
							</Col>
							<Col span="6">
								<i-switch v-model="switchData" size="large">
									<span slot="open">公开</span>
									<span slot="close">隐藏</span>
								</i-switch>
							</Col>
						</Row>
						
						<Row>
							<Form :label-width="280">
								<template v-for="item in listData">
							        <FormItem :label="item.name" style="margin-top:20px;">
							            <InputNumber style="margin-left:-330px;" :min="0" v-model="item.num"></InputNumber>
										<Select v-model="item.company" @on-change="onChanged(item.name,item.num,item.company)" style="width:100px">
		        							<Option v-for="item in companyList" :value="item.value" :key="item.value">{{item.value}}</Option>
		    							</Select>
							        </FormItem>
							    </template>
						    </Form>
						</Row>
						
					</div>
				</div>
			</div>
		</div>
		<div v-for="(c,index) in con" v-show="c.sch1">
			<h2 style="text-align: center;padding:0 0 20px 0">实时预览</h2>
			<div class="ma-cerfion-show">
				<p v-for="item in textData">{{item.title}}</p>
			</div>
		</div>
		<div class="footer-btn">
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="savePlant" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>

		<!-- 相关物种 -->
	    <vui-filter
	    ref="speciFilter"
	    :cols="2"
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

	</div>
</template>

<script>
	import $ from 'jquery'
	import vuiFilter from "~components/vuiFilter/filter"
	import api from '~api'
	export default {
		components: {
		    vuiFilter
		  },
		data() {
			return {
				listData: [],
				textData: [],
				companyList: [
        			{
        				value: '只',
        			},{
        				value: '个',
        			},{
        				value: '米',
        			},
        		],
				num: 0,
				company: '',
				speci: '',
				switchData: '公开',
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
			    pageCur: 1,
      			total: 0,
				transfer: true,
				con: [{
					content: '',
					content1: '',
					sch0: false,
					sch1: true,
					switch1: true,
					species: '',
					space: 0
				}],
				result: [{
					contents: {
						species: '',
						space: 0
					},
					contents1: {
						species: '',
						space: 0
					}
				}],
				wuzhong: [{
						value: '稻谷',
						label: '稻谷'
					},
					{
						value: '玉米',
						label: '玉米'
					}
				]
			}
		},
		created: function() {
			this.loadSpeciResult("", "", [], this.pageCur, [])
		},
		methods: {
			// 选取单位 即时预览
			onChanged(){
				this.textData  = []
				this.listData.map(function(item){
					if(item.num === undefined){
						item.num = 1
					}
					if(item.company === undefined){
						item.company = ''
					}
					item.title = item.name +':' + item.num + item.company
				})
				this.textData = this.listData
			},

			// 上一步
			preStep() {
				this.$parent.$parent.$parent.$router.go(-1)
			},

			// 跳过
			pass() {
				let type = this.$route.meta.type
				if(1 === type) {
					this.$parent.$parent.$parent.gotoPathSec(30)
				} else {
					this.$parent.$parent.$parent.gotoPath(30)
				}
			},

			// 下一步 保存种养信息单条数据
			savePlant() {
				var _this = this
				this.$api.post(
					'/member/userFullInfo/savePlant', {
						plant: this.con,
						step: this.$route.path
					}
				).then(response => {
					const flag = response.code
					if(flag == 200) {

					} else {
						_this.$Message.error('提交失败！')
					}
				})
			},

			// 相关物种 取相关物种相关事件 -- start
		    handleFilterModal(name) {
		      this.$refs[name].highFilterShow = true
		    },

		    loadSpeciDatas (item, callback) {
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
		    
	        handleSpeciSearch (letter, keyword, classify, result) {
	            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
	        },

		    loadSpeciResult(letter, keyword, classify, num, result) {
		      if (classify.length) {
		        var arr = []
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
		          if (result) {
		            result.forEach(item => {
		              data.list.forEach((child, index) => {
		                if (child.label === item.label) {
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

		    handleGetSpeciClassify (letter, keyword, classify, result) {
	            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
	        },

	        handleGetSpeciResult (classify, result) {
	        	let that = this
	            var arr = []
	            result.forEach(item => arr.push(item.label))
	            this.speci = arr.join(' ')

	            this.speciesDatas = result.map(function(item){
	                return item.value
	            })

	            that.listData = []
	            result.forEach(function(item,index){
	            	that.listData[index] = {
	            		name: item.label
	            	}
	            })
	        },

	        handleSpeciPageChange (letter, keyword, classify, num, result) {
	            this.pageCur = num
	            this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
	        },
	        // 取相关物种相关事件 -- end

		    // 隐藏-公开
			change1(e) {
				
			}
		}	
	}
</script>
<style scoped>
	.dropbtn {
		line-height: 30px;
		font-size: 16px;
		border: none;
		cursor: pointer;
		padding-right: 18px;
		color: #666;
		outline: none;
	}
	.ma-cerfion-p{
		margin: 10px 0 0 20px;
	}
	.ma-cerfion-show{padding: 10px;
	    border: 1px solid #efefef;
	    border-radius: 5px;
	    line-height: 24px;
	    min-height: 150px;
	}
</style>