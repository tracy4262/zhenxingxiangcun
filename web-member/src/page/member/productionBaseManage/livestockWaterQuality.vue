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
                        <td><Input :maxlength="6" v-model="data.chromaticity" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>浑浊度<sup>a</sup>（散射浑浊度单位）</td>
                        <td>≤3</td>
                        <td><Input :maxlength="6" v-model="data.turbidity" /></td>
                        <td>NTU</td>
                    </tr>
                    <tr>
                        <td>臭和味</td>
                        <td>不应有异臭、异味</td>
                        <td><Input :maxlength="6" v-model="data.stinkTaste" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>肉眼可见物<sup>a</sup></td>
                        <td>不应含有</td>
                        <td><Input :maxlength="6" v-model="data.visible" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>PH</td>
                        <td>6.5～8.5</td>
                        <td><Input :maxlength="6" v-model="data.ph" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>氟化物</td>
                        <td>≤1.0</td>
                        <td><Input :maxlength="6" v-model="data.fluoride" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>氰化物</td>
                        <td>≤0.05</td>
                        <td><Input :maxlength="6" v-model="data.cyanide" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总砷</td>
                        <td>≤0.05</td>
                        <td><Input :maxlength="6" v-model="data.arsenic" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总汞</td>
                        <td>≤0.001</td>
                        <td><Input :maxlength="6" v-model="data.mercury" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总镉</td>
                        <td>≤0.01</td>
                        <td><Input :maxlength="6" v-model="data.cadmium" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>六价铬</td>
                        <td>≤0.05</td>
                        <td><Input :maxlength="6" v-model="data.hexavalentChromium" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>总铅</td>
                        <td>≤0.05</td>
                        <td><Input :maxlength="6" v-model="data.lead" /></td>
                        <td>mg/L</td>
                    </tr>
                    <tr>
                        <td>菌落总数</td>
                        <td>≤100</td>
                        <td><Input :maxlength="6" v-model="data.coloniesNumber" /></td>
                        <td>CFU/mL</td>
                    </tr>
                    <tr>
                        <td>总大肠菌群</td>
                        <td>不得检出</td>
                        <td><Input :maxlength="6" v-model="data.coliform" /></td>
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
            productId: this.$route.query.id
        }).then(res => {
            if (res.data !== undefined) {
                this.data = res.data
            }
        })
    },
    methods: {
        handleSave () {
            this.$api.post('/member/product-livestock-water-quality/update', {
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