<template>
    <Row type="flex" align="bottom" class="rural-item-tab" :style="{'border-bottom': breadcrumb.length ? 'none' : '1px solid #ddd'}">
        <Col span="12">
            <template v-if="breadcrumb.length">
                <Breadcrumb class="mb10">
                    <template v-for="item in breadcrumb" v-if="item.url">
                        <BreadcrumbItem :to="item.url">{{item.title}}</BreadcrumbItem>
                    </template>
                    <template v-else>
                        <BreadcrumbItem>{{item.title}}</BreadcrumbItem>
                    </template>
                </Breadcrumb>
            </template>
            <template v-else>
                <h5 class="mt10">{{title.cn}}</h5>
                <p class="t-grey mt5 mb10">{{title.en}}</p>
            </template>
        </Col>
        <Col span="12">
            <Tabs :value="tab.length  ? tab[0] : ''" @on-click="handleClick">
                <TabPane :label="item" :name="item" v-for="(item,index) in tab" :key="index"></TabPane>
                <a class="ivu-tabs-tab" :href="more" slot="extra" v-if="more === '' ? false : true">更多</a>
            </Tabs>
        </Col>
    </Row>
</template>
<script>
export default {
    props:{
        breadcrumb: {
            type: Array,
            default () {
                return []
            }
        },
        title: Object,
        tab: {
            type: Array,
            default () {
                return [] 
            }
        },
        more: {
            type: String,
            default: ''
        }
    },
    components:{
    },
    data () {
        return {
        }
    },
    created(){
    },
    methods:{
        handleClick (name) {
            this.$emit('on-click', name)
        }
    }
}
</script>
<style lang="scss">
$color: #7AAE00;
.rural-item-tab{
    margin-top: 20px;
    .ivu-tabs-bar{
        border: none;
        margin-bottom: 1px;
        .ivu-tabs-nav-right{
            margin: 8px 0 0 15px;
            a{
                color: #657180;
            }
        }
    }
    .ivu-tabs-nav{
        float: right !important;
        .ivu-tabs-tab-active,
        .ivu-tabs-tab:hover{
            color: $color
        }
        .ivu-tabs-ink-bar{
            background: $color
        }
        .ivu-tabs-tab{
            &:last-child{
                margin-right: 0;
            }
        }
    }
}
</style>