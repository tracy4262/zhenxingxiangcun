<template>
  <div  class="pt30 pl10 pr10">
    <Form label-position="left" :label-width="150" >
        <Form-item label="资质">
            <Button type="primary"  @click="handleAdd"> <Icon type="plus"></Icon> 增加</Button>
        </Form-item>
        <Card v-for="(item,index) in formItems" :key="index" class="mt20">
            <Row type="flex" align="middle">
                <Col span="16" class="ell">
                    {{item.title}}
                </Col>
                <Col span="8" class="tr">
                    <div class="btn-toolbar" style="margin-top: -10px;">
                        <Button type="text" @click="handleEdit(item)"><Icon type="edit" size="16" class="pr5"></Icon> 编辑</Button>
                        <Button type="text" @click="handleDel(index)"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
                <Col span="24" class="t-grey pt10 pb10 ell">
                     {{item.abstract}}
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="3" v-for="(picName,index) in item.pictureList" class="pb10" :key="index">
                    <img :src="picName" width="100%" height="120px">
                </Col>
            </Row>
        </Card>
    </Form>
    <Modal
        v-model="qualificationsModel"
        title="新增资质"
        width="800px"
        :mask-closable="false">
        <div class="pd20">
            <Form  ref="formItem" :model="formItem" label-position="left" :label-width="100" :rules="formItemInline">
                <Row>
                    <Col :span="24">
                        <Form-item prop="title" label="标题" >
                            <Input v-model="formItem.title" :maxlength="50"></Input>
                        </Form-item>
                    </Col>
                </Row>
                <Row>
                    <Col :span="24">
                        <Form-item prop="abstract" label="简介" >
                            <Input v-model="formItem.abstract" type="textarea"  :maxlength="500" :autosize="{minRows: 4,maxRows: 6}"></Input>
                        </Form-item>
                    </Col>
                </Row>
                <Row>
                    <Col :span="24">
                        <Form-item prop="abstract" label="资料上传" >
                            <vui-upload
                                ref="qualification"
                                @on-getPictureList="getQualificationList"
                                :pictureLists="formItem.pictureList"
                                :hint="'图片大小小于2MB'"
                                :total="1000"
                                :size="[100,120]"
                            ></vui-upload>
                        </Form-item>                        
                    </Col>
                </Row>
            </Form>
        </div>
        <div slot="footer" class="tc">
            <Button type="default" @click="qualificationsModel = false">取消</Button>
            <Button type="primary" @click.native="handelOk">确定</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    export default {
        components:{
            vuiUpload
        },
        data () {
            return {
                qualificationsModel: false,
                formItem:{
                    title:'',
                    abstract:'',
                    pictureList:[]
                },
                formItems:[],
                isAdd:true,
                formItemInline:{
                    title:[{required:true, message: '请填写标题', trigger: 'blur' }]
                }
            }
        },
        methods: {
            //添加
            handleAdd(){
                this.qualificationsModel = true
                this.isAdd = true
                this.formItem = {
                    title:'',
                    abstract:'',
                    pictureList:[]
                }
                this.$refs['qualification'].handleGive(this.formItem.pictureList)
            },
            // 编辑
            handleEdit(item){
                this.isAdd = false
                this.qualificationsModel = true
                this.formItem = item
                this.$refs['qualification'].handleGive(this.formItem.pictureList)
            },
            //删除
            handleDel(index){
                this.$Modal.confirm({
                    title: '是否确定删除',
                    content: '是否确认删除？',
                    onOk:()=>{
                        this.formItems.splice(index,1)
                    },
                    okText:'确定',
                    cancelText:'取消'
                });
                
            },
            //确定
            handelOk(){
                this.$refs['formItem'].validate((valid) => {
                    if (valid) {
                        // 判断是编辑还是新增
                        if (this.isAdd) {
                            this.formItems.push(this.formItem)
                        } else {
                            this.formItems[this.editIndex] = this.formItem
                        }
                        this.qualificationsModel = false
                    }
                })
            },
            // 获取图片
            getQualificationList(e){
                var arr = []
                e.forEach(element => {
                    if(element.response){
                        arr.push(element.response.data.picName)
                    }                    
                });
                this.formItem.pictureList = arr
            }
        }
    }
</script>
<style>

</style>

