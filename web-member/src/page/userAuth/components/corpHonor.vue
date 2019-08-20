<template>
  <div class="pt30 pl10 pr10"> 
    <Form  label-position="left" :label-width="150" class="pb20">
        <Row :gutter="32">
            <Col span="12">
                <Form-item label="荣誉">
                    <Button type="primary" @click="handleAddHonor"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
            </Col>
        </Row>
        <Card v-for="(item,index) in data" :key="index" class="mt20">
            <Row type="flex" align="middle">
                <Col span="16">
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
            <Row class="mt10">
                <img v-if="pic" v-for="(pic,index) in item.honorPictureList" :key="index" :src="pic" class="honor-picture-list">
            </Row>
        </Card>
    </Form>
    <Modal
        v-model="honor"
        :title="title"
        :mask-closable="false"
        width="800">
        <div class="pd20">
            <Form  ref="honorFormItem" :model="honorFormItem" label-position="left" :label-width="100" :rules="honorFormItemInline">
                <Form-item label="权限">
                    <i-switch  v-model="honorFormItem.honour_status" size="large">
						<span slot="open">公开</span>
						<span slot="close">隐藏</span>
					</i-switch>
                </Form-item>
                <Form-item prop="name" label="标题">
                    <Input v-model="honorFormItem.name" :maxlength="50"/>
                </Form-item>
                <Form-item label="简介">
                    <Input v-model="honorFormItem.content" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="500" />
                </Form-item>
                <Form-item label="资料上传">
                    <vui-upload
                        ref="honor"
                        @on-getPictureList="getHonorList"
                        :hint="'图片大小小于2MB，最多上传 9 张'"
                        :total="9"
                        :size="[80,80]"
                    ></vui-upload>
                </Form-item>
            </Form>
        </div>
        <div slot="footer">
            <Button type="default" @click="honor = false"> 取消</Button> 
            <Button type="primary" @click="honorOk"> 确定</Button>
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
                honor: false,
                honorFormItem: {
                    honour_status: true,
                    name: '',
                    content: '',
                    honorPictureList: []
                },
                honorFormItemInline: {
                    name: [
                        { required: true, message: '请填写荣誉名称', trigger: 'blur' }
                    ]
                },
                data: [],
                tag: 'add',
                editIndex: 0,
                title: '新增荣誉'
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
            handleAddHonor () {
                this.tag = 'add'
                this.title = '新增荣誉'
                this.honorFormItem = {
                    honour_status: true,
                    name: '',
                    content: '',
                    honorPictureList: []
                }
                this.title = '新增荣誉'
                this.$refs.honor.handleGive([])
                this.honor = true
            },
            honorOk () {
                this.$refs['honorFormItem'].validate((valid) => {
                    if (valid) {
                        // 判断是编辑还是新增
                        if (this.tag === 'add') {
                            this.data.unshift(this.honorFormItem)
                            this.honor = false
                        } else if (this.tag === 'edit') {
                            this.data[this.editIndex] = this.honorFormItem
                            this.honor = false
                        }
                    }else{
                        this.$Message.error('请核对表单信息')
                    }
                })
            },
            getHonorList (e) {
                var arr = []
                e.forEach(element => {
                     if(element.response){
                        arr.push(element.response.data.picName)
                    }
                })
                this.honorFormItem.honorPictureList = arr
            },
            edit (index) {
                // 编辑
                this.honor = true
                this.tag = 'edit'
                this.title = '编辑荣誉'
                this.editIndex = index
                this.honorFormItem =  Object.assign({}, this.data[index])
                this.$refs.honor.handleGive(this.honorFormItem.honorPictureList)
            },
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
<style lang="scss" scoped>
    .honor-picture-list {
        width: 10%;
        height: 100px;
        float:left;
        margin-right: 10px;
        &:last-child{
            margin-right: 0;
        }
    }
</style>