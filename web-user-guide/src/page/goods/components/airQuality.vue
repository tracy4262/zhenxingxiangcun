<template>
    <div>
        <div class="ivu-table ivu-table-border ivu-table-small table">
            <table>
                <thead class="ivu-table-header tc">
                    <tr>
                        <th colspan="3">绿色食品 产地环境质量标准（NY/T 391-2013）</th>
                        <th colspan="2">基地生产环境</th>
                        <th rowspan="3" width="100">单位</th>
                    </tr>
                    <tr>
                        <th rowspan="2">项目</th>
                        <th colspan="2">指标</th>
                        <th colspan="2">指标</th>
                    </tr>
                    <tr>
                        <th width="120">日平均<sup>a</sup></th>
                        <th width="120">1小时<sup>b</sup></th>
                        <th width="120">日平均<sup>a</sup></th>
                        <th width="120">1小时<sup>b</sup></th>
                    </tr>
                </thead>
                <tbody class="ivu-table-body">
                    <tr>
                        <td>总悬浮颗粒物</td>
                        <td>≤0.30</td>
                        <td>—</td>
                        <td><span>{{ data.tspDay }}</span></td>
                        <td><span>{{ data.tspHour }}</span></td>
                        <td>mg/m3</td>
                    </tr>
                    <tr>
                        <td>二氧化硫</td>
                        <td>≤0.15</td>
                        <td>≤0.50</td>
                        <td><span>{{ data.sulfurDioxideDay }}</span></td>
                        <td><span>{{ data.sulfurDioxideHour }}</span></td>
                        <td>mg/m3</td>
                    </tr>
                    <tr>
                        <td>二氧化氮</td>
                        <td>≤0.08</td>
                        <td>≤0.20</td>
                        <td><span>{{ data.nitrogenDioxideDay }}</span></td>
                        <td><span>{{ data.nitrogenDioxideHour }}</span></td>
                        <td>mg/m3</td>
                    </tr>
                    <tr>
                        <td>氟化物</td>
                        <td>≤7</td>
                        <td>≤20</td>
                        <td><span>{{ data.fluorideDay }}</span></td>
                        <td><span>{{ data.fluorideHour }}</span></td>
                        <td>mg/m3</td>
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
                tspDay: '', 
                sulfurDioxideDay: '', 
                nitrogenDioxideDay: '', 
                fluorideDay: '', 
                tspHour: '', 
                sulfurDioxideHour: '', 
                nitrogenDioxideHour: '', 
                fluorideHour: '', 
                airQualitId: ''
            }
        }
    },
    created () {
        this.$api.post('/member/product-air-quality/query', {
            productId: this.id
        }).then(res => {
            if (res.data !== undefined) {
                this.data = res.data
            }
        })
    }
}
</script>
<style  lang="scss">

</style>
