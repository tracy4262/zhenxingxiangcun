<template>
  <div  class="education pt30 pl10 pr10">
    <Form :label-width="150" label-position="left" class="mt20 mb20">    
      <div  v-for="(item, index) in data" :key="index" v-if="item.team_status">
        <team-card :item="item" :index="index"></team-card>
      </div>
    </Form>
  </div>
</template>
<script>
import teamCard from './teamCard'
export default {
   components:{
    teamCard
  },
  data: () => ({
    more:false,
    teamAccount:false,
    formItem:{
      // role: '',
      job: '',
      name: '',
      avatar: [],
      idCard: '',
      phone: '',
      intro: '',
      educate: ''
    },
    data: [],
  }),
  mounted () {
  },
  methods: {
    //接收数据
    getData(val){
      console.log('val', val)
      this.data = val
    },
    //查看更多
    handleMore(){
      this.more = !this.more
    }
  }
}
</script>
<style lang="scss" scoped>
.ivu-avatar-super{
    width: 54px;
    height: 54px;
    line-height: 54px;
    border-radius: 50px;
}
</style>
