<template>
	<div>
		<ul class="new_data">
			<li v-for="(item,index) in newData" :key="index">
				<router-link :to="item.isSrc" tag="p" class="ma_ma_compon_po">{{item.title}}</router-link>
	        	<p class="ma_ma_compon_pt">{{item.columnType}}   {{item.createTime}}</p>
			</li>
	    </ul>
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
<style scoped lang="scss">
	.new_data{
		margin-top: 15px;
		border-top: 1px solid rgba(232,232,232,1);
		li {
			padding: 24px 0;
			border-bottom: 1px dashed #d8d8d8;
			.ma_ma_compon_po{
				font-size: 16px;
				cursor: pointer;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
			}
			.ma_ma_compon_pt{
				color: #999999;
				margin-top: 6px;
			}
		}
	}
</style>