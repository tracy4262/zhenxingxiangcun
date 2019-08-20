<template>
  <div class="pb40 pt10">
     <div class="introductions-layouts bg-white pd30" style="min-height: 500px;">
       <!-- <introduction-list :data="item" v-for="(item, index) in list"></introduction-list> -->
       <!-- <p class="tc title-name pb50">乡村概况</p>
       <img src="../../img/com-banner3.jpg" height="300" width="100%" alt="" class="mb20">
       <div class="content pd20">
            <p>先锋村位于武汉市江夏区山坡街西南端，濒临湖北省第三大湖泊——斧头湖。与咸安、嘉鱼隔湖相望，座落在一面连陆、三面环水的两个半岛上。该村气候温和，四季分明，年平均气温约16.8℃，日照长，霜期短，雨量充沛，年均降雨量1350毫升左右。空气清新，植被覆盖率占全村面积的66 %，水域面积占30%，全年优良天气360天以上。这里田地湖泊交叉、生态环境优美，是典型的江南水乡地形地貌。</p>
            <p>先锋村原名三门徐乡，元代成宗年间，江西饶州秀才徐廷琏举家迁移在此定居，经过几十代繁衍，至今已有700多年历史。因村内大多数人都姓徐，故名三门徐。</p>
            <p>先锋村行政区划面积7.1平方公里，陆地面积4.99 平方公里，水域面积2.11 平方公里。户籍人口1633人，常住人口 280人，全村由9个自然湾组合而成。产业主要是农业种植、水产养殖等。</p>
            <p>大屋徐湾是徐廷琏在先锋村的定居地，俗称老屋、又名大屋，故名大屋徐。大屋徐湾内的历史民居较多，大多是清代所建。其中徐公兴老宅，是清朝从三品中议大夫徐文厚在清代道光年间回乡建造，至今已经有180年的历史，为江夏区现存仅有的古民居群。其中，五开间两层民居具有武汉市独特民居建筑风格。我国楚剧名家徐大树和其女——著名影视演员徐帆也是该湾人。全湾面积1.23平方公里，其中陆地面积0.89平方公里，水域面积0.34平方公里。</p>
            <p>“新屋徐”是徐廷琏长子徐本一的后人迁居在此耕种土地、新建房屋，故名新屋徐。全湾面积0.86平方公里，其中陆地面积0.68平方公里，水域面积0.18平方公里。</p>
            <p>上门徐湾是陆地进入先锋村的第一个村湾，是徐廷琏三子徐本三的家人在此耕种土地、定居形成的村湾，故名上门徐。全湾面积1.17平方公里，其中陆地面积0.96平方公里，水域面积0.21平方公里。</p>
            <p>中门徐湾位于上门徐和新屋徐中间，故名中门徐，是徐廷琏长子徐本一后人在此耕种土地、定居形成的村湾。全湾面积1.14平方公里，其中陆地面积0.96平方公里，水域面积0.18平方公里。</p>
            <p>合边徐湾是徐廷琏长子徐本一后人在此耕种土地、定居形成的村湾。全湾面积0.69平方公里，其中陆地面积0.48平方公里，水域面积0.22平方公里。</p>
            <p>坑家咀湾，地处斧头湖畔，是徐廷琏次子徐本二后人在此种地打渔、在此定居形成的村湾。此处有一渔船落脚的土坑，故名坑家咀。全湾面积0.54平方公里，其中陆地面积0.46平方公里，水域面积0.08平方公里。</p>
            <p>张公山湾最早有一张氏人家在此居住，后是徐廷琏长子徐本一后人在此耕种土地、定居形成的村湾，故名张公山。全湾面积0.17平方公里，其中陆地面积0.140平方公里，水域面积0.03平方公里  。</p>
            <p>邓家畈湾最早在此定居的居民均姓邓，而且该地田畈甚多，故名邓家畈。全湾面积1.02平方公里，其中陆地面积0.23平方公里，水域面积0.79平方公里。</p>
            <p>郭咀湾最早是一户郭氏人家在此定居，该湾旁边有一个山咀，故名郭咀。全湾面积0.27平方公里，其中陆地面积0.18平方公里，水域面积0.09平方公里。</p>
       </div>
       <div class="introduction-map pl60 pr60 pt20">
            <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${map.latitude},${map.longitude}&title=我的位置&content=${map.longitude,map.latitude}&output=html`">
                <img :src="`//api.map.baidu.com/staticimage/v2?ak=7syPirZ2AWxacMfHeAfuujdDgFmxCB5g&center=${map.longitude},${map.latitude}&zoom=15&markers=${map.longitude},${map.latitude}&width=800`" width="100%" height="400px"/>
            </a>
       </div> -->
       <member-introduction ref="introduction" gate></member-introduction>
     </div>
  </div>
</template>
<script>
import memberIntroduction from '../newMember/components/memberIntroduction'
import { navStatus, goToPath } from './mixins/commonMixins'
  export default {
    mixins: [navStatus, goToPath],
    components: {
      memberIntroduction
    },
    data () {
      return {
        loginAccount: '',
        list: [],
        // { lng: 114.269853, lat: 30.04322 }
        map:{
            latitude: 30.04322,
            longitude: 114.269853,
        }
      }
    },
    created() {
      this.loginAccount = this.$route.query.uid
        this.$nextTick(() => {
            this.$refs['introduction'].getList()
        })
    },
    methods: {
    }
  }
</script>
<style scoped lang="scss">
.introductions-layouts{
  width:1000px;
  margin:0 auto;
  margin-top:40px;
  color:#4a4a4a;
  /* box-shadow: 0px 2px 14px 0px rgba(0,0,0,0.10); */
  .title-name{
      color: #4A4A4A;
      font-size: 26px;
  }
  .content{
      font-size: 14px;
      color: #4A4A4A;
      line-height: 28px;
  }
}
</style>

