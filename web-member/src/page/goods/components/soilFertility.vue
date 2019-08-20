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
                        <td><span>{{ data.soilOrganicMatterFirstDrought }}</span></td>
                        <td><span>{{ data.soilOrganicMatterFirstWater }}</span></td>
                        <td><span>{{ data.soilOrganicMatterFirstDish }}</span></td>
                        <td><span>{{ data.soilOrganicMatterFirstGarden }}</span></td>
                        <td><span>{{ data.soilOrganicMatterFirstHusbandry }}</span></td>
                        <td>g/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>10～15</td>
                        <td>20～25</td>
                        <td>20～30</td>
                        <td>15～20</td>
                        <td>15～20</td>
                        <td><span>{{ data.soilOrganicMatterSecondDrought }}</span></td>
                        <td><span>{{ data.soilOrganicMatterSecondWater }}</span></td>
                        <td><span>{{ data.soilOrganicMatterSecondDish }}</span></td>
                        <td><span>{{ data.soilOrganicMatterSecondGarden }}</span></td>
                        <td><span>{{ data.soilOrganicMatterSecondHusbandry }}</span></td>
                        <td>g/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>＜10</td>
                        <td>＜20</td>
                        <td>＜20</td>
                        <td>＜15</td>
                        <td>＜15</td>
                        <td><span>{{ data.soilOrganicMatterThirdDrought }}</span></td>
                        <td><span>{{ data.soilOrganicMatterThirdWater }}</span></td>
                        <td><span>{{ data.soilOrganicMatterThirdDish }}</span></td>
                        <td><span>{{ data.soilOrganicMatterThirdGarden }}</span></td>
                        <td><span>{{ data.soilOrganicMatterThirdHusbandry }}</span></td>
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
                        <td><span>{{ data.nitrogenFirstDrought }}</span></td>
                        <td><span>{{ data.nitrogenFirstWater }}</span></td>
                        <td><span>{{ data.nitrogenFirstDish }}</span></td>
                        <td><span>{{ data.nitrogenFirstGarden }}</span></td>
                        <td><span>{{ data.nitrogenFirstHusbandry }}</span></td>
                        <td>g/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>0.8～1.0</td>
                        <td>1.0～1.2</td>
                        <td>1.0～1.2</td>
                        <td>0.8～1.0</td>
                        <td>—</td>
                        <td><span>{{ data.nitrogenSecondDrought }}</span></td>
                        <td><span>{{ data.nitrogenSecondWater }}</span></td>
                        <td><span>{{ data.nitrogenSecondDish }}</span></td>
                        <td><span>{{ data.nitrogenSecondGarden }}</span></td>
                        <td><span>{{ data.nitrogenSecondHusbandry }}</span></td>
                        <td>g/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>＜0.8</td>
                        <td>＜1.0</td>
                        <td>＜1.0</td>
                        <td>＜0.8</td>
                        <td>—</td>
                        <td><span>{{ data.nitrogenThirdDrought }}</span></td>
                        <td><span>{{ data.nitrogenThirdWater }}</span></td>
                        <td><span>{{ data.nitrogenThirdDish }}</span></td>
                        <td><span>{{ data.nitrogenThirdGarden }}</span></td>
                        <td><span>{{ data.nitrogenThirdHusbandry }}</span></td>
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
                        <td><span>{{ data.phosphorusFirstDrought }}</span></td>
                        <td><span>{{ data.phosphorusFirstWater }}</span></td>
                        <td><span>{{ data.phosphorusFirstDish }}</span></td>
                        <td><span>{{ data.phosphorusFirstGarden }}</span></td>
                        <td><span>{{ data.phosphorusFirstHusbandry }}</span></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>5～10</td>
                        <td>10～15</td>
                        <td>20～40</td>
                        <td>5～10</td>
                        <td>5～10</td>
                        <td><span>{{ data.phosphorusSecondDrought }}</span></td>
                        <td><span>{{ data.phosphorusSecondWater }}</span></td>
                        <td><span>{{ data.phosphorusSecondDish }}</span></td>
                        <td><span>{{ data.phosphorusSecondGarden }}</span></td>
                        <td><span>{{ data.phosphorusSecondHusbandry }}</span></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>＜5</td>
                        <td>＜10</td>
                        <td>＜20</td>
                        <td>＜5</td>
                        <td>＜5</td>
                        <td><span>{{ data.phosphorusThirdDrought }}</span></td>
                        <td><span>{{ data.phosphorusThirdWater }}</span></td>
                        <td><span>{{ data.phosphorusThirdDish }}</span></td>
                        <td><span>{{ data.phosphorusThirdGarden }}</span></td>
                        <td><span>{{ data.phosphorusThirdHusbandry }}</span></td>
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
                        <td><span>{{ data.potassiumFirstDrought }}</span></td>
                        <td><span>{{ data.potassiumFirstWater }}</span></td>
                        <td><span>{{ data.potassiumFirstDish }}</span></td>
                        <td><span>{{ data.potassiumFirstGarden }}</span></td>
                        <td><span>{{ data.potassiumFirstHusbandry }}</span></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>80～120</td>
                        <td>50～100</td>
                        <td>100～150</td>
                        <td>50～100</td>
                        <td>—</td>
                        <td><span>{{ data.potassiumSecondDrought }}</span></td>
                        <td><span>{{ data.potassiumSecondWater }}</span></td>
                        <td><span>{{ data.potassiumSecondDish }}</span></td>
                        <td><span>{{ data.potassiumSecondGarden }}</span></td>
                        <td><span>{{ data.potassiumSecondHusbandry }}</span></td>
                        <td>mg/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>＜80</td>
                        <td>＜50</td>
                        <td>＜100</td>
                        <td>＜50</td>
                        <td>—</td>
                        <td><span>{{ data.potassiumThirdDrought }}</span></td>
                        <td><span>{{ data.potassiumThirdWater }}</span></td>
                        <td><span>{{ data.potassiumThirdDish }}</span></td>
                        <td><span>{{ data.potassiumThirdGarden }}</span></td>
                        <td><span>{{ data.potassiumThirdHusbandry }}</span></td>
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
                        <td><span>{{ data.cationicFirstDrought }}</span></td>
                        <td><span>{{ data.cationicFirstWater }}</span></td>
                        <td><span>{{ data.cationicFirstDish }}</span></td>
                        <td><span>{{ data.cationicFirstGarden }}</span></td>
                        <td><span>{{ data.cationicFirstHusbandry }}</span></td>
                        <td>cmol(+)/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>15～20</td>
                        <td>15～20</td>
                        <td>15～20</td>
                        <td>15～20</td>
                        <td>—</td>
                        <td><span>{{ data.cationicSecondDrought }}</span></td>
                        <td><span>{{ data.cationicSecondWater }}</span></td>
                        <td><span>{{ data.cationicSecondDish }}</span></td>
                        <td><span>{{ data.cationicSecondGarden }}</span></td>
                        <td><span>{{ data.cationicSecondHusbandry }}</span></td>
                        <td>cmol(+)/kg</td>
                    </tr>
                    <tr>
                        <td>Ⅲ</td>
                        <td>＜15</td>
                        <td>＜15</td>
                        <td>＜15</td>
                        <td>＜15</td>
                        <td>—</td>
                        <td><span>{{ data.cationicThirdDrought }}</span></td>
                        <td><span>{{ data.cationicThirdWater }}</span></td>
                        <td><span>{{ data.cationicThirdDish }}</span></td>
                        <td><span>{{ data.cationicThirdGarden }}</span></td>
                        <td><span>{{ data.cationicThirdHusbandry }}</span></td>
                        <td>cmol(+)/kg</td>
                    </tr>
                </tbody>
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
        console.log('soilFerti')
        this.$api.post('/member/product-soil-fertility/query', {
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