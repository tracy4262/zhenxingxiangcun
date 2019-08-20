<template>
    <div class="product-service pt30 pl10 pr10">
        <Form  label-position="left" :label-width="150" class="pb20">
            <Card class="product-service-card mb20"  v-for="(data,index) in data" :key="index">
                <Row >
                    <Col :span="4" class="product-img" v-if="data.pictureList[0]">
                        <img  :src="data.pictureList[0]" alt="">
                    </Col>
                    <Col :span="data.pictureList[0] ? 19:24" :offset="data.pictureList[0] ? 1:0">
                        <Row  class="detailCol">
                            <Col :span="16">
                                <span class="category-name">{{data.name}}</span>
                                <Tag type="border" color="primary">{{data.category}}</Tag>
                                <Tag type="border" color="primary">{{data.product}}</Tag>
                            </Col>
                        </Row>
                        <Row>
                            <Col :span="24"  class="detailCol ft12">
                                关联物种：{{data.relatedSpecies}}
                            </Col>
                            <Col :span="24"  class="detailCol ft12">
                                品牌：{{data.brand}}
                            </Col>
                        </Row>
                        <Row class="pb10">
                            <Col :span="24" class="detailCol">
                                <div class="content ell-2" v-if="data.introduction && data.introduction.length > 140">
                                    <span class="ft12">简介：</span>
                                    <span class="t-grey ft12" v-if="data.introduction">{{data.introduction.slice(0,140)}}...</span>
                                    <Button type="text" size="small" @click="handleMoreIntro" v-if="!moreIntro">查看更多</Button>
                                    <Button type="text" size="small" @click="handleMoreIntro" v-if="moreIntro">收起</Button>
                                </div>
                                <div class="content" v-else>
                                    <span class="ft12">简介：</span>
                                    <span class="t-grey ft12">{{data.introduction}}</span>
                                </div>  
                            </Col>
                        </Row>
                        <Row :gutter="8">
                            <Col span="4" v-for="(pic,index) in data.certificateList" :key="index"  class="pb10">
                                <div>
                                    <img v-if="pic" :src="pic" width="100%" height="120px;">
                                </div>
                            </Col>
                        </Row>
                    </Col>
                </Row>
                
            </Card>
        </Form>
    </div>
</template>
<script>
export default {
    created () {
    },
    data () {
        return {
            data:[],
            moreIntro:false
        }
    },
    methods: {
        //接收数据
        getData(val){
            this.data = val
        },
        //查看更多
        handleMoreIntro(){
            this.moreIntro = !this.moreIntro
        }
    }
}
</script>

<style lang="scss">
</style>
