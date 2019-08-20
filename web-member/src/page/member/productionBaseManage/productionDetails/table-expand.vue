<template>
    <div>
        <Table border :columns="row.children.columns" :data="row.children.data"></Table>
    </div>
</template>
<script>
    export default {
    	data() {
			return {


			}
		},
        props: {
            row: {}
        },
        created(){
        	//console.log(this.row)
		},
        methods: {
        	
		}
    };
</script>
<style scoped>
    .expand-row{
        margin-bottom: 16px;
    }
</style>