<template>
	<div class="layout">
		<top-bar ref="top" active="0"></top-bar>
		<div>
			<router-view></router-view>
		</div>
		<foot></foot>
	</div>
</template>
<script>
	import top from '../../top.vue'
	import foot from '../../foot.vue'
	export default {
		name:'index',
		components: {
			'top-bar': top,
			foot
		},
		name: 'app',
		data() {
			return {
				loginuserinfo: null,
				portal :'',
				value1: [],
				modal_loading: false,
				modal3: false,
				modal4: false,
				modal5: false,
				value: [],
				cardShow: false,
				appShow: true
			}
		},
		created: function () {
			this.loginuserinfo=JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
			this.$router.push('/index')
		},
		methods: {
			modalShow() {
				this.cardShow = !this.cardShow
				this.appShow = !this.appShow
			},
			search: function () {
				this.$router.push({
					path: '/search'
				})
			},
			pass() {
				this.modal1 = !this.modal1
			}
		}
	}
</script>