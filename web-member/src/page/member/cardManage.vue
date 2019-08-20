<template>
	<div class="layout">
	
    <div class="mb20 clear">
        名片类型
        <Select v-model="cardMangeSearch.type"  style="width:120px">
            <Option value="">--请选择--</Option>
            <Option value="个人名片">个人名片</Option>
            <Option value="企业名片">企业名片</Option>
        </Select>
       名片名称
        <Input v-model="cardMangeSearch.cardName" placeholder="请输入关键字" style="width:120px" /> 个人/企业名称
        <Input v-model="cardMangeSearch.name" placeholder="请输入关键字" style="width:120px" />       
        <Button type="primary" @click="selectQuery">查询</Button>
        <Button type="default" class="fr" @click.native="addCardMange">新增名片</Button>
    </div>
    <Table  border ref="cardMangeTable" size="small" :columns="cardMangeTable.columns" :data="cardMangeTable.data" />
    <div style="float: right" v-if="total>0">
        <Page :total="total" :page-size="pageSize" :current="currentPage"  @on-change="nextPage"></Page>
    </div>
         <!-- 弹层 -->
        <Modal v-model="cardMangeModalShow" title="名片管理" width="600" @on-ok="submit" @on-cancel="cancel">
            <Form :model="cardMangeForm" :label-width="100">
                <FormItem label="选择名片类型">
                    <Select v-model="cardMangeForm.type">
                        <Option value="个人名片">个人名片</Option>
                        <Option value="企业名片">企业名片</Option>
                    </Select>
                </FormItem>
                <FormItem label="名片名称">
                    <Row>
                        <Col span="9">
                            <Input v-model="cardMangeForm.cardName" value="cardMangeForm.cardName" placeholder="请输入" />
                        </Col>
                        <Col span="6" class="tr">个人/企业名称&emsp;</Col>
                        <Col span="9">
                            <Input v-model="cardMangeForm.name" value="cardMangeForm.name" placeholder="请输入" />
                        </Col>
                    </Row>
                </FormItem>
				 <FormItem label="简介">
                    <Row>
                        <Col span="24">
                            <Input v-model="cardMangeForm.synopsis" type="textarea" :rows="4"  value="cardMangeForm.synopsis" placeholder="请输入" />
                            <Button type="default" class="fr" @click.native="inputUserInfo">个人信息导入</Button>
                     </Col>
                    </Row>
                </FormItem>
				 <FormItem label="上传头像">
                    <Row>
                        <Col span="6">
                            <div style="float:left;margin:0px 40px;width: 40px;">
                                <div class="demo-upload-list" v-for="item in uploadList">
                                    <template v-if="item.status === 'finished'">
                                        <img :src="'http://'+item.response.data.picName">
                                                                            
                                    </template>
                                    <template v-else>
                                        <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
                                    </template>
                                </div>
                            </div>
                            <Upload ref="upload" :show-upload-list="false"
                                                name="upfile"
                                                :max-size="20480"
                                                :on-success="handleSuccess"
                                                :format="['jpg','png','jpeg']"
                                                multiple type="drag"
                                                :action="action"
                                                style="float:right;margin-top:25px;margin-top: 30px; margin-left: 30px;">
                                <Button type="primary" shape="circle" style="margin-top: 2px;" :disabled="size === 1">选择图片</Button>
                                <Button type="primary" shape="circle" style="margin-top: 2px;" @click.native="again">重新选择</Button>
                            </Upload>
                        </Col>
                    </Row>
                </FormItem>
            </Form>
        </Modal>

         <Modal v-model="userInfoModalShow" title="个人信息导入" width="600" @on-ok="userInfoSubmit" >
            <Row class="userInfo-list">
                <Col span="6" v-for="(item,index) in userInfoList" :key="index">
                        <div class="vui-flex vui-flex-middle wrap" :class="{'on':item.checked}" @click="handleSelect(index)">
                        <Icon type="android-checkbox" :size="22" class="t-green" style="position:absolute;top:10px;right:10px;" v-if="item.checked" />
                        <div class="vui-flex-item">
                            <p class="h6 t-green">账号: {{item.account}}</p>
                            <p>宗教: {{item.religion}}</p>
                            <p>政治面貌: {{item.policial}}</p>
                        </div>
                    </div>
                </Col>
            </Row>
            <div style="float: right">
                 <Page :total="total2" :page-size="pageSize2" :current="currentPage2"  @on-change="userInfoNextPage"></Page>
            </div>
        </Modal>
	</div>
</template>
<script>
    import api from '~api'
   
	export default {
 
		data() {
			return {
            lastchecked:0,
            userInfoList:[],
            userInfoModalShow:false,
            aaction: `${this.$url.upload}/upload/up`,
            total: 0, 
            pageSize: 5,
            currentPage: 1,
            total2: 0, 
            pageSize2: 8,
            currentPage2: 1,
            uploadList: [],
            modal1: false,
            size:0,
            cardMangeSearch:{
                cardName: '',
                name: '',
                type: ''
                },
            cardMangeTable:{
                columns:[
                    {
                        title: '名片名称',
                        key: 'cardName'
                    },{
                        title: '个人/企业名称',
                        key: 'name'
                    },{
                        title: '类型',
                        key: 'type'
                    },{
                        title: '更新时间',
                        key: 'updateTime'
                    },{
                        title: '操作',
                        key: 'action',
                        width: 300,
                        align: 'center',
                        render: (h,params) => {
                            return h('div', [
                                 h('Button', {                                    
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.particulars(params.row.id)
                                        }
                                    }
                                },'详情'),
                                
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },

                                    on: {
                                        click: () => {
                                            this.createQR(params.row.id)
                                        }
                                    }
                                },'查看二维码'),

                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.remove(params.row.id)
                                        }
                                    }
                                },'删除')
                            ]);
                        }
                    }
                ],
                data:[
                ],
                flag:true,
                index:0
            },
            cardMangeModalShow:false,
            cardMangeForm: {
                cardName: '',
                name:'',
                type:'',
				synopsis:'',
				picture:''
            },
			num:0,
			value: '',
			
			}
		},
		
created: 
    function() {
        this.selectQuery()
	},
    methods:{

        userInfoSubmit(){
            api.post(
                '/member/Card/userInfoSubmit/',
                { 
                     account: this.userInfoList[this.lastchecked].account					
				}
                ).then(response => {
                console.log(response)
                this.cardMangeForm.synopsis="账号: "+response.data.account+"  宗教: "+response.data.religion+"  政治面貌: "+response.data.policial
			}).catch(function(error) {
				console.log(error)
			})
        },

        //选择用户  只能选择一个用户
        handleSelect(index){
            this.userInfoList.forEach(item=>{
                item.checked = false
            })
            this.lastchecked=index
            this.userInfoList[index].checked = !this.userInfoList[index].checked
        },

        //查询用户信息
        selectUserInfo(){
            api.post(
                '/member/Card/userInfoList/'+this.currentPage2 + "?pageSize=" + this.pageSize2,	
                ).then(response => {
				console.log(response)
                this.userInfoList = response.data.list
                this.total2 = response.data.total
			}).catch(function(error) {
				console.log(error)
			})
        },

        userInfoNextPage(page){
            this.currentPage2 = page;
            api.post(
                '/member/Card/userInfoList/'+this.currentPage2 + "?pageSize=" + this.pageSize2,	
                ).then(response => {
				
                this.userInfoList = response.data.list
                this.total2 = response.data.total
                console.log(this.userInfoList)
			}).catch(function(error) {
				console.log(error)
			})
        },

        inputUserInfo(){
            this.cardMangeTable.flag = true
            this.cardMangeModalShow = true
            this.userInfoModalShow = true
            this.selectUserInfo();
        },

        //图片上传
        handleSuccess(response, file, fileList) {
            console.log("返回图片")
            this.cardMangeForm.picture='http://'+response.data.picName
			if(response.code==500){
				this.$Message.error('上传失败!')
			}else{
                this.$Message.success('上传成功!')
                this.uploadList = this.$refs.upload.fileList
				this.size=this.$refs.upload.fileList.length
		        console.log('size',this.size)
			}
			console.log(response)
			console.log(fileList)
            console.log(file)
        },
        //重新上传，删除以前照片
		again(){
			this.$refs.upload.fileList=[];
		},

		selectQuery() {
            api.post(
                '/member/Card/selectQuery/'+this.currentPage + "?pageSize=" + this.pageSize,
                {
                    type: this.cardMangeSearch.type,
                    cardName: this.cardMangeSearch.cardName,	
                    name: this.cardMangeSearch.name,						
				}
                ).then(response => {
				console.log(response)
                this.cardMangeTable.data = response.data.list
                this.total = response.data.total
			}).catch(function(error) {
				console.log(error)
			})
        },

        nextPage(page) {
            this.currentPage = page;
            api.post(
               '/member/Card/selectQuery/'+this.currentPage + "?pageSize=" + this.pageSize,
                {
                    type: this.cardMangeSearch.type,
                    cardName: this.cardMangeSearch.cardName,	
                    name: this.cardMangeSearch.name,						
				}	
                ).then(response => {
				console.log(response)
                this.cardMangeTable.data = response.data.list
                this.total = response.data.total
			}).catch(function(error) {
				console.log(error)
			})
        },
        

		//添加按钮
        addCardMange(){
            this.cardMangeTable.flag = true
            this.cardMangeModalShow = true
		},
		
		//提交
        submit(){
            if (this.cardMangeForm.type=='') {
                 this.$Message.error('名片类型没有选择')
				 console.log(error)
            }
            if (this.cardMangeForm.cardName=='') {
                 this.$Message.error('名片名称没有填写')
				 console.log(error)
            }
            if (this.cardMangeForm.name=='') {
                 this.$Message.error('个人/企业名称没有填写')
				 console.log(error)
            }
            api.post('/member/Card/insert',{
				type: this.cardMangeForm.type,
                cardName: this.cardMangeForm.cardName,	
                name: this.cardMangeForm.name,	
                synopsis: this.cardMangeForm.synopsis,	
                picture: 'http://'+this.uploadList[0].response.data.picName,						
            }).then(response => {
                console.log(response.data);
                if(1 == response.data) {
                    this.$Message.success('添加成功!');
                    cardName: '';
                    name: '';
                    type: '';
                    synopsis: '';
                    picture: '';
                    this.selectQuery()
                } else if(0 == response.data) {
                    this.$Message.error('用户登录失效');
                }
            }).catch(function(error) {
                console.log(error)
            })
            
        },

		//取消
		cancel(){
            this.cardMangeModalShow = false
        },

        //详情
        particulars(id){
            api.post(
                '/member/Card/fingById',{
                    id: id,		
				}
			).then(response => {
                this.$Modal.info({
                title: '名片详情',
                content: `名片类型: ${response.data.type}<br>
                          名片名称: ${response.data.cardName}<br>
                          个人/企业名称: ${response.data.name}<br>
                          简介: ${response.data.synopsis}<br>
                          头像: ${response.data.picture}`
                })
			}).catch(function(error) {
				console.log(error)
            })
        },

        //生成二维码
        createQR(id){
             api.post(
                '/member/Card/createQR',{
                    id: id,		
				}
			).then(response => {
                const content ='http://'+response.data
                console.log(content)
                this.$Modal.info({
                    title: '二维码',
                    render: (h) => {
                        return h('Img', {
                            attrs: {
                                src: content,
                            },
                            style: {
                                width: '200px',
                                height: '200px',
                            },
                        })
                    }
                })
			}).catch(function(error) {
				console.log(error)
            })
        },

        //删除
        remove (id) {
            this.$Modal.confirm({
                title: '请你确认是否删除',
                onOk: () => {
                    api.post(
                        '/member/Card/remove',
                        {
                            id: id,							
                        }
                    )
                    .then(response => {
                        this.selectQuery()
                        if(1 == response.data) {
                            this.$Message.success('删除成功!');
                        } else {
                            this.$Message.error('删除失败');
                        }
                    }).catch(function(error) {
                        console.log(error)
                    })
                },
                onCancel: () => {
                    this.$Message.info('取消删除');
                }
            });
        }      
    }
}
</script>

<style scoped>

    .demo-upload-list {
		display: inline-block;
		width: 60px;
		height: 60px;
		text-align: center;
		line-height: 60px;
		border: 1px solid transparent;
		border-radius: 4px;
		overflow: hidden;
		background: #fff;
		position: relative;
		box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
		margin-right: 4px;
	}

	.demo-upload-list img {
		width: 100%;
		height: 100%;
	}

	.demo-upload-list:hover .demo-upload-list-cover {
		display: block;
	}

	.demo-upload-list-cover {
		color: #fff;
		font-size: 16px;
		display: none;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		background: rgba(0, 0, 0, .6);
	}
    /*main样式开始*/
    .main-l {
        border: 1px solid #ededed;
        margin-right: 0;
    }

    .main-r-top {
        height: 134px;
        background: url("../../img/images/hyzx-banner.png") no-repeat top center;
    }

    .main-r-top ul {
        float: right;
        margin: 70px 30px 0 0;
    }

    .main-r-top ul li {
        float: left;
        margin-left: 34px;
        text-align: center;
        font-size: 16px;
    }

    .main-r-top ul li p {
        font-size: 20px;
    }

    .main-r-top ul li span {
        margin-left: 0px;
        font-size: 14px;
    }
</style>
<style lang="scss">
.userInfo-list{
    .wrap{
        &:hover{
            box-shadow: 0 0 3px 1px rgba(0,0,0,.1);
        }
        &.on{
            border-color: #00c587;
        }
        position: relative;
        margin: 10px;
        border:1px solid #efefef;
        transition: all .3s;
    }
    .ivu-col{
        padding: 0 !important;
    }
    .ivu-btn{
        min-width: 80px;
    }
}
</style>