<template>
    <div>
        <Row v-show="xian">
            <p style="text-align: center;margin-top: 30px;font-size: 16px;line-height: 200px">
                你所填的任何信息都会在这里形成人物百科</p>
        </Row>
        <Row v-show="shi"  style="margin: 40px 120px ">
                <div class="mian-bot1">
                    <div class="mian-bot3-title">
                        <p>隐私信息</p>
                        <div>{{contract}}</div>
                    </div>
                </div>
                <div class="mian-bot1">
                    <div class="mian-bot3-title">
                        <p>网络信息</p>
                        <div>{{basicInfo}}</div>
                    </div>
                </div>
                <div class="mian-bot1">
                    <div class="mian-bot3-title">
                        <p>教育经历</p>
                        <div>{{education}}</div>
                    </div>
                </div>
                <div class="mian-bot1">
                    <div class="mian-bot3-title">
                        <p>工作经历</p>
                        <div>{{work}}</div>
                    </div>
                </div>
                <!--<div class="mian-bot1">
                    <div class="mian-bot3-title">
                        <p>种养信息</p>
                        <div>{{farmlan}}</div>
                    </div>
                </div>-->
                <div class="mian-bot1">
                    <div class="mian-bot3-title">
                        <p>政治面貌</p>
                        <div>{{policial}}</div>
                    </div>
                </div>
                <div class="mian-bot1">
                    <div class="mian-bot3-title">
                        <p>宗教信仰</p>
                        <div>{{religion}}</div>
                    </div>
                </div>
        </Row>
        <div class="footer-btn">
			<Button type="primary"  class="zhuce-btn1" @click="next">继续</Button>
		</div>
    </div>
</template>
<script>
import api from '~api'
import $ from 'jquery'
export default {
    data() {
        return {
            contract: '',
            education: '',
            work: '',
            farmlan: '',
            policial: '',
            religion: '',
            honor: '',
            basicInfo: '',
            xian: true,
            shi: false
        }
    },
    created: function() {
        this.fetchData()
    },
    methods: {
    	next(){
			let type=this.$route.meta.type
			if(1 === type){
				this.$parent.$parent.$parent.gotoPathSec(25)
			}else{
				this.$parent.$parent.$parent.gotoPath(25)
			}
		},
        fetchData: function() {
                api.get('/member/userFullInfo/findUserFullInfo')
                    .then(response => {
                    	console.log("全部",response)
                        if (null == response.data) {
                            this.xian = true;
                            this.shi = false;
                        } else {
                            this.xian = false;
                            this.shi = true;
                            this.contract = response.data.contract1
                            this.education = response.data.education
                            this.work = response.data.work
                            this.farmlan = response.data.farmlan1
                            this.policial = response.data.policial1
                            this.religion = response.data.religion1
                            this.basicInfo = response.data.basic1
                        }
                    })
        }
    }
}
</script>
<style scoped>
.main-bot {
    width: 600px;
    /*border-top: 1px solid #ededed;*/
    position: relative;
}

.mian-bot1 {
    margin: 20px 0 30px;
}

.mian-bot3-title {
     width: auto;
    font-size: 16px;
    border-left: 4px solid #00c587;
    padding:0 0 10px 10px;
    line-height: 16px;
	margin-bottom: 5px;
}

.mian-bot3-title p{
    display: block;
    font-size: 16px;
    padding-left: 10px;
    line-height: 16px;
    margin-bottom: 10px;
}
.mian-bot3-title div{
    font-size: 14px;
}
.mian-bot3-title a {
    color: #333;
    font-size: 12px;
}

.main-bot2 img {
    margin-right: 16px;
    margin-top: 8px;
}

.main-bot2 p {
    font-size: 14px;
    line-height: 30px;
}

.main-bot2 ul {
    margin-right: 63px;
}

.main-bot2 ul li {
    line-height: 30px;
    font-size: 14px;
}

.main-bot3 li {
    line-height: 18px;
}

.main-bot3 ul {
    margin-left: 24px;
}

.check-box ul li {
    margin-bottom: 20px;
    font-size: 14px;
}

.ivu-switch-checked {
    border-color: #00c587;
    background-color: #00c587;
}

.ivu-btn-group-small>.ivu-btn {
    padding: 4px 12px;
    font-size: 12px;
    border-radius: 3px;
}

.ivu-btn-group:not(.ivu-btn-group-vertical) .ivu-btn-primary:not(:first-child):not(:last-child) {
    border: 1px solid #00c587;
}

.ivu-btn:hover {
    color: #00c587;
    background-color: #fff;
    border-color: #00c587;
}


/*本页出了头和底部样式结束*/
</style>