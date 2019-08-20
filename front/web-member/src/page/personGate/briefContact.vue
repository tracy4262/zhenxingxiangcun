<template>
    <div class="pb50">        
        <img src="../../img/com-banner7.jpg" height="400" width="100%" alt="">
        <div class="layouts">
            <div class="tc pt20 mb50">
                <h5 class="mt30">联系我们</h5>
                <p class="mt10">Contact us</p>
            </div>
            <Row type="flex" align="middle" class="mb50">
                <Col span="12">
                    <!-- <h5 class="mb20" v-if="data.userName.status">{{data.userName.model}}</h5> -->
                    <Form class="com-contact-list" :label-width="100" label-position="left">
                         <!-- 1公开 0隐藏 -->
                        <FormItem label="姓名：" v-if="data.userName.status">{{data.userName.model}}</FormItem>
                        <FormItem  label="手机号：" v-if="data.phone.status">{{data.phone.model}}</FormItem>
                        <FormItem  label="座机号：" v-if="data.tel.status">{{data.tel.model}}</FormItem>
                        <FormItem  label="邮箱：" v-if="nextWorkData.status">{{nextWorkData.Email.model}}</FormItem>
                        <FormItem  label="邮编：" v-if="data.postalCode.status">{{data.postalCode.model}}</FormItem>
                        <FormItem  label="QQ：" v-if="nextWorkData.status">{{nextWorkData.QQ.model}}</FormItem>
                        <FormItem  label="通讯地址：" v-if="data.addr.status">{{data.addr.model.substring(0, data.addr.model.length)}}</FormItem>
                        <FormItem  label="详细地址：" v-if="data.addrDetail.status">{{data.addrDetail.model}}</FormItem>
                    </Form>
                </Col>
                <Col span="12" v-if="data.coordinatePoint.model.lat && data.coordinatePoint.status">
                    <!--<a target="_blank" :href="`http://map.baidu.com/?newmap=1&ie=utf-8&s=s%26wd%3D${location}`">-->
                    <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${data.coordinatePoint.model.lat},${data.coordinatePoint.model.lng}&title=我的位置&content=${location}&output=html`">
                        <img :src="`//api.map.baidu.com/staticimage?height=200&center=${data.coordinatePoint.model.lng},${data.coordinatePoint.model.lat}&zoom=15&scale=2&markers=${data.coordinatePoint.model.lng},${data.coordinatePoint.model.lat}`" alt="" width="100%">
                    </a>
                </Col>
            </Row>
            
        </div>
    </div>
</template>
<script>
import api from '~api'
import $ from 'jquery'
import { navStatus } from '~page/companyGate/mixins/commonMixins'
export default {
    mixins: [navStatus],
    data () {
        return {
            index: 9,
            data:{
                    avatar:'',
                    userName:{model:'',name:'姓名',status:false},
                    sex:{model:'',name:'性别',status:false},
                    ethnicGroup:{model:'',name:'民族',status:false},
                    birthday:{model:'',name:'生日',status:false},
                    profession:{model:'',name:'职业',status:false},
                    professionalTitle:{model:'',name:'职称',status:false},
                    species:{model:'',name:'擅长物种',status:false},
                    phone:{model:'',name:'手机号码',status:false},
                    addr:{model:'',name:'常住地',status:false},
                    addrDetail:{model:'',name:'详细地址',status:false},
                    coordinatePoint:{model:'',name:'坐标位置',status:false},
                    postalCode:{model:'',name:'邮政编码',status:false},
                    tel:{model:'',name:'座机号码',status:false},
                },
            nextWorkData:{
                    ID:{model:'',name:'农事无忧ID'},
                    QQ:{model:'',name:'QQ号码'},
                    Email:{model:'',name:'邮箱'},
                    domainName:{model:'',name:'申请域名'},
                    status:false
                },
            location: '',
            loginAccount: ''
        }
    },
    created(){
        this.loginAccount = this.$route.query.uid
        this.getData()
    },
    methods: {
         getData(){ //查询类型 1 个人 2 专家 3 企业 4 商城企业 5 机关 6 乡村
            this.$api.post('/member/perfectInfo/findPerfectInfo', { account: this.loginAccount }).then(response=>{
                if(response.code == 200){
                    var data = response.data
                    //隐私信息
                    if (
                        data.privateInformation &&
                        Object.keys(data.privateInformation).length
                    ) {
                        // 如果有数据说明之前已经保存过，不管是保存的是否是空数据，都已经有数据格式了
                        var datas = data.privateInformation
                        console.log('datasdatasdatasdatasdatasdatasdatasdatasdatasdatasdatasdatas', datas)
                        if(datas.coordinatePoint.model){
                            var center = {}
                            center.lng = datas.coordinatePoint.model.split(',')[0]
                            center.lat = datas.coordinatePoint.model.split(',')[1]
                            datas.coordinatePoint.model = center
                        }                        
                        this.data = datas                        
                        var v = this
                        // 转换经纬度
                        if(data.privateInformation.coordinatePoint.model){
                            $.ajax({
                                async : 'false',
                                url : 'http://api.map.baidu.com/geocoder/v2/',
                                type : 'GET',
                                dataType : 'jsonp',
                                jsonpCallback : 'showLocation',
                                timeout : '5000',
                                contentType : 'application/json;utf-8',
                                data : {
                                    location: v.data.coordinatePoint.model.lat + ',' + v.data.coordinatePoint.model.lng,
                                    output:'json',
                                    ak:'7syPirZ2AWxacMfHeAfuujdDgFmxCB5g',
                                    callback:'showLocation'
                                },
                                success : function(data) {
                                    console.log('data',data)
                                    v.location = data.result.formatted_address
                                }
                            });
                        }
                    }
                     //网络信息
                    if (
                        data.networkInformation &&
                        Object.keys(data.networkInformation).length
                    ) {
                        // 如果有数据说明之前已经保存过，不管是保存的是否是空数据，都已经有数据格式了
                        this.nextWorkData = data.networkInformation
                    }
                }
            })
        }
    },
    mounted() {
    }
}
</script>
<style lang="scss">
.briefCon{margin: 60px 0;line-height: 30px;}
.briefCon img{width: 100%;}
</style>
