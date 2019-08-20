<template>
  <div class="pd20">
    <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh"></Title>
    <Form :label-width="100" label-position="left" ref="data" class="ml20 mt20">
    <Title title="会员所在地"></Title>
      <div class="pd20">
          <Row  :gutter="38">
            <Col span="8">
              <FormItem label="权限">
                <Switch class="ml20"  size="large" v-model="memberLocation.status">
                  <span slot="open">公开</span>
                  <span slot="close">隐藏</span>
                </Switch>
              </FormItem>
            </Col>
          </Row>
          <FormItem label="所在位置">
            <Row>
              <Col span="20">
                <vui-cascander :values="memberLocation.memberLocation.location" @handle-get-result="handleGetData"></vui-cascander>
              </Col>
            </Row>
          </FormItem>
          <FormItem label="详细地址">
            <Row>
              <Col span="10" class="pr10">
                <Input v-model="memberLocation.memberLocation.address" :maxlength="50"  placeholder="请输入所在街道/湾组/楼栋" @on-change="addressChange"></Input>
              </Col>
              <Col span="10" class="pl10">
                <Input v-model="memberLocation.memberLocation.house_number" :maxlength="20" placeholder="请输入详细门牌号"  @on-change="addressChange"></Input>
              </Col>
            </Row>
          </FormItem>
          <FormItem label="地址预览">
              <Input type="textarea" 
              :value="memberLocation.memberLocation.perfect_address" 
              :autosize="{minRows: 3,maxRows: 5}" readonly></Input>
            </FormItem>
      </div>
      
    <Title title="会员所在地经纬度"></Title>
      <div class="pd20  pb40">
          <Row :gutter="38">
            <Col span="8">
              <FormItem label="东经">
                <Input v-model="memberLatitudeLongitude.memberLatitudeLongitude.longitude" readonly >></Input>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="北纬">
                <Input v-model="memberLatitudeLongitude.memberLatitudeLongitude.latitude" readonly></Input>
              </FormItem>
            </Col>
            <Col span="8" style="text-decoration: underline; color: #6C6C6C; cursor: pointer;" class="pt5">
              <span @click="onSelectPoint(-1, memberLatitudeLongitude.memberLatitudeLongitude)">定位获取</span>
            </Col>
          </Row>
          <div class="pb30 pt10" v-if="memberLatitudeLongitude.memberLatitudeLongitude.latitude">
            <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${memberLatitudeLongitude.memberLatitudeLongitude.latitude},${memberLatitudeLongitude.memberLatitudeLongitude.longitude}&title=我的位置&content=${memberLatitudeLongitude.memberLatitudeLongitude.longitude,memberLatitudeLongitude.memberLatitudeLongitude.latitude}&output=html`">
              <img :src="`//api.map.baidu.com/staticimage/v2?ak=7syPirZ2AWxacMfHeAfuujdDgFmxCB5g&center=${memberLatitudeLongitude.memberLatitudeLongitude.longitude},${memberLatitudeLongitude.memberLatitudeLongitude.latitude}&zoom=15&markers=${memberLatitudeLongitude.memberLatitudeLongitude.longitude},${memberLatitudeLongitude.memberLatitudeLongitude.latitude}&width=800`" width="100%" />
            </a>
          </div>
      </div>
    <Title title="会员四邻"></Title>
      <div class="pd20  pb40">
        <Row  :gutter="38">
          <Col span="8">
            <FormItem label="权限">
              <Switch class="ml20"  size="large" v-model="memberNeighbor.status">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="16" class="four-neighbors">
          <Col span="24" class="pb20" v-for="(item, index) in memberNeighbor.memberNeighbor">
            <span>
              <!-- <Button v-if="!item.edit" style="max-width:110px"><span class="ell">{{item.name}}</span></Button>
              <Input style="width:110px;" v-else :maxlength="20" v-model="item.name" :ref="`neighbors${index}`"></Input> -->
              <Input style="width:110px;" :maxlength="20" v-model="item.name" :ref="`neighbors${index}`"  @on-change="changePreview"></Input>
            </span>
            <!-- <Button v-else>
              <span class="ell">
                最
                <span class="t-green" v-if="index === 0">东</span>
                <span class="t-green" v-if="index === 1">南</span>
                <span class="t-green" v-if="index === 2">西</span>
                <span class="t-green" v-if="index === 3">北</span>
                点经纬度
              </span>
            </Button> -->
            <span class="four-neighbors-label">东经</span> 
            <Input style="width:125px;" :maxlength="20" readonly v-model="item.east_longitude"  @on-change="changePreview"></Input>
            <span class="four-neighbors-label">北纬</span> 
            <Input style="width:125px;" :maxlength="20" readonly v-model="item.east_latitude"  @on-change="changePreview"></Input>
            <span class="four-neighbors-label">与</span> 
            <Input style="width:130px;" :maxlength="20" v-model="item.neighbor_name" placeholder="请填写标识物" @on-change="changePreview"></Input>
            <span class="four-neighbors-label">相邻</span>
            <span  style="text-decoration: underline; color: #6C6C6C; cursor: pointer; font-size: 12px;" @click="onSelectPoint(index)">定位获取</span>
            <Button class="ml10" v-if="index >= 4" @click="fourNeighborsDel(item, index)">删除</Button>
          </Col>
          <Button type="primary" ghost @click="fourNeighborsAdd" class="mt20 btn-light-primary" icon="md-add">增加</Button>
        </Row>
      </div>
    <Title title="查看实况地址"></Title>
      <div class="pd20 pb40">
        <Row  :gutter="38">
          <Col span="8">
            <FormItem label="权限">
              <Switch class="ml20"  size="large" v-model="memberLiveAddress.status">
                <span slot="open">公开</span>
                <span slot="close">隐藏</span>
              </Switch>
            </FormItem>
          </Col>
        </Row>
        <Row  :gutter="24" class="pb20" v-for="(item, index) in memberLiveAddress.memberLiveAddress">
          <Col span="1">
            {{index+1}}、
          </Col>
          <Col span="8">
            <Input v-model="item.name" :maxlength="20"></Input>
          </Col>
            <Col span="12">
              <Input v-model="item.url" :maxlength="200"></Input>
          </Col>
            <Col span="3" v-if="memberLiveAddress.memberLiveAddress.length != 1">
            <Button class="ml10" @click="handleDelLiveAddress(item, index)">删除</Button>
          </Col>
        </Row>
        <Row  :gutter="24">
          <Col span="8" offset="1">
            <Button type="primary" @click="handleAddArress">增加地址</Button>
          </Col>
        </Row>
      </div>
      <Title title="文字预览"></Title>
      <div class="pd20 pt30">
        <Input type="textarea" v-model="textPreview.text_preview" :autosize="{minRows: 3,maxRows: 5}"></Input>
      </div>
        
    </Form>
    <div class="pd40 tc">
      <Button type="primary" v-if="isLoading">保存</Button>
      <Button type="primary" v-else @click="onSave">保存</Button>
    </div>
     <vui-map ref="experMap" @on-get-point="onGetPoint"></vui-map>
  </div>
</template>

<script>
import Title from '../../components/title'
import vuiCascander from '~components/vuiCascader/index'
import vuiMap from '../../../member/components/productionMap'
export default {
  components: {
    Title,
    vuiCascander,
    vuiMap
  },
  props: {
    yearId: {
      type: String
    },
    id: {
      type: String
    },
    appId: {
      type: String
    }
  },
  data () {
    return {
      // 会员四邻 neighbor_flag 0 默认的，1 新增的
      memberNeighbor: {
        status: true,
        memberNeighbor: [
          // {name: '', east_latitude: '', east_longitude: '', neighbor_name: '', neighbor_flag: 0}
        ],
        memberNeighbor_name: '会员四邻'
      },
      // 会员所在地经纬度
      memberLatitudeLongitude: {
        status: true,
        memberLatitudeLongitude:{},
        memberLatitudeLongitude_name: '会员所在地经纬度'
      },
      // 查看实况地址
      memberLiveAddress: {
        status: true,
        memberLiveAddress: [
          {name: '', url: ''}
        ],
        memberLiveAddress_name: '查看实况地址'
      },
      // 会员虚拟信息
      memberLocation: {
        status: true,
        memberLocation: {
            location: '',
            address: '',
            house_number: '',
            perfect_address: ''
          },
        // memberLocation_name: '会员虚拟信息'
      },
      textPreview: {},
      location: '',
      account: '',
      templateId: '',
      isLoading: true,
      title: '地理位置信息'
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
    //初始化获取收藏夹数据
    handleInit(){
        this.$api.post('/member-reversion/physicalGeography/findGeographicalPosition', {
          user_id: this.account,
          year_id: this.yearId,
          parent_id: this.id,
          templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
                this.isLoading = false
                this.memberNeighbor.memberNeighbor = response.data.memberNeighbor
                this.memberNeighbor.status = response.data.memberNeighborStatus

                this.memberLatitudeLongitude.memberLatitudeLongitude = response.data.memberLatitudeLongitude
                this.memberLatitudeLongitude.status = response.data.memberLatitudeLongitudeStatus

                this.memberLiveAddress.memberLiveAddress = response.data.memberLiveAddress
                this.memberLiveAddress.status = response.data.memberLiveAddressStatus

                this.memberLocation.memberLocation = response.data.memberLocation
                this.memberLocation.status = response.data.memberLocationStatus

                this.textPreview = response.data.textPreview
                this.sys_dict_id = this.id
            }
        })
    },
    // 保存
    onSave () {
      this.isLoading = true
      this.textPreview.is_complete = '1'
      let list = {
        memberNeighbor: this.memberNeighbor,
        memberLatitudeLongitude: this.memberLatitudeLongitude,
        memberLiveAddress: this.memberLiveAddress,
        memberLocation: this.memberLocation,
        textPreview: this.textPreview,
        sys_dict_id: this.sys_dict_id,
        yearId: this.yearId,
        user_id: this.$user.loginAccount,
        templateId: this.templateId
      }
      this.$api.post('/member-reversion/physicalGeography/saveGeographicalPosition', list).then(response => {
        if (response.code === 200) {
          this.$Message.success('保存成功')
          this.$emit('on-save')
          this.handleInit()
        }
      })
    },
    // 会员虚拟信息部分
    // 所在位置
    handleGetData (value, selectedData) {
      let labelArr = []
      selectedData.forEach(element => {
          labelArr.push(element.label)
      })
      this.memberLocation.memberLocation.location = labelArr.join('/')
      this.$nextTick(() => {
        this.addressChange()
      })
    },
    // 地址发生改变
    addressChange () {
      let str = ''
      this.memberLocation.memberLocation.perfect_address = ''
      if (this.memberLocation.memberLocation.location) {
        str += '所在位置：' + this.memberLocation.memberLocation.location + '、'
      }
      if (this.memberLocation.memberLocation.address ) {
          str += '详细地址：' + this.memberLocation.memberLocation.address + '、'
      }
      if (this.memberLocation.memberLocation.house_number) {
        str += this.memberLocation.memberLocation.house_number + '号、'
      }
      if (str) {
        str = str.substring(0, str.length - 1) + '。'
      }
      this.memberLocation.memberLocation.perfect_address = str
      this.$nextTick(() => {
        this.changePreview()
      })
    },
    // 文字预览 
    changePreview () {
      this.textPreview.text_preview = ''
      // 会员所在地
      if (this.memberLocation.memberLocation.perfect_address) {
        this.textPreview.text_preview += this.memberLocation.memberLocation.perfect_address.substring(0, this.memberLocation.memberLocation.perfect_address.length - 1) + '，'
      }
      // 会员所在地经纬度
      if (this.location) {
        this.textPreview.text_preview += `东经：${this.memberLatitudeLongitude.memberLatitudeLongitude.longitude}，北纬：${this.memberLatitudeLongitude.memberLatitudeLongitude.latitude}，`
      }
      // 会员四邻部分
      this.memberNeighbor.memberNeighbor.forEach(e => {
        if (e.neighbor_name && e.name) {
          this.textPreview.text_preview += `${e.name}与${e.neighbor_name}相邻，`
        }
      })
      if (this.textPreview.text_preview) {
        this.textPreview.text_preview = `${this.textPreview.text_preview.substring(0,this.textPreview.text_preview.length-1)}。`
      }
    },
    // 所在经纬度部分
    // 点击定位获取
    onSelectPoint (index) {
      this.activeMode = index
      // 地图 数据回显
      if (index === -1) {
        if (this.memberLatitudeLongitude.memberLatitudeLongitude.latitude) {
          this.$refs.experMap.points = {lat: this.memberLatitudeLongitude.memberLatitudeLongitude.latitude, lng: this.memberLatitudeLongitude.memberLatitudeLongitude.longitude}
        } else {
          this.$refs.experMap.points = {}
        }
      } else {
        if (this.memberNeighbor.memberNeighbor[this.activeMode].east_latitude) {
          this.$refs.experMap.points = {lat: this.memberNeighbor.memberNeighbor[this.activeMode].east_latitude, lng: this.memberNeighbor.memberNeighbor[this.activeMode].east_longitude}
        } else {
          this.$refs.experMap.points = {}
        }
      }
      this.$refs.experMap.showMap = true
    },
    // 取坐标
    onGetPoint (point) {
      if (this.activeMode != -1) {
        let item = this.memberNeighbor.memberNeighbor[this.activeMode]
        if(point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
          item.east_longitude = point.lng
          item.east_latitude = point.lat
        } else {
          item.east_longitude = ''
          item.east_latitude = ''
        }
        this.memberNeighbor.memberNeighbor.splice(this.activeMode, 1, item)
      } else {
        if(point.lng !== '' && point.lng !== undefined && point.lat !== '' && point.lat !== undefined) {
          this.memberLatitudeLongitude.memberLatitudeLongitude.longitude = point.lng
          this.memberLatitudeLongitude.memberLatitudeLongitude.latitude = point.lat
          this.$nextTick(() => {
            this.changePreview()
          })
          this.location = `${point.lat},${point.lng}`
        } else {
          this.location = ''
          this.memberLatitudeLongitude.memberLatitudeLongitude.longitude = ''
          this.memberLatitudeLongitude.memberLatitudeLongitude.latitude = ''
        }
      }
    },
    // 会员四邻部分
    // 点击添加
    fourNeighborsAdd () {
      let flag = 0
      this.memberNeighbor.memberNeighbor.length > 4 ? flag = 1 : flag = 0
      let list = {
          name: '',
          longitude: '',
          east_latitude: '',
          neighbor_name: '',
          neighbor_flag: flag,
          edit: true
        }
      this.memberNeighbor.memberNeighbor.push(list)
      this.$nextTick(e => {
        if (this.memberNeighbor.memberNeighbor.length > 4) {
          this.$refs[`neighbors${this.memberNeighbor.memberNeighbor.length-1}`][0].focus()
        }
      })
    },
    // 删除
    fourNeighborsDel (item, index) {
      this.$Modal.confirm({
          title: '是否确定删除',
          onOk:()=>{
            if (item.id) {
              this.$api.post('/member-reversion/physicalGeography/deleteMemberNeighbor',{id: item.id}).then(response => {
                if (response.code === 200) {
                  this.memberNeighbor.memberNeighbor.splice(index, 1)
                  this.$Message.success('删除成功!')
                  this.changePreview()
                }
              })
            } else {
              this.memberNeighbor.memberNeighbor.splice(index, 1)
              this.$Message.success('删除成功!')
              this.changePreview()
            }
          },
          okText:'确定',
          cancelText:'取消'
      })
    },
    // 查看实况部分
    // 点击增加地址
    handleAddArress () {
      let list = {
        name: '',
        url: ''
      }
      this.memberLiveAddress.memberLiveAddress.push(list)
    },
    // 点击删除
    handleDelLiveAddress (item, index) {
      this.$Modal.confirm({
          title: '是否确定删除',
          // content: '是否确认删除？',
          onOk:()=>{
            if (item.id) {
              this.$api.post('/member-reversion/physicalGeography/deleteMemberLiveAddress',{id: item.id}).then(response => {
                if (response.code === 200) {
                  this.memberLiveAddress.memberLiveAddress.splice(index, 1)
                  this.$Message.success('删除成功!')
                }
              })
            } else {
              this.memberLiveAddress.memberLiveAddress.splice(index, 1)
              this.$Message.success('删除成功!')
            }
          },
          okText:'确定',
          cancelText:'取消'
      });
    },
    leftRefresh () {
        this.$emit('left-refresh')
    }
  }
}
</script>

<style lang="scss" scoped>
.four-neighbors {
  .four-neighbors-label{
    font-size: 12px;
    padding: 0px 10px;
  }
}
</style>
