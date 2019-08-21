
// 导航状态
export const navStatus = {
  data () {
      return {
        index: 0,
      }
  },
  created(){
    this.$parent.checkNav()
  },
  methods: {
    // navClick () {
      // let path = window.location.pathname.split('/')[2]
      // let checkedIndex = 0
      // this.$parent.data.forEach((item, index) => {
      //   item.checked = false
      //   if (this.$route.query.tabType) {
      //     if (`${path}/${this.$route.query.tabType}` === item.attributionId) {
      //       item.checked = true
      //       checkedIndex = index
      //     } else {
      //       item.checked = false
      //     }
      //   } else {
      //     if (path === item.attributionId) {
      //       item.checked = true
      //       checkedIndex = index
      //     } else {
      //       item.checked = false
      //     }
      //   }
      // })
      // this.$parent.data.splice(checkedIndex, 1, this.$parent.data[checkedIndex])
  },
  watch: {
    $route(){
      console.log('watch')
      console.log(window.location)
      let path = window.location.pathname.split('/')[2]
      if (path != 'index') {
        this.getList()
      }
      this.createdInit()
      this.$parent.checkNav()
    },
  }
}


export const goToPath = {
  data () {
      return {
      }
  },
  created(){
  },
  methods: {
    // 详情页跳转
    goDetail(item){
      var url = ''
      var id = item.id
      var informationId = item.informationId
      var columnType = item.columnType
      if(item.dataType == '动态'){
          if(columnType == '图书'){
              url = `/InforMation/bookBlurb?id=${informationId}&informationDetailId=${id}&book_type=information`
          }else{
              url =  `/InforMation/findInforMationDetail?id=${id}`
          }
      }else if(item.dataType == '政策'){
          if(columnType == '图书'){
              url = `/InforMation/bookBlurb?id=${informationId}&informationDetailId=${id}&book_type=policy`
          }else{
              url = `/InforMation/policyDetail?id=${id}`
          }
      }else if(item.dataType == '知识'){
          if(columnType == '图书'){
              url = `/InforMation/bookBlurb?id=${informationId}&informationDetailId=${id}&book_type=knowledge`
          }else{
              url = `/InforMation/knowledgeDetail?id=${id}`
          }
      } else if (item.dataType == '标准') {
          url = `/inforMation/standardDetail?id=${item.standardDetailId}`
      }
      window.open(url, "_blank");
    },
  }
}

// 用户信息
export const userInfo = {
  data () {
    return {
      userInfo: {},
    }
  },
  created () {
    this.userInfo=JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
  }
}

// 用户信息
export const moments = {
  data () {
    return {
      lunar: {
        tg: '甲乙丙丁戊己庚辛壬癸',
        dz: '子丑寅卯辰巳午未申酉戌亥',
        number: '一二三四五六七八九十',
        year: '鼠牛虎兔龙蛇马羊猴鸡狗猪',
        month: '正二三四五六七八九十冬腊',
        monthadd: [0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334],
        calendar: [0xA4B, 0x5164B, 0x6A5, 0x6D4, 0x415B5, 0x2B6, 0x957, 0x2092F, 0x497, 0x60C96, 0xD4A, 0xEA5, 0x50DA9, 0x5AD, 0x2B6, 0x3126E, 0x92E, 0x7192D, 0xC95, 0xD4A, 0x61B4A, 0xB55, 0x56A, 0x4155B, 0x25D, 0x92D, 0x2192B, 0xA95, 0x71695, 0x6CA, 0xB55, 0x50AB5, 0x4DA, 0xA5B, 0x30A57, 0x52B, 0x8152A, 0xE95, 0x6AA, 0x615AA, 0xAB5, 0x4B6, 0x414AE, 0xA57, 0x526, 0x31D26, 0xD95, 0x70B55, 0x56A, 0x96D, 0x5095D, 0x4AD, 0xA4D, 0x41A4D, 0xD25, 0x81AA5, 0xB54, 0xB6A, 0x612DA, 0x95B, 0x49B, 0x41497, 0xA4B, 0xA164B, 0x6A5, 0x6D4, 0x615B4, 0xAB6, 0x957, 0x5092F, 0x497, 0x64B, 0x30D4A, 0xEA5, 0x80D65, 0x5AC, 0xAB6, 0x5126D, 0x92E, 0xC96, 0x41A95, 0xD4A, 0xDA5, 0x20B55, 0x56A, 0x7155B, 0x25D, 0x92D, 0x5192B, 0xA95, 0xB4A, 0x416AA, 0xAD5, 0x90AB5, 0x4BA, 0xA5B, 0x60A57, 0x52B, 0xA93, 0x40E95]
      }
    }
  },
  methods: {
    getWeatherType (string) {
      // 类型 晴天  云  阴  风 雨 
        var res = "";
        if(/^晴$/.test(str)) {
          res = "icon-qing";
        } else if(/^小雨$/.test(str)) {
          res = "icon-xiaoyu1";
        } else if(/^中雨$/.test(str)) {
          res = "icon-zhongyu	";
        } else if(/^大雨$/.test(str)) {
          res = "icon-dayu";
        } else if(/^大暴雨$/.test(str)) {
          res = "icon-dabaoyu";
        } else if(/^阵雨$/.test(str)) {
          res = "icon-zhenyu";
        } else if(/^雷阵雨$/.test(str)) {
          res = "icon-leizhenyu";
        } else if(/^雷电$/.test(str)) {
          res = "icon-leidian";
        } else if(/^冻雨$/.test(str)) {
          res = "icon-dongyu";
        } else if(/^冰雹$/.test(str)) {
          res = "icon-tianqitubiao_bingbao";
        } else if(/^大风$/.test(str)) {
          res = "icon-tianqitubiao_dafeng";
        } else if(/^扬沙$/.test(str)) {
          res = "icon-tianqitubiao_yangsha";
        } else if(/^浮尘$/.test(str)) {
          res = "icon-fuchen";
        } else if(/^沙尘暴$/.test(str)) {
          res = "icon-shachenbao";
        } else if(/^雾霾$/.test(str)) {
          res = "icon-wumai";
        } else if(/^雾$/.test(str)) {
          res = "icon-wu";
        } else if(/雪/.test(str)) {
          res = "icon-baoxue";
        } else if(/^多云$/.test(str)) {
          res = "icon-duoyun";
        } else if(/^阴$/.test(str)) {
          res = "icon-yin";
        }
        return res;
    },
   getLunarDate(date) {

      var year, month, day;
      if (!date) {
          date = new Date(), year = date.getFullYear(), month = date.getMonth(), day = date.getDate();
      } else {
          date = date.split('-'), year = parseInt(date[0]), month = date[1] - 1, day = parseInt(date[2]);
      }

      if (year < 1921 || year > 2020) {
          return {}
      }

      var total, m, n, k, bit, lunarYear, lunarMonth, lunarDay;
      var isEnd = false;
      var tmp = year;
      if (tmp < 1900) {
          tmp += 1900;
      }
      total = (tmp - 1921) * 365 + Math.floor((tmp - 1921) / 4) + this.lunar.monthadd[month] + day - 38;
      if (year % 4 == 0 && month > 1) {
          total++;
      }
      for (m = 0;; m++) {
          k = (this.lunar.calendar[m] < 0xfff) ? 11 : 12;
          for (n = k; n >= 0; n--) {
              bit = (this.lunar.calendar[m] >> n) & 1;
              if (total <= 29 + bit) {
                  isEnd = true;
                  break;
              }
              total = total - 29 - bit;
          }
          if (isEnd) break;
      }
      lunarYear = 1921 + m;
      lunarMonth = k - n + 1;
      lunarDay = total;
      if (k == 12) {
          if (lunarMonth == Math.floor(this.lunar.calendar[m] / 0x10000) + 1) {
              lunarMonth = 1 - lunarMonth;
          }
          if (lunarMonth > Math.floor(this.lunar.calendar[m] / 0x10000) + 1) {
              lunarMonth--;
          }
      }

      return {
          lunarYear: lunarYear,
          lunarMonth: this.getString(lunarMonth),
          lunarDay: this.getString(lunarDay),
      };
    },
    getString (number) {
      let string = ''
      let arr = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', 
      '十一', '十二', '十三', '十四', '十五', '十六', '十七', '十八', '十九', '二十', 
      '二十一', '二十二', '二十三', '二十四', '二十五', '二十六', '二十七', '二十八', '二十九', '三十', '三十一']
      string = arr[number]
      return string
      
    },
  getLunarDateString (lunarDate) {
      if (!lunarDate.lunarDay) return;
      var data = {},
          lunarYear = lunarDate.lunarYear,
          lunarMonth = lunarDate.lunarMonth,
          lunarDay = lunarDate.lunarDay;

      data.tg = this.lunar.tg.charAt((lunarYear - 4) % 10);
      data.dz = this.lunar.dz.charAt((lunarYear - 4) % 12);
      data.year = this.lunar.year.charAt((lunarYear - 4) % 12);
      data.month = lunarMonth < 1 ? '(闰)' + this.lunar.month.charAt(-lunarMonth - 1) : this.lunar.month.charAt(lunarMonth - 1);

      data.day = (lunarDay < 11) ? "初" : ((lunarDay < 20) ? "十" : ((lunarDay < 30) ? "廿" : "三十"));
      if (lunarDay % 10 != 0 || lunarDay == 10) {
          data.day += this.lunar.number.charAt((lunarDay - 1) % 10);
      }
      return data;
    }
  }
}