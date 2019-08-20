<template>
    <div>
        <div style="padding: 0 38px">
            <Row class="follow-top">
                <Col span="12">
                <div class="follow-box">
                    <h2 class="follow-h">选择物种类型</h2>
                    <div class="follow-tree">
                       <Tree :data="specTree"  show-checkbox multiple ref="tree"></Tree>
                    </div>
                </div>
                <div class="follow-add">
                    <Button type="default" @click="handleFilterModal('speciFilter')">添加</Button>
                    <Button type="default" @click="deleteType">删除</Button>
                </div>
                </Col>
                <Col span="12">
                <div class="follow-box">
                    <h2 class="follow-h">选择物种</h2>
                    <div class="follow-tag">
                         <template>
                        <Tag v-for="(item,index) in specResult" :key="item" type="border"  :name="item"  closable color="primary" @on-close="handleClose">{{item}}</Tag>
                         </template>
                    </div>
                </div>
                <div class="follow-save">
                    <Button type="default" @click="save">保存</Button>
                </div>
                </Col>
            </Row>
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
    <!--3设置栏目第二步结束-->
</template>
<script>
    import api from '~api'
    import $ from 'jquery'
    import vuiFilter from "~components/vuiFilter/filter";
    export default {
        components: {
            vuiFilter
        },
        data() {
            return {
                specTree: [{
                    title: '关注物种',
                    expand: true,
                    children: [
                        {
                            title: '动物',
                            expand: true,
                            children: []
                        },
                        {
                            title: '植物',
                            expand: true,
                            children: []
                        }

                    ]
                }],
                total: 0,
                pageCur: 1,
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
                speciResultDatas: [],
                //物种类型结果
                typeResultTemp:[],
                //物种结果
                specsResultTemp:[],
                show:true,
                specResult:[],
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
            }
        },
        created() {
            // 取相关物种结果
            this.loadSpeciResult("", "", [], this.pageCur, [])
            // 第一次获取数据库中的信息
            api.post('/member/indivi/hadSaveSpecies',{
                account:this.loginuserinfo.loginAccount
            }).then(res => {
                let fieldName = JSON.parse(res.data.fieldName)
                let speciesName = JSON.parse(res.data.speciesName)
                this.typeResultTemp = fieldName
                this.specsResultTemp = speciesName
                this.getTreeDatas(this.specsResultTemp,this.typeResultTemp)
            })
            //this.changeTree()
        },
        methods: {
            handleClose(event,name){
                let index = this.specResult.indexOf(name);
                this.specResult.splice(index, 1)
                this.specsResultTemp.forEach(e => {
                    if (name === e.label){
                        this.specsResultTemp.splice(this.specsResultTemp.indexOf(e),1)
                    }
                })
            },
            // 高级搜索弹窗
            handleFilterModal(name) {
                this.$refs[name].highFilterShow = true
                this.loadSpeciResult('全部', '', [], this.pageCur, [])
                this.$refs[name].selClassifyData = []
                this.$refs[name].selClassifyText = []
                this.$refs[name].selResultData = []
                var child = this.$refs[name].classifyDatas.children
                this.$refs[name].classifyDatas.forEach(item => {
                    item.checked = false
                    if(item.children){
                        item.children.forEach(child => {
                            child.checked = false
                        })
                    }
                })
            },
            loadSpeciDatas(item, callback) {
                item.loading = true;
                api.post(`/member/specicesClass/findByParentId/${item.value}`)
                    .then(res => {
                        item.loading = false;
                        var d = res.data;
                        d.forEach(child => {
                            child.checked = false;
                            child.label = child.className;
                        });
                        item.children = d;
                        callback();
                    });
            },
            // 相关物种
            handleSpeciSearch(letter, keyword, classify, result) {
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
                this.$Message.success("筛选完成！");
            },
            handleGetSpeciClassify(letter, keyword, classify, result) {
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);

                this.$Message.success("筛选完成！")
            },
            handleGetSpeciResult(classify, result) {
                classify.forEach(item => {
                    this.typeResultTemp.push(item)
                })
                //  添加物种
                result.forEach(item => {
                    this.specsResultTemp.push(item)
                })
                this.specsResultTemp = this.distinct_arr_specs(this.specsResultTemp)
                this.typeResultTemp = this.distinct_arr_type(this.typeResultTemp)
                this.getTreeDatas(this.specsResultTemp,this.typeResultTemp)

            },
            handleSpeciPageChange(letter, keyword, classify, num, result) {
                this.pageCur = num;
                this.loadSpeciResult(letter, keyword, classify, this.pageCur, result);
                this.$Message.success("筛选完成！");
            },
            loadSpeciResult(letter, keyword, classify, num, result) {
                if (classify.length) {
                    var arr = []
                    var type = ''
                    classify.forEach(item => {
                        arr.push(item.classId)
                        if (item.value !== undefined)
                            type = item.value
                    })
                } else {
                    arr = null
                }
                api.post("/member/specicesClass/findSpecies", {
                        keywords: keyword,
                        fpinyin: letter === "全部" ? "" : letter,
                        fclassifiedid: arr,
                        pageNum: num,
                        pageSize: 32,
                        type: type
                    })
                    .then(res => {
                        var data = res.data;
                        this.total = data.total;
                        if (result) {
                            result.forEach(item => {
                                data.list.forEach((child, index) => {
                                    if (child.label === item.label) {
                                        child.checked = true;
                                    }
                                });
                            });
                        } else {
                            data.list.forEach(child => {
                                child.checked = false;
                            });
                        }
                        this.speciResultDatas = data.list;
                    });
            },
            save(){
                this.specsResultTemp = this.distinct_arr_specs(this.specsResultTemp)
                this.typeResultTemp = this.distinct_arr_type(this.typeResultTemp)
                let speciesName = []
                let fieldName = []
                api.post('/member/indivi/save', {
                    account: this.loginuserinfo.loginAccount,
                    speciesName: this.specsResultTemp,
                    fieldName: this.typeResultTemp,
                    step: ''
                }).then(res => {
                    if (200 === res.code) {
                        this.$Message.success("保存成功")
                    }
                })
            },
            deleteType(){
                let seletedNode = this.$refs.tree.getCheckedNodes()
                let count = 0
                // 剔除关注物种,动物,植物
                seletedNode.forEach(e => {
                    if(e.title ==='关注物种'||e.title ==='动物' || e.title === '植物') count++
                })
                if (0 === count){
                    if(0 === seletedNode.length){
                        this.$Message.error('请勾选要删除的类型!')
                    }else{
                        seletedNode.forEach( e => {
                            this.typeResultTemp.forEach( (item,index)=> {
                                if (item.label === e.title ){
                                    this.typeResultTemp.splice(index,1)

                                }
                            })
                        })
                        //更新树结构
                        this.getTreeDatas(this.specsResultTemp,this.typeResultTemp)
                        this.$Message.success('删除成功,请保存!')
                    }
                }else if (1 === count ){
                // 肯定是删除了动物或植物中的一种
                    seletedNode.forEach( e => {
                        this.typeResultTemp.forEach( (item,index)=> {
                            if (item.label === e.title && e.title !== '动物' && e.title !== '植物' ){
                                this.typeResultTemp.splice(index,1)
                            }
                        })
                    })
                    //更新树结构
                    this.getTreeDatas(this.specsResultTemp,this.typeResultTemp)
                    this.$Message.success('删除成功,请保存!')
                } else if (3 === count) {
                    // 清空所有的分类
                    seletedNode.forEach( e => {
                        this.typeResultTemp.forEach( (item,index)=> {
                            if (item.label === e.title && e.title !== '动物' && e.title !== '植物' && e.title !== '关注物种'){
                                this.typeResultTemp.splice(index,1)
                            }
                        })
                    })
                    //更新树结构
                    this.getTreeDatas(this.specsResultTemp,this.typeResultTemp)
                    this.$Message.success('删除成功,请保存!')
                } else {
                    this.$Message.error('请勾选正确的分类!');
                }
            },
            changeTree(){
                // 从数据库中得到结果
            },
            //物种 去重
            distinct_arr_specs(arr) {
                if (!arr) return null
                let resultArr = []
                arr.forEach(el => {
                    let notExist = true
                    resultArr.forEach(element => {
                        if (el.label === element.label) {
                            notExist = false
                            return false
                        }
                    })
                    if (notExist)
                        resultArr.push(el)
                })
                return resultArr
            },
            //类型 去重
            distinct_arr_type(arr){
                if (!arr) return null
                let resultArr = []
                arr.forEach(el =>{
                    let notExist = true
                    resultArr.forEach(element=> {
                        if (el.label === element.label) {
                            notExist = false
                            return false
                        }
                    })
                    if (notExist)
                        resultArr.push(el)
                })
                return resultArr
            },
                getTreeDatas(speciesName,fieldName){
                //初始化树结构
                this.specTree[0].children[0].children=[]
                this.specTree[0].children[1].children=[]
                this.specResult=[]
                speciesName.forEach(item => {
                    this.specResult.push(item.label)
                })
                if (fieldName.length > 0) {
                    fieldName.forEach(i => {
                        let classId= i.classId
                        api.post('/wiki/speciesclass/listSpeciesclass', {
                            classId: classId
                        }).then(res => {
                            if (200 === res.code) {
                                if("0" === res.data[0].parentId){// 0是动物 1 是植物
                                    this.specTree[0].children[0].children.push({title:i.label,expand:true})
                                }else {
                                    this.specTree[0].children[1].children.push({title:i.label,expand:true})
                                }
                            }
                        })
                    })
                }
            }

        }
    }
</script>
<style scoped>
    @import '../../css/identification.css';
</style>
<style lang="scss" scoped>
    .follow-box{
        padding-top: 16px;
        border:1px solid gainsboro;
        height: 400px;
        overflow-y: auto;
    }
    .follow-spec-item li {
        display:inline
    }
    .follow-top{
        margin-top: 20px;
    }
    .follow-tree{
        margin-top: 24px;
        margin-left: 100px;
    }
    .follow-add{
        padding-bottom: 100px;
        margin-left: 160px;
        margin-top: 10px;
    }
    .follow-save{
        padding-bottom: 100px;
        margin-left: 160px;
        margin-top: 10px;
    }
    .follow-h{
        margin-left: 146px;
        color: #00c261;
        letter-spacing:2px;
     }
    .follow-tag{
        margin-left: 10px;
        margin-top: 26px;
        height: 438.67px;
        overflow:auto;
    }
    ::-webkit-scrollbar
    {
        width: 1px;
        height: 1px;
        background-color: rgba(245, 245, 245, 0);
    }
</style>