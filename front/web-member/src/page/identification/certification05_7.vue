
<template>
	<div style="text-align: center;color: #666;" class="mt30">
		<img src="../../img/developing.png" class="mb30" height="200"/>
		<h1 class="mb30">开发中,敬请期待···！</h1>
		<!--<div style="text-align: center;margin: 20px 0 40px 0" class="vui-collapse-wrap">
			<div v-for="(c,index) in con" class="mb10">
				<div class="vui-flex vui-flex-center vui-flex-middle hd" v-show="c.sch0" @click="show(index)">
					<p class="vui-flex-item tl">
						{{c.space === '' ? '请填写相关信息' :c.content1}}
					</p>
					<i class="ivu-icon ivu-icon-chevron-down"></i>
				</div>
				<div v-show="c.sch1">
					<div style="margin-bottom: 16px" class="content">
						<span style="font-size: 14px;padding-right: 6px;text-align: right">种养物种</span>
						<span style="font-size: 14px;width: 56px;display: inline-block;text-align: left;margin-right: 50px">
							<Select v-model="c.species" style="width:100px" @on-change="change(index)" :transfer='transfer'>
								<Option v-for="item in wuzhong" :value="item.value" :key="item.value">{{ item.label }}
								</Option>
							</Select>-->
							<!--<Input v-model="c.species"  readonly  @on-focus="handleFilterModal('speciFilter')" @on-change="change(index)" :transfer='transfer' style="width: 120px"/>
						</span>
						<span style="font-size: 14px;padding-right: 6px;text-align: right">耕地面积(亩)</span>
						<span style="font-size: 14px;width: 56px;display: inline-block;text-align: left">
							<InputNumber :max="10000" :min="0" v-model="c.space" :step="1" style="width: 100px" @on-change="change(index)"></InputNumber>
						</span>
						<span style="font-size: 14px;text-align: right;padding-left:50px;"></span>

						<i-switch style="margin-left: 30px" v-model="c.switch1" @on-change="change1(index)" size="large">
							<span slot="open">公开</span>
							<span slot="close">隐藏</span>
						</i-switch>
					</div>
				</div>
				
				
			</div>
			<div style="margin-bottom: 16px" id="add">
				<span style="font-size: 14px;padding-right: 30px;text-align: right; margin-left: -462px;color: #00c587" @click="add()">新增</span>
			</div>
		</div>
		<div v-for="(c,index) in con" v-show="c.sch1">
			<h2 style="text-align: center;padding:0 0 20px 0">实时预览</h2>
			<Input v-model="c.content" type="textarea" :rows="4" placeholder="请输入..."></Input>
		</div>-->
		
		<div class="footer-btn">
			<i-button type="primary" @click="preStep" size="large">上一步</i-button>
			<i-button type="primary" @click="pass" size="large">下一步</i-button>
			<span class="tiaoguo" @click="pass">跳过</span>
		</div>
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
				speciResultDatas: [],
				options:[{
					name:'物种',
					value:'物种'
				}],
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
			//this.loadSpeciResult("", "", [], this.pageCur, [])
		},
		methods: {
			changeType(){
				
			},
			preStep() {
                let type=this.$route.meta.type
                if(1 === type){
                    this.$parent.$parent.$parent.$router.push('/pro/member/progress23/progress28')
                }else{
                    this.$parent.$parent.$parent.$router.push('/pro/member/step23/step28')
                }
			},
			pass() {
				let type = this.$route.meta.type
				if(1 === type) {
					this.$parent.$parent.$parent.gotoPathSec(30)
				} else {
					this.$parent.$parent.$parent.gotoPath(30)
				}
			},
			//保存种养信息单条数据
			savePlant() {
				var _this = this
				this.$api.post(
					'/member/userFullInfo/savePlant', {
						plant: this.con,
						step: this.$route.path
					}
				).then(response => {
					const flag = response.code
					console.log("plant", response)
					if(flag == 200) {
						_this.saveAll()

					} else {
						_this.$Message.error('提交失败！')
					}
				})
			},
			saveAll() {
				this.$api.post(
					'/member/userFullInfo/insert', {
						plant: this.con,
					}
				).then(response => {
					const flag = response.code
					if(flag === 200) {
						this.$Message.success('提交成功！')
						this.pass()
		
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
      if (classify.length) {
        var arr = [];
        classify.forEach(item => {
          arr.push(item.classId)
        });
      } else {
        arr = null;
      }
      api.post("/member/specicesClass/findSpecies", {
          keywords: keyword,
          fpinyin: letter === "全部" ? "" : letter,
          fclassifiedid: arr,
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
    handleGetSpeciResult(classify, result) {
      var arr = [];
      result.forEach(item => arr.push(item.label))
      this.info.specy = arr.join(" ")
      
    },
			change(e) {
				if(this.con[e].space != 0) {
					this.result[e].contents.space = this.con[e].space + '亩';
					if(this.con[e].switch1 == true) {
						this.result[e].contents1.space = this.con[e].space + '亩';
					}
				}
				if(this.con[e].species != '') {
					this.result[e].contents.species = this.con[e].species;
					if(this.con[e].switch1 == true) {
						this.result[e].contents1.species = this.con[e].species;
					}
				}
				this.con[e].content = '种植：' + this.result[e].contents.species + this.result[e].contents.space;
				this.con[e].content1 = '种植：' + this.result[e].contents1.species + this.result[e].contents1.space;
			},
			change1(e) {
				if(this.con[e].space != 0 & this.con[e].switch1 == true) {
					this.result[e].contents1.space = this.con[e].space + '亩,';
				} else {
					this.result[e].contents1.space = 0
				}
				if(this.con[e].species != '' & this.con[e].switch1 == true) {
					this.result[e].contents1.species = this.con[e].species;
				} else {
					this.result[e].contents1.species = ''
				}
				
			this.con[e].content ='种养物种：'+this.result[e].contents.species +'，耕地面积：' + this.result[e].contents.space ;
			this.con[e].content1 ='种养物种：'+this.result[e].contents1.species + '，耕地面积：' + this.result[e].contents1.space;
			},
			
		add() {
			var len = this.con.length
			this.con[len - 1].sch0 = true
			this.con[len - 1].sch1 = false
			var newcon = {
				content: '',
				content1: '',
				switch1: true,
				sch0: false,
				sch1: true,
				species: '',
				space: 0,
			};
			this.con.push(newcon);
				var newresult = {
					contents: {
						species: '',
						space: 0
					},
					contents1: {
						species: '',
						space: 0,
					}
				}
				this.result.push(newresult)
			},
			show(e) {
				var obj = event.target;
				var this_ = $(obj).parent().next();
				$(this_).fadeToggle("slow", "linear", function() {
					$(this_).addClass("intro1")
				})
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
</style>