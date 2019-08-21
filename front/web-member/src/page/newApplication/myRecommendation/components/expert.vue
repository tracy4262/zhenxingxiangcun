<template>
    <div class="pd20" style="min-height: 500px;">
        <Button :type="activeIndex === 0 ? 'primary' : 'text'" @click="find">查找专家</Button>
        <Button :type="activeIndex === 1 ? 'primary' : 'text'" @click="has">已推荐专家</Button>
        <Form ref="queryInfo" :model="queryInfo" label-position="left" :label-width="100" :rules="ruleInline" class="mt20">
            <Row :gutter="32">
                <Col span="7">
                    <Form-item prop="trade" class="input" label="行政区划">
                        <Cascader v-model="queryInfo.locationArr" :render-format="format" :data="locationList" :load-data="loadPositionDatas" change-on-select>
                        </Cascader>
                    </Form-item>
                </Col>
                <Col span="7">
                    <Form-item prop="speci" class="input" label="相关物种">
                        <Input v-model="queryInfo.relatedSpecies" readonly @on-focus="handleFilterModal('speciFilter')" />
                    </Form-item>
                </Col>
                <Col span="7">
                    <Form-item prop="name" class="input" label="专家姓名">
                        <Input v-model="queryInfo.unitOrName" clearable />
                    </Form-item>
                </Col>
                <Col span="3">
                    <Button type="primary" @click="query">查询</Button>
                </Col>
            </Row>
        </Form>
        <Button type="primary" @click="operate" v-if="flag">批量操作</Button>
        <Button type="primary" @click="exitOperate" v-if="!flag">退出批量操作</Button>
        <Button type="primary" @click="add" v-if="!flag"><span v-if="activeIndex === 0">添加推荐</span><span v-else>取消推荐</span></Button>
        <div class="mt20">
            <CheckboxGroup v-model="choosed">
                <Row :gutter="24">
                    <Col span="6" v-for="(item, index) in list" class="mb20">
                        <!-- 查找专家中，所有已推荐未推荐的都会查出来，所以已推荐的不能再次进行选择 -->
                        <Checkbox :label="item.id" v-if="!flag && item.isRecommend === '未推荐'" style="position: absolute; top: 10px; right: 10px; z-index: 1;"><span>&nbsp;</span></Checkbox>
                        <!-- 已推荐专家中所有的都是已推荐专家，所以不用做限制 -->
                        <Checkbox :label="item.id" v-if="activeIndex === 0 && !flag && item.isRecommend === '未推荐'" style="position: absolute; top: 10px; right: 10px; z-index: 1;"><span>&nbsp;</span></Checkbox>
                        <expert-item :item="item" @refresh="refresh"></expert-item>
                    </Col>
                </Row>
            </CheckboxGroup>
        </div>
        <div class="mt20 tr" v-if="list.length !== 0">
            <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="pageChange" />
        </div>
        <!-- 相关物种 -->
        <vui-filter
            ref="speciFilter"
            :transfer="true"
            :cols="2"
            :pageShow="true"
            :total="total5"
            :pageCur="pageCur5"
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
import vuiFilter from '~components/vuiFilter/filter'
import expertItem from './expert-item'
export default {
    components: {
        vuiFilter,
        expertItem
    },
    data () {
        return {
            activeIndex: 0,
            queryInfo: {
                locationArr: [],
                location: '',
                relatedSpecies: '',
                relatedSpeciesId: '',
                unitOrName: ''
            },
            ruleInline: {},
            list:[],
            total: 0,
            pageSize: 8,
            pageNum: 1,
            locationList: [],
            flag: true,
            speciClassifyDatas: [
                {
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
            speciResultDatas: [],
            total5: 0,
            pageCur5: 1,
            choosed: []
        }
    },
    created () {
        // 取地址
        this.$api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
            this.locationList = res.data
        })
        // 取相关物种结果
        this.loadSpeciResult('', '', [], this.pageCur5, [])
        this.init()
    },
    methods: {
        init () {
            this.list = [] // 必须要加，不然会有意想不到的问题
            this.$api.post('/member-reversion/myRecommend/expertList', {
                account: this.$user.loginAccount,
                flag: this.activeIndex === 0 ? '0' : '1', //0:查询所有专家, 1:查询已推荐专家
                address: this.queryInfo.location,
                expertName: this.queryInfo.unitOrName,
                relatedSpecies: this.queryInfo.relatedSpecies,
                pageNum: this.pageNum,
                pageSize: this.pageSize
            }).then(response => {
                if (response.code === 200) {
                    console.log('res', response)
                    this.list = response.data.list
                    this.total = response.data.total
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        find () {
            this.pageNum = 1
            this.activeIndex = 0
            this.flag = true
            // 清空查询条件
            this.queryInfo = {
                locationArr: [],
                location: '',
                relatedSpecies: '',
                relatedSpeciesId: '',
                unitOrName: ''
            }
            // 查询所有的专家
            this.init()
        },
        has () {
            this.pageNum = 1
            this.activeIndex = 1
            this.flag = true
            // 清空查询条件
            this.queryInfo = {
                locationArr: [],
                location: '',
                relatedSpecies: '',
                relatedSpeciesId: '',
                unitOrName: ''
            }
            // 查询已经推荐的专家
            this.init()
        },
        pageChange (page) {
            this.pageNum = page
            this.init()
        },
        query () {
            this.pageNum = 1
            this.init()
        },
        loadPositionDatas (item, callback) {
            item.loading = true
            this.$api.post(`/member/town/next/${item.value}`).then(res => {
                item.loading = false
                item.children = res.data
                callback()
            })
        },
        format (labels, selectedData) {
            let locationStr = labels.join('/')
            this.queryInfo.location = locationStr
            return locationStr
        },
        operate () {
            this.flag = false
            this.choosed = []
        },
        exitOperate () {
            this.flag = true
            this.choosed = []
        },
        add () {
            if (this.choosed.length === 0) {
                this.$Message.info(this.activeIndex === 0 ? '请先选择要推荐的专家！' : '请先选择要取消推荐的专家！')
                return
            }
            this.$Modal.confirm({
                title: '操作提示',
                content: this.activeIndex === 0 ? '设置为推荐的专家将在您的门户对外宣传展示！请确认是否设置为推荐专家！' : '取消推荐的专家将从您的门户删除！请确认是否取消推荐！',
                onOk: () => {
                    // 请求添加接口，请求取消接口
                    // 刷新当前页面
                    let list = []
                    this.choosed.forEach(elemenet => {
                        list.push({
                            id: elemenet
                        })
                    })
                    this.$api.post('/member-reversion/myRecommend/operation', {
                        account: this.$user.loginAccount,
                        flag: this.activeIndex === 0 ? 1 : 0, // 0:取消推荐, 1:推荐
                        type: 3, // 1:推荐服务, 2:推荐基地, 3:推荐专家
                        list: list
                    }).then(response => {
                        if (response.code === 200) {
                            console.log('res', response)
                            this.$Message.success('推荐成功！')
                            this.init()
                            this.flag = true
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        },
        refresh () {
            this.pageNum = 1
            this.init()
        },
        // 高级搜索弹窗
        handleFilterModal (name) {
            this.$refs[name].highFilterShow = true
        },
        // 相关物种
        handleSpeciSearch (letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur5, result)
            this.$Message.success('筛选完成！')
        },
        handleGetSpeciClassify (letter, keyword, classify, result) {
            this.loadSpeciResult(letter, keyword, classify, this.pageCur5, result)
            this.$Message.success('筛选完成！')
        },
        handleGetSpeciResult (classify, result) {
            var arr = []
            var arr2 = []
            result.forEach(item => {
                arr.push(item.label)
                arr2.push(item.value)
            })
            this.queryInfo.relatedSpecies = arr.join(' ')
            this.queryInfo.relatedSpeciesId = arr2.join(' ')
        },
        handleSpeciPageChange (letter, keyword, classify, num, result) {
            this.pageCur5 = num
            this.loadSpeciResult(letter, keyword, classify, this.pageCur5, result)
            this.$Message.success('筛选完成！')
        },
        loadSpeciResult (letter, keyword, classify, num, result) {
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
            this.$api.post('/member/specicesClass/findSpecies', {
                keywords: keyword,
                fpinyin: letter === '全部' ? '' : letter, 
                fclassifiedid: arr,
                type: type,
                pageNum: num,
                pageSize: 32
            }).then(res => {
                var data = res.data
                this.total5 = data.total
                if (result) {
                    result.forEach(item => {
                        data.list.forEach((child,index)=>{
                            if (child.label === item.label) {
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
    }
}
</script>
<style lang="scss" scoped>

</style>
