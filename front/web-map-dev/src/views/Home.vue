
<style  lang="less" scoped>
.home-index{
  height:  calc(100vh - 95px);
}
.ol-popup{
  background: #ffffff;
  height: 100px;
  width: 100px;
}
.ol-popup:after, .ol-popup:before {  
    top: 100%;  
    border: solid transparent;  
    content: " ";  
    height: 0;  
    width: 0;  
    position: absolute;  
    pointer-events: none;  
}  
.ol-popup:after {  
    border-top-color: white;  
    border-width: 10px;  
    left: 48px;  
    margin-left: -10px;  
}  
.ol-popup:before {  
    border-top-color: #cccccc;  
    border-width: 11px;  
    left: 48px;  
    margin-left: -11px;  
}
.ol-popup {  
    position: absolute;  
    background-color: white;  
    -webkit-filter: drop-shadow(0 1px 4px rgba(0,0,0,0.2));  
    filter: drop-shadow(0 1px 4px rgba(0,0,0,0.2));  
    /* padding: 15px; */
    border-radius: 2px;  
    /* border: 1px solid #cccccc;   */
    bottom: 12px;  
    left: -50px;  
    // max-width: 300px;
    width: 300px;
    height: 100px;

    // width: 400px;
    // height: 300px;
    /* display: none; */
}
</style>

<template>
<div>
  <!-- <Affix>
    <top></top>
  </Affix> -->
  <div class="home-index " ref="rootmap" id="map">
    <leftBar
      ref="leftBar" 
      :data="data"
      :edit="edit"
      @on-click="checkedList"
      @on-search="leftSearch"
      @on-change="leftBarChange" ></leftBar>
    <rightBar
      v-if="showRight && edit"
      ref="bar"
      :data="data"
      @on-cancel="featuresBlanking"
      @on-change="barChange"
      @on-add="handleAdd"
      @on-edit="toolEdit"
      @on-del="handleDel"
      @on-save="handleSave"></rightBar>
      <!-- 点击标注 的点 或者面 给的提示 -->
      <div id="popup" ref="popup" class="ol-popup">
        <popup ref="popups" @on-close="close"></popup>
      </div>
  </div>
</div>
</template>

<script>
// import top from '@/components/top'
import $ from "jquery"
import rightBar from '@/components/Action-bar'
import leftBar from '@/components/left-bar'
import popup from '@/components/Action-bar/popup';

import { unByKey } from "ol/Observable";
import { Map, View } from "ol";
import Overlay from "ol/Overlay";
import Projection from "ol/proj/Projection";

import Image from "ol/layer/Image";
import TileLayer from "ol/layer/Tile";
import VectorLayer from 'ol/layer/Vector';
import { toStringHDMS } from 'ol/coordinate';
import { toLonLat } from 'ol/proj';

import ImageWMS from "ol/source/ImageWMS";
import OSM from "ol/source/OSM";
import VectorSource from 'ol/source/Vector';
import WMTSSource from 'ol/source/WMTS';
import TileArcGISRest from 'ol/source/TileArcGISRest';
import WMTSTilegrid from 'ol/tilegrid/WMTS';

import WKT from 'ol/format/WKT';
import GeoJSON from 'ol/format/GeoJSON';
import WMTSCapabilities from 'ol/format/WMTSCapabilities';

import {Circle as CircleStyle, Fill, Stroke, Style} from 'ol/style';

import { Draw } from "ol/interaction";
import Select from "ol/interaction/Select";
import Modify from "ol/interaction/Modify";
import { constants } from 'crypto';

import { selectData } from './Mixin';
import axios from 'axios'
import { debuglog } from 'util';


export default {
  name: 'home-index',
  mixins: [selectData],
  props: {
    edit: {
      type: Boolean,
      default: false
    }
  },
  components: {
    // top,
    rightBar,
    leftBar,
    popup
  },
  data () {
    return {
      showRight: false,
      html: '', // 提示的数据
      overlay: null, // 弹出框
      barSelectList: {}, // 左侧选中的下拉数据
      activeVectorLayer: null, // 编辑或者新增或者删除的时候画的图层
      map: null, // map
      OnlineEditingList:{ // 在线编辑
        DrawForm: {
            draw: null,
            source: new VectorSource()
        },
        features: [], // 存 的当前操作的 元素数据 保存时候 将这个数据传给后台
        isMove: false // 判断当前操作的元素是否移动过
      },
      tool_edit: { // 绘图工具 定义修改几何图形的功能控件
        Modify: null,
        select: null
      },
      type: '', // 当前的操作类型 新增 Insert 修改  Update 删除 Delete
      bbox: { // 图层的 坐标系 用于存储所有图层的 坐标系 根据坐标系来定位
        minx: [],
        miny: [],
        maxx: [],
        maxy: []
      },
      detailFlag: true, // 防止多次点击 点击一次后设置为 true ，取到值之后 更改为false
      portrait: {},
      // 年度文件夹
      files: [],
      // 当前年度文件夹的id
      yearId: '',
      addType: '', // 点击添加 的时候 存添加的类型 在保存时 根据这个判断 使用 layerName
      keyWord:'' // 数据展示页面 关键字搜索
    }
  },
  created () {
    // 初始化查询 年度文件夹 获取年度id
    this.$api.post('/member-reversion/perfect/findYearInfo', {
      account: this.$user.loginAccount
    }).then(response => {
      if (response.code === 200) {
        response.data.forEach(element => {
          if (element.fileName.substring(0, 4) === new Date().getFullYear().toString()) {
            this.yearId = element.id
            return
          } else {
            this.files.push({
              name: element.fileName,
              id: element.id,
              year: element.fileName.substring(0, 4)
            })
          }
        })
        if (!this.yearId) { // 没有当年的 则计算出与当年最接近的
          let currentYear = Number(new Date().getFullYear().toString())
          let oldValue = null
          this.files.forEach((element, i) => {
            let newValue = Math.abs(Number(element.year) - currentYear)
            if (oldValue === null) {
              oldValue = Math.abs(Number(element.year) - currentYear)
              this.yearId = element.year
            } else {
              if (newValue < oldValue) {
                oldValue = newValue
                this.yearId = element.year
              }
            }
          })
        }
      }
    }).catch(error => {
    })
  },
  mounted () {
    this.initAll()
    this.initToolEdit()
  },
  methods: {
    getLeftSelectBarLiat (list) {
      let selectList = null
      if (list.parent) {
        // 选了一级
        if (list.children) {
          // 选了二级
          this.$refs['leftBar'].getChildrenData()
          selectList = this.$refs['leftBar'].childrenData
        } else {
          this.$refs['leftBar'].getFilterData()
          selectList = this.$refs['leftBar'].filterData
        }
      }
      return selectList
    },
    // 左边搜索
    leftSearch (list) {
      this.close()
      let selectList = this.getLeftSelectBarLiat(list) ? this.getLeftSelectBarLiat(list) : {}
      let filterWord = ''
      if (list.parent == '01') { // 风景名胜
        filterWord = list.SFMF
      } else if (list.parent == 'businessOutlets') {
        filterWord = list.WDLX
      }
      let keyword = list.keyword
      /** 
      1.如果是 有要过滤的，那么看过滤条件有没有 有 就 移除当前图层 重新添加上 
        并在加上之后给 this.data 中添加一个标识
        没有 就判断之前有没有标识，有标识，也要重新移除后添加
      */
      // 获取全部图层 与当前选择图层做对比 让选中图层显示 其他图层隐藏 当前没有选中图层 则全部显示
      var updateList = []
      var get_removeLayers = this.map.getLayers();
      var get_removeLayer = $.extend(true,{}, get_removeLayers); // 拷贝数据 改变此数据源数据不变
      for(var key in get_removeLayer.array_){
        if (key == 0 || key == 1 || key == 2 || key == 3) { // 影像跟 高亮不隐藏
          get_removeLayer.array_[key].setVisible(true);  //通过控制wfsLayer图层的隐藏
        } else {
          if (selectList.list && selectList.list.length) {
            let flag = false
            selectList.list.forEach(element => {
              // key == 0 影像底图 key == 1 以及边框高亮 不隐藏
              if(get_removeLayer.array_[key].ol_uid == element.ol_uid){
                // false 隐藏 true 显示 有一次为true则每次都为true
                flag = true
                get_removeLayer.array_[key].setVisible(true);  //通过控制wfsLayer图层的隐藏
                this.filterLayers(this.data, filterWord, element.ol_uid, get_removeLayer.array_[key], keyword)
              } else if (!flag) {
                get_removeLayer.array_[key].setVisible(false);  //通过控制wfsLayer图层的隐藏
              }
            })
          } else if (selectList.children && selectList.children.length) {
            let flag = false
            selectList.children.forEach(e => {
              e.list.forEach(element => {
                // key == 0 影像底图 key == 1 以及边框高亮 不隐藏
                if(get_removeLayer.array_[key].ol_uid == element.ol_uid){
                  // false 隐藏 true 显示 有一次为true则每次都为true
                  flag = true
                  get_removeLayer.array_[key].setVisible(true);  //通过控制wfsLayer图层的隐藏
                  this.filterLayers(this.data, filterWord, element.ol_uid, get_removeLayer.array_[key], keyword)
                } else if (!flag) {
                  get_removeLayer.array_[key].setVisible(false);  //通过控制wfsLayer图层的隐藏
                }
              })
            })
          } else {
            get_removeLayer.array_[key].setVisible(true);  //通过控制wfsLayer图层的隐藏
            this.fiterDataList(this.data, get_removeLayer.array_[key], '', keyword)
          }
        }
      }
      this.keyWord = list.keyword
    },
    /**
     * 1、刚开始 查询全部图层 不过滤
     * 2、选择了 图层 不过滤 （判断之前有没有过滤，有就删除 重新添加，没有就正常隐藏或者显示）
     * 3、选择了 图层 过滤  
     * （判断之前有没有过滤，没有就删除 重新添加，
     * 有要判断过滤条件是否一样，一样就正常隐藏或者显示，不一样就删除 重新添加）
     */
    // 没有筛选条件 显示所有图层时调用
    fiterDataList (data, layer, filterWord, keyword) {
      data.forEach(dataList => {
        if (dataList.filter || this.keyWord != keyword) { // 有过滤条件的 或者是要查询关键字的
          if (dataList.list && dataList.list.length) {
            dataList.list.forEach(list => {
              if (list.ol_uid == layer.ol_uid) {
                if ((filterWord && !list.isFilter) || (!filterWord && list.isFilter) || (list.isFilter && list.filterWord != filterWord) || this.keyWord != keyword) {
                  this.map.removeLayer(layer)
                  this.addFiterLayer(dataList, list, dataList.filter, filterWord, keyword)
                }
              }
            })
          } else if (dataList.children && dataList.children.length) {
            this.fiterDataList(dataList.children, layer, filterWord, keyword)
          }
        }
      })
    },
    addFiterLayer (list, element, filterKey, filterWord, keyword) {
        let account = this.edit ? this.$user.loginAccount : ''
        let SLD_BODY = this.getBody(element.layerName, account, list.showLabel[0], filterKey, filterWord, list.keyword, keyword)
        let wmsParam = {
          'LAYERS': element.layerName, // 此处可以是单个图层名称，也可以是图层组名称，或多个图层名称
          'VERSION': "1.1.1",
          'STYLES': ``,
          'REQUEST': 'GetMap',
          'SRS': "EPSG:4490",
          'SLD_BODY':SLD_BODY
        }
        let layers = new Image({
            source: new ImageWMS({
                url: `${this.$config.baseUrl.mapapi}${element.url}`,
                params: wmsParam,
                ratio: 1,
                // opacity: '0.3',
                serverType: 'geoserver',
                projection: 'EPSG:4490'
            })
        })
        element.ol_uid = layers.ol_uid
        if (filterWord) { // 查部分
          element.isFilter = true
          element.filterWord = filterWord
        } else { // 查全部
          element.isFilter = false
          element.filterWord = ''
        }
        this.map.addLayer(layers)
    },
    // 有选择图层时 调用 传入当前图层 olid 调用
    filterLayers (data, filterWord, id, layer, keyword) {
      data.forEach(element => {
        if (element.list && element.list.length) {
          element.list.forEach(e => {
            if (e.ol_uid === id) {
              this.map.removeLayer(layer)
              this.addFiterLayer(element, e, element.filter, filterWord, keyword)
            }
          })
        } else if (element.children && element.children.length) {
          this.filterLayers(element.children, filterWord, id, layer, keyword)
        }
      })
    },
    // 根据左边的下拉显示显示定位并显示不同的地方
    leftBarChange (list) {
      // 清除之前的 图层
      var layers = this.map.getLayers();
      var copy_layers = $.extend(true,{}, layers); // 拷贝数据 改变此数据源数据不变
      if(copy_layers.array_.length > 0){ // 删除绘制
        for(var key in copy_layers.array_){
          if (key != 0 && key != 1) {
            for(var twoKey in layers.array_){
              if(copy_layers.array_[key].ol_uid == layers.array_[twoKey].ol_uid){
                this.map.removeLayer(layers.array_[twoKey])
              }
            }
          }
        }
      }
      // 将右侧的下拉 数据带过来
      this.portrait = Object.assign({}, list)
      // 显示右侧 窗口
      this.showRight = true
      this.initImage()
    },
    // 初始化
    initAll () {
      this.overlay = new Overlay({
          // 设置弹出框的容器
          element: this.$refs['popup'],
          // 是否自动平移，即假如标记在屏幕边缘，弹出时自动平移地图使弹出框完全可见
          autoPan: true,
          autoPanAnimation : {
            duration : 250
            //当Popup超出地图边界时，为了Popup全部可见，地图移动的速度. 
          }
      })
      // 初始化地图
      this.map = new Map({
        view: new View({
            center: [110.14, 32.57],
            zoom: 9,
            projection: new Projection({
              code: "EPSG:4490",
              units: 'm',
            }),
            maxResolution: 1.40625 * 2 * 2,
            minZoom: 0,
            maxZoom: 20
        }),
        overlays: [this.overlay],
        layers: [],
        target: 'map'
      })
      var arcgisTileLayer_DT = new TileLayer({
        source: new TileArcGISRest({
          // IMG_HB_7_17 卫星图
          url: 'http://www.tiandituhubei.com:6080/arcgis/rest/services/IMG_ZY3_HB/MapServer'
          // url: 'http://www.tiandituhubei.com:6080/arcgis/rest/services/IMG_ZY3_HB/MapServer/tile/14/5372/26611'
        })
      })
      this.map.getLayers().insertAt(1,arcgisTileLayer_DT)
      var arcgisTileLayer_ZJ = new TileLayer({
        source: new TileArcGISRest({
          // url: 'http://www.tiandituhubei.com:6080/arcgis/rest/services/SLZJ_L7_L20_HB/MapServer'
          url: 'http://www.tiandituhubei.com:6080/arcgis/rest/services/IDT_L7_L20_HB/MapServer'
        })
      })
      this.map.getLayers().insertAt(2,arcgisTileLayer_ZJ)
      this.map.getView().fit([108,29,116,33], {duration: 250});

      // this.map.getView().fit([346874.458569343,3319404.1069781, 350595.687591241, 3321933.70648175], {duration: 250});

      // 添加点击事件
      this.map.on('singleclick', evt => {
        // 如果 有要显示的label 并且不是操作状态（删除、新增、编辑）下才执行 每次操作完成保存后 this.type会为 ''
        if (!this.type) {
          this.mapEdit_Click(evt, this.overlay)
        }
      })
    },
    // 过滤 数组 取children 为空 有url的数据 的 bbox 获取到其坐标
    filtersBox (data) {
      data.forEach(e => {
        if (e.children && e.children.length) {
          this.filtersBox(e.children)
        } else {
          if (e.url) {
            let bbox = e.bbox.split(',')
            this.bbox.minx.push(bbox[0])
            this.bbox.miny.push(bbox[1])
            this.bbox.maxx.push(bbox[2])
            this.bbox.maxy.push(bbox[3])
          }
        }
      })
    },
    // 定位 根据所有图层的 坐标系 来定位 将所有图层都显示在页面中
    getLoaction (list) {
      let data = [this.portrait]
      this.filtersBox(data)
      if(this.bbox.minx.length > 0 && this.bbox.miny.length > 0 && this.bbox.maxx.length > 0 && this.bbox.maxy.length > 0){
          var full_minx = Math.min.apply(null, this.bbox.minx);
          var full_miny = Math.min.apply(null, this.bbox.miny);
          var full_maxx = Math.max.apply(null, this.bbox.maxx);
          var full_maxy = Math.max.apply(null, this.bbox.maxy);
          var full_bbox = [full_minx , full_miny , full_maxx , full_maxy];
          this.map.getView().fit(full_bbox, {duration: 250});
      }
    },
    // 过滤 数组 将有children 为空 有url的数据 添加到图层中
    filtersUrl (data) {
      data.forEach(e => {
        if (e.children && e.children.length) {
          this.filtersUrl(e.children)
        } else {
          if (e.list && e.list.length) {
            e.list.forEach((element, index) => {
              let account = this.edit ? this.$user.loginAccount : ''
              let SLD_BODY = this.getBody(element.layerName, account, e.showLabel[0])
              let wmsParam = {
                'LAYERS': element.layerName, // 此处可以是单个图层名称，也可以是图层组名称，或多个图层名称
                'VERSION': "1.1.1",
                // 'STYLES': `${element.layerName}.sld`,
                'STYLES': ``,
                'REQUEST': 'GetMap',
                'SRS': "EPSG:4490",
                'SLD_BODY':SLD_BODY
              }
              let layers = new Image({
                  source: new ImageWMS({
                      url: `${this.$config.baseUrl.mapapi}${element.url}`,
                      params: wmsParam,
                      ratio: 1,
                      // opacity: '0.3',
                      serverType: 'geoserver',
                      projection: 'EPSG:4490'
                  })
              })
              // 获取到图层的 ol_uid 给下拉列表绑定上 当下拉列表改变的时候，显示选中的图层 清空时显示全部图层
              element.ol_uid = layers.ol_uid
              this.map.addLayer(layers)
            })
          }
        }
      })
    },
    // 添加图层
    addMap(id, type) {
      let e = this.barSelectList
      e.list.forEach((element, index) => {
        console.log('循环')
        if (element.ol_uid === id) {
        console.log('循环，有一样的')
          let account = this.edit ? this.$user.loginAccount : ''
          let SLD_BODY = this.getBody(element.layerName, account, e.showLabel[0])
          let wmsParam = {
            'LAYERS': element.layerName, // 此处可以是单个图层名称，也可以是图层组名称，或多个图层名称
            'VERSION': "1.1.1",
            'STYLES': ``,
            'REQUEST': 'GetMap',
            'SRS': "EPSG:4490",
            'SLD_BODY':SLD_BODY
          }
          let layers = new Image({
              source: new ImageWMS({
                  url: `${this.$config.baseUrl.mapapi}${element.url}`,
                  params: wmsParam,
                  ratio: 1,
                  // opacity: '0.3',
                  serverType: 'geoserver',
                  projection: 'EPSG:4490'
              })
          })
          // 获取到图层的 ol_uid 给下拉列表绑定上 当下拉列表改变的时候，显示选中的图层 清空时显示全部图层
          if (type === 'Refresh') { // 说明是更新图层 获取到下拉列表中的数据 对 找到当前选择的 重新赋值 ol_uid
            this.RefreshLayer (element, layers, this.data)
          } else {
            e.ol_uid = layers.ol_uid
          }
          this.map.addLayer(layers)
        }
      })
    },
    // 说明是更新图层 获取到下拉列表中的数据 对 找到当前选择的 重新赋值 ol_uid
    RefreshLayer (list, layers, barData) {
      barData.forEach(e => {
        if (e.children && e.children.length) {
          this.RefreshLayer(list, layers, e.children)
        } else {
          if (e.list && e.list.length) {
            let flag = false
            e.list.forEach(element => {
              // 只更新一个当前操作图层
              if (element.ol_uid == list.ol_uid) {
                flag = true
                element.ol_uid = layers.ol_uid
              }
            })
            if (flag) {
              console.log('重新赋值')
              this.barSelectList = e
            }
          }
        }
      })
    },
    // 点击 关闭 提示
    close () {
      this.overlay.setPosition(undefined);
    },
    // tool_edit 初始化控件
    initToolEdit () {
      // 定义修改几何图形的功能控件
      this.tool_edit.Modify = {
          init: () => {
              //初始化一个交互选择控件，并添加到地图容器中
              this.tool_edit.select = new Select();
              this.map.addInteraction(this.tool_edit.select);
              //初始化一个交互编辑控件，并添加到地图容器中
              this.tool_edit.modify = new Modify({
                  features: this.tool_edit.select.getFeatures()           //选中的要素集
              });
              this.map.addInteraction(this.tool_edit.modify);
              //设置激活状态变更的处理
              this.tool_edit.Modify.setEvents();
          },
          setEvents: () => {
              var selectedFeatures = this.tool_edit.select.getFeatures();       //选中的要素集
              // 添加选中要素变更事件
              this.tool_edit.select.on("select", (e) => {
                console.log('eeeeselect', e)
                this.OnlineEditingList.layer_id = null; // 清空存储当前选中的要素的 layer_id
                if (e.selected.length) { // 有 点击 选中的数据
                  let features = this.OnlineEditingList.features
                  for(var key in features){
                    // 选中的数据 对比
                    if((features[key].layer_id == e.selected[0].id_ || features[key].layer_id == e.selected[0].ol_uid) && (features[key].op == "Update" || features[key].op == "Insert")){
                      this.OnlineEditingList.layer_id = features[key].layer_id; // 存储当前选中的要素的 layer_id
                      if(features[key].op == "Insert"){
                        this.$refs['bar'].title = '创建要素'
                        this.OnlineEditingList.isMove = true
                      }else if(features[key].op == "Update"){
                        this.$refs['bar'].title = '编辑要素'
                      }
                      let list = {}
                      for(var twoKey in features[key].properties){ // 当前点击的要素所有值 赋值给 要素字段编辑框
                        list[features[key].properties[twoKey].name] = features[key].properties[twoKey].value
                      }
                      // 计算经纬度
                      let x = (list.x1 * 1000000000000000 + list.x2 * 1000000000000000) / 2000000000000000
                      let y = (list.y1 * 1000000000000000 + list.y2 * 1000000000000000) / 2000000000000000
                      let location = `${x}, ${y}`
                      // 根据经纬度 查询行政区域地址 112.439009,29.996829
                      this.getAddress(location)
                      this.$refs['bar'].handleShowForm(list)
                    }
                  }
                } else {
                }
              })
              // 图形改变事件
              this.tool_edit.modify.on("modifyend", (e) => {
                var features = e.features.array_;
                var value = new WKT().writeFeature(features[0])
                var polygon = features[0].getGeometry().extent_;
                // 计算经纬度
                let x = (polygon[0] * 1000000000000000 + polygon[2] * 1000000000000000) / 2000000000000000
                let y = (polygon[1] * 1000000000000000 + polygon[3] * 1000000000000000) / 2000000000000000
                let location = `${x}, ${y}`
                // 根据经纬度 查询行政区域地址 112.439009,29.996829
                this.getAddress(location)
                // 将改变后的  图形的  位置信息 重新赋值给 features 中的 the_geom
                this.OnlineEditingList.features.forEach(element => {
                  element.properties.forEach(e => {
                    if (e.name == 'the_geom') {
                      e.value = value
                    }
                  })
                })
                this.OnlineEditingList.isMove = true
              });
          },
          setActive: (active) => {
              this.tool_edit.select.setActive(active); // 激活选择要素控件
              this.tool_edit.modify.setActive(active); // 激活修改要素控件
          }
      };
      this.tool_edit.Modify.init(); // 初始化几何图形修改控件
      this.tool_edit.Modify.setActive(false); // 初始化完成默认关闭
    },
    // 根据经纬度 查询行政区域地址
    getAddress (location) {
      let url = `https://restapi.amap.com/v3/geocode/regeo?location=${location}&key=95adbfe8184e519713d2b5c9bcd97019`
      this.$api.ajaxGet(url).then(res => {
        if (res.status == 1) {
          let flag = false
          this.OnlineEditingList.features.forEach(element => {
            element.properties.forEach(e => {
              if (e.name == 'XZQY') {
                flag = true
                e.value = res.regeocode.formatted_address
              }
            })
            if (!flag) {
              element.properties.push({ name: 'XZQY', value: res.regeocode.formatted_address })
            }
          })
        } else {
        }
      })
    },
    // 右侧 下拉改变， 生成对应的新图层
    barChange (selectList) {
      this.featuresBlanking()
      // 获取全部图层 与当前选择图层做对比 让选中图层显示 其他图层隐藏 当前没有选中图层 则全部显示
      this.barSelectList = selectList
      var get_removeLayer = this.map.getLayers();
      for(var key in get_removeLayer.array_){
        if (key == 0 || key == 1 || key == 2 || key == 3) { // 影像跟 高亮不隐藏
          get_removeLayer.array_[key].setVisible(true);  //通过控制wfsLayer图层的隐藏
        } else {
          if (selectList.list && selectList.list.length) {
            let flag = false
            selectList.list.forEach(element => {
              // key == 0 影像底图 key == 1 以及边框高亮 不隐藏
              if(get_removeLayer.array_[key].ol_uid == element.ol_uid){
                // false 隐藏 true 显示 有一次为true则每次都为true
                flag = true
                get_removeLayer.array_[key].setVisible(true);  //通过控制wfsLayer图层的隐藏
              } else if (!flag) {
                get_removeLayer.array_[key].setVisible(false);  //通过控制wfsLayer图层的隐藏
              }
            })
          } else {
            get_removeLayer.array_[key].setVisible(true);  //通过控制wfsLayer图层的隐藏
          }
        }
      }
    },
    // 清除 编辑或者新增画的图层
    featuresBlanking () {
      this.$refs['bar'].showForm = false
      this.close()
      this.addType = ''
      this.type = ''
      if (this.activeVectorLayer) {
        // 删除 刚刚绘制的图
        this.map.removeLayer(this.activeVectorLayer);
        this.activeVectorLayer = null
      }
      if (this.OnlineEditingList.DrawForm.source) {
       this.OnlineEditingList.DrawForm.source.clear(); // 清空绘制的图形
      }
      if (this.OnlineEditingList.DrawForm.draw) { // 清空 即将绘制图形的小蓝点
        this.map.removeInteraction(this.OnlineEditingList.DrawForm.draw)
      }
      // 删除 蓝色边框
      if(this.tool_edit.Modify){
        this.tool_edit.Modify.setActive(false);//关闭几何图形修改控件
        this.tool_edit.select.setMap(null); // 移除选中交互
        var selectedFeatures = this.tool_edit.select.getFeatures();//选中的要素集
        if(selectedFeatures && selectedFeatures.array_ && selectedFeatures.array_.length > 0){
          // 移除已选中数据
          selectedFeatures.remove(selectedFeatures.array_[0])
        }
      }
      // 每次操作都让 features 置空
      this.OnlineEditingList.features = []
    },
    // 点击删除
    handleDel () {
      this.OnlineEditingList.isMove = true
      this.featuresBlanking()
      this.type = 'Delete'
      // this.tool_edit.Modify.setActive(true); // 开启 几何图形 功能控件
      this.tool_edit.select.setMap(this.map); // 选中交互 开启
      // 开启选中要素事件
      this.OnlineEditingList.PointMap = this.map.on('click',this.mapEdit_Click);
    },
    // 点击添加
    handleAdd (type) {
      this.featuresBlanking()
      this.type = 'Insert'
      this.addType = type.type
      this.OnlineEditingList.isMove = true
      this.addInteraction_edit(type)
    },
    // 点击 编辑
    toolEdit () {
      this.OnlineEditingList.isMove = false
      this.featuresBlanking()
      this.type = 'Update'
      this.tool_edit.select.setMap(this.map); // 选中交互 开启
      // 开启选中要素事件
      this.OnlineEditingList.PointMap = this.map.on('click', this.mapEdit_Click);
    },
    // 编辑保存
    handleSave (data) {
      // 遍历当前的 features
      this.OnlineEditingList.features.forEach(element => {
        // 当前操作的gid 与 当前遍历额id相等
        if (element.id == data.gid) {
          let ND = true
          let YHM = true
          element.properties.forEach(e => {
            // 取当前 features 中的 the_geom 的值 与 行政区域的值 重新给当前表单赋值 （因为有可能修改改过图形的位置）
            if (e.name == 'the_geom') {
              data.the_geom = e.value
            }
            if (e.name == 'XZQY') {
              data.XZQY = e.value
            }
            if (e.name == 'YHM') {
              YHM = false
              data.YHM = this.$user.loginAccount
            }
            if (e.name == 'ND') {
              ND = false
              if (!e.value) {
                data.ND = this.yearId
              } else {
                data.ND = e.value
              }
            }
          })
          if (ND) {
            data.ND = this.yearId
          }
          if (ND) {
            data.YHM = this.$user.loginAccount
          }
          // 遍历表单数据 将表单数据修改为后台需要的数据格式
          let dataArray = []
          let dubObj = {}
          for (const key in data) {
            // 去重
            if (!dubObj.hasOwnProperty(key)) {
              dubObj[key] = data[key]
              dataArray.push({
                name: key,
                value: data[key]
              })
            }
          }
          element.properties = dataArray
        }
      })
      let layerName = ''
      if (this.addType) { // 说明是新增 可以根据addtype 判断是哪个 图层 Point LineString Polygon
        if (this.addType == 'Point') {
          layerName = `${this.barSelectList.layerName}_D`
        }
        if (this.addType == 'LineString') {
          layerName = `${this.barSelectList.layerName}_X`
        }
        if (this.addType == 'Polygon') {
          layerName = `${this.barSelectList.layerName}_M`
        }
        this.barSelectList.type = layerName
      } else { // 删除或者编辑 通过 判断图层
        layerName = this.barSelectList.type
      }
      var list = {
        name: layerName, // 图层名
        features: this.OnlineEditingList.features
      }
      let _this = this
      this.$api.mappost('/api/space/editFeatures', list).then(res => {
        if (res.status === 200) {
          _this.$Message.success('操作成功')
          _this.reload(_this)
        } else {
          _this.featuresBlanking()
          _this.$Message.error('操作失败')
        }
      })
    },
    reload (_this) {
      _this.featuresBlanking()
      if (_this.OnlineEditingList.isMove) {
        // 获取所有图层 找到当前的图层数据 移除 后重新加载 并改变当前下拉框中绑定的 ol_uid 以及当前选中的数据 barSelectList
        let get_removeLayer = _this.map.getLayers();
        let barSelectListList = _this.barSelectList.list // 一定要在这儿声明变量接收 不然后面 添加图层后 barSelectList.ol_uid 会改变
        for(var key in get_removeLayer.array_){
          barSelectListList.forEach(e => {
            if( _this.barSelectList.type == e.layerName && get_removeLayer.array_[key].ol_uid == e.ol_uid){
              // 删除当前图层
              _this.map.removeLayer(get_removeLayer.array_[key])
              // 添加图层
              _this.addMap(e.ol_uid, 'Refresh')
              return
            }
          })
        }
      }
    },
    // 开启选中要素事件  的点击事件
    async mapEdit_Click (evt, overlay) {
      if (this.detailFlag) {
        this.detailFlag = false
      } else {
        return
      }
      let _that = this
      this.coordinate = evt.coordinate
      var layers = this.map.getLayers()
      var params_ = null
      var bbox = evt.frameState.extent
      var x = evt.originalEvent.layerX
      var y = evt.originalEvent.layerY
      let overlayList = []
      let selectList = null
      if (!this.edit) {
        let list = this.$refs['leftBar'].list
        let leftSelectBarLiat = this.getLeftSelectBarLiat(list)
        if (leftSelectBarLiat) {
          selectList = leftSelectBarLiat
          if (selectList.children) {
            selectList.children.forEach(e => {
              overlayList = overlayList.concat(e.list)
            })
          } else {
            overlayList = selectList.list
          }
        }
      } else {
        overlayList = this.barSelectList.list
      }
      if (overlay && !overlayList.length) {
        // 获取当前点击的 图层要素的 一些参数
        for(var key in layers.array_){
          if (key > 3) {
            let params_ = layers.array_[key].getSource().params_;
            // 此方法会执行三次，分别是点线面的查询 有个flag的标识 一旦f有数据了 flag就为true 当再次有数据的时候判断
            await _that.getDetail(params_, evt, overlay, layers.array_[key])
          }
        }
      } else {
        // 获取当前点击的 图层要素的 一些参数
        for(var key in layers.array_){
          for (const i in overlayList) {
            if(layers.array_[key].ol_uid ==  overlayList[i].ol_uid){
              let params_ = layers.array_[key].getSource().params_;
              // 此方法会执行三次，分别是点线面的查询 有个flag的标识 一旦f有数据了 flag就为true 当再次有数据的时候判断
              await _that.getDetail(params_, evt, overlay, layers.array_[key])
            }
          }
        }
      }
      if (!this.detailFlag) {
        this.detailFlag = true
      } else {
        return
      }
      if(params_ == null){
        return
      }
    },
    // 根据 图层 查询当前点击的数据
    async getDetail (params_, evt, overlay, clickTypes) {
      var bbox = evt.frameState.extent
      var x = evt.originalEvent.layerX
      var y = evt.originalEvent.layerY
      // 根据参数拼接url this.$config.baseUrl.mapdev +
      var url_Parameter = '/api/ovitserver/' + params_.LAYERS + '/wms/' +
                          "?SERVICE=" + 'WMS' +
                          "&FORMAT=" + 'image/png' +
                          "&INFO_FORMAT=" + 'application/json' +
                          "&VERSION=" + params_.VERSION +
                          "&REQUEST=" + 'GetFeatureInfo' +
                          "&LAYERS=" + params_.LAYERS +
                          "&FEATURE_COUNT=" + '1' +
                          "&X=" + x +
                          "&Y=" + y +
                          "&STYLES=" + params_.STYLES +
                          "&SRS=" + params_.SRS +
                          "&WIDTH=" + evt.frameState.size[0] +
                          "&HEIGHT=" + evt.frameState.size[1] +
                          "&BBOX=" + bbox.join(',');
      
      if (!this.detailFlag) {
        // 根据参数的url 获取要素具体信息
        await this.$api.mapget(url_Parameter).then(response => {
          if (response.features && response.features.length && !this.detailFlag) {
            // 可能会查大别人的标注 所以判断一下用户名
            // if ((this.edit && response.features[0].properties.YHM == this.$user.loginAccount) || !this.edit) {
              this.detailFlag = true
              this.barSelectList.type = params_.LAYERS
              if (overlay) { // 如果是 非操作状态下 则是显示提示
                // 遍历data 获取到与当前的uid相等的数据对应的 value  根据value显示提示框
                var coordinate = evt.coordinate;
                let properties = response.features[0].properties
                this.filterDataForUid(this.data, clickTypes, properties)
                this.overlay.setPosition(coordinate);
              } else { // 操作状态下 开启点击
                this.PointMapEditPopup(response , params_.SRS , this.type)
              }
            // }
          }
        }).catch(error => {
        })
      }
    },
    checkedList (item) {
      let title = item.label
      let currentView = item.value
      let name = item.name
      this.$refs['popups'].handleShowForm(item, title, name, currentView)
      let x = (item.x1 * 1000000000000000 + item.x2 * 1000000000000000) / 2000000000000000
      let y = (item.y1 * 1000000000000000 + item.y2 * 1000000000000000) / 2000000000000000
      this.overlay.setPosition([x, y]);
    },
    // 通过UID 来判断是点击的那个图层 显示对应图层的表单
    filterDataForUid (data, clickTypes, properties) {
      data.forEach(dataList => {
        if (dataList.list && dataList.list.length) {
          dataList.list.forEach(e => {
            if (clickTypes.ol_uid == e.ol_uid) {
              let title = dataList.label
              let currentView = dataList.value
              let name = properties[dataList.showLabel[0]]
              this.$refs['popups'].handleShowForm(properties, title, name, currentView)
            }
          })
        } else if (dataList.children && dataList.children.length) {
          this.filterDataForUid(dataList.children, clickTypes, properties)
        }
      })
    },
    // 开启要素点击要素后 点击后的回调函数
    PointMapEditPopup (val, SRS, type) {
      //点图高亮
      var styles;
      if(type == 'Update'){
        styles = new Style({
          fill: new Fill({ // 矢量图层填充颜色，以及透明度
            color: 'rgba(255, 255, 0, 0.3)'
          }),
          stroke: new Stroke({ // 边界样式
            color: 'rgba(255, 255, 0, 1)',
            width: 4
          }),
          image: new CircleStyle({
            radius: 7,
            fill: new Fill({
              color: 'rgba(255, 255, 0, 1)'
            })
          })
        })
      }
      if(type == 'Delete'){
        styles = new Style({
          fill: new Fill({ // 矢量图层填充颜色，以及透明度
            color: 'rgba(224, 32, 32, 0.5)'
          }),
          stroke: new Stroke({ // 边界样式
            color: 'rgba(224, 32, 32, 1)',
            width: 4
          }),
          image: new CircleStyle({
            radius: 7,
            fill: new Fill({
              color: 'rgba(224, 32, 32, 1)'
            })
          })
        })
      }
      if(val.features.length > 0){
        var source = new VectorSource({
          features: (new GeoJSON()).readFeatures(val)
        })
        // 单个要素只能选择一次
        for(var key in this.OnlineEditingList.features){
          if(this.OnlineEditingList.features[key].op == "Update" || this.OnlineEditingList.features[key].op == "Delete"){
            if(this.OnlineEditingList.features[key].id == val.features[0].id){
              return
            }
          }
        }

        var value = new WKT().writeFeature((new GeoJSON()).readFeatures(val)[0])
        var data;
        if(type == 'Update'){
          data = {
            "op": "Update",
            "properties": [],
            "id": val.features[0].properties.gid,
            "layer_id": val.features[0].id,
          }
          let properties = val.features[0].properties
          let list = [
            {
              "name": "the_geom",
              "value": value
            }
          ]
          for(var key in properties){
            list.push({
              name: key,
              value: properties[key]
            })
          }
          data.properties = list
          data.data = list
        }
        if(type == 'Delete'){
          data = {
            "op": "Delete",
            "id": val.features[0].properties.gid,
            "layer_id": val.features[0].id,
          }
        }
        this.OnlineEditingList.features.push(data);
        var vectorLayer = new VectorLayer({
          source: source,
          style: styles,
        })
        this.activeVectorLayer = vectorLayer
        this.map.addLayer(vectorLayer)

        unByKey(this.OnlineEditingList.PointMap); // 关闭点图 在线编辑

        if (this.type === 'Delete') {
          this.$Modal.confirm({
            title: '是否确定删除',
            onOk:()=>{
              var data = {
                name:  this.barSelectList.type,
                features: this.OnlineEditingList.features
              }
              this.$api.mappost('/api/space/editFeatures', data).then(res => {
                if (res.status == -1) {
                  this.$Message.warning(res.message)
                } else {
                  this.$Message.success('删除成功')
                  this.OnlineEditingList.isMove = true
                  this.reload(this)
                }
              })
            },
            onCancel: () => {
              // 点击了取消
              this.featuresBlanking ()
            },
            okText:'确定',
            cancelText:'取消'
          })
        } else {
          this.tool_edit.Modify.setActive(true); // 激活几何图形修改控件
        }
      }
    },
    // 点击新建 后调用 绘制函数
    addInteraction_edit(obj){
      let typeValue = obj.type
      // 实例化一个矢量图层Vector作为绘制层
      var source = this.OnlineEditingList.DrawForm.source;
      var vectorLayer = new VectorLayer({
        source: source,
        style: new Style({
          fill: new Fill({ // 矢量图层填充颜色，以及透明度
            color: 'rgba(0, 193, 36, 0.5)'
          }),
          stroke: new Stroke({ // 边界样式
            color: 'rgba(0, 193, 36, 1)',
            width: 4
          }),
          image: new CircleStyle({
            radius: 7,
            fill: new Fill({
              color: 'rgba(0, 193, 36, 1)'
            })
          })
        }),
      });
      this.activeVectorLayer = vectorLayer
      this.map.addLayer(vectorLayer);
      var type = typeValue;
      if(typeValue !== 'None'){
        var geometryFunction, maxPoints;
        if(typeValue === 'Square'){                 //矩形
          type = 'Circle';               //设置绘制类型为Circle
          //设置几何信息变更函数，即创建正方形
          // geometryFunction = ol.interaction.Draw.createRegularPolygon(4);
          geometryFunction = ol.interaction.Draw.createBox();
        }
        //实例化图形绘制控件对象并添加到地图容器中
        this.OnlineEditingList.DrawForm.draw = new Draw({
          source: source,
          type: type,                                //几何图形类型
          geometryFunction: geometryFunction,             //几何信息变更时的回调函数
          maxPoints: maxPoints                            //最大点数
        });
        this.map.addInteraction(this.OnlineEditingList.DrawForm.draw);

        this.OnlineEditingList.DrawForm.draw.on('drawend', (evt) => {
          this.map.removeInteraction(this.OnlineEditingList.DrawForm.draw); // 移除绘制图形控件
          var value = new WKT().writeFeature(evt.feature)
          // 获取坐标系
          var polygon = evt.feature.getGeometry().extent_
          var data ={
              "op": "Insert",
              "properties": [
                {
                  "name": "the_geom",
                  "value": value
                }, {
                  "name": "x1",
                  "value": polygon[0]
                }, {
                  "name": "y1",
                  "value": polygon[1]
                }, {
                  "name": "x2",
                  "value": polygon[2]
                }, {
                  "name": "y2",
                  "value": polygon[3]
                }
              ],
              "layer_id": evt.feature.ol_uid,
            }
            // 将新建的要素，添加的features中 后续一起保存
          this.OnlineEditingList.features.push(data);
          // 激活几何图形修改控件
          this.tool_edit.Modify.setActive(true);
        })
      }else{
        //清空绘制的图形
        source.clear();
      }
    },
    // 初始化影像图层
    initImage () {
      var parser = new WMTSCapabilities()
        $.ajax({
            type: "GET",
            // /api/ovitserver/yanzhacun/wmts?SERVICE=WMTS&REQUEST=GetCapabilities
            // url: `${this.$config.baseUrl.portrait}/arcgis/services/YX/MapServer/WMSServer?version=1.3.0&request=getmap&layers=0&styles=default&crs=4490&bbox=112.412921,29.984054,112.451815,30.007323&width=2048&height=2048&format=image/png`,
            url: `${this.$config.baseUrl.mapapi}${this.portrait.url}?SERVICE=WMTS&REQUEST=GetCapabilities`,
            dataType: "xml",
            async: false,
            success: (response) => {
                var xmlResponse = this.xmlToString(response);
                var result = parser.read(xmlResponse);

                if(result == null || !result){
                  return;
                }
                var matrixIds = [];
                //计算分辨率
                var lods = [];
                if(result.Contents && result.Contents.TileMatrixSet){
                    for(var i=0;i<result.Contents.TileMatrixSet.length;i++){
                        var ele = result.Contents.TileMatrixSet[i];
                        if(ele.Identifier == result.Contents.TileMatrixSet[i].Identifier){
                            for(var j=0;j<ele.TileMatrix.length;j++){
                                var elej = ele.TileMatrix[j];
                                var res = this.scaleToRes(elej.ScaleDenominator);
                                lods.push(res);
                                matrixIds.push(elej.Identifier);
                            }
                            break;
                        }
                    }
                }

                var projectionExtent = result.Contents.Layer[0].WGS84BoundingBox;
                var wmtsLyr = new TileLayer({
                    source: new WMTSSource({
                        url:`${this.$config.baseUrl.mapapi}${this.portrait.url}`,
                        layer: result.Contents.Layer[0].Identifier,
                        matrixSet: result.Contents.TileMatrixSet[0].Identifier,
                        format: result.Contents.Layer[0].Format[0],
                        projection: result.Contents.TileMatrixSet[0].SupportedCRS,
                        tileGrid: new WMTSTilegrid({
                            origin: [0 , projectionExtent[3]],
                            resolutions: lods,
                            matrixIds: matrixIds
                        }),
                        style: result.Contents.Layer[0].Style[0].Identifier,
                        wrapX: true
                    })
                });
                this.map.addLayer(wmtsLyr);
                // 底图 边框高亮
                var gaolianglayers = new Image({
                  source: new ImageWMS({
                    url: `${this.$config.baseUrl.mapapi}${this.portrait.borderUrl}`,
                    params: {
                      'LAYERS': this.portrait.borderLayers,
                      'VERSION': '1.1.1',
                      'STYLES': this.portrait.borderStyle,
                      'REQUEST': 'GetMap',
                      'SRS': 'EPSG:4490',
                      'exceptions': 'application/vnd.ogc.se_inimage'
                    },
                    serverType: 'geoserver',
                  })
                })
                this.map.addLayer(gaolianglayers)
                // 取 下拉数据 根据下来数据显示全部图层
                this.filtersUrl(this.data)
                this.getLoaction(this.data)
            },
            //失败的回调函数
            error: (error) => {
            }
        })
    },
    //wmts服务加载
    scaleToRes (Scale) {
      var serverDpi = 90.7142857142857;
      var res;
      // if(results.wkt.indexOf("PROJCS") != -1){//平面坐标系
      //   res = Scale * 0.0254 / serverDpi;
      // }else{//球面坐标系
      //   res =  Scale * 0.0254 * 360 / 6378137 / Math.PI / 2 / serverDpi;
      // }
       res =  Scale * 0.0254 * 360 / 6378137 / Math.PI / 2 / serverDpi;
      return res
    },
    //xml对象转string
    xmlToString (xmlObj) {
      if (document.all) { // IE浏览器
        return xmlObj.xml
      } else { // 其他浏览器
        return (new XMLSerializer()).serializeToString(xmlObj)
      }
    }
  }
}
</script>