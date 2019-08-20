<template>
    <div class="layouts expertDynamic">
        <Card :padding="20" v-for="(item, index) in data" :key="index" class="ma-card mb20 standard-card-ivu">
            <Row @click.native="handleClick(item.standardDetailId)">
                <Col span="21">
                    <Row align="middle">
                        <Col span="4" class="line-hight">{{ item.standardNumber }}</Col>
                        <Col span="20" class="tl">
                            <h5 class="line-hight" >{{ item.chineseStandardName }}</h5> &nbsp;
                            <div class="ivu-tag ivu-tag-border-custom ivu-tag-checked " 
                                :style="item.standardTrait === '强制性标准' ? ' border-color: #FF7921;' : 'border-color: #F5A623;'">
                                <span class="ivu-tag-text" :style="item.standardTrait === '强制性标准' ? 'color: #FF7921;' : 'color: #F5A623'">{{ item.standardTrait }}</span>
                            </div>
                            <div class="ivu-tag ivu-tag-border-custom ivu-tag-checked" 
                                :style="item.standardStatus === '现行' ? 'border-color: #4AB344;' : 'border-color: #9B9B9B;'">
                                <span class="ivu-tag-text" :style="item.standardStatus === '现行' ? 'color: #4AB344;' : 'color: #9B9B9B'">{{ item.standardStatus }}</span>
                            </div>
                        </Col>
                    </Row>
                     <Row align="middle">
                        <Col span="24" class="line-hight"> <p>{{item.abstracts}}</p></Col>
                    </Row>
                </Col>
                <Col span="3">
                     <Row>
                        <Col span="24"  class="tc pb10">{{ moment(item.createTime).format('YYYY-MM-DD') }}</Col>
                    </Row>
                    <Row>
                        <Col span="24" class="tc"> <Button type="default" id="per-arrow-right"  shape="circle" icon="ios-arrow-right"></Button></Col>
                    </Row>
                </Col>
            </Row>
        </Card>
    </div>
</template>
<script>
export default {
    props: {
        data: {
            type: Array
        }
    },
    data() {
        return {
        }
    },
    created(){
    },
    methods:{
        handleClick(id){
            this.$emit('on-change', id)
        }
    }
}
</script>

<style lang="scss">
.expertDynamic{
    .ivu-tag{
        background: #fff;
    }
}

</style>