// 事件代理
export function EventTrigger (event, ele) {
  // IE
    if (document.all) {
        document.getElementById(ele).click();
    } else {
        var e = document.createEvent('MouseEvents');
        e.initEvent(event, true, true);
        if (document.getElementById(ele)) {
          document.getElementById(ele).dispatchEvent(e);
      }
    }
}



// 解决小数精度问题 加法
export function numAdd (num1, num2) {
    let baseNum, baseNum1, baseNum2;
    try {
      baseNum1 = num1.toString().split('.')[1].length;
  } catch (e) {
      baseNum1 = 0;
  }
    try {
      baseNum2 = num2.toString().split('.')[1].length;
  } catch (e) {
      baseNum2 = 0;
  }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
    return (num1 * baseNum + num2 * baseNum) / baseNum;
}

// 解决小数精度问题 乘法
export function numMulti (num1, num2) {
    var baseNum = 0;
    try {
      baseNum += num1.toString().split('.')[1].length;
  } catch (e) {
  }
    try {
      baseNum += num2.toString().split('.')[1].length;
  } catch (e) {
  }
    return Number(num1.toString().replace('.', '')) * Number(num2.toString().replace('.', '')) / Math.pow(10, baseNum);
}
