<template>
  <div class="dish-list-ivu">
      <Row class="mt20 pl20">
          <Col span="24" style="line-height: 30px;">
              <span>包房</span>
              <span v-for="(item,index) in privateRoomDatas" :key="index" class="pl10 pr10">
                  <span @click="choosePrivateRoom(item, index)" :class="{'farm-group-btn-active': index === activePrivateRoom, 'farm-group-btn': true}">
                      {{ item.roomName }}
                  </span>
              </span>
          </Col>
      </Row>
      <Row class="mt20 pl20">
          <Col span="24" style="line-height: 30px;">
              <span>餐桌</span>
              <span v-for="(item,index) in diningTableDatas" :key="index" class="pl10 pr10">
                  <span @click="chooseDiningTable(item, index)" :class="{'farm-group-btn-active': index === activeDiningTable, 'farm-group-btn': true}">
                      {{ item.number }}
                  </span>
              </span>
          </Col>
      </Row>
      <Row class="mt20 pl20">
          <Col span="24" style="line-height: 30px;">
              <span>菜品分类</span>
              <span v-for="(item,index) in dishDatas" :key="index" class="pl10 pr10">
                  <span @click="chooseDishData(item, index)" :class="{'farm-group-btn-active': index === activeDish, 'farm-group-btn': true}">
                      {{ item.foodClassName }}
                  </span>
              </span>
          </Col>
      </Row>
      <Row class="mt20 pl20" :gutter="18">
          <Col span="4" v-for="(item, index) in dishList" class="pb20">
            <Card>
              <div class="tc" style="">
                <img :src="item.foodImage[0]" alt="" width="100%" height="142px">
                <p class="pt10 ell" :title="item.foodName">{{item.foodName}}</p>
                <p class="pt5 pb10" v-if="item.discountPrice">
                  <span class="t-orange">¥ {{item.discountPrice}}</span> 
                  <span class="t-grey pl5" style="text-decoration: line-through;">¥ {{item.foodPrice}}</span>
                </p>
                <p class="pt5 pb10" v-else>
                  <span class="t-orange">¥ {{item.foodPrice}}</span>
                </p>
              </div>
            </Card>
          </Col>
      </Row>
  </div>
    
  <!-- <div class="dish-list-ivu">
      <Row type="flex" align="middle" style="border: 1px solid rgb(244, 244, 244); border-bottom:none;">
          <Col span="2" style="line-height: 30px;" class="pt15 pb15 tc">
            <span>包房</span>
          </Col>
          <Col span="22" style="line-height: 30px; border-left: 1px solid rgb(244, 244, 244);" class="pt15 pb15">
              <span v-for="(item,index) in privateRoomDatas" :key="index" class="pl10 pr10">
                  <span @click="choosePrivateRoom(item, index)" :class="{'farm-group-btn-active': index === activePrivateRoom, 'farm-group-btn': true}">
                      {{ item.roomName }}
                  </span>
              </span>
          </Col>
      </Row>
      <Row type="flex" align="middle" style="border: 1px solid rgb(244, 244, 244); border-bottom:none;">
          <Col span="2" style="line-height: 30px;" class="pt15 pb15 tc">
              <span>餐桌</span>
          </Col>
          <Col span="22" style="line-height: 30px; border-left: 1px solid rgb(244, 244, 244);" class="pt15 pb15">
              <span v-for="(item,index) in diningTableDatas" :key="index" class="pl10 pr10">
                  <span @click="chooseDiningTable(item, index)" :class="{'farm-group-btn-active': index === activeDiningTable, 'farm-group-btn': true}">
                      {{ item.number }}
                  </span>
              </span>
          </Col>
      </Row>
      <Row type="flex" align="middle" style="border: 1px solid rgb(244, 244, 244);">
          <Col span="2" style="line-height: 30px; " class="pt15 pb15 tc">
              <span>菜品分类</span>
          </Col>
          <Col span="22" style="line-height: 30px; border-left: 1px solid rgb(244, 244, 244);" class="pt15 pb15">
              <div>
                  <span v-for="(item,index) in dishDatas" :key="index" class="pl10 pr10">
                    <span @click="chooseDishData(item, index)" :class="{'farm-group-btn-active': index === activeDish, 'farm-group-btn': true}">
                        {{ item.foodClassName }}
                    </span>
                </span>
              </div>
                <Row class="mt20 pl20" :gutter="18">
                    <Col span="4" v-for="(item, index) in dishList" class="pb20">
                        <Card>
                        <div class="tc" style="">
                            <img :src="item.foodImage[0]" alt="" width="100%" height="142px">
                            <p class="pt10 ell" :title="item.foodName">{{item.foodName}}</p>
                            <p class="pt5 pb10" v-if="item.discountPrice">
                            <span class="t-orange">¥ {{item.discountPrice}}</span> 
                            <span class="t-grey pl5" style="text-decoration: line-through;">¥ {{item.foodPrice}}</span>
                            </p>
                            <p class="pt5 pb10" v-else>
                            <span class="t-orange">¥ {{item.foodPrice}}</span>
                            </p>
                        </div>
                        </Card>
                    </Col>
                </Row>
          </Col>
      </Row>
  </div> -->
</template>
<script>
  export default {
    data() {
      return {
        dishDatas: [
          {
            foodClassName: '不限',
            id: ''
          }
        ],
        diningTableDatas: [
           {
            roomName: '不限',
            number: '不限'
          }
        ],
        privateRoomDatas: [
           {
            roomName: '不限'
          }
        ],
        account: '',
        activeDish: 0,
        activeDiningTable: 0,
        activePrivateRoom: 0,
        dishList: []
      }
    },
    created() {
      this.account = this.$route.query.uid
      // 获取全部菜品
      this.handleGetdishList('')
      // 初始化获取菜品分类
      this.handleGetdishDatas()
      // 初始化获取餐桌
      this.handleGetTableDatas({
          account: this.account,
          pageNum: 1,
          pageSize: 10000,
          number: '',
          status: ''
      })
      // 初始化获取包房列表
      this.handleGetprivateRoomDatas({
            roomName: '',
            status: '',
            pageNum: 1,
            pageSize: 10000,
            account: this.account
        })
    },
    methods: {
      // 初始化 获取全部菜品
      handleGetdishList (type) {
        this.$api.post('/member/restaurant/findFood', {
          account: this.account,
          pageNum: 1,
          pageSize: 100000,
          foodName: '',
          foodClassId: type,
          status: ''
        }).then(response => {
                console.log('res table', response)
                if (response.code === 200) {
                  this.dishList = response.data.list
                }
            }).catch(error => {
                console.log(error)
            })
      },
      // 菜品化分类
      handleGetdishDatas () {
          this.$api.post('/member/restaurant/findRestaurant', {
              account: this.account,
              pageNum: 1,
              pageSize: 10000
          }).then(response => {
              if (response.code === 200) {
                  this.dishDatas = this.dishDatas.concat(response.data.list)
              }
          }).catch(error => {
              this.$Message.error('初始化分类失败！')
          })
        },
        // 初始化获取餐桌
        handleGetTableDatas (data) {
          this.$api.post('/member/restaurant/findTable', data).then(response => {
              if (response.code === 200) {
                  this.diningTableDatas = this.diningTableDatas.concat(response.data.list)
              }
          }).catch(error => {
              console.log(error)
          })
        },
        handleGetprivateRoomDatas (data) {
          this.$api.post('/member/restaurant/findRoom', data).then(response => {
              if (response.code === 200) {
                  this.privateRoomDatas = this.privateRoomDatas.concat(response.data.list)
              }
          }).catch(error => {
              console.log(error)
          })
        },
        // 选择包房
        choosePrivateRoom (item, index) {
          this.activePrivateRoom = index
        },
        // 选择餐桌
        chooseDiningTable (item, index) {
          this.activeDiningTable = index
        },
        // 选择菜品分类
        chooseDishData (item, index) {
          this.activeDish = index
          this.handleGetdishList(item.id)
        }
    }
  }

</script>
<style scoped>
    .farm-group-btn {
        color: #9B9B9B;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
    .farm-group-btn-active {
        color: #00c587;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
</style>
<style lang="scss">
.dish-list-ivu{
  .ivu-card-body{
    padding: 0px;
  }
}
</style>