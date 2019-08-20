// 过滤tree节点
export const TreeFilter = {
  data () {
    return {
      keyWord: '',
      newTreeData: []
    }
  },
  methods: {
    onSearchTree () {
      this.newTreeData = []
      if (this.keyWord) {
        this.getArray(this.oldTreeData, this.keyWord)
        this.data = this.newTreeData
      } else {
        this.data = this.oldTreeData
      }
    },
    getArray(data,name){
      for (var i in data) {
        if (data[i].name.indexOf(name) !== -1) {
          this.newTreeData.push(data[i])
        } else {
          this.getArray(data[i].children, name);
        }
      }
    }
  }
}