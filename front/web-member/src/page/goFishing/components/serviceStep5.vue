<template>
<div class="pd20">
    <Form :label-width="100">
        <Row :gutter="32">
            <Col span="8">
                <FormItem label="服务名称">
                    <Input v-model="formItem.serviceName"></Input>
                </FormItem>
            </Col>
            <Col span="8">
                <FormItem label="服务类型"> 
                    <Select style="width:100%" v-model="formItem.serviceType" clearable>
                        <Option v-for="item in serviceNames" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </FormItem>
            </Col>
            <Col span="8">
                <!-- <Button >清除</Button> -->
                <Button @click="onSearch">查询</Button>
            </Col>
        </Row>
    </Form>
    <Title title="已关联"></Title>
    <vuicard :data="isRelationData" :isRelation="true" @on-init="onSearch"></vuicard>
    <Page v-if="isRelationData.length" class="mt30 tc pb50" :page-size="isRelationPageSize" :total="isRelationTotal" :current="isRelationPageNum" @on-change="changePageIsRelation"></Page>
    <Title title="未关联" class="mt30"></Title>
    <vuicard :data="unRelationData" :isRelation="false" @on-init="onSearch"></vuicard>
    <Page  v-if="unRelationData.length" class="mt30 tc pb50" :page-size="unRelationPageSize" :total="unRelationTotal" :current="unRelationPageNum" @on-change="changePageUnRelation"></Page>
    <div class="tc pt20">
        <Button type="primary" @click="handleBack">上一步</Button>
        <Button type="primary" @click="handleSave">完成</Button>
        <Button type="text" @click="handleNext">以后再完善</Button>
    </div>
</div>
</template>
<script>
import Title from './title'
import vuicard from './card'
    export default {
        components: {
            Title,
            vuicard
        },
        data() {
            return{
                isRelationData: [],
                unRelationData: [],
                data: [],
                formItem: {
                    serviceType: '',
                    serviceName:''
                },
                id: '',
                unRelationPageNum: 1,
                unRelationPageSize: 10,
                unRelationTotal: 0,
                isRelationPageNum: 1,
                isRelationPageSize: 10,
                isRelationTotal: 0,
                serviceNames: [ // 0垂钓 1采摘 2景区 3餐饮 4住宿 空为全部
                    {label: '垂钓', value: '0'},
                    {label: '采摘', value: '1'},
                    {label: '民宿', value: '4'},
                    {label: '农家乐', value: '3'},
                    {label: '景区', value: '2'},
                ]
            }
        },
        created() {
            this.id = this.$route.query.id
            this.getUnRelationData()
            this.getIsRelationData()
        },
        methods:{
            getUnRelationData () {
                this.$api.post('/member/fishing/findJoinServiceList', {
                    account: this.$user.loginAccount,
                    service_name: this.formItem.serviceName,
                    joinService: 0, //  0 未关联。 1已关联
                    pageNum: this.unRelationPageNum,
                    pageSize: this.unRelationPageSize,
                    id: this.id,
                    type: this.formItem.serviceType === 'undefined' ? '' : this.formItem.serviceType // 0垂钓 1采摘 2景区 3餐饮 4住宿 空为全部
                }).then(response => {
                    if (response.code === 200) {
                        this.unRelationData = response.data.dataList
                        this.unRelationTotal = response.data.total
                    }
                })
            },
            changePageUnRelation (e) {
                this.unRelationPageNum = e
                this.getUnRelationData()
            },
            getIsRelationData () {
                this.$api.post('/member/fishing/findJoinServiceList', {
                    account: this.$user.loginAccount,
                    service_name: this.formItem.serviceName,
                    joinService: 1, //  0 未关联。 1已关联
                    pageNum: this.isRelationPageNum,
                    pageSize: this.isRelationPageSize,
                    id: this.id,
                    type: this.formItem.serviceType === 'undefined' ? '' : this.formItem.serviceType // 0垂钓 1采摘 2景区 3餐饮 4住宿 空为全部
                }).then(response => {
                    if (response.code === 200) {
                        this.isRelationData = response.data.dataList
                        this.isRelationTotal = response.data.total
                    }
                })
            },
            changePageIsRelation (e) {
                this.isRelationPageNum = e
                this.getIsRelationData()
            },
            onSearch () {
                this.changePageIsRelation(1)
                this.changePageUnRelation(1)
            },
            // 保存
            handleSave () {
                this.$api.post('/member/fishing/updateFishingService',{
                    flag: '1',
                    id: this.id,
                }).then(response=>{
                    if(response.code == 200){
                        this.$router.push('/fishing/service')
                    }
                })
            },
            // 以后在完善
            handleNext () {
                this.$router.push('/fishing/service')
            },
            // 上一步
            handleBack () {
                this.$router.push('/addService/step4?id='+this.id)
            }
        }
    }
</script>