export function letter () {
  var l = []
  for (var i = 0; i < 26; i++) {
    l.push({
      name: String.fromCharCode(65 + i),
      checked: false
    })
  }
  return l
}
