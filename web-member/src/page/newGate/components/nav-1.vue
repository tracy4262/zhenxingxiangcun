<template>
    <div>
        <top/>
        <nav class="new-gate-nav">
            <Row class="pt15 pb15" type="flex" align="middle" style="min-height:81px;">
                <Col span="4">
                    <div class="vui-flex pl20">
                        <img v-if="websiteInfo.websiteLOGO" :src="websiteInfo.websiteLOGO" alt="" width="51px" height="51px">
                        <div class="vui-flex-item ell pl10" style="line-height: 51px;" :title="`${websiteInfo.websiteName}${websiteInfo.nameSuffix}`">
                            {{websiteInfo.websiteName}}{{websiteInfo.nameSuffix}}
                        </div>
                    </div>
                </Col>
                <Col span="20">
                <div class="pr50">
                     <ul class="clear pr50">
                        <template v-for="(item,index) in data">
                            <li>
                                <Dropdown>
                                    <router-link :to="`/portals/${item.attributionId}?uid=${$route.query.uid}`" class="item" :class="{'on': item.checked}">
                                        {{item.columnName}}
                                    </router-link>
                                </Dropdown>
                            </li>
                        </template>
                    </ul>
                    
                </div>
                </Col>
            </Row>
        </nav>
        <router-view></router-view>
        <foot/>
    </div>
</template>
<script>
import foot from './foot'
import top from './top'
export default {
    components:{
        top,
        foot
    },
    data () {
        return {
            data:[{
                title: '首页',
                url: '/portals/index',
                checked: true,
                isShow: true
            },{
                title: '乡村介绍',
                url: '/portals/Introduction',
                checked: false,
                isShow: true
            }, {
                title: '乡村动态',
                url: '/portals/dynamic',
                checked: false,
                isShow: true
            }, {
                title: '乡村政策',
                url: '/portals/policy',
                checked: false,
                isShow: true
            }, {
                title: '乡村知识',
                url: '/portals/knowledge',
                checked: false,
                isShow: true
            }, {
                title: '标准',
                url: '/portals/standard',
                checked: false,
                isShow: true
            }, {
                title: '专家团队',
                url: '/portals/expert',
                checked: false,
                isShow: true
            }, 
            {
                title: '联系我们',
                url: '/portals/contact',
                checked: false,
                isShow: true
            }],
            loginAccount:'',
            headData:{},
            templateId: '',
            websiteInfo: {}
        }
    },
    created(){
        this.loginAccount = this.$route.query.uid
        this.$api.post('/member-reversion/realStep/findEnableStep', {
                account: this.loginAccount
            }).then(response => {
            if (response.code === 200) {
                if (response.data) {
                    this.templateId = response.data.templateId
                    this.getData()
                    this.getIntroduction()
                }
            }
        })
    },
    methods:{
        getIndex(){
            let path = window.location.pathname.split('/')[2]
            let checkedIndex = 0
            this.data.forEach((item, index) => {
                item.checked = false
                if (path === item.attributionId) {
                item.checked = true
                checkedIndex = index
                } else {
                item.checked = false
                }
            })
            this.data.splice(checkedIndex, 1, this.data[checkedIndex])
        },
        
        getIntroduction () {
            // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
            let url = this.templateId === '0' ? '/member-reversion/websiteSettings/findWebsiteSettingsInfo' : '/member-reversion/user/websiteSettings/findWebsiteSettingsInfo'
            this.$api.post(url, {
                    account: this.loginAccount,
                    templateId: this.templateId
                }).then(response => {
                    if (response.code === 200) {
                        if (response.data.websiteInfo) {
                        this.websiteInfo = response.data.websiteInfo
                        }
                    }
            })
        },
        // 查询数据
        getData(){
            // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
            let url = this.templateId === '0' ? '/member-reversion/columnSetting/findColumnSettingInfo' : '/member-reversion/user/columnSetting/findColumnSettingInfo'
            this.$api.post(url, {
                account: this.loginAccount,
                templateId: this.templateId
            }).then(response => {
                if (response.code === 200) {
                    let arr = [{columnName: '首页', attribution: '全部', attributionId: 'index'}]
                    let data = arr.concat(response.data.columnSetting)
                    let arrs = []
                    for (var i = data.length - 1; i >= 0; i--){
                        arrs.push(data[i])
                    }
                    this.data = arrs
                    this.getIndex()
                }
            })
        }
    }
}
</script>
<style lang="scss" scoped>
.new-gate-nav {
    min-width: 1366px;
    margin: 0 auto;
    li{
        float: right;
    }
    .item{
        font-family: PingFangSC-Regular;
        color: #4A4A4A;
        font-size: 14px;
        padding: 10px 12px;
        display: block;
        &:hover,
        &.on{
            color: #00c587;
        }
    }
}
</style>
