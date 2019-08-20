<template>
  <div>
    <Modal :title="title" v-model="visible">
        <div v-for="(item, index) in imgsSrc" v-if="visible" class="pd10" style="width:120px; height:120px;display:inline-block;">
          <img :src="item" style="width: 100%;height:100%">
        </div>
        <div slot="footer"></div>
    </Modal>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        visible: false,
        title: '',
        imgsSrc: []
      }
    },
    methods: {
      previewImages (imgData, title) {
        this.imgsSrc = imgData
        console.log('title',title)
        this.title = title
        this.visible = true
      }
    }
  }
</script>
