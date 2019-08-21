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
                        <td colspan="2"> <span>{{ data.colorSniffTaste }}</span></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>PH</td>
                        <td colspan="2">6.5～9.0</td>
                        <td colspan="2"> <span>{{ data.ph }}</span></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>溶解氧</td>
                        <td colspan="2">＞5</td>
                        <td colspan="2"> <span>{{ data.dissolvedOxygen }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>生化需氧量（BOD5）</td>
                        <td>≤5</td>
                        <td>≤3</td>
                        <td> <span>{{ data.biochemicalOxygenDemandFresh }}</span></td>
                        <td> <span>{{ data.biochemicalOxygenDemandSea }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总大肠菌群</td>
                        <td colspan="2">≤500（贝类50）</td>
                        <td colspan="2"> <span>{{ data.escherichia }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总汞</td>
                        <td>≤0.0005</td>
                        <td>≤0.0002</td>
                        <td> <span>{{ data.mercuryFresh }}</span></td>
                        <td> <span>{{ data.mercurySea }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总镉</td>
                        <td colspan="2">≤0.005</td>
                        <td colspan="2"> <span>{{ data.cadmium }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总铅</td>
                        <td>≤0.05</td>
                        <td>≤0.005</td>
                        <td> <span>{{ data.leadFresh }}</span></td>
                        <td> <span>{{ data.leadSea }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总铜</td>
                        <td colspan="2">≤0.01</td>
                        <td colspan="2"> <span>{{ data.copper }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总砷</td>
                        <td>≤0.05</td>
                        <td>≤0.03</td>
                        <td> <span>{{ data.arsenicFresh }}</span></td>
                        <td> <span>{{ data.arsenicSea }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>六价铬</td>
                        <td>≤0.05</td>
                        <td>≤0.005</td>
                        <td> <span>{{ data.hexavalentChromiumFresh }}</span></td>
                        <td> <span>{{ data.hexavalentChromiumSea }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>挥发酚</td>
                        <td colspan="2">≤0.005</td>
                        <td colspan="2"> <span>{{ data.volatilePhenol }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>石油类</td>
                        <td colspan="2">≤0.05</td>
                        <td colspan="2"> <span>{{ data.petroleum }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>活性磷酸盐（以P计）</td>
                        <td>—</td>
                        <td>≤0.03</td>
                        <td>—</td>
                        <td> <span>{{ data.labilePhosphateSea }}</span></td>
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
    </div>
</template>

<script>
export default {
    components:{
    },
    props: {
        id: {
            type: String,
            required: true
        }
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
            productId: this.id
        }).then(res => {
            if (res.data !== undefined) {
                this.data = res.data
            }
        })
    }
}
</script>