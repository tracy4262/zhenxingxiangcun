<template>
  <div>
    <top :address="false" />
    <div class="main">
      <Row :gutter="16">
        <Col span="24">
            <router-view></router-view>
        </Col>
      </Row>
    </div>
    <foot></foot>
  </div>
</template>
<script>
import top from '../../top'
import highApp from '~components/memberHighApp'
import BaseApp from '~components/memberBaseApp'
import divider from '~components/divider'
import memberHeader from '../member/components/memberHeader'
import UserApp from '~components/memberUserApp'
import foot from '../../foot'

export default{
    components: {
        top,
        highApp,
        BaseApp,
        divider,
        memberHeader,
        UserApp,
        foot
    },
    data () {
        return {

        }
    },
    methods: {

    }
}
</script>
<style scoped>
.main{
  min-height: 800px
}
</style>
