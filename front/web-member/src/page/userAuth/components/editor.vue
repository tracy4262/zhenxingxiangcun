<template>
    <div class="pd20">     
        <quil-editor @quilCon="getDetail" :content="data[0].editor"></quil-editor>
    </div>
</template>
<script>
import quilEditor from '~components/vuequilEditor';
export default {
  components: {
    quilEditor
  },
  data() {
    return {
        data:[{
           editor: '' 
        }]
    };
  },
  methods: {
     //点击下一步的时候表单验证
    handleSubmit(){
        this.$emit('on-submit',true)
    },
    //接收父组件的数据
    getData(val){
        console.log('editor',val)
        this.data = val
    },
    //接收编辑器返回的数据
    getDetail (e) {
      this.data[0].editor = e
    }
  }
};
</script>
