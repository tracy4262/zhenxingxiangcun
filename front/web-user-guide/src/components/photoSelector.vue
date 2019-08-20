<template>
    <div>
        <Modal
        v-model="photoSelectorShow"
        @on-ok="handleSaveResult"
        :title="title" width="800"
        :mask-closable="false"
        :closable="false"
        :transfer="transfer"
        :styles="{top: '20px'}">
            <Select style="width:200px" v-model="album" @on-change="handleAlbumChange" not-found-text="暂无相册">
                <Option v-for="item in photoAlbum" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
            <div class="photo-content">
                <Row>
                    <Checkbox-group v-model="choosed">
                        <i-col span="4" v-for="(item,index) in resultDatas" :key="index">
                            <figure>
                                <Checkbox :label="item.src" class="photo-checkbox" :disabled="item.disable"><span>&nbsp;&nbsp;</span></Checkbox>
                                <img :src="item.src" style="width: 110px; height: 110px; margin-left: 10px; margin-top: 10px;">
                            </figure>
                        </i-col>
                    </Checkbox-group>
                </Row>
            </div>
            <div class="photo-selected">
                <Row>
                    <i-col span="4" v-for="(item,index) in choosed" :key="index">
                        <figure>
                            <img :src="item" style="width: 110px; height: 110px; margin-left: 10px; margin-top: 10px;">
                        </figure>
                    </i-col>
                </Row>
            </div>
        </Modal>
    </div>
</template>

<script>
    export default {
        name: 'photoSelector',
        data () {
            return {
                photoSelectorShow: false,
                title: '插入图片',
                choosed: [],
                album: 0,
                photoAlbum: []
            }
        },
        props: {
            transfer: {
                type: Boolean,
                default: false
            },
            resultDatas: Array
        },
        mounted() {
          this.$nextTick(() => {
            // 从我的风采导入图片 默认显示第一个相册
            this.getAlbum('photo')
          })
        },
        methods: {
            getAlbum (name) {
              this.$api.post('/member/product-base/media-library-query-all', {
                  account: this.$user.loginAccount,
                  mediaType: 1
              }).then(response => {
                  if (response.code === 200) {
                      if (response.data.length !== 0) {
                          this.album = response.data[0].mediaId
                          this.handleAlbumChange(response.data[0].mediaId)
                      }
                      response.data.forEach(element => {
                          this.photoAlbum.push({
                              label: element.mediaName,
                              value: element.mediaId
                          })
                      })
                  }
              }).catch(error => {
                  this.$Message.error('获取相册异常！')
              })
            },
            // 相册改变
            handleAlbumChange (value) {
                this.$emit('on-change', value)
            },
            // 保存最后结果
            handleSaveResult () {
                this.$emit('on-get-result', this.choosed)
                this.photoSelectorShow = false
            }
        }
    }
</script>

<style scoped>
    .photo-content {
        width: 100%;
        height: 300px;
        margin-top: 10px;
        border: 1px #e9eaec solid;
        overflow: auto;
        overflow-x: hidden;
    }
    .photo-selected {
        width: 100%;
        height: 150px;
        margin-top: 10px;
        border: 1px #e9eaec solid;
        overflow: auto;
        overflow-x: hidden;
    }
    .photo-checkbox {
        position: absolute;
        z-index: 1;
        right: -10px;
        top: 12px;
    }
</style>
