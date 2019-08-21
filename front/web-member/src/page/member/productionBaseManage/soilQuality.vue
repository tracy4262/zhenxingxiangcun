<template>
    <div>
        <div class="ivu-table ivu-table-border ivu-table-small table">
            <table>
                <thead class="ivu-table-header tc">
                    <tr>
                        <th colspan="7">绿色食品 产地环境质量标准（NY/T 391-2013）</th>
                        <th colspan="4">基地土壤肥力</th>
                        <th rowspan="4" width="80">单位</th>
                    </tr>
                    <tr>
                        <th rowspan="3" width="80">项目</th>
                        <th colspan="3">旱地</th>
                        <th colspan="3">水田</th>
                        <th colspan="2">旱地</th>
                        <th colspan="2">水田</th>
                    </tr>
                    <tr>
                        <th rowspan="2" width="80">pH＜6.5</th>
                        <th rowspan="2" width="80">6.5≤pH≤7.5</th>
                        <th rowspan="2" width="80">pH＞7.5</th>
                        <th rowspan="2" width="80">pH＜6.5</th>
                        <th rowspan="2" width="80">6.5≤pH≤7.5</th>
                        <th rowspan="2" width="80">pH＞7.5</th>
                        <th rowspan="2" width="80">PH范围</th>
                        <th rowspan="2">含量值</th>
                        <th rowspan="2">PH范围</th>
                        <th rowspan="2">含量值</th>
                    </tr>
                </thead>
                <tbody class="ivu-table-body">
                    <tr>
                        <td>总镉</td>
                        <td>≤0.30</td>
                        <td>≤0.30</td>
                        <td>≤0.40</td>
                        <td>≤0.30</td>
                        <td>≤0.30</td>
                        <td>≤0.40</td>
                        <td><Input :maxlength="6" v-model="data.cadmiumDroughtRange" /></td>
                        <td><Input :maxlength="6" v-model="data.cadmiumDroughtContent" /></td>
                        <td><Input :maxlength="6" v-model="data.cadmiumWaterRange" /></td>
                        <td><Input :maxlength="6" v-model="data.cadmiumWaterContent" /></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>总汞</td>
                        <td>≤0.25</td>
                        <td>≤0.30</td>
                        <td>≤0.35</td>
                        <td>≤0.30</td>
                        <td>≤0.40</td>
                        <td>≤0.40</td>
                        <td><Input :maxlength="6" v-model="data.mercuryDroughtRange" /></td>
                        <td><Input :maxlength="6" v-model="data.mercuryDroughtContent" /></td>
                        <td><Input :maxlength="6" v-model="data.mercuryWaterRange" /></td>
                        <td><Input :maxlength="6" v-model="data.mercuryWaterContent" /></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>总砷</td>
                        <td>≤25</td>
                        <td>≤20</td>
                        <td>≤20</td>
                        <td>≤20</td>
                        <td>≤20</td>
                        <td>≤15</td>
                        <td><Input :maxlength="6" v-model="data.arsenicDroughtRange" /></td>
                        <td><Input :maxlength="6" v-model="data.arsenicDroughtContent" /></td>
                        <td><Input :maxlength="6" v-model="data.arsenicWaterRange" /></td>
                        <td><Input :maxlength="6" v-model="data.arsenicWaterContent" /></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>总铅</td>
                        <td>≤50</td>
                        <td>≤50</td>
                        <td>≤50</td>
                        <td>≤50</td>
                        <td>≤50</td>
                        <td>≤50</td>
                        <td><Input :maxlength="6" v-model="data.leadDroughtRange" /></td>
                        <td><Input :maxlength="6" v-model="data.leadDroughtContent" /></td>
                        <td><Input :maxlength="6" v-model="data.leadWaterRange" /></td>
                        <td><Input :maxlength="6" v-model="data.leadWaterContent" /></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>总铬</td>
                        <td>≤120</td>
                        <td>≤120</td>
                        <td>≤120</td>
                        <td>≤120</td>
                        <td>≤120</td>
                        <td>≤120</td>
                        <td><Input :maxlength="6" v-model="data.chromeDroughtRange" /></td>
                        <td><Input :maxlength="6" v-model="data.chromeDroughtContent" /></td>
                        <td><Input :maxlength="6" v-model="data.chromeWaterRange" /></td>
                        <td><Input :maxlength="6" v-model="data.chromeWaterContent" /></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>总铜</td>
                        <td>≤50</td>
                        <td>≤60</td>
                        <td>≤60</td>
                        <td>≤60</td>
                        <td>≤60</td>
                        <td>≤60</td>
                        <td><Input :maxlength="6" v-model="data.copperDroughtRange" /></td>
                        <td><Input :maxlength="6" v-model="data.copperDroughtContent" /></td>
                        <td><Input :maxlength="6" v-model="data.copperWaterRange" /></td>
                        <td><Input :maxlength="6" v-model="data.copperWaterContent" /></td>
                        <td>mg/kg</td>
                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="12">注1： 果园土壤中铜限量值为旱田铜限量值的2倍。</td>
                    </tr>
                    <tr>
                        <td colspan="12">注2：水旱轮作的标准值取严不取宽。</td>
                    </tr>
                    <tr>
                        <td colspan="12">注3：底泥按照水田标准执行。</td>
                    </tr>
                </tfoot>
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
                cadmiumDroughtRange: '',
                cadmiumDroughtContent: '',
                cadmiumWaterRange: '',
                cadmiumWaterContent: '',
                mercuryDroughtRange: '',
                mercuryWaterRange: '', 
                mercuryWaterContent: '',
                arsenicDroughtRange: '',
                arsenicDroughtContent: '',
                arsenicWaterRange: '',
                arsenicWaterContent: '',
                leadDroughtRange: '',
                leadDroughtContent: '',
                leadWaterRange: '',
                leadWaterContent: '',
                chromeDroughtRange: '',
                chromeDroughtContent: '',
                chromeWaterRange: '',
                chromeWaterContent: '',
                copperDroughtRange: '',
                copperDroughtContent: '',
                copperWaterRange: '',
                copperWaterContent: '',
                mercuryDroughtContent: '',
                soilId: '1'
            }
        }
    },
    created () {
        this.$api.post('/member/product-soil-quality/query', {
            productId: this.$route.query.id
        }).then(res => {
            if (res.data !== undefined) {
                this.data = res.data
            }
        })
    },
    methods: {
        handleSave () {
            this.$api.post('/member/product-soil-quality/update', {
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