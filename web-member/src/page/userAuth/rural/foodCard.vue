<template>
    <div class="food-quality">
        <Card class="mb20 food-card">
            <Row  type="flex" align="middle">
                <Col :span="6">
                   <Avatar :src="data.avatar" class="avatarImg" />
                </Col>
                <Col :span="18">
                    <Row>
                        <Col :span="12" class="colDetail">  
                            <span class="category-name">{{data.name}}</span>
                        </Col>
                        <Col span="12">
                            <div class="btn-toolbar">
                                <Button type="text"  @click="handleEdit"  size="small"><Icon type="edit" size="16" class="pr5"></Icon> 编辑</Button>
                                <Button type="text"  @click="handleDel"  size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                            </div>
                        </Col>
                    </Row>
                    <Row>
                        <Col :span="24" class="colDetail">
                            <span  style="color:#00c587">{{data.category}}</span>
                        </Col>
                    </Row>
                    <Row class="pt5 t-grey ft12">
                        <Col span="24">
                            <div class="content ell-2" v-if="!more && data.introduction">
                                {{data.introduction.slice(0,140)}}...<Button type="text" size="small" v-if="data.introduction.length > 140" @click="handleMore">查看更多</Button>
                            </div>
                            <div class="content" v-if="more && data.introduction">
                                {{data.introduction}}
                                <div class="tr"><Button type="text" size="small" @click="handleMore">收起</Button></div>
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
            default:()=>{
                return {
                }
            }
        },
        index:{
            type:Number,
            default:()=>{
                return 0
            }
        }
    },
    data(){
        return{
            more:false
        }
    },
    methods:{
        // 查看更多
        handleMore(){
            this.more = !this.more
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

<style lang="scss">
.food-quality{
    .food-card{
        width:50%;
        .avatarImg{
            width:86px;
            height:108px;
            border-radius: 0;
        }
        .colDetail{
            line-height: 35px;
        }
        .category-name{
            font-size: 16px;
            color:#4A4A4A;
        }
    }
}

</style>


