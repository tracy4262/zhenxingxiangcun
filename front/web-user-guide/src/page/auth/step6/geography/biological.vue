
<template>
 <!-- 生物资源信息 -->
  <div class="pd20">
    <Title :title="title"  edit :id="id" :yearId="yearId"></Title>
    <Form :label-width="100" label-position="left" class="pd20 mt40">
      <Row  :gutter="38">
        <Col span="8">
          <FormItem label="权限">
            <Switch class="ml20"  size="large" v-model="status" :disabled="true">
              <span slot="open">公开</span>
              <span slot="close">隐藏</span>
            </Switch>
          </FormItem>
        </Col>
      </Row>
      <Row  :gutter="40">
        <Col span="12">
            <Row  :gutter="16" class="pb20">
              <Col span="12"> <b>野生动物</b> </Col>
            </Row>
            <Row  :gutter="16" class="pb20">
              <Col span="9"> 野生动物名称 </Col>
              <Col span="11"> 
                  <Input v-model="animalLists" readonly  :disabled="true"
                  class="pinput-clear" 
                  @on-focus="handleFilterModal('speciFilter2', 'animal')" 
                  icon="ios-close" 
                  @on-click="inputClear('animal')" @on-change="changePreview"/>
              </Col>
            </Row>
            <Row  :gutter="16" class="pb20" v-for="(item, index) in animalList">
              <Col span="9"> 
                <p>{{item.animalType}}</p>
              </Col>
              <Col span="11"> 
                <Select v-model="item.animalName" multiple @on-open-change="selectChangePreview" :disabled="true">
                    <Option v-for="item in animalData" :value="item.label" :key="item.label">{{ item.label }}</Option>
                </Select>
              </Col>
              <Col span="2"> <Button  v-if="index > 1" :disabled="true">删除</Button> </Col>
            </Row>
            <!-- <Button type="primary" ghost @click="animalAdd" class="mt20 btn-light-primary" icon="md-add">增加</Button> -->
        </Col>
         <Col span="12">
            <Row  :gutter="16" class="pb20">
              <Col span="12"> <b>野生植物</b> </Col>
            </Row>
            <Row  :gutter="16" class="pb20">
              <Col span="9"> 野生植物名称 </Col>
              <Col span="11"> 
                  <Input v-model="plantLists" readonly  :disabled="true"
                    class="pinput-clear" 
                    @on-focus="handleFilterModal('speciFilter2', 'plant')" 
                    icon="ios-close" 
                    @on-click="inputClear('plant')" @on-change="changePreview"/>
              </Col>
            </Row>
            <Row  :gutter="16" class="pb20" v-for="(item, index) in plantList">
              <Col span="9"> 
                <p ></p>{{item.plantType}} &nbsp;</p>
              </Col>
              <Col span="11"> 
                <Select v-model="item.plantName" multiple  @on-open-change="changePreview" :disabled="true">
                    <Option v-for="item in plantData" :value="item.label" :key="item.label">{{ item.label }}</Option>
                </Select>
              </Col>
              <Col span="2"> <Button  v-if="index > 1" :disabled="true">删除</Button> </Col>
            </Row>
            <!-- <Button type="primary" ghost @click="plantAdd" class="mt20 btn-light-primary" icon="md-add" >增加</Button> -->
        </Col>
      </Row>
    </Form>
    <Title title="文字预览" class="mt40"></Title>
    <div class="pd20 tc pt30">
      <Input v-model="textPreview.text_preview" type="textarea" :autosize="{minRows: 4,maxRows: 10}" ></Input>
      <Button type="primary" @click="handleSave" class="mt40">保存</Button>
    </div>
     <!-- 擅长物种 -->
		<vui-filter 
            ref="speciFilter2" 
            :cols="2" 
            :num="100000" 
            :pageShow="true" 
            :total="total2" 
            :pageCur="pageCur2" 
            :classifyDatas="speciClassifyDatas" 
            :resultDatas="speciResultDatas" 
            :load-data="loadSpeciDatas" 
            @on-search="handleSpeciSearch" 
            @on-get-classify="handleGetSpeciClassify" 
            @on-get-result="handleGetSpeciResult" 
            @on-page-change="handleSpeciPageChange" />
  </div>
</template>

<script>
import Title from '../../components/title'
import vuiFilter from '~components/vuiFilter/filter'
export default {
  props: {
    yearId: {
      type: String
    },
    id: {
      type: String
    },
    appId: {
      type: String
    }
  },
  components: {
    Title,
    vuiFilter
  },
  data () {
    return {
      total2: 0,
      pageCur2: 1,
      speciClassifyDatas: [{
          label: '动物',
          value: '0',
          classId: '',
          loading: false,
          checked: false,
          children: []
        },
        {
          label: '植物',
          value: '1',
          classId: '',
          loading: false,
          checked: false,
          children: []
        }
      ],
      speciResultDatas: [],
      status: true,
      // 植物
      plantList: [
        {plantType: '国家Ⅰ级濒危植物', plantName: []},
        {plantType: '国家Ⅱ级濒危植物', plantName: []},
      ],
      plantLists: '', // 所选择的 植物 名称
      plantData: [], // 植物 名称下拉 数据
      // 动物
      animalList: [
        {animalType: '国家Ⅰ级濒危动物', animalName: []},
        {animalType: '国家Ⅱ级濒危动物', animalName: []}
      ],
      animalLists: '', // 所选择的 动物 名称
      animalData: [], // 动物 名称下拉 数据
      animalDatas: [], // 动物 名称下拉 数据
      textPreview: {},
      title: '',
      activeType: '',
      activeId: ''
      
    }
  },
  created() {
    // 取相关物种结果
    this.loadSpeciResult('', '', [], this.pageCur2, [])
    // this.handleInit()
  },
  methods: {
    //初始化取数据
    handleInit () {
      this.$api.post('/member-reversion/physicalGeography/findBiologicalResources',{
        templateId: this.$template.id, user_id: this.$user.loginAccount, year_id: this.yearId, parent_id: this.id
      }).then(response => {
        if (response.code === 200) {
          this.title = response.data.biologicalResources_name
          this.status = response.data.status
          this.activeId = response.data.biologicalResources.id
          if (!response.data.textPreview.text_preview) {
            response.data.textPreview.text_preview = `已发现野生动物有（）种，其中：（）有（）；已发现野生植物有（）种，其中：（）有（）。`
          }
          this.textPreview = response.data.textPreview
          let data = response.data.biologicalResources
          // 植物
          this.plantList = data.plantList
          // 所选择的 植物 名称
          if (data.plantLists) {
            this.plantLists = data.plantLists
          }
          // 植物 名称下拉 数据
          if (data.plantData) {
            this.plantData = data.plantData
          }
          // 动物
          this.animalList = data.animalList
          // 所选择的 动物 名称
          if (data.animalLists) {
            this.animalLists = data.animalLists
          }
          // 动物 名称下拉 数据
          if (data.animalData) {
            this.animalData = data.animalData
          }
        }
      })
    },
    // 保存
    handleSave () {
      this.textPreview.is_complete = true
      let list = {
        biologicalResources: {
          // 植物
          plantList: this.plantList,
          plantLists: this.plantLists, // 所选择的 植物 名称
          plantData: this.plantData, // 植物 名称下拉 数据
          // 动物
          animalList: this.animalList,
          animalLists: this.animalLists, // 所选择的 动物 名称
          animalData: this.animalData, // 动物 名称下拉 数据
          id: this.activeId,
        },
        status: true,
        biologicalResources_name: '生物资源信息',
        textPreview: this.textPreview,
        sys_dict_id: this.id,
        yearId: this.yearId,
        templateId: this.$template.id,
        user_id: this.$user.loginAccount
      }
      this.$api.post('/member-reversion/physicalGeography/saveBiologicalResources', list).then(response => {
        if (response.code === 200) {
          this.$emit('on-save')
          this.$Message.success('保存成功')
          this.handleInit()
        }
      })
    },
    // 文字预览 
    changePreview (type) {
      let str = ''
      if (this.animalData.length) {
         str += `已发现野生动物有${this.animalData.length}种，`
         this.animalList.forEach(e => {
           if (e.animalType && e.animalName.length) {
            str += `其中：${e.animalType}有${e.animalName.join('、')}；`
           }
         })
      }
      if (this.plantData.length) {
         str += `已发现野生植物有${this.plantData.length}种，`
         this.plantList.forEach(e => {
           if (e.plantType && e.plantName.length) {
            str += `其中：${e.plantType}有${e.plantName.join('、')}；`
           }
         })
      }
      if (str) {
        str = `${str.substring(0,str.length-1)}。`
      }
      this.textPreview.text_preview = str
    },
    // 下拉面板关闭时触发
    selectChangePreview (e) {
      if (!e) {
        this.changePreview()
      }
    },
    // 植物 删除
    plantDel (item, index) {
      this.$Modal.confirm({
          title: '是否确定删除',
          onOk:()=>{
            this.plantList.splice(index, 1)
            this.$Message.success('删除成功')
          },
          okText:'确定',
          cancelText:'取消'
      })
    },
    // 动物 删除
    animalDel (item, index) {
      this.$Modal.confirm({
          title: '是否确定删除',
          onOk:()=>{
            this.animalList.splice(index, 1)
            this.$Message.success('删除成功')
          },
          okText:'确定',
          cancelText:'取消'
      })
    },
    // 植物双击编辑
    plantEdit (item, index, ref) {
      this.plantList.forEach(e => {
        e.edit = false
      })
      item.edit = true
      this.plantList.splice(index, 1, item)
      // 数据更新后自动获取输入框的焦点
      this.$nextTick(e => {
        this.$refs[`plant${index}`][0].focus()
      })
    },
    // 动物双击编辑
    animalEdit (item, index, ref) {
      this.animalList.forEach(e => {
        e.edit = false
      })
      item.edit = true
      this.animalList.splice(index, 1, item)
      // 数据更新后自动获取输入框的焦点
      this.$nextTick(e => {
        this.$refs[`animal${index}`][0].focus()
      })
    },
    // 植物失去焦点保存
    plantOnBlur (item, index, ref) {
      item.edit = false
      this.plantList.splice(index, 1, item)
    },
     // 动物失去焦点保存
    animalOnBlur (item, index, ref) {
      item.edit = false
      this.animalList.splice(index, 1, item)
    },
    // 植物增加
    plantAdd () {
      this.plantList.forEach(e => {
        e.edit = false
      })
      let list = {plantType: '', edit: true, plantName: []}
      this.plantList.push(list)
      this.$nextTick(e => {
        this.$refs[`plant${this.plantList.length-1}`][0].focus()
      })
    },
    // 动物增加
    animalAdd () {
      this.animalList.forEach(e => {
        e.edit = false
      })
      let list = {animalType: '', edit: true, animalName: []}
      this.animalList.push(list)
      this.$nextTick(e => {
        this.$refs[`animal${this.animalList.length-1}`][0].focus()
      })
    },
    // 高级搜索弹窗
    handleFilterModal (name, type) {
      this.activeType = type
      // 数据回显
      if (type == 'animal') {
        // 取相关物种结果
        this.$refs.speciFilter2.selClassifyData = this.animalData
        this.$refs.speciFilter2.selResultData = this.animalData
        this.loadSpeciResult('', '', [], this.pageCur2, this.animalData)
      }
      if (type == 'plant') {
        this.$refs.speciFilter2.selClassifyData = this.plantData
        this.$refs.speciFilter2.selResultData = this.plantData
        this.loadSpeciResult('', '', [], this.pageCur2, this.plantData)
      }
      this.$refs[name].highFilterShow = true
    },
    //清除文本框
    inputClear(item){
      if (item == 'animal') {
        this.animalLists = ''
        this.$refs.speciFilter2.handleReset();
        this.handleGetSpeciResult('', []);
      }
      if (item == 'plant') {
        this.plantLists = ''
        this.$refs.speciFilter2.handleReset();
        this.handleGetSpeciResult('', []);
      }
    },
    // 物种
    loadSpeciResult(letter, keyword, classify, num, result) {
      if(classify.length) {
        var arr = []
        var type = ''
        classify.forEach(item => {
          arr.push(item.classId)
          if(item.value !== undefined) type = item.value
        })
      } else {
        arr = null
      }
      this.$api.post('/member/specicesClass/findSpecies', {
        keywords: keyword,
        fpinyin: letter === '全部' ? '' : letter,
        fclassifiedid: arr,
        type: type,
        pageNum: num,
        pageSize: 32
      }).then(res => {
        var data = res.data
        this.total = data.total
        if(result) {
          result.forEach(item => {
            data.list.forEach((child, index) => {
              if(child.label === item.label) {
                child.checked = true
              }
            })
          })
        } else {
          data.list.forEach(child => {
            child.checked = false
          })
        }
        this.speciResultDatas = data.list
      })
    },
    loadSpeciDatas(item, callback) {
      item.loading = true
      this.$api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
        item.loading = false
        var d = res.data
        d.forEach(child => {
          child.checked = false
          child.label = child.className
        })
        item.children = d
        callback()
      })
    },
    // 擅长物种
    handleGetSpeciResult(classify, result) {
      let label = []
      let value = []
      result.forEach(item => label.push(item.label))
      result.forEach(item => value.push(item.value))
      if (this.activeType == 'animal') { // 动物
        this.animalData = result
        this.animalDatas = result
        this.animalLists = label.join(' ')
      }
      if (this.activeType == 'plant') { // 植物
        this.plantData = result
        this.plantDatas = result
        this.plantLists = label.join(' ')
      }
      // this.changePreview()
    },
    handleSpeciSearch(letter, keyword, classify, result) {
      this.loadSpeciResult(letter, keyword, classify, this.pageCur2, result)
      this.$Message.success('筛选完成！')
    },
    handleGetSpeciClassify(letter, keyword, classify, result) {
      this.loadSpeciResult(letter, keyword, classify, this.pageCur2, result)
      this.$Message.success('筛选完成！')
    },
    handleSpeciPageChange(letter, keyword, classify, num, result) {
      this.pageCur2 = num
      this.loadSpeciResult(letter, keyword, classify, this.pageCur2, result)
      this.$Message.success('筛选完成！')
    },
    loadSpeciResult(letter, keyword, classify, num, result) {
      if(classify.length) {
        var arr = []
        var type = ''
        classify.forEach(item => {
          arr.push(item.classId)
          if(item.value !== undefined) type = item.value
        })
      } else {
        arr = null
      }
      this.$api.post('/member/specicesClass/findSpecies', {
        keywords: keyword,
        fpinyin: letter === '全部' ? '' : letter,
        fclassifiedid: arr,
        type: type,
        pageNum: num,
        pageSize: 32
      }).then(res => {
        var data = res.data
        this.total2 = data.total
        if(result) {
          result.forEach(item => {
            data.list.forEach((child, index) => {
              if(child.label === item.label) {
                child.checked = true
              }
            })
          })
        } else {
          data.list.forEach(child => {
            child.checked = false
          })
        }
        this.speciResultDatas = data.list
      })
    },
  }
}
</script>

<style lang="scss" scoped>

</style>
