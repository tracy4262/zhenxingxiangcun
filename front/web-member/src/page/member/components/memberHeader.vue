<template>
    <div class="vui-member-header">
        <Row type="flex" align="middle" class="layouts">
            <Col span="4">
                <img :src="avatar" class="vui-member-user-head" width="160" height="160" v-if="avatar!=''">
                <img src="../../../img/default_header.png" class="vui-member-user-head" width="160" height="160" v-else>
            </Col>
            <Col span="12">
                    <Row>
                        <Col span="15">
                            <div class="user-name ell" v-if="displayName !== ''">{{displayName}}</div>
                        </Col>
                    </Row>
                    <Row v-if="editSignature" class="mt15">
                        <div class="ns-ava ns-ava-line" style="-webkit-box-orient: vertical;" v-if="signature" @click="changeSignature">{{signature}}  <Icon type="edit"></Icon></div>
                        <div class="ns-ava" v-else @click="changeSignature">还没有签名  <Icon type="edit"></Icon></div>
                    </Row>
                    <Row v-else class="mt15">
                        <Input type="text" class="ns-ava" v-if="signature" v-model.trim="signature" @on-blur="saveSignature" :placeholder="signature" autofocus/>
                        <Input type="text" class="ns-ava" v-else v-model.trim="signature" @on-blur="saveSignature" placeholder="请输入签名"/>
                    </Row>
                    <Row class="mt15">
	                    <Avatar icon="ios-card" size="small" style="background-color: #19be6b" @click.native="handleRestartAuthen"/>
	                    <span class="ns-avas mr10" >{{authen}}</span>
                    	<Avatar icon="logo-vimeo" size="small" style="background-color: #ff9900" />
                    	<span class="ns-avas">{{attestation}}</span>
                    </Row>
                    <Row class="mt15">
                        <Col span="7" class="guide-button">
                            <Button type="default" @click="showUserGuide" v-if="isMemberCenter">打开用户向导</Button>
                        </Col>
                    </Row>
            </Col>
            <Col span="8">
            	<!--<Row type="flex" justify="center" class="vui-user-info tc">
		            <Col span="8">
		                <p class="follow-num">关注</p>
		                 <span class="follow-title">{{favorite}}</span>
		            </Col>
		            <Col span="8">
		            	<p class="follow-num">粉丝</p>
		                <span class="follow-title">{{favorite}}</span>
		            </Col>
		            <Col span="8">
		             	<p class="follow-num">总访问</p>
		                <span class="follow-title">{{number}}</span>
		            </Col>
        		</Row>-->
            </Col>
        </Row>
        <div>
        </div>
    </div>
</template>
<script>
import api from '~api'
export default {
    name:'memberCenter',
    props: {
        isMemberCenter: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            displayName: '',
            favorite: '0',
            number: '0',
            signature: '',
            authen: '未实名',
            avatar: '',
            status: false,
            attestation: '',
            editSignature: true,
            loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
        }
    },
    created () {
        this.$api.post('/member/login/findCurrentUser', {
            account: this.loginUser.loginAccount
        }).then(res => {
            console.log('res123', res)
        	if (res.data.displayName) {
        		this.displayName = res.data.displayName
        	}
            if (res.data.avatar) {
           	    this.avatar = res.data.avatar
            }
            // 判断是否实名 企业机关乡村由于是代理过来的 默认已实名
            // 个人专家种养户
            if (res.data.userType === 0 || res.data.userType === 4 || res.data.userType === 2) {
                if (res.data.isRealIdentity && res.data.isRealIdentity === 'Y') {
                    this.authen = '已实名'
                } else {
                    this.authen = '未实名'
                }
            } else {
                this.authen = '已实名'
            }
            this.favorite = res.data.favorite
            this.number = res.data.number
            // this.isCertification()
            if (res.data.signaTure) {
            	this.signature = res.data.signaTure
            }
            console.log('userType', res.data.userType)
            if (res.data.userType !== undefined) {
                let userType = res.data.userType
                //用户类型 0 个人用户  1 企业 2政府 3机关4专家 5乡村
                if (1 === userType) {
                    this.attestation = '企业认证'
                } else if (2 === userType) {
                    this.attestation = '种养户认证'
                } else if (3 === userType) {
                    this.attestation = '机关认证'
                } else if (4 === userType) {
                    this.attestation = '专家认证'
                } else if (5 === userType) {
                    this.attestation = '乡村认证'
                } else {
                    this.attestation = '个人用户'
                }
            }
        })
    },
    methods:{
        handleRestartAuthen () {
            this.$emit('on-click')
        },
        //修改签名
        changeSignature(){
            this.editSignature = !this.editSignature
        },
        saveSignature(){
            this.editSignature = !this.editSignature
            if(this.signature && this.signature!= " "){
                api.get('/member/login/editSignature?signature='+this.signature)
                .then(res => {
                    if(res.code === 200){
                        this.$Message.success('修改成功！')
                    }else{
                        this.$Message.error('修改失败！')
                    }
                })
            }
        },
        showUserGuide () {
            this.$emit('showUserGuide')
        }
    }
}
</script>

<style lang="scss">
.guide-button {
    .ivu-btn{
        background-color: #82ca99;
        color: #ffffff;
        border-color: #82ca99;
        &:hover {
            color: #ffffff;
            background-color: #82ca99;
            border-color: #82ca99;
        }
    }
}
.vui-member-header{
    margin: 0 0 10px;
    padding: 70px 0 10px;
    background: url('../../../img/lunbo.png') no-repeat top center;
    border: 1px solid #ededed;
    height: 300px;
    .vui-user-info {
        font-size: 16px
    }
    .vui-member-user-head{
        margin-left: 20px;
        border: 5px solid #fff;
        width: 150px;
    	height: 150px;
    }
    .user-name{
        font-size: 20px;
        color: #fff;
    	font-family: "微软雅黑";
    .authen-color{
    }
    	color: #fff;
    	font-family: "微软雅黑";

    }
    .ivu-avatar{
    	vertical-align: middle;
    }
    .ns-ava{
    	color: #fff;
    	font-family: "微软雅黑";
        cursor: pointer;
    }
    .ns-avas{
        color: #fff;
        font-family: "微软雅黑";
    }
    .ns-ava-line{
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
    }
    .follow-num{
    	color: #045828;
    	font-family: "微软雅黑";
    	font-size: 14px;
    	margin-bottom: 30px;
    }
    .follow-title{
    	color: #fff;
    	font-family: arial;
    	font-size: 20px;
    }
}
</style>
