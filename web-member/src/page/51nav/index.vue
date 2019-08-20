<template>
	<div class="layout">
		<top ref="top" active="0"></top>
		<v-content></v-content>
		<foot></foot>
	</div>
</template>
<script>
	import top from '../../top.vue'
	import foot from '../../foot.vue'
	import vContent from './content'
	export default {
		name:'index',
		components: {
			top,
			vContent,
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