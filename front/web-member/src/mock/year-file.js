export default{
  'data|1-5': [{
    year: /[2][0][1-9][1-9]/,
    name: function () {
      return this.year + '年度'
    }
  }]
}
