<!--
    作者：chenqim
    时间：2019-01-07
    描述：生产基地详细信息编辑页
-->
<template>
    <div class="pd20" style="min-height: 500px;">
        <Card>
            <Row>
                <Col span="3">
                    <Button :type="allBtn ? 'primary' : 'text'" @click="onTagSelectAll">
                        <Tooltip placement="top" class="ell" :delay="1000">
                            全部
                            <div slot="content"> 全部 </div>
                        </Tooltip>
                    </Button>
                </Col>
                <Col span="3" v-for="(item, index) in fileTags" :key="index">
                    <Button style="max-width: 100%;" :type="item.checked ? 'primary' : 'text'" @click="onTagSelect(item, index)">
                        <Tooltip placement="top" class="ell" :delay="1000">
                            {{item.name}}
                            <div slot="content"> {{item.name}} </div>
                        </Tooltip>
                    </Button>
                </Col>
            </Row>
        </Card>
        <!-- 模块 -->
        <div class="mt20">
            <component v-bind:is="mode" :appId="appId" @handleRefresh="refresh"></component>
        </div>
        <div class="tc mt40">
            <Button type="default" @click="quit" style="width: 105px;">退出</Button>
            <Button type="primary" @click="last" style="width: 105px;" class="ml10">上一步</Button>
            <Button type="primary" @click="next" style="width: 105px;" class="ml10">完成</Button>
        </div>
    </div>
</template>
<script>
import all from './all'
import geography from './geography'
import landInfo from './landInfo'
import environment from './environment'
import communalFacilities from './communalFacilities'
import economicGrowth from './economicGrowth'
export default {
    name: 'detailInfo',
    components: {
        all,
        geography,
        landInfo,
        environment,
        communalFacilities,
        economicGrowth
    },
    data () {
        return {
            fileTags: [],
            mode: 'all',
            appId: '',
            allBtn: true,
            active: null,
            baseId: ''
        }
    },
    created () {
        this.baseId = this.$route.query.id
        this.initFileTags()
    },
    methods: {
        // 选择全部
        onTagSelectAll () {
            this.setStatus(this.fileTags)
            this.allBtn = true
            this.mode = 'all'
        },
        // 选择标签
        onTagSelect (d, index) {
            console.log('ididididi', d)
            console.log(d.mode)
            this.setStatus(this.fileTags)
            d.checked = true
            this.allBtn = false
            this.mode = d.mode
            this.appId = d.id
            this.active = index
        },
        // 初始化应用标签信息
        initFileTags () {
            this.$api.post('/member-reversion/productionBase/findModuleInfo', {
                account: this.$user.loginAccount
            }).then(response => {
                console.log('tag res', response)
                if (response.code === 200) {
                    response.data.forEach(element => {
                        this.fileTags.push({
                            id: element.appId,
                            name: element.appName,
                            mode: element.url
                        })
                    })
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        refresh () {},
        // 循环设置状态
        setStatus (obj) {
            obj.forEach(item => item.checked = false)
        },
        quit () {
            this.$router.push('/member/productionBaseList')
        },
        last () {
            this.$emit('last')
        },
        next () {
            this.$api.post('/member-reversion/productionBase/editComplete', {
                baseId: this.baseId,
                account: this.$user.loginAccount
            }).then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功！')
                    this.$emit('next')
                } else {
                    this.$Message.error('服务器异常！')
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        }
    }
}
</script>
<style lang="scss" scoped>

</style>
