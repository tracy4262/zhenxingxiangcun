<template>
    <div>
        <prview v-if="categoryId === 'CP01' || categoryId === 'CP02' || categoryId === 'CP03' || categoryId === 'CP04' || categoryId === 'CP05' || 
            categoryId === 'CP06' || categoryId === 'CP07' || categoryId === 'CP08'"></prview>
        <prview2 v-if="categoryId === 'CP09'"></prview2>
    </div>
</template>
<script>
import prview from './components/prview'
import prview2 from './components/prview2'
export default {
    components:{
        prview,
        prview2
    },
    data () {
        return {
            categoryId: '',
            templateId: '',
            templateType: '',
            templateName: ''
        }
    },
    created () {
        this.categoryId = this.$route.query.categoryId
        this.templateId = this.$route.query.templateId
        this.templateType = this.$route.query.templateType
        this.templateName = this.$route.query.templateName
    }
}
</script>
<style lang="scss" scoped>

</style>
