<template>
  <div v-if="data" class="mt30">
    <Title title="编辑区" desc="注： NaN表示不限长度"></Title>
    <ele-input
    :data="data"
    v-if="data.type === 'text'"
    @on-save="handleSubmit"
    @on-cancel="handleCancel"
    ></ele-input>

    <ele-textarea
    :data="data"
    v-if="data.type === 'textarea'"
    @on-save="handleSubmit"
    @on-cancel="handleCancel"
    ></ele-textarea>

    <ele-select
    :data="data"
    v-if="data.type === 'select'"
    @on-save="handleSubmit"
    @on-cancel="handleCancel"
    ></ele-select>

    <ele-radio
    :data="data"
    v-if="data.type === 'radio'"
    @on-save="handleSubmit"
    @on-cancel="handleCancel"
    ></ele-radio>


  </div>
</template>
<script>
import Title from './components/title'
import eleInput from './components/input'
import eleTextarea from './components/textarea'
import eleSelect from './components/select'
import eleRadio from './components/radio'
export default {
  props: {
    data: Object
  },
  components: {
    Title,
    eleInput,
    eleTextarea,
    eleSelect,
    eleRadio
  },
  data: () => ({
  }),
  methods: {
    handleSubmit () {
      this.$emit('on-save', this.data.type)
    },
    handleCancel () {
      this.$emit('on-cancel')
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
