<template>
	<div>
		<div class="ma_compon" v-for="item in newData">
			<router-link :to="item.isSrc">
				<p class="ma_ma_compon_po">{{item.title}}</p>
			</router-link>
	        
	        <p class="ma_ma_compon_pt">{{item.columnType}}   {{item.createTime}}</p>
	    </div>
	</div>
</template>
<script>
    export default {
    	name: 'recommend',
    	data() {
			return {

			}
		},
        props: {
            newData: {
            	type:Array
            }
        },
        created(){
		},

        methods: {
        	
		}
    };
</script>
<style scoped>
	.ma_ma_compon_po{margin-top: 28px;font-size: 16px;}
	.ma_ma_compon_pt{line-height: 28px;margin-bottom: 28px;color: #999999;}
</style>