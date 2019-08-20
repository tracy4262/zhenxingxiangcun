<template>
  <Card dis-hover v-show="data.length >0">
    <h5 class="mb10 pl5" style="border-left: 5px solid #00c587" >目录({{`${data.length }章`}})</h5>
    <dl class="vui-book-cata">
      <template v-for="(item,index) in data">
        <dt class="b" @click="handleToggle(item)">第{{index+1}}章：{{item.title}}</dt>
        <template v-for="(child,i) in item.children" v-if="item.expand">
          <transition name="fade">
            <dd class="mt10 mb10 pl30" @click="handleShowContent(child,item.title)" :class="{active: child.checked}">
              <Tooltip :content="child.title" placement="top">
                第{{i+1}}节：{{child.title}}
              </Tooltip>
            </dd>
          </transition>
        </template>
      </template>
    </dl>
  </Card>
</template>
<script>
export default {
  data: () => ({
    data: []
  }),
    created() {
        this.$api.post('/member/inforMation/findInFormationBookInfo', {id:this.$route.query.informationId,book_type:this.$route.query.book_type,flag:0}).then(response => {
            let result = response.data
            if(result != '' ) {
                this.data = result.book_detail_data;
                this.data.forEach(i => {
                    i.children.forEach(d => {
                        d.checked = false
                    })
                })
                this.data[0].children[0].checked = true;
            }
        }).catch(error => {
            console.error(error)
        })
    },
  methods: {
    handleToggle (item) {
      item.expand = !item.expand
    },
    handleShowContent (child,title) {
      this.data.forEach(i => {
        i.children.forEach(d => {
          d.checked = false
        })
      })
      child.checked = true
      this.$emit('on-get-data', title,child)
    }
  }
}
</script>
<style lang="scss">
.vui-book-cata{
  * {
    cursor: pointer;
  }
  .active{
    color: #00c587;
  }
  .ivu-tooltip,
  .ivu-tooltip-rel{
    display: block;
  }
  .ivu-tooltip-rel{
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

}
</style>
