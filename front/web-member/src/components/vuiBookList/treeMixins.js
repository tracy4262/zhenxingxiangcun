// 编辑
export function EditNode (o, d, i) {
  d.edit = true
  o.$nextTick(() => {
    o.$refs[`input${i}`][0].focus()
  })
}

// 焦点
export function FocusNode (o) {
  var input = o.$refs[`input${o.data.length - 1}`]
  console.log("input",input)
  if (input[0]) {
    o.$nextTick(() => {
      input[0].focus()
    })
  }
}

// 取消选择
export function CancelNode (o) {
  o.forEach(item => {
    item.checked = false
    item.children.forEach(child => { child.checked = false })
  })
}
