<template>
    <div>
        <div class="ivu-table ivu-table-border ivu-table-small table">
            <table>
                <thead class="ivu-table-header tc">
                    <tr>
                        <th colspan="2">绿色食品 产地环境质量标准（NY/T 391-2013）</th>
                        <th>基地畜禽养殖用水</th>
                        <th rowspan="2">单位</th>
                    </tr>
                    <tr>
                        <th>项目</th>
                        <th>指标</th>
                        <th>指标</th>
                    </tr>
                </thead>
                <tbody class="ivu-table-body">
                    <tr>
                        <td>色度</td>
                        <td>≤15，并不应呈现其他异色</td>
                        <td> <span>{{ data.chromaticity }}</span></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>浑浊度<sup>a</sup>（散射浑浊度单位）</td>
                        <td>≤3</td>
                        <td> <span>{{ data.turbidity }}</span></td>
                        <td>NTU</td>
                    </tr>
                    <tr>
                        <td>臭和味</td>
                        <td>不应有异臭、异味</td>
                        <td> <span>{{ data.stinkTaste }}</span></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>肉眼可见物<sup>a</sup></td>
                        <td>不应含有</td>
                        <td> <span>{{ data.visible }}</span></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>PH</td>
                        <td>6.5～8.5</td>
                        <td> <span>{{ data.ph }}</span></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>氟化物</td>
                        <td>≤1.0</td>
                        <td> <span>{{ data.fluoride }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>氰化物</td>
                        <td>≤0.05</td>
                        <td> <span>{{ data.cyanide }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总砷</td>
                        <td>≤0.05</td>
                        <td> <span>{{ data.arsenic }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总汞</td>
                        <td>≤0.001</td>
                        <td> <span>{{ data.mercury }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总镉</td>
                        <td>≤0.01</td>
                        <td> <span>{{ data.cadmium }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>六价铬</td>
                        <td>≤0.05</td>
                        <td> <span>{{ data.hexavalentChromium }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总铅</td>
                        <td>≤0.05</td>
                        <td> <span>{{ data.lead }}</span></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>菌落总数</td>
                        <td>≤100</td>
                        <td> <span>{{ data.coloniesNumber }}</span></td>
                        <td>CFU/mL</td>
                    </tr>
                    <tr>
                        <td>总大肠菌群</td>
                        <td>不得检出</td>
                        <td> <span>{{ data.coliform }}</span></td>
                        <td>MPN/100mL</td>
                    </tr>
                </tbody>
                <tfoot class="ivu-table-foot">
                    <tr>
                        <td colspan="100%">
                            散养模式免测该指标。
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
                chromaticity: '', 
                turbidity: '', 
                stinkTaste: '', 
                visible: '', 
                ph: '', 
                fluoride: '', 
                cyanide: '', 
                arsenic: '', 
                mercury: '', 
                cadmium: '', 
                hexavalentChromium: '', 
                lead: '', 
                coloniesNumber: '', 
                coliform: '', 
                livestockId: ''
            }
        }
    },
    created () {
        this.$api.post('/member/product-livestock-water-quality/query', {
            productId: this.id
        }).then(res => {
            if (res.data !== undefined) {
                this.data = res.data
            }
        })
    }
}
</script>