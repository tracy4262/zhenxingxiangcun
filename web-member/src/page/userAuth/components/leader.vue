<template>
    <div class="leader  pt30 pl10 pr10">
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="领导班子">
                        <Button type="primary" @click="handleAddLeader"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <leader-card v-for="(item,index) in leaderFormItems"
                :index="index"
                :key="index"
                :data="item"
                @on-edit="editLeader"
                @on-del="delLeader">
            ></leader-card>
        </Form>
        <leader-modal 
            :title="'添加成员'" 
            ref="modal" 
            :leaderForm="leaderForm" 
            @on-save="handleSave"
            ></leader-modal>
    </div>
</template>
<script>
import leaderModal from './leader-modal'
import leaderCard from './leaderCard'
export default {
    components:{
        leaderCard,
        leaderModal
    },
    props:{
        title:{
            type:String,
            default:()=>{
                return '添加成员'
            }
        },
        leaderFormItems:{
            type:Array,
            default:()=>{
                return []
            }
        }
    },
    data () {
        return{
            leaderForm:{},
            leaderShow: false,            
            // leaderFormItems:[],
            isAdd: true,
            editIndex : ''
        }
    },
    methods:{
        //添加
        handleAddLeader () {
            this.isAdd = true
            this.$refs['modal'].leaderShow = true
            //重置弹窗数据
            this.leaderForm = {
                job:'',
                name:'',
                degree:'',
                idcard:'',
                phone:'',
                avatar:'',
                introduction:''
            }
            this.$refs['modal'].reset ()
        },
        //保存
        handleSave (e) {
            this.$refs['modal'].leaderShow = false
            if(this.isAdd){
                //添加
                console.log(e)
                this.leaderFormItems.push(e)
            } else {
                //编辑
                this.leaderFormItems.splice(this.editIndex,1,e)
            }            
        },
        //编辑
        editLeader (index) {
            this.editIndex = index
            this.isAdd = false
            this.leaderForm = this.leaderFormItems[index]
            this.$refs['modal'].leaderShow = true
            this.$refs['modal'].$refs['upload'].handleGive( this.leaderForm.avatar)
        },
        //删除
        delLeader (index) {
            this.leaderFormItems.splice(index,1)
        }
    }  
}
</script>

<style>

</style>


