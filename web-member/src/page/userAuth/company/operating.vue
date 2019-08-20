<template>
  <div class="pt30 pl10 pr10">
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="办公设施">
                        <Button type="primary" @click="handleAdd('facilities')"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <operating-card v-for="(item,index) in facilities" 
                :index="index" 
                :key="index" 
                :data="item" 
                @on-edit="editFacilities"
                @on-del="delFacilities">
            </operating-card>
        </Form>
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="生产设施">
                        <Button type="primary" @click="handleAdd('production')"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <operating-card v-for="(item,index) in production" 
                :index="index" 
                :key="index" 
                :data="item" 
                @on-edit="editProduction"
                @on-del="delProduction">
            </operating-card>
        </Form>
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="仓储设施">
                        <Button type="primary" @click="handleAdd('storage')"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <operating-card v-for="(item,index) in storage" 
                :index="index" 
                :key="index" 
                :data="item" 
                @on-edit="editStorage"
                @on-del="delStorage">
            </operating-card>
        </Form>
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="包装设施">
                        <Button type="primary" @click="handleAdd('packing')"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <operating-card v-for="(item,index) in packing" 
                :index="index" 
                :key="index" 
                :data="item" 
                @on-edit="editPacking"
                @on-del="delPacking">
            </operating-card>
        </Form>
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="运输设施">
                        <Button type="primary" @click="handleAdd('transport')"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <operating-card v-for="(item,index) in transport" 
                :index="index" 
                :key="index" 
                :data="item" 
                @on-edit="editTransport"
                @on-del="delTransport">
            </operating-card>
        </Form>
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="运输设施">
                        <Button type="primary" @click="handleAdd('instrument')"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <operating-card v-for="(item,index) in instrument" 
                :index="index" 
                :key="index" 
                :data="item" 
                @on-edit="editInstrument"
                @on-del="delInstrument">
            </operating-card>
        </Form>
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="运输设施">
                        <Button type="primary" @click="handleAdd('placeOfBusiness')"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <operating-card v-for="(item,index) in placeOfBusiness" 
                :index="index" 
                :key="index" 
                :data="item" 
                @on-edit="editPlaceOfBusiness"
                @on-del="delPlaceOfBusiness">
            </operating-card>
        </Form>
        <Form  label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="其他">
                        <Button type="primary" @click="handleAdd('other')"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <operating-card v-for="(item,index) in other" 
                :index="index" 
                :key="index" 
                :data="item" 
                @on-edit="editOther"
                @on-del="delOther">
            </operating-card>
        </Form>
         <Modal
            v-model="showAdd"
            :title="title"
            :mask-closable="false">
            <div class="pd20">
                <Form  ref="formItem" :model="formItem" label-position="left" :label-width="100" :rules="formItemInline">
                    <Form-item prop="name" label="资产名称">
                        <Input v-model="formItem.name" :maxlength="20"></Input>
                    </Form-item>
                    <Form-item prop="eplain" label="资产说明">
                       <Input v-model="formItem.eplain" type="textarea" :autosize="{minRows: 3,maxRows: 8}" :maxlength="500"></Input>
                    </Form-item>
                </Form>
            </div>
            <div slot="footer" class="tc">
                <Button type="default" @click="showAdd = false">取消</Button>
                <Button type="primary" @click.native="ok">确定</Button>
            </div>
        </Modal>
  </div>
</template>
<script>
import  operatingCard from './operatingCard'
    export default{
        components:{
            operatingCard
        },
        data(){
            return{
                //当前操作的是那一个
                activeAdd:'',
                showAdd:false,
                title:'',
                finsh:false,
                formItem:{
                    eplain:'',
                    name:''
                },
                formItemInline:{
                    name:[{required: true,  message: '请填写资产名称',trigger: 'blur' }]
                },
                facilities:[],
                production:[],
                storage:[],
                packing:[],
                transport:[],
                instrument:[],
                placeOfBusiness:[],
                other:[]
            }
        },
        methods:{
            //公共添加事件
            handleAdd(type){
                this.formItem = {
                    eplain:'',
                    name:''
                }
                this.finsh = false
                this.activeAdd = type
                this.getSwitch(this.activeAdd)
                this.showAdd = true
            },
            // 公共设施编辑删除事件
            editFacilities(index){
                this.handleAdd('facilities')
                this.formItem = this.facilities[index]
            },
            delFacilities(index){                
                this.facilities.splice(index,1)
            },
            // 生产设施编辑删除事件
            editProduction(index){
                this.handleAdd('production')
                this.formItem = this.production[index]
            },
            delProduction(index){     
                this.production.splice(index,1)       
            },
             // 仓储设施编辑删除事件
            editStorage(index){
                this.handleAdd('storage')
                this.formItem = this.storage[index]
            },
            delStorage(index){                
                this.storage.splice(index,1)
            },
            //包装设施
            editPacking(index){
                this.handleAdd('packing')
                this.formItem = this.packing[index]
            },
            delPacking(index){                
                this.packing.splice(index,1)
            },
            //运输设施
            editTransport (index){
                this.handleAdd('transport')
                this.formItem = this.transport[index]
            },
            delTransport (index){                
                this.transport.splice(index,1)
            },
            //仪器设施
            editInstrument (index){
                this.handleAdd('instrument')
                this.formItem = this.instrument[index]
            },
            delInstrument (index){                
                this.instrument.splice(index,1)
            },
            //经营场所
            editPlaceOfBusiness (index){
                this.handleAdd('placeOfBusiness')
                this.formItem = this.placeOfBusiness[index]
            },
            delPlaceOfBusinesst (index){                
                this.placeOfBusiness.splice(index,1)
            },
            //其他
            editOther (index){
                this.handleAdd('other')
                this.formItem = this.other[index]
            },
            delOther (index){                
                this.other.splice(index,1)
            },
            //弹窗确认
            ok(){
                this.$refs['formItem'].validate((valid) => {
                    if (valid) {
                        this.finsh = true
                        this.getSwitch(this.activeAdd)
                        this.showAdd = false
                    } else {
                        this.$Message.error('请填写资产名称');
                    }
                })
            },
            getSwitch(type){
                switch (type){
                    case 'facilities': this.finsh ? this.facilities.push(this.formItem) : this.title = '新增办公设施' ; break ;
                    case 'production': this.finsh ? this.production.push(this.formItem) : this.title = '新增生产设施' ; break ;
                    case 'storage': this.finsh ? this.storage.push(this.formItem) : this.title = '新增仓储设施' ; break ;
                    case 'packing': this.finsh ? this.packing.push(this.formItem) : this.title = '新增包装设施' ; break ;
                    case 'transport': this.finsh ? this.transport.push(this.formItem) : this.title = '新增运输设施' ; break ;
                    case 'instrument': this.finsh ? this.instrument.push(this.formItem) : this.title = '新增仪器设施' ; break ;
                    case 'placeOfBusiness': this.finsh ? this.placeOfBusiness.push(this.formItem) : this.title = '新增经营场所' ; break ;
                    case 'other': this.finsh ? this.other.push(this.formItem) : this.title = '其他' ; break ;
                }
            }
        }
    }

</script>
<style>


</style>

