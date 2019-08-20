<template>
<div class="wrapper layout">
    <top :address="false" />
   
    <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="4" class="main-l">
                        <high-app name="高级应用" />
                        <Divider />
                        <base-app name="基础应用" />
                        <Divider />
                        <user-app name="通用应用" />
                    </Col>
                    <Col span="20">
                        <member-header />

                        <Tabs value="cardManage" class="company-code-tabs">
                            <TabPane label="名片管理" name="cardManage" class="pd20">
                                <cardManage :userHead="uploadHead.src" @on-head-click="onHeadClick" />
                            </TabPane>
                            <TabPane :label="internaCode" name="internaCodeManage" class="pd20">
                                <internaCode :show="internaCodeShow" />
                            </TabPane>
                            <TabPane :label="securityCode" name="securityCodeManage" class="pd20">
                                <securityCode :show="securityCodeShow" />
                            </TabPane>
                            <TabPane :label="ascendCode" name="ascendCodeManage" class="pd20">
                                <ascendCode :show="ascendCodeShow" />
                            </TabPane>
                        </Tabs>

                    </Col>
                </Row>
            </div>
        </div>
        <!-- 头像上传 -->
        <my-upload field="img"
        @crop-success="cropSuccess"
        v-model="uploadHead.show"
        :url="uploadHead.url"
        :headers="uploadHead.headers"
        :params="uploadHead.otherParams" />
   </div>
</template>

<script>
import  top from '../../top'
import  highApp from '~components/memberHighApp'
import  BaseApp from '~components/memberBaseApp'
import UserApp from '~components/memberUserApp'
import myUpload from 'vue-image-crop-upload'
import  cardManage from './cardManage'
import  internaCode from './internaCode'
import  securityCode from './securityCode'
import  ascendCode from './ascendCode'
import api from '~src/api/api'
import memberHeader from './components/memberHeader'

export default {
    components:{
        top,
        highApp,
        BaseApp,
        myUpload,
        cardManage,
        internaCode,
        securityCode,
        ascendCode,
        memberHeader,
        UserApp
    },
    data() {
        return {
            displayName: '吴刚',
            loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            signature: '还没有签名！',
            internaCode: (h) => {
                return h('div',[
                        h('span','国际码管理'),
                        h('Dropdown',{
                            scopedSlots: {
                                list:  props => {
                                    return h('DropdownMenu', props.list,[
                                        h('DropdownItem','生成国际商品码'),
                                        h('DropdownItem','查询国际商品码')
                                    ])
                                }
                            },
                            props:{
                                placement:'bottom'
                            },
                            nativeOn: {
                                click: (e) => {
                                    if(e.target.innerText === '生成国际商品码'){
                                        this.internaCodeShow = true
                                    }else{
                                        this.internaCodeShow = false
                                    }
                                }
                            }
                        }, [
                            h('Icon',{
                                props:{
                                    type:'arrow-down-b'
                                },
                                style:{
                                    margin:'0 0 0 12px'
                                }
                            })
                        ]
                    )
                ])
            },
            securityCode: (h) => {
                return h('div',[
                        h('span','防伪码管理'),
                        h('Dropdown',{
                            scopedSlots: {
                                list:  props => {
                                    return h('DropdownMenu', props.list,[
                                        h('DropdownItem','生成防伪码'),
                                        h('DropdownItem','查询防伪码')
                                    ])
                                }
                            },
                            props:{
                                placement:'bottom'
                            },
                            nativeOn: {
                                click: (e) => {
                                    if(e.target.innerText === '生成防伪码'){
                                        this.securityCodeShow = true
                                    }else{
                                        this.securityCodeShow = false
                                    }
                                }
                            }
                        }, [
                            h('Icon',{
                                props:{
                                    type:'arrow-down-b'
                                },
                                style:{
                                    margin:'0 0 0 12px'
                                }
                            })
                        ]
                    )
                ])
            },
            ascendCode: (h) => {
                return h('div',[
                        h('span','追溯码管理'),
                        h('Dropdown',{
                            scopedSlots: {
                                list:  props => {
                                    return h('DropdownMenu', props.list,[
                                        h('DropdownItem','生成追溯码'),
                                        h('DropdownItem','查询追溯码')
                                    ])
                                }
                            },
                            props:{
                                placement:'bottom'
                            },
                            nativeOn: {
                                click: (e) => {
                                    if(e.target.innerText === '生成追溯码'){
                                        this.ascendCodeShow = true
                                    }else{
                                        this.ascendCodeShow = false
                                    }
                                }
                            }
                        }, [
                            h('Icon',{
                                props:{
                                    type:'arrow-down-b'
                                },
                                style:{
                                    margin:'0 0 0 12px'
                                }
                            })
                        ]
                    )
                ])
            },
            uploadHead:{
                src:'../src/img/timg-(1).png',
                url:'https://httpbin.org/post',
                show:false,
                otherParams: {
                    token: '123456798',
                    name: 'img'
                },
                headers: {
                    smail: '*_~'
                }

            },
            internaCodeShow:true,
            securityCodeShow:true,
            ascendCodeShow:true
        }
    },
    created(){
    },
    methods:{
        cropSuccess(imgDataUrl, field){
            this.uploadHead.src = imgDataUrl
        },
        onHeadClick(){
            console.log(111)
            this.uploadHead.show = true
        },
        show() {
            this.modal2 = true;
            this.$router.push("/pro/member/step1");
        },
        tabsClick(name){
            this.tabsDynaShow = (name === 'name1' ? true : false)
            //this.columnMydynamic=[]
            //showMydynamic(0)
        },
        publish() {
            if(this.publishShow == false) {
                this.publishShow = true
            }
        },
        nextPage(page) {
            this.currentPage = page;
            axios.get(this.$url.api + '/login/friendlist/' + this.currentPage)
                .then(response => {
                    this.friend = response.data.list
                    this.total = response.data.total
                })
        },
    }
}
</script>

