<template>
    <!--3设置栏目第四步开始-->
    <div>
        <div style="padding: 0 38px">
            <Row>
                <Col span="12" style="border: 1px solid #ededed;width: 358px;margin-right: 26px;">
                <div style="margin-left: 128px;">
                    <h2 style="line-height: 52px">选择政策类型</h2>
                    <div class="zhuce-list">
                        <Tree :data="policys" show-checkbox multiple @on-check-change="change"></Tree>
                    </div>
                    <div style="margin: 14px">
                        <Button type="primary" size="small" @click="modal1=true">添加</Button>&nbsp&nbsp&nbsp
                        <Button type="error" size="small" @click="del">删除</Button>
                    </div>
                    <Modal v-model="modal1" width="450" :closable="true" :mask-closable="false" class="gex" @on-ok="ok">
                        <Row style="margin-top: 20px;padding: 0 20px;text-align:left;font-size: 12px;">
							<span>当前选中：
								<span v-for="c in leibie" style="color: red;">*{{c}};</span>
							</span>
                        </Row>
                        <hr />
                        <Collapse accordion @on-change="getname1">
                            <Panel name="党务">
                                党务
                                <div slot="content">
                                    <span class="checked" @click="savea()">综合 </span>
                                    <span class="checked" @click="savea()">组织</span>
                                    <span class="checked" @click="savea()">宣传</span>
                                    <span class="checked" @click="savea()">统战</span>
                                    <span class="checked" @click="savea()">外联</span>
                                </div>
                            </Panel>
                            <Panel name="政务">
                                政务
                                <div slot="content">
                                    <span class="checked" @click="savea()">综合 </span>
                                    <span class="checked" @click="savea()">外交</span>
                                    <span class="checked" @click="savea()">国防</span>
                                    <span class="checked" @click="savea()">发展</span>
                                    <span class="checked" @click="savea()">教育</span>
                                </div>
                            </Panel>
                            <Panel name="全团">
                                全团
                                <div slot="content">
                                    <span class="checked" @click="savea()">工会 </span>
                                    <span class="checked" @click="savea()">共青团</span>
                                </div>
                            </Panel>
                            <Panel name="群众自治">
                                群众自治
                            </Panel>
                            <Panel name="国际">
                                国际
                            </Panel>
                        </Collapse>
                    </Modal>
                </div>
                </Col>
                <Col span="12" style="border: 1px solid #ededed;width: 358px">
                <div style="text-align: center">
                    <h2 style="line-height: 52px">关联关注</h2>
                    <div class="zhuce-list2">
                        <ul class="species3">
                            <li @click="changeColor" :class="{'intro': relationPro}">关联产品</li>
                            <li @click="changeColor":class="{'intro': relationSpe}">关联物种</li>
                            <li @click="changeColor":class="{'intro': relationSer}">关联服务</li>
                        </ul>
                    </div>
                    <div style="margin: 14px">
                        <Button type="primary" size="small" @click="save">保存</Button>
                    </div>
                </div>
                </Col>
            </Row>
        </div>
    </div>
    <!--3设置栏目第四步结束-->
</template>
<script>
    import $ from 'jquery'
    import api from '~api'
    export default {
        data() {
            return {
                modal1: false,
                policys: [{
                    expand: true,
                    title: '政策',
                    children: []
                }],
                leibie2: [],
                leibie1: [],
                leibie: [],
                resultCP: [],
                child1: [],
                result: [],
                result1: [],
                relationPro:false,
                relationSpe:false,
                relationSer:false
            }
        },
        methods: {
            changeColor() {
                var obj = event.target
                var len = this.resultCP.length;
                var e = $(obj).html()
                var _this = this;
                $(obj).toggleClass("intro");
                var cla = $(obj).attr("class")
                console.log("intro=" + cla)
                if (cla == "intro") {
                    if (len > 0) {
                        var le = _this.resultCP.indexOf(e)
                        console.log("e==" + e)
                        console.log(_this.resultCP)
                        if (le == -1) {
                            _this.resultCP.push(e)
                        }

                    } else {
                        _this.resultCP.push(e)
                    }
                } else {
                    if (len > 0) {
                        var le = _this.resultCP.indexOf(e)
                        if (le > -1) {
                            _this.resultCP.splice(le, 1)
                        }
                    }
                }
                console.log(this.resultCP)
            },
            getname1(e) {
                this.panduan()
                this.child1 = e[0]
                this.leibie1 = []
            },
            panduan() {
                if (this.leibie1.length != 0) {
                    if (this.leibie2.length != 0) {
                        var cen = []
                        this.leibie2.forEach(c => {
                            cen.push(c.title)
                        })
                        var len = cen.indexOf(this.child1)
                        if (len > -1) {
                            this.leibie2[len].children = this.leibie1
                        } else {
                            var con = { 'title': this.child1, 'children': this.leibie1 }
                            this.leibie2.push(con)
                        }
                    } else {
                        var con = { 'title': this.child1, 'children': this.leibie1 }
                        this.leibie2.push(con)
                    }
                }
            },
            ok() {
                $(".gex span").removeClass("intro")
                if (this.leibie1.length != 0) {
                    this.panduan()
                    this.policys[0].children = this.leibie2
                } else {
                    this.policys[0].children = this.leibie2
                }
                console.log("政策----")
                console.log(this.policys)
            },
            change(e) {
                var _this = this
                this.result = []
                e.forEach(c => {
                    this.result1.push(c.title)
                    if (c.children != undefined) {
                        _this.result.push(c.title)
                        c.children.forEach(t => {
                            _this.result.push(t.title)
                        })
                    } else {
                        var len = _this.result.indexOf(c.title)
                        if (len == -1) {
                            _this.result.push(c.title)
                        } else {
                            _this.result.splice(len, 1)
                        }
                    }
                })
                console.log(this.result)
            },
            del() {
                var _this = this
                var cen1 = []
                var cen2 = []
                _this.result.forEach(e => {
                    this.policys[0].children.forEach(({ title: r, children: c }, index) => {
                        if (e == r) {
                            _this.policys[0].children.splice(index, 1)
                        } else {
                            c.forEach((f, len) => {
                                if (f.title == e) {
                                    _this.policys[0].children[index].children.splice(len, 1)
                                }
                            })
                        }
                    })
                })
                this.result1.forEach(c => {
                    var len = this.leibie.indexOf(c)
                    if (len != -1) {
                        this.leibie.splice(len, 1)
                    }
                })
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
                console.log(this.leibie1)
            },
            save() {
                this.resultCP = []
                console.log('this.leibie-->', this.leibie)
                $('.species3').find('.intro').each((index, item) => {
                    this.resultCP.push($(item).text())
                })
                console.log('this.leibie-->',this.leibie)
                console.log('this.resultCP-->',this.resultCP)
                if(this.leibie.length && this.resultCP.length){
                    api.post('/member/indivi/policy',
                        {
                            ledge: this.leibie,
                            leibie: this.resultCP,
                            step:''
                        }
                    ).then(response => {
                        if ("OK" == response.data) {
                            this.$Message.success('保存成功！')
                        } else {
                            this.$Message.error('保存失败！')
                        }
                    })
                }else{
                    this.$Message.error('请选择需要关联的类型！')
                }
            }
        }, created() {
            var selected = []
            api.post('/member/indivi/hadSavePolicy').then(response => {
                console.log('response--->',response)
                if (200 === response.code) {
                    console.log('response.data.ledge--->',response.data.ledge)
                    this.leibie =   response.data.ledge
                    this.resultCP =  response.data.leibie
                    selected = this.resultCP
                    if (selected.indexOf('关联产品') != -1) {
                        this.relationPro = true
                    }
                    if (selected.indexOf('关联物种') != -1) {
                        this.relationSpe = true
                    }
                    if (selected.indexOf('关联服务') != -1) {
                        this.relationSer = true
                    }
                }
            })

        }
    }
</script>