<template>
  <div class="ivu-room-class">
    <Modal
        v-model="isShow"
        title="房间分类"
        @on-ok="asyncOK">
        <div>
            <i-form :model="roomClassData" :label-width="100">
                <Form-item label="房间标题">
                    <i-input :value.sync="roomClassData.romTitle" placeholder="请输入"></i-input>
                </Form-item>
                <Form-item label="房间描述">
                    <i-input type="textarea" :autosize="{minRows: 5,maxRows: 5}" :value.sync="roomClassData.romTitle" placeholder="请输入"></i-input>
                </Form-item>                          
            </i-form>      
        </div>
    </Modal>
  </div>
</template>
<script>
    export default{
        props:{
            roomClass:{
                type:Boolean,
                default:true
            }
        },
        data(){
            return{
                isShow:true,
                roomClassData:{
                    romTitle:''
                }
            }
        },
        methods:{
            asyncOK(){
                
            }
        },
        watch:{
            roomClass(){
                this.isShow = this.roomClass
            }
        }
    }
</script>
<style>


</style>

