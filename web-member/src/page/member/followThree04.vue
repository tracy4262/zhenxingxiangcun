<template>
    <!--3设置栏目第四步开始-->
    <div>
        <div style="padding: 0 38px">
            <Row>
                <Col span="12" style="border: 1px solid #ededed;width: 358px;margin-right: 26px;">
                <div style="margin-left: 128px;">
                    <h2 style="line-height: 52px">选择知识类型</h2>
                    <div class="zhuce-list">
                        <Tree :data="knowledges" show-checkbox multiple @on-check-change="changeKnow"></Tree>
                    </div>
                    <div style="margin: 14px">
                        <Button type="primary" size="small" @click="modal1=true">添加</Button>
                        &nbsp&nbsp&nbsp
                        <Button type="error" size="small" @click="del">删除</Button>
                    </div>
                    <Modal v-model="modal1" width="450" :closable="true" :mask-closable="false" class="gex" @on-ok="ok">
                        <Row style="margin-top: 20px;padding: 0 20px;text-align:left;font-size: 12px;">
							<span>当前选中：
								<span v-for="c in leibie" style="color: red;">*{{c}}</span>
							</span>
                        </Row>
                        <hr/>
                        <Collapse accordion @on-change="getname1">
                            <Panel name="农林牧渔">
                                农林牧渔
                                <div slot="content">
                                    <Tabs v-model="tabname" size="small" :animated="false" @on-click="getname">
                                        <Tab-pane label="种植园地" name="种植园地">
                                            <span class="checked" @click="savea()">种植标准</span>
                                            <span class="checked" @click="savea()">种植技术</span>
                                            <span class="checked" @click="savea()">农事提醒</span>
                                            <span class="checked" @click="savea()">育种技术</span>
                                            <span class="checked" @click="savea()">育苗技术</span>
                                        </Tab-pane>
                                        <Tab-pane label="养殖园地" name="养殖园地">
                                            <span class="checked" @click="savea()">养殖标准</span>
                                            <span class="checked" @click="savea()">养殖技术</span>
                                            <span class="checked" @click="savea()">养殖提醒</span>
                                        </Tab-pane>
                                        <Tab-pane label="种养模式" name="种养模式">
                                        </Tab-pane>
                                        <Tab-pane label="规划方案" name="规划方案">
                                        </Tab-pane>
                                        <Tab-pane label="农业工程" name="农业工程">
                                            <span class="checked" @click="savea()">农村能源</span>
                                            <span class="checked" @click="savea()">水能应用</span>
                                            <span class="checked" @click="savea()">风能应用</span>
                                            <span class="checked" @click="savea()">太阳能</span>
                                            <span class="checked" @click="savea()">地下热能</span>
                                        </Tab-pane>
                                        <Tab-pane label="农业基础科学" name="农业基础科学">
                                        </Tab-pane>
                                    </Tabs>
                                </div>
                            </Panel>
                            <Panel name="食品科学">
                                食品科学
                                <div slot="content">
                                    <Tabs size="small" :animated="false" @on-click="getname">
                                        <Tab-pane label="加工技术" name="加工技术">
                                        </Tab-pane>
                                        <Tab-pane label="食品机械" name="食品机械">
                                        </Tab-pane>
                                        <Tab-pane label="食品工程" name="食品工程">
                                        </Tab-pane>
                                        <Tab-pane label="食品标准" name="食品标准">
                                        </Tab-pane>
                                    </Tabs>
                                </div>
                            </Panel>
                            <Panel name="经济">
                                经济
                            </Panel>
                            <Panel name="法律">
                                法律
                            </Panel>
                        </Collapse>
                    </Modal>
                </div>
                </Col>
                <Col span="12" style="border: 1px solid #ededed;width: 358px">
                <div style="text-align: center">
                    <h2 style="line-height: 52px">关联关注</h2>
                    <div class="zhuce-list2">
                        <ul class="species">
                            <li @click="changeColor" :class="{'intro': relationPro}">关联产品</li>
                            <li @click="changeColor" :class="{'intro': relationSpe}">关联物种</li>
                            <li @click="changeColor" :class="{'intro': relationSer}">关联服务</li>
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
                knowledges: [{
                    expand: true,
                    title: '知识',
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
                resultCP: [],
                checka: [],
                checkb: [],
                relationPro: false,
                relationSpe: false,
                relationSer: false
            }
        },
        methods: {
            changeKnow(e) {
                var _this = this
                this.checka = []
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
            },
            del() {
                var _this = this
                var cen1 = []
                var cen2 = []
                _this.checka.forEach(e => {
                    this.knowledges[0].children.forEach(({
                                                             title: r,
                                                             children: c
                                                         }, index) => {
                        if (e == r) {
                            _this.knowledges[0].children.splice(index, 1)
                        } else {
                            c.forEach((f, len) => {
                                if (f.title == e) {
                                    _this.knowledges[0].children[index].children.splice(len, 1)
                                } else {
                                    f.children.forEach((r, len1) => {
                                        if (r.title == e) {
                                            _this.knowledges[0].children[index].children[len].children.splice(len1, 1)
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
            getname(e) {
                this.panduan3()
                this.leibie1 = []
                this.child2 = e
                console.log(this.child2)
                console.log("result--" + this.leibie2)
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
                            var con = {
                                'title': this.child2,
                                'children': this.leibie1
                            }
                            this.leibie2.push(con)
                        }
                    } else {
                        var con = {
                            'title': this.child2,
                            'children': this.leibie1
                        }
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
                this.leibie2 = []
                this.child1 = e[0]
            },
            ok() {
                var len = this.leibie1.length
                if (len != 0) {
                    this.panduan3()
                    this.panduan()
                    this.knowledges[0].children = this.result
                } else if (this.leibie2.length != 0) {
                    this.panduan()
                    this.knowledges[0].children = this.result
                } else {
                    this.knowledges[0].children = this.result
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
                            var con = {
                                'title': _this.child1,
                                'children': _this.leibie2
                            }
                            _this.result.push(con)
                        }
                    } else {
                        var con = {
                            'title': _this.child1,
                            'children': _this.leibie2
                        }
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
                var flag = cla.indexOf('intro')
                if (flag != -1) {
                    if (len > 0) {
                        var le = _this.leibie.indexOf(e)
                        if (le == -1) {
                            _this.leibie.push(e)
                        }
                    } else {
                        _this.leibie.push(e)
                    }
                    if (len1 > 0) {
                        var le1 = _this.leibie1.indexOf({
                            "title": e
                        })
                        if (le1 == -1) {
                            _this.leibie1.push({
                                "title": e
                            })
                        }

                    } else {
                        _this.leibie1.push({
                            "title": e
                        })
                    }
                } else {
                    if (len > 0) {
                        var le = _this.leibie.indexOf(e)
                        if (le > -1) {
                            _this.leibie.splice(le, 1)
                        }
                    }
                    if (len1 > 0) {
                        var le = _this.leibie1.indexOf({
                            "title": e
                        })
                        if (le > -1) {
                            _this.leibie1.splice(le, 1)
                        }
                    }
                }
            },
            //保存知识
            save() {
                this.resultCP = []
                console.log('this.leibie-->', this.leibie)
                $('.species').find('.intro').each((index, item) => {
                    this.resultCP.push($(item).text())
                })

                if (this.leibie.length && this.resultCP.length) {
                    api.post('/member/indivi/ledge',
                        {
                            ledge: this.leibie,
                            leibie: this.resultCP,
                            step: ''
                        }
                    ).then(response => {
                        if ("OK" == response.data) {
                            this.$Message.success('保存成功！')
                        } else {
                            this.$Message.error('保存失败！')
                        }
                    })
                } else {
                    this.$Message.error('请选择需要关联的类型！')
                }
            },
        },
        created() {
            var selected = []
            //获得选择中的物种
//            $(".zhuce-list2 ul li").each(function(){
//                 alert($(this).text())
//                console.log('$(this)-->',$(this))
//            })
            api.post('/member/indivi/hadSaveKnowlege').then(response => {
                console.log('response---->', response)
                if (200 === response.code) {
                    this.leibie = response.data.ledge
                    this.resultCP = response.data.leibie
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