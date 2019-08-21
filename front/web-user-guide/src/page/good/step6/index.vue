<template>
    <div>
        <prview></prview>
    </div>
</template>
<script>
import prview from './components/prview'
export default {
    components:{
        prview
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
