<template>
  <div >
    <div class="certification pd20" v-for="(item, index) in data">
        <Row :gutter="38">
          <Col span="24"  class="pb15" v-if="item.memberClassFlag">
            会员类别：{{item.memberClass}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12"  class="pb15" v-if="item.memberNameFlag">
            会员名称全称：{{item.memberName}}
          </Col>
           <Col span="12"  class="pb15" v-if="item.memberNamePinyinFlag">
            全称拼音：{{item.memberNamePinyin}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12"  class="pb15" v-if="item.memberAbbreviationFlag">
            会员名称简写：{{item.memberAbbreviation}}
          </Col>
          <Col span="12"  class="pb15" v-if="item.abbreviationPinyinFlag">
            会员名称简称拼音：{{item.abbreviationPinyin}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12"  class="pb15" v-if="item.aptitudeNameFlag">
            资质名称：{{item.aptitudeName}}
          </Col>
           <Col span="12"  class="pb15" v-if="item.aptitudeNumberFlag">
            资质编号：{{item.aptitudeNumber}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="24"  class="pb15" v-if="item.aptitudeImageFlag">
            资质照片：<span class="t-green" @click="previewImages(item.aptitudeImage, '资质认证——资质照片')">查看</span>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="24"  class="pb15" v-if="item.remarkFlag">
            说明：{{item.remark}}
          </Col>
        </Row>
    </div>
    <image-preview ref="imagePreview"></image-preview>
  </div>
</template>
<script>
import imagePreview from './imagePreview'
  export default {
  components: {
    imagePreview,
  },
    props: {
      data: {
        type: Array,
        default: () => {
          return []
        }
      }
    },
    data () {
      return {
        // data: [{
        //   user_id: this.account,
        //   status: true,
        //   member_class: '',
        //   member_name: '',
        //   member_name_pinyin: '',
        //   member_abbreviation: '',
        //   abbreviation_pinyin: '',
        //   aptitude_name: '',
        //   aptitude_number: '',
        //   aptitude_image: [],
        //   remark: '',
        //   isEdit: true
        // }],
      }
    },
    created() {
    },
    methods: {
      // 查看照片
      previewImages (img, title) {
        this.$refs['imagePreview'].previewImages(img, title)
      },
    }
  }
</script>

