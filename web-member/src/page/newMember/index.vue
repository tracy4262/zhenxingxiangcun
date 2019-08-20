<template>
	<div style="background: #F9F9F9;">
		<!-- 导航栏 -->
		<Affix>
			<top :address="false"></top>
		</Affix>
      <div :style="{'min-height': height}">
          <div class="layouts">
            <Row type="flex" align="middle">
              <Col span="12">
                <Breadcrumb class="pd20">
                  <BreadcrumbItem to="/index">首页</BreadcrumbItem>
                  <BreadcrumbItem>会员中心</BreadcrumbItem>
                </Breadcrumb>
              </Col>
              <Col span="12">
              </Col>
            </Row>
            <Row>
              <Col span="5">
                <file-list class="mb20"></file-list>
                <watch-list class="mb20"></watch-list>
								<mall-list class="mb20"></mall-list>
                <app-list class="mb20"></app-list>
              </Col>
              <Col span="19">
                <member-center class="ml20"></member-center>
              </Col>
            </Row>
          </div>
      </div>
		<foot></foot>
  </div>
</template>
<script>
import top from '../../top'
import foot from '../../foot'
import appList from './components/appList'
import watchList from './components/watchList'
import fileList from './components/fileList'
import mallList from './components/mallList'
import memberCenter from './components/memberCenter'
  export default {
    components: {
      top,
      foot,
      appList,
      fileList,
			watchList,
			mallList,
      memberCenter
    },
    data () {
      return {
        height: 0
      }
    },
    created () {
			this.checkAuth()
    },
    methods: {
      // 检查认证到了第几步
			checkAuth () {
				this.$api.post('/member-reversion/realStep/findEnableStep', {
					account: this.$user.loginAccount
				}).then(response => {
					console.log('check auth response', response)
					if (response.code === 200) {
						if (response.data) {
							// 说明已经进行了实名认证根据步骤跳转到相应的实名认证步骤 step为7说明实名认证已完成
							if (response.data.step !== '7') {
								this.$Notice.config({
									top: 120,
									duration: 10
								})
								this.$Notice.info({
									title: '实名认证',
									render: h => {
										return h('span', [
											'您还未完成实名认证，是否 ',
											h('a', {
												style: {
													color: '#00c587'
												},
												on: {
													click: () => {
														// this.goToAuth(this.$user.isIdentityVerification, response.data.userType)
														if (response.data.step === '6') {
															this.$router.push({
																path: `/auth/step6`,
																query: {
																	templateId: response.data.templateId
																}
															})
														} else if (response.data.step === '6.4') {
															this.$router.push({
																path: `/auth/step7`,
																query: {
																	templateId: response.data.templateId
																}
															})
														} else {
															let step = parseInt(response.data.step) + 1
															this.$router.push({
																path: `/auth/step${step}`,
																query: {
																	templateId: response.data.templateId
																}
															})
														}
														this.$Notice.destroy()
													}
												}
											}, '继续认证 '), '？'
										])
									}
								})
							}
						} else {
							// 说明还未进行过实名认证
							this.$Notice.config({
								top: 120,
								duration: 10
							})
							this.$Notice.info({
								title: '实名认证',
								render: h => {
									return h('span', [
										'您还未完成实名认证，是否 ',
										h('a', {
											style: {
												color: '#00c587'
											},
											on: {
												click: () => {
													this.$router.push({
														path: `/auth/step1`
													})
													this.$Notice.destroy()
												}
											}
										}, '继续认证 '), '？'
									])
								}
							})
						}
					}
				})
			},
    },
    mounted () {
      this.height = `${window.innerHeight}px`
    }
  }
</script>
<style scoped>
  .nswy-id {
    font-size: 14px;
    color: #4A4A4A;;
  }
</style>
