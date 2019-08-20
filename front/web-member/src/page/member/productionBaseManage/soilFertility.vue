<template>
    <div>
        <div class="ivu-table ivu-table-border ivu-table-small table">
            <table>
                <thead class="ivu-table-header tc">
                    <tr>
                        <th colspan="7">绿色食品 产地环境质量标准（NY/T 391-2013）</th>
                        <th colspan="5">基地土壤肥力</th>
                        <th rowspan="2" width="80">单位</th>
                    </tr>
                    <tr>
                        <th width="60">项目</th>
                        <th width="40">级别</th>
                        <th width="80">旱地</th>
                        <th width="80">水田</th>
                        <th width="80">菜地</th>
                        <th width="80">园地</th>
                        <th width="60">牧地</th>
                        <th width="60">旱地</th>
                        <th width="60">水田</th>
                        <th width="60">菜地</th>
                        <th width="60">园地</th>
                        <th width="60">牧地</th>
                    </tr>
                </thead>
                <tbody class="ivu-table-body">
                    <tr>
                        <td rowspan="3">有机质，g/kg</td>
                        <td>Ⅰ</td>
                        <td>＞15</td>
                        <td>＞25</td>
                        <td>＞30</td>
                        <td>＞20</td>
                        <td>＞20</td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterFirstDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterFirstWater" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterFirstDish" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterFirstGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterFirstHusbandry" /></td>
                        <td>g/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>10～15</td>
                        <td>20～25</td>
                        <td>20～30</td>
                        <td>15～20</td>
                        <td>15～20</td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterSecondDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterSecondWater" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterSecondDish" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterSecondGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterSecondHusbandry" /></td>
                        <td>g/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>＜10</td>
                        <td>＜20</td>
                        <td>＜20</td>
                        <td>＜15</td>
                        <td>＜15</td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterThirdDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterThirdWater" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterThirdDish" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterThirdGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.soilOrganicMatterThirdHusbandry" /></td>
                        <td>g/kg</td>
                    </tr>
                    <tr>
                        <td rowspan="3">全氮，g/kg</td>
                        <td>Ⅰ</td>
                        <td>＞1.0</td>
                        <td>＞1.2</td>
                        <td>＞1.2</td>
                        <td>＞1.0</td>
                        <td>—</td>
                        <td><Input :maxlength="6" v-model="data.nitrogenFirstDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenFirstWater" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenFirstDish" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenFirstGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenFirstHusbandry" /></td>
                        <td>g/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>0.8～1.0</td>
                        <td>1.0～1.2</td>
                        <td>1.0～1.2</td>
                        <td>0.8～1.0</td>
                        <td>—</td>
                        <td><Input :maxlength="6" v-model="data.nitrogenSecondDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenSecondWater" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenSecondDish" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenSecondGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenSecondHusbandry" /></td>
                        <td>g/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>＜0.8</td>
                        <td>＜1.0</td>
                        <td>＜1.0</td>
                        <td>＜0.8</td>
                        <td>—</td>
                        <td><Input :maxlength="6" v-model="data.nitrogenThirdDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenThirdWater" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenThirdDish" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenThirdGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.nitrogenThirdHusbandry" /></td>
                        <td>g/kg</td>
                    </tr>
                    <tr>
                        <td rowspan="3">有效磷，mg/kg</td>
                        <td>Ⅰ</td>
                        <td>＞10</td>
                        <td>＞15</td>
                        <td>＞40</td>
                        <td>＞10</td>
                        <td>＞10</td>
                        <td><Input :maxlength="6" v-model="data.phosphorusFirstDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusFirstWater" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusFirstDish" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusFirstGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusFirstHusbandry" /></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>5～10</td>
                        <td>10～15</td>
                        <td>20～40</td>
                        <td>5～10</td>
                        <td>5～10</td>
                        <td><Input :maxlength="6" v-model="data.phosphorusSecondDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusSecondWater" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusSecondDish" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusSecondGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusSecondHusbandry" /></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>＜5</td>
                        <td>＜10</td>
                        <td>＜20</td>
                        <td>＜5</td>
                        <td>＜5</td>
                        <td><Input :maxlength="6" v-model="data.phosphorusThirdDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusThirdWater" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusThirdDish" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusThirdGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.phosphorusThirdHusbandry" /></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td rowspan="3">速效钾，mg/kg</td>
                        <td>Ⅰ</td>
                        <td>＞120</td>
                        <td>＞100</td>
                        <td>＞150</td>
                        <td>＞100</td>
                        <td>—</td>
                        <td><Input :maxlength="6" v-model="data.potassiumFirstDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumFirstWater" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumFirstDish" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumFirstGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumFirstHusbandry" /></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>80～120</td>
                        <td>50～100</td>
                        <td>100～150</td>
                        <td>50～100</td>
                        <td>—</td>
                        <td><Input :maxlength="6" v-model="data.potassiumSecondDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumSecondWater" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumSecondDish" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumSecondGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumSecondHusbandry" /></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>＜80</td>
                        <td>＜50</td>
                        <td>＜100</td>
                        <td>＜50</td>
                        <td>—</td>
                        <td><Input :maxlength="6" v-model="data.potassiumThirdDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumThirdWater" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumThirdDish" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumThirdGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.potassiumThirdHusbandry" /></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td rowspan="3">阳离子交换量，cmol(+)/kg</td>
                        <td>Ⅰ</td>
                        <td>＞20</td>
                        <td>＞20</td>
                        <td>＞20</td>
                        <td>＞20</td>
                        <td>—</td>
                        <td><Input :maxlength="6" v-model="data.cationicFirstDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicFirstWater" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicFirstDish" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicFirstGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicFirstHusbandry" /></td>
                        <td>cmol(+)/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>15～20</td>
                        <td>15～20</td>
                        <td>15～20</td>
                        <td>15～20</td>
                        <td>—</td>
                        <td><Input :maxlength="6" v-model="data.cationicSecondDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicSecondWater" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicSecondDish" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicSecondGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicSecondHusbandry" /></td>
                        <td>cmol(+)/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>＜15</td>
                        <td>＜15</td>
                        <td>＜15</td>
                        <td>＜15</td>
                        <td>—</td>
                        <td><Input :maxlength="6" v-model="data.cationicThirdDrought" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicThirdWater" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicThirdDish" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicThirdGarden" /></td>
                        <td><Input :maxlength="6" v-model="data.cationicThirdHusbandry" /></td>
                        <td>cmol(+)/kg</td>
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
                soilOrganicMatterFirstDrought: '',
                soilOrganicMatterFirstWater: '',
                soilOrganicMatterFirstDish: '',
                soilOrganicMatterFirstGarden: '',
                soilOrganicMatterFirstHusbandry: '',
                soilOrganicMatterSecondDrought: '',
                soilOrganicMatterSecondWater: '',
                soilOrganicMatterSecondDish: '',
                soilOrganicMatterSecondGarden: '',
                soilOrganicMatterSecondHusbandry: '',
                soilOrganicMatterThirdDrought: '',
                soilOrganicMatterThirdWater: '',
                soilOrganicMatterThirdDish: '',
                soilOrganicMatterThirdGarden: '',
                soilOrganicMatterThirdHusbandry: '',
                nitrogenFirstDrought: '',
                nitrogenFirstWater: '',
                nitrogenFirstDish: '',
                nitrogenFirstGarden: '',
                nitrogenFirstHusbandry: '',
                nitrogenSecondDrought: '',
                nitrogenSecondWater: '',
                nitrogenSecondDish: '',
                nitrogenSecondGarden: '',
                nitrogenSecondHusbandry: '',
                nitrogenThirdDrought: '',
                nitrogenThirdWater: '',
                nitrogenThirdDish: '',
                nitrogenThirdGarden: '',
                nitrogenThirdHusbandry: '',
                phosphorusFirstDrought: '',
                phosphorusFirstWater: '',
                phosphorusFirstDish: '',
                phosphorusFirstGarden: '',
                phosphorusFirstHusbandry: '',
                phosphorusSecondDrought: '',
                phosphorusSecondWater: '',
                phosphorusSecondDish: '',
                phosphorusSecondGarden: '',
                phosphorusSecondHusbandry: '',
                phosphorusThirdDrought: '',
                phosphorusThirdWater: '',
                phosphorusThirdDish: '',
                phosphorusThirdGarden: '',
                phosphorusThirdHusbandry: '',
                potassiumFirstDrought: '',
                potassiumFirstWater: '',
                potassiumFirstDish: '',
                potassiumFirstGarden: '',
                potassiumFirstHusbandry: '',
                potassiumSecondDrought: '',
                potassiumSecondWater: '',
                potassiumSecondDish: '',
                potassiumSecondGarden: '',
                potassiumSecondHusbandry: '',
                potassiumThirdDrought: '',
                potassiumThirdWater: '',
                potassiumThirdDish: '',
                potassiumThirdGarden: '',
                potassiumThirdHusbandry: '',
                cationicFirstDrought: '',
                cationicFirstWater: '',
                cationicFirstDish: '',
                cationicFirstGarden: '',
                cationicFirstHusbandry: '',
                cationicSecondDrought: '',
                cationicSecondWater: '',
                cationicSecondDish: '',
                cationicSecondGarden: '',
                cationicSecondHusbandry: '',
                cationicThirdDrought: '',
                cationicThirdWater: '',
                cationicThirdDish: '',
                cationicThirdGarden: '',
                cationicThirdHusbandry: '',
            }
        }
    },
    created () {
        this.$api.post('/member/product-soil-fertility/query', {
            productId: this.$route.query.id
        }).then(res => {
            if (res.data !== undefined) {
                this.data = res.data
            }
        })
    },
    methods: {
        handleSave () {
            this.$api.post('/member/product-soil-fertility/save', {
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