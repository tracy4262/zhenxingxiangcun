<template>
  <div>
    <div class="vui-layout">
      <Row type="flex" align="middle" justify="center" class="wiki-logo">
        <Col span="1">
          <img src="../../assets/imgs/wiki-logo.png" alt="">
        </Col>
        <Col span="3">
          <p class="title">物种百科</p>
        </Col>
      </Row>
      <wiki-search @on-get-keyword="handlekeyWord" @on-change="handleKeyWordChange"></wiki-search>
    </div>
    <div class="wiki-filter-tabs">
      <Tabs :animated="false" class="vui-layout" @on-click="handTabsClick" ref="tabs">
        <TabPane label="全部">
          <Form :label-width="90" class="vui-from-sm pt20 pb20">
            <filter-item label="按专业查询" :data="major" @on-get-filter="handleGetMajor"></filter-item>
            <filter-item label="按拼音查询" :data="letter" @on-get-filter="handleGetLetter"></filter-item>
          </Form>
        </TabPane>
        <TabPane label="动物">
          <Form :label-width="90" class="vui-from-sm pt20 pb20">
            <filter-item label="按动物查询" :data="animal" @on-get-filter="handleGetFaunaAnimal"></filter-item>
            <filter-item label="按专业查询" :data="major" @on-get-filter="handleGetMajor"></filter-item>
            <filter-item label="按拼音查询" :data="letter" @on-get-filter="handleGetLetter"></filter-item>
          </Form>
        </TabPane>
        <TabPane label="植物">
          <Form :label-width="90" class="vui-from-sm pt20 pb20">
            <filter-item label="按植物查询" :data="plant" @on-get-filter="handleGetFaunaPlant"></filter-item>
            <filter-item label="按专业查询" :data="major" @on-get-filter="handleGetMajor"></filter-item>
            <filter-item label="按拼音查询" :data="letter" @on-get-filter="handleGetLetter"></filter-item>
          </Form>
        </TabPane>
        <TabPane label="最近更新">
        </TabPane>
      </Tabs>
    </div>

    <div style="background:#fcfcfc;min-height:500px" class="pb20">
      <div class="vui-layout">
        <Form :label-width="100" >
          <!-- 数据列表 -->
          <!-- 全部 -->
          <list-item :data="allResult" v-if="show === 0" :more="allResultMore"></list-item>

          <!-- 动物 -->
          <list-item :data="botanyData" v-if="show === 1" :more="botanyDataMore"></list-item>

          <!-- 植物 -->
          <list-item :data="animalData" v-if="show === 2" :more="animalDataMore"></list-item>

          <!-- 最近更新 -->
          <list-item :data="updatesResult" v-if="show === 3" :more="updatesResultMore"></list-item>
          <vui-loading :circle="2" v-if="loadingShow"></vui-loading>
        </Form>
      </div>
    </div>

  </div>
</template>

<script>
import {letter} from '~utils/letter'
import wikiSearch from '~components/wiki-search'
import filterItem from './components/filter-item'
import listItem from './components/list-item'
import vuiLoading from '~components/vui-loading'
export default {
  components: {
    wikiSearch,
    filterItem,
    listItem,
    vuiLoading
  },
  data () {
    return {
      show: 0,
      loadingShow: false,
      allResultMore: false,
      botanyDataMore: false,
      animalDataMore: false,
      updatesResultMore: false,
      keyword: '',
      fclassifiedidAnimal: '',
      fclassifiedidPlant: '',
      findustriaclassifiedid: '',
      fpinyin: '',
      plant: [],
      animal: [],
      major: [],
      letter: [],
      result: [],
      // 最近更新
      updatesPageNum: 1,
      updatesTotal: 0,
      updatesSize: 24,
      updatesCurrentPage: 1,
      updatesResult: [],
      // 植物
      animalPageNum: 1,
      animalPageSize: 24,
      animalPageTotal: 0,
      animalCurrentPage: 1,
      animalData: [],
      // 动物
      botanyPageNum: 1,
      botanyPageSize: 24,
      botanypageTotal: 0,
      botanyCurrentPage: 1,
      botanyData: [],
      // 全部
      allPageNum: 1,
      allPageSize: 24,
      allpageTotal: 0,
      allCurrentPage: 1,
      allResult: []
    }
  },
  created () {
    // 拼音
    this.letter = letter()
    this.letter.unshift({
      name: '全部',
      checked: false
    })
    // 默认取全动植物数据
    this.loadFauna('0')
    this.loadFauna('1')
    // 取专业
    this.major = [{
      name: '全部',
      findustriaclassifiedid: '',
      checked: false
    }, {
      name: '农业',
      findustriaclassifiedid: 'A01',
      checked: false
    }, {
      name: '林业',
      findustriaclassifiedid: 'A02',
      checked: false
    }, {
      name: '畜牧业',
      findustriaclassifiedid: 'A03',
      checked: false
    }, {
      name: '水产业',
      findustriaclassifiedid: 'A04',
      checked: false
    }]
    // 取物种列表
    this.loadSpeciesList()
    // 最近更新数据
  },
  methods: {
    // 筛选条件
    handTabsClick (name) {
      this.show = name
      // 全部
      if (name === 0) {
        this.loadSpeciesList(1)
      }
      // 动物
      if (name === 1) {
        this.fclassifiedidAnimal = '0'
        this.getBotanyData(1)
      }
      // 植物
      if (name === 2) {
        this.fclassifiedidPlant = '1'
        this.getAnimalData(1)
      }
      // 最近更新
      if (name === 3) {
        this.getUpdatesDatas(1)
      }
    },
    // 动物更新
    getBotanyData (type) {
      this.loadingShow = true
      this.$api.post('wiki/api/species/getSpeciesListInfo', {
        keywords: this.keyword,
        pageSize: this.botanyPageSize,
        pageNum: this.botanyPageNum,
        fclassifiedid: [this.fclassifiedidAnimal],
        findustriaclassifiedid: this.findustriaclassifiedid,
        fpinyin: this.fpinyin
      }).then(res => {
        this.loadingShow = false
        console.log('this.botanyData.length', this.botanyData.length)
        if (res.data.totalNum === this.botanyData.length) {
          this.botanyDataMore = true
        } else {
          this.botanypageTotal = res.data.totalNum
          if (type) {
            this.botanyData = res.data.speciesListData ? res.data.speciesListData : []
          } else {
            this.botanyData = [...this.botanyData, ...res.data.speciesListData]
          }
        }
      })
    },
    // 获取植物
    getAnimalData (type) {
      this.loadingShow = true
      this.$api.post('wiki/api/species/getSpeciesListInfo', {
        keywords: this.keyword,
        pageSize: this.animalPageSize,
        pageNum: this.animalPageNum,
        fclassifiedid: [this.fclassifiedidPlant],
        findustriaclassifiedid: this.findustriaclassifiedid,
        fpinyin: this.fpinyin
      }).then(res => {
        this.animalPageTotal = res.data.totalNum
        if (type) {
          this.animalData = res.data.speciesListData ? res.data.speciesListData : []
        } else {
          this.animalData = [...this.animalData, ...res.data.speciesListData]
        }
        this.loadingShow = false
      })
    },
    // 最近更新
    getUpdatesDatas (type) {
      this.loadingShow = true
      this.$api.post('wiki/api/species/listSpecies', {
        keywords: this.keyword,
        sortType: '2',
        pageNum: this.updatesPageNum,
        pageSize: this.updatesSize
      }).then(res => {
        this.updatesTotal = res.total
        if (type) {
          this.updatesResult = res.data ? res.data : []
        } else {
          this.updatesResult = [...this.updatesResult, ...res.data]
        }
        this.loadingShow = false
      })
    },
    // 取筛选条件，改变当前选择的数据，调用查询接口
    // 按专业查询
    handleGetMajor (data) {
      this.findustriaclassifiedid = data.findustriaclassifiedid
      this.search()
    },
    // 按拼音查询
    handleGetLetter (data) {
      this.fpinyin = data.name === '全部' ? '' : data.name
      this.search()
    },
    // 按动植物查询
    handleGetFaunaAnimal (data) {
      this.fclassifiedidAnimal = data.classId
      this.search()
    },
    // 按植物物查询
    handleGetFaunaPlant (data) {
      this.fclassifiedidPlant = data.classId
      this.search()
    },
    // 取动植物
    loadFauna (id) {
      this.$api.post('wiki/speciesclass/listSpeciesclass', {
        parentId: id
      }).then(res => {
        let d = res.data
        let arr = []
        if (!d.length) return
        d.forEach(item => {
          item.checked = false
          arr.push({
            name: item.className,
            checked: item.checked,
            classId: item.classId,
            indexId: item.indexId
          })
        })
        if (id === '0') {
          this.animal = arr
        } else {
          this.plant = arr
        }
      })
    },
    // 搜索
    handlekeyWord (keyword) {
      this.keyword = keyword
      this.search()
    },
    handleKeyWordChange (keyword) {
      this.keyword = keyword
    },
    search () {
      window.scrollTo(0, 0)
      this.allResultMore = false
      this.botanyDataMore = false
      this.animalDataMore = false
      this.updatesResultMore = false
      // 全部
      if (this.show === 0) {
        this.allPageNum = 1
        // this.loadSpeciesList()
        this.loadingShow = true
        this.$api.post('wiki/api/species/listSpecies', {
          keywords: this.keyword,
          pageNum: this.allPageNum,
          pageSize: this.allPageSize,
          findustriaclassifiedid: this.findustriaclassifiedid,
          fpinyin: this.fpinyin
        }).then(res => {
          this.allResult = res.data ? res.data : []
          this.allpageTotal = res.total
          this.allCurrentPage = res.page.currentPage
          this.loadingShow = false
        })
      }
      // 动物
      if (this.show === 1) {
        // this.getBotanyData()
        this.loadingShow = true
        this.botanyPageNum = 1
        this.$api.post('wiki/api/species/getSpeciesListInfo', {
          keywords: this.keyword,
          pageSize: this.botanyPageSize,
          pageNum: this.botanyPageNum,
          fclassifiedid: [this.fclassifiedidAnimal],
          findustriaclassifiedid: this.findustriaclassifiedid,
          fpinyin: this.fpinyin,
          parentId: 1
        }).then(res => {
          this.botanypageTotal = res.data.totalNum
          this.botanyData = res.data.speciesListData ? res.data.speciesListData : []
          this.loadingShow = false
        })
      }
      // 植物
      if (this.show === 2) {
        // this.getAnimalData()
        this.loadingShow = true
        this.animalPageNum = 1
        this.$api.post('wiki/api/species/getSpeciesListInfo', {
          keywords: this.keyword,
          pageSize: this.animalPageSize,
          pageNum: this.animalPageNum,
          fclassifiedid: [this.fclassifiedidPlant],
          findustriaclassifiedid: this.findustriaclassifiedid,
          fpinyin: this.fpinyin,
          parentId: 0
        }).then(res => {
          this.animalPageTotal = res.data.totalNum
          this.animalData = res.data.speciesListData ? res.data.speciesListData : []
          this.loadingShow = false
        })
      }
      // 最近更新
      if (this.show === 3) {
        this.loadingShow = true
        this.updatesPageNum = 1
        this.$api.post('wiki/api/species/listSpecies', {
          keywords: this.keyword,
          pageNum: this.updatesPageNum,
          pageSize: this.updatesSize,
          fpinyin: this.fpinyin
        }).then(res => {
          this.updatesTotal = res.total
          this.updatesResult = res.data ? res.data : []
          this.loadingShow = false
        })
      }
    },
    // 取全部
    loadSpeciesList (type) {
      this.loadingShow = true
      this.$api.post('wiki/api/species/listSpecies', {
        keywords: this.keyword,
        pageNum: this.allPageNum,
        pageSize: this.allPageSize,
        findustriaclassifiedid: this.findustriaclassifiedid,
        fpinyin: this.fpinyin
      }).then(res => {
        if (type) {
          this.allResult = res.data ? res.data : []
        } else {
          this.allResult = [...this.allResult, ...res.data]
        }
        this.allpageTotal = res.total
        this.allCurrentPage = res.page.currentPage
        this.loadingShow = false
      })
    },
    scrollLoad () {
      // 滚动条距离顶部的高度
      let scroll = document.documentElement.scrollTop || document.body.scrollTop
      // 当前页面的总高度
      let windowH = document.documentElement.clientHeight || document.body.clientHeight
      // 当前可视的页面高度
      let scrollH = document.documentElement.scrollHeight || document.body.scrollHeight
      // 距离顶部+当前高度 >=文档总高度 即代表滑动到底部
      if (scrollH <= scroll + windowH + 1 && !this.loadingShow) {
        // 滚动条到达底部
        if (this.show === 3) {
          // 说明是最近更新
          this.updatesResultMore = false
          if (this.updatesTotal > this.updatesResult.length) {
            this.updatesPageNum++
            this.getUpdatesDatas()
          } else {
            this.updatesResultMore = true
          }
        }
        if (this.show === 0) {
          this.allResultMore = false
          if (this.allpageTotal > this.allResult.length) {
            // 全部
            this.allPageNum++
            this.loadSpeciesList()
          } else {
            this.allResultMore = true
          }
        }
        if (this.show === 2) {
          this.animalDataMore = false
          if (this.animalPageTotal > this.animalData.length) {
            // 植物 parentId 0动物 1植物
            this.animalPageNum++
            this.getAnimalData()
          } else {
            this.animalDataMore = true
          }
        }
        if (this.show === 1) {
          this.botanyDataMore = false
          if (this.botanypageTotal > this.botanyData.length) {
            // 动物 parentId 0动物 1植物
            this.botanyPageNum++
            this.getBotanyData()
          } else {
            this.botanyDataMore = true
          }
        }
      }
    }
  },
  mounted () {
    window.addEventListener('scroll', this.scrollLoad)
  },
  destroyed () {
    window.removeEventListener('scroll', this.scrollLoad)
  }
}
</script>

<style lang="scss">


.wiki{
  &-logo{
    margin-top: 90px;
    .title{
      font-size: 34px;
      font-weight: 700;
      font-family: serif;
      padding-left: 5px;
    }
  }
  &-filter-tabs {
    position: relative;
    border-bottom: 1px solid #E7E7E7;
    &:before{
      content: '';
      position: absolute;
      border-top: 1px solid #E7E7E7;
      left: 0;
      right: 0;
      top: 35px;
    }
    .ivu-tabs-bar{
      margin-bottom: 0
    }
  }
}
</style>
