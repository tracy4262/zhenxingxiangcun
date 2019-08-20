<template>
	<!--3设置栏目第五步开始-->
	<div>
		<div style="padding: 0 38px">
			<Row>
				<Col span="12" style="border: 1px solid #ededed;width: 358px;margin-right: 26px;">
				<div style="margin-left: 128px;">
					<h2 style="line-height: 52px">选择产品类型</h2>
					<div class="zhuce-list">
						<Tree :data="products" show-checkbox multiple @on-check-change="changePro" ref="products"></Tree>
					</div>
					<div style="margin: 14px">
						<Button type="default" size="small" @click="modal1=true">添加</Button>&nbsp;&nbsp;&nbsp;
						<Button type="error" size="small" @click="del">删除</Button>
					</div>
					<Modal v-model="modal1" width="450" :closable="true" :mask-closable="false" class="gex" @on-ok="ok">
						<Row style="margin-top: 20px;padding: 0 20px;text-align:left;font-size: 12px;">
							<span>当前选中：
								<span v-for="(c,index) in leibie" style="color: red;" :key="index">*{{c}};</span>
							</span>
						</Row>
						<hr />
						<Collapse accordion @on-change="getname1">
							<Panel name="农资产品">
								农资产品
								<div slot="content">
									<Tabs v-model="tabname" size="small" :animated="false" @on-click="getname">
										<Tab-pane label="种用" name="种用">
											<span class="checked" @click="savea()">粮食类 </span>
											<span class="checked" @click="savea()">纤维类</span>
											<span class="checked" @click="savea()">油料类</span>
											<span class="checked" @click="savea()">瓜果蔬类</span>
											<span class="checked" @click="savea()">菌藻类</span>
										</Tab-pane>
										<Tab-pane label="农药" name="农药">
											<span class="checked" @click="savea()">杀虫剂 </span>
											<span class="checked" @click="savea()">除草剂</span>
											<span class="checked" @click="savea()">杀螨剂</span>
											<span class="checked" @click="savea()">增效剂</span>
										</Tab-pane>
										<Tab-pane label="肥料" name="肥料">
											<span class="checked" @click="savea()">氮肥 </span>
											<span class="checked" @click="savea()">磷肥</span>
											<span class="checked" @click="savea()">钾肥</span>
											<span class="checked" @click="savea()">复合肥料</span>
										</Tab-pane>
										<Tab-pane label="兽药" name="兽药">
											<span class="checked" @click="savea()">中兽药方剂 </span>
											<span class="checked" @click="savea()">化学药品</span>
										</Tab-pane>
										<Tab-pane label="饲料" name="饲料">
											<span class="checked" @click="savea()">配合饲料 </span>
											<span class="checked" @click="savea()">饲料添加剂</span>
											<span class="checked" @click="savea()">动物性蛋白质饲料</span>
											<span class="checked" @click="savea()">植物性蛋白质饲料</span>
											<span class="checked" @click="savea()">宠物饲料</span>
										</Tab-pane>
										<Tab-pane label="耗材" name="耗材">
											耗材
										</Tab-pane>
									</Tabs>
								</div>
							</Panel>
							<Panel name="生产用具">
								生产用具
								<div slot="content">
									<Tabs size="small" :animated="false" @on-click="getname">
										<Tab-pane label="农用工具及用品" name="农用工具及用品">
											<span class="checked" @click="savea()">农业通用用具 </span>
											<span class="checked" @click="savea()">农林专用用具</span>
											<span class="checked" @click="savea()">畜牧专用用具</span>
											<span class="checked" @click="savea()">渔业专用用具</span>
											<span class="checked" @click="savea()">农业生产耗材</span>
										</Tab-pane>
										<Tab-pane label="检测专用产品" name="检测专用产品">
											<span class="checked" @click="savea()">通用检测用具 </span>
											<span class="checked" @click="savea()">专用检测用具</span>
											<span class="checked" @click="savea()">检测专用耗材</span>
										</Tab-pane>
										<Tab-pane label="仓储专用产品" name="仓储专用产品">
											<span class="checked" @click="savea()">氮肥 </span>
											<span class="checked" @click="savea()">仓储通用用具 </span>
											<span class="checked" @click="savea()">仓储专用用具</span>
											<span class="checked" @click="savea()">仓储专用耗材</span>
										</Tab-pane>
										<Tab-pane label="包装专用产品" name="包装专用产品">
											<span class="checked" @click="savea()">包装通用用具 </span>
											<span class="checked" @click="savea()">包装专用用具</span>
											<span class="checked" @click="savea()">包装专用耗材</span>
										</Tab-pane>
										<Tab-pane label="装卸搬运专用产品" name="装卸搬运专用产品">
											<span class="checked" @click="savea()">配合饲料 </span>
											<span class="checked" @click="savea()">装卸搬运通用用具 </span>
											<span class="checked" @click="savea()">装卸搬运专用用具</span>
											<span class="checked" @click="savea()">装卸搬运专用耗材</span>
										</Tab-pane>
										<Tab-pane label="运输用具及用品" name="运输用具及用品">
											<span class="checked" @click="savea()">运输通用用具 </span>
											<span class="checked" @click="savea()">运输专用用具</span>
											<span class="checked" @click="savea()">运输专用耗材</span>
										</Tab-pane>
									</Tabs>
								</div>
							</Panel>
							<Panel name="机械设备">
								机械设备
								<div slot="content">
									<Tabs size="small" :animated="false" @on-click="getname">
										<Tab-pane label="农业通用机械设备" name="农业通用机械设备">
											<span class="checked" @click="savea()">拖拉机 </span>
											<span class="checked" @click="savea()">内燃机</span>
											<span class="checked" @click="savea()">燃油发电机组</span>
											<span class="checked" @click="savea()">风力设备</span>
											<span class="checked" @click="savea()">水利设备</span>
										</Tab-pane>
										<Tab-pane label="农林专用机械设备" name="农林专用机械设备">
											<span class="checked" @click="savea()">种子机械 </span>
											<span class="checked" @click="savea()">挖掘机械</span>
											<span class="checked" @click="savea()">平地机械</span>
										</Tab-pane>
										<Tab-pane label="畜禽机械设备" name="畜禽机械设备">
											<span class="checked" @click="savea()">氮肥 </span>
											<span class="checked" @click="savea()">畜禽饲料机械设备 </span>
										</Tab-pane>
										<Tab-pane label="水产机械设备" name="水产机械设备">
											<span class="checked" @click="savea()">包装通用用具 </span>
											<span class="checked" @click="savea()">水生动物饲养机械设备 </span>
										</Tab-pane>
										<Tab-pane label="加工机械" name="加工机械">
											<span class="checked" @click="savea()">配合饲料 </span>
											<span class="checked" @click="savea()">通用加工机械 </span>
											<span class="checked" @click="savea()">专用加工机械</span>
											<span class="checked" @click="savea()">木材加工机械</span>
										</Tab-pane>
									</Tabs>
								</div>
							</Panel>
							<Panel name="仪器仪表">
								仪器仪表
								<div slot="content">
									<Tabs size="small" :animated="false" @on-click="getname">
										<Tab-pane label="检测设备" name="检测设备">
											<span class="checked" @click="savea()">检测设备</span>
										</Tab-pane>
									</Tabs>
								</div>
							</Panel>
							<Panel name="农产品">
								农产品
								<div slot="content">
									<Tabs size="small" :animated="false" @on-click="getname">
										<Tab-pane label="植物产品" name="植物产品">
											<span class="checked" @click="savea()">粮食及制品 </span>
											<span class="checked" @click="savea()">油料及制品</span>
											<span class="checked" @click="savea()">瓜果蔬及制品</span>
											<span class="checked" @click="savea()">菌藻及制品</span>
											<span class="checked" @click="savea()">糖料及制品</span>
										</Tab-pane>
										<Tab-pane label="鲜活动物" name="鲜活动物">
											<span class="checked" @click="savea()">食用活动物 </span>
											<span class="checked" @click="savea()">役用活动物</span>
											<span class="checked" @click="savea()">娱用活动物</span>
										</Tab-pane>
										<Tab-pane label="动物产品" name="动物产品">
											<span class="checked" @click="savea()">畜肉及制品 </span>
											<span class="checked" @click="savea()">禽肉及制品</span>
											<span class="checked" @click="savea()">水产品及制品</span>
										</Tab-pane>
									</Tabs>
								</div>
							</Panel>
							<Panel name="食用产品">
								食用产品
								<div slot="content">
									<Tabs size="small" :animated="false" @on-click="getname">
										<Tab-pane label="食品" name="食品">
											<span class="checked" @click="savea()">日用副食品 </span>
											<span class="checked" @click="savea()">调味品</span>
											<span class="checked" @click="savea()">营养保健品</span>
											<span class="checked" @click="savea()">饮用水</span>
											<span class="checked" @click="savea()">精装茶及茶制品</span>
										</Tab-pane>
										<Tab-pane label="食品添加剂" name="食品添加剂">
											<span class="checked" @click="savea()">食品增稠剂 </span>
											<span class="checked" @click="savea()">蛋白质添加剂</span>
											<span class="checked" @click="savea()">食品甜味添加剂</span>
										</Tab-pane>
									</Tabs>
								</div>
							</Panel>
							<Panel name="图书音像">
								图书音像
								<div slot="content">
									<Tabs size="small" :animated="false" @on-click="getname">
										<Tab-pane label="报纸" name="报纸">
											<span class="checked" @click="savea()">报纸</span>
										</Tab-pane>
										<Tab-pane label="图书" name="图书">
											<span class="checked" @click="savea()">图书</span>
										</Tab-pane>
										<Tab-pane label="音像" name="音像">
											<span class="checked" @click="savea()">音像</span>
										</Tab-pane>
										<Tab-pane label="软件" name="软件">
											<span class="checked" @click="savea()">软件</span>
										</Tab-pane>
									</Tabs>
								</div>
							</Panel>
							<Panel name="日用产品">
								日用产品
								<div slot="content">
									<Tabs size="small" :animated="false" @on-click="getname">
										<Tab-pane label="能源产品 " name="能源产品">
											<span class="checked" @click="savea()">能源产品</span>
										</Tab-pane>
										<Tab-pane label="服饰产品" name="服饰产品">
											<span class="checked" @click="savea()">服饰产品</span>
										</Tab-pane>
										<Tab-pane label="鞋帽用品" name="鞋帽用品">
											<span class="checked" @click="savea()">鞋帽用品</span>
										</Tab-pane>
										<Tab-pane label="床上用品" name="床上用品">
											<span class="checked" @click="savea()">床上用品</span>
										</Tab-pane>
									</Tabs>
								</div>
							</Panel>
							<Panel name="无形商品">
								无形商品
								<div slot="content">
									<Tabs size="small" :animated="false" @on-click="getname">
										<Tab-pane label="土地使用权" name="土地使用权">
											<span class="checked" @click="savea()">土地使用权</span>
										</Tab-pane>
										<Tab-pane label="商标权" name="商标权">
											<span class="checked" @click="savea()">商标权</span>
										</Tab-pane>
										<Tab-pane label="专利权" name="专利权">
											<span class="checked" @click="savea()">专利权</span>
										</Tab-pane>
										<Tab-pane label="非专利技术" name="非专利技术">
											<span class="checked" @click="savea()">非专利技术</span>
										</Tab-pane>
									</Tabs>
								</div>
							</Panel>
						</Collapse>
					</Modal>
				</div>
				</Col>
				<Col span="12" style="border: 1px solid #ededed;width: 358px">
				<div style="text-align: center">
					<h2 style="line-height: 52px">关注产品</h2>
					<div class="zhuce-list2">
					</div>
					<div style="margin: 14px">
						<Button type="primary" size="small">保存</Button>
					</div>
				</div>
				</Col>
			</Row>
		</div>
		<div  class="footer-btn" >
					<i-button type="primary" @click="preStep" size="large">上一步</i-button>
					<i-button type="primary" @click="pass" size="large">下一步</i-button>
					<span class="tiaoguo" @click="pass">跳过</span>
		</div>
	</div>
	<!--3设置栏目第五步结束-->
</template>
<script>
import $ from 'jquery'
export default {
	data() {
		return {
			products: [{
				expand: true,
				title: '产品',
				children: []
			}],
			modal1: false,
			result: [],
			leibie: [],
			leibie1: [],
			leibie2: [],
			child1: '',
			child2: '',
			tabname: '',
			checka: [],
			checkb: []
		}
	},
	
	methods: {
		preStep(){
			// this.$parent.$parent.$parent.$parent.$router.go(-1)
			this.$router.push({ path: "/pro/member/progress6/progress7/progress8"})
		},
		pass(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.$parent.$parent.gotoPathSec(10)
			}else{
				this.$parent.$parent.$parent.$parent.gotoPath(10)
			}
		},
		changePro(e) {
			var _this = this
			this.checka = []
			console.log('changePro',e)
			e.forEach((c, index) => {
				this.checkb.push(c.title)
				if (c.children != undefined) {
					var len = _this.checka.indexOf(c.title)
					if (len == -1) {
						_this.checka.push(c.title)
					} else {
						//判断该节点是否是2级节点，若果是则不删除
						c.children.forEach(q => {
							if (q.children == undefined) {
								_this.checka.splice(len, 1)
							}
						})
					}
					c.children.forEach(t => {
						this.checkb.push(t.title)
						var len = _this.checka.indexOf(t.title)
						if (len == -1) {
							_this.checka.push(t.title)
						}
						if (t.children != undefined) {
							t.children.forEach(r => {
								this.checkb.push(r.title)
								var len = _this.checka.indexOf(r.title)
								if (len == -1) {
									_this.checka.push(r.title)
								}
							})
						}
					})
				} else {
					var len = _this.checka.indexOf(c.title)
					if (len == -1) {
						_this.checka.push(c.title)
					} else {
						_this.checka.splice(len, 1)
					}
				}
			})
			console.log('this.checka',this.checka)
		},
		del() {
			
			var _this = this
			_this.checka.forEach(e => {
				this.products[0].children.forEach(({
						title: r,
					children: c
					}, index) => {
					if (e == r) {
						_this.products[0].children.splice(index, 1)
					} else {
						c.forEach((f, len) => {
							if (f.title == e) {
								_this.products[0].children[index].children.splice(len, 1)
							} else {
								f.children.forEach((r, len1) => {
									if (r.title == e) {
										_this.products[0].children[index].children[len].children.splice(len1, 1)
										
									}
								})
							}
						})
					}
				})
			})
			this.checkb.forEach(c => {
				var len = this.leibie.indexOf(c)
				if (len != -1) {
					this.leibie.splice(len, 1)
				}
			})
		},
		changeColor() {
			var obj = event.target
			$(obj).toggleClass("intro");
		},
		getname(e) {
			$(".gex .ivu-tabs-tabpane span").removeClass("intro")
			this.panduan3()
			this.leibie1 = []
			this.child2 = e
		},
		//三级类别判断
		panduan3() {
			var _this = this;
			$(".gex .ivu-tabs-tabpane span").removeClass("intro")
			if (this.leibie1.length != 0) {
				if (this.leibie2.length != 0) {
					var cen = []
					this.leibie2.forEach(c => {
						cen.push(c.title)
					})
					var len = cen.indexOf(this.child2)
					if (len > -1) {
						this.leibie2[len].children = this.leibie2
					} else {
						var con = { 'title': this.child2, 'children': this.leibie1 }
						this.leibie2.push(con)
					}
				} else {
					var con = { 'title': this.child2, 'children': this.leibie1 }
					this.leibie2.push(con)
				}
			}
			this.leibie1 = []
		},
		getname1(e) {
			if (this.leibie1.length != 0) {
				this.panduan3()
			}
			this.panduan()
			console.log("result------")
			console.log(this.result)
			this.leibie2 = []
			this.child1 = e[0]
		},
		ok() {
			var len = this.leibie1.length
			if (len != 0) {
				this.panduan3()
				this.panduan()
				this.products[0].children = this.result
				console.log(this.products)
			} else if (this.leibie2.length != 0) {
				this.panduan()
				this.products[0].children = this.result
				console.log(this.products)
			} else {
				this.products[0].children = this.result
				console.log(this.products)
			}
		},
		panduan() {
			var _this = this;
			if ('' != this.child2 & '' != this.child1 & 0 != this.leibie2.length) {
				if (this.result.length != 0) {
					var cen = []
					var cen1 = []
					this.result.forEach(c => {
						console.log("title=" + c.title)
						cen.push(c.title)
					})
					var len = cen.indexOf(_this.child1)
					if (len > -1) {
						this.result[len].children = _this.leibie2
					} else {
						var con = { 'title': _this.child1, 'children': _this.leibie2 }
						_this.result.push(con)
					}
				} else {
					var con = { 'title': _this.child1, 'children': _this.leibie2 }
					this.result.push(con)
				}
			}
		},
		savea() {
			var obj = event.target
			var e = $(obj).html()
			var len = this.leibie.length;
			var len1 = this.leibie1.length;
			var _this = this;
			$(obj).toggleClass("intro");
			var cla = $(obj).attr("class")
			var flag=cla.indexOf('intro')
			if (flag!=-1) {
				if (len > 0) {
					var le = _this.leibie.indexOf(e)
					if (le == -1) {
						_this.leibie.push(e)
					}
				} else {
					_this.leibie.push(e)
				}
				if (len1 > 0) {
					var le1 = _this.leibie1.indexOf({ "title": e })
					if (le1 == -1) {
						_this.leibie1.push({ "title": e })
					}
				} else {
					_this.leibie1.push({ "title": e })
				}
			} else {
				if (len > 0) {
					var le = _this.leibie.indexOf(e)
					if (le > -1) {
						_this.leibie.splice(le, 1)
					}
				}
				if (len1 > 0) {
					var le = _this.leibie1.indexOf({ "title": e })
					if (le > -1) {
						_this.leibie1.splice(le, 1)
					}
				}
			}
		}
	}
}
</script>