<template>
  <Modal v-model="highFilterShow" width="640" :mask-closable="false" :closable="false" :transfer="transfer">
    <section class="vui-high-filter">
      <div class="vui-high-filter-item">
        <Row type="flex" justify="center" align="middle" class="vui-high-filter-letter">
          <Col span="11">
            按拼音搜索
            <Dropdown placement="bottom-start">
              <span class="vui-high-filter-letter-val">{{letterVal}}<Icon type="ios-arrow-down"></Icon></span>
              <DropdownMenu slot="list">
                <Row class="pd5 tc" type="flex" align="middle">
                  <Col span="4" :key="index" v-for="(item,index) in letter">
                    <div @click="handleSelLetter(item,index)" class="vui-high-filter-letter-item" :class="{'on':item.checked}" :style="{'font-size': index === 0 ? '14px' : ''}">{{item.name}}</div>
                  </Col>
                </Row>
              </DropdownMenu>
            </Dropdown>
          </Col>
          <Col span="13">
            <Row class="vui-filter-search">
              <Col span="20">
                <Input v-model="keyword" placeholder="请输入搜索关键字"></Input>
              </Col>
              <Col span="4">
                <Button type="default" long @click="handleSearch"><Icon type="ios-search" size="18"></Icon></Button>
              </Col>
            </Row>
          </Col>
        </Row>
      </div>
      <divider v-if="!simple" class="mb10 mt10" />

      <!-- 条件 -->
      <div @click="classifyShow = !classifyShow" v-if="!simple">
        <div class="vui-high-filter-item" v-if="selClassifyText.length && !classifyShow">
          当前选中分类：<span v-for="(item, index) in selClassifyText" :key="index" class="sel-text">{{item.label}}&emsp;</span>
        </div>
        <template v-else>
          <div v-if="!classifyShow" class="vui-high-filter-item tc classify-title">点击选择分类</div>
        </template>
      </div>
      <divider v-if="!classifyShow" class="mb10 mt10" />

      <div class="vui-high-filter-classify" v-if="classifyShow">
        <div class="vui-high-filter-item" v-if="selClassifyData.length">
          <Row type="flex" align="middle">
            <Col span="3">当前选中</Col>
            <Col span="21">
              <Tag
              type="border"
              closable
              :key="index"
              v-for="(item,index) in selClassifyData"
              @on-close="onClassifyCancel"
              :name="item.label">{{ item.label }}</Tag>
              <!-- <Tag color="red" closable @on-close="clearAll" v-show="selClassifyData.length">清除全部</Tag> -->
            </Col>
          </Row>
        </div>
        <divider v-if="selClassifyData.length" class="mb10 mt10" />
        <Row v-if="!simple">
          <vui-filter-list
          :cols="cols"
          :data="classifyDatas"
          @on-classify-click="onClassifyClick">
          </vui-filter-list>
        </Row>
        <div class="tc pd10" style="border-top:1px solid #d7dde4" v-if="!simple">
          <Button type="primary" class="mr10" @click="handleSaveClassify">确定</Button>
          <Button type="default" @click="handleCancelClassify">取消</Button>
        </div>
      </div>

      <!-- 结果分类 -->
      <div class="vui-high-filter-product scroll" v-if="!classifyShow">
        <Row v-if="resultDatas.length">
          <template v-for="(item, index) in resultDatas" :key="index">
            <Col span="6" :key="index" :class="{'on':item.checked}" @click.native="handleSelResult(item,index)">
              <div class="vui-high-filter-product-item ell" :key="index">{{item.label}}</div>
            </Col>
          </template>
        </Row>
        <div class="tc pt50" v-else>
          <Icon type="sad-outline" size="60"></Icon>
          <p class="mt10">哎呀~没有其他数据辣~</p>
        </div>
      </div>
      <divider class="mb10" v-if="selResultData.length" />

      <div class="vui-high-filter-item" v-if="selResultData.length">
        <Row type="flex" align="middle">
          <Col span="3">当前选中</Col>
          <Col span="21" style="white-space:pre-wrap;">
            <Tag
            type="border"
            closable
            :key="index"
            v-for="(item,index) in selResultData" :key="index"
            @on-close="onResultCancel"
            :name="item.label">{{ item.label }}</Tag>
          </Col>
        </Row>
      </div>
    </section>
    <template slot="footer">
      <Row class="pd10 tl">
        <Col span="18">
          <Page
          :total="total"
          :current="pageCur"
          :page-size="32"
          size="small"
          show-total
          v-if="pageShow"
          @on-change="handlePageChange"></Page>
        </Col>
        <Col span="6" :offset="pageShow ? '0' : '18'">
          <Button type="primary" @click.native="handleSaveResult">确定</Button>
          <Button type="default" @click="handleCancel">取消</Button>
        </Col>
      </Row>
    </template>
  </Modal>
</template>

<script>
import vuiFilterList from './list'
import { letter } from '@/utils/letter'
import divider from '@/components/divider'
export default {
  name: 'vui-filter',
  components: {
    vuiFilterList,
    divider
  },
  props: {
    transfer: {
      type: Boolean,
      default: false
    },
    classifyDatas: Array,
    resultDatas: Array,
    loadData: {
      type: Function
    },
    simple: {
      type: Boolean,
      default: false
    },
    pageShow: {
      type: Boolean,
      default: false
    },
    total: Number,
    pageCur: Number,
    cols: {
      type: Number,
      default: 4
    },
    num: {
      type: Number,
      default: null
    }
  },
  data () {
    return {
      highFilterShow: false,
      keyword: '',
      filterLetterShow: false,
      classifyShow: false,
      productShow: false,
      letter: [],
      letterVal: '全部',
      selClassifyData: [],
      selClassifyText: [],
      selResultData: [],
      current: 1
    }
  },
  created () {
    // 字母
    this.letter = letter()
    this.letter.unshift({ name: '全部', checked: false })
  },
  methods: {
    // 字母索引选中
    handleSelLetter (item, index) {
      this.clearCheck(this.letter)
      this.letter[index].checked = !this.letter[index].checked
      this.letterVal = item.name
      if (this.simple) {
        this.$emit('on-search', this.letterVal, this.keyword, this.selResultData)
      } else {
        this.$emit('on-search', this.letterVal, this.keyword, this.selClassifyData)
      }
    },
    // 搜索
    handleSearch () {
      if (this.keyword) {
        if (this.simple) {
          this.$emit('on-search', this.letterVal, this.keyword, this.selResultData)
        } else {
          this.$emit('on-search', this.letterVal, this.keyword, this.selClassifyData)
        }
      } else {
        this.$Message.error('请输入搜索内容~')
      }
    },
    // 勾选分类
    onClassifyClick (item) {
      item.checked = !item.checked
      if (item.checked) {
        this.selClassifyData.push(item)
      } else {
        this.selClassifyData.splice(this.selClassifyData.indexOf(item.label), 1)
      }
    },
    // 删除选中分类
    onClassifyCancel (event, name) {
      this.selClassifyData.splice(this.selClassifyData.indexOf(name), 1)
      this.classifyDatas.forEach(item => {
        if (item.label === name) item.checked = false
        if (!item.children) return
        if (item.children.length) {
          item.children.forEach(child => {
            if (child.label === name) child.checked = false
          })
        }
      })
      if (this.simple) {
        this.resultDatas.forEach(item => {
          if (item.label === name) item.checked = false
        })
      }
    },
    // 删除选中结果
    onResultCancel (event, name) {
      this.resultDatas.forEach(item => {
        if (item.label === name) {
          item.checked = false
        }
      })
      this.selResultData.forEach((item, index) => {
        if (item.label === name) {
          this.selResultData.splice(index, 1)
        }
      })
    },
    // 选择结果分类
    handleSelResult (item, index) {
      if (this.num) {
        if (item.checked) {
          item.checked = false
          // 删除选中
          this.selResultData.forEach((e, i) => {
            if (e.value == item.value) {
              this.selResultData.splice(i, 1)
              return
            }
          })
        } else {
          // 已选中的数据 跟 限制选中的做对比
          if (this.selResultData.length < this.num) {
            item.checked = true
            this.selResultData.push(item)
          } else {
            item.checked = false
            this.$Message.info(`只能选择${this.num}个`)
          }
        }
        // var active = []
        // this.resultDatas.map(child => {
        //   if (child.checked) active.push(child)
        // })
        // if (active.length < this.num) {
        //   item.checked = true
        // } else {
        //   item.checked = false
        //   this.$Message.info(`只能选择${this.num}个`)
        // }
        // if (item.checked) this.selResultData.push(item)
        // this.selResultData = this.selResultData.slice(0, this.num)
      } else {
        item.checked = !item.checked
        if (item.checked) {
          this.selResultData.push(item)
        } else {
          this.selResultData.forEach((child, index) => {
            if (child.label === item.label) {
              this.selResultData.splice(index, 1)
            }
          })
        }
      }
    },
    // 保存选择分类结果
    handleSaveClassify () {
      this.selClassifyText = this.selClassifyData
      this.classifyShow = false
      this.$emit('on-get-classify', this.letterVal, this.keyword, this.selClassifyData)
    },
    handleCancelClassify () {
      this.classifyShow = !this.classifyShow
      this.clearCheck(this.classifyDatas)
      this.selClassifyData = []
    },
    // 保存最后筛选结果
    handleSaveResult () {
      this.$emit('on-get-result', this.selClassifyData, this.selResultData)
      this.highFilterShow = false
    },
    // 切页
    handlePageChange (num) {
      this.$emit('on-page-change', this.letterVal, this.keyword, this.selClassifyData, num, this.selResultData)
    },
    // 取消
    handleCancel () {
      this.highFilterShow = false
      this.handleReset()
    },
    // 重置
    handleReset () {
      this.keyword = ''
      this.clearCheck(this.letter)
      this.letterVal = '全部'
      this.selResultData = []
      this.selClassifyData = []
      this.selClassifyText = []
      this.selResultData = []
      if (this.classifyDatas) this.clearCheck(this.classifyDatas)
      if (this.resultDatas) this.clearCheck(this.resultDatas)
    },
    // 清除状态
    clearCheck (data) {
      data.forEach(item => {
        item.checked = false
        if (item.children) {
          this.clearCheck(item.children)
        }
      })
    }
  }
}
</script>

<style lang="less">
.vui-high-filter{
    position: relative;
    font-size: 14px;
  .classify-title{
    padding: 10px 0;
    &:hover{
      background:  #00c587;
      color: #fff;
    }
  }
  .vui-filter-search{
    .ivu-input,
    .ivu-btn{
      border-radius: 0
    }
    .ivu-btn{
      margin-left:-1px;
      height: 32px;
    }
  }
  &-item{
    .ivu-tag-border{
      // color: #FF7921 !important;
      position: relative;
      padding: 0 25px 0 5px;
      .ivu-icon{
        position: absolute;
        right: 5px;
        top: 3px !important;
        left: initial !important;
      }
    }
    .sel-text{
      // display:  inline-block;
      // white-space:  nowrap;
      color: #FF7921;
    }
  }
  &-letter{
    &-item{
      font-size: 16px;
      cursor: pointer;
    }
    &-val{
      display: inline-block;
      vertical-align: middle;
      width: 60px;
      border: 1px solid #d7dde4;
      height: 32px;
      line-height: 1.5;
      padding: 4px 7px;
      border-radius: 3px;
      .ivu-icon{
        position: absolute;
        right: 10px;
        top: 9px;
      }
    }
  }
  &-letter,
  &-product{
    .on{
      color: #FF7921;
    }
  }
  &-product{
    height:240px;
    overflow: auto;
    &-item{
      cursor: pointer;
      padding: 4px 10px;
    }
  }
  .scroll{
    &::-webkit-scrollbar {
      width: 8px;
      height: 8px;
    }
    &::-webkit-scrollbar-thumb {
      border-radius: 10px;
      background-color: rgba(51,51,51,.15);
    }
  }
}
</style>
