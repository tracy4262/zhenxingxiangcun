<template>
    <div>
        <div class="ivu-table ivu-table-border ivu-table-small table">
            <table>
                <thead class="ivu-table-header tc">
                    <tr>
                        <th colspan="2">绿色食品 产地环境质量标准（NY/T 391-2013）</th>
                        <th colspan="2">基地食用菌栽培基质</th>
                        <th rowspan="2">单位</th>
                    </tr>
                    <tr>
                        <th>项目</th>
                        <th>指标</th>
                        <th colspan="2">指标</th>
                    </tr>
                </thead>
                <tbody class="ivu-table-body">
                    <tr>
                        <td>总汞</td>
                        <td>≤0.1</td>
                        <td><Input :maxlength="6" v-model="data.mercuryFirst" /></td>
                        <!--<td><Input :maxlength="6" v-model="data.mercurySecond" /></td>-->
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>总砷</td>
                        <td>≤0.8</td>
                        <td><Input :maxlength="6" v-model="data.arsenicFirst" /></td>
                        <!--<td><Input :maxlength="6" v-model="data.arsenicSecond" /></td>-->
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>总镉</td>
                        <td>≤0.3</td>
                        <td><Input :maxlength="6" v-model="data.cadmiumFirst" /></td>
                        <!--<td><Input :maxlength="6" v-model="data.cadmiumSecond" /></td>-->
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>总铅</td>
                        <td>≤35</td>
                        <td><Input :maxlength="6" v-model="data.leadFirst" /></td>
                        <!--<td><Input :maxlength="6" v-model="data.leadSecond" /></td>-->
                        <td>mg/kg</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <Row class="mt20">
            <Col span="2" offset="11">
                <Button type="primary" long @click.native="handleSave">保存</Button>
            </Col>
        </Row>
    </div>
</template>

<script>
export default {
    components:{
    },
    data() {
        return {
            data: {
                mercuryFirst: '', 
                productId: '', 
                arsenicFirst: '', 
                cadmiumFirst: '', 
                leadFirst: '', 
                edibleId: '', 
                mercurySecond: '', 
                arsenicSecond: '', 
                cadmiumSecond: '', 
                leadSecond: ''
            }
        }
    },
    created () {
        this.$api.post('/member/product-edible-fungus/query', {
            productId: this.$route.query.id
        }).then(res => {
            if (res.data !== undefined) {
                this.data = res.data
            }
        })
    },
    methods: {
        handleSave () {
            this.$api.post('/member/product-edible-fungus/update', {
                productId: this.$route.query.id,
                data: this.data
            }).then(res => {
                if(res.code === 200) {
                    this.$Message.success('修改成功')
                } else {
                    this.$Message.error('修改失败')
                }
            })
        }
    }
}
</script>
<style lang="scss">
</style>