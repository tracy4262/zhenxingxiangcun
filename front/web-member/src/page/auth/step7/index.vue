<template>
  <div>
    <Card class="pd20">
      <Title title="年度文件" subTitle="（请选择年度文件夹，并对年度文件内容进行编辑）"></Title>
      <Row class="pd20 file-list">
        <Col span="3" v-for="(item, index) in files" :key="index">
        <div class="item tc mt15" @click="onFileSelect(item)" style="cursor:pointer">
          <img :src="`../static/img/${item.checked ? 'icon-file-active.png' : 'icon-file-default.png'}`" />
          <p class="ell">{{item.name}}</p>
          <Icon type="ios-close-circle" color="#ed4014" size="20" @click.stop="onFileDel(item)" />
        </div>
        </Col>
        <Col span="3">
        <div class="tc mt15" style="cursor:pointer" @click="onFileAdd">
          <img src="../../../../static/img/icon-file-add.png" />
          <p class="ell">添加</p>
        </div>
        </Col>
      </Row>
      <Divider />

      <Row v-if="yearId">
        <Col span="3" class="mt15">
          <Button :type="allBtn ? 'primary' : 'text'" @click="onTagSelectAll">
              <Tooltip placement="top" class="ell" :delay="1000">
                全部
                <div slot="content"> 全部 </div>
              </Tooltip>
          </Button>
        </Col>
        <Col span="3" class="mt15" v-for="(item, index) in fileTags" :key="index">
          <Button style="max-width: 100%;" :type="item.checked ? 'primary' : 'text'" @click="onTagSelect(item, index)">
              <Tooltip placement="top" class="ell" :delay="1000">
                {{item.name}}
                <div slot="content"> {{item.name}} </div>
              </Tooltip>
          </Button>
        </Col>
      </Row>
      <p v-else class="tc">请添加当前年份文件夹或选择其他年份编辑</p>
    </Card>

    <!-- 添加弹窗 -->
    <Modal
    v-model="fileAddModel"
    title="添加年度文件夹"
    :mask-closable="false"
    class-name="vertical-center-modal"
    width="360">
      <div>
        <DatePicker type="year" placeholder="请选择年份" format="yyyy年度" editable @on-change="getYear" 
        :open="open"
         @on-clear="open = false"
         @on-ok="open = false" style="width: 100%;">
          <a href="javascript:void(0)" @click="open = !open">
            <Input v-model="fileName" readonly icon="ios-calendar-outline" @on-blur="open = false"/>
          </a>
        </DatePicker>
        <!-- <DatePicker type="year" v-model="fileName" placeholder="请选择年份" format="yyyy年度" editable @on-change="getYear" style="width: 100%;"></DatePicker> -->
        <!-- <Input v-model="fileName" :maxlength="8" placeholder="请输入年度文件夹名称 不得超过8个汉字"></Input> -->
      </div>
      <div slot="footer">
          <Button type="text" @click="cancel">取消</Button>
          <Button type="primary" @click="onSaveFileName">确定</Button>
      </div>
    </Modal>
    
    <!-- 模块 -->
    <div class="mt20">
      <component v-bind:is="mode" :yearId="yearId" :appId="appId" @handleRefresh="refresh"></component>
    </div>
    <div class="tc pd20">
      <Button type="primary" @click="handleClickBack" class="back-btn mr20 mt40">返回并上一步</Button>
      <Button type="primary" @click="onSave" class="mt40">完成</Button>
    </div>


  </div>
</template>
<script>
import Title from '../components/title'
import all from './all'
import networkInfo from './network'
import geography from './geography'
import population from './population/index'
import environment from './environment/index'
import culturalLandscape from './culturalLandscape'
import honor from './honor/index'
import policy from './policy/index'
import professionalInformation from './professionalInformation/index'
import work from './work/index'
import education from './education/index'
import communalFacilities from './communalFacilities/index'
import familyMember from './familyMember/index'
import purchaseInformation from './purchaseInformation/index'
// import financial from './financial/index'
import reportManage from './reportManage/index'
import bankAccount from './bankAccount/index'
import historicalEvolution from './historicalEvolution'
import administrative from './administrative'
import nationalReligion from './nationalReligion'
import economicGrowth from './economicGrowth'
import privacy from './privacy'
import provideServices from './provideServices'
import outputProduct from './outputProduct'
import landInfo from './landInfo'
import landUseStatus from './landUseStatus'
import assetSetting from './assetSetting'
import expertAuthentication from './expertAuthentication/index'
import businessOutlets from './businessOutlets'


export default {
  components: {
    Title,
    all,
    networkInfo,
    geography,
    population,
    environment,
    culturalLandscape,
    honor,
    policy,
    professionalInformation,
    work,
    education,
    communalFacilities,
    familyMember,
    purchaseInformation,
    // financial,
    reportManage,
    bankAccount,
    historicalEvolution,
    administrative,
    nationalReligion,
    economicGrowth,
    privacy,
    provideServices,
    outputProduct,
    landInfo,
    landUseStatus,
    assetSetting,
    expertAuthentication,
    businessOutlets
  },
  data() {
    return {
      files: [],
      fileAddModel: false,
      fileName: '',
      allBtn: true,
      fileTags: [],
      mode: 'all',
      yearId: '',
      appId: '',
      active: null,
      stepId: '',
      displayName: '',
      open: false,
      flagCount:0,
    }
  },
  created () {
     // 查询模板 步骤
    this.$api.post('/member-reversion/realStep/findStep', {
          account: this.$user.loginAccount,
          templateId: this.$route.query.templateId
      }).then(response => {
          if (response.code === 200) {
              // reload 存在， 说明是点击模板后查询的 如果 response.data 存在 那么则需要修改url
              if (response.data) {
                  this.stepId = response.data.id
              }
          }
      })
      // 查询用户真实姓名
      this.$api.post('/member/login/findCurrentUser', {
          account: this.$user.loginAccount
        }).then(response => {
        	if (response.data.displayName) {
        		this.displayName = response.data.displayName
        	}
        })
    this.init()
    this.initFileTags()
  },
  methods: {
    // 上一步
    handleClickBack () {
      this.$router.push({
        path: '/auth/step6',
        query: {
          templateId: this.$route.query.templateId
        }
      })
    },
    // 完成
    onSave () {
      this.$api.post('/member-reversion/realStep/save', {
        loginStep:{
            id: this.stepId,
            account: this.$user.loginAccount,
            templateId: this.$route.query.templateId,
            step: 7
          }
      }).then(response => {
        if (response.code === 200) {
          this.$router.push(`/pro/member?uid=${this.$user.loginAccount}`)
        }
      })
    },
    // 初始化查询年度文件夹信息
    init () {
      this.yearId = ''
      this.$api.post('/member-reversion/perfect/findYearInfo', {
        account: this.$user.loginAccount
      }).then(response => {
        console.log('response', response)
        if (response.code === 200) {
          response.data.forEach(element => {
            this.files.push({
              name: element.fileName,
              id: element.id,
              checked: element.fileName.substring(0, 4) === new Date().getFullYear().toString() ? true : false
            })
          })
          // 默认年份id
          this.files.forEach(element => {
            if (element.checked) {
              this.yearId = element.id
            }
          })
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 初始化应用标签信息
    initFileTags () {
      this.$api.post('/member-reversion/user/perfect/findModuleInfo', {
        account: this.$user.loginAccount,
        level: '0',
        templateId: this.$route.query.templateId
      }).then(response => {
        console.log('tags response', response)
        if (response.code === 200) {
          response.data.forEach(element => {
            this.fileTags.push({
              id: element.appId,
              name: element.appName,
              mode: element.url
            })
            if(element.appName === '专业信息' || element.appName === '专家认证') {
              this.flagCount ++
            }
          })
          console.log('flagCount',this.flagCount)
          if (this.active !== null) {
            this.fileTags[this.active].checked = true
          } else {
            // 初始化 查询url里面有没有active 有的话就是从会员中心点击进来的，name可以进来的时候执行点击当前mode的事件
            let active = this.$route.query.active
            this.fileTags.forEach((element, index) => {
              if (element.mode == active) {
                this.onTagSelect(element, index)
              }
            });
          }
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 选择年度文件
    onFileSelect(d) {
      this.setStatus(this.files)
      d.checked = true
      this.yearId = d.id
      this.setStatus(this.fileTags)
      this.allBtn = true
      this.mode = 'all'
    },
    // 新增年度文件
    onFileAdd() {
      this.fileAddModel = true
    },
    // 新增年度文件夹
    onSaveFileName() {
      if (this.fileName !== '') {
        this.$api.post('/member-reversion/perfect/saveYearInfo', {
          account: this.$user.loginAccount,
          fileName: this.fileName
        }).then(response => {
          console.log('response', response)
          if (response.code === 200) {
            this.$Message.success('添加成功！')
            this.files = []
            this.init()
            this.fileAddModel = false
            this.fileName = ''
          } else if (response.code === 300) {
            this.$Message.error('该年度文件夹已存在！')
          }
        }).catch(error => {
          this.$Message.error('服务器异常！')
        })
      }
    },
    cancel () {
      this.fileAddModel = false
      this.fileName = ''
    },
    // 删除文件
    onFileDel(item) {
      this.$Modal.confirm({
        title: '操作提示',
        content: '<p>您确认要删除该文件吗？</p>',
        onOk: () => {
          this.$api.post('/member-reversion/perfect/deleteYearInfo', {
            id: item.id
          }).then(response => {
            console.log('response', response)
            if (response.code === 200) {
              this.$Message.info('删除成功！')
              this.files = []
              this.init()
            }
          }).catch(error => {
            this.$Message.error('删除失败！')
          })
        }
      })
    },
    // 选择全部
    onTagSelectAll () {
      this.setStatus(this.fileTags)
      this.allBtn = true
      this.mode = 'all'
    },
    // 选择标签
    onTagSelect (d, index) {
      console.log('ididididi', d)
      console.log(d.mode)
      this.setStatus(this.fileTags)
      d.checked = true
      this.allBtn = false
      this.mode = d.mode
      this.appId = d.id
      this.active = index
    },
    // 循环设置状态
    setStatus (obj) {
      obj.forEach(item => item.checked = false)
    },
    getYear (v1, v2) {
      this.fileName = v1
    },
    refresh () {
      this.fileTags = []
      this.initFileTags()
    }
  },
  mounted() {
    // 取文件夹
    /* this.$api.get('/year-file').then(res => {
      this.setStatus(res.data)
      this.files = res.data
    }) */
    // 取标签
    /* this.$api.get('/year-tag').then(res => {
      this.setStatus(res.data)
      this.fileTags = res.data
    }) */
  }
}
</script>
<style lang="scss">
.file-list {
  .item {
    position: relative;
    overflow: hidden;
    &:hover {
      .ivu-icon {
        right: 15px;
      }
    }
    .ivu-icon {
      position: absolute;
      top: 0;
      right: -100px;
      transition: all 0.3s;
    }
  }
}
</style>
<style lang="scss" scoped>
.back-btn {
  background-color: #9B9B9B;
  border-color: #9B9B9B;
  &:hover {
    background-color: #9B9B9B;
    border-color: #9B9B9B;
  }
}
</style>

