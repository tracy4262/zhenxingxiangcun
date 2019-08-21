<!--
    作者：chenqim
    时间：2019-02-20
    描述：生产基地照片上传控件
-->
 <template>
    <div>
        <Button type="default" @click="uploadPhoto">
            <Icon type="ios-cloud-upload-outline" size="16" class="pr5"></Icon>上传图片
        </Button>
        <Button type="default" @click="handlePhotoSelectorModal('photo')">
            <Icon type="ios-download-outline" size="16" class="pr5"></Icon>从文件管理导入
        </Button>
        <!-- 选中照片 -->
        <div>
            <div v-for="(item, index) in data" style="float: left" class="mt20 mr20 mb20 photo-list">
                <img :src="item.imageUrl" style="width: 200px; height: 140px;">
                <Dropdown style="margin-left: 20px" placement="bottom-end" class="setBtn">
                    <!-- <a href="javascript:void(0)">设置</a> -->
                    <Button type="default" size="small">设置</Button>
                    <DropdownMenu slot="list">
                        <DropdownItem name="edit" @click.native="setEdit(index)">
                            <Icon type="md-create" style="padding-right:10px"/>编辑
                        </DropdownItem>
                        <DropdownItem name="delete" @click.native="setDelete(index)">
                            <Icon type="ios-trash" style="padding-right:10px"/>删除
                        </DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </div>
        </div>
        <!-- 上传照片弹出框 -->
        <Modal v-model="modal" title="上传照片" width="1000">
            <div style="min-height: 300px;">
                <Row type="flex" align="middle">
                    <Col span="1"><p>上传到</p></Col>
                    <Col span="6">
                        <Select size="large" v-model="uploadInfo.selectedValue" class="ml20">
                            <Option v-for="(item,index) in photos" :key="index" :value="item.mediaId">
                                <img :src="item.imageUrl" style="width:35px;height:35px;margin:0 4px">{{item.mediaName}}
                            </Option>
                        </Select>
                    </Col>
                    <Col span="4" offset="1"><a @click="createAlbum">新建相册</a></Col>
                </Row>
                <Row type="flex" align="middle">
                    <Col span="12" offset="1">
                        <vupload
                            class="ml20 mt20"
                            ref="upload"
                            @on-getPictureList="getPictureList"
                            :total="100"
                            :hint="'支持拓展名称：png/jpg'"
                        ></vupload>
                    </Col>
                </Row>
            </div>
            <div slot="footer">
                <Button @click="ok" type="primary">确定</Button>
                <Button @click="cancel">取消</Button>
            </div>
        </Modal>
        <!-- 新建相册弹框 -->
        <Modal v-model="addModal" title="新建相册" width="500">
            <Form ref="formlist" :model="formlist" label-position="left" :label-width="100" :rules="rules" class="mt20">
                <Row>
                    <Form-item label="相册名称" prop="name">
                        <Input v-model="formlist.name" />
                    </Form-item>
                </Row>
                <Row>
                    <Form-item label="相册描述">
                        <Input v-model="formlist.description" type="textarea" />
                    </Form-item>
                </Row>
                <Row>
                    <Form-item label="创建人" prop="creator">
                        <Input v-model="formlist.creator" />
                    </Form-item>
                </Row>
            </Form>
            <div slot="footer">
                <Button @click="ok3" type="primary">确定</Button>
                <Button @click="cancel3">取消</Button>
            </div>
        </Modal>
        <!-- 编辑照片信息弹出框 -->
        <Modal v-model="editModal" title="编辑照片信息" width="500">
            <Form ref="formItem" :model="item" label-position="left" :label-width="100" :rules="formItemInline" class="mt20">
                <Row>
                    <Form-item label="照片名称">
                        <Input v-model="item.mediaName" />
                    </Form-item>
                </Row>
                <Row>
                    <Form-item label="照片描述">
                        <Input v-model="item.mediaDescribe" type="textarea" />
                    </Form-item>
                </Row>
                <Row>
                    <Form-item label="拍摄人">
                        <Input v-model="item.author" />
                    </Form-item>
                </Row>
                <Row>
                    <Form-item label="拍摄时间">
                        <DatePicker type="date" v-model="item.photoTime" :options="options" style="width: 100%;"></DatePicker>
                    </Form-item>
                </Row>
                <Row>
                    <Form-item label="拍摄地点">
                        <Input v-model="item.photoAddress" />
                    </Form-item>
                </Row>
            </Form>
            <div slot="footer">
                <Button @click="ok2" type="primary">确定</Button>
                <Button @click="cancel2">取消</Button>
            </div>
        </Modal>
        <!-- 从文件管理导入 -->
        <photoSelector
            ref="photo"
            @on-change="albumChange"
            @on-get-result="handleGetPhotoResult"
            :resultDatas="p"/>
    </div>
</template>
<script>
import vupload from "~components/vui-upload"
import photoSelector from "~components/photoSelector"
export default {
    name: 'photoSelect',
    components: {
        vupload,
        photoSelector
    },
    data () {
        return {
            activeIndex: 0,
            modal: false,
            editModal: false,
            addModal: false,
            uploadInfo: {
                selectedValue: ''
            },
            photos: [],
            p: [],
            list: [],
            data: [],
            item: {
                mediaName: '',
                mediaDescribe: '',
                author: '',
                photoTime: '',
                photoAddress: '',
            },
            formItemInline: {},
            nickName: '',
            formlist: {
                name: '',
                description: '',
                creator: ''
            },
            rules: {
                name: [
                    { required: true, message: '相册名称不能为空！', trigger: 'blur' }
                ],
                creator: [
                    { required: true, message: '创建人不能为空！', trigger: 'blur' }
                ]
            },
            options: {
                disabledDate (date) {
                    return date && date.valueOf() > Date.now()
                }
            }
        }
    },
    created () {
        this.queryData()
    },
    methods: {
        queryData() {
            this.$api.post("/member/media/listMediaLibrary", {
                mediaType: 1,
                account: this.$user.loginAccount,
                pageNum: 1,
                pageSize: 1000
            }).then(res => {
                console.log('res', res.data)
                this.photos = res.data
            })
        },
        getPictureList (value) {
            var arr = [];
            value.forEach(element => {
                if (element.response) {
                    arr.push({
                        name: element.name,
                        url: element.response.data.picName
                    })
                }
            })
            this.list = arr;
        },
        ok () {
            if (this.uploadInfo.selectedValue === '') {
                this.$Message.info("请选择一个相册！")
            } else if (this.list.length === 0) {
                this.$Message.info("上传的图片不能为空！")
            } else {
                this.$api.post("/member/media/saveMediaLibraryDetail", {
                    mediaId: this.uploadInfo.selectedValue,
                    mediaUrl: this.list
                }).then(res => {
                    if (res.code === 200) {
                        this.modal = false
                        this.list.forEach(element => {
                            this.data.push({
                                mediaName: '',
                                mediaDescribe: '',
                                author: this.nickName,
                                photoTime: '',
                                photoAddress: '',
                                imageUrl: element.url
                            })
                        })
                        this.$emit('get-data', this.data)
                    }
                })
            }
        },
        cancel () {
            this.modal = false
        },
        getPhoto (params, array) {
            this.$api.post("/member/product-base/media-library-detail-query-list", params).then(response => {
                if (response.code === 200) {
                    for (let i = 0; i < response.data.list.length; i++) {
                        array.push({
                            id: response.data.list[i].id,
                            src: response.data.list[i].mediaUrl,
                            disable: false
                        })
                    }
                }
            })
        },
        // 从我的风采导入图片
        handlePhotoSelectorModal (name) {
            this.$refs[name].photoSelectorShow = true
            this.$refs[name].choosed = []
        },
        uploadPhoto () {
            this.modal = true
            this.$refs['upload'].handleGive('')
            this.list = []
            this.uploadInfo.selectedValue = ''
        },
        albumChange (value) {
            this.p = []
            this.getPhoto({
                mediaId: value,
                pageNum: 1,
                pageSize: 1000
            }, this.p)
        },
        handleGetPhotoResult (result) {
            result.forEach(element => {
                this.data.push({
                    mediaName: '',
                    mediaDescribe: '',
                    author: this.nickName,
                    photoTime: '',
                    photoAddress: '',
                    imageUrl: element
                })
            })
            this.$emit('get-data', this.data)
        },
        setEdit (index) {
            this.activeIndex = index
            this.item.mediaName = this.data[index].mediaName
            this.item.mediaDescribe = this.data[index].mediaDescribe
            this.item.author = this.data[index].author 
            this.item.photoTime = this.data[index].photoTime
            this.item.photoAddress = this.data[index].photoAddress
            this.editModal = true
        },
        setDelete (index) {
            this.$Modal.confirm({
                title: '操作提示',
                content: '是否确认删除该照片？',
                onOk:()=>{
                    this.data.splice(index, 1)
                    this.$emit('get-data', this.data)
                },
                okText:'确定',
                cancelText:'取消'
            })
        },
        ok2 () {
            this.data[this.activeIndex].mediaName = this.item.mediaName
            this.data[this.activeIndex].mediaDescribe = this.item.mediaDescribe
            this.data[this.activeIndex].author = this.item.author
            let photoTime = this.moment(this.item.photoTime).format('YYYY-MM-DD')
            this.data[this.activeIndex].photoTime = photoTime
            this.data[this.activeIndex].photoAddress = this.item.photoAddress
            this.editModal = false
        },
        cancel2 () {
            this.editModal = false
        },
        createAlbum () {
            this.formlist.name = ''
            this.formlist.description = ''
            this.formlist.creator = this.nickName
            this.addModal = true
        },
        ok3 () {
            this.$refs['formlist'].validate((valid) => {
                if (valid) {
                    this.$api.post("/member/media/saveMediaLibrary", {
                        mediaName: this.formlist.name,
                        mediaDescribe: this.formlist.description,
                        author: this.formlist.creator,
                        mediaType: 1,
                        image_url: "../static/datas/img/myStyle/no-picture.png",
                        account: this.$user.loginAccount
                    }).then(res => {
                        if (res.data === 1) {
                            this.$Message.success("创建成功！")
                            this.addModal = false
                            this.queryData()
                        } else if (res.code === 1000 || response.code === "1000") {
                            this.$Message.error("相册已存在！")
                        }
                    }) 
                }
            })
        },
        cancel3 () {
            this.addModal = false
        }
    }
}
</script>
<style lang="scss" scoped>
.photo-list {
    position: relative;
    &:hover {
        .setBtn {
            display: inline-block;
            position: absolute;
            right: 10px;
            top: 10px;
        }
    }
    .setBtn {
        display: none;
        position: absolute;
        right: 10px;
        top: 10px;
    }
}
</style>

