<template>
    <div class="product-service">
        <Card class="product-service-card mb20">
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
                        <Col span="8">
                            <div class="btn-toolbar">
                                <Button type="text"  @click="handleEdit"  size="small"><Icon type="edit" size="16" class="pr5"></Icon> 编辑</Button>
                                <Button type="text"  @click="handleDel"  size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                            </div>
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
                    <!-- <Row>
                        <Col :span="24" class="detailCol">
                            <div class="content ell-2" v-if="data.knowledgeProperty && data.knowledgeProperty.length > 140">
                                <span class="ft12">知识产权：</span>
                                <span class="t-grey ft12" v-if="data.knowledgeProperty">{{data.knowledgeProperty.slice(0,140)}}...</span>
                                <Button type="text" size="small" @click="handleMoreKnowledge" v-if="!moreKnowledge">查看更多</Button>
                                <Button type="text" size="small" @click="handleMoreKnowledge" v-if="moreKnowledge">收起</Button>
                            </div>
                            <div class="content" v-else>
                                <span class="ft12">知识产权：</span>
                                <span class="t-grey ft12">{{data.knowledgeProperty}}</span>
                            </div>
                           
                        </Col>
                    </Row> -->
                    <Row class="pb10">
                        <Col :span="24" class="detailCol">
                            <div class="content" v-if="data.introduction && data.introduction.length > 140">
                                <span class="ft12">简介：</span>
                                <span class="t-grey ft12" v-if="data.introduction && !moreIntro">{{data.introduction.slice(0,140)}}...</span>
                                <span class="t-grey ft12" v-if="data.introduction && moreIntro">{{data.introduction}}</span>
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
    </div>
</template>


<script>
export default {
    props:{
        data:{
            type:Object,
            default: () => {
                return {}
            }
        },
        index:{
            type:Number,
            default: () => {
                return 0
            }
        }
    },
    data () {
        return {
            moreKnowledge:false,
            moreIntro:false

        }
    },
    methods:{
        // 查看更多
        handleMoreKnowledge(){
            this.moreKnowledge = !this.moreKnowledge
        },
        handleMoreIntro(){
            this.moreIntro = !this.moreIntro
        },
        //编辑
        handleEdit(){
            this.$emit('on-edit',this.index)
        },
        // 删除
        handleDel(){
            this.$Modal.confirm({
                title: '是否确定删除',
                content: '是否确认删除？',
                onOk:()=>{
                    this.$emit('on-del',this.index)
                },
                okText:'确定',
                cancelText:'取消'
            });
        },
    }
  
}
</script>
<style scoped>
 .honor-picture-list {
        width: 10%;
        height: 100px;
        float:left;
        margin-right: 10px;
    }
</style>

<style lang="scss">
.ft12{
    font-size: 12px;
}
.product-service{
    .product-img{
        text-align: center;
        img{
            display: block;
            width:100%;
            height:150px;
        }
    }
    .detailCol{
        // height:40px;
        line-height:28px;
        .ivu-tag{
            height:20px;
            line-height: 20px;
            font-size: 12px;
            margin-right: 10px;
        }
        .category-name{
            margin-right: 20px;
            font-size: 16px;
        }
        .category-grand{
            font-size: 12px;
        }
    }
}
</style>


