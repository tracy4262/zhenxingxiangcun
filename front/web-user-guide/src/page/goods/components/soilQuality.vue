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
                        <td><span>{{ data.cadmiumDroughtRange }}</span></td>
                        <td><span>{{ data.cadmiumDroughtContent }}</span></td>
                        <td><span>{{ data.cadmiumWaterRange }}</span></td>
                        <td><span>{{ data.cadmiumWaterContent }}</span></td>
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
                        <td><span>{{ data.mercuryDroughtRange }}</span></td>
                        <td><span>{{ data.mercuryDroughtContent }}</span></td>
                        <td><span>{{ data.mercuryWaterRange }}</span></td>
                        <td><span>{{ data.mercuryWaterContent }}</span></td>
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
                        <td><span>{{ data.arsenicDroughtRange }}</span></td>
                        <td><span>{{ data.arsenicDroughtContent }}</span></td>
                        <td><span>{{ data.arsenicWaterRange }}</span></td>
                        <td><span>{{ data.arsenicWaterContent }}</span></td>
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
                        <td><span>{{ data.leadDroughtRange }}</span></td>
                        <td><span>{{ data.leadDroughtContent }}</span></td>
                        <td><span>{{ data.leadWaterRange }}</span></td>
                        <td><span>{{ data.leadWaterContent }}</span></td>
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
                        <td><span>{{ data.chromeDroughtRange }}</span></td>
                        <td><span>{{ data.chromeDroughtContent }}</span></td>
                        <td><span>{{ data.chromeWaterRange }}</span></td>
                        <td><span>{{ data.chromeWaterContent }}</span></td>
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
                        <td><span>{{ data.copperDroughtRange }}</span></td>
                        <td><span>{{ data.copperDroughtContent }}</span></td>
                        <td><span>{{ data.copperWaterRange }}</span></td>
                        <td><span>{{ data.copperWaterContent }}</span></td>
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
        console.log('soilQua')
        this.$api.post('/member/product-soil-quality/query', {
            productId: this.id
        }).then(res => {
            if (res.data !== undefined) {
                this.data = res.data
            }
        })
    }
}
</script>
<style lang="scss">
</style>