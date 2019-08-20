<template>
    <div class="wrapper">
        <top :address="false" active="1"/>
        <!-- 导航 -->
       <head-nav></head-nav>
        <!--        <mall-search :datas="search" />-->
        <section class="layouts">
            <!-- 要文排行 -->
            <Row class="mt30">
                <Col span="8">
                <Carousel v-model="carousel.value" :autoplay="carousel.autoplay" :dots="carousel.dots"
                          :arrow="carousel.arrow">
                    <Carousel-item v-for="(item,index) in carousel1List" :key="index">
                        <Row>
                            <Col span="24" class="mb10">
                            <img :src="item.pictureUrl" width="100%" height="330px">
                            <!-- <div class="tit">{{item.title}}</div>-->
                            </Col>
                        </Row>
                    </Carousel-item>
                </Carousel>
                </Col>
                <Col span="8" class="pl20 pr10">
                <mall-new-title text="农业要闻"/>
                <ul class="mall-news agro-news has-dot">
                    <li v-for="(item,index) in agroList1" :key='index' style="height: 30px;line-height: 30px;">
                        <a :href="item.url" class="tit ell">{{item.title}}</a>
                    </li>
                    <!-- <li v-for="(item,index) in agroList" :key='index' style="height: 30px;line-height: 30px;">
                        <a  :href="item.url"  class="tit ell">{{item.title}}</a>
                    </li> -->
                </ul>
                </Col>
                <Col span="8">
                <mall-new-title text="阅读排行" style="padding: 3px 8px;"/>
                <ul class="mall-news read-rate has-num">
                    <li v-for="(item,index) in mostReadList" class="clear" :key="index" style="padding:5px 10px">
                        <Col span="1">
                        <div class="num">{{index+1}}</div>
                        </Col>
                        <Col span="18">
                        <a :href="item.url" class="ell tit" :title="item.title">{{item.title}}</a>
                        </Col>
                        <Col span="5" class="t-grey tr">
                        {{item.clickRate}}</Col>
                    </li>
                </ul>
                </Col>
            </Row>
            <!-- 热门 推荐 -->
            <Row class="mt30">
                <Col span="4" class="hot-classify">
                <div class="vui-new-title" style="background:#F7F7F7;">
                    <Row>
                        <Col span="12">
                        <span class="vui-new-title-left">热门分类</span>
                        </Col>
                        <Col span="12" class="tr vui-new-title-right">
                        <Icon type="ios-search" :size="20" class="mt5" @click.native="openClassifySearch"/>
                        </Col>
                    </Row>
                </div>
                <transition name="fade" mode="out-in">
                    <div class="classify-search" v-show="hotClassifyShow">
                        <Icon type="android-close" :size="16"
                              style="position: absolute;top:10px;left: 7px;z-index: 99;background: #fff;color:#999;"
                              @click.native="closeClassifySearch"/>
                        <Select
                                v-model="hotClassIfy.value"
                                filterable>
                            <Option v-for="(option, index) in hotClassIfy.list" :value="option.value" :key="index">
                                {{option.label}}
                            </Option>
                        </Select>
                        <Icon type="ios-search" :size="20"
                              style="position: absolute;top:8px;right: 7px;z-index: 99;background: #fff;color:#999;"/>
                    </div>
                </transition>

                <min-nav></min-nav>
                
                </Col>
                <Col span="20" class="recom-list pl20">
                <mall-new-title text="产品推荐" more background="#F7F7F7">
                    <template slot="more">
                        <a href="javascript:void(0);" @click="getRecommend(2)" class="link">推荐产品</a> |
                        <a href="javascript:void(0);" @click="getRecommend(0)" class="link">热门产品</a> |
                        <a href="javascript:void(0);" @click="getRecommend(1)" class="link">最新产品</a>
                    </template>
                </mall-new-title>
                <div style="border:1px solid #F5F5F5;" class="pl15 pr15 pt15">
                    <Row :gutter="16">
                        <Col span="6" v-for="(item, index) in recomList">
                            <div @click="handleDetail(item)" class="pb15">
                                <div style="border:1px solid #F5F5F5;">
                                    <img :src="item.src[0] ? item.src[0] : '../../../../../static/img/goods-list-no-picture1.png'" alt="" width="100%" height="140">
                                </div>
                                <div class="pl5 pr5">
                                    <p style="color:#EE2127" class="pb5 pt5" v-if="item.price && item.finish"><b>￥ {{item.price}}</b></p>
                                    <p style="color:#EE2127" class="pb5 pt5" v-else><b>￥ {{item.discount}}</b></p>
                                    <p class="ell" :title="item.seller" style="cursor: default; font-size:12px;" >{{item.seller}}</p>
                                    <p class="ell" :title="item.address" style="cursor: default; font-size:12px;">{{item.address}}</p>
                                </div>
                            </div>
                        </Col>
                    </Row>
                </div>
                </Col>
            </Row>
            <div class="mt30 mb30">
            </div>

            <!-- 服务 -->
            <Row>
                <Col span="6" class="serve-classify">
                <div class="vui-new-title" style="background:#F7F7F7;">
                    <Row>
                        <Col span="12">
                        <span class="vui-new-title-left">服务分类</span>
                        </Col>
                        <Col span="12" class="tr vui-new-title-right">
                        <Icon type="ios-search" :size="20" class="mt5" @click.native="openClassifySearch"/>
                        </Col>
                    </Row>
                </div>
                <Row :gutter="10" class="list">
                    <Col span="8" v-for="(item,index) in serveClassifyData" :key="index">
                    <a href="javascript:;" style="color: #4A4A4A;">{{item}}</a>
                    </Col>
                </Row>
                <transition name="fade" mode="out-in">
                    <div class="classify-search" v-show="hotClassifyShow">
                        <Icon type="android-close" :size="16"
                              style="position: absolute;top:10px;left: 7px;z-index: 99;background: #fff;color:#999;"
                              @click.native="closeClassifySearch"/>
                        <Select
                                v-model="hotClassIfy.value"
                                filterable>
                            <Option v-for="(option, index) in hotClassIfy.list" :value="option.value" :key="index">
                                {{option.label}}
                            </Option>
                        </Select>
                        <Icon type="ios-search" :size="20"
                              style="position: absolute;top:8px;right: 7px;z-index: 99;background: #fff;color:#999;"/>
                    </div>
                </transition>
                </Col>
                <Col span="9" class="pr20">
                <mall-new-title text="服务供应" more>
                    <template slot="more">
                        <a href="javascript:;" class="link">更多 >></a>
                    </template>
                </mall-new-title>
                <ul class="mall-news read-rate has-num mt5">
                    <li v-for="(item,index) in readList" class="clear">
                        <Col span="1">
                        <div class="num">{{index+1}}</div>
                        </Col>
                        <Col span="18">
                        <a :href="item.url" class="ell tit">{{item.title}}</a>
                        </Col>
                        <Col span="5" class="t-grey tr">
                        {{item.clickRate}}</Col>
                    </li>
                </ul>
                </Col>
                <Col span="9" class="pr20">
                <mall-new-title text="服务需求" more>
                    <template slot="more">
                        <a href="javascript:;" class="link">更多 >></a>
                    </template>
                </mall-new-title>
                <ul class="mall-news read-rate has-num mt5">
                    <li v-for="(item,index) in readList" class="clear">
                        <Col span="1">
                        <div class="num">{{index+1}}</div>
                        </Col>
                        <Col span="18">
                        <a :href="item.url" class="ell tit">{{item.title}}</a>
                        </Col>
                        <Col span="5" class="t-grey tr">
                        {{item.clickRate}}</Col>
                    </li>
                </ul>
                </Col>
            </Row>
            <div class="mt30 mb30">
            </div>

            <Row class="info-list">
                <mall-new-title text="资讯中心" more background="#F7F7F7">
                    <template slot="more">
                        <a href="javaScript:;" @click="showInfo('020107')" class="link"
                           :class="{'info-list-a':'020107'==activeListA}">会员动态</a> |
                        <a href="javaScript:;" @click="showInfo('020101')" class="link"
                           :class="{'info-list-a':'020101'==activeListA}">行业动态</a> |
                        <a href="javaScript:;" @click="showInfo('020102')" class="link"
                           :class="{'info-list-a':'020102'==activeListA}">企业动态</a> |
                        <a href="javaScript:;" @click="showInfo('020103')" class="link"
                           :class="{'info-list-a':'020103'==activeListA}">专家动态</a> |
                        <a href="javaScript:;" @click="showInfo('020104')" class="link"
                           :class="{'info-list-a':'020104'==activeListA}">乡村动态</a> |
                        <a href="javaScript:;" @click="showInfo('020105')" class="link"
                           :class="{'info-list-a':'020105'==activeListA}">群协动态</a> |
                        <a href="javaScript:;" @click="showInfo('020106')" class="link"
                           :class="{'info-list-a':'020106'==activeListA}">国际动态</a> |
                        <a href="javaScript:;" @click="showInfo('020108')" class="link"
                           :class="{'info-list-a':'020108'==activeListA}">城乡互动</a>
                    </template>
                </mall-new-title>
                <div style="height:330px">
                    <Col span="8" class="mt20">
                    <Carousel v-model="carousel2.value" :autoplay="carousel2.autoplay" :dots="carousel2.dots"
                              :arrow="carousel2.arrow">
                        <Carousel-item v-for="(item,index) in carousel2List" :key="index">
                            <Row>
                                <Col span="24" class="mb10">
                                <img :src="item.pictureUrl" width="100%" height="290px">
                                </Col>
                            </Row>
                        </Carousel-item>
                    </Carousel>
                    </Col>
                    <Col span="8" class="pl20 pr10">
                    <ul class="mall-news agro-news has-dot" style="margin-top: 10px;">
                        <li v-for="(item,index) in info1" style="height: 30px;line-height: 30px;">
                            <template v-if="item.columnType==='图书'">
                                <router-link
                                        :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.informationDetailId,'book_type':'information' }}"
                                        class="tit ell"> {{ item.title }}
                                </router-link>
                            </template>
                            <template v-if="item.columnType!='图书'">
                                <router-link
                                        :to="{ path: '/InforMation/findInforMationDetail', query: { 'id': item.informationDetailId }}"
                                        class="tit ell"> {{ item.title }}
                                </router-link>
                            </template>
                        </li>
                    </ul>
                    </Col>
                    <Col span="8">
                    <ul class="mall-news agro-news has-dot" style="margin-top: 10px;">
                        <li v-for="(item,index) in info2" style="height: 30px;line-height: 30px;">
                            <template v-if="item.columnType==='图书'">
                                <router-link
                                        :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.informationDetailId,'book_type':'information' }}"
                                        class="tit ell"> {{ item.title }}
                                </router-link>
                            </template>
                            <template v-if="item.columnType!='图书'">
                                <router-link
                                        :to="{ path: '/InforMation/findInforMationDetail', query: { 'id': item.informationDetailId }}"
                                        class="tit ell"> {{ item.title }}
                                </router-link>
                            </template>
                        </li>
                    </ul>
                    </Col>
                </div>

            </Row>
            <Row>
                <Col span="24">
                <slick ref="slick" :options="slickOptions" class="slick">
                    <a :href="item.url" v-for="item in slickList"><img :src="item.src"></a>
                </slick>
                </Col>
            </Row>

            <Row>
                <mall-new-title text="知识大全" more background="#F7F7F7"/>
                <div style="margin-top:15px">
                    <Col span="8">
                    <div class="notice notice-h-xs">
                        <div class="notice-title">
                            <span @click="isKepuShow1 = true" :class="{active: !!isKepuShow1}">科普</span> &nbsp;| <span
                                @click="isKepuShow1 = false" :class="{active: !isKepuShow1}">论文</span>
                        </div>
                        <ul class="notice-ul" v-show="isKepuShow1">
                            <li v-for="item in leibie" v-if="item.leibie==='科普'" class="notice-li">
                                <template v-if="item.column_type==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.information_detail_id,'book_type':'knowledge' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.column_type!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/knowledgeDetail', query: { 'id': item.information_detail_id }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                        <ul class="notice-ul" v-show="! isKepuShow1">
                            <li v-for="item in leibie" v-if="item.leibie==='论文'" class="noticce-li">
                                <template v-if="item.column_type==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.information_detail_id,'book_type':'knowledge' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.column_type!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/knowledgeDetail', query: { 'id': item.information_detail_id }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                    </div>
                    </Col>
                    <Col span="8">
                    <div class="notice notice-h-xs">
                        <div class="notice-title">
                            <span @click="isKepuShow2 = true" :class="{active: !!isKepuShow2}">图书</span> &nbsp;| <span
                                @click="isKepuShow2 = false" :class="{active: !isKepuShow2}">科技成果</span>
                        </div>
                        <ul class="notice-ul" v-show="isKepuShow2">
                            <li v-for="item in leibie" v-if="item.leibie==='图书'" class="notice-li">
                                <template v-if="item.column_type==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.information_detail_id,'book_type':'knowledge' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.column_type!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/knowledgeDetail', query: { 'id': item.information_detail_id }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                        <ul class="notice-ul" v-show="!isKepuShow2">
                            <li class="notice-li" v-for="item in leibie" v-if="item.leibie==='科技成果'">
                                <template v-if="item.column_type==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.information_detail_id,'book_type':'knowledge' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.column_type!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/knowledgeDetail', query: { 'id': item.information_detail_id }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                    </div>
                    </Col>
                    <Col span="8">
                    <!-- 整个结构全是错的！一个组件就成完成的效果 写一大砣！-->
                    <div class="notice notice-h-xs">
                        <div class="notice-title">
                            <span @click="isShowTab3(1)" :class="{active: isKepuShow3 === 1}">种养标准</span> &nbsp;|
                            <span @click="isShowTab3(2)" :class="{active: isKepuShow3 === 2}">经验交流</span> &nbsp;|
                            <span @click="isShowTab3(3)" :class="{active: isKepuShow3 === 3}">专利</span>
                        </div>
                        <ul class="notice-ul" v-show="isKepuShow3 === 1">
                            <li class="notice-li" v-for="item in leibie" v-if="item.leibie==='种养标准'">
                                <template v-if="item.column_type==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.information_detail_id,'book_type':'knowledge' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.column_type!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/knowledgeDetail', query: { 'id': item.information_detail_id }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                        <ul class="notice-ul" v-show="isKepuShow3 === 2">
                            <li class="notice-li" v-for="item in leibie" v-if="item.leibie==='经验交流'">
                                <template v-if="item.column_type==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.information_detail_id,'book_type':'knowledge' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.column_type!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/knowledgeDetail', query: { 'id': item.information_detail_id }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                        <ul class="notice-ul" v-show="isKepuShow3 === 3">
                            <li class="notice-li" v-for="item in leibie" v-if="item.leibie==='专利'">
                                <template v-if="item.column_type==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.information_detail_id,'book_type':'knowledge' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.column_type!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/knowledgeDetail', query: { 'id': item.information_detail_id }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                    </div>
                    </Col>
                </div>
                <div>
                </div>

            </Row>
            <div class="mt30 mb30">
            </div>
            <Row class="mb30">
                <mall-new-title text="法律法规" more background="#F7F7F7"/>
                <div style="margin-top:15px">
                    <Col span="8">
                    <div class="notice notice-h-xs">
                        <div class="notice-title">
                            <span>通知公告</span>
                            <router-link :to="{ path: '/51index/policyList', query: { 'id': '030203' }}" class="n-more"> 更多
                                >>
                            </router-link>
                        </div>
                        <ul class="notice-ul">
                            <li v-for="item in notice" class="notice-li">
                                <template v-if="item.columnType==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.informationDetailId,'book_type':'policy' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.columnType!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/policyDetail', query: { 'id': item.informationDetailId }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                    </div>
                    <div class="notice notice-h-l">
                        <div class="notice-title">
                            <span>政策法规</span>
                            <router-link :to="{ path: '/51index/policyList', query: { 'id': '030201' }}" class="n-more"> 更多
                                >>
                            </router-link>
                        </div>
                        <ul class="notice-ul">
                            <li v-for="item in regulations" class="notice-li">
                                <template v-if="item.columnType==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.informationDetailId,'book_type':'policy' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.columnType!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/policyDetail', query: { 'id': item.informationDetailId }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                    </div>
                    </Col>
                    <Col span="8">
                    <div class="notice notice-h-xs">
                        <div class="notice-title">
                            <span>党务动态</span>
                            <router-link :to="{ path: '/51index/policyList', query: { 'id': '030209' }}" class="n-more"> 更多
                                >>
                            </router-link>
                        </div>
                        <ul class="notice-ul">
                            <li v-for="item in partyworkdynamic" class="notice-li">
                                <template v-if="item.columnType==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.informationDetailId,'book_type':'policy' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.columnType!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/policyDetail', query: { 'id': item.informationDetailId }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                    </div>
                    <div class="notice notice-h-xs">
                        <div class="notice-title">
                            <span>政务动态</span>
                            <router-link :to="{ path: '/51index/policyList', query: { 'id': '030210' }}" class="n-more"> 更多
                                >>
                            </router-link>
                        </div>
                        <ul class="notice-ul">
                            <li v-for="item in groverworkdynamic" class="notice-li">
                                <template v-if="item.columnType==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.informationDetailId,'book_type':'policy' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.columnType!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/policyDetail', query: { 'id': item.informationDetailId }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                    </div>
                    <div class="notice notice-h-xs">
                        <div class="notice-title">
                            <span>政策解读</span>
                            <router-link :to="{ path: '/51index/policyList', query: { 'id': '030202' }}" class="n-more"> 更多
                                >>
                            </router-link>
                        </div>
                        <ul class="notice-ul">
                            <li v-for="item in interpretation" class="notice-li">
                                <template v-if="item.columnType==='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/bookBlurb', query: { 'id': item.id,'informationDetailId':item.informationDetailId,'book_type':'policy' }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                                <template v-if="item.columnType!='图书'">
                                    <router-link
                                            :to="{ path: '/InforMation/policyDetail', query: { 'id': item.informationDetailId }}"
                                            class="tit ell"> {{ item.title }}
                                    </router-link>
                                </template>
                            </li>
                        </ul>
                    </div>
                    </Col>
                    <Col span="8" class="biaozhun">
                    <div class="clear pt10 pb10">
                        <span class="biaozhun_title">标准</span>
                        <a :href="standardLink" class="fr mt5 more">更多 >></a>
                    </div>
                    <template v-for="(item,index) in standardList">
                        <Card class="standard" :padding="10" style="font-size: 12px"
                              @click.native="goToDetail(item.standardDetailId)">
                            <Row>
                                <Col span="18" :title="item.standardNumber">
                                <span>{{ item.standardNumber }}</span>
                                <span class="t-orange ml5">{{ item.standardTrait }} </span>
                                <span class="t-green ml5" :class="{'t-grey': item.standardStatus === '即将实施'}">{{ item.standardStatus }} </span>
                                </Col>
                                <Col span="6">
                                <p class="tr t-grey">{{moment(item.createTime).format('YYYY-MM-DD')}}</p>
                                </Col>
                            </Row>
                            <Row class="mt5">
                                <Col span="24">
                                <p>{{ item.chineseStandardName }}</p>
                                </Col>
                            </Row>
                        </Card>
                    </template>
                    </Col>
                </div>


            </Row>
        </section>
        <foot></foot>
    </div>
</template>
<script>
    import api from '~api';
    import $ from 'jquery';
    import top from '../../top';
    import headNav from '../pro/components/headNav'
    import minNav from '../goods/components/min-nav'
    import foot from '../../foot';
    import mallNewTitle from '~components/mallNewTitle';
    import mallSearch from '~components/mallSearch';
    import slick from 'vue-slick';
    import 'slick-carousel/slick/slick.css';
    import {
      findStandardMixin,
      fetchDataMixin,
      showledMixin,
      showImgMixin,
      showInfoMixin,
      getRecommendMixin,
      getMostReadListMixin
    } from '../../service/mall/indexMixins';
    export default {
        components: {
            top,
            headNav,
            minNav,
            foot,
            mallNewTitle,
            mallSearch,
            slick
        },
        mixins: [findStandardMixin,fetchDataMixin,showledMixin,showImgMixin,showInfoMixin,getRecommendMixin, getMostReadListMixin
        ],
        data() {
            return {
                isKepuShow1: true,
                isKepuShow2: true,
                isKepuShow3: 1,
                policyLink: '/51index/policyList',
                standardLink: '/51index/standardList',
                knowLedgeLink: '/51index/knowledgeList',
                activeListA: 0,
                productList: '',
                navList: [
                {
                    url: '/51index',
                    text: '首页'
                }, {
                    url: '/51index/inforMationList',
                    text: '资讯'
                }, {
                    url: '/51index/policyList',
                    text: '政策'
                }, {
                    url: '/51index/knowledgeList',
                    text: '知识'
                }, {
                    url: '/pro/productList',
                    text: '产品'
                }, {
                    url: '/pro/serviceList',
                    text: '服务'
                }, {
                    url: '/51index/standardList',
                    text: '标准'
                }, {
                    url: '/pro/enterpriseList',
                    text: '企业'
                }, {
                    url: '/pro/departmentList',
                    text: '机关'
                }, {
                    url: '/pro/expertList',
                    text: '专家'
                }
            ],
                search: {
                    value: '',
                    loading: false,
                    defOpt: [],
                    hotTag: [{
                        text: '苹果',
                        url: 'javascript:;'
                    }, {
                        text: '香蕉',
                        url: 'javascript:;'
                    }, {
                        text: '梨子',
                        url: 'javascript:;'
                    }, {
                        text: '西瓜',
                        url: 'javascript:;'
                    }, {
                        text: '葡萄',
                        url: 'javascript:;'
                    }, {
                        text: '哈密瓜',
                        url: 'javascript:;'
                    }],
                    filterOpt: [
                        {label: '蔬菜', value: 10},
                        {label: '苹果', value: 20},
                        {label: '香蕉', value: 30},
                        {label: '梨子', value: 40},
                        {label: '西瓜', value: 50}
                    ]
                },
                hotClassIfy: {
                    value: '',
                    loading: false,
                    list: [
                        {label: '有机肥设备', value: 1},
                        {label: '有机肥发孝剂', value: 2},
                        {label: '有机肥生物', value: 3},
                        {label: '有机肥原料', value: 4},
                        {label: '有机肥设备', value: 5},
                        {label: '有机肥发孝剂', value: 6},
                        {label: '有机肥生物', value: 7},
                        {label: '有机肥原料', value: 8},
                        {label: '有机肥设备', value: 9},
                        {label: '有机肥发孝剂', value: 10},
                        {label: '有机肥生物', value: 11},
                        {label: '有机肥原料', value: 12},
                        {label: '有机肥设备', value: 13},
                        {label: '有机肥发孝剂', value: 14},
                        {label: '生物', value: 15}
                    ]
                },
                hotClassifyShow: false,
                serveClassifyData:
                    ['科研服务',
                        '信息服务',
                        '咨询服务',
                        '环境管理',
                        '专业技术',
                        '法律服务',
                        '金融服务',
                        '工民建设',
                        '农林生产',
                        '畜牧生产',
                        '渔业生产',
                        '仓储服务',
                        '加工服务',
                        '包装服务',
                        '运输服务',
                        '营售服务',
                        '质检技术',
                        '标准认证',
                        '维修服务',
                        '清洁服务',
                        '旅行服务',
                        '住宿服务',
                        '餐饮服务',
                        '教育服务',
                        '卫生服务',
                        '影视服务',
                        '文化艺术'],
                currentPage: 1,
                pageSize: 6,
                total: 0,
                info1: [],
                info2: [],
                leibie: [],
                carousel: {
                    value: 0,
                    dots: 'outside',
                    autoplay: true,
                    arrow: 'never',
                    list: [],
                    lists: []
                },
                carousel2: {
                    value: 0,
                    dots: 'outside',
                    autoplay: true,
                    arrow: 'never',
                    list: [],
                    lists: []
                },
                agroList1: [
                    {title: '习近平同金正恩会谈 推动中朝关系长期稳定发展', url: '/InforMation/policyDetail?id=1443'},
                    {title: '习近平同金正恩举行会谈', url: '/InforMation/policyDetail?id=1446'},
                    {title: '“一带一路”农业合作提供更多创新机会', url: '/InforMation/policyDetail?id=1447'},
                    {title: '李克强会见美国国会议员访华团', url: '/InforMation/policyDetail?id=1445'},
                    {title: '韩长赋:用习近平总书记“三农”思想指导乡村振兴', url: '/InforMation/policyDetail?id=1444'},
                    {title: '农业农村部:深入推进四大作物和特色作物良种联合攻关', url: '/InforMation/policyDetail?id=1448'},
                    {title: '【种业十大杰出人物】朱英国：“与苍生同梦”', url: '/InforMation/policyDetail?id=1449'},
                    {title: '国办:严格土地用途管制 从严控制建设占用耕地', url: '/InforMation/policyDetail?id=1450'},
                    {title: '当前春耕备耕热潮掀起进展顺利 呈现五大特点', url: '/InforMation/policyDetail?id=1451'},
                    {title: '袁隆平国际杂交水稻种业硅谷在成都开工建设', url: '/InforMation/policyDetail?id=1452'},
                ],
                agroList: [],
                readList: [{
                    title: '上周发生了哪些农业大事？7月第2期',
                    clickRate: 11314,
                    url: 'javascript:;'
                }, {
                    title: '江苏大丰区农委加大农业结构调整服务推进力度',
                    clickRate: 10900,
                    url: 'javascript:;'
                }, {
                    title: '公司注册三个商标,法人电话被骚扰不断,个人信息还.....',
                    clickRate: 3967,
                    url: 'javascript:;'
                }, {
                    title: '武汉：本周蔬菜价格基本稳定',
                    clickRate: 2234,
                    url: 'javascript:;'
                }, {
                    title: '安徽：2017年实现全省农村电商县域全覆盖',
                    clickRate: 1178,
                    url: 'javascript:;'
                }, {
                    title: '专家答疑解惑：农民所不了解的微生物肥料',
                    clickRate: 600,
                    url: 'javascript:;'
                }, {
                    title: '“人可以喝的农药”实现农业生产与生态环境的平衡',
                    clickRate: 400,
                    url: 'javascript:;'
                }, {
                    title: '农业部发布控制小麦赤霉病危害技术指导意见',
                    clickRate: 300,
                    url: 'javascript:;'
                }, {
                    title: '江苏大丰区农委加大农业结构调整服务推进力度',
                    clickRate: 200,
                    url: 'javascript:;'
                }, {
                    title: '2017年7月下旬杀菌剂原药价格行情',
                    clickRate: 100,
                    url: 'javascript:;'
                }],
                minNav: [],
                recomList: [],
                minRecomList: [],
                organizList: [{
                    title: '沂水县起伟果蔬种植专业合作社',
                    url: 'javascript:;'
                }, {
                    title: '沂水县起伟果蔬种植专业合作社',
                    url: 'javascript:;'
                }, {
                    title: '沂水县起伟果蔬种植专业合作社',
                    url: 'javascript:;'
                }, {
                    title: '沂水县起伟果蔬种植专业合作社',
                    url: 'javascript:;'
                }, {
                    title: '沂水县起伟果蔬种植专业合作社',
                    url: 'javascript:;'
                }, {
                    title: '沂水县起伟果蔬种植专业合作社',
                    url: 'javascript:;'
                }, {
                    title: '沂水县起伟果蔬种植专业合作社',
                    url: 'javascript:;'
                }, {
                    title: '沂水县起伟果蔬种植专业合作社',
                    url: 'javascript:;'
                }, {
                    title: '沂水县起伟果蔬种植专业合作社',
                    url: 'javascript:;'
                }, {
                    title: '沂水县起伟果蔬种植专业合作社',
                    url: 'javascript:;'
                }],
                slickList: [{
                    src: '../static/datas/img/kaocha01.png',
                    url: 'javascript:;'
                }, {
                    src: '../static/datas/img/kaocha02.png',
                    url: 'javascript:;'
                }, {
                    src: '../static/datas/img/kaocha03.png',
                    url: 'javascript:;'
                }, {
                    src: '../static/datas/img/kaocha04.png',
                    url: 'javascript:;'
                }, {
                    src: '../static/datas/img/kaocha05.png',
                    url: 'javascript:;'
                }, {
                    src: '../static/datas/img/kaocha06.png',
                    url: 'javascript:;'
                }, {
                    src: '../static/datas/img/kaocha01.png',
                    url: 'javascript:;'
                }, {
                    src: '../static/datas/img/kaocha02.png',
                    url: 'javascript:;'
                }, {
                    src: '../static/datas/img/kaocha03.png',
                    url: 'javascript:;'
                }, {
                    src: '../static/datas/img/kaocha04.png',
                    url: 'javascript:;'
                }],
                slickOptions: {
                    slidesToShow: 6,
                    autoplay: true,
                    slidesToScroll: 1,
                    autoplaySpeed: 1000,
                    arrows: false
                },
                loreList: [{
                    title: '云南广南：推进精准扶贫工作，入股资金近30',
                    src: 'javascript:;'
                }, {
                    title: '江苏大丰区农委加大农业结构调整服务推进力度',
                    src: 'javascript:;'
                }, {
                    title: '小马奔腾陨落记：人才出走竞争削弱加速衰落',
                    src: 'javascript:;'
                }, {
                    title: '一位山羊奶肥皂创业者分享的5条创业经验',
                    src: 'javascript:;'
                }, {
                    title: '一位山羊奶肥皂创业者分享的5条创业经验',
                    src: 'javascript:;'
                }],
                regulations: [],
                interpretation: [],
                notice: [],
                partyworkdynamic: [],
                groverworkdynamic: [],
                standardList: [],
                mostReadList: []
            };
        },
        created() {
            this.getMostReadList();
            this.fetchData();
            this.showInfo('020101');
            this.showled();
            this.getRecommend(2);
            this.showImg(1);
            this.showImg(2);
            this.findStandard();
            // 获取产品推荐的 内容
            this.handleGetProduct()
        },
        methods: {
             // 到详情页
            handleDetail (item) {
                this.$router.push(`/goods/detail?id=${item.id}&account=${item.account}`)
            },
            // 获取产品推荐的 内容
            handleGetProduct () {
                // this.recomList
                 // default :1 默认排序。timePriceFlag: 0 价格正序 1价格倒序 ,name 关键字
                let list ={
                    pageSize: 8,
                    pageNum: 1,
                    isHomeDisplay: 1,
                    default: 1
                }
                this.$api.post('/portal/shopCommdoity/findShopCommodityList',list).then(response => {
                    console.log('response产品数据', response)
                    if (response.code == 200) {
                        this.recomList = response.data.list
                    }
                })
            },
            isShowTab3(flag) {
                if (flag === 1) {
                    this.isKepuShow3 = 1;
                } else if (flag === 2) {
                    this.isKepuShow3 = 2;
                } else {
                    this.isKepuShow3 = 3;
                }
                // e.target.style.color  = '#00c587';
            },
            openClassifySearch() {
                this.hotClassifyShow = true;
            },
            closeClassifySearch() {
                this.hotClassifyShow = false;
            },
            goToDetail(id) {
                this.$router.push({
                    path: '/inforMation/standardDetail',
                    query: {
                        id: id,
                        status: 2
                    }
                });
            }
        }
    };
</script>
<style lang="scss">
    .serve-classify .list a {
        color: #4A4A4A
    }

    /*notice-style start*/
    .notice {
        width: 400px;
        margin-top: 4.5px;
        margin-bottom: 4.5px
    }

    .notice-h-xs {
        height: 205px;
    }

    .notice-h-l {
        height: 407px;
    }

    .notice .n-more {
        float: right;
        margin-right: 17px;
        font-size: 12px;
        height: 34px;
        line-height: 34px;
        height: 34px;
        line-height: 34px;
        color: #9B9B9B;
    }

    .notice .notice-title span {
        font-size: 15px;
        font-weight: 600;
        color: #4A4A4A;
        height: 34px;
        line-height: 34px;
        margin-left: 12px;
        margin-top: 11px;
        padding-bottom: 6px;
        cursor: pointer;
        &.active {
            color: #00c587;
        }

    }

    .notice ul li {
        margin-left: 13px;
        height: 24px;
        line-height: 24px;
        margin-top: 6px;
        margin-bottom: 6px;
        font-size: 14px;
        color: #4A4A4A;
        width: 352px;
        overflow: hidden;
    }

    .notice ul li a {
        color: #4A4A4A;
    }

    .notice ul li a:hover {
        color: #00c587;
    }

    /*notice-style start*/

    /*biaozhu-style start*/
    .biaozhun_title {
        font-family: PingFangSC-Semibold;
        font-size: 16px;
        color: #4A4A4A;
        height: 32px;
        line-height: 32px;
        margin-left: 14px;
        margin-top: 24px
    }

    .more {
        margin-right: 13px;
        font-size: 12px;
        color: #B4B4B4;
    }

    .biaozhun {
        background: #FFFFFF;
        border: 1px solid #EEEEEE;
        width: 400px;
        height: 660px;
    }

    .standard {
        margin: 5px;
        cursor: pointer;
    }
</style>
