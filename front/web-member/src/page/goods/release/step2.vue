<template>
    <div>
        <intangibleAssets v-if="categoryId == 'CP09'"></intangibleAssets>
        <prview v-else></prview>
    </div>
</template>
<script>
import prview from './components2/prview'
import intangibleAssets from './components2/intangibleAssets'
export default {
    components:{
        prview,
        intangibleAssets
    },
    data () {
        return {
            categoryId: ''
        }
    },
    created() {
      this.categoryId = this.$route.query.categoryId
    },
}
</script>
<style lang="scss" scoped>

</style>
