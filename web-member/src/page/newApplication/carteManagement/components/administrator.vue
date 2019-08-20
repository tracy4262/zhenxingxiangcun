<template>
  <div class="identity">
    <div class="certification pd20" v-for="(item, index) in data">
        <Row :gutter="38">
          <Col span="12" v-if="item.memberNameFlag" class="pb15">
          会员全称：{{item.memberName}}
            </FormItem>
          </Col>
          <Col span="12" v-if="item.memberAbbreviationFlag" class="pb15">
          会员名称简写：{{item.memberAbbreviation}}
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12" v-if="item.nameFlag" class="pb15">
          网站管理员姓名：{{item.name}}
          </Col>
          <Col span="12" v-if="item.sexFlag" class="pb15">
          性别：{{item.sex}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12" v-if="item.cardFlag" class="pb15">
          身份证号：{{item.card}}
          </Col>
          <Col span="12" v-if="item.phoneFlag" class="pb15">
          手机：{{item.phone}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12" v-if="item.jobFlag" class="pb15">
          职务：{{item.job}}
          </Col>
          <Col span="12" v-if="item.jobTitleFlag" class="pb15">
          职称：{{item.jobTitle}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12" v-if="item.emailFlag" class="pb15">
          邮箱：{{item.email}}
          </Col>
        </Row>
         <Row :gutter="38">
          <Col span="24" v-if="item.dutyFlag" class="pb15">
          职责：{{item.duty}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12" v-if="item.cardImageFlag" class="pb15">
            身份证照片：<span class="t-green" @click="previewImages(item.cardImage, '联系方式——身份证照片')">查看</span>
          </Col>
          <Col span="12" v-if="item.empowerImageFlag" class="pb15">
            授权书照片：<span class="t-green" @click="previewImages(item.empowerImage, '联系方式——授权书照片')">查看</span>
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
        //   member_name: '',
        //   member_abbreviation: '',
        //   sex: '',
        //   name: '',
        //   phone: '',
        //   card: '',
        //   job: '',
        //   job_title: '',
        //   duty: '',
        //   card_image: [],
        //   empower_image: [],
        //   isEdit: true,
        //   member_name_id: ''
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

