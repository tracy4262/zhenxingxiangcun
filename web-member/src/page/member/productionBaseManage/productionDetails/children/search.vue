<template>
<div>
    <Modal
      v-model="objData.modalMa"
      title="请选择土地类型"
      @on-ok="ok"
      @on-cancel="cancel">
      <Row class="ma_head">
        <Col span="24">
          <Input v-model="changeName" placeholder="请选择土地类型" disabled/>
        </Col> 
      </Row>
      <Row class="ma_model">
          <Col span="8" class="ma_model_col ma_ul">
            <ul>  
              <template v-for="(item,index) in formData">
                <li @click="formDatas(index,$event)">
                  {{item.name}}
                  <i class="ivu-icon ivu-icon-ios-arrow-right ma_icon"></i>
                </li>
              </template>
            </ul>
          </Col>

          <Col span="8" class="ma_model_col ma_ul ma_ul_2">
            <ul>
              <template v-for="(item,index) in formDataChildren">
                <li @click="formDatasChild(index,$event,item.name)">
                  {{item.name}}
                </li>
              </template>
            </ul>
          </Col>

          <Col span="8">
            <Row class="ma_search">
              <Col span="24">
                <Input v-model="searchData" icon="ios-search" placeholder="请输入搜索内容"  @on-click="onSearch"></Input>
              </Col> 
            </Row>
            <Row>
              <Row>
                <Col>
                  <span style="line-height: 30px;padding-left: 10px;">常用类型</span>
                </Col>
              </Row>
              <Row class="ma_search_ul">
                <Col span="24" style="height: 215px;">
                  <ul>
                    <template v-for="(item,index) in formList">
                      <li @click="delChange(item.name,$event)">
                        {{item.name}}
                        <i @click="delLists(index,$event)" class="ivu-icon ma_icon ivu-icon-ios-close"></i>
                      </li>
                    </template>
                    <li v-show="formList.length===0">
                      暂无数据
                    </li>
                  </ul>
                </Col> 
              </Row>
            </Row>
          </Col>
      </Row>
    </Modal>
</div>
</template>

<script>
export default {
  props: {
  	objData: {
  	}
  },
  data () {
    return {
      formData:[
        {
          value: '01',
          name: '草地',
          children: [
            {
              value: '01',
              name: '人工牧草地'
            },
            {
              value: '02',
              name: '天然牧草地'
            },
            {
              value: '03',
              name: '其他草地'
            },
          ]
        },
        {
          value: '02',
          name: '林地',
          children: [
            {
              value: '01',
              name: '人工林地'
            },
            {
              value: '02',
              name: '天然林地'
            },
            {
              value: '03',
              name: '其他林地'
            },
          ]
        },
      ],
      formDataChildren: [],
      formList: [
        {
          value: '01',
          name: '林地-人工林地'
        },
        {
          value: '02',
          name: '林地-天然林地'
        },
        {
          value: '03',
          name: '草地-人工牧草地'
        }
      ],
      formList1: [
        {
          value: '01',
          name: '林地-人工林地'
        },
        {
          value: '02',
          name: '林地-天然林地'
        },
        {
          value: '03',
          name: '草地-人工牧草地'
        }
      ],
      changeName: '',
      isName: '',
      searchData: '',
      two: false
    }
  },
  methods:{
    ok(){
      if(this.two === false){
        this.changeName = ''
        this.$emit('changeName',this.changeName)
        return false
      }else{
        this.$emit('changeName',this.changeName)
        let json = JSON.stringify(this.formList1)
        this.formList = JSON.parse(json)
        this.searchData = ''
        this.two = false
      }
    	
    },
    cancel(){
      this.changeName = ''
      this.noColor()
      this.formDataChildren = []
      let json = JSON.stringify(this.formList1)
      this.formList = JSON.parse(json)
      this.searchData = ''
      this.two = false
    },

    // 取消变色
    noColor(){
      let lis = document.querySelectorAll('.ma_ul li')
       Array.prototype.slice.call(lis).map(function(item){
        item.className = ''
      })
    },

    // 1级列表click
    formDatas(index,e){
      this.noColor()
      e.target.className = 'ma_color'

      // 常用类型样式清空
      let lis = document.querySelectorAll('.ma_search_ul li')
      Array.prototype.slice.call(lis).map(function(item){
        item.className = ''
      })

      // 记录1级列表name 并赋值2级列表
      this.formDataChildren = this.formData[index].children
      this.isName = this.formData[index].name
      this.changeName = this.formData[index].name
    },

    // 2级列表click
    formDatasChild(index,e,name){
      let lis = document.querySelectorAll('.ma_ul_2 li')
      Array.prototype.slice.call(lis).map(function(item){
        item.className = ''
      })
      e.target.className = 'ma_color'

      // 记录2级name 并赋值
      this.changeName = this.isName + '-' + name
      this.two = true
    },

    // 搜索类型
    onSearch(){
      let that = this

      // 空值搜全部
      let json = JSON.stringify(that.formList1)
      if( that.searchData === ''){
        that.formList = JSON.parse(json)
        return false
      }

      // 模糊搜索
      let result = []
      this.formList.map(function(item){
        if( item.name.indexOf(that.searchData) === 0){
          result.push(item.name)
        }
        return result
      })
      if(result.length !== 0){
        this.formList.length = result.length
        this.formList.map(function(item,index){
          item.name = result[index]
        })
      }else{
        this.formList = []
      }
    },

    // 常用类型选择
    delChange(name,e){
      if(e.target.tagName === 'LI'){
        this.noColor()
        this.changeName = name
        this.formDataChildren = []
        this.two = true
      }
    },

    // 类型删除
    delLists(index,e){
      if(e.target.tagName === 'I'){
        this.formList.splice(index,1)
      }
    }
  }
}
</script>
<style scoped>

.ma_head{margin-bottom: 10px;}

.ma_model{height: 300px;border: 1px solid #e3e3e3;}
.ma_model_col{border-right: 1px solid #e3e3e3;height: 100%;}
.ma_model_col:nth-child(3){border-right: 0;}

.ma_model ul{height: 100%;overflow: scroll;}
.ma_model ul li{line-height: 40px;padding-left: 20px;position: relative;cursor: pointer;}
.ma_icon{position: absolute;top: 15px;right: 20px;}
.ma_icons{position: absolute;top: 15px;right: 20px;width: 10px;height: 10px;background: red;}

.ma_search{padding: 10px;border-bottom: 1px solid #e3e3e3;}

.ma_color{color: #2d8cf0;background: #efefef;}
</style>
