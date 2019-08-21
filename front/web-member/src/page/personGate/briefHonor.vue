<template>
    <div class="pb50 briefHonor mt20">
        <div v-if="honorList.length > 0">
            <Row :gutter="16">
                <Col span="6" v-for="(item,index) in honorList" :key="index" class="mb20">
                    <Card :padding="0">
                        <div style="text-align:center">
                          <img :src="item.honorPictureList[0]" height="200" width="100%" @click="handleViewerClick(index)" style="cursor:pointer;display:block">
                          <viewer
                            class="viewer"
                            v-show="0"
                            @inited="inited($event, index)"
                            :images="item.honorPictureList"
                            :ref="`viewer${index}`">
                            <div scope="scope">
                              <img
                              v-for="(child,index) in item.honorPictureList"
                              :key="index"
                              :src="child"
                              :alt="item.content"
                              width="100%"
                              height="200">
                            </div>
                          </viewer>
                        </div>
                        <p class="mt10 mb10">{{ item.name }}</p>
                    </Card>
                </Col>
            </Row>
        </div>
        <div class="ma-polic-img" v-if="honorList.length === 0">
            <img src="../../img/ma-img-002.png">
            <p style="margin-top: 10px;">暂无数据</p>
        </div>
    </div>
</template>
<script>
import 'viewerjs/dist/viewer.css'
import Viewer from 'v-viewer/src/component.vue'
export default {
    components: {
        Viewer
    },
    data () {
        return {
          honorList: [],
          account: '',
          pageSize:12,
          pageNum:1,
          total:0,
          options: {}
        }
    },
    created(){
        this.account = this.$route.query.uid
        this.getData()
    },
    methods: {
        getData () {
            this.$api.post('/member/perfectInfo/findPerfectInfo', {
                account: this.account
            }).then(response => {
                console.log('response', response)
                if (response.code === 200 && response.data.corpHonor) {
                    this.honorList = response.data.corpHonor
                }
            })
        },
        inited ($event, index) {
          this.$refs[`viewer${index}`].$viewer = $event
        },
        handleViewerClick (index) {
          this.$nextTick(() => {
            this.$refs[`viewer${index}`].$viewer.show()
          })
        },
    }
}
</script>
<style lang="scss">
.briefHonor .ma-polic-img{margin-top: 60px;}
.briefHonor .ivu-row{text-align:center;padding: 20px 0;}
.briefHonor .ivu-row p{line-height: 20px;}
</style>
<style lang="scss">
  .viewer-container {
    .viewer-footer{
      background-color: rgba(0,0,0,.5);
    }
    .viewer-title{
      white-space: inherit;
      line-height: 1.5;
      margin: 10px auto 10px;
      max-width: 80%;
    }
    .viewer-navbar{
      background: none;
    }
  }
</style>
