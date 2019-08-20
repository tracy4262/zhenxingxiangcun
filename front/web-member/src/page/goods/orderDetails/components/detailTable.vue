<template>
  <div>
    <Table
      class="mt20"
      ref="selection"
      @on-selection-change="getTempAll"
      :columns="columns"
      :data="data"
    ></Table>
  </div>
</template>
<script>
export default {
  props: {
    data: {
        type: Array,
        default: () => {
            return []
        }
    },
    columns: {
        type: Array,
        default: () => {
            return []
        }
    }
  },
  data() {
    return {
      buyTotal: 0
    };
  },
  methods: {
    getTempAll(selection) {
      var temp = 0;
      selection.forEach(e => {
        temp += parseInt(e.biddingCount);
      });
      this.buyTotal = temp;
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
