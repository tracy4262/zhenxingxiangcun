<template>
  <div class="vui-member-high-app">
    <h5 class="vui-member-high-app-title">{{name}}</h5>
    <Row v-for="(item,index) in basedata" :key="index" class='mb15'>
    	<template v-if="item.status">
	      <Col span="7"><img :src="item.src" alt=""></Col>
	      <Col span="17">
	        <a :href="item.url">
	          <p>{{item.title}}</p>
	        </a>
	      </Col>
      </template>
    </Row>
  </div>
</template>
<script>
export default {
	props: {
    name: String
  },
  data(){
  	return{
  		data:[],
      basedata:[],
  		loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
  	}
  },
  watch: {
    data () {
			this.basedata.forEach(item => {
  			this.data.forEach(child=>{
  				if(item.title === child.appid){
  					item.status=true
  				}
  			})
		  })
    }
  },
  created () {
		this.$api.post('/member/bank/findPersonApp', {
			level : 1,
			account: this.loginUser.loginAccount
		}).then(response => {
			if (response.data) {
				response.data.forEach(e => {
					var arr = []
					arr = e.url.split(";")
					this.basedata.push({url: arr[0], title: e.name, src: arr[1], status: e.checked})
				})
			}
		}).catch(error => {
			console.error(error)
		})
  }
}
</script>

<style lang="scss">
.vui-member-high-app{
  padding:10px;
  &-title{
    font-size: 16px ;
    padding:10px 0 20px;
  }
  &-fold{
    text-align: center;
    padding:10px 0;
  }
}
</style>
