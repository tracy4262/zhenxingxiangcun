<template>
    <div>
        <!-- 文档文件夹 -->
        <template v-if="fileShow">
            <Button type="primary" class="mr10" @click="uploadFile" v-if="isshowEdit">上传文档</Button>
            <Button type="default" @click="createdFileFolder" v-if="isshowEdit">新建文件夹</Button>
            <!-- 循环生成文档文件夹 -->
            <Row :gutter="16" class="album mt20">
                <i-col span="6" v-for="(item,index) in fileFolderList" :key="index" class="photo-content">
                    <figure class="wrap mb10 border drop-out pointer" style="border: none;">
                        <Dropdown trigger="click" placement="left-start" class="folder-drop drop-in" v-if="isshowEdit">
                            <Icon type="ios-arrow-down" style="padding:2px 5px;"></Icon>
                            <Dropdown-menu slot="list">
                                <Dropdown-item>
                                    <div @click="fileFolderEdit(item.id,index)"><Icon type="edit"></Icon> 编辑</div>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <div @click="fileFolderDel(item.id,index)"><Icon type="trash-a"></Icon> 删除</div>
                                </Dropdown-item>
                            </Dropdown-menu>
                        </Dropdown>
                        <img src="../../../../static/datas/img/myStyle/folder.png" @click="lookFile(item.id)" alt="">
                    </figure>
                    <p class="ell" :title="item.name">{{item.name}}</p>
                    <p class="t-grey">创建于{{item.time}}</p>
                </i-col>
            </Row>
            <Row style="text-align: center; height: 100px;">
                <Page :total="total2" @on-change="getNextPage2" :page-size="pageSize2" :current="page2" class="pages"></Page>
            </Row>
        </template>
        <!-- 文档列表页面 -->
        <template v-else>
            <Button type="primary" @click="back">返回</Button>
            <Row :gutter="16" class="album mt20">
                <div class="tip" v-if="isEmpty">该文件夹空空如也，请上传文件！</div>
                <i-col span="6" v-for="(item,index) in fileList" :key="index" class="photo-content">
                    <figure class="wrap mb10 border drop-out pointer" style="border: none;">
                        <Dropdown  :transfer="true" trigger="click" placement="left-start" class="file-drop drop-in">
                            <Icon type="ios-arrow-down" style="padding:2px 5px;"></Icon>
                            <Dropdown-menu slot="list" v-if="isshowEdit">
                                <Dropdown-item>
                                    <div @click="editFile(item.id,index)"><Icon type="edit"></Icon> 编辑</div>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <div @click="downloadFile(item.url)"><Icon type="arrow-down-a"></Icon> 下载</div>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <div class="btn-2" :data-clipboard-text="item.url"><Icon type="share"></Icon> 引用</div>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <div @click="delFile(item.id,index)"><Icon type="trash-a"></Icon> 删除</div>
                                </Dropdown-item>
                            </Dropdown-menu>
                        </Dropdown>
                        <vue-share 
                            :config="{
                                sites: ['qzone', 'qq', 'weibo','wechat'],
                                url: 'http:'+item.url
                            }"
                            class="share">
                        </vue-share>
                        <img :src="item.src" width="100%">
                    </figure>
                    <p class="ell" :title="item.title">{{item.title}}</p>
                    <p class="t-grey">上传于{{item.time}}</p>
                </i-col>
            </Row>
            <Row style="text-align: center; height: 100px;">
                <Page :total="total" @on-change="getNextPage" :page-size="pageSize" class="pages"></Page>
            </Row>
        </template>
        <!-- 上传文档 -->
        <Modal v-model="uploadFileShow" title="上传文档" width="600px" :mask-closable="false" :closable="false">
            <Form ref="fileUploadInfo" :model="fileUploadInfo" label-position="right" :label-width="100" :rules="ruleInline">
                    <Form-item label="上传到：" prop="toWhere">
                        <i-select v-model="fileUploadInfo.toWhere" class="mb10"  style="width:200px">
                            <Option v-for="(item,index) in fileToWhereList" :value="item.value" :key="index">{{ item.label }}</Option>
                        </i-select>
                        <Button type="text" style="color: #108EE9;" size="small" @click="createdFileFolder">创建文件夹</Button>
                    </Form-item>
                    <Form-item label="文档：" prop="file_list">
                        <div class="up-video" v-for="(item,index) in fileUploadInfo.file_list" :key="index">
                            <template v-if="item.status === 'finished'">
                                <!-- <img v-if="doc" src="../../../../static/datas/img/myStyle/DOC.png" style="width: 60px; height:60px;">
                                <img v-if="xls" src="../../../../static/datas/img/myStyle/XLS.png" style="width: 60px; height:60px;">
                                <img v-if="ppt" src="../../../../static/datas/img/myStyle/PPT.png" style="width: 60px; height:60px;">
                                <img v-if="pdf" src="../../../../static/datas/img/myStyle/PDF.png" style="width: 60px; height:60px;">
                                <img v-if="txt" src="../../../../static/datas/img/myStyle/TXT.png" style="width: 60px; height:60px;"> -->
                                <img src="../../../../static/datas/img/myStyle/OTH.png" style="width: 60px; height:60px;">
                                <p style="line-height: 1;" class="ell" :title="item.name">{{item.name}}</p>
                                <div class="demo-upload-list-cover">
                                    <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
                                </div>
                            </template>
                            <template v-else>
                                <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
                            </template>
                        </div>
                        <Upload ref="upload" :show-upload-list="false"
                                name="upfile"
                                :format="['doc','docx','xls','xlsx','rmvb','ppt','pptx','txt','pdf']"
                                :max-size="20480"
                                :on-success="handleSuccess"
                                :on-exceeded-size="handleMaxSize"
                                :on-format-error="handleFormatError"
                                multiple
                                type="drag"
                                :action="action"
                                style="display: inline-block;width:58px;">
                            <div style="width: 58px;height:58px;line-height: 58px;">
                                <Icon type="md-add-circle" size="20"></Icon>
                            </div>
                        </Upload>
                    </Form-item>
                </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="uploadFileOkCancel()">取消</Button>
                <Button type="primary" size="large" @click="uploadFileOk()">确定</Button>
            </div>
        </Modal>
        <!-- 创建文件夹 -->
        <Modal v-model="createFileFolderShow" title="创建文件夹" width="600" :mask-closable="false" :closable="false">
            <Form ref="fileFolderInfo" :model="fileFolderInfo" label-position="right" :label-width="120" :rules="ruleInline2">
                <Form-item label="文件夹名称：" prop="file_folder_name">
                    <Input v-model="fileFolderInfo.file_folder_name" :maxlength="15" placeholder="名称不能超过15字" />
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="createFileFolderCancel()">取消</Button>
                <Button type="primary" size="large" @click="createFileFolderOk()">确定</Button>
            </div>
        </Modal>
        <!-- 编辑文件夹 -->
        <Modal v-model="editFileFolderShow" title="编辑文件夹" width="600" :mask-closable="false" :closable="false">
            <Form ref="editFileFolderInfo" :model="editFileFolderInfo" label-position="right" :label-width="120" :rules="ruleInline3">
                <Form-item label="文件夹名称：" prop="file_folder_name">
                    <Input v-model="editFileFolderInfo.file_folder_name" :maxlength="15" placeholder="名称不能超过15字" />
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="editFileFolderCancel()">取消</Button>
                <Button type="primary" size="large" @click="editFileFolderOk()">确定</Button>
            </div>
        </Modal>
        <!-- 编辑文档 -->
        <Modal v-model="editFileShow" title="编辑文档" width="600" :mask-closable="false" :closable="false">
            <Form ref="editFileInfo" :model="editFileInfo" label-position="right" :label-width="100" :rules="ruleInline4">
                <Form-item label="文档名称：" prop="file_name">
                    <Input v-model="editFileInfo.file_name" :maxlength="15" placeholder="名称不能超过15字" />
                </Form-item>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="editFileCancel()">取消</Button>
                <Button type="primary" size="large" @click="editFileOk()">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import api from '~api'
    import Clipboard from 'clipboard'
    import vueShare from '~components/vueShare'

    export default {

        components: {
            Clipboard,
            vueShare
        },
        data() {
            const validateFileFolderName = (rule, value, callback) => {
                if (value.replace(/(^\s+)|(\s+$)/g, "") === '') {
                    callback(new Error('文件夹名称不能为空'));
                } else {
                    callback();
                }
            };
            return {
                loginuserinfo:JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                isshowEdit:true,
                index: -1,
                id: -1,
                editFileFolderIndex: -1,
                editFileFolderId: -1,
                fileFolderId: -1,
                total: 0,
                pageSize: 20,
                page: 1,
                total2: 0,
                pageSize2: 12,
                page2: 1,
                doc: true,
                xls: false,
                ppt: false,
                pdf: false,
                txt: false,
                fileShow: true,
                uploadFileShow: false,
                createFileFolderShow: false,
                editFileFolderShow: false,
                loop: false,
                editFileShow: false,
                isEmpty: false,
                action: `${this.$url.upload}/upload/up`,
                fileFolderList: [],
                fileList: [],
                fileUploadInfo: {
                    toWhere: '',
                    file_list: []
                },
                ruleInline: {
                    toWhere: [
                        {required: true, message: '上传文件夹不能为空'}
                    ],
                    file_list: [
                        {required: true, type:'array', min: 1, message: '文件不能为空', trigger: 'change'}
                    ]
                },
                fileToWhereList: [],
                fileFolderInfo: {
                    file_folder_name: ''
                },
                ruleInline2: {
                    file_folder_name: [
                        {validator: validateFileFolderName, trigger: 'blur'}
                    ]
                },
                editFileFolderInfo: {
                    file_folder_name: ''
                },
                ruleInline3: {
                    file_folder_name: [
                        {validator: validateFileFolderName, trigger: 'blur'}
                    ]
                },
                editFileInfo: {
                    file_name: ''
                },
                ruleInline4: {
                    file_name: [
                        {required: true, message: '文件名称不能为空', trigger: 'blur'}
                    ]
                }
            }
        },
        created () {
            /*
             *   TODO 请求接口获取用户所有的相册数据push到albumList
             * */
            this.query(1)
        },
        mounted () {
            let _that = this
            const clipboard = new Clipboard('.btn-2')
            clipboard.on('success', function(e) {
                _that.$Message.success({content: '链接已复制到剪切板', duration: 1.5})
                e.clearSelection()
            })
            clipboard.on('error', function(e) {
                _that.$Message.success({content: '该浏览器不支持复制到剪切板功能', duration: 1.5})
            })
        },
        methods: {
            // 列表查询
            query (page) {
                api.post('/member/media/listMediaLibrary', {
                    mediaType: 3,
                    account: this.loginuserinfo.loginAccount,
                    pageSize: this.pageSize2,
                    pageNum: page
                }).then(response => {
                    if (response.code === 200) {
                        this.total2 = response.total
                        for (let i = 0; i < response.data.length; i++) {
                            this.fileFolderList.push({
                                id: response.data[i].mediaId,
                                name: response.data[i].mediaName,
                                time: response.data[i].createTime
                            })
                            this.fileToWhereList.push({
                                label: response.data[i].mediaName,
                                value: response.data[i].mediaId
                            })
                        }
                    }
                })
            },
            queryFile (params) {
                let _that = this
                api.post('/member/media/listMediaLibraryDetail', params).then(response => {
                    if (response.code === 200) {
                        for (let i = 0; i < response.data.length; i++) {
                            _that.fileList.push({
                                id: response.data[i].id,
                                src: '../../../../static/datas/img/myStyle/OTH.png',
                                url: response.data[i].mediaUrl,
                                title: response.data[i].name,
                                time: response.data[i].createTime
                            })
                        }
                    }
                    if (_that.fileList.length === 0) {
                        _that.isEmpty = true
                    }
                    _that.total = response.total
                    _that.pageSize = response.page.pageSize
                })
            },
            getNextPage (page) {
                this.page = page
                let params = {mediaId: this.fileFolderId, pageNum: page, pageSize: this.pageSize}
                this.fileList = []
                this.queryFile(params)
            },
            getNextPage2 (page) {
                this.page2 = page
                this.fileFolderList = []
                this.query(page)
            },
            handleSubmit (name) {
                let flag = false
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        flag = true
                    } else {
                    }
                })
                return flag
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            },
            uploadFile () {
                this.uploadFileShow = true
            },
            play () {
                this.isshowpdf = true
            },
            back () {
                this.fileShow = !this.fileShow
                this.fileList = []
                this.isEmpty = false
            },
            createdFileFolder () {
                this.uploadFileShow = false
                this.createFileFolderShow = true
            },
            lookFile (id) {
                this.fileShow = false
                this.fileFolderId = id
                let params = {mediaId: id, pageNum: 1, pageSize: this.pageSize}
                this.queryFile(params)
            },
            fileFolderEdit (id, index) {
                this.editFileFolderShow = true
                this.editFileFolderIndex = index
                this.editFileFolderId = id
            },
            fileFolderDel (id, index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '<p>是否确认删除这个文件夹？</p><p class="t-grey">删除文件夹后文件也会随之删除</p>',
                    onOk: () => {
                        let _that = this
                        api.get('/member/media/deleteMediaLibrary/' + id).then(response => {
                            if (response.data === 1) {
                                _that.fileFolderList = []
                                _that.fileToWhereList = []
                                _that.query(this.page2)
                                _that.$Message.info('删除成功')
                            }
                        })
                    },
                    onCancel: () => {}
                })
            },
            uploadFileOk () {
                let flag = this.handleSubmit('fileUploadInfo')
                if (!flag) {
                    return
                }
                let list = []
                for (let i = 0; i < this.fileUploadInfo.file_list.length; i++) {
                    let obj = {name: this.fileUploadInfo.file_list[i].name, url: this.fileUploadInfo.file_list[i].response.data.picName}
                    list.push(obj)
                }
                let data = {
                    mediaId: this.fileUploadInfo.toWhere,
                    mediaUrl: list
                }
                api.post('/member/media/saveMediaLibraryDetail', data).then(response => {
                    if (response.code === 200) {
                        this.uploadFileSuccessShow = true
                        this.fileUploadInfo.toWhere = ''
                        this.fileUploadInfo.file_list = []
                        this.$refs.upload.fileList = []
                        this.uploadFileShow = false
                        this.handleReset('fileUploadInfo')
                    }
                })
            },
            uploadFileOkCancel () {
                this.fileUploadInfo.toWhere = ''
                this.fileUploadInfo.file_list = []
                this.$refs.upload.fileList = []
                this.uploadFileShow = false
                this.handleReset('fileUploadInfo')
            },
            // 上传文档
            handleSuccess (response, file, fileList) {
                if (response.code === 500) {
                    this.$Message.error('上传失败!')
                } else {
                    this.$Message.success('上传成功!')
                    this.fileUploadInfo.file_list = this.$refs.upload.fileList
                    this.handleSubmit('fileUploadInfo')
                }
            },
            // 删除文档
            handleRemove (file) {
                const fileList = this.fileUploadInfo.file_list
                this.fileUploadInfo.file_list.splice(fileList.indexOf(file), 1)
            },
            // 文件大小限制
            handleMaxSize (file) {
                this.$Notice.warning({
                    title: '文件大小超出限制',
                    desc: '文件大小过大，应不超过20M。',
                    duration: 6
                })
            },
            // 文件格式限制
            handleFormatError (file) {
                this.$Notice.warning({
                    title: '文件格式有误',
                    desc: '文件格式不正确，请选择doc、docx、xls、xlsx、rmvb、ppt、pptx、txt、pdf格式。',
                    duration: 6
                })
            },
            createFileFolderOk () {
                /*
                 *   TODO 请求保存文件夹接口保存文件夹数据
                 * */
                let flag = this.handleSubmit('fileFolderInfo')
                if (!flag) {
                    return
                }
                let _that = this
                let name = this.fileFolderInfo.file_folder_name
                let data = {mediaName: name, mediaType: 3}
                api.post('/member/media/saveMediaLibrary', data).then(response=> {
                    if (response.code === 1000 || response.code === '1000') {
                        _that.$Message.error('文件夹已存在！')
                    }
                    if (response.data === 1) {
                        _that.fileFolderList = []
                        _that.fileToWhereList = []
                        _that.fileFolderInfo.file_folder_name = ''
                        _that.createFileFolderShow = false
                        _that.query(1)
                        this.page2 = 1
                    }
                })
            },
            createFileFolderCancel () {
                this.fileFolderInfo.file_folder_name = ''
                this.createFileFolderShow = false
                this.handleReset('fileFolderInfo')
            },
            editFileFolderOk () {
                let flag = this.handleSubmit('editFileFolderInfo')
                if (!flag) {
                    return
                }
                let name = this.editFileFolderInfo.file_folder_name
                let id = this.editFileFolderId
                let _that = this
                api.post('/member/media/updateMediaLibrary', {
                    mediaId: id,
                    mediaName: name
                }).then(response => {
                    if (response.data === 1) {
                        _that.fileFolderList = []
                        _that.fileToWhereList = []
                        _that.editFileFolderInfo.file_folder_name = ''
                        _that.editFileFolderShow = false
                        _that.query(this.page2)
                    }
                })
            },
            editFileFolderCancel () {
                this.editFileFolderInfo.file_folder_name = ''
                this.editFileFolderShow = false
                this.handleReset('editFileFolderInfo')
            },
            editFile (id, index) {
                this.editFileShow = true
                this.index = index
                this.id = id
            },
            delFile (id, index) {
                this.$Modal.confirm({
                    title: '确认对话框标题',
                    content: '<p>是否确认删除这个文档？</p>',
                    onOk: () => {
                        let _that = this
                        api.get('/member/media/deleteMediaLibraryDetail/' + id).then(response => {
                            if (response.data === 1) {
                                _that.fileList = []
                                let params = {mediaId: _that.fileFolderId, pageNum: _that.page, pageSize: _that.pageSize}
                                _that.queryFile(params)
                                _that.$Message.info('删除成功')
                            }
                        })
                    },
                    onCancel: () => {}
                })
            },
            downloadFile (url) {
                window.location.href = url
            },
            editFileOk () {
                let flag = this.handleSubmit('editFileInfo')
                if (!flag) {
                    return
                }
                let name = this.editFileInfo.file_name
                let id = this.id
                api.post('/member/media/updateMediaLibraryDetail', {
                    id: id,
                    name: name
                }).then(response => {
                    if (response.data === 1) {
                        this.editFileShow = false
                        this.fileList[this.index].title = name
                        this.editFileInfo.file_name = ''
                    }
                })
            },
            editFileCancel () {
                this.editFileInfo.file_name = ''
                this.editFileShow = false
                this.handleReset('editFileInfo')
            }
        }
    }
</script>
<style scoped>
    .border {
        width: 174px;
        height:140px;
    }
    .folder-drop {
        margin-top: 15px;
    }
    .file-drop {
        margin-top: 5px;
    }
    .tip {
        margin-left: 10px;
    }
    .pages {
        margin-top: 30px;
    }
    .pointer {
        cursor:pointer;
    }
    .photo-content {
        height: 190px;
        margin-bottom: 5px;
    }
</style>
<style lang="scss">
    .up-video {
        display: inline-block;
        width: 60px;
        text-align: center;
        border-radius: 4px;
        background: #fff;
        position: relative;
        margin-right: 4px;
        vertical-align: top;
        &:hover{
            .demo-upload-list-cover{
                display: block
            }
        }
        .demo-upload-list-cover {
            height: 60px;
            line-height: 60px;
        }
    }
    .drop-out {
        &:hover {
            .drop-in {
                display: block;
            }
            .share {
                display: block;
            }
        }
        .drop-in {
            display: none;
        }
        .share {
            display: none;
            position: absolute;
            z-index: 999;
            background-color: rgba(96, 96, 96, 0.4);
            text-align: center;
            bottom: 0;
            right: 0;
            left: 0;
        }
    }
</style>
