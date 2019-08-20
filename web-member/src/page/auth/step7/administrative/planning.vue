<template>
  <div class="pd20">
    <Form :label-width="100" label-position="left">
      <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh"></Title>
      <div class="pd20">
          <Row  :gutter="38">
            <Col span="8">
              <FormItem label="权限">
                <Switch class="ml20"  size="large" v-model="status">
                  <span slot="open">公开</span>
                  <span slot="close">隐藏</span>
                </Switch>
              </FormItem>
            </Col>
          </Row>
          <Row  :gutter="38">
            <Col span="4">
             <Button class="btn-light-primary" @click="handleAdd" icon="md-add">增加节点</Button>
            </Col>
            <Col span="8" offset="12">
              <Input v-model="keyWord" placeholder="查找" suffix="ios-search" @on-change="onSearchTree" :maxlength="20"></Input>
            </Col>
          </Row>
          <div class="pt20 pb20">
            <Tree :data="data" :render="renderContent"></Tree>
          </div>
      </div>
      <Title title="文字预览"></Title>
      <div class="pd20 pt30">
        <Input type="textarea" v-model="textPreview.text_preview" :autosize="{minRows: 3,maxRows: 5}"></Input>
      </div>
        
    </Form>
    <div class="pd40 tc">
      <Button type="primary" v-if="isLoading">保存</Button>
      <Button type="primary" @click="onSave" v-else>保存</Button>
    </div>
  </div>
</template>

<script>
import Title from '../../components/title'
import {TreeFilter} from '../../mixins.js'
export default {
  components: {
    Title
  },
  mixins: [TreeFilter],
  props: {
    yearId: {
      type: String
    },
    appId: {
      type: String
    },
    id: {
    type: String
    }
  },
  data () {
    return {
      data: [],
      textPreview: {},
      status: true,
      sys_dict_id: '',
      title: '行政区划',
      account: '',
      templateId: '',
      isLoading: true
    }
  },
  created() {
    this.templateId = this.$route.query.templateId
    this.account = this.$user.loginAccount
  },
  methods: {
    initTitle () {
        this.$api.post('/member-reversion/user/perfect/findTableHead', {
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
                if (response.data.propertyName) {
                    this.title = response.data.propertyName
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    // 增加
    handleAdd () {
      this.data.push({
          pid: 0,
          name: '新建文件夹',
          expand: true,
          children: []
      })
      // 调用保存方法
        this.saveCollections(this.data[this.data.length-1], 'add')
    },
    // 保存收藏夹
    saveCollections(data, type){
        //保存操作，保存完成后调重新获取数据
        let list = {
            sys_dict_id: this.id,
            user_id: this.account,
            textPreview: this.textPreview,
            administrativeDivision_name: this.title,
            administrativeDivision: data,
            status: this.status,
            yearId: this.yearId,
            templateId: this.templateId
        }
        if(this.checkCollection(data.name)){
            this.$api.post('/member-reversion/administrationDivision/saveAdministrativeDivision', list).then(res => {
                if ( 200 === res.code) {
                    if (type === 'add') {
                        this.$Message.success('添加成功')
                    } else {
                        this.$Message.success('编辑成功')
                    }
                    this.handleInit(1)
                }else{
                    if(400 === res.code && 'exist' === res.data){
                        this.$Message.error('分组名不能相同')
                        this.handleInit(1)
                    }
                }
            })
        }
    },
    //初始化获取收藏夹数据
    handleInit(type){
        this.$api.post('/member-reversion/administrationDivision/findAdministrativeDivision', {
            user_id: this.account,
            year_id: this.yearId,
            parent_id: this.id,
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
                this.isLoading = false
                this.data = response.data.administrativeDivision
                // this.title = response.data.administrativeDivision_name
                if (!type) {
                    this.textPreview = response.data.textPreview
                }
                this.status = response.data.status
                this.oldTreeData = this.data
            }
        })
    },
    renderContent (h, {root, node, data}) {
        return h('span', [
            //左侧
            h('span', [
                h('Icon', {
                    props: {
                        type: 'ios-paper-outline'
                    },
                    style: {
                        marginRight: '8px'
                    }
                }),
                h('Input', {
                      props: {
                        value: data.name,
                        size: 'small',
                        autofocus: true
                    },
                    style: {
                        width: '120px'
                    },
                    on: {
                        'on-blur': (e) => {
                            // if(e.target.value != data.name){
                                this.updateCollect(data,e.target.value)
                            // }
                        }
                    }
                }),
            ]),
            //右侧
            h('span', {
                style: {
                    display: 'inline-block',
                    float: 'right',
                    marginRight: '32px'
                }
              }, [
                //添加按钮
                h('Button', {
                props: {
                    size: 'small',
                    icon:'md-add'
                },
                style: {
                    marginRight: '8px'
                },
                on: {
                    click: () => {
                        this.append(data)
                    }
                }}),
                // 删按钮
                h('Button', {
                    props: {
                        size: 'small',
                        icon: 'md-close'
                    },
                    on: {
                        click: () => {
                            console.log('删除')
                            this.remove(root, node, data)
                        }
                    }
                })
            ])
        ])
    },
    // 失去焦点
    updateCollect(data,title){
        //修改操作，修改完成后调重新获取数据
       data.name = title
       this.saveCollections(data, 'edit')
    },
     //判断收藏夹名称长度以及是否存在
    checkCollection (title) {
        //判断是否为空
        if (title) {
            //判断长度
            if (title.length > 20) {
                this.$Message.error('输入字数不能超过20个字！')
                return false
            } else {
                return true
            }
        } else {
            this.$Message.error('请输入收藏夹名称')
            return false
        }
    },
    // 增加
    append (data) {
        data.children.push({
            remark:'',
            pid: data.id,
            name: `新建文件夹${data.children.length+1}`,
            expand: true,
            id: 0,
            children:[]
        })
        this.saveCollections({
            remark:'',
            pid: data.id,
            name: `新建文件夹${data.children.length+1}`,
            expand: true,
            id: 0,
            children:[]
        }, 'add')
    },
    // 删除
    remove (root, node, data) {
      if (node.node.children.length) { 
          // 说明下级还有子文件夹
          this.$Message.warning('请先删除下级目录')
      } else {
          this.$Modal.confirm({
              title: '是否确定删除',
              content: '是否确认删除？',
              onOk:()=>{
                  // root坐在父节点的所有元素，包含父元素下所有子元素的children，nodeKey
                  this.$api.post('/member-reversion/administrationDivision/deleteAdministrativeDivision', {
                      id: data.id
                  }).then(res => {
                      if ( 200 === res.code) {
                        this.$Message.success('删除成功！')
                        this.handleInit()
                      }
                  })
              },
              okText:'确定',
              cancelText:'取消'
          });
      }
    },
    // 保存
    onSave () {
        if (!this.isLoading) {
            this.isLoading = true
            this.textPreview.is_complete = true
            let list = {
                textPreview: this.textPreview,
                sys_dict_id: this.id,
                administrativeDivision_name: this.title,
                yearId: this.yearId,
                user_id: this.$user.loginAccount,
                status: this.status,
                templateId: this.templateId
            }
            this.$api.post('/member-reversion/administrationDivision/saveTextPreview', list).then(response => {
                this.isLoading = false
                if (response.code === 200) {
                    this.$Message.success('保存成功')
                    this.$emit('on-save')
                    this.handleInit()
                }
            })
        }
    },
    leftRefresh () {
        this.$emit('left-refresh')
    }
  }
}
</script>
