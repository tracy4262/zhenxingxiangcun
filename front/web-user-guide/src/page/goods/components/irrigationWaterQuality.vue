<template>
    <div>
        <div class="ivu-table ivu-table-border ivu-table-small table">
            <table>
                <thead class="ivu-table-header tc">
                    <tr>
                        <th colspan="2">绿色食品 产地环境质量标准（NY/T 391-2013）</th>
                        <th>基地灌溉水质</th>
                        <th rowspan="2" width="100">单位</th>
                    </tr>
                    <tr>
                        <th>项目</th>
                        <th>指标</th>
                        <th>指标</th>
                    </tr>
                </thead>
                <tbody class="ivu-table-body">
                    <tr>
                        <td>PH</td>
                        <td>5.5～8.5</td>
                        <td><span>{{ data.ph }}</span></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>总汞</td>
                        <td>≤0.001</td>
                        <td><span>{{ data.mercury }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总镉</td>
                        <td>≤0.005</td>
                        <td><span>{{ data.cadmium }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总砷</td>
                        <td>≤0.05</td>
                        <td><span>{{ data.arsenic }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总铅</td>
                        <td>≤0.1</td>
                        <td><span>{{ data.lead }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>六价铬</td>
                        <td>≤0.1</td>
                        <td><span>{{ data.hexavalentChromium }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>氟化物</td>
                        <td>≤2.0</td>
                        <td><span>{{ data.fluoride }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>化学需氧量（CODcr）</td>
                        <td>≤60</td>
                        <td><span>{{ data.chemicalOxygenDemand }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>石油类</td>
                        <td>≤1.0</td>
                        <td><span>{{ data.petroleum }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>粪大肠菌群</td>
                        <td>≤10000</td>
                        <td><span>{{ data.fecalColiform }}</span></td>
                        <td>个/L</td>
                    </tr>
                </tbody>
                <tfoot class="ivu-table-foot">
                    <tr>
                        <td colspan="100%">
                            灌溉蔬菜、瓜类和草本水果的地表水需测粪大肠菌群，其他情况不测粪大肠菌群。
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
                ph: '', 
                mercury: '', 
                cadmium: '', 
                arsenic: '', 
                lead: '', 
                hexavalentChromium: '', 
                fluoride: '', 
                chemicalOxygenDemand: '', 
                petroleum: '', 
                fecalColiform: '', 
                irrigationId: ''
            }
        }
    },
    created () {
        this.$api.post('/member/product-irrigation-water-quality/query', {
            productId: this.id
        }).then(res => {
            if (res.data !== undefined) {
                this.data = res.data
            }
        })
    }
}
</script>