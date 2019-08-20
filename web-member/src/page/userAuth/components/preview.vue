<template>
    <div >
        <Modal
        width="660"
        v-model="more"
        :mask-closable="false"
        :styles="{top: '20px'}"
        title="预览信息">
        <div class="pd20">
            <div id="privateInformation" v-if="tabsList.privateInformation">
                <Title title="隐私信息"></Title>
                <p class="pl15">{{ privateInformation }}</p>
            </div>
            <div id="networkInformation" v-if="tabsList.networkInformation" class="mt20">
                <Title title="网络信息"></Title>
                <p class="pl15">{{ networkInformation }}</p>
            </div>
            <div id="education" v-if="tabsList.education" class="mt20">
                <Title title="教育经历"></Title>
                <p class="pl15" v-html="education"></p>
            </div>
            <div id="work" v-if="tabsList.work" class="mt20">
                <Title title="工作经历"></Title>
                <p class="pl15" v-html="work"></p>
            </div>
            <div id="policy" v-if="tabsList.policy" class="mt20">
                <Title title="政治面貌"></Title>
                <p class="pl15">{{ policy }}</p>
            </div>
            <div id="religion" v-if="tabsList.religion" class="mt20">
                <Title title="宗教信仰"></Title>
                <p class="pl15">{{ religion }}</p>
            </div>
            <div id="professionalInformation" v-if="tabsList.professionalInformation" class="mt20">
                <Title title="专业信息"></Title>
                <p class="pl15">{{ professionalInformation }}</p>
            </div>
            <div id="corpHonor" v-if="tabsList.corpHonor" class="mt20">
                <Title title="荣誉风采"></Title>
                <p class="pl15" v-html="corpHonor"></p>
            </div>
            <div id="member" v-if="tabsList.member">
                <Title title="家庭成员"></Title>
                <p class="pl15" v-html="member"></p>
            </div>
            <div id="product" v-if="tabsList.product">
                <Title title="产出产品"></Title>
                <p class="pl15" v-html="product"></p>
            </div>
            <div id="provide" v-if="tabsList.provide">
                <Title title="提供服务"></Title>
                <p class="pl15" v-html="provide"></p>
            </div>
            <div id="purchase" v-if="tabsList.purchase">
                <Title title="求购信息"></Title>
                <p class="pl15" v-html="purchase"></p>
            </div>
            <div id="asset" v-if="tabsList.asset">
                <Title title="资产信息"></Title>
                <Tag style="margin-left: 15px; margin-top: 10px; margin-bottom: 10px;" checked color="default">房产信息</Tag>
                <p class="pl15" v-html="house"></p>
                <p class="pl15" v-html="placeOfBusiness"></p>
                <Tag style="margin-left: 15px; margin-top: 10px; margin-bottom: 10px;" checked color="default">主要设备</Tag>
                <p class="pl15">{{ modern }}</p>
                <Tag style="margin-left: 15px; margin-top: 10px; margin-bottom: 10px;" checked color="default">生产设施</Tag>
                <p class="pl15" v-html="facilityAssets"></p>
                <Tag style="margin-left: 15px; margin-top: 10px; margin-bottom: 10px;" checked color="default">无形资产</Tag>
                <p class="pl15" v-html="intangibleAssets"></p>
            </div>
            <div id="department" v-if="tabsList.department" class="mt20">
                <Title title="组织机构"></Title>
                <!-- <p class="pl15" v-html="dept"></p> -->
                <Tree class="pl15" :render="renderContent" :data="dept"></Tree>
            </div>
            <div id="team" v-if="tabsList.team" class="mt20">
                <Title title="管理团队"></Title>
                <p class="pl15" v-html="team"></p>
            </div>
            <div id="survey" v-if="tabsList.survey" class="mt20">
                <Title title="经营概况"></Title>
                <p class="pl15">{{ survey }}</p>
            </div>
            <div id="assetFinance" v-if="tabsList.assetFinance" class="mt20">
                <Title title="财务信息"></Title>
                <p class="pl15">{{ assetFinance }}</p>
            </div>
            <div id="productService" v-if="tabsList.productService" class="mt20">
                <Title title="产品&服务"></Title>
                <p class="pl15" v-html="productService"></p>
            </div>
            <div id="qualification" v-if="tabsList.qualification" class="mt20">
                <Title title="专业资质"></Title>
                <p class="pl15" v-html="qualification"></p>
            </div>
            <div id="commonAssets" v-if="tabsList.commonAssets" class="mt20">
                <Title title="普通资产"></Title>
                <p class="pl15" v-html="commonAssets"></p>
            </div>
            <div id="overViewInfo" v-if="tabsList.overViewInfo" class="mt20">
                <Title title="概览信息"></Title>
                <p class="pl15" v-html="overViewInfo"></p>
            </div>
        </div>
        <div slot="footer" class="tc">
            <Button type="default" @click="more = false">取消</Button>
        </div>
    </Modal>
    </div>
</template>

<script>
import Title from './title'
export default {
    components: {
        Title
    },
    props:{
        tabsList: {
            type: Object
        }
    },
    data () {
        return{
            more: false,
            privateInformation: '',
            networkInformation: '',
            education: '',
            work: '',
            policy: '',
            religion: '',
            professionalInformation: '',
            corpHonor: '',
            member: '',
            product: '',
            provide: '',
            purchase: '',
            house: '',
            modern: '',
            dept: [],
            team: '',
            survey: '',
            assetFinance: '',
            productService: '',
            qualification: '',
            commonAssets: '',
            intangibleAssets: '',
            facilityAssets: '',
            placeOfBusiness: '',
            overViewInfo: ''
        }
    },
    created () {
    },
    methods:{
        handleCancel(){

        },
        handleOk(){

        },
        renderContent (h, { root, node, data }) {
            return h('span', {
                style: {
                    display: 'inline-block',
                    width: '100%'
                }
            }, [
                h('span', [
                    h('Icon', {
                        props: {
                            type: 'ios-paper-outline'
                        }
                    }),
                    h('span', {
                        style: {
                            marginLeft: '8px',
                            cursor: 'pointer'
                        },
                        on: {
                            click: () => { /* this.show(node) */ }
                        }
                    }, data.title)
                ])
            ]);
        }
    }
}
</script>

