// 定义右侧下拉框中的数据
export const selectData = {
  data () {
    return {
      data: [
        {
          label: '文化景观',
          value: '01',
          showEdit: 'Point',
          filter: 'SFMF',
          children: [
            {
              label: '风景名胜设施',
              value: 'famousScenery',
              bbox: '108.193359375,28.27056884765625,114.6533203125,32.80242919921875',
              layerName: 'FJMSSS',
              url: '/api/ovitserver/FJMSSS/wms',
              showLabel: ['FJQMC'],
              keyword: [{name: 'FJQMC', type: 'LIKE'}],
              filter: 'SFMF',
              list: [
                {
                  url: '/api/ovitserver/FJMSSS_D/wms',
                  layerName: 'FJMSSS_D',
                },
                {
                  url: '/api/ovitserver/FJMSSS_X/wms',
                  layerName: 'FJMSSS_X',
                },
                {
                  url: '/api/ovitserver/FJMSSS_M/wms',
                  layerName: 'FJMSSS_M',
                }
              ]
            },
            {
              label: '文化遗产设施',
              value: 'culturalHeritage',
              bbox: '112.3740005493164,29.9562406539917,112.47493743896484,30.027050971984863',
              layerName: 'WHYCSS',
              filter: 'SFMF',
              url: '/api/ovitserver/WHYCSS/wms',
              showLabel: ['WHYCMC'],
              keyword: [{name: 'WHYCMC', type: 'LIKE'}],
              list: [
                {
                  url: '/api/ovitserver/WHYCSS_D/wms',
                  layerName: 'WHYCSS_D',
                },
                {
                  url: '/api/ovitserver/WHYCSS_X/wms',
                  layerName: 'WHYCSS_X',
                },
                {
                  url: '/api/ovitserver/WHYCSS_M/wms',
                  layerName: 'WHYCSS_M',
                }
              ]
            }
          ]
        },
        {
          label: '公共设施',
          showEdit: 'Point',
          value: '02',
          children: [
            {
              label: '通信设施及用户信息',
              value: 'communicationFacilitiesAndUser',
              bbox: '112.40717411041268,29.978449344635035,112.4576425552369,30.013854503631617',
              layerName: 'TXSSJYHXX',
              url: '/api/ovitserver/TXSSJYHXX/wms',
              showLabel: ['SSMC'],
              keyword: [{name: 'SSMC', type: 'LIKE'}],
              list: [
                {
                  url: '/api/ovitserver/TXSSJYHXX_D/wms',
                  layerName: 'TXSSJYHXX_D',
                },
                {
                  url: '/api/ovitserver/TXSSJYHXX_X/wms',
                  layerName: 'TXSSJYHXX_X',
                },
                {
                  url: '/api/ovitserver/TXSSJYHXX_M/wms',
                  layerName: 'TXSSJYHXX_M',
                }
              ]
            },
            {
              label: '文化设施信息',
              value: 'culturalFacility',
              bbox: '112.4174952507019,29.993260502815247,112.43011236190796,30.002111792564392',
              layerName: 'WHSSXX',
              url: '/api/ovitserver/WHSSXX/wms',
              showLabel: ['SSMC'],
              keyword: [{name: 'SSMC', type: 'LIKE'}],
              list: [
                {
                  url: '/api/ovitserver/WHSSXX_D/wms',
                  layerName: 'WHSSXX_D',
                },
                {
                  url: '/api/ovitserver/WHSSXX_X/wms',
                  layerName: 'WHSSXX_X',
                },
                {
                  url: '/api/ovitserver/WHSSXX_M/wms',
                  layerName: 'WHSSXX_M',
                }
              ]
            },
            {
              label: '教育设施信息',
              value: 'educationalFacilities',
              bbox: '112.40352630615243,29.978749752044703,112.45399475097665,30.014154911041285',
              layerName: 'JYSSXX',
              url: '/api/ovitserver/JYSSXX/wms',
              showLabel: ['SSMC'],
              keyword: [{name: 'SSMC', type: 'LIKE'}],
              list: [
                {
                  url: '/api/ovitserver/JYSSXX_D/wms',
                  layerName: 'JYSSXX_D',
                },
                {
                  url: '/api/ovitserver/JYSSXX_X/wms',
                  layerName: 'JYSSXX_X',
                },
                {
                  url: '/api/ovitserver/JYSSXX_M/wms',
                  layerName: 'JYSSXX_M',
                }
              ]
            },
            {
              label: '垃圾处理设施信息',
              value: 'garbageDisposalFacilities',
              bbox: '112.42811679840096,29.995620846748377,112.44073390960702,30.004472136497522',
              layerName: 'LJCLSSXX',
              url: '/api/ovitserver/LJCLSSXX/wms',
              showLabel: ['SSMC'],
              keyword: [{name: 'SSMC', type: 'LIKE'}],
              list: [
                {
                  url: '/api/ovitserver/LJCLSSXX_D/wms',
                  layerName: 'LJCLSSXX_D',
                },
                {
                  url: '/api/ovitserver/LJCLSSXX_X/wms',
                  layerName: 'LJCLSSXX_X',
                },
                {
                  url: '/api/ovitserver/LJCLSSXX_M/wms',
                  layerName: 'LJCLSSXX_M',
                }
              ]
            },
            {
              label: '养老福利设施信息',
              value: 'pensionFacilities',
              bbox: '112.3740005493164,29.9562406539917,112.47493743896484,30.027050971984863',
              layerName: 'YLFLSSXX',
              url: '/api/ovitserver/YLFLSSXX/wms',
              showLabel: ['SSMC'],
              keyword: [{name: 'SSMC', type: 'LIKE'}],
              list: [
                {
                  url: '/api/ovitserver/YLFLSSXX_D/wms',
                  layerName: 'YLFLSSXX_D',
                },
                {
                  url: '/api/ovitserver/YLFLSSXX_M/wms',
                  layerName: 'YLFLSSXX_M',
                },
                {
                  url: '/api/ovitserver/YLFLSSXX_X/wms',
                  layerName: 'YLFLSSXX_X',
                }
              ]
            },
            {
              label: '自来水设施及用户信息',
              value: 'runningWaterAndUser',
              bbox: '112.41475939750671,29.99466598033905,112.42737650871277,30.003517270088196',
              layerName: 'ZLSSSJYHXX',
              url: '/api/ovitserver/ZLSSSJYHXX/wms',
              showLabel: ['SSMC'],
              keyword: [{name: 'SSMC', type: 'LIKE'}],
              list: [
                {
                  url: '/api/ovitserver/ZLSSSJYHXX_D/wms',
                  layerName: 'ZLSSSJYHXX_D',
                },
                {
                  url: '/api/ovitserver/ZLSSSJYHXX_M/wms',
                  layerName: 'ZLSSSJYHXX_M',
                },
                {
                  url: '/api/ovitserver/ZLSSSJYHXX_X/wms',
                  layerName: 'ZLSSSJYHXX_X',
                }
              ]
            },
            {
              label: '安保设施信息',
              value: 'securityFacilities',
              bbox: '112.4165940284729,29.98893141746521,112.44182825088501,30.0066339969635',
              layerName: 'ABSSXX',
              url: '/api/ovitserver/ABSSXX/wms',
              showLabel: ['SSMC'],
              keyword: [{name: 'SSMC', type: 'LIKE'}],
              list: [
                {
                  url: '/api/ovitserver/ABSSXX_D/wms',
                  layerName: 'ABSSXX_D',
                },
                {
                  url: '/api/ovitserver/ABSSXX_M/wms',
                  layerName: 'ABSSXX_M',
                },
                {
                  url: '/api/ovitserver/ABSSXX_X/wms',
                  layerName: 'ABSSXX_X',
                }
              ]
            },
            {
              label: '体育设施信息',
              value: 'sportsFacilities',
              bbox: '112.4155855178833,29.97969388961792,112.46605396270752,30.015099048614502',
              layerName: 'TYSSXX',
              url: '/api/ovitserver/TYSSXX/wms',
              showLabel: ['SSMC'],
              keyword: [{name: 'SSMC', type: 'LIKE'}],
              list: [
                {
                  url: '/api/ovitserver/TYSSXX_D/wms',
                  layerName: 'TYSSXX_D',
                },
                {
                  url: '/api/ovitserver/TYSSXX_X/wms',
                  layerName: 'TYSSXX_X',
                },
                {
                  url: '/api/ovitserver/TYSSXX_M/wms',
                  layerName: 'TYSSXX_M',
                }
              ]
            },
            {
              label: '交通设施及用户信息',
              value: 'trafficAndUser',
              bbox: '112.42341756820687,29.98706459999087,112.44865179061898,30.00476717948916',
              layerName: 'JTSSJYHXX',
              url: '/api/ovitserver/JTSSJYHXX/wms',
              showLabel: ['SSMC'],
              keyword: [{name: 'SSMC', type: 'LIKE'}],
              list: [
                {
                  url: '/api/ovitserver/JTSSJYHXX_D/wms',
                  layerName: 'JTSSJYHXX_D',
                },
                {
                  url: '/api/ovitserver/JTSSJYHXX_X/wms',
                  layerName: 'JTSSJYHXX_X',
                },
                {
                  url: '/api/ovitserver/JTSSJYHXX_M/wms',
                  layerName: 'JTSSJYHXX_M',
                }
              ]
            },
            {
              label: '水利设施信息',
              value: 'waterConservancyFacilities',
              bbox: '112.40936279296884,29.987128973007227,112.43459701538094,30.004831552505518',
              layerName: 'SLSSXX',
              url: '/api/ovitserver/SLSSXX/wms',
              showLabel: ['SSMC'],
              keyword: [{name: 'SSMC', type: 'LIKE'}],
              list: [
                {
                  url: '/api/ovitserver/SLSSXX_D/wms',
                  layerName: 'SLSSXX_D',
                },
                {
                  url: '/api/ovitserver/SLSSXX_X/wms',
                  layerName: 'SLSSXX_X',
                },
                {
                  url: '/api/ovitserver/SLSSXX_M/wms',
                  layerName: 'SLSSXX_M',
                }
              ]
            }
          ]
        },
        {
          showEdit: 'Point',
          label: '营业网点',
          value: 'businessOutlets',
          bbox: '112.42356777191162,29.989226460456848,112.43618488311768,29.998077750205994',
          layerName: 'YYWD',
          url: '/api/ovitserver/YYWD/wms',
          showLabel: ['WDMC'],
          keyword: [{name: 'WDMC', type: 'LIKE'}],
          filter: 'WDLX',
          list: [
            {
              url: '/api/ovitserver/YYWD_D/wms',
              layerName: 'YYWD_D',
            },
            {
              url: '/api/ovitserver/YYWD_X/wms',
              layerName: 'YYWD_X',
            },
            {
              url: '/api/ovitserver/YYWD_M/wms',
              layerName: 'YYWD_M',
            }
          ]
        },
        {
          showEdit: 'Polygon',
          label: '地块信息',
          value: '04',
          STYLES: 'DKXX.sld',
          bbox: '112.36610412597656,29.957356452941895,112.467041015625,30.02816677093506',
          layerName: 'DKXX',
          url: '/api/ovitserver/DKXX/wms',
          showLabel: ['TDLYQLRMC'],
          keyword: [{name: 'TDLYQLRMC', type: 'LIKE'}],
          list: [
            {
              url: '/api/ovitserver/DKXX_D/wms',
              layerName: 'DKXX_D',
            },
            {
              url: '/api/ovitserver/DKXX_X/wms',
              layerName: 'DKXX_X',
            },
            {
              url: '/api/ovitserver/DKXX_M/wms',
              layerName: 'DKXX_M',
            }
          ]
        },
        // {
        //   showEdit: 'Polygon',
        //   label: '自然地理信息',
        //   value: '05',
        //   STYLES: 'DKXX.sld',
        //   bbox: '112.37661838531494,29.958386421203613,112.46287822723389,30.029196739196777',
        //   layerName: 'ZRDLXX',
        //   url: '/api/ovitserver/ZRDLXX/wms'
        //   // children: [
        //   //   {
        //   //     label: '生物资源信息',
        //   //     value: 'biological'
        //   //   },
        //   //   {
        //   //     label: '气候信息',
        //   //     value: 'climate'
        //   //   },
        //   //   {
        //   //     label: '国土面积信息',
        //   //     value: 'geographyLand'
        //   //   },
        //   //   {
        //   //     label: '地质矿产信息',
        //   //     value: 'minerals'
        //   //   },
        //   //   {
        //   //     label: '地形地貌信息',
        //   //     value: 'topography'
        //   //   }
        //   // ]
        // }
      ],
      SLD_BODY: '',
      otherFilterBody: '',
      YHMFilterBody: ''
    }
  },
  methods: {
    // 添加图层 时候生成 SLD_BODY
    getBody (layerName, YHM, label, filterKey, filterWord, keywordList, keyword) {
      let SLD_BODY
      /**
       * <ogc:Filter>
       *    <ogc:And>
       *      <ogc:Or>
       *        <ogc:PropertyIsLike wildCard="%" singleChar="_" escape="\">
       *          <ogc:PropertyName>a</ogc:PropertyName>
       *          <ogc:Literal>%42%</ogc:Literal>
       *        </ogc:PropertyIsLike>
       *        <ogc:PropertyIsLike wildCard="%" singleChar="_" escape="\">
       *          <ogc:PropertyName>b</ogc:PropertyName>
       *          <ogc:Literal>%42%</ogc:Literal>
       *        </ogc:PropertyIsLike>
       *      </ogc:Or>
       *      <ogc:PropertyIsEqualTo>
       *        <ogc:PropertyName>c</ogc:PropertyName>
       *        <ogc:Literal>d</ogc:Literal>
       *      </ogc:PropertyIsEqualTo>
       *    </ogc:And>
       *  </ogc:Filter>
       */
      // // 拼接查询条件 多个条件用<And>
      // this.otherFilterBody = ''
      // this.YHMFilterBody = ''
      // if (filterKey && filterWord) {
      //   this.otherFilterBody = `<ogc:PropertyIsEqualTo>`
      //                       +  `<ogc:PropertyName>${filterKey}</ogc:PropertyName>`
      //                       +  `<ogc:Literal>${filterWord}</ogc:Literal>`
      //                       +  `</ogc:PropertyIsEqualTo>`
      // }
      // // 查询 全部人 跟查询 当前登陆人
      // if (YHM) {
      //   this.YHMFilterBody = `<ogc:PropertyIsEqualTo>`
      //                     +  `<ogc:PropertyName>YHM</ogc:PropertyName>`
      //                     +  `<ogc:Literal>${YHM}</ogc:Literal>`
      //                     +  `</ogc:PropertyIsEqualTo>`
      // }
      // let fiterStr = ''
      // if (this.YHMFilterBody) {
      //    fiterStr = `<ogc:Filter>${this.YHMFilterBody}</ogc:Filter>`
      // }
      // if (this.otherFilterBody){
      //   fiterStr = `<ogc:Filter>${this.otherFilterBody}</ogc:Filter>`
      // }
      // if (this.YHMFilterBody && this.otherFilterBody) {
      //   fiterStr = `<ogc:Filter><And>${this.YHMFilterBody}${this.otherFilterBody}</And></ogc:Filter>`
      // }
      // 用户名 && 其他过滤 && 关键字（关键字a || 关键字b || 关键字c）
      this.YHMFilterBody = ''
      this.otherFilterBody = ''
      let fiterStr = ''
      let keywordStr = ``
      // 用户名
      if (YHM) {
        this.YHMFilterBody = `<ogc:PropertyIsEqualTo>`
                          +  `<ogc:PropertyName>YHM</ogc:PropertyName>`
                          +  `<ogc:Literal>${YHM}</ogc:Literal>`
                          +  `</ogc:PropertyIsEqualTo>`
      }
      // 其他过滤条件
      if (filterKey && filterWord) {
        this.otherFilterBody = `<ogc:PropertyIsLike wildCard="%" singleChar="_" escape="\">`
                            +    `<ogc:PropertyName>${filterKey}</ogc:PropertyName>`
                            +    `<ogc:Literal>%${filterWord}%</ogc:Literal>`
                            +  `</ogc:PropertyIsLike>`
      }
      // keyword
      console.log('keywordList', keywordList)
      console.log('keyword', keyword)
      if (keywordList && keyword) {
        keywordList.forEach(e => {
          keywordStr += `<ogc:PropertyIsLike wildCard="%" singleChar="_" escape="\">`
                      +    `<ogc:PropertyName>${e.name}</ogc:PropertyName>`
                      +    `<ogc:Literal>%${keyword}%</ogc:Literal>`
                      +  `</ogc:PropertyIsLike>`
        })
      }
      // 有用户名 则是数据标注页面
      if (this.YHMFilterBody) { // 有用户名
        fiterStr = `<ogc:Filter>${this.YHMFilterBody}</ogc:Filter>`
      } else { // 没有 则是数据展示页面
        if (keywordStr && this.otherFilterBody) {
          fiterStr =  `<ogc:Filter>`
                  +    `<ogc:And>`
                  +      `<ogc:Or>`
                  +      `${keywordStr}`
                  +      `</ogc:Or>`
                  +      `${this.otherFilterBody}`
                  +    `</ogc:And>`
                  +  `</ogc:Filter>`
        } else if (keywordStr) {
          fiterStr =  `<ogc:Filter>`
                  +      `${keywordStr}`
                  +  `</ogc:Filter>`
        } else if (this.otherFilterBody) {
          fiterStr =  `<ogc:Filter>`
                  +     `${this.otherFilterBody}`
                  +  `</ogc:Filter>`
        }
      }
      console.log('fiterStrfiterStr', fiterStr)
      let type = layerName.split('_')[1]
      // 面
      if (type == 'M') {
       SLD_BODY = `<sld:StyledLayerDescriptor version="1.0.0" xmlns:sld="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc" xmlns:xlink="http://www.w3.org/1999/xlink">`
      +  `<sld:NamedLayer>`
      +    `<sld:Name>${layerName}</sld:Name>`
      +    `<sld:UserStyle>`
      +     ` <sld:Name>Style1</sld:Name>`
      +      `<sld:FeatureTypeStyle>`
      +        `<sld:Rule>`
      +          `<sld:Name>${layerName}</sld:Name>`
      +          `<sld:Title>${layerName}</sld:Title>`
      // +          `<ogc:Filter>`
      +          `${fiterStr}`
      // +          `</ogc:Filter>`
      +          `<sld:PolygonSymbolizer>`
      +            `<sld:Fill>`
      +              `<sld:CssParameter name="fill">#FFFFFF</sld:CssParameter>`
      +              `<sld:CssParameter name="fill-opacity">0.5</sld:CssParameter>`
      +            `</sld:Fill>`
      +          `</sld:PolygonSymbolizer>`
      +          `<sld:LineSymbolizer>`
      +            `<sld:Stroke>`
      +              `<sld:CssParameter name="stroke">#FF0000</sld:CssParameter>`
      +              `<sld:CssParameter name="stroke-width">1.5</sld:CssParameter>`
      +              `<sld:CssParameter name="stroke-opacity">1</sld:CssParameter>`
      +            `</sld:Stroke>`
      +          `</sld:LineSymbolizer>`
      +          `<sld:TextSymbolizer>`
      +            `<sld:Label>`
      +              `<ogc:PropertyName>${label}</ogc:PropertyName>`
      +            `</sld:Label>`
      +            `<sld:Font>`
      +              `<sld:CssParameter name="font-family">宋体</sld:CssParameter>`
      +              `<sld:CssParameter name="font-size">32</sld:CssParameter>`
      +              `<sld:CssParameter name="font-style">normal</sld:CssParameter>`
      +              `<sld:CssParameter name="font-weight">normal</sld:CssParameter>`
      +            `</sld:Font>`
      +            `<sld:LabelPlacement>`
      +              `<sld:PointPlacement>`
      +                `<sld:AnchorPoint>`
      +                  `<sld:AnchorPointX>0.5</sld:AnchorPointX>`
      +                  `<sld:AnchorPointY>0.5</sld:AnchorPointY>`
      +                `</sld:AnchorPoint>`
      +                `<sld:Displacement>`
      +                  `<sld:DisplacementX>0</sld:DisplacementX>`
      +                  `<sld:DisplacementY>0</sld:DisplacementY>`
      +                `</sld:Displacement>`
      +              `</sld:PointPlacement>`
      +            `</sld:LabelPlacement>`
      +            `<sld:Fill>`
      +              `<sld:CssParameter name="fill">#000000</sld:CssParameter>`
      +              `<sld:CssParameter name="fill-opacity">1.0</sld:CssParameter>`
      +            `</sld:Fill>`
      +            `<sld:VendorOption name="conflictResolution">true</sld:VendorOption>`
      +            `<sld:VendorOption name="autoWrap">100</sld:VendorOption>`
      +          `</sld:TextSymbolizer>`
      +        `</sld:Rule>`
      +      `</sld:FeatureTypeStyle>`
      +    `</sld:UserStyle>`
      +  `</sld:NamedLayer>`
      +`</sld:StyledLayerDescriptor>`
      
      } else if (type == 'D') {
        SLD_BODY = '<sld:StyledLayerDescriptor version="1.0.0" xmlns:sld="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc" xmlns:xlink="http://www.w3.org/1999/xlink">'
                      + `<sld:NamedLayer>`
                      + `<sld:Name>${layerName}</sld:Name>`
                      +  `<sld:UserStyle>`
                      +    `<sld:Name>Style1</sld:Name>`
                      +      `<sld:FeatureTypeStyle>`
                      +      `<sld:Rule>`
                      +        `<sld:Name>${layerName}</sld:Name>`
                      +        `<sld:Title>${layerName}</sld:Title>`
                      // +	    `<ogc:Filter>`
                      +          `${fiterStr}`
                      // +	    `</ogc:Filter>`
                      +        `<sld:PointSymbolizer>`
                      +          `<sld:Graphic>`
                      +            `<sld:Mark>`
                      +              `<sld:WellKnownName>ttf://ESRI Default Marker#0x61</sld:WellKnownName>`
                      +              `<sld:Fill>`
                      +                `<sld:CssParameter name="fill">#00FF00</sld:CssParameter>`
                      +                `<sld:CssParameter name="fill-opacity">1</sld:CssParameter>`
                      +              `</sld:Fill>`
                      +            `</sld:Mark>`
                      +            `<sld:Size>32</sld:Size>`
                      +            `<sld:Rotation>0</sld:Rotation>`
                      +          `</sld:Graphic>`
                      +        `</sld:PointSymbolizer>`
                      +        `<sld:TextSymbolizer>`
                      +          `<sld:Label>`
                      +            `<ogc:PropertyName>${label}</ogc:PropertyName>`
                      +          `</sld:Label>`
                      +          `<sld:Font>`
                      +            `<sld:CssParameter name="font-family">宋体</sld:CssParameter>`
                      +            `<sld:CssParameter name="font-size">32</sld:CssParameter>`
                      +            `<sld:CssParameter name="font-style">normal</sld:CssParameter>`
                      +            `<sld:CssParameter name="font-weight">normal</sld:CssParameter>`
                      +         `</sld:Font>`
                      +         `<sld:LabelPlacement>`
                      +            `<sld:PointPlacement>`
                      +              `<sld:AnchorPoint>`
                      +                `<sld:AnchorPointX>0</sld:AnchorPointX>`
                      +                `<sld:AnchorPointY>0</sld:AnchorPointY>`
                      +              `</sld:AnchorPoint>`
                      +             `<sld:Displacement>`
                      +                `<sld:DisplacementX>0</sld:DisplacementX>`
                      +                `<sld:DisplacementY>0</sld:DisplacementY>`
                      +              `</sld:Displacement>`
                      +            `</sld:PointPlacement>`
                      +          `</sld:LabelPlacement>`
                      +          `<sld:Fill>`
                      +            `<sld:CssParameter name="fill">#000000</sld:CssParameter>`
                      +            `<sld:CssParameter name="fill-opacity">1.0</sld:CssParameter>`
                      +          `</sld:Fill>`
                      +          `<sld:VendorOption name="conflictResolution">true</sld:VendorOption>`
                      +          `<sld:VendorOption name="autoWrap">100</sld:VendorOption>`
                      +        `</sld:TextSymbolizer>`
                      +      `</sld:Rule>`
                      +    `</sld:FeatureTypeStyle>`
                      +  `</sld:UserStyle>`
                      + `</sld:NamedLayer>`
                      +`</sld:StyledLayerDescriptor>`
        
      } else if (type == 'X') {
        SLD_BODY = `<sld:StyledLayerDescriptor version="1.0.0" xmlns:sld="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc" xmlns:xlink="http://www.w3.org/1999/xlink">`
        +   `<sld:NamedLayer>`
        +     `<sld:Name>${layerName}</sld:Name>`
        +     `<sld:UserStyle>`
        +       `<sld:Name>Style1</sld:Name>`
        +       `<sld:FeatureTypeStyle>`
        +         `<sld:Rule>`
        +           `<sld:Name>${layerName}</sld:Name>`
        +           `<sld:Title>${layerName}</sld:Title>`
        // +             `<ogc:Filter>`
        +          `${fiterStr}`
        // +             `</ogc:Filter>`
        +           `<sld:LineSymbolizer>`
        +             `<sld:Stroke>`
        +               `<sld:CssParameter name="stroke">#06AB00</sld:CssParameter>`
        +               `<sld:CssParameter name="stroke-width">1.33</sld:CssParameter>`
        +               `<sld:CssParameter name="stroke-opacity">1</sld:CssParameter>`
        +             `</sld:Stroke>`
        +           `</sld:LineSymbolizer>`
        +           `<sld:TextSymbolizer>`
        +             `<sld:Label>`
        +               `<ogc:PropertyName>${label}</ogc:PropertyName>`
        +             `</sld:Label>`
        +             `<sld:Font>`
        +               `<sld:CssParameter name="font-family">宋体</sld:CssParameter>`
        +               `<sld:CssParameter name="font-size">32</sld:CssParameter>`
        +               `<sld:CssParameter name="font-style">normal</sld:CssParameter>`
        +               `<sld:CssParameter name="font-weight">normal</sld:CssParameter>`
        +             `</sld:Font>`
        +             `<sld:Fill>`
        +               `<sld:CssParameter name="fill">#000000</sld:CssParameter>`
        +               `<sld:CssParameter name="fill-opacity">1.0</sld:CssParameter>`
        +             `</sld:Fill>`
        +             `<sld:LabelPlacement>`
        +               `<sld:LinePlacement>`
        +                 `<sld:PerpendicularOffset>0</sld:PerpendicularOffset>`
        +               `</sld:LinePlacement>`
        +             `</sld:LabelPlacement>`
        +             `<sld:VendorOption name="conflictResolution">true</sld:VendorOption>`
        +             `<sld:VendorOption name="autoWrap">100</sld:VendorOption>`
        +           `</sld:TextSymbolizer>`
        +         `</sld:Rule>`
        +       `</sld:FeatureTypeStyle>`
        +     `</sld:UserStyle>`
        +   `</sld:NamedLayer>`
        + `</sld:StyledLayerDescriptor>`
      
      }

      return encodeURI(SLD_BODY)
      // return SLD_BODY
    }
  },
}

