<template>
  <div class="outputGuess">
    <ul class="out_nav">
      <li 
      v-for="(item, index) in navList" :key="index" 
      :class="{navActive: isActive === item.name}"
      @click="switchTab(item)"
      >{{item.name}}</li>
    </ul>
    <Divider />
    <components :is="currentView" :activeId="id"></components>
  </div>
</template>

<script>
import allRecords from './component/records/allRecords'
import customRecords from './component/records/customRecords'
import fertilizeRecords from './component/records/fertilizeRecords'
import harvestRecords from './component/records/harvestRecords'
import medicineRecords from './component/records/medicineRecords'
import researchRecords from './component/records/researchRecords'
import seedRecords from './component/records/seedRecords'
export default {
  components: {
    allRecords,
    customRecords,
    fertilizeRecords,
    harvestRecords,
    medicineRecords,
    researchRecords,
    seedRecords
  },
  data () {
    return {
      currentView: 'allRecords',
      navList: [
        {name: '全部', value: 'allRecords'},
        // {name: '播种', value: 'seedRecords'},
        // {name: '施肥', value: 'fertilizeRecords'},
        // {name: '施药', value: 'medicineRecords'},
        // {name: '调产', value: 'researchRecords'},
        // {name: '收获', value: 'harvestRecords'},
        // {name: '自定义', value: 'customRecords'} 
      ],
      isActive: '全部',
      id: ''
    }
  },
  created() {
    this.$api.post('/shop/plant/findPlantTitleInfo').then(response => {
      if (response.code === 200) {
        let list = [
          {name: '全部', value: 'allRecords'}
        ]
        this.navList = list.concat(response.data)
      }
    })
  },
  methods: {
    switchTab (item) {
      this.isActive = item.name
      this.currentView = item.value
      this.id = item.id
    }
  }
}
</script>

<style lang="scss" scoped>
.outputGuess{
  width: 1000px;
  min-height: 800px;
  margin: 0 auto;
  background-color: #fff;
  .out_nav{
    display: flex;
    padding: 38px 46px 10px;
    li{
      width: 60px;
      height: 34px;
      line-height: 34px;
      text-align: center;
      color: #4A4A4A ;
      font-size: 14px;
      margin-right: 30px;
      cursor: pointer;
      &:hover{
        background: #00C587;
        color: #fff;
      }
    }
    .navActive{
      background: #00C587;
      color: #fff;
    }
  }
}
</style>
