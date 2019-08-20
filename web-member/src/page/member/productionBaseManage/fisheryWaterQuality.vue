<template>
    <div>
        <div class="ivu-table ivu-table-border ivu-table-small table">
            <table>
                <thead class="ivu-table-header tc">
                    <tr>
                        <th colspan="3">绿色食品 产地环境质量标准（NY/T 391-2013）</th>
                        <th colspan="2">基地渔业水质</th>
                        <th rowspan="3">单位</th>
                    </tr>
                    <tr>
                        <th rowspan="2">项目</th>
                        <th colspan="2">指标</th>
                        <th colspan="2">指标</th>
                    </tr>
                    <tr>
                        <th width="150">淡水</th>
                        <th width="150">海水</th>
                        <th width="150">淡水</th>
                        <th width="150">海水</th>
                    </tr>
                </thead>
                <tbody class="ivu-table-body">
                    <tr>
                        <td>色、臭、味</td>
                        <td colspan="2">不应有异色、异臭、异味</td>
                        <td colspan="2"><Input :maxlength="6" v-model="data.colorSniffTaste" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>PH</td>
                        <td colspan="2">6.5～9.0</td>
                        <td colspan="2"><Input :maxlength="6" v-model="data.ph" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>溶解氧</td>
                        <td colspan="2">＞5</td>
                        <td colspan="2"><Input :maxlength="6" v-model="data.dissolvedOxygen" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>生化需氧量（BOD5）</td>
                        <td>≤5</td>
                        <td>≤3</td>
                        <td><Input :maxlength="6" v-model="data.biochemicalOxygenDemandFresh" /></td>
                        <td><Input :maxlength="6" v-model="data.biochemicalOxygenDemandSea" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总大肠菌群</td>
                        <td colspan="2">≤500（贝类50）</td>
                        <td colspan="2"><Input :maxlength="6" v-model="data.escherichia" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总汞</td>
                        <td>≤0.0005</td>
                        <td>≤0.0002</td>
                        <td><Input :maxlength="6" v-model="data.mercuryFresh" /></td>
                        <td><Input :maxlength="6" v-model="data.mercurySea" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总镉</td>
                        <td colspan="2">≤0.005</td>
                        <td colspan="2"><Input :maxlength="6" v-model="data.cadmium" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总铅</td>
                        <td>≤0.05</td>
                        <td>≤0.005</td>
                        <td><Input :maxlength="6" v-model="data.leadFresh" /></td>
                        <td><Input :maxlength="6" v-model="data.leadSea" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总铜</td>
                        <td colspan="2">≤0.01</td>
                        <td colspan="2"><Input :maxlength="6" v-model="data.copper" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总砷</td>
                        <td>≤0.05</td>
                        <td>≤0.03</td>
                        <td><Input :maxlength="6" v-model="data.arsenicFresh" /></td>
                        <td><Input :maxlength="6" v-model="data.arsenicSea" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>六价铬</td>
                        <td>≤0.05</td>
                        <td>≤0.005</td>
                        <td><Input :maxlength="6" v-model="data.hexavalentChromiumFresh" /></td>
                        <td><Input :maxlength="6" v-model="data.hexavalentChromiumSea" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>挥发酚</td>
                        <td colspan="2">≤0.005</td>
                        <td colspan="2"><Input :maxlength="6" v-model="data.volatilePhenol" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>石油类</td>
                        <td colspan="2">≤0.05</td>
                        <td colspan="2"><Input :maxlength="6" v-model="data.petroleum" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>活性磷酸盐（以P计）</td>
                        <td>—</td>
                        <td>≤0.03</td>
                        <td>—</td>
                        <td><Input :maxlength="6" v-model="data.labilePhosphateSea" /></td>
                        <td>mg/L</td>
                    </tr>
                </tbody>
                <tfoot class="ivu-table-foot">
                    <tr>
                        <td colspan="100%">
                            水中漂浮物质需要满足水面不应出现油膜或浮沫要求。
                        </td>
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
                colorSniffTaste: '', 
                ph: '', 
                dissolvedOxygen: '', 
                biochemicalOxygenDemandFresh: '', 
                biochemicalOxygenDemandSea: '', 
                escherichia: '', 
                mercuryFresh: '', 
                mercurySea: '', 
                cadmium: '', 
                leadFresh: '', 
                leadSea: '', 
                copper: '', 
                arsenicFresh: '', 
                arsenicSea: '', 
                hexavalentChromiumFresh: '', 
                hexavalentChromiumSea: '', 
                volatilePhenol: '', 
                petroleum: '', 
                labilePhosphateSea: '', 
                fisheriesId: ''
            }
        }
    },
    created () {
        this.$api.post('/member/product-fisheries-water-quality/query', {
            productId: this.$route.query.id
        }).then(res => {
            if (res.data !== undefined) {
                this.data = res.data
            }
        })
    },
    methods: {
        handleSave () {
            this.$api.post('/member/product-fisheries-water-quality/update', {
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