<template>
  <div class="vui-member-base-app">
    <h5 class="vui-member-base-app-title">{{name}}</h5>
    <ul class="vui-member-base-app-list">
      <li v-for="item in data">
        <a :href="item.url">• {{item.title}}</a>
      </li>
    </ul>
    <!-- <div class="vui-member-base-app-fold">
        <Button size="small">收起</Button>
    </div> -->
  </div>
</template>

<script>
	import axios2 from '~src/api/api'
export default {
	name:'userApp',
  props: {
    name: String
  },
  data(){
  	return{
  		data:[]
  	}
  },
  created(){
  	/*axios2.get('baseApp.json').then(res => {
				this.data = res.data[1]
			})*/
      this.$api.post('/member/bank/findAllappInfo', {
            level : 2
        }
      ).then(res=>{
          if(res.data.length){
              console.log('通用应用',res)
              res.data.forEach(e=>{
                  this.data.push({title:e.appName, url:e.url})
                  /*if(e.level===2){
                      this.data.push({title:e.appName, url:e.url})
                  }*/
              })
          }
      }).catch(error => {
          console.error(error)
      })
  }
}
</script>


