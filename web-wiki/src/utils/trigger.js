export function trigger (event, ele) {
  // IE
  if (document.all) {
    document.getElementById(ele).click()
  } else {
    var e = document.createEvent('MouseEvents')
    // 这里的click可以换成你想触发的行为
    e.initEvent(event, true, true)
    // 这里的clickME可以换成你想触发行为的DOM结点
    document.getElementById(ele).dispatchEvent(e)
  }
}
