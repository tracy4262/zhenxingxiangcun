<template>
  <Card>
    <p class="t-orange pt20 pb20">设置栏目将帮助您建立栏目选单自由设置和发布栏目专属信息，权限过滤将确保这些信息能被您经过允许的人查看。</p>
    <Title title="设置栏目"></Title>
    <div class="pd20">
      <Row class="mb20 tc">
        <Col span="2" >栏目名称</Col>
        <Col span="4" :offset="4">是否启用/隐藏</Col>
        <Col span="4" :offset="4">访问权限</Col>
      </Row>
      <Row v-for="(item, index) in data" :key="index" type="flex" align="middle" class="mb15 tc">
        <Col span="2" >
          {{item.name}}
        </Col>
        <Col span="4" :offset="4">
          <Switch v-model="item.status" size="large" :disabled="item.name === '动态' ? true : false" @on-change="handleSwitchChange($event, item)">
            <span slot="open">启用</span>
            <span slot="close">隐藏</span>
          </Switch>
        </Col>
        <Col span="4" :offset="4">
          <Select v-model="item.authority" @on-change="handleChange($event, item)">
            <Option v-for="(item,index) in author" :key="index" :value="item.value" >{{ item.label }}</Option>
          </Select>
        </Col>
      </Row>
    </div>
    <div class="tc pd20">
        <Button type="primary" @click="handleBack">上一步</Button>
        <Button type="primary" @click="handleClickNext">下一步</Button>
        <Button type="text" @click="handleJump">跳过</Button>
    </div>
  </Card>
</template>
<script>
import Title from '../components/title'
export default {
  components:{
    Title
  },
  data: () => ({
    loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
    data: [],
		author: [
			{
				value: 0,
				label: '所有人可见'
			}, {
				value: 1,
				label: '仅自己可见'
			}, {
				value: 2,
				label: '仅好友可见'
			}
		]
  }),
  created(){
    this.getItem()
  },
  methods:{
    getItem(){
      this.$api.get('/member/columnSettings/findColumnSetting?uid='+this.loginuserinfo.loginAccount).then(respone=>{
        if(respone.code == 200){
          this.data = respone.data
        }
      })
    },
    // 切换状态
    handleSwitchChange ($event, item) {
      item.status = $event
    },
    // 权限
    handleChange ($event, item) {
      console.log($event)
      item.authority = $event
    },
    //跳过
    handleJump () {
      this.$emit('on-jump')
    },
    //上一步
    handleBack () {
      this.$emit('on-back')
    },
    // 下一步 保存数据
    handleClickNext () {
      this.$api.post('/member/columnSettings/saveColumnSettings',{data:this.data}).then(Response=>{
        if(Response.code == 200){
          this.$Message.success('保存成功')
          this.$emit('on-next')
        }else{
          this.$Message.error('保存失败')
        }
      })
    }
  }
}
</script>
<style lang="scss">
</style>
