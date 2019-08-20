<template>
  <div class="pt30 pl10 pr10"> 
    <Form  label-position="left" :label-width="150" class="pb20">
        <Row :gutter="32">
            <Col span="12">
                <Form-item label="资质">
                    <Button type="primary" @click="handleAddQualification"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
            </Col>
        </Row>
        <Card v-for="(item,index) in data" :key="index" class="mt20">
            <Row type="flex" align="middle">
                <Col span="16" class="ell">
                    {{item.name}}
                </Col>
                <Col span="8" class="tr">
                    <div class="btn-toolbar" style="margin-top: -10px;">
                        <Button type="text" @click="edit(index)"><Icon type="edit" size="16" class="pr5"></Icon> 编辑</Button>
                        <Button type="text" @click="del(index)"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
            </Row>
            <Row>
                <Col span="16" class="t-grey pt5">
                {{item.content}}
                </Col>
            </Row>
            <Row class="mt10" :gutter="8">
                <Col span="3" v-for="(picName,index) in item.qualificationPictureList" class="pb10" :key="index">
                    <img :src="picName" width="100%" height="120px">
                </Col>
            </Row>
        </Card>
    </Form>
    <Modal
        v-model="qualification"
        :title="title"
        width="800px"
        :mask-closable="false">
        <div class="pd20">
            <Form ref="qualificationFormItem" :model="qualificationFormItem" label-position="left" :label-width="100" :rules="qualificationFormItemInline">
                <FormItem label="权限">
                    <i-switch  v-model="qualificationFormItem.professional_status" size="large">
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </FormItem>
                <Form-item prop="name" label="资质名称">
                    <Input v-model="qualificationFormItem.name" :maxlength="50"/>
                </Form-item>
                <Form-item label="简介">
                    <Input v-model="qualificationFormItem.content" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="500" />
                </Form-item>
                <Form-item label="资料上传">
                    <vui-upload
                        ref="qualification"
                        @on-getPictureList="getQualificationList"
                        :pictureLists="qualificationFormItem.qualificationPictureList"
                        :hint="'图片大小小于2MB，最多上传 9 张'"
                        :total="9"
                        :size="[80,80]"
                    ></vui-upload>
                </Form-item>
            </Form>
        </div>
        <div slot="footer">
            <Button type="default" @click="qualification = false"> 取消</Button> 
            <Button type="primary" @click="qualificationOk"> 确定</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
    import vuiUpload from '~components/vui-upload'
    export default {
        components: {
            vuiUpload
        },
        data () {
            return {
                qualification: false,
                qualificationFormItem: {
                    professional_status: true,
                    name: '',
                    content: '',
                    qualificationPictureList: []
                },
                qualificationFormItemInline: {
                    name: [
                        { required: true, message: '请填写资质名称', trigger: 'blur' }
                    ]
                },
                data: [],
                tag: true,
                editIndex: 0,
                title: '新增资质'
            }
        },
        methods: {
            //接收数据
			getData(val){
                this.data = val
			},
            //点击下一步的时候表单验证
            handleSubmit(){
                this.$emit('on-submit',true)
            },
            //增加
            handleAddQualification () {
                this.tag = true
                this.title = '新增资质'
                this.qualification = true
                this.qualificationFormItem = {
                    professional_status: true,
                    name: '',
                    qualificationPictureList: []
                }
                this.$refs['qualification'].handleGive(this.qualificationFormItem.qualificationPictureList)
            },
            // 确认
            qualificationOk () {
                // 表单验证
                this.$refs['qualificationFormItem'].validate((valid) => {
                    if (valid) {
                        // 判断是编辑还是新增
                        if (this.tag) {
                            this.data.unshift(this.qualificationFormItem)
                        } else {
                            this.data[this.editIndex] = this.qualificationFormItem
                        }
                        this.qualification = false
                    }else{
                        this.$Message.error('请核对表单信息')
                    }
                })
            },
            //获取图片
            getQualificationList (list) {
                var arr = []
                list.forEach(element => {
                     if(element.response){
                        arr.push(element.response.data.picName)
                    }       
                });
                this.qualificationFormItem.qualificationPictureList = arr
            },
            edit (index) {
                // 编辑专业资质
                this.qualificationFormItem =  Object.assign({}, this.data[index])
                this.$refs.qualification.pictureList = this.data[index].qualificationPictureList
                this.$refs.qualification.$refs.upload.fileList = this.data[index].qualificationPictureList
                this.qualification = !this.qualification
                this.title = '编辑资质'
                this.tag = false
                this.title = '编辑资质'
                this.editIndex = index
                this.$refs['qualification'].handleGive(this.qualificationFormItem.qualificationPictureList)
            },
            //删除
            del (index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '是否确认删除？',
                    onOk:()=>{
                        this.data.splice(index, 1)
                    },
                    okText:'确定',
                    cancelText:'取消'
                })
            }
        }
    }
</script>
