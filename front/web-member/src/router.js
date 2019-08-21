// 导入Vue和VueRouter
import Vue from 'vue'
import VueRouter from 'vue-router'

import wyNav from './page/51nav/index'
// 掌上无忧
import wyPocket from './page/51pocket/index'

// 51首页
import wyWrapper from './page/51index/wrapper'
import wyIndex from './page/51index/index'
import inforMationList from './page/51index/inforMationList'
import policyList from './page/51index/policyList'
import knowledgeList from './page/51index/knowledgeList'
import serviceList from './page/51index/serviceList.vue'
import indexServiceAll from './page/51index/serviceAll'
import indexServiceFishing from './page/51index/serviceFishing'
import indexServicePicking from './page/51index/servicePicking'
import indexServiceStay from './page/51index/serviceStay'
import indexServiceRestaurant from './page/51index/serviceRestaurant'
import indexServiceScenicSpot from './page/51index/serviceScenicSpot'
import indexServiceConsultation from './page/51index/serviceConsultation'
// import enterpriseList from './page/51index/enterpriseList'
// import departmentList from './page/51index/departmentList'
// import expertList from './page/51index/expertList'
import getMember from './page/51index/getMember-new'

import standardList from './page/51index/standardList'

// 会员中心
// import memberIndex from './page/member/member.vue'
import memberIndex from './page/newMember/index.vue'
import myFollow from './page/newMember/myFollow/index.vue'
import appCenterIndex from './page/appCenter/index.vue'
import cardManage from './page/member/cardManage.vue'

import inforMation from './page/InforMation/InforMation.vue'
import findInforMationDetail from './page/InforMation/InforMationDetail.vue'
import policy from './page/InforMation/policy.vue'
import policyDetail from './page/InforMation/policyDetail.vue'
import knowledge from './page/InforMation/knowledge.vue'
import knowledgeDetail from './page/InforMation/knowledgeDetail.vue'
import expertDetail from './page/InforMation/expertDetail.vue'
import standardDetail from './page/InforMation/standardDetail.vue'
import InforMationserviceDetail from './page/InforMation/serviceDetail.vue'

import enterpriseDetail from './page/InforMation/enterpriseDetail.vue'

//图书简介
import bookBlurb from './page/InforMation/bookBlurb.vue'
import bookDetail from './page/InforMation/bookDetail.vue'

// 新版咨询服务
import consultationService from './page/service/consultationService/index.vue'
import addConsultationService from './page/service/consultationService/addServiceIndex.vue'
import addConsultationServiceStep1 from './page/service/consultationService/components/step1.vue'
import addConsultationServiceStep2 from './page/service/consultationService/components/step2.vue'
import addConsultationServiceStep3 from './page/service/consultationService/components/step3.vue'
import addConsultationServiceStep4 from './page/service/consultationService/components/step4.vue'
import addConsultationServiceStep5 from './page/service/consultationService/components/step5.vue'
import consultationServiceDetail from './page/InforMation/consultationServiceDetail.vue'

// 新版代理
import index from './page/newApplication/proxy/index.vue'
import proxy from './page/newApplication/proxy/components/proxy.vue'
import applyProxy from './page/newApplication/proxy/components/applyProxy'
import pendingApproval from './page/newApplication/proxy/components/pendingApproval'

//圈子
// import circle from './page/circle/indexCircle.vue'
// import circleDetail from './page/circle/circleDetail.vue'

//机关详情
import departmentDetail from './page/InforMation/departmentDetail.vue'


import mapNav from './page/map/index'

// 无忧首页
// import mallIndex from './page/mall/index'
// import ypAuction from './page/mall/ypAuction'
// import hotGroupBuy from './page/mall/hotGroupBuy'
// import fixPriceProduct from './page/mall/fixPriceProduct'
// import stock from './page/mall/stock'
// import newPresell from './page/mall/newPresell'
// import ascend from './page/mall/ascend'
// import hotGroupBuyDetail from './page/mall/hotGroupBuyDetail'
// import fixPriceProductDetail from './page/mall/fixPriceProductDetail'
// import ypAuctionDetail from './page/mall/ypAuctionDetail'
// import newPresellDetail from './page/mall/newPresellDetail'
// import stockDetail from './page/mall/stockDetail'
// import ascendDetail from './page/mall/ascendDetail'

// 会员中心
import personCodeManage from './page/member/personCodeManage'
import companyCodeManage from './page/member/companyCodeManage'

// 我的推荐
import myRecommendation from './page/newApplication/myRecommendation/index.vue'

// 新版聘请
// import employManage from './page/newApplication/employ/index.vue'

import publicNumManage from './page/member/publicNumManage'
import fundAccountManage from './page/member/fundAccountManage'

import followTop from './page/member/followTop.vue'
import follow01 from './page/member/followThree01.vue'
import follow02 from './page/member/followThree02.vue'
import follow03 from './page/member/followThree03.vue'
import follow04 from './page/member/followThree04.vue'
import follow05 from './page/member/followThree05.vue'
import follow06 from './page/member/followThree06.vue'
import follow from './page/member/follow.vue'
import addSpec from './page/member/addSpec.vue'
import authenGuide from './page/member/authenGuide.vue'

// 新版收藏管理
import collect from './page/newApplication/collect/index.vue'

// 名称库
import nameLibrary from './page/nameLibrary/nameLibrary.vue'
import addSpecies from './page/nameLibrary/addSpecies.vue'
import addVariety from './page/nameLibrary/addVariety.vue'
import addCommonProduct from './page/nameLibrary/addCommonProduct.vue'
import addDisease from './page/nameLibrary/addDisease.vue'
import addPest from './page/nameLibrary/addPest.vue'

// 名称库
import nameLibraryIndex from './page/newApplication/nameLibrary/index.vue'
import nameLibrarySpecies from './page/newApplication/nameLibrary/species.vue'
import nameLibraryaddSpecies from './page/newApplication/nameLibrary/components/addSpecies.vue'
import nameLibraryVariety from './page/newApplication/nameLibrary/variety.vue'
import nameLibraryaddVariety from './page/newApplication/nameLibrary/components/addVariety.vue'
import nameLibraryDisease from './page/newApplication/nameLibrary/disease.vue'
import nameLibraryaddDisease from './page/newApplication/nameLibrary/components/addDisease.vue'
import nameLibraryPest from './page/newApplication/nameLibrary/pest.vue'
import nameLibraryaddPest from './page/newApplication/nameLibrary/components/addPest.vue'
import nameLibraryCommodity from './page/newApplication/nameLibrary/commodity.vue'
import nameLibraryaddCommodity from './page/newApplication/nameLibrary/components/addCommodity.vue'
import nameLibraryService from './page/newApplication/nameLibrary/service.vue'
import nameLibraryaddService from './page/newApplication/nameLibrary/components/addService.vue'
import nameLibraryUnit from './page/newApplication/nameLibrary/unit.vue'
import nameLibraryaddUnit from './page/newApplication/nameLibrary/components/addUnit.vue'


// 关注管理
import newFollow from './page/follow/follow'
// 关注管理 单应用模块
import focusManagement from './page/focusManagement/index'
import focusManagementSpecies from './page/focusManagement/species'
import focusManagementProduct from './page/focusManagement/product'
import focusManagementService from './page/focusManagement/service'
import focusManagementMember from './page/focusManagement/member'
import focusManagementPublic from './page/focusManagement/public'
import focusManagementNews from './page/focusManagement/news'
import focusManagementKnowledge from './page/focusManagement/knowledge'
import focusManagementPolicy from './page/focusManagement/policy'
// 关系圈 新
import relationIndex from './page/newApplication/relationManage/index.vue'


// 新门户
import portalNav from './page/newGate/components/nav'
import portalIndex from './page/newGate/index'
import portalIntroduction from './page/newGate/introduction'
import portalDynamic from './page/newGate/dynamic'
import portalPolicy from './page/newGate/policy'
import portalKnowledge from './page/newGate/knowledge'
import portalContcat from './page/newGate/contcat'
import portalStandard from './page/newGate/standard'
import portalService from './page/newGate/service'
import portalProduct from './page/newGate/product'
import portalProductionBase from './page/newGate/productionBase'

// 新门户 乡村门户
import ruralPortalNav from './page/newGate/ruralGate/components/nav'
import ruralPortalIndex from './page/newGate/ruralGate/index'

// 新门户 乡村合作社
import cooperativePortalNav from './page/newGate/cooperativeGate/components/nav'
import cooperativePortalIndex from './page/newGate/cooperativeGate/index'

// 新门户 农头 企业
import farmHeadPortalNav from './page/newGate/farmHeadGate/components/nav'
import farmHeadPortalIndex from './page/newGate/farmHeadGate/index'

// 新门户 专家门户
import expertPortalNav from './page/newGate/expertGate/components/nav'
import expertPortalIndex from './page/newGate/expertGate/index'
import expertPortalService from './page/newGate/expertGate/components/service'
import expertPortalProduct from './page/newGate/expertGate/components/product'
import expertPortalContcat from './page/newGate/expertGate/components/contcat'
import expertPortalProductionBase from './page/newGate/expertGate/components/productionBase'


//种养户
import addPlanter from './page/member/addPlanter'

// 公用导航开始结束

// import top from './top.vue'
// import upload from './components/upload'

//支付页面
import payment from './components/payment'

//付费阅读
import payReading from './components/payReading'

// 文件管理
import fileManageIndex from './page/newApplication/fileManage/index'

// 生产基地管理
import productionBaseGuide from './page/member/productionBaseManage/productionBaseGuide'
import addProductionBase from './page/member/productionBaseManage/addProductionBase'
import addProductionBaseStep1 from './page/member/productionBaseManage/addProductionBaseStep1'
import addProductionBaseStep2 from './page/member/productionBaseManage/addProductionBaseStep2'
import addProductionBaseStep3 from './page/member/productionBaseManage/addProductionBaseStep3'
import addProductionBaseStep4 from './page/member/productionBaseManage/addProductionBaseStep4'

// 新版生产基地管理
import productionBaseList from './page/newApplication/productionBase/ListIndex.vue'
import editProductionBase from './page/newApplication/productionBase/editIndex.vue'
import productionBaseDetail from './page/newApplication/productionBase/detailIndex'

// 认证
import auth from '~auth/index'
import authStep1 from '~auth/step1/index'
import authStep3 from '~auth/step3/index'
import authStep2 from '~auth/step2/index'
import authStep6 from '~auth/step6/index'
import authStep4 from '~auth/step4/index'
import authStep5 from '~auth/step5/index'
import authStep7 from '~auth/step7/index'

//个人资料
import personalIndex from '~page/newPersonalDatum/index'
import personalDetail from './page/newPersonalDatum/components/detail'
import personalModify from './page/newPersonalDatum/components/modify'

//垂钓
import goFishingIndex from './page/goFishing/index'
import fishingSpecies from './page/goFishing/speciesList'
import fishingService from './page/goFishing/components/service'
import fishingAddService from './page/goFishing/components/addService'
import addServiceSetp1 from './page/goFishing/components/serviceStep1'
import addServiceSetp2 from './page/goFishing/components/serviceStep2'
import addServiceSetp3 from './page/goFishing/components/serviceStep3'
import addServiceSetp4 from './page/goFishing/components/serviceStep4'
import addServiceSetp5 from './page/goFishing/components/serviceStep5'
// 垂钓产品列表
import fishingProductList from './page/goFishing/productList'

// 采摘
import goPickingIndex from './page/goPicking/index'
import pickingProductList from './page/goPicking/productList'
import pickingSpecies from './page/goPicking/speciesList'
import pickingService from './page/goPicking/components/service'
import pickingAddService from './page/goPicking/components/addService'
import pickingAddServiceSetp1 from './page/goPicking/components/serviceStep1'
import pickingAddServiceSetp2 from './page/goPicking/components/serviceStep2'
import pickingAddServiceSetp3 from './page/goPicking/components/serviceStep3'
import pickingAddServiceSetp4 from './page/goPicking/components/serviceStep4'
import pickingAddServiceSetp5 from './page/goPicking/components/serviceStep5'

// 景区
import scenicSpotIndex from './page/scenicSpot/index'
import scenicSpotTicket from './page/scenicSpot/ticket'
import scenicSpotService from './page/scenicSpot/service'
import scenicSpotOrder from './page/scenicSpot/order'
import scenicSpotAddService from './page/scenicSpot/components/addService'
import scenicSpotstep1 from './page/scenicSpot/components/step1'
import scenicSpotstep2 from './page/scenicSpot/components/step2'
import scenicSpotstep3 from './page/scenicSpot/components/step3'
import scenicSpotstep4 from './page/scenicSpot/components/step4'
import scenicSpotstep5 from './page/scenicSpot/components/step5'
import scenicAddSetMeal from './page/scenicSpot/components/add-set-meal/index'
// 住宿
import stayIndex from './page/stay/index'
import stayRoomType from './page/stay/roomType'
import stayRoomList from './page/stay/roomList'
import stayService from './page/stay/service'
import stayOrder from './page/stay/order'
import stayAddService from './page/stay/components/addService'
import staystep1 from './page/stay/components/step1'
import staystep2 from './page/stay/components/step2'
import staystep3 from './page/stay/components/step3'
import staystep4 from './page/stay/components/step4'
import staystep5 from './page/stay/components/step5'
import stayAddSetMeal from './page/stay/components/add-set-meal/index'

// 种植业生产管理
import productionControlIndex from './page/productionControl/index'
import yearList from './page/productionControl/yearList'
import plantList from './page/productionControl/plantList'
import productionPlans from './page/productionControl/productionPlans'
import outputGuess from './page/productionControl/outputGuess'
import productionRecords from './page/productionControl/productionRecords'

// 库存管理
import inventoryControl from './page/inventoryControl/index'
import config from './page/inventoryControl/config'
import storeInfoSee from './page/inventoryControl/storeInfoSee'


// 餐饮管理
import restaurantIndex from './page/restaurantManagement/index'
import restaurantMenuType from './page/restaurantManagement/menuType'
import restaurantDish from './page/restaurantManagement/dish'
import restaurantOrder from './page/restaurantManagement/order'
import restaurantPrivateRoom from './page/restaurantManagement/privateRoom'
import restaurantDiningTable from './page/restaurantManagement/diningTable'
import restaurantService from './page/restaurantManagement/components/service'
import restaurantAddService from './page/restaurantManagement/components/addService'
import restaurantAddServiceStep1 from './page/restaurantManagement/components/serviceStep1'
import restaurantAddServiceStep2 from './page/restaurantManagement/components/serviceStep2'
import restaurantAddServiceStep3 from './page/restaurantManagement/components/serviceStep3'
import restaurantAddServiceStep4 from './page/restaurantManagement/components/serviceStep4'
import restaurantAddServiceStep5 from './page/restaurantManagement/components/serviceStep5'
import restaurantAddSetMeal from './page/restaurantManagement/components/add-set-meal/index'

// 服务订单
import serviceOrderIndex from './page/serviceOrder/orderIndex'

// 商品页
// import goodsIndex from './page/goods/index/index'
// import showGoods from './page/goods/index/components/showGoods.vue'
import goodsDetail from './page/goods/detail/index'
import newGoodsDetail from './page/goods/detail/newIndex'
import auctionOrder from './page/goods/detail/components/auctionOrder'


import goodslistIndex from './page/goods/index/list-index'
import indexGoodAll from './page/goods/index/all'
import goodsRetrospect from './page/goods/index/findRetrospect'
import goodsSearch from './page/goods/index/searchList'

// 商品发布
import releaseGoods from './page/goods/release/index'
import releaseGoodsStep1 from './page/goods/release/step1'
import releaseGoodsStep2 from './page/goods/release/step2'
import releaseGoodsStep3 from './page/goods/release/step3'
import releaseGoodsStep4 from './page/goods/release/step4'
import releaseGoodsStep5 from './page/goods/release/step5'
import address from './page/goods/address'
import shopCart from './page/goods/shop-cart'
import orderCheck from './page/goods/order-check'
// 订单管理
import orderDetails from './page/goods/orderDetails/index'
import soldGoods from './page/goods/orderDetails/soldGoods'
import purchasedGoods from './page/goods/orderDetails/purchasedGoods'
import purchasedBidding from './page/goods/orderDetails/purchasedBidding'
import soldBidding from './page/goods/orderDetails/soldBidding'

// 正在出售
import onSale from './page/goods/on-sale'
// 货架
import goodsShelf from './page/goods/goods-shelf'
import recycleBin from './page/goods/recycle-bin'

// 名片管理
import carteManagement from './page/newApplication/carteManagement/index'
// 员工门户管理
import staffPortal from './page/newApplication/staffPortal/index'




Vue.use(VueRouter)

const routers = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: wyNav
    },{
      path: '/index',
      component: wyNav
    },{ // 掌上无忧
      path: '/pocket',
      component: wyPocket
    }, { // 51首页
      path: '/51index',
      component: wyWrapper,
      children: [{
        path: '/',
        component: wyIndex
      }, {
        path: 'inforMationList',
        component: inforMationList
      }, {
        path: 'policyList',
        component: policyList
      }, {
        path: 'knowledgeList',
        component: knowledgeList
      },
      {
        path: 'standardList',
        component: standardList
      },
      //  {
      //   path: 'enterpriseList',
      //   component: enterpriseList
      // }, {
      //   path: 'departmentList',
      //   component: departmentList
      // }, {
      //   path: 'expertList',
      //   component: expertList
      // }
      {
        path:'getMember',
        component:getMember
      }
    ]
    },
    {
      path: '/51Index/serviceList',
      component: serviceList,
      children: [
        {
          path: 'all',
          name: 'all',
          component: indexServiceAll
        },
        {
          path: 'fishing',
          name: 'fishing',
          component: indexServiceFishing
        },
        {
          path: 'picking',
          name: 'indexServicePicking',
          component: indexServicePicking
        },
        {
          path: 'stay',
          name: 'indexServiceStay',
          component: indexServiceStay
        },
        {
          path: 'restaurant',
          name: 'restaurant',
          component: indexServiceRestaurant
        },
        {
          path: 'scenicSpot',
          name: 'scenicSpot',
          component: indexServiceScenicSpot
        },
        {
          path: 'consultation',
          name: 'consultation',
          component: indexServiceConsultation
        }
      ]
    },
    // {
    //   path: '/pro/head',
    //   component: head
    // },
    {
      path: '/payment',
      component: payment
    },
    {
      path: '/payReading',
      component: payReading
    },
    {
      path: '/member',
      component: index,
      children: [
        {
          path: 'proxy',
          component: proxy,
        },
        {
          path: 'applyProxy',
          component: applyProxy,
        },
        {
          path: 'pendingApproval',
          component: pendingApproval,
        }
      ]
    },
    {
      path: '/pro/cardManage',
      component: cardManage
    },
    {
      path: '/pro/follow',
      component: follow,
      children: [
        {
          path: 'follow01',
          component: follow01
        },
        {
          path: 'follow02',
          component: follow02
        },
        {
          path: 'follow03',
          component: follow03
        },
        {
          path: 'follow04',
          component: follow04
        },
        {
          path: 'follow05',
          component: follow05
        },
        {
          path: 'follow06',
          component: follow06
        }
      ]
    },
    {
      path: '/member/follow',
      component: newFollow
    },
    {
      path: '/focusManagement',
      component: focusManagement,
      children: [
        {
          path: 'species',
          name: 'focusManagementSpecies',
          component: focusManagementSpecies
        }, {
          path: 'product',
          name: 'product',
          component: focusManagementProduct
        }, {
          path: 'service',
          name: 'focusManagementService',
          component: focusManagementService
        }, {
          path: 'member',
          name: 'member',
          component: focusManagementMember
        }, {
          path: 'public',
          name: 'public',
          component: focusManagementPublic
        }, {
          path: 'news',
          name: 'news',
          component: focusManagementNews
        }, {
          path: 'knowledge',
          name: 'knowledge',
          component: focusManagementKnowledge
        }, {
          path: 'policy',
          name: 'policy',
          component: focusManagementPolicy
        }
      ]
    },
    {
      path: '/nameLibrary',
      component: nameLibraryIndex,
      children: [
        {
          path: 'species',
          name: 'nameLibrarySpecies',
          component: nameLibrarySpecies,
        }, {
          path: 'addSpecies',
          name: 'addSpecies',
          component: nameLibraryaddSpecies,
        },{
          path: 'variety',
          name: 'variety',
          component: nameLibraryVariety,
        }, {
          path: 'addVariety',
          name: 'addVariety',
          component: nameLibraryaddVariety,
        },{
          path: 'disease',
          name: 'disease',
          component: nameLibraryDisease,
        }, {
          path: 'addDisease',
          name: 'addDisease',
          component: nameLibraryaddDisease,
        },{
          path: 'pest',
          name: 'pest',
          component: nameLibraryPest,
        }, {
          path: 'addPest',
          name: 'addPest',
          component: nameLibraryaddPest,
        },{
          path: 'commodity',
          name: 'commodity',
          component: nameLibraryCommodity,
        }, {
          path: 'addCommodity',
          name: 'addCommodity',
          component: nameLibraryaddCommodity,
        }, {
          path: 'service',
          name: 'nameLibraryService',
          component: nameLibraryService,
        }, {
          path: 'addService',
          name: 'addService',
          component: nameLibraryaddService,
        }
      ]
    },
    {
      path: '/pro/nameLibrary',
      component: nameLibrary,
      meta: {
        auth: true
      }
    },
    {
      path: '/pro/addSpec',
      component: addSpec,
      meta: {
        auth: true
      }
    },
    {
      path: '/pro/addSpecies',
      component: addSpecies,
      meta: {
        auth: true
      }
    },
    {
      path: '/pro/addVariety',
      component: addVariety,
      meta: {
        auth: true
      }
    },
    {
      path: '/pro/addCommonProduct',
      component: addCommonProduct,
      meta: {
        auth: true
      }
    },
    {
      path: '/pro/addDisease',
      component: addDisease,
      meta: {
        auth: true
      }
    },
    {
      path: '/pro/addPest',
      component: addPest,
      meta: {
        auth: true
      }
    },
    {
      path: '/member/collect',
      component: collect
    },
    {
      path: '/pro/authenGuide',
      component: authenGuide
    },
    // {
    //   path: '/top',
    //   component: top
    // },
    // {
    //   path: '/upload',
    //   component: upload
    // },
    // 文件管理
    {
      path: '/member/fileManageIndex',
      component: fileManageIndex
    },
    {
      path: '/relationIndex',
      component: relationIndex
    },
    {
      path: '/pro/member',
      component: memberIndex,
      meta: {
        auth: true
      }
    },
    {
      path: '/pro/myFollow',
      component: myFollow
    },
    {
      path: '/center',
      component: appCenterIndex
    },
    {
      path: '/member/personCodeManage',
      component: personCodeManage
    },
    {
      path: '/member/companyCodeManage',
      component: companyCodeManage
    },
    {
      path: '/member/myRecommendation',
      component: myRecommendation
    },
    /* {
      path: '/member/employManage',
      component: employManage
    }, */
    {
      path: '/member/publicNumManage',
      component: publicNumManage
    },
    {
      path: '/member/fundAccountManage',
      component: fundAccountManage
    },
    {
      path: '/InforMation/findInforMationDetail',
      component: findInforMationDetail
    },
    {
      path: '/InforMation/policy',
      component: policy
    },
    {
      path: '/InforMation/policyDetail',
      component: policyDetail
    },
    { path: '/InforMation/standardDetail', component: standardDetail },
    { path: '/InforMation/serviceDetail', component: InforMationserviceDetail},
    {
      path: '/InforMation/knowledge',
      component: knowledge
    },
    {
      path: '/InforMation/knowledgeDetail',
      component: knowledgeDetail
    },
    {
      path: '/InforMation/expertDetail',
      component: expertDetail
    },
    {
      path: '/enterprise/detail',
      component: enterpriseDetail
    },
    { path: '/InforMation/findInforMationDetail', component: findInforMationDetail },
    { path: '/InforMation/policy', component: policy },
    { path: '/InforMation/policyDetail', component: policyDetail },
    { path: '/InforMation/knowledge', component: knowledge },
    { path: '/InforMation/knowledgeDetail', component: knowledgeDetail },
    { path: '/InforMation/expertDetail', component: expertDetail },
    { path: '/enterprise/detail', component: enterpriseDetail },
    { path: '/InforMation/bookBlurb', component: bookBlurb },
    { path: '/InforMation/bookDetail', component: bookDetail },

    {
      path: '/departmentDetail',
      component: departmentDetail
    },
    //圈子
    // {
    //     path: '/circle',
    //     component: circle
    // },
    // {
    //     path: '/circleDetail',
    //     component: circleDetail
    // },
    {
      path: '/mapNav',
      component: mapNav
    },
    // {
    //   path: '/mall/index',
    //   name: 'mallIndex',
    //   component: mallIndex
    // },
    // {
    //   path: '/mall/ypAuction',
    //   component: ypAuction
    // },
    // {
    //   path: '/mall/hotGroupBuy',
    //   component: hotGroupBuy
    // },
    // {
    //   path: '/mall/fixPriceProduct',
    //   component: fixPriceProduct
    // },
    // {
    //   path: '/mall/stock',
    //   component: stock
    // },
    // {
    //   path: '/mall/newPresell',
    //   component: newPresell
    // },
    // {
    //   path: '/mall/ascend',
    //   component: ascend
    // },
    // {
    //   path: '/mall/hotGroupBuyDetail',
    //   component: hotGroupBuyDetail
    // },
    // {
    //   path: '/mall/fixPriceProductDetail',
    //   component: fixPriceProductDetail
    // },
    // {
    //   path: '/mall/ypAuctionDetail',
    //   component: ypAuctionDetail
    // },
    // {
    //   path: '/mall/newPresellDetail',
    //   component: newPresellDetail
    // },
    // {
    //   path: '/mall/stockDetail',
    //   component: stockDetail
    // },
    // {
    //   path: '/mall/ascendDetail',
    //   component: ascendDetail
    // },
    {
      path: '/service/consultationService',
      component: consultationService
    },
    {
      path: '/consultationService/detail',
      component: consultationServiceDetail
    },
    {
      path: '/addConsultationService',
      component: addConsultationService,
      children: [
        {
          path: 'step1',
          component: addConsultationServiceStep1,
        },
        {
          path: 'step2',
          component: addConsultationServiceStep2,
        },
        {
          path: 'step3',
          component: addConsultationServiceStep3,
        },
        {
          path: 'step4',
          component: addConsultationServiceStep4,
        },
        {
          path: 'step5',
          component: addConsultationServiceStep5,
        }
      ]
    },
    // 生产基地管理
    {
      path: '/member/productionBaseGuide',
      component: productionBaseGuide
    },
    {
      path: '/member/addProductionBase',
      component: addProductionBase,
      children: [
        {
          path: 'addProductionBaseStep1',
          component: addProductionBaseStep1
        },
        {
          path: 'addProductionBaseStep2',
          component: addProductionBaseStep2
        },
        {
          path: 'addProductionBaseStep3',
          component: addProductionBaseStep3
        },
        {
          path: 'addProductionBaseStep4',
          component: addProductionBaseStep4
        }
      ]
    },
    {
      path: '/member/productionBaseList',
      component: productionBaseList
    },
    {
      path: '/member/editProductionBase',
      component: editProductionBase
    },
    {
      path: '/member/productionBaseDetail',
      component: productionBaseDetail
    },
    // 种养
    {
      path: '/pro/addPlanter',
      component: addPlanter
    },
    // 认证
    {
      path: '/auth',
      component: auth,
      redirect: '/auth/step1',
      meta: {
        auth: true
      },
      children: [{
        path: 'step1',
        component: authStep1,
        meta: {
          suth: true
        }
      },
      {
        path: 'step3',
        component: authStep3,
        meta: {
          auth: true
        }
      }, {
        path: 'step2',
        component: authStep2,
        meta: {
          auth: true
        }
      }, {
        path: 'step6',
        component: authStep6,
        meta: {
          auth: true
        }
      }, {
        path: 'step4',
        component: authStep4,
        meta: {
          auth: true
        }
      }, {
        path: 'step5',
        component: authStep5,
        meta: {
          auth: true
        }
      }, {
        path: 'step7',
        component: authStep7,
        meta: {
          auth: true
        }
      }]
    },
    // 员工门户管理
    {
      path: '/staffPortal',
      component: staffPortal
    },

    // 新门户
    {
      path: '/portals',
      component: portalNav,
      children: [
        {
          path: 'index',
          component: portalIndex
        },
        {
          path: 'introduction',
          component: portalIntroduction
        },
        {
          path: 'dynamic',
          component: portalDynamic
        },
        {
          path: 'policy',
          component: portalPolicy
        },
        {
          path: 'knowledge',
          component: portalKnowledge
        },
        {
          path: 'contcat',
          component: portalContcat
        },
        {
          path: 'standard',
          component: portalStandard
        },
        {
          path: 'service',
          component: portalService
        },
        {
          path: 'product',
          component: portalProduct
        },
        {
          path: 'productionBase',
          component: portalProductionBase
        },
      ]
    },
    // 新门户 乡村
    {
      path: '/ruralPortal',
      component: ruralPortalNav,
      children: [
        {
          path: 'index',
          component: ruralPortalIndex
        },
        {
          path: 'introduction',
          component: portalIntroduction
        },
        {
          path: 'dynamic',
          component: portalDynamic
        },
        {
          path: 'policy',
          component: portalPolicy
        },
        {
          path: 'knowledge',
          component: portalKnowledge
        },
        {
          path: 'contcat',
          component: portalContcat
        },
        {
          path: 'standard',
          component: portalStandard
        },
        {
          path: 'service',
          component: portalService
        },
        {
          path: 'product',
          component: portalProduct
        },
        {
          path: 'productionBase',
          component: portalProductionBase
        },
      ]
    },
    // 新门户 合作社
    {
      path: '/cooperativePortal',
      component: cooperativePortalNav,
      children: [
        {
          path: 'index',
          component: cooperativePortalIndex
        },
        {
          path: 'introduction',
          component: portalIntroduction
        },
        {
          path: 'dynamic',
          component: portalDynamic
        },
        {
          path: 'policy',
          component: portalPolicy
        },
        {
          path: 'knowledge',
          component: portalKnowledge
        },
        {
          path: 'contcat',
          component: portalContcat
        },
        {
          path: 'standard',
          component: portalStandard
        },
        {
          path: 'service',
          component: portalService
        },
        {
          path: 'product',
          component: portalProduct
        },
        {
          path: 'productionBase',
          component: portalProductionBase
        },
      ]
    },
    // 新门户 农头
    {
      path: '/farmHeadPortal',
      component:farmHeadPortalNav,
      children: [
        {
          path: 'index',
          component: farmHeadPortalIndex
        },
        {
          path: 'introduction',
          component: portalIntroduction
        },
        {
          path: 'dynamic',
          component: portalDynamic
        },
        {
          path: 'policy',
          component: portalPolicy
        },
        {
          path: 'knowledge',
          component: portalKnowledge
        },
        {
          path: 'contcat',
          component: portalContcat
        },
        {
          path: 'standard',
          component: portalStandard
        },
        {
          path: 'service',
          component: portalService
        },
        {
          path: 'product',
          component: portalProduct
        },
        {
          path: 'productionBase',
          component: portalProductionBase
        },
      ]
    },
    // 新门户 专家
    {
      path: '/expertPortal',
      component:expertPortalNav,
      children: [
        {
          path: 'index',
          component: expertPortalIndex
        },
        {
          path: 'introduction',
          component: portalIntroduction
        },
        {
          path: 'dynamic',
          component: portalDynamic
        },
        {
          path: 'policy',
          component: portalPolicy
        },
        {
          path: 'knowledge',
          component: portalKnowledge
        },
        {
          path: 'contcat',
          component: expertPortalContcat
        },
        {
          path: 'standard',
          component: portalStandard
        },
        {
          path: 'service',
          component: expertPortalService
        },
        {
          path: 'product',
          component: expertPortalProduct
        },
        {
          path: 'productionBase',
          component: expertPortalProductionBase
        },
      ]
    },
    // 名片管理
    {
      path: '/carteManagement',
      component: carteManagement
    },
    //个人的资料
    {
      path: '/personalIndex',
      component: personalIndex,
      children: [
        {
          path: 'detail',
          component: personalDetail
        },
        {
          path: 'modify',
          component: personalModify
        }
      ]
    },
    //垂钓goFishingIndex
    {
      path: '/fishing',
      name: 'goFishingIndex',
      component: goFishingIndex,
      children: [
        {
          path: 'service',
          name: 'fishingService',
          component: fishingService
        },
        {
          path: 'productList',
          name: 'fishingProductList',
          component: fishingProductList
        },
        {
          path: 'species',
          name: 'fishingSpecies',
          component: fishingSpecies
        }
      ]
    },
    //垂钓添加服务
    {
      path: '/addService',
      component: fishingAddService,
      // name: 'fishing',
      children: [
        {
          path: 'step1',
          name: 'addServiceSetp1',
          component: addServiceSetp1
        },
        {
          path: 'step2',
          name: 'addServiceSetp2',
          component: addServiceSetp2
        },
        {
          path: 'step3',
          name: 'addServiceSetp3',
          component: addServiceSetp3
        },
        {
          path: 'step4',
          name: 'addServiceSetp4',
          component: addServiceSetp4
        },
        {
          path: 'step5',
          name: 'addServiceSetp5',
          component: addServiceSetp5
        }
      ]
    },
    // 采摘goPickingIndex
    {
      path: '/picking',
      name: 'goPickingIndex',
      component: goPickingIndex,
      children: [
        {
          path: 'productList',
          name: 'pickingProductList',
          component: pickingProductList
        },
        {
          path: 'species',
          name: 'pickingSpecies',
          component: pickingSpecies
        },
        {
          path: 'service',
          name: 'pickingService',
          component: pickingService
        },
      ]
    },
    //垂钓添加服务
    {
      path: '/pickingAddService',
      component: pickingAddService,
      name: 'picking',
      children: [
        {
          path: 'step1',
          name: 'pickingAddServiceSetp1',
          component: pickingAddServiceSetp1
        },
        {
          path: 'step2',
          name: 'pickingAddServiceSetp2',
          component: pickingAddServiceSetp2
        },
        {
          path: 'step3',
          name: 'pickingAddServiceSetp3',
          component: pickingAddServiceSetp3
        },
        {
          path: 'step4',
          name: 'pickingAddServiceSetp4',
          component: pickingAddServiceSetp4
        },
        {
          path: 'step5',
          name: 'pickingAddServiceSetp5',
          component: pickingAddServiceSetp5
        }
      ]
    },
    // 景区
    {
      path: '/scenicSpot',
      component: scenicSpotIndex,
      children: [
        {
          path: 'ticket',
          name: 'ticket',
          component: scenicSpotTicket
        },
        {
          path: 'service',
          name: 'scenicSpotService',
          component: scenicSpotService
        },
        {
          path: 'order',
          name: 'scenicSpotOrder',
          component: scenicSpotOrder
        }
      ]
    },
    {
      path: '/scenicSpot/add-set-meal',
      component: scenicAddSetMeal
    },
    // 住宿
    {
      path: '/stay',
      name: 'stayIndex',
      component: stayIndex,
      children: [
        {
          path: 'roomType',
          name: 'roomType',
          component: stayRoomType
        },
        {
          path: 'roomList',
          name: 'roomList',
          component: stayRoomList
        },
        {
          path: 'service',
          name: 'stayService',
          component: stayService
        },
        {
          path: 'order',
          name: 'stayOrder',
          component: stayOrder
        }
      ]
    },
    {
      path: '/stay/add-set-meal',
      component: stayAddSetMeal
    },
    // 餐饮管理
    {
      path: '/restaurant',
      component: restaurantIndex,
      children: [
        {
          path: 'menuType',
          name: 'menuType',
          component: restaurantMenuType
        },
        {
          path: 'dish',
          name: 'dish',
          component: restaurantDish
        },
        {
          path: 'privateRoom',
          name: 'privateRoom',
          component: restaurantPrivateRoom
        },
        {
          path: 'diningTable',
          name: 'diningTable',
          component: restaurantDiningTable
        },
        {
          path: 'service',
          name: 'restaurantService',
          component: restaurantService
        },
        {
          path: 'order',
          name: 'restaurantOrder',
          component: restaurantOrder
        }
      ]
    },
    // 种植业生产管理
    {
      path: '/productionControl',
      component: productionControlIndex,
      children: [
        {
          path: 'yearList',
          name: 'yearList',
          component: yearList,
        },
        {
          path: 'plantList',
          name: 'plantList',
          component: plantList,
        },
        {
          path: 'productionPlans',
          name: 'productionPlans',
          component: productionPlans,
        },
        {
          path: 'outputGuess',
          name: 'outputGuess',
          component: outputGuess,
        },
        {
          path: 'productionRecords',
          name: 'productionRecords',
          component: productionRecords,
        },
      ]
    },
    // 库存管理
    {
      path: '/inventoryControl',
        component: inventoryControl,
        children: [
          {
            path: 'config',
            name: 'config',
            component: config
          }
        ]
    },
    {
      path: '/storeInfoSee',
      name: 'storeInfoSee',
      component: storeInfoSee
    },
    //
    {
      path: '/restaurant/add-set-meal',
      component: restaurantAddSetMeal
    },
    //  住宿 添加服务
    {
      path: '/stayAddService',
      component: stayAddService,
      children: [
        {
          path: 'step1',
          name: 'staystep1',
          component: staystep1
        },
        {
          path: 'step2',
          name: 'staystep2',
          component: staystep2
        },
        {
          path: 'step3',
          name: 'staystep3',
          component: staystep3
        },
        {
          path: 'step4',
          name: 'staystep4',
          component: staystep4
        },
        {
          path: 'step5',
          name: 'staystep5',
          component: staystep5
        }
      ]
    },
    // 景区 添加服务
    {
      path: '/scenicSpotAddService',
      component: scenicSpotAddService,
      children: [
        {
          path: 'step1',
          name: 'scenicSpotstep1',
          component: scenicSpotstep1
        },
        {
          path: 'step2',
          name: 'scenicSpotstep2',
          component: scenicSpotstep2
        },
        {
          path: 'step3',
          name: 'scenicSpotstep3',
          component: scenicSpotstep3
        },
        {
          path: 'step4',
          name: 'scenicSpotstep4',
          component: scenicSpotstep4
        },
        {
          path: 'step5',
          name: 'scenicSpotstep5',
          component: scenicSpotstep5
        }
      ]
    },
    // 餐饮添加服务
    {
      path: '/restaurantAddService',
      component: restaurantAddService,
      children: [
        {
          path: 'step1',
          name: 'restaurantAddServiceStep1',
          component: restaurantAddServiceStep1
        },
        {
          path: 'step2',
          name: 'restaurantAddServiceStep2',
          component: restaurantAddServiceStep2
        },
        {
          path: 'step3',
          name: 'restaurantAddServiceStep3',
          component: restaurantAddServiceStep3
        },
        {
          path: 'step4',
          name: 'restaurantAddServiceStep4',
          component: restaurantAddServiceStep4
        },
        {
          path: 'step5',
          name: 'restaurantAddServiceStep5',
          component: restaurantAddServiceStep5
        }
      ]
    },
    // 服务订单
    {
      path: '/service-order/index',
      component: serviceOrderIndex
    },
    // 商品
    {
      path: '/goods',
      component: goodslistIndex,
      children: [
        {
          path: 'index',
          name: 'index',
          component: indexGoodAll
        },
        {
          path: 'retrospect',
          name: 'retrospect',
          component: goodsRetrospect
        },
        {
          path: 'search',
          name: 'search',
          component: goodsSearch
        }
      ]
    },
    // {
    //   path: '/goods/index',
    //   component: goodsIndex
    // },
    // {
    //   path: '/goods/showGoods',
    //   component: showGoods
    // },
    {
      path: '/goods/detail',
      component: goodsDetail
    },
    {
      path: '/goods/newDetail',
      component: newGoodsDetail
    },{
      path: '/goods/auction-order-check',
      component: auctionOrder
    },
    {
      path: '/release-goods',
      component: releaseGoods,
      meta: {
        auth: true
      },
      children: [
        {
          path: '/release-goods',
          component: releaseGoodsStep1,
          meta: {
            auth: true
          }
        },
        {
          path: 'step1',
          component: releaseGoodsStep1,
          meta: {
            auth: true
          }
        },
        {
          path: 'step2',
          component: releaseGoodsStep2,
          meta: {
            auth: true
          }
        },
        {
          path: 'step3',
          component: releaseGoodsStep3,
          meta: {
            auth: true
          }
        },
        {
          path: 'step4',
          component: releaseGoodsStep4,
          meta: {
            auth: true
          }
        },
        {
          path: 'step5',
          component: releaseGoodsStep5,
          meta: {
            auth: true
          }
        }
      ]
    },
    // 正在出售
    {
      path: '/goods/on-sale',
      component: onSale,
      meta: {
        auth: true
      }
    },
    // 货架
    {
      path: '/goods/goods-shelf',
      component: goodsShelf,
      meta: {
        auth: true
      }
    },
    {
      path: '/goods/recycle-bin',
      component: recycleBin,
      meta: {
        auth: true
      }
    },
    {
      path: '/address',
      component: address,
      meta: {
        auth: true
      }
    },
    {
      path: '/shop-cart',
      component: shopCart,
      meta: {
        auth: true
      }
    },
    {
      path: '/order-check',
      component: orderCheck,
      meta: {
        auth: true
      }
    },
    {
      path: '/orderDetails',
      component: orderDetails,
      meta: {
        auth: true
      },
      children: [
        {
          path: 'soldGoods',
          name: 'soldGoods',
          component: soldGoods,
          meta: {
            auth: true
          }
        },
        {
          path: 'purchasedGoods',
          name: 'purchasedGoods',
          component: purchasedGoods,
          meta: {
            auth: true
          }
        },
        {
            path: 'purchasedBidding',
            name: 'purchasedBidding',
            component: purchasedBidding,
            meta: {
                auth: true
            }
        },
        {
            path: 'soldBidding',
            name: 'soldBidding',
            component: soldBidding,
            meta: {
              auth: true
            }
        }
      ]
    }
  ]
})

export default routers
