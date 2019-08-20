<template>
    <div>
       <Select v-model="words" 
            filterable
            @on-change="handleChange"
            class="units"  placeholder="单位">
            <Option v-for="(item, index) in units" :value="item.unit_name" :key="index">{{item.unit_name}}</Option>
        </Select>
    </div>
</template>
<script>
    export default {
        props:{
            keyWords: {
                type: String,
                default:'公斤'
            }
        },
        data () {
            return {
                units: [],
                words: '公斤'
            }
        },
        created() {
            this.handleInit('')
        },
        watch:{
            keyWords(newVal,oldVal){
                this.words = newVal
            }
        },
        methods:{
            // 初始化查询 
            handleInit (keyWords) {
                this.$api.post('/portal/shopCommdoity/findUnitStandard',{name: keyWords}).then(response => {
                    if (response.code == 200) {
                        this.units = response.data
                    }
                })
            },
            // handleSearch (e) {
            //     console.log('eeeeeeeee',e)
            //     this.handleInit(e)
            // },
            handleChange (e) {
                this.$emit('on-change',e)
            }
        }
    }
</script>