<template>
    <div>
        <div style="background-color: #d8d8d8; height: 1px; margin-left: 20px;"></div>
        <Row class="tc mt40" style="font-size: 0;">
            <div :class="activeIndex === 0 ? 'btn-title btn-title-active' : 'btn-title'" @click="change(0)">相关知识</div>
            <div :class="activeIndex === 1 ? 'btn-title btn-title-active' : 'btn-title'" @click="change(1)">相关政策</div>
            <div :class="activeIndex === 2 ? 'btn-title btn-title-active' : 'btn-title'" @click="change(2)">相关资讯</div>
        </Row>
        <Row class="mt20 mb40">
            <Card>
            <!-- <div class="tc" v-if="dataList.length === 0">{{ text }}</div> -->
            <div v-if="dataList.length === 0" class="pd20 tc">
                <img src="../../../assets/imgs/no-result.png" height="100" alt="">
                <p class="t-grey">暂无数据</p>
            </div>
            <div v-else>
                <articles :dataType="type" :data="item" v-for="(item,index) in dataList" :key="index" class="mb15"/>
                <div class="tc">
                <Button type="ghost" @click="more">查看更多...</Button>
                </div>
            </div>
            </Card>
        </Row>
    </div>
</template>
<script>
/* eslint-disable */
import articles from './articles'
export default {
    name: 'related',
    components: {
        articles
    },
    data () {
        return {
            activeIndex: 0,
            dataList: [],
            type: '知识',
            text: '',
            queryType: 'knowledge'
        }
    },
    created () {
        // 查询相关知识资讯政策
        this.init()
    },
    methods: {
        init () {
            this.$api.post('member/speciesWiki/information', {
                type: this.queryType,
                speciesId: this.$route.query.speciesid,
                pageSize: 5
            }).then(response => {
                console.log('response', response)
                if (response.code === 200) {
                if (response.data.list.length > 0) {
                    this.dataList = response.data.list
                } else {
                    this.dataList = []
                    this.text = '暂无数据'
                }
                }
            })
        },
        change (index) {
            this.activeIndex = index
            if (index === 0) {
                this.type = '知识'
                this.queryType = 'knowledge'
            } else if (index === 1) {
                this.type = '政策'
                this.queryType = 'policy'
            } else if (index === 2) {
                this.type = '动态'
                this.queryType = 'information'
            }
            this.init()
        },
        more () {
            if (this.activeIndex === 0) {
                window.open('/51index/knowledgeList', '_blank')
            } else if (this.activeIndex === 1) {
                window.open('/51index/policyList', '_blank')
            } else if (this.activeIndex === 2) {
                window.open('/51index/inforMationList', '_blank')
            }
        }
    }
}
</script>
<style lang="scss" scoped>
.btn-title {
  display: inline-block;
  height: 48px;
  width: 173px;
  background-color: #e8e8e8;
  font-size: 16px;
  line-height: 48px;
  cursor: pointer;
  /* &:hover {
    background-color: #00c981;
  } */
}
.btn-title-active {
  transition: 0.5s;
  background-color: #00c981;
  color: #e4f9f1;
}
</style>
