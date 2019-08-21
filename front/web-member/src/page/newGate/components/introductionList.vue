<template>
  <div>
       <p style="font-szie:16px;" class="pt20 pb20"><b>{{data.title}}</b></p>
       <p class="pl20 pr20">{{data.content}}</p>
  </div>
</template>
<script>
  export default {
    props: {
      data: {
        type: Object,
        default: () => {
          return {}
        }
      },
    },
    data () {
      return {

      }
    },
    methods: {

    }
  }
</script>
